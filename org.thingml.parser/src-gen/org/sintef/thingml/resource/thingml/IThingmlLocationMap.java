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
 * A LocationMap maps EObjects to the position of their textual representations.
 * For each org.eclipse.emf.ecore.EObject the map contains information about the
 * line, the column, the character position where the object begins and the
 * character position where the object ends.
 */
public interface IThingmlLocationMap {
	
	/**
	 * Used by parsers to set location information.
	 */
	public void setLine(org.eclipse.emf.ecore.EObject element, int line);
	
	/**
	 * Returns the line where the given element starts.
	 */
	public int getLine(org.eclipse.emf.ecore.EObject element);
	
	/**
	 * Used by parsers to set location information.
	 */
	public void setColumn(org.eclipse.emf.ecore.EObject element, int column);
	
	/**
	 * Returns the column where the given element starts.
	 */
	public int getColumn(org.eclipse.emf.ecore.EObject element);
	
	/**
	 * Used by parsers to set location information.
	 */
	public void setCharStart(org.eclipse.emf.ecore.EObject element, int charStart);
	
	/**
	 * Returns the character position where the given element starts.
	 */
	public int getCharStart(org.eclipse.emf.ecore.EObject element);
	
	/**
	 * Used by parsers to set location information.
	 */
	public void setCharEnd(org.eclipse.emf.ecore.EObject element, int charEnd);
	
	/**
	 * Returns the character position where the given element ends.
	 */
	public int getCharEnd(org.eclipse.emf.ecore.EObject element);
	
	/**
	 * Returns all EObjects that are located at the given offset in the text document.
	 * This method can return multiple elements, because containers include their
	 * children in the textual representation. The child elements are returned at the
	 * head of the result list.
	 * 
	 * @param documentOffset
	 *  the offset where to search for elements
	 * 
	 * @return a list of elements located at the given offset
	 */
	public java.util.List<org.eclipse.emf.ecore.EObject> getElementsAt(int documentOffset);
	
	/**
	 * Returns all EObjects that are located between the given offsets in the text
	 * document. The method can return multiple elements. This no guarantee about the
	 * order of the elements returned by this method. Even parsing the same document
	 * twice may yield a different order of elements.
	 * 
	 * @param startOffset
	 * @param endOffset
	 * 
	 * @return a list of elements located between the given offsets
	 */
	public java.util.List<org.eclipse.emf.ecore.EObject> getElementsBetween(int startOffset, int endOffset);
	
}
