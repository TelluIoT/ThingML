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
package org.sintef.thingml.resource.thingml.grammar;

/**
 * The abstract super class for all elements of a grammar. This class provides
 * methods to traverse the grammar rules.
 */
public abstract class ThingmlSyntaxElement {
	
	private ThingmlSyntaxElement[] children;
	private ThingmlSyntaxElement parent;
	private org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality;
	
	public ThingmlSyntaxElement(org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality cardinality, ThingmlSyntaxElement[] children) {
		this.cardinality = cardinality;
		this.children = children;
		if (this.children != null) {
			for (ThingmlSyntaxElement child : this.children) {
				child.setParent(this);
			}
		}
	}
	
	/**
	 * Sets the parent of this syntax element. This method must be invoked at most
	 * once.
	 */
	public void setParent(ThingmlSyntaxElement parent) {
		assert this.parent == null;
		this.parent = parent;
	}
	
	/**
	 * Returns the parent of this syntax element. This parent is determined by the
	 * containment hierarchy in the CS model.
	 */
	public ThingmlSyntaxElement getParent() {
		return parent;
	}
	
	public ThingmlSyntaxElement[] getChildren() {
		if (children == null) {
			return new ThingmlSyntaxElement[0];
		}
		return children;
	}
	
	public org.eclipse.emf.ecore.EClass getMetaclass() {
		return parent.getMetaclass();
	}
	
	public org.sintef.thingml.resource.thingml.grammar.ThingmlCardinality getCardinality() {
		return cardinality;
	}
	
}
