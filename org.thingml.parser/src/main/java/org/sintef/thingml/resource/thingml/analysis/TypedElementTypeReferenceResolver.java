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
package org.sintef.thingml.resource.thingml.analysis;

import java.util.ArrayList;

import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.Type;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class TypedElementTypeReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.TypedElement, org.sintef.thingml.Type> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.TypedElement, org.sintef.thingml.Type> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.TypedElement, org.sintef.thingml.Type>();
	
	public void resolve(String identifier, org.sintef.thingml.TypedElement container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.Type> result) {
		ThingMLModel root = ThingMLHelpers.findContainingModel(container);
		ArrayList<Type> ts = ThingMLHelpers.findSimpleType(root, identifier, resolveFuzzy);
		for (Type t : ts) {
			if (resolveFuzzy) result.addMapping(t.getName(), t);
			else if (t.getName().equals(identifier)) {
				result.addMapping(t.getName(), t);
			}
		}
		if(!result.wasResolved()) result.setErrorMessage("Cannot resolve type " + identifier);
	}
	
	public String deResolve(org.sintef.thingml.Type element, org.sintef.thingml.TypedElement container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
