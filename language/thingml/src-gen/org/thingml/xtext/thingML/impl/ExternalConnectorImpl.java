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

import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.ThingMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>External Connector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.impl.ExternalConnectorImpl#getInst <em>Inst</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ExternalConnectorImpl#getPort <em>Port</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.impl.ExternalConnectorImpl#getProtocol <em>Protocol</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExternalConnectorImpl extends AbstractConnectorImpl implements ExternalConnector
{
  /**
   * The cached value of the '{@link #getInst() <em>Inst</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInst()
   * @generated
   * @ordered
   */
  protected Instance inst;

  /**
   * The cached value of the '{@link #getPort() <em>Port</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPort()
   * @generated
   * @ordered
   */
  protected Port port;

  /**
   * The cached value of the '{@link #getProtocol() <em>Protocol</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProtocol()
   * @generated
   * @ordered
   */
  protected Protocol protocol;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ExternalConnectorImpl()
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
    return ThingMLPackage.Literals.EXTERNAL_CONNECTOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Instance getInst()
  {
    if (inst != null && inst.eIsProxy())
    {
      InternalEObject oldInst = (InternalEObject)inst;
      inst = (Instance)eResolveProxy(oldInst);
      if (inst != oldInst)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingMLPackage.EXTERNAL_CONNECTOR__INST, oldInst, inst));
      }
    }
    return inst;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Instance basicGetInst()
  {
    return inst;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInst(Instance newInst)
  {
    Instance oldInst = inst;
    inst = newInst;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.EXTERNAL_CONNECTOR__INST, oldInst, inst));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Port getPort()
  {
    if (port != null && port.eIsProxy())
    {
      InternalEObject oldPort = (InternalEObject)port;
      port = (Port)eResolveProxy(oldPort);
      if (port != oldPort)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingMLPackage.EXTERNAL_CONNECTOR__PORT, oldPort, port));
      }
    }
    return port;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Port basicGetPort()
  {
    return port;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPort(Port newPort)
  {
    Port oldPort = port;
    port = newPort;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.EXTERNAL_CONNECTOR__PORT, oldPort, port));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Protocol getProtocol()
  {
    if (protocol != null && protocol.eIsProxy())
    {
      InternalEObject oldProtocol = (InternalEObject)protocol;
      protocol = (Protocol)eResolveProxy(oldProtocol);
      if (protocol != oldProtocol)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingMLPackage.EXTERNAL_CONNECTOR__PROTOCOL, oldProtocol, protocol));
      }
    }
    return protocol;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Protocol basicGetProtocol()
  {
    return protocol;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setProtocol(Protocol newProtocol)
  {
    Protocol oldProtocol = protocol;
    protocol = newProtocol;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ThingMLPackage.EXTERNAL_CONNECTOR__PROTOCOL, oldProtocol, protocol));
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
      case ThingMLPackage.EXTERNAL_CONNECTOR__INST:
        if (resolve) return getInst();
        return basicGetInst();
      case ThingMLPackage.EXTERNAL_CONNECTOR__PORT:
        if (resolve) return getPort();
        return basicGetPort();
      case ThingMLPackage.EXTERNAL_CONNECTOR__PROTOCOL:
        if (resolve) return getProtocol();
        return basicGetProtocol();
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
      case ThingMLPackage.EXTERNAL_CONNECTOR__INST:
        setInst((Instance)newValue);
        return;
      case ThingMLPackage.EXTERNAL_CONNECTOR__PORT:
        setPort((Port)newValue);
        return;
      case ThingMLPackage.EXTERNAL_CONNECTOR__PROTOCOL:
        setProtocol((Protocol)newValue);
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
      case ThingMLPackage.EXTERNAL_CONNECTOR__INST:
        setInst((Instance)null);
        return;
      case ThingMLPackage.EXTERNAL_CONNECTOR__PORT:
        setPort((Port)null);
        return;
      case ThingMLPackage.EXTERNAL_CONNECTOR__PROTOCOL:
        setProtocol((Protocol)null);
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
      case ThingMLPackage.EXTERNAL_CONNECTOR__INST:
        return inst != null;
      case ThingMLPackage.EXTERNAL_CONNECTOR__PORT:
        return port != null;
      case ThingMLPackage.EXTERNAL_CONNECTOR__PROTOCOL:
        return protocol != null;
    }
    return super.eIsSet(featureID);
  }

} //ExternalConnectorImpl
