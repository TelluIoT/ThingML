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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.sintef.thingml.EventReference;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.ReceiveMessage;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.EventReferenceImpl#getMsgRef <em>Msg Ref</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.EventReferenceImpl#getParamRef <em>Param Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventReferenceImpl extends ExpressionImpl implements EventReference {
	/**
	 * The cached value of the '{@link #getMsgRef() <em>Msg Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMsgRef()
	 * @generated
	 * @ordered
	 */
	protected ReceiveMessage msgRef;

	/**
	 * The cached value of the '{@link #getParamRef() <em>Param Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParamRef()
	 * @generated
	 * @ordered
	 */
	protected Parameter paramRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.EVENT_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReceiveMessage getMsgRef() {
		if (msgRef != null && msgRef.eIsProxy()) {
			InternalEObject oldMsgRef = (InternalEObject)msgRef;
			msgRef = (ReceiveMessage)eResolveProxy(oldMsgRef);
			if (msgRef != oldMsgRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.EVENT_REFERENCE__MSG_REF, oldMsgRef, msgRef));
			}
		}
		return msgRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReceiveMessage basicGetMsgRef() {
		return msgRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMsgRef(ReceiveMessage newMsgRef) {
		ReceiveMessage oldMsgRef = msgRef;
		msgRef = newMsgRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.EVENT_REFERENCE__MSG_REF, oldMsgRef, msgRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter getParamRef() {
		if (paramRef != null && paramRef.eIsProxy()) {
			InternalEObject oldParamRef = (InternalEObject)paramRef;
			paramRef = (Parameter)eResolveProxy(oldParamRef);
			if (paramRef != oldParamRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.EVENT_REFERENCE__PARAM_REF, oldParamRef, paramRef));
			}
		}
		return paramRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter basicGetParamRef() {
		return paramRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParamRef(Parameter newParamRef) {
		Parameter oldParamRef = paramRef;
		paramRef = newParamRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.EVENT_REFERENCE__PARAM_REF, oldParamRef, paramRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ThingmlPackage.EVENT_REFERENCE__MSG_REF:
				if (resolve) return getMsgRef();
				return basicGetMsgRef();
			case ThingmlPackage.EVENT_REFERENCE__PARAM_REF:
				if (resolve) return getParamRef();
				return basicGetParamRef();
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
			case ThingmlPackage.EVENT_REFERENCE__MSG_REF:
				setMsgRef((ReceiveMessage)newValue);
				return;
			case ThingmlPackage.EVENT_REFERENCE__PARAM_REF:
				setParamRef((Parameter)newValue);
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
			case ThingmlPackage.EVENT_REFERENCE__MSG_REF:
				setMsgRef((ReceiveMessage)null);
				return;
			case ThingmlPackage.EVENT_REFERENCE__PARAM_REF:
				setParamRef((Parameter)null);
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
			case ThingmlPackage.EVENT_REFERENCE__MSG_REF:
				return msgRef != null;
			case ThingmlPackage.EVENT_REFERENCE__PARAM_REF:
				return paramRef != null;
		}
		return super.eIsSet(featureID);
	}

} //EventReferenceImpl
