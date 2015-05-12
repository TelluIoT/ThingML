/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.util;

/**
 * Class ThingmlTextResourceUtil can be used to perform common tasks on text
 * resources, such as loading and saving resources, as well as, checking them for
 * errors. This class is deprecated and has been replaced by
 * org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.
 */
public class ThingmlTextResourceUtil {
	
	/**
	 * Use org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource()
	 * instead.
	 */
	@Deprecated	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(org.eclipse.core.resources.IFile file) {
		return new org.sintef.thingml.resource.thingml.util.ThingmlEclipseProxy().getResource(file);
	}
	
	/**
	 * Use org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource()
	 * instead.
	 */
	@Deprecated	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(java.io.File file, java.util.Map<?,?> options) {
		return org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource(file, options);
	}
	
	/**
	 * Use org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource()
	 * instead.
	 */
	@Deprecated	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(org.eclipse.emf.common.util.URI uri) {
		return org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource(uri);
	}
	
	/**
	 * Use org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource()
	 * instead.
	 */
	@Deprecated	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(org.eclipse.emf.common.util.URI uri, java.util.Map<?,?> options) {
		return org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource(uri, options);
	}
	
}
