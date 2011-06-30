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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.ThingMLModel;


public class ThingMLModelImportsReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.ThingMLModel, org.sintef.thingml.ThingMLModel> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.ThingMLModel, org.sintef.thingml.ThingMLModel> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.ThingMLModel, org.sintef.thingml.ThingMLModel>();
	
	public void resolve(java.lang.String identifier, org.sintef.thingml.ThingMLModel container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.ThingMLModel> result) {
		try {
			URI uri = URI.createURI(identifier).resolve(container.eResource().getURI());
			//Resource r = container.eResource().getResourceSet().getResource(uri, true);
			Resource r = new ResourceSetImpl().getResource(uri, true);
			r.load(null);
			ThingMLModel m = (ThingMLModel)r.getContents().get(0);
			result.addMapping(identifier, m);
		} catch (ClassCastException e) {
			result.setErrorMessage("The given URI contains a model with a wrong type");
		} catch (Exception e) {
			result.setErrorMessage("Unable to load model with uri: " + identifier);
		}
	}
	
	public java.lang.String deResolve(org.sintef.thingml.ThingMLModel element, org.sintef.thingml.ThingMLModel container, org.eclipse.emf.ecore.EReference reference) {
		return element.eResource().getURI().toString();
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend on any option
	}
	
}
