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

public class IntegerAnnotation extends Annotation {
	
	public boolean onlyPositive;
	
	public IntegerAnnotation(String name, String description, EClass[] scope, boolean onlyPositive) {
		super(name, description, scope);
		this.onlyPositive = onlyPositive;
	}

	@Override
	public boolean check(EObject source, String value) {
		if (!super.check(source, value)) return false;
		try {
			int v = Integer.parseInt(value);
			if (onlyPositive) return v >= 0;
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}		
	}
	
	@Override
	public String toString() {
		if (onlyPositive)
			return super.toString() + " Valid values are positive integers.";
		else
			return super.toString() + " Valid values integers.";
	}

}
