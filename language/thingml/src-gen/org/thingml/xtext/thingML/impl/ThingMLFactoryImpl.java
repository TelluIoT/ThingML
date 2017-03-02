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
package org.thingml.xtext.thingML.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.thingml.xtext.thingML.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ThingMLFactoryImpl extends EFactoryImpl implements ThingMLFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ThingMLFactory init()
  {
    try
    {
      ThingMLFactory theThingMLFactory = (ThingMLFactory)EPackage.Registry.INSTANCE.getEFactory(ThingMLPackage.eNS_URI);
      if (theThingMLFactory != null)
      {
        return theThingMLFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new ThingMLFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ThingMLFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case ThingMLPackage.THING_ML_MODEL: return createThingMLModel();
      case ThingMLPackage.PLATFORM_ANNOTATION: return createPlatformAnnotation();
      case ThingMLPackage.NAMED_ELEMENT: return createNamedElement();
      case ThingMLPackage.ANNOTATED_ELEMENT: return createAnnotatedElement();
      case ThingMLPackage.VARIABLE: return createVariable();
      case ThingMLPackage.TYPE_REF: return createTypeRef();
      case ThingMLPackage.TYPE: return createType();
      case ThingMLPackage.PRIMITIVE_TYPE: return createPrimitiveType();
      case ThingMLPackage.OBJECT_TYPE: return createObjectType();
      case ThingMLPackage.ENUMERATION: return createEnumeration();
      case ThingMLPackage.ENUMERATION_LITERAL: return createEnumerationLiteral();
      case ThingMLPackage.THING: return createThing();
      case ThingMLPackage.PROPERTY_ASSIGN: return createPropertyAssign();
      case ThingMLPackage.PROTOCOL: return createProtocol();
      case ThingMLPackage.FUNCTION: return createFunction();
      case ThingMLPackage.PROPERTY: return createProperty();
      case ThingMLPackage.MESSAGE: return createMessage();
      case ThingMLPackage.PARAMETER: return createParameter();
      case ThingMLPackage.PORT: return createPort();
      case ThingMLPackage.REQUIRED_PORT: return createRequiredPort();
      case ThingMLPackage.PROVIDED_PORT: return createProvidedPort();
      case ThingMLPackage.INTERNAL_PORT: return createInternalPort();
      case ThingMLPackage.STATE: return createState();
      case ThingMLPackage.HANDLER: return createHandler();
      case ThingMLPackage.TRANSITION: return createTransition();
      case ThingMLPackage.INTERNAL_TRANSITION: return createInternalTransition();
      case ThingMLPackage.COMPOSITE_STATE: return createCompositeState();
      case ThingMLPackage.SESSION: return createSession();
      case ThingMLPackage.REGION: return createRegion();
      case ThingMLPackage.FINAL_STATE: return createFinalState();
      case ThingMLPackage.STATE_CONTAINER: return createStateContainer();
      case ThingMLPackage.EVENT: return createEvent();
      case ThingMLPackage.RECEIVE_MESSAGE: return createReceiveMessage();
      case ThingMLPackage.ACTION: return createAction();
      case ThingMLPackage.ACTION_BLOCK: return createActionBlock();
      case ThingMLPackage.EXTERN_STATEMENT: return createExternStatement();
      case ThingMLPackage.LOCAL_VARIABLE: return createLocalVariable();
      case ThingMLPackage.SEND_ACTION: return createSendAction();
      case ThingMLPackage.VARIABLE_ASSIGNMENT: return createVariableAssignment();
      case ThingMLPackage.INCREMENT: return createIncrement();
      case ThingMLPackage.DECREMENT: return createDecrement();
      case ThingMLPackage.LOOP_ACTION: return createLoopAction();
      case ThingMLPackage.CONDITIONAL_ACTION: return createConditionalAction();
      case ThingMLPackage.RETURN_ACTION: return createReturnAction();
      case ThingMLPackage.PRINT_ACTION: return createPrintAction();
      case ThingMLPackage.ERROR_ACTION: return createErrorAction();
      case ThingMLPackage.START_SESSION: return createStartSession();
      case ThingMLPackage.FUNCTION_CALL_STATEMENT: return createFunctionCallStatement();
      case ThingMLPackage.EXPRESSION: return createExpression();
      case ThingMLPackage.EXTERN_EXPRESSION: return createExternExpression();
      case ThingMLPackage.ENUM_LITERAL_REF: return createEnumLiteralRef();
      case ThingMLPackage.INTEGER_LITERAL: return createIntegerLiteral();
      case ThingMLPackage.BOOLEAN_LITERAL: return createBooleanLiteral();
      case ThingMLPackage.STRING_LITERAL: return createStringLiteral();
      case ThingMLPackage.DOUBLE_LITERAL: return createDoubleLiteral();
      case ThingMLPackage.PROPERTY_REFERENCE: return createPropertyReference();
      case ThingMLPackage.EVENT_REFERENCE: return createEventReference();
      case ThingMLPackage.FUNCTION_CALL_EXPRESSION: return createFunctionCallExpression();
      case ThingMLPackage.CONFIGURATION: return createConfiguration();
      case ThingMLPackage.INSTANCE: return createInstance();
      case ThingMLPackage.CONFIG_PROPERTY_ASSIGN: return createConfigPropertyAssign();
      case ThingMLPackage.ABSTRACT_CONNECTOR: return createAbstractConnector();
      case ThingMLPackage.CONNECTOR: return createConnector();
      case ThingMLPackage.EXTERNAL_CONNECTOR: return createExternalConnector();
      case ThingMLPackage.CAST_EXPRESSION: return createCastExpression();
      case ThingMLPackage.OR_EXPRESSION: return createOrExpression();
      case ThingMLPackage.AND_EXPRESSION: return createAndExpression();
      case ThingMLPackage.EQUALS_EXPRESSION: return createEqualsExpression();
      case ThingMLPackage.NOT_EQUALS_EXPRESSION: return createNotEqualsExpression();
      case ThingMLPackage.GREATER_EXPRESSION: return createGreaterExpression();
      case ThingMLPackage.LOWER_EXPRESSION: return createLowerExpression();
      case ThingMLPackage.GREATER_OR_EQUAL_EXPRESSION: return createGreaterOrEqualExpression();
      case ThingMLPackage.LOWER_OR_EQUAL_EXPRESSION: return createLowerOrEqualExpression();
      case ThingMLPackage.PLUS_EXPRESSION: return createPlusExpression();
      case ThingMLPackage.MINUS_EXPRESSION: return createMinusExpression();
      case ThingMLPackage.TIMES_EXPRESSION: return createTimesExpression();
      case ThingMLPackage.DIV_EXPRESSION: return createDivExpression();
      case ThingMLPackage.MOD_EXPRESSION: return createModExpression();
      case ThingMLPackage.EXPRESSION_GROUP: return createExpressionGroup();
      case ThingMLPackage.NOT_EXPRESSION: return createNotExpression();
      case ThingMLPackage.UNARY_MINUS: return createUnaryMinus();
      case ThingMLPackage.ARRAY_INDEX: return createArrayIndex();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ThingMLModel createThingMLModel()
  {
    ThingMLModelImpl thingMLModel = new ThingMLModelImpl();
    return thingMLModel;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PlatformAnnotation createPlatformAnnotation()
  {
    PlatformAnnotationImpl platformAnnotation = new PlatformAnnotationImpl();
    return platformAnnotation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NamedElement createNamedElement()
  {
    NamedElementImpl namedElement = new NamedElementImpl();
    return namedElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotatedElement createAnnotatedElement()
  {
    AnnotatedElementImpl annotatedElement = new AnnotatedElementImpl();
    return annotatedElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Variable createVariable()
  {
    VariableImpl variable = new VariableImpl();
    return variable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypeRef createTypeRef()
  {
    TypeRefImpl typeRef = new TypeRefImpl();
    return typeRef;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type createType()
  {
    TypeImpl type = new TypeImpl();
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PrimitiveType createPrimitiveType()
  {
    PrimitiveTypeImpl primitiveType = new PrimitiveTypeImpl();
    return primitiveType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ObjectType createObjectType()
  {
    ObjectTypeImpl objectType = new ObjectTypeImpl();
    return objectType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Enumeration createEnumeration()
  {
    EnumerationImpl enumeration = new EnumerationImpl();
    return enumeration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnumerationLiteral createEnumerationLiteral()
  {
    EnumerationLiteralImpl enumerationLiteral = new EnumerationLiteralImpl();
    return enumerationLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Thing createThing()
  {
    ThingImpl thing = new ThingImpl();
    return thing;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PropertyAssign createPropertyAssign()
  {
    PropertyAssignImpl propertyAssign = new PropertyAssignImpl();
    return propertyAssign;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Protocol createProtocol()
  {
    ProtocolImpl protocol = new ProtocolImpl();
    return protocol;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Function createFunction()
  {
    FunctionImpl function = new FunctionImpl();
    return function;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Property createProperty()
  {
    PropertyImpl property = new PropertyImpl();
    return property;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Message createMessage()
  {
    MessageImpl message = new MessageImpl();
    return message;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Parameter createParameter()
  {
    ParameterImpl parameter = new ParameterImpl();
    return parameter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Port createPort()
  {
    PortImpl port = new PortImpl();
    return port;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RequiredPort createRequiredPort()
  {
    RequiredPortImpl requiredPort = new RequiredPortImpl();
    return requiredPort;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ProvidedPort createProvidedPort()
  {
    ProvidedPortImpl providedPort = new ProvidedPortImpl();
    return providedPort;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InternalPort createInternalPort()
  {
    InternalPortImpl internalPort = new InternalPortImpl();
    return internalPort;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public State createState()
  {
    StateImpl state = new StateImpl();
    return state;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Handler createHandler()
  {
    HandlerImpl handler = new HandlerImpl();
    return handler;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Transition createTransition()
  {
    TransitionImpl transition = new TransitionImpl();
    return transition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InternalTransition createInternalTransition()
  {
    InternalTransitionImpl internalTransition = new InternalTransitionImpl();
    return internalTransition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CompositeState createCompositeState()
  {
    CompositeStateImpl compositeState = new CompositeStateImpl();
    return compositeState;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Session createSession()
  {
    SessionImpl session = new SessionImpl();
    return session;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Region createRegion()
  {
    RegionImpl region = new RegionImpl();
    return region;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FinalState createFinalState()
  {
    FinalStateImpl finalState = new FinalStateImpl();
    return finalState;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StateContainer createStateContainer()
  {
    StateContainerImpl stateContainer = new StateContainerImpl();
    return stateContainer;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Event createEvent()
  {
    EventImpl event = new EventImpl();
    return event;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReceiveMessage createReceiveMessage()
  {
    ReceiveMessageImpl receiveMessage = new ReceiveMessageImpl();
    return receiveMessage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Action createAction()
  {
    ActionImpl action = new ActionImpl();
    return action;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ActionBlock createActionBlock()
  {
    ActionBlockImpl actionBlock = new ActionBlockImpl();
    return actionBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExternStatement createExternStatement()
  {
    ExternStatementImpl externStatement = new ExternStatementImpl();
    return externStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalVariable createLocalVariable()
  {
    LocalVariableImpl localVariable = new LocalVariableImpl();
    return localVariable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SendAction createSendAction()
  {
    SendActionImpl sendAction = new SendActionImpl();
    return sendAction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VariableAssignment createVariableAssignment()
  {
    VariableAssignmentImpl variableAssignment = new VariableAssignmentImpl();
    return variableAssignment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Increment createIncrement()
  {
    IncrementImpl increment = new IncrementImpl();
    return increment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Decrement createDecrement()
  {
    DecrementImpl decrement = new DecrementImpl();
    return decrement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LoopAction createLoopAction()
  {
    LoopActionImpl loopAction = new LoopActionImpl();
    return loopAction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConditionalAction createConditionalAction()
  {
    ConditionalActionImpl conditionalAction = new ConditionalActionImpl();
    return conditionalAction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReturnAction createReturnAction()
  {
    ReturnActionImpl returnAction = new ReturnActionImpl();
    return returnAction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PrintAction createPrintAction()
  {
    PrintActionImpl printAction = new PrintActionImpl();
    return printAction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ErrorAction createErrorAction()
  {
    ErrorActionImpl errorAction = new ErrorActionImpl();
    return errorAction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StartSession createStartSession()
  {
    StartSessionImpl startSession = new StartSessionImpl();
    return startSession;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FunctionCallStatement createFunctionCallStatement()
  {
    FunctionCallStatementImpl functionCallStatement = new FunctionCallStatementImpl();
    return functionCallStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression createExpression()
  {
    ExpressionImpl expression = new ExpressionImpl();
    return expression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExternExpression createExternExpression()
  {
    ExternExpressionImpl externExpression = new ExternExpressionImpl();
    return externExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnumLiteralRef createEnumLiteralRef()
  {
    EnumLiteralRefImpl enumLiteralRef = new EnumLiteralRefImpl();
    return enumLiteralRef;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerLiteral createIntegerLiteral()
  {
    IntegerLiteralImpl integerLiteral = new IntegerLiteralImpl();
    return integerLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BooleanLiteral createBooleanLiteral()
  {
    BooleanLiteralImpl booleanLiteral = new BooleanLiteralImpl();
    return booleanLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StringLiteral createStringLiteral()
  {
    StringLiteralImpl stringLiteral = new StringLiteralImpl();
    return stringLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DoubleLiteral createDoubleLiteral()
  {
    DoubleLiteralImpl doubleLiteral = new DoubleLiteralImpl();
    return doubleLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PropertyReference createPropertyReference()
  {
    PropertyReferenceImpl propertyReference = new PropertyReferenceImpl();
    return propertyReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EventReference createEventReference()
  {
    EventReferenceImpl eventReference = new EventReferenceImpl();
    return eventReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FunctionCallExpression createFunctionCallExpression()
  {
    FunctionCallExpressionImpl functionCallExpression = new FunctionCallExpressionImpl();
    return functionCallExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Configuration createConfiguration()
  {
    ConfigurationImpl configuration = new ConfigurationImpl();
    return configuration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Instance createInstance()
  {
    InstanceImpl instance = new InstanceImpl();
    return instance;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConfigPropertyAssign createConfigPropertyAssign()
  {
    ConfigPropertyAssignImpl configPropertyAssign = new ConfigPropertyAssignImpl();
    return configPropertyAssign;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AbstractConnector createAbstractConnector()
  {
    AbstractConnectorImpl abstractConnector = new AbstractConnectorImpl();
    return abstractConnector;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Connector createConnector()
  {
    ConnectorImpl connector = new ConnectorImpl();
    return connector;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExternalConnector createExternalConnector()
  {
    ExternalConnectorImpl externalConnector = new ExternalConnectorImpl();
    return externalConnector;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CastExpression createCastExpression()
  {
    CastExpressionImpl castExpression = new CastExpressionImpl();
    return castExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public OrExpression createOrExpression()
  {
    OrExpressionImpl orExpression = new OrExpressionImpl();
    return orExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AndExpression createAndExpression()
  {
    AndExpressionImpl andExpression = new AndExpressionImpl();
    return andExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EqualsExpression createEqualsExpression()
  {
    EqualsExpressionImpl equalsExpression = new EqualsExpressionImpl();
    return equalsExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotEqualsExpression createNotEqualsExpression()
  {
    NotEqualsExpressionImpl notEqualsExpression = new NotEqualsExpressionImpl();
    return notEqualsExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GreaterExpression createGreaterExpression()
  {
    GreaterExpressionImpl greaterExpression = new GreaterExpressionImpl();
    return greaterExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LowerExpression createLowerExpression()
  {
    LowerExpressionImpl lowerExpression = new LowerExpressionImpl();
    return lowerExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GreaterOrEqualExpression createGreaterOrEqualExpression()
  {
    GreaterOrEqualExpressionImpl greaterOrEqualExpression = new GreaterOrEqualExpressionImpl();
    return greaterOrEqualExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LowerOrEqualExpression createLowerOrEqualExpression()
  {
    LowerOrEqualExpressionImpl lowerOrEqualExpression = new LowerOrEqualExpressionImpl();
    return lowerOrEqualExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PlusExpression createPlusExpression()
  {
    PlusExpressionImpl plusExpression = new PlusExpressionImpl();
    return plusExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MinusExpression createMinusExpression()
  {
    MinusExpressionImpl minusExpression = new MinusExpressionImpl();
    return minusExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TimesExpression createTimesExpression()
  {
    TimesExpressionImpl timesExpression = new TimesExpressionImpl();
    return timesExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DivExpression createDivExpression()
  {
    DivExpressionImpl divExpression = new DivExpressionImpl();
    return divExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModExpression createModExpression()
  {
    ModExpressionImpl modExpression = new ModExpressionImpl();
    return modExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionGroup createExpressionGroup()
  {
    ExpressionGroupImpl expressionGroup = new ExpressionGroupImpl();
    return expressionGroup;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotExpression createNotExpression()
  {
    NotExpressionImpl notExpression = new NotExpressionImpl();
    return notExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UnaryMinus createUnaryMinus()
  {
    UnaryMinusImpl unaryMinus = new UnaryMinusImpl();
    return unaryMinus;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ArrayIndex createArrayIndex()
  {
    ArrayIndexImpl arrayIndex = new ArrayIndexImpl();
    return arrayIndex;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ThingMLPackage getThingMLPackage()
  {
    return (ThingMLPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static ThingMLPackage getPackage()
  {
    return ThingMLPackage.eINSTANCE;
  }

} //ThingMLFactoryImpl
