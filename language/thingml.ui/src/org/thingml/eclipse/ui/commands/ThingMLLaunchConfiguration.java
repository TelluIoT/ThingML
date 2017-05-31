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
