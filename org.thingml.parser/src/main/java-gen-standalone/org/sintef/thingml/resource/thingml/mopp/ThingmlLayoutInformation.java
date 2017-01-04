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
 * ThingmlLayoutInformation objects are used to store layout information that is
 * found while parsing text files. Layout information does include all unused
 * tokens. Usually, these are whitespace characters, line breaks and comments, but
 * depending on the concrete syntax definition it can also include other tokens.
 * ThingmlLayoutInformations are aggregated in LayoutInformationAdapters. One
 * ThingmlLayoutInformation contains the layout that was found before a keyword,
 * attribute or reference.
 */
public class ThingmlLayoutInformation {
	
	/**
	 * The element in the grammar that is associated with this layout information.
	 * This can be either an attribute, a non-containment reference (placeholder) or a
	 * terminal (boolean or enumeration).
	 */
	private final org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement syntaxElement;
	
	/**
	 * The offset in the document where this piece of layout was found.
	 */
	private final int startOffset;
	
	/**
	 * Contains a concatenated version of all hidden tokens that were found before
	 * this object.
	 */
	private final String hiddenTokenText;
	
	/**
	 * Contains the visible token that represented this object in its text form.
	 */
	private final String visibleTokenText;
	
	/**
	 * The object the layout information refers to. This can be either the value of an
	 * attribute or a referenced EObject.
	 */
	private Object object;
	
	/**
	 * A flag that is used to remember whether the proxy to which this layout refers
	 * was resolved.
	 */
	private boolean wasResolved;
	
	public ThingmlLayoutInformation(org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement syntaxElement, Object object, int startOffset, String hiddenTokenText, String visibleTokenText) {
		super();
		this.syntaxElement = syntaxElement;
		this.object = object;
		this.startOffset = startOffset;
		this.hiddenTokenText = hiddenTokenText;
		this.visibleTokenText = visibleTokenText;
	}
	
	public org.sintef.thingml.resource.thingml.grammar.ThingmlSyntaxElement getSyntaxElement() {
		return syntaxElement;
	}
	
	public int getStartOffset() {
		return startOffset;
	}
	
	/**
	 * Returns the objects that this layout information adapter refers to. This can be
	 * either the value of an attribute or a referenced object. If the parameter
	 * 'resolve' is set to true and the referenced object is a proxy, this method
	 * tries to resolve the proxy.
	 */
	public Object getObject(org.eclipse.emf.ecore.EObject container, boolean resolve) {
		if (wasResolved || !resolve) {
			return object;
		}
		// we need to try to resolve proxy objects again, because the proxy might have
		// been resolved before this adapter existed, which means we missed the
		// replaceProxy() notification
		if (object instanceof org.eclipse.emf.ecore.InternalEObject) {
			org.eclipse.emf.ecore.InternalEObject internalObject = (org.eclipse.emf.ecore.InternalEObject) object;
			if (internalObject.eIsProxy()) {
				if (container instanceof org.eclipse.emf.ecore.InternalEObject) {
					org.eclipse.emf.ecore.InternalEObject internalContainer = (org.eclipse.emf.ecore.InternalEObject) container;
					org.eclipse.emf.ecore.EObject resolvedObject = internalContainer.eResolveProxy(internalObject);
					if (resolvedObject != internalObject) {
						object = resolvedObject;
						wasResolved = true;
					}
				}
			}
		} else {
			wasResolved = true;
		}
		return object;
	}
	
	public String getHiddenTokenText() {
		return hiddenTokenText;
	}
	
	public String getVisibleTokenText() {
		return visibleTokenText;
	}
	
	public void replaceProxy(org.eclipse.emf.ecore.EObject proxy, org.eclipse.emf.ecore.EObject target) {
		if (this.object == proxy) {
			this.object = target;
		}
	}
	
	public String toString() {
		return "visible='" + visibleTokenText + "', hidden='" + hiddenTokenText + "', object='" + object + "', syntaxElement='" + syntaxElement + "'";
	}
	
}
