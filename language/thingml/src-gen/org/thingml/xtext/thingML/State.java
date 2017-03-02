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
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.State#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.State#getEntry <em>Entry</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.State#getExit <em>Exit</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.State#getInternal <em>Internal</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.State#getOutgoing <em>Outgoing</em>}</li>
 * </ul>
 *
 * @see org.thingml.xtext.thingML.ThingMLPackage#getState()
 * @model
 * @generated
 */
public interface State extends NamedElement, AnnotatedElement
{
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
   * @see org.thingml.xtext.thingML.ThingMLPackage#getState_Properties()
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
   * @see org.thingml.xtext.thingML.ThingMLPackage#getState_Entry()
   * @model containment="true"
   * @generated
   */
  Action getEntry();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.State#getEntry <em>Entry</em>}' containment reference.
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
   * @see org.thingml.xtext.thingML.ThingMLPackage#getState_Exit()
   * @model containment="true"
   * @generated
   */
  Action getExit();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.State#getExit <em>Exit</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Exit</em>' containment reference.
   * @see #getExit()
   * @generated
   */
  void setExit(Action value);

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
   * @see org.thingml.xtext.thingML.ThingMLPackage#getState_Internal()
   * @model containment="true"
   * @generated
   */
  EList<InternalTransition> getInternal();

  /**
   * Returns the value of the '<em><b>Outgoing</b></em>' containment reference list.
   * The list contents are of type {@link org.thingml.xtext.thingML.Transition}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Outgoing</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Outgoing</em>' containment reference list.
   * @see org.thingml.xtext.thingML.ThingMLPackage#getState_Outgoing()
   * @model containment="true"
   * @generated
   */
  EList<Transition> getOutgoing();

} // State
