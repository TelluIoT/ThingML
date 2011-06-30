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
/**
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
package org.sintef.thingml.resource.thingml;

public interface IThingmlQuickFix {
	
	/**
	 * Returns a string that briefly describes the quick fix.
	 * 
	 * @return brief description to display
	 */
	public String getDisplayString();
	
	/**
	 * Returns an image key that references an image for the quick fix. This key will
	 * be passed to the ImageProvider class of the resource UI plug-in to obtain the
	 * image.
	 * 
	 * @return key of the image to display
	 */
	public String getImageKey();
	
	/**
	 * Applies the fix and returns the new text for the resource. If the fix does not
	 * change the current resource, but others, null must be returned.
	 */
	public String apply(String currentText);
	
	/**
	 * Returns a collection of objects the fix refers to. This collection is used to
	 * check whether the fix is can still be applied even after a workbench restart.
	 */
	public java.util.Collection<org.eclipse.emf.ecore.EObject> getContextObjects();
	
	/**
	 * Returns a string representation of the context in which this quick fix can be
	 * used. Typically this is a list of the URIs of the EObjects the fix applies to
	 * concatenated with the type of the quick fix. This context string is used to
	 * find quick fixes when the quick fix wizard is invoked from the problems view.
	 * The context string must stay the same when resources are (re)loaded from disc.
	 * This is required to find quick fixes that have been created before (i.e., the
	 * last time the resource was loaded).
	 */
	public String getContextAsString();
	
}
