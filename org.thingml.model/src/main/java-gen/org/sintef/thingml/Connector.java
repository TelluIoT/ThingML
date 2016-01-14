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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connector</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.Connector#getSrv <em>Srv</em>}</li>
 *   <li>{@link org.sintef.thingml.Connector#getCli <em>Cli</em>}</li>
 *   <li>{@link org.sintef.thingml.Connector#getRequired <em>Required</em>}</li>
 *   <li>{@link org.sintef.thingml.Connector#getProvided <em>Provided</em>}</li>
 * </ul>
 *
 * @see org.sintef.thingml.ThingmlPackage#getConnector()
 * @model
 * @generated
 */
public interface Connector extends AbstractConnector {
	/**
	 * Returns the value of the '<em><b>Srv</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Srv</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Srv</em>' containment reference.
	 * @see #setSrv(InstanceRef)
	 * @see org.sintef.thingml.ThingmlPackage#getConnector_Srv()
	 * @model containment="true" required="true"
	 * @generated
	 */
	InstanceRef getSrv();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Connector#getSrv <em>Srv</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Srv</em>' containment reference.
	 * @see #getSrv()
	 * @generated
	 */
	void setSrv(InstanceRef value);

	/**
	 * Returns the value of the '<em><b>Cli</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cli</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cli</em>' containment reference.
	 * @see #setCli(InstanceRef)
	 * @see org.sintef.thingml.ThingmlPackage#getConnector_Cli()
	 * @model containment="true" required="true"
	 * @generated
	 */
	InstanceRef getCli();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Connector#getCli <em>Cli</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cli</em>' containment reference.
	 * @see #getCli()
	 * @generated
	 */
	void setCli(InstanceRef value);

	/**
	 * Returns the value of the '<em><b>Required</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required</em>' reference.
	 * @see #setRequired(RequiredPort)
	 * @see org.sintef.thingml.ThingmlPackage#getConnector_Required()
	 * @model required="true"
	 * @generated
	 */
	RequiredPort getRequired();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Connector#getRequired <em>Required</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required</em>' reference.
	 * @see #getRequired()
	 * @generated
	 */
	void setRequired(RequiredPort value);

	/**
	 * Returns the value of the '<em><b>Provided</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided</em>' reference.
	 * @see #setProvided(ProvidedPort)
	 * @see org.sintef.thingml.ThingmlPackage#getConnector_Provided()
	 * @model required="true"
	 * @generated
	 */
	ProvidedPort getProvided();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Connector#getProvided <em>Provided</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided</em>' reference.
	 * @see #getProvided()
	 * @generated
	 */
	void setProvided(ProvidedPort value);

} // Connector
