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
package org.sintef.thingml.resource.thingml;

public interface IThingmlReferenceCache {
	
	/**
	 * Returns all EObjects of the given type.
	 */
	public java.util.Set<org.eclipse.emf.ecore.EObject> getObjects(org.eclipse.emf.ecore.EClass type);
	
	/**
	 * Initializes the cache with the object tree that is rooted at <code>root</code>.
	 * The cache allows to retrieve of objects of a given type or a given name. If the
	 * cache was already initialized, no action is performed.
	 */
	public void initialize(org.eclipse.emf.ecore.EObject root);
	
	/**
	 * Returns the map from object names to objects that was created when the cache
	 * was initialized.
	 */
	public java.util.Map<String, java.util.Set<org.eclipse.emf.ecore.EObject>> getNameToObjectsMap();
	
	/**
	 * Clears the cache.
	 */
	public void clear();
	
}
