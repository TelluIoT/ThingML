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
package org.sintef.thingml.resource.thingml.util;

/**
 * This class provides basic infrastructure to interpret models. To implement
 * concrete interpreters, subclass this abstract interpreter and override the
 * interprete_* methods. The interpretation can be customized by binding the two
 * type parameters (ResultType, ContextType). The former is returned by all
 * interprete_* methods, while the latter is passed from method to method while
 * traversing the model. The concrete traversal strategy can also be exchanged.
 * One can use a static traversal strategy by pushing all objects to interpret on
 * the interpretation stack (using addObjectToInterprete()) before calling
 * interprete(). Alternatively, the traversal strategy can be dynamic by pushing
 * objects on the interpretation stack during interpretation.
 */
public class AbstractThingmlInterpreter<ResultType, ContextType> {
	
	private java.util.Stack<org.eclipse.emf.ecore.EObject> interpretationStack = new java.util.Stack<org.eclipse.emf.ecore.EObject>();
	private java.util.List<org.sintef.thingml.resource.thingml.IThingmlInterpreterListener> listeners = new java.util.ArrayList<org.sintef.thingml.resource.thingml.IThingmlInterpreterListener>();
	private org.eclipse.emf.ecore.EObject nextObjectToInterprete;
	private Object currentContext;
	
	public ResultType interprete(ContextType context) {
		ResultType result = null;
		org.eclipse.emf.ecore.EObject next = null;
		currentContext = context;
		while (!interpretationStack.empty()) {
			try {
				next = interpretationStack.pop();
			} catch (java.util.EmptyStackException ese) {
				// this can happen when the interpreter was terminated between the call to empty()
				// and pop()
				break;
			}
			nextObjectToInterprete = next;
			notifyListeners(next);
			result = interprete(next, context);
			if (!continueInterpretation(context, result)) {
				break;
			}
		}
		currentContext = null;
		return result;
	}
	
	/**
	 * Override this method to stop the overall interpretation depending on the result
	 * of the interpretation of a single model elements.
	 */
	public boolean continueInterpretation(ContextType context, ResultType result) {
		return true;
	}
	
	public ResultType interprete(org.eclipse.emf.ecore.EObject object, ContextType context) {
		ResultType result = null;
		if (object instanceof org.sintef.thingml.ThingMLModel) {
			result = interprete_org_sintef_thingml_ThingMLModel((org.sintef.thingml.ThingMLModel) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Function) {
			result = interprete_org_sintef_thingml_Function((org.sintef.thingml.Function) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Message) {
			result = interprete_org_sintef_thingml_Message((org.sintef.thingml.Message) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Thing) {
			result = interprete_org_sintef_thingml_Thing((org.sintef.thingml.Thing) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Parameter) {
			result = interprete_org_sintef_thingml_Parameter((org.sintef.thingml.Parameter) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Variable) {
			result = interprete_org_sintef_thingml_Variable((org.sintef.thingml.Variable) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Enumeration) {
			result = interprete_org_sintef_thingml_Enumeration((org.sintef.thingml.Enumeration) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Type) {
			result = interprete_org_sintef_thingml_Type((org.sintef.thingml.Type) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Property) {
			result = interprete_org_sintef_thingml_Property((org.sintef.thingml.Property) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.PropertyAssign) {
			result = interprete_org_sintef_thingml_PropertyAssign((org.sintef.thingml.PropertyAssign) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.PrimitiveType) {
			result = interprete_org_sintef_thingml_PrimitiveType((org.sintef.thingml.PrimitiveType) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.EnumerationLiteral) {
			result = interprete_org_sintef_thingml_EnumerationLiteral((org.sintef.thingml.EnumerationLiteral) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.StateMachine) {
			result = interprete_org_sintef_thingml_StateMachine((org.sintef.thingml.StateMachine) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Transition) {
			result = interprete_org_sintef_thingml_Transition((org.sintef.thingml.Transition) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.InternalTransition) {
			result = interprete_org_sintef_thingml_InternalTransition((org.sintef.thingml.InternalTransition) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Handler) {
			result = interprete_org_sintef_thingml_Handler((org.sintef.thingml.Handler) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.CompositeState) {
			result = interprete_org_sintef_thingml_CompositeState((org.sintef.thingml.CompositeState) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ParallelRegion) {
			result = interprete_org_sintef_thingml_ParallelRegion((org.sintef.thingml.ParallelRegion) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Region) {
			result = interprete_org_sintef_thingml_Region((org.sintef.thingml.Region) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ThingMLElement) {
			result = interprete_org_sintef_thingml_ThingMLElement((org.sintef.thingml.ThingMLElement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.TypedElement) {
			result = interprete_org_sintef_thingml_TypedElement((org.sintef.thingml.TypedElement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.PlatformAnnotation) {
			result = interprete_org_sintef_thingml_PlatformAnnotation((org.sintef.thingml.PlatformAnnotation) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.State) {
			result = interprete_org_sintef_thingml_State((org.sintef.thingml.State) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.AnnotatedElement) {
			result = interprete_org_sintef_thingml_AnnotatedElement((org.sintef.thingml.AnnotatedElement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ActionBlock) {
			result = interprete_org_sintef_thingml_ActionBlock((org.sintef.thingml.ActionBlock) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ExternStatement) {
			result = interprete_org_sintef_thingml_ExternStatement((org.sintef.thingml.ExternStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Action) {
			result = interprete_org_sintef_thingml_Action((org.sintef.thingml.Action) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ExternExpression) {
			result = interprete_org_sintef_thingml_ExternExpression((org.sintef.thingml.ExternExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Expression) {
			result = interprete_org_sintef_thingml_Expression((org.sintef.thingml.Expression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.SendAction) {
			result = interprete_org_sintef_thingml_SendAction((org.sintef.thingml.SendAction) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.VariableAssignment) {
			result = interprete_org_sintef_thingml_VariableAssignment((org.sintef.thingml.VariableAssignment) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ReceiveMessage) {
			result = interprete_org_sintef_thingml_ReceiveMessage((org.sintef.thingml.ReceiveMessage) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Event) {
			result = interprete_org_sintef_thingml_Event((org.sintef.thingml.Event) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.RequiredPort) {
			result = interprete_org_sintef_thingml_RequiredPort((org.sintef.thingml.RequiredPort) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ProvidedPort) {
			result = interprete_org_sintef_thingml_ProvidedPort((org.sintef.thingml.ProvidedPort) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Port) {
			result = interprete_org_sintef_thingml_Port((org.sintef.thingml.Port) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.EnumLiteralRef) {
			result = interprete_org_sintef_thingml_EnumLiteralRef((org.sintef.thingml.EnumLiteralRef) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.IntegerLiteral) {
			result = interprete_org_sintef_thingml_IntegerLiteral((org.sintef.thingml.IntegerLiteral) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.BooleanLiteral) {
			result = interprete_org_sintef_thingml_BooleanLiteral((org.sintef.thingml.BooleanLiteral) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.StringLiteral) {
			result = interprete_org_sintef_thingml_StringLiteral((org.sintef.thingml.StringLiteral) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.DoubleLiteral) {
			result = interprete_org_sintef_thingml_DoubleLiteral((org.sintef.thingml.DoubleLiteral) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Literal) {
			result = interprete_org_sintef_thingml_Literal((org.sintef.thingml.Literal) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.NotExpression) {
			result = interprete_org_sintef_thingml_NotExpression((org.sintef.thingml.NotExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.UnaryMinus) {
			result = interprete_org_sintef_thingml_UnaryMinus((org.sintef.thingml.UnaryMinus) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.UnaryExpression) {
			result = interprete_org_sintef_thingml_UnaryExpression((org.sintef.thingml.UnaryExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.PlusExpression) {
			result = interprete_org_sintef_thingml_PlusExpression((org.sintef.thingml.PlusExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.MinusExpression) {
			result = interprete_org_sintef_thingml_MinusExpression((org.sintef.thingml.MinusExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.TimesExpression) {
			result = interprete_org_sintef_thingml_TimesExpression((org.sintef.thingml.TimesExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.DivExpression) {
			result = interprete_org_sintef_thingml_DivExpression((org.sintef.thingml.DivExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ModExpression) {
			result = interprete_org_sintef_thingml_ModExpression((org.sintef.thingml.ModExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.EqualsExpression) {
			result = interprete_org_sintef_thingml_EqualsExpression((org.sintef.thingml.EqualsExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.GreaterExpression) {
			result = interprete_org_sintef_thingml_GreaterExpression((org.sintef.thingml.GreaterExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.LowerExpression) {
			result = interprete_org_sintef_thingml_LowerExpression((org.sintef.thingml.LowerExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.AndExpression) {
			result = interprete_org_sintef_thingml_AndExpression((org.sintef.thingml.AndExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.OrExpression) {
			result = interprete_org_sintef_thingml_OrExpression((org.sintef.thingml.OrExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.BinaryExpression) {
			result = interprete_org_sintef_thingml_BinaryExpression((org.sintef.thingml.BinaryExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.LoopAction) {
			result = interprete_org_sintef_thingml_LoopAction((org.sintef.thingml.LoopAction) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ConditionalAction) {
			result = interprete_org_sintef_thingml_ConditionalAction((org.sintef.thingml.ConditionalAction) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ControlStructure) {
			result = interprete_org_sintef_thingml_ControlStructure((org.sintef.thingml.ControlStructure) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.PropertyReference) {
			result = interprete_org_sintef_thingml_PropertyReference((org.sintef.thingml.PropertyReference) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ArrayIndex) {
			result = interprete_org_sintef_thingml_ArrayIndex((org.sintef.thingml.ArrayIndex) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ExpressionGroup) {
			result = interprete_org_sintef_thingml_ExpressionGroup((org.sintef.thingml.ExpressionGroup) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ReturnAction) {
			result = interprete_org_sintef_thingml_ReturnAction((org.sintef.thingml.ReturnAction) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.PrintAction) {
			result = interprete_org_sintef_thingml_PrintAction((org.sintef.thingml.PrintAction) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ErrorAction) {
			result = interprete_org_sintef_thingml_ErrorAction((org.sintef.thingml.ErrorAction) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Configuration) {
			result = interprete_org_sintef_thingml_Configuration((org.sintef.thingml.Configuration) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Instance) {
			result = interprete_org_sintef_thingml_Instance((org.sintef.thingml.Instance) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Connector) {
			result = interprete_org_sintef_thingml_Connector((org.sintef.thingml.Connector) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ExternalConnector) {
			result = interprete_org_sintef_thingml_ExternalConnector((org.sintef.thingml.ExternalConnector) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.AbstractConnector) {
			result = interprete_org_sintef_thingml_AbstractConnector((org.sintef.thingml.AbstractConnector) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ConfigPropertyAssign) {
			result = interprete_org_sintef_thingml_ConfigPropertyAssign((org.sintef.thingml.ConfigPropertyAssign) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ConfigInclude) {
			result = interprete_org_sintef_thingml_ConfigInclude((org.sintef.thingml.ConfigInclude) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.InstanceRef) {
			result = interprete_org_sintef_thingml_InstanceRef((org.sintef.thingml.InstanceRef) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.FunctionCallStatement) {
			result = interprete_org_sintef_thingml_FunctionCallStatement((org.sintef.thingml.FunctionCallStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.FunctionCallExpression) {
			result = interprete_org_sintef_thingml_FunctionCallExpression((org.sintef.thingml.FunctionCallExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.FunctionCall) {
			result = interprete_org_sintef_thingml_FunctionCall((org.sintef.thingml.FunctionCall) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.LocalVariable) {
			result = interprete_org_sintef_thingml_LocalVariable((org.sintef.thingml.LocalVariable) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Stream) {
			result = interprete_org_sintef_thingml_Stream((org.sintef.thingml.Stream) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.StreamExpression) {
			result = interprete_org_sintef_thingml_StreamExpression((org.sintef.thingml.StreamExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.StreamParamReference) {
			result = interprete_org_sintef_thingml_StreamParamReference((org.sintef.thingml.StreamParamReference) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.StreamOutput) {
			result = interprete_org_sintef_thingml_StreamOutput((org.sintef.thingml.StreamOutput) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.JoinSources) {
			result = interprete_org_sintef_thingml_JoinSources((org.sintef.thingml.JoinSources) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.MergeSources) {
			result = interprete_org_sintef_thingml_MergeSources((org.sintef.thingml.MergeSources) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.SourceComposition) {
			result = interprete_org_sintef_thingml_SourceComposition((org.sintef.thingml.SourceComposition) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.SimpleSource) {
			result = interprete_org_sintef_thingml_SimpleSource((org.sintef.thingml.SimpleSource) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Source) {
			result = interprete_org_sintef_thingml_Source((org.sintef.thingml.Source) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Filter) {
			result = interprete_org_sintef_thingml_Filter((org.sintef.thingml.Filter) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ViewSource) {
			result = interprete_org_sintef_thingml_ViewSource((org.sintef.thingml.ViewSource) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Operator) {
			result = interprete_org_sintef_thingml_Operator((org.sintef.thingml.Operator) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.MessageParameter) {
			result = interprete_org_sintef_thingml_MessageParameter((org.sintef.thingml.MessageParameter) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.SglMsgParamOperatorCall) {
			result = interprete_org_sintef_thingml_SglMsgParamOperatorCall((org.sintef.thingml.SglMsgParamOperatorCall) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Reference) {
			result = interprete_org_sintef_thingml_Reference((org.sintef.thingml.Reference) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ReferencedElmt) {
			result = interprete_org_sintef_thingml_ReferencedElmt((org.sintef.thingml.ReferencedElmt) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.SglMsgParamOperator) {
			result = interprete_org_sintef_thingml_SglMsgParamOperator((org.sintef.thingml.SglMsgParamOperator) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.LengthWindow) {
			result = interprete_org_sintef_thingml_LengthWindow((org.sintef.thingml.LengthWindow) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.TimeWindow) {
			result = interprete_org_sintef_thingml_TimeWindow((org.sintef.thingml.TimeWindow) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.SimpleParamRef) {
			result = interprete_org_sintef_thingml_SimpleParamRef((org.sintef.thingml.SimpleParamRef) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ArrayParamRef) {
			result = interprete_org_sintef_thingml_ArrayParamRef((org.sintef.thingml.ArrayParamRef) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ParamReference) {
			result = interprete_org_sintef_thingml_ParamReference((org.sintef.thingml.ParamReference) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.LengthArray) {
			result = interprete_org_sintef_thingml_LengthArray((org.sintef.thingml.LengthArray) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.PredifinedProperty) {
			result = interprete_org_sintef_thingml_PredifinedProperty((org.sintef.thingml.PredifinedProperty) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.ElmtProperty) {
			result = interprete_org_sintef_thingml_ElmtProperty((org.sintef.thingml.ElmtProperty) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.WindowView) {
			result = interprete_org_sintef_thingml_WindowView((org.sintef.thingml.WindowView) object, context);
		}
		if (result != null) {
			return result;
		}
		return result;
	}
	
	public ResultType interprete_org_sintef_thingml_ThingMLModel(org.sintef.thingml.ThingMLModel thingMLModel, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Function(org.sintef.thingml.Function function, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Message(org.sintef.thingml.Message message, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Thing(org.sintef.thingml.Thing thing, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Parameter(org.sintef.thingml.Parameter parameter, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Variable(org.sintef.thingml.Variable variable, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ThingMLElement(org.sintef.thingml.ThingMLElement thingMLElement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Type(org.sintef.thingml.Type type, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_TypedElement(org.sintef.thingml.TypedElement typedElement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Property(org.sintef.thingml.Property property, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PropertyAssign(org.sintef.thingml.PropertyAssign propertyAssign, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PlatformAnnotation(org.sintef.thingml.PlatformAnnotation platformAnnotation, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Enumeration(org.sintef.thingml.Enumeration enumeration, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PrimitiveType(org.sintef.thingml.PrimitiveType primitiveType, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_EnumerationLiteral(org.sintef.thingml.EnumerationLiteral enumerationLiteral, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_AnnotatedElement(org.sintef.thingml.AnnotatedElement annotatedElement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_StateMachine(org.sintef.thingml.StateMachine stateMachine, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Handler(org.sintef.thingml.Handler handler, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Transition(org.sintef.thingml.Transition transition, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_InternalTransition(org.sintef.thingml.InternalTransition internalTransition, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_State(org.sintef.thingml.State state, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_CompositeState(org.sintef.thingml.CompositeState compositeState, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Region(org.sintef.thingml.Region region, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ParallelRegion(org.sintef.thingml.ParallelRegion parallelRegion, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Action(org.sintef.thingml.Action action, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ActionBlock(org.sintef.thingml.ActionBlock actionBlock, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ExternStatement(org.sintef.thingml.ExternStatement externStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Expression(org.sintef.thingml.Expression expression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ExternExpression(org.sintef.thingml.ExternExpression externExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_SendAction(org.sintef.thingml.SendAction sendAction, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_VariableAssignment(org.sintef.thingml.VariableAssignment variableAssignment, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Event(org.sintef.thingml.Event event, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ReceiveMessage(org.sintef.thingml.ReceiveMessage receiveMessage, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Port(org.sintef.thingml.Port port, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_RequiredPort(org.sintef.thingml.RequiredPort requiredPort, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ProvidedPort(org.sintef.thingml.ProvidedPort providedPort, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Literal(org.sintef.thingml.Literal literal, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_EnumLiteralRef(org.sintef.thingml.EnumLiteralRef enumLiteralRef, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_IntegerLiteral(org.sintef.thingml.IntegerLiteral integerLiteral, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_BooleanLiteral(org.sintef.thingml.BooleanLiteral booleanLiteral, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_StringLiteral(org.sintef.thingml.StringLiteral stringLiteral, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_DoubleLiteral(org.sintef.thingml.DoubleLiteral doubleLiteral, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_UnaryExpression(org.sintef.thingml.UnaryExpression unaryExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_NotExpression(org.sintef.thingml.NotExpression notExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_UnaryMinus(org.sintef.thingml.UnaryMinus unaryMinus, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_BinaryExpression(org.sintef.thingml.BinaryExpression binaryExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PlusExpression(org.sintef.thingml.PlusExpression plusExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_MinusExpression(org.sintef.thingml.MinusExpression minusExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_TimesExpression(org.sintef.thingml.TimesExpression timesExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_DivExpression(org.sintef.thingml.DivExpression divExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ModExpression(org.sintef.thingml.ModExpression modExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_EqualsExpression(org.sintef.thingml.EqualsExpression equalsExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_GreaterExpression(org.sintef.thingml.GreaterExpression greaterExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_LowerExpression(org.sintef.thingml.LowerExpression lowerExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_AndExpression(org.sintef.thingml.AndExpression andExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_OrExpression(org.sintef.thingml.OrExpression orExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ControlStructure(org.sintef.thingml.ControlStructure controlStructure, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_LoopAction(org.sintef.thingml.LoopAction loopAction, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ConditionalAction(org.sintef.thingml.ConditionalAction conditionalAction, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PropertyReference(org.sintef.thingml.PropertyReference propertyReference, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ArrayIndex(org.sintef.thingml.ArrayIndex arrayIndex, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ExpressionGroup(org.sintef.thingml.ExpressionGroup expressionGroup, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ReturnAction(org.sintef.thingml.ReturnAction returnAction, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PrintAction(org.sintef.thingml.PrintAction printAction, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ErrorAction(org.sintef.thingml.ErrorAction errorAction, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Configuration(org.sintef.thingml.Configuration configuration, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Instance(org.sintef.thingml.Instance instance, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Connector(org.sintef.thingml.Connector connector, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ExternalConnector(org.sintef.thingml.ExternalConnector externalConnector, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_AbstractConnector(org.sintef.thingml.AbstractConnector abstractConnector, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ConfigPropertyAssign(org.sintef.thingml.ConfigPropertyAssign configPropertyAssign, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ConfigInclude(org.sintef.thingml.ConfigInclude configInclude, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_InstanceRef(org.sintef.thingml.InstanceRef instanceRef, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_FunctionCall(org.sintef.thingml.FunctionCall functionCall, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_FunctionCallStatement(org.sintef.thingml.FunctionCallStatement functionCallStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_FunctionCallExpression(org.sintef.thingml.FunctionCallExpression functionCallExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_LocalVariable(org.sintef.thingml.LocalVariable localVariable, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Stream(org.sintef.thingml.Stream stream, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_StreamExpression(org.sintef.thingml.StreamExpression streamExpression, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_StreamParamReference(org.sintef.thingml.StreamParamReference streamParamReference, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_StreamOutput(org.sintef.thingml.StreamOutput streamOutput, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Source(org.sintef.thingml.Source source, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_SourceComposition(org.sintef.thingml.SourceComposition sourceComposition, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_JoinSources(org.sintef.thingml.JoinSources joinSources, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_MergeSources(org.sintef.thingml.MergeSources mergeSources, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_SimpleSource(org.sintef.thingml.SimpleSource simpleSource, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ViewSource(org.sintef.thingml.ViewSource viewSource, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Filter(org.sintef.thingml.Filter filter, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Operator(org.sintef.thingml.Operator operator, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_MessageParameter(org.sintef.thingml.MessageParameter messageParameter, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_SglMsgParamOperatorCall(org.sintef.thingml.SglMsgParamOperatorCall sglMsgParamOperatorCall, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Reference(org.sintef.thingml.Reference reference, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ReferencedElmt(org.sintef.thingml.ReferencedElmt referencedElmt, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_SglMsgParamOperator(org.sintef.thingml.SglMsgParamOperator sglMsgParamOperator, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_LengthWindow(org.sintef.thingml.LengthWindow lengthWindow, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_TimeWindow(org.sintef.thingml.TimeWindow timeWindow, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ParamReference(org.sintef.thingml.ParamReference paramReference, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_SimpleParamRef(org.sintef.thingml.SimpleParamRef simpleParamRef, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ArrayParamRef(org.sintef.thingml.ArrayParamRef arrayParamRef, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ElmtProperty(org.sintef.thingml.ElmtProperty elmtProperty, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PredifinedProperty(org.sintef.thingml.PredifinedProperty predifinedProperty, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_LengthArray(org.sintef.thingml.LengthArray lengthArray, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_WindowView(org.sintef.thingml.WindowView windowView, ContextType context) {
		return null;
	}
	
	private void notifyListeners(org.eclipse.emf.ecore.EObject element) {
		for (org.sintef.thingml.resource.thingml.IThingmlInterpreterListener listener : listeners) {
			listener.handleInterpreteObject(element);
		}
	}
	
	/**
	 * Adds the given object to the interpretation stack. Attention: Objects that are
	 * added first, are interpret last.
	 */
	public void addObjectToInterprete(org.eclipse.emf.ecore.EObject object) {
		interpretationStack.push(object);
	}
	
	/**
	 * Adds the given collection of objects to the interpretation stack. Attention:
	 * Collections that are added first, are interpret last.
	 */
	public void addObjectsToInterprete(java.util.Collection<? extends org.eclipse.emf.ecore.EObject> objects) {
		for (org.eclipse.emf.ecore.EObject object : objects) {
			addObjectToInterprete(object);
		}
	}
	
	/**
	 * Adds the given collection of objects in reverse order to the interpretation
	 * stack.
	 */
	public void addObjectsToInterpreteInReverseOrder(java.util.Collection<? extends org.eclipse.emf.ecore.EObject> objects) {
		java.util.List<org.eclipse.emf.ecore.EObject> reverse = new java.util.ArrayList<org.eclipse.emf.ecore.EObject>(objects.size());
		reverse.addAll(objects);
		java.util.Collections.reverse(reverse);
		addObjectsToInterprete(reverse);
	}
	
	/**
	 * Adds the given object and all its children to the interpretation stack such
	 * that they are interpret in top down order.
	 */
	public void addObjectTreeToInterpreteTopDown(org.eclipse.emf.ecore.EObject root) {
		java.util.List<org.eclipse.emf.ecore.EObject> objects = new java.util.ArrayList<org.eclipse.emf.ecore.EObject>();
		objects.add(root);
		java.util.Iterator<org.eclipse.emf.ecore.EObject> it = root.eAllContents();
		while (it.hasNext()) {
			org.eclipse.emf.ecore.EObject eObject = (org.eclipse.emf.ecore.EObject) it.next();
			objects.add(eObject);
		}
		addObjectsToInterpreteInReverseOrder(objects);
	}
	
	public void addListener(org.sintef.thingml.resource.thingml.IThingmlInterpreterListener newListener) {
		listeners.add(newListener);
	}
	
	public boolean removeListener(org.sintef.thingml.resource.thingml.IThingmlInterpreterListener listener) {
		return listeners.remove(listener);
	}
	
	public org.eclipse.emf.ecore.EObject getNextObjectToInterprete() {
		return nextObjectToInterprete;
	}
	
	public java.util.Stack<org.eclipse.emf.ecore.EObject> getInterpretationStack() {
		return interpretationStack;
	}
	
	public void terminate() {
		interpretationStack.clear();
	}
	
	public Object getCurrentContext() {
		return currentContext;
	}
	
}
