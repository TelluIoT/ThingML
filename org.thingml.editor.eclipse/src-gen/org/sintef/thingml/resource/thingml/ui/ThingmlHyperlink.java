/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
				org.eclipse.ui.IEditorPart editorPart = page.openEditor(new org.eclipse.ui.part.FileEditorInput(file), desc.getId());
				if (editorPart instanceof org.sintef.thingml.resource.thingml.ui.ThingmlEditor) {
					org.sintef.thingml.resource.thingml.ui.ThingmlEditor emftEditor = (org.sintef.thingml.resource.thingml.ui.ThingmlEditor) editorPart;
					emftEditor.setCaret(linkTarget, text);
				}
			} catch (org.eclipse.ui.PartInitException e) {
				e.printStackTrace();
			}
		}
	}
	
	private org.eclipse.core.resources.IFile getIFileFromResource() {
		org.eclipse.emf.ecore.resource.Resource linkTargetResource = linkTarget.eResource();
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
