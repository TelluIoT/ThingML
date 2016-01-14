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
package org.sintef.thingml.impl;

import org.eclipse.emf.ecore.EClass;

import org.sintef.thingml.PrimitiveType;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Primitive Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class PrimitiveTypeImpl extends TypeImpl implements PrimitiveType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PrimitiveTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.PRIMITIVE_TYPE;
	}

	/**
	 * @generated NOT
	 */
	public boolean isNumber() {
	   return this.getName().contains("Int") || this.getName().contains("Long") || this.getName().contains("Float") || this.getName().contains("Double");
	}

	/**
	 * @generated NOT
	 */
	public boolean isBoolean() {
		return this.getName().contains("Bool");
	}

	/**
	 * @generated NOT
	 */
	public boolean isString() {
		return this.getName().contains("String");
	}

	/**
	 * @generated NOT
	 */
	public boolean isChar() {
		return this.getName().contains("Char");
	}

	/**
	 * @generated NOT
	 */
	public boolean isByte() {
		return this.getName().contains("Byte");
	}

} //PrimitiveTypeImpl
