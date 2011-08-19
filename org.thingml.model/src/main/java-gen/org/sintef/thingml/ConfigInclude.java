/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sintef.thingml;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Config Include</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sintef.thingml.ConfigInclude#getConfig <em>Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sintef.thingml.ThingmlPackage#getConfigInclude()
 * @model
 * @generated
 */
public interface ConfigInclude extends AnnotatedElement {
	/**
	 * Returns the value of the '<em><b>Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Config</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Config</em>' reference.
	 * @see #setConfig(Configuration)
	 * @see org.sintef.thingml.ThingmlPackage#getConfigInclude_Config()
	 * @model required="true"
	 * @generated
	 */
	Configuration getConfig();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.ConfigInclude#getConfig <em>Config</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Config</em>' reference.
	 * @see #getConfig()
	 * @generated
	 */
	void setConfig(Configuration value);

} // ConfigInclude
