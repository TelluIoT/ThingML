/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlResourceFactory implements org.eclipse.emf.ecore.resource.Resource.Factory {
	
	public ThingmlResourceFactory() {
		super();
	}
	
	public org.eclipse.emf.ecore.resource.Resource createResource(org.eclipse.emf.common.util.URI uri) {
		return new org.sintef.thingml.resource.thingml.mopp.ThingmlResource(uri);
	}
	
}
