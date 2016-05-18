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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.sintef.thingml.Connector;
import org.sintef.thingml.InstanceRef;
import org.sintef.thingml.ProvidedPort;
import org.sintef.thingml.RequiredPort;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.ConnectorImpl#getSrv <em>Srv</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ConnectorImpl#getCli <em>Cli</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ConnectorImpl#getRequired <em>Required</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ConnectorImpl#getProvided <em>Provided</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConnectorImpl extends AbstractConnectorImpl implements Connector {
	/**
	 * The cached value of the '{@link #getSrv() <em>Srv</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrv()
	 * @generated
	 * @ordered
	 */
	protected InstanceRef srv;

	/**
	 * The cached value of the '{@link #getCli() <em>Cli</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCli()
	 * @generated
	 * @ordered
	 */
	protected InstanceRef cli;

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
	protected ConnectorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.CONNECTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstanceRef getSrv() {
		return srv;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSrv(InstanceRef newSrv, NotificationChain msgs) {
		InstanceRef oldSrv = srv;
		srv = newSrv;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ThingmlPackage.CONNECTOR__SRV, oldSrv, newSrv);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSrv(InstanceRef newSrv) {
		if (newSrv != srv) {
			NotificationChain msgs = null;
			if (srv != null)
				msgs = ((InternalEObject)srv).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.CONNECTOR__SRV, null, msgs);
			if (newSrv != null)
				msgs = ((InternalEObject)newSrv).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.CONNECTOR__SRV, null, msgs);
			msgs = basicSetSrv(newSrv, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.CONNECTOR__SRV, newSrv, newSrv));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstanceRef getCli() {
		return cli;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCli(InstanceRef newCli, NotificationChain msgs) {
		InstanceRef oldCli = cli;
		cli = newCli;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ThingmlPackage.CONNECTOR__CLI, oldCli, newCli);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCli(InstanceRef newCli) {
		if (newCli != cli) {
			NotificationChain msgs = null;
			if (cli != null)
				msgs = ((InternalEObject)cli).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.CONNECTOR__CLI, null, msgs);
			if (newCli != null)
				msgs = ((InternalEObject)newCli).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.CONNECTOR__CLI, null, msgs);
			msgs = basicSetCli(newCli, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.CONNECTOR__CLI, newCli, newCli));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiredPort getRequired() {
		if (required != null && required.eIsProxy()) {
			InternalEObject oldRequired = (InternalEObject)required;
			required = (RequiredPort)eResolveProxy(oldRequired);
			if (required != oldRequired) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.CONNECTOR__REQUIRED, oldRequired, required));
			}
		}
		return required;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiredPort basicGetRequired() {
		return required;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequired(RequiredPort newRequired) {
		RequiredPort oldRequired = required;
		required = newRequired;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.CONNECTOR__REQUIRED, oldRequired, required));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProvidedPort getProvided() {
		if (provided != null && provided.eIsProxy()) {
			InternalEObject oldProvided = (InternalEObject)provided;
			provided = (ProvidedPort)eResolveProxy(oldProvided);
			if (provided != oldProvided) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.CONNECTOR__PROVIDED, oldProvided, provided));
			}
		}
		return provided;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProvidedPort basicGetProvided() {
		return provided;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvided(ProvidedPort newProvided) {
		ProvidedPort oldProvided = provided;
		provided = newProvided;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.CONNECTOR__PROVIDED, oldProvided, provided));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ThingmlPackage.CONNECTOR__SRV:
				return basicSetSrv(null, msgs);
			case ThingmlPackage.CONNECTOR__CLI:
				return basicSetCli(null, msgs);
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
			case ThingmlPackage.CONNECTOR__SRV:
				return getSrv();
			case ThingmlPackage.CONNECTOR__CLI:
				return getCli();
			case ThingmlPackage.CONNECTOR__REQUIRED:
				if (resolve) return getRequired();
				return basicGetRequired();
			case ThingmlPackage.CONNECTOR__PROVIDED:
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ThingmlPackage.CONNECTOR__SRV:
				setSrv((InstanceRef)newValue);
				return;
			case ThingmlPackage.CONNECTOR__CLI:
				setCli((InstanceRef)newValue);
				return;
			case ThingmlPackage.CONNECTOR__REQUIRED:
				setRequired((RequiredPort)newValue);
				return;
			case ThingmlPackage.CONNECTOR__PROVIDED:
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case ThingmlPackage.CONNECTOR__SRV:
				setSrv((InstanceRef)null);
				return;
			case ThingmlPackage.CONNECTOR__CLI:
				setCli((InstanceRef)null);
				return;
			case ThingmlPackage.CONNECTOR__REQUIRED:
				setRequired((RequiredPort)null);
				return;
			case ThingmlPackage.CONNECTOR__PROVIDED:
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ThingmlPackage.CONNECTOR__SRV:
				return srv != null;
			case ThingmlPackage.CONNECTOR__CLI:
				return cli != null;
			case ThingmlPackage.CONNECTOR__REQUIRED:
				return required != null;
			case ThingmlPackage.CONNECTOR__PROVIDED:
				return provided != null;
		}
		return super.eIsSet(featureID);
	}

} //ConnectorImpl
