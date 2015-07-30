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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Message Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sintef.thingml.MessageReference#getMsgReference <em>Msg Reference</em>}</li>
 *   <li>{@link org.sintef.thingml.MessageReference#getParamRef <em>Param Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sintef.thingml.ThingmlPackage#getMessageReference()
 * @model
 * @generated
 */
public interface MessageReference extends Expression {
	/**
	 * Returns the value of the '<em><b>Msg Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Msg Reference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Msg Reference</em>' reference.
	 * @see #setMsgReference(MessageParameter)
	 * @see org.sintef.thingml.ThingmlPackage#getMessageReference_MsgReference()
	 * @model required="true"
	 * @generated
	 */
	MessageParameter getMsgReference();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.MessageReference#getMsgReference <em>Msg Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Msg Reference</em>' reference.
	 * @see #getMsgReference()
	 * @generated
	 */
	void setMsgReference(MessageParameter value);

	/**
	 * Returns the value of the '<em><b>Param Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Param Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Param Ref</em>' reference.
	 * @see #setParamRef(Parameter)
	 * @see org.sintef.thingml.ThingmlPackage#getMessageReference_ParamRef()
	 * @model required="true"
	 * @generated
	 */
	Parameter getParamRef();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.MessageReference#getParamRef <em>Param Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Param Ref</em>' reference.
	 * @see #getParamRef()
	 * @generated
	 */
	void setParamRef(Parameter value);

} // MessageReference
