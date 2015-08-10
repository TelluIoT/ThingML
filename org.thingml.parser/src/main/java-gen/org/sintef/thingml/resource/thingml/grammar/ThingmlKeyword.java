/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.grammar;

/**
 * A class to represent a keyword in the grammar.
 */
public class ThingmlKeyword extends org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement {
	
	private final String value;
	
	public ThingmlKeyword(String value, org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality) {
		super(cardinality, null);
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return value;
	}
	
}
