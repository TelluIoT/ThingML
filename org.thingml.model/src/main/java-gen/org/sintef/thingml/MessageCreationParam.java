/**
 */
package org.sintef.thingml;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Message Creation Param</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sintef.thingml.MessageCreationParam#getMsgType <em>Msg Type</em>}</li>
 *   <li>{@link org.sintef.thingml.MessageCreationParam#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sintef.thingml.ThingmlPackage#getMessageCreationParam()
 * @model
 * @generated
 */
public interface MessageCreationParam extends Expression {
	/**
	 * Returns the value of the '<em><b>Msg Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Msg Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Msg Type</em>' reference.
	 * @see #setMsgType(SimpleMessage)
	 * @see org.sintef.thingml.ThingmlPackage#getMessageCreationParam_MsgType()
	 * @model required="true"
	 * @generated
	 */
	SimpleMessage getMsgType();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.MessageCreationParam#getMsgType <em>Msg Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Msg Type</em>' reference.
	 * @see #getMsgType()
	 * @generated
	 */
	void setMsgType(SimpleMessage value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getMessageCreationParam_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getParameters();

} // MessageCreationParam
