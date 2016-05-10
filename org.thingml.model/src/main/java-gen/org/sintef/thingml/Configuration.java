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

import java.util.*;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.Configuration#getInstances <em>Instances</em>}</li>
 *   <li>{@link org.sintef.thingml.Configuration#getConnectors <em>Connectors</em>}</li>
 *   <li>{@link org.sintef.thingml.Configuration#getPropassigns <em>Propassigns</em>}</li>
 * </ul>
 *
 * @see org.sintef.thingml.ThingmlPackage#getConfiguration()
 * @model
 * @generated
 */
public interface Configuration extends AnnotatedElement {
	/**
	 * Returns the value of the '<em><b>Instances</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Instance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instances</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getConfiguration_Instances()
	 * @model containment="true"
	 * @generated
	 */
	EList<Instance> getInstances();

	/**
	 * Returns the value of the '<em><b>Connectors</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.AbstractConnector}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connectors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connectors</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getConfiguration_Connectors()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractConnector> getConnectors();

	/**
	 * Returns the value of the '<em><b>Propassigns</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.ConfigPropertyAssign}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Propassigns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Propassigns</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getConfiguration_Propassigns()
	 * @model containment="true"
	 * @generated
	 */
	EList<ConfigPropertyAssign> getPropassigns();




    } // Configuration
