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
package org.sintef.thingml.resource.thingml.util;

/**
 * A utility class to resolve proxy objects that allows to terminate resolving.
 */
public class ThingmlInterruptibleEcoreResolver {
	
	private boolean terminate = false;
	
	public void terminate() {
		terminate = true;
	}
	
	/**
	 * Visits all proxies in the resource set and tries to resolve them.
	 * 
	 * @param resourceSet the objects to visit.
	 */
	public void resolveAll(org.eclipse.emf.ecore.resource.ResourceSet resourceSet) {
		java.util.List<org.eclipse.emf.ecore.resource.Resource> resources = resourceSet.getResources();
		for (int i = 0; i < resources.size() && !terminate; i++) {
			resolveAll(resources.get(i));
		}
	}
	
	/**
	 * Visits all proxies in the resource and tries to resolve them.
	 * 
	 * @param resource the objects to visit.
	 */
	public void resolveAll(org.eclipse.emf.ecore.resource.Resource resource) {
		for (org.eclipse.emf.ecore.EObject eObject : resource.getContents()) {
			if (terminate) {
				return;
			}
			resolveAll(eObject);
		}
	}
	
	/**
	 * Visits all proxies referenced by the object and recursively any of its
	 * contained objects.
	 * 
	 * @param eObject the object to visit.
	 */
	public void resolveAll(org.eclipse.emf.ecore.EObject eObject) {
		eObject.eContainer();
		resolveCrossReferences(eObject);
		for (java.util.Iterator<org.eclipse.emf.ecore.EObject> i = eObject.eAllContents(); i.hasNext();) {
			if (terminate) {
				return;
			}
			org.eclipse.emf.ecore.EObject childEObject = i.next();
			resolveCrossReferences(childEObject);
		}
	}
	
	protected void resolveCrossReferences(org.eclipse.emf.ecore.EObject eObject) {
		for (java.util.Iterator<org.eclipse.emf.ecore.EObject> i = eObject.eCrossReferences().iterator(); i.hasNext(); i.next()) {
			// The loop resolves the cross references by visiting them.
			if (terminate) {
				return;
			}
		}
	}
	
	/**
	 * Searches for all unresolved proxy objects in the given resource.
	 * 
	 * @param resource
	 * 
	 * @return all proxy objects that are not resolvable
	 */
	public java.util.Set<org.eclipse.emf.ecore.EObject> findUnresolvedProxies(org.eclipse.emf.ecore.resource.Resource resource) {
		java.util.Set<org.eclipse.emf.ecore.EObject> unresolvedProxies = new java.util.LinkedHashSet<org.eclipse.emf.ecore.EObject>();
		
		for (java.util.Iterator<org.eclipse.emf.ecore.EObject> elementIt = org.eclipse.emf.ecore.util.EcoreUtil.getAllContents(resource, true); elementIt.hasNext(); ) {
			org.eclipse.emf.ecore.InternalEObject nextElement = (org.eclipse.emf.ecore.InternalEObject) elementIt.next();
			if (terminate) {
				return unresolvedProxies;
			}
			if (nextElement.eIsProxy()) {
				unresolvedProxies.add(nextElement);
			}
			for (org.eclipse.emf.ecore.EObject crElement : nextElement.eCrossReferences()) {
				if (terminate) {
					return unresolvedProxies;
				}
				crElement = org.eclipse.emf.ecore.util.EcoreUtil.resolve(crElement, resource);
				if (crElement.eIsProxy()) {
					unresolvedProxies.add(crElement);
				}
			}
		}
		return unresolvedProxies;
	}
	
	/**
	 * Searches for all unresolved proxy objects in the given resource set.
	 * 
	 * @param resourceSet
	 * 
	 * @return all proxy objects that are not resolvable
	 */
	public java.util.Set<org.eclipse.emf.ecore.EObject> findUnresolvedProxies(org.eclipse.emf.ecore.resource.ResourceSet resourceSet) {
		java.util.Set<org.eclipse.emf.ecore.EObject> unresolvedProxies = new java.util.LinkedHashSet<org.eclipse.emf.ecore.EObject>();
		
		for (org.eclipse.emf.ecore.resource.Resource resource : resourceSet.getResources()) {
			if (terminate) {
				return unresolvedProxies;
			}
			unresolvedProxies.addAll(findUnresolvedProxies(resource));
		}
		return unresolvedProxies;
	}
	
}
