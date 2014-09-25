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
package org.thingml.eclipse.ui.popup.actions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.core.internal.resources.File;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.sintef.thingml.ThingMLModel;
import org.thingml.cgenerator.CGenerator;

public class ThingMLCompileArduino implements IObjectActionDelegate {

	private Shell shell;

	/**
	 * Constructor for Action1.
	 */
	public ThingMLCompileArduino() {
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
		ThingMLModel thingmlModel = LoadModelUtil.getInstance()
				.loadThingMLmodel(f1);
		java.io.File ftemp = null;
		String tempDir = System.getProperty("java.io.tmpdir") + "tmp"
				+ System.nanoTime();
		ftemp = new java.io.File(tempDir);
		if (!ftemp.exists())
			ftemp.mkdir();
		CGenerator.compileAndRunArduino(thingmlModel,
				ftemp.getAbsolutePath() + java.io.File.pathSeparator,
				ftemp.getAbsolutePath() + java.io.File.pathSeparator,false);
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
