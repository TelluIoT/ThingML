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
package org.sintef.thingml;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Start Stream</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.StartStream#getStream <em>Stream</em>}</li>
 * </ul>
 *
 * @see org.sintef.thingml.ThingmlPackage#getStartStream()
 * @model
 * @generated
 */
public interface StartStream extends Action {
	/**
	 * Returns the value of the '<em><b>Stream</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stream</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stream</em>' reference.
	 * @see #setStream(Stream)
	 * @see org.sintef.thingml.ThingmlPackage#getStartStream_Stream()
	 * @model required="true"
	 * @generated
	 */
	Stream getStream();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.StartStream#getStream <em>Stream</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stream</em>' reference.
	 * @see #getStream()
	 * @generated
	 */
	void setStream(Stream value);

} // StartStream
