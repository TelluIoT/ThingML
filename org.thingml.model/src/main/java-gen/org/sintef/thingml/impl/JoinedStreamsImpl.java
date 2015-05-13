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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.sintef.thingml.JoinedStreams;
import org.sintef.thingml.Operator;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Joined Streams</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.JoinedStreamsImpl#getJoinOperator <em>Join Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JoinedStreamsImpl extends ComplexStreamImpl implements JoinedStreams {
	/**
	 * The cached value of the '{@link #getJoinOperator() <em>Join Operator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinOperator()
	 * @generated
	 * @ordered
	 */
	protected Operator joinOperator;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JoinedStreamsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.JOINED_STREAMS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operator getJoinOperator() {
		if (joinOperator != null && joinOperator.eIsProxy()) {
			InternalEObject oldJoinOperator = (InternalEObject)joinOperator;
			joinOperator = (Operator)eResolveProxy(oldJoinOperator);
			if (joinOperator != oldJoinOperator) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.JOINED_STREAMS__JOIN_OPERATOR, oldJoinOperator, joinOperator));
			}
		}
		return joinOperator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operator basicGetJoinOperator() {
		return joinOperator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJoinOperator(Operator newJoinOperator) {
		Operator oldJoinOperator = joinOperator;
		joinOperator = newJoinOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.JOINED_STREAMS__JOIN_OPERATOR, oldJoinOperator, joinOperator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ThingmlPackage.JOINED_STREAMS__JOIN_OPERATOR:
				if (resolve) return getJoinOperator();
				return basicGetJoinOperator();
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
			case ThingmlPackage.JOINED_STREAMS__JOIN_OPERATOR:
				setJoinOperator((Operator)newValue);
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
			case ThingmlPackage.JOINED_STREAMS__JOIN_OPERATOR:
				setJoinOperator((Operator)null);
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
			case ThingmlPackage.JOINED_STREAMS__JOIN_OPERATOR:
				return joinOperator != null;
		}
		return super.eIsSet(featureID);
	}

} //JoinedStreamsImpl
