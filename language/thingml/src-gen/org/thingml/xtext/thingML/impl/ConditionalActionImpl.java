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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.ConditionalAction;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ThingMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conditional Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.impl.ConditionalActionImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ConditionalActionImpl#getAction <em>Action</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ConditionalActionImpl#getElseAction <em>Else Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalActionImpl extends ActionImpl implements ConditionalAction
{
  /**
   * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCondition()
   * @generated
   * @ordered
   */
  protected Expression condition;

  /**
   * The cached value of the '{@link #getAction() <em>Action</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAction()
   * @generated
   * @ordered
   */
  protected Action action;

  /**
   * The cached value of the '{@link #getElseAction() <em>Else Action</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElseAction()
   * @generated
   * @ordered
   */
  protected Action elseAction;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ConditionalActionImpl()
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
    return ThingMLPackage.Literals.CONDITIONAL_ACTION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getCondition()
  {
    return condition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCondition(Expression newCondition, NotificationChain msgs)
  {
    Expression oldCondition = condition;
    condition = newCondition;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ThingMLPackage.CONDITIONAL_ACTION__CONDITION, oldCondition, newCondition);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCondition(Expression newCondition)
  {
    if (newCondition != condition)
    {
      NotificationChain msgs = null;
      if (condition != null)
        msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ThingMLPackage.CONDITIONAL_ACTION__CONDITION, null, msgs);
      if (newCondition != null)
        msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ThingMLPackage.CONDITIONAL_ACTION__CONDITION, null, msgs);
      msgs = basicSetCondition(newCondition, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.CONDITIONAL_ACTION__CONDITION, newCondition, newCondition));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Action getAction()
  {
    return action;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAction(Action newAction, NotificationChain msgs)
  {
    Action oldAction = action;
    action = newAction;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ThingMLPackage.CONDITIONAL_ACTION__ACTION, oldAction, newAction);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAction(Action newAction)
  {
    if (newAction != action)
    {
      NotificationChain msgs = null;
      if (action != null)
        msgs = ((InternalEObject)action).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ThingMLPackage.CONDITIONAL_ACTION__ACTION, null, msgs);
      if (newAction != null)
        msgs = ((InternalEObject)newAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ThingMLPackage.CONDITIONAL_ACTION__ACTION, null, msgs);
      msgs = basicSetAction(newAction, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.CONDITIONAL_ACTION__ACTION, newAction, newAction));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Action getElseAction()
  {
    return elseAction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetElseAction(Action newElseAction, NotificationChain msgs)
  {
    Action oldElseAction = elseAction;
    elseAction = newElseAction;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ThingMLPackage.CONDITIONAL_ACTION__ELSE_ACTION, oldElseAction, newElseAction);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setElseAction(Action newElseAction)
  {
    if (newElseAction != elseAction)
    {
      NotificationChain msgs = null;
      if (elseAction != null)
        msgs = ((InternalEObject)elseAction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ThingMLPackage.CONDITIONAL_ACTION__ELSE_ACTION, null, msgs);
      if (newElseAction != null)
        msgs = ((InternalEObject)newElseAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ThingMLPackage.CONDITIONAL_ACTION__ELSE_ACTION, null, msgs);
      msgs = basicSetElseAction(newElseAction, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.CONDITIONAL_ACTION__ELSE_ACTION, newElseAction, newElseAction));
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
      case ThingMLPackage.CONDITIONAL_ACTION__CONDITION:
        return basicSetCondition(null, msgs);
      case ThingMLPackage.CONDITIONAL_ACTION__ACTION:
        return basicSetAction(null, msgs);
      case ThingMLPackage.CONDITIONAL_ACTION__ELSE_ACTION:
        return basicSetElseAction(null, msgs);
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
      case ThingMLPackage.CONDITIONAL_ACTION__CONDITION:
        return getCondition();
      case ThingMLPackage.CONDITIONAL_ACTION__ACTION:
        return getAction();
      case ThingMLPackage.CONDITIONAL_ACTION__ELSE_ACTION:
        return getElseAction();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case ThingMLPackage.CONDITIONAL_ACTION__CONDITION:
        setCondition((Expression)newValue);
        return;
      case ThingMLPackage.CONDITIONAL_ACTION__ACTION:
        setAction((Action)newValue);
        return;
      case ThingMLPackage.CONDITIONAL_ACTION__ELSE_ACTION:
        setElseAction((Action)newValue);
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
      case ThingMLPackage.CONDITIONAL_ACTION__CONDITION:
        setCondition((Expression)null);
        return;
      case ThingMLPackage.CONDITIONAL_ACTION__ACTION:
        setAction((Action)null);
        return;
      case ThingMLPackage.CONDITIONAL_ACTION__ELSE_ACTION:
        setElseAction((Action)null);
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
      case ThingMLPackage.CONDITIONAL_ACTION__CONDITION:
        return condition != null;
      case ThingMLPackage.CONDITIONAL_ACTION__ACTION:
        return action != null;
      case ThingMLPackage.CONDITIONAL_ACTION__ELSE_ACTION:
        return elseAction != null;
    }
    return super.eIsSet(featureID);
  }

} //ConditionalActionImpl
