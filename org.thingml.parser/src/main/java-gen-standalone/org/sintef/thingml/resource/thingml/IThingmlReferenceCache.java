/**
 * <copyright>
 * </copyright>
 *
 * 
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
