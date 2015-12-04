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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
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
		Type t = ThingmlPackageImpl.init().getThingmlFactory().createPrimitiveType();
		if (hasAnnotation("type_checker")) {
			t.setName(annotation("type_checker").get(0));
			PlatformAnnotation a = ThingmlPackageImpl.init().getThingmlFactory().createPlatformAnnotation();
			a.setName("type_checker");
			a.setValue(t.getName());
			t.getAnnotations().add(a);
		} else {
			t.setName("ANY_TYPE");
			PlatformAnnotation a = ThingmlPackageImpl.init().getThingmlFactory().createPlatformAnnotation();
			a.setName("type_checker");
			a.setValue("Any");
			t.getAnnotations().add(a);
		}
		return t;
	}

	/**
	 * @generated NOT
	 * @param t
	 * @return
     */
	public boolean isA(Type t) {
		System.out.println(getName() + "(" + getBroadType().getName() + ") is a " + t.getName() + "(" + t.getBroadType().getName() + ")?");
		if (getBroadType().getName().equals(t.getBroadType().getName()))
			return true;
		if (getBroadType().getName().equals("Integer") && t.getBroadType().getName().equals("Real"))
			return true;
		return false;
	}

} //TypeImpl
