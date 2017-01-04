/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
/**
 */
package org.sintef.thingml;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Join Sources</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sintef.thingml.JoinSources#getRules <em>Rules</em>}</li>
 * </ul>
 *
 * @see org.sintef.thingml.ThingmlPackage#getJoinSources()
 * @model
 * @generated
 */
public interface JoinSources extends SourceComposition {

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.sintef.thingml.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see org.sintef.thingml.ThingmlPackage#getJoinSources_Rules()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getRules();
} // JoinSources
