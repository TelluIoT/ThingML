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

import org.sintef.thingml.AnnotatedElement;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.PlatformAnnotation;
import org.sintef.thingml.ThingmlPackage;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
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

    /**
     *
     * @return
     * @generated NOT
     */
    @Override
    public List<PlatformAnnotation> allAnnotations() {
        return ((AnnotatedElement)this).allAnnotations();
    }

    /**
     *
     * @param annotation
     * @param value
     * @return
     * @generated NOT
     */
    @Override
    public boolean isDefined(String annotation, String value) {
        return ((AnnotatedElement)this).isDefined(annotation, value);
    }

    /**
     *
     * @param name
     * @return
     * @generated NOT
     */
    @Override
    public boolean hasAnnotation(String name) {
        return ((AnnotatedElement)this).hasAnnotation(name);
    }

    /**
     *
     * @param name
     * @return
     * @generated NOT
     */
    @Override
    public String annotation(String name) {
        return ((AnnotatedElement)this).annotation(name);
    }

} //ParameterImpl
