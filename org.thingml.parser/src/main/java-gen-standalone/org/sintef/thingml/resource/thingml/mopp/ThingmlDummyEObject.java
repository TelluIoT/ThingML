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
package org.sintef.thingml.resource.thingml.mopp;

/**
 * The DummyEObject is used to build a stack of dummy objects when descending by
 * tail recursion into left recursive rules. They cache the setting information
 * for initializing concrete EObject instances.
 * When the tail descent is finished this stack is reduced in reverse order. The
 * EObjects are created using the setting informations and a containment hierarchy
 * is build using the left recursive EStructuralFeature.
 */
public class ThingmlDummyEObject extends org.eclipse.emf.ecore.impl.EObjectImpl  {
	
	private java.util.Map<org.eclipse.emf.ecore.EStructuralFeature, Object> keyValueMap;
	private String recurseFeatureName;
	private org.eclipse.emf.ecore.EClass type;
	
	public ThingmlDummyEObject(org.eclipse.emf.ecore.EClass type, String recurseFeatureName) {
		this.recurseFeatureName = recurseFeatureName;
		this.type = type;
		keyValueMap = new java.util.LinkedHashMap<org.eclipse.emf.ecore.EStructuralFeature, Object>();
	}
	
	public org.eclipse.emf.ecore.EObject applyTo(org.eclipse.emf.ecore.EObject currentTarget) {
		org.eclipse.emf.ecore.EStructuralFeature recurseFeature = currentTarget.eClass().getEStructuralFeature(this.recurseFeatureName);
		org.eclipse.emf.ecore.EObject newObject = currentTarget.eClass().getEPackage().getEFactoryInstance().create(type);
		for (org.eclipse.emf.ecore.EStructuralFeature f : keyValueMap.keySet()) {
			org.eclipse.emf.ecore.EStructuralFeature structuralFeature = newObject.eClass().getEStructuralFeature(f.getName());
			newObject.eSet(structuralFeature, keyValueMap.get(f));
		}
		
		newObject.eSet(recurseFeature, currentTarget);
		return newObject;
	}
	
	public Object getValueByName(String name) {
		for (org.eclipse.emf.ecore.EStructuralFeature f : this.keyValueMap.keySet()) {
			if (f.getName().equals(name)) return this.keyValueMap.get(f);
		}
		return null;
	}
	
	/**
	 * proxy method
	 */
	public org.eclipse.emf.ecore.EClass eClass() {
		return type;
	}
	
	public void eSet(org.eclipse.emf.ecore.EStructuralFeature structuralFeature, Object a0) {
		this.keyValueMap.put(structuralFeature, a0);
	}
	
	public String toString() {
		String keyValuePairs = recurseFeatureName + ": ";
		for (org.eclipse.emf.ecore.EStructuralFeature f : keyValueMap.keySet()) {
			keyValuePairs += f.getName() + " = " + keyValueMap.get(f) + "\n";
		}
		return keyValuePairs;
	}
}
