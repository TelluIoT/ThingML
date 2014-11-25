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
package org.thingml.eclipse.ui;


import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;

public class ThingMLConsole {

	private static ThingMLConsole instance;
	
	public static ThingMLConsole getInstance() {
		if (instance == null) {
			instance = new ThingMLConsole();
		}
		return instance;
	}
	
	private static Color getOutputColor() {
		Display display = Display.getCurrent();
		return display.getSystemColor(SWT.COLOR_BLUE);
	}
	
	private static Color getDebugColor() {
		Display display = Display.getCurrent();
		return display.getSystemColor(SWT.COLOR_BLACK);
	}
	
	private static Color getErrorColor() {
		Display display = Display.getCurrent();
		return display.getSystemColor(SWT.COLOR_RED);
	}
	
	private IOConsoleOutputStream dbg;
	private IOConsoleOutputStream msg;
	private IOConsoleOutputStream err; 
	
	private ThingMLConsole() {
		IOConsole console = new IOConsole("[ThingML]", null);
		dbg = console.newOutputStream();
		msg = console.newOutputStream();
		err = console.newOutputStream();
		dbg.setColor(getDebugColor());
		msg.setColor(getOutputColor());
		err.setColor(getErrorColor());
		
		console.activate();
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IOConsole[]{console});
		
	}
	
	
	public OutputStream getMessageSteam() {
		return msg;
	}
	
	public OutputStream getDebugSteam() {
		return dbg;
	}
	
	public OutputStream getErrorSteam() {
		return err;
	}
	
	
	public void printError(String txt) {
		try {
			err.write(txt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printMessage(String txt) {
		try {
			msg.write(txt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printDebug(String txt) {
		try {
			dbg.write(txt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
