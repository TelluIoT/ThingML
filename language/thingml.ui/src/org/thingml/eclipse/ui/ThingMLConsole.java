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
package org.thingml.eclipse.ui;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;

public class ThingMLConsole {

	private static ThingMLConsole instance;
	
	private static Color COLOR = new Color(new Device() {
		
		@Override
		public boolean isAutoScalable() {
			return false;
		}
		
		@Override
		public long internal_new_GC(GCData data) {
			return 0;
		}
		
		@Override
		public void internal_dispose_GC(long hDC, GCData data) {				
		}
	}, new RGB(0, 0, 0));
	
	public static ThingMLConsole getInstance() {
		if (instance == null) {
			instance = new ThingMLConsole();
		}
		return instance;
	}
	
	private static Color getOutputColor() {
		Display display = Display.getCurrent();
		if (display != null)
			return display.getSystemColor(SWT.COLOR_BLUE);
		return COLOR;
	}
	
	private static Color getDebugColor() {
		Display display = Display.getCurrent();
		if(display != null)
			return display.getSystemColor(SWT.COLOR_BLACK);
		return COLOR;
	}
	
	private static Color getErrorColor() {
		Display display = Display.getCurrent();
		if (display != null)
			return display.getSystemColor(SWT.COLOR_RED);
		return COLOR;
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
	
	//FIXME: move that into a FileHelper class
	public void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}
	
	//FIXME: move that into a FileHelper class
	public void emptyFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                emptyFolder(f);
	            } else {
	            	f.delete();
	            }
	        }
	    }
	}
	
}
