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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.StartSession;
import org.thingml.xtext.thingML.ThingMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Start Session</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.impl.StartSessionImpl#getSession <em>Session</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StartSessionImpl extends ActionImpl implements StartSession
{
  /**
   * The cached value of the '{@link #getSession() <em>Session</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSession()
   * @generated
   * @ordered
   */
  protected Session session;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected StartSessionImpl()
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
    return ThingMLPackage.Literals.START_SESSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Session getSession()
  {
    if (session != null && session.eIsProxy())
    {
      InternalEObject oldSession = (InternalEObject)session;
      session = (Session)eResolveProxy(oldSession);
      if (session != oldSession)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingMLPackage.START_SESSION__SESSION, oldSession, session));
      }
    }
    return session;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Session basicGetSession()
  {
    return session;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSession(Session newSession)
  {
    Session oldSession = session;
    session = newSession;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.START_SESSION__SESSION, oldSession, session));
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
      case ThingMLPackage.START_SESSION__SESSION:
        if (resolve) return getSession();
        return basicGetSession();
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
      case ThingMLPackage.START_SESSION__SESSION:
        setSession((Session)newValue);
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
      case ThingMLPackage.START_SESSION__SESSION:
        setSession((Session)null);
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
      case ThingMLPackage.START_SESSION__SESSION:
        return session != null;
    }
    return super.eIsSet(featureID);
  }

} //StartSessionImpl
