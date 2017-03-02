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

import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.ProvidedPort;
import org.thingml.xtext.thingML.RequiredPort;
import org.thingml.xtext.thingML.ThingMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.impl.ConnectorImpl#getCli <em>Cli</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ConnectorImpl#getRequired <em>Required</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ConnectorImpl#getSrv <em>Srv</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ConnectorImpl#getProvided <em>Provided</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConnectorImpl extends AbstractConnectorImpl implements Connector
{
  /**
   * The cached value of the '{@link #getCli() <em>Cli</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCli()
   * @generated
   * @ordered
   */
  protected Instance cli;

  /**
   * The cached value of the '{@link #getRequired() <em>Required</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRequired()
   * @generated
   * @ordered
   */
  protected RequiredPort required;

  /**
   * The cached value of the '{@link #getSrv() <em>Srv</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSrv()
   * @generated
   * @ordered
   */
  protected Instance srv;

  /**
   * The cached value of the '{@link #getProvided() <em>Provided</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProvided()
   * @generated
   * @ordered
   */
  protected ProvidedPort provided;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ConnectorImpl()
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
    return ThingMLPackage.Literals.CONNECTOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Instance getCli()
  {
    if (cli != null && cli.eIsProxy())
    {
      InternalEObject oldCli = (InternalEObject)cli;
      cli = (Instance)eResolveProxy(oldCli);
      if (cli != oldCli)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingMLPackage.CONNECTOR__CLI, oldCli, cli));
      }
    }
    return cli;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Instance basicGetCli()
  {
    return cli;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCli(Instance newCli)
  {
    Instance oldCli = cli;
    cli = newCli;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.CONNECTOR__CLI, oldCli, cli));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RequiredPort getRequired()
  {
    if (required != null && required.eIsProxy())
    {
      InternalEObject oldRequired = (InternalEObject)required;
      required = (RequiredPort)eResolveProxy(oldRequired);
      if (required != oldRequired)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingMLPackage.CONNECTOR__REQUIRED, oldRequired, required));
      }
    }
    return required;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RequiredPort basicGetRequired()
  {
    return required;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRequired(RequiredPort newRequired)
  {
    RequiredPort oldRequired = required;
    required = newRequired;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.CONNECTOR__REQUIRED, oldRequired, required));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Instance getSrv()
  {
    if (srv != null && srv.eIsProxy())
    {
      InternalEObject oldSrv = (InternalEObject)srv;
      srv = (Instance)eResolveProxy(oldSrv);
      if (srv != oldSrv)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingMLPackage.CONNECTOR__SRV, oldSrv, srv));
      }
    }
    return srv;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Instance basicGetSrv()
  {
    return srv;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSrv(Instance newSrv)
  {
    Instance oldSrv = srv;
    srv = newSrv;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.CONNECTOR__SRV, oldSrv, srv));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ProvidedPort getProvided()
  {
    if (provided != null && provided.eIsProxy())
    {
      InternalEObject oldProvided = (InternalEObject)provided;
      provided = (ProvidedPort)eResolveProxy(oldProvided);
      if (provided != oldProvided)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingMLPackage.CONNECTOR__PROVIDED, oldProvided, provided));
      }
    }
    return provided;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ProvidedPort basicGetProvided()
  {
    return provided;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setProvided(ProvidedPort newProvided)
  {
    ProvidedPort oldProvided = provided;
    provided = newProvided;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.CONNECTOR__PROVIDED, oldProvided, provided));
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
      case ThingMLPackage.CONNECTOR__CLI:
        if (resolve) return getCli();
        return basicGetCli();
      case ThingMLPackage.CONNECTOR__REQUIRED:
        if (resolve) return getRequired();
        return basicGetRequired();
      case ThingMLPackage.CONNECTOR__SRV:
        if (resolve) return getSrv();
        return basicGetSrv();
      case ThingMLPackage.CONNECTOR__PROVIDED:
        if (resolve) return getProvided();
        return basicGetProvided();
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
      case ThingMLPackage.CONNECTOR__CLI:
        setCli((Instance)newValue);
        return;
      case ThingMLPackage.CONNECTOR__REQUIRED:
        setRequired((RequiredPort)newValue);
        return;
      case ThingMLPackage.CONNECTOR__SRV:
        setSrv((Instance)newValue);
        return;
      case ThingMLPackage.CONNECTOR__PROVIDED:
        setProvided((ProvidedPort)newValue);
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
      case ThingMLPackage.CONNECTOR__CLI:
        setCli((Instance)null);
        return;
      case ThingMLPackage.CONNECTOR__REQUIRED:
        setRequired((RequiredPort)null);
        return;
      case ThingMLPackage.CONNECTOR__SRV:
        setSrv((Instance)null);
        return;
      case ThingMLPackage.CONNECTOR__PROVIDED:
        setProvided((ProvidedPort)null);
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
      case ThingMLPackage.CONNECTOR__CLI:
        return cli != null;
      case ThingMLPackage.CONNECTOR__REQUIRED:
        return required != null;
      case ThingMLPackage.CONNECTOR__SRV:
        return srv != null;
      case ThingMLPackage.CONNECTOR__PROVIDED:
        return provided != null;
    }
    return super.eIsSet(featureID);
  }

} //ConnectorImpl
