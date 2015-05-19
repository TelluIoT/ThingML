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

import org.sintef.thingml.Handler;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.ReceiveMessage;

public class SimpleStreamReferenceParamRefReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.SimpleStreamReference, org.sintef.thingml.Parameter> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.SimpleStreamReference, org.sintef.thingml.Parameter> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.SimpleStreamReference, org.sintef.thingml.Parameter>();
	
	public void resolve(String identifier, org.sintef.thingml.SimpleStreamReference container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.Parameter> result) {
		//delegate.resolve(identifier, container, reference, position, resolveFuzzy, result);

		// get the transition object
		ReceiveMessage rm = container.getStreamRef().getSource();
		if (rm != null) {
			Message m = rm.getMessage();
			for (Parameter p : m.getParameters()) {
				if (resolveFuzzy) {
					if (p.getName().startsWith(identifier))
						result.addMapping(p.getName(), p);
				} else {
					if (p.getName().equals(identifier))
						result.addMapping(p.getName(), p);					
				} 
			}
		}
		if (!result.wasResolved())
			result.setErrorMessage("Cannot resolve receive message(event) parameter: " + identifier);
	}
	
	public String deResolve(org.sintef.thingml.Parameter element, org.sintef.thingml.SimpleStreamReference container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
