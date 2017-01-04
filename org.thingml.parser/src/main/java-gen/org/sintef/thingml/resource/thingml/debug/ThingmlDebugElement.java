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

public abstract class ThingmlDebugElement extends org.eclipse.debug.core.model.DebugElement {
	
	/**
	 * Constructs a new debug element in the given target.
	 */
	public ThingmlDebugElement(org.eclipse.debug.core.model.IDebugTarget target) {
		super(target);
	}
	
	public String getModelIdentifier() {
		return org.sintef.thingml.resource.thingml.mopp.ThingmlPlugin.DEBUG_MODEL_ID;
	}
	
	/**
	 * Returns the breakpoint manager.
	 * 
	 * @return the breakpoint manager
	 */
	protected org.eclipse.debug.core.IBreakpointManager getBreakpointManager() {
		return org.eclipse.debug.core.DebugPlugin.getDefault().getBreakpointManager();
	}
	
}
