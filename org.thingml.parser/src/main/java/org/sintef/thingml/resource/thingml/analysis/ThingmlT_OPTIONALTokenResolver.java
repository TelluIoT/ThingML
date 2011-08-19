/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.analysis;

public class ThingmlT_OPTIONALTokenResolver implements org.sintef.thingml.resource.thingml.IThingmlTokenResolver {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultTokenResolver defaultTokenResolver = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultTokenResolver();
	
	public String deResolve(Object value, org.eclipse.emf.ecore.EStructuralFeature feature, org.eclipse.emf.ecore.EObject container) {
		java.lang.String result = "";
		if (value.toString().equals("true")) {
			result = "optional ";
		}
		return result;
	}
	
	public void resolve(String lexem, org.eclipse.emf.ecore.EStructuralFeature feature, org.sintef.thingml.resource.thingml.IThingmlTokenResolveResult result) {
		defaultTokenResolver.resolve(lexem, feature, result);
		result.setResolvedToken(lexem.equals("optional"));
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		defaultTokenResolver.setOptions(options);
	}
	
}
