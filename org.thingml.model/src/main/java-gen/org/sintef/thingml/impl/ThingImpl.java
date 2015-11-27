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
package org.sintef.thingml.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sintef.thingml.Function;
import org.sintef.thingml.Message;
import org.sintef.thingml.Operator;
import org.sintef.thingml.Port;
import org.sintef.thingml.Property;
import org.sintef.thingml.PropertyAssign;
import org.sintef.thingml.StateMachine;
import org.sintef.thingml.Stream;
import org.sintef.thingml.Thing;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Thing</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.ThingImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingImpl#isFragment <em>Fragment</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingImpl#getBehaviour <em>Behaviour</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingImpl#getIncludes <em>Includes</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingImpl#getAssign <em>Assign</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingImpl#getMessages <em>Messages</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingImpl#getFunctions <em>Functions</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingImpl#getStreams <em>Streams</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingImpl#getOperators <em>Operators</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ThingImpl extends TypeImpl implements Thing {
	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> properties;

	/**
	 * The default value of the '{@link #isFragment() <em>Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFragment()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FRAGMENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFragment() <em>Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFragment()
	 * @generated
	 * @ordered
	 */
	protected boolean fragment = FRAGMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> ports;

	/**
	 * The cached value of the '{@link #getBehaviour() <em>Behaviour</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBehaviour()
	 * @generated
	 * @ordered
	 */
	protected EList<StateMachine> behaviour;

	/**
	 * The cached value of the '{@link #getIncludes() <em>Includes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludes()
	 * @generated
	 * @ordered
	 */
	protected EList<Thing> includes;

	/**
	 * The cached value of the '{@link #getAssign() <em>Assign</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssign()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyAssign> assign;

	/**
	 * The cached value of the '{@link #getMessages() <em>Messages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessages()
	 * @generated
	 * @ordered
	 */
	protected EList<Message> messages;

	/**
	 * The cached value of the '{@link #getFunctions() <em>Functions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctions()
	 * @generated
	 * @ordered
	 */
	protected EList<Function> functions;

	/**
	 * The cached value of the '{@link #getStreams() <em>Streams</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStreams()
	 * @generated
	 * @ordered
	 */
	protected EList<Stream> streams;

	/**
	 * The cached value of the '{@link #getOperators() <em>Operators</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperators()
	 * @generated
	 * @ordered
	 */
	protected EList<Operator> operators;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ThingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.THING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Property> getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList<Property>(Property.class, this, ThingmlPackage.THING__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFragment() {
		return fragment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFragment(boolean newFragment) {
		boolean oldFragment = fragment;
		fragment = newFragment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.THING__FRAGMENT, oldFragment, fragment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentWithInverseEList<Port>(Port.class, this, ThingmlPackage.THING__PORTS, ThingmlPackage.PORT__OWNER);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StateMachine> getBehaviour() {
		if (behaviour == null) {
			behaviour = new EObjectContainmentEList<StateMachine>(StateMachine.class, this, ThingmlPackage.THING__BEHAVIOUR);
		}
		return behaviour;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Thing> getIncludes() {
		if (includes == null) {
			includes = new EObjectResolvingEList<Thing>(Thing.class, this, ThingmlPackage.THING__INCLUDES);
		}
		return includes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyAssign> getAssign() {
		if (assign == null) {
			assign = new EObjectContainmentEList<PropertyAssign>(PropertyAssign.class, this, ThingmlPackage.THING__ASSIGN);
		}
		return assign;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Message> getMessages() {
		if (messages == null) {
			messages = new EObjectContainmentEList<Message>(Message.class, this, ThingmlPackage.THING__MESSAGES);
		}
		return messages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Function> getFunctions() {
		if (functions == null) {
			functions = new EObjectContainmentEList<Function>(Function.class, this, ThingmlPackage.THING__FUNCTIONS);
		}
		return functions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Stream> getStreams() {
		if (streams == null) {
			streams = new EObjectContainmentEList<Stream>(Stream.class, this, ThingmlPackage.THING__STREAMS);
		}
		return streams;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Operator> getOperators() {
		if (operators == null) {
			operators = new EObjectContainmentEList<Operator>(Operator.class, this, ThingmlPackage.THING__OPERATORS);
		}
		return operators;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ThingmlPackage.THING__PORTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPorts()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ThingmlPackage.THING__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.THING__PORTS:
				return ((InternalEList<?>)getPorts()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.THING__BEHAVIOUR:
				return ((InternalEList<?>)getBehaviour()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.THING__ASSIGN:
				return ((InternalEList<?>)getAssign()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.THING__MESSAGES:
				return ((InternalEList<?>)getMessages()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.THING__FUNCTIONS:
				return ((InternalEList<?>)getFunctions()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.THING__STREAMS:
				return ((InternalEList<?>)getStreams()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.THING__OPERATORS:
				return ((InternalEList<?>)getOperators()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ThingmlPackage.THING__PROPERTIES:
				return getProperties();
			case ThingmlPackage.THING__FRAGMENT:
				return isFragment();
			case ThingmlPackage.THING__PORTS:
				return getPorts();
			case ThingmlPackage.THING__BEHAVIOUR:
				return getBehaviour();
			case ThingmlPackage.THING__INCLUDES:
				return getIncludes();
			case ThingmlPackage.THING__ASSIGN:
				return getAssign();
			case ThingmlPackage.THING__MESSAGES:
				return getMessages();
			case ThingmlPackage.THING__FUNCTIONS:
				return getFunctions();
			case ThingmlPackage.THING__STREAMS:
				return getStreams();
			case ThingmlPackage.THING__OPERATORS:
				return getOperators();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ThingmlPackage.THING__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends Property>)newValue);
				return;
			case ThingmlPackage.THING__FRAGMENT:
				setFragment((Boolean)newValue);
				return;
			case ThingmlPackage.THING__PORTS:
				getPorts().clear();
				getPorts().addAll((Collection<? extends Port>)newValue);
				return;
			case ThingmlPackage.THING__BEHAVIOUR:
				getBehaviour().clear();
				getBehaviour().addAll((Collection<? extends StateMachine>)newValue);
				return;
			case ThingmlPackage.THING__INCLUDES:
				getIncludes().clear();
				getIncludes().addAll((Collection<? extends Thing>)newValue);
				return;
			case ThingmlPackage.THING__ASSIGN:
				getAssign().clear();
				getAssign().addAll((Collection<? extends PropertyAssign>)newValue);
				return;
			case ThingmlPackage.THING__MESSAGES:
				getMessages().clear();
				getMessages().addAll((Collection<? extends Message>)newValue);
				return;
			case ThingmlPackage.THING__FUNCTIONS:
				getFunctions().clear();
				getFunctions().addAll((Collection<? extends Function>)newValue);
				return;
			case ThingmlPackage.THING__STREAMS:
				getStreams().clear();
				getStreams().addAll((Collection<? extends Stream>)newValue);
				return;
			case ThingmlPackage.THING__OPERATORS:
				getOperators().clear();
				getOperators().addAll((Collection<? extends Operator>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ThingmlPackage.THING__PROPERTIES:
				getProperties().clear();
				return;
			case ThingmlPackage.THING__FRAGMENT:
				setFragment(FRAGMENT_EDEFAULT);
				return;
			case ThingmlPackage.THING__PORTS:
				getPorts().clear();
				return;
			case ThingmlPackage.THING__BEHAVIOUR:
				getBehaviour().clear();
				return;
			case ThingmlPackage.THING__INCLUDES:
				getIncludes().clear();
				return;
			case ThingmlPackage.THING__ASSIGN:
				getAssign().clear();
				return;
			case ThingmlPackage.THING__MESSAGES:
				getMessages().clear();
				return;
			case ThingmlPackage.THING__FUNCTIONS:
				getFunctions().clear();
				return;
			case ThingmlPackage.THING__STREAMS:
				getStreams().clear();
				return;
			case ThingmlPackage.THING__OPERATORS:
				getOperators().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ThingmlPackage.THING__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case ThingmlPackage.THING__FRAGMENT:
				return fragment != FRAGMENT_EDEFAULT;
			case ThingmlPackage.THING__PORTS:
				return ports != null && !ports.isEmpty();
			case ThingmlPackage.THING__BEHAVIOUR:
				return behaviour != null && !behaviour.isEmpty();
			case ThingmlPackage.THING__INCLUDES:
				return includes != null && !includes.isEmpty();
			case ThingmlPackage.THING__ASSIGN:
				return assign != null && !assign.isEmpty();
			case ThingmlPackage.THING__MESSAGES:
				return messages != null && !messages.isEmpty();
			case ThingmlPackage.THING__FUNCTIONS:
				return functions != null && !functions.isEmpty();
			case ThingmlPackage.THING__STREAMS:
				return streams != null && !streams.isEmpty();
			case ThingmlPackage.THING__OPERATORS:
				return operators != null && !operators.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (fragment: ");
		result.append(fragment);
		result.append(')');
		return result.toString();
	}

    //Derived properties

    /**
     *
     * @return
     * @generated NOT
     */
    public boolean isSingleton() {
        return isDefined("singleton", "true");
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Transition> allTransitionsWithAction() {
        //var result = new ArrayList[Handler]()
        final List<Transition> result = new ArrayList<Transition>();
        for(StateMachine sm : getBehaviour()) {
            for(State s : sm.allStates()) {
                for(Transition o : s.getOutgoing()) {
                    if (o.getAction() != null) {
                        result.add(o);
                    }
                }
            }
        }
        return result;
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<InternalTransition> allInternalTransitionsWithAction() {
        //var result = new ArrayList[Handler]()
        final List<InternalTransition> result = new ArrayList<InternalTransition>();
        for(StateMachine sm : getBehaviour()) {
            for(State s : sm.allStates()) {
                for(InternalTransition o : s.getInternal()) {
                    if (o.getAction() != null) {
                        result.add(o);
                    }
                }
            }
        }
        return result;
    }


    /**
     *
     * @return
     * @generated NOT
     */
    public List<Thing> allFragments() {
        return ThingMLHelpers.allThingFragments(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Property> allProperties() {
        return ThingMLHelpers.allProperties(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Function> allFunctions() {
        return ThingMLHelpers.allFunctions(this);
    }

	@Override
	public List<Operator> allOperators() {
		return ThingMLHelpers.allOperators(this);
	}

	/**
     *
     * @return
     * @generated NOT
     */
    @Override
    public List<PlatformAnnotation> allAnnotations() {
        return ThingMLHelpers.allAnnotations(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Port> allPorts() {
        return ThingMLHelpers.allPorts(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Message> allIncomingMessages() {
        return ThingMLHelpers.allIncomingMessages(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Message> allOutgoingMessages() {
        return ThingMLHelpers.allOutgoingMessages(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<StateMachine> allStateMachines() {
        return ThingMLHelpers.allStateMachines(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Message> allMessages() {
        return ThingMLHelpers.allMessages(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Property> allPropertiesInDepth() {
        List<Property> result = allProperties();
        for(StateMachine sm : allStateMachines()) {
            result.addAll(sm.allContainedProperties());
        }
        return result;
    }

    /**
     *
     * @param p
     * @return
     * @generated NOT
     */
    public Expression initExpression(Property p) {

        if (allProperties().contains(p)) {  // It is a property of the thing

            List<PropertyAssign> assigns = new ArrayList<PropertyAssign>();
            for (PropertyAssign e : getAssign()) {
                if (e.getProperty().equals(p))
                    assigns.add(e);
            }

            // If the expression is defined locally return the init expression
            if (getProperties().contains(p)) {
                if (assigns.size() > 0)
                    System.out.println("Error: Thing " + getName() + " cannot redefine initial value for property " + p.getName());
                return p.getInit();
            }

            if (assigns.size() > 1)
                System.out.println("Error: Thing " + getName() + " contains several assignments for property " + p.getName());

            if (assigns.size() == 1) {
                return assigns.get(0).getInit();
            }

            List<Thing> imports = new ArrayList<Thing>();
            for (Thing t : getIncludes()) {
                if (t.allProperties().contains(p)) {
                    imports.add(t);
                }
            }
            //  imports cannot be empty since the property must be defined in a imported thing
            if (imports.size() > 1)
                System.out.println("Warning: Thing " + getName() + " gets property " + p.getName() + " from several paths, it should define its initial value");

            return imports.get(0).initExpression(p);
        } else { // It is a property of a state machine
            return p.getInit();
        }
    }


    /**
     *
     * @param p
     * @return
     * @generated NOT
     */
    public List<PropertyAssign> initExpressionsForArray(Property p) {

        List<PropertyAssign> result = new ArrayList<PropertyAssign>();

        if (allProperties().contains(p)) {  // It is a property of the thing

            // collect assignment in the imported things first:
            for (Thing t : getIncludes()) {
                if (t.allProperties().contains(p))
                    result.addAll(t.initExpressionsForArray(p));
            }
            // collect assignments in this thing
            List<PropertyAssign> assigns = null;
            for(PropertyAssign pa : getAssign()) {
                if (pa.getProperty().equals(p))
                    result.add(pa);
            }
        }
        else { // It is a property of a state machine
            // No way to initialize arrays in state machines (so far)
        }
        return result;
    }

} //ThingImpl
