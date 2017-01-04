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
/**
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
package org.sintef.thingml.resource.thingml.analysis;

import java.util.ArrayList;

import org.sintef.thingml.Thing;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class ThingIncludesReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.Thing, org.sintef.thingml.Thing> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.Thing, org.sintef.thingml.Thing> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.Thing, org.sintef.thingml.Thing>();
	
	public void resolve(String identifier, org.sintef.thingml.Thing container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.Thing> result) {
		ThingMLModel root = ThingMLHelpers.findContainingModel(container);
		ArrayList<Thing> ts = ThingMLHelpers.findThing(root, identifier, resolveFuzzy);
		for (Thing t : ts) result.addMapping(t.getName(), t);
		if(!result.wasResolved()) result.setErrorMessage("Cannot resolve thing " + identifier);
	}
	
	public String deResolve(org.sintef.thingml.Thing element, org.sintef.thingml.Thing container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
