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
 * The DebuggerListener receives commands from the Eclipse Debug framework and
 * sends these commands to a debuggable process (e.g., an interpreter or generated
 * code).
 */
public class ThingmlDebuggerListener<ResultType, ContextType> implements Runnable {
	
	private static final Class<?>[] PRIMITIVE_TYPES = new Class<?>[] {
		String.class,
		Integer.class, int.class,
		Long.class, long.class,
		Boolean.class, boolean.class,
		Float.class, float.class,
		Double.class, double.class,
		Byte.class, byte.class,
		Short.class, short.class,
		Character.class, char.class,
	};
	
	private final static class ArrayPartition {
		
		private final Object array;
		private final int startIndex;
		private final int endIndex;
		
		public ArrayPartition(Object array, int startIndex, int endIndex) {
			super();
			this.array = array;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}
		
		public int getStartIndex() {
			return startIndex;
		}
		
		public int getEndIndex() {
			return endIndex;
		}
		
		public Object getArray() {
			return array;
		}
		
	}
	
	private boolean stop = false;
	private org.sintef.thingml.resource.thingml.debug.AbstractThingmlDebuggable debuggable;
	/**
	 * The last object id that was used.
	 */
	private long id = 0;
	/**
	 * This map maps object ids to pairs of object names and object values.
	 */
	private java.util.Map<Long, org.sintef.thingml.resource.thingml.util.ThingmlPair<String, Object>> objectMap = new java.util.LinkedHashMap<Long, org.sintef.thingml.resource.thingml.util.ThingmlPair<String, Object>>();
	
	private org.sintef.thingml.resource.thingml.debug.ThingmlDebugCommunicationHelper communicationHelper = new org.sintef.thingml.resource.thingml.debug.ThingmlDebugCommunicationHelper();
	
	private int requestPort;
	
	public ThingmlDebuggerListener(int requestPort) {
		super();
		this.requestPort = requestPort;
	}
	
	public void run() {
		try {
			runDebugger();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
	private void runDebugger() throws java.io.IOException {
		java.net.ServerSocket server = new java.net.ServerSocket(requestPort);
		java.net.Socket accept = server.accept();
		java.io.InputStream inputStream = accept.getInputStream();
		java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream));
		java.io.PrintStream output = new java.io.PrintStream(accept.getOutputStream());
		
		org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage command;
		while (!stop) {
			command = communicationHelper.receive(reader);
			if (command == null) {
				break;
			}
			if (command.hasType(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.EXIT)) {
				debuggable.terminate();
				stop = true;
			} else if (command.hasType(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.RESUME)) {
				debuggable.resume();
			} else if (command.hasType(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.STEP_OVER)) {
				debuggable.stepOver();
			} else if (command.hasType(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.STEP_INTO)) {
				debuggable.stepInto();
			} else if (command.hasType(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.STEP_RETURN)) {
				debuggable.stepReturn();
			} else if (command.hasType(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.ADD_LINE_BREAKPOINT)) {
				String location = command.getArgument(0);
				int line = Integer.parseInt(command.getArgument(1));
				debuggable.addLineBreakpoint(location, line);
			} else if (command.hasType(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.REMOVE_LINE_BREAKPOINT)) {
				String location = command.getArgument(0);
				int line = Integer.parseInt(command.getArgument(1));
				debuggable.removeLineBreakpoint(location, line);
			} else if (command.hasType(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.GET_STACK)) {
				final String[] stack = debuggable.getStack();
				String controlStack = org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.encode('#', stack);
				org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage message = new org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.GET_STACK_RESPONSE, new String[] {controlStack});
				communicationHelper.sendEvent(message, output);
			} else if (command.hasType(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.GET_FRAME_VARIABLES)) {
				String stackFrame = command.getArgument(0);
				java.util.Map<String, Object> frameVariables = debuggable.getFrameVariables(stackFrame);
				
				java.util.List<String> topVariableIDs = new java.util.ArrayList<String>();
				for (String name : frameVariables.keySet()) {
					Object value = frameVariables.get(name);
					long id = getObjectID(name, value);
					topVariableIDs.add(Long.toString(id));
				}
				org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage message = new org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.GET_FRAME_VARIABLES_RESPONSE, topVariableIDs);
				communicationHelper.sendEvent(message, output);
			} else if (command.hasType(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.GET_VARIABLES)) {
				String[] arguments = command.getArguments();
				Long[] requestedIDs = new Long[arguments.length];
				int i = 0;
				for (String argument : arguments) {
					requestedIDs[i++] = Long.parseLong(argument);
				}
				// create variable strings
				String[] varStrings = new String[arguments.length];
				i = 0;
				for (Long requestedID : requestedIDs) {
					Object next = objectMap.get(requestedID).getRight();
					String varString = convertToString(requestedID, next);
					varStrings[i++] = varString;
				}
				org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage message = new org.sintef.thingml.resource.thingml.debug.ThingmlDebugMessage(org.sintef.thingml.resource.thingml.debug.EThingmlDebugMessageTypes.GET_VARIABLES_RESPONSE, varStrings);
				communicationHelper.sendEvent(message, output);
			} else {
				System.out.println("ERROR: Unrecognized command (" + command + ")!");
				output.append("Unrecognized command!");
			}
		}
		// closing server
		server.close();
	}
	
	private String convertToString(long id, Object object) {
		String name = objectMap.get(id).getLeft();
		
		java.util.Map<String, Object> properties = new java.util.LinkedHashMap<String, Object>();
		properties.put("!name", name);
		properties.put("!id", Long.toString(id));
		String valueString = object == null ? "null" : object.toString();
		if (object != null) {
			if (object instanceof org.eclipse.emf.ecore.EObject) {
				org.eclipse.emf.ecore.EObject eObject = (org.eclipse.emf.ecore.EObject) object;
				org.eclipse.emf.ecore.EClass eClass = eObject.eClass();
				String eClassName = eClass.getName();
				valueString = eClassName + " (id=" + id + ")";
				properties.put("!type", eClassName);
				
				java.util.List<org.eclipse.emf.ecore.EStructuralFeature> features = eClass.getEAllStructuralFeatures();
				for (org.eclipse.emf.ecore.EStructuralFeature feature : features) {
					Object value = eObject.eGet(feature);
					String featureName = feature.getName();
					long valueID = getObjectID(featureName, value);
					properties.put(featureName, Long.toString(valueID));
				}
			} else if (object instanceof ArrayPartition) {
				ArrayPartition partition = (ArrayPartition) object;
				valueString = "";
				// if there is only a single partition, the elements of the array are directly
				// used a children
				for (int i = partition.getStartIndex(); i < partition.getEndIndex(); i++) {
					Object array = partition.getArray();
					Object objectAtIndex = java.lang.reflect.Array.get(array, i);
					String fieldName = "[" + i + "]";
					long valueID = getObjectID(fieldName, objectAtIndex);
					properties.put(fieldName, Long.toString(valueID));
				}
			} else {
				Class<? extends Object> javaClass = object.getClass();
				valueString = javaClass.getSimpleName() + " (id=" + id + ")";
				if (!isPrimitiveTypeClass(javaClass)) {
					addFields(object, properties, javaClass);
				} else {
					valueString = object.toString();
				}
				if (javaClass.isArray()) {
					int length = java.lang.reflect.Array.getLength(object);
					int partitions = getPartitionCount(length);
					Class<?> componentType = javaClass.getComponentType();
					valueString = componentType.getName() + "[" + length + "] (id=" + id + ")";
					if (partitions == 1) {
						// if there is only a single partition, the elements of the array are directly
						// used a children
						for (int i = 0; i < length; i++) {
							Object objectAtIndex = java.lang.reflect.Array.get(object, i);
							String fieldName = "[" + i + "]";
							long valueID = getObjectID(fieldName, objectAtIndex);
							properties.put(fieldName, Long.toString(valueID));
						}
					} else {
						// if there are multiple partitions, we introduce artificial objects that
						// represent partitions of the array
						for (int i = 0; i < partitions; i++) {
							int startIndex = i * 100;
							int endIndex = Math.min((i + 1) * 100, length);
							String fieldName = "[" + startIndex + ".." + (endIndex - 1) + "]";
							ArrayPartition newPartition = new ArrayPartition(object, startIndex, endIndex);
							long valueID = getObjectID(fieldName, newPartition);
							properties.put(fieldName, Long.toString(valueID));
						}
					}
				}
			}
		}
		properties.put("!valueString", valueString);
		
		return org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.convertToString(properties);
	}
	
	private int getPartitionCount(int arraySize) {
		int partitionSize = 100;
		int numPartitions = arraySize / partitionSize;
		int remainder = arraySize % partitionSize;
		if (remainder > 0) {
			numPartitions++;
		}
		return numPartitions;
	}
	
	private void addFields(Object object, java.util.Map<String, Object> properties, Class<?> javaClass) {
		java.lang.reflect.Field[] fields = javaClass.getDeclaredFields();
		for (java.lang.reflect.Field field : fields) {
			// here we should check the settings of the debug view
			if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			try {
				field.setAccessible(true);
				Object value = field.get(object);
				String fieldName = field.getName();
				long valueID = getObjectID(fieldName, value);
				properties.put(fieldName, Long.toString(valueID));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		Class<?> superclass = javaClass.getSuperclass();
		if (superclass != null) {
			addFields(object, properties, superclass);
		}
	}
	private boolean isPrimitiveTypeClass(Class<?> javaClass) {
		for (Class<?> clazz : PRIMITIVE_TYPES) {
			if (clazz.getName().equals(javaClass.getName())) {
				return true;
			}
		}
		return false;
	}
	
	private long getObjectID(String name, Object value) {
		org.sintef.thingml.resource.thingml.util.ThingmlPair<String, Object> pair = new org.sintef.thingml.resource.thingml.util.ThingmlPair<String, Object>(name, value);
		if (objectMap.containsValue(pair)) {
			for (Long nextID : objectMap.keySet()) {
				Object next = objectMap.get(nextID);
				if (pair.equals(next)) {
					return nextID;
				}
			}
			// This should not happen, because objectMap.containsValue() was true. Maybe there
			// is a fault equals() method?
			assert false;
			return -1;
		} else {
			long usedID = id;
			objectMap.put(usedID, pair);
			id++;
			return usedID;
		}
	}
	
	public org.sintef.thingml.resource.thingml.debug.AbstractThingmlDebuggable getDebuggable() {
		return debuggable;
	}
	
	public void setDebuggable(org.sintef.thingml.resource.thingml.debug.AbstractThingmlDebuggable debuggable) {
		this.debuggable = debuggable;
	}
	
}
