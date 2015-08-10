/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.grammar;

/**
 * A class to represent placeholders in a grammar.
 */
public class ThingmlPlaceholder extends org.sintef.thingml.resource.thingml.grammar.ThingmlTerminal {
	
	private final String tokenName;
	
	public ThingmlPlaceholder(org.eclipse.emf.ecore.EStructuralFeature feature, String tokenName, org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality, int mandatoryOccurencesAfter) {
		super(feature, cardinality, mandatoryOccurencesAfter);
		this.tokenName = tokenName;
	}
	
	public String getTokenName() {
		return tokenName;
	}
	
}
