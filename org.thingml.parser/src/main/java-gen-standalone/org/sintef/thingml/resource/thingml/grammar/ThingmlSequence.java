/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.grammar;

public class ThingmlSequence extends org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement {
	
	public ThingmlSequence(org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality, org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement... elements) {
		super(cardinality, elements);
	}
	
	public String toString() {
		return org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.explode(getChildren(), " ");
	}
	
}
