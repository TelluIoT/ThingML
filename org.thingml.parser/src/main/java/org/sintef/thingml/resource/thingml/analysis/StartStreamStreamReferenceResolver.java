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

public class StartStreamStreamReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.StartStream, org.sintef.thingml.Stream> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.StartStream, org.sintef.thingml.Stream> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.StartStream, org.sintef.thingml.Stream>();
	
	public void resolve(String identifier, org.sintef.thingml.StartStream container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.Stream> result) {
		delegate.resolve(identifier, container, reference, position, resolveFuzzy, result);
	}
	
	public String deResolve(org.sintef.thingml.Stream element, org.sintef.thingml.StartStream container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
