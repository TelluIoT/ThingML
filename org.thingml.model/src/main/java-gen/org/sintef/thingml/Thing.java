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

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Thing</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.Thing#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.sintef.thingml.Thing#isFragment <em>Fragment</em>}</li>
 *   <li>{@link org.sintef.thingml.Thing#getPorts <em>Ports</em>}</li>
 *   <li>{@link org.sintef.thingml.Thing#getBehaviour <em>Behaviour</em>}</li>
 *   <li>{@link org.sintef.thingml.Thing#getIncludes <em>Includes</em>}</li>
 *   <li>{@link org.sintef.thingml.Thing#getAssign <em>Assign</em>}</li>
 *   <li>{@link org.sintef.thingml.Thing#getMessages <em>Messages</em>}</li>
 *   <li>{@link org.sintef.thingml.Thing#getFunctions <em>Functions</em>}</li>
 *   <li>{@link org.sintef.thingml.Thing#getStreams <em>Streams</em>}</li>
 *   <li>{@link org.sintef.thingml.Thing#getOperators <em>Operators</em>}</li>
 * </ul>
 *
 * @see org.sintef.thingml.ThingmlPackage#getThing()
 * @model
 * @generated
 */
public interface Thing extends Type {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThing_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getProperties();

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
	 * @see org.sintef.thingml.ThingmlPackage#getThing_Fragment()
	 * @model
	 * @generated
	 */
	boolean isFragment();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Thing#isFragment <em>Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fragment</em>' attribute.
	 * @see #isFragment()
	 * @generated
	 */
	void setFragment(boolean value);

	/**
	 * Returns the value of the '<em><b>Ports</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Port}.
	 * It is bidirectional and its opposite is '{@link org.sintef.thingml.Port#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ports</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThing_Ports()
	 * @see org.sintef.thingml.Port#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
	EList<Port> getPorts();

	/**
	 * Returns the value of the '<em><b>Behaviour</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.StateMachine}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Behaviour</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behaviour</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThing_Behaviour()
	 * @model containment="true"
	 * @generated
	 */
	EList<StateMachine> getBehaviour();

	/**
	 * Returns the value of the '<em><b>Includes</b></em>' reference list.
	 * The list contents are of type {@link org.sintef.thingml.Thing}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Includes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Includes</em>' reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThing_Includes()
	 * @model
	 * @generated
	 */
	EList<Thing> getIncludes();

	/**
	 * Returns the value of the '<em><b>Assign</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.PropertyAssign}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assign</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assign</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThing_Assign()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertyAssign> getAssign();

	/**
	 * Returns the value of the '<em><b>Messages</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Message}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Messages</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Messages</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThing_Messages()
	 * @model containment="true"
	 * @generated
	 */
	EList<Message> getMessages();

	/**
	 * Returns the value of the '<em><b>Functions</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Function}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Functions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functions</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThing_Functions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Function> getFunctions();

    /**
	 * Returns the value of the '<em><b>Streams</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Stream}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Streams</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Streams</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThing_Streams()
	 * @model containment="true"
	 * @generated
	 */
	EList<Stream> getStreams();

				/**
	 * Returns the value of the '<em><b>Operators</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Operator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operators</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operators</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getThing_Operators()
	 * @model containment="true"
	 * @generated
	 */
	EList<Operator> getOperators();

				//Derived properties

    /**
     * @generated NOT
     */
    boolean isSingleton();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Transition> allTransitionsWithAction();

    /**
     *
     * @return
     * @generated NOT
     */
    List<InternalTransition> allInternalTransitionsWithAction();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Thing> allFragments();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Property> allProperties();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Function> allFunctions();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Operator> allOperators();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Port> allPorts();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Message> allIncomingMessages();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Message> allOutgoingMessages();

    /**
     *
     * @return
     * @generated NOT
     */
    List<StateMachine> allStateMachines();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Message> allMessages();

    /**
     *
     * @return
     * @generated NOT
     */
    List<Property> allPropertiesInDepth();

    /**
     *
     * @param p
     * @return
     * @generated NOT
     */
    Expression initExpression(Property p);

    /**
     *
     * @param p
     * @return
     * @generated NOT
     */
    List<PropertyAssign> initExpressionsForArray(Property p);

	/**
	 * @generated NOT
	 * @return
     */
	List<Action> allAction(Class clazz);

	/**
	 * @generated NOT
	 * @return
	 */
	List<Expression> allExpression(Class clazz);

} // Thing
