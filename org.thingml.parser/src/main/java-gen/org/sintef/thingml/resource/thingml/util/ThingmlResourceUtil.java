/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.util;

/**
 * Class ResourceUtil can be used to perform common tasks on resources, such as
 * resolving proxy object, saving resources, as well as, checking them for errors.
 */
public class ThingmlResourceUtil {
	
	/**
	 * Searches for all unresolved proxy objects in the given resource set.
	 * 
	 * @param resourceSet
	 * 
	 * @return all proxy objects that are not resolvable
	 */
	public static java.util.Set<org.eclipse.emf.ecore.EObject> findUnresolvedProxies(org.eclipse.emf.ecore.resource.ResourceSet resourceSet) {
		java.util.Set<org.eclipse.emf.ecore.EObject> unresolvedProxies = new java.util.LinkedHashSet<org.eclipse.emf.ecore.EObject>();
		
		for (org.eclipse.emf.ecore.resource.Resource resource : resourceSet.getResources()) {
			unresolvedProxies.addAll(findUnresolvedProxies(resource));
		}
		return unresolvedProxies;
	}
	
	/**
	 * Searches for all unresolved proxy objects in the given resource.
	 * 
	 * @param resource
	 * 
	 * @return all proxy objects that are not resolvable
	 */
	public static java.util.Set<org.eclipse.emf.ecore.EObject> findUnresolvedProxies(org.eclipse.emf.ecore.resource.Resource resource) {
		java.util.Set<org.eclipse.emf.ecore.EObject> unresolvedProxies = new java.util.LinkedHashSet<org.eclipse.emf.ecore.EObject>();
		
		for (java.util.Iterator<org.eclipse.emf.ecore.EObject> elementIt = org.eclipse.emf.ecore.util.EcoreUtil.getAllContents(resource, true); elementIt.hasNext(); ) {
			org.eclipse.emf.ecore.InternalEObject nextElement = (org.eclipse.emf.ecore.InternalEObject) elementIt.next();
			if (nextElement.eIsProxy()) {
				unresolvedProxies.add(nextElement);
			}
			for (org.eclipse.emf.ecore.EObject crElement : nextElement.eCrossReferences()) {
				crElement = org.eclipse.emf.ecore.util.EcoreUtil.resolve(crElement, resource);
				if (crElement.eIsProxy()) {
					unresolvedProxies.add(crElement);
				}
			}
		}
		return unresolvedProxies;
	}
	
	/**
	 * Tries to resolve all unresolved proxy objects in the given resource. If all
	 * proxies were resolved true is returned. If some could not be resolved, false is
	 * returned.
	 * 
	 * @param resource the resource containing the proxy object
	 * 
	 * @return true on success
	 */
	public static boolean resolveAll(org.eclipse.emf.ecore.resource.Resource resource) {
		org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(resource);
		if (findUnresolvedProxies(resource).size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(org.eclipse.core.resources.IFile file) {
		org.eclipse.emf.ecore.resource.ResourceSet rs = new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();
		org.eclipse.emf.ecore.resource.Resource csResource = rs.getResource(org.eclipse.emf.common.util.URI.createPlatformResourceURI(file.getFullPath().toString(),true), true);
		return (org.sintef.thingml.resource.thingml.mopp.ThingmlResource) csResource;
	}
	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(java.io.File file) {
		return getResource(file, null);
	}
	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(java.io.File file, java.util.Map<?,?> options) {
		return getResource(org.eclipse.emf.common.util.URI.createFileURI(file.getAbsolutePath()), options);
	}
	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(org.eclipse.emf.common.util.URI uri) {
		return getResource(uri, null);
	}
	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(org.eclipse.emf.common.util.URI uri, java.util.Map<?,?> options) {
		new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().registerResourceFactory();
		org.eclipse.emf.ecore.resource.ResourceSet rs = new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();
		if (options != null) {
			rs.getLoadOptions().putAll(options);
		}
		org.eclipse.emf.ecore.resource.Resource resource = rs.getResource(uri, true);
		return (org.sintef.thingml.resource.thingml.mopp.ThingmlResource) resource;
	}
	
	/**
	 * Returns the root element of the resource with the given URI.
	 */
	public static org.sintef.thingml.ThingMLModel getResourceContent(org.eclipse.emf.common.util.URI uri) {
		return getResourceContent(uri, null);
	}
	
	/**
	 * Returns the root element of the resource with the given URI.
	 */
	public static org.sintef.thingml.ThingMLModel getResourceContent(org.eclipse.emf.common.util.URI uri, java.util.Map<?,?> options) {
		org.eclipse.emf.ecore.resource.Resource resource = getResource(uri, options);
		if (resource == null) {
			return null;
		}
		java.util.List<org.eclipse.emf.ecore.EObject> contents = resource.getContents();
		if (contents == null || contents.isEmpty()) {
			return null;
		}
		org.eclipse.emf.ecore.EObject root = contents.get(0);
		return (org.sintef.thingml.ThingMLModel) root;
	}
	
	public static void saveResource(java.io.File file, org.eclipse.emf.ecore.resource.Resource resource) throws java.io.IOException {
		java.util.Map<?, ?> options = java.util.Collections.EMPTY_MAP;
		java.io.OutputStream outputStream = new java.io.FileOutputStream(file);
		resource.save(outputStream, options);
		outputStream.close();
	}
	
	public static boolean containsErrors(org.eclipse.emf.ecore.resource.Resource resource) {
		return !resource.getErrors().isEmpty();
	}
	
	public static boolean containsWarnings(org.eclipse.emf.ecore.resource.Resource resource) {
		return !resource.getWarnings().isEmpty();
	}
	
	public static boolean containsProblems(org.eclipse.emf.ecore.resource.Resource resource) {
		return containsErrors(resource) || containsWarnings(resource);
	}
	
}
