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

/**
 * A ReferenceCache can be used to improve the performance of the reference
 * resolving. This default implementation is initialized by traversing the content
 * of the current resource. During this traversal, two maps are created. One (the
 * classToObject map) can be used to retrieve all objects of a given type. The
 * other one (the nameToObjects map) can be used to retrieve all objects for a
 * given name.
 */
public class ThingmlReferenceCache extends org.eclipse.emf.common.notify.impl.AdapterImpl implements org.sintef.thingml.resource.thingml.IThingmlReferenceCache {
	
	private java.util.Map<org.eclipse.emf.ecore.EClass, java.util.Set<org.eclipse.emf.ecore.EObject>> classToObjectsMap = new java.util.LinkedHashMap<org.eclipse.emf.ecore.EClass, java.util.Set<org.eclipse.emf.ecore.EObject>>();
	private java.util.Map<String, java.util.Set<org.eclipse.emf.ecore.EObject>> nameToObjectsMap  = new java.util.LinkedHashMap<String, java.util.Set<org.eclipse.emf.ecore.EObject>>();
	private boolean isInitialized;
	private org.sintef.thingml.resource.thingml.IThingmlNameProvider nameProvider;
	
	public ThingmlReferenceCache(org.sintef.thingml.resource.thingml.IThingmlNameProvider nameProvider) {
		super();
		this.nameProvider = nameProvider;
	}
	
	public java.util.Set<org.eclipse.emf.ecore.EObject> getObjects(org.eclipse.emf.ecore.EClass type) {
		return classToObjectsMap.get(type);
	}
	
	public void initialize(org.eclipse.emf.ecore.EObject root) {
		if (isInitialized) {
			return;
		}
		put(root);
		java.util.Iterator<org.eclipse.emf.ecore.EObject> it = root.eAllContents();
		while (it.hasNext()) {
			put(it.next());
		}
		isInitialized = true;
	}
	
	public java.util.Map<String, java.util.Set<org.eclipse.emf.ecore.EObject>> getNameToObjectsMap() {
		return nameToObjectsMap;
	}
	
	private void put(org.eclipse.emf.ecore.EObject object) {
		org.eclipse.emf.ecore.EClass eClass = object.eClass();
		put(classToObjectsMap, eClass, object);
		java.util.List<String> names = nameProvider.getNames(object);
		for (String name : names) {
			put(nameToObjectsMap, name, object);
		}
	}
	
	private <T> void put(java.util.Map<T, java.util.Set<org.eclipse.emf.ecore.EObject>> map, T key, org.eclipse.emf.ecore.EObject object) {
		if (!map.containsKey(key)) {
			map.put(key, new java.util.LinkedHashSet<org.eclipse.emf.ecore.EObject>());
		}
		map.get(key).add(object);
	}
	
	public void clear() {
		classToObjectsMap.clear();
		nameToObjectsMap.clear();
		isInitialized = false;
	}
	
}
