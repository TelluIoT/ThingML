/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.debug;

public class ThingmlSourcePathComputerDelegate implements org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate {
	
	public org.eclipse.debug.core.sourcelookup.ISourceContainer[] computeSourceContainers(org.eclipse.debug.core.ILaunchConfiguration configuration, org.eclipse.core.runtime.IProgressMonitor monitor) throws org.eclipse.core.runtime.CoreException {
		return new org.eclipse.debug.core.sourcelookup.ISourceContainer[] {new org.eclipse.debug.core.sourcelookup.ISourceContainer() {
			
			@SuppressWarnings("rawtypes")			
			public Object getAdapter(Class adapter) {
				return null;
			}
			
			public boolean isComposite() {
				return false;
			}
			
			public void init(org.eclipse.debug.core.sourcelookup.ISourceLookupDirector director) {
				// do nothing
			}
			
			public org.eclipse.debug.core.sourcelookup.ISourceContainerType getType() {
				return null;
			}
			
			public org.eclipse.debug.core.sourcelookup.ISourceContainer[] getSourceContainers() throws org.eclipse.core.runtime.CoreException {
				return new org.eclipse.debug.core.sourcelookup.ISourceContainer[0];
			}
			
			public String getName() {
				return "Resource org.eclipse.emf.common.util.URI";
			}
			
			public Object[] findSourceElements(String name) throws org.eclipse.core.runtime.CoreException {
				org.eclipse.emf.common.util.URI eUri = org.eclipse.emf.common.util.URI.createURI(name);
				if (eUri.isPlatformResource()) {
					String platformString = eUri.toPlatformString(true);
					return new Object[] {org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot().findMember(platformString)};
				}
				return new Object[0];
			}
			
			public void dispose() {
			}
		}};
	}
	
}
