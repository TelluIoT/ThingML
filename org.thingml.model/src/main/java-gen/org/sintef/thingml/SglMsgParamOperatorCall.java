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
 * A representation of the model object '<em><b>Sgl Msg Param Operator Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.SglMsgParamOperatorCall#getOperatorRef <em>Operator Ref</em>}</li>
 *   <li>{@link org.sintef.thingml.SglMsgParamOperatorCall#getParameter <em>Parameter</em>}</li>
 * </ul>
 *
 * @see org.sintef.thingml.ThingmlPackage#getSglMsgParamOperatorCall()
 * @model
 * @generated
 */
public interface SglMsgParamOperatorCall extends EObject {
	/**
	 * Returns the value of the '<em><b>Operator Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator Ref</em>' reference.
	 * @see #setOperatorRef(SglMsgParamOperator)
	 * @see org.sintef.thingml.ThingmlPackage#getSglMsgParamOperatorCall_OperatorRef()
	 * @model required="true"
	 * @generated
	 */
	SglMsgParamOperator getOperatorRef();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.SglMsgParamOperatorCall#getOperatorRef <em>Operator Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator Ref</em>' reference.
	 * @see #getOperatorRef()
	 * @generated
	 */
	void setOperatorRef(SglMsgParamOperator value);

	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter</em>' reference.
	 * @see #setParameter(Source)
	 * @see org.sintef.thingml.ThingmlPackage#getSglMsgParamOperatorCall_Parameter()
	 * @model required="true"
	 * @generated
	 */
	Source getParameter();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.SglMsgParamOperatorCall#getParameter <em>Parameter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameter</em>' reference.
	 * @see #getParameter()
	 * @generated
	 */
	void setParameter(Source value);

} // SglMsgParamOperatorCall
