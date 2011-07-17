/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.analysis;

import java.util.ArrayList;

import org.sintef.thingml.Enumeration;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class EnumLiteralRefEnumReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.EnumLiteralRef, org.sintef.thingml.Enumeration> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.EnumLiteralRef, org.sintef.thingml.Enumeration> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.EnumLiteralRef, org.sintef.thingml.Enumeration>();
	
	public void resolve(String identifier, org.sintef.thingml.EnumLiteralRef container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.Enumeration> result) {
		ThingMLModel root = ThingMLHelpers.findContainingModel(container);
		ArrayList<Enumeration> ts = ThingMLHelpers.findEnumeration(root, identifier, resolveFuzzy);
		for (Enumeration t : ts) result.addMapping(t.getName(), t);
		if(!result.wasResolved()) result.setErrorMessage("Cannot resolve enumeration " + identifier);
	}
	
	public String deResolve(org.sintef.thingml.Enumeration element, org.sintef.thingml.EnumLiteralRef container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
