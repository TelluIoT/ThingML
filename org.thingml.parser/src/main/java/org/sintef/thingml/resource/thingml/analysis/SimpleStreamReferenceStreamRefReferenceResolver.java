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

import java.util.ArrayList;

import org.sintef.thingml.Event;
import org.sintef.thingml.ReceiveMessage;
import org.sintef.thingml.SimpleStream;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class SimpleStreamReferenceStreamRefReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.SimpleStreamReference, org.sintef.thingml.SimpleStream> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.SimpleStreamReference, org.sintef.thingml.SimpleStream> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.SimpleStreamReference, org.sintef.thingml.SimpleStream>();
	
	public void resolve(String identifier, org.sintef.thingml.SimpleStreamReference container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.SimpleStream> result) {
		//delegate.resolve(identifier, container, reference, position, resolveFuzzy, result);
		
		ArrayList<Event> evts = ThingMLHelpers.findEvents(container, identifier, resolveFuzzy);
		
		for (Event rm : evts) {
			if (rm instanceof SimpleStream) {
				result.addMapping(rm.getName(), (SimpleStream)rm);
			}
		}		
		if (!result.wasResolved())
			result.setErrorMessage("Cannot resolve receive message (event): " + identifier);
	
	}
	
	public String deResolve(org.sintef.thingml.SimpleStream element, org.sintef.thingml.SimpleStreamReference container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
