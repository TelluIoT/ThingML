/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sintef.thingml.resource.thingml.mopp;

/**
 * A quick fix that replaces the target of a reference with another EObject. This
 * class is used to implement default quick fixes for references that could not be
 * resolved, but can also be used by custom reference resolvers.
 */
public class ThingmlChangeReferenceQuickFix extends org.sintef.thingml.resource.thingml.mopp.ThingmlQuickFix {
	
	private org.eclipse.emf.ecore.EObject container;
	private org.eclipse.emf.ecore.EReference reference;
	private org.eclipse.emf.ecore.EObject oldTarget;
	private org.eclipse.emf.ecore.EObject newTarget;
	
	public ThingmlChangeReferenceQuickFix(String displayString, String imageKey, org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EReference reference, org.eclipse.emf.ecore.EObject oldTarget, org.eclipse.emf.ecore.EObject newTarget) {
		super(displayString, imageKey, java.util.Arrays.asList(container, reference, oldTarget));
		this.container = container;
		this.reference = reference;
		this.oldTarget = oldTarget;
		this.newTarget = newTarget;
	}
	
	@Override	
	public void applyChanges() {
		org.eclipse.emf.ecore.util.EcoreUtil.replace(container, reference, oldTarget, newTarget);
	}
	
}
