/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlResourceFactoryDelegator implements org.eclipse.emf.ecore.resource.Resource.Factory {
	
	protected java.util.Map<String, org.eclipse.emf.ecore.resource.Resource.Factory> factories = null;
	
	public ThingmlResourceFactoryDelegator() {
		init();
	}
	
	protected void init() {
		if (factories == null) {
			factories = new java.util.LinkedHashMap<String, org.eclipse.emf.ecore.resource.Resource.Factory>();
		}
		if (factories.get("") == null) {
			factories.put("", new org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory());
		}
	}
	
	public java.util.Map<String, org.eclipse.emf.ecore.resource.Resource.Factory> getResourceFactoriesMap() {
		return factories;
	}
	
	public org.eclipse.emf.ecore.resource.Resource.Factory getFactoryForURI(org.eclipse.emf.common.util.URI uri) {
		org.eclipse.emf.common.util.URI trimmedURI = uri.trimFileExtension();
		String secondaryFileExtension = trimmedURI.fileExtension();
		org.eclipse.emf.ecore.resource.Resource.Factory factory = factories.get(secondaryFileExtension);
		if (factory == null) {
			factory = factories.get("");
		}
		return factory;
	}
	
	public org.eclipse.emf.ecore.resource.Resource createResource(org.eclipse.emf.common.util.URI uri) {
		return getFactoryForURI(uri).createResource(uri);
	}
	
}
