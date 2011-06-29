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
	
	public ResultType interprete(ContextType context) {
		ResultType result = null;
		while (!interpretationStack.empty()) {
			org.eclipse.emf.ecore.EObject next = interpretationStack.pop();
			result = interprete(next, context);
			if (!continueInterpretation(result)) {
				break;
			}
		}
		return result;
	}
	
	/**
	 * Override this method to stop the overall interpretation depending on the result
	 * of the interpretation of a single model elements.
	 */
	public boolean continueInterpretation(ResultType result) {
		return true;
	}
	
	public ResultType interprete(org.eclipse.emf.ecore.EObject object, ContextType context) {
		ResultType result = null;
		if (object instanceof org.sintef.thingml.ErrorAction) {
			result = interprete_org_sintef_thingml_ErrorAction((org.sintef.thingml.ErrorAction) object, context);
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
		if (object instanceof org.sintef.thingml.ExpressionGroup) {
			result = interprete_org_sintef_thingml_ExpressionGroup((org.sintef.thingml.ExpressionGroup) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.DictionaryReference) {
			result = interprete_org_sintef_thingml_DictionaryReference((org.sintef.thingml.DictionaryReference) object, context);
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
		if (object instanceof org.sintef.thingml.ConditionalAction) {
			result = interprete_org_sintef_thingml_ConditionalAction((org.sintef.thingml.ConditionalAction) object, context);
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
		if (object instanceof org.sintef.thingml.ControlStructure) {
			result = interprete_org_sintef_thingml_ControlStructure((org.sintef.thingml.ControlStructure) object, context);
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
		if (object instanceof org.sintef.thingml.AndExpression) {
			result = interprete_org_sintef_thingml_AndExpression((org.sintef.thingml.AndExpression) object, context);
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
		if (object instanceof org.sintef.thingml.GreaterExpression) {
			result = interprete_org_sintef_thingml_GreaterExpression((org.sintef.thingml.GreaterExpression) object, context);
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
		if (object instanceof org.sintef.thingml.ModExpression) {
			result = interprete_org_sintef_thingml_ModExpression((org.sintef.thingml.ModExpression) object, context);
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
		if (object instanceof org.sintef.thingml.TimesExpression) {
			result = interprete_org_sintef_thingml_TimesExpression((org.sintef.thingml.TimesExpression) object, context);
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
		if (object instanceof org.sintef.thingml.PlusExpression) {
			result = interprete_org_sintef_thingml_PlusExpression((org.sintef.thingml.PlusExpression) object, context);
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
		if (object instanceof org.sintef.thingml.UnaryMinus) {
			result = interprete_org_sintef_thingml_UnaryMinus((org.sintef.thingml.UnaryMinus) object, context);
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
		if (object instanceof org.sintef.thingml.UnaryExpression) {
			result = interprete_org_sintef_thingml_UnaryExpression((org.sintef.thingml.UnaryExpression) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.DoubleLitteral) {
			result = interprete_org_sintef_thingml_DoubleLitteral((org.sintef.thingml.DoubleLitteral) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.StringLitteral) {
			result = interprete_org_sintef_thingml_StringLitteral((org.sintef.thingml.StringLitteral) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.BooleanLitteral) {
			result = interprete_org_sintef_thingml_BooleanLitteral((org.sintef.thingml.BooleanLitteral) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.IntegerLitteral) {
			result = interprete_org_sintef_thingml_IntegerLitteral((org.sintef.thingml.IntegerLitteral) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.Litteral) {
			result = interprete_org_sintef_thingml_Litteral((org.sintef.thingml.Litteral) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.sintef.thingml.EventReference) {
			result = interprete_org_sintef_thingml_EventReference((org.sintef.thingml.EventReference) object, context);
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
		if (object instanceof org.sintef.thingml.Dictionary) {
			result = interprete_org_sintef_thingml_Dictionary((org.sintef.thingml.Dictionary) object, context);
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
		if (object instanceof org.sintef.thingml.PropertyAssignment) {
			result = interprete_org_sintef_thingml_PropertyAssignment((org.sintef.thingml.PropertyAssignment) object, context);
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
		if (object instanceof org.sintef.thingml.ExternStatement) {
			result = interprete_org_sintef_thingml_ExternStatement((org.sintef.thingml.ExternStatement) object, context);
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
		if (object instanceof org.sintef.thingml.Action) {
			result = interprete_org_sintef_thingml_Action((org.sintef.thingml.Action) object, context);
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
		if (object instanceof org.sintef.thingml.CompositeState) {
			result = interprete_org_sintef_thingml_CompositeState((org.sintef.thingml.CompositeState) object, context);
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
		if (object instanceof org.sintef.thingml.State) {
			result = interprete_org_sintef_thingml_State((org.sintef.thingml.State) object, context);
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
		if (object instanceof org.sintef.thingml.Transition) {
			result = interprete_org_sintef_thingml_Transition((org.sintef.thingml.Transition) object, context);
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
		if (object instanceof org.sintef.thingml.StateMachine) {
			result = interprete_org_sintef_thingml_StateMachine((org.sintef.thingml.StateMachine) object, context);
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
		if (object instanceof org.sintef.thingml.PrimitiveType) {
			result = interprete_org_sintef_thingml_PrimitiveType((org.sintef.thingml.PrimitiveType) object, context);
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
		if (object instanceof org.sintef.thingml.Property) {
			result = interprete_org_sintef_thingml_Property((org.sintef.thingml.Property) object, context);
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
		if (object instanceof org.sintef.thingml.Type) {
			result = interprete_org_sintef_thingml_Type((org.sintef.thingml.Type) object, context);
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
		if (object instanceof org.sintef.thingml.PlatformAnnotation) {
			result = interprete_org_sintef_thingml_PlatformAnnotation((org.sintef.thingml.PlatformAnnotation) object, context);
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
		if (object instanceof org.sintef.thingml.Message) {
			result = interprete_org_sintef_thingml_Message((org.sintef.thingml.Message) object, context);
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
		if (object instanceof org.sintef.thingml.ThingMLModel) {
			result = interprete_org_sintef_thingml_ThingMLModel((org.sintef.thingml.ThingMLModel) object, context);
		}
		if (result != null) {
			return result;
		}
		return result;
	}
	
	public ResultType interprete_org_sintef_thingml_ThingMLModel(org.sintef.thingml.ThingMLModel object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Message(org.sintef.thingml.Message object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Thing(org.sintef.thingml.Thing object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Parameter(org.sintef.thingml.Parameter object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ThingMLElement(org.sintef.thingml.ThingMLElement object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Type(org.sintef.thingml.Type object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Property(org.sintef.thingml.Property object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PlatformAnnotation(org.sintef.thingml.PlatformAnnotation object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Enumeration(org.sintef.thingml.Enumeration object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PrimitiveType(org.sintef.thingml.PrimitiveType object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_EnumerationLiteral(org.sintef.thingml.EnumerationLiteral object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_AnnotatedElement(org.sintef.thingml.AnnotatedElement object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_StateMachine(org.sintef.thingml.StateMachine object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Handler(org.sintef.thingml.Handler object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Transition(org.sintef.thingml.Transition object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_InternalTransition(org.sintef.thingml.InternalTransition object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_State(org.sintef.thingml.State object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_CompositeState(org.sintef.thingml.CompositeState object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Region(org.sintef.thingml.Region object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ParallelRegion(org.sintef.thingml.ParallelRegion object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Action(org.sintef.thingml.Action object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ActionBlock(org.sintef.thingml.ActionBlock object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ExternStatement(org.sintef.thingml.ExternStatement object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Expression(org.sintef.thingml.Expression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ExternExpression(org.sintef.thingml.ExternExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_SendAction(org.sintef.thingml.SendAction object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PropertyAssignment(org.sintef.thingml.PropertyAssignment object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Event(org.sintef.thingml.Event object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ReceiveMessage(org.sintef.thingml.ReceiveMessage object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Dictionary(org.sintef.thingml.Dictionary object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Port(org.sintef.thingml.Port object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_EventReference(org.sintef.thingml.EventReference object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_Litteral(org.sintef.thingml.Litteral object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_IntegerLitteral(org.sintef.thingml.IntegerLitteral object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_BooleanLitteral(org.sintef.thingml.BooleanLitteral object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_StringLitteral(org.sintef.thingml.StringLitteral object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_DoubleLitteral(org.sintef.thingml.DoubleLitteral object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_UnaryExpression(org.sintef.thingml.UnaryExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_NotExpression(org.sintef.thingml.NotExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_UnaryMinus(org.sintef.thingml.UnaryMinus object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_BinaryExpression(org.sintef.thingml.BinaryExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PlusExpression(org.sintef.thingml.PlusExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_MinusExpression(org.sintef.thingml.MinusExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_TimesExpression(org.sintef.thingml.TimesExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_DivExpression(org.sintef.thingml.DivExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ModExpression(org.sintef.thingml.ModExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_EqualsExpression(org.sintef.thingml.EqualsExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_GreaterExpression(org.sintef.thingml.GreaterExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_LowerExpression(org.sintef.thingml.LowerExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_AndExpression(org.sintef.thingml.AndExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_OrExpression(org.sintef.thingml.OrExpression object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ControlStructure(org.sintef.thingml.ControlStructure object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_LoopAction(org.sintef.thingml.LoopAction object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ConditionalAction(org.sintef.thingml.ConditionalAction object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PropertyReference(org.sintef.thingml.PropertyReference object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_DictionaryReference(org.sintef.thingml.DictionaryReference object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ExpressionGroup(org.sintef.thingml.ExpressionGroup object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_PrintAction(org.sintef.thingml.PrintAction object, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_sintef_thingml_ErrorAction(org.sintef.thingml.ErrorAction object, ContextType context) {
		return null;
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
	
}
