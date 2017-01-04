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
package org.sintef.thingml.resource.thingml.ui.launch;

/**
 * A class that converts the current selection or active editor to a launch
 * configuration.
 * Set the overrideLaunchShortcut option to false to customize this class.
 */
public class ThingmlLaunchShortcut implements org.eclipse.debug.ui.ILaunchShortcut2 {
	
	public void launch(org.eclipse.jface.viewers.ISelection selection, String mode) {
		if (selection instanceof org.eclipse.jface.viewers.IStructuredSelection) {
			org.eclipse.jface.viewers.IStructuredSelection structuredSelection = (org.eclipse.jface.viewers.IStructuredSelection) selection;
			java.util.Iterator<?> it = structuredSelection.iterator();
			while (it.hasNext()) {
				Object object = it.next();
				if (object instanceof org.eclipse.core.resources.IFile) {
					org.eclipse.core.resources.IFile file = (org.eclipse.core.resources.IFile) object;
					launch(file, mode);
				}
			}
		}
	}
	
	public void launch(org.eclipse.ui.IEditorPart editorPart, String mode) {
		org.eclipse.ui.IEditorInput editorInput = editorPart.getEditorInput();
		if (editorInput instanceof org.eclipse.ui.IFileEditorInput) {
			org.eclipse.ui.IFileEditorInput fileInput = (org.eclipse.ui.IFileEditorInput) editorInput;
			launch(fileInput.getFile(), mode);
		}
	}
	
	private void launch(org.eclipse.core.resources.IFile file, String mode) {
		try {
			org.eclipse.debug.core.ILaunchManager lm = org.eclipse.debug.core.DebugPlugin.getDefault().getLaunchManager();
			org.eclipse.debug.core.ILaunchConfigurationType type = lm.getLaunchConfigurationType(new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().getLaunchConfigurationType());
			org.eclipse.debug.core.ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, file.getName());
			org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			workingCopy.setAttribute(org.sintef.thingml.resource.thingml.launch.ThingmlLaunchConfigurationDelegate.ATTR_RESOURCE_URI, uri.toString());
			org.eclipse.debug.core.ILaunchConfiguration configuration = workingCopy.doSave();
			org.eclipse.debug.ui.DebugUITools.launch(configuration, mode);
		} catch (org.eclipse.core.runtime.CoreException e) {
			org.sintef.thingml.resource.thingml.mopp.ThingmlPlugin.logError("Exception while launching selection", e);
		}
	}
	
	public org.eclipse.debug.core.ILaunchConfiguration[] getLaunchConfigurations(org.eclipse.jface.viewers.ISelection selection) {
		return null;
	}
	
	public org.eclipse.debug.core.ILaunchConfiguration[] getLaunchConfigurations(org.eclipse.ui.IEditorPart editorPart) {
		return null;
	}
	
	public org.eclipse.core.resources.IResource getLaunchableResource(org.eclipse.jface.viewers.ISelection selection) {
		return null;
	}
	
	public org.eclipse.core.resources.IResource getLaunchableResource(org.eclipse.ui.IEditorPart editorPart) {
		return null;
	}
	
}
