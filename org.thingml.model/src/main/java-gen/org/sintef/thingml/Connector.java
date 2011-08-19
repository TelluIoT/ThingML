/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sintef.thingml;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connector</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sintef.thingml.Connector#getSrv <em>Srv</em>}</li>
 *   <li>{@link org.sintef.thingml.Connector#getCli <em>Cli</em>}</li>
 *   <li>{@link org.sintef.thingml.Connector#getRequired <em>Required</em>}</li>
 *   <li>{@link org.sintef.thingml.Connector#getProvided <em>Provided</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sintef.thingml.ThingmlPackage#getConnector()
 * @model
 * @generated
 */
public interface Connector extends AnnotatedElement {
	/**
	 * Returns the value of the '<em><b>Srv</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Srv</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Srv</em>' containment reference.
	 * @see #setSrv(InstanceRef)
	 * @see org.sintef.thingml.ThingmlPackage#getConnector_Srv()
	 * @model containment="true" required="true"
	 * @generated
	 */
	InstanceRef getSrv();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Connector#getSrv <em>Srv</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Srv</em>' containment reference.
	 * @see #getSrv()
	 * @generated
	 */
	void setSrv(InstanceRef value);

	/**
	 * Returns the value of the '<em><b>Cli</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cli</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cli</em>' containment reference.
	 * @see #setCli(InstanceRef)
	 * @see org.sintef.thingml.ThingmlPackage#getConnector_Cli()
	 * @model containment="true" required="true"
	 * @generated
	 */
	InstanceRef getCli();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Connector#getCli <em>Cli</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cli</em>' containment reference.
	 * @see #getCli()
	 * @generated
	 */
	void setCli(InstanceRef value);

	/**
	 * Returns the value of the '<em><b>Required</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required</em>' reference.
	 * @see #setRequired(RequiredPort)
	 * @see org.sintef.thingml.ThingmlPackage#getConnector_Required()
	 * @model required="true"
	 * @generated
	 */
	RequiredPort getRequired();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Connector#getRequired <em>Required</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required</em>' reference.
	 * @see #getRequired()
	 * @generated
	 */
	void setRequired(RequiredPort value);

	/**
	 * Returns the value of the '<em><b>Provided</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided</em>' reference.
	 * @see #setProvided(ProvidedPort)
	 * @see org.sintef.thingml.ThingmlPackage#getConnector_Provided()
	 * @model required="true"
	 * @generated
	 */
	ProvidedPort getProvided();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.Connector#getProvided <em>Provided</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided</em>' reference.
	 * @see #getProvided()
	 * @generated
	 */
	void setProvided(ProvidedPort value);

} // Connector
