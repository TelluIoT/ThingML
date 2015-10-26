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
			ThingmlFactory theThingmlFactory = (ThingmlFactory)EPackage.Registry.INSTANCE.getEFactory(ThingmlPackage.eNS_URI);
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
			case ThingmlPackage.FUNCTION: return createFunction();
			case ThingmlPackage.MESSAGE: return createMessage();
			case ThingmlPackage.THING: return createThing();
			case ThingmlPackage.PARAMETER: return createParameter();
			case ThingmlPackage.PROPERTY: return createProperty();
			case ThingmlPackage.PROPERTY_ASSIGN: return createPropertyAssign();
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
			case ThingmlPackage.VARIABLE_ASSIGNMENT: return createVariableAssignment();
			case ThingmlPackage.RECEIVE_MESSAGE: return createReceiveMessage();
			case ThingmlPackage.REQUIRED_PORT: return createRequiredPort();
			case ThingmlPackage.PROVIDED_PORT: return createProvidedPort();
			case ThingmlPackage.ENUM_LITERAL_REF: return createEnumLiteralRef();
			case ThingmlPackage.INTEGER_LITERAL: return createIntegerLiteral();
			case ThingmlPackage.BOOLEAN_LITERAL: return createBooleanLiteral();
			case ThingmlPackage.STRING_LITERAL: return createStringLiteral();
			case ThingmlPackage.DOUBLE_LITERAL: return createDoubleLiteral();
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
			case ThingmlPackage.ARRAY_INDEX: return createArrayIndex();
			case ThingmlPackage.EXPRESSION_GROUP: return createExpressionGroup();
			case ThingmlPackage.RETURN_ACTION: return createReturnAction();
			case ThingmlPackage.PRINT_ACTION: return createPrintAction();
			case ThingmlPackage.ERROR_ACTION: return createErrorAction();
			case ThingmlPackage.CONFIGURATION: return createConfiguration();
			case ThingmlPackage.INSTANCE: return createInstance();
			case ThingmlPackage.CONNECTOR: return createConnector();
			case ThingmlPackage.EXTERNAL_CONNECTOR: return createExternalConnector();
			case ThingmlPackage.CONFIG_PROPERTY_ASSIGN: return createConfigPropertyAssign();
			case ThingmlPackage.CONFIG_INCLUDE: return createConfigInclude();
			case ThingmlPackage.INSTANCE_REF: return createInstanceRef();
			case ThingmlPackage.FUNCTION_CALL_STATEMENT: return createFunctionCallStatement();
			case ThingmlPackage.FUNCTION_CALL_EXPRESSION: return createFunctionCallExpression();
			case ThingmlPackage.LOCAL_VARIABLE: return createLocalVariable();
			case ThingmlPackage.STREAM: return createStream();
			case ThingmlPackage.STREAM_EXPRESSION: return createStreamExpression();
			case ThingmlPackage.STREAM_PARAM_REFERENCE: return createStreamParamReference();
			case ThingmlPackage.STREAM_OUTPUT: return createStreamOutput();
			case ThingmlPackage.JOIN_SOURCES: return createJoinSources();
			case ThingmlPackage.MERGE_SOURCES: return createMergeSources();
			case ThingmlPackage.SIMPLE_SOURCE: return createSimpleSource();
			case ThingmlPackage.FILTER: return createFilter();
			case ThingmlPackage.MESSAGE_PARAMETER: return createMessageParameter();
			case ThingmlPackage.SGL_MSG_PARAM_OPERATOR_CALL: return createSglMsgParamOperatorCall();
			case ThingmlPackage.REFERENCE: return createReference();
			case ThingmlPackage.SGL_MSG_PARAM_OPERATOR: return createSglMsgParamOperator();
			case ThingmlPackage.LENGTH_WINDOW: return createLengthWindow();
			case ThingmlPackage.TIME_WINDOW: return createTimeWindow();
			case ThingmlPackage.SIMPLE_PARAM_REF: return createSimpleParamRef();
			case ThingmlPackage.ARRAY_PARAM_REF: return createArrayParamRef();
			case ThingmlPackage.LENGTH_ARRAY: return createLengthArray();
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
	public Function createFunction() {
		FunctionImpl function = new FunctionImpl();
		return function;
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
	public PropertyAssign createPropertyAssign() {
		PropertyAssignImpl propertyAssign = new PropertyAssignImpl();
		return propertyAssign;
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
	public VariableAssignment createVariableAssignment() {
		VariableAssignmentImpl variableAssignment = new VariableAssignmentImpl();
		return variableAssignment;
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
	public RequiredPort createRequiredPort() {
		RequiredPortImpl requiredPort = new RequiredPortImpl();
		return requiredPort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProvidedPort createProvidedPort() {
		ProvidedPortImpl providedPort = new ProvidedPortImpl();
		return providedPort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumLiteralRef createEnumLiteralRef() {
		EnumLiteralRefImpl enumLiteralRef = new EnumLiteralRefImpl();
		return enumLiteralRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerLiteral createIntegerLiteral() {
		IntegerLiteralImpl integerLiteral = new IntegerLiteralImpl();
		return integerLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanLiteral createBooleanLiteral() {
		BooleanLiteralImpl booleanLiteral = new BooleanLiteralImpl();
		return booleanLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringLiteral createStringLiteral() {
		StringLiteralImpl stringLiteral = new StringLiteralImpl();
		return stringLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DoubleLiteral createDoubleLiteral() {
		DoubleLiteralImpl doubleLiteral = new DoubleLiteralImpl();
		return doubleLiteral;
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
	public ArrayIndex createArrayIndex() {
		ArrayIndexImpl arrayIndex = new ArrayIndexImpl();
		return arrayIndex;
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
	public ReturnAction createReturnAction() {
		ReturnActionImpl returnAction = new ReturnActionImpl();
		return returnAction;
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
	public Configuration createConfiguration() {
		ConfigurationImpl configuration = new ConfigurationImpl();
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Instance createInstance() {
		InstanceImpl instance = new InstanceImpl();
		return instance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Connector createConnector() {
		ConnectorImpl connector = new ConnectorImpl();
		return connector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExternalConnector createExternalConnector() {
		ExternalConnectorImpl externalConnector = new ExternalConnectorImpl();
		return externalConnector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigPropertyAssign createConfigPropertyAssign() {
		ConfigPropertyAssignImpl configPropertyAssign = new ConfigPropertyAssignImpl();
		return configPropertyAssign;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigInclude createConfigInclude() {
		ConfigIncludeImpl configInclude = new ConfigIncludeImpl();
		return configInclude;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstanceRef createInstanceRef() {
		InstanceRefImpl instanceRef = new InstanceRefImpl();
		return instanceRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionCallStatement createFunctionCallStatement() {
		FunctionCallStatementImpl functionCallStatement = new FunctionCallStatementImpl();
		return functionCallStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionCallExpression createFunctionCallExpression() {
		FunctionCallExpressionImpl functionCallExpression = new FunctionCallExpressionImpl();
		return functionCallExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalVariable createLocalVariable() {
		LocalVariableImpl localVariable = new LocalVariableImpl();
		return localVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Stream createStream() {
		StreamImpl stream = new StreamImpl();
		return stream;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamExpression createStreamExpression() {
		StreamExpressionImpl streamExpression = new StreamExpressionImpl();
		return streamExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamParamReference createStreamParamReference() {
		StreamParamReferenceImpl streamParamReference = new StreamParamReferenceImpl();
		return streamParamReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamOutput createStreamOutput() {
		StreamOutputImpl streamOutput = new StreamOutputImpl();
		return streamOutput;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JoinSources createJoinSources() {
		JoinSourcesImpl joinSources = new JoinSourcesImpl();
		return joinSources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeSources createMergeSources() {
		MergeSourcesImpl mergeSources = new MergeSourcesImpl();
		return mergeSources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleSource createSimpleSource() {
		SimpleSourceImpl simpleSource = new SimpleSourceImpl();
		return simpleSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Filter createFilter() {
		FilterImpl filter = new FilterImpl();
		return filter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MessageParameter createMessageParameter() {
		MessageParameterImpl messageParameter = new MessageParameterImpl();
		return messageParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SglMsgParamOperatorCall createSglMsgParamOperatorCall() {
		SglMsgParamOperatorCallImpl sglMsgParamOperatorCall = new SglMsgParamOperatorCallImpl();
		return sglMsgParamOperatorCall;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Reference createReference() {
		ReferenceImpl reference = new ReferenceImpl();
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SglMsgParamOperator createSglMsgParamOperator() {
		SglMsgParamOperatorImpl sglMsgParamOperator = new SglMsgParamOperatorImpl();
		return sglMsgParamOperator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LengthWindow createLengthWindow() {
		LengthWindowImpl lengthWindow = new LengthWindowImpl();
		return lengthWindow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeWindow createTimeWindow() {
		TimeWindowImpl timeWindow = new TimeWindowImpl();
		return timeWindow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleParamRef createSimpleParamRef() {
		SimpleParamRefImpl simpleParamRef = new SimpleParamRefImpl();
		return simpleParamRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayParamRef createArrayParamRef() {
		ArrayParamRefImpl arrayParamRef = new ArrayParamRefImpl();
		return arrayParamRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LengthArray createLengthArray() {
		LengthArrayImpl lengthArray = new LengthArrayImpl();
		return lengthArray;
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
