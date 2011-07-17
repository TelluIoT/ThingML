/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml;

public interface IThingmlHoverTextProvider {
	
	public String getHoverText(org.eclipse.emf.ecore.EObject object);
	public String getHoverText(org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EObject referencedObject);
}
