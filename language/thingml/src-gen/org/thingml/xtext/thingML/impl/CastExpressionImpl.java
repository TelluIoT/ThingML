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

import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ThingMLPackage;
import org.thingml.xtext.thingML.Type;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cast Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.impl.CastExpressionImpl#getTerm <em>Term</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.CastExpressionImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.CastExpressionImpl#isIsArray <em>Is Array</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CastExpressionImpl extends ExpressionImpl implements CastExpression
{
  /**
   * The cached value of the '{@link #getTerm() <em>Term</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTerm()
   * @generated
   * @ordered
   */
  protected Expression term;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected Type type;

  /**
   * The default value of the '{@link #isIsArray() <em>Is Array</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsArray()
   * @generated
   * @ordered
   */
  protected static final boolean IS_ARRAY_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isIsArray() <em>Is Array</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsArray()
   * @generated
   * @ordered
   */
  protected boolean isArray = IS_ARRAY_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CastExpressionImpl()
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
    return ThingMLPackage.Literals.CAST_EXPRESSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getTerm()
  {
    return term;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTerm(Expression newTerm, NotificationChain msgs)
  {
    Expression oldTerm = term;
    term = newTerm;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ThingMLPackage.CAST_EXPRESSION__TERM, oldTerm, newTerm);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTerm(Expression newTerm)
  {
    if (newTerm != term)
    {
      NotificationChain msgs = null;
      if (term != null)
        msgs = ((InternalEObject)term).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ThingMLPackage.CAST_EXPRESSION__TERM, null, msgs);
      if (newTerm != null)
        msgs = ((InternalEObject)newTerm).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ThingMLPackage.CAST_EXPRESSION__TERM, null, msgs);
      msgs = basicSetTerm(newTerm, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.CAST_EXPRESSION__TERM, newTerm, newTerm));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type getType()
  {
    if (type != null && type.eIsProxy())
    {
      InternalEObject oldType = (InternalEObject)type;
      type = (Type)eResolveProxy(oldType);
      if (type != oldType)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingMLPackage.CAST_EXPRESSION__TYPE, oldType, type));
      }
    }
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type basicGetType()
  {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(Type newType)
  {
    Type oldType = type;
    type = newType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.CAST_EXPRESSION__TYPE, oldType, type));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isIsArray()
  {
    return isArray;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIsArray(boolean newIsArray)
  {
    boolean oldIsArray = isArray;
    isArray = newIsArray;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.CAST_EXPRESSION__IS_ARRAY, oldIsArray, isArray));
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
      case ThingMLPackage.CAST_EXPRESSION__TERM:
        return basicSetTerm(null, msgs);
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
      case ThingMLPackage.CAST_EXPRESSION__TERM:
        return getTerm();
      case ThingMLPackage.CAST_EXPRESSION__TYPE:
        if (resolve) return getType();
        return basicGetType();
      case ThingMLPackage.CAST_EXPRESSION__IS_ARRAY:
        return isIsArray();
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
      case ThingMLPackage.CAST_EXPRESSION__TERM:
        setTerm((Expression)newValue);
        return;
      case ThingMLPackage.CAST_EXPRESSION__TYPE:
        setType((Type)newValue);
        return;
      case ThingMLPackage.CAST_EXPRESSION__IS_ARRAY:
        setIsArray((Boolean)newValue);
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
      case ThingMLPackage.CAST_EXPRESSION__TERM:
        setTerm((Expression)null);
        return;
      case ThingMLPackage.CAST_EXPRESSION__TYPE:
        setType((Type)null);
        return;
      case ThingMLPackage.CAST_EXPRESSION__IS_ARRAY:
        setIsArray(IS_ARRAY_EDEFAULT);
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
      case ThingMLPackage.CAST_EXPRESSION__TERM:
        return term != null;
      case ThingMLPackage.CAST_EXPRESSION__TYPE:
        return type != null;
      case ThingMLPackage.CAST_EXPRESSION__IS_ARRAY:
        return isArray != IS_ARRAY_EDEFAULT;
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
    result.append(" (isArray: ");
    result.append(isArray);
    result.append(')');
    return result.toString();
  }

} //CastExpressionImpl
