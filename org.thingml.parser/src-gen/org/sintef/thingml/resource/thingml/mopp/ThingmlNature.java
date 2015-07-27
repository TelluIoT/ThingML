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
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlNature implements org.eclipse.core.resources.IProjectNature {
	
	public static final String NATURE_ID = "org.sintef.thingml.resource.thingml.nature";
	
	private org.eclipse.core.resources.IProject project;
	
	/**
	 * the IDs of all builders, IDs of additional builders can be added here
	 */
	public final static String[] BUILDER_IDS = {org.sintef.thingml.resource.thingml.mopp.ThingmlBuilderAdapter.BUILDER_ID};
	
	public static void activate(org.eclipse.core.resources.IProject project) {
		try {
			org.eclipse.core.resources.IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			
			for (int i = 0; i < natures.length; ++i) {
				if (NATURE_ID.equals(natures[i])) {
					// already active
					return;
				}
			}
			// Add the nature
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		} catch (org.eclipse.core.runtime.CoreException e) {
		}
	}
	
	public static void deactivate(org.eclipse.core.resources.IProject project) {
		try {
			org.eclipse.core.resources.IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			
			for (int i = 0; i < natures.length; ++i) {
				if (NATURE_ID.equals(natures[i])) {
					// Remove the nature
					String[] newNatures = new String[natures.length - 1];
					System.arraycopy(natures, 0, newNatures, 0, i);
					System.arraycopy(natures, i + 1, newNatures, i, natures.length - i - 1);
					description.setNatureIds(newNatures);
					project.setDescription(description, null);
					return;
				}
			}
		} catch (org.eclipse.core.runtime.CoreException e) {
		}
	}
	
	public static boolean hasNature(org.eclipse.core.resources.IProject project) {
		try {
			org.eclipse.core.resources.IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			for (int i = 0; i < natures.length; ++i) {
				if (NATURE_ID.equals(natures[i])) {
					return true;
				}
			}
		} catch (org.eclipse.core.runtime.CoreException e) {
		}
		return false;
	}
	
	public void configure() throws org.eclipse.core.runtime.CoreException {
		org.eclipse.core.resources.IProjectDescription desc = project.getDescription();
		org.eclipse.core.resources.ICommand[] commands = desc.getBuildSpec();
		
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(org.sintef.thingml.resource.thingml.mopp.ThingmlBuilderAdapter.BUILDER_ID)) {
				return;
			}
		}
		org.eclipse.core.resources.ICommand[] newCommands = commands;
		outer: for (int j = 0; j < BUILDER_IDS.length; j++) {
			for (int i = 0; i < commands.length; ++i) {
				if (commands[i].getBuilderName().equals(BUILDER_IDS[j])) {
					continue outer;
				}
			}
			org.eclipse.core.resources.ICommand[] tempCommands = new org.eclipse.core.resources.ICommand[newCommands.length + 1];
			System.arraycopy(newCommands, 0, tempCommands, 0, newCommands.length);
			org.eclipse.core.resources.ICommand command = desc.newCommand();
			command.setBuilderName(BUILDER_IDS[j]);
			tempCommands[tempCommands.length - 1] = command;
			newCommands = tempCommands;
		}
		if (newCommands != commands) {
			desc.setBuildSpec(newCommands);
			project.setDescription(desc, null);
		}
	}
	
	public void deconfigure() throws org.eclipse.core.runtime.CoreException {
		org.eclipse.core.resources.IProjectDescription description = getProject().getDescription();
		org.eclipse.core.resources.ICommand[] commands = description.getBuildSpec();
		org.eclipse.core.resources.ICommand[] newCommands = commands;
		for (int j = 0; j < BUILDER_IDS.length; j++) {
			for (int i = 0; i < newCommands.length; ++i) {
				if (newCommands[i].getBuilderName().equals(BUILDER_IDS[j])) {
					org.eclipse.core.resources.ICommand[] tempCommands = new org.eclipse.core.resources.ICommand[newCommands.length - 1];
					System.arraycopy(newCommands, 0, tempCommands, 0, i);
					System.arraycopy(newCommands, i + 1, tempCommands, i, newCommands.length - i - 1);
					newCommands = tempCommands;
					break;
				}
			}
		}
		if (newCommands != commands) {
			description.setBuildSpec(newCommands);
		}
	}
	
	public org.eclipse.core.resources.IProject getProject() {
		return project;
	}
	
	public void setProject(org.eclipse.core.resources.IProject project) {
		this.project = project;
	}
	
}
