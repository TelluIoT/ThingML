/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sintef.thingml;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sintef.thingml.CompositeState#getRegion <em>Region</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sintef.thingml.ThingmlPackage#getCompositeState()
 * @model
 * @generated
 */
public interface CompositeState extends State, Region {
	/**
	 * Returns the value of the '<em><b>Region</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.ParallelRegion}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Region</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Region</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getCompositeState_Region()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParallelRegion> getRegion();

} // CompositeState
