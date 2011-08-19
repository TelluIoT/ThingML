/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sintef.thingml;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Literal Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sintef.thingml.EnumLiteralRef#getEnum <em>Enum</em>}</li>
 *   <li>{@link org.sintef.thingml.EnumLiteralRef#getLiteral <em>Literal</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sintef.thingml.ThingmlPackage#getEnumLiteralRef()
 * @model
 * @generated
 */
public interface EnumLiteralRef extends Literal {
	/**
	 * Returns the value of the '<em><b>Enum</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enum</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enum</em>' reference.
	 * @see #setEnum(Enumeration)
	 * @see org.sintef.thingml.ThingmlPackage#getEnumLiteralRef_Enum()
	 * @model required="true"
	 * @generated
	 */
	Enumeration getEnum();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.EnumLiteralRef#getEnum <em>Enum</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enum</em>' reference.
	 * @see #getEnum()
	 * @generated
	 */
	void setEnum(Enumeration value);

	/**
	 * Returns the value of the '<em><b>Literal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Literal</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Literal</em>' reference.
	 * @see #setLiteral(EnumerationLiteral)
	 * @see org.sintef.thingml.ThingmlPackage#getEnumLiteralRef_Literal()
	 * @model required="true"
	 * @generated
	 */
	EnumerationLiteral getLiteral();

	/**
	 * Sets the value of the '{@link org.sintef.thingml.EnumLiteralRef#getLiteral <em>Literal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Literal</em>' reference.
	 * @see #getLiteral()
	 * @generated
	 */
	void setLiteral(EnumerationLiteral value);

} // EnumLiteralRef
