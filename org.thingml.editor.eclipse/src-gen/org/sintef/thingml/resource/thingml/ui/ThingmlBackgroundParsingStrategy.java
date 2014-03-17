/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sintef.thingml.resource.thingml.ui;

/**
 * A background parsing strategy that starts parsing after a amount of time after
 * the last key stroke. If keys are pressed within the delay interval, the delay
 * is reset. If keys are pressed during background parsing the parse thread is
 * stopped and a new parse task is scheduled.
 */
public class ThingmlBackgroundParsingStrategy {
	
	private static long DELAY = 500;
	
	/**
	 * this timer is used to schedule a parsing task and execute it after a given delay
	 */
	private Object lock = new Object();
	
	/**
	 * the background parsing task (may be null)
	 */
	private org.eclipse.core.runtime.jobs.Job job;
	
	/**
	 * Schedules a task for background parsing that will be started after a delay.
	 */
	public void parse(org.eclipse.jface.text.DocumentEvent event, final org.sintef.thingml.resource.thingml.IThingmlTextResource resource, final org.sintef.thingml.resource.thingml.ui.ThingmlEditor editor) {
		parse(event.getDocument(), resource, editor, DELAY);
	}
	
	/**
	 * Schedules a task for background parsing that will be started after a delay.
	 */
	public void parse(org.eclipse.jface.text.IDocument document, final org.sintef.thingml.resource.thingml.IThingmlTextResource resource, final org.sintef.thingml.resource.thingml.ui.ThingmlEditor editor, long delay) {
		parse(document.get(), resource, editor, delay);
	}
	
	/**
	 * Schedules a task for background parsing that will be started after a delay.
	 */
	public void parse(final String contents, final org.sintef.thingml.resource.thingml.IThingmlTextResource resource, final org.sintef.thingml.resource.thingml.ui.ThingmlEditor editor, long delay) {
		if (resource == null) {
			return;
		}
		if (contents == null) {
			return;
		}
		
		// this synchronization is needed to avoid the creation of multiple tasks. without
		// the synchronization this could easily happen, when this method is accessed by
		// multiple threads. the creation of multiple tasks would imply that multiple
		// background parsing threads for one editor are created, which is not desired.
		synchronized (lock) {
			// cancel old task
			if (job != null) {
				// stop current parser (if there is one)
				job.cancel();
				try {
					job.join();
				} catch (InterruptedException e) {}
			}
			
			// schedule new task
			job = new org.eclipse.core.runtime.jobs.Job("parsing document") {
				
				protected org.eclipse.core.runtime.IStatus run(org.eclipse.core.runtime.IProgressMonitor monitor) {
					try {
						resource.reload(new java.io.ByteArrayInputStream(contents.getBytes()), null);
					} catch (java.io.IOException e) {
						e.printStackTrace();
					}
					// the post parsing stuff must be executed in a separate job to avoid deadlocks on
					// the document
					org.eclipse.core.runtime.jobs.Job finishJob = new org.eclipse.core.runtime.jobs.Job("refreshing views") {
						protected org.eclipse.core.runtime.IStatus run(org.eclipse.core.runtime.IProgressMonitor monitor) {
							editor.notifyBackgroundParsingFinished();
							return org.eclipse.core.runtime.Status.OK_STATUS;
						}
					};
					finishJob.schedule(10);
					return org.eclipse.core.runtime.Status.OK_STATUS;
				}
				
				protected void canceling() {
					resource.cancelReload();
				}
			};
			job.schedule(delay);
		}
	}
	
}
