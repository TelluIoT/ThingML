/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.analysis;

import org.sintef.thingml.ConfigInclude;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Message;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class InstanceRefConfigReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.InstanceRef, org.sintef.thingml.ConfigInclude> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.InstanceRef, org.sintef.thingml.ConfigInclude> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.InstanceRef, org.sintef.thingml.ConfigInclude>();
	
	public void resolve(String identifier, org.sintef.thingml.InstanceRef container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.ConfigInclude> result) {
		//delegate.resolve(identifier, container, reference, position, resolveFuzzy, result);
		
		// In which configuration should we look:
		Configuration cfg = ThingMLHelpers.findContainingConfiguration(container);
		
		for(ConfigInclude ci : container.getConfig()) {
			if (ci.getConfig() != null) cfg = ci.getConfig();
		}
		
		for (ConfigInclude ci : cfg.getConfigs()) {
			if (ci.getName().startsWith(identifier)) {
				if (resolveFuzzy) result.addMapping(ci.getName(), ci);
				else if (ci.getName().equals(identifier))
					result.addMapping(ci.getName(), ci);
			}
		}
		if(!result.wasResolved()) result.setErrorMessage("Cannot resolve group " + identifier + " in configuration " + cfg.getName()); 
		
	}
	
	public String deResolve(org.sintef.thingml.ConfigInclude element, org.sintef.thingml.InstanceRef container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
