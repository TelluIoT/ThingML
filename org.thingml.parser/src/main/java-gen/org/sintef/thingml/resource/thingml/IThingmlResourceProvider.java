/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml;

/**
 * Implementors of this interface provide an EMF resource.
 */
public interface IThingmlResourceProvider {
	
	/**
	 * Returns the resource.
	 */
	public org.sintef.thingml.resource.thingml.IThingmlTextResource getResource();
	
}
