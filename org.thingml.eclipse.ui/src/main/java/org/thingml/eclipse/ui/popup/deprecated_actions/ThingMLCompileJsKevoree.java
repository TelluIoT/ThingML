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
package org.thingml.eclipse.ui.popup.deprecated_actions;

import java.util.ArrayList;

import org.thingml.compilers.*;
import org.thingml.compilers.actions.*;
import org.thingml.eclipse.ui.ThingMLConsole;
import org.thingml.javagenerator.kevoree.KevoreeGenerator;
import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ThingMLModel;

public class ThingMLCompileJsKevoree implements IObjectActionDelegate {

	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public ThingMLCompileJsKevoree() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService().getSelection().isEmpty())
			return;
		File f = (File) ((TreeSelection) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getSelectionService()
				.getSelection()).getFirstElement();
		java.io.File f1 = f.getLocation().toFile();
        ThingMLModel model = LoadModelUtil.getInstance().loadThingMLmodel(f1);
        ArrayList<Configuration> toCompile = new ArrayList<Configuration>();
        for ( Configuration cfg :  model.allConfigurations() ) {
            if (!cfg.isFragment()) toCompile.add(cfg);
        }

        if (toCompile.isEmpty()) {
            ThingMLConsole.getInstance().printError("ERROR: The selected model does not contain any concrete Configuration to compile. \n");
            ThingMLConsole.getInstance().printError("Compilation stopped.\n");
        }

        // Create the output directory in the current project in a folder "/thingml-gen/<platform>/"
        IProject project = f.getProject();
        java.io.File project_folder =  project.getLocation().toFile();
        java.io.File thingmlgen_folder = new java.io.File(project_folder, "thingml-gen");

        if (!thingmlgen_folder.exists()) {
            ThingMLConsole.getInstance().printDebug("Creating thingml-gen folder in " + project_folder.getAbsolutePath()  + "\n");
            thingmlgen_folder.mkdir();
        }

        java.io.File platform_folder = new java.io.File(thingmlgen_folder, "javascript");
        if (!platform_folder.exists()) {
            ThingMLConsole.getInstance().printDebug("Creating folder javascript in "+ thingmlgen_folder.getAbsolutePath() + "\n");
            platform_folder.mkdir();
        }

        // Compile all the configuration
        ThingMLCompiler compiler = new JavaScriptCompiler();
        for ( Configuration cfg :  toCompile ) {
            compiler.setOutputDirectory(platform_folder);
            Context ctx = new Context(compiler);
            compiler.getBuildCompiler().generate(cfg, ctx);
            compiler.compileConnector("kevoree-js", cfg);
            ctx.dump();
        }
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
