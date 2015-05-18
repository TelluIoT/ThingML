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
package org.sintef.thingml.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.sintef.thingml.Property;
import org.sintef.thingml.Stream;
import org.sintef.thingml.ThingmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Stream</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sintef.thingml.impl.StreamImpl#getPortSend <em>Port Send</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.StreamImpl#getStreamMessage <em>Stream Message</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.StreamImpl#getEventProperty <em>Event Property</em>}</li>
 *   <li>{@link org.sintef.thingml.impl.StreamImpl#getWithSubscribe <em>With Subscribe</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class StreamImpl extends EventImpl implements Stream {
	/**
	 * The cached value of the '{@link #getPortSend() <em>Port Send</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPortSend()
	 * @generated
	 * @ordered
	 */
	protected Port portSend;
	/**
	 * The cached value of the '{@link #getStreamMessage() <em>Stream Message</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStreamMessage()
	 * @generated
	 * @ordered
	 */
	protected Message streamMessage;
	/**
	 * The cached value of the '{@link #getEventProperty() <em>Event Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventProperty()
	 * @generated
	 * @ordered
	 */
	protected Property eventProperty;

	/**
	 * The default value of the '{@link #getWithSubscribe() <em>With Subscribe</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWithSubscribe()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean WITH_SUBSCRIBE_EDEFAULT = Boolean.TRUE;
	/**
	 * The cached value of the '{@link #getWithSubscribe() <em>With Subscribe</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWithSubscribe()
	 * @generated
	 * @ordered
	 */
	protected Boolean withSubscribe = WITH_SUBSCRIBE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StreamImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ThingmlPackage.Literals.STREAM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port getPortSend() {
		if (portSend != null && portSend.eIsProxy()) {
			InternalEObject oldPortSend = (InternalEObject)portSend;
			portSend = (Port)eResolveProxy(oldPortSend);
			if (portSend != oldPortSend) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.STREAM__PORT_SEND, oldPortSend, portSend));
			}
		}
		return portSend;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port basicGetPortSend() {
		return portSend;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPortSend(Port newPortSend) {
		Port oldPortSend = portSend;
		portSend = newPortSend;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.STREAM__PORT_SEND, oldPortSend, portSend));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message getStreamMessage() {
		if (streamMessage != null && streamMessage.eIsProxy()) {
			InternalEObject oldStreamMessage = (InternalEObject)streamMessage;
			streamMessage = (Message)eResolveProxy(oldStreamMessage);
			if (streamMessage != oldStreamMessage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.STREAM__STREAM_MESSAGE, oldStreamMessage, streamMessage));
			}
		}
		return streamMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message basicGetStreamMessage() {
		return streamMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStreamMessage(Message newStreamMessage) {
		Message oldStreamMessage = streamMessage;
		streamMessage = newStreamMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.STREAM__STREAM_MESSAGE, oldStreamMessage, streamMessage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property getEventProperty() {
		if (eventProperty != null && eventProperty.eIsProxy()) {
			InternalEObject oldEventProperty = (InternalEObject)eventProperty;
			eventProperty = (Property)eResolveProxy(oldEventProperty);
			if (eventProperty != oldEventProperty) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ThingmlPackage.STREAM__EVENT_PROPERTY, oldEventProperty, eventProperty));
			}
		}
		return eventProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property basicGetEventProperty() {
		return eventProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventProperty(Property newEventProperty) {
		Property oldEventProperty = eventProperty;
		eventProperty = newEventProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.STREAM__EVENT_PROPERTY, oldEventProperty, eventProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getWithSubscribe() {
		return withSubscribe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWithSubscribe(Boolean newWithSubscribe) {
		Boolean oldWithSubscribe = withSubscribe;
		withSubscribe = newWithSubscribe;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ThingmlPackage.STREAM__WITH_SUBSCRIBE, oldWithSubscribe, withSubscribe));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ThingmlPackage.STREAM__PORT_SEND:
				if (resolve) return getPortSend();
				return basicGetPortSend();
			case ThingmlPackage.STREAM__STREAM_MESSAGE:
				if (resolve) return getStreamMessage();
				return basicGetStreamMessage();
			case ThingmlPackage.STREAM__EVENT_PROPERTY:
				if (resolve) return getEventProperty();
				return basicGetEventProperty();
			case ThingmlPackage.STREAM__WITH_SUBSCRIBE:
				return getWithSubscribe();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ThingmlPackage.STREAM__PORT_SEND:
				setPortSend((Port)newValue);
				return;
			case ThingmlPackage.STREAM__STREAM_MESSAGE:
				setStreamMessage((Message)newValue);
				return;
			case ThingmlPackage.STREAM__EVENT_PROPERTY:
				setEventProperty((Property)newValue);
				return;
			case ThingmlPackage.STREAM__WITH_SUBSCRIBE:
				setWithSubscribe((Boolean)newValue);
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
			case ThingmlPackage.STREAM__PORT_SEND:
				setPortSend((Port)null);
				return;
			case ThingmlPackage.STREAM__STREAM_MESSAGE:
				setStreamMessage((Message)null);
				return;
			case ThingmlPackage.STREAM__EVENT_PROPERTY:
				setEventProperty((Property)null);
				return;
			case ThingmlPackage.STREAM__WITH_SUBSCRIBE:
				setWithSubscribe(WITH_SUBSCRIBE_EDEFAULT);
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
			case ThingmlPackage.STREAM__PORT_SEND:
				return portSend != null;
			case ThingmlPackage.STREAM__STREAM_MESSAGE:
				return streamMessage != null;
			case ThingmlPackage.STREAM__EVENT_PROPERTY:
				return eventProperty != null;
			case ThingmlPackage.STREAM__WITH_SUBSCRIBE:
				return WITH_SUBSCRIBE_EDEFAULT == null ? withSubscribe != null : !WITH_SUBSCRIBE_EDEFAULT.equals(withSubscribe);
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
		result.append(" (withSubscribe: ");
		result.append(withSubscribe);
		result.append(')');
		return result.toString();
	}

} //StreamImpl
