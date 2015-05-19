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

import org.sintef.thingml.Parameter;
import org.sintef.thingml.SimpleStream;
import org.sintef.thingml.SimpleStreamReference;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Stream Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.SimpleStreamReferenceImpl#getParamRef <em>Param Ref</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.SimpleStreamReferenceImpl#getStreamRef <em>Stream Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimpleStreamReferenceImpl extends ExpressionImpl implements SimpleStreamReference {
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
	 * The cached value of the '{@link #getStreamRef() <em>Stream Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStreamRef()
	 * @generated
	 * @ordered
	 */
	protected SimpleStream streamRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimpleStreamReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.SIMPLE_STREAM_REFERENCE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.SIMPLE_STREAM_REFERENCE__PARAM_REF, oldParamRef, paramRef));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.SIMPLE_STREAM_REFERENCE__PARAM_REF, oldParamRef, paramRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleStream getStreamRef() {
		if (streamRef != null && streamRef.eIsProxy()) {
			InternalEObject oldStreamRef = (InternalEObject)streamRef;
			streamRef = (SimpleStream)eResolveProxy(oldStreamRef);
			if (streamRef != oldStreamRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.SIMPLE_STREAM_REFERENCE__STREAM_REF, oldStreamRef, streamRef));
			}
		}
		return streamRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleStream basicGetStreamRef() {
		return streamRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStreamRef(SimpleStream newStreamRef) {
		SimpleStream oldStreamRef = streamRef;
		streamRef = newStreamRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.SIMPLE_STREAM_REFERENCE__STREAM_REF, oldStreamRef, streamRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ThingmlPackage.SIMPLE_STREAM_REFERENCE__PARAM_REF:
				if (resolve) return getParamRef();
				return basicGetParamRef();
			case ThingmlPackage.SIMPLE_STREAM_REFERENCE__STREAM_REF:
				if (resolve) return getStreamRef();
				return basicGetStreamRef();
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
			case ThingmlPackage.SIMPLE_STREAM_REFERENCE__PARAM_REF:
				setParamRef((Parameter)newValue);
				return;
			case ThingmlPackage.SIMPLE_STREAM_REFERENCE__STREAM_REF:
				setStreamRef((SimpleStream)newValue);
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
			case ThingmlPackage.SIMPLE_STREAM_REFERENCE__PARAM_REF:
				setParamRef((Parameter)null);
				return;
			case ThingmlPackage.SIMPLE_STREAM_REFERENCE__STREAM_REF:
				setStreamRef((SimpleStream)null);
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
			case ThingmlPackage.SIMPLE_STREAM_REFERENCE__PARAM_REF:
				return paramRef != null;
			case ThingmlPackage.SIMPLE_STREAM_REFERENCE__STREAM_REF:
				return streamRef != null;
		}
		return super.eIsSet(featureID);
	}

} //SimpleStreamReferenceImpl
