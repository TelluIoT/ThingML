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
package org.thingml.xtext.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;
import org.thingml.xtext.services.ThingMLGrammarAccess;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.AndExpression;
import org.thingml.xtext.thingML.AnnotatedElement;
import org.thingml.xtext.thingML.ArrayIndex;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.ConditionalAction;
import org.thingml.xtext.thingML.ConfigPropertyAssign;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.Decrement;
import org.thingml.xtext.thingML.DivExpression;
import org.thingml.xtext.thingML.DoubleLiteral;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.EqualsExpression;
import org.thingml.xtext.thingML.ErrorAction;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.ExternExpression;
import org.thingml.xtext.thingML.ExternStatement;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.FinalState;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.FunctionCallStatement;
import org.thingml.xtext.thingML.GreaterExpression;
import org.thingml.xtext.thingML.GreaterOrEqualExpression;
import org.thingml.xtext.thingML.Increment;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.InternalPort;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.LoopAction;
import org.thingml.xtext.thingML.LowerExpression;
import org.thingml.xtext.thingML.LowerOrEqualExpression;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.MinusExpression;
import org.thingml.xtext.thingML.ModExpression;
import org.thingml.xtext.thingML.NamedElement;
import org.thingml.xtext.thingML.NotEqualsExpression;
import org.thingml.xtext.thingML.NotExpression;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.OrExpression;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.PlusExpression;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.PrintAction;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyAssign;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.ProvidedPort;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.RequiredPort;
import org.thingml.xtext.thingML.ReturnAction;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.StartSession;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.ThingMLPackage;
import org.thingml.xtext.thingML.TimesExpression;
import org.thingml.xtext.thingML.Transition;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.UnaryMinus;
import org.thingml.xtext.thingML.Variable;
import org.thingml.xtext.thingML.VariableAssignment;

@SuppressWarnings("all")
public class ThingMLSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private ThingMLGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == ThingMLPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case ThingMLPackage.ACTION_BLOCK:
				sequence_ActionBlock(context, (ActionBlock) semanticObject); 
				return; 
			case ThingMLPackage.AND_EXPRESSION:
				sequence_AndExpression(context, (AndExpression) semanticObject); 
				return; 
			case ThingMLPackage.ANNOTATED_ELEMENT:
				sequence_AnnotatedElement(context, (AnnotatedElement) semanticObject); 
				return; 
			case ThingMLPackage.ARRAY_INDEX:
				sequence_ArrayIndexPostfix(context, (ArrayIndex) semanticObject); 
				return; 
			case ThingMLPackage.BOOLEAN_LITERAL:
				sequence_BooleanLiteral(context, (BooleanLiteral) semanticObject); 
				return; 
			case ThingMLPackage.CAST_EXPRESSION:
				sequence_CastExpression(context, (CastExpression) semanticObject); 
				return; 
			case ThingMLPackage.COMPOSITE_STATE:
				if (rule == grammarAccess.getCompositeStateRule()
						|| rule == grammarAccess.getStateContainerRule()) {
					sequence_CompositeState(context, (CompositeState) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getNamedElementRule()
						|| rule == grammarAccess.getAnnotatedElementRule()
						|| rule == grammarAccess.getStateRule()) {
					sequence_CompositeState_StateMachine(context, (CompositeState) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getStateMachineRule()) {
					sequence_StateMachine(context, (CompositeState) semanticObject); 
					return; 
				}
				else break;
			case ThingMLPackage.CONDITIONAL_ACTION:
				sequence_ConditionalAction(context, (ConditionalAction) semanticObject); 
				return; 
			case ThingMLPackage.CONFIG_PROPERTY_ASSIGN:
				sequence_ConfigPropertyAssign(context, (ConfigPropertyAssign) semanticObject); 
				return; 
			case ThingMLPackage.CONFIGURATION:
				sequence_Configuration(context, (Configuration) semanticObject); 
				return; 
			case ThingMLPackage.CONNECTOR:
				sequence_Connector(context, (Connector) semanticObject); 
				return; 
			case ThingMLPackage.DECREMENT:
				sequence_Decrement(context, (Decrement) semanticObject); 
				return; 
			case ThingMLPackage.DIV_EXPRESSION:
				sequence_Multiplication(context, (DivExpression) semanticObject); 
				return; 
			case ThingMLPackage.DOUBLE_LITERAL:
				sequence_DoubleLiteral(context, (DoubleLiteral) semanticObject); 
				return; 
			case ThingMLPackage.ENUM_LITERAL_REF:
				sequence_EnumLiteralRef(context, (EnumLiteralRef) semanticObject); 
				return; 
			case ThingMLPackage.ENUMERATION:
				sequence_Enumeration(context, (Enumeration) semanticObject); 
				return; 
			case ThingMLPackage.ENUMERATION_LITERAL:
				sequence_EnumerationLiteral(context, (EnumerationLiteral) semanticObject); 
				return; 
			case ThingMLPackage.EQUALS_EXPRESSION:
				sequence_Equality(context, (EqualsExpression) semanticObject); 
				return; 
			case ThingMLPackage.ERROR_ACTION:
				sequence_ErrorAction(context, (ErrorAction) semanticObject); 
				return; 
			case ThingMLPackage.EVENT_REFERENCE:
				sequence_EventReference(context, (EventReference) semanticObject); 
				return; 
			case ThingMLPackage.EXPRESSION_GROUP:
				sequence_Primary(context, (ExpressionGroup) semanticObject); 
				return; 
			case ThingMLPackage.EXTERN_EXPRESSION:
				sequence_ExternExpression(context, (ExternExpression) semanticObject); 
				return; 
			case ThingMLPackage.EXTERN_STATEMENT:
				sequence_ExternStatement(context, (ExternStatement) semanticObject); 
				return; 
			case ThingMLPackage.EXTERNAL_CONNECTOR:
				sequence_ExternalConnector(context, (ExternalConnector) semanticObject); 
				return; 
			case ThingMLPackage.FINAL_STATE:
				sequence_FinalState(context, (FinalState) semanticObject); 
				return; 
			case ThingMLPackage.FUNCTION:
				if (rule == grammarAccess.getAbstractFunctionRule()) {
					sequence_AbstractFunction(context, (Function) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getNamedElementRule()
						|| rule == grammarAccess.getAnnotatedElementRule()
						|| rule == grammarAccess.getFunctionRule()) {
					sequence_AbstractFunction_Function(context, (Function) semanticObject); 
					return; 
				}
				else break;
			case ThingMLPackage.FUNCTION_CALL_EXPRESSION:
				sequence_FunctionCallExpression(context, (FunctionCallExpression) semanticObject); 
				return; 
			case ThingMLPackage.FUNCTION_CALL_STATEMENT:
				sequence_FunctionCallStatement(context, (FunctionCallStatement) semanticObject); 
				return; 
			case ThingMLPackage.GREATER_EXPRESSION:
				sequence_Comparaison(context, (GreaterExpression) semanticObject); 
				return; 
			case ThingMLPackage.GREATER_OR_EQUAL_EXPRESSION:
				sequence_Comparaison(context, (GreaterOrEqualExpression) semanticObject); 
				return; 
			case ThingMLPackage.INCREMENT:
				sequence_Increment(context, (Increment) semanticObject); 
				return; 
			case ThingMLPackage.INSTANCE:
				sequence_Instance(context, (Instance) semanticObject); 
				return; 
			case ThingMLPackage.INTEGER_LITERAL:
				sequence_IntegerLiteral(context, (IntegerLiteral) semanticObject); 
				return; 
			case ThingMLPackage.INTERNAL_PORT:
				sequence_InternalPort(context, (InternalPort) semanticObject); 
				return; 
			case ThingMLPackage.INTERNAL_TRANSITION:
				sequence_InternalTransition(context, (InternalTransition) semanticObject); 
				return; 
			case ThingMLPackage.LOCAL_VARIABLE:
				sequence_LocalVariable(context, (LocalVariable) semanticObject); 
				return; 
			case ThingMLPackage.LOOP_ACTION:
				sequence_LoopAction(context, (LoopAction) semanticObject); 
				return; 
			case ThingMLPackage.LOWER_EXPRESSION:
				sequence_Comparaison(context, (LowerExpression) semanticObject); 
				return; 
			case ThingMLPackage.LOWER_OR_EQUAL_EXPRESSION:
				sequence_Comparaison(context, (LowerOrEqualExpression) semanticObject); 
				return; 
			case ThingMLPackage.MESSAGE:
				sequence_Message(context, (Message) semanticObject); 
				return; 
			case ThingMLPackage.MINUS_EXPRESSION:
				sequence_Addition(context, (MinusExpression) semanticObject); 
				return; 
			case ThingMLPackage.MOD_EXPRESSION:
				sequence_Modulo(context, (ModExpression) semanticObject); 
				return; 
			case ThingMLPackage.NAMED_ELEMENT:
				sequence_NamedElement(context, (NamedElement) semanticObject); 
				return; 
			case ThingMLPackage.NOT_EQUALS_EXPRESSION:
				sequence_Equality(context, (NotEqualsExpression) semanticObject); 
				return; 
			case ThingMLPackage.NOT_EXPRESSION:
				sequence_Primary(context, (NotExpression) semanticObject); 
				return; 
			case ThingMLPackage.OBJECT_TYPE:
				sequence_ObjectType(context, (ObjectType) semanticObject); 
				return; 
			case ThingMLPackage.OR_EXPRESSION:
				sequence_OrExpression(context, (OrExpression) semanticObject); 
				return; 
			case ThingMLPackage.PARAMETER:
				sequence_Parameter(context, (org.thingml.xtext.thingML.Parameter) semanticObject); 
				return; 
			case ThingMLPackage.PLATFORM_ANNOTATION:
				sequence_PlatformAnnotation(context, (PlatformAnnotation) semanticObject); 
				return; 
			case ThingMLPackage.PLUS_EXPRESSION:
				sequence_Addition(context, (PlusExpression) semanticObject); 
				return; 
			case ThingMLPackage.PRIMITIVE_TYPE:
				sequence_PrimitiveType(context, (PrimitiveType) semanticObject); 
				return; 
			case ThingMLPackage.PRINT_ACTION:
				sequence_PrintAction(context, (PrintAction) semanticObject); 
				return; 
			case ThingMLPackage.PROPERTY:
				sequence_Property(context, (Property) semanticObject); 
				return; 
			case ThingMLPackage.PROPERTY_ASSIGN:
				sequence_PropertyAssign(context, (PropertyAssign) semanticObject); 
				return; 
			case ThingMLPackage.PROPERTY_REFERENCE:
				sequence_PropertyReference(context, (PropertyReference) semanticObject); 
				return; 
			case ThingMLPackage.PROTOCOL:
				sequence_Protocol(context, (Protocol) semanticObject); 
				return; 
			case ThingMLPackage.PROVIDED_PORT:
				sequence_ProvidedPort(context, (ProvidedPort) semanticObject); 
				return; 
			case ThingMLPackage.RECEIVE_MESSAGE:
				sequence_ReceiveMessage(context, (ReceiveMessage) semanticObject); 
				return; 
			case ThingMLPackage.REGION:
				sequence_Region(context, (Region) semanticObject); 
				return; 
			case ThingMLPackage.REQUIRED_PORT:
				sequence_RequiredPort(context, (RequiredPort) semanticObject); 
				return; 
			case ThingMLPackage.RETURN_ACTION:
				sequence_ReturnAction(context, (ReturnAction) semanticObject); 
				return; 
			case ThingMLPackage.SEND_ACTION:
				sequence_SendAction(context, (SendAction) semanticObject); 
				return; 
			case ThingMLPackage.SESSION:
				sequence_Session(context, (Session) semanticObject); 
				return; 
			case ThingMLPackage.START_SESSION:
				sequence_StartSession(context, (StartSession) semanticObject); 
				return; 
			case ThingMLPackage.STATE:
				sequence_State(context, (State) semanticObject); 
				return; 
			case ThingMLPackage.STATE_CONTAINER:
				sequence_StateContainer(context, (StateContainer) semanticObject); 
				return; 
			case ThingMLPackage.STRING_LITERAL:
				sequence_StringLiteral(context, (StringLiteral) semanticObject); 
				return; 
			case ThingMLPackage.THING:
				sequence_Thing(context, (Thing) semanticObject); 
				return; 
			case ThingMLPackage.THING_ML_MODEL:
				sequence_ThingMLModel(context, (ThingMLModel) semanticObject); 
				return; 
			case ThingMLPackage.TIMES_EXPRESSION:
				sequence_Multiplication(context, (TimesExpression) semanticObject); 
				return; 
			case ThingMLPackage.TRANSITION:
				sequence_Transition(context, (Transition) semanticObject); 
				return; 
			case ThingMLPackage.TYPE_REF:
				sequence_TypeRef(context, (TypeRef) semanticObject); 
				return; 
			case ThingMLPackage.UNARY_MINUS:
				sequence_Primary(context, (UnaryMinus) semanticObject); 
				return; 
			case ThingMLPackage.VARIABLE:
				sequence_Variable(context, (Variable) semanticObject); 
				return; 
			case ThingMLPackage.VARIABLE_ASSIGNMENT:
				sequence_VariableAssignment(context, (VariableAssignment) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     AbstractFunction returns Function
	 *
	 * Constraint:
	 *     (abstract?='abstract' name=ID (parameters+=Parameter parameters+=Parameter*)? typeRef=TypeRef? annotations+=PlatformAnnotation*)
	 */
	protected void sequence_AbstractFunction(ISerializationContext context, Function semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Function
	 *     AnnotatedElement returns Function
	 *     Function returns Function
	 *
	 * Constraint:
	 *     (
	 *         (name=ID (parameters+=Parameter parameters+=Parameter*)? typeRef=TypeRef? annotations+=PlatformAnnotation* body=Action) | 
	 *         (abstract?='abstract' name=ID (parameters+=Parameter parameters+=Parameter*)? typeRef=TypeRef? annotations+=PlatformAnnotation*)
	 *     )
	 */
	protected void sequence_AbstractFunction_Function(ISerializationContext context, Function semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Action returns ActionBlock
	 *     ActionBlock returns ActionBlock
	 *
	 * Constraint:
	 *     actions+=Action*
	 */
	protected void sequence_ActionBlock(ISerializationContext context, ActionBlock semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns MinusExpression
	 *     CastExpression returns MinusExpression
	 *     CastExpression.CastExpression_1_0 returns MinusExpression
	 *     OrExpression returns MinusExpression
	 *     OrExpression.OrExpression_1_0 returns MinusExpression
	 *     AndExpression returns MinusExpression
	 *     AndExpression.AndExpression_1_0 returns MinusExpression
	 *     Equality returns MinusExpression
	 *     Equality.EqualsExpression_1_0_0 returns MinusExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns MinusExpression
	 *     Comparaison returns MinusExpression
	 *     Comparaison.GreaterExpression_1_0_0 returns MinusExpression
	 *     Comparaison.LowerExpression_1_1_0 returns MinusExpression
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns MinusExpression
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns MinusExpression
	 *     Addition returns MinusExpression
	 *     Addition.PlusExpression_1_0_0 returns MinusExpression
	 *     Addition.MinusExpression_1_1_0 returns MinusExpression
	 *
	 * Constraint:
	 *     (lhs=Addition_MinusExpression_1_1_0 rhs=Multiplication)
	 */
	protected void sequence_Addition(ISerializationContext context, MinusExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.MINUS_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.MINUS_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.MINUS_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.MINUS_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getAdditionAccess().getMinusExpressionLhsAction_1_1_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_1_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns PlusExpression
	 *     CastExpression returns PlusExpression
	 *     CastExpression.CastExpression_1_0 returns PlusExpression
	 *     OrExpression returns PlusExpression
	 *     OrExpression.OrExpression_1_0 returns PlusExpression
	 *     AndExpression returns PlusExpression
	 *     AndExpression.AndExpression_1_0 returns PlusExpression
	 *     Equality returns PlusExpression
	 *     Equality.EqualsExpression_1_0_0 returns PlusExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns PlusExpression
	 *     Comparaison returns PlusExpression
	 *     Comparaison.GreaterExpression_1_0_0 returns PlusExpression
	 *     Comparaison.LowerExpression_1_1_0 returns PlusExpression
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns PlusExpression
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns PlusExpression
	 *     Addition returns PlusExpression
	 *     Addition.PlusExpression_1_0_0 returns PlusExpression
	 *     Addition.MinusExpression_1_1_0 returns PlusExpression
	 *
	 * Constraint:
	 *     (lhs=Addition_PlusExpression_1_0_0 rhs=Multiplication)
	 */
	protected void sequence_Addition(ISerializationContext context, PlusExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.PLUS_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.PLUS_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.PLUS_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.PLUS_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getAdditionAccess().getPlusExpressionLhsAction_1_0_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_0_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns AndExpression
	 *     CastExpression returns AndExpression
	 *     CastExpression.CastExpression_1_0 returns AndExpression
	 *     OrExpression returns AndExpression
	 *     OrExpression.OrExpression_1_0 returns AndExpression
	 *     AndExpression returns AndExpression
	 *     AndExpression.AndExpression_1_0 returns AndExpression
	 *
	 * Constraint:
	 *     (lhs=AndExpression_AndExpression_1_0 rhs=Equality)
	 */
	protected void sequence_AndExpression(ISerializationContext context, AndExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.AND_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.AND_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.AND_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.AND_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getAndExpressionAccess().getAndExpressionLhsAction_1_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getAndExpressionAccess().getRhsEqualityParserRuleCall_1_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     AnnotatedElement returns AnnotatedElement
	 *
	 * Constraint:
	 *     annotations+=PlatformAnnotation+
	 */
	protected void sequence_AnnotatedElement(ISerializationContext context, AnnotatedElement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns ArrayIndex
	 *     CastExpression returns ArrayIndex
	 *     CastExpression.CastExpression_1_0 returns ArrayIndex
	 *     OrExpression returns ArrayIndex
	 *     OrExpression.OrExpression_1_0 returns ArrayIndex
	 *     AndExpression returns ArrayIndex
	 *     AndExpression.AndExpression_1_0 returns ArrayIndex
	 *     Equality returns ArrayIndex
	 *     Equality.EqualsExpression_1_0_0 returns ArrayIndex
	 *     Equality.NotEqualsExpression_1_1_0 returns ArrayIndex
	 *     Comparaison returns ArrayIndex
	 *     Comparaison.GreaterExpression_1_0_0 returns ArrayIndex
	 *     Comparaison.LowerExpression_1_1_0 returns ArrayIndex
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns ArrayIndex
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns ArrayIndex
	 *     Addition returns ArrayIndex
	 *     Addition.PlusExpression_1_0_0 returns ArrayIndex
	 *     Addition.MinusExpression_1_1_0 returns ArrayIndex
	 *     Multiplication returns ArrayIndex
	 *     Multiplication.TimesExpression_1_0_0 returns ArrayIndex
	 *     Multiplication.DivExpression_1_1_0 returns ArrayIndex
	 *     Modulo returns ArrayIndex
	 *     Modulo.ModExpression_1_0 returns ArrayIndex
	 *     Primary returns ArrayIndex
	 *     ArrayIndexPostfix returns ArrayIndex
	 *
	 * Constraint:
	 *     (array=ArrayIndexPostfix_ArrayIndex_1_0 index=Expression)
	 */
	protected void sequence_ArrayIndexPostfix(ISerializationContext context, ArrayIndex semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.ARRAY_INDEX__ARRAY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.ARRAY_INDEX__ARRAY));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.ARRAY_INDEX__INDEX) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.ARRAY_INDEX__INDEX));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getArrayIndexPostfixAccess().getArrayIndexArrayAction_1_0(), semanticObject.getArray());
		feeder.accept(grammarAccess.getArrayIndexPostfixAccess().getIndexExpressionParserRuleCall_1_2_0(), semanticObject.getIndex());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns BooleanLiteral
	 *     CastExpression returns BooleanLiteral
	 *     CastExpression.CastExpression_1_0 returns BooleanLiteral
	 *     OrExpression returns BooleanLiteral
	 *     OrExpression.OrExpression_1_0 returns BooleanLiteral
	 *     AndExpression returns BooleanLiteral
	 *     AndExpression.AndExpression_1_0 returns BooleanLiteral
	 *     Equality returns BooleanLiteral
	 *     Equality.EqualsExpression_1_0_0 returns BooleanLiteral
	 *     Equality.NotEqualsExpression_1_1_0 returns BooleanLiteral
	 *     Comparaison returns BooleanLiteral
	 *     Comparaison.GreaterExpression_1_0_0 returns BooleanLiteral
	 *     Comparaison.LowerExpression_1_1_0 returns BooleanLiteral
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns BooleanLiteral
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns BooleanLiteral
	 *     Addition returns BooleanLiteral
	 *     Addition.PlusExpression_1_0_0 returns BooleanLiteral
	 *     Addition.MinusExpression_1_1_0 returns BooleanLiteral
	 *     Multiplication returns BooleanLiteral
	 *     Multiplication.TimesExpression_1_0_0 returns BooleanLiteral
	 *     Multiplication.DivExpression_1_1_0 returns BooleanLiteral
	 *     Modulo returns BooleanLiteral
	 *     Modulo.ModExpression_1_0 returns BooleanLiteral
	 *     Primary returns BooleanLiteral
	 *     ArrayIndexPostfix returns BooleanLiteral
	 *     ArrayIndexPostfix.ArrayIndex_1_0 returns BooleanLiteral
	 *     AtomicExpression returns BooleanLiteral
	 *     BooleanLiteral returns BooleanLiteral
	 *
	 * Constraint:
	 *     boolValue?='true'?
	 */
	protected void sequence_BooleanLiteral(ISerializationContext context, BooleanLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns CastExpression
	 *     CastExpression returns CastExpression
	 *
	 * Constraint:
	 *     (term=CastExpression_CastExpression_1_0 type=[Type|ID] isArray?='['?)
	 */
	protected void sequence_CastExpression(ISerializationContext context, CastExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns GreaterExpression
	 *     CastExpression returns GreaterExpression
	 *     CastExpression.CastExpression_1_0 returns GreaterExpression
	 *     OrExpression returns GreaterExpression
	 *     OrExpression.OrExpression_1_0 returns GreaterExpression
	 *     AndExpression returns GreaterExpression
	 *     AndExpression.AndExpression_1_0 returns GreaterExpression
	 *     Equality returns GreaterExpression
	 *     Equality.EqualsExpression_1_0_0 returns GreaterExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns GreaterExpression
	 *     Comparaison returns GreaterExpression
	 *     Comparaison.GreaterExpression_1_0_0 returns GreaterExpression
	 *     Comparaison.LowerExpression_1_1_0 returns GreaterExpression
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns GreaterExpression
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns GreaterExpression
	 *
	 * Constraint:
	 *     (lhs=Comparaison_GreaterExpression_1_0_0 rhs=Addition)
	 */
	protected void sequence_Comparaison(ISerializationContext context, GreaterExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.GREATER_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.GREATER_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.GREATER_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.GREATER_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getComparaisonAccess().getGreaterExpressionLhsAction_1_0_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_0_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns GreaterOrEqualExpression
	 *     CastExpression returns GreaterOrEqualExpression
	 *     CastExpression.CastExpression_1_0 returns GreaterOrEqualExpression
	 *     OrExpression returns GreaterOrEqualExpression
	 *     OrExpression.OrExpression_1_0 returns GreaterOrEqualExpression
	 *     AndExpression returns GreaterOrEqualExpression
	 *     AndExpression.AndExpression_1_0 returns GreaterOrEqualExpression
	 *     Equality returns GreaterOrEqualExpression
	 *     Equality.EqualsExpression_1_0_0 returns GreaterOrEqualExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns GreaterOrEqualExpression
	 *     Comparaison returns GreaterOrEqualExpression
	 *     Comparaison.GreaterExpression_1_0_0 returns GreaterOrEqualExpression
	 *     Comparaison.LowerExpression_1_1_0 returns GreaterOrEqualExpression
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns GreaterOrEqualExpression
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns GreaterOrEqualExpression
	 *
	 * Constraint:
	 *     (lhs=Comparaison_GreaterOrEqualExpression_1_2_0 rhs=Addition)
	 */
	protected void sequence_Comparaison(ISerializationContext context, GreaterOrEqualExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.GREATER_OR_EQUAL_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.GREATER_OR_EQUAL_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.GREATER_OR_EQUAL_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.GREATER_OR_EQUAL_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getComparaisonAccess().getGreaterOrEqualExpressionLhsAction_1_2_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_2_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns LowerExpression
	 *     CastExpression returns LowerExpression
	 *     CastExpression.CastExpression_1_0 returns LowerExpression
	 *     OrExpression returns LowerExpression
	 *     OrExpression.OrExpression_1_0 returns LowerExpression
	 *     AndExpression returns LowerExpression
	 *     AndExpression.AndExpression_1_0 returns LowerExpression
	 *     Equality returns LowerExpression
	 *     Equality.EqualsExpression_1_0_0 returns LowerExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns LowerExpression
	 *     Comparaison returns LowerExpression
	 *     Comparaison.GreaterExpression_1_0_0 returns LowerExpression
	 *     Comparaison.LowerExpression_1_1_0 returns LowerExpression
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns LowerExpression
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns LowerExpression
	 *
	 * Constraint:
	 *     (lhs=Comparaison_LowerExpression_1_1_0 rhs=Addition)
	 */
	protected void sequence_Comparaison(ISerializationContext context, LowerExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.LOWER_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.LOWER_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.LOWER_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.LOWER_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getComparaisonAccess().getLowerExpressionLhsAction_1_1_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_1_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns LowerOrEqualExpression
	 *     CastExpression returns LowerOrEqualExpression
	 *     CastExpression.CastExpression_1_0 returns LowerOrEqualExpression
	 *     OrExpression returns LowerOrEqualExpression
	 *     OrExpression.OrExpression_1_0 returns LowerOrEqualExpression
	 *     AndExpression returns LowerOrEqualExpression
	 *     AndExpression.AndExpression_1_0 returns LowerOrEqualExpression
	 *     Equality returns LowerOrEqualExpression
	 *     Equality.EqualsExpression_1_0_0 returns LowerOrEqualExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns LowerOrEqualExpression
	 *     Comparaison returns LowerOrEqualExpression
	 *     Comparaison.GreaterExpression_1_0_0 returns LowerOrEqualExpression
	 *     Comparaison.LowerExpression_1_1_0 returns LowerOrEqualExpression
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns LowerOrEqualExpression
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns LowerOrEqualExpression
	 *
	 * Constraint:
	 *     (lhs=Comparaison_LowerOrEqualExpression_1_3_0 rhs=Addition)
	 */
	protected void sequence_Comparaison(ISerializationContext context, LowerOrEqualExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.LOWER_OR_EQUAL_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.LOWER_OR_EQUAL_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.LOWER_OR_EQUAL_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.LOWER_OR_EQUAL_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getComparaisonAccess().getLowerOrEqualExpressionLhsAction_1_3_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_3_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     CompositeState returns CompositeState
	 *     StateContainer returns CompositeState
	 *
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         initial=[State|ID] 
	 *         history?='history'? 
	 *         annotations+=PlatformAnnotation* 
	 *         properties+=Property* 
	 *         entry=Action? 
	 *         exit=Action? 
	 *         (substate+=State | internal+=InternalTransition | outgoing+=Transition)* 
	 *         region+=Region? 
	 *         (session+=Session? region+=Region?)*
	 *     )
	 */
	protected void sequence_CompositeState(ISerializationContext context, CompositeState semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns CompositeState
	 *     AnnotatedElement returns CompositeState
	 *     State returns CompositeState
	 *
	 * Constraint:
	 *     (
	 *         (
	 *             name=ID 
	 *             initial=[State|ID] 
	 *             history?='history'? 
	 *             annotations+=PlatformAnnotation* 
	 *             properties+=Property* 
	 *             entry=Action? 
	 *             exit=Action? 
	 *             (substate+=State | internal+=InternalTransition | outgoing+=Transition)* 
	 *             session+=Session? 
	 *             (region+=Region? session+=Session?)*
	 *         ) | 
	 *         (
	 *             name=ID? 
	 *             initial=[State|ID] 
	 *             history?='history'? 
	 *             annotations+=PlatformAnnotation* 
	 *             properties+=Property* 
	 *             entry=Action? 
	 *             exit=Action? 
	 *             substate+=State? 
	 *             (internal+=InternalTransition? substate+=State?)* 
	 *             (region+=Region | session+=Session)*
	 *         )
	 *     )
	 */
	protected void sequence_CompositeState_StateMachine(ISerializationContext context, CompositeState semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Action returns ConditionalAction
	 *     ConditionalAction returns ConditionalAction
	 *
	 * Constraint:
	 *     (condition=Expression action=Action elseAction=Action?)
	 */
	protected void sequence_ConditionalAction(ISerializationContext context, ConditionalAction semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ConfigPropertyAssign returns ConfigPropertyAssign
	 *
	 * Constraint:
	 *     (instance=[Instance|ID] property=[Property|ID] index+=Expression* init=Expression annotations+=PlatformAnnotation*)
	 */
	protected void sequence_ConfigPropertyAssign(ISerializationContext context, ConfigPropertyAssign semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Configuration
	 *     AnnotatedElement returns Configuration
	 *     Configuration returns Configuration
	 *
	 * Constraint:
	 *     (name=ID annotations+=PlatformAnnotation* (instances+=Instance | connectors+=AbstractConnector | propassigns+=ConfigPropertyAssign)*)
	 */
	protected void sequence_Configuration(ISerializationContext context, Configuration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Connector
	 *     AnnotatedElement returns Connector
	 *     AbstractConnector returns Connector
	 *     Connector returns Connector
	 *
	 * Constraint:
	 *     (
	 *         name=ID? 
	 *         cli=[Instance|ID] 
	 *         required=[RequiredPort|ID] 
	 *         srv=[Instance|ID] 
	 *         provided=[ProvidedPort|ID] 
	 *         annotations+=PlatformAnnotation*
	 *     )
	 */
	protected void sequence_Connector(ISerializationContext context, Connector semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Action returns Decrement
	 *     Decrement returns Decrement
	 *
	 * Constraint:
	 *     var=[Variable|ID]
	 */
	protected void sequence_Decrement(ISerializationContext context, Decrement semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.DECREMENT__VAR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.DECREMENT__VAR));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getDecrementAccess().getVarVariableIDTerminalRuleCall_0_0_1(), semanticObject.eGet(ThingMLPackage.Literals.DECREMENT__VAR, false));
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns DoubleLiteral
	 *     CastExpression returns DoubleLiteral
	 *     CastExpression.CastExpression_1_0 returns DoubleLiteral
	 *     OrExpression returns DoubleLiteral
	 *     OrExpression.OrExpression_1_0 returns DoubleLiteral
	 *     AndExpression returns DoubleLiteral
	 *     AndExpression.AndExpression_1_0 returns DoubleLiteral
	 *     Equality returns DoubleLiteral
	 *     Equality.EqualsExpression_1_0_0 returns DoubleLiteral
	 *     Equality.NotEqualsExpression_1_1_0 returns DoubleLiteral
	 *     Comparaison returns DoubleLiteral
	 *     Comparaison.GreaterExpression_1_0_0 returns DoubleLiteral
	 *     Comparaison.LowerExpression_1_1_0 returns DoubleLiteral
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns DoubleLiteral
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns DoubleLiteral
	 *     Addition returns DoubleLiteral
	 *     Addition.PlusExpression_1_0_0 returns DoubleLiteral
	 *     Addition.MinusExpression_1_1_0 returns DoubleLiteral
	 *     Multiplication returns DoubleLiteral
	 *     Multiplication.TimesExpression_1_0_0 returns DoubleLiteral
	 *     Multiplication.DivExpression_1_1_0 returns DoubleLiteral
	 *     Modulo returns DoubleLiteral
	 *     Modulo.ModExpression_1_0 returns DoubleLiteral
	 *     Primary returns DoubleLiteral
	 *     ArrayIndexPostfix returns DoubleLiteral
	 *     ArrayIndexPostfix.ArrayIndex_1_0 returns DoubleLiteral
	 *     AtomicExpression returns DoubleLiteral
	 *     DoubleLiteral returns DoubleLiteral
	 *
	 * Constraint:
	 *     doubleValue=FLOAT
	 */
	protected void sequence_DoubleLiteral(ISerializationContext context, DoubleLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.DOUBLE_LITERAL__DOUBLE_VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.DOUBLE_LITERAL__DOUBLE_VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getDoubleLiteralAccess().getDoubleValueFLOATTerminalRuleCall_0(), semanticObject.getDoubleValue());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns EnumLiteralRef
	 *     CastExpression returns EnumLiteralRef
	 *     CastExpression.CastExpression_1_0 returns EnumLiteralRef
	 *     OrExpression returns EnumLiteralRef
	 *     OrExpression.OrExpression_1_0 returns EnumLiteralRef
	 *     AndExpression returns EnumLiteralRef
	 *     AndExpression.AndExpression_1_0 returns EnumLiteralRef
	 *     Equality returns EnumLiteralRef
	 *     Equality.EqualsExpression_1_0_0 returns EnumLiteralRef
	 *     Equality.NotEqualsExpression_1_1_0 returns EnumLiteralRef
	 *     Comparaison returns EnumLiteralRef
	 *     Comparaison.GreaterExpression_1_0_0 returns EnumLiteralRef
	 *     Comparaison.LowerExpression_1_1_0 returns EnumLiteralRef
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns EnumLiteralRef
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns EnumLiteralRef
	 *     Addition returns EnumLiteralRef
	 *     Addition.PlusExpression_1_0_0 returns EnumLiteralRef
	 *     Addition.MinusExpression_1_1_0 returns EnumLiteralRef
	 *     Multiplication returns EnumLiteralRef
	 *     Multiplication.TimesExpression_1_0_0 returns EnumLiteralRef
	 *     Multiplication.DivExpression_1_1_0 returns EnumLiteralRef
	 *     Modulo returns EnumLiteralRef
	 *     Modulo.ModExpression_1_0 returns EnumLiteralRef
	 *     Primary returns EnumLiteralRef
	 *     ArrayIndexPostfix returns EnumLiteralRef
	 *     ArrayIndexPostfix.ArrayIndex_1_0 returns EnumLiteralRef
	 *     AtomicExpression returns EnumLiteralRef
	 *     EnumLiteralRef returns EnumLiteralRef
	 *
	 * Constraint:
	 *     (enum=[Enumeration|ID] literal=[EnumerationLiteral|ID])
	 */
	protected void sequence_EnumLiteralRef(ISerializationContext context, EnumLiteralRef semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.ENUM_LITERAL_REF__ENUM) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.ENUM_LITERAL_REF__ENUM));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.ENUM_LITERAL_REF__LITERAL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.ENUM_LITERAL_REF__LITERAL));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getEnumLiteralRefAccess().getEnumEnumerationIDTerminalRuleCall_0_0_1(), semanticObject.eGet(ThingMLPackage.Literals.ENUM_LITERAL_REF__ENUM, false));
		feeder.accept(grammarAccess.getEnumLiteralRefAccess().getLiteralEnumerationLiteralIDTerminalRuleCall_2_0_1(), semanticObject.eGet(ThingMLPackage.Literals.ENUM_LITERAL_REF__LITERAL, false));
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns EnumerationLiteral
	 *     AnnotatedElement returns EnumerationLiteral
	 *     EnumerationLiteral returns EnumerationLiteral
	 *
	 * Constraint:
	 *     (name=ID annotations+=PlatformAnnotation*)
	 */
	protected void sequence_EnumerationLiteral(ISerializationContext context, EnumerationLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Enumeration
	 *     AnnotatedElement returns Enumeration
	 *     Type returns Enumeration
	 *     Enumeration returns Enumeration
	 *
	 * Constraint:
	 *     (name=ID annotations+=PlatformAnnotation* literals+=EnumerationLiteral*)
	 */
	protected void sequence_Enumeration(ISerializationContext context, Enumeration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns EqualsExpression
	 *     CastExpression returns EqualsExpression
	 *     CastExpression.CastExpression_1_0 returns EqualsExpression
	 *     OrExpression returns EqualsExpression
	 *     OrExpression.OrExpression_1_0 returns EqualsExpression
	 *     AndExpression returns EqualsExpression
	 *     AndExpression.AndExpression_1_0 returns EqualsExpression
	 *     Equality returns EqualsExpression
	 *     Equality.EqualsExpression_1_0_0 returns EqualsExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns EqualsExpression
	 *
	 * Constraint:
	 *     (lhs=Equality_EqualsExpression_1_0_0 rhs=Comparaison)
	 */
	protected void sequence_Equality(ISerializationContext context, EqualsExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.EQUALS_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.EQUALS_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.EQUALS_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.EQUALS_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getEqualityAccess().getEqualsExpressionLhsAction_1_0_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_0_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns NotEqualsExpression
	 *     CastExpression returns NotEqualsExpression
	 *     CastExpression.CastExpression_1_0 returns NotEqualsExpression
	 *     OrExpression returns NotEqualsExpression
	 *     OrExpression.OrExpression_1_0 returns NotEqualsExpression
	 *     AndExpression returns NotEqualsExpression
	 *     AndExpression.AndExpression_1_0 returns NotEqualsExpression
	 *     Equality returns NotEqualsExpression
	 *     Equality.EqualsExpression_1_0_0 returns NotEqualsExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns NotEqualsExpression
	 *
	 * Constraint:
	 *     (lhs=Equality_NotEqualsExpression_1_1_0 rhs=Comparaison)
	 */
	protected void sequence_Equality(ISerializationContext context, NotEqualsExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.NOT_EQUALS_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.NOT_EQUALS_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.NOT_EQUALS_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.NOT_EQUALS_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getEqualityAccess().getNotEqualsExpressionLhsAction_1_1_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_1_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Action returns ErrorAction
	 *     ErrorAction returns ErrorAction
	 *
	 * Constraint:
	 *     msg=Expression
	 */
	protected void sequence_ErrorAction(ISerializationContext context, ErrorAction semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.ERROR_ACTION__MSG) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.ERROR_ACTION__MSG));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getErrorActionAccess().getMsgExpressionParserRuleCall_1_0(), semanticObject.getMsg());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns EventReference
	 *     CastExpression returns EventReference
	 *     CastExpression.CastExpression_1_0 returns EventReference
	 *     OrExpression returns EventReference
	 *     OrExpression.OrExpression_1_0 returns EventReference
	 *     AndExpression returns EventReference
	 *     AndExpression.AndExpression_1_0 returns EventReference
	 *     Equality returns EventReference
	 *     Equality.EqualsExpression_1_0_0 returns EventReference
	 *     Equality.NotEqualsExpression_1_1_0 returns EventReference
	 *     Comparaison returns EventReference
	 *     Comparaison.GreaterExpression_1_0_0 returns EventReference
	 *     Comparaison.LowerExpression_1_1_0 returns EventReference
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns EventReference
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns EventReference
	 *     Addition returns EventReference
	 *     Addition.PlusExpression_1_0_0 returns EventReference
	 *     Addition.MinusExpression_1_1_0 returns EventReference
	 *     Multiplication returns EventReference
	 *     Multiplication.TimesExpression_1_0_0 returns EventReference
	 *     Multiplication.DivExpression_1_1_0 returns EventReference
	 *     Modulo returns EventReference
	 *     Modulo.ModExpression_1_0 returns EventReference
	 *     Primary returns EventReference
	 *     ArrayIndexPostfix returns EventReference
	 *     ArrayIndexPostfix.ArrayIndex_1_0 returns EventReference
	 *     AtomicExpression returns EventReference
	 *     EventReference returns EventReference
	 *
	 * Constraint:
	 *     (receiveMsg=[Event|ID] parameter=[Parameter|ID])
	 */
	protected void sequence_EventReference(ISerializationContext context, EventReference semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.EVENT_REFERENCE__RECEIVE_MSG) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.EVENT_REFERENCE__RECEIVE_MSG));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.EVENT_REFERENCE__PARAMETER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.EVENT_REFERENCE__PARAMETER));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getEventReferenceAccess().getReceiveMsgEventIDTerminalRuleCall_0_0_1(), semanticObject.eGet(ThingMLPackage.Literals.EVENT_REFERENCE__RECEIVE_MSG, false));
		feeder.accept(grammarAccess.getEventReferenceAccess().getParameterParameterIDTerminalRuleCall_2_0_1(), semanticObject.eGet(ThingMLPackage.Literals.EVENT_REFERENCE__PARAMETER, false));
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns ExternExpression
	 *     CastExpression returns ExternExpression
	 *     CastExpression.CastExpression_1_0 returns ExternExpression
	 *     OrExpression returns ExternExpression
	 *     OrExpression.OrExpression_1_0 returns ExternExpression
	 *     AndExpression returns ExternExpression
	 *     AndExpression.AndExpression_1_0 returns ExternExpression
	 *     Equality returns ExternExpression
	 *     Equality.EqualsExpression_1_0_0 returns ExternExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns ExternExpression
	 *     Comparaison returns ExternExpression
	 *     Comparaison.GreaterExpression_1_0_0 returns ExternExpression
	 *     Comparaison.LowerExpression_1_1_0 returns ExternExpression
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns ExternExpression
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns ExternExpression
	 *     Addition returns ExternExpression
	 *     Addition.PlusExpression_1_0_0 returns ExternExpression
	 *     Addition.MinusExpression_1_1_0 returns ExternExpression
	 *     Multiplication returns ExternExpression
	 *     Multiplication.TimesExpression_1_0_0 returns ExternExpression
	 *     Multiplication.DivExpression_1_1_0 returns ExternExpression
	 *     Modulo returns ExternExpression
	 *     Modulo.ModExpression_1_0 returns ExternExpression
	 *     Primary returns ExternExpression
	 *     ArrayIndexPostfix returns ExternExpression
	 *     ArrayIndexPostfix.ArrayIndex_1_0 returns ExternExpression
	 *     AtomicExpression returns ExternExpression
	 *     ExternExpression returns ExternExpression
	 *
	 * Constraint:
	 *     (expression=STRING_EXT segments+=Expression*)
	 */
	protected void sequence_ExternExpression(ISerializationContext context, ExternExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Action returns ExternStatement
	 *     ExternStatement returns ExternStatement
	 *
	 * Constraint:
	 *     (statement=STRING_EXT segments+=Expression*)
	 */
	protected void sequence_ExternStatement(ISerializationContext context, ExternStatement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns ExternalConnector
	 *     AnnotatedElement returns ExternalConnector
	 *     AbstractConnector returns ExternalConnector
	 *     ExternalConnector returns ExternalConnector
	 *
	 * Constraint:
	 *     (name=ID? inst=[Instance|ID] port=[Port|ID] protocol=[Protocol|ID] annotations+=PlatformAnnotation*)
	 */
	protected void sequence_ExternalConnector(ISerializationContext context, ExternalConnector semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns FinalState
	 *     AnnotatedElement returns FinalState
	 *     State returns FinalState
	 *     FinalState returns FinalState
	 *
	 * Constraint:
	 *     (name=ID annotations+=PlatformAnnotation* entry=Action?)
	 */
	protected void sequence_FinalState(ISerializationContext context, FinalState semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns FunctionCallExpression
	 *     CastExpression returns FunctionCallExpression
	 *     CastExpression.CastExpression_1_0 returns FunctionCallExpression
	 *     OrExpression returns FunctionCallExpression
	 *     OrExpression.OrExpression_1_0 returns FunctionCallExpression
	 *     AndExpression returns FunctionCallExpression
	 *     AndExpression.AndExpression_1_0 returns FunctionCallExpression
	 *     Equality returns FunctionCallExpression
	 *     Equality.EqualsExpression_1_0_0 returns FunctionCallExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns FunctionCallExpression
	 *     Comparaison returns FunctionCallExpression
	 *     Comparaison.GreaterExpression_1_0_0 returns FunctionCallExpression
	 *     Comparaison.LowerExpression_1_1_0 returns FunctionCallExpression
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns FunctionCallExpression
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns FunctionCallExpression
	 *     Addition returns FunctionCallExpression
	 *     Addition.PlusExpression_1_0_0 returns FunctionCallExpression
	 *     Addition.MinusExpression_1_1_0 returns FunctionCallExpression
	 *     Multiplication returns FunctionCallExpression
	 *     Multiplication.TimesExpression_1_0_0 returns FunctionCallExpression
	 *     Multiplication.DivExpression_1_1_0 returns FunctionCallExpression
	 *     Modulo returns FunctionCallExpression
	 *     Modulo.ModExpression_1_0 returns FunctionCallExpression
	 *     Primary returns FunctionCallExpression
	 *     ArrayIndexPostfix returns FunctionCallExpression
	 *     ArrayIndexPostfix.ArrayIndex_1_0 returns FunctionCallExpression
	 *     AtomicExpression returns FunctionCallExpression
	 *     FunctionCallExpression returns FunctionCallExpression
	 *
	 * Constraint:
	 *     (function=[Function|ID] (parameters+=Expression parameters+=Expression*)?)
	 */
	protected void sequence_FunctionCallExpression(ISerializationContext context, FunctionCallExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Action returns FunctionCallStatement
	 *     FunctionCallStatement returns FunctionCallStatement
	 *
	 * Constraint:
	 *     (function=[Function|ID] (parameters+=Expression parameters+=Expression*)?)
	 */
	protected void sequence_FunctionCallStatement(ISerializationContext context, FunctionCallStatement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Action returns Increment
	 *     Increment returns Increment
	 *
	 * Constraint:
	 *     var=[Variable|ID]
	 */
	protected void sequence_Increment(ISerializationContext context, Increment semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.INCREMENT__VAR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.INCREMENT__VAR));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getIncrementAccess().getVarVariableIDTerminalRuleCall_0_0_1(), semanticObject.eGet(ThingMLPackage.Literals.INCREMENT__VAR, false));
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Instance
	 *     AnnotatedElement returns Instance
	 *     Instance returns Instance
	 *
	 * Constraint:
	 *     (name=ID type=[Thing|ID] annotations+=PlatformAnnotation*)
	 */
	protected void sequence_Instance(ISerializationContext context, Instance semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns IntegerLiteral
	 *     CastExpression returns IntegerLiteral
	 *     CastExpression.CastExpression_1_0 returns IntegerLiteral
	 *     OrExpression returns IntegerLiteral
	 *     OrExpression.OrExpression_1_0 returns IntegerLiteral
	 *     AndExpression returns IntegerLiteral
	 *     AndExpression.AndExpression_1_0 returns IntegerLiteral
	 *     Equality returns IntegerLiteral
	 *     Equality.EqualsExpression_1_0_0 returns IntegerLiteral
	 *     Equality.NotEqualsExpression_1_1_0 returns IntegerLiteral
	 *     Comparaison returns IntegerLiteral
	 *     Comparaison.GreaterExpression_1_0_0 returns IntegerLiteral
	 *     Comparaison.LowerExpression_1_1_0 returns IntegerLiteral
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns IntegerLiteral
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns IntegerLiteral
	 *     Addition returns IntegerLiteral
	 *     Addition.PlusExpression_1_0_0 returns IntegerLiteral
	 *     Addition.MinusExpression_1_1_0 returns IntegerLiteral
	 *     Multiplication returns IntegerLiteral
	 *     Multiplication.TimesExpression_1_0_0 returns IntegerLiteral
	 *     Multiplication.DivExpression_1_1_0 returns IntegerLiteral
	 *     Modulo returns IntegerLiteral
	 *     Modulo.ModExpression_1_0 returns IntegerLiteral
	 *     Primary returns IntegerLiteral
	 *     ArrayIndexPostfix returns IntegerLiteral
	 *     ArrayIndexPostfix.ArrayIndex_1_0 returns IntegerLiteral
	 *     AtomicExpression returns IntegerLiteral
	 *     IntegerLiteral returns IntegerLiteral
	 *
	 * Constraint:
	 *     intValue=INT
	 */
	protected void sequence_IntegerLiteral(ISerializationContext context, IntegerLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.INTEGER_LITERAL__INT_VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.INTEGER_LITERAL__INT_VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getIntegerLiteralAccess().getIntValueINTTerminalRuleCall_0(), semanticObject.getIntValue());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns InternalPort
	 *     AnnotatedElement returns InternalPort
	 *     Port returns InternalPort
	 *     InternalPort returns InternalPort
	 *
	 * Constraint:
	 *     (name=ID annotations+=PlatformAnnotation* ((sends+=[Message|ID] sends+=[Message|ID]*) | (receives+=[Message|ID] receives+=[Message|ID]*))*)
	 */
	protected void sequence_InternalPort(ISerializationContext context, InternalPort semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns InternalTransition
	 *     AnnotatedElement returns InternalTransition
	 *     Handler returns InternalTransition
	 *     InternalTransition returns InternalTransition
	 *
	 * Constraint:
	 *     (name=ID? annotations+=PlatformAnnotation* event+=Event* guard=Expression? action=Action?)
	 */
	protected void sequence_InternalTransition(ISerializationContext context, InternalTransition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns LocalVariable
	 *     AnnotatedElement returns LocalVariable
	 *     Variable returns LocalVariable
	 *     Action returns LocalVariable
	 *     LocalVariable returns LocalVariable
	 *
	 * Constraint:
	 *     (readonly?='readonly'? name=ID typeRef=TypeRef init=Expression? annotations+=PlatformAnnotation*)
	 */
	protected void sequence_LocalVariable(ISerializationContext context, LocalVariable semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Action returns LoopAction
	 *     LoopAction returns LoopAction
	 *
	 * Constraint:
	 *     (condition=Expression action=Action)
	 */
	protected void sequence_LoopAction(ISerializationContext context, LoopAction semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.LOOP_ACTION__CONDITION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.LOOP_ACTION__CONDITION));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.LOOP_ACTION__ACTION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.LOOP_ACTION__ACTION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getLoopActionAccess().getConditionExpressionParserRuleCall_2_0(), semanticObject.getCondition());
		feeder.accept(grammarAccess.getLoopActionAccess().getActionActionParserRuleCall_4_0(), semanticObject.getAction());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Message
	 *     AnnotatedElement returns Message
	 *     Message returns Message
	 *
	 * Constraint:
	 *     (name=ID (parameters+=Parameter parameters+=Parameter*)? annotations+=PlatformAnnotation*)
	 */
	protected void sequence_Message(ISerializationContext context, Message semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns ModExpression
	 *     CastExpression returns ModExpression
	 *     CastExpression.CastExpression_1_0 returns ModExpression
	 *     OrExpression returns ModExpression
	 *     OrExpression.OrExpression_1_0 returns ModExpression
	 *     AndExpression returns ModExpression
	 *     AndExpression.AndExpression_1_0 returns ModExpression
	 *     Equality returns ModExpression
	 *     Equality.EqualsExpression_1_0_0 returns ModExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns ModExpression
	 *     Comparaison returns ModExpression
	 *     Comparaison.GreaterExpression_1_0_0 returns ModExpression
	 *     Comparaison.LowerExpression_1_1_0 returns ModExpression
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns ModExpression
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns ModExpression
	 *     Addition returns ModExpression
	 *     Addition.PlusExpression_1_0_0 returns ModExpression
	 *     Addition.MinusExpression_1_1_0 returns ModExpression
	 *     Multiplication returns ModExpression
	 *     Multiplication.TimesExpression_1_0_0 returns ModExpression
	 *     Multiplication.DivExpression_1_1_0 returns ModExpression
	 *     Modulo returns ModExpression
	 *
	 * Constraint:
	 *     (lhs=Modulo_ModExpression_1_0 rhs=Expression)
	 */
	protected void sequence_Modulo(ISerializationContext context, ModExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.MOD_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.MOD_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.MOD_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.MOD_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getModuloAccess().getModExpressionLhsAction_1_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getModuloAccess().getRhsExpressionParserRuleCall_1_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns DivExpression
	 *     CastExpression returns DivExpression
	 *     CastExpression.CastExpression_1_0 returns DivExpression
	 *     OrExpression returns DivExpression
	 *     OrExpression.OrExpression_1_0 returns DivExpression
	 *     AndExpression returns DivExpression
	 *     AndExpression.AndExpression_1_0 returns DivExpression
	 *     Equality returns DivExpression
	 *     Equality.EqualsExpression_1_0_0 returns DivExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns DivExpression
	 *     Comparaison returns DivExpression
	 *     Comparaison.GreaterExpression_1_0_0 returns DivExpression
	 *     Comparaison.LowerExpression_1_1_0 returns DivExpression
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns DivExpression
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns DivExpression
	 *     Addition returns DivExpression
	 *     Addition.PlusExpression_1_0_0 returns DivExpression
	 *     Addition.MinusExpression_1_1_0 returns DivExpression
	 *     Multiplication returns DivExpression
	 *     Multiplication.TimesExpression_1_0_0 returns DivExpression
	 *     Multiplication.DivExpression_1_1_0 returns DivExpression
	 *
	 * Constraint:
	 *     (lhs=Multiplication_DivExpression_1_1_0 rhs=Modulo)
	 */
	protected void sequence_Multiplication(ISerializationContext context, DivExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.DIV_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.DIV_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.DIV_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.DIV_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getMultiplicationAccess().getDivExpressionLhsAction_1_1_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_1_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns TimesExpression
	 *     CastExpression returns TimesExpression
	 *     CastExpression.CastExpression_1_0 returns TimesExpression
	 *     OrExpression returns TimesExpression
	 *     OrExpression.OrExpression_1_0 returns TimesExpression
	 *     AndExpression returns TimesExpression
	 *     AndExpression.AndExpression_1_0 returns TimesExpression
	 *     Equality returns TimesExpression
	 *     Equality.EqualsExpression_1_0_0 returns TimesExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns TimesExpression
	 *     Comparaison returns TimesExpression
	 *     Comparaison.GreaterExpression_1_0_0 returns TimesExpression
	 *     Comparaison.LowerExpression_1_1_0 returns TimesExpression
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns TimesExpression
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns TimesExpression
	 *     Addition returns TimesExpression
	 *     Addition.PlusExpression_1_0_0 returns TimesExpression
	 *     Addition.MinusExpression_1_1_0 returns TimesExpression
	 *     Multiplication returns TimesExpression
	 *     Multiplication.TimesExpression_1_0_0 returns TimesExpression
	 *     Multiplication.DivExpression_1_1_0 returns TimesExpression
	 *
	 * Constraint:
	 *     (lhs=Multiplication_TimesExpression_1_0_0 rhs=Modulo)
	 */
	protected void sequence_Multiplication(ISerializationContext context, TimesExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.TIMES_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.TIMES_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.TIMES_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.TIMES_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getMultiplicationAccess().getTimesExpressionLhsAction_1_0_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_0_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns NamedElement
	 *
	 * Constraint:
	 *     name=ID
	 */
	protected void sequence_NamedElement(ISerializationContext context, NamedElement semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.NAMED_ELEMENT__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.NAMED_ELEMENT__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getNamedElementAccess().getNameIDTerminalRuleCall_14_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns ObjectType
	 *     AnnotatedElement returns ObjectType
	 *     Type returns ObjectType
	 *     ObjectType returns ObjectType
	 *
	 * Constraint:
	 *     (name=ID annotations+=PlatformAnnotation*)
	 */
	protected void sequence_ObjectType(ISerializationContext context, ObjectType semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns OrExpression
	 *     CastExpression returns OrExpression
	 *     CastExpression.CastExpression_1_0 returns OrExpression
	 *     OrExpression returns OrExpression
	 *     OrExpression.OrExpression_1_0 returns OrExpression
	 *
	 * Constraint:
	 *     (lhs=OrExpression_OrExpression_1_0 rhs=AndExpression)
	 */
	protected void sequence_OrExpression(ISerializationContext context, OrExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.OR_EXPRESSION__LHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.OR_EXPRESSION__LHS));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.OR_EXPRESSION__RHS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.OR_EXPRESSION__RHS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getOrExpressionAccess().getOrExpressionLhsAction_1_0(), semanticObject.getLhs());
		feeder.accept(grammarAccess.getOrExpressionAccess().getRhsAndExpressionParserRuleCall_1_2_0(), semanticObject.getRhs());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Parameter
	 *     AnnotatedElement returns Parameter
	 *     Variable returns Parameter
	 *     Parameter returns Parameter
	 *
	 * Constraint:
	 *     (name=ID typeRef=TypeRef annotations+=PlatformAnnotation*)
	 */
	protected void sequence_Parameter(ISerializationContext context, org.thingml.xtext.thingML.Parameter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     PlatformAnnotation returns PlatformAnnotation
	 *
	 * Constraint:
	 *     (name=ANNOTATION_ID value=STRING_LIT)
	 */
	protected void sequence_PlatformAnnotation(ISerializationContext context, PlatformAnnotation semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.PLATFORM_ANNOTATION__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.PLATFORM_ANNOTATION__NAME));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.PLATFORM_ANNOTATION__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.PLATFORM_ANNOTATION__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getPlatformAnnotationAccess().getNameANNOTATION_IDTerminalRuleCall_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getPlatformAnnotationAccess().getValueSTRING_LITTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns ExpressionGroup
	 *     CastExpression returns ExpressionGroup
	 *     CastExpression.CastExpression_1_0 returns ExpressionGroup
	 *     OrExpression returns ExpressionGroup
	 *     OrExpression.OrExpression_1_0 returns ExpressionGroup
	 *     AndExpression returns ExpressionGroup
	 *     AndExpression.AndExpression_1_0 returns ExpressionGroup
	 *     Equality returns ExpressionGroup
	 *     Equality.EqualsExpression_1_0_0 returns ExpressionGroup
	 *     Equality.NotEqualsExpression_1_1_0 returns ExpressionGroup
	 *     Comparaison returns ExpressionGroup
	 *     Comparaison.GreaterExpression_1_0_0 returns ExpressionGroup
	 *     Comparaison.LowerExpression_1_1_0 returns ExpressionGroup
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns ExpressionGroup
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns ExpressionGroup
	 *     Addition returns ExpressionGroup
	 *     Addition.PlusExpression_1_0_0 returns ExpressionGroup
	 *     Addition.MinusExpression_1_1_0 returns ExpressionGroup
	 *     Multiplication returns ExpressionGroup
	 *     Multiplication.TimesExpression_1_0_0 returns ExpressionGroup
	 *     Multiplication.DivExpression_1_1_0 returns ExpressionGroup
	 *     Modulo returns ExpressionGroup
	 *     Modulo.ModExpression_1_0 returns ExpressionGroup
	 *     Primary returns ExpressionGroup
	 *
	 * Constraint:
	 *     term=Expression
	 */
	protected void sequence_Primary(ISerializationContext context, ExpressionGroup semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.EXPRESSION_GROUP__TERM) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.EXPRESSION_GROUP__TERM));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getPrimaryAccess().getTermExpressionParserRuleCall_0_2_0(), semanticObject.getTerm());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns NotExpression
	 *     CastExpression returns NotExpression
	 *     CastExpression.CastExpression_1_0 returns NotExpression
	 *     OrExpression returns NotExpression
	 *     OrExpression.OrExpression_1_0 returns NotExpression
	 *     AndExpression returns NotExpression
	 *     AndExpression.AndExpression_1_0 returns NotExpression
	 *     Equality returns NotExpression
	 *     Equality.EqualsExpression_1_0_0 returns NotExpression
	 *     Equality.NotEqualsExpression_1_1_0 returns NotExpression
	 *     Comparaison returns NotExpression
	 *     Comparaison.GreaterExpression_1_0_0 returns NotExpression
	 *     Comparaison.LowerExpression_1_1_0 returns NotExpression
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns NotExpression
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns NotExpression
	 *     Addition returns NotExpression
	 *     Addition.PlusExpression_1_0_0 returns NotExpression
	 *     Addition.MinusExpression_1_1_0 returns NotExpression
	 *     Multiplication returns NotExpression
	 *     Multiplication.TimesExpression_1_0_0 returns NotExpression
	 *     Multiplication.DivExpression_1_1_0 returns NotExpression
	 *     Modulo returns NotExpression
	 *     Modulo.ModExpression_1_0 returns NotExpression
	 *     Primary returns NotExpression
	 *
	 * Constraint:
	 *     term=Primary
	 */
	protected void sequence_Primary(ISerializationContext context, NotExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.NOT_EXPRESSION__TERM) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.NOT_EXPRESSION__TERM));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getPrimaryAccess().getTermPrimaryParserRuleCall_1_2_0(), semanticObject.getTerm());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns UnaryMinus
	 *     CastExpression returns UnaryMinus
	 *     CastExpression.CastExpression_1_0 returns UnaryMinus
	 *     OrExpression returns UnaryMinus
	 *     OrExpression.OrExpression_1_0 returns UnaryMinus
	 *     AndExpression returns UnaryMinus
	 *     AndExpression.AndExpression_1_0 returns UnaryMinus
	 *     Equality returns UnaryMinus
	 *     Equality.EqualsExpression_1_0_0 returns UnaryMinus
	 *     Equality.NotEqualsExpression_1_1_0 returns UnaryMinus
	 *     Comparaison returns UnaryMinus
	 *     Comparaison.GreaterExpression_1_0_0 returns UnaryMinus
	 *     Comparaison.LowerExpression_1_1_0 returns UnaryMinus
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns UnaryMinus
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns UnaryMinus
	 *     Addition returns UnaryMinus
	 *     Addition.PlusExpression_1_0_0 returns UnaryMinus
	 *     Addition.MinusExpression_1_1_0 returns UnaryMinus
	 *     Multiplication returns UnaryMinus
	 *     Multiplication.TimesExpression_1_0_0 returns UnaryMinus
	 *     Multiplication.DivExpression_1_1_0 returns UnaryMinus
	 *     Modulo returns UnaryMinus
	 *     Modulo.ModExpression_1_0 returns UnaryMinus
	 *     Primary returns UnaryMinus
	 *
	 * Constraint:
	 *     term=Primary
	 */
	protected void sequence_Primary(ISerializationContext context, UnaryMinus semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.UNARY_MINUS__TERM) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.UNARY_MINUS__TERM));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getPrimaryAccess().getTermPrimaryParserRuleCall_2_2_0(), semanticObject.getTerm());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns PrimitiveType
	 *     AnnotatedElement returns PrimitiveType
	 *     Type returns PrimitiveType
	 *     PrimitiveType returns PrimitiveType
	 *
	 * Constraint:
	 *     (name=ID ByteSize=INT annotations+=PlatformAnnotation*)
	 */
	protected void sequence_PrimitiveType(ISerializationContext context, PrimitiveType semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Action returns PrintAction
	 *     PrintAction returns PrintAction
	 *
	 * Constraint:
	 *     msg=Expression
	 */
	protected void sequence_PrintAction(ISerializationContext context, PrintAction semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.PRINT_ACTION__MSG) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.PRINT_ACTION__MSG));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getPrintActionAccess().getMsgExpressionParserRuleCall_1_0(), semanticObject.getMsg());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     AnnotatedElement returns PropertyAssign
	 *     PropertyAssign returns PropertyAssign
	 *
	 * Constraint:
	 *     (property=[Property|ID] index+=Expression* init=Expression annotations+=PlatformAnnotation*)
	 */
	protected void sequence_PropertyAssign(ISerializationContext context, PropertyAssign semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns PropertyReference
	 *     CastExpression returns PropertyReference
	 *     CastExpression.CastExpression_1_0 returns PropertyReference
	 *     OrExpression returns PropertyReference
	 *     OrExpression.OrExpression_1_0 returns PropertyReference
	 *     AndExpression returns PropertyReference
	 *     AndExpression.AndExpression_1_0 returns PropertyReference
	 *     Equality returns PropertyReference
	 *     Equality.EqualsExpression_1_0_0 returns PropertyReference
	 *     Equality.NotEqualsExpression_1_1_0 returns PropertyReference
	 *     Comparaison returns PropertyReference
	 *     Comparaison.GreaterExpression_1_0_0 returns PropertyReference
	 *     Comparaison.LowerExpression_1_1_0 returns PropertyReference
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns PropertyReference
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns PropertyReference
	 *     Addition returns PropertyReference
	 *     Addition.PlusExpression_1_0_0 returns PropertyReference
	 *     Addition.MinusExpression_1_1_0 returns PropertyReference
	 *     Multiplication returns PropertyReference
	 *     Multiplication.TimesExpression_1_0_0 returns PropertyReference
	 *     Multiplication.DivExpression_1_1_0 returns PropertyReference
	 *     Modulo returns PropertyReference
	 *     Modulo.ModExpression_1_0 returns PropertyReference
	 *     Primary returns PropertyReference
	 *     ArrayIndexPostfix returns PropertyReference
	 *     ArrayIndexPostfix.ArrayIndex_1_0 returns PropertyReference
	 *     AtomicExpression returns PropertyReference
	 *     PropertyReference returns PropertyReference
	 *
	 * Constraint:
	 *     property=[Variable|ID]
	 */
	protected void sequence_PropertyReference(ISerializationContext context, PropertyReference semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.PROPERTY_REFERENCE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.PROPERTY_REFERENCE__PROPERTY));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getPropertyReferenceAccess().getPropertyVariableIDTerminalRuleCall_0_1(), semanticObject.eGet(ThingMLPackage.Literals.PROPERTY_REFERENCE__PROPERTY, false));
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Property
	 *     AnnotatedElement returns Property
	 *     Variable returns Property
	 *     Property returns Property
	 *
	 * Constraint:
	 *     (readonly?='readonly'? name=ID typeRef=TypeRef init=Expression? annotations+=PlatformAnnotation*)
	 */
	protected void sequence_Property(ISerializationContext context, Property semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Protocol
	 *     AnnotatedElement returns Protocol
	 *     Protocol returns Protocol
	 *
	 * Constraint:
	 *     (name=ID annotations+=PlatformAnnotation*)
	 */
	protected void sequence_Protocol(ISerializationContext context, Protocol semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns ProvidedPort
	 *     AnnotatedElement returns ProvidedPort
	 *     Port returns ProvidedPort
	 *     ProvidedPort returns ProvidedPort
	 *
	 * Constraint:
	 *     (name=ID annotations+=PlatformAnnotation* ((sends+=[Message|ID] sends+=[Message|ID]*) | (receives+=[Message|ID] receives+=[Message|ID]*))*)
	 */
	protected void sequence_ProvidedPort(ISerializationContext context, ProvidedPort semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns ReceiveMessage
	 *     Event returns ReceiveMessage
	 *     ReceiveMessage returns ReceiveMessage
	 *
	 * Constraint:
	 *     (name=ID? port=[Port|ID] message=[Message|ID])
	 */
	protected void sequence_ReceiveMessage(ISerializationContext context, ReceiveMessage semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Region
	 *     AnnotatedElement returns Region
	 *     Region returns Region
	 *     StateContainer returns Region
	 *
	 * Constraint:
	 *     (name=ID? initial=[State|ID] history?='history'? annotations+=PlatformAnnotation* substate+=State*)
	 */
	protected void sequence_Region(ISerializationContext context, Region semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns RequiredPort
	 *     AnnotatedElement returns RequiredPort
	 *     Port returns RequiredPort
	 *     RequiredPort returns RequiredPort
	 *
	 * Constraint:
	 *     (
	 *         optional?='optional'? 
	 *         name=ID 
	 *         annotations+=PlatformAnnotation* 
	 *         ((sends+=[Message|ID] sends+=[Message|ID]*) | (receives+=[Message|ID] receives+=[Message|ID]*))*
	 *     )
	 */
	protected void sequence_RequiredPort(ISerializationContext context, RequiredPort semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Action returns ReturnAction
	 *     ReturnAction returns ReturnAction
	 *
	 * Constraint:
	 *     exp=Expression
	 */
	protected void sequence_ReturnAction(ISerializationContext context, ReturnAction semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.RETURN_ACTION__EXP) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.RETURN_ACTION__EXP));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getReturnActionAccess().getExpExpressionParserRuleCall_1_0(), semanticObject.getExp());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Action returns SendAction
	 *     SendAction returns SendAction
	 *
	 * Constraint:
	 *     (port=[Port|ID] message=[Message|ID] (parameters+=Expression parameters+=Expression*)?)
	 */
	protected void sequence_SendAction(ISerializationContext context, SendAction semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Session
	 *     AnnotatedElement returns Session
	 *     Session returns Session
	 *     StateContainer returns Session
	 *
	 * Constraint:
	 *     (name=ID (maxInstances=IntegerLiteral | maxInstances=PropertyReference)? initial=[State|ID] annotations+=PlatformAnnotation* substate+=State*)
	 */
	protected void sequence_Session(ISerializationContext context, Session semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Action returns StartSession
	 *     StartSession returns StartSession
	 *
	 * Constraint:
	 *     session=[Session|ID]
	 */
	protected void sequence_StartSession(ISerializationContext context, StartSession semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.START_SESSION__SESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.START_SESSION__SESSION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getStartSessionAccess().getSessionSessionIDTerminalRuleCall_1_0_1(), semanticObject.eGet(ThingMLPackage.Literals.START_SESSION__SESSION, false));
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns StateContainer
	 *     AnnotatedElement returns StateContainer
	 *     StateContainer returns StateContainer
	 *
	 * Constraint:
	 *     (initial=[State|ID] history?='history'? substate+=State*)
	 */
	protected void sequence_StateContainer(ISerializationContext context, StateContainer semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     StateMachine returns CompositeState
	 *
	 * Constraint:
	 *     (
	 *         name=ID? 
	 *         initial=[State|ID] 
	 *         history?='history'? 
	 *         annotations+=PlatformAnnotation* 
	 *         properties+=Property* 
	 *         entry=Action? 
	 *         exit=Action? 
	 *         (substate+=State | internal+=InternalTransition)* 
	 *         region+=Region? 
	 *         (session+=Session? region+=Region?)*
	 *     )
	 */
	protected void sequence_StateMachine(ISerializationContext context, CompositeState semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns State
	 *     AnnotatedElement returns State
	 *     State returns State
	 *
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         annotations+=PlatformAnnotation* 
	 *         properties+=Property* 
	 *         entry=Action? 
	 *         exit=Action? 
	 *         (internal+=InternalTransition | outgoing+=Transition)*
	 *     )
	 */
	protected void sequence_State(ISerializationContext context, State semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Expression returns StringLiteral
	 *     CastExpression returns StringLiteral
	 *     CastExpression.CastExpression_1_0 returns StringLiteral
	 *     OrExpression returns StringLiteral
	 *     OrExpression.OrExpression_1_0 returns StringLiteral
	 *     AndExpression returns StringLiteral
	 *     AndExpression.AndExpression_1_0 returns StringLiteral
	 *     Equality returns StringLiteral
	 *     Equality.EqualsExpression_1_0_0 returns StringLiteral
	 *     Equality.NotEqualsExpression_1_1_0 returns StringLiteral
	 *     Comparaison returns StringLiteral
	 *     Comparaison.GreaterExpression_1_0_0 returns StringLiteral
	 *     Comparaison.LowerExpression_1_1_0 returns StringLiteral
	 *     Comparaison.GreaterOrEqualExpression_1_2_0 returns StringLiteral
	 *     Comparaison.LowerOrEqualExpression_1_3_0 returns StringLiteral
	 *     Addition returns StringLiteral
	 *     Addition.PlusExpression_1_0_0 returns StringLiteral
	 *     Addition.MinusExpression_1_1_0 returns StringLiteral
	 *     Multiplication returns StringLiteral
	 *     Multiplication.TimesExpression_1_0_0 returns StringLiteral
	 *     Multiplication.DivExpression_1_1_0 returns StringLiteral
	 *     Modulo returns StringLiteral
	 *     Modulo.ModExpression_1_0 returns StringLiteral
	 *     Primary returns StringLiteral
	 *     ArrayIndexPostfix returns StringLiteral
	 *     ArrayIndexPostfix.ArrayIndex_1_0 returns StringLiteral
	 *     AtomicExpression returns StringLiteral
	 *     StringLiteral returns StringLiteral
	 *
	 * Constraint:
	 *     stringValue=STRING_LIT
	 */
	protected void sequence_StringLiteral(ISerializationContext context, StringLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.STRING_LITERAL__STRING_VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.STRING_LITERAL__STRING_VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getStringLiteralAccess().getStringValueSTRING_LITTerminalRuleCall_0(), semanticObject.getStringValue());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ThingMLModel returns ThingMLModel
	 *
	 * Constraint:
	 *     (importURI+=STRING_LIT+ | (importURI+=STRING_LIT+ (types+=Type | protocols+=Protocol | configs+=Configuration)+))?
	 */
	protected void sequence_ThingMLModel(ISerializationContext context, ThingMLModel semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Thing
	 *     AnnotatedElement returns Thing
	 *     Type returns Thing
	 *     Thing returns Thing
	 *
	 * Constraint:
	 *     (
	 *         fragment?='fragment'? 
	 *         name=ID 
	 *         (includes+=[Thing|ID] includes+=[Thing|ID]*)? 
	 *         annotations+=PlatformAnnotation* 
	 *         (
	 *             messages+=Message | 
	 *             ports+=Port | 
	 *             properties+=Property | 
	 *             functions+=Function | 
	 *             assign+=PropertyAssign | 
	 *             behaviour+=StateMachine
	 *         )*
	 *     )
	 */
	protected void sequence_Thing(ISerializationContext context, Thing semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Transition
	 *     AnnotatedElement returns Transition
	 *     Handler returns Transition
	 *     Transition returns Transition
	 *
	 * Constraint:
	 *     (
	 *         name=ID? 
	 *         target=[State|ID] 
	 *         annotations+=PlatformAnnotation* 
	 *         event+=Event* 
	 *         guard=Expression? 
	 *         action=Action?
	 *     )
	 */
	protected void sequence_Transition(ISerializationContext context, Transition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns TypeRef
	 *
	 * Constraint:
	 *     (type=[Type|ID] (isArray?='[' cardinality=Expression?)?)
	 */
	protected void sequence_TypeRef(ISerializationContext context, TypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Action returns VariableAssignment
	 *     VariableAssignment returns VariableAssignment
	 *
	 * Constraint:
	 *     (property=[Variable|ID] index+=Expression* expression=Expression)
	 */
	protected void sequence_VariableAssignment(ISerializationContext context, VariableAssignment semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     NamedElement returns Variable
	 *     AnnotatedElement returns Variable
	 *     Variable returns Variable
	 *
	 * Constraint:
	 *     (name=ID typeRef=TypeRef)
	 */
	protected void sequence_Variable(ISerializationContext context, Variable semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.NAMED_ELEMENT__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.NAMED_ELEMENT__NAME));
			if (transientValues.isValueTransient(semanticObject, ThingMLPackage.Literals.VARIABLE__TYPE_REF) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ThingMLPackage.Literals.VARIABLE__TYPE_REF));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getVariableAccess().getNameIDTerminalRuleCall_0_1_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getVariableAccess().getTypeRefTypeRefParserRuleCall_0_3_0(), semanticObject.getTypeRef());
		feeder.finish();
	}
	
	
}
