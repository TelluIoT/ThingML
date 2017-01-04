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

import org.sintef.thingml.Message;
import org.sintef.thingml.MessageParameter;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.ReceiveMessage;
import org.sintef.thingml.Reference;
import org.sintef.thingml.ReferencedElmt;
import org.sintef.thingml.SimpleSource;
import org.sintef.thingml.SourceComposition;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class ParamReferenceParameterRefReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.ParamReference, org.sintef.thingml.Parameter> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.ParamReference, org.sintef.thingml.Parameter> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.ParamReference, org.sintef.thingml.Parameter>();
	
	public void resolve(String identifier, org.sintef.thingml.ParamReference container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.Parameter> result) {
		Reference parent = ThingMLHelpers.findContainer(container,Reference.class);
		ReferencedElmt elmt = parent.getReference();

		Message message = null;
		if(elmt instanceof ReceiveMessage) {
			message = ((ReceiveMessage) elmt).getMessage();
		} else if(elmt instanceof MessageParameter) {
			message = ((MessageParameter)elmt).getMsgRef();
		} else if(elmt instanceof Parameter) {
			Parameter parameter = (Parameter) elmt;
			/*if(resolveFuzzy && parameter.getName().startsWith(identifier)) {
				result.addMapping(parameter.getName(),parameter);
			} else if(!resolveFuzzy && parameter.getName().equals(identifier)) {
				result.addMapping(parameter.getName(),parameter);
			}*/
		} else if(elmt instanceof SimpleSource) {
			message = ((SimpleSource)elmt).getMessage().getMessage();
		} else if (elmt instanceof SourceComposition) {
			SourceComposition sc = (SourceComposition) elmt;
			message = sc.getResultMessage();			
		} else if(elmt instanceof Message) {
			message = (Message) elmt;
		} else if (elmt instanceof Reference) {
			Reference r = (Reference) elmt;
			if (r.getParameter() instanceof MessageParameter) {
				message = ((MessageParameter)r.getParameter()).getMsgRef();
			}				
		}
		else {
			result.setErrorMessage("The reference is incorrect or a new referenced element has been added (" + elmt.getClass().getName() + "). " +
					"May be you should update the resolver (" + this.getClass().getName() + ").");
		}

		if(message != null) {
			for(Parameter parameter : message.getParameters()) {
				if(resolveFuzzy && parameter.getName().startsWith(identifier)) {
					result.addMapping(parameter.getName(),parameter);
				} else if(!resolveFuzzy && parameter.getName().equals(identifier)) {
					result.addMapping(parameter.getName(),parameter);
				}
			}

			if(!result.wasResolved()) {
				result.setErrorMessage("The parameter " + identifier + " cannot be resolved for message " + message.getName());
			}
		}

	}
	
	public String deResolve(org.sintef.thingml.Parameter element, org.sintef.thingml.ParamReference container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
