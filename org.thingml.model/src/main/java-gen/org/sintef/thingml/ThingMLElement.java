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
package org.sintef.thingml;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Thing ML Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.ThingMLElement#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.sintef.thingml.ThingmlPackage#getThingMLElement()
 * @model abstract="true"
 * @generated
 */
public interface ThingMLElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.sintef.thingml.ThingmlPackage#getThingMLElement_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.ThingMLElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

    //Derived properties

    /**
     *
     * @return
     * @generated NOT
     */
    ThingMLModel findContainingModel();

    /**
     *
     * @return
     * @generated NOT
     */
    Thing findContainingThing();

    /**
     *
     * @return
     * @generated NOT
     */
    Configuration findContainingConfiguration();

    /**
     *
     * @return
     * @generated NOT
     */
    State findContainingState();

    /**
     *
     * @return
     * @generated NOT
     */
    Region findContainingRegion();

    /**
     *
     * @return
     * @generated NOT
     */
    Handler findContainingHandler();

    /**
     *
     * @param separator
     * @return
     * @generated NOT
     */
    String qname(String separator);

} // ThingMLElement
