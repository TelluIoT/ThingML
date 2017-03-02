/**
 * *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.thingml.xtext.thingML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cast Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.CastExpression#getTerm <em>Term</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.CastExpression#getType <em>Type</em>}</li>
 *   <li>{@link org.thingml.xtext.thingML.CastExpression#isIsArray <em>Is Array</em>}</li>
 * </ul>
 *
 * @see org.thingml.xtext.thingML.ThingMLPackage#getCastExpression()
 * @model
 * @generated
 */
public interface CastExpression extends Expression
{
  /**
   * Returns the value of the '<em><b>Term</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Term</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Term</em>' containment reference.
   * @see #setTerm(Expression)
   * @see org.thingml.xtext.thingML.ThingMLPackage#getCastExpression_Term()
   * @model containment="true"
   * @generated
   */
  Expression getTerm();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.CastExpression#getTerm <em>Term</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Term</em>' containment reference.
   * @see #getTerm()
   * @generated
   */
  void setTerm(Expression value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' reference.
   * @see #setType(Type)
   * @see org.thingml.xtext.thingML.ThingMLPackage#getCastExpression_Type()
   * @model
   * @generated
   */
  Type getType();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.CastExpression#getType <em>Type</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' reference.
   * @see #getType()
   * @generated
   */
  void setType(Type value);

  /**
   * Returns the value of the '<em><b>Is Array</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Is Array</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Is Array</em>' attribute.
   * @see #setIsArray(boolean)
   * @see org.thingml.xtext.thingML.ThingMLPackage#getCastExpression_IsArray()
   * @model
   * @generated
   */
  boolean isIsArray();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.CastExpression#isIsArray <em>Is Array</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Is Array</em>' attribute.
   * @see #isIsArray()
   * @generated
   */
  void setIsArray(boolean value);

} // CastExpression
