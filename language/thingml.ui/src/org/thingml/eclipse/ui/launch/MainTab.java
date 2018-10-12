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
package org.thingml.eclipse.ui.launch;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.StringVariableSelectionDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.thingml.compilers.utils.AutoThingMLCompiler;
import org.thingml.eclipse.ui.launch.ThingMLLauncher.CompilerInfo;

@SuppressWarnings("restriction")
public class MainTab extends AbstractLaunchConfigurationTab {
	
	protected String[] compilerIds;
	protected String[] compilerDescriptions;
	protected Text fModelText;
	protected Button fAutoCompilerButton;
	protected Combo fCompilerCombo;
	protected Label fCompilerDescription;
	protected Button fDefaultOutputButton;
	protected Button fCustomOutputButton;
	protected Text fOutputDirectoryText;
	protected Button fOutputWorkspaceButton;
	protected Button fOutputFilesystemButton;
	protected Button fOutputVariablesButton;

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		comp.setLayout(new GridLayout(2, true));
		comp.setFont(parent.getFont());
		
		createModelConfigComponent(comp);
		createCompilerSelectComponent(comp);
		createCompilerConfigComponent(comp);
		createOutputDirectoryComponent(comp);
	}
	
	protected void createModelConfigComponent(Composite parent) {
		Group group = SWTFactory.createGroup(parent, "Model to compile", 2, 2, GridData.FILL_HORIZONTAL);
		fModelText = SWTFactory.createSingleText(group, 1);
		fModelText.setEditable(false);
		
		Button selectModelButton = SWTFactory.createPushButton(group, "Select file...", null);
		selectModelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleSelectModelButtonSelected();
			}
		});
	}
	
	protected void createCompilerSelectComponent(Composite parent) {
		Group group = SWTFactory.createGroup(parent, "Compiler to use", 1, 1, GridData.FILL_BOTH);
		
		if (ThingMLLauncher.compiler_auto != null) {
			fAutoCompilerButton = SWTFactory.createCheckButton(group, "Automatically select compiler", null, false, 1);
			fAutoCompilerButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateFormView();
				}
			});			
		}
		
		fCompilerCombo = new Combo(group, SWT.READ_ONLY);
		fCompilerCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fCompilerCombo.setFont(parent.getFont());
		
		// Create list of compilers
		List<CompilerInfo> infos = ThingMLLauncher.compilers;
		String[] compilerNames = new String[infos.size()+1];
		compilerIds = new String[infos.size()+1];
		compilerDescriptions = new String[infos.size()+1];
		// The not-selected option
		compilerIds[0] = "";
		compilerNames[0] = "Select compiler:";
		compilerDescriptions[0] = "Please select a compiler to use...";
		// Actual compilers
		int i = 1;
		for (CompilerInfo compiler : infos) {
			compilerIds[i] = compiler.getId();
			compilerNames[i] = compiler.getName();
			compilerDescriptions[i] = compiler.getDescription();
			i++;
		}
		fCompilerCombo.setItems(compilerNames);
		
		fCompilerDescription = SWTFactory.createWrapLabel(group, "", 1);
		fCompilerDescription.setLayoutData(new GridData(GridData.FILL_BOTH));
		fCompilerDescription.setFont(parent.getFont());
		
		fCompilerCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateFormView();
			}
		});
	}
	
	protected void createCompilerConfigComponent(Composite parent) {
		/*Group group = */SWTFactory.createGroup(parent, "Configure compiler", 1, 1, GridData.FILL_BOTH);
	}
	
	protected void createOutputDirectoryComponent(Composite parent) {
		Group group = SWTFactory.createGroup(parent, "Generated code output directory", 2, 2, GridData.FILL_HORIZONTAL);
		
		fDefaultOutputButton = SWTFactory.createRadioButton(group, "Default directory");
		GridData gd = new GridData(SWT.BEGINNING, SWT.NORMAL, true, false);
	    gd.horizontalSpan = 2;
	    fDefaultOutputButton.setLayoutData(gd);
	    
	    fCustomOutputButton = SWTFactory.createRadioButton(group, "Custom directory");
	    fCustomOutputButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
	    
	    SelectionListener radioListener = new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		updateFormView();
	    	}
		};
		fDefaultOutputButton.addSelectionListener(radioListener);
		fCustomOutputButton.addSelectionListener(radioListener);
	    
	    fOutputDirectoryText = SWTFactory.createSingleText(group, 1);
	    fOutputDirectoryText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    fOutputDirectoryText.setFont(parent.getFont());
	    fOutputDirectoryText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				hasChanged(true);
			}
		});
	    
	    Composite comp = SWTFactory.createComposite(group, 3, 2, GridData.HORIZONTAL_ALIGN_END);
	    fOutputWorkspaceButton = createPushButton(comp, "Workspace...", null);
	    fOutputWorkspaceButton.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		handleOutputWorkspaceButtonSelected();
	    	}
		});
	    fOutputFilesystemButton = createPushButton(comp, "File System...", null);
	    fOutputFilesystemButton.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		handleOutputFilesystemButtonSelected();
	    	}
		});
	    fOutputVariablesButton = createPushButton(comp, "Variables...", null);
	    fOutputVariablesButton.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		handleOutputVariablesButtonSelected();
	    	}
		});
	}
	
	/* --- Dialog handlers --- */
	protected void handleSelectModelButtonSelected() {
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), new WorkbenchLabelProvider(), new ThingMLFilesContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		dialog.setAllowMultiple(false);
		dialog.setTitle("Select ThingML model:");
		dialog.setInitialSelection(fModelText.getData());
		dialog.open();
		if (dialog.getFirstResult() instanceof IFile) {
			fModelText.setData(dialog.getFirstResult());
			updateFormView();
		}
	}
	
	protected void handleOutputWorkspaceButtonSelected() {
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), new WorkbenchLabelProvider(), new DirectoriesContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		dialog.setAllowMultiple(false);
		dialog.setTitle("Select output directory");
		dialog.setMessage("Select directory to output generated platform code into");
		// Set initial selection to project of the selected ThingML model
		if (fModelText.getData() instanceof IFile) {
			IFile modelFile = (IFile)fModelText.getData();
			IProject modelProject = modelFile.getProject();
			if (modelProject.getFolder("thingml-gen").exists())
				dialog.setInitialSelection(modelProject.getFolder("thingml-gen"));
			else
				dialog.setInitialSelection(modelProject);
		}
		dialog.open();
		IResource resource = (IResource)dialog.getFirstResult();
		if (resource != null) {
			String arg = resource.getFullPath().toString();
			String fileLoc = VariablesPlugin.getDefault().getStringVariableManager().generateVariableExpression("workspace_loc", arg);
			fOutputDirectoryText.setText(fileLoc);
		}
	}
	
	protected void handleOutputFilesystemButtonSelected() {
		DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.OPEN);
		dialog.setText("Select output directory");
		dialog.setMessage("Select directory to output generated platform code into");
		String filePath = dialog.open();
		if (filePath != null) {
			fOutputDirectoryText.setText(filePath);
			fOutputDirectoryText.setData(null);
		}
	}
	
	protected void handleOutputVariablesButtonSelected() {
		StringVariableSelectionDialog dialog = new StringVariableSelectionDialog(getShell());
		dialog.open();
		String variable = dialog.getVariableExpression();
		if (variable != null) {
			fOutputDirectoryText.insert(variable);
		}
	}
	
	public static final String defaultOutputDirectory;
	static {
		IStringVariableManager manager = VariablesPlugin.getDefault().getStringVariableManager();
		StringBuffer expression = new StringBuffer();
		expression.append(manager.generateVariableExpression("project_loc", manager.generateVariableExpression("thingml.model", null)));
		expression.append("/thingml-gen/");
		expression.append(manager.generateVariableExpression("thingml.compiler", null));
		expression.append("/");
		defaultOutputDirectory = expression.toString();
	}
	
	protected void updateFormView() {
		// Set the selected model path
		if (fModelText.getData() == null || !(fModelText.getData() instanceof IFile)) {
			fModelText.setData(null);
			fModelText.setText("");
		} else {
			IFile selectedFile = (IFile)fModelText.getData();
			fModelText.setText(selectedFile.getFullPath().toOSString());
		}
		// Set the compiler desciption
		if (fAutoCompilerButton != null && fAutoCompilerButton.getSelection()) {
			fCompilerCombo.setEnabled(false);
			fCompilerDescription.setText(ThingMLLauncher.compiler_auto.getDescription());
		} else {
			fCompilerCombo.setEnabled(true);
			fCompilerDescription.setText(compilerDescriptions[fCompilerCombo.getSelectionIndex()]);
		}
		// Set the output directory path
		if (fDefaultOutputButton.getSelection()) {
			fOutputDirectoryText.setText(defaultOutputDirectory);
			fOutputDirectoryText.setEnabled(false);
			fOutputWorkspaceButton.setEnabled(false);
			fOutputFilesystemButton.setEnabled(false);
			fOutputVariablesButton.setEnabled(false);
		} else {
			fOutputDirectoryText.setEnabled(true);
			fOutputWorkspaceButton.setEnabled(true);
			fOutputFilesystemButton.setEnabled(true);
			fOutputVariablesButton.setEnabled(true);
		}
		
		// Something has changed
		hasChanged(true);
	}
	
	protected void hasChanged(boolean changed) {
		setDirty(changed);
		getLaunchConfigurationDialog().updateButtons();
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		setConfigurationDefaults(configuration);
	}
	
	private static final String defaultCompilerID = ThingMLLauncher.compiler_auto != null ? AutoThingMLCompiler.ID : "";
	
	public static void setConfigurationDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setMappedResources(null);
		configuration.setAttribute("org.thingml.launchconfig.compiler", defaultCompilerID);
		configuration.setAttribute("org.thingml.launchconfig.outdir", defaultOutputDirectory);
	}
	
	protected int getSelectedCompilerIndex(String id) {
		for (int i = 0; i < compilerIds.length; i++) {
			String cid = compilerIds[i];
			if (cid.equals(id))
				return i;
		}
		return 0;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		// Set selected model
		Object modelData = null;
		try {
			IResource[] mappedResources = configuration.getMappedResources();
			if (mappedResources != null && mappedResources.length == 1 && mappedResources[0] instanceof IFile)
				modelData = mappedResources[0];
		} catch (CoreException e) {}
		fModelText.setData(modelData);
		// Set selected compiler
		String compilerID = defaultCompilerID;
		try {
			compilerID = configuration.getAttribute("org.thingml.launchconfig.compiler", defaultCompilerID);
		} catch (CoreException e) {
			fAutoCompilerButton.setSelection(true);
			fCompilerCombo.select(0);
		}
		if (compilerID.equals(AutoThingMLCompiler.ID)) {
			fAutoCompilerButton.setSelection(true);
			fCompilerCombo.select(0);
		} else {
			fAutoCompilerButton.setSelection(false);
			int i = getSelectedCompilerIndex(compilerID);
			fCompilerCombo.select(i);
		}
		
		// Set output directory
		try {
			fOutputDirectoryText.setText(configuration.getAttribute("org.thingml.launchconfig.outdir", defaultOutputDirectory));
		} catch (CoreException e) {
			fOutputDirectoryText.setText(defaultOutputDirectory);
		}
		if (fOutputDirectoryText.getText().equals(defaultOutputDirectory)) {
			fDefaultOutputButton.setSelection(true);
		} else {
			fCustomOutputButton.setSelection(true);
		}
		// Update the form, but set dirty back to false since nothing has changed
		updateFormView();
		hasChanged(false);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (fModelText.getData() != null && fModelText.getData() instanceof IFile)
			configuration.setMappedResources(new IResource[] { (IFile)fModelText.getData() });
		else
			configuration.setMappedResources(null);
		
		if (fAutoCompilerButton != null && fAutoCompilerButton.getSelection())
			configuration.setAttribute("org.thingml.launchconfig.compiler", AutoThingMLCompiler.ID);
		else
			configuration.setAttribute("org.thingml.launchconfig.compiler", compilerIds[fCompilerCombo.getSelectionIndex()]);
		
		configuration.setAttribute("org.thingml.launchconfig.outdir", fOutputDirectoryText.getText());
	}
	
	@Override
	public String getName() {
		return "Main";
	}
	
	@Override
	public Image getImage() {
		return ImageDescriptor.createFromURL(this.getClass().getClassLoader().getResource("icons/thingml.png")).createImage();
	}
	
	/* --- Content providers for dialogs --- */
	private static class ThingMLFilesContentProvider extends BaseWorkbenchContentProvider {
		@Override
		public Object[] getChildren(Object element) {
			Object[] originals = super.getChildren(element);
			List<Object> filtered = new LinkedList<Object>();
			for (Object original : originals) {
				if (original instanceof IFile) {
					// Only show .thingml-files
					IFile file = (IFile)original;
					if ("thingml".equals(file.getFileExtension()))
						filtered.add(original);
				} else {
					filtered.add(original);
				}
			}
			return filtered.toArray();
		}
	}
	
	private static class DirectoriesContentProvider extends BaseWorkbenchContentProvider {
		@Override
		public Object[] getChildren(Object element) {
			Object[] originals = super.getChildren(element);
			List<Object> filtered = new LinkedList<Object>();
			for (Object original : originals) {
				if (original instanceof IFolder || original instanceof IProject) {
					// Only show folders
					filtered.add(original);
				}
			}
			return filtered.toArray();
		}
	}
}
