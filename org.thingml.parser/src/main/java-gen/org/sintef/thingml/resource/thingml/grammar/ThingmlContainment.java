/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.grammar;

public class ThingmlContainment extends org.sintef.thingml.resource.thingml.grammar.ThingmlTerminal {
	
	private final org.eclipse.emf.ecore.EClass[] allowedTypes;
	
	public ThingmlContainment(org.eclipse.emf.ecore.EStructuralFeature feature, org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality, org.eclipse.emf.ecore.EClass[] allowedTypes, int mandatoryOccurencesAfter) {
		super(feature, cardinality, mandatoryOccurencesAfter);
		this.allowedTypes = allowedTypes;
	}
	
	public org.eclipse.emf.ecore.EClass[] getAllowedTypes() {
		return allowedTypes;
	}
	
	public String toString() {
		String typeRestrictions = null;
		if (allowedTypes != null && allowedTypes.length > 0) {
			typeRestrictions = org.sintef.thingml.resource.thingml.util.ThingmlStringUtil.explode(allowedTypes, ", ", new org.sintef.thingml.resource.thingml.IThingmlFunction1<String, org.eclipse.emf.ecore.EClass>() {
				public String execute(org.eclipse.emf.ecore.EClass eClass) {
					return eClass.getName();
				}
			});
		}
		return getFeature().getName() + (typeRestrictions == null ? "" : "[" + typeRestrictions + "]");
	}
	
}
