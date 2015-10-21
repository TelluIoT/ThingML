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
	 * The feature id for the '<em><b>Configs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING_ML_MODEL__CONFIGS = 2;

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
	int THING_ML_ELEMENT = 6;

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
	int ANNOTATED_ELEMENT = 15;

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
	 * The meta object id for the '{@link org.sintef.thingml.impl.FunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.FunctionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getFunction()
	 * @generated
	 */
	int FUNCTION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__TYPE = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__CARDINALITY = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Is Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__IS_ARRAY = ANNOTATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__PARAMETERS = ANNOTATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__BODY = ANNOTATED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.MessageImpl <em>Message</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.MessageImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getMessage()
	 * @generated
	 */
	int MESSAGE = 2;

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
	int TYPE = 7;

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
	int THING = 3;

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
	 * The feature id for the '<em><b>Assign</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__ASSIGN = TYPE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Messages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__MESSAGES = TYPE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__FUNCTIONS = TYPE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Streams</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__STREAMS = TYPE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Operators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING__OPERATORS = TYPE_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Thing</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THING_FEATURE_COUNT = TYPE_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.TypedElementImpl <em>Typed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.TypedElementImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getTypedElement()
	 * @generated
	 */
	int TYPED_ELEMENT = 8;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__CARDINALITY = 1;

	/**
	 * The feature id for the '<em><b>Is Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__IS_ARRAY = 2;

	/**
	 * The number of structural features of the '<em>Typed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.VariableImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__TYPE = TYPED_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__CARDINALITY = TYPED_ELEMENT__CARDINALITY;

	/**
	 * The feature id for the '<em><b>Is Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__IS_ARRAY = TYPED_ELEMENT__IS_ARRAY;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = TYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__ANNOTATIONS = TYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ParameterImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__TYPE = VARIABLE__TYPE;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__CARDINALITY = VARIABLE__CARDINALITY;

	/**
	 * The feature id for the '<em><b>Is Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__IS_ARRAY = VARIABLE__IS_ARRAY;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__ANNOTATIONS = VARIABLE__ANNOTATIONS;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PropertyImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 9;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__TYPE = VARIABLE__TYPE;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__CARDINALITY = VARIABLE__CARDINALITY;

	/**
	 * The feature id for the '<em><b>Is Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__IS_ARRAY = VARIABLE__IS_ARRAY;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__ANNOTATIONS = VARIABLE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__INIT = VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Changeable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__CHANGEABLE = VARIABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PropertyAssignImpl <em>Property Assign</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PropertyAssignImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPropertyAssign()
	 * @generated
	 */
	int PROPERTY_ASSIGN = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGN__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGN__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGN__INIT = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGN__PROPERTY = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGN__INDEX = ANNOTATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Property Assign</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGN_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PlatformAnnotationImpl <em>Platform Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PlatformAnnotationImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPlatformAnnotation()
	 * @generated
	 */
	int PLATFORM_ANNOTATION = 11;

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
	int ENUMERATION = 12;

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
	int PRIMITIVE_TYPE = 13;

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
	int ENUMERATION_LITERAL = 14;

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
	 * The feature id for the '<em><b>Enum</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL__ENUM = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enumeration Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.StateImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getState()
	 * @generated
	 */
	int STATE = 20;

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
	int COMPOSITE_STATE = 21;

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
	int STATE_MACHINE = 16;

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
	int HANDLER = 17;

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
	int TRANSITION = 18;

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
	 * The feature id for the '<em><b>After</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__AFTER = HANDLER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Before</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__BEFORE = HANDLER_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = HANDLER_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.InternalTransitionImpl <em>Internal Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.InternalTransitionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getInternalTransition()
	 * @generated
	 */
	int INTERNAL_TRANSITION = 19;

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
	int REGION = 22;

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
	int PARALLEL_REGION = 23;

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
	int ACTION = 24;

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
	int ACTION_BLOCK = 25;

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
	int EXTERN_STATEMENT = 26;

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
	int EXPRESSION = 27;

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
	int EXTERN_EXPRESSION = 28;

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
	int SEND_ACTION = 29;

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
	 * The meta object id for the '{@link org.sintef.thingml.impl.VariableAssignmentImpl <em>Variable Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.VariableAssignmentImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getVariableAssignment()
	 * @generated
	 */
	int VARIABLE_ASSIGNMENT = 30;

	/**
	 * The feature id for the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ASSIGNMENT__PROPERTY = ACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ASSIGNMENT__EXPRESSION = ACTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ASSIGNMENT__INDEX = ACTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Variable Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ASSIGNMENT_FEATURE_COUNT = ACTION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.EventImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEvent()
	 * @generated
	 */
	int EVENT = 31;

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
	int RECEIVE_MESSAGE = 32;

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
	 * The meta object id for the '{@link org.sintef.thingml.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PortImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPort()
	 * @generated
	 */
	int PORT = 33;

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
	 * The meta object id for the '{@link org.sintef.thingml.impl.RequiredPortImpl <em>Required Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.RequiredPortImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getRequiredPort()
	 * @generated
	 */
	int REQUIRED_PORT = 34;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_PORT__NAME = PORT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_PORT__ANNOTATIONS = PORT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_PORT__OWNER = PORT__OWNER;

	/**
	 * The feature id for the '<em><b>Receives</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_PORT__RECEIVES = PORT__RECEIVES;

	/**
	 * The feature id for the '<em><b>Sends</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_PORT__SENDS = PORT__SENDS;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_PORT__OPTIONAL = PORT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Required Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_PORT_FEATURE_COUNT = PORT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ProvidedPortImpl <em>Provided Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ProvidedPortImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getProvidedPort()
	 * @generated
	 */
	int PROVIDED_PORT = 35;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_PORT__NAME = PORT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_PORT__ANNOTATIONS = PORT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_PORT__OWNER = PORT__OWNER;

	/**
	 * The feature id for the '<em><b>Receives</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_PORT__RECEIVES = PORT__RECEIVES;

	/**
	 * The feature id for the '<em><b>Sends</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_PORT__SENDS = PORT__SENDS;

	/**
	 * The number of structural features of the '<em>Provided Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_PORT_FEATURE_COUNT = PORT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.LiteralImpl <em>Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.LiteralImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLiteral()
	 * @generated
	 */
	int LITERAL = 36;

	/**
	 * The number of structural features of the '<em>Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.EnumLiteralRefImpl <em>Enum Literal Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.EnumLiteralRefImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEnumLiteralRef()
	 * @generated
	 */
	int ENUM_LITERAL_REF = 37;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_LITERAL_REF__ENUM = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_LITERAL_REF__LITERAL = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Enum Literal Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_LITERAL_REF_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.IntegerLiteralImpl <em>Integer Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.IntegerLiteralImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getIntegerLiteral()
	 * @generated
	 */
	int INTEGER_LITERAL = 38;

	/**
	 * The feature id for the '<em><b>Int Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL__INT_VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.BooleanLiteralImpl <em>Boolean Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.BooleanLiteralImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getBooleanLiteral()
	 * @generated
	 */
	int BOOLEAN_LITERAL = 39;

	/**
	 * The feature id for the '<em><b>Bool Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITERAL__BOOL_VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.StringLiteralImpl <em>String Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.StringLiteralImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStringLiteral()
	 * @generated
	 */
	int STRING_LITERAL = 40;

	/**
	 * The feature id for the '<em><b>String Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL__STRING_VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.DoubleLiteralImpl <em>Double Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.DoubleLiteralImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getDoubleLiteral()
	 * @generated
	 */
	int DOUBLE_LITERAL = 41;

	/**
	 * The feature id for the '<em><b>Double Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL__DOUBLE_VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Double Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.UnaryExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getUnaryExpression()
	 * @generated
	 */
	int UNARY_EXPRESSION = 42;

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
	int NOT_EXPRESSION = 43;

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
	int UNARY_MINUS = 44;

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
	int BINARY_EXPRESSION = 45;

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
	int PLUS_EXPRESSION = 46;

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
	int MINUS_EXPRESSION = 47;

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
	int TIMES_EXPRESSION = 48;

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
	int DIV_EXPRESSION = 49;

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
	int MOD_EXPRESSION = 50;

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
	int EQUALS_EXPRESSION = 51;

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
	int GREATER_EXPRESSION = 52;

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
	int LOWER_EXPRESSION = 53;

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
	int AND_EXPRESSION = 54;

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
	int OR_EXPRESSION = 55;

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
	int CONTROL_STRUCTURE = 56;

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
	int LOOP_ACTION = 57;

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
	int CONDITIONAL_ACTION = 58;

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
	 * The feature id for the '<em><b>Else Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_ACTION__ELSE_ACTION = CONTROL_STRUCTURE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Conditional Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_ACTION_FEATURE_COUNT = CONTROL_STRUCTURE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PropertyReferenceImpl <em>Property Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PropertyReferenceImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPropertyReference()
	 * @generated
	 */
	int PROPERTY_REFERENCE = 59;

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
	 * The meta object id for the '{@link org.sintef.thingml.impl.ArrayIndexImpl <em>Array Index</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ArrayIndexImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getArrayIndex()
	 * @generated
	 */
	int ARRAY_INDEX = 60;

	/**
	 * The feature id for the '<em><b>Array</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_INDEX__ARRAY = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_INDEX__INDEX = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Array Index</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_INDEX_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ExpressionGroupImpl <em>Expression Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ExpressionGroupImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getExpressionGroup()
	 * @generated
	 */
	int EXPRESSION_GROUP = 61;

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
	 * The meta object id for the '{@link org.sintef.thingml.impl.ReturnActionImpl <em>Return Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ReturnActionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getReturnAction()
	 * @generated
	 */
	int RETURN_ACTION = 62;

	/**
	 * The feature id for the '<em><b>Exp</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_ACTION__EXP = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Return Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_ACTION_FEATURE_COUNT = ACTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PrintActionImpl <em>Print Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PrintActionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPrintAction()
	 * @generated
	 */
	int PRINT_ACTION = 63;

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
	int ERROR_ACTION = 64;

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
	 * The meta object id for the '{@link org.sintef.thingml.impl.ConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ConfigurationImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getConfiguration()
	 * @generated
	 */
	int CONFIGURATION = 65;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__INSTANCES = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Connectors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__CONNECTORS = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Fragment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__FRAGMENT = ANNOTATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Configs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__CONFIGS = ANNOTATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Propassigns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__PROPASSIGNS = ANNOTATED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.InstanceImpl <em>Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.InstanceImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getInstance()
	 * @generated
	 */
	int INSTANCE = 66;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__TYPE = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Assign</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__ASSIGN = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.AbstractConnectorImpl <em>Abstract Connector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.AbstractConnectorImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getAbstractConnector()
	 * @generated
	 */
	int ABSTRACT_CONNECTOR = 69;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONNECTOR__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONNECTOR__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The number of structural features of the '<em>Abstract Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONNECTOR_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ConnectorImpl <em>Connector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ConnectorImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getConnector()
	 * @generated
	 */
	int CONNECTOR = 67;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__NAME = ABSTRACT_CONNECTOR__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__ANNOTATIONS = ABSTRACT_CONNECTOR__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Srv</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__SRV = ABSTRACT_CONNECTOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cli</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__CLI = ABSTRACT_CONNECTOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Required</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__REQUIRED = ABSTRACT_CONNECTOR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Provided</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__PROVIDED = ABSTRACT_CONNECTOR_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_FEATURE_COUNT = ABSTRACT_CONNECTOR_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ExternalConnectorImpl <em>External Connector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ExternalConnectorImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getExternalConnector()
	 * @generated
	 */
	int EXTERNAL_CONNECTOR = 68;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_CONNECTOR__NAME = ABSTRACT_CONNECTOR__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_CONNECTOR__ANNOTATIONS = ABSTRACT_CONNECTOR__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Inst</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_CONNECTOR__INST = ABSTRACT_CONNECTOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_CONNECTOR__PORT = ABSTRACT_CONNECTOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Protocol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_CONNECTOR__PROTOCOL = ABSTRACT_CONNECTOR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>External Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_CONNECTOR_FEATURE_COUNT = ABSTRACT_CONNECTOR_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ConfigPropertyAssignImpl <em>Config Property Assign</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ConfigPropertyAssignImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getConfigPropertyAssign()
	 * @generated
	 */
	int CONFIG_PROPERTY_ASSIGN = 70;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_ASSIGN__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_ASSIGN__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_ASSIGN__INIT = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_ASSIGN__PROPERTY = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_ASSIGN__INSTANCE = ANNOTATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_ASSIGN__INDEX = ANNOTATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Config Property Assign</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_ASSIGN_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ConfigIncludeImpl <em>Config Include</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ConfigIncludeImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getConfigInclude()
	 * @generated
	 */
	int CONFIG_INCLUDE = 71;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_INCLUDE__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_INCLUDE__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_INCLUDE__CONFIG = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Config Include</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_INCLUDE_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.InstanceRefImpl <em>Instance Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.InstanceRefImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getInstanceRef()
	 * @generated
	 */
	int INSTANCE_REF = 72;

	/**
	 * The feature id for the '<em><b>Config</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_REF__CONFIG = 0;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_REF__INSTANCE = 1;

	/**
	 * The number of structural features of the '<em>Instance Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_REF_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.FunctionCallImpl <em>Function Call</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.FunctionCallImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getFunctionCall()
	 * @generated
	 */
	int FUNCTION_CALL = 73;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__PARAMETERS = 0;

	/**
	 * The feature id for the '<em><b>Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__FUNCTION = 1;

	/**
	 * The number of structural features of the '<em>Function Call</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.FunctionCallStatementImpl <em>Function Call Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.FunctionCallStatementImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getFunctionCallStatement()
	 * @generated
	 */
	int FUNCTION_CALL_STATEMENT = 74;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL_STATEMENT__PARAMETERS = ACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL_STATEMENT__FUNCTION = ACTION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Function Call Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL_STATEMENT_FEATURE_COUNT = ACTION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.FunctionCallExpressionImpl <em>Function Call Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.FunctionCallExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getFunctionCallExpression()
	 * @generated
	 */
	int FUNCTION_CALL_EXPRESSION = 75;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL_EXPRESSION__PARAMETERS = FUNCTION_CALL__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL_EXPRESSION__FUNCTION = FUNCTION_CALL__FUNCTION;

	/**
	 * The number of structural features of the '<em>Function Call Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL_EXPRESSION_FEATURE_COUNT = FUNCTION_CALL_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.LocalVariableImpl <em>Local Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.LocalVariableImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLocalVariable()
	 * @generated
	 */
	int LOCAL_VARIABLE = 76;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__TYPE = VARIABLE__TYPE;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__CARDINALITY = VARIABLE__CARDINALITY;

	/**
	 * The feature id for the '<em><b>Is Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__IS_ARRAY = VARIABLE__IS_ARRAY;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__NAME = VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__ANNOTATIONS = VARIABLE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__INIT = VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Changeable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__CHANGEABLE = VARIABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Local Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 2;


	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.StreamImpl <em>Stream</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.StreamImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStream()
	 * @generated
	 */
	int STREAM = 77;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM__NAME = ANNOTATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM__ANNOTATIONS = ANNOTATED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Selection</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM__SELECTION = ANNOTATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Output</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM__OUTPUT = ANNOTATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Input</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM__INPUT = ANNOTATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Stream</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_FEATURE_COUNT = ANNOTATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.StreamExpressionImpl <em>Stream Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.StreamExpressionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStreamExpression()
	 * @generated
	 */
	int STREAM_EXPRESSION = 78;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION__NAME = THING_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION__EXPRESSION = THING_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Stream Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION_FEATURE_COUNT = THING_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.StreamParamReferenceImpl <em>Stream Param Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.StreamParamReferenceImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStreamParamReference()
	 * @generated
	 */
	int STREAM_PARAM_REFERENCE = 79;

	/**
	 * The feature id for the '<em><b>Index Param</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_PARAM_REFERENCE__INDEX_PARAM = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Stream Param Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_PARAM_REFERENCE_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.StreamOutputImpl <em>Stream Output</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.StreamOutputImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStreamOutput()
	 * @generated
	 */
	int STREAM_OUTPUT = 80;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_OUTPUT__PARAMETERS = 0;

	/**
	 * The feature id for the '<em><b>Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_OUTPUT__MESSAGE = 1;

	/**
	 * The feature id for the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_OUTPUT__PORT = 2;

	/**
	 * The number of structural features of the '<em>Stream Output</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_OUTPUT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.SourceImpl <em>Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.SourceImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSource()
	 * @generated
	 */
	int SOURCE = 81;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__NAME = THING_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Operators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__OPERATORS = THING_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FEATURE_COUNT = THING_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.SourceCompositionImpl <em>Source Composition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.SourceCompositionImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSourceComposition()
	 * @generated
	 */
	int SOURCE_COMPOSITION = 82;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_COMPOSITION__NAME = SOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Operators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_COMPOSITION__OPERATORS = SOURCE__OPERATORS;

	/**
	 * The feature id for the '<em><b>Sources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_COMPOSITION__SOURCES = SOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Result Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_COMPOSITION__RESULT_MESSAGE = SOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_COMPOSITION__RULES = SOURCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Source Composition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_COMPOSITION_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.JoinSourcesImpl <em>Join Sources</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.JoinSourcesImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getJoinSources()
	 * @generated
	 */
	int JOIN_SOURCES = 83;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOIN_SOURCES__NAME = SOURCE_COMPOSITION__NAME;

	/**
	 * The feature id for the '<em><b>Operators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOIN_SOURCES__OPERATORS = SOURCE_COMPOSITION__OPERATORS;

	/**
	 * The feature id for the '<em><b>Sources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOIN_SOURCES__SOURCES = SOURCE_COMPOSITION__SOURCES;

	/**
	 * The feature id for the '<em><b>Result Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOIN_SOURCES__RESULT_MESSAGE = SOURCE_COMPOSITION__RESULT_MESSAGE;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOIN_SOURCES__RULES = SOURCE_COMPOSITION__RULES;

	/**
	 * The number of structural features of the '<em>Join Sources</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOIN_SOURCES_FEATURE_COUNT = SOURCE_COMPOSITION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.MergeSourcesImpl <em>Merge Sources</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.MergeSourcesImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getMergeSources()
	 * @generated
	 */
	int MERGE_SOURCES = 84;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_SOURCES__NAME = SOURCE_COMPOSITION__NAME;

	/**
	 * The feature id for the '<em><b>Operators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_SOURCES__OPERATORS = SOURCE_COMPOSITION__OPERATORS;

	/**
	 * The feature id for the '<em><b>Sources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_SOURCES__SOURCES = SOURCE_COMPOSITION__SOURCES;

	/**
	 * The feature id for the '<em><b>Result Message</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_SOURCES__RESULT_MESSAGE = SOURCE_COMPOSITION__RESULT_MESSAGE;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_SOURCES__RULES = SOURCE_COMPOSITION__RULES;

	/**
	 * The number of structural features of the '<em>Merge Sources</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_SOURCES_FEATURE_COUNT = SOURCE_COMPOSITION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.SimpleSourceImpl <em>Simple Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.SimpleSourceImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSimpleSource()
	 * @generated
	 */
	int SIMPLE_SOURCE = 85;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_SOURCE__NAME = SOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Operators</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_SOURCE__OPERATORS = SOURCE__OPERATORS;

	/**
	 * The feature id for the '<em><b>Message</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_SOURCE__MESSAGE = SOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Simple Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_SOURCE_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ViewSourceImpl <em>View Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ViewSourceImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getViewSource()
	 * @generated
	 */
	int VIEW_SOURCE = 86;

	/**
	 * The number of structural features of the '<em>View Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_SOURCE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.FilterImpl <em>Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.FilterImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getFilter()
	 * @generated
	 */
	int FILTER = 87;

	/**
	 * The feature id for the '<em><b>Filter Op</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER__FILTER_OP = VIEW_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FEATURE_COUNT = VIEW_SOURCE_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.OperatorImpl <em>Operator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.OperatorImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getOperator()
	 * @generated
	 */
	int OPERATOR = 88;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATOR__TYPE = TYPED_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATOR__CARDINALITY = TYPED_ELEMENT__CARDINALITY;

	/**
	 * The feature id for the '<em><b>Is Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATOR__IS_ARRAY = TYPED_ELEMENT__IS_ARRAY;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATOR__NAME = TYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATOR__BODY = TYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Operator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATOR_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.MessageParameterImpl <em>Message Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.MessageParameterImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getMessageParameter()
	 * @generated
	 */
	int MESSAGE_PARAMETER = 89;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_PARAMETER__NAME = THING_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Msg Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_PARAMETER__MSG_REF = THING_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Message Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_PARAMETER_FEATURE_COUNT = THING_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.SglMsgParamOperatorCallImpl <em>Sgl Msg Param Operator Call</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.SglMsgParamOperatorCallImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSglMsgParamOperatorCall()
	 * @generated
	 */
	int SGL_MSG_PARAM_OPERATOR_CALL = 90;

	/**
	 * The feature id for the '<em><b>Operator Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SGL_MSG_PARAM_OPERATOR_CALL__OPERATOR_REF = 0;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SGL_MSG_PARAM_OPERATOR_CALL__PARAMETER = 1;

	/**
	 * The number of structural features of the '<em>Sgl Msg Param Operator Call</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SGL_MSG_PARAM_OPERATOR_CALL_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ReferenceImpl <em>Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ReferenceImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getReference()
	 * @generated
	 */
	int REFERENCE = 91;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE__REFERENCE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE__PARAMETER = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ReferencedElmtImpl <em>Referenced Elmt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ReferencedElmtImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getReferencedElmt()
	 * @generated
	 */
	int REFERENCED_ELMT = 92;

	/**
	 * The number of structural features of the '<em>Referenced Elmt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCED_ELMT_FEATURE_COUNT = 0;


	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.SglMsgParamOperatorImpl <em>Sgl Msg Param Operator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.SglMsgParamOperatorImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSglMsgParamOperator()
	 * @generated
	 */
	int SGL_MSG_PARAM_OPERATOR = 93;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SGL_MSG_PARAM_OPERATOR__TYPE = OPERATOR__TYPE;

	/**
	 * The feature id for the '<em><b>Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SGL_MSG_PARAM_OPERATOR__CARDINALITY = OPERATOR__CARDINALITY;

	/**
	 * The feature id for the '<em><b>Is Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SGL_MSG_PARAM_OPERATOR__IS_ARRAY = OPERATOR__IS_ARRAY;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SGL_MSG_PARAM_OPERATOR__NAME = OPERATOR__NAME;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SGL_MSG_PARAM_OPERATOR__BODY = OPERATOR__BODY;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SGL_MSG_PARAM_OPERATOR__PARAMETER = OPERATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sgl Msg Param Operator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SGL_MSG_PARAM_OPERATOR_FEATURE_COUNT = OPERATOR_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.WindowViewImpl <em>Window View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.WindowViewImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getWindowView()
	 * @generated
	 */
	int WINDOW_VIEW = 102;

	/**
	 * The number of structural features of the '<em>Window View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW_VIEW_FEATURE_COUNT = VIEW_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.LengthWindowImpl <em>Length Window</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.LengthWindowImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLengthWindow()
	 * @generated
	 */
	int LENGTH_WINDOW = 94;

	/**
	 * The feature id for the '<em><b>Nb Events</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LENGTH_WINDOW__NB_EVENTS = WINDOW_VIEW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Step</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LENGTH_WINDOW__STEP = WINDOW_VIEW_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Length Window</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LENGTH_WINDOW_FEATURE_COUNT = WINDOW_VIEW_FEATURE_COUNT + 2;


	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.TimeWindowImpl <em>Time Window</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.TimeWindowImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getTimeWindow()
	 * @generated
	 */
	int TIME_WINDOW = 95;

	/**
	 * The feature id for the '<em><b>Step</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_WINDOW__STEP = WINDOW_VIEW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_WINDOW__SIZE = WINDOW_VIEW_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Time Window</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_WINDOW_FEATURE_COUNT = WINDOW_VIEW_FEATURE_COUNT + 2;


	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ElmtPropertyImpl <em>Elmt Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ElmtPropertyImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getElmtProperty()
	 * @generated
	 */
	int ELMT_PROPERTY = 99;

	/**
	 * The number of structural features of the '<em>Elmt Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELMT_PROPERTY_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ParamReferenceImpl <em>Param Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ParamReferenceImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getParamReference()
	 * @generated
	 */
	int PARAM_REFERENCE = 96;

	/**
	 * The feature id for the '<em><b>Parameter Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM_REFERENCE__PARAMETER_REF = ELMT_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Param Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM_REFERENCE_FEATURE_COUNT = ELMT_PROPERTY_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.SimpleParamRefImpl <em>Simple Param Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.SimpleParamRefImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSimpleParamRef()
	 * @generated
	 */
	int SIMPLE_PARAM_REF = 97;

	/**
	 * The feature id for the '<em><b>Parameter Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PARAM_REF__PARAMETER_REF = PARAM_REFERENCE__PARAMETER_REF;

	/**
	 * The number of structural features of the '<em>Simple Param Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PARAM_REF_FEATURE_COUNT = PARAM_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.ArrayParamRefImpl <em>Array Param Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.ArrayParamRefImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getArrayParamRef()
	 * @generated
	 */
	int ARRAY_PARAM_REF = 98;

	/**
	 * The feature id for the '<em><b>Parameter Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PARAM_REF__PARAMETER_REF = PARAM_REFERENCE__PARAMETER_REF;

	/**
	 * The number of structural features of the '<em>Array Param Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PARAM_REF_FEATURE_COUNT = PARAM_REFERENCE_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.PredifinedPropertyImpl <em>Predifined Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.PredifinedPropertyImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPredifinedProperty()
	 * @generated
	 */
	int PREDIFINED_PROPERTY = 100;

	/**
	 * The number of structural features of the '<em>Predifined Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREDIFINED_PROPERTY_FEATURE_COUNT = ELMT_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sintef.thingml.impl.LengthArrayImpl <em>Length Array</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sintef.thingml.impl.LengthArrayImpl
	 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLengthArray()
	 * @generated
	 */
	int LENGTH_ARRAY = 101;

	/**
	 * The number of structural features of the '<em>Length Array</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LENGTH_ARRAY_FEATURE_COUNT = PREDIFINED_PROPERTY_FEATURE_COUNT + 0;


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
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.ThingMLModel#getConfigs <em>Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Configs</em>'.
	 * @see org.sintef.thingml.ThingMLModel#getConfigs()
	 * @see #getThingMLModel()
	 * @generated
	 */
	EReference getThingMLModel_Configs();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function</em>'.
	 * @see org.sintef.thingml.Function
	 * @generated
	 */
	EClass getFunction();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Function#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.sintef.thingml.Function#getParameters()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Parameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Function#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.sintef.thingml.Function#getBody()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Body();

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
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Thing#getAssign <em>Assign</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assign</em>'.
	 * @see org.sintef.thingml.Thing#getAssign()
	 * @see #getThing()
	 * @generated
	 */
	EReference getThing_Assign();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Thing#getMessages <em>Messages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Messages</em>'.
	 * @see org.sintef.thingml.Thing#getMessages()
	 * @see #getThing()
	 * @generated
	 */
	EReference getThing_Messages();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Thing#getFunctions <em>Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Functions</em>'.
	 * @see org.sintef.thingml.Thing#getFunctions()
	 * @see #getThing()
	 * @generated
	 */
	EReference getThing_Functions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Thing#getStreams <em>Streams</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Streams</em>'.
	 * @see org.sintef.thingml.Thing#getStreams()
	 * @see #getThing()
	 * @generated
	 */
	EReference getThing_Streams();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Thing#getOperators <em>Operators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operators</em>'.
	 * @see org.sintef.thingml.Thing#getOperators()
	 * @see #getThing()
	 * @generated
	 */
	EReference getThing_Operators();

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
	 * Returns the meta object for class '{@link org.sintef.thingml.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see org.sintef.thingml.Variable
	 * @generated
	 */
	EClass getVariable();

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
	 * Returns the meta object for class '{@link org.sintef.thingml.TypedElement <em>Typed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Element</em>'.
	 * @see org.sintef.thingml.TypedElement
	 * @generated
	 */
	EClass getTypedElement();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.TypedElement#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sintef.thingml.TypedElement#getType()
	 * @see #getTypedElement()
	 * @generated
	 */
	EReference getTypedElement_Type();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.TypedElement#getCardinality <em>Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cardinality</em>'.
	 * @see org.sintef.thingml.TypedElement#getCardinality()
	 * @see #getTypedElement()
	 * @generated
	 */
	EReference getTypedElement_Cardinality();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.TypedElement#isIsArray <em>Is Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Array</em>'.
	 * @see org.sintef.thingml.TypedElement#isIsArray()
	 * @see #getTypedElement()
	 * @generated
	 */
	EAttribute getTypedElement_IsArray();

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
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Property#getInit <em>Init</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Init</em>'.
	 * @see org.sintef.thingml.Property#getInit()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_Init();

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
	 * Returns the meta object for class '{@link org.sintef.thingml.PropertyAssign <em>Property Assign</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Assign</em>'.
	 * @see org.sintef.thingml.PropertyAssign
	 * @generated
	 */
	EClass getPropertyAssign();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.PropertyAssign#getInit <em>Init</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Init</em>'.
	 * @see org.sintef.thingml.PropertyAssign#getInit()
	 * @see #getPropertyAssign()
	 * @generated
	 */
	EReference getPropertyAssign_Init();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.PropertyAssign#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property</em>'.
	 * @see org.sintef.thingml.PropertyAssign#getProperty()
	 * @see #getPropertyAssign()
	 * @generated
	 */
	EReference getPropertyAssign_Property();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.PropertyAssign#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Index</em>'.
	 * @see org.sintef.thingml.PropertyAssign#getIndex()
	 * @see #getPropertyAssign()
	 * @generated
	 */
	EReference getPropertyAssign_Index();

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
	 * Returns the meta object for the container reference '{@link org.sintef.thingml.EnumerationLiteral#getEnum <em>Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Enum</em>'.
	 * @see org.sintef.thingml.EnumerationLiteral#getEnum()
	 * @see #getEnumerationLiteral()
	 * @generated
	 */
	EReference getEnumerationLiteral_Enum();

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
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Transition#getAfter <em>After</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>After</em>'.
	 * @see org.sintef.thingml.Transition#getAfter()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_After();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Transition#getBefore <em>Before</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Before</em>'.
	 * @see org.sintef.thingml.Transition#getBefore()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Before();

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
	 * Returns the meta object for class '{@link org.sintef.thingml.VariableAssignment <em>Variable Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Assignment</em>'.
	 * @see org.sintef.thingml.VariableAssignment
	 * @generated
	 */
	EClass getVariableAssignment();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.VariableAssignment#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property</em>'.
	 * @see org.sintef.thingml.VariableAssignment#getProperty()
	 * @see #getVariableAssignment()
	 * @generated
	 */
	EReference getVariableAssignment_Property();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.VariableAssignment#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.sintef.thingml.VariableAssignment#getExpression()
	 * @see #getVariableAssignment()
	 * @generated
	 */
	EReference getVariableAssignment_Expression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.VariableAssignment#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Index</em>'.
	 * @see org.sintef.thingml.VariableAssignment#getIndex()
	 * @see #getVariableAssignment()
	 * @generated
	 */
	EReference getVariableAssignment_Index();

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
	 * Returns the meta object for class '{@link org.sintef.thingml.RequiredPort <em>Required Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Required Port</em>'.
	 * @see org.sintef.thingml.RequiredPort
	 * @generated
	 */
	EClass getRequiredPort();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.RequiredPort#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.sintef.thingml.RequiredPort#isOptional()
	 * @see #getRequiredPort()
	 * @generated
	 */
	EAttribute getRequiredPort_Optional();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ProvidedPort <em>Provided Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provided Port</em>'.
	 * @see org.sintef.thingml.ProvidedPort
	 * @generated
	 */
	EClass getProvidedPort();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Literal <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Literal</em>'.
	 * @see org.sintef.thingml.Literal
	 * @generated
	 */
	EClass getLiteral();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.EnumLiteralRef <em>Enum Literal Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Literal Ref</em>'.
	 * @see org.sintef.thingml.EnumLiteralRef
	 * @generated
	 */
	EClass getEnumLiteralRef();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.EnumLiteralRef#getEnum <em>Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Enum</em>'.
	 * @see org.sintef.thingml.EnumLiteralRef#getEnum()
	 * @see #getEnumLiteralRef()
	 * @generated
	 */
	EReference getEnumLiteralRef_Enum();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.EnumLiteralRef#getLiteral <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Literal</em>'.
	 * @see org.sintef.thingml.EnumLiteralRef#getLiteral()
	 * @see #getEnumLiteralRef()
	 * @generated
	 */
	EReference getEnumLiteralRef_Literal();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.IntegerLiteral <em>Integer Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Literal</em>'.
	 * @see org.sintef.thingml.IntegerLiteral
	 * @generated
	 */
	EClass getIntegerLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.IntegerLiteral#getIntValue <em>Int Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Int Value</em>'.
	 * @see org.sintef.thingml.IntegerLiteral#getIntValue()
	 * @see #getIntegerLiteral()
	 * @generated
	 */
	EAttribute getIntegerLiteral_IntValue();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.BooleanLiteral <em>Boolean Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Literal</em>'.
	 * @see org.sintef.thingml.BooleanLiteral
	 * @generated
	 */
	EClass getBooleanLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.BooleanLiteral#isBoolValue <em>Bool Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bool Value</em>'.
	 * @see org.sintef.thingml.BooleanLiteral#isBoolValue()
	 * @see #getBooleanLiteral()
	 * @generated
	 */
	EAttribute getBooleanLiteral_BoolValue();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.StringLiteral <em>String Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Literal</em>'.
	 * @see org.sintef.thingml.StringLiteral
	 * @generated
	 */
	EClass getStringLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.StringLiteral#getStringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String Value</em>'.
	 * @see org.sintef.thingml.StringLiteral#getStringValue()
	 * @see #getStringLiteral()
	 * @generated
	 */
	EAttribute getStringLiteral_StringValue();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.DoubleLiteral <em>Double Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Double Literal</em>'.
	 * @see org.sintef.thingml.DoubleLiteral
	 * @generated
	 */
	EClass getDoubleLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.DoubleLiteral#getDoubleValue <em>Double Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Double Value</em>'.
	 * @see org.sintef.thingml.DoubleLiteral#getDoubleValue()
	 * @see #getDoubleLiteral()
	 * @generated
	 */
	EAttribute getDoubleLiteral_DoubleValue();

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
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.ConditionalAction#getElseAction <em>Else Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Else Action</em>'.
	 * @see org.sintef.thingml.ConditionalAction#getElseAction()
	 * @see #getConditionalAction()
	 * @generated
	 */
	EReference getConditionalAction_ElseAction();

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
	 * Returns the meta object for class '{@link org.sintef.thingml.ArrayIndex <em>Array Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Index</em>'.
	 * @see org.sintef.thingml.ArrayIndex
	 * @generated
	 */
	EClass getArrayIndex();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.ArrayIndex#getArray <em>Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Array</em>'.
	 * @see org.sintef.thingml.ArrayIndex#getArray()
	 * @see #getArrayIndex()
	 * @generated
	 */
	EReference getArrayIndex_Array();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.ArrayIndex#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index</em>'.
	 * @see org.sintef.thingml.ArrayIndex#getIndex()
	 * @see #getArrayIndex()
	 * @generated
	 */
	EReference getArrayIndex_Index();

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
	 * Returns the meta object for class '{@link org.sintef.thingml.ReturnAction <em>Return Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Return Action</em>'.
	 * @see org.sintef.thingml.ReturnAction
	 * @generated
	 */
	EClass getReturnAction();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.ReturnAction#getExp <em>Exp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exp</em>'.
	 * @see org.sintef.thingml.ReturnAction#getExp()
	 * @see #getReturnAction()
	 * @generated
	 */
	EReference getReturnAction_Exp();

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
	 * Returns the meta object for class '{@link org.sintef.thingml.Configuration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see org.sintef.thingml.Configuration
	 * @generated
	 */
	EClass getConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Configuration#getInstances <em>Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Instances</em>'.
	 * @see org.sintef.thingml.Configuration#getInstances()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_Instances();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Configuration#getConnectors <em>Connectors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Connectors</em>'.
	 * @see org.sintef.thingml.Configuration#getConnectors()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_Connectors();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.Configuration#isFragment <em>Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fragment</em>'.
	 * @see org.sintef.thingml.Configuration#isFragment()
	 * @see #getConfiguration()
	 * @generated
	 */
	EAttribute getConfiguration_Fragment();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Configuration#getConfigs <em>Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Configs</em>'.
	 * @see org.sintef.thingml.Configuration#getConfigs()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_Configs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Configuration#getPropassigns <em>Propassigns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Propassigns</em>'.
	 * @see org.sintef.thingml.Configuration#getPropassigns()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_Propassigns();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Instance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instance</em>'.
	 * @see org.sintef.thingml.Instance
	 * @generated
	 */
	EClass getInstance();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.Instance#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sintef.thingml.Instance#getType()
	 * @see #getInstance()
	 * @generated
	 */
	EReference getInstance_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Instance#getAssign <em>Assign</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assign</em>'.
	 * @see org.sintef.thingml.Instance#getAssign()
	 * @see #getInstance()
	 * @generated
	 */
	EReference getInstance_Assign();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Connector <em>Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connector</em>'.
	 * @see org.sintef.thingml.Connector
	 * @generated
	 */
	EClass getConnector();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Connector#getSrv <em>Srv</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Srv</em>'.
	 * @see org.sintef.thingml.Connector#getSrv()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_Srv();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Connector#getCli <em>Cli</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cli</em>'.
	 * @see org.sintef.thingml.Connector#getCli()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_Cli();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.Connector#getRequired <em>Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Required</em>'.
	 * @see org.sintef.thingml.Connector#getRequired()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_Required();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.Connector#getProvided <em>Provided</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provided</em>'.
	 * @see org.sintef.thingml.Connector#getProvided()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_Provided();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ExternalConnector <em>External Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Connector</em>'.
	 * @see org.sintef.thingml.ExternalConnector
	 * @generated
	 */
	EClass getExternalConnector();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.ExternalConnector#getInst <em>Inst</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inst</em>'.
	 * @see org.sintef.thingml.ExternalConnector#getInst()
	 * @see #getExternalConnector()
	 * @generated
	 */
	EReference getExternalConnector_Inst();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.ExternalConnector#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Port</em>'.
	 * @see org.sintef.thingml.ExternalConnector#getPort()
	 * @see #getExternalConnector()
	 * @generated
	 */
	EReference getExternalConnector_Port();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.ExternalConnector#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Protocol</em>'.
	 * @see org.sintef.thingml.ExternalConnector#getProtocol()
	 * @see #getExternalConnector()
	 * @generated
	 */
	EAttribute getExternalConnector_Protocol();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.AbstractConnector <em>Abstract Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Connector</em>'.
	 * @see org.sintef.thingml.AbstractConnector
	 * @generated
	 */
	EClass getAbstractConnector();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ConfigPropertyAssign <em>Config Property Assign</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Config Property Assign</em>'.
	 * @see org.sintef.thingml.ConfigPropertyAssign
	 * @generated
	 */
	EClass getConfigPropertyAssign();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.ConfigPropertyAssign#getInit <em>Init</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Init</em>'.
	 * @see org.sintef.thingml.ConfigPropertyAssign#getInit()
	 * @see #getConfigPropertyAssign()
	 * @generated
	 */
	EReference getConfigPropertyAssign_Init();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.ConfigPropertyAssign#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property</em>'.
	 * @see org.sintef.thingml.ConfigPropertyAssign#getProperty()
	 * @see #getConfigPropertyAssign()
	 * @generated
	 */
	EReference getConfigPropertyAssign_Property();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.ConfigPropertyAssign#getInstance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Instance</em>'.
	 * @see org.sintef.thingml.ConfigPropertyAssign#getInstance()
	 * @see #getConfigPropertyAssign()
	 * @generated
	 */
	EReference getConfigPropertyAssign_Instance();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.ConfigPropertyAssign#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Index</em>'.
	 * @see org.sintef.thingml.ConfigPropertyAssign#getIndex()
	 * @see #getConfigPropertyAssign()
	 * @generated
	 */
	EReference getConfigPropertyAssign_Index();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ConfigInclude <em>Config Include</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Config Include</em>'.
	 * @see org.sintef.thingml.ConfigInclude
	 * @generated
	 */
	EClass getConfigInclude();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.ConfigInclude#getConfig <em>Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Config</em>'.
	 * @see org.sintef.thingml.ConfigInclude#getConfig()
	 * @see #getConfigInclude()
	 * @generated
	 */
	EReference getConfigInclude_Config();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.InstanceRef <em>Instance Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instance Ref</em>'.
	 * @see org.sintef.thingml.InstanceRef
	 * @generated
	 */
	EClass getInstanceRef();

	/**
	 * Returns the meta object for the reference list '{@link org.sintef.thingml.InstanceRef#getConfig <em>Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Config</em>'.
	 * @see org.sintef.thingml.InstanceRef#getConfig()
	 * @see #getInstanceRef()
	 * @generated
	 */
	EReference getInstanceRef_Config();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.InstanceRef#getInstance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Instance</em>'.
	 * @see org.sintef.thingml.InstanceRef#getInstance()
	 * @see #getInstanceRef()
	 * @generated
	 */
	EReference getInstanceRef_Instance();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.FunctionCall <em>Function Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Call</em>'.
	 * @see org.sintef.thingml.FunctionCall
	 * @generated
	 */
	EClass getFunctionCall();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.FunctionCall#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.sintef.thingml.FunctionCall#getParameters()
	 * @see #getFunctionCall()
	 * @generated
	 */
	EReference getFunctionCall_Parameters();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.FunctionCall#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Function</em>'.
	 * @see org.sintef.thingml.FunctionCall#getFunction()
	 * @see #getFunctionCall()
	 * @generated
	 */
	EReference getFunctionCall_Function();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.FunctionCallStatement <em>Function Call Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Call Statement</em>'.
	 * @see org.sintef.thingml.FunctionCallStatement
	 * @generated
	 */
	EClass getFunctionCallStatement();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.FunctionCallExpression <em>Function Call Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Call Expression</em>'.
	 * @see org.sintef.thingml.FunctionCallExpression
	 * @generated
	 */
	EClass getFunctionCallExpression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.LocalVariable <em>Local Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Variable</em>'.
	 * @see org.sintef.thingml.LocalVariable
	 * @generated
	 */
	EClass getLocalVariable();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.LocalVariable#getInit <em>Init</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Init</em>'.
	 * @see org.sintef.thingml.LocalVariable#getInit()
	 * @see #getLocalVariable()
	 * @generated
	 */
	EReference getLocalVariable_Init();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.LocalVariable#isChangeable <em>Changeable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Changeable</em>'.
	 * @see org.sintef.thingml.LocalVariable#isChangeable()
	 * @see #getLocalVariable()
	 * @generated
	 */
	EAttribute getLocalVariable_Changeable();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Stream <em>Stream</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stream</em>'.
	 * @see org.sintef.thingml.Stream
	 * @generated
	 */
	EClass getStream();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Stream#getSelection <em>Selection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Selection</em>'.
	 * @see org.sintef.thingml.Stream#getSelection()
	 * @see #getStream()
	 * @generated
	 */
	EReference getStream_Selection();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Stream#getOutput <em>Output</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Output</em>'.
	 * @see org.sintef.thingml.Stream#getOutput()
	 * @see #getStream()
	 * @generated
	 */
	EReference getStream_Output();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Stream#getInput <em>Input</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Input</em>'.
	 * @see org.sintef.thingml.Stream#getInput()
	 * @see #getStream()
	 * @generated
	 */
	EReference getStream_Input();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.StreamExpression <em>Stream Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stream Expression</em>'.
	 * @see org.sintef.thingml.StreamExpression
	 * @generated
	 */
	EClass getStreamExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.StreamExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.sintef.thingml.StreamExpression#getExpression()
	 * @see #getStreamExpression()
	 * @generated
	 */
	EReference getStreamExpression_Expression();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.StreamParamReference <em>Stream Param Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stream Param Reference</em>'.
	 * @see org.sintef.thingml.StreamParamReference
	 * @generated
	 */
	EClass getStreamParamReference();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.StreamParamReference#getIndexParam <em>Index Param</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index Param</em>'.
	 * @see org.sintef.thingml.StreamParamReference#getIndexParam()
	 * @see #getStreamParamReference()
	 * @generated
	 */
	EAttribute getStreamParamReference_IndexParam();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.StreamOutput <em>Stream Output</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stream Output</em>'.
	 * @see org.sintef.thingml.StreamOutput
	 * @generated
	 */
	EClass getStreamOutput();

	/**
	 * Returns the meta object for the reference list '{@link org.sintef.thingml.StreamOutput#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Parameters</em>'.
	 * @see org.sintef.thingml.StreamOutput#getParameters()
	 * @see #getStreamOutput()
	 * @generated
	 */
	EReference getStreamOutput_Parameters();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.StreamOutput#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Message</em>'.
	 * @see org.sintef.thingml.StreamOutput#getMessage()
	 * @see #getStreamOutput()
	 * @generated
	 */
	EReference getStreamOutput_Message();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.StreamOutput#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Port</em>'.
	 * @see org.sintef.thingml.StreamOutput#getPort()
	 * @see #getStreamOutput()
	 * @generated
	 */
	EReference getStreamOutput_Port();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Source <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see org.sintef.thingml.Source
	 * @generated
	 */
	EClass getSource();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.Source#getOperators <em>Operators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operators</em>'.
	 * @see org.sintef.thingml.Source#getOperators()
	 * @see #getSource()
	 * @generated
	 */
	EReference getSource_Operators();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.SourceComposition <em>Source Composition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source Composition</em>'.
	 * @see org.sintef.thingml.SourceComposition
	 * @generated
	 */
	EClass getSourceComposition();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.SourceComposition#getSources <em>Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sources</em>'.
	 * @see org.sintef.thingml.SourceComposition#getSources()
	 * @see #getSourceComposition()
	 * @generated
	 */
	EReference getSourceComposition_Sources();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.SourceComposition#getResultMessage <em>Result Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Result Message</em>'.
	 * @see org.sintef.thingml.SourceComposition#getResultMessage()
	 * @see #getSourceComposition()
	 * @generated
	 */
	EReference getSourceComposition_ResultMessage();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sintef.thingml.SourceComposition#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see org.sintef.thingml.SourceComposition#getRules()
	 * @see #getSourceComposition()
	 * @generated
	 */
	EReference getSourceComposition_Rules();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.JoinSources <em>Join Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Join Sources</em>'.
	 * @see org.sintef.thingml.JoinSources
	 * @generated
	 */
	EClass getJoinSources();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.MergeSources <em>Merge Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Merge Sources</em>'.
	 * @see org.sintef.thingml.MergeSources
	 * @generated
	 */
	EClass getMergeSources();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.SimpleSource <em>Simple Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Source</em>'.
	 * @see org.sintef.thingml.SimpleSource
	 * @generated
	 */
	EClass getSimpleSource();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.SimpleSource#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Message</em>'.
	 * @see org.sintef.thingml.SimpleSource#getMessage()
	 * @see #getSimpleSource()
	 * @generated
	 */
	EReference getSimpleSource_Message();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ViewSource <em>View Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View Source</em>'.
	 * @see org.sintef.thingml.ViewSource
	 * @generated
	 */
	EClass getViewSource();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Filter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Filter</em>'.
	 * @see org.sintef.thingml.Filter
	 * @generated
	 */
	EClass getFilter();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Filter#getFilterOp <em>Filter Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Filter Op</em>'.
	 * @see org.sintef.thingml.Filter#getFilterOp()
	 * @see #getFilter()
	 * @generated
	 */
	EReference getFilter_FilterOp();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Operator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operator</em>'.
	 * @see org.sintef.thingml.Operator
	 * @generated
	 */
	EClass getOperator();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Operator#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.sintef.thingml.Operator#getBody()
	 * @see #getOperator()
	 * @generated
	 */
	EReference getOperator_Body();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.MessageParameter <em>Message Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Message Parameter</em>'.
	 * @see org.sintef.thingml.MessageParameter
	 * @generated
	 */
	EClass getMessageParameter();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.MessageParameter#getMsgRef <em>Msg Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Msg Ref</em>'.
	 * @see org.sintef.thingml.MessageParameter#getMsgRef()
	 * @see #getMessageParameter()
	 * @generated
	 */
	EReference getMessageParameter_MsgRef();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.SglMsgParamOperatorCall <em>Sgl Msg Param Operator Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sgl Msg Param Operator Call</em>'.
	 * @see org.sintef.thingml.SglMsgParamOperatorCall
	 * @generated
	 */
	EClass getSglMsgParamOperatorCall();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.SglMsgParamOperatorCall#getOperatorRef <em>Operator Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Operator Ref</em>'.
	 * @see org.sintef.thingml.SglMsgParamOperatorCall#getOperatorRef()
	 * @see #getSglMsgParamOperatorCall()
	 * @generated
	 */
	EReference getSglMsgParamOperatorCall_OperatorRef();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.SglMsgParamOperatorCall#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parameter</em>'.
	 * @see org.sintef.thingml.SglMsgParamOperatorCall#getParameter()
	 * @see #getSglMsgParamOperatorCall()
	 * @generated
	 */
	EReference getSglMsgParamOperatorCall_Parameter();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.Reference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference</em>'.
	 * @see org.sintef.thingml.Reference
	 * @generated
	 */
	EClass getReference();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.Reference#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Parameter</em>'.
	 * @see org.sintef.thingml.Reference#getParameter()
	 * @see #getReference()
	 * @generated
	 */
	EReference getReference_Parameter();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.Reference#getReference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reference</em>'.
	 * @see org.sintef.thingml.Reference#getReference()
	 * @see #getReference()
	 * @generated
	 */
	EReference getReference_Reference();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ReferencedElmt <em>Referenced Elmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Referenced Elmt</em>'.
	 * @see org.sintef.thingml.ReferencedElmt
	 * @generated
	 */
	EClass getReferencedElmt();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.SglMsgParamOperator <em>Sgl Msg Param Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sgl Msg Param Operator</em>'.
	 * @see org.sintef.thingml.SglMsgParamOperator
	 * @generated
	 */
	EClass getSglMsgParamOperator();

	/**
	 * Returns the meta object for the containment reference '{@link org.sintef.thingml.SglMsgParamOperator#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Parameter</em>'.
	 * @see org.sintef.thingml.SglMsgParamOperator#getParameter()
	 * @see #getSglMsgParamOperator()
	 * @generated
	 */
	EReference getSglMsgParamOperator_Parameter();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.LengthWindow <em>Length Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Length Window</em>'.
	 * @see org.sintef.thingml.LengthWindow
	 * @generated
	 */
	EClass getLengthWindow();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.LengthWindow#getNbEvents <em>Nb Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nb Events</em>'.
	 * @see org.sintef.thingml.LengthWindow#getNbEvents()
	 * @see #getLengthWindow()
	 * @generated
	 */
	EAttribute getLengthWindow_NbEvents();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.LengthWindow#getStep <em>Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Step</em>'.
	 * @see org.sintef.thingml.LengthWindow#getStep()
	 * @see #getLengthWindow()
	 * @generated
	 */
	EAttribute getLengthWindow_Step();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.TimeWindow <em>Time Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Time Window</em>'.
	 * @see org.sintef.thingml.TimeWindow
	 * @generated
	 */
	EClass getTimeWindow();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.TimeWindow#getStep <em>Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Step</em>'.
	 * @see org.sintef.thingml.TimeWindow#getStep()
	 * @see #getTimeWindow()
	 * @generated
	 */
	EAttribute getTimeWindow_Step();

	/**
	 * Returns the meta object for the attribute '{@link org.sintef.thingml.TimeWindow#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see org.sintef.thingml.TimeWindow#getSize()
	 * @see #getTimeWindow()
	 * @generated
	 */
	EAttribute getTimeWindow_Size();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ParamReference <em>Param Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Param Reference</em>'.
	 * @see org.sintef.thingml.ParamReference
	 * @generated
	 */
	EClass getParamReference();

	/**
	 * Returns the meta object for the reference '{@link org.sintef.thingml.ParamReference#getParameterRef <em>Parameter Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parameter Ref</em>'.
	 * @see org.sintef.thingml.ParamReference#getParameterRef()
	 * @see #getParamReference()
	 * @generated
	 */
	EReference getParamReference_ParameterRef();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.SimpleParamRef <em>Simple Param Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Param Ref</em>'.
	 * @see org.sintef.thingml.SimpleParamRef
	 * @generated
	 */
	EClass getSimpleParamRef();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ArrayParamRef <em>Array Param Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Param Ref</em>'.
	 * @see org.sintef.thingml.ArrayParamRef
	 * @generated
	 */
	EClass getArrayParamRef();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.ElmtProperty <em>Elmt Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Elmt Property</em>'.
	 * @see org.sintef.thingml.ElmtProperty
	 * @generated
	 */
	EClass getElmtProperty();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.PredifinedProperty <em>Predifined Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Predifined Property</em>'.
	 * @see org.sintef.thingml.PredifinedProperty
	 * @generated
	 */
	EClass getPredifinedProperty();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.LengthArray <em>Length Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Length Array</em>'.
	 * @see org.sintef.thingml.LengthArray
	 * @generated
	 */
	EClass getLengthArray();

	/**
	 * Returns the meta object for class '{@link org.sintef.thingml.WindowView <em>Window View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Window View</em>'.
	 * @see org.sintef.thingml.WindowView
	 * @generated
	 */
	EClass getWindowView();

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
		 * The meta object literal for the '<em><b>Configs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING_ML_MODEL__CONFIGS = eINSTANCE.getThingMLModel_Configs();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.FunctionImpl <em>Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.FunctionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getFunction()
		 * @generated
		 */
		EClass FUNCTION = eINSTANCE.getFunction();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__PARAMETERS = eINSTANCE.getFunction_Parameters();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__BODY = eINSTANCE.getFunction_Body();

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
		 * The meta object literal for the '<em><b>Assign</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING__ASSIGN = eINSTANCE.getThing_Assign();

		/**
		 * The meta object literal for the '<em><b>Messages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING__MESSAGES = eINSTANCE.getThing_Messages();

		/**
		 * The meta object literal for the '<em><b>Functions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING__FUNCTIONS = eINSTANCE.getThing_Functions();

		/**
		 * The meta object literal for the '<em><b>Streams</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING__STREAMS = eINSTANCE.getThing_Streams();

		/**
		 * The meta object literal for the '<em><b>Operators</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THING__OPERATORS = eINSTANCE.getThing_Operators();

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
		 * The meta object literal for the '{@link org.sintef.thingml.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.VariableImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

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
		 * The meta object literal for the '{@link org.sintef.thingml.impl.TypedElementImpl <em>Typed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.TypedElementImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getTypedElement()
		 * @generated
		 */
		EClass TYPED_ELEMENT = eINSTANCE.getTypedElement();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPED_ELEMENT__TYPE = eINSTANCE.getTypedElement_Type();

		/**
		 * The meta object literal for the '<em><b>Cardinality</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPED_ELEMENT__CARDINALITY = eINSTANCE.getTypedElement_Cardinality();

		/**
		 * The meta object literal for the '<em><b>Is Array</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPED_ELEMENT__IS_ARRAY = eINSTANCE.getTypedElement_IsArray();

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
		 * The meta object literal for the '<em><b>Init</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__INIT = eINSTANCE.getProperty_Init();

		/**
		 * The meta object literal for the '<em><b>Changeable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__CHANGEABLE = eINSTANCE.getProperty_Changeable();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.PropertyAssignImpl <em>Property Assign</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.PropertyAssignImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPropertyAssign()
		 * @generated
		 */
		EClass PROPERTY_ASSIGN = eINSTANCE.getPropertyAssign();

		/**
		 * The meta object literal for the '<em><b>Init</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_ASSIGN__INIT = eINSTANCE.getPropertyAssign_Init();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_ASSIGN__PROPERTY = eINSTANCE.getPropertyAssign_Property();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_ASSIGN__INDEX = eINSTANCE.getPropertyAssign_Index();

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
		 * The meta object literal for the '<em><b>Enum</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATION_LITERAL__ENUM = eINSTANCE.getEnumerationLiteral_Enum();

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
		 * The meta object literal for the '<em><b>After</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__AFTER = eINSTANCE.getTransition_After();

		/**
		 * The meta object literal for the '<em><b>Before</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__BEFORE = eINSTANCE.getTransition_Before();

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
		 * The meta object literal for the '{@link org.sintef.thingml.impl.VariableAssignmentImpl <em>Variable Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.VariableAssignmentImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getVariableAssignment()
		 * @generated
		 */
		EClass VARIABLE_ASSIGNMENT = eINSTANCE.getVariableAssignment();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_ASSIGNMENT__PROPERTY = eINSTANCE.getVariableAssignment_Property();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_ASSIGNMENT__EXPRESSION = eINSTANCE.getVariableAssignment_Expression();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_ASSIGNMENT__INDEX = eINSTANCE.getVariableAssignment_Index();

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
		 * The meta object literal for the '{@link org.sintef.thingml.impl.RequiredPortImpl <em>Required Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.RequiredPortImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getRequiredPort()
		 * @generated
		 */
		EClass REQUIRED_PORT = eINSTANCE.getRequiredPort();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIRED_PORT__OPTIONAL = eINSTANCE.getRequiredPort_Optional();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ProvidedPortImpl <em>Provided Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ProvidedPortImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getProvidedPort()
		 * @generated
		 */
		EClass PROVIDED_PORT = eINSTANCE.getProvidedPort();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.LiteralImpl <em>Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.LiteralImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLiteral()
		 * @generated
		 */
		EClass LITERAL = eINSTANCE.getLiteral();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.EnumLiteralRefImpl <em>Enum Literal Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.EnumLiteralRefImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getEnumLiteralRef()
		 * @generated
		 */
		EClass ENUM_LITERAL_REF = eINSTANCE.getEnumLiteralRef();

		/**
		 * The meta object literal for the '<em><b>Enum</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUM_LITERAL_REF__ENUM = eINSTANCE.getEnumLiteralRef_Enum();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUM_LITERAL_REF__LITERAL = eINSTANCE.getEnumLiteralRef_Literal();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.IntegerLiteralImpl <em>Integer Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.IntegerLiteralImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getIntegerLiteral()
		 * @generated
		 */
		EClass INTEGER_LITERAL = eINSTANCE.getIntegerLiteral();

		/**
		 * The meta object literal for the '<em><b>Int Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_LITERAL__INT_VALUE = eINSTANCE.getIntegerLiteral_IntValue();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.BooleanLiteralImpl <em>Boolean Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.BooleanLiteralImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getBooleanLiteral()
		 * @generated
		 */
		EClass BOOLEAN_LITERAL = eINSTANCE.getBooleanLiteral();

		/**
		 * The meta object literal for the '<em><b>Bool Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_LITERAL__BOOL_VALUE = eINSTANCE.getBooleanLiteral_BoolValue();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.StringLiteralImpl <em>String Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.StringLiteralImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStringLiteral()
		 * @generated
		 */
		EClass STRING_LITERAL = eINSTANCE.getStringLiteral();

		/**
		 * The meta object literal for the '<em><b>String Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_LITERAL__STRING_VALUE = eINSTANCE.getStringLiteral_StringValue();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.DoubleLiteralImpl <em>Double Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.DoubleLiteralImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getDoubleLiteral()
		 * @generated
		 */
		EClass DOUBLE_LITERAL = eINSTANCE.getDoubleLiteral();

		/**
		 * The meta object literal for the '<em><b>Double Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOUBLE_LITERAL__DOUBLE_VALUE = eINSTANCE.getDoubleLiteral_DoubleValue();

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
		 * The meta object literal for the '<em><b>Else Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_ACTION__ELSE_ACTION = eINSTANCE.getConditionalAction_ElseAction();

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
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ArrayIndexImpl <em>Array Index</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ArrayIndexImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getArrayIndex()
		 * @generated
		 */
		EClass ARRAY_INDEX = eINSTANCE.getArrayIndex();

		/**
		 * The meta object literal for the '<em><b>Array</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARRAY_INDEX__ARRAY = eINSTANCE.getArrayIndex_Array();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARRAY_INDEX__INDEX = eINSTANCE.getArrayIndex_Index();

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
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ReturnActionImpl <em>Return Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ReturnActionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getReturnAction()
		 * @generated
		 */
		EClass RETURN_ACTION = eINSTANCE.getReturnAction();

		/**
		 * The meta object literal for the '<em><b>Exp</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RETURN_ACTION__EXP = eINSTANCE.getReturnAction_Exp();

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

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ConfigurationImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getConfiguration()
		 * @generated
		 */
		EClass CONFIGURATION = eINSTANCE.getConfiguration();

		/**
		 * The meta object literal for the '<em><b>Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__INSTANCES = eINSTANCE.getConfiguration_Instances();

		/**
		 * The meta object literal for the '<em><b>Connectors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__CONNECTORS = eINSTANCE.getConfiguration_Connectors();

		/**
		 * The meta object literal for the '<em><b>Fragment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION__FRAGMENT = eINSTANCE.getConfiguration_Fragment();

		/**
		 * The meta object literal for the '<em><b>Configs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__CONFIGS = eINSTANCE.getConfiguration_Configs();

		/**
		 * The meta object literal for the '<em><b>Propassigns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__PROPASSIGNS = eINSTANCE.getConfiguration_Propassigns();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.InstanceImpl <em>Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.InstanceImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getInstance()
		 * @generated
		 */
		EClass INSTANCE = eINSTANCE.getInstance();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE__TYPE = eINSTANCE.getInstance_Type();

		/**
		 * The meta object literal for the '<em><b>Assign</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE__ASSIGN = eINSTANCE.getInstance_Assign();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ConnectorImpl <em>Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ConnectorImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getConnector()
		 * @generated
		 */
		EClass CONNECTOR = eINSTANCE.getConnector();

		/**
		 * The meta object literal for the '<em><b>Srv</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR__SRV = eINSTANCE.getConnector_Srv();

		/**
		 * The meta object literal for the '<em><b>Cli</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR__CLI = eINSTANCE.getConnector_Cli();

		/**
		 * The meta object literal for the '<em><b>Required</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR__REQUIRED = eINSTANCE.getConnector_Required();

		/**
		 * The meta object literal for the '<em><b>Provided</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR__PROVIDED = eINSTANCE.getConnector_Provided();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ExternalConnectorImpl <em>External Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ExternalConnectorImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getExternalConnector()
		 * @generated
		 */
		EClass EXTERNAL_CONNECTOR = eINSTANCE.getExternalConnector();

		/**
		 * The meta object literal for the '<em><b>Inst</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_CONNECTOR__INST = eINSTANCE.getExternalConnector_Inst();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_CONNECTOR__PORT = eINSTANCE.getExternalConnector_Port();

		/**
		 * The meta object literal for the '<em><b>Protocol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_CONNECTOR__PROTOCOL = eINSTANCE.getExternalConnector_Protocol();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.AbstractConnectorImpl <em>Abstract Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.AbstractConnectorImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getAbstractConnector()
		 * @generated
		 */
		EClass ABSTRACT_CONNECTOR = eINSTANCE.getAbstractConnector();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ConfigPropertyAssignImpl <em>Config Property Assign</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ConfigPropertyAssignImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getConfigPropertyAssign()
		 * @generated
		 */
		EClass CONFIG_PROPERTY_ASSIGN = eINSTANCE.getConfigPropertyAssign();

		/**
		 * The meta object literal for the '<em><b>Init</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIG_PROPERTY_ASSIGN__INIT = eINSTANCE.getConfigPropertyAssign_Init();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIG_PROPERTY_ASSIGN__PROPERTY = eINSTANCE.getConfigPropertyAssign_Property();

		/**
		 * The meta object literal for the '<em><b>Instance</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIG_PROPERTY_ASSIGN__INSTANCE = eINSTANCE.getConfigPropertyAssign_Instance();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIG_PROPERTY_ASSIGN__INDEX = eINSTANCE.getConfigPropertyAssign_Index();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ConfigIncludeImpl <em>Config Include</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ConfigIncludeImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getConfigInclude()
		 * @generated
		 */
		EClass CONFIG_INCLUDE = eINSTANCE.getConfigInclude();

		/**
		 * The meta object literal for the '<em><b>Config</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIG_INCLUDE__CONFIG = eINSTANCE.getConfigInclude_Config();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.InstanceRefImpl <em>Instance Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.InstanceRefImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getInstanceRef()
		 * @generated
		 */
		EClass INSTANCE_REF = eINSTANCE.getInstanceRef();

		/**
		 * The meta object literal for the '<em><b>Config</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE_REF__CONFIG = eINSTANCE.getInstanceRef_Config();

		/**
		 * The meta object literal for the '<em><b>Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE_REF__INSTANCE = eINSTANCE.getInstanceRef_Instance();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.FunctionCallImpl <em>Function Call</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.FunctionCallImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getFunctionCall()
		 * @generated
		 */
		EClass FUNCTION_CALL = eINSTANCE.getFunctionCall();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_CALL__PARAMETERS = eINSTANCE.getFunctionCall_Parameters();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_CALL__FUNCTION = eINSTANCE.getFunctionCall_Function();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.FunctionCallStatementImpl <em>Function Call Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.FunctionCallStatementImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getFunctionCallStatement()
		 * @generated
		 */
		EClass FUNCTION_CALL_STATEMENT = eINSTANCE.getFunctionCallStatement();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.FunctionCallExpressionImpl <em>Function Call Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.FunctionCallExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getFunctionCallExpression()
		 * @generated
		 */
		EClass FUNCTION_CALL_EXPRESSION = eINSTANCE.getFunctionCallExpression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.LocalVariableImpl <em>Local Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.LocalVariableImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLocalVariable()
		 * @generated
		 */
		EClass LOCAL_VARIABLE = eINSTANCE.getLocalVariable();

		/**
		 * The meta object literal for the '<em><b>Init</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCAL_VARIABLE__INIT = eINSTANCE.getLocalVariable_Init();

		/**
		 * The meta object literal for the '<em><b>Changeable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_VARIABLE__CHANGEABLE = eINSTANCE.getLocalVariable_Changeable();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.StreamImpl <em>Stream</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.StreamImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStream()
		 * @generated
		 */
		EClass STREAM = eINSTANCE.getStream();

		/**
		 * The meta object literal for the '<em><b>Selection</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM__SELECTION = eINSTANCE.getStream_Selection();

		/**
		 * The meta object literal for the '<em><b>Output</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM__OUTPUT = eINSTANCE.getStream_Output();

		/**
		 * The meta object literal for the '<em><b>Input</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM__INPUT = eINSTANCE.getStream_Input();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.StreamExpressionImpl <em>Stream Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.StreamExpressionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStreamExpression()
		 * @generated
		 */
		EClass STREAM_EXPRESSION = eINSTANCE.getStreamExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_EXPRESSION__EXPRESSION = eINSTANCE.getStreamExpression_Expression();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.StreamParamReferenceImpl <em>Stream Param Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.StreamParamReferenceImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStreamParamReference()
		 * @generated
		 */
		EClass STREAM_PARAM_REFERENCE = eINSTANCE.getStreamParamReference();

		/**
		 * The meta object literal for the '<em><b>Index Param</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STREAM_PARAM_REFERENCE__INDEX_PARAM = eINSTANCE.getStreamParamReference_IndexParam();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.StreamOutputImpl <em>Stream Output</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.StreamOutputImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getStreamOutput()
		 * @generated
		 */
		EClass STREAM_OUTPUT = eINSTANCE.getStreamOutput();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_OUTPUT__PARAMETERS = eINSTANCE.getStreamOutput_Parameters();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_OUTPUT__MESSAGE = eINSTANCE.getStreamOutput_Message();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_OUTPUT__PORT = eINSTANCE.getStreamOutput_Port();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.SourceImpl <em>Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.SourceImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSource()
		 * @generated
		 */
		EClass SOURCE = eINSTANCE.getSource();

		/**
		 * The meta object literal for the '<em><b>Operators</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE__OPERATORS = eINSTANCE.getSource_Operators();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.SourceCompositionImpl <em>Source Composition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.SourceCompositionImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSourceComposition()
		 * @generated
		 */
		EClass SOURCE_COMPOSITION = eINSTANCE.getSourceComposition();

		/**
		 * The meta object literal for the '<em><b>Sources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_COMPOSITION__SOURCES = eINSTANCE.getSourceComposition_Sources();

		/**
		 * The meta object literal for the '<em><b>Result Message</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_COMPOSITION__RESULT_MESSAGE = eINSTANCE.getSourceComposition_ResultMessage();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_COMPOSITION__RULES = eINSTANCE.getSourceComposition_Rules();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.JoinSourcesImpl <em>Join Sources</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.JoinSourcesImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getJoinSources()
		 * @generated
		 */
		EClass JOIN_SOURCES = eINSTANCE.getJoinSources();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.MergeSourcesImpl <em>Merge Sources</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.MergeSourcesImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getMergeSources()
		 * @generated
		 */
		EClass MERGE_SOURCES = eINSTANCE.getMergeSources();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.SimpleSourceImpl <em>Simple Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.SimpleSourceImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSimpleSource()
		 * @generated
		 */
		EClass SIMPLE_SOURCE = eINSTANCE.getSimpleSource();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_SOURCE__MESSAGE = eINSTANCE.getSimpleSource_Message();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ViewSourceImpl <em>View Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ViewSourceImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getViewSource()
		 * @generated
		 */
		EClass VIEW_SOURCE = eINSTANCE.getViewSource();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.FilterImpl <em>Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.FilterImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getFilter()
		 * @generated
		 */
		EClass FILTER = eINSTANCE.getFilter();

		/**
		 * The meta object literal for the '<em><b>Filter Op</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILTER__FILTER_OP = eINSTANCE.getFilter_FilterOp();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.OperatorImpl <em>Operator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.OperatorImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getOperator()
		 * @generated
		 */
		EClass OPERATOR = eINSTANCE.getOperator();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATOR__BODY = eINSTANCE.getOperator_Body();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.MessageParameterImpl <em>Message Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.MessageParameterImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getMessageParameter()
		 * @generated
		 */
		EClass MESSAGE_PARAMETER = eINSTANCE.getMessageParameter();

		/**
		 * The meta object literal for the '<em><b>Msg Ref</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MESSAGE_PARAMETER__MSG_REF = eINSTANCE.getMessageParameter_MsgRef();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.SglMsgParamOperatorCallImpl <em>Sgl Msg Param Operator Call</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.SglMsgParamOperatorCallImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSglMsgParamOperatorCall()
		 * @generated
		 */
		EClass SGL_MSG_PARAM_OPERATOR_CALL = eINSTANCE.getSglMsgParamOperatorCall();

		/**
		 * The meta object literal for the '<em><b>Operator Ref</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SGL_MSG_PARAM_OPERATOR_CALL__OPERATOR_REF = eINSTANCE.getSglMsgParamOperatorCall_OperatorRef();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SGL_MSG_PARAM_OPERATOR_CALL__PARAMETER = eINSTANCE.getSglMsgParamOperatorCall_Parameter();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ReferenceImpl <em>Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ReferenceImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getReference()
		 * @generated
		 */
		EClass REFERENCE = eINSTANCE.getReference();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCE__PARAMETER = eINSTANCE.getReference_Parameter();

		/**
		 * The meta object literal for the '<em><b>Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCE__REFERENCE = eINSTANCE.getReference_Reference();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ReferencedElmtImpl <em>Referenced Elmt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ReferencedElmtImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getReferencedElmt()
		 * @generated
		 */
		EClass REFERENCED_ELMT = eINSTANCE.getReferencedElmt();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.SglMsgParamOperatorImpl <em>Sgl Msg Param Operator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.SglMsgParamOperatorImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSglMsgParamOperator()
		 * @generated
		 */
		EClass SGL_MSG_PARAM_OPERATOR = eINSTANCE.getSglMsgParamOperator();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SGL_MSG_PARAM_OPERATOR__PARAMETER = eINSTANCE.getSglMsgParamOperator_Parameter();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.LengthWindowImpl <em>Length Window</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.LengthWindowImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLengthWindow()
		 * @generated
		 */
		EClass LENGTH_WINDOW = eINSTANCE.getLengthWindow();

		/**
		 * The meta object literal for the '<em><b>Nb Events</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LENGTH_WINDOW__NB_EVENTS = eINSTANCE.getLengthWindow_NbEvents();

		/**
		 * The meta object literal for the '<em><b>Step</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LENGTH_WINDOW__STEP = eINSTANCE.getLengthWindow_Step();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.TimeWindowImpl <em>Time Window</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.TimeWindowImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getTimeWindow()
		 * @generated
		 */
		EClass TIME_WINDOW = eINSTANCE.getTimeWindow();

		/**
		 * The meta object literal for the '<em><b>Step</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIME_WINDOW__STEP = eINSTANCE.getTimeWindow_Step();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIME_WINDOW__SIZE = eINSTANCE.getTimeWindow_Size();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ParamReferenceImpl <em>Param Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ParamReferenceImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getParamReference()
		 * @generated
		 */
		EClass PARAM_REFERENCE = eINSTANCE.getParamReference();

		/**
		 * The meta object literal for the '<em><b>Parameter Ref</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAM_REFERENCE__PARAMETER_REF = eINSTANCE.getParamReference_ParameterRef();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.SimpleParamRefImpl <em>Simple Param Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.SimpleParamRefImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getSimpleParamRef()
		 * @generated
		 */
		EClass SIMPLE_PARAM_REF = eINSTANCE.getSimpleParamRef();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ArrayParamRefImpl <em>Array Param Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ArrayParamRefImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getArrayParamRef()
		 * @generated
		 */
		EClass ARRAY_PARAM_REF = eINSTANCE.getArrayParamRef();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.ElmtPropertyImpl <em>Elmt Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.ElmtPropertyImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getElmtProperty()
		 * @generated
		 */
		EClass ELMT_PROPERTY = eINSTANCE.getElmtProperty();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.PredifinedPropertyImpl <em>Predifined Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.PredifinedPropertyImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getPredifinedProperty()
		 * @generated
		 */
		EClass PREDIFINED_PROPERTY = eINSTANCE.getPredifinedProperty();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.LengthArrayImpl <em>Length Array</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.LengthArrayImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getLengthArray()
		 * @generated
		 */
		EClass LENGTH_ARRAY = eINSTANCE.getLengthArray();

		/**
		 * The meta object literal for the '{@link org.sintef.thingml.impl.WindowViewImpl <em>Window View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sintef.thingml.impl.WindowViewImpl
		 * @see org.sintef.thingml.impl.ThingmlPackageImpl#getWindowView()
		 * @generated
		 */
		EClass WINDOW_VIEW = eINSTANCE.getWindowView();

	}

} //ThingmlPackage
