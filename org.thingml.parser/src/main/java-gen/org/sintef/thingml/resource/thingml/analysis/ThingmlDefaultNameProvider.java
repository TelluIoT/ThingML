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
package org.sintef.thingml.resource.thingml.analysis;

public class ThingmlDefaultNameProvider implements org.sintef.thingml.resource.thingml.IThingmlNameProvider {
	
	public final static String NAME_FEATURE = "name";
	
	/**
	 * Returns a list of potential identifiers that may be used to reference the given
	 * element. This method can be overridden to customize the identification of
	 * elements.
	 */
	public java.util.List<String> getNames(org.eclipse.emf.ecore.EObject element) {
		java.util.List<String> names = new java.util.ArrayList<String>();
		
		// first check for attributes that have set the ID flag to true
		java.util.List<org.eclipse.emf.ecore.EAttribute> attributes = element.eClass().getEAllAttributes();
		for (org.eclipse.emf.ecore.EAttribute attribute : attributes) {
			if (attribute.isID()) {
				Object attributeValue = element.eGet(attribute);
				if (attributeValue != null) {
					names.add(attributeValue.toString());
				}
			}
		}
		
		// then check for an attribute that is called 'name'
		org.eclipse.emf.ecore.EStructuralFeature nameAttr = element.eClass().getEStructuralFeature(NAME_FEATURE);
		if (nameAttr instanceof org.eclipse.emf.ecore.EAttribute) {
			Object attributeValue = element.eGet(nameAttr);
			if (attributeValue != null) {
				names.add(attributeValue.toString());
			}
		} else {
			// try any other string attribute found
			for (org.eclipse.emf.ecore.EAttribute attribute : attributes) {
				if ("java.lang.String".equals(attribute.getEType().getInstanceClassName())) {
					Object attributeValue = element.eGet(attribute);
					if (attributeValue != null) {
						names.add(attributeValue.toString());
					}
				}
			}
			
			// try operations without arguments that return strings and which have a name that
			// ends with 'name'
			for (org.eclipse.emf.ecore.EOperation operation : element.eClass().getEAllOperations()) {
				if (operation.getName().toLowerCase().endsWith(NAME_FEATURE) && operation.getEParameters().size() == 0) {
					Object result = org.sintef.thingml.resource.thingml.util.ThingmlEObjectUtil.invokeOperation(element, operation);
					if (result != null) {
						names.add(result.toString());
					}
				}
			}
		}
		return names;
	}
	
}
