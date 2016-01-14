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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.sintef.thingml.ReceiveMessage;
import org.sintef.thingml.SglMsgParamOperator;
import org.sintef.thingml.SglMsgParamOperatorCall;
import org.sintef.thingml.Source;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sgl Msg Param Operator Call</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.SglMsgParamOperatorCallImpl#getOperatorRef <em>Operator Ref</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.SglMsgParamOperatorCallImpl#getParameter <em>Parameter</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SglMsgParamOperatorCallImpl extends EObjectImpl implements SglMsgParamOperatorCall {
	/**
	 * The cached value of the '{@link #getOperatorRef() <em>Operator Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperatorRef()
	 * @generated
	 * @ordered
	 */
	protected SglMsgParamOperator operatorRef;

	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected Source parameter;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SglMsgParamOperatorCallImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.SGL_MSG_PARAM_OPERATOR_CALL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SglMsgParamOperator getOperatorRef() {
		if (operatorRef != null && operatorRef.eIsProxy()) {
			InternalEObject oldOperatorRef = (InternalEObject)operatorRef;
			operatorRef = (SglMsgParamOperator)eResolveProxy(oldOperatorRef);
			if (operatorRef != oldOperatorRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__OPERATOR_REF, oldOperatorRef, operatorRef));
			}
		}
		return operatorRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SglMsgParamOperator basicGetOperatorRef() {
		return operatorRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperatorRef(SglMsgParamOperator newOperatorRef) {
		SglMsgParamOperator oldOperatorRef = operatorRef;
		operatorRef = newOperatorRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__OPERATOR_REF, oldOperatorRef, operatorRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Source getParameter() {
		if (parameter != null && parameter.eIsProxy()) {
			InternalEObject oldParameter = (InternalEObject)parameter;
			parameter = (Source)eResolveProxy(oldParameter);
			if (parameter != oldParameter) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__PARAMETER, oldParameter, parameter));
			}
		}
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Source basicGetParameter() {
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParameter(Source newParameter) {
		Source oldParameter = parameter;
		parameter = newParameter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__PARAMETER, oldParameter, parameter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__OPERATOR_REF:
				if (resolve) return getOperatorRef();
				return basicGetOperatorRef();
			case ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__PARAMETER:
				if (resolve) return getParameter();
				return basicGetParameter();
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
			case ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__OPERATOR_REF:
				setOperatorRef((SglMsgParamOperator)newValue);
				return;
			case ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__PARAMETER:
				setParameter((Source)newValue);
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
			case ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__OPERATOR_REF:
				setOperatorRef((SglMsgParamOperator)null);
				return;
			case ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__PARAMETER:
				setParameter((Source)null);
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
			case ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__OPERATOR_REF:
				return operatorRef != null;
			case ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL__PARAMETER:
				return parameter != null;
		}
		return super.eIsSet(featureID);
	}

} //SglMsgParamOperatorCallImpl
