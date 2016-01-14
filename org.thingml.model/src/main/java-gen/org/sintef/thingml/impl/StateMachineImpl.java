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

import org.eclipse.emf.ecore.EClass;

import org.sintef.thingml.StateMachine;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class StateMachineImpl extends CompositeStateImpl implements StateMachine {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateMachineImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.STATE_MACHINE;
	}


    //Derived properties

    /**
     *
     * @return
     * @generated NOT
     */
    @Override
    public List<State> allContainedStates() {
        return super.allContainedStates();
    }

    /**
     *
     * @return
     * @generated NOT
     */
    @Override
    public List<Region> allContainedRegions() {
        return super.allContainedRegions();
    }

    /**
     *
     * @return
     * @generated NOT
     */
    @Override
    public List<Property> allContainedProperties() {
        return super.allContainedProperties();
    }

    /**
     *
     * @return
     * @generated NOT
     */
    @Override
    public List<Region> directSubRegions() {
        return super.directSubRegions();
    }

    /**
     *
     * @return
     * @generated NOT
     */
    @Override
    public List<CompositeState> allContainedCompositeStates() {
        return super.allContainedCompositeStates();
    }

    /**
     *
     * @return
     * @generated NOT
     */
    @Override
    public List<State> allContainedSimpleStates() {
        return super.allContainedSimpleStates();
    }

    /**
     *
     * @return
     * @generated NOT
     */
    @Override
    public Set<Type> allUsedTypes() {
        return super.allUsedTypes();
    }



    } //StateMachineImpl
