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

    //Derived properties

    /**
     *
     * @return
     * @generated NOT
     */
    List<ThingMLModel> allThingMLModelModels();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Type> allTypes();

    /**
     *
     * @return
     * @generated NOT
     */
    Set<Type> allUsedTypes();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Type> allSimpleTypes();

    /**
     *
     * @return
     * @generated NOT
     */
    Set<Type> allUsedSimpleTypes();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Thing> allThings();

    /**
     *
     * @return
     * @generated NOT
     */
    Set<Message> allMessages();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Configuration> allConfigurations();

} // ThingMLModel
