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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.ThingMLModel#getImportURI <em>Import URI</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.ThingMLModel#getTypes <em>Types</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.ThingMLModel#getProtocols <em>Protocols</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.ThingMLModel#getConfigs <em>Configs</em>}</li>
 * </ul>
 *
 * @see org.thingml.xtext.thingML.ThingMLPackage#getThingMLModel()
 * @model
 * @generated
 */
public interface ThingMLModel extends EObject
{
  /**
   * Returns the value of the '<em><b>Import URI</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Import URI</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Import URI</em>' attribute list.
   * @see org.thingml.xtext.thingML.ThingMLPackage#getThingMLModel_ImportURI()
   * @model unique="false"
   * @generated
   */
  EList<String> getImportURI();

  /**
   * Returns the value of the '<em><b>Types</b></em>' containment reference list.
   * The list contents are of type {@link org.thingml.xtext.thingML.Type}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Types</em>' containment reference list.
   * @see org.thingml.xtext.thingML.ThingMLPackage#getThingMLModel_Types()
   * @model containment="true"
   * @generated
   */
  EList<Type> getTypes();

  /**
   * Returns the value of the '<em><b>Protocols</b></em>' containment reference list.
   * The list contents are of type {@link org.thingml.xtext.thingML.Protocol}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Protocols</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Protocols</em>' containment reference list.
   * @see org.thingml.xtext.thingML.ThingMLPackage#getThingMLModel_Protocols()
   * @model containment="true"
   * @generated
   */
  EList<Protocol> getProtocols();

  /**
   * Returns the value of the '<em><b>Configs</b></em>' containment reference list.
   * The list contents are of type {@link org.thingml.xtext.thingML.Configuration}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Configs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Configs</em>' containment reference list.
   * @see org.thingml.xtext.thingML.ThingMLPackage#getThingMLModel_Configs()
   * @model containment="true"
   * @generated
   */
  EList<Configuration> getConfigs();

} // ThingMLModel
