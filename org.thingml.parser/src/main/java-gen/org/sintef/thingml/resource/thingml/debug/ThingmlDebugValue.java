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

public class ThingmlDebugValue extends org.sintef.thingml.resource.thingml.debug.ThingmlDebugElement implements org.eclipse.debug.core.model.IValue {
	
	private org.sintef.thingml.resource.thingml.debug.ThingmlDebugTarget debugTarget;
	private org.eclipse.debug.core.model.IVariable[] variables;
	private String referenceTypeName;
	private String valueString;
	private java.util.Map<String, Long> children;
	
	public ThingmlDebugValue(org.sintef.thingml.resource.thingml.debug.ThingmlDebugTarget target, String id, String valueString, String referenceTypeName, java.util.Map<String, Long> children) {
		super(target);
		this.debugTarget = target;
		this.valueString = valueString;
		this.referenceTypeName = referenceTypeName;
		this.children = children;
	}
	
	public String getReferenceTypeName() throws org.eclipse.debug.core.DebugException {
		return referenceTypeName;
	}
	
	public String getValueString() throws org.eclipse.debug.core.DebugException {
		return valueString;
	}
	
	public boolean isAllocated() throws org.eclipse.debug.core.DebugException {
		return true;
	}
	
	public org.eclipse.debug.core.model.IVariable[] getVariables() throws org.eclipse.debug.core.DebugException {
		if (variables == null) {
			// request variables from debug client
			java.util.Collection<Long> childIDs = children.values();
			String[] childIDStrings = new String[childIDs.size()];
			int i = 0;
			for (Long childID : childIDs) {
				childIDStrings[i++] = childID.toString();
			}
			org.eclipse.debug.core.model.IVariable[] response = debugTarget.getDebugProxy().getVariables(childIDStrings);
			variables = response;
		}
		return variables;
	}
	
	public boolean hasVariables() throws org.eclipse.debug.core.DebugException {
		return this.children.keySet().size() > 0;
	}
	
	public org.eclipse.debug.core.model.IVariable getChild(int index) {
		java.util.Set<String> keySet = this.children.keySet();
		java.util.Iterator<String> iterator = keySet.iterator();
		String keyAtIndex = iterator.next();
		for (int i = 0; i < index; i++) {
			keyAtIndex = iterator.next();
		}
		Long childID = this.children.get(keyAtIndex);
		org.eclipse.debug.core.model.IVariable[] response = debugTarget.getDebugProxy().getVariables(childID.toString());
		return response[0];
	}
	
	public int getVariableCount() {
		return this.children.keySet().size();
	}
	
}
