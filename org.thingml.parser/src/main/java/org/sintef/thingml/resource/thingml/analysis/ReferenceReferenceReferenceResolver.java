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

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;

import java.util.List;

public class ReferenceReferenceReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.Reference, org.sintef.thingml.ReferencedElmt> {

	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.Reference, org.sintef.thingml.ReferencedElmt> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.Reference, org.sintef.thingml.ReferencedElmt>();

	public void resolve(String identifier, org.sintef.thingml.Reference container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.ReferencedElmt> result) {
		Action parentAction = ThingMLHelpers.findContainer(container,Action.class);
		if(parentAction != null) {
			List<Variable> variables = ThingMLHelpers.allVisibleVariables(parentAction);
			for(Variable v : variables) {
				if (v.getName() != null) {
					if (resolveFuzzy && v.getName().startsWith(identifier)) {
						result.addMapping(v.getName(), v);
					} else if (!resolveFuzzy && v.getName().equals(identifier)) {
						result.addMapping(v.getName(), v);
					}
				}
			}
			if(result.wasResolved()) {
				return;
			}
		}

		ThingMLElement parent = ThingMLHelpers.findReferenceContainer(container);
		
		if(parent instanceof Handler) { //we reference an event in a handler
			Handler handler = (Handler) parent;
			for (Event event : handler.getEvent()) {
				if(event instanceof ReceiveMessage && event.getName() != null) {
					if (resolveFuzzy && event.getName().startsWith(identifier)) {
						result.addMapping(event.getName(), (ReceiveMessage)event);
					} else if (!resolveFuzzy && event.getName().equals(identifier)) {
						result.addMapping(event.getName(),(ReceiveMessage)event);
					}
				}
			}
			if (!result.wasResolved())
				result.setErrorMessage("Cannot resolve receive message (event): " + identifier);
		} else if(parent instanceof SourceComposition) {//we reference an event in one of the stream inputs
			SourceComposition sc = (SourceComposition) parent;
			if(resolveFuzzy && sc.getName().startsWith(identifier)) {
				result.addMapping(sc.getName(), sc.getResultMessage());
			} else if(!resolveFuzzy && sc.getName().equals(identifier)) {
				result.addMapping(sc.getName(), sc.getResultMessage());
			}
			//if(!result.wasResolved()) {
				for(Source source : ((SourceComposition) parent).getSources()) {
					ReceiveMessage receiveMessage = ((SimpleSource) source).getMessage();
					if(resolveFuzzy && source.getName().startsWith(identifier)) {
						result.addMapping(source.getName(),receiveMessage);
					} else if(!resolveFuzzy && source.getName().equals(identifier)) {
						result.addMapping(source.getName(),receiveMessage);
					}
				}
			//}				
			if(!result.wasResolved()) {
				Stream stream = ThingMLHelpers.findContainingStream(parent);
				result.setErrorMessage("Cannot resolve receive message " + identifier + " in the sources of " + stream.getName());
			}

		} else if(parent instanceof Stream) {//we reference an event in one of the stream inputs			
			Source source = ((Stream) parent).getInput(); 
			if(resolveFuzzy && source.getName().startsWith(identifier)) {
				result.addMapping(source.getName(),source);
			} else if(!resolveFuzzy && source.getName().equals(identifier)) {
				result.addMapping(source.getName(),source);
			}			

			if(!result.wasResolved()) {
				Stream stream = ThingMLHelpers.findContainingStream(parent);
				result.setErrorMessage("Cannot resolve receive message " + identifier + " in the sources of " + stream.getName());
			}
		} else if(parent instanceof Source) {//we reference an event in one of the stream inputs			
			Source source = (Source) parent; 
			if(resolveFuzzy && source.getName().startsWith(identifier)) {
				result.addMapping(source.getName(),source);
			} else if(!resolveFuzzy && source.getName().equals(identifier)) {
				result.addMapping(source.getName(),source);
			}			

			if(!result.wasResolved()) {
				Stream stream = ThingMLHelpers.findContainingStream(parent);
				result.setErrorMessage("Cannot resolve receive message " + identifier + " in the sources of " + stream.getName());
			}
		} else {
			if (parent != null)
				result.setErrorMessage("The reference has not a good parent (" + parent.getClass().getName() + ")! ");
			else 
				result.setErrorMessage("The reference has not a good parent (null)! ");
		}
	}

	public String deResolve(org.sintef.thingml.ReferencedElmt element, org.sintef.thingml.Reference container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}

	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}

}
