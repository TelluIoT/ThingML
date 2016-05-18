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
package org.sintef.thingml;

import org.eclipse.emf.ecore.EObject;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Action</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.sintef.thingml.ThingmlPackage#getAction()
 * @model abstract="true"
 * @generated
 */
public interface Action extends EObject {
    /**
     * @generated NOT
     * @return
     */
    List<Expression> getAllExpressions();

    /**
     * @generated NOT
     * @return
     */
    List<Expression> getAllExpressions(Class clazz);

    /**
     * @generated NOT
     * @return
     */
    List<Action> getAllActions();

    /**
     * @generated NOT
     * @return
     */
    List<Action> getAllActions(Class clazz);
} // Action
