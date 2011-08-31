/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml;

/**
 * A token that is returned by ITextLexer instances. Each token has a name, a
 * length and is found at a certain offset in a document.
 */
public interface IThingmlTextToken {
	
	/**
	 * Returns the name (i.e., the type) of this token.
	 */
	public String getName();
	
	/**
	 * Returns the offset at which the token was found in the document.
	 */
	public int getOffset();
	
	/**
	 * Returns the length of this token.
	 */
	public int getLength();
	
	/**
	 * Returns the line this token was found at.
	 */
	public int getLine();
	
	/**
	 * Returns the column this token was found at.
	 */
	public int getColumn();
	
	/**
	 * Returns false if the token is not usable for syntax highlighting. The EOF (End
	 * of File) token is an example for such a token.
	 */
	public boolean canBeUsedForSyntaxHighlighting();
	
	/**
	 * Returns the text of this token
	 */
	public String getText();
	
}
