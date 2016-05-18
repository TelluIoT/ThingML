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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.sintef.thingml.Expression;
import org.sintef.thingml.Property;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.PropertyImpl#getInit <em>Init</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.PropertyImpl#isChangeable <em>Changeable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropertyImpl extends VariableImpl implements Property {
	/**
	 * The cached value of the '{@link #getInit() <em>Init</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInit()
	 * @generated
	 * @ordered
	 */
	protected Expression init;

	/**
	 * The default value of the '{@link #isChangeable() <em>Changeable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isChangeable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CHANGEABLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isChangeable() <em>Changeable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isChangeable()
	 * @generated
	 * @ordered
	 */
	protected boolean changeable = CHANGEABLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getInit() {
		return init;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInit(Expression newInit, NotificationChain msgs) {
		Expression oldInit = init;
		init = newInit;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ThingmlPackage.PROPERTY__INIT, oldInit, newInit);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInit(Expression newInit) {
		if (newInit != init) {
			NotificationChain msgs = null;
			if (init != null)
				msgs = ((InternalEObject)init).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.PROPERTY__INIT, null, msgs);
			if (newInit != null)
				msgs = ((InternalEObject)newInit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ThingmlPackage.PROPERTY__INIT, null, msgs);
			msgs = basicSetInit(newInit, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.PROPERTY__INIT, newInit, newInit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isChangeable() {
		return changeable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChangeable(boolean newChangeable) {
		boolean oldChangeable = changeable;
		changeable = newChangeable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.PROPERTY__CHANGEABLE, oldChangeable, changeable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ThingmlPackage.PROPERTY__INIT:
				return basicSetInit(null, msgs);
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
			case ThingmlPackage.PROPERTY__INIT:
				return getInit();
			case ThingmlPackage.PROPERTY__CHANGEABLE:
				return isChangeable();
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
			case ThingmlPackage.PROPERTY__INIT:
				setInit((Expression)newValue);
				return;
			case ThingmlPackage.PROPERTY__CHANGEABLE:
				setChangeable((Boolean)newValue);
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
			case ThingmlPackage.PROPERTY__INIT:
				setInit((Expression)null);
				return;
			case ThingmlPackage.PROPERTY__CHANGEABLE:
				setChangeable(CHANGEABLE_EDEFAULT);
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
			case ThingmlPackage.PROPERTY__INIT:
				return init != null;
			case ThingmlPackage.PROPERTY__CHANGEABLE:
				return changeable != CHANGEABLE_EDEFAULT;
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
		result.append(" (changeable: ");
		result.append(changeable);
		result.append(')');
		return result.toString();
	}

    //Derived properties

    /**
     *
     * @return
     * @generated NOT
     */
    public List<PlatformAnnotation> allAnnotations() {
        return this.getAnnotations();
    }

    /**
     *
     * @param annotation
     * @param value
     * @return
     * @generated NOT
     */
    public boolean isDefined(String annotation, String value) {
        PlatformAnnotation pa = null;
        for (PlatformAnnotation a : getAnnotations()) {
            if (a.getName().equals(annotation)) {
                pa = a;
                break;
            }
        }
        if (pa == null) {
            return false;
        } else {
            return pa.getValue().equals(value);
        }
    }

    /**
     *
     * @param name
     * @return
     * @generated NOT
     */
	public boolean hasAnnotation(String name) {
		for (PlatformAnnotation a : allAnnotations()) {
			if (a.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

    /**
     *
     * @param name
     * @return
     * @generated NOT
     */
	public List<String> annotation(String name) {
		List<String> result = new ArrayList<String>();
		for (PlatformAnnotation a : getAnnotations()) {
			if (a.getName().equals(name)) {
				result.add(a.getValue());
			}
		}
		return result;
	}

    //Derived properties

    /**
     *
     * @return
     * @generated NOT
     */
    public ThingMLModel findContainingModel() {
        return ThingMLHelpers.findContainingModel(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public Thing findContainingThing() {
        return ThingMLHelpers.findContainingThing(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public Configuration findContainingConfiguration() {
        return ThingMLHelpers.findContainingConfiguration(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public State findContainingState() {
        return ThingMLHelpers.findContainingState(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public Region findContainingRegion() {
        return ThingMLHelpers.findContainingRegion(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public Handler findContainingHandler() {
        return ThingMLHelpers.findContainingHandler(this);
    }

    /**
     *
     * @param separator
     * @return
     * @generated NOT
     */
    public String qname(String separator) {
        if (separator == null) {
            separator = "::";
        }
        String result = "";
        ThingMLElement elem  = this;
        String name = null;
        while(elem != null) {
            name = elem.getName();
            if (name == null || name == "") name = elem.getClass().getName();
            if (result == null) result = name;
            else result = name + separator + result;
            if (elem.eContainer() != null && elem.eContainer() instanceof ThingMLElement)
                elem = (ThingMLElement)elem.eContainer();
            else elem = null;
        }
        return result;
    }

} //PropertyImpl
