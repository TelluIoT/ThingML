/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml;

public interface IThingmlBuilder {
	
	public boolean isBuildingNeeded(org.eclipse.emf.common.util.URI uri);
	
	public org.eclipse.core.runtime.IStatus build(org.sintef.thingml.resource.thingml.mopp.ThingmlResource resource, org.eclipse.core.runtime.IProgressMonitor monitor);
}
