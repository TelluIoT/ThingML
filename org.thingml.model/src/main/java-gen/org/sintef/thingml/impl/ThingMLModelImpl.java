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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sintef.thingml.Configuration;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.Type;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Thing ML Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.ThingMLModelImpl#getTypes <em>Types</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingMLModelImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.ThingMLModelImpl#getConfigs <em>Configs</em>}</li>
 * </ul>
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
		}
		return super.eIsSet(featureID);
	}

    //Derived properties

    /**
     *
     * @return
     * @generated NOT
     */
    public List<ThingMLModel> allThingMLModelModels() {
        return ThingMLHelpers.allThingMLModelModels(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Type> allTypes() {
        return ThingMLHelpers.allTypes(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    @Override
    public Set<Type> allUsedTypes() {
        return ThingMLHelpers.allUsedTypes(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Type> allSimpleTypes() {
        return ThingMLHelpers.allSimpleTypes(this);
    }

    /**
     * @generated NOT
     * @return
     */
    @Override
    public Set<Type> allUsedSimpleTypes() {
        return ThingMLHelpers.allUsedSimpleTypes(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Thing> allThings() {
        return ThingMLHelpers.allThings(this);
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public Set<Message> allMessages() {
        Set<Message> msg = new HashSet<Message>();
        for(Thing t : allThings()) {
            msg.addAll(t.allMessages());
        }
        return msg;
    }

    /**
     *
     * @return
     * @generated NOT
     */
    public List<Configuration> allConfigurations() {
        return ThingMLHelpers.allConfigurations(this);
    }

} //ThingMLModelImpl
