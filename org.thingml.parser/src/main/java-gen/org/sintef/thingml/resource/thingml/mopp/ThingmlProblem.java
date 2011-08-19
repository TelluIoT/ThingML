/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlProblem implements org.sintef.thingml.resource.thingml.IThingmlProblem {
	
	private String message;
	private org.sintef.thingml.resource.thingml.ThingmlEProblemType type;
	private org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity severity;
	private java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlQuickFix> quickFixes;
	
	public ThingmlProblem(String message, org.sintef.thingml.resource.thingml.ThingmlEProblemType type, org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity severity) {
		this(message, type, severity, java.util.Collections.<org.sintef.thingml.resource.thingml.IThingmlQuickFix>emptySet());
	}
	
	public ThingmlProblem(String message, org.sintef.thingml.resource.thingml.ThingmlEProblemType type, org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity severity, org.sintef.thingml.resource.thingml.IThingmlQuickFix quickFix) {
		this(message, type, severity, java.util.Collections.singleton(quickFix));
	}
	
	public ThingmlProblem(String message, org.sintef.thingml.resource.thingml.ThingmlEProblemType type, org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity severity, java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlQuickFix> quickFixes) {
		super();
		this.message = message;
		this.type = type;
		this.severity = severity;
		this.quickFixes = new java.util.LinkedHashSet<org.sintef.thingml.resource.thingml.IThingmlQuickFix>();
		this.quickFixes.addAll(quickFixes);
	}
	
	public org.sintef.thingml.resource.thingml.ThingmlEProblemType getType() {
		return type;
	}
	
	public org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity getSeverity() {
		return severity;
	}
	
	public String getMessage() {
		return message;
	}
	
	public java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlQuickFix> getQuickFixes() {
		return quickFixes;
	}
	
}
