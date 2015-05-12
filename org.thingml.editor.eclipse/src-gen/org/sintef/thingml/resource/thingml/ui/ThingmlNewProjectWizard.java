/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

/**
 * This class is based on:
 * <i>org.eclipse.gef.examples.ui.pde.internal.wizards.ProjectUnzipperNewWizard</i>
 * .
 * It is responsible for offering an example project via the new dialog of Eclipse.
 */
public class ThingmlNewProjectWizard extends org.eclipse.jface.wizard.Wizard implements org.eclipse.ui.INewWizard, org.eclipse.core.runtime.IExecutableExtension {
	
	/**
	 * The single page provided by this base implementation. It provides all the
	 * functionality required to capture the name and location of the target project.
	 */
	private org.eclipse.ui.dialogs.WizardNewProjectCreationPage wizardNewProjectCreationPage;
	
	/**
	 * The name of the project creation page
	 */
	private String pageName = "New " + new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().getSyntaxName() + " Project";
	
	/**
	 * The title of the project creation page
	 */
	private String pageTitle = pageName;
	
	/**
	 * The description of the project creation page
	 */
	private String pageDescription = "Enter a name and select a location where the new project shall be created.";
	
	/**
	 *  The name of the project in the project creation page
	 */
	private String  pageProjectName= "";
	
	/**
	 * The name of the new project zip file (relative to the UI plugin's root)
	 */
	private String  newProjectZip="newProject.zip";
	
	/**
	 * The configuration element associated with this new project wizard
	 */
	private org.eclipse.core.runtime.IConfigurationElement config;
	
	/**
	 * The constructor.
	 */
	public ThingmlNewProjectWizard() {
		super();
	}
	
	/**
	 * Performs the bulk of the wizard functionality: project creation, the unzip
	 * operation and classpath update.
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish
	 */
	public boolean performFinish() {
		
		try {
			org.eclipse.jface.operation.IRunnableWithProgress operation = new org.eclipse.ui.actions.WorkspaceModifyOperation() {
				
				public void execute(org.eclipse.core.runtime.IProgressMonitor monitor) throws InterruptedException {
					try {
						monitor.beginTask("Creating Example Project", 120);
						
						// Create the project folder
						org.eclipse.core.runtime.IPath projectPath = wizardNewProjectCreationPage.getLocationPath();
						
						String projectName = wizardNewProjectCreationPage.getProjectName();
						String projectFolder = projectPath.toOSString() + java.io.File.separator + projectName;
						java.io.File projectFolderFile = new java.io.File(projectFolder);
						
						org.eclipse.core.resources.IWorkspace workspace = org.eclipse.core.resources.ResourcesPlugin.getWorkspace();
						org.eclipse.core.resources.IProject project = workspace.getRoot().getProject(projectName);
						
						// If the project does not exist, we will create it and populate it.
						if (!project.exists()) {
							projectFolderFile.mkdirs();
							monitor.worked(10);
							
							org.osgi.framework.Bundle bundle = org.eclipse.core.runtime.Platform.getBundle("org.sintef.thingml.resource.thingml.ui");
							java.net.URL newProjectZipURL = bundle.getEntry(newProjectZip);
							
							if (newProjectZipURL != null) {
								// Copy plug-in project code
								extractProject(projectFolderFile, newProjectZipURL, new org.eclipse.core.runtime.SubProgressMonitor(monitor, 100));
							}
							
							if (monitor.isCanceled()) {
								throw new InterruptedException();
							}
							
							org.eclipse.core.resources.IProjectDescription desc = workspace.newProjectDescription(project.getName());
							if (!projectPath.equals(workspace.getRoot().getLocation())) {
								desc.setLocation(new org.eclipse.core.runtime.Path(projectFolder));
							}
							
							String natureID = org.sintef.thingml.resource.thingml.mopp.ThingmlNature.NATURE_ID;
							java.util.List<org.eclipse.core.resources.ICommand> buildCommands = new java.util.ArrayList<org.eclipse.core.resources.ICommand>();
							for (String builderID : org.sintef.thingml.resource.thingml.mopp.ThingmlNature.BUILDER_IDS) {
								org.eclipse.core.resources.ICommand command = desc.newCommand();
								command.setBuilderName(builderID);
								buildCommands.add(command);
							}
							
							desc.setNatureIds(new String[] {natureID});
							desc.setBuildSpec(buildCommands.toArray(new org.eclipse.core.resources.ICommand[buildCommands.size()]));
							project.create(desc, monitor);
							// Now, we ensure that the project is open.
							project.open(monitor);
							renameProject(project, projectName);
							
							org.eclipse.core.resources.IFile defaultNewFile = project.getFile("NEW_FILE_PLACEHOLDER");
							if (newProjectZipURL == null) {
								defaultNewFile.create(new java.io.ByteArrayInputStream(new byte[0]), true, null);
							}
							if (defaultNewFile.exists()) {
								org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation info = new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation();
								String fileName = "new_file." + info.getSyntaxName();
								String content = info.getNewFileContentProvider().getNewFileContent("new_file." + info.getSyntaxName());
								defaultNewFile.setContents(new java.io.ByteArrayInputStream(content.getBytes()), org.eclipse.core.resources.IFile.FORCE, null);
								defaultNewFile.move(project.getProjectRelativePath().append(fileName), true, null);
							}
						}
						
						monitor.worked(10);
						if (monitor.isCanceled()) {
							throw new InterruptedException();
						}
						
					} catch (java.io.IOException e) {
						throw new RuntimeException(e);
					} catch (org.eclipse.core.runtime.CoreException e) {
						throw new RuntimeException(e);
					} finally {
						monitor.done();
					}
				}
			};
			
			getContainer().run(false, true, operation);
			
			// Set perspective
			org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard.updatePerspective(config);
			
		} catch (InterruptedException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Unzip the project archive to the specified folder
	 * 
	 * @param projectFolderFile The folder where to unzip the project archive
	 * @param monitor Monitor to display progress and/or cancel operation
	 * 
	 * @throws java.io.IOException
	 * 
	 * @throws InterruptedException
	 * 
	 * @throws java.io.FileNotFoundException
	 */
	private void extractProject(java.io.File projectFolderFile, java.net.URL url, org.eclipse.core.runtime.IProgressMonitor monitor) throws java.io.FileNotFoundException, java.io.IOException, InterruptedException {
		
		// Get project archive
		java.net.URL urlZipLocal = org.eclipse.core.runtime.FileLocator.toFileURL(url);
		
		// Walk each element and unzip
		java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(urlZipLocal.getPath());
		
		try {
			// Allow for a hundred work units
			monitor.beginTask("Extracting Project", zipFile.size());
			
			unzip(zipFile, projectFolderFile, monitor);
		} finally {
			zipFile.close();
			monitor.done();
		}
	}
	
	/**
	 * Unzips the platform formatted zip file to specified folder
	 * 
	 * @param zipFile The platform formatted zip file
	 * @param projectFolderFile The folder where to unzip the project archive
	 * @param monitor Monitor to display progress and/or cancel operation
	 * 
	 * @throws java.io.IOException
	 * 
	 * @throws java.io.FileNotFoundException
	 * 
	 * @throws InterruptedException
	 */
	private void unzip(java.util.zip.ZipFile zipFile, java.io.File projectFolderFile, org.eclipse.core.runtime.IProgressMonitor monitor) throws java.io.IOException, java.io.FileNotFoundException, InterruptedException {
		
		java.util.Enumeration<? extends java.util.zip.ZipEntry> e = zipFile.entries();
		
		while (e.hasMoreElements()) {
			java.util.zip.ZipEntry zipEntry = (java.util.zip.ZipEntry) e.nextElement();
			java.io.File file = new java.io.File(projectFolderFile, zipEntry.getName());
			
			if (false == zipEntry.isDirectory()) {
				
				// Copy files (and make sure parent directory exist)
				java.io.File parentFile = file.getParentFile();
				if (null != parentFile && false == parentFile.exists()) {
					parentFile.mkdirs();
				}
				
				org.eclipse.core.runtime.Path path = new org.eclipse.core.runtime.Path(file.getPath());
				if ("java".equals(path.getFileExtension())) {
					java.io.InputStreamReader is = null;
					java.io.OutputStreamWriter os = null;
					
					try {
						is = new java.io.InputStreamReader(zipFile.getInputStream(zipEntry), "ISO-8859-1");
						os = new java.io.OutputStreamWriter(new java.io.FileOutputStream(file), org.eclipse.core.resources.ResourcesPlugin.getEncoding());
						char[] buffer = new char[102400];
						while (true) {
							int len = is.read(buffer);
							if (len < 0)							break;
							os.write(buffer, 0, len);
						}
					} finally {
						if (null != is) {
							is.close();
						}
						if (null != os) {
							os.close();
						}
					}
				} else {
					java.io.InputStream is = null;
					java.io.OutputStream os = null;
					
					try {
						is = zipFile.getInputStream(zipEntry);
						os = new java.io.FileOutputStream(file);
						
						byte[] buffer = new byte[102400];
						while (true) {
							int len = is.read(buffer);
							if (len < 0)							break;
							os.write(buffer, 0, len);
						}
					} finally {
						if (null != is) {
							is.close();
						}
						if (null != os) {
							os.close();
						}
					}
				}
			}
			
			monitor.worked(1);
			
			if (monitor.isCanceled()) {
				throw new InterruptedException();
			}
		}
	}
	
	/**
	 * Renames the specified project to the specified name
	 * 
	 * @param project Project to rename
	 * @param projectName New name for the project
	 * 
	 * @throws org.eclipse.core.runtime.CoreException
	 */
	private void renameProject(org.eclipse.core.resources.IProject project, String projectName) throws org.eclipse.core.runtime.CoreException {
		org.eclipse.core.resources.IProjectDescription description = project.getDescription();
		description.setName(projectName);
		project.move(description, org.eclipse.core.resources.IResource.FORCE | org.eclipse.core.resources.IResource.SHALLOW, null);
	}
	
	/**
	 * Creates the sole wizard page contributed by this base implementation; the
	 * standard Eclipse WizardNewProjectCreationPage.
	 * 
	 * @see
	 * org.eclipse.ui.dialogs.WizardNewProjectCreationPage#org.eclipse.ui.dialogs.Wizar
	 * dNewProjectCreationPage(String)
	 */
	public void init(org.eclipse.ui.IWorkbench workbench, org.eclipse.jface.viewers.IStructuredSelection selection) {
		// Set default image for all wizard pages
		org.eclipse.core.runtime.IPath path = new org.eclipse.core.runtime.Path("icons/new_project_wizban.gif");
		org.osgi.framework.Bundle bundle = org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.getDefault().getBundle();
		java.net.URL url = org.eclipse.core.runtime.FileLocator.find(bundle, path, null);
		org.eclipse.jface.resource.ImageDescriptor descriptor = org.eclipse.jface.resource.ImageDescriptor.createFromURL(url);
		setDefaultPageImageDescriptor(descriptor);
		
		wizardNewProjectCreationPage = new org.eclipse.ui.dialogs.WizardNewProjectCreationPage(pageName);
		wizardNewProjectCreationPage.setTitle(pageTitle);
		wizardNewProjectCreationPage.setDescription(pageDescription);
		wizardNewProjectCreationPage.setInitialProjectName(pageProjectName);
		
		this.addPage(wizardNewProjectCreationPage);
	}
	
	public void setInitializationData(org.eclipse.core.runtime.IConfigurationElement configIn, String propertyName, Object data) throws org.eclipse.core.runtime.CoreException {
		config = configIn;
	}
	
}
