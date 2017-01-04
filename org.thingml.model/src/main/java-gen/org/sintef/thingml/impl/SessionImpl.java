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
/**
 */
package org.sintef.thingml.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.sintef.thingml.Expression;
import org.sintef.thingml.Session;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Session</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.SessionImpl#getMaxInstances <em>Max Instances</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SessionImpl extends CompositeStateImpl implements Session {
	/**
	 * The cached value of the '{@link #getMaxInstances() <em>Max Instances</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxInstances()
	 * @generated
	 * @ordered
	 */
	protected Expression maxInstances;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SessionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.SESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getMaxInstances() {
		return maxInstances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxInstances(Expression newMaxInstances, NotificationChain msgs) {
		Expression oldMaxInstances = maxInstances;
		maxInstances = newMaxInstances;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ThingmlPackage.SESSION__MAX_INSTANCES, oldMaxInstances, newMaxInstances);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxInstances(Expression newMaxInstances) {
		if (newMaxInstances != maxInstances) {
			NotificationChain msgs = null;
			if (maxInstances != null)
				msgs = ((InternalEObject)maxInstances).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.SESSION__MAX_INSTANCES, null, msgs);
			if (newMaxInstances != null)
				msgs = ((InternalEObject)newMaxInstances).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.SESSION__MAX_INSTANCES, null, msgs);
			msgs = basicSetMaxInstances(newMaxInstances, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.SESSION__MAX_INSTANCES, newMaxInstances, newMaxInstances));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ThingmlPackage.SESSION__MAX_INSTANCES:
				return basicSetMaxInstances(null, msgs);
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
			case ThingmlPackage.SESSION__MAX_INSTANCES:
				return getMaxInstances();
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
			case ThingmlPackage.SESSION__MAX_INSTANCES:
				setMaxInstances((Expression)newValue);
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
			case ThingmlPackage.SESSION__MAX_INSTANCES:
				setMaxInstances((Expression)null);
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
			case ThingmlPackage.SESSION__MAX_INSTANCES:
				return maxInstances != null;
		}
		return super.eIsSet(featureID);
	}

} //SessionImpl
