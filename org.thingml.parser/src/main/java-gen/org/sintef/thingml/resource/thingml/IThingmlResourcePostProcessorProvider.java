/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml;

/**
 * Implementors of this interface can provide a post-processor for text resources.
 */
public interface IThingmlResourcePostProcessorProvider {
	
	/**
	 * Returns the processor that shall be called after text resource are successfully
	 * parsed.
	 */
	public org.sintef.thingml.resource.thingml.IThingmlResourcePostProcessor getResourcePostProcessor();
	
}
