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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source Composition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.SourceComposition#getSources <em>Sources</em>}</li>
 *   <li>{@link org.sintef.thingml.SourceComposition#getResultMessage <em>Result Message</em>}</li>
 *   <li>{@link org.sintef.thingml.SourceComposition#getRules <em>Rules</em>}</li>
 * </ul>
 *
 * @see org.sintef.thingml.ThingmlPackage#getSourceComposition()
 * @model abstract="true"
 * @generated
 */
public interface SourceComposition extends Source {

	/**
	 * Returns the value of the '<em><b>Sources</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Source}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sources</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sources</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getSourceComposition_Sources()
	 * @model containment="true" lower="2"
	 * @generated
	 */
	EList<Source> getSources();

	/**
	 * Returns the value of the '<em><b>Result Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Message</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Message</em>' reference.
	 * @see #setResultMessage(Message)
	 * @see org.sintef.thingml.ThingmlPackage#getSourceComposition_ResultMessage()
	 * @model required="true"
	 * @generated
	 */
	Message getResultMessage();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.SourceComposition#getResultMessage <em>Result Message</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Message</em>' reference.
	 * @see #getResultMessage()
	 * @generated
	 */
	void setResultMessage(Message value);

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getSourceComposition_Rules()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getRules();
} // SourceComposition
