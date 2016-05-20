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
package org.sintef.thingml.resource.thingml.analysis;

import org.sintef.thingml.Message;
import org.sintef.thingml.Thing;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class SourceCompositionResultMessageReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.SourceComposition, org.sintef.thingml.Message> {

	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.SourceComposition, org.sintef.thingml.Message> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.SourceComposition, org.sintef.thingml.Message>();

	public void resolve(String identifier, org.sintef.thingml.SourceComposition container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.Message> result) {

		
		/*if(resolveFuzzy && container.getName().startsWith(identifier)) {
			result.addMapping(container.getName(),container.getResultMessage());
		} else if(!resolveFuzzy && container.getName().equals(identifier)) {
			result.addMapping(container.getName(),container.getResultMessage());
		}*/			


		if (!result.wasResolved()) {
			Thing thing = ThingMLHelpers.findContainingThing(container);
			for(Message m : ThingMLHelpers.allMessages(thing)) {
				if(resolveFuzzy && m.getName().startsWith(identifier)) {
					result.addMapping(m.getName(),m);
				} else if(!resolveFuzzy && m.getName().equals(identifier)) {
					result.addMapping(m.getName(),m);
				}
			}
		}

		if (!result.wasResolved())
			result.setErrorMessage("Cannot resolve message name: " + identifier);
	}

	public String deResolve(org.sintef.thingml.Message element, org.sintef.thingml.SourceComposition container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}

	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}

}
