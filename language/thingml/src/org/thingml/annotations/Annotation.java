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
package org.thingml.annotations;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.thingML.ThingMLPackage;

public class Annotation {
	
	public String name;	

	public String description;
	public EClass scope[] = {ThingMLPackage.eINSTANCE.getAnnotatedElement()}; //By default, annotations could apply to any AnnotatedElement
	
	public Annotation(String name, String description, EClass scope[]) {
		this.name = name;
		this.description = description;
		this.scope = scope;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean check(EObject source, String value) {
		for(EClass clazz : scope) {
			if (clazz.isInstance(source)) {
				return true;
			}
		}
		return false;
	}
	
	protected String scopeToString() {
		String result = "";
		for(EClass c : scope) {
			result += c.getName() + ", ";
		}
		return result;
	}

	@Override
	public String toString() {
		return "@" + name + " applies to " + scopeToString() + ". " + description + ".";
	}
	
	
}
