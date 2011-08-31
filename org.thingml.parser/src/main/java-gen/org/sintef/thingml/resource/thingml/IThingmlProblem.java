/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml;

public interface IThingmlProblem {
	public String getMessage();
	public org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity getSeverity();
	public org.sintef.thingml.resource.thingml.ThingmlEProblemType getType();
	public java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlQuickFix> getQuickFixes();
}
