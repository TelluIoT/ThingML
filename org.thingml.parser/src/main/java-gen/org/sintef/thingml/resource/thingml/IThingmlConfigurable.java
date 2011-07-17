/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml;

/**
 * Implementors of this interface can be configured by a map of options (or
 * parameters).
 */
public interface IThingmlConfigurable {
	
	/**
	 * Passes the options given by the map to the configurable object.
	 */
	public void setOptions(java.util.Map<?,?> options);
}
