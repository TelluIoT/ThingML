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
	 * If the cache was already initialized, no action is performed.
	 */
	public void initialize(org.eclipse.emf.ecore.EObject root);
	
	/**
	 * Clears the cache.
	 */
	public void clear();
	
}
