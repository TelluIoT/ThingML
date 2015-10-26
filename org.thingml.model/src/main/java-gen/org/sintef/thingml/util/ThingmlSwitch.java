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
package org.sintef.thingml.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.sintef.thingml.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.sintef.thingml.ThingmlPackage
 * @generated
 */
public class ThingmlSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ThingmlPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThingmlSwitch() {
		if (modelPackage == null) {
			modelPackage = ThingmlPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ThingmlPackage.THING_ML_MODEL: {
				ThingMLModel thingMLModel = (ThingMLModel)theEObject;
				T result = caseThingMLModel(thingMLModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.FUNCTION: {
				Function function = (Function)theEObject;
				T result = caseFunction(function);
				if (result == null) result = caseAnnotatedElement(function);
				if (result == null) result = caseTypedElement(function);
				if (result == null) result = caseThingMLElement(function);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.MESSAGE: {
				Message message = (Message)theEObject;
				T result = caseMessage(message);
				if (result == null) result = caseAnnotatedElement(message);
				if (result == null) result = caseThingMLElement(message);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.THING: {
				Thing thing = (Thing)theEObject;
				T result = caseThing(thing);
				if (result == null) result = caseType(thing);
				if (result == null) result = caseAnnotatedElement(thing);
				if (result == null) result = caseThingMLElement(thing);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PARAMETER: {
				Parameter parameter = (Parameter)theEObject;
				T result = caseParameter(parameter);
				if (result == null) result = caseVariable(parameter);
				if (result == null) result = caseTypedElement(parameter);
				if (result == null) result = caseAnnotatedElement(parameter);
				if (result == null) result = caseReferencedElmt(parameter);
				if (result == null) result = caseThingMLElement(parameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.VARIABLE: {
				Variable variable = (Variable)theEObject;
				T result = caseVariable(variable);
				if (result == null) result = caseTypedElement(variable);
				if (result == null) result = caseAnnotatedElement(variable);
				if (result == null) result = caseReferencedElmt(variable);
				if (result == null) result = caseThingMLElement(variable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.THING_ML_ELEMENT: {
				ThingMLElement thingMLElement = (ThingMLElement)theEObject;
				T result = caseThingMLElement(thingMLElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.TYPE: {
				Type type = (Type)theEObject;
				T result = caseType(type);
				if (result == null) result = caseAnnotatedElement(type);
				if (result == null) result = caseThingMLElement(type);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.TYPED_ELEMENT: {
				TypedElement typedElement = (TypedElement)theEObject;
				T result = caseTypedElement(typedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PROPERTY: {
				Property property = (Property)theEObject;
				T result = caseProperty(property);
				if (result == null) result = caseVariable(property);
				if (result == null) result = caseTypedElement(property);
				if (result == null) result = caseAnnotatedElement(property);
				if (result == null) result = caseReferencedElmt(property);
				if (result == null) result = caseThingMLElement(property);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PROPERTY_ASSIGN: {
				PropertyAssign propertyAssign = (PropertyAssign)theEObject;
				T result = casePropertyAssign(propertyAssign);
				if (result == null) result = caseAnnotatedElement(propertyAssign);
				if (result == null) result = caseThingMLElement(propertyAssign);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PLATFORM_ANNOTATION: {
				PlatformAnnotation platformAnnotation = (PlatformAnnotation)theEObject;
				T result = casePlatformAnnotation(platformAnnotation);
				if (result == null) result = caseThingMLElement(platformAnnotation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.ENUMERATION: {
				Enumeration enumeration = (Enumeration)theEObject;
				T result = caseEnumeration(enumeration);
				if (result == null) result = caseType(enumeration);
				if (result == null) result = caseAnnotatedElement(enumeration);
				if (result == null) result = caseThingMLElement(enumeration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PRIMITIVE_TYPE: {
				PrimitiveType primitiveType = (PrimitiveType)theEObject;
				T result = casePrimitiveType(primitiveType);
				if (result == null) result = caseType(primitiveType);
				if (result == null) result = caseAnnotatedElement(primitiveType);
				if (result == null) result = caseThingMLElement(primitiveType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.ENUMERATION_LITERAL: {
				EnumerationLiteral enumerationLiteral = (EnumerationLiteral)theEObject;
				T result = caseEnumerationLiteral(enumerationLiteral);
				if (result == null) result = caseAnnotatedElement(enumerationLiteral);
				if (result == null) result = caseThingMLElement(enumerationLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.ANNOTATED_ELEMENT: {
				AnnotatedElement annotatedElement = (AnnotatedElement)theEObject;
				T result = caseAnnotatedElement(annotatedElement);
				if (result == null) result = caseThingMLElement(annotatedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.STATE_MACHINE: {
				StateMachine stateMachine = (StateMachine)theEObject;
				T result = caseStateMachine(stateMachine);
				if (result == null) result = caseCompositeState(stateMachine);
				if (result == null) result = caseState(stateMachine);
				if (result == null) result = caseRegion(stateMachine);
				if (result == null) result = caseAnnotatedElement(stateMachine);
				if (result == null) result = caseThingMLElement(stateMachine);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.HANDLER: {
				Handler handler = (Handler)theEObject;
				T result = caseHandler(handler);
				if (result == null) result = caseAnnotatedElement(handler);
				if (result == null) result = caseThingMLElement(handler);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.TRANSITION: {
				Transition transition = (Transition)theEObject;
				T result = caseTransition(transition);
				if (result == null) result = caseHandler(transition);
				if (result == null) result = caseAnnotatedElement(transition);
				if (result == null) result = caseThingMLElement(transition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.INTERNAL_TRANSITION: {
				InternalTransition internalTransition = (InternalTransition)theEObject;
				T result = caseInternalTransition(internalTransition);
				if (result == null) result = caseHandler(internalTransition);
				if (result == null) result = caseAnnotatedElement(internalTransition);
				if (result == null) result = caseThingMLElement(internalTransition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.STATE: {
				State state = (State)theEObject;
				T result = caseState(state);
				if (result == null) result = caseAnnotatedElement(state);
				if (result == null) result = caseThingMLElement(state);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.COMPOSITE_STATE: {
				CompositeState compositeState = (CompositeState)theEObject;
				T result = caseCompositeState(compositeState);
				if (result == null) result = caseState(compositeState);
				if (result == null) result = caseRegion(compositeState);
				if (result == null) result = caseAnnotatedElement(compositeState);
				if (result == null) result = caseThingMLElement(compositeState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.REGION: {
				Region region = (Region)theEObject;
				T result = caseRegion(region);
				if (result == null) result = caseAnnotatedElement(region);
				if (result == null) result = caseThingMLElement(region);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PARALLEL_REGION: {
				ParallelRegion parallelRegion = (ParallelRegion)theEObject;
				T result = caseParallelRegion(parallelRegion);
				if (result == null) result = caseRegion(parallelRegion);
				if (result == null) result = caseAnnotatedElement(parallelRegion);
				if (result == null) result = caseThingMLElement(parallelRegion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.ACTION: {
				Action action = (Action)theEObject;
				T result = caseAction(action);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.ACTION_BLOCK: {
				ActionBlock actionBlock = (ActionBlock)theEObject;
				T result = caseActionBlock(actionBlock);
				if (result == null) result = caseAction(actionBlock);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.EXTERN_STATEMENT: {
				ExternStatement externStatement = (ExternStatement)theEObject;
				T result = caseExternStatement(externStatement);
				if (result == null) result = caseAction(externStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.EXPRESSION: {
				Expression expression = (Expression)theEObject;
				T result = caseExpression(expression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.EXTERN_EXPRESSION: {
				ExternExpression externExpression = (ExternExpression)theEObject;
				T result = caseExternExpression(externExpression);
				if (result == null) result = caseExpression(externExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.SEND_ACTION: {
				SendAction sendAction = (SendAction)theEObject;
				T result = caseSendAction(sendAction);
				if (result == null) result = caseAction(sendAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.VARIABLE_ASSIGNMENT: {
				VariableAssignment variableAssignment = (VariableAssignment)theEObject;
				T result = caseVariableAssignment(variableAssignment);
				if (result == null) result = caseAction(variableAssignment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.EVENT: {
				Event event = (Event)theEObject;
				T result = caseEvent(event);
				if (result == null) result = caseThingMLElement(event);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.RECEIVE_MESSAGE: {
				ReceiveMessage receiveMessage = (ReceiveMessage)theEObject;
				T result = caseReceiveMessage(receiveMessage);
				if (result == null) result = caseEvent(receiveMessage);
				if (result == null) result = caseReferencedElmt(receiveMessage);
				if (result == null) result = caseThingMLElement(receiveMessage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PORT: {
				Port port = (Port)theEObject;
				T result = casePort(port);
				if (result == null) result = caseAnnotatedElement(port);
				if (result == null) result = caseThingMLElement(port);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.REQUIRED_PORT: {
				RequiredPort requiredPort = (RequiredPort)theEObject;
				T result = caseRequiredPort(requiredPort);
				if (result == null) result = casePort(requiredPort);
				if (result == null) result = caseAnnotatedElement(requiredPort);
				if (result == null) result = caseThingMLElement(requiredPort);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PROVIDED_PORT: {
				ProvidedPort providedPort = (ProvidedPort)theEObject;
				T result = caseProvidedPort(providedPort);
				if (result == null) result = casePort(providedPort);
				if (result == null) result = caseAnnotatedElement(providedPort);
				if (result == null) result = caseThingMLElement(providedPort);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.LITERAL: {
				Literal literal = (Literal)theEObject;
				T result = caseLiteral(literal);
				if (result == null) result = caseExpression(literal);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.ENUM_LITERAL_REF: {
				EnumLiteralRef enumLiteralRef = (EnumLiteralRef)theEObject;
				T result = caseEnumLiteralRef(enumLiteralRef);
				if (result == null) result = caseLiteral(enumLiteralRef);
				if (result == null) result = caseExpression(enumLiteralRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.INTEGER_LITERAL: {
				IntegerLiteral integerLiteral = (IntegerLiteral)theEObject;
				T result = caseIntegerLiteral(integerLiteral);
				if (result == null) result = caseLiteral(integerLiteral);
				if (result == null) result = caseExpression(integerLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.BOOLEAN_LITERAL: {
				BooleanLiteral booleanLiteral = (BooleanLiteral)theEObject;
				T result = caseBooleanLiteral(booleanLiteral);
				if (result == null) result = caseLiteral(booleanLiteral);
				if (result == null) result = caseExpression(booleanLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.STRING_LITERAL: {
				StringLiteral stringLiteral = (StringLiteral)theEObject;
				T result = caseStringLiteral(stringLiteral);
				if (result == null) result = caseLiteral(stringLiteral);
				if (result == null) result = caseExpression(stringLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.DOUBLE_LITERAL: {
				DoubleLiteral doubleLiteral = (DoubleLiteral)theEObject;
				T result = caseDoubleLiteral(doubleLiteral);
				if (result == null) result = caseLiteral(doubleLiteral);
				if (result == null) result = caseExpression(doubleLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.UNARY_EXPRESSION: {
				UnaryExpression unaryExpression = (UnaryExpression)theEObject;
				T result = caseUnaryExpression(unaryExpression);
				if (result == null) result = caseExpression(unaryExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.NOT_EXPRESSION: {
				NotExpression notExpression = (NotExpression)theEObject;
				T result = caseNotExpression(notExpression);
				if (result == null) result = caseUnaryExpression(notExpression);
				if (result == null) result = caseExpression(notExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.UNARY_MINUS: {
				UnaryMinus unaryMinus = (UnaryMinus)theEObject;
				T result = caseUnaryMinus(unaryMinus);
				if (result == null) result = caseUnaryExpression(unaryMinus);
				if (result == null) result = caseExpression(unaryMinus);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.BINARY_EXPRESSION: {
				BinaryExpression binaryExpression = (BinaryExpression)theEObject;
				T result = caseBinaryExpression(binaryExpression);
				if (result == null) result = caseExpression(binaryExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PLUS_EXPRESSION: {
				PlusExpression plusExpression = (PlusExpression)theEObject;
				T result = casePlusExpression(plusExpression);
				if (result == null) result = caseBinaryExpression(plusExpression);
				if (result == null) result = caseExpression(plusExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.MINUS_EXPRESSION: {
				MinusExpression minusExpression = (MinusExpression)theEObject;
				T result = caseMinusExpression(minusExpression);
				if (result == null) result = caseBinaryExpression(minusExpression);
				if (result == null) result = caseExpression(minusExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.TIMES_EXPRESSION: {
				TimesExpression timesExpression = (TimesExpression)theEObject;
				T result = caseTimesExpression(timesExpression);
				if (result == null) result = caseBinaryExpression(timesExpression);
				if (result == null) result = caseExpression(timesExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.DIV_EXPRESSION: {
				DivExpression divExpression = (DivExpression)theEObject;
				T result = caseDivExpression(divExpression);
				if (result == null) result = caseBinaryExpression(divExpression);
				if (result == null) result = caseExpression(divExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.MOD_EXPRESSION: {
				ModExpression modExpression = (ModExpression)theEObject;
				T result = caseModExpression(modExpression);
				if (result == null) result = caseBinaryExpression(modExpression);
				if (result == null) result = caseExpression(modExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.EQUALS_EXPRESSION: {
				EqualsExpression equalsExpression = (EqualsExpression)theEObject;
				T result = caseEqualsExpression(equalsExpression);
				if (result == null) result = caseBinaryExpression(equalsExpression);
				if (result == null) result = caseExpression(equalsExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.GREATER_EXPRESSION: {
				GreaterExpression greaterExpression = (GreaterExpression)theEObject;
				T result = caseGreaterExpression(greaterExpression);
				if (result == null) result = caseBinaryExpression(greaterExpression);
				if (result == null) result = caseExpression(greaterExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.LOWER_EXPRESSION: {
				LowerExpression lowerExpression = (LowerExpression)theEObject;
				T result = caseLowerExpression(lowerExpression);
				if (result == null) result = caseBinaryExpression(lowerExpression);
				if (result == null) result = caseExpression(lowerExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.AND_EXPRESSION: {
				AndExpression andExpression = (AndExpression)theEObject;
				T result = caseAndExpression(andExpression);
				if (result == null) result = caseBinaryExpression(andExpression);
				if (result == null) result = caseExpression(andExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.OR_EXPRESSION: {
				OrExpression orExpression = (OrExpression)theEObject;
				T result = caseOrExpression(orExpression);
				if (result == null) result = caseBinaryExpression(orExpression);
				if (result == null) result = caseExpression(orExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.CONTROL_STRUCTURE: {
				ControlStructure controlStructure = (ControlStructure)theEObject;
				T result = caseControlStructure(controlStructure);
				if (result == null) result = caseAction(controlStructure);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.LOOP_ACTION: {
				LoopAction loopAction = (LoopAction)theEObject;
				T result = caseLoopAction(loopAction);
				if (result == null) result = caseControlStructure(loopAction);
				if (result == null) result = caseAction(loopAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.CONDITIONAL_ACTION: {
				ConditionalAction conditionalAction = (ConditionalAction)theEObject;
				T result = caseConditionalAction(conditionalAction);
				if (result == null) result = caseControlStructure(conditionalAction);
				if (result == null) result = caseAction(conditionalAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PROPERTY_REFERENCE: {
				PropertyReference propertyReference = (PropertyReference)theEObject;
				T result = casePropertyReference(propertyReference);
				if (result == null) result = caseExpression(propertyReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.ARRAY_INDEX: {
				ArrayIndex arrayIndex = (ArrayIndex)theEObject;
				T result = caseArrayIndex(arrayIndex);
				if (result == null) result = caseExpression(arrayIndex);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.EXPRESSION_GROUP: {
				ExpressionGroup expressionGroup = (ExpressionGroup)theEObject;
				T result = caseExpressionGroup(expressionGroup);
				if (result == null) result = caseExpression(expressionGroup);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.RETURN_ACTION: {
				ReturnAction returnAction = (ReturnAction)theEObject;
				T result = caseReturnAction(returnAction);
				if (result == null) result = caseAction(returnAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PRINT_ACTION: {
				PrintAction printAction = (PrintAction)theEObject;
				T result = casePrintAction(printAction);
				if (result == null) result = caseAction(printAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.ERROR_ACTION: {
				ErrorAction errorAction = (ErrorAction)theEObject;
				T result = caseErrorAction(errorAction);
				if (result == null) result = caseAction(errorAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.CONFIGURATION: {
				Configuration configuration = (Configuration)theEObject;
				T result = caseConfiguration(configuration);
				if (result == null) result = caseAnnotatedElement(configuration);
				if (result == null) result = caseThingMLElement(configuration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.INSTANCE: {
				Instance instance = (Instance)theEObject;
				T result = caseInstance(instance);
				if (result == null) result = caseAnnotatedElement(instance);
				if (result == null) result = caseThingMLElement(instance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.CONNECTOR: {
				Connector connector = (Connector)theEObject;
				T result = caseConnector(connector);
				if (result == null) result = caseAbstractConnector(connector);
				if (result == null) result = caseAnnotatedElement(connector);
				if (result == null) result = caseThingMLElement(connector);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.EXTERNAL_CONNECTOR: {
				ExternalConnector externalConnector = (ExternalConnector)theEObject;
				T result = caseExternalConnector(externalConnector);
				if (result == null) result = caseAbstractConnector(externalConnector);
				if (result == null) result = caseAnnotatedElement(externalConnector);
				if (result == null) result = caseThingMLElement(externalConnector);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.ABSTRACT_CONNECTOR: {
				AbstractConnector abstractConnector = (AbstractConnector)theEObject;
				T result = caseAbstractConnector(abstractConnector);
				if (result == null) result = caseAnnotatedElement(abstractConnector);
				if (result == null) result = caseThingMLElement(abstractConnector);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.CONFIG_PROPERTY_ASSIGN: {
				ConfigPropertyAssign configPropertyAssign = (ConfigPropertyAssign)theEObject;
				T result = caseConfigPropertyAssign(configPropertyAssign);
				if (result == null) result = caseAnnotatedElement(configPropertyAssign);
				if (result == null) result = caseThingMLElement(configPropertyAssign);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.CONFIG_INCLUDE: {
				ConfigInclude configInclude = (ConfigInclude)theEObject;
				T result = caseConfigInclude(configInclude);
				if (result == null) result = caseAnnotatedElement(configInclude);
				if (result == null) result = caseThingMLElement(configInclude);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.INSTANCE_REF: {
				InstanceRef instanceRef = (InstanceRef)theEObject;
				T result = caseInstanceRef(instanceRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.FUNCTION_CALL: {
				FunctionCall functionCall = (FunctionCall)theEObject;
				T result = caseFunctionCall(functionCall);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.FUNCTION_CALL_STATEMENT: {
				FunctionCallStatement functionCallStatement = (FunctionCallStatement)theEObject;
				T result = caseFunctionCallStatement(functionCallStatement);
				if (result == null) result = caseAction(functionCallStatement);
				if (result == null) result = caseFunctionCall(functionCallStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.FUNCTION_CALL_EXPRESSION: {
				FunctionCallExpression functionCallExpression = (FunctionCallExpression)theEObject;
				T result = caseFunctionCallExpression(functionCallExpression);
				if (result == null) result = caseFunctionCall(functionCallExpression);
				if (result == null) result = caseExpression(functionCallExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.LOCAL_VARIABLE: {
				LocalVariable localVariable = (LocalVariable)theEObject;
				T result = caseLocalVariable(localVariable);
				if (result == null) result = caseVariable(localVariable);
				if (result == null) result = caseAction(localVariable);
				if (result == null) result = caseTypedElement(localVariable);
				if (result == null) result = caseAnnotatedElement(localVariable);
				if (result == null) result = caseReferencedElmt(localVariable);
				if (result == null) result = caseThingMLElement(localVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.STREAM: {
				Stream stream = (Stream)theEObject;
				T result = caseStream(stream);
				if (result == null) result = caseAnnotatedElement(stream);
				if (result == null) result = caseThingMLElement(stream);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.STREAM_EXPRESSION: {
				StreamExpression streamExpression = (StreamExpression)theEObject;
				T result = caseStreamExpression(streamExpression);
				if (result == null) result = caseThingMLElement(streamExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.STREAM_PARAM_REFERENCE: {
				StreamParamReference streamParamReference = (StreamParamReference)theEObject;
				T result = caseStreamParamReference(streamParamReference);
				if (result == null) result = caseExpression(streamParamReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.STREAM_OUTPUT: {
				StreamOutput streamOutput = (StreamOutput)theEObject;
				T result = caseStreamOutput(streamOutput);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.SOURCE: {
				Source source = (Source)theEObject;
				T result = caseSource(source);
				if (result == null) result = caseThingMLElement(source);
				if (result == null) result = caseReferencedElmt(source);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.SOURCE_COMPOSITION: {
				SourceComposition sourceComposition = (SourceComposition)theEObject;
				T result = caseSourceComposition(sourceComposition);
				if (result == null) result = caseSource(sourceComposition);
				if (result == null) result = caseThingMLElement(sourceComposition);
				if (result == null) result = caseReferencedElmt(sourceComposition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.JOIN_SOURCES: {
				JoinSources joinSources = (JoinSources)theEObject;
				T result = caseJoinSources(joinSources);
				if (result == null) result = caseSourceComposition(joinSources);
				if (result == null) result = caseSource(joinSources);
				if (result == null) result = caseThingMLElement(joinSources);
				if (result == null) result = caseReferencedElmt(joinSources);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.MERGE_SOURCES: {
				MergeSources mergeSources = (MergeSources)theEObject;
				T result = caseMergeSources(mergeSources);
				if (result == null) result = caseSourceComposition(mergeSources);
				if (result == null) result = caseSource(mergeSources);
				if (result == null) result = caseThingMLElement(mergeSources);
				if (result == null) result = caseReferencedElmt(mergeSources);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.SIMPLE_SOURCE: {
				SimpleSource simpleSource = (SimpleSource)theEObject;
				T result = caseSimpleSource(simpleSource);
				if (result == null) result = caseSource(simpleSource);
				if (result == null) result = caseThingMLElement(simpleSource);
				if (result == null) result = caseReferencedElmt(simpleSource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.VIEW_SOURCE: {
				ViewSource viewSource = (ViewSource)theEObject;
				T result = caseViewSource(viewSource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.FILTER: {
				Filter filter = (Filter)theEObject;
				T result = caseFilter(filter);
				if (result == null) result = caseViewSource(filter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.OPERATOR: {
				Operator operator = (Operator)theEObject;
				T result = caseOperator(operator);
				if (result == null) result = caseTypedElement(operator);
				if (result == null) result = caseThingMLElement(operator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.MESSAGE_PARAMETER: {
				MessageParameter messageParameter = (MessageParameter)theEObject;
				T result = caseMessageParameter(messageParameter);
				if (result == null) result = caseThingMLElement(messageParameter);
				if (result == null) result = caseReferencedElmt(messageParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL: {
				SglMsgParamOperatorCall sglMsgParamOperatorCall = (SglMsgParamOperatorCall)theEObject;
				T result = caseSglMsgParamOperatorCall(sglMsgParamOperatorCall);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.REFERENCE: {
				Reference reference = (Reference)theEObject;
				T result = caseReference(reference);
				if (result == null) result = caseExpression(reference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.REFERENCED_ELMT: {
				ReferencedElmt referencedElmt = (ReferencedElmt)theEObject;
				T result = caseReferencedElmt(referencedElmt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.SGL_MSG_PARAM_OPERATOR: {
				SglMsgParamOperator sglMsgParamOperator = (SglMsgParamOperator)theEObject;
				T result = caseSglMsgParamOperator(sglMsgParamOperator);
				if (result == null) result = caseOperator(sglMsgParamOperator);
				if (result == null) result = caseTypedElement(sglMsgParamOperator);
				if (result == null) result = caseThingMLElement(sglMsgParamOperator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.LENGTH_WINDOW: {
				LengthWindow lengthWindow = (LengthWindow)theEObject;
				T result = caseLengthWindow(lengthWindow);
				if (result == null) result = caseWindowView(lengthWindow);
				if (result == null) result = caseViewSource(lengthWindow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.TIME_WINDOW: {
				TimeWindow timeWindow = (TimeWindow)theEObject;
				T result = caseTimeWindow(timeWindow);
				if (result == null) result = caseWindowView(timeWindow);
				if (result == null) result = caseViewSource(timeWindow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PARAM_REFERENCE: {
				ParamReference paramReference = (ParamReference)theEObject;
				T result = caseParamReference(paramReference);
				if (result == null) result = caseElmtProperty(paramReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.SIMPLE_PARAM_REF: {
				SimpleParamRef simpleParamRef = (SimpleParamRef)theEObject;
				T result = caseSimpleParamRef(simpleParamRef);
				if (result == null) result = caseParamReference(simpleParamRef);
				if (result == null) result = caseElmtProperty(simpleParamRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.ARRAY_PARAM_REF: {
				ArrayParamRef arrayParamRef = (ArrayParamRef)theEObject;
				T result = caseArrayParamRef(arrayParamRef);
				if (result == null) result = caseParamReference(arrayParamRef);
				if (result == null) result = caseElmtProperty(arrayParamRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.ELMT_PROPERTY: {
				ElmtProperty elmtProperty = (ElmtProperty)theEObject;
				T result = caseElmtProperty(elmtProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.PREDIFINED_PROPERTY: {
				PredifinedProperty predifinedProperty = (PredifinedProperty)theEObject;
				T result = casePredifinedProperty(predifinedProperty);
				if (result == null) result = caseElmtProperty(predifinedProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.LENGTH_ARRAY: {
				LengthArray lengthArray = (LengthArray)theEObject;
				T result = caseLengthArray(lengthArray);
				if (result == null) result = casePredifinedProperty(lengthArray);
				if (result == null) result = caseElmtProperty(lengthArray);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ThingmlPackage.WINDOW_VIEW: {
				WindowView windowView = (WindowView)theEObject;
				T result = caseWindowView(windowView);
				if (result == null) result = caseViewSource(windowView);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Thing ML Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Thing ML Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseThingMLModel(ThingMLModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunction(Function object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Message</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Message</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMessage(Message object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Thing</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Thing</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseThing(Thing object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameter(Parameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariable(Variable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Thing ML Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Thing ML Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseThingMLElement(ThingMLElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseType(Type object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Typed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Typed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypedElement(TypedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProperty(Property object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Assign</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Assign</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyAssign(PropertyAssign object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Platform Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Platform Annotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlatformAnnotation(PlatformAnnotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enumeration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enumeration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumeration(Enumeration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Primitive Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Primitive Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrimitiveType(PrimitiveType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enumeration Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enumeration Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumerationLiteral(EnumerationLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Annotated Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Annotated Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnnotatedElement(AnnotatedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Machine</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateMachine(StateMachine object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Handler</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Handler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHandler(Handler object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransition(Transition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Internal Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Internal Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInternalTransition(InternalTransition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseState(State object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composite State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composite State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositeState(CompositeState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Region</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Region</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRegion(Region object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parallel Region</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parallel Region</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParallelRegion(ParallelRegion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAction(Action object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionBlock(ActionBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Extern Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Extern Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExternStatement(ExternStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpression(Expression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Extern Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Extern Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExternExpression(ExternExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Send Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Send Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSendAction(SendAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable Assignment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariableAssignment(VariableAssignment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEvent(Event object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Receive Message</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Receive Message</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReceiveMessage(ReceiveMessage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Port</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Port</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePort(Port object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Required Port</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Required Port</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRequiredPort(RequiredPort object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Provided Port</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Provided Port</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProvidedPort(ProvidedPort object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteral(Literal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Literal Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Literal Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumLiteralRef(EnumLiteralRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerLiteral(IntegerLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanLiteral(BooleanLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringLiteral(StringLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Double Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Double Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDoubleLiteral(DoubleLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnaryExpression(UnaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Not Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Not Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNotExpression(NotExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unary Minus</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unary Minus</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnaryMinus(UnaryMinus object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Binary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBinaryExpression(BinaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Plus Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Plus Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlusExpression(PlusExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Minus Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Minus Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMinusExpression(MinusExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Times Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Times Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTimesExpression(TimesExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Div Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Div Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDivExpression(DivExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mod Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mod Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModExpression(ModExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Equals Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Equals Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEqualsExpression(EqualsExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Greater Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Greater Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGreaterExpression(GreaterExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Lower Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Lower Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLowerExpression(LowerExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>And Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>And Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAndExpression(AndExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Or Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Or Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOrExpression(OrExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Control Structure</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Control Structure</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseControlStructure(ControlStructure object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Loop Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Loop Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLoopAction(LoopAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Conditional Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Conditional Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConditionalAction(ConditionalAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyReference(PropertyReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Array Index</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Array Index</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArrayIndex(ArrayIndex object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpressionGroup(ExpressionGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Return Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Return Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReturnAction(ReturnAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Print Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Print Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrintAction(PrintAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorAction(ErrorAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfiguration(Configuration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInstance(Instance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connector</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connector</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnector(Connector object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>External Connector</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>External Connector</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExternalConnector(ExternalConnector object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Connector</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Connector</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractConnector(AbstractConnector object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Config Property Assign</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Config Property Assign</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfigPropertyAssign(ConfigPropertyAssign object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Config Include</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Config Include</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfigInclude(ConfigInclude object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Instance Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Instance Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInstanceRef(InstanceRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Call</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Call</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionCall(FunctionCall object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Call Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Call Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionCallStatement(FunctionCallStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Call Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Call Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionCallExpression(FunctionCallExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Local Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Local Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocalVariable(LocalVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stream</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stream</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStream(Stream object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stream Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stream Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStreamExpression(StreamExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stream Param Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stream Param Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStreamParamReference(StreamParamReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stream Output</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stream Output</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStreamOutput(StreamOutput object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSource(Source object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Source Composition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Source Composition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSourceComposition(SourceComposition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Join Sources</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Join Sources</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJoinSources(JoinSources object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merge Sources</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merge Sources</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergeSources(MergeSources object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleSource(SimpleSource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>View Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>View Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseViewSource(ViewSource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFilter(Filter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOperator(Operator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Message Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Message Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMessageParameter(MessageParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sgl Msg Param Operator Call</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sgl Msg Param Operator Call</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSglMsgParamOperatorCall(SglMsgParamOperatorCall object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReference(Reference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Referenced Elmt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Referenced Elmt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReferencedElmt(ReferencedElmt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sgl Msg Param Operator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sgl Msg Param Operator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSglMsgParamOperator(SglMsgParamOperator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Length Window</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Length Window</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLengthWindow(LengthWindow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Time Window</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Time Window</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTimeWindow(TimeWindow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Param Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Param Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParamReference(ParamReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Param Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Param Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleParamRef(SimpleParamRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Array Param Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Array Param Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArrayParamRef(ArrayParamRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Elmt Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Elmt Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElmtProperty(ElmtProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Predifined Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Predifined Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePredifinedProperty(PredifinedProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Length Array</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Length Array</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLengthArray(LengthArray object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Window View</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Window View</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWindowView(WindowView object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ThingmlSwitch
