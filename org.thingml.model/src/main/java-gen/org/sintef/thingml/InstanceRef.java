/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sintef.thingml;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Instance Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sintef.thingml.InstanceRef#getConfig <em>Config</em>}</li>
 *   <li>{@link org.sintef.thingml.InstanceRef#getInstance <em>Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sintef.thingml.ThingmlPackage#getInstanceRef()
 * @model
 * @generated
 */
public interface InstanceRef extends EObject {
	/**
	 * Returns the value of the '<em><b>Config</b></em>' reference list.
	 * The list contents are of type {@link org.sintef.thingml.ConfigInclude}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Config</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Config</em>' reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getInstanceRef_Config()
	 * @model
	 * @generated
	 */
	EList<ConfigInclude> getConfig();

	/**
	 * Returns the value of the '<em><b>Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instance</em>' reference.
	 * @see #setInstance(Instance)
	 * @see org.sintef.thingml.ThingmlPackage#getInstanceRef_Instance()
	 * @model required="true"
	 * @generated
	 */
	Instance getInstance();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.InstanceRef#getInstance <em>Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance</em>' reference.
	 * @see #getInstance()
	 * @generated
	 */
	void setInstance(Instance value);

} // InstanceRef
