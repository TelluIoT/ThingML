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

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.xtext.validation.Issue;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.registry.ThingMLCompilerRegistry;
import org.thingml.compilers.utils.AutoThingMLCompiler;
import org.thingml.eclipse.ui.ThingMLConsole;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.validation.Checker;

public class ThingMLLauncher extends LaunchConfigurationDelegate {
	
	protected File getValidInputFile(ILaunchConfiguration configuration, ThingMLConsole console) throws CoreException {
		
		IFile inputFile = null;
		// Check that one is set
		if (configuration.getMappedResources() != null && configuration.getMappedResources().length == 1 && configuration.getMappedResources()[0] instanceof IFile)
			inputFile = (IFile)configuration.getMappedResources()[0];
		// Check that it is valid
		if (inputFile == null || !inputFile.exists()) {
			console.printErrorln("Missing input model!");
			return null;
		} else if (!("thingml".equals(inputFile.getFileExtension()))) {
			console.printErrorln("Input model is not a .thingml-file!");
			return null;
		}
		// Find the absolute file path
		IPath location = inputFile.getLocation();
		if (location == null) {
			console.printErrorln("Cannot locate input model in filesystem!");
			return null;
		} else {
			return location.toFile();
		}
	}
	
	protected ThingMLCompiler getValidCompiler(ILaunchConfiguration configuration, ThingMLConsole console) throws CoreException {
		String selectedCompiler = configuration.getAttribute("org.thingml.launchconfig.compiler", "");
		if (selectedCompiler.equals(AutoThingMLCompiler.ID))
			return compiler_auto;
		if (selectedCompiler.isEmpty()) {
			console.printErrorln("Compiler not selected!");
			return null;
		}
		return registry.createCompilerInstanceByName(selectedCompiler);
	}
	
	protected File getValidOutdir(ILaunchConfiguration configuration, ThingMLConsole console) throws CoreException {
		String selectedOutdir = configuration.getAttribute("org.thingml.launchconfig.outdir", "");
		if (selectedOutdir.isEmpty()) {
			console.printErrorln("Not output directory selected!");
			return null;
		}
		selectedOutdir = VariableResolver.resolveExpressionForLaunchConfiguration(selectedOutdir, configuration);
		File outDir = new File(selectedOutdir);
		if (outDir.exists() && !outDir.isDirectory()) {
			console.printErrorln("Output directory is not a directory!");
			return null;
		} else if (!outDir.exists()) {
			if (!outDir.mkdirs()) {
				console.printErrorln("Could not create output directory!");
				return null;
			}
		}
		return outDir;
	}
	
	protected Logger getConsoleLogger(ThingMLConsole console) {
		return new Logger() {
			@Override
			public void warning(String message) {
				console.printWarnln(message);				
			}
			@Override
			public void info(String message) {
				console.printMessageln(message);
			}
			@Override
			public void error(String message) {
				console.printErrorln(message);
			}
			@Override
			public void debug(String message) {
				console.printDebugln(message);
			}
		};
	}
	
	protected void printIssuesSortedByLocation(List<Issue> issues, String type, Consumer<String> print) {
		String location = "";
		for (Issue issue : issues) {
			String currentLocation = issue.getUriToProblem() != null ? issue.getUriToProblem().toFileString() : null;
			// Print location every time it changes
			if (currentLocation != null && !location.equals(currentLocation)) {
				print.accept(type);
				print.accept("(s) in ");
				print.accept(currentLocation);
				print.accept("\n");
			}
			// Print the actual issue
			print.accept("\t [line ");
			print.accept(issue.getLineNumber().toString());
			print.accept("]: ");
			print.accept(issue.getMessage());
			print.accept("\n");
		}
	}
	
	protected File getCleanFolderForConfiguration(File outDir, Configuration config, ThingMLConsole console) {
		File configFolder = new File(outDir, config.getName());
		if (configFolder.exists()) {
			if (configFolder.isDirectory()) {
				// Clean the folder of previous results
				try {
					Files.walkFileTree(configFolder.toPath(), new FileVisitor<Path>() {
						@Override
						public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
							Files.delete(dir);
							return FileVisitResult.CONTINUE;
						}
						@Override
						public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) { return FileVisitResult.CONTINUE; }
						@Override
						public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
							Files.delete(file);
							return FileVisitResult.CONTINUE;
						}
						@Override
						public FileVisitResult visitFileFailed(Path file, IOException exc) { return FileVisitResult.TERMINATE; }
					});
				} catch (IOException e) {
					console.printErrorln("Could not clean directory "+configFolder.getAbsolutePath());
					return null;
				}
			} else {
				console.printErrorln(configFolder.getAbsolutePath()+" exists but is not a directory!");
				return null;
			}
		} else {
			if (!configFolder.mkdirs()) {
				console.printErrorln("Could not create directory "+configFolder.getAbsolutePath());
				return null;
			}
		}
		return configFolder;
	}

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		ThingMLConsole console = ThingMLConsole.getInstance();
		console.clear();
		console.activate();
		monitor.beginTask("Generate platform code", 2);
		console.printDebugln("MODE: "+mode);
		console.printDebugln("**** Starting compilation ["+new Date()+"] ****");
		
		// Get launch configuration info
		File inputFile = getValidInputFile(configuration, console);
		ThingMLCompiler selectedCompiler = getValidCompiler(configuration, console);
		File selectedOutdir = getValidOutdir(configuration, console);
		
		// Check that we have everything that we need
		if (inputFile == null || selectedCompiler == null || selectedOutdir == null) {
			console.printErrorln("There was errors in the configuration...");
			monitor.done();
			return;
		}
		
		// Print start info
		console.printDebugln("Input model: "+inputFile.getPath());
		console.printDebugln("Compiler: "+selectedCompiler.getName());
		console.printDebugln("Output directory: "+selectedOutdir.getPath());
		
		// Load the ThingML-model
		Logger consoleLogger = getConsoleLogger(console);
		ThingMLModel inputModel = ThingMLCompiler.loadModel(inputFile, consoleLogger);
		monitor.worked(1);
		
		// If it was not loaded, there was probably some errors
		if (inputModel == null) {
			console.printErrorln("The input model could not be loaded!");
			if (!ThingMLCompiler.errors.isEmpty()) {
				console.printErrorln("Please fix the following errors:");
				for (String error : ThingMLCompiler.errors)
					console.printErrorln(error);
			}
			monitor.done();
			return;
		}
		if (!ThingMLCompiler.warnings.isEmpty()) {
			for (String warning : ThingMLCompiler.warnings)
				console.printWarnln(warning);
		}
		
		// Get configurations to compile
		List<Configuration> configs = ThingMLHelpers.allConfigurations(inputModel);
		for (Configuration config : configs) {
			// Run checker on configuration
			Checker checker = new Checker();
			long startChecker = System.currentTimeMillis();
			console.printDebug("Checking configuration "+config.getName()+"...");
			boolean configValid = checker.validateConfiguration(config);
			long checkerTook = System.currentTimeMillis()-startChecker;
			console.printDebugln(" Done. Took "+checkerTook+"ms");
			
			if (configValid) {
				printIssuesSortedByLocation(checker.getInfos(), "Info", console::printMessage);
				printIssuesSortedByLocation(checker.getWarnings(), "Warning", console::printWarn);
				
				// Create folder to put output files
				File configDir = getCleanFolderForConfiguration(selectedOutdir, config, console);
				if (configDir == null) {
					console.printErrorln("Configuration "+config.getName()+" could not be compiled ["+new Date()+"]");
					continue;
				}
				
				long startCompiler = System.currentTimeMillis();
				console.printDebug("Compiling configuration "+config.getName()+"...");
				
				// Run actual compilation
				//ThingMLCompiler compiler = selectedCompiler.clone(); // This doesn't currently work as it should
				ThingMLCompiler compiler = registry.createCompilerInstanceByName(selectedCompiler.getID());
				compiler.setInputDirectory(inputFile.getParentFile()); // FIXME: This is probably not a very good way to do this...
				compiler.setOutputDirectory(configDir);
				compiler.compile(config);
				
				long compilerTook = System.currentTimeMillis()-startCompiler;
				console.printDebugln(" Done. Took "+compilerTook+"ms");
				console.printDebugln("Configuration "+config.getName()+" compiled successfully ["+new Date()+"]");
			} else {
				printIssuesSortedByLocation(checker.getErrors(), "Error", console::printError);
				console.printErrorln("Configuration "+config.getName()+" could not be compiled because of errors ["+new Date()+"]");
			}
		}
		monitor.worked(1);
		
		// Refresh the workspace in Eclipse so we see the new files
		IFile thingmlFile = (IFile)configuration.getMappedResources()[0]; // We have already checked that this is safe
		thingmlFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
		
		// All done :)
		monitor.done();
	}
	
	
	/* --- Helpers for listing compilers --- */
	protected static final ThingMLCompilerRegistry registry = ThingMLCompilerRegistry.getInstance();
	public static final Set<String> compilerIDs = Collections.unmodifiableSet(registry.getCompilerIds());
	
	public static class CompilerInfo {
		private String Id;
		private String Name;
		private String Description;
		
		private CompilerInfo(String id, String name, String description) {
			Id = id;
			Name = name;
			Description = description;
		}
		
		public String getId() { return Id; }
		public String getName() { return Name; }
		public String getDescription() { return Description; }
		
		private static Comparator<CompilerInfo> compareByName() {
			return new Comparator<ThingMLLauncher.CompilerInfo>() {
				@Override
				public int compare(CompilerInfo c1, CompilerInfo c2) {
					return c1.getName().compareTo(c2.getName());
				}
			};
		}
	}
	
	public static final List<CompilerInfo> compilers;
	public static ThingMLCompiler compiler_auto = null;
	static {
		List<CompilerInfo> infos = new LinkedList<CompilerInfo>();
		// Add all compilers with a name, except for auto compiler
		for (String compiler : compilerIDs) {
			if (registry.getCompilerNameById(compiler) != null) {
				if (compiler.equals(AutoThingMLCompiler.ID)) {
					compiler_auto = registry.createCompilerInstanceByName(compiler);
				} else {
					infos.add(new CompilerInfo(
							compiler, 
							registry.getCompilerNameById(compiler), 
							registry.getCompilerDescriptionById(compiler)
							));
				}
			}
		}
		// Sort the list
		infos.sort(CompilerInfo.compareByName());
		// Create an unmodifiable copy
		compilers = Collections.unmodifiableList(infos);
	}
}
