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

import org.sintef.thingml.Expression;
import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.sintef.thingml.Type;
import org.sintef.thingml.constraints.ThingMLHelpers;


public class ReceiveMessageMessageReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.ReceiveMessage, Message> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.ReceiveMessage, Message> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.ReceiveMessage, Message>();
	
	public void resolve(java.lang.String identifier, org.sintef.thingml.ReceiveMessage container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<Message> result) {

		Port p = container.getPort();
		for (Message m : p.getReceives()) {
			//if (m.getName() == null) break; //TODO: REMOVE THIS
			if (m.getName().startsWith(identifier)) {
				if (resolveFuzzy) result.addMapping(m.getName(), m);
				else if (m.getName().equals(identifier))
					result.addMapping(m.getName(), m);
			}
		}
		if(!result.wasResolved()) result.setErrorMessage("Cannot resolve message " + identifier);
	}
	
	public java.lang.String deResolve(Message element, org.sintef.thingml.ReceiveMessage container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend on any option
	}
	
}
