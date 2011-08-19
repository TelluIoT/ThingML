/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlParseResult implements org.sintef.thingml.resource.thingml.IThingmlParseResult {
	
	private org.eclipse.emf.ecore.EObject root;
	private java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlCommand<org.sintef.thingml.resource.thingml.IThingmlTextResource>> commands = new java.util.ArrayList<org.sintef.thingml.resource.thingml.IThingmlCommand<org.sintef.thingml.resource.thingml.IThingmlTextResource>>();
	
	public ThingmlParseResult() {
		super();
	}
	
	public void setRoot(org.eclipse.emf.ecore.EObject root) {
		this.root = root;
	}
	
	public org.eclipse.emf.ecore.EObject getRoot() {
		return root;
	}
	
	public java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlCommand<org.sintef.thingml.resource.thingml.IThingmlTextResource>> getPostParseCommands() {
		return commands;
	}
	
}
