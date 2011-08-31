/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlAntlrScanner implements org.sintef.thingml.resource.thingml.IThingmlTextScanner {
	
	private org.antlr.runtime3_3_0.Lexer antlrLexer;
	
	public ThingmlAntlrScanner(org.antlr.runtime3_3_0.Lexer antlrLexer) {
		this.antlrLexer = antlrLexer;
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTextToken getNextToken() {
		if (antlrLexer.getCharStream() == null) {
			return null;
		}
		final org.antlr.runtime3_3_0.Token current = antlrLexer.nextToken();
		if (current == null || current.getType() < 0) {
			return null;
		}
		org.sintef.thingml.resource.thingml.IThingmlTextToken result = new org.sintef.thingml.resource.thingml.mopp.ThingmlTextToken(current);
		return result;
	}
	
	public void setText(String text) {
		antlrLexer.setCharStream(new org.antlr.runtime3_3_0.ANTLRStringStream(text));
	}
	
}
