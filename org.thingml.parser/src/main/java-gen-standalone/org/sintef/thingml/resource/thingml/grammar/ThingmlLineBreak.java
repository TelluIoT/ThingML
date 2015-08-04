/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.grammar;

public class ThingmlLineBreak extends org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement {
	
	private final int tabs;
	
	public ThingmlLineBreak(org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality, int tabs) {
		super(cardinality);
		this.tabs = tabs;
	}
	
	public int getTabs() {
		return tabs;
	}
	
	public String toString() {
		return "!" + getTabs();
	}
	
}
