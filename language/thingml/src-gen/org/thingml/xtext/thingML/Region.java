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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Region</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.Region#getInitial <em>Initial</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Region#isHistory <em>History</em>}</li>
 * </ul>
 *
 * @see org.thingml.xtext.thingML.ThingMLPackage#getRegion()
 * @model
 * @generated
 */
public interface Region extends EObject
{
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
   * @see org.thingml.xtext.thingML.ThingMLPackage#getRegion_Initial()
   * @model
   * @generated
   */
  State getInitial();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.Region#getInitial <em>Initial</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Initial</em>' reference.
   * @see #getInitial()
   * @generated
   */
  void setInitial(State value);

  /**
   * Returns the value of the '<em><b>History</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>History</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>History</em>' attribute.
   * @see #setHistory(boolean)
   * @see org.thingml.xtext.thingML.ThingMLPackage#getRegion_History()
   * @model
   * @generated
   */
  boolean isHistory();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.Region#isHistory <em>History</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>History</em>' attribute.
   * @see #isHistory()
   * @generated
   */
  void setHistory(boolean value);

} // Region
