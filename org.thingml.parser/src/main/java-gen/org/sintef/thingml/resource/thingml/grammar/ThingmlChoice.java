/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.grammar;

public class ThingmlChoice extends org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement {
	
	public ThingmlChoice(org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality, org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement... choices) {
		super(cardinality, choices);
	}
	
	public String toString() {
		return org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.explode(getChildren(), "|");
	}
	
}
