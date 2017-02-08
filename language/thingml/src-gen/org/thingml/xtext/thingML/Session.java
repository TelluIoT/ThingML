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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Session</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.Session#getName <em>Name</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Session#getMaxInstances <em>Max Instances</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Session#getInitial <em>Initial</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Session#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Session#getEntry <em>Entry</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Session#getExit <em>Exit</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Session#getSubstate <em>Substate</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Session#getInternal <em>Internal</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Session#getRegion <em>Region</em>}</li>
 * </ul>
 *
 * @see org.thingml.xtext.thingML.ThingMLPackage#getSession()
 * @model
 * @generated
 */
public interface Session extends AnnotatedElement, RegionOrSession
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.thingml.xtext.thingML.ThingMLPackage#getSession_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.Session#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Max Instances</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Max Instances</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Max Instances</em>' attribute.
   * @see #setMaxInstances(int)
   * @see org.thingml.xtext.thingML.ThingMLPackage#getSession_MaxInstances()
   * @model
   * @generated
   */
  int getMaxInstances();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.Session#getMaxInstances <em>Max Instances</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Max Instances</em>' attribute.
   * @see #getMaxInstances()
   * @generated
   */
  void setMaxInstances(int value);

  /**
   * Returns the value of the '<em><b>Initial</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Initial</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Initial</em>' reference.
   * @see #setInitial(State)
   * @see org.thingml.xtext.thingML.ThingMLPackage#getSession_Initial()
   * @model
   * @generated
   */
  State getInitial();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.Session#getInitial <em>Initial</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Initial</em>' reference.
   * @see #getInitial()
   * @generated
   */
  void setInitial(State value);

  /**
   * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
   * The list contents are of type {@link org.thingml.xtext.thingML.Property}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Properties</em>' containment reference list.
   * @see org.thingml.xtext.thingML.ThingMLPackage#getSession_Properties()
   * @model containment="true"
   * @generated
   */
  EList<Property> getProperties();

  /**
   * Returns the value of the '<em><b>Entry</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Entry</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Entry</em>' containment reference.
   * @see #setEntry(Action)
   * @see org.thingml.xtext.thingML.ThingMLPackage#getSession_Entry()
   * @model containment="true"
   * @generated
   */
  Action getEntry();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.Session#getEntry <em>Entry</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Entry</em>' containment reference.
   * @see #getEntry()
   * @generated
   */
  void setEntry(Action value);

  /**
   * Returns the value of the '<em><b>Exit</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Exit</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exit</em>' containment reference.
   * @see #setExit(Action)
   * @see org.thingml.xtext.thingML.ThingMLPackage#getSession_Exit()
   * @model containment="true"
   * @generated
   */
  Action getExit();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.Session#getExit <em>Exit</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Exit</em>' containment reference.
   * @see #getExit()
   * @generated
   */
  void setExit(Action value);

  /**
   * Returns the value of the '<em><b>Substate</b></em>' containment reference list.
   * The list contents are of type {@link org.thingml.xtext.thingML.State}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Substate</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Substate</em>' containment reference list.
   * @see org.thingml.xtext.thingML.ThingMLPackage#getSession_Substate()
   * @model containment="true"
   * @generated
   */
  EList<State> getSubstate();

  /**
   * Returns the value of the '<em><b>Internal</b></em>' containment reference list.
   * The list contents are of type {@link org.thingml.xtext.thingML.InternalTransition}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Internal</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Internal</em>' containment reference list.
   * @see org.thingml.xtext.thingML.ThingMLPackage#getSession_Internal()
   * @model containment="true"
   * @generated
   */
  EList<InternalTransition> getInternal();

  /**
   * Returns the value of the '<em><b>Region</b></em>' containment reference list.
   * The list contents are of type {@link org.thingml.xtext.thingML.RegionOrSession}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Region</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Region</em>' containment reference list.
   * @see org.thingml.xtext.thingML.ThingMLPackage#getSession_Region()
   * @model containment="true"
   * @generated
   */
  EList<RegionOrSession> getRegion();

} // Session
