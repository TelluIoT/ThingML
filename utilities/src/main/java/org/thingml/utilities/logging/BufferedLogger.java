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
package org.thingml.utilities.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class BufferedLogger extends Logger {
	private static class LogMsg {
		public enum Type { DEBUG, INFO, WARNING, ERROR };
		
		public Type type;
		public String message;
		
		public LogMsg(Type type, String message) {
			this.type = type;
			this.message = message;
		}
	}
	
	List<LogMsg> messages;
	
	public BufferedLogger() {
		messages = new ArrayList<LogMsg>();
	}

	/* --- Logger interface --- */
	@Override
	public void debug(String message) {
		synchronized (messages) {
			messages.add(new LogMsg(LogMsg.Type.DEBUG, message));
		}
	}

	@Override
	public void info(String message) {
		synchronized (messages) {
			messages.add(new LogMsg(LogMsg.Type.INFO, message));
		}
	}

	@Override
	public void warning(String message) {
		synchronized (messages) {
			messages.add(new LogMsg(LogMsg.Type.WARNING, message));
		}
	}

	@Override
	public void error(String message) {
		synchronized (messages) {
			messages.add(new LogMsg(LogMsg.Type.ERROR, message));
		}
	}
	
	/* --- Access to buffered messages --- */
	private boolean hasAny(LogMsg.Type type) {
		synchronized (messages) {
			if (messages.isEmpty()) return false;
			for (LogMsg message : messages) {
				if (message.type.equals(type)) return true;
			}
			return false;
		}
	}

	public boolean hasDebug() { return hasAny(LogMsg.Type.DEBUG); }
	public boolean hasInfo() { return hasAny(LogMsg.Type.INFO); }
	public boolean hasWarning() { return hasAny(LogMsg.Type.WARNING); }
	public boolean hasError() { return hasAny(LogMsg.Type.ERROR); }
	
	
	private String getAll(LogMsg.Type type, boolean remove) {
		synchronized (messages) {
			StringBuilder result = new StringBuilder();
			ListIterator<LogMsg> it = messages.listIterator();
			while (it.hasNext()) {
				LogMsg msg = it.next();
				if (msg.type.equals(type))
					result.append(msg.message+System.lineSeparator());
				if (remove)
					it.remove();
			}
			return result.toString();
		}
	}
	
	public String getDebug(boolean remove) { return getAll(LogMsg.Type.DEBUG, remove); }
	public String getDebug() { return getDebug(true); }
	
	public String getInfo(boolean remove) { return getAll(LogMsg.Type.INFO, remove); }
	public String getInfo() { return getInfo(true); }
	
	public String getWarning(boolean remove) { return getAll(LogMsg.Type.WARNING, remove); }
	public String getWarning() { return getWarning(true); }
	
	public String getError(boolean remove) { return getAll(LogMsg.Type.ERROR, remove); }
	public String getError() { return getError(true); }

	
	/* --- Print the buffered messages to another logger --- */
	public void print(Logger logger, boolean remove) {
		synchronized (messages) {
			ListIterator<LogMsg> it = messages.listIterator();
			while (it.hasNext()) {
				LogMsg msg = it.next();
				switch (msg.type) {
					case DEBUG:
						logger.debug(msg.message);
						break;
					case INFO:
						logger.info(msg.message);
						break;
					case WARNING:
						logger.warning(msg.message);
						break;
					case ERROR:
						logger.error(msg.message);;
						break;
				}
				if (remove)
					it.remove();
			}
		}
	}
	
	public void print(Logger logger) { print(logger, true); }
	public void print(boolean remove) { print(Logger.SYSTEM, remove); }
	public void print() { print(Logger.SYSTEM, true); }
}
