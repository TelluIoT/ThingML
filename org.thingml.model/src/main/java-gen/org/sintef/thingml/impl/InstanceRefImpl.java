/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.sintef.thingml.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.sintef.thingml.Instance;
import org.sintef.thingml.InstanceRef;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Instance Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.InstanceRefImpl#getInstance <em>Instance</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InstanceRefImpl extends EObjectImpl implements InstanceRef {
	/**
	 * The cached value of the '{@link #getInstance() <em>Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstance()
	 * @generated
	 * @ordered
	 */
	protected Instance instance;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstanceRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.INSTANCE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Instance getInstance() {
		if (instance != null && instance.eIsProxy()) {
			InternalEObject oldInstance = (InternalEObject)instance;
			instance = (Instance)eResolveProxy(oldInstance);
			if (instance != oldInstance) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.INSTANCE_REF__INSTANCE, oldInstance, instance));
			}
		}
		return instance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Instance basicGetInstance() {
		return instance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstance(Instance newInstance) {
		Instance oldInstance = instance;
		instance = newInstance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.INSTANCE_REF__INSTANCE, oldInstance, instance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ThingmlPackage.INSTANCE_REF__INSTANCE:
				if (resolve) return getInstance();
				return basicGetInstance();
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
			case ThingmlPackage.INSTANCE_REF__INSTANCE:
				setInstance((Instance)newValue);
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
			case ThingmlPackage.INSTANCE_REF__INSTANCE:
				setInstance((Instance)null);
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
			case ThingmlPackage.INSTANCE_REF__INSTANCE:
				return instance != null;
		}
		return super.eIsSet(featureID);
	}

} //InstanceRefImpl
