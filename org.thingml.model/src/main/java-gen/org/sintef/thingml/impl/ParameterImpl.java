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

import org.sintef.thingml.Parameter;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ParameterImpl extends VariableImpl implements Parameter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.PARAMETER;
	}

    //Derived properties

    /**
     *
     * @return
     * @generated NOT
     */
    public List<PlatformAnnotation> allAnnotations() {
        return this.annotations;
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

} //ParameterImpl
