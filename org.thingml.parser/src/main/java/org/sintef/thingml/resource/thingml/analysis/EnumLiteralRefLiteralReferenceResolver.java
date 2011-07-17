/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.analysis;

import java.util.ArrayList;

import org.sintef.thingml.Configuration;
import org.sintef.thingml.EnumerationLiteral;
import org.sintef.thingml.Instance;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class EnumLiteralRefLiteralReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.EnumLiteralRef, org.sintef.thingml.EnumerationLiteral> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.EnumLiteralRef, org.sintef.thingml.EnumerationLiteral> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.EnumLiteralRef, org.sintef.thingml.EnumerationLiteral>();
	
	public void resolve(String identifier, org.sintef.thingml.EnumLiteralRef container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.EnumerationLiteral> result) {
		ArrayList<EnumerationLiteral> ts = ThingMLHelpers.findEnumerationLiteral(container.getEnum(), identifier, resolveFuzzy);
		for (EnumerationLiteral t : ts) result.addMapping(t.getName(), t);
		if(!result.wasResolved()) result.setErrorMessage("Cannot resolve enumeration literal " + identifier);
	}
	
	public String deResolve(org.sintef.thingml.EnumerationLiteral element, org.sintef.thingml.EnumLiteralRef container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
