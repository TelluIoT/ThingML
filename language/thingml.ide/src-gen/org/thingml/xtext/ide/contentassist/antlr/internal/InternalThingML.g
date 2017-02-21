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
grammar InternalThingML;

options {
	superClass=AbstractInternalContentAssistParser;
}

@lexer::header {
package org.thingml.xtext.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
}

@parser::header {
package org.thingml.xtext.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.thingml.xtext.services.ThingMLGrammarAccess;

}
@parser::members {
	private ThingMLGrammarAccess grammarAccess;

	public void setGrammarAccess(ThingMLGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}

	@Override
	protected Grammar getGrammar() {
		return grammarAccess.getGrammar();
	}

	@Override
	protected String getValueForTokenName(String tokenName) {
		return tokenName;
	}
}

// Entry rule entryRuleThingMLModel
entryRuleThingMLModel
:
{ before(grammarAccess.getThingMLModelRule()); }
	 ruleThingMLModel
{ after(grammarAccess.getThingMLModelRule()); } 
	 EOF 
;

// Rule ThingMLModel
ruleThingMLModel 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getThingMLModelAccess().getGroup()); }
		(rule__ThingMLModel__Group__0)
		{ after(grammarAccess.getThingMLModelAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRulePlatformAnnotation
entryRulePlatformAnnotation
:
{ before(grammarAccess.getPlatformAnnotationRule()); }
	 rulePlatformAnnotation
{ after(grammarAccess.getPlatformAnnotationRule()); } 
	 EOF 
;

// Rule PlatformAnnotation
rulePlatformAnnotation 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getPlatformAnnotationAccess().getGroup()); }
		(rule__PlatformAnnotation__Group__0)
		{ after(grammarAccess.getPlatformAnnotationAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleVariable
entryRuleVariable
:
{ before(grammarAccess.getVariableRule()); }
	 ruleVariable
{ after(grammarAccess.getVariableRule()); } 
	 EOF 
;

// Rule Variable
ruleVariable 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getVariableAccess().getAlternatives()); }
		(rule__Variable__Alternatives)
		{ after(grammarAccess.getVariableAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTypeRef
entryRuleTypeRef
:
{ before(grammarAccess.getTypeRefRule()); }
	 ruleTypeRef
{ after(grammarAccess.getTypeRefRule()); } 
	 EOF 
;

// Rule TypeRef
ruleTypeRef 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeRefAccess().getGroup()); }
		(rule__TypeRef__Group__0)
		{ after(grammarAccess.getTypeRefAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleType
entryRuleType
:
{ before(grammarAccess.getTypeRule()); }
	 ruleType
{ after(grammarAccess.getTypeRule()); } 
	 EOF 
;

// Rule Type
ruleType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTypeAccess().getAlternatives()); }
		(rule__Type__Alternatives)
		{ after(grammarAccess.getTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRulePrimitiveType
entryRulePrimitiveType
:
{ before(grammarAccess.getPrimitiveTypeRule()); }
	 rulePrimitiveType
{ after(grammarAccess.getPrimitiveTypeRule()); } 
	 EOF 
;

// Rule PrimitiveType
rulePrimitiveType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getPrimitiveTypeAccess().getGroup()); }
		(rule__PrimitiveType__Group__0)
		{ after(grammarAccess.getPrimitiveTypeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleObjectType
entryRuleObjectType
:
{ before(grammarAccess.getObjectTypeRule()); }
	 ruleObjectType
{ after(grammarAccess.getObjectTypeRule()); } 
	 EOF 
;

// Rule ObjectType
ruleObjectType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getObjectTypeAccess().getGroup()); }
		(rule__ObjectType__Group__0)
		{ after(grammarAccess.getObjectTypeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEnumeration
entryRuleEnumeration
:
{ before(grammarAccess.getEnumerationRule()); }
	 ruleEnumeration
{ after(grammarAccess.getEnumerationRule()); } 
	 EOF 
;

// Rule Enumeration
ruleEnumeration 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEnumerationAccess().getGroup()); }
		(rule__Enumeration__Group__0)
		{ after(grammarAccess.getEnumerationAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEnumerationLiteral
entryRuleEnumerationLiteral
:
{ before(grammarAccess.getEnumerationLiteralRule()); }
	 ruleEnumerationLiteral
{ after(grammarAccess.getEnumerationLiteralRule()); } 
	 EOF 
;

// Rule EnumerationLiteral
ruleEnumerationLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEnumerationLiteralAccess().getGroup()); }
		(rule__EnumerationLiteral__Group__0)
		{ after(grammarAccess.getEnumerationLiteralAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleThing
entryRuleThing
:
{ before(grammarAccess.getThingRule()); }
	 ruleThing
{ after(grammarAccess.getThingRule()); } 
	 EOF 
;

// Rule Thing
ruleThing 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getThingAccess().getGroup()); }
		(rule__Thing__Group__0)
		{ after(grammarAccess.getThingAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRulePropertyAssign
entryRulePropertyAssign
:
{ before(grammarAccess.getPropertyAssignRule()); }
	 rulePropertyAssign
{ after(grammarAccess.getPropertyAssignRule()); } 
	 EOF 
;

// Rule PropertyAssign
rulePropertyAssign 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getPropertyAssignAccess().getGroup()); }
		(rule__PropertyAssign__Group__0)
		{ after(grammarAccess.getPropertyAssignAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleProtocol
entryRuleProtocol
:
{ before(grammarAccess.getProtocolRule()); }
	 ruleProtocol
{ after(grammarAccess.getProtocolRule()); } 
	 EOF 
;

// Rule Protocol
ruleProtocol 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getProtocolAccess().getGroup()); }
		(rule__Protocol__Group__0)
		{ after(grammarAccess.getProtocolAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleFunction
entryRuleFunction
:
{ before(grammarAccess.getFunctionRule()); }
	 ruleFunction
{ after(grammarAccess.getFunctionRule()); } 
	 EOF 
;

// Rule Function
ruleFunction 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getFunctionAccess().getGroup()); }
		(rule__Function__Group__0)
		{ after(grammarAccess.getFunctionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleProperty
entryRuleProperty
:
{ before(grammarAccess.getPropertyRule()); }
	 ruleProperty
{ after(grammarAccess.getPropertyRule()); } 
	 EOF 
;

// Rule Property
ruleProperty 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getPropertyAccess().getGroup()); }
		(rule__Property__Group__0)
		{ after(grammarAccess.getPropertyAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleMessage
entryRuleMessage
:
{ before(grammarAccess.getMessageRule()); }
	 ruleMessage
{ after(grammarAccess.getMessageRule()); } 
	 EOF 
;

// Rule Message
ruleMessage 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getMessageAccess().getGroup()); }
		(rule__Message__Group__0)
		{ after(grammarAccess.getMessageAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleParameter
entryRuleParameter
:
{ before(grammarAccess.getParameterRule()); }
	 ruleParameter
{ after(grammarAccess.getParameterRule()); } 
	 EOF 
;

// Rule Parameter
ruleParameter 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getParameterAccess().getGroup()); }
		(rule__Parameter__Group__0)
		{ after(grammarAccess.getParameterAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRulePort
entryRulePort
:
{ before(grammarAccess.getPortRule()); }
	 rulePort
{ after(grammarAccess.getPortRule()); } 
	 EOF 
;

// Rule Port
rulePort 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getPortAccess().getAlternatives()); }
		(rule__Port__Alternatives)
		{ after(grammarAccess.getPortAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleRequiredPort
entryRuleRequiredPort
:
{ before(grammarAccess.getRequiredPortRule()); }
	 ruleRequiredPort
{ after(grammarAccess.getRequiredPortRule()); } 
	 EOF 
;

// Rule RequiredPort
ruleRequiredPort 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getRequiredPortAccess().getGroup()); }
		(rule__RequiredPort__Group__0)
		{ after(grammarAccess.getRequiredPortAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleProvidedPort
entryRuleProvidedPort
:
{ before(grammarAccess.getProvidedPortRule()); }
	 ruleProvidedPort
{ after(grammarAccess.getProvidedPortRule()); } 
	 EOF 
;

// Rule ProvidedPort
ruleProvidedPort 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getProvidedPortAccess().getGroup()); }
		(rule__ProvidedPort__Group__0)
		{ after(grammarAccess.getProvidedPortAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleInternalPort
entryRuleInternalPort
:
{ before(grammarAccess.getInternalPortRule()); }
	 ruleInternalPort
{ after(grammarAccess.getInternalPortRule()); } 
	 EOF 
;

// Rule InternalPort
ruleInternalPort 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getInternalPortAccess().getGroup()); }
		(rule__InternalPort__Group__0)
		{ after(grammarAccess.getInternalPortAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleState
entryRuleState
:
{ before(grammarAccess.getStateRule()); }
	 ruleState
{ after(grammarAccess.getStateRule()); } 
	 EOF 
;

// Rule State
ruleState 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getStateAccess().getAlternatives()); }
		(rule__State__Alternatives)
		{ after(grammarAccess.getStateAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleHandler
entryRuleHandler
:
{ before(grammarAccess.getHandlerRule()); }
	 ruleHandler
{ after(grammarAccess.getHandlerRule()); } 
	 EOF 
;

// Rule Handler
ruleHandler 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getHandlerAccess().getAlternatives()); }
		(rule__Handler__Alternatives)
		{ after(grammarAccess.getHandlerAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTransition
entryRuleTransition
:
{ before(grammarAccess.getTransitionRule()); }
	 ruleTransition
{ after(grammarAccess.getTransitionRule()); } 
	 EOF 
;

// Rule Transition
ruleTransition 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTransitionAccess().getGroup()); }
		(rule__Transition__Group__0)
		{ after(grammarAccess.getTransitionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleInternalTransition
entryRuleInternalTransition
:
{ before(grammarAccess.getInternalTransitionRule()); }
	 ruleInternalTransition
{ after(grammarAccess.getInternalTransitionRule()); } 
	 EOF 
;

// Rule InternalTransition
ruleInternalTransition 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getInternalTransitionAccess().getGroup()); }
		(rule__InternalTransition__Group__0)
		{ after(grammarAccess.getInternalTransitionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCompositeState
entryRuleCompositeState
:
{ before(grammarAccess.getCompositeStateRule()); }
	 ruleCompositeState
{ after(grammarAccess.getCompositeStateRule()); } 
	 EOF 
;

// Rule CompositeState
ruleCompositeState 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCompositeStateAccess().getGroup()); }
		(rule__CompositeState__Group__0)
		{ after(grammarAccess.getCompositeStateAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleStateMachine
entryRuleStateMachine
:
{ before(grammarAccess.getStateMachineRule()); }
	 ruleStateMachine
{ after(grammarAccess.getStateMachineRule()); } 
	 EOF 
;

// Rule StateMachine
ruleStateMachine 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getStateMachineAccess().getGroup()); }
		(rule__StateMachine__Group__0)
		{ after(grammarAccess.getStateMachineAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSession
entryRuleSession
:
{ before(grammarAccess.getSessionRule()); }
	 ruleSession
{ after(grammarAccess.getSessionRule()); } 
	 EOF 
;

// Rule Session
ruleSession 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSessionAccess().getGroup()); }
		(rule__Session__Group__0)
		{ after(grammarAccess.getSessionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleRegion
entryRuleRegion
:
{ before(grammarAccess.getRegionRule()); }
	 ruleRegion
{ after(grammarAccess.getRegionRule()); } 
	 EOF 
;

// Rule Region
ruleRegion 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getRegionAccess().getGroup()); }
		(rule__Region__Group__0)
		{ after(grammarAccess.getRegionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleFinalState
entryRuleFinalState
:
{ before(grammarAccess.getFinalStateRule()); }
	 ruleFinalState
{ after(grammarAccess.getFinalStateRule()); } 
	 EOF 
;

// Rule FinalState
ruleFinalState 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getFinalStateAccess().getGroup()); }
		(rule__FinalState__Group__0)
		{ after(grammarAccess.getFinalStateAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleStateContainer
entryRuleStateContainer
:
{ before(grammarAccess.getStateContainerRule()); }
	 ruleStateContainer
{ after(grammarAccess.getStateContainerRule()); } 
	 EOF 
;

// Rule StateContainer
ruleStateContainer 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getStateContainerAccess().getAlternatives()); }
		(rule__StateContainer__Alternatives)
		{ after(grammarAccess.getStateContainerAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEvent
entryRuleEvent
:
{ before(grammarAccess.getEventRule()); }
	 ruleEvent
{ after(grammarAccess.getEventRule()); } 
	 EOF 
;

// Rule Event
ruleEvent 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEventAccess().getReceiveMessageParserRuleCall()); }
		ruleReceiveMessage
		{ after(grammarAccess.getEventAccess().getReceiveMessageParserRuleCall()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleReceiveMessage
entryRuleReceiveMessage
:
{ before(grammarAccess.getReceiveMessageRule()); }
	 ruleReceiveMessage
{ after(grammarAccess.getReceiveMessageRule()); } 
	 EOF 
;

// Rule ReceiveMessage
ruleReceiveMessage 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getReceiveMessageAccess().getGroup()); }
		(rule__ReceiveMessage__Group__0)
		{ after(grammarAccess.getReceiveMessageAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleAction
entryRuleAction
:
{ before(grammarAccess.getActionRule()); }
	 ruleAction
{ after(grammarAccess.getActionRule()); } 
	 EOF 
;

// Rule Action
ruleAction 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getActionAccess().getAlternatives()); }
		(rule__Action__Alternatives)
		{ after(grammarAccess.getActionAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleActionBlock
entryRuleActionBlock
:
{ before(grammarAccess.getActionBlockRule()); }
	 ruleActionBlock
{ after(grammarAccess.getActionBlockRule()); } 
	 EOF 
;

// Rule ActionBlock
ruleActionBlock 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getActionBlockAccess().getGroup()); }
		(rule__ActionBlock__Group__0)
		{ after(grammarAccess.getActionBlockAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleExternStatement
entryRuleExternStatement
:
{ before(grammarAccess.getExternStatementRule()); }
	 ruleExternStatement
{ after(grammarAccess.getExternStatementRule()); } 
	 EOF 
;

// Rule ExternStatement
ruleExternStatement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getExternStatementAccess().getGroup()); }
		(rule__ExternStatement__Group__0)
		{ after(grammarAccess.getExternStatementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleLocalVariable
entryRuleLocalVariable
:
{ before(grammarAccess.getLocalVariableRule()); }
	 ruleLocalVariable
{ after(grammarAccess.getLocalVariableRule()); } 
	 EOF 
;

// Rule LocalVariable
ruleLocalVariable 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getLocalVariableAccess().getGroup()); }
		(rule__LocalVariable__Group__0)
		{ after(grammarAccess.getLocalVariableAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSendAction
entryRuleSendAction
:
{ before(grammarAccess.getSendActionRule()); }
	 ruleSendAction
{ after(grammarAccess.getSendActionRule()); } 
	 EOF 
;

// Rule SendAction
ruleSendAction 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSendActionAccess().getGroup()); }
		(rule__SendAction__Group__0)
		{ after(grammarAccess.getSendActionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleVariableAssignment
entryRuleVariableAssignment
:
{ before(grammarAccess.getVariableAssignmentRule()); }
	 ruleVariableAssignment
{ after(grammarAccess.getVariableAssignmentRule()); } 
	 EOF 
;

// Rule VariableAssignment
ruleVariableAssignment 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getVariableAssignmentAccess().getGroup()); }
		(rule__VariableAssignment__Group__0)
		{ after(grammarAccess.getVariableAssignmentAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleIncrement
entryRuleIncrement
:
{ before(grammarAccess.getIncrementRule()); }
	 ruleIncrement
{ after(grammarAccess.getIncrementRule()); } 
	 EOF 
;

// Rule Increment
ruleIncrement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getIncrementAccess().getGroup()); }
		(rule__Increment__Group__0)
		{ after(grammarAccess.getIncrementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleDecrement
entryRuleDecrement
:
{ before(grammarAccess.getDecrementRule()); }
	 ruleDecrement
{ after(grammarAccess.getDecrementRule()); } 
	 EOF 
;

// Rule Decrement
ruleDecrement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getDecrementAccess().getGroup()); }
		(rule__Decrement__Group__0)
		{ after(grammarAccess.getDecrementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleLoopAction
entryRuleLoopAction
:
{ before(grammarAccess.getLoopActionRule()); }
	 ruleLoopAction
{ after(grammarAccess.getLoopActionRule()); } 
	 EOF 
;

// Rule LoopAction
ruleLoopAction 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getLoopActionAccess().getGroup()); }
		(rule__LoopAction__Group__0)
		{ after(grammarAccess.getLoopActionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleConditionalAction
entryRuleConditionalAction
:
{ before(grammarAccess.getConditionalActionRule()); }
	 ruleConditionalAction
{ after(grammarAccess.getConditionalActionRule()); } 
	 EOF 
;

// Rule ConditionalAction
ruleConditionalAction 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getConditionalActionAccess().getGroup()); }
		(rule__ConditionalAction__Group__0)
		{ after(grammarAccess.getConditionalActionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleReturnAction
entryRuleReturnAction
:
{ before(grammarAccess.getReturnActionRule()); }
	 ruleReturnAction
{ after(grammarAccess.getReturnActionRule()); } 
	 EOF 
;

// Rule ReturnAction
ruleReturnAction 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getReturnActionAccess().getGroup()); }
		(rule__ReturnAction__Group__0)
		{ after(grammarAccess.getReturnActionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRulePrintAction
entryRulePrintAction
:
{ before(grammarAccess.getPrintActionRule()); }
	 rulePrintAction
{ after(grammarAccess.getPrintActionRule()); } 
	 EOF 
;

// Rule PrintAction
rulePrintAction 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getPrintActionAccess().getGroup()); }
		(rule__PrintAction__Group__0)
		{ after(grammarAccess.getPrintActionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleErrorAction
entryRuleErrorAction
:
{ before(grammarAccess.getErrorActionRule()); }
	 ruleErrorAction
{ after(grammarAccess.getErrorActionRule()); } 
	 EOF 
;

// Rule ErrorAction
ruleErrorAction 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getErrorActionAccess().getGroup()); }
		(rule__ErrorAction__Group__0)
		{ after(grammarAccess.getErrorActionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleStartSession
entryRuleStartSession
:
{ before(grammarAccess.getStartSessionRule()); }
	 ruleStartSession
{ after(grammarAccess.getStartSessionRule()); } 
	 EOF 
;

// Rule StartSession
ruleStartSession 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getStartSessionAccess().getGroup()); }
		(rule__StartSession__Group__0)
		{ after(grammarAccess.getStartSessionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleFunctionCallStatement
entryRuleFunctionCallStatement
:
{ before(grammarAccess.getFunctionCallStatementRule()); }
	 ruleFunctionCallStatement
{ after(grammarAccess.getFunctionCallStatementRule()); } 
	 EOF 
;

// Rule FunctionCallStatement
ruleFunctionCallStatement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getFunctionCallStatementAccess().getGroup()); }
		(rule__FunctionCallStatement__Group__0)
		{ after(grammarAccess.getFunctionCallStatementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleExpression
entryRuleExpression
:
{ before(grammarAccess.getExpressionRule()); }
	 ruleExpression
{ after(grammarAccess.getExpressionRule()); } 
	 EOF 
;

// Rule Expression
ruleExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getExpressionAccess().getOrExpressionParserRuleCall()); }
		ruleOrExpression
		{ after(grammarAccess.getExpressionAccess().getOrExpressionParserRuleCall()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleOrExpression
entryRuleOrExpression
:
{ before(grammarAccess.getOrExpressionRule()); }
	 ruleOrExpression
{ after(grammarAccess.getOrExpressionRule()); } 
	 EOF 
;

// Rule OrExpression
ruleOrExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getOrExpressionAccess().getGroup()); }
		(rule__OrExpression__Group__0)
		{ after(grammarAccess.getOrExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleAndExpression
entryRuleAndExpression
:
{ before(grammarAccess.getAndExpressionRule()); }
	 ruleAndExpression
{ after(grammarAccess.getAndExpressionRule()); } 
	 EOF 
;

// Rule AndExpression
ruleAndExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getAndExpressionAccess().getGroup()); }
		(rule__AndExpression__Group__0)
		{ after(grammarAccess.getAndExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEquality
entryRuleEquality
:
{ before(grammarAccess.getEqualityRule()); }
	 ruleEquality
{ after(grammarAccess.getEqualityRule()); } 
	 EOF 
;

// Rule Equality
ruleEquality 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEqualityAccess().getGroup()); }
		(rule__Equality__Group__0)
		{ after(grammarAccess.getEqualityAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleComparaison
entryRuleComparaison
:
{ before(grammarAccess.getComparaisonRule()); }
	 ruleComparaison
{ after(grammarAccess.getComparaisonRule()); } 
	 EOF 
;

// Rule Comparaison
ruleComparaison 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getComparaisonAccess().getGroup()); }
		(rule__Comparaison__Group__0)
		{ after(grammarAccess.getComparaisonAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleAddition
entryRuleAddition
:
{ before(grammarAccess.getAdditionRule()); }
	 ruleAddition
{ after(grammarAccess.getAdditionRule()); } 
	 EOF 
;

// Rule Addition
ruleAddition 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getAdditionAccess().getGroup()); }
		(rule__Addition__Group__0)
		{ after(grammarAccess.getAdditionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleMultiplication
entryRuleMultiplication
:
{ before(grammarAccess.getMultiplicationRule()); }
	 ruleMultiplication
{ after(grammarAccess.getMultiplicationRule()); } 
	 EOF 
;

// Rule Multiplication
ruleMultiplication 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getMultiplicationAccess().getGroup()); }
		(rule__Multiplication__Group__0)
		{ after(grammarAccess.getMultiplicationAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleModulo
entryRuleModulo
:
{ before(grammarAccess.getModuloRule()); }
	 ruleModulo
{ after(grammarAccess.getModuloRule()); } 
	 EOF 
;

// Rule Modulo
ruleModulo 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getModuloAccess().getGroup()); }
		(rule__Modulo__Group__0)
		{ after(grammarAccess.getModuloAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRulePrimary
entryRulePrimary
:
{ before(grammarAccess.getPrimaryRule()); }
	 rulePrimary
{ after(grammarAccess.getPrimaryRule()); } 
	 EOF 
;

// Rule Primary
rulePrimary 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getPrimaryAccess().getAlternatives()); }
		(rule__Primary__Alternatives)
		{ after(grammarAccess.getPrimaryAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleArrayIndexPostfix
entryRuleArrayIndexPostfix
:
{ before(grammarAccess.getArrayIndexPostfixRule()); }
	 ruleArrayIndexPostfix
{ after(grammarAccess.getArrayIndexPostfixRule()); } 
	 EOF 
;

// Rule ArrayIndexPostfix
ruleArrayIndexPostfix 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getArrayIndexPostfixAccess().getGroup()); }
		(rule__ArrayIndexPostfix__Group__0)
		{ after(grammarAccess.getArrayIndexPostfixAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleAtomicExpression
entryRuleAtomicExpression
:
{ before(grammarAccess.getAtomicExpressionRule()); }
	 ruleAtomicExpression
{ after(grammarAccess.getAtomicExpressionRule()); } 
	 EOF 
;

// Rule AtomicExpression
ruleAtomicExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getAtomicExpressionAccess().getAlternatives()); }
		(rule__AtomicExpression__Alternatives)
		{ after(grammarAccess.getAtomicExpressionAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleExternExpression
entryRuleExternExpression
:
{ before(grammarAccess.getExternExpressionRule()); }
	 ruleExternExpression
{ after(grammarAccess.getExternExpressionRule()); } 
	 EOF 
;

// Rule ExternExpression
ruleExternExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getExternExpressionAccess().getGroup()); }
		(rule__ExternExpression__Group__0)
		{ after(grammarAccess.getExternExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEnumLiteralRef
entryRuleEnumLiteralRef
:
{ before(grammarAccess.getEnumLiteralRefRule()); }
	 ruleEnumLiteralRef
{ after(grammarAccess.getEnumLiteralRefRule()); } 
	 EOF 
;

// Rule EnumLiteralRef
ruleEnumLiteralRef 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEnumLiteralRefAccess().getGroup()); }
		(rule__EnumLiteralRef__Group__0)
		{ after(grammarAccess.getEnumLiteralRefAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleIntegerLiteral
entryRuleIntegerLiteral
:
{ before(grammarAccess.getIntegerLiteralRule()); }
	 ruleIntegerLiteral
{ after(grammarAccess.getIntegerLiteralRule()); } 
	 EOF 
;

// Rule IntegerLiteral
ruleIntegerLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getIntegerLiteralAccess().getIntValueAssignment()); }
		(rule__IntegerLiteral__IntValueAssignment)
		{ after(grammarAccess.getIntegerLiteralAccess().getIntValueAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleBooleanLiteral
entryRuleBooleanLiteral
:
{ before(grammarAccess.getBooleanLiteralRule()); }
	 ruleBooleanLiteral
{ after(grammarAccess.getBooleanLiteralRule()); } 
	 EOF 
;

// Rule BooleanLiteral
ruleBooleanLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getBooleanLiteralAccess().getAlternatives()); }
		(rule__BooleanLiteral__Alternatives)
		{ after(grammarAccess.getBooleanLiteralAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleStringLiteral
entryRuleStringLiteral
:
{ before(grammarAccess.getStringLiteralRule()); }
	 ruleStringLiteral
{ after(grammarAccess.getStringLiteralRule()); } 
	 EOF 
;

// Rule StringLiteral
ruleStringLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getStringLiteralAccess().getStringValueAssignment()); }
		(rule__StringLiteral__StringValueAssignment)
		{ after(grammarAccess.getStringLiteralAccess().getStringValueAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleDoubleLiteral
entryRuleDoubleLiteral
:
{ before(grammarAccess.getDoubleLiteralRule()); }
	 ruleDoubleLiteral
{ after(grammarAccess.getDoubleLiteralRule()); } 
	 EOF 
;

// Rule DoubleLiteral
ruleDoubleLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getDoubleLiteralAccess().getDoubleValueAssignment()); }
		(rule__DoubleLiteral__DoubleValueAssignment)
		{ after(grammarAccess.getDoubleLiteralAccess().getDoubleValueAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRulePropertyReference
entryRulePropertyReference
:
{ before(grammarAccess.getPropertyReferenceRule()); }
	 rulePropertyReference
{ after(grammarAccess.getPropertyReferenceRule()); } 
	 EOF 
;

// Rule PropertyReference
rulePropertyReference 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getPropertyReferenceAccess().getPropertyAssignment()); }
		(rule__PropertyReference__PropertyAssignment)
		{ after(grammarAccess.getPropertyReferenceAccess().getPropertyAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEventReference
entryRuleEventReference
:
{ before(grammarAccess.getEventReferenceRule()); }
	 ruleEventReference
{ after(grammarAccess.getEventReferenceRule()); } 
	 EOF 
;

// Rule EventReference
ruleEventReference 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEventReferenceAccess().getGroup()); }
		(rule__EventReference__Group__0)
		{ after(grammarAccess.getEventReferenceAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleFunctionCallExpression
entryRuleFunctionCallExpression
:
{ before(grammarAccess.getFunctionCallExpressionRule()); }
	 ruleFunctionCallExpression
{ after(grammarAccess.getFunctionCallExpressionRule()); } 
	 EOF 
;

// Rule FunctionCallExpression
ruleFunctionCallExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getFunctionCallExpressionAccess().getGroup()); }
		(rule__FunctionCallExpression__Group__0)
		{ after(grammarAccess.getFunctionCallExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleConfiguration
entryRuleConfiguration
:
{ before(grammarAccess.getConfigurationRule()); }
	 ruleConfiguration
{ after(grammarAccess.getConfigurationRule()); } 
	 EOF 
;

// Rule Configuration
ruleConfiguration 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getConfigurationAccess().getGroup()); }
		(rule__Configuration__Group__0)
		{ after(grammarAccess.getConfigurationAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleInstance
entryRuleInstance
:
{ before(grammarAccess.getInstanceRule()); }
	 ruleInstance
{ after(grammarAccess.getInstanceRule()); } 
	 EOF 
;

// Rule Instance
ruleInstance 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getInstanceAccess().getGroup()); }
		(rule__Instance__Group__0)
		{ after(grammarAccess.getInstanceAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleConfigPropertyAssign
entryRuleConfigPropertyAssign
:
{ before(grammarAccess.getConfigPropertyAssignRule()); }
	 ruleConfigPropertyAssign
{ after(grammarAccess.getConfigPropertyAssignRule()); } 
	 EOF 
;

// Rule ConfigPropertyAssign
ruleConfigPropertyAssign 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getConfigPropertyAssignAccess().getGroup()); }
		(rule__ConfigPropertyAssign__Group__0)
		{ after(grammarAccess.getConfigPropertyAssignAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleAbstractConnector
entryRuleAbstractConnector
:
{ before(grammarAccess.getAbstractConnectorRule()); }
	 ruleAbstractConnector
{ after(grammarAccess.getAbstractConnectorRule()); } 
	 EOF 
;

// Rule AbstractConnector
ruleAbstractConnector 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getAbstractConnectorAccess().getAlternatives()); }
		(rule__AbstractConnector__Alternatives)
		{ after(grammarAccess.getAbstractConnectorAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleConnector
entryRuleConnector
:
{ before(grammarAccess.getConnectorRule()); }
	 ruleConnector
{ after(grammarAccess.getConnectorRule()); } 
	 EOF 
;

// Rule Connector
ruleConnector 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getConnectorAccess().getGroup()); }
		(rule__Connector__Group__0)
		{ after(grammarAccess.getConnectorAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleExternalConnector
entryRuleExternalConnector
:
{ before(grammarAccess.getExternalConnectorRule()); }
	 ruleExternalConnector
{ after(grammarAccess.getExternalConnectorRule()); } 
	 EOF 
;

// Rule ExternalConnector
ruleExternalConnector 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getExternalConnectorAccess().getGroup()); }
		(rule__ExternalConnector__Group__0)
		{ after(grammarAccess.getExternalConnectorAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThingMLModel__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingMLModelAccess().getTypesAssignment_1_0()); }
		(rule__ThingMLModel__TypesAssignment_1_0)
		{ after(grammarAccess.getThingMLModelAccess().getTypesAssignment_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getThingMLModelAccess().getProtocolsAssignment_1_1()); }
		(rule__ThingMLModel__ProtocolsAssignment_1_1)
		{ after(grammarAccess.getThingMLModelAccess().getProtocolsAssignment_1_1()); }
	)
	|
	(
		{ before(grammarAccess.getThingMLModelAccess().getConfigsAssignment_1_2()); }
		(rule__ThingMLModel__ConfigsAssignment_1_2)
		{ after(grammarAccess.getThingMLModelAccess().getConfigsAssignment_1_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Variable__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVariableAccess().getGroup_0()); }
		(rule__Variable__Group_0__0)
		{ after(grammarAccess.getVariableAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getVariableAccess().getLocalVariableParserRuleCall_1()); }
		ruleLocalVariable
		{ after(grammarAccess.getVariableAccess().getLocalVariableParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getVariableAccess().getPropertyParserRuleCall_2()); }
		ruleProperty
		{ after(grammarAccess.getVariableAccess().getPropertyParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getVariableAccess().getParameterParserRuleCall_3()); }
		ruleParameter
		{ after(grammarAccess.getVariableAccess().getParameterParserRuleCall_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Type__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeAccess().getPrimitiveTypeParserRuleCall_0()); }
		rulePrimitiveType
		{ after(grammarAccess.getTypeAccess().getPrimitiveTypeParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getObjectTypeParserRuleCall_1()); }
		ruleObjectType
		{ after(grammarAccess.getTypeAccess().getObjectTypeParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getEnumerationParserRuleCall_2()); }
		ruleEnumeration
		{ after(grammarAccess.getTypeAccess().getEnumerationParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getTypeAccess().getThingParserRuleCall_3()); }
		ruleThing
		{ after(grammarAccess.getTypeAccess().getThingParserRuleCall_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Alternatives_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingAccess().getMessagesAssignment_6_0()); }
		(rule__Thing__MessagesAssignment_6_0)
		{ after(grammarAccess.getThingAccess().getMessagesAssignment_6_0()); }
	)
	|
	(
		{ before(grammarAccess.getThingAccess().getPortsAssignment_6_1()); }
		(rule__Thing__PortsAssignment_6_1)
		{ after(grammarAccess.getThingAccess().getPortsAssignment_6_1()); }
	)
	|
	(
		{ before(grammarAccess.getThingAccess().getPropertiesAssignment_6_2()); }
		(rule__Thing__PropertiesAssignment_6_2)
		{ after(grammarAccess.getThingAccess().getPropertiesAssignment_6_2()); }
	)
	|
	(
		{ before(grammarAccess.getThingAccess().getFunctionsAssignment_6_3()); }
		(rule__Thing__FunctionsAssignment_6_3)
		{ after(grammarAccess.getThingAccess().getFunctionsAssignment_6_3()); }
	)
	|
	(
		{ before(grammarAccess.getThingAccess().getAssignAssignment_6_4()); }
		(rule__Thing__AssignAssignment_6_4)
		{ after(grammarAccess.getThingAccess().getAssignAssignment_6_4()); }
	)
	|
	(
		{ before(grammarAccess.getThingAccess().getBehaviourAssignment_6_5()); }
		(rule__Thing__BehaviourAssignment_6_5)
		{ after(grammarAccess.getThingAccess().getBehaviourAssignment_6_5()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Port__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPortAccess().getRequiredPortParserRuleCall_0()); }
		ruleRequiredPort
		{ after(grammarAccess.getPortAccess().getRequiredPortParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getPortAccess().getProvidedPortParserRuleCall_1()); }
		ruleProvidedPort
		{ after(grammarAccess.getPortAccess().getProvidedPortParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getPortAccess().getInternalPortParserRuleCall_2()); }
		ruleInternalPort
		{ after(grammarAccess.getPortAccess().getInternalPortParserRuleCall_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Alternatives_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRequiredPortAccess().getGroup_6_0()); }
		(rule__RequiredPort__Group_6_0__0)
		{ after(grammarAccess.getRequiredPortAccess().getGroup_6_0()); }
	)
	|
	(
		{ before(grammarAccess.getRequiredPortAccess().getGroup_6_1()); }
		(rule__RequiredPort__Group_6_1__0)
		{ after(grammarAccess.getRequiredPortAccess().getGroup_6_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Alternatives_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProvidedPortAccess().getGroup_5_0()); }
		(rule__ProvidedPort__Group_5_0__0)
		{ after(grammarAccess.getProvidedPortAccess().getGroup_5_0()); }
	)
	|
	(
		{ before(grammarAccess.getProvidedPortAccess().getGroup_5_1()); }
		(rule__ProvidedPort__Group_5_1__0)
		{ after(grammarAccess.getProvidedPortAccess().getGroup_5_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Alternatives_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInternalPortAccess().getGroup_5_0()); }
		(rule__InternalPort__Group_5_0__0)
		{ after(grammarAccess.getInternalPortAccess().getGroup_5_0()); }
	)
	|
	(
		{ before(grammarAccess.getInternalPortAccess().getGroup_5_1()); }
		(rule__InternalPort__Group_5_1__0)
		{ after(grammarAccess.getInternalPortAccess().getGroup_5_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getStateMachineParserRuleCall_0()); }
		ruleStateMachine
		{ after(grammarAccess.getStateAccess().getStateMachineParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getStateAccess().getFinalStateParserRuleCall_1()); }
		ruleFinalState
		{ after(grammarAccess.getStateAccess().getFinalStateParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getStateAccess().getCompositeStateParserRuleCall_2()); }
		ruleCompositeState
		{ after(grammarAccess.getStateAccess().getCompositeStateParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getStateAccess().getGroup_3()); }
		(rule__State__Group_3__0)
		{ after(grammarAccess.getStateAccess().getGroup_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Alternatives_3_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getInternalAssignment_3_7_0()); }
		(rule__State__InternalAssignment_3_7_0)
		{ after(grammarAccess.getStateAccess().getInternalAssignment_3_7_0()); }
	)
	|
	(
		{ before(grammarAccess.getStateAccess().getOutgoingAssignment_3_7_1()); }
		(rule__State__OutgoingAssignment_3_7_1)
		{ after(grammarAccess.getStateAccess().getOutgoingAssignment_3_7_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Handler__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getHandlerAccess().getTransitionParserRuleCall_0()); }
		ruleTransition
		{ after(grammarAccess.getHandlerAccess().getTransitionParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getHandlerAccess().getInternalTransitionParserRuleCall_1()); }
		ruleInternalTransition
		{ after(grammarAccess.getHandlerAccess().getInternalTransitionParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Alternatives_11
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getSubstateAssignment_11_0()); }
		(rule__CompositeState__SubstateAssignment_11_0)
		{ after(grammarAccess.getCompositeStateAccess().getSubstateAssignment_11_0()); }
	)
	|
	(
		{ before(grammarAccess.getCompositeStateAccess().getInternalAssignment_11_1()); }
		(rule__CompositeState__InternalAssignment_11_1)
		{ after(grammarAccess.getCompositeStateAccess().getInternalAssignment_11_1()); }
	)
	|
	(
		{ before(grammarAccess.getCompositeStateAccess().getOutgoingAssignment_11_2()); }
		(rule__CompositeState__OutgoingAssignment_11_2)
		{ after(grammarAccess.getCompositeStateAccess().getOutgoingAssignment_11_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Alternatives_12
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getRegionAssignment_12_0()); }
		(rule__CompositeState__RegionAssignment_12_0)
		{ after(grammarAccess.getCompositeStateAccess().getRegionAssignment_12_0()); }
	)
	|
	(
		{ before(grammarAccess.getCompositeStateAccess().getSessionAssignment_12_1()); }
		(rule__CompositeState__SessionAssignment_12_1)
		{ after(grammarAccess.getCompositeStateAccess().getSessionAssignment_12_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Alternatives_10
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getSubstateAssignment_10_0()); }
		(rule__StateMachine__SubstateAssignment_10_0)
		{ after(grammarAccess.getStateMachineAccess().getSubstateAssignment_10_0()); }
	)
	|
	(
		{ before(grammarAccess.getStateMachineAccess().getInternalAssignment_10_1()); }
		(rule__StateMachine__InternalAssignment_10_1)
		{ after(grammarAccess.getStateMachineAccess().getInternalAssignment_10_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Alternatives_11
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getRegionAssignment_11_0()); }
		(rule__StateMachine__RegionAssignment_11_0)
		{ after(grammarAccess.getStateMachineAccess().getRegionAssignment_11_0()); }
	)
	|
	(
		{ before(grammarAccess.getStateMachineAccess().getSessionAssignment_11_1()); }
		(rule__StateMachine__SessionAssignment_11_1)
		{ after(grammarAccess.getStateMachineAccess().getSessionAssignment_11_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__MaxInstancesAlternatives_2_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSessionAccess().getMaxInstancesIntegerLiteralParserRuleCall_2_1_0_0()); }
		ruleIntegerLiteral
		{ after(grammarAccess.getSessionAccess().getMaxInstancesIntegerLiteralParserRuleCall_2_1_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getSessionAccess().getMaxInstancesPropertyReferenceParserRuleCall_2_1_0_1()); }
		rulePropertyReference
		{ after(grammarAccess.getSessionAccess().getMaxInstancesPropertyReferenceParserRuleCall_2_1_0_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateContainerAccess().getCompositeStateParserRuleCall_0()); }
		ruleCompositeState
		{ after(grammarAccess.getStateContainerAccess().getCompositeStateParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getStateContainerAccess().getRegionParserRuleCall_1()); }
		ruleRegion
		{ after(grammarAccess.getStateContainerAccess().getRegionParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getStateContainerAccess().getSessionParserRuleCall_2()); }
		ruleSession
		{ after(grammarAccess.getStateContainerAccess().getSessionParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getStateContainerAccess().getGroup_3()); }
		(rule__StateContainer__Group_3__0)
		{ after(grammarAccess.getStateContainerAccess().getGroup_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Action__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getActionAccess().getActionBlockParserRuleCall_0()); }
		ruleActionBlock
		{ after(grammarAccess.getActionAccess().getActionBlockParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getExternStatementParserRuleCall_1()); }
		ruleExternStatement
		{ after(grammarAccess.getActionAccess().getExternStatementParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getSendActionParserRuleCall_2()); }
		ruleSendAction
		{ after(grammarAccess.getActionAccess().getSendActionParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getVariableAssignmentParserRuleCall_3()); }
		ruleVariableAssignment
		{ after(grammarAccess.getActionAccess().getVariableAssignmentParserRuleCall_3()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getIncrementParserRuleCall_4()); }
		ruleIncrement
		{ after(grammarAccess.getActionAccess().getIncrementParserRuleCall_4()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getDecrementParserRuleCall_5()); }
		ruleDecrement
		{ after(grammarAccess.getActionAccess().getDecrementParserRuleCall_5()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getLoopActionParserRuleCall_6()); }
		ruleLoopAction
		{ after(grammarAccess.getActionAccess().getLoopActionParserRuleCall_6()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getConditionalActionParserRuleCall_7()); }
		ruleConditionalAction
		{ after(grammarAccess.getActionAccess().getConditionalActionParserRuleCall_7()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getReturnActionParserRuleCall_8()); }
		ruleReturnAction
		{ after(grammarAccess.getActionAccess().getReturnActionParserRuleCall_8()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getPrintActionParserRuleCall_9()); }
		rulePrintAction
		{ after(grammarAccess.getActionAccess().getPrintActionParserRuleCall_9()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getErrorActionParserRuleCall_10()); }
		ruleErrorAction
		{ after(grammarAccess.getActionAccess().getErrorActionParserRuleCall_10()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getStartSessionParserRuleCall_11()); }
		ruleStartSession
		{ after(grammarAccess.getActionAccess().getStartSessionParserRuleCall_11()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getFunctionCallStatementParserRuleCall_12()); }
		ruleFunctionCallStatement
		{ after(grammarAccess.getActionAccess().getFunctionCallStatementParserRuleCall_12()); }
	)
	|
	(
		{ before(grammarAccess.getActionAccess().getLocalVariableParserRuleCall_13()); }
		ruleLocalVariable
		{ after(grammarAccess.getActionAccess().getLocalVariableParserRuleCall_13()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEqualityAccess().getGroup_1_0()); }
		(rule__Equality__Group_1_0__0)
		{ after(grammarAccess.getEqualityAccess().getGroup_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getEqualityAccess().getGroup_1_1()); }
		(rule__Equality__Group_1_1__0)
		{ after(grammarAccess.getEqualityAccess().getGroup_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getComparaisonAccess().getGroup_1_0()); }
		(rule__Comparaison__Group_1_0__0)
		{ after(grammarAccess.getComparaisonAccess().getGroup_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getComparaisonAccess().getGroup_1_1()); }
		(rule__Comparaison__Group_1_1__0)
		{ after(grammarAccess.getComparaisonAccess().getGroup_1_1()); }
	)
	|
	(
		{ before(grammarAccess.getComparaisonAccess().getGroup_1_2()); }
		(rule__Comparaison__Group_1_2__0)
		{ after(grammarAccess.getComparaisonAccess().getGroup_1_2()); }
	)
	|
	(
		{ before(grammarAccess.getComparaisonAccess().getGroup_1_3()); }
		(rule__Comparaison__Group_1_3__0)
		{ after(grammarAccess.getComparaisonAccess().getGroup_1_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAdditionAccess().getGroup_1_0()); }
		(rule__Addition__Group_1_0__0)
		{ after(grammarAccess.getAdditionAccess().getGroup_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getAdditionAccess().getGroup_1_1()); }
		(rule__Addition__Group_1_1__0)
		{ after(grammarAccess.getAdditionAccess().getGroup_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getMultiplicationAccess().getGroup_1_0()); }
		(rule__Multiplication__Group_1_0__0)
		{ after(grammarAccess.getMultiplicationAccess().getGroup_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getMultiplicationAccess().getGroup_1_1()); }
		(rule__Multiplication__Group_1_1__0)
		{ after(grammarAccess.getMultiplicationAccess().getGroup_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrimaryAccess().getGroup_0()); }
		(rule__Primary__Group_0__0)
		{ after(grammarAccess.getPrimaryAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getPrimaryAccess().getGroup_1()); }
		(rule__Primary__Group_1__0)
		{ after(grammarAccess.getPrimaryAccess().getGroup_1()); }
	)
	|
	(
		{ before(grammarAccess.getPrimaryAccess().getGroup_2()); }
		(rule__Primary__Group_2__0)
		{ after(grammarAccess.getPrimaryAccess().getGroup_2()); }
	)
	|
	(
		{ before(grammarAccess.getPrimaryAccess().getArrayIndexPostfixParserRuleCall_3()); }
		ruleArrayIndexPostfix
		{ after(grammarAccess.getPrimaryAccess().getArrayIndexPostfixParserRuleCall_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__AtomicExpression__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAtomicExpressionAccess().getExternExpressionParserRuleCall_0()); }
		ruleExternExpression
		{ after(grammarAccess.getAtomicExpressionAccess().getExternExpressionParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getAtomicExpressionAccess().getEnumLiteralRefParserRuleCall_1()); }
		ruleEnumLiteralRef
		{ after(grammarAccess.getAtomicExpressionAccess().getEnumLiteralRefParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getAtomicExpressionAccess().getIntegerLiteralParserRuleCall_2()); }
		ruleIntegerLiteral
		{ after(grammarAccess.getAtomicExpressionAccess().getIntegerLiteralParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getAtomicExpressionAccess().getBooleanLiteralParserRuleCall_3()); }
		ruleBooleanLiteral
		{ after(grammarAccess.getAtomicExpressionAccess().getBooleanLiteralParserRuleCall_3()); }
	)
	|
	(
		{ before(grammarAccess.getAtomicExpressionAccess().getStringLiteralParserRuleCall_4()); }
		ruleStringLiteral
		{ after(grammarAccess.getAtomicExpressionAccess().getStringLiteralParserRuleCall_4()); }
	)
	|
	(
		{ before(grammarAccess.getAtomicExpressionAccess().getDoubleLiteralParserRuleCall_5()); }
		ruleDoubleLiteral
		{ after(grammarAccess.getAtomicExpressionAccess().getDoubleLiteralParserRuleCall_5()); }
	)
	|
	(
		{ before(grammarAccess.getAtomicExpressionAccess().getPropertyReferenceParserRuleCall_6()); }
		rulePropertyReference
		{ after(grammarAccess.getAtomicExpressionAccess().getPropertyReferenceParserRuleCall_6()); }
	)
	|
	(
		{ before(grammarAccess.getAtomicExpressionAccess().getFunctionCallExpressionParserRuleCall_7()); }
		ruleFunctionCallExpression
		{ after(grammarAccess.getAtomicExpressionAccess().getFunctionCallExpressionParserRuleCall_7()); }
	)
	|
	(
		{ before(grammarAccess.getAtomicExpressionAccess().getEventReferenceParserRuleCall_8()); }
		ruleEventReference
		{ after(grammarAccess.getAtomicExpressionAccess().getEventReferenceParserRuleCall_8()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__BooleanLiteral__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getBooleanLiteralAccess().getBoolValueAssignment_0()); }
		(rule__BooleanLiteral__BoolValueAssignment_0)
		{ after(grammarAccess.getBooleanLiteralAccess().getBoolValueAssignment_0()); }
	)
	|
	(
		{ before(grammarAccess.getBooleanLiteralAccess().getGroup_1()); }
		(rule__BooleanLiteral__Group_1__0)
		{ after(grammarAccess.getBooleanLiteralAccess().getGroup_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__Alternatives_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConfigurationAccess().getInstancesAssignment_4_0()); }
		(rule__Configuration__InstancesAssignment_4_0)
		{ after(grammarAccess.getConfigurationAccess().getInstancesAssignment_4_0()); }
	)
	|
	(
		{ before(grammarAccess.getConfigurationAccess().getConnectorsAssignment_4_1()); }
		(rule__Configuration__ConnectorsAssignment_4_1)
		{ after(grammarAccess.getConfigurationAccess().getConnectorsAssignment_4_1()); }
	)
	|
	(
		{ before(grammarAccess.getConfigurationAccess().getPropassignsAssignment_4_2()); }
		(rule__Configuration__PropassignsAssignment_4_2)
		{ after(grammarAccess.getConfigurationAccess().getPropassignsAssignment_4_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__AbstractConnector__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAbstractConnectorAccess().getConnectorParserRuleCall_0()); }
		ruleConnector
		{ after(grammarAccess.getAbstractConnectorAccess().getConnectorParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getAbstractConnectorAccess().getExternalConnectorParserRuleCall_1()); }
		ruleExternalConnector
		{ after(grammarAccess.getAbstractConnectorAccess().getExternalConnectorParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThingMLModel__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ThingMLModel__Group__0__Impl
	rule__ThingMLModel__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ThingMLModel__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingMLModelAccess().getGroup_0()); }
	(rule__ThingMLModel__Group_0__0)*
	{ after(grammarAccess.getThingMLModelAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThingMLModel__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ThingMLModel__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ThingMLModel__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingMLModelAccess().getAlternatives_1()); }
	(rule__ThingMLModel__Alternatives_1)*
	{ after(grammarAccess.getThingMLModelAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ThingMLModel__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ThingMLModel__Group_0__0__Impl
	rule__ThingMLModel__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ThingMLModel__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingMLModelAccess().getImportKeyword_0_0()); }
	'import'
	{ after(grammarAccess.getThingMLModelAccess().getImportKeyword_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThingMLModel__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ThingMLModel__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ThingMLModel__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingMLModelAccess().getImportURIAssignment_0_1()); }
	(rule__ThingMLModel__ImportURIAssignment_0_1)
	{ after(grammarAccess.getThingMLModelAccess().getImportURIAssignment_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__PlatformAnnotation__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PlatformAnnotation__Group__0__Impl
	rule__PlatformAnnotation__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__PlatformAnnotation__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPlatformAnnotationAccess().getNameAssignment_0()); }
	(rule__PlatformAnnotation__NameAssignment_0)
	{ after(grammarAccess.getPlatformAnnotationAccess().getNameAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PlatformAnnotation__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PlatformAnnotation__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__PlatformAnnotation__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPlatformAnnotationAccess().getValueAssignment_1()); }
	(rule__PlatformAnnotation__ValueAssignment_1)
	{ after(grammarAccess.getPlatformAnnotationAccess().getValueAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Variable__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Variable__Group_0__0__Impl
	rule__Variable__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Variable__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVariableAccess().getVarKeyword_0_0()); }
	'var'
	{ after(grammarAccess.getVariableAccess().getVarKeyword_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Variable__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Variable__Group_0__1__Impl
	rule__Variable__Group_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Variable__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVariableAccess().getNameAssignment_0_1()); }
	(rule__Variable__NameAssignment_0_1)
	{ after(grammarAccess.getVariableAccess().getNameAssignment_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Variable__Group_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Variable__Group_0__2__Impl
	rule__Variable__Group_0__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Variable__Group_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVariableAccess().getColonKeyword_0_2()); }
	':'
	{ after(grammarAccess.getVariableAccess().getColonKeyword_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Variable__Group_0__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Variable__Group_0__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Variable__Group_0__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVariableAccess().getTypeRefAssignment_0_3()); }
	(rule__Variable__TypeRefAssignment_0_3)
	{ after(grammarAccess.getVariableAccess().getTypeRefAssignment_0_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeRef__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeRef__Group__0__Impl
	rule__TypeRef__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeRefAccess().getTypeAssignment_0()); }
	(rule__TypeRef__TypeAssignment_0)
	{ after(grammarAccess.getTypeRefAccess().getTypeAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeRef__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeRefAccess().getGroup_1()); }
	(rule__TypeRef__Group_1__0)?
	{ after(grammarAccess.getTypeRefAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TypeRef__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeRef__Group_1__0__Impl
	rule__TypeRef__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeRefAccess().getIsArrayAssignment_1_0()); }
	(rule__TypeRef__IsArrayAssignment_1_0)
	{ after(grammarAccess.getTypeRefAccess().getIsArrayAssignment_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeRef__Group_1__1__Impl
	rule__TypeRef__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeRefAccess().getCardinalityAssignment_1_1()); }
	(rule__TypeRef__CardinalityAssignment_1_1)?
	{ after(grammarAccess.getTypeRefAccess().getCardinalityAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TypeRef__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTypeRefAccess().getRightSquareBracketKeyword_1_2()); }
	']'
	{ after(grammarAccess.getTypeRefAccess().getRightSquareBracketKeyword_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__PrimitiveType__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__0__Impl
	rule__PrimitiveType__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getDatatypeKeyword_0()); }
	'datatype'
	{ after(grammarAccess.getPrimitiveTypeAccess().getDatatypeKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__1__Impl
	rule__PrimitiveType__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getNameAssignment_1()); }
	(rule__PrimitiveType__NameAssignment_1)
	{ after(grammarAccess.getPrimitiveTypeAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__2__Impl
	rule__PrimitiveType__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getLessThanSignKeyword_2()); }
	'<'
	{ after(grammarAccess.getPrimitiveTypeAccess().getLessThanSignKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__3__Impl
	rule__PrimitiveType__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getByteSizeAssignment_3()); }
	(rule__PrimitiveType__ByteSizeAssignment_3)
	{ after(grammarAccess.getPrimitiveTypeAccess().getByteSizeAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__4__Impl
	rule__PrimitiveType__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getGreaterThanSignKeyword_4()); }
	'>'
	{ after(grammarAccess.getPrimitiveTypeAccess().getGreaterThanSignKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__5__Impl
	rule__PrimitiveType__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getAnnotationsAssignment_5()); }
	(rule__PrimitiveType__AnnotationsAssignment_5)*
	{ after(grammarAccess.getPrimitiveTypeAccess().getAnnotationsAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrimitiveType__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimitiveTypeAccess().getSemicolonKeyword_6()); }
	(';')?
	{ after(grammarAccess.getPrimitiveTypeAccess().getSemicolonKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ObjectType__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ObjectType__Group__0__Impl
	rule__ObjectType__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ObjectType__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getObjectTypeAccess().getObjectKeyword_0()); }
	'object'
	{ after(grammarAccess.getObjectTypeAccess().getObjectKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ObjectType__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ObjectType__Group__1__Impl
	rule__ObjectType__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ObjectType__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getObjectTypeAccess().getNameAssignment_1()); }
	(rule__ObjectType__NameAssignment_1)
	{ after(grammarAccess.getObjectTypeAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ObjectType__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ObjectType__Group__2__Impl
	rule__ObjectType__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ObjectType__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getObjectTypeAccess().getAnnotationsAssignment_2()); }
	(rule__ObjectType__AnnotationsAssignment_2)*
	{ after(grammarAccess.getObjectTypeAccess().getAnnotationsAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ObjectType__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ObjectType__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ObjectType__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getObjectTypeAccess().getSemicolonKeyword_3()); }
	(';')?
	{ after(grammarAccess.getObjectTypeAccess().getSemicolonKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Enumeration__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Enumeration__Group__0__Impl
	rule__Enumeration__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumerationAccess().getEnumerationKeyword_0()); }
	'enumeration'
	{ after(grammarAccess.getEnumerationAccess().getEnumerationKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Enumeration__Group__1__Impl
	rule__Enumeration__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumerationAccess().getNameAssignment_1()); }
	(rule__Enumeration__NameAssignment_1)
	{ after(grammarAccess.getEnumerationAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Enumeration__Group__2__Impl
	rule__Enumeration__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumerationAccess().getAnnotationsAssignment_2()); }
	(rule__Enumeration__AnnotationsAssignment_2)*
	{ after(grammarAccess.getEnumerationAccess().getAnnotationsAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Enumeration__Group__3__Impl
	rule__Enumeration__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumerationAccess().getLeftCurlyBracketKeyword_3()); }
	'{'
	{ after(grammarAccess.getEnumerationAccess().getLeftCurlyBracketKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Enumeration__Group__4__Impl
	rule__Enumeration__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumerationAccess().getLiteralsAssignment_4()); }
	(rule__Enumeration__LiteralsAssignment_4)*
	{ after(grammarAccess.getEnumerationAccess().getLiteralsAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Enumeration__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumerationAccess().getRightCurlyBracketKeyword_5()); }
	'}'
	{ after(grammarAccess.getEnumerationAccess().getRightCurlyBracketKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__EnumerationLiteral__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EnumerationLiteral__Group__0__Impl
	rule__EnumerationLiteral__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumerationLiteral__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumerationLiteralAccess().getNameAssignment_0()); }
	(rule__EnumerationLiteral__NameAssignment_0)
	{ after(grammarAccess.getEnumerationLiteralAccess().getNameAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumerationLiteral__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EnumerationLiteral__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumerationLiteral__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumerationLiteralAccess().getAnnotationsAssignment_1()); }
	(rule__EnumerationLiteral__AnnotationsAssignment_1)*
	{ after(grammarAccess.getEnumerationLiteralAccess().getAnnotationsAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Thing__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group__0__Impl
	rule__Thing__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getThingKeyword_0()); }
	'thing'
	{ after(grammarAccess.getThingAccess().getThingKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group__1__Impl
	rule__Thing__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getFragmentAssignment_1()); }
	(rule__Thing__FragmentAssignment_1)?
	{ after(grammarAccess.getThingAccess().getFragmentAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group__2__Impl
	rule__Thing__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getNameAssignment_2()); }
	(rule__Thing__NameAssignment_2)
	{ after(grammarAccess.getThingAccess().getNameAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group__3__Impl
	rule__Thing__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getGroup_3()); }
	(rule__Thing__Group_3__0)?
	{ after(grammarAccess.getThingAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group__4__Impl
	rule__Thing__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getAnnotationsAssignment_4()); }
	(rule__Thing__AnnotationsAssignment_4)*
	{ after(grammarAccess.getThingAccess().getAnnotationsAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group__5__Impl
	rule__Thing__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getLeftCurlyBracketKeyword_5()); }
	'{'
	{ after(grammarAccess.getThingAccess().getLeftCurlyBracketKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group__6__Impl
	rule__Thing__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getAlternatives_6()); }
	(rule__Thing__Alternatives_6)*
	{ after(grammarAccess.getThingAccess().getAlternatives_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group__7__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getRightCurlyBracketKeyword_7()); }
	'}'
	{ after(grammarAccess.getThingAccess().getRightCurlyBracketKeyword_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Thing__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group_3__0__Impl
	rule__Thing__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getIncludesKeyword_3_0()); }
	'includes'
	{ after(grammarAccess.getThingAccess().getIncludesKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group_3__1__Impl
	rule__Thing__Group_3__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getIncludesAssignment_3_1()); }
	(rule__Thing__IncludesAssignment_3_1)
	{ after(grammarAccess.getThingAccess().getIncludesAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group_3__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group_3__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group_3__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getGroup_3_2()); }
	(rule__Thing__Group_3_2__0)*
	{ after(grammarAccess.getThingAccess().getGroup_3_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Thing__Group_3_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group_3_2__0__Impl
	rule__Thing__Group_3_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group_3_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getCommaKeyword_3_2_0()); }
	','
	{ after(grammarAccess.getThingAccess().getCommaKeyword_3_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group_3_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Thing__Group_3_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__Group_3_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getThingAccess().getIncludesAssignment_3_2_1()); }
	(rule__Thing__IncludesAssignment_3_2_1)
	{ after(grammarAccess.getThingAccess().getIncludesAssignment_3_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__PropertyAssign__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PropertyAssign__Group__0__Impl
	rule__PropertyAssign__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAssignAccess().getSetKeyword_0()); }
	'set'
	{ after(grammarAccess.getPropertyAssignAccess().getSetKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PropertyAssign__Group__1__Impl
	rule__PropertyAssign__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAssignAccess().getPropertyAssignment_1()); }
	(rule__PropertyAssign__PropertyAssignment_1)
	{ after(grammarAccess.getPropertyAssignAccess().getPropertyAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PropertyAssign__Group__2__Impl
	rule__PropertyAssign__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAssignAccess().getGroup_2()); }
	(rule__PropertyAssign__Group_2__0)*
	{ after(grammarAccess.getPropertyAssignAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PropertyAssign__Group__3__Impl
	rule__PropertyAssign__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAssignAccess().getEqualsSignKeyword_3()); }
	'='
	{ after(grammarAccess.getPropertyAssignAccess().getEqualsSignKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PropertyAssign__Group__4__Impl
	rule__PropertyAssign__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAssignAccess().getInitAssignment_4()); }
	(rule__PropertyAssign__InitAssignment_4)
	{ after(grammarAccess.getPropertyAssignAccess().getInitAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PropertyAssign__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAssignAccess().getAnnotationsAssignment_5()); }
	(rule__PropertyAssign__AnnotationsAssignment_5)*
	{ after(grammarAccess.getPropertyAssignAccess().getAnnotationsAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__PropertyAssign__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PropertyAssign__Group_2__0__Impl
	rule__PropertyAssign__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAssignAccess().getLeftSquareBracketKeyword_2_0()); }
	'['
	{ after(grammarAccess.getPropertyAssignAccess().getLeftSquareBracketKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PropertyAssign__Group_2__1__Impl
	rule__PropertyAssign__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAssignAccess().getIndexAssignment_2_1()); }
	(rule__PropertyAssign__IndexAssignment_2_1)
	{ after(grammarAccess.getPropertyAssignAccess().getIndexAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PropertyAssign__Group_2__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAssignAccess().getRightSquareBracketKeyword_2_2()); }
	']'
	{ after(grammarAccess.getPropertyAssignAccess().getRightSquareBracketKeyword_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Protocol__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Protocol__Group__0__Impl
	rule__Protocol__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Protocol__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProtocolAccess().getProtocolKeyword_0()); }
	'protocol'
	{ after(grammarAccess.getProtocolAccess().getProtocolKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Protocol__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Protocol__Group__1__Impl
	rule__Protocol__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Protocol__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProtocolAccess().getNameAssignment_1()); }
	(rule__Protocol__NameAssignment_1)
	{ after(grammarAccess.getProtocolAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Protocol__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Protocol__Group__2__Impl
	rule__Protocol__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Protocol__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProtocolAccess().getAnnotationsAssignment_2()); }
	(rule__Protocol__AnnotationsAssignment_2)*
	{ after(grammarAccess.getProtocolAccess().getAnnotationsAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Protocol__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Protocol__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Protocol__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProtocolAccess().getSemicolonKeyword_3()); }
	';'
	{ after(grammarAccess.getProtocolAccess().getSemicolonKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Function__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group__0__Impl
	rule__Function__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getFunctionKeyword_0()); }
	'function'
	{ after(grammarAccess.getFunctionAccess().getFunctionKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group__1__Impl
	rule__Function__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getNameAssignment_1()); }
	(rule__Function__NameAssignment_1)
	{ after(grammarAccess.getFunctionAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group__2__Impl
	rule__Function__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_2()); }
	'('
	{ after(grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group__3__Impl
	rule__Function__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getGroup_3()); }
	(rule__Function__Group_3__0)?
	{ after(grammarAccess.getFunctionAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group__4__Impl
	rule__Function__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getRightParenthesisKeyword_4()); }
	')'
	{ after(grammarAccess.getFunctionAccess().getRightParenthesisKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group__5__Impl
	rule__Function__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getGroup_5()); }
	(rule__Function__Group_5__0)?
	{ after(grammarAccess.getFunctionAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group__6__Impl
	rule__Function__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getAnnotationsAssignment_6()); }
	(rule__Function__AnnotationsAssignment_6)*
	{ after(grammarAccess.getFunctionAccess().getAnnotationsAssignment_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group__7__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getBodyAssignment_7()); }
	(rule__Function__BodyAssignment_7)
	{ after(grammarAccess.getFunctionAccess().getBodyAssignment_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Function__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group_3__0__Impl
	rule__Function__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getParametersAssignment_3_0()); }
	(rule__Function__ParametersAssignment_3_0)
	{ after(grammarAccess.getFunctionAccess().getParametersAssignment_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getGroup_3_1()); }
	(rule__Function__Group_3_1__0)*
	{ after(grammarAccess.getFunctionAccess().getGroup_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Function__Group_3_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group_3_1__0__Impl
	rule__Function__Group_3_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group_3_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getCommaKeyword_3_1_0()); }
	','
	{ after(grammarAccess.getFunctionAccess().getCommaKeyword_3_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group_3_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group_3_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group_3_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getParametersAssignment_3_1_1()); }
	(rule__Function__ParametersAssignment_3_1_1)
	{ after(grammarAccess.getFunctionAccess().getParametersAssignment_3_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Function__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group_5__0__Impl
	rule__Function__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getColonKeyword_5_0()); }
	':'
	{ after(grammarAccess.getFunctionAccess().getColonKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Function__Group_5__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionAccess().getTypeRefAssignment_5_1()); }
	(rule__Function__TypeRefAssignment_5_1)
	{ after(grammarAccess.getFunctionAccess().getTypeRefAssignment_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Property__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Property__Group__0__Impl
	rule__Property__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAccess().getReadonlyAssignment_0()); }
	(rule__Property__ReadonlyAssignment_0)?
	{ after(grammarAccess.getPropertyAccess().getReadonlyAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Property__Group__1__Impl
	rule__Property__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAccess().getPropertyKeyword_1()); }
	'property'
	{ after(grammarAccess.getPropertyAccess().getPropertyKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Property__Group__2__Impl
	rule__Property__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAccess().getNameAssignment_2()); }
	(rule__Property__NameAssignment_2)
	{ after(grammarAccess.getPropertyAccess().getNameAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Property__Group__3__Impl
	rule__Property__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAccess().getColonKeyword_3()); }
	':'
	{ after(grammarAccess.getPropertyAccess().getColonKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Property__Group__4__Impl
	rule__Property__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAccess().getTypeRefAssignment_4()); }
	(rule__Property__TypeRefAssignment_4)
	{ after(grammarAccess.getPropertyAccess().getTypeRefAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Property__Group__5__Impl
	rule__Property__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAccess().getGroup_5()); }
	(rule__Property__Group_5__0)?
	{ after(grammarAccess.getPropertyAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Property__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAccess().getAnnotationsAssignment_6()); }
	(rule__Property__AnnotationsAssignment_6)*
	{ after(grammarAccess.getPropertyAccess().getAnnotationsAssignment_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Property__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Property__Group_5__0__Impl
	rule__Property__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAccess().getEqualsSignKeyword_5_0()); }
	'='
	{ after(grammarAccess.getPropertyAccess().getEqualsSignKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Property__Group_5__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPropertyAccess().getInitAssignment_5_1()); }
	(rule__Property__InitAssignment_5_1)
	{ after(grammarAccess.getPropertyAccess().getInitAssignment_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Message__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Message__Group__0__Impl
	rule__Message__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMessageAccess().getMessageKeyword_0()); }
	'message'
	{ after(grammarAccess.getMessageAccess().getMessageKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Message__Group__1__Impl
	rule__Message__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMessageAccess().getNameAssignment_1()); }
	(rule__Message__NameAssignment_1)
	{ after(grammarAccess.getMessageAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Message__Group__2__Impl
	rule__Message__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMessageAccess().getLeftParenthesisKeyword_2()); }
	'('
	{ after(grammarAccess.getMessageAccess().getLeftParenthesisKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Message__Group__3__Impl
	rule__Message__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMessageAccess().getGroup_3()); }
	(rule__Message__Group_3__0)?
	{ after(grammarAccess.getMessageAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Message__Group__4__Impl
	rule__Message__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMessageAccess().getRightParenthesisKeyword_4()); }
	')'
	{ after(grammarAccess.getMessageAccess().getRightParenthesisKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Message__Group__5__Impl
	rule__Message__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMessageAccess().getAnnotationsAssignment_5()); }
	(rule__Message__AnnotationsAssignment_5)*
	{ after(grammarAccess.getMessageAccess().getAnnotationsAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Message__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMessageAccess().getSemicolonKeyword_6()); }
	(';')?
	{ after(grammarAccess.getMessageAccess().getSemicolonKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Message__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Message__Group_3__0__Impl
	rule__Message__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMessageAccess().getParametersAssignment_3_0()); }
	(rule__Message__ParametersAssignment_3_0)
	{ after(grammarAccess.getMessageAccess().getParametersAssignment_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Message__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMessageAccess().getGroup_3_1()); }
	(rule__Message__Group_3_1__0)*
	{ after(grammarAccess.getMessageAccess().getGroup_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Message__Group_3_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Message__Group_3_1__0__Impl
	rule__Message__Group_3_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group_3_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMessageAccess().getCommaKeyword_3_1_0()); }
	','
	{ after(grammarAccess.getMessageAccess().getCommaKeyword_3_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group_3_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Message__Group_3_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__Group_3_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMessageAccess().getParametersAssignment_3_1_1()); }
	(rule__Message__ParametersAssignment_3_1_1)
	{ after(grammarAccess.getMessageAccess().getParametersAssignment_3_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Parameter__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Parameter__Group__0__Impl
	rule__Parameter__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Parameter__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterAccess().getNameAssignment_0()); }
	(rule__Parameter__NameAssignment_0)
	{ after(grammarAccess.getParameterAccess().getNameAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Parameter__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Parameter__Group__1__Impl
	rule__Parameter__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Parameter__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterAccess().getColonKeyword_1()); }
	':'
	{ after(grammarAccess.getParameterAccess().getColonKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Parameter__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Parameter__Group__2__Impl
	rule__Parameter__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Parameter__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterAccess().getTypeRefAssignment_2()); }
	(rule__Parameter__TypeRefAssignment_2)
	{ after(grammarAccess.getParameterAccess().getTypeRefAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Parameter__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Parameter__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Parameter__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getParameterAccess().getAnnotationsAssignment_3()); }
	(rule__Parameter__AnnotationsAssignment_3)*
	{ after(grammarAccess.getParameterAccess().getAnnotationsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RequiredPort__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group__0__Impl
	rule__RequiredPort__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getOptionalAssignment_0()); }
	(rule__RequiredPort__OptionalAssignment_0)?
	{ after(grammarAccess.getRequiredPortAccess().getOptionalAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group__1__Impl
	rule__RequiredPort__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getRequiredKeyword_1()); }
	'required'
	{ after(grammarAccess.getRequiredPortAccess().getRequiredKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group__2__Impl
	rule__RequiredPort__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getPortKeyword_2()); }
	'port'
	{ after(grammarAccess.getRequiredPortAccess().getPortKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group__3__Impl
	rule__RequiredPort__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getNameAssignment_3()); }
	(rule__RequiredPort__NameAssignment_3)
	{ after(grammarAccess.getRequiredPortAccess().getNameAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group__4__Impl
	rule__RequiredPort__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getAnnotationsAssignment_4()); }
	(rule__RequiredPort__AnnotationsAssignment_4)*
	{ after(grammarAccess.getRequiredPortAccess().getAnnotationsAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group__5__Impl
	rule__RequiredPort__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getLeftCurlyBracketKeyword_5()); }
	'{'
	{ after(grammarAccess.getRequiredPortAccess().getLeftCurlyBracketKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group__6__Impl
	rule__RequiredPort__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getAlternatives_6()); }
	(rule__RequiredPort__Alternatives_6)*
	{ after(grammarAccess.getRequiredPortAccess().getAlternatives_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group__7__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getRightCurlyBracketKeyword_7()); }
	'}'
	{ after(grammarAccess.getRequiredPortAccess().getRightCurlyBracketKeyword_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RequiredPort__Group_6_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group_6_0__0__Impl
	rule__RequiredPort__Group_6_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getSendsKeyword_6_0_0()); }
	'sends'
	{ after(grammarAccess.getRequiredPortAccess().getSendsKeyword_6_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group_6_0__1__Impl
	rule__RequiredPort__Group_6_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getSendsAssignment_6_0_1()); }
	(rule__RequiredPort__SendsAssignment_6_0_1)
	{ after(grammarAccess.getRequiredPortAccess().getSendsAssignment_6_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group_6_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getGroup_6_0_2()); }
	(rule__RequiredPort__Group_6_0_2__0)*
	{ after(grammarAccess.getRequiredPortAccess().getGroup_6_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RequiredPort__Group_6_0_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group_6_0_2__0__Impl
	rule__RequiredPort__Group_6_0_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_0_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getCommaKeyword_6_0_2_0()); }
	','
	{ after(grammarAccess.getRequiredPortAccess().getCommaKeyword_6_0_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_0_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group_6_0_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_0_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getSendsAssignment_6_0_2_1()); }
	(rule__RequiredPort__SendsAssignment_6_0_2_1)
	{ after(grammarAccess.getRequiredPortAccess().getSendsAssignment_6_0_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RequiredPort__Group_6_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group_6_1__0__Impl
	rule__RequiredPort__Group_6_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getReceivesKeyword_6_1_0()); }
	'receives'
	{ after(grammarAccess.getRequiredPortAccess().getReceivesKeyword_6_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group_6_1__1__Impl
	rule__RequiredPort__Group_6_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getReceivesAssignment_6_1_1()); }
	(rule__RequiredPort__ReceivesAssignment_6_1_1)
	{ after(grammarAccess.getRequiredPortAccess().getReceivesAssignment_6_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group_6_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getGroup_6_1_2()); }
	(rule__RequiredPort__Group_6_1_2__0)*
	{ after(grammarAccess.getRequiredPortAccess().getGroup_6_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RequiredPort__Group_6_1_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group_6_1_2__0__Impl
	rule__RequiredPort__Group_6_1_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_1_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getCommaKeyword_6_1_2_0()); }
	','
	{ after(grammarAccess.getRequiredPortAccess().getCommaKeyword_6_1_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_1_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RequiredPort__Group_6_1_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__Group_6_1_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRequiredPortAccess().getReceivesAssignment_6_1_2_1()); }
	(rule__RequiredPort__ReceivesAssignment_6_1_2_1)
	{ after(grammarAccess.getRequiredPortAccess().getReceivesAssignment_6_1_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProvidedPort__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group__0__Impl
	rule__ProvidedPort__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getProvidedKeyword_0()); }
	'provided'
	{ after(grammarAccess.getProvidedPortAccess().getProvidedKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group__1__Impl
	rule__ProvidedPort__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getPortKeyword_1()); }
	'port'
	{ after(grammarAccess.getProvidedPortAccess().getPortKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group__2__Impl
	rule__ProvidedPort__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getNameAssignment_2()); }
	(rule__ProvidedPort__NameAssignment_2)
	{ after(grammarAccess.getProvidedPortAccess().getNameAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group__3__Impl
	rule__ProvidedPort__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getAnnotationsAssignment_3()); }
	(rule__ProvidedPort__AnnotationsAssignment_3)*
	{ after(grammarAccess.getProvidedPortAccess().getAnnotationsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group__4__Impl
	rule__ProvidedPort__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getLeftCurlyBracketKeyword_4()); }
	'{'
	{ after(grammarAccess.getProvidedPortAccess().getLeftCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group__5__Impl
	rule__ProvidedPort__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getAlternatives_5()); }
	(rule__ProvidedPort__Alternatives_5)*
	{ after(grammarAccess.getProvidedPortAccess().getAlternatives_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getRightCurlyBracketKeyword_6()); }
	'}'
	{ after(grammarAccess.getProvidedPortAccess().getRightCurlyBracketKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProvidedPort__Group_5_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group_5_0__0__Impl
	rule__ProvidedPort__Group_5_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getSendsKeyword_5_0_0()); }
	'sends'
	{ after(grammarAccess.getProvidedPortAccess().getSendsKeyword_5_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group_5_0__1__Impl
	rule__ProvidedPort__Group_5_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getSendsAssignment_5_0_1()); }
	(rule__ProvidedPort__SendsAssignment_5_0_1)
	{ after(grammarAccess.getProvidedPortAccess().getSendsAssignment_5_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group_5_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getGroup_5_0_2()); }
	(rule__ProvidedPort__Group_5_0_2__0)*
	{ after(grammarAccess.getProvidedPortAccess().getGroup_5_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProvidedPort__Group_5_0_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group_5_0_2__0__Impl
	rule__ProvidedPort__Group_5_0_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_0_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getCommaKeyword_5_0_2_0()); }
	','
	{ after(grammarAccess.getProvidedPortAccess().getCommaKeyword_5_0_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_0_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group_5_0_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_0_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getSendsAssignment_5_0_2_1()); }
	(rule__ProvidedPort__SendsAssignment_5_0_2_1)
	{ after(grammarAccess.getProvidedPortAccess().getSendsAssignment_5_0_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProvidedPort__Group_5_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group_5_1__0__Impl
	rule__ProvidedPort__Group_5_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getReceivesKeyword_5_1_0()); }
	'receives'
	{ after(grammarAccess.getProvidedPortAccess().getReceivesKeyword_5_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group_5_1__1__Impl
	rule__ProvidedPort__Group_5_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getReceivesAssignment_5_1_1()); }
	(rule__ProvidedPort__ReceivesAssignment_5_1_1)
	{ after(grammarAccess.getProvidedPortAccess().getReceivesAssignment_5_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group_5_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getGroup_5_1_2()); }
	(rule__ProvidedPort__Group_5_1_2__0)*
	{ after(grammarAccess.getProvidedPortAccess().getGroup_5_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ProvidedPort__Group_5_1_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group_5_1_2__0__Impl
	rule__ProvidedPort__Group_5_1_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_1_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getCommaKeyword_5_1_2_0()); }
	','
	{ after(grammarAccess.getProvidedPortAccess().getCommaKeyword_5_1_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_1_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ProvidedPort__Group_5_1_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__Group_5_1_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getProvidedPortAccess().getReceivesAssignment_5_1_2_1()); }
	(rule__ProvidedPort__ReceivesAssignment_5_1_2_1)
	{ after(grammarAccess.getProvidedPortAccess().getReceivesAssignment_5_1_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__InternalPort__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group__0__Impl
	rule__InternalPort__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getInternalKeyword_0()); }
	'internal'
	{ after(grammarAccess.getInternalPortAccess().getInternalKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group__1__Impl
	rule__InternalPort__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getPortKeyword_1()); }
	'port'
	{ after(grammarAccess.getInternalPortAccess().getPortKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group__2__Impl
	rule__InternalPort__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getNameAssignment_2()); }
	(rule__InternalPort__NameAssignment_2)
	{ after(grammarAccess.getInternalPortAccess().getNameAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group__3__Impl
	rule__InternalPort__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getAnnotationsAssignment_3()); }
	(rule__InternalPort__AnnotationsAssignment_3)*
	{ after(grammarAccess.getInternalPortAccess().getAnnotationsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group__4__Impl
	rule__InternalPort__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getLeftCurlyBracketKeyword_4()); }
	'{'
	{ after(grammarAccess.getInternalPortAccess().getLeftCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group__5__Impl
	rule__InternalPort__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getAlternatives_5()); }
	(rule__InternalPort__Alternatives_5)*
	{ after(grammarAccess.getInternalPortAccess().getAlternatives_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getRightCurlyBracketKeyword_6()); }
	'}'
	{ after(grammarAccess.getInternalPortAccess().getRightCurlyBracketKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__InternalPort__Group_5_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group_5_0__0__Impl
	rule__InternalPort__Group_5_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getSendsKeyword_5_0_0()); }
	'sends'
	{ after(grammarAccess.getInternalPortAccess().getSendsKeyword_5_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group_5_0__1__Impl
	rule__InternalPort__Group_5_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getSendsAssignment_5_0_1()); }
	(rule__InternalPort__SendsAssignment_5_0_1)
	{ after(grammarAccess.getInternalPortAccess().getSendsAssignment_5_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group_5_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getGroup_5_0_2()); }
	(rule__InternalPort__Group_5_0_2__0)*
	{ after(grammarAccess.getInternalPortAccess().getGroup_5_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__InternalPort__Group_5_0_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group_5_0_2__0__Impl
	rule__InternalPort__Group_5_0_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_0_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getCommaKeyword_5_0_2_0()); }
	','
	{ after(grammarAccess.getInternalPortAccess().getCommaKeyword_5_0_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_0_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group_5_0_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_0_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getSendsAssignment_5_0_2_1()); }
	(rule__InternalPort__SendsAssignment_5_0_2_1)
	{ after(grammarAccess.getInternalPortAccess().getSendsAssignment_5_0_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__InternalPort__Group_5_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group_5_1__0__Impl
	rule__InternalPort__Group_5_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getReceivesKeyword_5_1_0()); }
	'receives'
	{ after(grammarAccess.getInternalPortAccess().getReceivesKeyword_5_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group_5_1__1__Impl
	rule__InternalPort__Group_5_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getReceivesAssignment_5_1_1()); }
	(rule__InternalPort__ReceivesAssignment_5_1_1)
	{ after(grammarAccess.getInternalPortAccess().getReceivesAssignment_5_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group_5_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getGroup_5_1_2()); }
	(rule__InternalPort__Group_5_1_2__0)*
	{ after(grammarAccess.getInternalPortAccess().getGroup_5_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__InternalPort__Group_5_1_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group_5_1_2__0__Impl
	rule__InternalPort__Group_5_1_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_1_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getCommaKeyword_5_1_2_0()); }
	','
	{ after(grammarAccess.getInternalPortAccess().getCommaKeyword_5_1_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_1_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalPort__Group_5_1_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__Group_5_1_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalPortAccess().getReceivesAssignment_5_1_2_1()); }
	(rule__InternalPort__ReceivesAssignment_5_1_2_1)
	{ after(grammarAccess.getInternalPortAccess().getReceivesAssignment_5_1_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__State__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3__0__Impl
	rule__State__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getStateKeyword_3_0()); }
	'state'
	{ after(grammarAccess.getStateAccess().getStateKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3__1__Impl
	rule__State__Group_3__2
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getNameAssignment_3_1()); }
	(rule__State__NameAssignment_3_1)
	{ after(grammarAccess.getStateAccess().getNameAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3__2__Impl
	rule__State__Group_3__3
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getAnnotationsAssignment_3_2()); }
	(rule__State__AnnotationsAssignment_3_2)*
	{ after(grammarAccess.getStateAccess().getAnnotationsAssignment_3_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3__3__Impl
	rule__State__Group_3__4
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_3_3()); }
	'{'
	{ after(grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_3_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3__4__Impl
	rule__State__Group_3__5
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getPropertiesAssignment_3_4()); }
	(rule__State__PropertiesAssignment_3_4)*
	{ after(grammarAccess.getStateAccess().getPropertiesAssignment_3_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3__5__Impl
	rule__State__Group_3__6
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getGroup_3_5()); }
	(rule__State__Group_3_5__0)?
	{ after(grammarAccess.getStateAccess().getGroup_3_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3__6__Impl
	rule__State__Group_3__7
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getGroup_3_6()); }
	(rule__State__Group_3_6__0)?
	{ after(grammarAccess.getStateAccess().getGroup_3_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3__7__Impl
	rule__State__Group_3__8
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getAlternatives_3_7()); }
	(rule__State__Alternatives_3_7)*
	{ after(grammarAccess.getStateAccess().getAlternatives_3_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3__8__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getRightCurlyBracketKeyword_3_8()); }
	'}'
	{ after(grammarAccess.getStateAccess().getRightCurlyBracketKeyword_3_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__State__Group_3_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3_5__0__Impl
	rule__State__Group_3_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getOnKeyword_3_5_0()); }
	'on'
	{ after(grammarAccess.getStateAccess().getOnKeyword_3_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3_5__1__Impl
	rule__State__Group_3_5__2
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getEntryKeyword_3_5_1()); }
	'entry'
	{ after(grammarAccess.getStateAccess().getEntryKeyword_3_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3_5__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3_5__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3_5__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getEntryAssignment_3_5_2()); }
	(rule__State__EntryAssignment_3_5_2)
	{ after(grammarAccess.getStateAccess().getEntryAssignment_3_5_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__State__Group_3_6__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3_6__0__Impl
	rule__State__Group_3_6__1
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3_6__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getOnKeyword_3_6_0()); }
	'on'
	{ after(grammarAccess.getStateAccess().getOnKeyword_3_6_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3_6__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3_6__1__Impl
	rule__State__Group_3_6__2
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3_6__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getExitKeyword_3_6_1()); }
	'exit'
	{ after(grammarAccess.getStateAccess().getExitKeyword_3_6_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3_6__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__State__Group_3_6__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__State__Group_3_6__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateAccess().getExitAssignment_3_6_2()); }
	(rule__State__ExitAssignment_3_6_2)
	{ after(grammarAccess.getStateAccess().getExitAssignment_3_6_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Transition__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__0__Impl
	rule__Transition__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getTransitionKeyword_0()); }
	'transition'
	{ after(grammarAccess.getTransitionAccess().getTransitionKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__1__Impl
	rule__Transition__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getNameAssignment_1()); }
	(rule__Transition__NameAssignment_1)?
	{ after(grammarAccess.getTransitionAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__2__Impl
	rule__Transition__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getHyphenMinusGreaterThanSignKeyword_2()); }
	'->'
	{ after(grammarAccess.getTransitionAccess().getHyphenMinusGreaterThanSignKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__3__Impl
	rule__Transition__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getTargetAssignment_3()); }
	(rule__Transition__TargetAssignment_3)
	{ after(grammarAccess.getTransitionAccess().getTargetAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__4__Impl
	rule__Transition__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getAnnotationsAssignment_4()); }
	(rule__Transition__AnnotationsAssignment_4)*
	{ after(grammarAccess.getTransitionAccess().getAnnotationsAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__5__Impl
	rule__Transition__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getGroup_5()); }
	(rule__Transition__Group_5__0)*
	{ after(grammarAccess.getTransitionAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__6__Impl
	rule__Transition__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getGroup_6()); }
	(rule__Transition__Group_6__0)?
	{ after(grammarAccess.getTransitionAccess().getGroup_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group__7__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getGroup_7()); }
	(rule__Transition__Group_7__0)?
	{ after(grammarAccess.getTransitionAccess().getGroup_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Transition__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group_5__0__Impl
	rule__Transition__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getEventKeyword_5_0()); }
	'event'
	{ after(grammarAccess.getTransitionAccess().getEventKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group_5__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getEventAssignment_5_1()); }
	(rule__Transition__EventAssignment_5_1)
	{ after(grammarAccess.getTransitionAccess().getEventAssignment_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Transition__Group_6__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group_6__0__Impl
	rule__Transition__Group_6__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_6__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getGuardKeyword_6_0()); }
	'guard'
	{ after(grammarAccess.getTransitionAccess().getGuardKeyword_6_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_6__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group_6__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_6__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getGuardAssignment_6_1()); }
	(rule__Transition__GuardAssignment_6_1)
	{ after(grammarAccess.getTransitionAccess().getGuardAssignment_6_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Transition__Group_7__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group_7__0__Impl
	rule__Transition__Group_7__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_7__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getActionKeyword_7_0()); }
	'action'
	{ after(grammarAccess.getTransitionAccess().getActionKeyword_7_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_7__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Transition__Group_7__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__Group_7__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTransitionAccess().getActionAssignment_7_1()); }
	(rule__Transition__ActionAssignment_7_1)
	{ after(grammarAccess.getTransitionAccess().getActionAssignment_7_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__InternalTransition__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group__0__Impl
	rule__InternalTransition__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getInternalTransitionAction_0()); }
	()
	{ after(grammarAccess.getInternalTransitionAccess().getInternalTransitionAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group__1__Impl
	rule__InternalTransition__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getInternalKeyword_1()); }
	'internal'
	{ after(grammarAccess.getInternalTransitionAccess().getInternalKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group__2__Impl
	rule__InternalTransition__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getNameAssignment_2()); }
	(rule__InternalTransition__NameAssignment_2)?
	{ after(grammarAccess.getInternalTransitionAccess().getNameAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group__3__Impl
	rule__InternalTransition__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getAnnotationsAssignment_3()); }
	(rule__InternalTransition__AnnotationsAssignment_3)*
	{ after(grammarAccess.getInternalTransitionAccess().getAnnotationsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group__4__Impl
	rule__InternalTransition__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getGroup_4()); }
	(rule__InternalTransition__Group_4__0)*
	{ after(grammarAccess.getInternalTransitionAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group__5__Impl
	rule__InternalTransition__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getGroup_5()); }
	(rule__InternalTransition__Group_5__0)?
	{ after(grammarAccess.getInternalTransitionAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getGroup_6()); }
	(rule__InternalTransition__Group_6__0)?
	{ after(grammarAccess.getInternalTransitionAccess().getGroup_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__InternalTransition__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group_4__0__Impl
	rule__InternalTransition__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getEventKeyword_4_0()); }
	'event'
	{ after(grammarAccess.getInternalTransitionAccess().getEventKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group_4__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getEventAssignment_4_1()); }
	(rule__InternalTransition__EventAssignment_4_1)
	{ after(grammarAccess.getInternalTransitionAccess().getEventAssignment_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__InternalTransition__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group_5__0__Impl
	rule__InternalTransition__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getGuardKeyword_5_0()); }
	'guard'
	{ after(grammarAccess.getInternalTransitionAccess().getGuardKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group_5__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getGuardAssignment_5_1()); }
	(rule__InternalTransition__GuardAssignment_5_1)
	{ after(grammarAccess.getInternalTransitionAccess().getGuardAssignment_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__InternalTransition__Group_6__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group_6__0__Impl
	rule__InternalTransition__Group_6__1
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group_6__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getActionKeyword_6_0()); }
	'action'
	{ after(grammarAccess.getInternalTransitionAccess().getActionKeyword_6_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group_6__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__InternalTransition__Group_6__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__Group_6__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInternalTransitionAccess().getActionAssignment_6_1()); }
	(rule__InternalTransition__ActionAssignment_6_1)
	{ after(grammarAccess.getInternalTransitionAccess().getActionAssignment_6_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CompositeState__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__0__Impl
	rule__CompositeState__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getCompositeKeyword_0()); }
	'composite'
	{ after(grammarAccess.getCompositeStateAccess().getCompositeKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__1__Impl
	rule__CompositeState__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getStateKeyword_1()); }
	'state'
	{ after(grammarAccess.getCompositeStateAccess().getStateKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__2__Impl
	rule__CompositeState__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getNameAssignment_2()); }
	(rule__CompositeState__NameAssignment_2)
	{ after(grammarAccess.getCompositeStateAccess().getNameAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__3__Impl
	rule__CompositeState__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getInitKeyword_3()); }
	'init'
	{ after(grammarAccess.getCompositeStateAccess().getInitKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__4__Impl
	rule__CompositeState__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getInitialAssignment_4()); }
	(rule__CompositeState__InitialAssignment_4)
	{ after(grammarAccess.getCompositeStateAccess().getInitialAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__5__Impl
	rule__CompositeState__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getGroup_5()); }
	(rule__CompositeState__Group_5__0)?
	{ after(grammarAccess.getCompositeStateAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__6__Impl
	rule__CompositeState__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getAnnotationsAssignment_6()); }
	(rule__CompositeState__AnnotationsAssignment_6)*
	{ after(grammarAccess.getCompositeStateAccess().getAnnotationsAssignment_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__7__Impl
	rule__CompositeState__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getLeftCurlyBracketKeyword_7()); }
	'{'
	{ after(grammarAccess.getCompositeStateAccess().getLeftCurlyBracketKeyword_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__8__Impl
	rule__CompositeState__Group__9
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getPropertiesAssignment_8()); }
	(rule__CompositeState__PropertiesAssignment_8)*
	{ after(grammarAccess.getCompositeStateAccess().getPropertiesAssignment_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__9__Impl
	rule__CompositeState__Group__10
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__9__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getGroup_9()); }
	(rule__CompositeState__Group_9__0)?
	{ after(grammarAccess.getCompositeStateAccess().getGroup_9()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__10
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__10__Impl
	rule__CompositeState__Group__11
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__10__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getGroup_10()); }
	(rule__CompositeState__Group_10__0)?
	{ after(grammarAccess.getCompositeStateAccess().getGroup_10()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__11
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__11__Impl
	rule__CompositeState__Group__12
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__11__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getAlternatives_11()); }
	(rule__CompositeState__Alternatives_11)*
	{ after(grammarAccess.getCompositeStateAccess().getAlternatives_11()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__12
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__12__Impl
	rule__CompositeState__Group__13
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__12__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getAlternatives_12()); }
	(rule__CompositeState__Alternatives_12)*
	{ after(grammarAccess.getCompositeStateAccess().getAlternatives_12()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__13
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group__13__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group__13__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getRightCurlyBracketKeyword_13()); }
	'}'
	{ after(grammarAccess.getCompositeStateAccess().getRightCurlyBracketKeyword_13()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CompositeState__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group_5__0__Impl
	rule__CompositeState__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getKeepsKeyword_5_0()); }
	'keeps'
	{ after(grammarAccess.getCompositeStateAccess().getKeepsKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group_5__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getHistoryAssignment_5_1()); }
	(rule__CompositeState__HistoryAssignment_5_1)
	{ after(grammarAccess.getCompositeStateAccess().getHistoryAssignment_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CompositeState__Group_9__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group_9__0__Impl
	rule__CompositeState__Group_9__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_9__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getOnKeyword_9_0()); }
	'on'
	{ after(grammarAccess.getCompositeStateAccess().getOnKeyword_9_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_9__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group_9__1__Impl
	rule__CompositeState__Group_9__2
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_9__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getEntryKeyword_9_1()); }
	'entry'
	{ after(grammarAccess.getCompositeStateAccess().getEntryKeyword_9_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_9__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group_9__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_9__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getEntryAssignment_9_2()); }
	(rule__CompositeState__EntryAssignment_9_2)
	{ after(grammarAccess.getCompositeStateAccess().getEntryAssignment_9_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CompositeState__Group_10__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group_10__0__Impl
	rule__CompositeState__Group_10__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_10__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getOnKeyword_10_0()); }
	'on'
	{ after(grammarAccess.getCompositeStateAccess().getOnKeyword_10_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_10__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group_10__1__Impl
	rule__CompositeState__Group_10__2
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_10__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getExitKeyword_10_1()); }
	'exit'
	{ after(grammarAccess.getCompositeStateAccess().getExitKeyword_10_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_10__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CompositeState__Group_10__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__Group_10__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCompositeStateAccess().getExitAssignment_10_2()); }
	(rule__CompositeState__ExitAssignment_10_2)
	{ after(grammarAccess.getCompositeStateAccess().getExitAssignment_10_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__StateMachine__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__0__Impl
	rule__StateMachine__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getStatechartKeyword_0()); }
	'statechart'
	{ after(grammarAccess.getStateMachineAccess().getStatechartKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__1__Impl
	rule__StateMachine__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getNameAssignment_1()); }
	(rule__StateMachine__NameAssignment_1)?
	{ after(grammarAccess.getStateMachineAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__2__Impl
	rule__StateMachine__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getInitKeyword_2()); }
	'init'
	{ after(grammarAccess.getStateMachineAccess().getInitKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__3__Impl
	rule__StateMachine__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getInitialAssignment_3()); }
	(rule__StateMachine__InitialAssignment_3)
	{ after(grammarAccess.getStateMachineAccess().getInitialAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__4__Impl
	rule__StateMachine__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getGroup_4()); }
	(rule__StateMachine__Group_4__0)?
	{ after(grammarAccess.getStateMachineAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__5__Impl
	rule__StateMachine__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getAnnotationsAssignment_5()); }
	(rule__StateMachine__AnnotationsAssignment_5)*
	{ after(grammarAccess.getStateMachineAccess().getAnnotationsAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__6__Impl
	rule__StateMachine__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getLeftCurlyBracketKeyword_6()); }
	'{'
	{ after(grammarAccess.getStateMachineAccess().getLeftCurlyBracketKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__7__Impl
	rule__StateMachine__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getPropertiesAssignment_7()); }
	(rule__StateMachine__PropertiesAssignment_7)*
	{ after(grammarAccess.getStateMachineAccess().getPropertiesAssignment_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__8__Impl
	rule__StateMachine__Group__9
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getGroup_8()); }
	(rule__StateMachine__Group_8__0)?
	{ after(grammarAccess.getStateMachineAccess().getGroup_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__9__Impl
	rule__StateMachine__Group__10
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__9__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getGroup_9()); }
	(rule__StateMachine__Group_9__0)?
	{ after(grammarAccess.getStateMachineAccess().getGroup_9()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__10
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__10__Impl
	rule__StateMachine__Group__11
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__10__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getAlternatives_10()); }
	(rule__StateMachine__Alternatives_10)*
	{ after(grammarAccess.getStateMachineAccess().getAlternatives_10()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__11
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__11__Impl
	rule__StateMachine__Group__12
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__11__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getAlternatives_11()); }
	(rule__StateMachine__Alternatives_11)*
	{ after(grammarAccess.getStateMachineAccess().getAlternatives_11()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__12
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group__12__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group__12__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getRightCurlyBracketKeyword_12()); }
	'}'
	{ after(grammarAccess.getStateMachineAccess().getRightCurlyBracketKeyword_12()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__StateMachine__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group_4__0__Impl
	rule__StateMachine__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getKeepsKeyword_4_0()); }
	'keeps'
	{ after(grammarAccess.getStateMachineAccess().getKeepsKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group_4__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getHistoryAssignment_4_1()); }
	(rule__StateMachine__HistoryAssignment_4_1)
	{ after(grammarAccess.getStateMachineAccess().getHistoryAssignment_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__StateMachine__Group_8__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group_8__0__Impl
	rule__StateMachine__Group_8__1
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_8__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getOnKeyword_8_0()); }
	'on'
	{ after(grammarAccess.getStateMachineAccess().getOnKeyword_8_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_8__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group_8__1__Impl
	rule__StateMachine__Group_8__2
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_8__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getEntryKeyword_8_1()); }
	'entry'
	{ after(grammarAccess.getStateMachineAccess().getEntryKeyword_8_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_8__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group_8__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_8__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getEntryAssignment_8_2()); }
	(rule__StateMachine__EntryAssignment_8_2)
	{ after(grammarAccess.getStateMachineAccess().getEntryAssignment_8_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__StateMachine__Group_9__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group_9__0__Impl
	rule__StateMachine__Group_9__1
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_9__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getOnKeyword_9_0()); }
	'on'
	{ after(grammarAccess.getStateMachineAccess().getOnKeyword_9_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_9__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group_9__1__Impl
	rule__StateMachine__Group_9__2
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_9__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getExitKeyword_9_1()); }
	'exit'
	{ after(grammarAccess.getStateMachineAccess().getExitKeyword_9_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_9__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateMachine__Group_9__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__Group_9__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateMachineAccess().getExitAssignment_9_2()); }
	(rule__StateMachine__ExitAssignment_9_2)
	{ after(grammarAccess.getStateMachineAccess().getExitAssignment_9_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Session__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Session__Group__0__Impl
	rule__Session__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSessionAccess().getSessionKeyword_0()); }
	'session'
	{ after(grammarAccess.getSessionAccess().getSessionKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Session__Group__1__Impl
	rule__Session__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSessionAccess().getNameAssignment_1()); }
	(rule__Session__NameAssignment_1)
	{ after(grammarAccess.getSessionAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Session__Group__2__Impl
	rule__Session__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSessionAccess().getGroup_2()); }
	(rule__Session__Group_2__0)?
	{ after(grammarAccess.getSessionAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Session__Group__3__Impl
	rule__Session__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSessionAccess().getInitKeyword_3()); }
	'init'
	{ after(grammarAccess.getSessionAccess().getInitKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Session__Group__4__Impl
	rule__Session__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSessionAccess().getInitialAssignment_4()); }
	(rule__Session__InitialAssignment_4)
	{ after(grammarAccess.getSessionAccess().getInitialAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Session__Group__5__Impl
	rule__Session__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSessionAccess().getAnnotationsAssignment_5()); }
	(rule__Session__AnnotationsAssignment_5)*
	{ after(grammarAccess.getSessionAccess().getAnnotationsAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Session__Group__6__Impl
	rule__Session__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSessionAccess().getLeftCurlyBracketKeyword_6()); }
	'{'
	{ after(grammarAccess.getSessionAccess().getLeftCurlyBracketKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Session__Group__7__Impl
	rule__Session__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSessionAccess().getSubstateAssignment_7()); }
	(rule__Session__SubstateAssignment_7)*
	{ after(grammarAccess.getSessionAccess().getSubstateAssignment_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Session__Group__8__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSessionAccess().getRightCurlyBracketKeyword_8()); }
	'}'
	{ after(grammarAccess.getSessionAccess().getRightCurlyBracketKeyword_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Session__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Session__Group_2__0__Impl
	rule__Session__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSessionAccess().getLessThanSignKeyword_2_0()); }
	'<'
	{ after(grammarAccess.getSessionAccess().getLessThanSignKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Session__Group_2__1__Impl
	rule__Session__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSessionAccess().getMaxInstancesAssignment_2_1()); }
	(rule__Session__MaxInstancesAssignment_2_1)
	{ after(grammarAccess.getSessionAccess().getMaxInstancesAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Session__Group_2__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSessionAccess().getGreaterThanSignKeyword_2_2()); }
	'>'
	{ after(grammarAccess.getSessionAccess().getGreaterThanSignKeyword_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Region__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Region__Group__0__Impl
	rule__Region__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegionAccess().getRegionKeyword_0()); }
	'region'
	{ after(grammarAccess.getRegionAccess().getRegionKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Region__Group__1__Impl
	rule__Region__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegionAccess().getNameAssignment_1()); }
	(rule__Region__NameAssignment_1)?
	{ after(grammarAccess.getRegionAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Region__Group__2__Impl
	rule__Region__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegionAccess().getInitKeyword_2()); }
	'init'
	{ after(grammarAccess.getRegionAccess().getInitKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Region__Group__3__Impl
	rule__Region__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegionAccess().getInitialAssignment_3()); }
	(rule__Region__InitialAssignment_3)
	{ after(grammarAccess.getRegionAccess().getInitialAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Region__Group__4__Impl
	rule__Region__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegionAccess().getGroup_4()); }
	(rule__Region__Group_4__0)?
	{ after(grammarAccess.getRegionAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Region__Group__5__Impl
	rule__Region__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegionAccess().getAnnotationsAssignment_5()); }
	(rule__Region__AnnotationsAssignment_5)*
	{ after(grammarAccess.getRegionAccess().getAnnotationsAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Region__Group__6__Impl
	rule__Region__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegionAccess().getLeftCurlyBracketKeyword_6()); }
	'{'
	{ after(grammarAccess.getRegionAccess().getLeftCurlyBracketKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Region__Group__7__Impl
	rule__Region__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegionAccess().getSubstateAssignment_7()); }
	(rule__Region__SubstateAssignment_7)*
	{ after(grammarAccess.getRegionAccess().getSubstateAssignment_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Region__Group__8__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegionAccess().getRightCurlyBracketKeyword_8()); }
	'}'
	{ after(grammarAccess.getRegionAccess().getRightCurlyBracketKeyword_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Region__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Region__Group_4__0__Impl
	rule__Region__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegionAccess().getKeepsKeyword_4_0()); }
	'keeps'
	{ after(grammarAccess.getRegionAccess().getKeepsKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Region__Group_4__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegionAccess().getHistoryAssignment_4_1()); }
	(rule__Region__HistoryAssignment_4_1)
	{ after(grammarAccess.getRegionAccess().getHistoryAssignment_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FinalState__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FinalState__Group__0__Impl
	rule__FinalState__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFinalStateAccess().getFinalKeyword_0()); }
	'final'
	{ after(grammarAccess.getFinalStateAccess().getFinalKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FinalState__Group__1__Impl
	rule__FinalState__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFinalStateAccess().getStateKeyword_1()); }
	'state'
	{ after(grammarAccess.getFinalStateAccess().getStateKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FinalState__Group__2__Impl
	rule__FinalState__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFinalStateAccess().getNameAssignment_2()); }
	(rule__FinalState__NameAssignment_2)
	{ after(grammarAccess.getFinalStateAccess().getNameAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FinalState__Group__3__Impl
	rule__FinalState__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFinalStateAccess().getAnnotationsAssignment_3()); }
	(rule__FinalState__AnnotationsAssignment_3)*
	{ after(grammarAccess.getFinalStateAccess().getAnnotationsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FinalState__Group__4__Impl
	rule__FinalState__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFinalStateAccess().getLeftCurlyBracketKeyword_4()); }
	'{'
	{ after(grammarAccess.getFinalStateAccess().getLeftCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FinalState__Group__5__Impl
	rule__FinalState__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFinalStateAccess().getGroup_5()); }
	(rule__FinalState__Group_5__0)?
	{ after(grammarAccess.getFinalStateAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FinalState__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFinalStateAccess().getRightCurlyBracketKeyword_6()); }
	'}'
	{ after(grammarAccess.getFinalStateAccess().getRightCurlyBracketKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FinalState__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FinalState__Group_5__0__Impl
	rule__FinalState__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFinalStateAccess().getOnKeyword_5_0()); }
	'on'
	{ after(grammarAccess.getFinalStateAccess().getOnKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FinalState__Group_5__1__Impl
	rule__FinalState__Group_5__2
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFinalStateAccess().getEntryKeyword_5_1()); }
	'entry'
	{ after(grammarAccess.getFinalStateAccess().getEntryKeyword_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group_5__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FinalState__Group_5__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__Group_5__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFinalStateAccess().getEntryAssignment_5_2()); }
	(rule__FinalState__EntryAssignment_5_2)
	{ after(grammarAccess.getFinalStateAccess().getEntryAssignment_5_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__StateContainer__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateContainer__Group_3__0__Impl
	rule__StateContainer__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateContainerAccess().getKeepsKeyword_3_0()); }
	'keeps'
	{ after(grammarAccess.getStateContainerAccess().getKeepsKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateContainer__Group_3__1__Impl
	rule__StateContainer__Group_3__2
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateContainerAccess().getInitialAssignment_3_1()); }
	(rule__StateContainer__InitialAssignment_3_1)
	{ after(grammarAccess.getStateContainerAccess().getInitialAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateContainer__Group_3__2__Impl
	rule__StateContainer__Group_3__3
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateContainerAccess().getGroup_3_2()); }
	(rule__StateContainer__Group_3_2__0)?
	{ after(grammarAccess.getStateContainerAccess().getGroup_3_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateContainer__Group_3__3__Impl
	rule__StateContainer__Group_3__4
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateContainerAccess().getLeftCurlyBracketKeyword_3_3()); }
	'{'
	{ after(grammarAccess.getStateContainerAccess().getLeftCurlyBracketKeyword_3_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateContainer__Group_3__4__Impl
	rule__StateContainer__Group_3__5
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateContainerAccess().getSubstateAssignment_3_4()); }
	(rule__StateContainer__SubstateAssignment_3_4)*
	{ after(grammarAccess.getStateContainerAccess().getSubstateAssignment_3_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateContainer__Group_3__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateContainerAccess().getRightCurlyBracketKeyword_3_5()); }
	'}'
	{ after(grammarAccess.getStateContainerAccess().getRightCurlyBracketKeyword_3_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__StateContainer__Group_3_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateContainer__Group_3_2__0__Impl
	rule__StateContainer__Group_3_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateContainerAccess().getKeepsKeyword_3_2_0()); }
	'keeps'
	{ after(grammarAccess.getStateContainerAccess().getKeepsKeyword_3_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StateContainer__Group_3_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__Group_3_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStateContainerAccess().getHistoryAssignment_3_2_1()); }
	(rule__StateContainer__HistoryAssignment_3_2_1)
	{ after(grammarAccess.getStateContainerAccess().getHistoryAssignment_3_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ReceiveMessage__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ReceiveMessage__Group__0__Impl
	rule__ReceiveMessage__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReceiveMessageAccess().getGroup_0()); }
	(rule__ReceiveMessage__Group_0__0)?
	{ after(grammarAccess.getReceiveMessageAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ReceiveMessage__Group__1__Impl
	rule__ReceiveMessage__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReceiveMessageAccess().getPortAssignment_1()); }
	(rule__ReceiveMessage__PortAssignment_1)
	{ after(grammarAccess.getReceiveMessageAccess().getPortAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ReceiveMessage__Group__2__Impl
	rule__ReceiveMessage__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReceiveMessageAccess().getQuestionMarkKeyword_2()); }
	'?'
	{ after(grammarAccess.getReceiveMessageAccess().getQuestionMarkKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ReceiveMessage__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReceiveMessageAccess().getMessageAssignment_3()); }
	(rule__ReceiveMessage__MessageAssignment_3)
	{ after(grammarAccess.getReceiveMessageAccess().getMessageAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ReceiveMessage__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ReceiveMessage__Group_0__0__Impl
	rule__ReceiveMessage__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReceiveMessageAccess().getNameAssignment_0_0()); }
	(rule__ReceiveMessage__NameAssignment_0_0)
	{ after(grammarAccess.getReceiveMessageAccess().getNameAssignment_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ReceiveMessage__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReceiveMessageAccess().getColonKeyword_0_1()); }
	':'
	{ after(grammarAccess.getReceiveMessageAccess().getColonKeyword_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ActionBlock__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ActionBlock__Group__0__Impl
	rule__ActionBlock__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ActionBlock__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getActionBlockAccess().getActionBlockAction_0()); }
	()
	{ after(grammarAccess.getActionBlockAccess().getActionBlockAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ActionBlock__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ActionBlock__Group__1__Impl
	rule__ActionBlock__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ActionBlock__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getActionBlockAccess().getDoKeyword_1()); }
	'do'
	{ after(grammarAccess.getActionBlockAccess().getDoKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ActionBlock__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ActionBlock__Group__2__Impl
	rule__ActionBlock__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ActionBlock__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getActionBlockAccess().getActionsAssignment_2()); }
	(rule__ActionBlock__ActionsAssignment_2)*
	{ after(grammarAccess.getActionBlockAccess().getActionsAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ActionBlock__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ActionBlock__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ActionBlock__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getActionBlockAccess().getEndKeyword_3()); }
	'end'
	{ after(grammarAccess.getActionBlockAccess().getEndKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ExternStatement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternStatement__Group__0__Impl
	rule__ExternStatement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternStatement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternStatementAccess().getStatementAssignment_0()); }
	(rule__ExternStatement__StatementAssignment_0)
	{ after(grammarAccess.getExternStatementAccess().getStatementAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternStatement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternStatement__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternStatement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternStatementAccess().getGroup_1()); }
	(rule__ExternStatement__Group_1__0)*
	{ after(grammarAccess.getExternStatementAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ExternStatement__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternStatement__Group_1__0__Impl
	rule__ExternStatement__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternStatement__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternStatementAccess().getAmpersandKeyword_1_0()); }
	'&'
	{ after(grammarAccess.getExternStatementAccess().getAmpersandKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternStatement__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternStatement__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternStatement__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternStatementAccess().getSegmentsAssignment_1_1()); }
	(rule__ExternStatement__SegmentsAssignment_1_1)
	{ after(grammarAccess.getExternStatementAccess().getSegmentsAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__LocalVariable__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LocalVariable__Group__0__Impl
	rule__LocalVariable__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLocalVariableAccess().getReadonlyAssignment_0()); }
	(rule__LocalVariable__ReadonlyAssignment_0)?
	{ after(grammarAccess.getLocalVariableAccess().getReadonlyAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LocalVariable__Group__1__Impl
	rule__LocalVariable__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLocalVariableAccess().getVarKeyword_1()); }
	'var'
	{ after(grammarAccess.getLocalVariableAccess().getVarKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LocalVariable__Group__2__Impl
	rule__LocalVariable__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLocalVariableAccess().getNameAssignment_2()); }
	(rule__LocalVariable__NameAssignment_2)
	{ after(grammarAccess.getLocalVariableAccess().getNameAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LocalVariable__Group__3__Impl
	rule__LocalVariable__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLocalVariableAccess().getColonKeyword_3()); }
	':'
	{ after(grammarAccess.getLocalVariableAccess().getColonKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LocalVariable__Group__4__Impl
	rule__LocalVariable__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLocalVariableAccess().getTypeRefAssignment_4()); }
	(rule__LocalVariable__TypeRefAssignment_4)
	{ after(grammarAccess.getLocalVariableAccess().getTypeRefAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LocalVariable__Group__5__Impl
	rule__LocalVariable__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLocalVariableAccess().getGroup_5()); }
	(rule__LocalVariable__Group_5__0)?
	{ after(grammarAccess.getLocalVariableAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LocalVariable__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLocalVariableAccess().getAnnotationsAssignment_6()); }
	(rule__LocalVariable__AnnotationsAssignment_6)*
	{ after(grammarAccess.getLocalVariableAccess().getAnnotationsAssignment_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__LocalVariable__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LocalVariable__Group_5__0__Impl
	rule__LocalVariable__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLocalVariableAccess().getEqualsSignKeyword_5_0()); }
	'='
	{ after(grammarAccess.getLocalVariableAccess().getEqualsSignKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LocalVariable__Group_5__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLocalVariableAccess().getInitAssignment_5_1()); }
	(rule__LocalVariable__InitAssignment_5_1)
	{ after(grammarAccess.getLocalVariableAccess().getInitAssignment_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SendAction__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SendAction__Group__0__Impl
	rule__SendAction__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSendActionAccess().getPortAssignment_0()); }
	(rule__SendAction__PortAssignment_0)
	{ after(grammarAccess.getSendActionAccess().getPortAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SendAction__Group__1__Impl
	rule__SendAction__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSendActionAccess().getExclamationMarkKeyword_1()); }
	'!'
	{ after(grammarAccess.getSendActionAccess().getExclamationMarkKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SendAction__Group__2__Impl
	rule__SendAction__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSendActionAccess().getMessageAssignment_2()); }
	(rule__SendAction__MessageAssignment_2)
	{ after(grammarAccess.getSendActionAccess().getMessageAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SendAction__Group__3__Impl
	rule__SendAction__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSendActionAccess().getLeftParenthesisKeyword_3()); }
	'('
	{ after(grammarAccess.getSendActionAccess().getLeftParenthesisKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SendAction__Group__4__Impl
	rule__SendAction__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSendActionAccess().getGroup_4()); }
	(rule__SendAction__Group_4__0)?
	{ after(grammarAccess.getSendActionAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SendAction__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSendActionAccess().getRightParenthesisKeyword_5()); }
	')'
	{ after(grammarAccess.getSendActionAccess().getRightParenthesisKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SendAction__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SendAction__Group_4__0__Impl
	rule__SendAction__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSendActionAccess().getParametersAssignment_4_0()); }
	(rule__SendAction__ParametersAssignment_4_0)
	{ after(grammarAccess.getSendActionAccess().getParametersAssignment_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SendAction__Group_4__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSendActionAccess().getGroup_4_1()); }
	(rule__SendAction__Group_4_1__0)*
	{ after(grammarAccess.getSendActionAccess().getGroup_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SendAction__Group_4_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SendAction__Group_4_1__0__Impl
	rule__SendAction__Group_4_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group_4_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSendActionAccess().getCommaKeyword_4_1_0()); }
	','
	{ after(grammarAccess.getSendActionAccess().getCommaKeyword_4_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group_4_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SendAction__Group_4_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__Group_4_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSendActionAccess().getParametersAssignment_4_1_1()); }
	(rule__SendAction__ParametersAssignment_4_1_1)
	{ after(grammarAccess.getSendActionAccess().getParametersAssignment_4_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VariableAssignment__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VariableAssignment__Group__0__Impl
	rule__VariableAssignment__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVariableAssignmentAccess().getPropertyAssignment_0()); }
	(rule__VariableAssignment__PropertyAssignment_0)
	{ after(grammarAccess.getVariableAssignmentAccess().getPropertyAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VariableAssignment__Group__1__Impl
	rule__VariableAssignment__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVariableAssignmentAccess().getGroup_1()); }
	(rule__VariableAssignment__Group_1__0)*
	{ after(grammarAccess.getVariableAssignmentAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VariableAssignment__Group__2__Impl
	rule__VariableAssignment__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVariableAssignmentAccess().getEqualsSignKeyword_2()); }
	'='
	{ after(grammarAccess.getVariableAssignmentAccess().getEqualsSignKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VariableAssignment__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVariableAssignmentAccess().getExpressionAssignment_3()); }
	(rule__VariableAssignment__ExpressionAssignment_3)
	{ after(grammarAccess.getVariableAssignmentAccess().getExpressionAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VariableAssignment__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VariableAssignment__Group_1__0__Impl
	rule__VariableAssignment__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVariableAssignmentAccess().getLeftSquareBracketKeyword_1_0()); }
	'['
	{ after(grammarAccess.getVariableAssignmentAccess().getLeftSquareBracketKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VariableAssignment__Group_1__1__Impl
	rule__VariableAssignment__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVariableAssignmentAccess().getIndexAssignment_1_1()); }
	(rule__VariableAssignment__IndexAssignment_1_1)
	{ after(grammarAccess.getVariableAssignmentAccess().getIndexAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VariableAssignment__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVariableAssignmentAccess().getRightSquareBracketKeyword_1_2()); }
	']'
	{ after(grammarAccess.getVariableAssignmentAccess().getRightSquareBracketKeyword_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Increment__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Increment__Group__0__Impl
	rule__Increment__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Increment__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIncrementAccess().getVarAssignment_0()); }
	(rule__Increment__VarAssignment_0)
	{ after(grammarAccess.getIncrementAccess().getVarAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Increment__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Increment__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Increment__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIncrementAccess().getPlusSignPlusSignKeyword_1()); }
	'++'
	{ after(grammarAccess.getIncrementAccess().getPlusSignPlusSignKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Decrement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Decrement__Group__0__Impl
	rule__Decrement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Decrement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDecrementAccess().getVarAssignment_0()); }
	(rule__Decrement__VarAssignment_0)
	{ after(grammarAccess.getDecrementAccess().getVarAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Decrement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Decrement__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Decrement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDecrementAccess().getHyphenMinusHyphenMinusKeyword_1()); }
	'--'
	{ after(grammarAccess.getDecrementAccess().getHyphenMinusHyphenMinusKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__LoopAction__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LoopAction__Group__0__Impl
	rule__LoopAction__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__LoopAction__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLoopActionAccess().getWhileKeyword_0()); }
	'while'
	{ after(grammarAccess.getLoopActionAccess().getWhileKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LoopAction__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LoopAction__Group__1__Impl
	rule__LoopAction__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__LoopAction__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLoopActionAccess().getLeftParenthesisKeyword_1()); }
	'('
	{ after(grammarAccess.getLoopActionAccess().getLeftParenthesisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LoopAction__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LoopAction__Group__2__Impl
	rule__LoopAction__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__LoopAction__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLoopActionAccess().getConditionAssignment_2()); }
	(rule__LoopAction__ConditionAssignment_2)
	{ after(grammarAccess.getLoopActionAccess().getConditionAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LoopAction__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LoopAction__Group__3__Impl
	rule__LoopAction__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__LoopAction__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLoopActionAccess().getRightParenthesisKeyword_3()); }
	')'
	{ after(grammarAccess.getLoopActionAccess().getRightParenthesisKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LoopAction__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LoopAction__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__LoopAction__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLoopActionAccess().getActionAssignment_4()); }
	(rule__LoopAction__ActionAssignment_4)
	{ after(grammarAccess.getLoopActionAccess().getActionAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ConditionalAction__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConditionalAction__Group__0__Impl
	rule__ConditionalAction__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConditionalActionAccess().getIfKeyword_0()); }
	'if'
	{ after(grammarAccess.getConditionalActionAccess().getIfKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConditionalAction__Group__1__Impl
	rule__ConditionalAction__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConditionalActionAccess().getLeftParenthesisKeyword_1()); }
	'('
	{ after(grammarAccess.getConditionalActionAccess().getLeftParenthesisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConditionalAction__Group__2__Impl
	rule__ConditionalAction__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConditionalActionAccess().getConditionAssignment_2()); }
	(rule__ConditionalAction__ConditionAssignment_2)
	{ after(grammarAccess.getConditionalActionAccess().getConditionAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConditionalAction__Group__3__Impl
	rule__ConditionalAction__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConditionalActionAccess().getRightParenthesisKeyword_3()); }
	')'
	{ after(grammarAccess.getConditionalActionAccess().getRightParenthesisKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConditionalAction__Group__4__Impl
	rule__ConditionalAction__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConditionalActionAccess().getActionAssignment_4()); }
	(rule__ConditionalAction__ActionAssignment_4)
	{ after(grammarAccess.getConditionalActionAccess().getActionAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConditionalAction__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConditionalActionAccess().getGroup_5()); }
	(rule__ConditionalAction__Group_5__0)?
	{ after(grammarAccess.getConditionalActionAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ConditionalAction__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConditionalAction__Group_5__0__Impl
	rule__ConditionalAction__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConditionalActionAccess().getElseKeyword_5_0()); }
	'else'
	{ after(grammarAccess.getConditionalActionAccess().getElseKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConditionalAction__Group_5__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConditionalActionAccess().getElseActionAssignment_5_1()); }
	(rule__ConditionalAction__ElseActionAssignment_5_1)
	{ after(grammarAccess.getConditionalActionAccess().getElseActionAssignment_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ReturnAction__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ReturnAction__Group__0__Impl
	rule__ReturnAction__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ReturnAction__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReturnActionAccess().getReturnKeyword_0()); }
	'return'
	{ after(grammarAccess.getReturnActionAccess().getReturnKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ReturnAction__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ReturnAction__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ReturnAction__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReturnActionAccess().getExpAssignment_1()); }
	(rule__ReturnAction__ExpAssignment_1)
	{ after(grammarAccess.getReturnActionAccess().getExpAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__PrintAction__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrintAction__Group__0__Impl
	rule__PrintAction__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__PrintAction__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrintActionAccess().getPrintKeyword_0()); }
	'print'
	{ after(grammarAccess.getPrintActionAccess().getPrintKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrintAction__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__PrintAction__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__PrintAction__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrintActionAccess().getMsgAssignment_1()); }
	(rule__PrintAction__MsgAssignment_1)
	{ after(grammarAccess.getPrintActionAccess().getMsgAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ErrorAction__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ErrorAction__Group__0__Impl
	rule__ErrorAction__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ErrorAction__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getErrorActionAccess().getErrorKeyword_0()); }
	'error'
	{ after(grammarAccess.getErrorActionAccess().getErrorKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ErrorAction__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ErrorAction__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ErrorAction__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getErrorActionAccess().getMsgAssignment_1()); }
	(rule__ErrorAction__MsgAssignment_1)
	{ after(grammarAccess.getErrorActionAccess().getMsgAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__StartSession__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StartSession__Group__0__Impl
	rule__StartSession__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__StartSession__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStartSessionAccess().getForkKeyword_0()); }
	'fork'
	{ after(grammarAccess.getStartSessionAccess().getForkKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__StartSession__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__StartSession__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__StartSession__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getStartSessionAccess().getSessionAssignment_1()); }
	(rule__StartSession__SessionAssignment_1)
	{ after(grammarAccess.getStartSessionAccess().getSessionAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FunctionCallStatement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallStatement__Group__0__Impl
	rule__FunctionCallStatement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallStatementAccess().getFunctionAssignment_0()); }
	(rule__FunctionCallStatement__FunctionAssignment_0)
	{ after(grammarAccess.getFunctionCallStatementAccess().getFunctionAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallStatement__Group__1__Impl
	rule__FunctionCallStatement__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallStatementAccess().getLeftParenthesisKeyword_1()); }
	'('
	{ after(grammarAccess.getFunctionCallStatementAccess().getLeftParenthesisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallStatement__Group__2__Impl
	rule__FunctionCallStatement__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallStatementAccess().getGroup_2()); }
	(rule__FunctionCallStatement__Group_2__0)?
	{ after(grammarAccess.getFunctionCallStatementAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallStatement__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallStatementAccess().getRightParenthesisKeyword_3()); }
	')'
	{ after(grammarAccess.getFunctionCallStatementAccess().getRightParenthesisKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FunctionCallStatement__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallStatement__Group_2__0__Impl
	rule__FunctionCallStatement__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallStatementAccess().getParametersAssignment_2_0()); }
	(rule__FunctionCallStatement__ParametersAssignment_2_0)
	{ after(grammarAccess.getFunctionCallStatementAccess().getParametersAssignment_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallStatement__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallStatementAccess().getGroup_2_1()); }
	(rule__FunctionCallStatement__Group_2_1__0)*
	{ after(grammarAccess.getFunctionCallStatementAccess().getGroup_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FunctionCallStatement__Group_2_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallStatement__Group_2_1__0__Impl
	rule__FunctionCallStatement__Group_2_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group_2_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallStatementAccess().getCommaKeyword_2_1_0()); }
	','
	{ after(grammarAccess.getFunctionCallStatementAccess().getCommaKeyword_2_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group_2_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallStatement__Group_2_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__Group_2_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallStatementAccess().getParametersAssignment_2_1_1()); }
	(rule__FunctionCallStatement__ParametersAssignment_2_1_1)
	{ after(grammarAccess.getFunctionCallStatementAccess().getParametersAssignment_2_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__OrExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__OrExpression__Group__0__Impl
	rule__OrExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__OrExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getOrExpressionAccess().getAndExpressionParserRuleCall_0()); }
	ruleAndExpression
	{ after(grammarAccess.getOrExpressionAccess().getAndExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__OrExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__OrExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__OrExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getOrExpressionAccess().getGroup_1()); }
	(rule__OrExpression__Group_1__0)*
	{ after(grammarAccess.getOrExpressionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__OrExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__OrExpression__Group_1__0__Impl
	rule__OrExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__OrExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getOrExpressionAccess().getOrExpressionLhsAction_1_0()); }
	()
	{ after(grammarAccess.getOrExpressionAccess().getOrExpressionLhsAction_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__OrExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__OrExpression__Group_1__1__Impl
	rule__OrExpression__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__OrExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getOrExpressionAccess().getOrKeyword_1_1()); }
	'or'
	{ after(grammarAccess.getOrExpressionAccess().getOrKeyword_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__OrExpression__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__OrExpression__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__OrExpression__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getOrExpressionAccess().getRhsAssignment_1_2()); }
	(rule__OrExpression__RhsAssignment_1_2)
	{ after(grammarAccess.getOrExpressionAccess().getRhsAssignment_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__AndExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__AndExpression__Group__0__Impl
	rule__AndExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__AndExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAndExpressionAccess().getEqualityParserRuleCall_0()); }
	ruleEquality
	{ after(grammarAccess.getAndExpressionAccess().getEqualityParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__AndExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__AndExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__AndExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAndExpressionAccess().getGroup_1()); }
	(rule__AndExpression__Group_1__0)*
	{ after(grammarAccess.getAndExpressionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__AndExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__AndExpression__Group_1__0__Impl
	rule__AndExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__AndExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAndExpressionAccess().getAndExpressionLhsAction_1_0()); }
	()
	{ after(grammarAccess.getAndExpressionAccess().getAndExpressionLhsAction_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__AndExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__AndExpression__Group_1__1__Impl
	rule__AndExpression__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__AndExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAndExpressionAccess().getAndKeyword_1_1()); }
	'and'
	{ after(grammarAccess.getAndExpressionAccess().getAndKeyword_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__AndExpression__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__AndExpression__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__AndExpression__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAndExpressionAccess().getRhsAssignment_1_2()); }
	(rule__AndExpression__RhsAssignment_1_2)
	{ after(grammarAccess.getAndExpressionAccess().getRhsAssignment_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Equality__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Equality__Group__0__Impl
	rule__Equality__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEqualityAccess().getComparaisonParserRuleCall_0()); }
	ruleComparaison
	{ after(grammarAccess.getEqualityAccess().getComparaisonParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Equality__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEqualityAccess().getAlternatives_1()); }
	(rule__Equality__Alternatives_1)*
	{ after(grammarAccess.getEqualityAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Equality__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Equality__Group_1_0__0__Impl
	rule__Equality__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEqualityAccess().getEqualsExpressionLhsAction_1_0_0()); }
	()
	{ after(grammarAccess.getEqualityAccess().getEqualsExpressionLhsAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Equality__Group_1_0__1__Impl
	rule__Equality__Group_1_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEqualityAccess().getEqualsSignEqualsSignKeyword_1_0_1()); }
	'=='
	{ after(grammarAccess.getEqualityAccess().getEqualsSignEqualsSignKeyword_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group_1_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Equality__Group_1_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group_1_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEqualityAccess().getRhsAssignment_1_0_2()); }
	(rule__Equality__RhsAssignment_1_0_2)
	{ after(grammarAccess.getEqualityAccess().getRhsAssignment_1_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Equality__Group_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Equality__Group_1_1__0__Impl
	rule__Equality__Group_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEqualityAccess().getNotEqualsExpressionLhsAction_1_1_0()); }
	()
	{ after(grammarAccess.getEqualityAccess().getNotEqualsExpressionLhsAction_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Equality__Group_1_1__1__Impl
	rule__Equality__Group_1_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEqualityAccess().getExclamationMarkEqualsSignKeyword_1_1_1()); }
	'!='
	{ after(grammarAccess.getEqualityAccess().getExclamationMarkEqualsSignKeyword_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group_1_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Equality__Group_1_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__Group_1_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEqualityAccess().getRhsAssignment_1_1_2()); }
	(rule__Equality__RhsAssignment_1_1_2)
	{ after(grammarAccess.getEqualityAccess().getRhsAssignment_1_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Comparaison__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group__0__Impl
	rule__Comparaison__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getAdditionParserRuleCall_0()); }
	ruleAddition
	{ after(grammarAccess.getComparaisonAccess().getAdditionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getAlternatives_1()); }
	(rule__Comparaison__Alternatives_1)*
	{ after(grammarAccess.getComparaisonAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Comparaison__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group_1_0__0__Impl
	rule__Comparaison__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getGreaterExpressionLhsAction_1_0_0()); }
	()
	{ after(grammarAccess.getComparaisonAccess().getGreaterExpressionLhsAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group_1_0__1__Impl
	rule__Comparaison__Group_1_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getGreaterThanSignKeyword_1_0_1()); }
	'>'
	{ after(grammarAccess.getComparaisonAccess().getGreaterThanSignKeyword_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group_1_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getRhsAssignment_1_0_2()); }
	(rule__Comparaison__RhsAssignment_1_0_2)
	{ after(grammarAccess.getComparaisonAccess().getRhsAssignment_1_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Comparaison__Group_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group_1_1__0__Impl
	rule__Comparaison__Group_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getLowerExpressionLhsAction_1_1_0()); }
	()
	{ after(grammarAccess.getComparaisonAccess().getLowerExpressionLhsAction_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group_1_1__1__Impl
	rule__Comparaison__Group_1_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getLessThanSignKeyword_1_1_1()); }
	'<'
	{ after(grammarAccess.getComparaisonAccess().getLessThanSignKeyword_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group_1_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getRhsAssignment_1_1_2()); }
	(rule__Comparaison__RhsAssignment_1_1_2)
	{ after(grammarAccess.getComparaisonAccess().getRhsAssignment_1_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Comparaison__Group_1_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group_1_2__0__Impl
	rule__Comparaison__Group_1_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getGreaterOrEqualExpressionLhsAction_1_2_0()); }
	()
	{ after(grammarAccess.getComparaisonAccess().getGreaterOrEqualExpressionLhsAction_1_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group_1_2__1__Impl
	rule__Comparaison__Group_1_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getGreaterThanSignEqualsSignKeyword_1_2_1()); }
	'>='
	{ after(grammarAccess.getComparaisonAccess().getGreaterThanSignEqualsSignKeyword_1_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group_1_2__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getRhsAssignment_1_2_2()); }
	(rule__Comparaison__RhsAssignment_1_2_2)
	{ after(grammarAccess.getComparaisonAccess().getRhsAssignment_1_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Comparaison__Group_1_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group_1_3__0__Impl
	rule__Comparaison__Group_1_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getLowerOrEqualExpressionLhsAction_1_3_0()); }
	()
	{ after(grammarAccess.getComparaisonAccess().getLowerOrEqualExpressionLhsAction_1_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group_1_3__1__Impl
	rule__Comparaison__Group_1_3__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getLessThanSignEqualsSignKeyword_1_3_1()); }
	'<='
	{ after(grammarAccess.getComparaisonAccess().getLessThanSignEqualsSignKeyword_1_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_3__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Comparaison__Group_1_3__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__Group_1_3__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getComparaisonAccess().getRhsAssignment_1_3_2()); }
	(rule__Comparaison__RhsAssignment_1_3_2)
	{ after(grammarAccess.getComparaisonAccess().getRhsAssignment_1_3_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Addition__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Addition__Group__0__Impl
	rule__Addition__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0()); }
	ruleMultiplication
	{ after(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Addition__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAdditionAccess().getAlternatives_1()); }
	(rule__Addition__Alternatives_1)*
	{ after(grammarAccess.getAdditionAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Addition__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Addition__Group_1_0__0__Impl
	rule__Addition__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAdditionAccess().getPlusExpressionLhsAction_1_0_0()); }
	()
	{ after(grammarAccess.getAdditionAccess().getPlusExpressionLhsAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Addition__Group_1_0__1__Impl
	rule__Addition__Group_1_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAdditionAccess().getPlusSignKeyword_1_0_1()); }
	'+'
	{ after(grammarAccess.getAdditionAccess().getPlusSignKeyword_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group_1_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Addition__Group_1_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group_1_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAdditionAccess().getRhsAssignment_1_0_2()); }
	(rule__Addition__RhsAssignment_1_0_2)
	{ after(grammarAccess.getAdditionAccess().getRhsAssignment_1_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Addition__Group_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Addition__Group_1_1__0__Impl
	rule__Addition__Group_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAdditionAccess().getMinusExpressionLhsAction_1_1_0()); }
	()
	{ after(grammarAccess.getAdditionAccess().getMinusExpressionLhsAction_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Addition__Group_1_1__1__Impl
	rule__Addition__Group_1_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAdditionAccess().getHyphenMinusKeyword_1_1_1()); }
	'-'
	{ after(grammarAccess.getAdditionAccess().getHyphenMinusKeyword_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group_1_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Addition__Group_1_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__Group_1_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAdditionAccess().getRhsAssignment_1_1_2()); }
	(rule__Addition__RhsAssignment_1_1_2)
	{ after(grammarAccess.getAdditionAccess().getRhsAssignment_1_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Multiplication__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Multiplication__Group__0__Impl
	rule__Multiplication__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMultiplicationAccess().getModuloParserRuleCall_0()); }
	ruleModulo
	{ after(grammarAccess.getMultiplicationAccess().getModuloParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Multiplication__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMultiplicationAccess().getAlternatives_1()); }
	(rule__Multiplication__Alternatives_1)*
	{ after(grammarAccess.getMultiplicationAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Multiplication__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Multiplication__Group_1_0__0__Impl
	rule__Multiplication__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMultiplicationAccess().getTimesExpressionLhsAction_1_0_0()); }
	()
	{ after(grammarAccess.getMultiplicationAccess().getTimesExpressionLhsAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Multiplication__Group_1_0__1__Impl
	rule__Multiplication__Group_1_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_0_1()); }
	'*'
	{ after(grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group_1_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Multiplication__Group_1_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group_1_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMultiplicationAccess().getRhsAssignment_1_0_2()); }
	(rule__Multiplication__RhsAssignment_1_0_2)
	{ after(grammarAccess.getMultiplicationAccess().getRhsAssignment_1_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Multiplication__Group_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Multiplication__Group_1_1__0__Impl
	rule__Multiplication__Group_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMultiplicationAccess().getDivExpressionLhsAction_1_1_0()); }
	()
	{ after(grammarAccess.getMultiplicationAccess().getDivExpressionLhsAction_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Multiplication__Group_1_1__1__Impl
	rule__Multiplication__Group_1_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMultiplicationAccess().getSolidusKeyword_1_1_1()); }
	'/'
	{ after(grammarAccess.getMultiplicationAccess().getSolidusKeyword_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group_1_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Multiplication__Group_1_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__Group_1_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getMultiplicationAccess().getRhsAssignment_1_1_2()); }
	(rule__Multiplication__RhsAssignment_1_1_2)
	{ after(grammarAccess.getMultiplicationAccess().getRhsAssignment_1_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Modulo__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Modulo__Group__0__Impl
	rule__Modulo__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Modulo__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuloAccess().getPrimaryParserRuleCall_0()); }
	rulePrimary
	{ after(grammarAccess.getModuloAccess().getPrimaryParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Modulo__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Modulo__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Modulo__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuloAccess().getGroup_1()); }
	(rule__Modulo__Group_1__0)?
	{ after(grammarAccess.getModuloAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Modulo__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Modulo__Group_1__0__Impl
	rule__Modulo__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Modulo__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuloAccess().getModExpressionLhsAction_1_0()); }
	()
	{ after(grammarAccess.getModuloAccess().getModExpressionLhsAction_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Modulo__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Modulo__Group_1__1__Impl
	rule__Modulo__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Modulo__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuloAccess().getPercentSignKeyword_1_1()); }
	'%'
	{ after(grammarAccess.getModuloAccess().getPercentSignKeyword_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Modulo__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Modulo__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Modulo__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getModuloAccess().getRhsAssignment_1_2()); }
	(rule__Modulo__RhsAssignment_1_2)
	{ after(grammarAccess.getModuloAccess().getRhsAssignment_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Primary__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Primary__Group_0__0__Impl
	rule__Primary__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_0_0()); }
	'('
	{ after(grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Primary__Group_0__1__Impl
	rule__Primary__Group_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimaryAccess().getExpressionParserRuleCall_0_1()); }
	ruleExpression
	{ after(grammarAccess.getPrimaryAccess().getExpressionParserRuleCall_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Primary__Group_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_0_2()); }
	')'
	{ after(grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Primary__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Primary__Group_1__0__Impl
	rule__Primary__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimaryAccess().getNotExpressionAction_1_0()); }
	()
	{ after(grammarAccess.getPrimaryAccess().getNotExpressionAction_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Primary__Group_1__1__Impl
	rule__Primary__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimaryAccess().getNotKeyword_1_1()); }
	'not'
	{ after(grammarAccess.getPrimaryAccess().getNotKeyword_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Primary__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimaryAccess().getTermAssignment_1_2()); }
	(rule__Primary__TermAssignment_1_2)
	{ after(grammarAccess.getPrimaryAccess().getTermAssignment_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Primary__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Primary__Group_2__0__Impl
	rule__Primary__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimaryAccess().getUnaryMinusAction_2_0()); }
	()
	{ after(grammarAccess.getPrimaryAccess().getUnaryMinusAction_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Primary__Group_2__1__Impl
	rule__Primary__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimaryAccess().getHyphenMinusKeyword_2_1()); }
	'-'
	{ after(grammarAccess.getPrimaryAccess().getHyphenMinusKeyword_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Primary__Group_2__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getPrimaryAccess().getTermAssignment_2_2()); }
	(rule__Primary__TermAssignment_2_2)
	{ after(grammarAccess.getPrimaryAccess().getTermAssignment_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrayIndexPostfix__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayIndexPostfix__Group__0__Impl
	rule__ArrayIndexPostfix__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayIndexPostfix__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayIndexPostfixAccess().getAtomicExpressionParserRuleCall_0()); }
	ruleAtomicExpression
	{ after(grammarAccess.getArrayIndexPostfixAccess().getAtomicExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayIndexPostfix__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayIndexPostfix__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayIndexPostfix__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayIndexPostfixAccess().getGroup_1()); }
	(rule__ArrayIndexPostfix__Group_1__0)?
	{ after(grammarAccess.getArrayIndexPostfixAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ArrayIndexPostfix__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayIndexPostfix__Group_1__0__Impl
	rule__ArrayIndexPostfix__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayIndexPostfix__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayIndexPostfixAccess().getArrayIndexArrayAction_1_0()); }
	()
	{ after(grammarAccess.getArrayIndexPostfixAccess().getArrayIndexArrayAction_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayIndexPostfix__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayIndexPostfix__Group_1__1__Impl
	rule__ArrayIndexPostfix__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayIndexPostfix__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayIndexPostfixAccess().getLeftSquareBracketKeyword_1_1()); }
	'['
	{ after(grammarAccess.getArrayIndexPostfixAccess().getLeftSquareBracketKeyword_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayIndexPostfix__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayIndexPostfix__Group_1__2__Impl
	rule__ArrayIndexPostfix__Group_1__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayIndexPostfix__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayIndexPostfixAccess().getIndexAssignment_1_2()); }
	(rule__ArrayIndexPostfix__IndexAssignment_1_2)
	{ after(grammarAccess.getArrayIndexPostfixAccess().getIndexAssignment_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayIndexPostfix__Group_1__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ArrayIndexPostfix__Group_1__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayIndexPostfix__Group_1__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getArrayIndexPostfixAccess().getRightSquareBracketKeyword_1_3()); }
	']'
	{ after(grammarAccess.getArrayIndexPostfixAccess().getRightSquareBracketKeyword_1_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ExternExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternExpression__Group__0__Impl
	rule__ExternExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternExpressionAccess().getExpressionAssignment_0()); }
	(rule__ExternExpression__ExpressionAssignment_0)
	{ after(grammarAccess.getExternExpressionAccess().getExpressionAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternExpressionAccess().getGroup_1()); }
	(rule__ExternExpression__Group_1__0)*
	{ after(grammarAccess.getExternExpressionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ExternExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternExpression__Group_1__0__Impl
	rule__ExternExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternExpressionAccess().getAmpersandKeyword_1_0()); }
	'&'
	{ after(grammarAccess.getExternExpressionAccess().getAmpersandKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternExpression__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternExpressionAccess().getSegmentsAssignment_1_1()); }
	(rule__ExternExpression__SegmentsAssignment_1_1)
	{ after(grammarAccess.getExternExpressionAccess().getSegmentsAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__EnumLiteralRef__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EnumLiteralRef__Group__0__Impl
	rule__EnumLiteralRef__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumLiteralRef__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumLiteralRefAccess().getEnumAssignment_0()); }
	(rule__EnumLiteralRef__EnumAssignment_0)
	{ after(grammarAccess.getEnumLiteralRefAccess().getEnumAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumLiteralRef__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EnumLiteralRef__Group__1__Impl
	rule__EnumLiteralRef__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumLiteralRef__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumLiteralRefAccess().getColonKeyword_1()); }
	':'
	{ after(grammarAccess.getEnumLiteralRefAccess().getColonKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumLiteralRef__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EnumLiteralRef__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumLiteralRef__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumLiteralRefAccess().getLiteralAssignment_2()); }
	(rule__EnumLiteralRef__LiteralAssignment_2)
	{ after(grammarAccess.getEnumLiteralRefAccess().getLiteralAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__BooleanLiteral__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__BooleanLiteral__Group_1__0__Impl
	rule__BooleanLiteral__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__BooleanLiteral__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getBooleanLiteralAccess().getBooleanLiteralAction_1_0()); }
	()
	{ after(grammarAccess.getBooleanLiteralAccess().getBooleanLiteralAction_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__BooleanLiteral__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__BooleanLiteral__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__BooleanLiteral__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getBooleanLiteralAccess().getFalseKeyword_1_1()); }
	'false'
	{ after(grammarAccess.getBooleanLiteralAccess().getFalseKeyword_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__EventReference__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventReference__Group__0__Impl
	rule__EventReference__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__EventReference__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventReferenceAccess().getReceiveMsgAssignment_0()); }
	(rule__EventReference__ReceiveMsgAssignment_0)
	{ after(grammarAccess.getEventReferenceAccess().getReceiveMsgAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventReference__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventReference__Group__1__Impl
	rule__EventReference__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__EventReference__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventReferenceAccess().getFullStopKeyword_1()); }
	'.'
	{ after(grammarAccess.getEventReferenceAccess().getFullStopKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventReference__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventReference__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__EventReference__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventReferenceAccess().getParameterAssignment_2()); }
	(rule__EventReference__ParameterAssignment_2)
	{ after(grammarAccess.getEventReferenceAccess().getParameterAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FunctionCallExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallExpression__Group__0__Impl
	rule__FunctionCallExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallExpressionAccess().getFunctionAssignment_0()); }
	(rule__FunctionCallExpression__FunctionAssignment_0)
	{ after(grammarAccess.getFunctionCallExpressionAccess().getFunctionAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallExpression__Group__1__Impl
	rule__FunctionCallExpression__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallExpressionAccess().getLeftParenthesisKeyword_1()); }
	'('
	{ after(grammarAccess.getFunctionCallExpressionAccess().getLeftParenthesisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallExpression__Group__2__Impl
	rule__FunctionCallExpression__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallExpressionAccess().getGroup_2()); }
	(rule__FunctionCallExpression__Group_2__0)?
	{ after(grammarAccess.getFunctionCallExpressionAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallExpression__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallExpressionAccess().getRightParenthesisKeyword_3()); }
	')'
	{ after(grammarAccess.getFunctionCallExpressionAccess().getRightParenthesisKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FunctionCallExpression__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallExpression__Group_2__0__Impl
	rule__FunctionCallExpression__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallExpressionAccess().getParametersAssignment_2_0()); }
	(rule__FunctionCallExpression__ParametersAssignment_2_0)
	{ after(grammarAccess.getFunctionCallExpressionAccess().getParametersAssignment_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallExpression__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallExpressionAccess().getGroup_2_1()); }
	(rule__FunctionCallExpression__Group_2_1__0)*
	{ after(grammarAccess.getFunctionCallExpressionAccess().getGroup_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FunctionCallExpression__Group_2_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallExpression__Group_2_1__0__Impl
	rule__FunctionCallExpression__Group_2_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group_2_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallExpressionAccess().getCommaKeyword_2_1_0()); }
	','
	{ after(grammarAccess.getFunctionCallExpressionAccess().getCommaKeyword_2_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group_2_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FunctionCallExpression__Group_2_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__Group_2_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFunctionCallExpressionAccess().getParametersAssignment_2_1_1()); }
	(rule__FunctionCallExpression__ParametersAssignment_2_1_1)
	{ after(grammarAccess.getFunctionCallExpressionAccess().getParametersAssignment_2_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Configuration__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Configuration__Group__0__Impl
	rule__Configuration__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigurationAccess().getConfigurationKeyword_0()); }
	'configuration'
	{ after(grammarAccess.getConfigurationAccess().getConfigurationKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Configuration__Group__1__Impl
	rule__Configuration__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigurationAccess().getNameAssignment_1()); }
	(rule__Configuration__NameAssignment_1)
	{ after(grammarAccess.getConfigurationAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Configuration__Group__2__Impl
	rule__Configuration__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigurationAccess().getAnnotationsAssignment_2()); }
	(rule__Configuration__AnnotationsAssignment_2)*
	{ after(grammarAccess.getConfigurationAccess().getAnnotationsAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Configuration__Group__3__Impl
	rule__Configuration__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigurationAccess().getLeftCurlyBracketKeyword_3()); }
	'{'
	{ after(grammarAccess.getConfigurationAccess().getLeftCurlyBracketKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Configuration__Group__4__Impl
	rule__Configuration__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigurationAccess().getAlternatives_4()); }
	(rule__Configuration__Alternatives_4)*
	{ after(grammarAccess.getConfigurationAccess().getAlternatives_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Configuration__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigurationAccess().getRightCurlyBracketKeyword_5()); }
	'}'
	{ after(grammarAccess.getConfigurationAccess().getRightCurlyBracketKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Instance__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Instance__Group__0__Impl
	rule__Instance__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Instance__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInstanceAccess().getInstanceKeyword_0()); }
	'instance'
	{ after(grammarAccess.getInstanceAccess().getInstanceKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Instance__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Instance__Group__1__Impl
	rule__Instance__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Instance__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInstanceAccess().getNameAssignment_1()); }
	(rule__Instance__NameAssignment_1)
	{ after(grammarAccess.getInstanceAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Instance__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Instance__Group__2__Impl
	rule__Instance__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Instance__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInstanceAccess().getColonKeyword_2()); }
	':'
	{ after(grammarAccess.getInstanceAccess().getColonKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Instance__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Instance__Group__3__Impl
	rule__Instance__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Instance__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInstanceAccess().getTypeAssignment_3()); }
	(rule__Instance__TypeAssignment_3)
	{ after(grammarAccess.getInstanceAccess().getTypeAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Instance__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Instance__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Instance__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getInstanceAccess().getAnnotationsAssignment_4()); }
	(rule__Instance__AnnotationsAssignment_4)*
	{ after(grammarAccess.getInstanceAccess().getAnnotationsAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ConfigPropertyAssign__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConfigPropertyAssign__Group__0__Impl
	rule__ConfigPropertyAssign__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigPropertyAssignAccess().getSetKeyword_0()); }
	'set'
	{ after(grammarAccess.getConfigPropertyAssignAccess().getSetKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConfigPropertyAssign__Group__1__Impl
	rule__ConfigPropertyAssign__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigPropertyAssignAccess().getInstanceAssignment_1()); }
	(rule__ConfigPropertyAssign__InstanceAssignment_1)
	{ after(grammarAccess.getConfigPropertyAssignAccess().getInstanceAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConfigPropertyAssign__Group__2__Impl
	rule__ConfigPropertyAssign__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigPropertyAssignAccess().getFullStopKeyword_2()); }
	'.'
	{ after(grammarAccess.getConfigPropertyAssignAccess().getFullStopKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConfigPropertyAssign__Group__3__Impl
	rule__ConfigPropertyAssign__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigPropertyAssignAccess().getPropertyAssignment_3()); }
	(rule__ConfigPropertyAssign__PropertyAssignment_3)
	{ after(grammarAccess.getConfigPropertyAssignAccess().getPropertyAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConfigPropertyAssign__Group__4__Impl
	rule__ConfigPropertyAssign__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigPropertyAssignAccess().getGroup_4()); }
	(rule__ConfigPropertyAssign__Group_4__0)*
	{ after(grammarAccess.getConfigPropertyAssignAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConfigPropertyAssign__Group__5__Impl
	rule__ConfigPropertyAssign__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigPropertyAssignAccess().getEqualsSignKeyword_5()); }
	'='
	{ after(grammarAccess.getConfigPropertyAssignAccess().getEqualsSignKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConfigPropertyAssign__Group__6__Impl
	rule__ConfigPropertyAssign__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigPropertyAssignAccess().getInitAssignment_6()); }
	(rule__ConfigPropertyAssign__InitAssignment_6)
	{ after(grammarAccess.getConfigPropertyAssignAccess().getInitAssignment_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConfigPropertyAssign__Group__7__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigPropertyAssignAccess().getAnnotationsAssignment_7()); }
	(rule__ConfigPropertyAssign__AnnotationsAssignment_7)*
	{ after(grammarAccess.getConfigPropertyAssignAccess().getAnnotationsAssignment_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ConfigPropertyAssign__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConfigPropertyAssign__Group_4__0__Impl
	rule__ConfigPropertyAssign__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigPropertyAssignAccess().getLeftSquareBracketKeyword_4_0()); }
	'['
	{ after(grammarAccess.getConfigPropertyAssignAccess().getLeftSquareBracketKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConfigPropertyAssign__Group_4__1__Impl
	rule__ConfigPropertyAssign__Group_4__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigPropertyAssignAccess().getIndexAssignment_4_1()); }
	(rule__ConfigPropertyAssign__IndexAssignment_4_1)
	{ after(grammarAccess.getConfigPropertyAssignAccess().getIndexAssignment_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group_4__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ConfigPropertyAssign__Group_4__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__Group_4__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConfigPropertyAssignAccess().getRightSquareBracketKeyword_4_2()); }
	']'
	{ after(grammarAccess.getConfigPropertyAssignAccess().getRightSquareBracketKeyword_4_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Connector__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Connector__Group__0__Impl
	rule__Connector__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConnectorAccess().getConnectorKeyword_0()); }
	'connector'
	{ after(grammarAccess.getConnectorAccess().getConnectorKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Connector__Group__1__Impl
	rule__Connector__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConnectorAccess().getNameAssignment_1()); }
	(rule__Connector__NameAssignment_1)?
	{ after(grammarAccess.getConnectorAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Connector__Group__2__Impl
	rule__Connector__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConnectorAccess().getCliAssignment_2()); }
	(rule__Connector__CliAssignment_2)
	{ after(grammarAccess.getConnectorAccess().getCliAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Connector__Group__3__Impl
	rule__Connector__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConnectorAccess().getFullStopKeyword_3()); }
	'.'
	{ after(grammarAccess.getConnectorAccess().getFullStopKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Connector__Group__4__Impl
	rule__Connector__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConnectorAccess().getRequiredAssignment_4()); }
	(rule__Connector__RequiredAssignment_4)
	{ after(grammarAccess.getConnectorAccess().getRequiredAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Connector__Group__5__Impl
	rule__Connector__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConnectorAccess().getEqualsSignGreaterThanSignKeyword_5()); }
	'=>'
	{ after(grammarAccess.getConnectorAccess().getEqualsSignGreaterThanSignKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Connector__Group__6__Impl
	rule__Connector__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConnectorAccess().getSrvAssignment_6()); }
	(rule__Connector__SrvAssignment_6)
	{ after(grammarAccess.getConnectorAccess().getSrvAssignment_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Connector__Group__7__Impl
	rule__Connector__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConnectorAccess().getFullStopKeyword_7()); }
	'.'
	{ after(grammarAccess.getConnectorAccess().getFullStopKeyword_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Connector__Group__8__Impl
	rule__Connector__Group__9
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConnectorAccess().getProvidedAssignment_8()); }
	(rule__Connector__ProvidedAssignment_8)
	{ after(grammarAccess.getConnectorAccess().getProvidedAssignment_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Connector__Group__9__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__Group__9__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getConnectorAccess().getAnnotationsAssignment_9()); }
	(rule__Connector__AnnotationsAssignment_9)*
	{ after(grammarAccess.getConnectorAccess().getAnnotationsAssignment_9()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ExternalConnector__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternalConnector__Group__0__Impl
	rule__ExternalConnector__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternalConnectorAccess().getConnectorKeyword_0()); }
	'connector'
	{ after(grammarAccess.getExternalConnectorAccess().getConnectorKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternalConnector__Group__1__Impl
	rule__ExternalConnector__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternalConnectorAccess().getNameAssignment_1()); }
	(rule__ExternalConnector__NameAssignment_1)?
	{ after(grammarAccess.getExternalConnectorAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternalConnector__Group__2__Impl
	rule__ExternalConnector__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternalConnectorAccess().getInstAssignment_2()); }
	(rule__ExternalConnector__InstAssignment_2)
	{ after(grammarAccess.getExternalConnectorAccess().getInstAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternalConnector__Group__3__Impl
	rule__ExternalConnector__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternalConnectorAccess().getFullStopKeyword_3()); }
	'.'
	{ after(grammarAccess.getExternalConnectorAccess().getFullStopKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternalConnector__Group__4__Impl
	rule__ExternalConnector__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternalConnectorAccess().getPortAssignment_4()); }
	(rule__ExternalConnector__PortAssignment_4)
	{ after(grammarAccess.getExternalConnectorAccess().getPortAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternalConnector__Group__5__Impl
	rule__ExternalConnector__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternalConnectorAccess().getOverKeyword_5()); }
	'over'
	{ after(grammarAccess.getExternalConnectorAccess().getOverKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternalConnector__Group__6__Impl
	rule__ExternalConnector__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternalConnectorAccess().getProtocolAssignment_6()); }
	(rule__ExternalConnector__ProtocolAssignment_6)
	{ after(grammarAccess.getExternalConnectorAccess().getProtocolAssignment_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExternalConnector__Group__7__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExternalConnectorAccess().getAnnotationsAssignment_7()); }
	(rule__ExternalConnector__AnnotationsAssignment_7)*
	{ after(grammarAccess.getExternalConnectorAccess().getAnnotationsAssignment_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ThingMLModel__ImportURIAssignment_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingMLModelAccess().getImportURISTRING_LITTerminalRuleCall_0_1_0()); }
		RULE_STRING_LIT
		{ after(grammarAccess.getThingMLModelAccess().getImportURISTRING_LITTerminalRuleCall_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThingMLModel__TypesAssignment_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingMLModelAccess().getTypesTypeParserRuleCall_1_0_0()); }
		ruleType
		{ after(grammarAccess.getThingMLModelAccess().getTypesTypeParserRuleCall_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThingMLModel__ProtocolsAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingMLModelAccess().getProtocolsProtocolParserRuleCall_1_1_0()); }
		ruleProtocol
		{ after(grammarAccess.getThingMLModelAccess().getProtocolsProtocolParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ThingMLModel__ConfigsAssignment_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingMLModelAccess().getConfigsConfigurationParserRuleCall_1_2_0()); }
		ruleConfiguration
		{ after(grammarAccess.getThingMLModelAccess().getConfigsConfigurationParserRuleCall_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PlatformAnnotation__NameAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPlatformAnnotationAccess().getNameANNOTATION_IDTerminalRuleCall_0_0()); }
		RULE_ANNOTATION_ID
		{ after(grammarAccess.getPlatformAnnotationAccess().getNameANNOTATION_IDTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PlatformAnnotation__ValueAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPlatformAnnotationAccess().getValueSTRING_LITTerminalRuleCall_1_0()); }
		RULE_STRING_LIT
		{ after(grammarAccess.getPlatformAnnotationAccess().getValueSTRING_LITTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Variable__NameAssignment_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVariableAccess().getNameIDTerminalRuleCall_0_1_0()); }
		RULE_ID
		{ after(grammarAccess.getVariableAccess().getNameIDTerminalRuleCall_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Variable__TypeRefAssignment_0_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVariableAccess().getTypeRefTypeRefParserRuleCall_0_3_0()); }
		ruleTypeRef
		{ after(grammarAccess.getVariableAccess().getTypeRefTypeRefParserRuleCall_0_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__TypeAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeRefAccess().getTypeTypeCrossReference_0_0()); }
		(
			{ before(grammarAccess.getTypeRefAccess().getTypeTypeIDTerminalRuleCall_0_0_1()); }
			RULE_ID
			{ after(grammarAccess.getTypeRefAccess().getTypeTypeIDTerminalRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getTypeRefAccess().getTypeTypeCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__IsArrayAssignment_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeRefAccess().getIsArrayLeftSquareBracketKeyword_1_0_0()); }
		(
			{ before(grammarAccess.getTypeRefAccess().getIsArrayLeftSquareBracketKeyword_1_0_0()); }
			'['
			{ after(grammarAccess.getTypeRefAccess().getIsArrayLeftSquareBracketKeyword_1_0_0()); }
		)
		{ after(grammarAccess.getTypeRefAccess().getIsArrayLeftSquareBracketKeyword_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TypeRef__CardinalityAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTypeRefAccess().getCardinalityExpressionParserRuleCall_1_1_0()); }
		ruleExpression
		{ after(grammarAccess.getTypeRefAccess().getCardinalityExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrimitiveTypeAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getPrimitiveTypeAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__ByteSizeAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrimitiveTypeAccess().getByteSizeINTTerminalRuleCall_3_0()); }
		RULE_INT
		{ after(grammarAccess.getPrimitiveTypeAccess().getByteSizeINTTerminalRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrimitiveType__AnnotationsAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrimitiveTypeAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getPrimitiveTypeAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ObjectType__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getObjectTypeAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getObjectTypeAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ObjectType__AnnotationsAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getObjectTypeAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getObjectTypeAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEnumerationAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getEnumerationAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__AnnotationsAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEnumerationAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getEnumerationAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Enumeration__LiteralsAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEnumerationAccess().getLiteralsEnumerationLiteralParserRuleCall_4_0()); }
		ruleEnumerationLiteral
		{ after(grammarAccess.getEnumerationAccess().getLiteralsEnumerationLiteralParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumerationLiteral__NameAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEnumerationLiteralAccess().getNameIDTerminalRuleCall_0_0()); }
		RULE_ID
		{ after(grammarAccess.getEnumerationLiteralAccess().getNameIDTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumerationLiteral__AnnotationsAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEnumerationLiteralAccess().getAnnotationsPlatformAnnotationParserRuleCall_1_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getEnumerationLiteralAccess().getAnnotationsPlatformAnnotationParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__FragmentAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingAccess().getFragmentFragmentKeyword_1_0()); }
		(
			{ before(grammarAccess.getThingAccess().getFragmentFragmentKeyword_1_0()); }
			'fragment'
			{ after(grammarAccess.getThingAccess().getFragmentFragmentKeyword_1_0()); }
		)
		{ after(grammarAccess.getThingAccess().getFragmentFragmentKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__NameAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingAccess().getNameIDTerminalRuleCall_2_0()); }
		RULE_ID
		{ after(grammarAccess.getThingAccess().getNameIDTerminalRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__IncludesAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingAccess().getIncludesThingCrossReference_3_1_0()); }
		(
			{ before(grammarAccess.getThingAccess().getIncludesThingIDTerminalRuleCall_3_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getThingAccess().getIncludesThingIDTerminalRuleCall_3_1_0_1()); }
		)
		{ after(grammarAccess.getThingAccess().getIncludesThingCrossReference_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__IncludesAssignment_3_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingAccess().getIncludesThingCrossReference_3_2_1_0()); }
		(
			{ before(grammarAccess.getThingAccess().getIncludesThingIDTerminalRuleCall_3_2_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getThingAccess().getIncludesThingIDTerminalRuleCall_3_2_1_0_1()); }
		)
		{ after(grammarAccess.getThingAccess().getIncludesThingCrossReference_3_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__AnnotationsAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getThingAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__MessagesAssignment_6_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingAccess().getMessagesMessageParserRuleCall_6_0_0()); }
		ruleMessage
		{ after(grammarAccess.getThingAccess().getMessagesMessageParserRuleCall_6_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__PortsAssignment_6_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingAccess().getPortsPortParserRuleCall_6_1_0()); }
		rulePort
		{ after(grammarAccess.getThingAccess().getPortsPortParserRuleCall_6_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__PropertiesAssignment_6_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingAccess().getPropertiesPropertyParserRuleCall_6_2_0()); }
		ruleProperty
		{ after(grammarAccess.getThingAccess().getPropertiesPropertyParserRuleCall_6_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__FunctionsAssignment_6_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingAccess().getFunctionsFunctionParserRuleCall_6_3_0()); }
		ruleFunction
		{ after(grammarAccess.getThingAccess().getFunctionsFunctionParserRuleCall_6_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__AssignAssignment_6_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingAccess().getAssignPropertyAssignParserRuleCall_6_4_0()); }
		rulePropertyAssign
		{ after(grammarAccess.getThingAccess().getAssignPropertyAssignParserRuleCall_6_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Thing__BehaviourAssignment_6_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getThingAccess().getBehaviourStateMachineParserRuleCall_6_5_0()); }
		ruleStateMachine
		{ after(grammarAccess.getThingAccess().getBehaviourStateMachineParserRuleCall_6_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__PropertyAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPropertyAssignAccess().getPropertyPropertyCrossReference_1_0()); }
		(
			{ before(grammarAccess.getPropertyAssignAccess().getPropertyPropertyIDTerminalRuleCall_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getPropertyAssignAccess().getPropertyPropertyIDTerminalRuleCall_1_0_1()); }
		)
		{ after(grammarAccess.getPropertyAssignAccess().getPropertyPropertyCrossReference_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__IndexAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPropertyAssignAccess().getIndexExpressionParserRuleCall_2_1_0()); }
		ruleExpression
		{ after(grammarAccess.getPropertyAssignAccess().getIndexExpressionParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__InitAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPropertyAssignAccess().getInitExpressionParserRuleCall_4_0()); }
		ruleExpression
		{ after(grammarAccess.getPropertyAssignAccess().getInitExpressionParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyAssign__AnnotationsAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPropertyAssignAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getPropertyAssignAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Protocol__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProtocolAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getProtocolAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Protocol__AnnotationsAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProtocolAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getProtocolAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getFunctionAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__ParametersAssignment_3_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_3_0_0()); }
		ruleParameter
		{ after(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_3_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__ParametersAssignment_3_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_3_1_1_0()); }
		ruleParameter
		{ after(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_3_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__TypeRefAssignment_5_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionAccess().getTypeRefTypeRefParserRuleCall_5_1_0()); }
		ruleTypeRef
		{ after(grammarAccess.getFunctionAccess().getTypeRefTypeRefParserRuleCall_5_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__AnnotationsAssignment_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getFunctionAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Function__BodyAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionAccess().getBodyActionParserRuleCall_7_0()); }
		ruleAction
		{ after(grammarAccess.getFunctionAccess().getBodyActionParserRuleCall_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__ReadonlyAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPropertyAccess().getReadonlyReadonlyKeyword_0_0()); }
		(
			{ before(grammarAccess.getPropertyAccess().getReadonlyReadonlyKeyword_0_0()); }
			'readonly'
			{ after(grammarAccess.getPropertyAccess().getReadonlyReadonlyKeyword_0_0()); }
		)
		{ after(grammarAccess.getPropertyAccess().getReadonlyReadonlyKeyword_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__NameAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPropertyAccess().getNameIDTerminalRuleCall_2_0()); }
		RULE_ID
		{ after(grammarAccess.getPropertyAccess().getNameIDTerminalRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__TypeRefAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPropertyAccess().getTypeRefTypeRefParserRuleCall_4_0()); }
		ruleTypeRef
		{ after(grammarAccess.getPropertyAccess().getTypeRefTypeRefParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__InitAssignment_5_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPropertyAccess().getInitExpressionParserRuleCall_5_1_0()); }
		ruleExpression
		{ after(grammarAccess.getPropertyAccess().getInitExpressionParserRuleCall_5_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Property__AnnotationsAssignment_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPropertyAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getPropertyAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getMessageAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getMessageAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__ParametersAssignment_3_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_0_0()); }
		ruleParameter
		{ after(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__ParametersAssignment_3_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_1_1_0()); }
		ruleParameter
		{ after(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Message__AnnotationsAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getMessageAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getMessageAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Parameter__NameAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getParameterAccess().getNameIDTerminalRuleCall_0_0()); }
		RULE_ID
		{ after(grammarAccess.getParameterAccess().getNameIDTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Parameter__TypeRefAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getParameterAccess().getTypeRefTypeRefParserRuleCall_2_0()); }
		ruleTypeRef
		{ after(grammarAccess.getParameterAccess().getTypeRefTypeRefParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Parameter__AnnotationsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getParameterAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getParameterAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__OptionalAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRequiredPortAccess().getOptionalOptionalKeyword_0_0()); }
		(
			{ before(grammarAccess.getRequiredPortAccess().getOptionalOptionalKeyword_0_0()); }
			'optional'
			{ after(grammarAccess.getRequiredPortAccess().getOptionalOptionalKeyword_0_0()); }
		)
		{ after(grammarAccess.getRequiredPortAccess().getOptionalOptionalKeyword_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__NameAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRequiredPortAccess().getNameIDTerminalRuleCall_3_0()); }
		RULE_ID
		{ after(grammarAccess.getRequiredPortAccess().getNameIDTerminalRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__AnnotationsAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRequiredPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getRequiredPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__SendsAssignment_6_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_1_0()); }
		(
			{ before(grammarAccess.getRequiredPortAccess().getSendsMessageIDTerminalRuleCall_6_0_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getRequiredPortAccess().getSendsMessageIDTerminalRuleCall_6_0_1_0_1()); }
		)
		{ after(grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__SendsAssignment_6_0_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_2_1_0()); }
		(
			{ before(grammarAccess.getRequiredPortAccess().getSendsMessageIDTerminalRuleCall_6_0_2_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getRequiredPortAccess().getSendsMessageIDTerminalRuleCall_6_0_2_1_0_1()); }
		)
		{ after(grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__ReceivesAssignment_6_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_1_0()); }
		(
			{ before(grammarAccess.getRequiredPortAccess().getReceivesMessageIDTerminalRuleCall_6_1_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getRequiredPortAccess().getReceivesMessageIDTerminalRuleCall_6_1_1_0_1()); }
		)
		{ after(grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RequiredPort__ReceivesAssignment_6_1_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_2_1_0()); }
		(
			{ before(grammarAccess.getRequiredPortAccess().getReceivesMessageIDTerminalRuleCall_6_1_2_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getRequiredPortAccess().getReceivesMessageIDTerminalRuleCall_6_1_2_1_0_1()); }
		)
		{ after(grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__NameAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProvidedPortAccess().getNameIDTerminalRuleCall_2_0()); }
		RULE_ID
		{ after(grammarAccess.getProvidedPortAccess().getNameIDTerminalRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__AnnotationsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProvidedPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getProvidedPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__SendsAssignment_5_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_1_0()); }
		(
			{ before(grammarAccess.getProvidedPortAccess().getSendsMessageIDTerminalRuleCall_5_0_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getProvidedPortAccess().getSendsMessageIDTerminalRuleCall_5_0_1_0_1()); }
		)
		{ after(grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__SendsAssignment_5_0_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_2_1_0()); }
		(
			{ before(grammarAccess.getProvidedPortAccess().getSendsMessageIDTerminalRuleCall_5_0_2_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getProvidedPortAccess().getSendsMessageIDTerminalRuleCall_5_0_2_1_0_1()); }
		)
		{ after(grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__ReceivesAssignment_5_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_1_0()); }
		(
			{ before(grammarAccess.getProvidedPortAccess().getReceivesMessageIDTerminalRuleCall_5_1_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getProvidedPortAccess().getReceivesMessageIDTerminalRuleCall_5_1_1_0_1()); }
		)
		{ after(grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ProvidedPort__ReceivesAssignment_5_1_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_2_1_0()); }
		(
			{ before(grammarAccess.getProvidedPortAccess().getReceivesMessageIDTerminalRuleCall_5_1_2_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getProvidedPortAccess().getReceivesMessageIDTerminalRuleCall_5_1_2_1_0_1()); }
		)
		{ after(grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__NameAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInternalPortAccess().getNameIDTerminalRuleCall_2_0()); }
		RULE_ID
		{ after(grammarAccess.getInternalPortAccess().getNameIDTerminalRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__AnnotationsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInternalPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getInternalPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__SendsAssignment_5_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_1_0()); }
		(
			{ before(grammarAccess.getInternalPortAccess().getSendsMessageIDTerminalRuleCall_5_0_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getInternalPortAccess().getSendsMessageIDTerminalRuleCall_5_0_1_0_1()); }
		)
		{ after(grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__SendsAssignment_5_0_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_2_1_0()); }
		(
			{ before(grammarAccess.getInternalPortAccess().getSendsMessageIDTerminalRuleCall_5_0_2_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getInternalPortAccess().getSendsMessageIDTerminalRuleCall_5_0_2_1_0_1()); }
		)
		{ after(grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__ReceivesAssignment_5_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_1_0()); }
		(
			{ before(grammarAccess.getInternalPortAccess().getReceivesMessageIDTerminalRuleCall_5_1_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getInternalPortAccess().getReceivesMessageIDTerminalRuleCall_5_1_1_0_1()); }
		)
		{ after(grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalPort__ReceivesAssignment_5_1_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_2_1_0()); }
		(
			{ before(grammarAccess.getInternalPortAccess().getReceivesMessageIDTerminalRuleCall_5_1_2_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getInternalPortAccess().getReceivesMessageIDTerminalRuleCall_5_1_2_1_0_1()); }
		)
		{ after(grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__NameAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getNameIDTerminalRuleCall_3_1_0()); }
		RULE_ID
		{ after(grammarAccess.getStateAccess().getNameIDTerminalRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__AnnotationsAssignment_3_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_2_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__PropertiesAssignment_3_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getPropertiesPropertyParserRuleCall_3_4_0()); }
		ruleProperty
		{ after(grammarAccess.getStateAccess().getPropertiesPropertyParserRuleCall_3_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__EntryAssignment_3_5_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getEntryActionParserRuleCall_3_5_2_0()); }
		ruleAction
		{ after(grammarAccess.getStateAccess().getEntryActionParserRuleCall_3_5_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__ExitAssignment_3_6_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getExitActionParserRuleCall_3_6_2_0()); }
		ruleAction
		{ after(grammarAccess.getStateAccess().getExitActionParserRuleCall_3_6_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__InternalAssignment_3_7_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getInternalInternalTransitionParserRuleCall_3_7_0_0()); }
		ruleInternalTransition
		{ after(grammarAccess.getStateAccess().getInternalInternalTransitionParserRuleCall_3_7_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__State__OutgoingAssignment_3_7_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateAccess().getOutgoingTransitionParserRuleCall_3_7_1_0()); }
		ruleTransition
		{ after(grammarAccess.getStateAccess().getOutgoingTransitionParserRuleCall_3_7_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTransitionAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getTransitionAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__TargetAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTransitionAccess().getTargetStateCrossReference_3_0()); }
		(
			{ before(grammarAccess.getTransitionAccess().getTargetStateIDTerminalRuleCall_3_0_1()); }
			RULE_ID
			{ after(grammarAccess.getTransitionAccess().getTargetStateIDTerminalRuleCall_3_0_1()); }
		)
		{ after(grammarAccess.getTransitionAccess().getTargetStateCrossReference_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__AnnotationsAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__EventAssignment_5_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTransitionAccess().getEventEventParserRuleCall_5_1_0()); }
		ruleEvent
		{ after(grammarAccess.getTransitionAccess().getEventEventParserRuleCall_5_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__GuardAssignment_6_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTransitionAccess().getGuardExpressionParserRuleCall_6_1_0()); }
		ruleExpression
		{ after(grammarAccess.getTransitionAccess().getGuardExpressionParserRuleCall_6_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Transition__ActionAssignment_7_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTransitionAccess().getActionActionParserRuleCall_7_1_0()); }
		ruleAction
		{ after(grammarAccess.getTransitionAccess().getActionActionParserRuleCall_7_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__NameAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInternalTransitionAccess().getNameIDTerminalRuleCall_2_0()); }
		RULE_ID
		{ after(grammarAccess.getInternalTransitionAccess().getNameIDTerminalRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__AnnotationsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInternalTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getInternalTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__EventAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInternalTransitionAccess().getEventEventParserRuleCall_4_1_0()); }
		ruleEvent
		{ after(grammarAccess.getInternalTransitionAccess().getEventEventParserRuleCall_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__GuardAssignment_5_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInternalTransitionAccess().getGuardExpressionParserRuleCall_5_1_0()); }
		ruleExpression
		{ after(grammarAccess.getInternalTransitionAccess().getGuardExpressionParserRuleCall_5_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__InternalTransition__ActionAssignment_6_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInternalTransitionAccess().getActionActionParserRuleCall_6_1_0()); }
		ruleAction
		{ after(grammarAccess.getInternalTransitionAccess().getActionActionParserRuleCall_6_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__NameAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getNameIDTerminalRuleCall_2_0()); }
		RULE_ID
		{ after(grammarAccess.getCompositeStateAccess().getNameIDTerminalRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__InitialAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getInitialStateCrossReference_4_0()); }
		(
			{ before(grammarAccess.getCompositeStateAccess().getInitialStateIDTerminalRuleCall_4_0_1()); }
			RULE_ID
			{ after(grammarAccess.getCompositeStateAccess().getInitialStateIDTerminalRuleCall_4_0_1()); }
		)
		{ after(grammarAccess.getCompositeStateAccess().getInitialStateCrossReference_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__HistoryAssignment_5_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getHistoryHistoryKeyword_5_1_0()); }
		(
			{ before(grammarAccess.getCompositeStateAccess().getHistoryHistoryKeyword_5_1_0()); }
			'history'
			{ after(grammarAccess.getCompositeStateAccess().getHistoryHistoryKeyword_5_1_0()); }
		)
		{ after(grammarAccess.getCompositeStateAccess().getHistoryHistoryKeyword_5_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__AnnotationsAssignment_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getCompositeStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__PropertiesAssignment_8
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getPropertiesPropertyParserRuleCall_8_0()); }
		ruleProperty
		{ after(grammarAccess.getCompositeStateAccess().getPropertiesPropertyParserRuleCall_8_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__EntryAssignment_9_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getEntryActionParserRuleCall_9_2_0()); }
		ruleAction
		{ after(grammarAccess.getCompositeStateAccess().getEntryActionParserRuleCall_9_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__ExitAssignment_10_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getExitActionParserRuleCall_10_2_0()); }
		ruleAction
		{ after(grammarAccess.getCompositeStateAccess().getExitActionParserRuleCall_10_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__SubstateAssignment_11_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getSubstateStateParserRuleCall_11_0_0()); }
		ruleState
		{ after(grammarAccess.getCompositeStateAccess().getSubstateStateParserRuleCall_11_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__InternalAssignment_11_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getInternalInternalTransitionParserRuleCall_11_1_0()); }
		ruleInternalTransition
		{ after(grammarAccess.getCompositeStateAccess().getInternalInternalTransitionParserRuleCall_11_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__OutgoingAssignment_11_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getOutgoingTransitionParserRuleCall_11_2_0()); }
		ruleTransition
		{ after(grammarAccess.getCompositeStateAccess().getOutgoingTransitionParserRuleCall_11_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__RegionAssignment_12_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getRegionRegionParserRuleCall_12_0_0()); }
		ruleRegion
		{ after(grammarAccess.getCompositeStateAccess().getRegionRegionParserRuleCall_12_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompositeState__SessionAssignment_12_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompositeStateAccess().getSessionSessionParserRuleCall_12_1_0()); }
		ruleSession
		{ after(grammarAccess.getCompositeStateAccess().getSessionSessionParserRuleCall_12_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getStateMachineAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__InitialAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getInitialStateCrossReference_3_0()); }
		(
			{ before(grammarAccess.getStateMachineAccess().getInitialStateIDTerminalRuleCall_3_0_1()); }
			RULE_ID
			{ after(grammarAccess.getStateMachineAccess().getInitialStateIDTerminalRuleCall_3_0_1()); }
		)
		{ after(grammarAccess.getStateMachineAccess().getInitialStateCrossReference_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__HistoryAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getHistoryHistoryKeyword_4_1_0()); }
		(
			{ before(grammarAccess.getStateMachineAccess().getHistoryHistoryKeyword_4_1_0()); }
			'history'
			{ after(grammarAccess.getStateMachineAccess().getHistoryHistoryKeyword_4_1_0()); }
		)
		{ after(grammarAccess.getStateMachineAccess().getHistoryHistoryKeyword_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__AnnotationsAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getStateMachineAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__PropertiesAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getPropertiesPropertyParserRuleCall_7_0()); }
		ruleProperty
		{ after(grammarAccess.getStateMachineAccess().getPropertiesPropertyParserRuleCall_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__EntryAssignment_8_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getEntryActionParserRuleCall_8_2_0()); }
		ruleAction
		{ after(grammarAccess.getStateMachineAccess().getEntryActionParserRuleCall_8_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__ExitAssignment_9_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getExitActionParserRuleCall_9_2_0()); }
		ruleAction
		{ after(grammarAccess.getStateMachineAccess().getExitActionParserRuleCall_9_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__SubstateAssignment_10_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getSubstateStateParserRuleCall_10_0_0()); }
		ruleState
		{ after(grammarAccess.getStateMachineAccess().getSubstateStateParserRuleCall_10_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__InternalAssignment_10_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getInternalInternalTransitionParserRuleCall_10_1_0()); }
		ruleInternalTransition
		{ after(grammarAccess.getStateMachineAccess().getInternalInternalTransitionParserRuleCall_10_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__RegionAssignment_11_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getRegionRegionParserRuleCall_11_0_0()); }
		ruleRegion
		{ after(grammarAccess.getStateMachineAccess().getRegionRegionParserRuleCall_11_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateMachine__SessionAssignment_11_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateMachineAccess().getSessionSessionParserRuleCall_11_1_0()); }
		ruleSession
		{ after(grammarAccess.getStateMachineAccess().getSessionSessionParserRuleCall_11_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSessionAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getSessionAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__MaxInstancesAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSessionAccess().getMaxInstancesAlternatives_2_1_0()); }
		(rule__Session__MaxInstancesAlternatives_2_1_0)
		{ after(grammarAccess.getSessionAccess().getMaxInstancesAlternatives_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__InitialAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSessionAccess().getInitialStateCrossReference_4_0()); }
		(
			{ before(grammarAccess.getSessionAccess().getInitialStateIDTerminalRuleCall_4_0_1()); }
			RULE_ID
			{ after(grammarAccess.getSessionAccess().getInitialStateIDTerminalRuleCall_4_0_1()); }
		)
		{ after(grammarAccess.getSessionAccess().getInitialStateCrossReference_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__AnnotationsAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSessionAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getSessionAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Session__SubstateAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSessionAccess().getSubstateStateParserRuleCall_7_0()); }
		ruleState
		{ after(grammarAccess.getSessionAccess().getSubstateStateParserRuleCall_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRegionAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getRegionAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__InitialAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRegionAccess().getInitialStateCrossReference_3_0()); }
		(
			{ before(grammarAccess.getRegionAccess().getInitialStateIDTerminalRuleCall_3_0_1()); }
			RULE_ID
			{ after(grammarAccess.getRegionAccess().getInitialStateIDTerminalRuleCall_3_0_1()); }
		)
		{ after(grammarAccess.getRegionAccess().getInitialStateCrossReference_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__HistoryAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRegionAccess().getHistoryHistoryKeyword_4_1_0()); }
		(
			{ before(grammarAccess.getRegionAccess().getHistoryHistoryKeyword_4_1_0()); }
			'history'
			{ after(grammarAccess.getRegionAccess().getHistoryHistoryKeyword_4_1_0()); }
		)
		{ after(grammarAccess.getRegionAccess().getHistoryHistoryKeyword_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__AnnotationsAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRegionAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getRegionAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Region__SubstateAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRegionAccess().getSubstateStateParserRuleCall_7_0()); }
		ruleState
		{ after(grammarAccess.getRegionAccess().getSubstateStateParserRuleCall_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__NameAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFinalStateAccess().getNameIDTerminalRuleCall_2_0()); }
		RULE_ID
		{ after(grammarAccess.getFinalStateAccess().getNameIDTerminalRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__AnnotationsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFinalStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getFinalStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FinalState__EntryAssignment_5_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFinalStateAccess().getEntryActionParserRuleCall_5_2_0()); }
		ruleAction
		{ after(grammarAccess.getFinalStateAccess().getEntryActionParserRuleCall_5_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__InitialAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateContainerAccess().getInitialStateCrossReference_3_1_0()); }
		(
			{ before(grammarAccess.getStateContainerAccess().getInitialStateIDTerminalRuleCall_3_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getStateContainerAccess().getInitialStateIDTerminalRuleCall_3_1_0_1()); }
		)
		{ after(grammarAccess.getStateContainerAccess().getInitialStateCrossReference_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__HistoryAssignment_3_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateContainerAccess().getHistoryHistoryKeyword_3_2_1_0()); }
		(
			{ before(grammarAccess.getStateContainerAccess().getHistoryHistoryKeyword_3_2_1_0()); }
			'history'
			{ after(grammarAccess.getStateContainerAccess().getHistoryHistoryKeyword_3_2_1_0()); }
		)
		{ after(grammarAccess.getStateContainerAccess().getHistoryHistoryKeyword_3_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StateContainer__SubstateAssignment_3_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStateContainerAccess().getSubstateStateParserRuleCall_3_4_0()); }
		ruleState
		{ after(grammarAccess.getStateContainerAccess().getSubstateStateParserRuleCall_3_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__NameAssignment_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getReceiveMessageAccess().getNameIDTerminalRuleCall_0_0_0()); }
		RULE_ID
		{ after(grammarAccess.getReceiveMessageAccess().getNameIDTerminalRuleCall_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__PortAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getReceiveMessageAccess().getPortPortCrossReference_1_0()); }
		(
			{ before(grammarAccess.getReceiveMessageAccess().getPortPortIDTerminalRuleCall_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getReceiveMessageAccess().getPortPortIDTerminalRuleCall_1_0_1()); }
		)
		{ after(grammarAccess.getReceiveMessageAccess().getPortPortCrossReference_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ReceiveMessage__MessageAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getReceiveMessageAccess().getMessageMessageCrossReference_3_0()); }
		(
			{ before(grammarAccess.getReceiveMessageAccess().getMessageMessageIDTerminalRuleCall_3_0_1()); }
			RULE_ID
			{ after(grammarAccess.getReceiveMessageAccess().getMessageMessageIDTerminalRuleCall_3_0_1()); }
		)
		{ after(grammarAccess.getReceiveMessageAccess().getMessageMessageCrossReference_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ActionBlock__ActionsAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getActionBlockAccess().getActionsActionParserRuleCall_2_0()); }
		ruleAction
		{ after(grammarAccess.getActionBlockAccess().getActionsActionParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternStatement__StatementAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExternStatementAccess().getStatementSTRING_EXTTerminalRuleCall_0_0()); }
		RULE_STRING_EXT
		{ after(grammarAccess.getExternStatementAccess().getStatementSTRING_EXTTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternStatement__SegmentsAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExternStatementAccess().getSegmentsExpressionParserRuleCall_1_1_0()); }
		ruleExpression
		{ after(grammarAccess.getExternStatementAccess().getSegmentsExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__ReadonlyAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLocalVariableAccess().getReadonlyReadonlyKeyword_0_0()); }
		(
			{ before(grammarAccess.getLocalVariableAccess().getReadonlyReadonlyKeyword_0_0()); }
			'readonly'
			{ after(grammarAccess.getLocalVariableAccess().getReadonlyReadonlyKeyword_0_0()); }
		)
		{ after(grammarAccess.getLocalVariableAccess().getReadonlyReadonlyKeyword_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__NameAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLocalVariableAccess().getNameIDTerminalRuleCall_2_0()); }
		RULE_ID
		{ after(grammarAccess.getLocalVariableAccess().getNameIDTerminalRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__TypeRefAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLocalVariableAccess().getTypeRefTypeRefParserRuleCall_4_0()); }
		ruleTypeRef
		{ after(grammarAccess.getLocalVariableAccess().getTypeRefTypeRefParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__InitAssignment_5_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLocalVariableAccess().getInitExpressionParserRuleCall_5_1_0()); }
		ruleExpression
		{ after(grammarAccess.getLocalVariableAccess().getInitExpressionParserRuleCall_5_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalVariable__AnnotationsAssignment_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLocalVariableAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getLocalVariableAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__PortAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSendActionAccess().getPortPortCrossReference_0_0()); }
		(
			{ before(grammarAccess.getSendActionAccess().getPortPortIDTerminalRuleCall_0_0_1()); }
			RULE_ID
			{ after(grammarAccess.getSendActionAccess().getPortPortIDTerminalRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getSendActionAccess().getPortPortCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__MessageAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSendActionAccess().getMessageMessageCrossReference_2_0()); }
		(
			{ before(grammarAccess.getSendActionAccess().getMessageMessageIDTerminalRuleCall_2_0_1()); }
			RULE_ID
			{ after(grammarAccess.getSendActionAccess().getMessageMessageIDTerminalRuleCall_2_0_1()); }
		)
		{ after(grammarAccess.getSendActionAccess().getMessageMessageCrossReference_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__ParametersAssignment_4_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_0_0()); }
		ruleExpression
		{ after(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SendAction__ParametersAssignment_4_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_1_1_0()); }
		ruleExpression
		{ after(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__PropertyAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVariableAssignmentAccess().getPropertyVariableCrossReference_0_0()); }
		(
			{ before(grammarAccess.getVariableAssignmentAccess().getPropertyVariableIDTerminalRuleCall_0_0_1()); }
			RULE_ID
			{ after(grammarAccess.getVariableAssignmentAccess().getPropertyVariableIDTerminalRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getVariableAssignmentAccess().getPropertyVariableCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__IndexAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVariableAssignmentAccess().getIndexExpressionParserRuleCall_1_1_0()); }
		ruleExpression
		{ after(grammarAccess.getVariableAssignmentAccess().getIndexExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VariableAssignment__ExpressionAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVariableAssignmentAccess().getExpressionExpressionParserRuleCall_3_0()); }
		ruleExpression
		{ after(grammarAccess.getVariableAssignmentAccess().getExpressionExpressionParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Increment__VarAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIncrementAccess().getVarVariableCrossReference_0_0()); }
		(
			{ before(grammarAccess.getIncrementAccess().getVarVariableIDTerminalRuleCall_0_0_1()); }
			RULE_ID
			{ after(grammarAccess.getIncrementAccess().getVarVariableIDTerminalRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getIncrementAccess().getVarVariableCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Decrement__VarAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getDecrementAccess().getVarVariableCrossReference_0_0()); }
		(
			{ before(grammarAccess.getDecrementAccess().getVarVariableIDTerminalRuleCall_0_0_1()); }
			RULE_ID
			{ after(grammarAccess.getDecrementAccess().getVarVariableIDTerminalRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getDecrementAccess().getVarVariableCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LoopAction__ConditionAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLoopActionAccess().getConditionExpressionParserRuleCall_2_0()); }
		ruleExpression
		{ after(grammarAccess.getLoopActionAccess().getConditionExpressionParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LoopAction__ActionAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLoopActionAccess().getActionActionParserRuleCall_4_0()); }
		ruleAction
		{ after(grammarAccess.getLoopActionAccess().getActionActionParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__ConditionAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConditionalActionAccess().getConditionExpressionParserRuleCall_2_0()); }
		ruleExpression
		{ after(grammarAccess.getConditionalActionAccess().getConditionExpressionParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__ActionAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConditionalActionAccess().getActionActionParserRuleCall_4_0()); }
		ruleAction
		{ after(grammarAccess.getConditionalActionAccess().getActionActionParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConditionalAction__ElseActionAssignment_5_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConditionalActionAccess().getElseActionActionParserRuleCall_5_1_0()); }
		ruleAction
		{ after(grammarAccess.getConditionalActionAccess().getElseActionActionParserRuleCall_5_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ReturnAction__ExpAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getReturnActionAccess().getExpExpressionParserRuleCall_1_0()); }
		ruleExpression
		{ after(grammarAccess.getReturnActionAccess().getExpExpressionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PrintAction__MsgAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrintActionAccess().getMsgExpressionParserRuleCall_1_0()); }
		ruleExpression
		{ after(grammarAccess.getPrintActionAccess().getMsgExpressionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ErrorAction__MsgAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getErrorActionAccess().getMsgExpressionParserRuleCall_1_0()); }
		ruleExpression
		{ after(grammarAccess.getErrorActionAccess().getMsgExpressionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StartSession__SessionAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStartSessionAccess().getSessionSessionCrossReference_1_0()); }
		(
			{ before(grammarAccess.getStartSessionAccess().getSessionSessionIDTerminalRuleCall_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getStartSessionAccess().getSessionSessionIDTerminalRuleCall_1_0_1()); }
		)
		{ after(grammarAccess.getStartSessionAccess().getSessionSessionCrossReference_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__FunctionAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionCallStatementAccess().getFunctionFunctionCrossReference_0_0()); }
		(
			{ before(grammarAccess.getFunctionCallStatementAccess().getFunctionFunctionIDTerminalRuleCall_0_0_1()); }
			RULE_ID
			{ after(grammarAccess.getFunctionCallStatementAccess().getFunctionFunctionIDTerminalRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getFunctionCallStatementAccess().getFunctionFunctionCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__ParametersAssignment_2_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_0_0()); }
		ruleExpression
		{ after(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallStatement__ParametersAssignment_2_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_1_1_0()); }
		ruleExpression
		{ after(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__OrExpression__RhsAssignment_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getOrExpressionAccess().getRhsAndExpressionParserRuleCall_1_2_0()); }
		ruleAndExpression
		{ after(grammarAccess.getOrExpressionAccess().getRhsAndExpressionParserRuleCall_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__AndExpression__RhsAssignment_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAndExpressionAccess().getRhsEqualityParserRuleCall_1_2_0()); }
		ruleEquality
		{ after(grammarAccess.getAndExpressionAccess().getRhsEqualityParserRuleCall_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__RhsAssignment_1_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_0_2_0()); }
		ruleComparaison
		{ after(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_0_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Equality__RhsAssignment_1_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_1_2_0()); }
		ruleComparaison
		{ after(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__RhsAssignment_1_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_0_2_0()); }
		ruleAddition
		{ after(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_0_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__RhsAssignment_1_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_1_2_0()); }
		ruleAddition
		{ after(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__RhsAssignment_1_2_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_2_2_0()); }
		ruleAddition
		{ after(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_2_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Comparaison__RhsAssignment_1_3_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_3_2_0()); }
		ruleAddition
		{ after(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_3_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__RhsAssignment_1_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_0_2_0()); }
		ruleMultiplication
		{ after(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_0_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Addition__RhsAssignment_1_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_1_2_0()); }
		ruleMultiplication
		{ after(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__RhsAssignment_1_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_0_2_0()); }
		ruleModulo
		{ after(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_0_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Multiplication__RhsAssignment_1_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_1_2_0()); }
		ruleModulo
		{ after(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Modulo__RhsAssignment_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getModuloAccess().getRhsExpressionParserRuleCall_1_2_0()); }
		ruleExpression
		{ after(grammarAccess.getModuloAccess().getRhsExpressionParserRuleCall_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__TermAssignment_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrimaryAccess().getTermPrimaryParserRuleCall_1_2_0()); }
		rulePrimary
		{ after(grammarAccess.getPrimaryAccess().getTermPrimaryParserRuleCall_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Primary__TermAssignment_2_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPrimaryAccess().getTermPrimaryParserRuleCall_2_2_0()); }
		rulePrimary
		{ after(grammarAccess.getPrimaryAccess().getTermPrimaryParserRuleCall_2_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ArrayIndexPostfix__IndexAssignment_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getArrayIndexPostfixAccess().getIndexExpressionParserRuleCall_1_2_0()); }
		ruleExpression
		{ after(grammarAccess.getArrayIndexPostfixAccess().getIndexExpressionParserRuleCall_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternExpression__ExpressionAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExternExpressionAccess().getExpressionSTRING_EXTTerminalRuleCall_0_0()); }
		RULE_STRING_EXT
		{ after(grammarAccess.getExternExpressionAccess().getExpressionSTRING_EXTTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternExpression__SegmentsAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExternExpressionAccess().getSegmentsExpressionParserRuleCall_1_1_0()); }
		ruleExpression
		{ after(grammarAccess.getExternExpressionAccess().getSegmentsExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumLiteralRef__EnumAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEnumLiteralRefAccess().getEnumEnumerationCrossReference_0_0()); }
		(
			{ before(grammarAccess.getEnumLiteralRefAccess().getEnumEnumerationIDTerminalRuleCall_0_0_1()); }
			RULE_ID
			{ after(grammarAccess.getEnumLiteralRefAccess().getEnumEnumerationIDTerminalRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getEnumLiteralRefAccess().getEnumEnumerationCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumLiteralRef__LiteralAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEnumLiteralRefAccess().getLiteralEnumerationLiteralCrossReference_2_0()); }
		(
			{ before(grammarAccess.getEnumLiteralRefAccess().getLiteralEnumerationLiteralIDTerminalRuleCall_2_0_1()); }
			RULE_ID
			{ after(grammarAccess.getEnumLiteralRefAccess().getLiteralEnumerationLiteralIDTerminalRuleCall_2_0_1()); }
		)
		{ after(grammarAccess.getEnumLiteralRefAccess().getLiteralEnumerationLiteralCrossReference_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__IntegerLiteral__IntValueAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIntegerLiteralAccess().getIntValueINTTerminalRuleCall_0()); }
		RULE_INT
		{ after(grammarAccess.getIntegerLiteralAccess().getIntValueINTTerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__BooleanLiteral__BoolValueAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getBooleanLiteralAccess().getBoolValueTrueKeyword_0_0()); }
		(
			{ before(grammarAccess.getBooleanLiteralAccess().getBoolValueTrueKeyword_0_0()); }
			'true'
			{ after(grammarAccess.getBooleanLiteralAccess().getBoolValueTrueKeyword_0_0()); }
		)
		{ after(grammarAccess.getBooleanLiteralAccess().getBoolValueTrueKeyword_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__StringLiteral__StringValueAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getStringLiteralAccess().getStringValueSTRING_LITTerminalRuleCall_0()); }
		RULE_STRING_LIT
		{ after(grammarAccess.getStringLiteralAccess().getStringValueSTRING_LITTerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__DoubleLiteral__DoubleValueAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getDoubleLiteralAccess().getDoubleValueFLOATTerminalRuleCall_0()); }
		RULE_FLOAT
		{ after(grammarAccess.getDoubleLiteralAccess().getDoubleValueFLOATTerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PropertyReference__PropertyAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPropertyReferenceAccess().getPropertyVariableCrossReference_0()); }
		(
			{ before(grammarAccess.getPropertyReferenceAccess().getPropertyVariableIDTerminalRuleCall_0_1()); }
			RULE_ID
			{ after(grammarAccess.getPropertyReferenceAccess().getPropertyVariableIDTerminalRuleCall_0_1()); }
		)
		{ after(grammarAccess.getPropertyReferenceAccess().getPropertyVariableCrossReference_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventReference__ReceiveMsgAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEventReferenceAccess().getReceiveMsgEventCrossReference_0_0()); }
		(
			{ before(grammarAccess.getEventReferenceAccess().getReceiveMsgEventIDTerminalRuleCall_0_0_1()); }
			RULE_ID
			{ after(grammarAccess.getEventReferenceAccess().getReceiveMsgEventIDTerminalRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getEventReferenceAccess().getReceiveMsgEventCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventReference__ParameterAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEventReferenceAccess().getParameterParameterCrossReference_2_0()); }
		(
			{ before(grammarAccess.getEventReferenceAccess().getParameterParameterIDTerminalRuleCall_2_0_1()); }
			RULE_ID
			{ after(grammarAccess.getEventReferenceAccess().getParameterParameterIDTerminalRuleCall_2_0_1()); }
		)
		{ after(grammarAccess.getEventReferenceAccess().getParameterParameterCrossReference_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__FunctionAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionCallExpressionAccess().getFunctionFunctionCrossReference_0_0()); }
		(
			{ before(grammarAccess.getFunctionCallExpressionAccess().getFunctionFunctionIDTerminalRuleCall_0_0_1()); }
			RULE_ID
			{ after(grammarAccess.getFunctionCallExpressionAccess().getFunctionFunctionIDTerminalRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getFunctionCallExpressionAccess().getFunctionFunctionCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__ParametersAssignment_2_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionCallExpressionAccess().getParametersExpressionParserRuleCall_2_0_0()); }
		ruleExpression
		{ after(grammarAccess.getFunctionCallExpressionAccess().getParametersExpressionParserRuleCall_2_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__FunctionCallExpression__ParametersAssignment_2_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getFunctionCallExpressionAccess().getParametersExpressionParserRuleCall_2_1_1_0()); }
		ruleExpression
		{ after(grammarAccess.getFunctionCallExpressionAccess().getParametersExpressionParserRuleCall_2_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConfigurationAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getConfigurationAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__AnnotationsAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConfigurationAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getConfigurationAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__InstancesAssignment_4_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConfigurationAccess().getInstancesInstanceParserRuleCall_4_0_0()); }
		ruleInstance
		{ after(grammarAccess.getConfigurationAccess().getInstancesInstanceParserRuleCall_4_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__ConnectorsAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConfigurationAccess().getConnectorsAbstractConnectorParserRuleCall_4_1_0()); }
		ruleAbstractConnector
		{ after(grammarAccess.getConfigurationAccess().getConnectorsAbstractConnectorParserRuleCall_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Configuration__PropassignsAssignment_4_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConfigurationAccess().getPropassignsConfigPropertyAssignParserRuleCall_4_2_0()); }
		ruleConfigPropertyAssign
		{ after(grammarAccess.getConfigurationAccess().getPropassignsConfigPropertyAssignParserRuleCall_4_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Instance__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInstanceAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getInstanceAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Instance__TypeAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInstanceAccess().getTypeThingCrossReference_3_0()); }
		(
			{ before(grammarAccess.getInstanceAccess().getTypeThingIDTerminalRuleCall_3_0_1()); }
			RULE_ID
			{ after(grammarAccess.getInstanceAccess().getTypeThingIDTerminalRuleCall_3_0_1()); }
		)
		{ after(grammarAccess.getInstanceAccess().getTypeThingCrossReference_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Instance__AnnotationsAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getInstanceAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getInstanceAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__InstanceAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConfigPropertyAssignAccess().getInstanceInstanceCrossReference_1_0()); }
		(
			{ before(grammarAccess.getConfigPropertyAssignAccess().getInstanceInstanceIDTerminalRuleCall_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getConfigPropertyAssignAccess().getInstanceInstanceIDTerminalRuleCall_1_0_1()); }
		)
		{ after(grammarAccess.getConfigPropertyAssignAccess().getInstanceInstanceCrossReference_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__PropertyAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConfigPropertyAssignAccess().getPropertyPropertyCrossReference_3_0()); }
		(
			{ before(grammarAccess.getConfigPropertyAssignAccess().getPropertyPropertyIDTerminalRuleCall_3_0_1()); }
			RULE_ID
			{ after(grammarAccess.getConfigPropertyAssignAccess().getPropertyPropertyIDTerminalRuleCall_3_0_1()); }
		)
		{ after(grammarAccess.getConfigPropertyAssignAccess().getPropertyPropertyCrossReference_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__IndexAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConfigPropertyAssignAccess().getIndexExpressionParserRuleCall_4_1_0()); }
		ruleExpression
		{ after(grammarAccess.getConfigPropertyAssignAccess().getIndexExpressionParserRuleCall_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__InitAssignment_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConfigPropertyAssignAccess().getInitExpressionParserRuleCall_6_0()); }
		ruleExpression
		{ after(grammarAccess.getConfigPropertyAssignAccess().getInitExpressionParserRuleCall_6_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ConfigPropertyAssign__AnnotationsAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConfigPropertyAssignAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getConfigPropertyAssignAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConnectorAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getConnectorAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__CliAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConnectorAccess().getCliInstanceCrossReference_2_0()); }
		(
			{ before(grammarAccess.getConnectorAccess().getCliInstanceIDTerminalRuleCall_2_0_1()); }
			RULE_ID
			{ after(grammarAccess.getConnectorAccess().getCliInstanceIDTerminalRuleCall_2_0_1()); }
		)
		{ after(grammarAccess.getConnectorAccess().getCliInstanceCrossReference_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__RequiredAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConnectorAccess().getRequiredRequiredPortCrossReference_4_0()); }
		(
			{ before(grammarAccess.getConnectorAccess().getRequiredRequiredPortIDTerminalRuleCall_4_0_1()); }
			RULE_ID
			{ after(grammarAccess.getConnectorAccess().getRequiredRequiredPortIDTerminalRuleCall_4_0_1()); }
		)
		{ after(grammarAccess.getConnectorAccess().getRequiredRequiredPortCrossReference_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__SrvAssignment_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConnectorAccess().getSrvInstanceCrossReference_6_0()); }
		(
			{ before(grammarAccess.getConnectorAccess().getSrvInstanceIDTerminalRuleCall_6_0_1()); }
			RULE_ID
			{ after(grammarAccess.getConnectorAccess().getSrvInstanceIDTerminalRuleCall_6_0_1()); }
		)
		{ after(grammarAccess.getConnectorAccess().getSrvInstanceCrossReference_6_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__ProvidedAssignment_8
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConnectorAccess().getProvidedProvidedPortCrossReference_8_0()); }
		(
			{ before(grammarAccess.getConnectorAccess().getProvidedProvidedPortIDTerminalRuleCall_8_0_1()); }
			RULE_ID
			{ after(grammarAccess.getConnectorAccess().getProvidedProvidedPortIDTerminalRuleCall_8_0_1()); }
		)
		{ after(grammarAccess.getConnectorAccess().getProvidedProvidedPortCrossReference_8_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Connector__AnnotationsAssignment_9
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getConnectorAccess().getAnnotationsPlatformAnnotationParserRuleCall_9_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getConnectorAccess().getAnnotationsPlatformAnnotationParserRuleCall_9_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExternalConnectorAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getExternalConnectorAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__InstAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExternalConnectorAccess().getInstInstanceCrossReference_2_0()); }
		(
			{ before(grammarAccess.getExternalConnectorAccess().getInstInstanceIDTerminalRuleCall_2_0_1()); }
			RULE_ID
			{ after(grammarAccess.getExternalConnectorAccess().getInstInstanceIDTerminalRuleCall_2_0_1()); }
		)
		{ after(grammarAccess.getExternalConnectorAccess().getInstInstanceCrossReference_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__PortAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExternalConnectorAccess().getPortPortCrossReference_4_0()); }
		(
			{ before(grammarAccess.getExternalConnectorAccess().getPortPortIDTerminalRuleCall_4_0_1()); }
			RULE_ID
			{ after(grammarAccess.getExternalConnectorAccess().getPortPortIDTerminalRuleCall_4_0_1()); }
		)
		{ after(grammarAccess.getExternalConnectorAccess().getPortPortCrossReference_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__ProtocolAssignment_6
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExternalConnectorAccess().getProtocolProtocolCrossReference_6_0()); }
		(
			{ before(grammarAccess.getExternalConnectorAccess().getProtocolProtocolIDTerminalRuleCall_6_0_1()); }
			RULE_ID
			{ after(grammarAccess.getExternalConnectorAccess().getProtocolProtocolIDTerminalRuleCall_6_0_1()); }
		)
		{ after(grammarAccess.getExternalConnectorAccess().getProtocolProtocolCrossReference_6_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExternalConnector__AnnotationsAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExternalConnectorAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0()); }
		rulePlatformAnnotation
		{ after(grammarAccess.getExternalConnectorAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_FLOAT : (('0'..'9')+ '.' ('0'..'9')* (('e'|'E') ('+'|'-')? ('0'..'9')+)?|'.' ('0'..'9')+ (('e'|'E') ('+'|'-')? ('0'..'9')+)?|('0'..'9')+ ('e'|'E') ('+'|'-')? ('0'..'9')+);

RULE_ANNOTATION_ID : '@' ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_STRING_EXT : '\'' ('\\' .|~(('\\'|'\'')))* '\'';

RULE_STRING_LIT : '"' ('\\' .|~(('\\'|'"')))* '"';

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;
