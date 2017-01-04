/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
/**
 */
package org.sintef.thingml;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Stream</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.Stream#getSelection <em>Selection</em>}</li>
 *   <li>{@link org.sintef.thingml.Stream#getOutput <em>Output</em>}</li>
 *   <li>{@link org.sintef.thingml.Stream#getInput <em>Input</em>}</li>
 *   <li>{@link org.sintef.thingml.Stream#isDynamic <em>Dynamic</em>}</li>
 * </ul>
 *
 * @see org.sintef.thingml.ThingmlPackage#getStream()
 * @model
 * @generated
 */
public interface Stream extends AnnotatedElement {
	/**
	 * Returns the value of the '<em><b>Selection</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.LocalVariable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Selection</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Selection</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getStream_Selection()
	 * @model containment="true"
	 * @generated
	 */
	EList<LocalVariable> getSelection();

	/**
	 * Returns the value of the '<em><b>Output</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output</em>' containment reference.
	 * @see #setOutput(SendAction)
	 * @see org.sintef.thingml.ThingmlPackage#getStream_Output()
	 * @model containment="true" required="true"
	 * @generated
	 */
	SendAction getOutput();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Stream#getOutput <em>Output</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output</em>' containment reference.
	 * @see #getOutput()
	 * @generated
	 */
	void setOutput(SendAction value);

	/**
	 * Returns the value of the '<em><b>Input</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input</em>' containment reference.
	 * @see #setInput(Source)
	 * @see org.sintef.thingml.ThingmlPackage#getStream_Input()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Source getInput();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Stream#getInput <em>Input</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input</em>' containment reference.
	 * @see #getInput()
	 * @generated
	 */
	void setInput(Source value);

	/**
	 * Returns the value of the '<em><b>Dynamic</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dynamic</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dynamic</em>' attribute.
	 * @see #setDynamic(boolean)
	 * @see org.sintef.thingml.ThingmlPackage#getStream_Dynamic()
	 * @model default="false"
	 * @generated
	 */
	boolean isDynamic();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Stream#isDynamic <em>Dynamic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dynamic</em>' attribute.
	 * @see #isDynamic()
	 * @generated
	 */
	void setDynamic(boolean value);

} // Stream
