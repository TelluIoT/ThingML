/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.grammar;

public class ThingmlContainment extends org.sintef.thingml.resource.thingml.grammar.ThingmlTerminal {
	
	public ThingmlContainment(org.eclipse.emf.ecore.EStructuralFeature feature, org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality, int mandatoryOccurencesAfter) {
		super(feature, cardinality, mandatoryOccurencesAfter);
	}
	
	public String toString() {
		return getFeature().getName();
	}
	
}
