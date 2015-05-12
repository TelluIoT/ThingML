/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.grammar;

public class ThingmlCompound extends org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement {
	
	public ThingmlCompound(org.sintef.thingml.resource.thingml.grammar.ThingmlChoice choice, org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality) {
		super(cardinality, new org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement[] {choice});
	}
	
	public String toString() {
		return "(" + getChildren()[0] + ")";
	}
	
}
