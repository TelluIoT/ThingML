/**
 * *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.thingml.xtext.thingML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connector</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.Connector#getCli <em>Cli</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Connector#getRequired <em>Required</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Connector#getSrv <em>Srv</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Connector#getProvided <em>Provided</em>}</li>
 * </ul>
 *
 * @see org.thingml.xtext.thingML.ThingMLPackage#getConnector()
 * @model
 * @generated
 */
public interface Connector extends AbstractConnector
{
  /**
   * Returns the value of the '<em><b>Cli</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Cli</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Cli</em>' reference.
   * @see #setCli(Instance)
   * @see org.thingml.xtext.thingML.ThingMLPackage#getConnector_Cli()
   * @model
   * @generated
   */
  Instance getCli();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.Connector#getCli <em>Cli</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Cli</em>' reference.
   * @see #getCli()
   * @generated
   */
  void setCli(Instance value);

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
   * @see org.thingml.xtext.thingML.ThingMLPackage#getConnector_Required()
   * @model
   * @generated
   */
  RequiredPort getRequired();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.Connector#getRequired <em>Required</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Required</em>' reference.
   * @see #getRequired()
   * @generated
   */
  void setRequired(RequiredPort value);

  /**
   * Returns the value of the '<em><b>Srv</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Srv</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Srv</em>' reference.
   * @see #setSrv(Instance)
   * @see org.thingml.xtext.thingML.ThingMLPackage#getConnector_Srv()
   * @model
   * @generated
   */
  Instance getSrv();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.Connector#getSrv <em>Srv</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Srv</em>' reference.
   * @see #getSrv()
   * @generated
   */
  void setSrv(Instance value);

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
   * @see org.thingml.xtext.thingML.ThingMLPackage#getConnector_Provided()
   * @model
   * @generated
   */
  ProvidedPort getProvided();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.Connector#getProvided <em>Provided</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Provided</em>' reference.
   * @see #getProvided()
   * @generated
   */
  void setProvided(ProvidedPort value);

} // Connector
