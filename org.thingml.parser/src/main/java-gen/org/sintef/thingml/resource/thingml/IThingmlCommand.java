/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml;

/**
 * A simple interface for commands that can be executed and that return
 * information about the success of their execution.
 */
public interface IThingmlCommand<ContextType> {
	
	public boolean execute(ContextType context);
}
