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

import org.sintef.thingml.PlatformAnnotation;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.Type;
import org.sintef.thingml.constraints.Types;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class TypeImpl extends AnnotatedElementImpl implements Type {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.TYPE;
	}

	/**
	 * @generated NOT
	 * @return
	 */
	public Type getBroadType() {
		if (hasAnnotation("type_checker")) {
			String ty = annotation("type_checker").get(0);
			if (ty.equals("Integer"))
				return Types.INTEGER_TYPE;
			else if (ty.equals("Real"))
				return Types.REAL_TYPE;
			else if (ty.equals("Boolean"))
				return Types.BOOLEAN_TYPE;
			else if (ty.equals("String"))
				return Types.STRING_TYPE;
			else
				return Types.ANY_TYPE;
		}
		return Types.ANY_TYPE;
	}

	/**
	 * @generated NOT
	 * @param t
	 * @return
     */
	public boolean isA(Type t) {
		if (t.getBroadType() == Types.ANY_TYPE)
			return true;
		if (getBroadType() == t.getBroadType())
			return true;
		if (getBroadType() == Types.INTEGER_TYPE && t.getBroadType() == Types.REAL_TYPE)
			return true;
		return false;
	}

} //TypeImpl
