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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sintef.thingml.Action;
import org.sintef.thingml.ReceiveMessage;
import org.sintef.thingml.SendAction;
import org.sintef.thingml.Stream;
import org.sintef.thingml.StreamExpression;
import org.sintef.thingml.StreamOutput;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Stream</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.StreamImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.StreamImpl#isFinalStream <em>Final Stream</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.StreamImpl#getSelection <em>Selection</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.StreamImpl#getOutput <em>Output</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StreamImpl extends ThingMLElementImpl implements Stream {
	/**
	 * The cached value of the '{@link #getInputs() <em>Inputs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<ReceiveMessage> inputs;

	/**
	 * The default value of the '{@link #isFinalStream() <em>Final Stream</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFinalStream()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FINAL_STREAM_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isFinalStream() <em>Final Stream</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFinalStream()
	 * @generated
	 * @ordered
	 */
	protected boolean finalStream = FINAL_STREAM_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSelection() <em>Selection</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelection()
	 * @generated
	 * @ordered
	 */
	protected EList<StreamExpression> selection;

	/**
	 * The cached value of the '{@link #getOutput() <em>Output</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutput()
	 * @generated
	 * @ordered
	 */
	protected StreamOutput output;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StreamImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.STREAM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ReceiveMessage> getInputs() {
		if (inputs == null) {
			inputs = new EObjectContainmentEList<ReceiveMessage>(ReceiveMessage.class, this, ThingmlPackage.STREAM__INPUTS);
		}
		return inputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamOutput getOutput() {
		return output;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOutput(StreamOutput newOutput, NotificationChain msgs) {
		StreamOutput oldOutput = output;
		output = newOutput;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ThingmlPackage.STREAM__OUTPUT, oldOutput, newOutput);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutput(StreamOutput newOutput) {
		if (newOutput != output) {
			NotificationChain msgs = null;
			if (output != null)
				msgs = ((InternalEObject)output).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.STREAM__OUTPUT, null, msgs);
			if (newOutput != null)
				msgs = ((InternalEObject)newOutput).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.STREAM__OUTPUT, null, msgs);
			msgs = basicSetOutput(newOutput, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.STREAM__OUTPUT, newOutput, newOutput));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFinalStream() {
		return finalStream;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFinalStream(boolean newFinalStream) {
		boolean oldFinalStream = finalStream;
		finalStream = newFinalStream;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.STREAM__FINAL_STREAM, oldFinalStream, finalStream));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StreamExpression> getSelection() {
		if (selection == null) {
			selection = new EObjectContainmentEList<StreamExpression>(StreamExpression.class, this, ThingmlPackage.STREAM__SELECTION);
		}
		return selection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ThingmlPackage.STREAM__INPUTS:
				return ((InternalEList<?>)getInputs()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.STREAM__SELECTION:
				return ((InternalEList<?>)getSelection()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.STREAM__OUTPUT:
				return basicSetOutput(null, msgs);
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
			case ThingmlPackage.STREAM__INPUTS:
				return getInputs();
			case ThingmlPackage.STREAM__FINAL_STREAM:
				return isFinalStream();
			case ThingmlPackage.STREAM__SELECTION:
				return getSelection();
			case ThingmlPackage.STREAM__OUTPUT:
				return getOutput();
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
			case ThingmlPackage.STREAM__INPUTS:
				getInputs().clear();
				getInputs().addAll((Collection<? extends ReceiveMessage>)newValue);
				return;
			case ThingmlPackage.STREAM__FINAL_STREAM:
				setFinalStream((Boolean)newValue);
				return;
			case ThingmlPackage.STREAM__SELECTION:
				getSelection().clear();
				getSelection().addAll((Collection<? extends StreamExpression>)newValue);
				return;
			case ThingmlPackage.STREAM__OUTPUT:
				setOutput((StreamOutput)newValue);
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
			case ThingmlPackage.STREAM__INPUTS:
				getInputs().clear();
				return;
			case ThingmlPackage.STREAM__FINAL_STREAM:
				setFinalStream(FINAL_STREAM_EDEFAULT);
				return;
			case ThingmlPackage.STREAM__SELECTION:
				getSelection().clear();
				return;
			case ThingmlPackage.STREAM__OUTPUT:
				setOutput((StreamOutput)null);
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
			case ThingmlPackage.STREAM__INPUTS:
				return inputs != null && !inputs.isEmpty();
			case ThingmlPackage.STREAM__FINAL_STREAM:
				return finalStream != FINAL_STREAM_EDEFAULT;
			case ThingmlPackage.STREAM__SELECTION:
				return selection != null && !selection.isEmpty();
			case ThingmlPackage.STREAM__OUTPUT:
				return output != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (finalStream: ");
		result.append(finalStream);
		result.append(')');
		return result.toString();
	}

} //StreamImpl
