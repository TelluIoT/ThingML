/**
 * <copyright>
 * </copyright>
 *
 * $Id$
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

import org.sintef.thingml.ConfigInclude;
import org.sintef.thingml.ConfigPropertyAssign;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Connector;
import org.sintef.thingml.Instance;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.ConfigurationImpl#getInstances <em>Instances</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ConfigurationImpl#getConnectors <em>Connectors</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ConfigurationImpl#isFragment <em>Fragment</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ConfigurationImpl#getConfigs <em>Configs</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ConfigurationImpl#getPropassigns <em>Propassigns</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConfigurationImpl extends AnnotatedElementImpl implements Configuration {
	/**
	 * The cached value of the '{@link #getInstances() <em>Instances</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstances()
	 * @generated
	 * @ordered
	 */
	protected EList<Instance> instances;

	/**
	 * The cached value of the '{@link #getConnectors() <em>Connectors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectors()
	 * @generated
	 * @ordered
	 */
	protected EList<Connector> connectors;

	/**
	 * The default value of the '{@link #isFragment() <em>Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFragment()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FRAGMENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFragment() <em>Fragment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFragment()
	 * @generated
	 * @ordered
	 */
	protected boolean fragment = FRAGMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConfigs() <em>Configs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigs()
	 * @generated
	 * @ordered
	 */
	protected EList<ConfigInclude> configs;

	/**
	 * The cached value of the '{@link #getPropassigns() <em>Propassigns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropassigns()
	 * @generated
	 * @ordered
	 */
	protected EList<ConfigPropertyAssign> propassigns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Instance> getInstances() {
		if (instances == null) {
			instances = new EObjectContainmentEList<Instance>(Instance.class, this, ThingmlPackage.CONFIGURATION__INSTANCES);
		}
		return instances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Connector> getConnectors() {
		if (connectors == null) {
			connectors = new EObjectContainmentEList<Connector>(Connector.class, this, ThingmlPackage.CONFIGURATION__CONNECTORS);
		}
		return connectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFragment() {
		return fragment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFragment(boolean newFragment) {
		boolean oldFragment = fragment;
		fragment = newFragment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.CONFIGURATION__FRAGMENT, oldFragment, fragment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConfigInclude> getConfigs() {
		if (configs == null) {
			configs = new EObjectContainmentEList<ConfigInclude>(ConfigInclude.class, this, ThingmlPackage.CONFIGURATION__CONFIGS);
		}
		return configs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConfigPropertyAssign> getPropassigns() {
		if (propassigns == null) {
			propassigns = new EObjectContainmentEList<ConfigPropertyAssign>(ConfigPropertyAssign.class, this, ThingmlPackage.CONFIGURATION__PROPASSIGNS);
		}
		return propassigns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ThingmlPackage.CONFIGURATION__INSTANCES:
				return ((InternalEList<?>)getInstances()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.CONFIGURATION__CONNECTORS:
				return ((InternalEList<?>)getConnectors()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.CONFIGURATION__CONFIGS:
				return ((InternalEList<?>)getConfigs()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.CONFIGURATION__PROPASSIGNS:
				return ((InternalEList<?>)getPropassigns()).basicRemove(otherEnd, msgs);
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
			case ThingmlPackage.CONFIGURATION__INSTANCES:
				return getInstances();
			case ThingmlPackage.CONFIGURATION__CONNECTORS:
				return getConnectors();
			case ThingmlPackage.CONFIGURATION__FRAGMENT:
				return isFragment();
			case ThingmlPackage.CONFIGURATION__CONFIGS:
				return getConfigs();
			case ThingmlPackage.CONFIGURATION__PROPASSIGNS:
				return getPropassigns();
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
			case ThingmlPackage.CONFIGURATION__INSTANCES:
				getInstances().clear();
				getInstances().addAll((Collection<? extends Instance>)newValue);
				return;
			case ThingmlPackage.CONFIGURATION__CONNECTORS:
				getConnectors().clear();
				getConnectors().addAll((Collection<? extends Connector>)newValue);
				return;
			case ThingmlPackage.CONFIGURATION__FRAGMENT:
				setFragment((Boolean)newValue);
				return;
			case ThingmlPackage.CONFIGURATION__CONFIGS:
				getConfigs().clear();
				getConfigs().addAll((Collection<? extends ConfigInclude>)newValue);
				return;
			case ThingmlPackage.CONFIGURATION__PROPASSIGNS:
				getPropassigns().clear();
				getPropassigns().addAll((Collection<? extends ConfigPropertyAssign>)newValue);
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
			case ThingmlPackage.CONFIGURATION__INSTANCES:
				getInstances().clear();
				return;
			case ThingmlPackage.CONFIGURATION__CONNECTORS:
				getConnectors().clear();
				return;
			case ThingmlPackage.CONFIGURATION__FRAGMENT:
				setFragment(FRAGMENT_EDEFAULT);
				return;
			case ThingmlPackage.CONFIGURATION__CONFIGS:
				getConfigs().clear();
				return;
			case ThingmlPackage.CONFIGURATION__PROPASSIGNS:
				getPropassigns().clear();
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
			case ThingmlPackage.CONFIGURATION__INSTANCES:
				return instances != null && !instances.isEmpty();
			case ThingmlPackage.CONFIGURATION__CONNECTORS:
				return connectors != null && !connectors.isEmpty();
			case ThingmlPackage.CONFIGURATION__FRAGMENT:
				return fragment != FRAGMENT_EDEFAULT;
			case ThingmlPackage.CONFIGURATION__CONFIGS:
				return configs != null && !configs.isEmpty();
			case ThingmlPackage.CONFIGURATION__PROPASSIGNS:
				return propassigns != null && !propassigns.isEmpty();
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
		result.append(" (fragment: ");
		result.append(fragment);
		result.append(')');
		return result.toString();
	}

} //ConfigurationImpl
