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
		return display.getSystemColor(SWT.COLOR_GRAY);
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
