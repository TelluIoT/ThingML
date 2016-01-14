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
/**
 */
package org.sintef.thingml.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.sintef.thingml.Filter;
import org.sintef.thingml.OperatorCall;
import org.sintef.thingml.SglMsgParamOperatorCall;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.FilterImpl#getFilterOp <em>Filter Op</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FilterImpl extends ViewSourceImpl implements Filter {
	/**
	 * The cached value of the '{@link #getFilterOp() <em>Filter Op</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilterOp()
	 * @generated
	 * @ordered
	 */
	protected SglMsgParamOperatorCall filterOp;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FilterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.FILTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SglMsgParamOperatorCall getFilterOp() {
		return filterOp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFilterOp(SglMsgParamOperatorCall newFilterOp, NotificationChain msgs) {
		SglMsgParamOperatorCall oldFilterOp = filterOp;
		filterOp = newFilterOp;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ThingmlPackage.FILTER__FILTER_OP, oldFilterOp, newFilterOp);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFilterOp(SglMsgParamOperatorCall newFilterOp) {
		if (newFilterOp != filterOp) {
			NotificationChain msgs = null;
			if (filterOp != null)
				msgs = ((InternalEObject)filterOp).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.FILTER__FILTER_OP, null, msgs);
			if (newFilterOp != null)
				msgs = ((InternalEObject)newFilterOp).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.FILTER__FILTER_OP, null, msgs);
			msgs = basicSetFilterOp(newFilterOp, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.FILTER__FILTER_OP, newFilterOp, newFilterOp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ThingmlPackage.FILTER__FILTER_OP:
				return basicSetFilterOp(null, msgs);
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
			case ThingmlPackage.FILTER__FILTER_OP:
				return getFilterOp();
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
			case ThingmlPackage.FILTER__FILTER_OP:
				setFilterOp((SglMsgParamOperatorCall)newValue);
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
			case ThingmlPackage.FILTER__FILTER_OP:
				setFilterOp((SglMsgParamOperatorCall)null);
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
			case ThingmlPackage.FILTER__FILTER_OP:
				return filterOp != null;
		}
		return super.eIsSet(featureID);
	}

} //FilterImpl
