/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.debug;

public class ThingmlSourceLookupParticipant extends org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant {
	
	public String getSourceName(Object object) throws org.eclipse.core.runtime.CoreException {
		if (object instanceof org.sintef.thingml.resource.thingml.debug.ThingmlStackFrame) {
			org.sintef.thingml.resource.thingml.debug.ThingmlStackFrame frame = (org.sintef.thingml.resource.thingml.debug.ThingmlStackFrame) object;
			return frame.getResourceURI();
		}
		return null;
	}
	
}
