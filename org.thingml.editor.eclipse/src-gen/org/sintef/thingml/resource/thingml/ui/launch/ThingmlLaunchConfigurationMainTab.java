/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
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
package org.sintef.thingml.resource.thingml.ui.launch;

/**
 * A class that provides the main tab to parameterize launch configurations.
 * Set the overrideLaunchConfigurationMainTab option to false to customize this
 * class.
 */
public class ThingmlLaunchConfigurationMainTab extends org.eclipse.debug.ui.AbstractLaunchConfigurationTab {
	
	private org.eclipse.swt.widgets.Label uriLabel;
	private org.eclipse.swt.widgets.Text uriText;
	private org.eclipse.swt.widgets.Button workspaceButton;
	private org.eclipse.swt.widgets.Button fileSystemButton;
	
	public void createControl(org.eclipse.swt.widgets.Composite parent) {
		org.eclipse.swt.widgets.Composite comp = new org.eclipse.swt.widgets.Composite(parent, org.eclipse.swt.SWT.NONE);
		org.eclipse.swt.layout.GridLayout layout = new org.eclipse.swt.layout.GridLayout(1, true);
		comp.setLayout(layout);
		
		org.eclipse.swt.layout.GridData gd = new org.eclipse.swt.layout.GridData(org.eclipse.swt.layout.GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = org.eclipse.swt.SWT.FILL;
		comp.setLayoutData(gd);
		
		org.eclipse.swt.widgets.Group group = new org.eclipse.swt.widgets.Group(comp, org.eclipse.swt.SWT.NONE);
		group.setText("Launch parameters");
		group.setLayout(new org.eclipse.swt.layout.GridLayout(3, false));
		gd = new org.eclipse.swt.layout.GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = org.eclipse.swt.SWT.FILL;
		group.setLayoutData(gd);
		
		uriLabel = new org.eclipse.swt.widgets.Label(group, org.eclipse.swt.SWT.NONE);
		uriLabel.setText("Resource to execute:");
		gd = new org.eclipse.swt.layout.GridData();
		uriLabel.setLayoutData(gd);
		
		uriText = new org.eclipse.swt.widgets.Text(group, org.eclipse.swt.SWT.SINGLE | org.eclipse.swt.SWT.BORDER);
		uriText.setLayoutData(new org.eclipse.swt.layout.GridData(org.eclipse.swt.layout.GridData.FILL_HORIZONTAL));
		uriText.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});
		gd = new org.eclipse.swt.layout.GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = org.eclipse.swt.SWT.FILL;
		gd.horizontalSpan = 2;
		uriText.setLayoutData(gd);
		
		workspaceButton = new org.eclipse.swt.widgets.Button(group, org.eclipse.swt.SWT.PUSH);
		workspaceButton.setText("Workspace...");
		workspaceButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
				handleBrowseWorkspace();
			}
			
		});
		gd = new org.eclipse.swt.layout.GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = org.eclipse.swt.SWT.RIGHT;
		gd.horizontalSpan = 2;
		workspaceButton.setLayoutData(gd);
		
		fileSystemButton = new org.eclipse.swt.widgets.Button(group, org.eclipse.swt.SWT.PUSH);
		fileSystemButton.setText("File System...");
		fileSystemButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
				handleBrowseFileSystem();
			}
			
		});
		gd = new org.eclipse.swt.layout.GridData();
		gd.horizontalAlignment = org.eclipse.swt.SWT.RIGHT;
		fileSystemButton.setLayoutData(gd);
		
		setControl(comp);
	}
	
	public void setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy configuration) {
	}
	
	public void initializeFrom(org.eclipse.debug.core.ILaunchConfiguration configuration) {
		try {
			uriText.setText(configuration.getAttribute(org.sintef.thingml.resource.thingml.launch.ThingmlLaunchConfigurationDelegate.ATTR_RESOURCE_URI, ""));
			// more initialization code can be added here
		} catch (org.eclipse.core.runtime.CoreException e) {
			org.sintef.thingml.resource.thingml.mopp.ThingmlPlugin.logError("Can't initialize launch configuration tab.", e);
		}
	}
	
	public void performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(org.sintef.thingml.resource.thingml.launch.ThingmlLaunchConfigurationDelegate.ATTR_RESOURCE_URI, uriText.getText());
	}
	
	public String getName() {
		return "Main";
	}
	
	@Override	
	public org.eclipse.swt.graphics.Image getImage() {
		return org.sintef.thingml.resource.thingml.ui.ThingmlImageProvider.INSTANCE.getImage("icons/launch_tab_main_icon.gif");
	}
	
	protected void handleBrowseFileSystem() {
		org.eclipse.swt.widgets.FileDialog dialog = new org.eclipse.swt.widgets.FileDialog(getControl().getShell());
		dialog.setText("Select resource to launch");
		String result = dialog.open();
		if (result != null) {
			uriText.setText(org.eclipse.emf.common.util.URI.createFileURI(result).toString());
		}
	}
	
	protected void handleBrowseWorkspace() {
		org.eclipse.ui.dialogs.ElementTreeSelectionDialog dialog = new org.eclipse.ui.dialogs.ElementTreeSelectionDialog(getControl().getShell(), new org.eclipse.ui.model.WorkbenchLabelProvider(), new org.eclipse.ui.model.WorkbenchContentProvider());
		
		dialog.setInput(org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot());
		dialog.addFilter(new org.eclipse.jface.viewers.ViewerFilter() {
			
			@Override			
			public boolean select(org.eclipse.jface.viewers.Viewer viewer, Object parentElement, Object element) {
				if (element instanceof org.eclipse.core.resources.IFile) {
					org.eclipse.core.resources.IFile file = (org.eclipse.core.resources.IFile) element;
					return file.getFileExtension().equals(new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().getSyntaxName());
				}
				return true;
			}
		});
		dialog.setAllowMultiple(false);
		dialog.setTitle("Select model to launch");
		dialog.setMessage("Resource to launch");
		dialog.setValidator(new org.eclipse.ui.dialogs.ISelectionStatusValidator() {
			public org.eclipse.core.runtime.IStatus validate(Object[] selection) {
				if (selection.length > 0 && selection[0] instanceof org.eclipse.core.resources.IFile)				return new org.eclipse.core.runtime.Status(org.eclipse.core.runtime.IStatus.OK, org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.PLUGIN_ID, org.eclipse.core.runtime.IStatus.OK, "", null);
				
				return new org.eclipse.core.runtime.Status(org.eclipse.core.runtime.IStatus.ERROR, org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.PLUGIN_ID, org.eclipse.core.runtime.IStatus.ERROR, "", null);
			}
		});
		if (dialog.open() == org.eclipse.jface.window.Window.OK) {
			org.eclipse.core.resources.IFile file = (org.eclipse.core.resources.IFile) dialog.getFirstResult();
			uriText.setText(org.eclipse.emf.common.util.URI.createPlatformResourceURI(file.getFullPath().makeRelative().toString(), true).toString());
		}
	}
	
}
