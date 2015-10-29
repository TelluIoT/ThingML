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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sintef.thingml.Configuration#getInstances <em>Instances</em>}</li>
 *   <li>{@link org.sintef.thingml.Configuration#getConnectors <em>Connectors</em>}</li>
 *   <li>{@link org.sintef.thingml.Configuration#isFragment <em>Fragment</em>}</li>
 *   <li>{@link org.sintef.thingml.Configuration#getConfigs <em>Configs</em>}</li>
 *   <li>{@link org.sintef.thingml.Configuration#getPropassigns <em>Propassigns</em>}</li>
 * </ul>
 * </p>
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
	 * Returns the value of the '<em><b>Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fragment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fragment</em>' attribute.
	 * @see #setFragment(boolean)
	 * @see org.sintef.thingml.ThingmlPackage#getConfiguration_Fragment()
	 * @model
	 * @generated
	 */
	boolean isFragment();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Configuration#isFragment <em>Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fragment</em>' attribute.
	 * @see #isFragment()
	 * @generated
	 */
	void setFragment(boolean value);

	/**
	 * Returns the value of the '<em><b>Configs</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.ConfigInclude}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configs</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getConfiguration_Configs()
	 * @model containment="true"
	 * @generated
	 */
	EList<ConfigInclude> getConfigs();

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

    /**
     *
     * @return
     * @generated NOT
     */
    Set<Instance> allInstances();

    /**
     *
     * @return
     * @generated NOT
     */
    Map<Instance, String[]> allRemoteInstances();

    /**
     *
     * @return
     * @generated NOT
     */
    Set<Connector> allConnectors();

    /**
     *
     * @return
     * @generated NOT
     */
    Set<ConfigPropertyAssign> allPropAssigns();

    /**
     *
     * @return
     * @generated NOT
     */
    Map<Port, AbstractMap.SimpleImmutableEntry<List<Message>, List<Message>>> allRemoteMessages();

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
     * @param i
     * @return
     * @generated NOT
     */
    List<Property> allArrays(Instance i);

    /**
     *
     * @param i
     * @return
     * @generated NOT
     */
    List<AbstractMap.SimpleImmutableEntry<Property, Expression>> initExpressionsForInstance(Instance i);

    /**
     *
     * @param i
     * @return
     * @generated NOT
     *
     *   This method only initializes Array properties (property, index expression, init expression)
     */
    Map<Property, List<AbstractMap.SimpleImmutableEntry<Expression, Expression>>> initExpressionsForInstanceArrays(Instance i);


        /**
         Returns the set of destination for messages sent through the port p
         For each outgoing message the results gives the list of destinations
         sorted by source instance as a list of target instances+port
         message* -> source instance* -> (target instance, port)*

         TODO: WTF?! We need to return a proper structure that one can exploit, not that mess of Map<Message, ...>!!!

         * @generated NOT
         */
    Map<Message, Map<Instance, List<AbstractMap.SimpleImmutableEntry<Instance, Port>>>> allMessageDispatch(Thing t, Port p);

    /**
     * Returns all the dangling ports (not connected via a connector) for the instances of this configuration
     * @return
     * @generated NOT
     */
    Map<Instance, List<Port>> danglingPorts();

    /**
     *
     * @param i
     * @return
     * @generated NOT
     */
    List<Expression> initExpressions(Instance i, Property p);
    
    /**
     * @generated NOT
     * @return
     */
    List<Connector> getInternalConnectors();
    
    /**
     * @generated NOT
     * @return
     */
    List<ExternalConnector> getExternalConnectors();

    /**
     * Returns all thing instances ("clients") that dependents on instance i
     * @generated NOT
     * @return
     */
    List<Instance> getClients(Instance i);

    /**
     * Returns all thing instances i dependents on ("servers")
     * @generated NOT
     * @return
     */
    List<Instance> getServers(Instance i);

} // Configuration
