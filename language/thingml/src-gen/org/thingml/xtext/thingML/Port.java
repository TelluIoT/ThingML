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
 * A representation of the model object '<em><b>Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.Port#getSends <em>Sends</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.Port#getReceives <em>Receives</em>}</li>
 * </ul>
 *
 * @see org.thingml.xtext.thingML.ThingMLPackage#getPort()
 * @model
 * @generated
 */
public interface Port extends NamedElement, AnnotatedElement
{
  /**
   * Returns the value of the '<em><b>Sends</b></em>' reference list.
   * The list contents are of type {@link org.thingml.xtext.thingML.Message}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sends</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sends</em>' reference list.
   * @see org.thingml.xtext.thingML.ThingMLPackage#getPort_Sends()
   * @model
   * @generated
   */
  EList<Message> getSends();

  /**
   * Returns the value of the '<em><b>Receives</b></em>' reference list.
   * The list contents are of type {@link org.thingml.xtext.thingML.Message}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Receives</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Receives</em>' reference list.
   * @see org.thingml.xtext.thingML.ThingMLPackage#getPort_Receives()
   * @model
   * @generated
   */
  EList<Message> getReceives();

} // Port
