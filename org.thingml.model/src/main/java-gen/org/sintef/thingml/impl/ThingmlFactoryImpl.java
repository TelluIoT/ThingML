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
package org.sintef.thingml.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.sintef.thingml.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ThingmlFactoryImpl extends EFactoryImpl implements ThingmlFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ThingmlFactory init() {
		try {
			ThingmlFactory theThingmlFactory = (ThingmlFactory)EPackage.Registry.INSTANCE.getEFactory("http://thingml"); 
			if (theThingmlFactory != null) {
				return theThingmlFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ThingmlFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThingmlFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ThingmlPackage.THING_ML_MODEL: return createThingMLModel();
			case ThingmlPackage.MESSAGE: return createMessage();
			case ThingmlPackage.THING: return createThing();
			case ThingmlPackage.PARAMETER: return createParameter();
			case ThingmlPackage.PROPERTY: return createProperty();
			case ThingmlPackage.PLATFORM_ANNOTATION: return createPlatformAnnotation();
			case ThingmlPackage.ENUMERATION: return createEnumeration();
			case ThingmlPackage.PRIMITIVE_TYPE: return createPrimitiveType();
			case ThingmlPackage.ENUMERATION_LITERAL: return createEnumerationLiteral();
			case ThingmlPackage.STATE_MACHINE: return createStateMachine();
			case ThingmlPackage.TRANSITION: return createTransition();
			case ThingmlPackage.INTERNAL_TRANSITION: return createInternalTransition();
			case ThingmlPackage.STATE: return createState();
			case ThingmlPackage.COMPOSITE_STATE: return createCompositeState();
			case ThingmlPackage.PARALLEL_REGION: return createParallelRegion();
			case ThingmlPackage.ACTION_BLOCK: return createActionBlock();
			case ThingmlPackage.EXTERN_STATEMENT: return createExternStatement();
			case ThingmlPackage.EXTERN_EXPRESSION: return createExternExpression();
			case ThingmlPackage.SEND_ACTION: return createSendAction();
			case ThingmlPackage.PROPERTY_ASSIGNMENT: return createPropertyAssignment();
			case ThingmlPackage.RECEIVE_MESSAGE: return createReceiveMessage();
			case ThingmlPackage.DICTIONARY: return createDictionary();
			case ThingmlPackage.PORT: return createPort();
			case ThingmlPackage.EVENT_REFERENCE: return createEventReference();
			case ThingmlPackage.INTEGER_LITTERAL: return createIntegerLitteral();
			case ThingmlPackage.BOOLEAN_LITTERAL: return createBooleanLitteral();
			case ThingmlPackage.STRING_LITTERAL: return createStringLitteral();
			case ThingmlPackage.DOUBLE_LITTERAL: return createDoubleLitteral();
			case ThingmlPackage.NOT_EXPRESSION: return createNotExpression();
			case ThingmlPackage.UNARY_MINUS: return createUnaryMinus();
			case ThingmlPackage.PLUS_EXPRESSION: return createPlusExpression();
			case ThingmlPackage.MINUS_EXPRESSION: return createMinusExpression();
			case ThingmlPackage.TIMES_EXPRESSION: return createTimesExpression();
			case ThingmlPackage.DIV_EXPRESSION: return createDivExpression();
			case ThingmlPackage.MOD_EXPRESSION: return createModExpression();
			case ThingmlPackage.EQUALS_EXPRESSION: return createEqualsExpression();
			case ThingmlPackage.GREATER_EXPRESSION: return createGreaterExpression();
			case ThingmlPackage.LOWER_EXPRESSION: return createLowerExpression();
			case ThingmlPackage.AND_EXPRESSION: return createAndExpression();
			case ThingmlPackage.OR_EXPRESSION: return createOrExpression();
			case ThingmlPackage.LOOP_ACTION: return createLoopAction();
			case ThingmlPackage.CONDITIONAL_ACTION: return createConditionalAction();
			case ThingmlPackage.PROPERTY_REFERENCE: return createPropertyReference();
			case ThingmlPackage.DICTIONARY_REFERENCE: return createDictionaryReference();
			case ThingmlPackage.EXPRESSION_GROUP: return createExpressionGroup();
			case ThingmlPackage.PRINT_ACTION: return createPrintAction();
			case ThingmlPackage.ERROR_ACTION: return createErrorAction();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThingMLModel createThingMLModel() {
		ThingMLModelImpl thingMLModel = new ThingMLModelImpl();
		return thingMLModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message createMessage() {
		MessageImpl message = new MessageImpl();
		return message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Thing createThing() {
		ThingImpl thing = new ThingImpl();
		return thing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlatformAnnotation createPlatformAnnotation() {
		PlatformAnnotationImpl platformAnnotation = new PlatformAnnotationImpl();
		return platformAnnotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumeration createEnumeration() {
		EnumerationImpl enumeration = new EnumerationImpl();
		return enumeration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrimitiveType createPrimitiveType() {
		PrimitiveTypeImpl primitiveType = new PrimitiveTypeImpl();
		return primitiveType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumerationLiteral createEnumerationLiteral() {
		EnumerationLiteralImpl enumerationLiteral = new EnumerationLiteralImpl();
		return enumerationLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachine createStateMachine() {
		StateMachineImpl stateMachine = new StateMachineImpl();
		return stateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition createTransition() {
		TransitionImpl transition = new TransitionImpl();
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InternalTransition createInternalTransition() {
		InternalTransitionImpl internalTransition = new InternalTransitionImpl();
		return internalTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State createState() {
		StateImpl state = new StateImpl();
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeState createCompositeState() {
		CompositeStateImpl compositeState = new CompositeStateImpl();
		return compositeState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParallelRegion createParallelRegion() {
		ParallelRegionImpl parallelRegion = new ParallelRegionImpl();
		return parallelRegion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionBlock createActionBlock() {
		ActionBlockImpl actionBlock = new ActionBlockImpl();
		return actionBlock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExternStatement createExternStatement() {
		ExternStatementImpl externStatement = new ExternStatementImpl();
		return externStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExternExpression createExternExpression() {
		ExternExpressionImpl externExpression = new ExternExpressionImpl();
		return externExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SendAction createSendAction() {
		SendActionImpl sendAction = new SendActionImpl();
		return sendAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyAssignment createPropertyAssignment() {
		PropertyAssignmentImpl propertyAssignment = new PropertyAssignmentImpl();
		return propertyAssignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReceiveMessage createReceiveMessage() {
		ReceiveMessageImpl receiveMessage = new ReceiveMessageImpl();
		return receiveMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dictionary createDictionary() {
		DictionaryImpl dictionary = new DictionaryImpl();
		return dictionary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port createPort() {
		PortImpl port = new PortImpl();
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventReference createEventReference() {
		EventReferenceImpl eventReference = new EventReferenceImpl();
		return eventReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerLitteral createIntegerLitteral() {
		IntegerLitteralImpl integerLitteral = new IntegerLitteralImpl();
		return integerLitteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanLitteral createBooleanLitteral() {
		BooleanLitteralImpl booleanLitteral = new BooleanLitteralImpl();
		return booleanLitteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringLitteral createStringLitteral() {
		StringLitteralImpl stringLitteral = new StringLitteralImpl();
		return stringLitteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DoubleLitteral createDoubleLitteral() {
		DoubleLitteralImpl doubleLitteral = new DoubleLitteralImpl();
		return doubleLitteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotExpression createNotExpression() {
		NotExpressionImpl notExpression = new NotExpressionImpl();
		return notExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnaryMinus createUnaryMinus() {
		UnaryMinusImpl unaryMinus = new UnaryMinusImpl();
		return unaryMinus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlusExpression createPlusExpression() {
		PlusExpressionImpl plusExpression = new PlusExpressionImpl();
		return plusExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MinusExpression createMinusExpression() {
		MinusExpressionImpl minusExpression = new MinusExpressionImpl();
		return minusExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimesExpression createTimesExpression() {
		TimesExpressionImpl timesExpression = new TimesExpressionImpl();
		return timesExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DivExpression createDivExpression() {
		DivExpressionImpl divExpression = new DivExpressionImpl();
		return divExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModExpression createModExpression() {
		ModExpressionImpl modExpression = new ModExpressionImpl();
		return modExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EqualsExpression createEqualsExpression() {
		EqualsExpressionImpl equalsExpression = new EqualsExpressionImpl();
		return equalsExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GreaterExpression createGreaterExpression() {
		GreaterExpressionImpl greaterExpression = new GreaterExpressionImpl();
		return greaterExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LowerExpression createLowerExpression() {
		LowerExpressionImpl lowerExpression = new LowerExpressionImpl();
		return lowerExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AndExpression createAndExpression() {
		AndExpressionImpl andExpression = new AndExpressionImpl();
		return andExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrExpression createOrExpression() {
		OrExpressionImpl orExpression = new OrExpressionImpl();
		return orExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoopAction createLoopAction() {
		LoopActionImpl loopAction = new LoopActionImpl();
		return loopAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionalAction createConditionalAction() {
		ConditionalActionImpl conditionalAction = new ConditionalActionImpl();
		return conditionalAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyReference createPropertyReference() {
		PropertyReferenceImpl propertyReference = new PropertyReferenceImpl();
		return propertyReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DictionaryReference createDictionaryReference() {
		DictionaryReferenceImpl dictionaryReference = new DictionaryReferenceImpl();
		return dictionaryReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpressionGroup createExpressionGroup() {
		ExpressionGroupImpl expressionGroup = new ExpressionGroupImpl();
		return expressionGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrintAction createPrintAction() {
		PrintActionImpl printAction = new PrintActionImpl();
		return printAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ErrorAction createErrorAction() {
		ErrorActionImpl errorAction = new ErrorActionImpl();
		return errorAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThingmlPackage getThingmlPackage() {
		return (ThingmlPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ThingmlPackage getPackage() {
		return ThingmlPackage.eINSTANCE;
	}

} //ThingmlFactoryImpl
