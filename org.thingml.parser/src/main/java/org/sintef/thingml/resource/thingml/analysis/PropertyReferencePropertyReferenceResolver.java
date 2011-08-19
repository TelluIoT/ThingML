/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.analysis;

import java.util.ArrayList;

import org.sintef.thingml.ThingMLElement;
import org.sintef.thingml.Variable;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class PropertyReferencePropertyReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.PropertyReference, org.sintef.thingml.Variable> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.PropertyReference, org.sintef.thingml.Variable> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.PropertyReference, org.sintef.thingml.Variable>();
	
	public void resolve(String identifier, org.sintef.thingml.PropertyReference container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.Variable> result) {

		ArrayList<Variable> ps = ThingMLHelpers.findVisibleVariables(container, identifier, resolveFuzzy);
		for(Variable p : ps) {
			result.addMapping(p.getName(), p);
		}
		
		if (!result.wasResolved())
			result.setErrorMessage("Cannot resolve variable " + identifier);
	}
	
	public String deResolve(org.sintef.thingml.Variable element, org.sintef.thingml.PropertyReference container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
