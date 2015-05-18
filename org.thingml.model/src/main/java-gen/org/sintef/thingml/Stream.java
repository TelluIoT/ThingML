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
 * A representation of the model object '<em><b>Stream</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sintef.thingml.Stream#getPortSend <em>Port Send</em>}</li>
 *   <li>{@link org.sintef.thingml.Stream#getStreamMessage <em>Stream Message</em>}</li>
 *   <li>{@link org.sintef.thingml.Stream#getEventProperty <em>Event Property</em>}</li>
 *   <li>{@link org.sintef.thingml.Stream#getWithSubscribe <em>With Subscribe</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sintef.thingml.ThingmlPackage#getStream()
 * @model abstract="true"
 * @generated
 */
public interface Stream extends Event {

	/**
	 * Returns the value of the '<em><b>Port Send</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port Send</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port Send</em>' reference.
	 * @see #setPortSend(Port)
	 * @see org.sintef.thingml.ThingmlPackage#getStream_PortSend()
	 * @model required="true"
	 * @generated
	 */
	Port getPortSend();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Stream#getPortSend <em>Port Send</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port Send</em>' reference.
	 * @see #getPortSend()
	 * @generated
	 */
	void setPortSend(Port value);

	/**
	 * Returns the value of the '<em><b>Stream Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stream Message</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stream Message</em>' reference.
	 * @see #setStreamMessage(Message)
	 * @see org.sintef.thingml.ThingmlPackage#getStream_StreamMessage()
	 * @model required="true"
	 * @generated
	 */
	Message getStreamMessage();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Stream#getStreamMessage <em>Stream Message</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stream Message</em>' reference.
	 * @see #getStreamMessage()
	 * @generated
	 */
	void setStreamMessage(Message value);

	/**
	 * Returns the value of the '<em><b>Event Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Property</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Property</em>' reference.
	 * @see #setEventProperty(Property)
	 * @see org.sintef.thingml.ThingmlPackage#getStream_EventProperty()
	 * @model required="true"
	 * @generated
	 */
	Property getEventProperty();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Stream#getEventProperty <em>Event Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event Property</em>' reference.
	 * @see #getEventProperty()
	 * @generated
	 */
	void setEventProperty(Property value);

	/**
	 * Returns the value of the '<em><b>With Subscribe</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>With Subscribe</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>With Subscribe</em>' attribute.
	 * @see #setWithSubscribe(Boolean)
	 * @see org.sintef.thingml.ThingmlPackage#getStream_WithSubscribe()
	 * @model default="true"
	 * @generated
	 */
	Boolean getWithSubscribe();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Stream#getWithSubscribe <em>With Subscribe</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>With Subscribe</em>' attribute.
	 * @see #getWithSubscribe()
	 * @generated
	 */
	void setWithSubscribe(Boolean value);
} // Stream
