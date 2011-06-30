/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
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
/**
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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.sintef.thingml.ThingmlFactory
 * @model kind="package"
 * @generated
 */
public interface ThingmlPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "thingml";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://thingml";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "thingml";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ThingmlPackage eINSTANCE = org.sintef.thingml.impl.ThingmlPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ThingMLModelImpl <em>Thing ML Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ThingMLModelImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getThingMLModel()
	 * @generated
	 */
	int THING_ML_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING_ML_MODEL__TYPES = 0;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING_ML_MODEL__IMPORTS = 1;

	/**
	 * The feature id for the '<em><b>Messages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING_ML_MODEL__MESSAGES = 2;

	/**
	 * The number of structural features of the '<em>Thing ML Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING_ML_MODEL_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ThingMLElementImpl <em>Thing ML Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ThingMLElementImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getThingMLElement()
	 * @generated
	 */
	int THING_ML_ELEMENT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING_ML_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Thing ML Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING_ML_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.AnnotatedElementImpl <em>Annotated Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.AnnotatedElementImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getAnnotatedElement()
	 * @generated
	 */
	int ANNOTATED_ELEMENT = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_ELEMENT__NAME = THING_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_ELEMENT__ANNOTATIONS = THING_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Annotated Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_ELEMENT_FEATURE_COUNT = THING_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.MessageImpl <em>Message</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.MessageImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getMessage()
	 * @generated
	 */
	int MESSAGE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE__PARAMETERS = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Message</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.TypeImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ThingImpl <em>Thing</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ThingImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getThing()
	 * @generated
	 */
	int THING = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__NAME = TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__ANNOTATIONS = TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__PROPERTIES = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__FRAGMENT = TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__PORTS = TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Behaviour</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__BEHAVIOUR = TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Includes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__INCLUDES = TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Thing</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING_FEATURE_COUNT = TYPE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ParameterImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = THING_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__TYPE = THING_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = THING_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PropertyImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__TYPE = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Changeable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__CHANGEABLE = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__LOWER_BOUND = ANNOTATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__UPPER_BOUND = ANNOTATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PlatformAnnotationImpl <em>Platform Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PlatformAnnotationImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPlatformAnnotation()
	 * @generated
	 */
	int PLATFORM_ANNOTATION = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLATFORM_ANNOTATION__NAME = THING_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLATFORM_ANNOTATION__VALUE = THING_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Platform Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLATFORM_ANNOTATION_FEATURE_COUNT = THING_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.EnumerationImpl <em>Enumeration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.EnumerationImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEnumeration()
	 * @generated
	 */
	int ENUMERATION = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__NAME = TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__ANNOTATIONS = TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Literals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__LITERALS = TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enumeration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PrimitiveTypeImpl <em>Primitive Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PrimitiveTypeImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPrimitiveType()
	 * @generated
	 */
	int PRIMITIVE_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_TYPE__NAME = TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_TYPE__ANNOTATIONS = TYPE__ANNOTATIONS;

	/**
	 * The number of structural features of the '<em>Primitive Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.EnumerationLiteralImpl <em>Enumeration Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.EnumerationLiteralImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEnumerationLiteral()
	 * @generated
	 */
	int ENUMERATION_LITERAL = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The number of structural features of the '<em>Enumeration Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.StateImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getState()
	 * @generated
	 */
	int STATE = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OUTGOING = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__INCOMING = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ENTRY = ANNOTATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Exit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__EXIT = ANNOTATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__PROPERTIES = ANNOTATED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Internal</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__INTERNAL = ANNOTATED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.CompositeStateImpl <em>Composite State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.CompositeStateImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getCompositeState()
	 * @generated
	 */
	int COMPOSITE_STATE = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE__ANNOTATIONS = STATE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE__OUTGOING = STATE__OUTGOING;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE__INCOMING = STATE__INCOMING;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE__ENTRY = STATE__ENTRY;

	/**
	 * The feature id for the '<em><b>Exit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE__EXIT = STATE__EXIT;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE__PROPERTIES = STATE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Internal</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE__INTERNAL = STATE__INTERNAL;

	/**
	 * The feature id for the '<em><b>Substate</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE__SUBSTATE = STATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initial</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE__INITIAL = STATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>History</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE__HISTORY = STATE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Region</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE__REGION = STATE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Composite State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_STATE_FEATURE_COUNT = STATE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.StateMachineImpl <em>State Machine</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.StateMachineImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStateMachine()
	 * @generated
	 */
	int STATE_MACHINE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__NAME = COMPOSITE_STATE__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__ANNOTATIONS = COMPOSITE_STATE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__OUTGOING = COMPOSITE_STATE__OUTGOING;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__INCOMING = COMPOSITE_STATE__INCOMING;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__ENTRY = COMPOSITE_STATE__ENTRY;

	/**
	 * The feature id for the '<em><b>Exit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__EXIT = COMPOSITE_STATE__EXIT;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__PROPERTIES = COMPOSITE_STATE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Internal</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__INTERNAL = COMPOSITE_STATE__INTERNAL;

	/**
	 * The feature id for the '<em><b>Substate</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__SUBSTATE = COMPOSITE_STATE__SUBSTATE;

	/**
	 * The feature id for the '<em><b>Initial</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__INITIAL = COMPOSITE_STATE__INITIAL;

	/**
	 * The feature id for the '<em><b>History</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__HISTORY = COMPOSITE_STATE__HISTORY;

	/**
	 * The feature id for the '<em><b>Region</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__REGION = COMPOSITE_STATE__REGION;

	/**
	 * The number of structural features of the '<em>State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_FEATURE_COUNT = COMPOSITE_STATE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.HandlerImpl <em>Handler</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.HandlerImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getHandler()
	 * @generated
	 */
	int HANDLER = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDLER__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDLER__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Event</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDLER__EVENT = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDLER__GUARD = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDLER__ACTION = ANNOTATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Handler</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HANDLER_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.TransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.TransitionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__NAME = HANDLER__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ANNOTATIONS = HANDLER__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Event</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__EVENT = HANDLER__EVENT;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__GUARD = HANDLER__GUARD;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ACTION = HANDLER__ACTION;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TARGET = HANDLER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SOURCE = HANDLER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = HANDLER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.InternalTransitionImpl <em>Internal Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.InternalTransitionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getInternalTransition()
	 * @generated
	 */
	int INTERNAL_TRANSITION = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_TRANSITION__NAME = HANDLER__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_TRANSITION__ANNOTATIONS = HANDLER__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Event</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_TRANSITION__EVENT = HANDLER__EVENT;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_TRANSITION__GUARD = HANDLER__GUARD;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_TRANSITION__ACTION = HANDLER__ACTION;

	/**
	 * The number of structural features of the '<em>Internal Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_TRANSITION_FEATURE_COUNT = HANDLER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.RegionImpl <em>Region</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.RegionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getRegion()
	 * @generated
	 */
	int REGION = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Substate</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__SUBSTATE = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initial</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__INITIAL = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>History</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__HISTORY = ANNOTATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Region</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ParallelRegionImpl <em>Parallel Region</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ParallelRegionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getParallelRegion()
	 * @generated
	 */
	int PARALLEL_REGION = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_REGION__NAME = REGION__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_REGION__ANNOTATIONS = REGION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Substate</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_REGION__SUBSTATE = REGION__SUBSTATE;

	/**
	 * The feature id for the '<em><b>Initial</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_REGION__INITIAL = REGION__INITIAL;

	/**
	 * The feature id for the '<em><b>History</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_REGION__HISTORY = REGION__HISTORY;

	/**
	 * The number of structural features of the '<em>Parallel Region</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_REGION_FEATURE_COUNT = REGION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ActionImpl <em>Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ActionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 20;

	/**
	 * The number of structural features of the '<em>Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ActionBlockImpl <em>Action Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ActionBlockImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getActionBlock()
	 * @generated
	 */
	int ACTION_BLOCK = 21;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_BLOCK__ACTIONS = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Action Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_BLOCK_FEATURE_COUNT = ACTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ExternStatementImpl <em>Extern Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ExternStatementImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getExternStatement()
	 * @generated
	 */
	int EXTERN_STATEMENT = 22;

	/**
	 * The feature id for the '<em><b>Statement</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERN_STATEMENT__STATEMENT = ACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERN_STATEMENT__SEGMENTS = ACTION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Extern Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERN_STATEMENT_FEATURE_COUNT = ACTION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ExpressionImpl <em>Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 23;

	/**
	 * The number of structural features of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ExternExpressionImpl <em>Extern Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ExternExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getExternExpression()
	 * @generated
	 */
	int EXTERN_EXPRESSION = 24;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERN_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERN_EXPRESSION__SEGMENTS = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Extern Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERN_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.SendActionImpl <em>Send Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.SendActionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSendAction()
	 * @generated
	 */
	int SEND_ACTION = 25;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEND_ACTION__PARAMETERS = ACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEND_ACTION__MESSAGE = ACTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEND_ACTION__PORT = ACTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Send Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEND_ACTION_FEATURE_COUNT = ACTION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PropertyAssignmentImpl <em>Property Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PropertyAssignmentImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPropertyAssignment()
	 * @generated
	 */
	int PROPERTY_ASSIGNMENT = 26;

	/**
	 * The feature id for the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT__PROPERTY = ACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT__EXPRESSION = ACTION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Property Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_FEATURE_COUNT = ACTION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.EventImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEvent()
	 * @generated
	 */
	int EVENT = 27;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__NAME = THING_ML_ELEMENT__NAME;

	/**
	 * The number of structural features of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FEATURE_COUNT = THING_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ReceiveMessageImpl <em>Receive Message</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ReceiveMessageImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getReceiveMessage()
	 * @generated
	 */
	int RECEIVE_MESSAGE = 28;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECEIVE_MESSAGE__NAME = EVENT__NAME;

	/**
	 * The feature id for the '<em><b>Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECEIVE_MESSAGE__MESSAGE = EVENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECEIVE_MESSAGE__PORT = EVENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Receive Message</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECEIVE_MESSAGE_FEATURE_COUNT = EVENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.DictionaryImpl <em>Dictionary</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.DictionaryImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getDictionary()
	 * @generated
	 */
	int DICTIONARY = 29;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICTIONARY__NAME = PROPERTY__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICTIONARY__ANNOTATIONS = PROPERTY__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICTIONARY__TYPE = PROPERTY__TYPE;

	/**
	 * The feature id for the '<em><b>Changeable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICTIONARY__CHANGEABLE = PROPERTY__CHANGEABLE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICTIONARY__LOWER_BOUND = PROPERTY__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICTIONARY__UPPER_BOUND = PROPERTY__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Index Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICTIONARY__INDEX_TYPE = PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Dictionary</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICTIONARY_FEATURE_COUNT = PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PortImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPort()
	 * @generated
	 */
	int PORT = 30;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__OWNER = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Receives</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__RECEIVES = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sends</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__SENDS = ANNOTATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.EventReferenceImpl <em>Event Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.EventReferenceImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEventReference()
	 * @generated
	 */
	int EVENT_REFERENCE = 31;

	/**
	 * The feature id for the '<em><b>Msg Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_REFERENCE__MSG_REF = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Param Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_REFERENCE__PARAM_REF = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Event Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_REFERENCE_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.LitteralImpl <em>Litteral</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.LitteralImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLitteral()
	 * @generated
	 */
	int LITTERAL = 32;

	/**
	 * The number of structural features of the '<em>Litteral</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITTERAL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.IntegerLitteralImpl <em>Integer Litteral</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.IntegerLitteralImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getIntegerLitteral()
	 * @generated
	 */
	int INTEGER_LITTERAL = 33;

	/**
	 * The feature id for the '<em><b>Int Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITTERAL__INT_VALUE = LITTERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Litteral</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITTERAL_FEATURE_COUNT = LITTERAL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.BooleanLitteralImpl <em>Boolean Litteral</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.BooleanLitteralImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getBooleanLitteral()
	 * @generated
	 */
	int BOOLEAN_LITTERAL = 34;

	/**
	 * The feature id for the '<em><b>Bool Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITTERAL__BOOL_VALUE = LITTERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Litteral</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITTERAL_FEATURE_COUNT = LITTERAL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.StringLitteralImpl <em>String Litteral</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.StringLitteralImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStringLitteral()
	 * @generated
	 */
	int STRING_LITTERAL = 35;

	/**
	 * The feature id for the '<em><b>String Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITTERAL__STRING_VALUE = LITTERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Litteral</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITTERAL_FEATURE_COUNT = LITTERAL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.DoubleLitteralImpl <em>Double Litteral</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.DoubleLitteralImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getDoubleLitteral()
	 * @generated
	 */
	int DOUBLE_LITTERAL = 36;

	/**
	 * The feature id for the '<em><b>Double Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITTERAL__DOUBLE_VALUE = LITTERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Double Litteral</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITTERAL_FEATURE_COUNT = LITTERAL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.UnaryExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getUnaryExpression()
	 * @generated
	 */
	int UNARY_EXPRESSION = 37;

	/**
	 * The feature id for the '<em><b>Term</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__TERM = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.NotExpressionImpl <em>Not Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.NotExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getNotExpression()
	 * @generated
	 */
	int NOT_EXPRESSION = 38;

	/**
	 * The feature id for the '<em><b>Term</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_EXPRESSION__TERM = UNARY_EXPRESSION__TERM;

	/**
	 * The number of structural features of the '<em>Not Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_EXPRESSION_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.UnaryMinusImpl <em>Unary Minus</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.UnaryMinusImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getUnaryMinus()
	 * @generated
	 */
	int UNARY_MINUS = 39;

	/**
	 * The feature id for the '<em><b>Term</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_MINUS__TERM = UNARY_EXPRESSION__TERM;

	/**
	 * The number of structural features of the '<em>Unary Minus</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_MINUS_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.BinaryExpressionImpl <em>Binary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.BinaryExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getBinaryExpression()
	 * @generated
	 */
	int BINARY_EXPRESSION = 40;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__LHS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__RHS = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Binary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PlusExpressionImpl <em>Plus Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PlusExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPlusExpression()
	 * @generated
	 */
	int PLUS_EXPRESSION = 41;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUS_EXPRESSION__LHS = BINARY_EXPRESSION__LHS;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUS_EXPRESSION__RHS = BINARY_EXPRESSION__RHS;

	/**
	 * The number of structural features of the '<em>Plus Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUS_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.MinusExpressionImpl <em>Minus Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.MinusExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getMinusExpression()
	 * @generated
	 */
	int MINUS_EXPRESSION = 42;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINUS_EXPRESSION__LHS = BINARY_EXPRESSION__LHS;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINUS_EXPRESSION__RHS = BINARY_EXPRESSION__RHS;

	/**
	 * The number of structural features of the '<em>Minus Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINUS_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.TimesExpressionImpl <em>Times Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.TimesExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getTimesExpression()
	 * @generated
	 */
	int TIMES_EXPRESSION = 43;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMES_EXPRESSION__LHS = BINARY_EXPRESSION__LHS;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMES_EXPRESSION__RHS = BINARY_EXPRESSION__RHS;

	/**
	 * The number of structural features of the '<em>Times Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMES_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.DivExpressionImpl <em>Div Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.DivExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getDivExpression()
	 * @generated
	 */
	int DIV_EXPRESSION = 44;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIV_EXPRESSION__LHS = BINARY_EXPRESSION__LHS;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIV_EXPRESSION__RHS = BINARY_EXPRESSION__RHS;

	/**
	 * The number of structural features of the '<em>Div Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIV_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ModExpressionImpl <em>Mod Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ModExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getModExpression()
	 * @generated
	 */
	int MOD_EXPRESSION = 45;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOD_EXPRESSION__LHS = BINARY_EXPRESSION__LHS;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOD_EXPRESSION__RHS = BINARY_EXPRESSION__RHS;

	/**
	 * The number of structural features of the '<em>Mod Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOD_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.EqualsExpressionImpl <em>Equals Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.EqualsExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEqualsExpression()
	 * @generated
	 */
	int EQUALS_EXPRESSION = 46;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALS_EXPRESSION__LHS = BINARY_EXPRESSION__LHS;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALS_EXPRESSION__RHS = BINARY_EXPRESSION__RHS;

	/**
	 * The number of structural features of the '<em>Equals Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALS_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.GreaterExpressionImpl <em>Greater Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.GreaterExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getGreaterExpression()
	 * @generated
	 */
	int GREATER_EXPRESSION = 47;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GREATER_EXPRESSION__LHS = BINARY_EXPRESSION__LHS;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GREATER_EXPRESSION__RHS = BINARY_EXPRESSION__RHS;

	/**
	 * The number of structural features of the '<em>Greater Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GREATER_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.LowerExpressionImpl <em>Lower Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.LowerExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLowerExpression()
	 * @generated
	 */
	int LOWER_EXPRESSION = 48;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOWER_EXPRESSION__LHS = BINARY_EXPRESSION__LHS;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOWER_EXPRESSION__RHS = BINARY_EXPRESSION__RHS;

	/**
	 * The number of structural features of the '<em>Lower Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOWER_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.AndExpressionImpl <em>And Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.AndExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getAndExpression()
	 * @generated
	 */
	int AND_EXPRESSION = 49;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION__LHS = BINARY_EXPRESSION__LHS;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION__RHS = BINARY_EXPRESSION__RHS;

	/**
	 * The number of structural features of the '<em>And Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.OrExpressionImpl <em>Or Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.OrExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getOrExpression()
	 * @generated
	 */
	int OR_EXPRESSION = 50;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION__LHS = BINARY_EXPRESSION__LHS;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION__RHS = BINARY_EXPRESSION__RHS;

	/**
	 * The number of structural features of the '<em>Or Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ControlStructureImpl <em>Control Structure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ControlStructureImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getControlStructure()
	 * @generated
	 */
	int CONTROL_STRUCTURE = 51;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_STRUCTURE__ACTION = ACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_STRUCTURE__CONDITION = ACTION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Control Structure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_STRUCTURE_FEATURE_COUNT = ACTION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.LoopActionImpl <em>Loop Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.LoopActionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLoopAction()
	 * @generated
	 */
	int LOOP_ACTION = 52;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_ACTION__ACTION = CONTROL_STRUCTURE__ACTION;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_ACTION__CONDITION = CONTROL_STRUCTURE__CONDITION;

	/**
	 * The number of structural features of the '<em>Loop Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOP_ACTION_FEATURE_COUNT = CONTROL_STRUCTURE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ConditionalActionImpl <em>Conditional Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ConditionalActionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getConditionalAction()
	 * @generated
	 */
	int CONDITIONAL_ACTION = 53;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_ACTION__ACTION = CONTROL_STRUCTURE__ACTION;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_ACTION__CONDITION = CONTROL_STRUCTURE__CONDITION;

	/**
	 * The number of structural features of the '<em>Conditional Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_ACTION_FEATURE_COUNT = CONTROL_STRUCTURE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PropertyReferenceImpl <em>Property Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PropertyReferenceImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPropertyReference()
	 * @generated
	 */
	int PROPERTY_REFERENCE = 54;

	/**
	 * The feature id for the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_REFERENCE__PROPERTY = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Property Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_REFERENCE_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.DictionaryReferenceImpl <em>Dictionary Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.DictionaryReferenceImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getDictionaryReference()
	 * @generated
	 */
	int DICTIONARY_REFERENCE = 55;

	/**
	 * The feature id for the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICTIONARY_REFERENCE__PROPERTY = PROPERTY_REFERENCE__PROPERTY;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICTIONARY_REFERENCE__INDEX = PROPERTY_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Dictionary Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICTIONARY_REFERENCE_FEATURE_COUNT = PROPERTY_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ExpressionGroupImpl <em>Expression Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ExpressionGroupImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getExpressionGroup()
	 * @generated
	 */
	int EXPRESSION_GROUP = 56;

	/**
	 * The feature id for the '<em><b>Exp</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_GROUP__EXP = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expression Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_GROUP_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PrintActionImpl <em>Print Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PrintActionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPrintAction()
	 * @generated
	 */
	int PRINT_ACTION = 57;

	/**
	 * The feature id for the '<em><b>Msg</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRINT_ACTION__MSG = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Print Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRINT_ACTION_FEATURE_COUNT = ACTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ErrorActionImpl <em>Error Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ErrorActionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getErrorAction()
	 * @generated
	 */
	int ERROR_ACTION = 58;

	/**
	 * The feature id for the '<em><b>Msg</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_ACTION__MSG = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Error Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_ACTION_FEATURE_COUNT = ACTION_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ThingMLModel <em>Thing ML Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Thing ML Model</em>'.
	 * @see org.sintef.thingml.ThingMLModel
	 * @generated
	 */
	EClass getThingMLModel();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.ThingMLModel#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see org.sintef.thingml.ThingMLModel#getTypes()
	 * @see #getThingMLModel()
	 * @generated
	 */
	EReference getThingMLModel_Types();

	/**
	 * Returns the meta object for the reference list '{@link org.sintef.thingml.ThingMLModel#getImports <em>Imports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Imports</em>'.
	 * @see org.sintef.thingml.ThingMLModel#getImports()
	 * @see #getThingMLModel()
	 * @generated
	 */
	EReference getThingMLModel_Imports();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.ThingMLModel#getMessages <em>Messages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Messages</em>'.
	 * @see org.sintef.thingml.ThingMLModel#getMessages()
	 * @see #getThingMLModel()
	 * @generated
	 */
	EReference getThingMLModel_Messages();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Message <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Message</em>'.
	 * @see org.sintef.thingml.Message
	 * @generated
	 */
	EClass getMessage();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Message#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.sintef.thingml.Message#getParameters()
	 * @see #getMessage()
	 * @generated
	 */
	EReference getMessage_Parameters();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Thing <em>Thing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Thing</em>'.
	 * @see org.sintef.thingml.Thing
	 * @generated
	 */
	EClass getThing();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Thing#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.sintef.thingml.Thing#getProperties()
	 * @see #getThing()
	 * @generated
	 */
	EReference getThing_Properties();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.Thing#isFragment <em>Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fragment</em>'.
	 * @see org.sintef.thingml.Thing#isFragment()
	 * @see #getThing()
	 * @generated
	 */
	EAttribute getThing_Fragment();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Thing#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see org.sintef.thingml.Thing#getPorts()
	 * @see #getThing()
	 * @generated
	 */
	EReference getThing_Ports();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Thing#getBehaviour <em>Behaviour</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Behaviour</em>'.
	 * @see org.sintef.thingml.Thing#getBehaviour()
	 * @see #getThing()
	 * @generated
	 */
	EReference getThing_Behaviour();

	/**
	 * Returns the meta object for the reference list '{@link org.sintef.thingml.Thing#getIncludes <em>Includes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Includes</em>'.
	 * @see org.sintef.thingml.Thing#getIncludes()
	 * @see #getThing()
	 * @generated
	 */
	EReference getThing_Includes();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.sintef.thingml.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.Parameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sintef.thingml.Parameter#getType()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_Type();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ThingMLElement <em>Thing ML Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Thing ML Element</em>'.
	 * @see org.sintef.thingml.ThingMLElement
	 * @generated
	 */
	EClass getThingMLElement();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.ThingMLElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sintef.thingml.ThingMLElement#getName()
	 * @see #getThingMLElement()
	 * @generated
	 */
	EAttribute getThingMLElement_Name();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see org.sintef.thingml.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.sintef.thingml.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.Property#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sintef.thingml.Property#getType()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.Property#isChangeable <em>Changeable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Changeable</em>'.
	 * @see org.sintef.thingml.Property#isChangeable()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Changeable();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.Property#getLowerBound <em>Lower Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower Bound</em>'.
	 * @see org.sintef.thingml.Property#getLowerBound()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_LowerBound();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.Property#getUpperBound <em>Upper Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper Bound</em>'.
	 * @see org.sintef.thingml.Property#getUpperBound()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_UpperBound();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.PlatformAnnotation <em>Platform Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Platform Annotation</em>'.
	 * @see org.sintef.thingml.PlatformAnnotation
	 * @generated
	 */
	EClass getPlatformAnnotation();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.PlatformAnnotation#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.sintef.thingml.PlatformAnnotation#getValue()
	 * @see #getPlatformAnnotation()
	 * @generated
	 */
	EAttribute getPlatformAnnotation_Value();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Enumeration <em>Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumeration</em>'.
	 * @see org.sintef.thingml.Enumeration
	 * @generated
	 */
	EClass getEnumeration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Enumeration#getLiterals <em>Literals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Literals</em>'.
	 * @see org.sintef.thingml.Enumeration#getLiterals()
	 * @see #getEnumeration()
	 * @generated
	 */
	EReference getEnumeration_Literals();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.PrimitiveType <em>Primitive Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Primitive Type</em>'.
	 * @see org.sintef.thingml.PrimitiveType
	 * @generated
	 */
	EClass getPrimitiveType();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.EnumerationLiteral <em>Enumeration Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumeration Literal</em>'.
	 * @see org.sintef.thingml.EnumerationLiteral
	 * @generated
	 */
	EClass getEnumerationLiteral();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.AnnotatedElement <em>Annotated Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotated Element</em>'.
	 * @see org.sintef.thingml.AnnotatedElement
	 * @generated
	 */
	EClass getAnnotatedElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.AnnotatedElement#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotations</em>'.
	 * @see org.sintef.thingml.AnnotatedElement#getAnnotations()
	 * @see #getAnnotatedElement()
	 * @generated
	 */
	EReference getAnnotatedElement_Annotations();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.StateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Machine</em>'.
	 * @see org.sintef.thingml.StateMachine
	 * @generated
	 */
	EClass getStateMachine();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Handler <em>Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Handler</em>'.
	 * @see org.sintef.thingml.Handler
	 * @generated
	 */
	EClass getHandler();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Handler#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event</em>'.
	 * @see org.sintef.thingml.Handler#getEvent()
	 * @see #getHandler()
	 * @generated
	 */
	EReference getHandler_Event();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Handler#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Guard</em>'.
	 * @see org.sintef.thingml.Handler#getGuard()
	 * @see #getHandler()
	 * @generated
	 */
	EReference getHandler_Guard();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Handler#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action</em>'.
	 * @see org.sintef.thingml.Handler#getAction()
	 * @see #getHandler()
	 * @generated
	 */
	EReference getHandler_Action();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see org.sintef.thingml.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.Transition#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.sintef.thingml.Transition#getTarget()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Target();

	/**
	 * Returns the meta object for the container reference '{@link org.sintef.thingml.Transition#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Source</em>'.
	 * @see org.sintef.thingml.Transition#getSource()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Source();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.InternalTransition <em>Internal Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Internal Transition</em>'.
	 * @see org.sintef.thingml.InternalTransition
	 * @generated
	 */
	EClass getInternalTransition();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see org.sintef.thingml.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.State#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outgoing</em>'.
	 * @see org.sintef.thingml.State#getOutgoing()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Outgoing();

	/**
	 * Returns the meta object for the reference list '{@link org.sintef.thingml.State#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see org.sintef.thingml.State#getIncoming()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Incoming();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.State#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entry</em>'.
	 * @see org.sintef.thingml.State#getEntry()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Entry();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.State#getExit <em>Exit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exit</em>'.
	 * @see org.sintef.thingml.State#getExit()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Exit();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.State#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.sintef.thingml.State#getProperties()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Properties();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.State#getInternal <em>Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Internal</em>'.
	 * @see org.sintef.thingml.State#getInternal()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Internal();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.CompositeState <em>Composite State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite State</em>'.
	 * @see org.sintef.thingml.CompositeState
	 * @generated
	 */
	EClass getCompositeState();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.CompositeState#getRegion <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Region</em>'.
	 * @see org.sintef.thingml.CompositeState#getRegion()
	 * @see #getCompositeState()
	 * @generated
	 */
	EReference getCompositeState_Region();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Region <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Region</em>'.
	 * @see org.sintef.thingml.Region
	 * @generated
	 */
	EClass getRegion();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Region#getSubstate <em>Substate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Substate</em>'.
	 * @see org.sintef.thingml.Region#getSubstate()
	 * @see #getRegion()
	 * @generated
	 */
	EReference getRegion_Substate();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.Region#getInitial <em>Initial</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Initial</em>'.
	 * @see org.sintef.thingml.Region#getInitial()
	 * @see #getRegion()
	 * @generated
	 */
	EReference getRegion_Initial();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.Region#isHistory <em>History</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>History</em>'.
	 * @see org.sintef.thingml.Region#isHistory()
	 * @see #getRegion()
	 * @generated
	 */
	EAttribute getRegion_History();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ParallelRegion <em>Parallel Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parallel Region</em>'.
	 * @see org.sintef.thingml.ParallelRegion
	 * @generated
	 */
	EClass getParallelRegion();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action</em>'.
	 * @see org.sintef.thingml.Action
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ActionBlock <em>Action Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Block</em>'.
	 * @see org.sintef.thingml.ActionBlock
	 * @generated
	 */
	EClass getActionBlock();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.ActionBlock#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see org.sintef.thingml.ActionBlock#getActions()
	 * @see #getActionBlock()
	 * @generated
	 */
	EReference getActionBlock_Actions();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ExternStatement <em>Extern Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extern Statement</em>'.
	 * @see org.sintef.thingml.ExternStatement
	 * @generated
	 */
	EClass getExternStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.ExternStatement#getStatement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Statement</em>'.
	 * @see org.sintef.thingml.ExternStatement#getStatement()
	 * @see #getExternStatement()
	 * @generated
	 */
	EAttribute getExternStatement_Statement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.ExternStatement#getSegments <em>Segments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Segments</em>'.
	 * @see org.sintef.thingml.ExternStatement#getSegments()
	 * @see #getExternStatement()
	 * @generated
	 */
	EReference getExternStatement_Segments();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see org.sintef.thingml.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ExternExpression <em>Extern Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extern Expression</em>'.
	 * @see org.sintef.thingml.ExternExpression
	 * @generated
	 */
	EClass getExternExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.ExternExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see org.sintef.thingml.ExternExpression#getExpression()
	 * @see #getExternExpression()
	 * @generated
	 */
	EAttribute getExternExpression_Expression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.ExternExpression#getSegments <em>Segments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Segments</em>'.
	 * @see org.sintef.thingml.ExternExpression#getSegments()
	 * @see #getExternExpression()
	 * @generated
	 */
	EReference getExternExpression_Segments();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.SendAction <em>Send Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Send Action</em>'.
	 * @see org.sintef.thingml.SendAction
	 * @generated
	 */
	EClass getSendAction();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.SendAction#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.sintef.thingml.SendAction#getParameters()
	 * @see #getSendAction()
	 * @generated
	 */
	EReference getSendAction_Parameters();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.SendAction#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Message</em>'.
	 * @see org.sintef.thingml.SendAction#getMessage()
	 * @see #getSendAction()
	 * @generated
	 */
	EReference getSendAction_Message();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.SendAction#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Port</em>'.
	 * @see org.sintef.thingml.SendAction#getPort()
	 * @see #getSendAction()
	 * @generated
	 */
	EReference getSendAction_Port();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.PropertyAssignment <em>Property Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Assignment</em>'.
	 * @see org.sintef.thingml.PropertyAssignment
	 * @generated
	 */
	EClass getPropertyAssignment();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.PropertyAssignment#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property</em>'.
	 * @see org.sintef.thingml.PropertyAssignment#getProperty()
	 * @see #getPropertyAssignment()
	 * @generated
	 */
	EReference getPropertyAssignment_Property();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.PropertyAssignment#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.sintef.thingml.PropertyAssignment#getExpression()
	 * @see #getPropertyAssignment()
	 * @generated
	 */
	EReference getPropertyAssignment_Expression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event</em>'.
	 * @see org.sintef.thingml.Event
	 * @generated
	 */
	EClass getEvent();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ReceiveMessage <em>Receive Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Receive Message</em>'.
	 * @see org.sintef.thingml.ReceiveMessage
	 * @generated
	 */
	EClass getReceiveMessage();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.ReceiveMessage#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Message</em>'.
	 * @see org.sintef.thingml.ReceiveMessage#getMessage()
	 * @see #getReceiveMessage()
	 * @generated
	 */
	EReference getReceiveMessage_Message();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.ReceiveMessage#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Port</em>'.
	 * @see org.sintef.thingml.ReceiveMessage#getPort()
	 * @see #getReceiveMessage()
	 * @generated
	 */
	EReference getReceiveMessage_Port();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Dictionary <em>Dictionary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dictionary</em>'.
	 * @see org.sintef.thingml.Dictionary
	 * @generated
	 */
	EClass getDictionary();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.Dictionary#getIndexType <em>Index Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Index Type</em>'.
	 * @see org.sintef.thingml.Dictionary#getIndexType()
	 * @see #getDictionary()
	 * @generated
	 */
	EReference getDictionary_IndexType();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Port <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see org.sintef.thingml.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the container reference '{@link org.sintef.thingml.Port#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see org.sintef.thingml.Port#getOwner()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_Owner();

	/**
	 * Returns the meta object for the reference list '{@link org.sintef.thingml.Port#getReceives <em>Receives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Receives</em>'.
	 * @see org.sintef.thingml.Port#getReceives()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_Receives();

	/**
	 * Returns the meta object for the reference list '{@link org.sintef.thingml.Port#getSends <em>Sends</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sends</em>'.
	 * @see org.sintef.thingml.Port#getSends()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_Sends();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.EventReference <em>Event Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Reference</em>'.
	 * @see org.sintef.thingml.EventReference
	 * @generated
	 */
	EClass getEventReference();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.EventReference#getMsgRef <em>Msg Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Msg Ref</em>'.
	 * @see org.sintef.thingml.EventReference#getMsgRef()
	 * @see #getEventReference()
	 * @generated
	 */
	EReference getEventReference_MsgRef();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.EventReference#getParamRef <em>Param Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Param Ref</em>'.
	 * @see org.sintef.thingml.EventReference#getParamRef()
	 * @see #getEventReference()
	 * @generated
	 */
	EReference getEventReference_ParamRef();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Litteral <em>Litteral</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Litteral</em>'.
	 * @see org.sintef.thingml.Litteral
	 * @generated
	 */
	EClass getLitteral();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.IntegerLitteral <em>Integer Litteral</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Litteral</em>'.
	 * @see org.sintef.thingml.IntegerLitteral
	 * @generated
	 */
	EClass getIntegerLitteral();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.IntegerLitteral#getIntValue <em>Int Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Int Value</em>'.
	 * @see org.sintef.thingml.IntegerLitteral#getIntValue()
	 * @see #getIntegerLitteral()
	 * @generated
	 */
	EAttribute getIntegerLitteral_IntValue();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.BooleanLitteral <em>Boolean Litteral</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Litteral</em>'.
	 * @see org.sintef.thingml.BooleanLitteral
	 * @generated
	 */
	EClass getBooleanLitteral();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.BooleanLitteral#isBoolValue <em>Bool Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bool Value</em>'.
	 * @see org.sintef.thingml.BooleanLitteral#isBoolValue()
	 * @see #getBooleanLitteral()
	 * @generated
	 */
	EAttribute getBooleanLitteral_BoolValue();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.StringLitteral <em>String Litteral</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Litteral</em>'.
	 * @see org.sintef.thingml.StringLitteral
	 * @generated
	 */
	EClass getStringLitteral();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.StringLitteral#getStringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String Value</em>'.
	 * @see org.sintef.thingml.StringLitteral#getStringValue()
	 * @see #getStringLitteral()
	 * @generated
	 */
	EAttribute getStringLitteral_StringValue();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.DoubleLitteral <em>Double Litteral</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Double Litteral</em>'.
	 * @see org.sintef.thingml.DoubleLitteral
	 * @generated
	 */
	EClass getDoubleLitteral();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.DoubleLitteral#getDoubleValue <em>Double Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Double Value</em>'.
	 * @see org.sintef.thingml.DoubleLitteral#getDoubleValue()
	 * @see #getDoubleLitteral()
	 * @generated
	 */
	EAttribute getDoubleLitteral_DoubleValue();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.UnaryExpression <em>Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Expression</em>'.
	 * @see org.sintef.thingml.UnaryExpression
	 * @generated
	 */
	EClass getUnaryExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.UnaryExpression#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Term</em>'.
	 * @see org.sintef.thingml.UnaryExpression#getTerm()
	 * @see #getUnaryExpression()
	 * @generated
	 */
	EReference getUnaryExpression_Term();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.NotExpression <em>Not Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Not Expression</em>'.
	 * @see org.sintef.thingml.NotExpression
	 * @generated
	 */
	EClass getNotExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.UnaryMinus <em>Unary Minus</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Minus</em>'.
	 * @see org.sintef.thingml.UnaryMinus
	 * @generated
	 */
	EClass getUnaryMinus();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.BinaryExpression <em>Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Expression</em>'.
	 * @see org.sintef.thingml.BinaryExpression
	 * @generated
	 */
	EClass getBinaryExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.BinaryExpression#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.sintef.thingml.BinaryExpression#getLhs()
	 * @see #getBinaryExpression()
	 * @generated
	 */
	EReference getBinaryExpression_Lhs();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.BinaryExpression#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.sintef.thingml.BinaryExpression#getRhs()
	 * @see #getBinaryExpression()
	 * @generated
	 */
	EReference getBinaryExpression_Rhs();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.PlusExpression <em>Plus Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plus Expression</em>'.
	 * @see org.sintef.thingml.PlusExpression
	 * @generated
	 */
	EClass getPlusExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.MinusExpression <em>Minus Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Minus Expression</em>'.
	 * @see org.sintef.thingml.MinusExpression
	 * @generated
	 */
	EClass getMinusExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.TimesExpression <em>Times Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Times Expression</em>'.
	 * @see org.sintef.thingml.TimesExpression
	 * @generated
	 */
	EClass getTimesExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.DivExpression <em>Div Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Div Expression</em>'.
	 * @see org.sintef.thingml.DivExpression
	 * @generated
	 */
	EClass getDivExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ModExpression <em>Mod Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mod Expression</em>'.
	 * @see org.sintef.thingml.ModExpression
	 * @generated
	 */
	EClass getModExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.EqualsExpression <em>Equals Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Equals Expression</em>'.
	 * @see org.sintef.thingml.EqualsExpression
	 * @generated
	 */
	EClass getEqualsExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.GreaterExpression <em>Greater Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Greater Expression</em>'.
	 * @see org.sintef.thingml.GreaterExpression
	 * @generated
	 */
	EClass getGreaterExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.LowerExpression <em>Lower Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lower Expression</em>'.
	 * @see org.sintef.thingml.LowerExpression
	 * @generated
	 */
	EClass getLowerExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.AndExpression <em>And Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>And Expression</em>'.
	 * @see org.sintef.thingml.AndExpression
	 * @generated
	 */
	EClass getAndExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.OrExpression <em>Or Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Or Expression</em>'.
	 * @see org.sintef.thingml.OrExpression
	 * @generated
	 */
	EClass getOrExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ControlStructure <em>Control Structure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Control Structure</em>'.
	 * @see org.sintef.thingml.ControlStructure
	 * @generated
	 */
	EClass getControlStructure();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.ControlStructure#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action</em>'.
	 * @see org.sintef.thingml.ControlStructure#getAction()
	 * @see #getControlStructure()
	 * @generated
	 */
	EReference getControlStructure_Action();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.ControlStructure#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.sintef.thingml.ControlStructure#getCondition()
	 * @see #getControlStructure()
	 * @generated
	 */
	EReference getControlStructure_Condition();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.LoopAction <em>Loop Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Loop Action</em>'.
	 * @see org.sintef.thingml.LoopAction
	 * @generated
	 */
	EClass getLoopAction();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ConditionalAction <em>Conditional Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditional Action</em>'.
	 * @see org.sintef.thingml.ConditionalAction
	 * @generated
	 */
	EClass getConditionalAction();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.PropertyReference <em>Property Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Reference</em>'.
	 * @see org.sintef.thingml.PropertyReference
	 * @generated
	 */
	EClass getPropertyReference();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.PropertyReference#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property</em>'.
	 * @see org.sintef.thingml.PropertyReference#getProperty()
	 * @see #getPropertyReference()
	 * @generated
	 */
	EReference getPropertyReference_Property();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.DictionaryReference <em>Dictionary Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dictionary Reference</em>'.
	 * @see org.sintef.thingml.DictionaryReference
	 * @generated
	 */
	EClass getDictionaryReference();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.DictionaryReference#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index</em>'.
	 * @see org.sintef.thingml.DictionaryReference#getIndex()
	 * @see #getDictionaryReference()
	 * @generated
	 */
	EReference getDictionaryReference_Index();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ExpressionGroup <em>Expression Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression Group</em>'.
	 * @see org.sintef.thingml.ExpressionGroup
	 * @generated
	 */
	EClass getExpressionGroup();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.ExpressionGroup#getExp <em>Exp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exp</em>'.
	 * @see org.sintef.thingml.ExpressionGroup#getExp()
	 * @see #getExpressionGroup()
	 * @generated
	 */
	EReference getExpressionGroup_Exp();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.PrintAction <em>Print Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Print Action</em>'.
	 * @see org.sintef.thingml.PrintAction
	 * @generated
	 */
	EClass getPrintAction();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.PrintAction#getMsg <em>Msg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Msg</em>'.
	 * @see org.sintef.thingml.PrintAction#getMsg()
	 * @see #getPrintAction()
	 * @generated
	 */
	EReference getPrintAction_Msg();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ErrorAction <em>Error Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Error Action</em>'.
	 * @see org.sintef.thingml.ErrorAction
	 * @generated
	 */
	EClass getErrorAction();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.ErrorAction#getMsg <em>Msg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Msg</em>'.
	 * @see org.sintef.thingml.ErrorAction#getMsg()
	 * @see #getErrorAction()
	 * @generated
	 */
	EReference getErrorAction_Msg();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ThingmlFactory getThingmlFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ThingMLModelImpl <em>Thing ML Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ThingMLModelImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getThingMLModel()
		 * @generated
		 */
		EClass THING_ML_MODEL = eINSTANCE.getThingMLModel();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING_ML_MODEL__TYPES = eINSTANCE.getThingMLModel_Types();

		/**
		 * The meta object literal for the '<em><b>Imports</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING_ML_MODEL__IMPORTS = eINSTANCE.getThingMLModel_Imports();

		/**
		 * The meta object literal for the '<em><b>Messages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING_ML_MODEL__MESSAGES = eINSTANCE.getThingMLModel_Messages();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.MessageImpl <em>Message</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.MessageImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getMessage()
		 * @generated
		 */
		EClass MESSAGE = eINSTANCE.getMessage();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MESSAGE__PARAMETERS = eINSTANCE.getMessage_Parameters();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ThingImpl <em>Thing</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ThingImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getThing()
		 * @generated
		 */
		EClass THING = eINSTANCE.getThing();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING__PROPERTIES = eINSTANCE.getThing_Properties();

		/**
		 * The meta object literal for the '<em><b>Fragment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute THING__FRAGMENT = eINSTANCE.getThing_Fragment();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING__PORTS = eINSTANCE.getThing_Ports();

		/**
		 * The meta object literal for the '<em><b>Behaviour</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING__BEHAVIOUR = eINSTANCE.getThing_Behaviour();

		/**
		 * The meta object literal for the '<em><b>Includes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING__INCLUDES = eINSTANCE.getThing_Includes();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ParameterImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__TYPE = eINSTANCE.getParameter_Type();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ThingMLElementImpl <em>Thing ML Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ThingMLElementImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getThingMLElement()
		 * @generated
		 */
		EClass THING_ML_ELEMENT = eINSTANCE.getThingMLElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute THING_ML_ELEMENT__NAME = eINSTANCE.getThingMLElement_Name();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.TypeImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.PropertyImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__TYPE = eINSTANCE.getProperty_Type();

		/**
		 * The meta object literal for the '<em><b>Changeable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__CHANGEABLE = eINSTANCE.getProperty_Changeable();

		/**
		 * The meta object literal for the '<em><b>Lower Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__LOWER_BOUND = eINSTANCE.getProperty_LowerBound();

		/**
		 * The meta object literal for the '<em><b>Upper Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__UPPER_BOUND = eINSTANCE.getProperty_UpperBound();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.PlatformAnnotationImpl <em>Platform Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.PlatformAnnotationImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPlatformAnnotation()
		 * @generated
		 */
		EClass PLATFORM_ANNOTATION = eINSTANCE.getPlatformAnnotation();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLATFORM_ANNOTATION__VALUE = eINSTANCE.getPlatformAnnotation_Value();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.EnumerationImpl <em>Enumeration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.EnumerationImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEnumeration()
		 * @generated
		 */
		EClass ENUMERATION = eINSTANCE.getEnumeration();

		/**
		 * The meta object literal for the '<em><b>Literals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATION__LITERALS = eINSTANCE.getEnumeration_Literals();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.PrimitiveTypeImpl <em>Primitive Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.PrimitiveTypeImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPrimitiveType()
		 * @generated
		 */
		EClass PRIMITIVE_TYPE = eINSTANCE.getPrimitiveType();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.EnumerationLiteralImpl <em>Enumeration Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.EnumerationLiteralImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEnumerationLiteral()
		 * @generated
		 */
		EClass ENUMERATION_LITERAL = eINSTANCE.getEnumerationLiteral();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.AnnotatedElementImpl <em>Annotated Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.AnnotatedElementImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getAnnotatedElement()
		 * @generated
		 */
		EClass ANNOTATED_ELEMENT = eINSTANCE.getAnnotatedElement();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATED_ELEMENT__ANNOTATIONS = eINSTANCE.getAnnotatedElement_Annotations();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.StateMachineImpl <em>State Machine</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.StateMachineImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStateMachine()
		 * @generated
		 */
		EClass STATE_MACHINE = eINSTANCE.getStateMachine();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.HandlerImpl <em>Handler</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.HandlerImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getHandler()
		 * @generated
		 */
		EClass HANDLER = eINSTANCE.getHandler();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HANDLER__EVENT = eINSTANCE.getHandler_Event();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HANDLER__GUARD = eINSTANCE.getHandler_Guard();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HANDLER__ACTION = eINSTANCE.getHandler_Action();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.TransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.TransitionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__TARGET = eINSTANCE.getTransition_Target();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__SOURCE = eINSTANCE.getTransition_Source();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.InternalTransitionImpl <em>Internal Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.InternalTransitionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getInternalTransition()
		 * @generated
		 */
		EClass INTERNAL_TRANSITION = eINSTANCE.getInternalTransition();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.StateImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Outgoing</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__OUTGOING = eINSTANCE.getState_Outgoing();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__INCOMING = eINSTANCE.getState_Incoming();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__ENTRY = eINSTANCE.getState_Entry();

		/**
		 * The meta object literal for the '<em><b>Exit</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__EXIT = eINSTANCE.getState_Exit();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__PROPERTIES = eINSTANCE.getState_Properties();

		/**
		 * The meta object literal for the '<em><b>Internal</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__INTERNAL = eINSTANCE.getState_Internal();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.CompositeStateImpl <em>Composite State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.CompositeStateImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getCompositeState()
		 * @generated
		 */
		EClass COMPOSITE_STATE = eINSTANCE.getCompositeState();

		/**
		 * The meta object literal for the '<em><b>Region</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITE_STATE__REGION = eINSTANCE.getCompositeState_Region();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.RegionImpl <em>Region</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.RegionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getRegion()
		 * @generated
		 */
		EClass REGION = eINSTANCE.getRegion();

		/**
		 * The meta object literal for the '<em><b>Substate</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGION__SUBSTATE = eINSTANCE.getRegion_Substate();

		/**
		 * The meta object literal for the '<em><b>Initial</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGION__INITIAL = eINSTANCE.getRegion_Initial();

		/**
		 * The meta object literal for the '<em><b>History</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGION__HISTORY = eINSTANCE.getRegion_History();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ParallelRegionImpl <em>Parallel Region</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ParallelRegionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getParallelRegion()
		 * @generated
		 */
		EClass PARALLEL_REGION = eINSTANCE.getParallelRegion();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ActionImpl <em>Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ActionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ActionBlockImpl <em>Action Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ActionBlockImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getActionBlock()
		 * @generated
		 */
		EClass ACTION_BLOCK = eINSTANCE.getActionBlock();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION_BLOCK__ACTIONS = eINSTANCE.getActionBlock_Actions();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ExternStatementImpl <em>Extern Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ExternStatementImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getExternStatement()
		 * @generated
		 */
		EClass EXTERN_STATEMENT = eINSTANCE.getExternStatement();

		/**
		 * The meta object literal for the '<em><b>Statement</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERN_STATEMENT__STATEMENT = eINSTANCE.getExternStatement_Statement();

		/**
		 * The meta object literal for the '<em><b>Segments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERN_STATEMENT__SEGMENTS = eINSTANCE.getExternStatement_Segments();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ExpressionImpl <em>Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ExternExpressionImpl <em>Extern Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ExternExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getExternExpression()
		 * @generated
		 */
		EClass EXTERN_EXPRESSION = eINSTANCE.getExternExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERN_EXPRESSION__EXPRESSION = eINSTANCE.getExternExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>Segments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERN_EXPRESSION__SEGMENTS = eINSTANCE.getExternExpression_Segments();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.SendActionImpl <em>Send Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.SendActionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSendAction()
		 * @generated
		 */
		EClass SEND_ACTION = eINSTANCE.getSendAction();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEND_ACTION__PARAMETERS = eINSTANCE.getSendAction_Parameters();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEND_ACTION__MESSAGE = eINSTANCE.getSendAction_Message();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEND_ACTION__PORT = eINSTANCE.getSendAction_Port();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.PropertyAssignmentImpl <em>Property Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.PropertyAssignmentImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPropertyAssignment()
		 * @generated
		 */
		EClass PROPERTY_ASSIGNMENT = eINSTANCE.getPropertyAssignment();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_ASSIGNMENT__PROPERTY = eINSTANCE.getPropertyAssignment_Property();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_ASSIGNMENT__EXPRESSION = eINSTANCE.getPropertyAssignment_Expression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.EventImpl <em>Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.EventImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEvent()
		 * @generated
		 */
		EClass EVENT = eINSTANCE.getEvent();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ReceiveMessageImpl <em>Receive Message</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ReceiveMessageImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getReceiveMessage()
		 * @generated
		 */
		EClass RECEIVE_MESSAGE = eINSTANCE.getReceiveMessage();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RECEIVE_MESSAGE__MESSAGE = eINSTANCE.getReceiveMessage_Message();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RECEIVE_MESSAGE__PORT = eINSTANCE.getReceiveMessage_Port();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.DictionaryImpl <em>Dictionary</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.DictionaryImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getDictionary()
		 * @generated
		 */
		EClass DICTIONARY = eINSTANCE.getDictionary();

		/**
		 * The meta object literal for the '<em><b>Index Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DICTIONARY__INDEX_TYPE = eINSTANCE.getDictionary_IndexType();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.PortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.PortImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPort()
		 * @generated
		 */
		EClass PORT = eINSTANCE.getPort();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__OWNER = eINSTANCE.getPort_Owner();

		/**
		 * The meta object literal for the '<em><b>Receives</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__RECEIVES = eINSTANCE.getPort_Receives();

		/**
		 * The meta object literal for the '<em><b>Sends</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__SENDS = eINSTANCE.getPort_Sends();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.EventReferenceImpl <em>Event Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.EventReferenceImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEventReference()
		 * @generated
		 */
		EClass EVENT_REFERENCE = eINSTANCE.getEventReference();

		/**
		 * The meta object literal for the '<em><b>Msg Ref</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_REFERENCE__MSG_REF = eINSTANCE.getEventReference_MsgRef();

		/**
		 * The meta object literal for the '<em><b>Param Ref</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_REFERENCE__PARAM_REF = eINSTANCE.getEventReference_ParamRef();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.LitteralImpl <em>Litteral</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.LitteralImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLitteral()
		 * @generated
		 */
		EClass LITTERAL = eINSTANCE.getLitteral();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.IntegerLitteralImpl <em>Integer Litteral</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.IntegerLitteralImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getIntegerLitteral()
		 * @generated
		 */
		EClass INTEGER_LITTERAL = eINSTANCE.getIntegerLitteral();

		/**
		 * The meta object literal for the '<em><b>Int Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_LITTERAL__INT_VALUE = eINSTANCE.getIntegerLitteral_IntValue();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.BooleanLitteralImpl <em>Boolean Litteral</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.BooleanLitteralImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getBooleanLitteral()
		 * @generated
		 */
		EClass BOOLEAN_LITTERAL = eINSTANCE.getBooleanLitteral();

		/**
		 * The meta object literal for the '<em><b>Bool Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_LITTERAL__BOOL_VALUE = eINSTANCE.getBooleanLitteral_BoolValue();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.StringLitteralImpl <em>String Litteral</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.StringLitteralImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStringLitteral()
		 * @generated
		 */
		EClass STRING_LITTERAL = eINSTANCE.getStringLitteral();

		/**
		 * The meta object literal for the '<em><b>String Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_LITTERAL__STRING_VALUE = eINSTANCE.getStringLitteral_StringValue();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.DoubleLitteralImpl <em>Double Litteral</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.DoubleLitteralImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getDoubleLitteral()
		 * @generated
		 */
		EClass DOUBLE_LITTERAL = eINSTANCE.getDoubleLitteral();

		/**
		 * The meta object literal for the '<em><b>Double Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOUBLE_LITTERAL__DOUBLE_VALUE = eINSTANCE.getDoubleLitteral_DoubleValue();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.UnaryExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getUnaryExpression()
		 * @generated
		 */
		EClass UNARY_EXPRESSION = eINSTANCE.getUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Term</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNARY_EXPRESSION__TERM = eINSTANCE.getUnaryExpression_Term();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.NotExpressionImpl <em>Not Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.NotExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getNotExpression()
		 * @generated
		 */
		EClass NOT_EXPRESSION = eINSTANCE.getNotExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.UnaryMinusImpl <em>Unary Minus</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.UnaryMinusImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getUnaryMinus()
		 * @generated
		 */
		EClass UNARY_MINUS = eINSTANCE.getUnaryMinus();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.BinaryExpressionImpl <em>Binary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.BinaryExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getBinaryExpression()
		 * @generated
		 */
		EClass BINARY_EXPRESSION = eINSTANCE.getBinaryExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_EXPRESSION__LHS = eINSTANCE.getBinaryExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_EXPRESSION__RHS = eINSTANCE.getBinaryExpression_Rhs();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.PlusExpressionImpl <em>Plus Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.PlusExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPlusExpression()
		 * @generated
		 */
		EClass PLUS_EXPRESSION = eINSTANCE.getPlusExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.MinusExpressionImpl <em>Minus Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.MinusExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getMinusExpression()
		 * @generated
		 */
		EClass MINUS_EXPRESSION = eINSTANCE.getMinusExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.TimesExpressionImpl <em>Times Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.TimesExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getTimesExpression()
		 * @generated
		 */
		EClass TIMES_EXPRESSION = eINSTANCE.getTimesExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.DivExpressionImpl <em>Div Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.DivExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getDivExpression()
		 * @generated
		 */
		EClass DIV_EXPRESSION = eINSTANCE.getDivExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ModExpressionImpl <em>Mod Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ModExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getModExpression()
		 * @generated
		 */
		EClass MOD_EXPRESSION = eINSTANCE.getModExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.EqualsExpressionImpl <em>Equals Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.EqualsExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEqualsExpression()
		 * @generated
		 */
		EClass EQUALS_EXPRESSION = eINSTANCE.getEqualsExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.GreaterExpressionImpl <em>Greater Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.GreaterExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getGreaterExpression()
		 * @generated
		 */
		EClass GREATER_EXPRESSION = eINSTANCE.getGreaterExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.LowerExpressionImpl <em>Lower Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.LowerExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLowerExpression()
		 * @generated
		 */
		EClass LOWER_EXPRESSION = eINSTANCE.getLowerExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.AndExpressionImpl <em>And Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.AndExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getAndExpression()
		 * @generated
		 */
		EClass AND_EXPRESSION = eINSTANCE.getAndExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.OrExpressionImpl <em>Or Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.OrExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getOrExpression()
		 * @generated
		 */
		EClass OR_EXPRESSION = eINSTANCE.getOrExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ControlStructureImpl <em>Control Structure</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ControlStructureImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getControlStructure()
		 * @generated
		 */
		EClass CONTROL_STRUCTURE = eINSTANCE.getControlStructure();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTROL_STRUCTURE__ACTION = eINSTANCE.getControlStructure_Action();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTROL_STRUCTURE__CONDITION = eINSTANCE.getControlStructure_Condition();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.LoopActionImpl <em>Loop Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.LoopActionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLoopAction()
		 * @generated
		 */
		EClass LOOP_ACTION = eINSTANCE.getLoopAction();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ConditionalActionImpl <em>Conditional Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ConditionalActionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getConditionalAction()
		 * @generated
		 */
		EClass CONDITIONAL_ACTION = eINSTANCE.getConditionalAction();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.PropertyReferenceImpl <em>Property Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.PropertyReferenceImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPropertyReference()
		 * @generated
		 */
		EClass PROPERTY_REFERENCE = eINSTANCE.getPropertyReference();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_REFERENCE__PROPERTY = eINSTANCE.getPropertyReference_Property();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.DictionaryReferenceImpl <em>Dictionary Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.DictionaryReferenceImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getDictionaryReference()
		 * @generated
		 */
		EClass DICTIONARY_REFERENCE = eINSTANCE.getDictionaryReference();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DICTIONARY_REFERENCE__INDEX = eINSTANCE.getDictionaryReference_Index();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ExpressionGroupImpl <em>Expression Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ExpressionGroupImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getExpressionGroup()
		 * @generated
		 */
		EClass EXPRESSION_GROUP = eINSTANCE.getExpressionGroup();

		/**
		 * The meta object literal for the '<em><b>Exp</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPRESSION_GROUP__EXP = eINSTANCE.getExpressionGroup_Exp();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.PrintActionImpl <em>Print Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.PrintActionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPrintAction()
		 * @generated
		 */
		EClass PRINT_ACTION = eINSTANCE.getPrintAction();

		/**
		 * The meta object literal for the '<em><b>Msg</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRINT_ACTION__MSG = eINSTANCE.getPrintAction_Msg();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ErrorActionImpl <em>Error Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ErrorActionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getErrorAction()
		 * @generated
		 */
		EClass ERROR_ACTION = eINSTANCE.getErrorAction();

		/**
		 * The meta object literal for the '<em><b>Msg</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ERROR_ACTION__MSG = eINSTANCE.getErrorAction_Msg();

	}

} //ThingmlPackage
