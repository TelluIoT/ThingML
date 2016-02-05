/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlBracketInformationProvider {
	
	public class BracketPair implements org.sintef.thingml.resource.thingml.IThingmlBracketPair {
		
		private String opening;
		private String closing;
		private boolean closingEnabledInside;
		
		public BracketPair(String opening, String closing, boolean closingEnabledInside) {
			super();
			this.opening = opening;
			this.closing = closing;
			this.closingEnabledInside = closingEnabledInside;
		}
		
		public String getOpeningBracket() {
			return opening;
		}
		
		public String getClosingBracket() {
			return closing;
		}
		
		public boolean isClosingEnabledInside() {
			return closingEnabledInside;
		}
	}
	
	public java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlBracketPair> getBracketPairs() {
		java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlBracketPair> result = new java.util.ArrayList<org.sintef.thingml.resource.thingml.IThingmlBracketPair>();
		result.add(new BracketPair("(", ")", true));
		result.add(new BracketPair("{", "}", true));
		result.add(new BracketPair("<", ">", true));
		result.add(new BracketPair("[", "]", true));
		return result;
	}
	
}
