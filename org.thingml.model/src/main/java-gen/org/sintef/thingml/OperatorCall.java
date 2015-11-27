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
/**
 */
package org.sintef.thingml;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operator Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sintef.thingml.OperatorCall#getOperatorRef <em>Operator Ref</em>}</li>
 *   <li>{@link org.sintef.thingml.OperatorCall#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sintef.thingml.ThingmlPackage#getOperatorCall()
 * @model
 * @generated
 */
public interface OperatorCall extends EObject {
	/**
	 * Returns the value of the '<em><b>Operator Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator Ref</em>' reference.
	 * @see #setOperatorRef(Operator)
	 * @see org.sintef.thingml.ThingmlPackage#getOperatorCall_OperatorRef()
	 * @model required="true"
	 * @generated
	 */
	Operator getOperatorRef();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.OperatorCall#getOperatorRef <em>Operator Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator Ref</em>' reference.
	 * @see #getOperatorRef()
	 * @generated
	 */
	void setOperatorRef(Operator value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' reference list.
	 * The list contents are of type {@link org.sintef.thingml.ReceiveMessage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getOperatorCall_Parameters()
	 * @model
	 * @generated
	 */
	EList<ReceiveMessage> getParameters();

} // OperatorCall
