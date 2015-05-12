/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.mopp;

/**
 * Abstract super class for all expected elements. Provides methods to add
 * followers.
 */
public abstract class ThingmlAbstractExpectedElement implements org.sintef.thingml.resource.thingml.IThingmlExpectedElement {
	
	private org.eclipse.emf.ecore.EClass ruleMetaclass;
	
	private java.util.Set<org.sintef.thingml.resource.thingml.util.ThingmlPair<org.sintef.thingml.resource.thingml.IThingmlExpectedElement, org.sintef.thingml.resource.thingml.mopp.ThingmlContainedFeature[]>> followers = new java.util.LinkedHashSet<org.sintef.thingml.resource.thingml.util.ThingmlPair<org.sintef.thingml.resource.thingml.IThingmlExpectedElement, org.sintef.thingml.resource.thingml.mopp.ThingmlContainedFeature[]>>();
	
	public ThingmlAbstractExpectedElement(org.eclipse.emf.ecore.EClass ruleMetaclass) {
		super();
		this.ruleMetaclass = ruleMetaclass;
	}
	
	public org.eclipse.emf.ecore.EClass getRuleMetaclass() {
		return ruleMetaclass;
	}
	
	public void addFollower(org.sintef.thingml.resource.thingml.IThingmlExpectedElement follower, org.sintef.thingml.resource.thingml.mopp.ThingmlContainedFeature[] path) {
		followers.add(new org.sintef.thingml.resource.thingml.util.ThingmlPair<org.sintef.thingml.resource.thingml.IThingmlExpectedElement, org.sintef.thingml.resource.thingml.mopp.ThingmlContainedFeature[]>(follower, path));
	}
	
	public java.util.Collection<org.sintef.thingml.resource.thingml.util.ThingmlPair<org.sintef.thingml.resource.thingml.IThingmlExpectedElement, org.sintef.thingml.resource.thingml.mopp.ThingmlContainedFeature[]>> getFollowers() {
		return followers;
	}
	
}
