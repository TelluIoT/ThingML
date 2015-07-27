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
package org.sintef.thingml.resource.thingml.debug;

/**
 * The DebugProxy allows to communicate between the interpreter, which runs in a
 * separate thread or process and the Eclipse Debug framework (i.e., the
 * DebugTarget class).
 */
public class ThingmlDebugProxy {
	
	public static final int STARTUP_DELAY = 1000;
	
	private java.io.PrintStream output;
	
	private java.io.BufferedReader reader;
	
	private org.sintef.thingml.resource.thingml.debug.ThingmlDebugTarget debugTarget;
	
	private org.sintef.thingml.resource.thingml.debug.ThingmlDebugCommunicationHelper communicationHelper = new org.sintef.thingml.resource.thingml.debug.ThingmlDebugCommunicationHelper();
	
	public ThingmlDebugProxy(org.sintef.thingml.resource.thingml.debug.ThingmlDebugTarget debugTarget, int requestPort) throws java.net.UnknownHostException, java.io.IOException {
		this.debugTarget = debugTarget;
		// give interpreter a chance to start
		try {
			Thread.sleep(STARTUP_DELAY);
		} catch (InterruptedException e) {
		}
		startSocket(requestPort);
	}
	
	private void startSocket(int requestPort) throws java.net.UnknownHostException, java.io.IOException {
		// creating client proxy socket (trying to connect)...
		java.net.Socket client = new java.net.Socket("localhost", requestPort);
		// creating client proxy socket - done. (connected)
		try {
			java.io.BufferedInputStream input = new java.io.BufferedInputStream(client.getInputStream());
			reader = new java.io.BufferedReader(new java.io.InputStreamReader(input));
		} catch (java.io.IOException e) {
			System.out.println(e);
		}
		try {
			output = new java.io.PrintStream(client.getOutputStream());
		} catch (java.io.IOException e) {
			System.out.println(e);
		}
	}
	
	public void resume() {
		sendCommand(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.RESUME);
	}
	
	public void stepOver() {
		sendCommand(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.STEP_OVER);
	}
	
	public void stepInto() {
		sendCommand(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.STEP_INTO);
	}
	
	public void stepReturn() {
		sendCommand(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.STEP_RETURN);
	}
	
	public void terminate() {
		sendCommand(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.EXIT);
	}
	
	public org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage getStack() {
		return sendCommandAndRead(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.GET_STACK);
	}
	
	public void addLineBreakpoint(String location, int line) {
		org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage message = new org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.ADD_LINE_BREAKPOINT, new String[] {location, Integer.toString(line)});
		communicationHelper.sendEvent(message, output);
	}
	
	public void removeLineBreakpoint(String location, int line) {
		org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage message = new org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.REMOVE_LINE_BREAKPOINT, new String[] {location, Integer.toString(line)});
		communicationHelper.sendEvent(message, output);
	}
	
	public org.eclipse.debug.core.model.IVariable[] getStackVariables(String stackFrame) {
		org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage response = sendCommandAndRead(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.GET_FRAME_VARIABLES, new String[] {stackFrame});
		String[] ids = response.getArguments();
		// fetch all variables
		org.eclipse.debug.core.model.IVariable[] variables = getVariables(ids);
		return variables;
	}
	
	public org.eclipse.debug.core.model.IVariable[] getVariables(String... requestedIDs) {
		org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage response = sendCommandAndRead(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.GET_VARIABLES, requestedIDs);
		String[] varStrings = response.getArguments();
		org.sintef.thingml.resource.thingml.debug.ThingmlDebugVariable[] variables  = new org.sintef.thingml.resource.thingml.debug.ThingmlDebugVariable[varStrings.length];
		int i = 0;
		for (String varString : varStrings) {
			java.util.Map<String, String> properties = org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.convertFromString(varString);
			
			// convert varString to variables and values
			String valueString = properties.get("!valueString");
			String valueRefType = "valueRefType";
			java.util.Map<String, Long> childVariables = new java.util.TreeMap<String, Long>(new java.util.Comparator<String>() {
				public int compare(String s1, String s2) {
					return s1.compareToIgnoreCase(s2);
				}
			});
			for (String property : properties.keySet()) {
				// ignore special properties - they are not children
				if (property.startsWith("!")) {
					continue;
				}
				childVariables.put(property, Long.parseLong(properties.get(property)));
			}
			String id = properties.get("!id");
			org.eclipse.debug.core.model.IValue value = new org.sintef.thingml.resource.thingml.debug.ThingmlDebugValue(debugTarget, id, valueString, valueRefType, childVariables);
			
			String variableName = properties.get("!name");
			String variableRefType = properties.get("!type");
			
			org.sintef.thingml.resource.thingml.debug.ThingmlDebugVariable variable = new org.sintef.thingml.resource.thingml.debug.ThingmlDebugVariable(debugTarget, variableName, value, variableRefType);
			variables[i++] = variable;
		}
		return variables;
	}
	
	private void sendCommand(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes command, String... parameters) {
		org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage message = new org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage(command, parameters);
		communicationHelper.sendEvent(message, output);
	}
	
	private org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage sendCommandAndRead(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes command, String... parameters) {
		org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage message = new org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage(command, parameters);
		return communicationHelper.sendAndReceive(message, output, reader);
	}
	
}
