/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

/**
 * A helper class to add, get or remove positions with a specific category in a
 * document.
 */
public class ThingmlPositionHelper {
	
	/**
	 * Adds a position with the given offset and length into a document.
	 * 
	 * @param document the document to add a position into
	 * @param category the category of this position
	 * @param offset the offset of the position
	 * @param length the length of the position
	 */
	public void addPosition(org.eclipse.jface.text.IDocument document, String category, int offset, int length) {
		try {
			document.addPositionCategory(category);
			org.eclipse.jface.text.Position position = new org.eclipse.jface.text.Position(offset, length);
			document.addPosition(category, position);
		} catch (org.eclipse.jface.text.BadLocationException e) {
		} catch (org.eclipse.jface.text.BadPositionCategoryException e) {
		}
	}
	
	public org.eclipse.jface.text.Position createPosition(int offset, int length) {
		return new org.eclipse.jface.text.Position(offset, length);
	}
	
	/**
	 * Returns the positions of a specific category of the given document.
	 * 
	 * @param document the document to get the positions from
	 * @param category the position's category
	 * 
	 * @return an array of positions. If there is none return an array with the length
	 * = 0
	 */
	public org.eclipse.jface.text.Position[] getPositions(org.eclipse.jface.text.IDocument document, String category) {
		try {
			return document.getPositions(category);
		} catch (org.eclipse.jface.text.BadPositionCategoryException e) {
		}
		return new org.eclipse.jface.text.Position[0];
	}
	
	/**
	 * Returns the first position of a specific category of the given document.
	 * 
	 * @param document the document to get the positions from
	 * @param category the category of the position
	 * 
	 * @return a position. If there is none return <code>null</code>.
	 */
	public org.eclipse.jface.text.Position getFirstPosition(org.eclipse.jface.text.IDocument document, String category) {
		try {
			org.eclipse.jface.text.Position[] positions = document.getPositions(category);
			if (positions.length > 0) {
				return positions[0];
			}
		} catch (org.eclipse.jface.text.BadPositionCategoryException e) {
		}
		return null;
	}
	
	/**
	 * Deletes the position category from the document. All positions in this category
	 * are thus deleted as well.
	 * 
	 * @param document the document contains the category
	 * @param category the category to be removed
	 */
	public void removePositions(org.eclipse.jface.text.IDocument document, String category) {
		try {
			document.removePositionCategory(category);
		} catch (org.eclipse.jface.text.BadPositionCategoryException e) {
		}
	}
}
