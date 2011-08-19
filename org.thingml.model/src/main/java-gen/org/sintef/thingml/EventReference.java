/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sintef.thingml;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sintef.thingml.EventReference#getMsgRef <em>Msg Ref</em>}</li>
 *   <li>{@link org.sintef.thingml.EventReference#getParamRef <em>Param Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sintef.thingml.ThingmlPackage#getEventReference()
 * @model
 * @generated
 */
public interface EventReference extends Expression {
	/**
	 * Returns the value of the '<em><b>Msg Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Msg Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Msg Ref</em>' reference.
	 * @see #setMsgRef(ReceiveMessage)
	 * @see org.sintef.thingml.ThingmlPackage#getEventReference_MsgRef()
	 * @model required="true"
	 * @generated
	 */
	ReceiveMessage getMsgRef();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.EventReference#getMsgRef <em>Msg Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Msg Ref</em>' reference.
	 * @see #getMsgRef()
	 * @generated
	 */
	void setMsgRef(ReceiveMessage value);

	/**
	 * Returns the value of the '<em><b>Param Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Param Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Param Ref</em>' reference.
	 * @see #setParamRef(Parameter)
	 * @see org.sintef.thingml.ThingmlPackage#getEventReference_ParamRef()
	 * @model required="true"
	 * @generated
	 */
	Parameter getParamRef();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.EventReference#getParamRef <em>Param Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Param Ref</em>' reference.
	 * @see #getParamRef()
	 * @generated
	 */
	void setParamRef(Parameter value);

} // EventReference
