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

import org.sintef.thingml.CompositeState;
import org.sintef.thingml.Port;
import org.sintef.thingml.State;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class TransitionTargetReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.Transition, org.sintef.thingml.State> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.Transition, org.sintef.thingml.State> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.Transition, org.sintef.thingml.State>();
	
	public void resolve(java.lang.String identifier, org.sintef.thingml.Transition container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.State> result) {
		State s = ThingMLHelpers.findContainingState(container);
		for (State p : ThingMLHelpers.findValidTargetState(s, identifier, resolveFuzzy))
			result.addMapping(p.getName(), p);
		if (!result.wasResolved()) result.setErrorMessage("Cannot resolve target state " + identifier);
	}
	
	public java.lang.String deResolve(org.sintef.thingml.State element, org.sintef.thingml.Transition container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend on any option
	}
	
}
