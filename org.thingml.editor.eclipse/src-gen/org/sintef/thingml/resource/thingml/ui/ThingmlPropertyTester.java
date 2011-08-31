/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
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
package org.sintef.thingml.resource.thingml.ui;

public class ThingmlPropertyTester extends org.eclipse.core.expressions.PropertyTester {
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof org.eclipse.core.resources.IResource) {
			org.eclipse.core.resources.IResource resource = (org.eclipse.core.resources.IResource) receiver;
			return hasMatchingURI(resource);
		} else if (receiver instanceof org.eclipse.ui.part.FileEditorInput) {
			org.eclipse.ui.part.FileEditorInput editorInput = (org.eclipse.ui.part.FileEditorInput) receiver;
			org.eclipse.core.resources.IFile file = editorInput.getFile();
			return hasMatchingURI(file);
		}
		return false;
	}
	
	private boolean hasMatchingURI(org.eclipse.core.resources.IResource resource) {
		String path = resource.getLocationURI().getPath();
		return path.endsWith("." + new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().getSyntaxName());
	}
	
}
