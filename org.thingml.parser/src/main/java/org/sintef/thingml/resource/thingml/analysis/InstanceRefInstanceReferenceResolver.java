/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.analysis;

import org.sintef.thingml.Configuration;
import org.sintef.thingml.Instance;
import org.sintef.thingml.constraints.ThingMLHelpers;

public class InstanceRefInstanceReferenceResolver implements org.sintef.thingml.resource.thingml.IThingmlReferenceResolver<org.sintef.thingml.InstanceRef, org.sintef.thingml.Instance> {
	
	private org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.InstanceRef, org.sintef.thingml.Instance> delegate = new org.sintef.thingml.resource.thingml.analysis.ThingmlDefaultResolverDelegate<org.sintef.thingml.InstanceRef, org.sintef.thingml.Instance>();
	
	public void resolve(String identifier, org.sintef.thingml.InstanceRef container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, final org.sintef.thingml.resource.thingml.IThingmlReferenceResolveResult<org.sintef.thingml.Instance> result) {
		
		// In which configuration should we look:
		Configuration cfg = ThingMLHelpers.findContainingConfiguration(container);
		if (container.getConfig().size() > 0) cfg = container.getConfig().get(container.getConfig().size() -1).getConfig();
		
		for (Instance ci : cfg.getInstances()) {
			if (ci.getName().startsWith(identifier)) {
				if (resolveFuzzy) result.addMapping(ci.getName(), ci);
				else if (ci.getName().equals(identifier))
					result.addMapping(ci.getName(), ci);
			}
		}
		if(!result.wasResolved()) result.setErrorMessage("Cannot resolve instance " + identifier + " in configuration " + cfg.getName()); 
	}
	
	public String deResolve(org.sintef.thingml.Instance element, org.sintef.thingml.InstanceRef container, org.eclipse.emf.ecore.EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
