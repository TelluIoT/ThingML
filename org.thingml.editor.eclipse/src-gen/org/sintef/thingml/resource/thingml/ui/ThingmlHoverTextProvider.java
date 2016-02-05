/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

public class ThingmlHoverTextProvider implements org.sintef.thingml.resource.thingml.IThingmlHoverTextProvider {
	
	private org.sintef.thingml.resource.thingml.ui.ThingmlDefaultHoverTextProvider defaultProvider = new org.sintef.thingml.resource.thingml.ui.ThingmlDefaultHoverTextProvider();
	
	public String getHoverText(org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EObject referencedObject) {
		// Set option overrideHoverTextProvider to false and customize this method to
		// obtain custom hover texts.
		return defaultProvider.getHoverText(referencedObject);
	}
	
	public String getHoverText(org.eclipse.emf.ecore.EObject object) {
		// Set option overrideHoverTextProvider to false and customize this method to
		// obtain custom hover texts.
		return defaultProvider.getHoverText(object);
	}
	
}
