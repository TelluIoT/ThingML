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
package org.sintef.thingml;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.Port#getOwner <em>Owner</em>}</li>
 *   <li>{@link org.sintef.thingml.Port#getReceives <em>Receives</em>}</li>
 *   <li>{@link org.sintef.thingml.Port#getSends <em>Sends</em>}</li>
 * </ul>
 *
 * @see org.sintef.thingml.ThingmlPackage#getPort()
 * @model abstract="true"
 * @generated
 */
public interface Port extends AnnotatedElement {
	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sintef.thingml.Thing#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(Thing)
	 * @see org.sintef.thingml.ThingmlPackage#getPort_Owner()
	 * @see org.sintef.thingml.Thing#getPorts
	 * @model opposite="ports" transient="false"
	 * @generated
	 */
	Thing getOwner();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Port#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(Thing value);

	/**
	 * Returns the value of the '<em><b>Receives</b></em>' reference list.
	 * The list contents are of type {@link org.sintef.thingml.Message}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Receives</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Receives</em>' reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getPort_Receives()
	 * @model
	 * @generated
	 */
	EList<Message> getReceives();

	/**
	 * Returns the value of the '<em><b>Sends</b></em>' reference list.
	 * The list contents are of type {@link org.sintef.thingml.Message}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sends</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sends</em>' reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getPort_Sends()
	 * @model
	 * @generated
	 */
	EList<Message> getSends();

} // Port
