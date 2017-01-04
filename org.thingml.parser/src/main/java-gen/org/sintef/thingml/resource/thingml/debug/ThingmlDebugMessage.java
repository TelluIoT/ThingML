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
package org.sintef.thingml.resource.thingml.debug;

/**
 * DebugMessages are exchanged between the debug server (the Eclipse debug
 * framework) and the debug client (a running process or interpreter). To exchange
 * messages they are serialized and sent over sockets.
 */
public class ThingmlDebugMessage {
	
	private static final char DELIMITER = ':';
	private org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes messageType;
	private String[] arguments;
	
	public ThingmlDebugMessage(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes messageType, String[] arguments) {
		super();
		this.messageType = messageType;
		this.arguments = arguments;
	}
	
	public ThingmlDebugMessage(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes messageType, java.util.List<String> arguments) {
		super();
		this.messageType = messageType;
		this.arguments = new String[arguments.size()];
		for (int i = 0; i < arguments.size(); i++) {
			this.arguments[i] = arguments.get(i);
		}
	}
	
	public org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes getMessageType() {
		return messageType;
	}
	
	public String[] getArguments() {
		return arguments;
	}
	
	public String serialize() {
		java.util.List<String> parts = new java.util.ArrayList<String>();
		parts.add(messageType.name());
		for (String argument : arguments) {
			parts.add(argument);
		}
		return org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.encode(DELIMITER, parts);
	}
	
	public static ThingmlDebugMessage deserialize(String response) {
		java.util.List<String> parts = org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.decode(response, DELIMITER);
		String messageType = parts.get(0);
		String[] arguments = new String[parts.size() - 1];
		for (int i = 1; i < parts.size(); i++) {
			arguments[i - 1] = parts.get(i);
		}
		org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes type = org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.valueOf(messageType);
		ThingmlDebugMessage message = new ThingmlDebugMessage(type, arguments);
		return message;
	}
	
	public boolean hasType(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes type) {
		return this.messageType == type;
	}
	
	public String getArgument(int index) {
		return getArguments()[index];
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + "[" + messageType.name() + ": " + org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.explode(arguments, ", ") + "]";
	}
	
}
