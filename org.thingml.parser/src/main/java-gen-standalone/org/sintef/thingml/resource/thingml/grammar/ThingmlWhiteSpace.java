/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.grammar;

public class ThingmlWhiteSpace extends org.sintef.thingml.resource.thingml.grammar.ThingmlFormattingElement {
	
	private final int amount;
	
	public ThingmlWhiteSpace(int amount, org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality) {
		super(cardinality);
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public String toString() {
		return "#" + getAmount();
	}
	
}
