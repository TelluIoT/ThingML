/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.thingml.testing.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TimeoutThreadPoolExecutor {
	private ExecutorService executor;
	private ScheduledExecutorService canceller;
	private boolean isShutdown;
	private Collection<Future<?>> complexFutures;
	
	public TimeoutThreadPoolExecutor(int nThreads) {
		executor = Executors.newFixedThreadPool(nThreads > 2 ? nThreads-1 : 1);
		canceller = Executors.newSingleThreadScheduledExecutor();
		isShutdown = false;
		complexFutures = new ArrayList<Future<?>>();
	}
	
	public TimeoutThreadPoolExecutor() {
		this(Runtime.getRuntime().availableProcessors());
	}
	
	// Submit a simple task which will be stopped after timeout
	public Future<?> submit(Runnable task, long timeoutSeconds) {
		if (isShutdown) throw new RejectedExecutionException("Executor is shutdown");
		return new Cancellable(task, timeoutSeconds).future();
	}
	
	// Submit a task which will be stopped after timeout - if it returns before, the resulting Runnables will also be scheduled for execution with the same timeout
	public Future<?> submit(Callable<Collection<Runnable>> task, long timeoutSeconds) {
		if (isShutdown) throw new RejectedExecutionException("Executor is shutdown");
		 // We need to keep track of these so that we can let them finish and add new child-tasks before we shutdown the actual executor
		Future<?> complexFuture = new Cancellable(task, timeoutSeconds).future();
		complexFutures.add(complexFuture);
		return complexFuture;
	}
	
	public void shutdown(int timeoutMinutes) throws InterruptedException, TimeoutException {
		isShutdown = true;
		 // Since we are doing many waits, we want to keep track of it so that the total timeout is what the caller expects
		long waittime = ((long)timeoutMinutes)*60*1000;
		long startWait = System.currentTimeMillis();
		
		// Wait for all complex tasks to complete - and possibly add new simple tasks
		for (Future<?> future : complexFutures) {
			try {
				long alreadyWaited = System.currentTimeMillis()-startWait;
				if (alreadyWaited >= waittime) throw new TimeoutException();
				
				future.get(waittime-alreadyWaited, TimeUnit.MILLISECONDS);
			}
			catch (CancellationException | ExecutionException e) {} // We don't care about these
			catch (InterruptedException | TimeoutException e) {
				// Force shutdown of the executors
				executor.shutdownNow();
				canceller.shutdownNow();
				throw e;
			}
		}
		
		// Now all complex tasks are completed, so we properly shutdown the executor - and try to wait for completion
		try {
			// Stop the executor
			long alreadyWaited = System.currentTimeMillis()-startWait;
			if (alreadyWaited >= waittime) throw new TimeoutException();
			executor.shutdown();
			if (!executor.awaitTermination(waittime-alreadyWaited, TimeUnit.MILLISECONDS))
				throw new TimeoutException();
			
			// Stop the canceller (since the executor is done - we don't care about running or future cancellations)
			alreadyWaited = System.currentTimeMillis()-startWait;
			if (alreadyWaited >= waittime) throw new TimeoutException();
			canceller.shutdownNow();
			if (!canceller.awaitTermination(waittime-alreadyWaited, TimeUnit.MILLISECONDS))
				throw new TimeoutException();
		} catch (InterruptedException | TimeoutException e) {
			// Force shutdown of executors
			executor.shutdownNow();
			canceller.shutdownNow();
			throw e;
		}
	}
	
	/* --- Helper class that enables interrupting long running tasks in the executor using the scheduled executor (canceller) --- */
	private class Cancellable implements Runnable {
		private boolean isComplex;
		private long timeout;
		private Future<?> selfFuture;
		private Runnable cancel;
		private Future<?> cancelFuture;
		private Runnable simple;
		private Callable<Collection<Runnable>> complex;
		
		private Cancellable(boolean isComplex, long timeout) {
			this.isComplex = isComplex;
			this.timeout = timeout;
			// Create a canceller to schedule when execution starts
			this.cancel = new Runnable() {
				@Override
				public void run() {
					selfFuture.cancel(true);
				}
			};
		}

		/* --- Submit a simple task --- */
		public Cancellable(Runnable task, long timeout) {
			this(false, timeout);
			// Submit ourself for execution
			this.simple = task;
			this.selfFuture = executor.submit(this);
		}
		
		/* --- Submit a complex task --- */
		public Cancellable(Callable<Collection<Runnable>> task, long timeout) {
			this(true, timeout);
			// Submit ourself for execution
			this.complex = task;
			this.selfFuture = executor.submit(this);
		}

		@Override
		public void run() {
			// Schedule our own death
			this.cancelFuture = canceller.schedule(cancel, this.timeout, TimeUnit.SECONDS);
			// Run original work
			try {
				if (!this.isComplex) {
					// For simple tasks, we just run the original task
					this.simple.run();
				} else {
					// For complex tasks, we call the original task to get potential more tasks
					Collection<Runnable> tasks = this.complex.call();
					for (Runnable task : tasks) {
						// Submit these tasks for execution - with the original timeout
						// This bypasses the shutdown of the executor
						new Cancellable(task, timeout);
					}
				}
			} catch (Exception e) {} // We cannot really do anything with them
			// Cancel death
			finally {
				this.cancelFuture.cancel(true);
			}
		}
		
		public Future<?> future() { return this.selfFuture; }
	}
}
