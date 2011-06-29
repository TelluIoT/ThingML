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
package org.sintef.thingml.resource.thingml.util;

/**
 * A helper class that is able to create minimal model instances for Ecore models.
 */
public class ThingmlMinimalModelHelper {
	
	private final static org.sintef.thingml.resource.thingml.util.ThingmlEClassUtil eClassUtil = new org.sintef.thingml.resource.thingml.util.ThingmlEClassUtil();
	
	public org.eclipse.emf.ecore.EObject getMinimalModel(org.eclipse.emf.ecore.EClass eClass, java.util.Collection<org.eclipse.emf.ecore.EClass> allAvailableClasses) {
		return getMinimalModel(eClass, allAvailableClasses.toArray(new org.eclipse.emf.ecore.EClass[allAvailableClasses.size()]), null);
	}
	
	public org.eclipse.emf.ecore.EObject getMinimalModel(org.eclipse.emf.ecore.EClass eClass, org.eclipse.emf.ecore.EClass[] allAvailableClasses) {
		return getMinimalModel(eClass, allAvailableClasses, null);
	}
	
	public org.eclipse.emf.ecore.EObject getMinimalModel(org.eclipse.emf.ecore.EClass eClass, org.eclipse.emf.ecore.EClass[] allAvailableClasses, String name) {
		if (!contains(allAvailableClasses, eClass)) {
			return null;
		}
		org.eclipse.emf.ecore.EPackage ePackage = eClass.getEPackage();
		if (ePackage == null) {
			return null;
		}
		org.eclipse.emf.ecore.EObject root = ePackage.getEFactoryInstance().create(eClass);
		java.util.List<org.eclipse.emf.ecore.EStructuralFeature> features = eClass.getEAllStructuralFeatures();
		for (org.eclipse.emf.ecore.EStructuralFeature feature : features) {
			if (feature instanceof org.eclipse.emf.ecore.EReference) {
				org.eclipse.emf.ecore.EReference reference = (org.eclipse.emf.ecore.EReference) feature;
				if (reference.isUnsettable()) {
					continue;
				}
				if (!reference.isChangeable()) {
					continue;
				}
				
				org.eclipse.emf.ecore.EClassifier type = reference.getEType();
				if (type instanceof org.eclipse.emf.ecore.EClass) {
					org.eclipse.emf.ecore.EClass typeClass = (org.eclipse.emf.ecore.EClass) type;
					if (eClassUtil.isNotConcrete(typeClass)) {
						// find subclasses
						java.util.List<org.eclipse.emf.ecore.EClass> subClasses = eClassUtil.getSubClasses(typeClass, allAvailableClasses);
						if (subClasses.size() == 0) {
							continue;
						} else {
							// pick the first subclass
							typeClass = subClasses.get(0);
						}
					}
					int lowerBound = reference.getLowerBound();
					for (int i = 0; i < lowerBound; i++) {
						org.eclipse.emf.ecore.EObject subModel = null;
						if (reference.isContainment()) {
							org.eclipse.emf.ecore.EClass[] unusedClasses = getArraySubset(allAvailableClasses, eClass);
							subModel = getMinimalModel(typeClass, unusedClasses);
						}
						else {
							subModel = typeClass.getEPackage().getEFactoryInstance().create(typeClass);
							// set some proxy URI to make this object a proxy
							String initialValue = "#some" + org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.capitalize(typeClass.getName());
							org.eclipse.emf.common.util.URI proxyURI = org.eclipse.emf.common.util.URI.createURI(initialValue);
							((org.eclipse.emf.ecore.InternalEObject) subModel).eSetProxyURI(proxyURI);
						}
						if (subModel == null) {
							continue;
						}
						
						Object value = root.eGet(reference);
						if (value instanceof java.util.List<?>) {
							java.util.List<org.eclipse.emf.ecore.EObject> list = org.sintef.thingml.resource.thingml.util.ThingmlListUtil.castListUnchecked(value);
							list.add(subModel);
						} else {
							root.eSet(reference, subModel);
						}
					}
				}
			} else if (feature instanceof org.eclipse.emf.ecore.EAttribute) {
				org.eclipse.emf.ecore.EAttribute attribute = (org.eclipse.emf.ecore.EAttribute) feature;
				if ("EString".equals(attribute.getEType().getName())) {
					String initialValue;
					if (attribute.getName().equals("name") && name != null) {
						initialValue = name;
					}
					else {
						initialValue = "some" + org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.capitalize(attribute.getName());
					}
					Object value = root.eGet(attribute);
					if (value instanceof java.util.List<?>) {
						java.util.List<String> list = org.sintef.thingml.resource.thingml.util.ThingmlListUtil.castListUnchecked(value);
						list.add(initialValue);
					} else {
						root.eSet(attribute, initialValue);
					}
				}
			}
		}
		return root;
	}
	
	private boolean contains(org.eclipse.emf.ecore.EClass[] allAvailableClasses, org.eclipse.emf.ecore.EClass eClass) {
		for (org.eclipse.emf.ecore.EClass nextClass : allAvailableClasses) {
			if (eClass == nextClass) {
				return true;
			}
		}
		return false;
	}
	
	private org.eclipse.emf.ecore.EClass[] getArraySubset(org.eclipse.emf.ecore.EClass[] allClasses, org.eclipse.emf.ecore.EClass eClassToRemove) {
		java.util.List<org.eclipse.emf.ecore.EClass> subset = new java.util.ArrayList<org.eclipse.emf.ecore.EClass>();
		for (org.eclipse.emf.ecore.EClass eClass : allClasses) {
			if (eClass != eClassToRemove) {
				subset.add(eClass);
			}
		}
		return subset.toArray(new org.eclipse.emf.ecore.EClass[subset.size()]);
	}
	
}
