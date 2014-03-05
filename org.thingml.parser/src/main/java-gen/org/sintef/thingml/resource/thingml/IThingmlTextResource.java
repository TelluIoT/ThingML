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
package org.sintef.thingml.resource.thingml;

/**
 * An extended resource that can hold information about the exact positions of
 * each element of its content in a text file. This can be used to give more
 * detailed error feedback.
 */
public interface IThingmlTextResource extends org.eclipse.emf.ecore.resource.Resource, org.sintef.thingml.resource.thingml.IThingmlTextResourcePluginPart {
	
	/**
	 * Returns the raw contents of this resource. This method must be used by
	 * generated classes only. It is not intended to be used by clients.
	 */
	public org.eclipse.emf.common.util.EList<org.eclipse.emf.ecore.EObject> getContentsInternal();
	
	/**
	 * Try to load the content of this resource from the given stream. If loading
	 * fails, the state of this resource is kept. If loading is successful, the
	 * content of this resource is replaced with the new content.
	 * This method can be used to try loading erroneous files, as e.g., needed during
	 * background parsing in the editor.
	 * 
	 * @param stream the stream to read from
	 * @param options the load options to use
	 * 
	 * @throws java.io.IOException thrown if the stream can not be read
	 */
	public void reload(java.io.InputStream stream, java.util.Map<?,?> options) throws java.io.IOException;
	
	/**
	 * Try to cancel a current reload of this resource. It is not guaranteed that
	 * canceling is successful. If this resource has already finished parsing the new
	 * content, it will replace its content unconditionally.
	 */
	public void cancelReload();
	
	/**
	 * Returns a map containing information about the location of model elements in
	 * the text.
	 * 
	 * @return the model element to text location mapping
	 */
	public org.sintef.thingml.resource.thingml.IThingmlLocationMap getLocationMap();
	
	/**
	 * Adds an error that should be displayed at the position of the given element.
	 */
	public void addProblem(org.sintef.thingml.resource.thingml.IThingmlProblem problem, org.eclipse.emf.ecore.EObject element);
	
	/**
	 * Adds an error to be displayed at the indicated position.
	 */
	public void addProblem(org.sintef.thingml.resource.thingml.IThingmlProblem problem, int column, int line, int charStart, int charEnd);
	
	/**
	 * Internal method used by the parser to register a context dependent proxy object
	 * for later resolution.
	 * 
	 * @param container
	 * @param reference
	 * @param position
	 * @param id
	 * @param proxyElement
	 */
	public <ContainerType extends org.eclipse.emf.ecore.EObject, ReferenceType extends org.eclipse.emf.ecore.EObject> void registerContextDependentProxy(org.sintef.thingml.resource.thingml.IThingmlContextDependentURIFragmentFactory<ContainerType, ReferenceType> factory, ContainerType container, org.eclipse.emf.ecore.EReference reference, String id, org.eclipse.emf.ecore.EObject proxyElement, int position);
	
	/**
	 * Attaches a warning with the given message to object 'cause'.
	 */
	public void addWarning(String message, org.eclipse.emf.ecore.EObject cause);
	
	/**
	 * Attaches an error with the given message to object 'cause'.
	 */
	public void addError(String message, org.eclipse.emf.ecore.EObject cause);
	
	/**
	 * Returns the quick fix for the given context. This method is used by the
	 * MarkerResolutionGenerator to retrieve fixes for problem that are associated
	 * with this resource.
	 */
	public org.sintef.thingml.resource.thingml.IThingmlQuickFix getQuickFix(String quickFixContext);
	
}
