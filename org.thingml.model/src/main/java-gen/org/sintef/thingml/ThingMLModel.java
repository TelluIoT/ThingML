/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.sintef.thingml;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.Set;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Thing ML Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.ThingMLModel#getTypes <em>Types</em>}</li>
 *   <li>{@link org.sintef.thingml.ThingMLModel#getImports <em>Imports</em>}</li>
 *   <li>{@link org.sintef.thingml.ThingMLModel#getConfigs <em>Configs</em>}</li>
 *   <li>{@link org.sintef.thingml.ThingMLModel#getProtocols <em>Protocols</em>}</li>
 * </ul>
 *
 * @see org.sintef.thingml.ThingmlPackage#getThingMLModel()
 * @model
 * @generated
 */
public interface ThingMLModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Type}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThingMLModel_Types()
	 * @model containment="true"
	 * @generated
	 */
	EList<Type> getTypes();

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' reference list.
	 * The list contents are of type {@link org.sintef.thingml.ThingMLModel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imports</em>' reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThingMLModel_Imports()
	 * @model
	 * @generated
	 */
	EList<ThingMLModel> getImports();

	/**
	 * Returns the value of the '<em><b>Configs</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Configuration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configs</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThingMLModel_Configs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Configuration> getConfigs();

    /**
	 * Returns the value of the '<em><b>Protocols</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Protocol}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocols</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocols</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThingMLModel_Protocols()
	 * @model containment="true"
	 * @generated
	 */
	EList<Protocol> getProtocols();



} // ThingMLModel
