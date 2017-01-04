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
import org.sintef.thingml.Decrement;
import org.sintef.thingml.Expression;
import org.sintef.thingml.PropertyReference;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Decrement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.DecrementImpl#getVar <em>Var</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DecrementImpl extends ActionImpl implements Decrement {
	/**
	 * The cached value of the '{@link #getVar() <em>Var</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVar()
	 * @generated
	 * @ordered
	 */
	protected PropertyReference var;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DecrementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.DECREMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyReference getVar() {
		return var;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVar(PropertyReference newVar, NotificationChain msgs) {
		PropertyReference oldVar = var;
		var = newVar;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ThingmlPackage.DECREMENT__VAR, oldVar, newVar);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVar(PropertyReference newVar) {
		if (newVar != var) {
			NotificationChain msgs = null;
			if (var != null)
				msgs = ((InternalEObject)var).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.DECREMENT__VAR, null, msgs);
			if (newVar != null)
				msgs = ((InternalEObject)newVar).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.DECREMENT__VAR, null, msgs);
			msgs = basicSetVar(newVar, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.DECREMENT__VAR, newVar, newVar));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ThingmlPackage.DECREMENT__VAR:
				return basicSetVar(null, msgs);
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
			case ThingmlPackage.DECREMENT__VAR:
				return getVar();
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
			case ThingmlPackage.DECREMENT__VAR:
				setVar((PropertyReference)newValue);
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
			case ThingmlPackage.DECREMENT__VAR:
				setVar((PropertyReference)null);
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
			case ThingmlPackage.DECREMENT__VAR:
				return var != null;
		}
		return super.eIsSet(featureID);
	}

} //DecrementImpl
