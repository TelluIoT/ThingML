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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sintef.thingml.Expression;
import org.sintef.thingml.Message;
import org.sintef.thingml.Source;
import org.sintef.thingml.SourceComposition;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Source Composition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.SourceCompositionImpl#getSources <em>Sources</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.SourceCompositionImpl#getResultMessage <em>Result Message</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.SourceCompositionImpl#getRules <em>Rules</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SourceCompositionImpl extends SourceImpl implements SourceComposition {
	/**
	 * The cached value of the '{@link #getSources() <em>Sources</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSources()
	 * @generated
	 * @ordered
	 */
	protected EList<Source> sources;

	/**
	 * The cached value of the '{@link #getResultMessage() <em>Result Message</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultMessage()
	 * @generated
	 * @ordered
	 */
	protected Message resultMessage;
	/**
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> rules;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SourceCompositionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.SOURCE_COMPOSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Source> getSources() {
		if (sources == null) {
			sources = new EObjectContainmentEList<Source>(Source.class, this, ThingmlPackage.SOURCE_COMPOSITION__SOURCES);
		}
		return sources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message getResultMessage() {
		if (resultMessage != null && resultMessage.eIsProxy()) {
			InternalEObject oldResultMessage = (InternalEObject)resultMessage;
			resultMessage = (Message)eResolveProxy(oldResultMessage);
			if (resultMessage != oldResultMessage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.SOURCE_COMPOSITION__RESULT_MESSAGE, oldResultMessage, resultMessage));
			}
		}
		return resultMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message basicGetResultMessage() {
		return resultMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResultMessage(Message newResultMessage) {
		Message oldResultMessage = resultMessage;
		resultMessage = newResultMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.SOURCE_COMPOSITION__RESULT_MESSAGE, oldResultMessage, resultMessage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Expression> getRules() {
		if (rules == null) {
			rules = new EObjectContainmentEList<Expression>(Expression.class, this, ThingmlPackage.SOURCE_COMPOSITION__RULES);
		}
		return rules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ThingmlPackage.SOURCE_COMPOSITION__SOURCES:
				return ((InternalEList<?>)getSources()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.SOURCE_COMPOSITION__RULES:
				return ((InternalEList<?>)getRules()).basicRemove(otherEnd, msgs);
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
			case ThingmlPackage.SOURCE_COMPOSITION__SOURCES:
				return getSources();
			case ThingmlPackage.SOURCE_COMPOSITION__RESULT_MESSAGE:
				if (resolve) return getResultMessage();
				return basicGetResultMessage();
			case ThingmlPackage.SOURCE_COMPOSITION__RULES:
				return getRules();
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
			case ThingmlPackage.SOURCE_COMPOSITION__SOURCES:
				getSources().clear();
				getSources().addAll((Collection<? extends Source>)newValue);
				return;
			case ThingmlPackage.SOURCE_COMPOSITION__RESULT_MESSAGE:
				setResultMessage((Message)newValue);
				return;
			case ThingmlPackage.SOURCE_COMPOSITION__RULES:
				getRules().clear();
				getRules().addAll((Collection<? extends Expression>)newValue);
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
			case ThingmlPackage.SOURCE_COMPOSITION__SOURCES:
				getSources().clear();
				return;
			case ThingmlPackage.SOURCE_COMPOSITION__RESULT_MESSAGE:
				setResultMessage((Message)null);
				return;
			case ThingmlPackage.SOURCE_COMPOSITION__RULES:
				getRules().clear();
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
			case ThingmlPackage.SOURCE_COMPOSITION__SOURCES:
				return sources != null && !sources.isEmpty();
			case ThingmlPackage.SOURCE_COMPOSITION__RESULT_MESSAGE:
				return resultMessage != null;
			case ThingmlPackage.SOURCE_COMPOSITION__RULES:
				return rules != null && !rules.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SourceCompositionImpl
