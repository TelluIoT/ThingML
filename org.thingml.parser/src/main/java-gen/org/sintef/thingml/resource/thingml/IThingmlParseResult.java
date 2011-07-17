/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml;

/**
 * An interface used to access the result of parsing a document.
 */
public interface IThingmlParseResult {
	
	/**
	 * Returns the root object of the document.
	 */
	public org.eclipse.emf.ecore.EObject getRoot();
	
	/**
	 * Returns a list of commands that must be executed after parsing the document.
	 */
	public java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlCommand<org.sintef.thingml.resource.thingml.IThingmlTextResource>> getPostParseCommands();
	
}
