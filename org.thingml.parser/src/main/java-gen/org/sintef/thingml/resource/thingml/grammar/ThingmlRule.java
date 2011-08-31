/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.grammar;

/**
 * A class to represent a rules in the grammar.
 */
public class ThingmlRule extends org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement {
	
	private final org.eclipse.emf.ecore.EClass metaclass;
	
	public ThingmlRule(org.eclipse.emf.ecore.EClass metaclass, org.sintef.thingml.resource.thingml.grammar.ThingmlChoice choice, org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality) {
		super(cardinality, new org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement[] {choice});
		this.metaclass = metaclass;
	}
	
	public org.eclipse.emf.ecore.EClass getMetaclass() {
		return metaclass;
	}
	
	public org.sintef.thingml.resource.thingml.grammar.ThingmlChoice getDefinition() {
		return (org.sintef.thingml.resource.thingml.grammar.ThingmlChoice) getChildren()[0];
	}
	
}

