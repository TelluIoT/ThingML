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

import org.sintef.thingml.Source;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.ViewSource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>View Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.ViewSourceImpl#getSourceViewed <em>Source Viewed</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ViewSourceImpl extends SourceImpl implements ViewSource {
	/**
	 * The cached value of the '{@link #getSourceViewed() <em>Source Viewed</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceViewed()
	 * @generated
	 * @ordered
	 */
	protected Source sourceViewed;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ViewSourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.VIEW_SOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Source getSourceViewed() {
		if (sourceViewed != null && sourceViewed.eIsProxy()) {
			InternalEObject oldSourceViewed = (InternalEObject)sourceViewed;
			sourceViewed = (Source)eResolveProxy(oldSourceViewed);
			if (sourceViewed != oldSourceViewed) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.VIEW_SOURCE__SOURCE_VIEWED, oldSourceViewed, sourceViewed));
			}
		}
		return sourceViewed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Source basicGetSourceViewed() {
		return sourceViewed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceViewed(Source newSourceViewed) {
		Source oldSourceViewed = sourceViewed;
		sourceViewed = newSourceViewed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.VIEW_SOURCE__SOURCE_VIEWED, oldSourceViewed, sourceViewed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ThingmlPackage.VIEW_SOURCE__SOURCE_VIEWED:
				if (resolve) return getSourceViewed();
				return basicGetSourceViewed();
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
			case ThingmlPackage.VIEW_SOURCE__SOURCE_VIEWED:
				setSourceViewed((Source)newValue);
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
			case ThingmlPackage.VIEW_SOURCE__SOURCE_VIEWED:
				setSourceViewed((Source)null);
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
			case ThingmlPackage.VIEW_SOURCE__SOURCE_VIEWED:
				return sourceViewed != null;
		}
		return super.eIsSet(featureID);
	}

} //ViewSourceImpl
