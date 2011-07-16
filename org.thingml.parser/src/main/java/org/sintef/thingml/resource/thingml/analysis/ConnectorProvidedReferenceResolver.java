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
package org.sintef.thingml.resource.thingml.analysis;

import java.util.ArrayList;

import org.sintef.thingml.ProvidedPort;
import org.sintef.thingml.Thing;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class ConnectorProvidedReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.Connector, org.sintef.thingml.ProvidedPort> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.Connector, org.sintef.thingml.ProvidedPort> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.Connector, org.sintef.thingml.ProvidedPort>();
	
	public void resolve(String identifier, org.sintef.thingml.Connector container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.ProvidedPort> result) {
		Thing thing = container.getServer().getType();
		ArrayList<ProvidedPort> ts = ThingMLHelpers.findProvidedPort(thing, identifier, resolveFuzzy);
		for (ProvidedPort t : ts) result.addMapping(t.getName(), t);
		if(!result.wasResolved()) result.setErrorMessage("Cannot resolve port " + identifier);
	}
	
	public String deResolve(org.sintef.thingml.ProvidedPort element, org.sintef.thingml.Connector container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
