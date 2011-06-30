/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
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

import org.eclipse.emf.ecore.EObject;
import org.sintef.thingml.Event;
import org.sintef.thingml.Message;
import org.sintef.thingml.ReceiveMessage;
import org.sintef.thingml.Transition;
import org.sintef.thingml.constraints.ThingMLHelpers;


public class EventReferenceMsgRefReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.EventReference, org.sintef.thingml.ReceiveMessage> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.EventReference, org.sintef.thingml.ReceiveMessage> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.EventReference, org.sintef.thingml.ReceiveMessage>();
	
	public void resolve(String identifier, org.sintef.thingml.EventReference container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.ReceiveMessage> result) {
		// get the transition object
		
		ArrayList<Event> evts = ThingMLHelpers.findEvents(container, identifier, resolveFuzzy);
		
		for (Event rm : evts) {
			if (rm instanceof ReceiveMessage) {
				result.addMapping(rm.getName(), (ReceiveMessage)rm);
			}
		}		
		if (!result.wasResolved())
			result.setErrorMessage("Cannot resolve receive message (event): " + identifier);
	}
	
	public String deResolve(org.sintef.thingml.ReceiveMessage element, org.sintef.thingml.EventReference container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
