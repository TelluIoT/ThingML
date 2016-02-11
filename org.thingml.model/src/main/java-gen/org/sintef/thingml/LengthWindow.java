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
 * A representation of the model object '<em><b>Length Window</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.LengthWindow#getSize <em>Size</em>}</li>
 *   <li>{@link org.sintef.thingml.LengthWindow#getStep <em>Step</em>}</li>
 * </ul>
 *
 * @see org.sintef.thingml.ThingmlPackage#getLengthWindow()
 * @model
 * @generated
 */
public interface LengthWindow extends ViewSource {
	/**
	 * Returns the value of the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' containment reference.
	 * @see #setSize(Expression)
	 * @see org.sintef.thingml.ThingmlPackage#getLengthWindow_Size()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getSize();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.LengthWindow#getSize <em>Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' containment reference.
	 * @see #getSize()
	 * @generated
	 */
	void setSize(Expression value);

	/**
	 * Returns the value of the '<em><b>Step</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Step</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Step</em>' containment reference.
	 * @see #setStep(Expression)
	 * @see org.sintef.thingml.ThingmlPackage#getLengthWindow_Step()
	 * @model containment="true"
	 * @generated
	 */
	Expression getStep();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.LengthWindow#getStep <em>Step</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Step</em>' containment reference.
	 * @see #getStep()
	 * @generated
	 */
	void setStep(Expression value);

} // LengthWindow
