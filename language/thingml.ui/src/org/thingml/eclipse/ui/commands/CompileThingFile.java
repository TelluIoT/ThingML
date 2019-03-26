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
package org.thingml.eclipse.ui.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.validation.Issue;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.registry.ThingMLCompilerRegistry;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.compilers.spi.SerializationPlugin;
import org.thingml.eclipse.ui.ThingMLConsole;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.validation.Checker;


public class CompileThingFile implements IHandler {

	private static ServiceLoader<NetworkPlugin> plugins = ServiceLoader.load(NetworkPlugin.class);
	private static Set<NetworkPlugin> loadedPlugins;
	private static ServiceLoader<SerializationPlugin> serPlugins = ServiceLoader.load(SerializationPlugin.class);
	private static Set<SerializationPlugin> loadedSerPlugins;
	
	private Checker checker = new Checker();

	//IPreferenceStore store = Activator.getDefault().getPreferenceStore();

	static {
		loadedPlugins = new HashSet<>();
		plugins.reload();
		Iterator<NetworkPlugin> it = plugins.iterator();
		ThingMLConsole.getInstance().printMessage("Loading network plugins:\n");
		while(it.hasNext()) {        	
			NetworkPlugin p = it.next();
			loadedPlugins.add(p);
			ThingMLConsole.getInstance().printMessage("\t-" + p.getName() + "\n");
		}
		loadedSerPlugins = new HashSet<>();
		serPlugins.reload();
		Iterator<SerializationPlugin> sit = serPlugins.iterator();
		ThingMLConsole.getInstance().printMessage("Loading serialization plugins:\n");
		while(sit.hasNext()) {
			SerializationPlugin sp = sit.next();
			loadedSerPlugins.add(sp);
			ThingMLConsole.getInstance().printMessage("\t-" + sp.getName() + "\n");
		}
	}


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
			for(NetworkPlugin np : loadedPlugins) {
				for(String lang : np.getTargetedLanguages())
					if(lang.compareTo(compiler.getID()) == 0) {
						compiler.addNetworkPlugin(np);
					}
			}
			for(SerializationPlugin sp : loadedSerPlugins) {
				if(sp.getTargetedLanguages().contains(compiler.getID())) {
					compiler.addSerializationPlugin(sp);
				}
			}
			/*
            if (compiler instanceof PlantUMLCompiler) {
            	PlantUMLThingImplCompiler.FACTORIZE_TRANSITIONS = store.getBoolean(PreferenceConstants.UML_FACTORIZE);
            	ThingMLPrettyPrinter.HIDE_BLOCKS = store.getBoolean(PreferenceConstants.UML_HIDE_BLOCK);
            	ThingMLPrettyPrinter.MAX_BLOCK_SIZE = store.getInt(PreferenceConstants.UML_BLOCK_SIZE);
            	ThingMLPrettyPrinter.USE_ELLIPSIS_FOR_PARAMS = store.getBoolean(PreferenceConstants.UML_ELLIPSIS);
            }
			 */
			ThingMLConsole.getInstance().printDebug("Compiling with \"" + compiler.getName() + "\"[" + new Date() + "] (Platform: " + compiler.getID() + ")\n");

			// Fetch the input model to be used
			java.io.File f = null;
			IFile target_file = null;
			if (event.getApplicationContext() instanceof File) {
				f = (File) event.getApplicationContext();				
			} else {
				if (event.getApplicationContext() instanceof IFile) {
					target_file = (IFile) event.getApplicationContext();
				}
				else {
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
				}
				f = target_file.getLocation().toFile();
			}

			ThingMLConsole.getInstance().printDebug("Selected input file: " + f.getName() + " (" + f.getAbsolutePath() + ")\n");

			ThingMLModel model = ThingMLCompiler.loadModel(f);
			if (model == null) {
				ThingMLConsole.getInstance().printError("ERROR: The selected model cannot be loaded\n");
				if (ThingMLCompiler.errors.size() > 0)
					ThingMLConsole.getInstance().printError("Please fix the errors below:\n");
				for(String error : ThingMLCompiler.errors) {
					ThingMLConsole.getInstance().printError(error + "\n");
				}
				ThingMLConsole.getInstance().printError("Compilation stopped.\n");
				return null;
			}
			for(String warning : ThingMLCompiler.warnings) {
				ThingMLConsole.getInstance().printWarn(warning + "\n");
			}


			// Look for a Configurations to compile
			ArrayList<Configuration> toCompile = new ArrayList<Configuration>();
			for ( Configuration cfg :  ThingMLHelpers.allConfigurations(model)) {
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



			String pack = "org.thingml.generated"; //store.getString(PreferenceConstants.PACK_STRING);
			String[] options = new String[1];
			options[0] = pack;

			// Compile all the configuration
			for ( Configuration cfg :  toCompile ) {
				java.io.File cfg_folder = new java.io.File(platform_folder, cfg.getName());
				java.io.File in_folder = f.getAbsoluteFile().getParentFile();
				if (cfg_folder.exists()) {
					ThingMLConsole.getInstance().printDebug("Cleaning folder " + cfg_folder.getAbsolutePath() + "\n");
					ThingMLConsole.getInstance().emptyFolder(cfg_folder);
				} else {
					ThingMLConsole.getInstance().printDebug("Creating folder " + cfg_folder.getAbsolutePath() + "\n");
					cfg_folder.mkdir();
				}
				compiler = ThingMLCompilerRegistry.getInstance().createCompilerInstanceByName(compilerName);
				compiler.setOutputDirectory(cfg_folder);
				compiler.setInputDirectory(in_folder);
				compiler.setErrorStream(ThingMLConsole.getInstance().getErrorSteam());
				compiler.setMessageStream(ThingMLConsole.getInstance().getMessageSteam());

				ThingMLConsole.getInstance().printMessage("Checking configuration...\n");
				final long startChecker = System.currentTimeMillis();
				final boolean isValid = checker.validateConfiguration(cfg);
				ThingMLConsole.getInstance().printMessage("Checking configuration... Done! Took " + (System.currentTimeMillis() - startChecker) + " ms.\n");
				
				if (isValid) {
					String location = "";
					for (Issue error : checker.getWarnings()) {
						if (error.getUriToProblem()!=null && error.getUriToProblem().toFileString()!=null && !location.equals(error.getUriToProblem().toFileString())) {
							ThingMLConsole.getInstance().printWarn("Warnings(s) in " + error.getUriToProblem().toFileString() + "\n");
							location = error.getUriToProblem().toFileString();
						}
						ThingMLConsole.getInstance().printWarn("\t[line " + error.getLineNumber() + "]: " + error.getMessage() + "\n");
					}
					location = "";
					for (Issue error : checker.getInfos()) {
						if (error.getUriToProblem()!=null && error.getUriToProblem().toFileString()!=null && !location.equals(error.getUriToProblem().toFileString())) {
							ThingMLConsole.getInstance().printMessage("Infos(s) in " + error.getUriToProblem().toFileString() + "\n");
							location = error.getUriToProblem().toFileString();
						}
						ThingMLConsole.getInstance().printMessage("\t[line " + error.getLineNumber() + "]: " + error.getMessage() + "\n");
					}
					final long start = System.currentTimeMillis();
					compiler.compile(cfg, options);
					if(subCompiler != null) {
						ThingMLConsole.getInstance().printDebug("Compiling with connector compiler \"" + subCompiler + "\" (Platform: " + compiler.getID() + ")\n");
						compiler.compileConnector(subCompiler, cfg);
					}
					ThingMLConsole.getInstance().printDebug("Configuration " + cfg.getName() + " compiled successfully [" + new Date() + "]. Took " + (System.currentTimeMillis() - start) + " ms.\n");
				} else {
					ThingMLConsole.getInstance().printError("Configuration " + cfg.getName() + " could not be compiled because of errors [" + new Date() + "].\n");
					String location = "";
					for (Issue error : checker.getErrors()) {
						if (error.getUriToProblem()!=null && error.getUriToProblem().toFileString()!=null && !location.equals(error.getUriToProblem().toFileString())) {
							ThingMLConsole.getInstance().printError("Error(s) in " + error.getUriToProblem().toFileString() + "\n");
							location = error.getUriToProblem().toFileString();
						}
						ThingMLConsole.getInstance().printError("\t[line " + error.getLineNumber() + "]: " + error.getMessage() + "\n");
					}
				}
			}
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (Throwable e) {
			ThingMLConsole.getInstance().printError("Please contact the ThingML development team (though GitHub's issue tracker) with 1) your input model, and 2) the following stack trace:\n");
			ThingMLConsole.getInstance().printError("FATAL ERROR: Exception calling ThingML Compiler: " + e.toString() + "\n");			
			for(StackTraceElement ste : e.getStackTrace()) {//This should now print the stack trace to Eclipse ThingML Console.
				ThingMLConsole.getInstance().printError("\t" + ste.toString() + "\n");
			}
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
