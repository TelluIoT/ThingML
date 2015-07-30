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

import org.sintef.thingml.MessageParameter;
import org.sintef.thingml.MessageReference;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Message Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.MessageReferenceImpl#getMsgReference <em>Msg Reference</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.MessageReferenceImpl#getParamRef <em>Param Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MessageReferenceImpl extends ExpressionImpl implements MessageReference {
	/**
	 * The cached value of the '{@link #getMsgReference() <em>Msg Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMsgReference()
	 * @generated
	 * @ordered
	 */
	protected MessageParameter msgReference;

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
	protected MessageReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.MESSAGE_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MessageParameter getMsgReference() {
		if (msgReference != null && msgReference.eIsProxy()) {
			InternalEObject oldMsgReference = (InternalEObject)msgReference;
			msgReference = (MessageParameter)eResolveProxy(oldMsgReference);
			if (msgReference != oldMsgReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.MESSAGE_REFERENCE__MSG_REFERENCE, oldMsgReference, msgReference));
			}
		}
		return msgReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MessageParameter basicGetMsgReference() {
		return msgReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMsgReference(MessageParameter newMsgReference) {
		MessageParameter oldMsgReference = msgReference;
		msgReference = newMsgReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.MESSAGE_REFERENCE__MSG_REFERENCE, oldMsgReference, msgReference));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.MESSAGE_REFERENCE__PARAM_REF, oldParamRef, paramRef));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.MESSAGE_REFERENCE__PARAM_REF, oldParamRef, paramRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ThingmlPackage.MESSAGE_REFERENCE__MSG_REFERENCE:
				if (resolve) return getMsgReference();
				return basicGetMsgReference();
			case ThingmlPackage.MESSAGE_REFERENCE__PARAM_REF:
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
			case ThingmlPackage.MESSAGE_REFERENCE__MSG_REFERENCE:
				setMsgReference((MessageParameter)newValue);
				return;
			case ThingmlPackage.MESSAGE_REFERENCE__PARAM_REF:
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
			case ThingmlPackage.MESSAGE_REFERENCE__MSG_REFERENCE:
				setMsgReference((MessageParameter)null);
				return;
			case ThingmlPackage.MESSAGE_REFERENCE__PARAM_REF:
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
			case ThingmlPackage.MESSAGE_REFERENCE__MSG_REFERENCE:
				return msgReference != null;
			case ThingmlPackage.MESSAGE_REFERENCE__PARAM_REF:
				return paramRef != null;
		}
		return super.eIsSet(featureID);
	}

} //MessageReferenceImpl
