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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IDynamicVariable;
import org.eclipse.core.variables.IDynamicVariableResolver;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;

public class VariableResolver implements IDynamicVariableResolver {
	
	private static ILaunchConfiguration currentLaunchConfiguration = null;
	
	public static synchronized String resolveExpressionForLaunchConfiguration(String expression, ILaunchConfiguration configuration) throws CoreException {
		currentLaunchConfiguration = configuration;
		String substituted = VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(expression);
		currentLaunchConfiguration = null;
		return substituted;
	}

	@Override
	public String resolveValue(IDynamicVariable variable, String argument) throws CoreException {
		if (variable.getName().equals("thingml.compiler")) {
			return currentLaunchConfiguration.getAttribute("org.thingml.launchconfig.compiler", "");
		}
		if (variable.getName().equals("thingml.model")) {
			IResource[] resources = currentLaunchConfiguration.getMappedResources();
			if (resources != null && resources.length == 1 && resources[0] instanceof IFile) {
				IFile file = (IFile)resources[0];
				return file.getFullPath().toString();
			}
		}
		return null;
	}

}
