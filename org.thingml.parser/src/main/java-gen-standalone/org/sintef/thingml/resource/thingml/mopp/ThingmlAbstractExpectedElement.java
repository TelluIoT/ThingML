/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
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
