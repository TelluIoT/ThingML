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
package org.thingml.xtext.thingML.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyAssign;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Thing</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.impl.ThingImpl#isFragment <em>Fragment</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ThingImpl#getIncludes <em>Includes</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ThingImpl#getMessages <em>Messages</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ThingImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ThingImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ThingImpl#getFunctions <em>Functions</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ThingImpl#getAssign <em>Assign</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ThingImpl#getBehaviour <em>Behaviour</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ThingImpl extends TypeImpl implements Thing
{
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
   * The cached value of the '{@link #getIncludes() <em>Includes</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIncludes()
   * @generated
   * @ordered
   */
  protected EList<Thing> includes;

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
   * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPorts()
   * @generated
   * @ordered
   */
  protected EList<Port> ports;

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
   * The cached value of the '{@link #getFunctions() <em>Functions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFunctions()
   * @generated
   * @ordered
   */
  protected EList<Function> functions;

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
   * The cached value of the '{@link #getBehaviour() <em>Behaviour</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBehaviour()
   * @generated
   * @ordered
   */
  protected EList<CompositeState> behaviour;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ThingImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return ThingMLPackage.Literals.THING;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isFragment()
  {
    return fragment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFragment(boolean newFragment)
  {
    boolean oldFragment = fragment;
    fragment = newFragment;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.THING__FRAGMENT, oldFragment, fragment));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Thing> getIncludes()
  {
    if (includes == null)
    {
      includes = new EObjectResolvingEList<Thing>(Thing.class, this, ThingMLPackage.THING__INCLUDES);
    }
    return includes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Message> getMessages()
  {
    if (messages == null)
    {
      messages = new EObjectContainmentEList<Message>(Message.class, this, ThingMLPackage.THING__MESSAGES);
    }
    return messages;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Port> getPorts()
  {
    if (ports == null)
    {
      ports = new EObjectContainmentEList<Port>(Port.class, this, ThingMLPackage.THING__PORTS);
    }
    return ports;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Property> getProperties()
  {
    if (properties == null)
    {
      properties = new EObjectContainmentEList<Property>(Property.class, this, ThingMLPackage.THING__PROPERTIES);
    }
    return properties;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Function> getFunctions()
  {
    if (functions == null)
    {
      functions = new EObjectContainmentEList<Function>(Function.class, this, ThingMLPackage.THING__FUNCTIONS);
    }
    return functions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<PropertyAssign> getAssign()
  {
    if (assign == null)
    {
      assign = new EObjectContainmentEList<PropertyAssign>(PropertyAssign.class, this, ThingMLPackage.THING__ASSIGN);
    }
    return assign;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<CompositeState> getBehaviour()
  {
    if (behaviour == null)
    {
      behaviour = new EObjectContainmentEList<CompositeState>(CompositeState.class, this, ThingMLPackage.THING__BEHAVIOUR);
    }
    return behaviour;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case ThingMLPackage.THING__MESSAGES:
        return ((InternalEList<?>)getMessages()).basicRemove(otherEnd, msgs);
      case ThingMLPackage.THING__PORTS:
        return ((InternalEList<?>)getPorts()).basicRemove(otherEnd, msgs);
      case ThingMLPackage.THING__PROPERTIES:
        return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
      case ThingMLPackage.THING__FUNCTIONS:
        return ((InternalEList<?>)getFunctions()).basicRemove(otherEnd, msgs);
      case ThingMLPackage.THING__ASSIGN:
        return ((InternalEList<?>)getAssign()).basicRemove(otherEnd, msgs);
      case ThingMLPackage.THING__BEHAVIOUR:
        return ((InternalEList<?>)getBehaviour()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case ThingMLPackage.THING__FRAGMENT:
        return isFragment();
      case ThingMLPackage.THING__INCLUDES:
        return getIncludes();
      case ThingMLPackage.THING__MESSAGES:
        return getMessages();
      case ThingMLPackage.THING__PORTS:
        return getPorts();
      case ThingMLPackage.THING__PROPERTIES:
        return getProperties();
      case ThingMLPackage.THING__FUNCTIONS:
        return getFunctions();
      case ThingMLPackage.THING__ASSIGN:
        return getAssign();
      case ThingMLPackage.THING__BEHAVIOUR:
        return getBehaviour();
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
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case ThingMLPackage.THING__FRAGMENT:
        setFragment((Boolean)newValue);
        return;
      case ThingMLPackage.THING__INCLUDES:
        getIncludes().clear();
        getIncludes().addAll((Collection<? extends Thing>)newValue);
        return;
      case ThingMLPackage.THING__MESSAGES:
        getMessages().clear();
        getMessages().addAll((Collection<? extends Message>)newValue);
        return;
      case ThingMLPackage.THING__PORTS:
        getPorts().clear();
        getPorts().addAll((Collection<? extends Port>)newValue);
        return;
      case ThingMLPackage.THING__PROPERTIES:
        getProperties().clear();
        getProperties().addAll((Collection<? extends Property>)newValue);
        return;
      case ThingMLPackage.THING__FUNCTIONS:
        getFunctions().clear();
        getFunctions().addAll((Collection<? extends Function>)newValue);
        return;
      case ThingMLPackage.THING__ASSIGN:
        getAssign().clear();
        getAssign().addAll((Collection<? extends PropertyAssign>)newValue);
        return;
      case ThingMLPackage.THING__BEHAVIOUR:
        getBehaviour().clear();
        getBehaviour().addAll((Collection<? extends CompositeState>)newValue);
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
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case ThingMLPackage.THING__FRAGMENT:
        setFragment(FRAGMENT_EDEFAULT);
        return;
      case ThingMLPackage.THING__INCLUDES:
        getIncludes().clear();
        return;
      case ThingMLPackage.THING__MESSAGES:
        getMessages().clear();
        return;
      case ThingMLPackage.THING__PORTS:
        getPorts().clear();
        return;
      case ThingMLPackage.THING__PROPERTIES:
        getProperties().clear();
        return;
      case ThingMLPackage.THING__FUNCTIONS:
        getFunctions().clear();
        return;
      case ThingMLPackage.THING__ASSIGN:
        getAssign().clear();
        return;
      case ThingMLPackage.THING__BEHAVIOUR:
        getBehaviour().clear();
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
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case ThingMLPackage.THING__FRAGMENT:
        return fragment != FRAGMENT_EDEFAULT;
      case ThingMLPackage.THING__INCLUDES:
        return includes != null && !includes.isEmpty();
      case ThingMLPackage.THING__MESSAGES:
        return messages != null && !messages.isEmpty();
      case ThingMLPackage.THING__PORTS:
        return ports != null && !ports.isEmpty();
      case ThingMLPackage.THING__PROPERTIES:
        return properties != null && !properties.isEmpty();
      case ThingMLPackage.THING__FUNCTIONS:
        return functions != null && !functions.isEmpty();
      case ThingMLPackage.THING__ASSIGN:
        return assign != null && !assign.isEmpty();
      case ThingMLPackage.THING__BEHAVIOUR:
        return behaviour != null && !behaviour.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (fragment: ");
    result.append(fragment);
    result.append(')');
    return result.toString();
  }

} //ThingImpl
