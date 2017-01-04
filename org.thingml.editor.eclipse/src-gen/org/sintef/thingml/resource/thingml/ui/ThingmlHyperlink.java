/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.sintef.thingml.resource.thingml.ui;

/**
 * A hyperlink for the proxy elements in source code.
 */
public class ThingmlHyperlink implements org.eclipse.jface.text.hyperlink.IHyperlink {
	
	private String text;
	private org.eclipse.emf.ecore.EObject linkTarget;
	private org.eclipse.jface.text.IRegion region;
	
	/**
	 * Creates the hyperlink.
	 * 
	 * @param region the region of the hyperlink to highlight
	 * @param linkTarget the link target where this hyperlink should go to
	 * @param targetText the text to specify the target position in the
	 * <code>linkTarget</code>
	 */
	public ThingmlHyperlink(org.eclipse.jface.text.IRegion region, org.eclipse.emf.ecore.EObject linkTarget, String targetText) {
		this.region = region;
		this.linkTarget = linkTarget;
		this.text = targetText;
	}
	
	public String getHyperlinkText() {
		return text;
	}
	
	/**
	 * 
	 * @return the length of the hyperlink text
	 */
	public int length() {
		return text.length();
	}
	
	public String getTypeLabel() {
		return null;
	}
	
	/**
	 * Opens the resource in <code>linkTarget</code> with the generated editor, if it
	 * supports the file extension of this resource, and tries to jump to the
	 * definition. Otherwise it tries to open the target with the default editor.
	 */
	public void open() {
		if (linkTarget == null) {
			return;
		}
		org.eclipse.core.resources.IFile file = getIFileFromResource();
		if (file != null) {
			org.eclipse.ui.IWorkbench workbench = org.eclipse.ui.PlatformUI.getWorkbench();
			org.eclipse.ui.IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
			try {
				org.eclipse.ui.IEditorDescriptor desc = workbench.getEditorRegistry().getDefaultEditor(file.getName());
				if (desc == null) {
					desc = workbench.getEditorRegistry().findEditor("org.eclipse.emf.ecore.presentation.ReflectiveEditorID");
				}
				org.eclipse.ui.IEditorPart editorPart = page.openEditor(new org.eclipse.ui.part.FileEditorInput(file), desc.getId());
				if (editorPart instanceof org.eclipse.emf.edit.domain.IEditingDomainProvider) {
					org.eclipse.emf.edit.domain.IEditingDomainProvider editingDomainProvider = (org.eclipse.emf.edit.domain.IEditingDomainProvider) editorPart;
					org.eclipse.emf.edit.domain.EditingDomain editingDomain = editingDomainProvider.getEditingDomain();
					org.eclipse.emf.common.util.URI uri = org.eclipse.emf.ecore.util.EcoreUtil.getURI(linkTarget);
					org.eclipse.emf.ecore.EObject originalObject = editingDomain.getResourceSet().getEObject(uri, true);
					if (editingDomainProvider instanceof org.eclipse.emf.common.ui.viewer.IViewerProvider) {
						org.eclipse.emf.common.ui.viewer.IViewerProvider viewerProvider = (org.eclipse.emf.common.ui.viewer.IViewerProvider) editingDomainProvider;
						org.eclipse.jface.viewers.Viewer viewer = viewerProvider.getViewer();
						viewer.setSelection(new org.eclipse.jface.viewers.StructuredSelection(originalObject), true);
					}
				}
			} catch (org.eclipse.ui.PartInitException e) {
				org.sintef.thingml.resource.thingml.mopp.ThingmlPlugin.logError("Exception while opening hyperlink target.", e);
			}
		}
	}
	
	private org.eclipse.core.resources.IFile getIFileFromResource() {
		org.eclipse.emf.ecore.resource.Resource linkTargetResource = linkTarget.eResource();
		if (linkTargetResource == null) {
			return null;
		}
		org.eclipse.emf.common.util.URI resourceURI = linkTargetResource.getURI();
		if (linkTargetResource.getResourceSet() != null && linkTargetResource.getResourceSet().getURIConverter() != null) {
			resourceURI = linkTargetResource.getResourceSet().getURIConverter().normalize(resourceURI);
		}
		if (resourceURI.isPlatformResource()) {
			String platformString = resourceURI.toPlatformString(true);
			if (platformString != null) {
				org.eclipse.core.resources.IWorkspace workspace = org.eclipse.core.resources.ResourcesPlugin.getWorkspace();
				org.eclipse.core.resources.IWorkspaceRoot root = workspace.getRoot();
				return root.getFile(new org.eclipse.core.runtime.Path(platformString));
			}
		}
		return null;
	}
	
	public org.eclipse.jface.text.IRegion getHyperlinkRegion() {
		return region;
	}
	
}
