/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.analysis;

import java.util.ArrayList;

import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.Type;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class TypedElementTypeReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.TypedElement, org.sintef.thingml.Type> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.TypedElement, org.sintef.thingml.Type> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.TypedElement, org.sintef.thingml.Type>();
	
	public void resolve(String identifier, org.sintef.thingml.TypedElement container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.Type> result) {
		ThingMLModel root = ThingMLHelpers.findContainingModel(container);
		ArrayList<Type> ts = ThingMLHelpers.findSimpleType(root, identifier, resolveFuzzy);
		for (Type t : ts) {
			if (resolveFuzzy) result.addMapping(t.getName(), t);
			else if (t.getName().equals(identifier)) {
				result.addMapping(t.getName(), t);
			}
		}
		if(!result.wasResolved()) result.setErrorMessage("Cannot resolve type " + identifier);
	}
	
	public String deResolve(org.sintef.thingml.Type element, org.sintef.thingml.TypedElement container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
