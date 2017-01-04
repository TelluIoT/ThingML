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

public class ThingmlStackFrame extends org.sintef.thingml.resource.thingml.debug.ThingmlDebugElement implements org.eclipse.debug.core.model.IStackFrame {
	
	private org.sintef.thingml.resource.thingml.debug.ThingmlDebugTarget target;
	private String name;
	private int id;
	private String resourceURI;
	private int line;
	private int charStart;
	private int charEnd;
	
	public ThingmlStackFrame(org.sintef.thingml.resource.thingml.debug.ThingmlDebugTarget target, String data) {
		super(target);
		this.target = target;
		
		java.util.List<String> dataParts = org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.decode(data, ',');
		this.name = dataParts.get(0);
		this.id = Integer.parseInt(dataParts.get(1));
		this.resourceURI = dataParts.get(2);
		this.line = Integer.parseInt(dataParts.get(3));
		this.charStart = Integer.parseInt(dataParts.get(4));
		this.charEnd = Integer.parseInt(dataParts.get(5));
	}
	
	private org.sintef.thingml.resource.thingml.debug.ThingmlDebugTarget getTarget() {
		return target;
	}
	
	public int getLineNumber() throws org.eclipse.debug.core.DebugException {
		return this.line;
	}
	
	public int getCharStart() throws org.eclipse.debug.core.DebugException {
		return charStart;
	}
	
	public int getCharEnd() throws org.eclipse.debug.core.DebugException {
		return charEnd;
	}
	
	public String getName() throws org.eclipse.debug.core.DebugException {
		return this.name;
	}
	
	public org.eclipse.debug.core.model.IRegisterGroup[] getRegisterGroups() throws org.eclipse.debug.core.DebugException {
		return new org.eclipse.debug.core.model.IRegisterGroup[0];
	}
	
	public org.eclipse.debug.core.model.IThread getThread() {
		return target.getThread();
	}
	
	public org.eclipse.debug.core.model.IVariable[] getVariables() throws org.eclipse.debug.core.DebugException {
		// get root (top level) variables
		org.eclipse.debug.core.model.IVariable[] variables = getTarget().getDebugProxy().getStackVariables(Integer.toString(id));
		return variables;
	}
	
	public boolean hasRegisterGroups() throws org.eclipse.debug.core.DebugException {
		return false;
	}
	
	public boolean hasVariables() throws org.eclipse.debug.core.DebugException {
		return false;
	}
	
	public boolean canStepInto() {
		return getThread().canStepInto();
	}
	
	public boolean canStepOver() {
		return getThread().canStepOver();
	}
	
	public boolean canStepReturn() {
		return getThread().canStepReturn();
	}
	
	public boolean isStepping() {
		return getThread().isStepping();
	}
	
	public void stepInto() throws org.eclipse.debug.core.DebugException {
		getThread().stepInto();
	}
	
	public void stepOver() throws org.eclipse.debug.core.DebugException {
		getThread().stepOver();
	}
	
	public void stepReturn() throws org.eclipse.debug.core.DebugException {
		getThread().stepReturn();
	}
	
	public boolean canResume() {
		return getThread().canResume();
	}
	
	public boolean canSuspend() {
		return getThread().canSuspend();
	}
	
	public boolean isSuspended() {
		return getThread().isSuspended();
	}
	
	public void resume() throws org.eclipse.debug.core.DebugException {
		getThread().resume();
	}
	
	public void suspend() throws org.eclipse.debug.core.DebugException {
		getThread().suspend();
	}
	
	public boolean canTerminate() {
		return getThread().canTerminate();
	}
	
	public boolean isTerminated() {
		return getThread().isTerminated();
	}
	
	public void terminate() throws org.eclipse.debug.core.DebugException {
		getThread().terminate();
	}
	
	public String getResourceURI() {
		return this.resourceURI;
	}
	
	@Override	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result		+ ((resourceURI == null) ? 0 : resourceURI.hashCode());
		return result;
	}
	
	@Override	
	public boolean equals(Object obj) {
		if (this == obj)		return true;
		if (obj == null)		return false;
		if (getClass() != obj.getClass())		return false;
		org.sintef.thingml.resource.thingml.debug.ThingmlStackFrame other = (org.sintef.thingml.resource.thingml.debug.ThingmlStackFrame) obj;
		if (name == null) {
			if (other.name != null)			return false;
		} else if (!name.equals(other.name))		return false;
		if (resourceURI == null) {
			if (other.resourceURI != null)			return false;
		} else if (!resourceURI.equals(other.resourceURI))		return false;
		return true;
	}
	
}
