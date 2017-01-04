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
package org.sintef.thingml.resource.thingml;

/**
 * An element that is expected at a given position in a resource stream.
 */
public interface IThingmlExpectedElement {
	
	/**
	 * Returns the names of all tokens that are expected at the given position.
	 */
	public java.util.Set<String> getTokenNames();
	
	/**
	 * Returns the metaclass of the rule that contains the expected element.
	 */
	public org.eclipse.emf.ecore.EClass getRuleMetaclass();
	
	/**
	 * Returns the syntax element that is expected.
	 */
	public org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement getSymtaxElement();
	
	/**
	 * Adds an element that is a valid follower for this element.
	 */
	public void addFollower(org.sintef.thingml.resource.thingml.IThingmlExpectedElement follower, org.sintef.thingml.resource.thingml.mopp.ThingmlContainedFeature[] path);
	
	/**
	 * Returns all valid followers for this element. Each follower is represented by a
	 * pair of an expected elements and the containment trace that leads from the
	 * current element to the follower.
	 */
	public java.util.Collection<org.sintef.thingml.resource.thingml.util.ThingmlPair<org.sintef.thingml.resource.thingml.IThingmlExpectedElement, org.sintef.thingml.resource.thingml.mopp.ThingmlContainedFeature[]>> getFollowers();
	
}
