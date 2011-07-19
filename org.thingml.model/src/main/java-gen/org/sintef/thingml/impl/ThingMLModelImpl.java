/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sintef.thingml.Configuration;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.Type;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Thing ML Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.ThingMLModelImpl#getTypes <em>Types</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingMLModelImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingMLModelImpl#getConfigs <em>Configs</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingMLModelImpl#getUri <em>Uri</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingMLModelImpl#get__imports_cache <em>imports cache</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingMLModelImpl#get__imports_parent <em>imports parent</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ThingMLModelImpl extends EObjectImpl implements ThingMLModel {
	/**
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> types;

	/**
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected EList<ThingMLModel> imports;

	/**
	 * The cached value of the '{@link #getConfigs() <em>Configs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigs()
	 * @generated
	 * @ordered
	 */
	protected EList<Configuration> configs;

	/**
	 * The default value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected String uri = URI_EDEFAULT;

	/**
	 * The cached value of the '{@link #get__imports_cache() <em>imports cache</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get__imports_cache()
	 * @generated
	 * @ordered
	 */
	protected EList<ThingMLModel> __imports_cache;

	/**
	 * The cached value of the '{@link #get__imports_parent() <em>imports parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get__imports_parent()
	 * @generated
	 * @ordered
	 */
	protected ThingMLModel __imports_parent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ThingMLModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.THING_ML_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Type> getTypes() {
		if (types == null) {
			types = new EObjectContainmentEList<Type>(Type.class, this, ThingmlPackage.THING_ML_MODEL__TYPES);
		}
		return types;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ThingMLModel> getImports() {
		if (imports == null) {
			imports = new EObjectResolvingEList<ThingMLModel>(ThingMLModel.class, this, ThingmlPackage.THING_ML_MODEL__IMPORTS);
		}
		return imports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Configuration> getConfigs() {
		if (configs == null) {
			configs = new EObjectContainmentEList<Configuration>(Configuration.class, this, ThingmlPackage.THING_ML_MODEL__CONFIGS);
		}
		return configs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUri(String newUri) {
		String oldUri = uri;
		uri = newUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.THING_ML_MODEL__URI, oldUri, uri));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ThingMLModel> get__imports_cache() {
		if (__imports_cache == null) {
			__imports_cache = new EObjectResolvingEList<ThingMLModel>(ThingMLModel.class, this, ThingmlPackage.THING_ML_MODEL__IMPORTS_CACHE);
		}
		return __imports_cache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThingMLModel get__imports_parent() {
		if (__imports_parent != null && __imports_parent.eIsProxy()) {
			InternalEObject old__imports_parent = (InternalEObject)__imports_parent;
			__imports_parent = (ThingMLModel)eResolveProxy(old__imports_parent);
			if (__imports_parent != old__imports_parent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.THING_ML_MODEL__IMPORTS_PARENT, old__imports_parent, __imports_parent));
			}
		}
		return __imports_parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThingMLModel basicGet__imports_parent() {
		return __imports_parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void set__imports_parent(ThingMLModel new__imports_parent) {
		ThingMLModel old__imports_parent = __imports_parent;
		__imports_parent = new__imports_parent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.THING_ML_MODEL__IMPORTS_PARENT, old__imports_parent, __imports_parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ThingmlPackage.THING_ML_MODEL__TYPES:
				return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
			case ThingmlPackage.THING_ML_MODEL__CONFIGS:
				return ((InternalEList<?>)getConfigs()).basicRemove(otherEnd, msgs);
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
			case ThingmlPackage.THING_ML_MODEL__TYPES:
				return getTypes();
			case ThingmlPackage.THING_ML_MODEL__IMPORTS:
				return getImports();
			case ThingmlPackage.THING_ML_MODEL__CONFIGS:
				return getConfigs();
			case ThingmlPackage.THING_ML_MODEL__URI:
				return getUri();
			case ThingmlPackage.THING_ML_MODEL__IMPORTS_CACHE:
				return get__imports_cache();
			case ThingmlPackage.THING_ML_MODEL__IMPORTS_PARENT:
				if (resolve) return get__imports_parent();
				return basicGet__imports_parent();
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
			case ThingmlPackage.THING_ML_MODEL__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection<? extends Type>)newValue);
				return;
			case ThingmlPackage.THING_ML_MODEL__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection<? extends ThingMLModel>)newValue);
				return;
			case ThingmlPackage.THING_ML_MODEL__CONFIGS:
				getConfigs().clear();
				getConfigs().addAll((Collection<? extends Configuration>)newValue);
				return;
			case ThingmlPackage.THING_ML_MODEL__URI:
				setUri((String)newValue);
				return;
			case ThingmlPackage.THING_ML_MODEL__IMPORTS_CACHE:
				get__imports_cache().clear();
				get__imports_cache().addAll((Collection<? extends ThingMLModel>)newValue);
				return;
			case ThingmlPackage.THING_ML_MODEL__IMPORTS_PARENT:
				set__imports_parent((ThingMLModel)newValue);
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
			case ThingmlPackage.THING_ML_MODEL__TYPES:
				getTypes().clear();
				return;
			case ThingmlPackage.THING_ML_MODEL__IMPORTS:
				getImports().clear();
				return;
			case ThingmlPackage.THING_ML_MODEL__CONFIGS:
				getConfigs().clear();
				return;
			case ThingmlPackage.THING_ML_MODEL__URI:
				setUri(URI_EDEFAULT);
				return;
			case ThingmlPackage.THING_ML_MODEL__IMPORTS_CACHE:
				get__imports_cache().clear();
				return;
			case ThingmlPackage.THING_ML_MODEL__IMPORTS_PARENT:
				set__imports_parent((ThingMLModel)null);
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
			case ThingmlPackage.THING_ML_MODEL__TYPES:
				return types != null && !types.isEmpty();
			case ThingmlPackage.THING_ML_MODEL__IMPORTS:
				return imports != null && !imports.isEmpty();
			case ThingmlPackage.THING_ML_MODEL__CONFIGS:
				return configs != null && !configs.isEmpty();
			case ThingmlPackage.THING_ML_MODEL__URI:
				return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
			case ThingmlPackage.THING_ML_MODEL__IMPORTS_CACHE:
				return __imports_cache != null && !__imports_cache.isEmpty();
			case ThingmlPackage.THING_ML_MODEL__IMPORTS_PARENT:
				return __imports_parent != null;
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
		result.append(" (uri: ");
		result.append(uri);
		result.append(')');
		return result.toString();
	}

} //ThingMLModelImpl
