package org.thingml.eclipse.ui.commands;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;

public class ThingMLLaunchConfiguration extends LaunchConfigurationDelegate {

	final CompileThingFile action = new CompileThingFile();
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		final String compiler = configuration.getAttribute("compiler", "nodejs");
		final String src = configuration.getAttribute("source", "");		
		System.out.println("TODO: Call compiler :-)");				
		try {
			final Map<String, String> parameters = new HashMap<>();;
			parameters.put("org.thingml.eclipse.ui.commandParameterCompilerName", compiler);									
			final ExecutionEvent event = new ExecutionEvent(null, parameters, this, new File(src));
			System.out.println("Context = " + event.getApplicationContext());
			action.execute(event);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
