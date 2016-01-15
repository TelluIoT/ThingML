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
package org.thingml.eclipse.ui.commands;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.checker.Checker.CheckerInfo;
import org.thingml.compilers.registry.ThingMLCompilerRegistry;
import org.thingml.eclipse.preferences.PreferenceConstants;
import org.thingml.eclipse.ui.Activator;
import org.thingml.eclipse.ui.ThingMLConsole;

public class CompileThingFile implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		ThingMLConsole.getInstance().printDebug("\n\n********************************************************************************\n");
		try {
			// Fetch the compiler to be used
			String compilerName = event.getParameter("org.thingml.eclipse.ui.commandParameterCompilerName").toString();
			String subCompiler = null;
			if (compilerName.contains("/")) {
				subCompiler = compilerName.split("/")[1];
				compilerName = compilerName.split("/")[0];
			}


			ThingMLCompiler compiler = ThingMLCompilerRegistry.getInstance().createCompilerInstanceByName(compilerName);
			ThingMLConsole.getInstance().printDebug("Compiling with \"" + compiler.getName() + "\" (Platform: " + compiler.getID() + ")\n");

			// Fetch the input model to be used
			IFile target_file = null;
			ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
			if (selection != null & selection instanceof IStructuredSelection) {
				IStructuredSelection strucSelection = (IStructuredSelection) selection;

				if (!strucSelection.isEmpty() && strucSelection.getFirstElement() instanceof IFile) {
					target_file = (IFile) strucSelection.getFirstElement();
				}
				else {
					ThingMLConsole.getInstance().printError("ERROR: The selection is empty or does not contains a ThingML file. Compilation stopped.\n");
					return null;
				}

				if (strucSelection.size() > 1) {
					ThingMLConsole.getInstance().printDebug("WARNING: Selection contains more than one model. Using the first and ingnoring others.\n");
				}
			}

			java.io.File f = target_file.getLocation().toFile();
			ThingMLConsole.getInstance().printDebug("Selected input file: " + target_file.toString() + " (" + f.getAbsolutePath() + ")\n");

			// Load the ThingML model
			org.eclipse.emf.ecore.resource.Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			reg.getExtensionToFactoryMap().put("thingml", new ThingmlResourceFactory());

			ResourceSet rs = new ResourceSetImpl();
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("thingml", new ThingmlResourceFactory());
			URI xmiuri = URI.createFileURI(f.getAbsolutePath());
			Resource resource = rs.getResource(xmiuri,true);

			try {
				resource.load(new HashMap());
			} catch (IOException e) {
				ThingMLConsole.getInstance().printError("ERROR: Unable to load the selected model:\n");
				ThingMLConsole.getInstance().printError(e.getLocalizedMessage());
				e.printStackTrace();
				return null;
			}

			if (!resource.getErrors().isEmpty()) {
				ThingMLConsole.getInstance().printError("ERROR: The selected model contains errors:\n");
				for (Diagnostic d : resource.getErrors()) {
					ThingMLConsole.getInstance().printError(d.getLocation() + " : " + d.getMessage() + "\n");
				}
				ThingMLConsole.getInstance().printError("Compilation stopped.\n");
				return null;
			}

			ThingMLModel model = (ThingMLModel) resource.getContents().get(0);

			// Look for a Configurations to compile
			ArrayList<Configuration> toCompile = new ArrayList<Configuration>();
			for ( Configuration cfg :  model.allConfigurations() ) {
				toCompile.add(cfg);
			}

			if (toCompile.isEmpty()) {
				ThingMLConsole.getInstance().printError("ERROR: The selected model does not contain any concrete Configuration to compile. \n");
				ThingMLConsole.getInstance().printError("Compilation stopped.\n");
				return null;
			}

			// Create the output directory in the current project in a folder "/thingml-gen/<platform>/"
			IProject project = target_file.getProject();
			java.io.File project_folder =  project.getLocation().toFile();
			java.io.File thingmlgen_folder = new java.io.File(project_folder, "thingml-gen");

			if (!thingmlgen_folder.exists()) {
				ThingMLConsole.getInstance().printDebug("Creating thingml-gen folder in " + project_folder.getAbsolutePath()  + "\n");
				thingmlgen_folder.mkdir();
			}

			java.io.File platform_folder = new java.io.File(thingmlgen_folder, compiler.getID());
			/*if (platform_folder.exists()) {
				ThingMLConsole.getInstance().printDebug("Cleaning folder " + compiler.getID() + " in "+ thingmlgen_folder.getAbsolutePath() + "\n");
				ThingMLConsole.getInstance().emptyFolder(platform_folder);
			} else {
				ThingMLConsole.getInstance().printDebug("Creating folder " + compiler.getID() + " in "+ thingmlgen_folder.getAbsolutePath() + "\n");
				platform_folder.mkdir();
			}
			project.refreshLocal(IResource.DEPTH_INFINITE, null);*/


			IPreferenceStore store = Activator.getDefault().getPreferenceStore();
			String pack = store.getString(PreferenceConstants.PACK_STRING);
			String[] options = new String[1];
			options[0] = pack;

			// Compile all the configuration
			for ( Configuration cfg :  toCompile ) {
				java.io.File cfg_folder = new java.io.File(platform_folder, cfg.getName());
				if (cfg_folder.exists()) {
					ThingMLConsole.getInstance().printDebug("Cleaning folder " + cfg_folder.getAbsolutePath() + "\n");
					ThingMLConsole.getInstance().emptyFolder(cfg_folder);
				} else {
					ThingMLConsole.getInstance().printDebug("Creating folder " + cfg_folder.getAbsolutePath() + "\n");
					cfg_folder.mkdir();
				}
				compiler = ThingMLCompilerRegistry.getInstance().createCompilerInstanceByName(compilerName);
				compiler.setOutputDirectory(cfg_folder);
				compiler.setErrorStream(ThingMLConsole.getInstance().getErrorSteam());
				compiler.setMessageStream(ThingMLConsole.getInstance().getMessageSteam());

				compiler.checker.do_check(cfg);
				ThingMLConsole.getInstance().printMessage("Configuration " + cfg.getName() + " contains " + compiler.checker.Errors.size() + " error(s), " + compiler.checker.Warnings.size() + " warning(s), and " + compiler.checker.Notices.size() + " notices.");
				if (compiler.checker.Errors.size() > 0) {
					ThingMLConsole.getInstance().printMessage("Please fix the errors below. In future versions, we will block the code generation if errors are identified!");	
				}
				for(CheckerInfo i : compiler.checker.Errors) {
					ThingMLConsole.getInstance().printError(i.toString());		         
		        }
				for(CheckerInfo i : compiler.checker.Warnings) {
					ThingMLConsole.getInstance().printMessage(i.toString());		         
		        }
				for(CheckerInfo i : compiler.checker.Notices) {
					ThingMLConsole.getInstance().printMessage(i.toString());		         
		        }
				
				boolean result = compiler.compile(cfg, options);
				if(subCompiler != null) {
					ThingMLConsole.getInstance().printDebug("Compiling with connector compiler \"" + subCompiler + "\" (Platform: " + compiler.getID() + ")\n");
					result = result && compiler.compileConnector(subCompiler, cfg);
				}

				if (result) {
					ThingMLConsole.getInstance().printDebug("Configuration " + cfg.getName() + " compiled successfully.\n");
				}
				else {
					ThingMLConsole.getInstance().printError("ERROR: Unable to compile configuration " + cfg.getName() + ". Check error messages above.\n");
				}
			}
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (Throwable e) {
			ThingMLConsole.getInstance().printError("ERROR: Exeption calling ThingML Compiler: " + e.getLocalizedMessage());
			ThingMLConsole.getInstance().printError("Please contact the ThingML development team");
			e.printStackTrace(new PrintStream(ThingMLConsole.getInstance().getErrorSteam()));
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
