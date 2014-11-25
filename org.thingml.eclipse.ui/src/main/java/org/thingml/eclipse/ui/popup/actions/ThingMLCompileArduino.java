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

import org.eclipse.core.internal.resources.File;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.sintef.thingml.ThingMLModel;
import org.thingml.cgenerator.CGenerator;
import org.thingml.eclipse.preferences.PreferenceConstants;
import org.thingml.eclipse.ui.Activator;
import org.thingml.eclipse.ui.ThingMLConsole;

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
		
		ThingMLConsole.getInstance().printDebug("Running the Arduino Compiler\n");
		
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService().getSelection().isEmpty())
			return;
		File f = (File) ((TreeSelection) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getSelectionService()
				.getSelection()).getFirstElement();
		java.io.File f1 = f.getLocation().toFile();
		
		ThingMLConsole.getInstance().printDebug("Input file: " + f1.getAbsolutePath() + "\n");
		
		ThingMLModel thingmlModel = LoadModelUtil.getInstance()
				.loadThingMLmodel(f1);
		java.io.File ftemp = null;
		String tempDir = System.getProperty("java.io.tmpdir") + "/tmp"
				+ System.nanoTime();
		ftemp = new java.io.File(tempDir);
		if (!ftemp.exists())
			ftemp.mkdir();
		
		ThingMLConsole.getInstance().printDebug("Output directory: " + ftemp.getAbsolutePath() + "\n");
		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String arduibnoidefolder =store.getString(PreferenceConstants.P_STRING);
		String libraryfolder =store.getString(PreferenceConstants.T_STRING);
		//TODO Check if folder exist ?
		CGenerator.compileAndRunArduino(thingmlModel,
				arduibnoidefolder,
				libraryfolder,false);
		ThingMLConsole.getInstance().printDebug("Done.\n");
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
