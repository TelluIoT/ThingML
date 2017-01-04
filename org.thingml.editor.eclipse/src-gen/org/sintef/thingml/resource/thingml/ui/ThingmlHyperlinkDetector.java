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
package org.sintef.thingml.resource.thingml.ui;

/**
 * A hyperlink detector returns hyperlink if the token, where the mouse cursor
 * hovers, is a proxy.
 */
public class ThingmlHyperlinkDetector implements org.eclipse.jface.text.hyperlink.IHyperlinkDetector {
	
	private org.sintef.thingml.resource.thingml.IThingmlTextResource textResource;
	
	/**
	 * Creates a hyperlink detector.
	 * 
	 * @param resource the resource to use for calculating the locations.
	 */
	public ThingmlHyperlinkDetector(org.eclipse.emf.ecore.resource.Resource resource) {
		textResource = (org.sintef.thingml.resource.thingml.IThingmlTextResource) resource;
	}
	
	public org.eclipse.jface.text.hyperlink.IHyperlink[] detectHyperlinks(org.eclipse.jface.text.ITextViewer textViewer, org.eclipse.jface.text.IRegion region, boolean canShowMultipleHyperlinks) {
		org.sintef.thingml.resource.thingml.IThingmlLocationMap locationMap = textResource.getLocationMap();
		java.util.List<org.eclipse.emf.ecore.EObject> elementsAtOffset = locationMap.getElementsAt(region.getOffset());
		org.eclipse.emf.ecore.EObject resolvedEObject = null;
		for (org.eclipse.emf.ecore.EObject eObject : elementsAtOffset) {
			if (eObject.eIsProxy()) {
				resolvedEObject = org.eclipse.emf.ecore.util.EcoreUtil.resolve(eObject, textResource);
				if (resolvedEObject == eObject) {
					continue;
				}
				int offset = locationMap.getCharStart(eObject);
				int length = locationMap.getCharEnd(eObject) - offset + 1;
				String text = null;
				try {
					text = textViewer.getDocument().get(offset, length);
				} catch (org.eclipse.jface.text.BadLocationException e) {
				}
				// we skipt elements that are not contained in a resource, because we cannot jump
				// to them anyway
				if (resolvedEObject.eResource() != null) {
					org.eclipse.jface.text.hyperlink.IHyperlink hyperlink = new org.sintef.thingml.resource.thingml.ui.ThingmlHyperlink(new org.eclipse.jface.text.Region(offset, length), resolvedEObject, text);
					return new org.eclipse.jface.text.hyperlink.IHyperlink[] { hyperlink };
				}
			}
		}
		return null;
	}
	
}
