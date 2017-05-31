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
	superClass=AbstractInternalAntlrParser;
}

@lexer::header {
package org.thingml.xtext.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package org.thingml.xtext.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.thingml.xtext.services.ThingMLGrammarAccess;

}

@parser::members {

 	private ThingMLGrammarAccess grammarAccess;

    public InternalThingMLParser(TokenStream input, ThingMLGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }

    @Override
    protected String getFirstRuleName() {
    	return "ThingMLModel";
   	}

   	@Override
   	protected ThingMLGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}

}

@rulecatch {
    catch (RecognitionException re) {
        recover(input,re);
        appendSkippedTokens();
    }
}

// Entry rule entryRuleThingMLModel
entryRuleThingMLModel returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getThingMLModelRule()); }
	iv_ruleThingMLModel=ruleThingMLModel
	{ $current=$iv_ruleThingMLModel.current; }
	EOF;

// Rule ThingMLModel
ruleThingMLModel returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			otherlv_0='import'
			{
				newLeafNode(otherlv_0, grammarAccess.getThingMLModelAccess().getImportKeyword_0_0());
			}
			(
				(
					lv_importURI_1_0=RULE_STRING_LIT
					{
						newLeafNode(lv_importURI_1_0, grammarAccess.getThingMLModelAccess().getImportURISTRING_LITTerminalRuleCall_0_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getThingMLModelRule());
						}
						addWithLastConsumed(
							$current,
							"importURI",
							lv_importURI_1_0,
							"org.thingml.xtext.ThingML.STRING_LIT");
					}
				)
			)
		)*
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getThingMLModelAccess().getTypesTypeParserRuleCall_1_0_0());
					}
					lv_types_2_0=ruleType
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getThingMLModelRule());
						}
						add(
							$current,
							"types",
							lv_types_2_0,
							"org.thingml.xtext.ThingML.Type");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getThingMLModelAccess().getProtocolsProtocolParserRuleCall_1_1_0());
					}
					lv_protocols_3_0=ruleProtocol
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getThingMLModelRule());
						}
						add(
							$current,
							"protocols",
							lv_protocols_3_0,
							"org.thingml.xtext.ThingML.Protocol");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getThingMLModelAccess().getConfigsConfigurationParserRuleCall_1_2_0());
					}
					lv_configs_4_0=ruleConfiguration
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getThingMLModelRule());
						}
						add(
							$current,
							"configs",
							lv_configs_4_0,
							"org.thingml.xtext.ThingML.Configuration");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRulePlatformAnnotation
entryRulePlatformAnnotation returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPlatformAnnotationRule()); }
	iv_rulePlatformAnnotation=rulePlatformAnnotation
	{ $current=$iv_rulePlatformAnnotation.current; }
	EOF;

// Rule PlatformAnnotation
rulePlatformAnnotation returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_name_0_0=RULE_ANNOTATION_ID
				{
					newLeafNode(lv_name_0_0, grammarAccess.getPlatformAnnotationAccess().getNameANNOTATION_IDTerminalRuleCall_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPlatformAnnotationRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_0_0,
						"org.thingml.xtext.ThingML.ANNOTATION_ID");
				}
			)
		)
		(
			(
				lv_value_1_0=RULE_STRING_LIT
				{
					newLeafNode(lv_value_1_0, grammarAccess.getPlatformAnnotationAccess().getValueSTRING_LITTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPlatformAnnotationRule());
					}
					setWithLastConsumed(
						$current,
						"value",
						lv_value_1_0,
						"org.thingml.xtext.ThingML.STRING_LIT");
				}
			)
		)
	)
;

// Entry rule entryRuleVariable
entryRuleVariable returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getVariableRule()); }
	iv_ruleVariable=ruleVariable
	{ $current=$iv_ruleVariable.current; }
	EOF;

// Rule Variable
ruleVariable returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			otherlv_0='var'
			{
				newLeafNode(otherlv_0, grammarAccess.getVariableAccess().getVarKeyword_0_0());
			}
			(
				(
					lv_name_1_0=RULE_ID
					{
						newLeafNode(lv_name_1_0, grammarAccess.getVariableAccess().getNameIDTerminalRuleCall_0_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getVariableRule());
						}
						setWithLastConsumed(
							$current,
							"name",
							lv_name_1_0,
							"org.thingml.xtext.ThingML.ID");
					}
				)
			)
			otherlv_2=':'
			{
				newLeafNode(otherlv_2, grammarAccess.getVariableAccess().getColonKeyword_0_2());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVariableAccess().getTypeRefTypeRefParserRuleCall_0_3_0());
					}
					lv_typeRef_3_0=ruleTypeRef
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVariableRule());
						}
						set(
							$current,
							"typeRef",
							lv_typeRef_3_0,
							"org.thingml.xtext.ThingML.TypeRef");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		{
			newCompositeNode(grammarAccess.getVariableAccess().getLocalVariableParserRuleCall_1());
		}
		this_LocalVariable_4=ruleLocalVariable
		{
			$current = $this_LocalVariable_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getVariableAccess().getPropertyParserRuleCall_2());
		}
		this_Property_5=ruleProperty
		{
			$current = $this_Property_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getVariableAccess().getParameterParserRuleCall_3());
		}
		this_Parameter_6=ruleParameter
		{
			$current = $this_Parameter_6.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleTypeRef
entryRuleTypeRef returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeRefRule()); }
	iv_ruleTypeRef=ruleTypeRef
	{ $current=$iv_ruleTypeRef.current; }
	EOF;

// Rule TypeRef
ruleTypeRef returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTypeRefRule());
					}
				}
				otherlv_0=RULE_ID
				{
					newLeafNode(otherlv_0, grammarAccess.getTypeRefAccess().getTypeTypeCrossReference_0_0());
				}
			)
		)
		(
			(
				(
					lv_isArray_1_0='['
					{
						newLeafNode(lv_isArray_1_0, grammarAccess.getTypeRefAccess().getIsArrayLeftSquareBracketKeyword_1_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTypeRefRule());
						}
						setWithLastConsumed($current, "isArray", true, "[");
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getTypeRefAccess().getCardinalityExpressionParserRuleCall_1_1_0());
					}
					lv_cardinality_2_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTypeRefRule());
						}
						set(
							$current,
							"cardinality",
							lv_cardinality_2_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)?
			otherlv_3=']'
			{
				newLeafNode(otherlv_3, grammarAccess.getTypeRefAccess().getRightSquareBracketKeyword_1_2());
			}
		)?
	)
;

// Entry rule entryRuleType
entryRuleType returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeRule()); }
	iv_ruleType=ruleType
	{ $current=$iv_ruleType.current; }
	EOF;

// Rule Type
ruleType returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getTypeAccess().getPrimitiveTypeParserRuleCall_0());
		}
		this_PrimitiveType_0=rulePrimitiveType
		{
			$current = $this_PrimitiveType_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getObjectTypeParserRuleCall_1());
		}
		this_ObjectType_1=ruleObjectType
		{
			$current = $this_ObjectType_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getEnumerationParserRuleCall_2());
		}
		this_Enumeration_2=ruleEnumeration
		{
			$current = $this_Enumeration_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getThingParserRuleCall_3());
		}
		this_Thing_3=ruleThing
		{
			$current = $this_Thing_3.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRulePrimitiveType
entryRulePrimitiveType returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPrimitiveTypeRule()); }
	iv_rulePrimitiveType=rulePrimitiveType
	{ $current=$iv_rulePrimitiveType.current; }
	EOF;

// Rule PrimitiveType
rulePrimitiveType returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='datatype'
		{
			newLeafNode(otherlv_0, grammarAccess.getPrimitiveTypeAccess().getDatatypeKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getPrimitiveTypeAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPrimitiveTypeRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		otherlv_2='<'
		{
			newLeafNode(otherlv_2, grammarAccess.getPrimitiveTypeAccess().getLessThanSignKeyword_2());
		}
		(
			(
				lv_ByteSize_3_0=RULE_INT
				{
					newLeafNode(lv_ByteSize_3_0, grammarAccess.getPrimitiveTypeAccess().getByteSizeINTTerminalRuleCall_3_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPrimitiveTypeRule());
					}
					setWithLastConsumed(
						$current,
						"ByteSize",
						lv_ByteSize_3_0,
						"org.thingml.xtext.ThingML.INT");
				}
			)
		)
		otherlv_4='>'
		{
			newLeafNode(otherlv_4, grammarAccess.getPrimitiveTypeAccess().getGreaterThanSignKeyword_4());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
				}
				lv_annotations_5_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPrimitiveTypeRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_5_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			otherlv_6=';'
			{
				newLeafNode(otherlv_6, grammarAccess.getPrimitiveTypeAccess().getSemicolonKeyword_6());
			}
		)?
	)
;

// Entry rule entryRuleObjectType
entryRuleObjectType returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getObjectTypeRule()); }
	iv_ruleObjectType=ruleObjectType
	{ $current=$iv_ruleObjectType.current; }
	EOF;

// Rule ObjectType
ruleObjectType returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='object'
		{
			newLeafNode(otherlv_0, grammarAccess.getObjectTypeAccess().getObjectKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getObjectTypeAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getObjectTypeRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getObjectTypeAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
				}
				lv_annotations_2_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getObjectTypeRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_2_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			otherlv_3=';'
			{
				newLeafNode(otherlv_3, grammarAccess.getObjectTypeAccess().getSemicolonKeyword_3());
			}
		)?
	)
;

// Entry rule entryRuleEnumeration
entryRuleEnumeration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getEnumerationRule()); }
	iv_ruleEnumeration=ruleEnumeration
	{ $current=$iv_ruleEnumeration.current; }
	EOF;

// Rule Enumeration
ruleEnumeration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='enumeration'
		{
			newLeafNode(otherlv_0, grammarAccess.getEnumerationAccess().getEnumerationKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getEnumerationAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getEnumerationRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getEnumerationAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
				}
				lv_annotations_2_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getEnumerationRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_2_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_3='{'
		{
			newLeafNode(otherlv_3, grammarAccess.getEnumerationAccess().getLeftCurlyBracketKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getEnumerationAccess().getLiteralsEnumerationLiteralParserRuleCall_4_0());
				}
				lv_literals_4_0=ruleEnumerationLiteral
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getEnumerationRule());
					}
					add(
						$current,
						"literals",
						lv_literals_4_0,
						"org.thingml.xtext.ThingML.EnumerationLiteral");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_5='}'
		{
			newLeafNode(otherlv_5, grammarAccess.getEnumerationAccess().getRightCurlyBracketKeyword_5());
		}
	)
;

// Entry rule entryRuleEnumerationLiteral
entryRuleEnumerationLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getEnumerationLiteralRule()); }
	iv_ruleEnumerationLiteral=ruleEnumerationLiteral
	{ $current=$iv_ruleEnumerationLiteral.current; }
	EOF;

// Rule EnumerationLiteral
ruleEnumerationLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_name_0_0=RULE_ID
				{
					newLeafNode(lv_name_0_0, grammarAccess.getEnumerationLiteralAccess().getNameIDTerminalRuleCall_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getEnumerationLiteralRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_0_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getEnumerationLiteralAccess().getAnnotationsPlatformAnnotationParserRuleCall_1_0());
				}
				lv_annotations_1_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getEnumerationLiteralRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_1_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleThing
entryRuleThing returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getThingRule()); }
	iv_ruleThing=ruleThing
	{ $current=$iv_ruleThing.current; }
	EOF;

// Rule Thing
ruleThing returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='thing'
		{
			newLeafNode(otherlv_0, grammarAccess.getThingAccess().getThingKeyword_0());
		}
		(
			(
				lv_fragment_1_0='fragment'
				{
					newLeafNode(lv_fragment_1_0, grammarAccess.getThingAccess().getFragmentFragmentKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getThingRule());
					}
					setWithLastConsumed($current, "fragment", true, "fragment");
				}
			)
		)?
		(
			(
				lv_name_2_0=RULE_ID
				{
					newLeafNode(lv_name_2_0, grammarAccess.getThingAccess().getNameIDTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getThingRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_2_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		(
			otherlv_3='includes'
			{
				newLeafNode(otherlv_3, grammarAccess.getThingAccess().getIncludesKeyword_3_0());
			}
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getThingRule());
						}
					}
					otherlv_4=RULE_ID
					{
						newLeafNode(otherlv_4, grammarAccess.getThingAccess().getIncludesThingCrossReference_3_1_0());
					}
				)
			)
			(
				otherlv_5=','
				{
					newLeafNode(otherlv_5, grammarAccess.getThingAccess().getCommaKeyword_3_2_0());
				}
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getThingRule());
							}
						}
						otherlv_6=RULE_ID
						{
							newLeafNode(otherlv_6, grammarAccess.getThingAccess().getIncludesThingCrossReference_3_2_1_0());
						}
					)
				)
			)*
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getThingAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
				}
				lv_annotations_7_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getThingRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_7_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_8='{'
		{
			newLeafNode(otherlv_8, grammarAccess.getThingAccess().getLeftCurlyBracketKeyword_5());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getThingAccess().getMessagesMessageParserRuleCall_6_0_0());
					}
					lv_messages_9_0=ruleMessage
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getThingRule());
						}
						add(
							$current,
							"messages",
							lv_messages_9_0,
							"org.thingml.xtext.ThingML.Message");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getThingAccess().getPortsPortParserRuleCall_6_1_0());
					}
					lv_ports_10_0=rulePort
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getThingRule());
						}
						add(
							$current,
							"ports",
							lv_ports_10_0,
							"org.thingml.xtext.ThingML.Port");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getThingAccess().getPropertiesPropertyParserRuleCall_6_2_0());
					}
					lv_properties_11_0=ruleProperty
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getThingRule());
						}
						add(
							$current,
							"properties",
							lv_properties_11_0,
							"org.thingml.xtext.ThingML.Property");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getThingAccess().getFunctionsFunctionParserRuleCall_6_3_0());
					}
					lv_functions_12_0=ruleFunction
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getThingRule());
						}
						add(
							$current,
							"functions",
							lv_functions_12_0,
							"org.thingml.xtext.ThingML.Function");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getThingAccess().getAssignPropertyAssignParserRuleCall_6_4_0());
					}
					lv_assign_13_0=rulePropertyAssign
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getThingRule());
						}
						add(
							$current,
							"assign",
							lv_assign_13_0,
							"org.thingml.xtext.ThingML.PropertyAssign");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getThingAccess().getBehaviourStateMachineParserRuleCall_6_5_0());
					}
					lv_behaviour_14_0=ruleStateMachine
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getThingRule());
						}
						add(
							$current,
							"behaviour",
							lv_behaviour_14_0,
							"org.thingml.xtext.ThingML.StateMachine");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_15='}'
		{
			newLeafNode(otherlv_15, grammarAccess.getThingAccess().getRightCurlyBracketKeyword_7());
		}
	)
;

// Entry rule entryRulePropertyAssign
entryRulePropertyAssign returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPropertyAssignRule()); }
	iv_rulePropertyAssign=rulePropertyAssign
	{ $current=$iv_rulePropertyAssign.current; }
	EOF;

// Rule PropertyAssign
rulePropertyAssign returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='set'
		{
			newLeafNode(otherlv_0, grammarAccess.getPropertyAssignAccess().getSetKeyword_0());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPropertyAssignRule());
					}
				}
				otherlv_1=RULE_ID
				{
					newLeafNode(otherlv_1, grammarAccess.getPropertyAssignAccess().getPropertyPropertyCrossReference_1_0());
				}
			)
		)
		(
			otherlv_2='['
			{
				newLeafNode(otherlv_2, grammarAccess.getPropertyAssignAccess().getLeftSquareBracketKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getPropertyAssignAccess().getIndexExpressionParserRuleCall_2_1_0());
					}
					lv_index_3_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getPropertyAssignRule());
						}
						add(
							$current,
							"index",
							lv_index_3_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_4=']'
			{
				newLeafNode(otherlv_4, grammarAccess.getPropertyAssignAccess().getRightSquareBracketKeyword_2_2());
			}
		)*
		otherlv_5='='
		{
			newLeafNode(otherlv_5, grammarAccess.getPropertyAssignAccess().getEqualsSignKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getPropertyAssignAccess().getInitExpressionParserRuleCall_4_0());
				}
				lv_init_6_0=ruleExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyAssignRule());
					}
					set(
						$current,
						"init",
						lv_init_6_0,
						"org.thingml.xtext.ThingML.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getPropertyAssignAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
				}
				lv_annotations_7_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyAssignRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_7_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleProtocol
entryRuleProtocol returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getProtocolRule()); }
	iv_ruleProtocol=ruleProtocol
	{ $current=$iv_ruleProtocol.current; }
	EOF;

// Rule Protocol
ruleProtocol returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='protocol'
		{
			newLeafNode(otherlv_0, grammarAccess.getProtocolAccess().getProtocolKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getProtocolAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getProtocolRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getProtocolAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
				}
				lv_annotations_2_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getProtocolRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_2_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_3=';'
		{
			newLeafNode(otherlv_3, grammarAccess.getProtocolAccess().getSemicolonKeyword_3());
		}
	)
;

// Entry rule entryRuleFunction
entryRuleFunction returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getFunctionRule()); }
	iv_ruleFunction=ruleFunction
	{ $current=$iv_ruleFunction.current; }
	EOF;

// Rule Function
ruleFunction returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			otherlv_0='function'
			{
				newLeafNode(otherlv_0, grammarAccess.getFunctionAccess().getFunctionKeyword_0_0());
			}
			(
				(
					lv_name_1_0=RULE_ID
					{
						newLeafNode(lv_name_1_0, grammarAccess.getFunctionAccess().getNameIDTerminalRuleCall_0_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getFunctionRule());
						}
						setWithLastConsumed(
							$current,
							"name",
							lv_name_1_0,
							"org.thingml.xtext.ThingML.ID");
					}
				)
			)
			otherlv_2='('
			{
				newLeafNode(otherlv_2, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_0_2());
			}
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_0_3_0_0());
						}
						lv_parameters_3_0=ruleParameter
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getFunctionRule());
							}
							add(
								$current,
								"parameters",
								lv_parameters_3_0,
								"org.thingml.xtext.ThingML.Parameter");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_4=','
					{
						newLeafNode(otherlv_4, grammarAccess.getFunctionAccess().getCommaKeyword_0_3_1_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_0_3_1_1_0());
							}
							lv_parameters_5_0=ruleParameter
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getFunctionRule());
								}
								add(
									$current,
									"parameters",
									lv_parameters_5_0,
									"org.thingml.xtext.ThingML.Parameter");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
			)?
			otherlv_6=')'
			{
				newLeafNode(otherlv_6, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_0_4());
			}
			(
				otherlv_7=':'
				{
					newLeafNode(otherlv_7, grammarAccess.getFunctionAccess().getColonKeyword_0_5_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getFunctionAccess().getTypeRefTypeRefParserRuleCall_0_5_1_0());
						}
						lv_typeRef_8_0=ruleTypeRef
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getFunctionRule());
							}
							set(
								$current,
								"typeRef",
								lv_typeRef_8_0,
								"org.thingml.xtext.ThingML.TypeRef");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
			(
				(
					{
						newCompositeNode(grammarAccess.getFunctionAccess().getAnnotationsPlatformAnnotationParserRuleCall_0_6_0());
					}
					lv_annotations_9_0=rulePlatformAnnotation
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getFunctionRule());
						}
						add(
							$current,
							"annotations",
							lv_annotations_9_0,
							"org.thingml.xtext.ThingML.PlatformAnnotation");
						afterParserOrEnumRuleCall();
					}
				)
			)*
			(
				(
					{
						newCompositeNode(grammarAccess.getFunctionAccess().getBodyActionParserRuleCall_0_7_0());
					}
					lv_body_10_0=ruleAction
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getFunctionRule());
						}
						set(
							$current,
							"body",
							lv_body_10_0,
							"org.thingml.xtext.ThingML.Action");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		{
			newCompositeNode(grammarAccess.getFunctionAccess().getAbstractFunctionParserRuleCall_1());
		}
		this_AbstractFunction_11=ruleAbstractFunction
		{
			$current = $this_AbstractFunction_11.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleAbstractFunction
entryRuleAbstractFunction returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAbstractFunctionRule()); }
	iv_ruleAbstractFunction=ruleAbstractFunction
	{ $current=$iv_ruleAbstractFunction.current; }
	EOF;

// Rule AbstractFunction
ruleAbstractFunction returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_abstract_0_0='abstract'
				{
					newLeafNode(lv_abstract_0_0, grammarAccess.getAbstractFunctionAccess().getAbstractAbstractKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAbstractFunctionRule());
					}
					setWithLastConsumed($current, "abstract", true, "abstract");
				}
			)
		)
		otherlv_1='function'
		{
			newLeafNode(otherlv_1, grammarAccess.getAbstractFunctionAccess().getFunctionKeyword_1());
		}
		(
			(
				lv_name_2_0=RULE_ID
				{
					newLeafNode(lv_name_2_0, grammarAccess.getAbstractFunctionAccess().getNameIDTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAbstractFunctionRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_2_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		otherlv_3='('
		{
			newLeafNode(otherlv_3, grammarAccess.getAbstractFunctionAccess().getLeftParenthesisKeyword_3());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getAbstractFunctionAccess().getParametersParameterParserRuleCall_4_0_0());
					}
					lv_parameters_4_0=ruleParameter
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getAbstractFunctionRule());
						}
						add(
							$current,
							"parameters",
							lv_parameters_4_0,
							"org.thingml.xtext.ThingML.Parameter");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_5=','
				{
					newLeafNode(otherlv_5, grammarAccess.getAbstractFunctionAccess().getCommaKeyword_4_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAbstractFunctionAccess().getParametersParameterParserRuleCall_4_1_1_0());
						}
						lv_parameters_6_0=ruleParameter
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAbstractFunctionRule());
							}
							add(
								$current,
								"parameters",
								lv_parameters_6_0,
								"org.thingml.xtext.ThingML.Parameter");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_7=')'
		{
			newLeafNode(otherlv_7, grammarAccess.getAbstractFunctionAccess().getRightParenthesisKeyword_5());
		}
		(
			otherlv_8=':'
			{
				newLeafNode(otherlv_8, grammarAccess.getAbstractFunctionAccess().getColonKeyword_6_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getAbstractFunctionAccess().getTypeRefTypeRefParserRuleCall_6_1_0());
					}
					lv_typeRef_9_0=ruleTypeRef
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getAbstractFunctionRule());
						}
						set(
							$current,
							"typeRef",
							lv_typeRef_9_0,
							"org.thingml.xtext.ThingML.TypeRef");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getAbstractFunctionAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0());
				}
				lv_annotations_10_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getAbstractFunctionRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_10_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleProperty
entryRuleProperty returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPropertyRule()); }
	iv_ruleProperty=ruleProperty
	{ $current=$iv_ruleProperty.current; }
	EOF;

// Rule Property
ruleProperty returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_readonly_0_0='readonly'
				{
					newLeafNode(lv_readonly_0_0, grammarAccess.getPropertyAccess().getReadonlyReadonlyKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPropertyRule());
					}
					setWithLastConsumed($current, "readonly", true, "readonly");
				}
			)
		)?
		otherlv_1='property'
		{
			newLeafNode(otherlv_1, grammarAccess.getPropertyAccess().getPropertyKeyword_1());
		}
		(
			(
				lv_name_2_0=RULE_ID
				{
					newLeafNode(lv_name_2_0, grammarAccess.getPropertyAccess().getNameIDTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPropertyRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_2_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		otherlv_3=':'
		{
			newLeafNode(otherlv_3, grammarAccess.getPropertyAccess().getColonKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getPropertyAccess().getTypeRefTypeRefParserRuleCall_4_0());
				}
				lv_typeRef_4_0=ruleTypeRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyRule());
					}
					set(
						$current,
						"typeRef",
						lv_typeRef_4_0,
						"org.thingml.xtext.ThingML.TypeRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_5='='
			{
				newLeafNode(otherlv_5, grammarAccess.getPropertyAccess().getEqualsSignKeyword_5_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getPropertyAccess().getInitExpressionParserRuleCall_5_1_0());
					}
					lv_init_6_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getPropertyRule());
						}
						set(
							$current,
							"init",
							lv_init_6_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getPropertyAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
				}
				lv_annotations_7_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_7_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleMessage
entryRuleMessage returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getMessageRule()); }
	iv_ruleMessage=ruleMessage
	{ $current=$iv_ruleMessage.current; }
	EOF;

// Rule Message
ruleMessage returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='message'
		{
			newLeafNode(otherlv_0, grammarAccess.getMessageAccess().getMessageKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getMessageAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getMessageRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		otherlv_2='('
		{
			newLeafNode(otherlv_2, grammarAccess.getMessageAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_0_0());
					}
					lv_parameters_3_0=ruleParameter
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getMessageRule());
						}
						add(
							$current,
							"parameters",
							lv_parameters_3_0,
							"org.thingml.xtext.ThingML.Parameter");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=','
				{
					newLeafNode(otherlv_4, grammarAccess.getMessageAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_1_1_0());
						}
						lv_parameters_5_0=ruleParameter
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getMessageRule());
							}
							add(
								$current,
								"parameters",
								lv_parameters_5_0,
								"org.thingml.xtext.ThingML.Parameter");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_6=')'
		{
			newLeafNode(otherlv_6, grammarAccess.getMessageAccess().getRightParenthesisKeyword_4());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getMessageAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
				}
				lv_annotations_7_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getMessageRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_7_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			otherlv_8=';'
			{
				newLeafNode(otherlv_8, grammarAccess.getMessageAccess().getSemicolonKeyword_6());
			}
		)?
	)
;

// Entry rule entryRuleParameter
entryRuleParameter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getParameterRule()); }
	iv_ruleParameter=ruleParameter
	{ $current=$iv_ruleParameter.current; }
	EOF;

// Rule Parameter
ruleParameter returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_name_0_0=RULE_ID
				{
					newLeafNode(lv_name_0_0, grammarAccess.getParameterAccess().getNameIDTerminalRuleCall_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getParameterRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_0_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		otherlv_1=':'
		{
			newLeafNode(otherlv_1, grammarAccess.getParameterAccess().getColonKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getParameterAccess().getTypeRefTypeRefParserRuleCall_2_0());
				}
				lv_typeRef_2_0=ruleTypeRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getParameterRule());
					}
					set(
						$current,
						"typeRef",
						lv_typeRef_2_0,
						"org.thingml.xtext.ThingML.TypeRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getParameterAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
				}
				lv_annotations_3_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getParameterRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_3_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRulePort
entryRulePort returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPortRule()); }
	iv_rulePort=rulePort
	{ $current=$iv_rulePort.current; }
	EOF;

// Rule Port
rulePort returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getPortAccess().getRequiredPortParserRuleCall_0());
		}
		this_RequiredPort_0=ruleRequiredPort
		{
			$current = $this_RequiredPort_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPortAccess().getProvidedPortParserRuleCall_1());
		}
		this_ProvidedPort_1=ruleProvidedPort
		{
			$current = $this_ProvidedPort_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPortAccess().getInternalPortParserRuleCall_2());
		}
		this_InternalPort_2=ruleInternalPort
		{
			$current = $this_InternalPort_2.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleRequiredPort
entryRuleRequiredPort returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getRequiredPortRule()); }
	iv_ruleRequiredPort=ruleRequiredPort
	{ $current=$iv_ruleRequiredPort.current; }
	EOF;

// Rule RequiredPort
ruleRequiredPort returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_optional_0_0='optional'
				{
					newLeafNode(lv_optional_0_0, grammarAccess.getRequiredPortAccess().getOptionalOptionalKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getRequiredPortRule());
					}
					setWithLastConsumed($current, "optional", true, "optional");
				}
			)
		)?
		otherlv_1='required'
		{
			newLeafNode(otherlv_1, grammarAccess.getRequiredPortAccess().getRequiredKeyword_1());
		}
		otherlv_2='port'
		{
			newLeafNode(otherlv_2, grammarAccess.getRequiredPortAccess().getPortKeyword_2());
		}
		(
			(
				lv_name_3_0=RULE_ID
				{
					newLeafNode(lv_name_3_0, grammarAccess.getRequiredPortAccess().getNameIDTerminalRuleCall_3_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getRequiredPortRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_3_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getRequiredPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
				}
				lv_annotations_4_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getRequiredPortRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_4_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_5='{'
		{
			newLeafNode(otherlv_5, grammarAccess.getRequiredPortAccess().getLeftCurlyBracketKeyword_5());
		}
		(
			(
				otherlv_6='sends'
				{
					newLeafNode(otherlv_6, grammarAccess.getRequiredPortAccess().getSendsKeyword_6_0_0());
				}
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getRequiredPortRule());
							}
						}
						otherlv_7=RULE_ID
						{
							newLeafNode(otherlv_7, grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_1_0());
						}
					)
				)
				(
					otherlv_8=','
					{
						newLeafNode(otherlv_8, grammarAccess.getRequiredPortAccess().getCommaKeyword_6_0_2_0());
					}
					(
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getRequiredPortRule());
								}
							}
							otherlv_9=RULE_ID
							{
								newLeafNode(otherlv_9, grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_2_1_0());
							}
						)
					)
				)*
			)
			    |
			(
				otherlv_10='receives'
				{
					newLeafNode(otherlv_10, grammarAccess.getRequiredPortAccess().getReceivesKeyword_6_1_0());
				}
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getRequiredPortRule());
							}
						}
						otherlv_11=RULE_ID
						{
							newLeafNode(otherlv_11, grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_1_0());
						}
					)
				)
				(
					otherlv_12=','
					{
						newLeafNode(otherlv_12, grammarAccess.getRequiredPortAccess().getCommaKeyword_6_1_2_0());
					}
					(
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getRequiredPortRule());
								}
							}
							otherlv_13=RULE_ID
							{
								newLeafNode(otherlv_13, grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_2_1_0());
							}
						)
					)
				)*
			)
		)*
		otherlv_14='}'
		{
			newLeafNode(otherlv_14, grammarAccess.getRequiredPortAccess().getRightCurlyBracketKeyword_7());
		}
	)
;

// Entry rule entryRuleProvidedPort
entryRuleProvidedPort returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getProvidedPortRule()); }
	iv_ruleProvidedPort=ruleProvidedPort
	{ $current=$iv_ruleProvidedPort.current; }
	EOF;

// Rule ProvidedPort
ruleProvidedPort returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='provided'
		{
			newLeafNode(otherlv_0, grammarAccess.getProvidedPortAccess().getProvidedKeyword_0());
		}
		otherlv_1='port'
		{
			newLeafNode(otherlv_1, grammarAccess.getProvidedPortAccess().getPortKeyword_1());
		}
		(
			(
				lv_name_2_0=RULE_ID
				{
					newLeafNode(lv_name_2_0, grammarAccess.getProvidedPortAccess().getNameIDTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getProvidedPortRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_2_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getProvidedPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
				}
				lv_annotations_3_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getProvidedPortRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_3_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_4='{'
		{
			newLeafNode(otherlv_4, grammarAccess.getProvidedPortAccess().getLeftCurlyBracketKeyword_4());
		}
		(
			(
				otherlv_5='sends'
				{
					newLeafNode(otherlv_5, grammarAccess.getProvidedPortAccess().getSendsKeyword_5_0_0());
				}
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getProvidedPortRule());
							}
						}
						otherlv_6=RULE_ID
						{
							newLeafNode(otherlv_6, grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_1_0());
						}
					)
				)
				(
					otherlv_7=','
					{
						newLeafNode(otherlv_7, grammarAccess.getProvidedPortAccess().getCommaKeyword_5_0_2_0());
					}
					(
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getProvidedPortRule());
								}
							}
							otherlv_8=RULE_ID
							{
								newLeafNode(otherlv_8, grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_2_1_0());
							}
						)
					)
				)*
			)
			    |
			(
				otherlv_9='receives'
				{
					newLeafNode(otherlv_9, grammarAccess.getProvidedPortAccess().getReceivesKeyword_5_1_0());
				}
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getProvidedPortRule());
							}
						}
						otherlv_10=RULE_ID
						{
							newLeafNode(otherlv_10, grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_1_0());
						}
					)
				)
				(
					otherlv_11=','
					{
						newLeafNode(otherlv_11, grammarAccess.getProvidedPortAccess().getCommaKeyword_5_1_2_0());
					}
					(
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getProvidedPortRule());
								}
							}
							otherlv_12=RULE_ID
							{
								newLeafNode(otherlv_12, grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_2_1_0());
							}
						)
					)
				)*
			)
		)*
		otherlv_13='}'
		{
			newLeafNode(otherlv_13, grammarAccess.getProvidedPortAccess().getRightCurlyBracketKeyword_6());
		}
	)
;

// Entry rule entryRuleInternalPort
entryRuleInternalPort returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getInternalPortRule()); }
	iv_ruleInternalPort=ruleInternalPort
	{ $current=$iv_ruleInternalPort.current; }
	EOF;

// Rule InternalPort
ruleInternalPort returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='internal'
		{
			newLeafNode(otherlv_0, grammarAccess.getInternalPortAccess().getInternalKeyword_0());
		}
		otherlv_1='port'
		{
			newLeafNode(otherlv_1, grammarAccess.getInternalPortAccess().getPortKeyword_1());
		}
		(
			(
				lv_name_2_0=RULE_ID
				{
					newLeafNode(lv_name_2_0, grammarAccess.getInternalPortAccess().getNameIDTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getInternalPortRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_2_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getInternalPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
				}
				lv_annotations_3_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getInternalPortRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_3_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_4='{'
		{
			newLeafNode(otherlv_4, grammarAccess.getInternalPortAccess().getLeftCurlyBracketKeyword_4());
		}
		(
			(
				otherlv_5='sends'
				{
					newLeafNode(otherlv_5, grammarAccess.getInternalPortAccess().getSendsKeyword_5_0_0());
				}
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getInternalPortRule());
							}
						}
						otherlv_6=RULE_ID
						{
							newLeafNode(otherlv_6, grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_1_0());
						}
					)
				)
				(
					otherlv_7=','
					{
						newLeafNode(otherlv_7, grammarAccess.getInternalPortAccess().getCommaKeyword_5_0_2_0());
					}
					(
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getInternalPortRule());
								}
							}
							otherlv_8=RULE_ID
							{
								newLeafNode(otherlv_8, grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_2_1_0());
							}
						)
					)
				)*
			)
			    |
			(
				otherlv_9='receives'
				{
					newLeafNode(otherlv_9, grammarAccess.getInternalPortAccess().getReceivesKeyword_5_1_0());
				}
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getInternalPortRule());
							}
						}
						otherlv_10=RULE_ID
						{
							newLeafNode(otherlv_10, grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_1_0());
						}
					)
				)
				(
					otherlv_11=','
					{
						newLeafNode(otherlv_11, grammarAccess.getInternalPortAccess().getCommaKeyword_5_1_2_0());
					}
					(
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getInternalPortRule());
								}
							}
							otherlv_12=RULE_ID
							{
								newLeafNode(otherlv_12, grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_2_1_0());
							}
						)
					)
				)*
			)
		)*
		otherlv_13='}'
		{
			newLeafNode(otherlv_13, grammarAccess.getInternalPortAccess().getRightCurlyBracketKeyword_6());
		}
	)
;

// Entry rule entryRuleState
entryRuleState returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getStateRule()); }
	iv_ruleState=ruleState
	{ $current=$iv_ruleState.current; }
	EOF;

// Rule State
ruleState returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getStateAccess().getStateMachineParserRuleCall_0());
		}
		this_StateMachine_0=ruleStateMachine
		{
			$current = $this_StateMachine_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getStateAccess().getFinalStateParserRuleCall_1());
		}
		this_FinalState_1=ruleFinalState
		{
			$current = $this_FinalState_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getStateAccess().getCompositeStateParserRuleCall_2());
		}
		this_CompositeState_2=ruleCompositeState
		{
			$current = $this_CompositeState_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			otherlv_3='state'
			{
				newLeafNode(otherlv_3, grammarAccess.getStateAccess().getStateKeyword_3_0());
			}
			(
				(
					lv_name_4_0=RULE_ID
					{
						newLeafNode(lv_name_4_0, grammarAccess.getStateAccess().getNameIDTerminalRuleCall_3_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getStateRule());
						}
						setWithLastConsumed(
							$current,
							"name",
							lv_name_4_0,
							"org.thingml.xtext.ThingML.ID");
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_2_0());
					}
					lv_annotations_5_0=rulePlatformAnnotation
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getStateRule());
						}
						add(
							$current,
							"annotations",
							lv_annotations_5_0,
							"org.thingml.xtext.ThingML.PlatformAnnotation");
						afterParserOrEnumRuleCall();
					}
				)
			)*
			otherlv_6='{'
			{
				newLeafNode(otherlv_6, grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_3_3());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getStateAccess().getPropertiesPropertyParserRuleCall_3_4_0());
					}
					lv_properties_7_0=ruleProperty
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getStateRule());
						}
						add(
							$current,
							"properties",
							lv_properties_7_0,
							"org.thingml.xtext.ThingML.Property");
						afterParserOrEnumRuleCall();
					}
				)
			)*
			(
				otherlv_8='on'
				{
					newLeafNode(otherlv_8, grammarAccess.getStateAccess().getOnKeyword_3_5_0());
				}
				otherlv_9='entry'
				{
					newLeafNode(otherlv_9, grammarAccess.getStateAccess().getEntryKeyword_3_5_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getStateAccess().getEntryActionParserRuleCall_3_5_2_0());
						}
						lv_entry_10_0=ruleAction
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getStateRule());
							}
							set(
								$current,
								"entry",
								lv_entry_10_0,
								"org.thingml.xtext.ThingML.Action");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
			(
				otherlv_11='on'
				{
					newLeafNode(otherlv_11, grammarAccess.getStateAccess().getOnKeyword_3_6_0());
				}
				otherlv_12='exit'
				{
					newLeafNode(otherlv_12, grammarAccess.getStateAccess().getExitKeyword_3_6_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getStateAccess().getExitActionParserRuleCall_3_6_2_0());
						}
						lv_exit_13_0=ruleAction
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getStateRule());
							}
							set(
								$current,
								"exit",
								lv_exit_13_0,
								"org.thingml.xtext.ThingML.Action");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getStateAccess().getInternalInternalTransitionParserRuleCall_3_7_0_0());
						}
						lv_internal_14_0=ruleInternalTransition
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getStateRule());
							}
							add(
								$current,
								"internal",
								lv_internal_14_0,
								"org.thingml.xtext.ThingML.InternalTransition");
							afterParserOrEnumRuleCall();
						}
					)
				)
				    |
				(
					(
						{
							newCompositeNode(grammarAccess.getStateAccess().getOutgoingTransitionParserRuleCall_3_7_1_0());
						}
						lv_outgoing_15_0=ruleTransition
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getStateRule());
							}
							add(
								$current,
								"outgoing",
								lv_outgoing_15_0,
								"org.thingml.xtext.ThingML.Transition");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			otherlv_16='}'
			{
				newLeafNode(otherlv_16, grammarAccess.getStateAccess().getRightCurlyBracketKeyword_3_8());
			}
		)
	)
;

// Entry rule entryRuleHandler
entryRuleHandler returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getHandlerRule()); }
	iv_ruleHandler=ruleHandler
	{ $current=$iv_ruleHandler.current; }
	EOF;

// Rule Handler
ruleHandler returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getHandlerAccess().getTransitionParserRuleCall_0());
		}
		this_Transition_0=ruleTransition
		{
			$current = $this_Transition_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getHandlerAccess().getInternalTransitionParserRuleCall_1());
		}
		this_InternalTransition_1=ruleInternalTransition
		{
			$current = $this_InternalTransition_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleTransition
entryRuleTransition returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTransitionRule()); }
	iv_ruleTransition=ruleTransition
	{ $current=$iv_ruleTransition.current; }
	EOF;

// Rule Transition
ruleTransition returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='transition'
		{
			newLeafNode(otherlv_0, grammarAccess.getTransitionAccess().getTransitionKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getTransitionAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTransitionRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)?
		otherlv_2='->'
		{
			newLeafNode(otherlv_2, grammarAccess.getTransitionAccess().getHyphenMinusGreaterThanSignKeyword_2());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTransitionRule());
					}
				}
				otherlv_3=RULE_ID
				{
					newLeafNode(otherlv_3, grammarAccess.getTransitionAccess().getTargetStateCrossReference_3_0());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
				}
				lv_annotations_4_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTransitionRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_4_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			otherlv_5='event'
			{
				newLeafNode(otherlv_5, grammarAccess.getTransitionAccess().getEventKeyword_5_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTransitionAccess().getEventEventParserRuleCall_5_1_0());
					}
					lv_event_6_0=ruleEvent
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTransitionRule());
						}
						add(
							$current,
							"event",
							lv_event_6_0,
							"org.thingml.xtext.ThingML.Event");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		(
			otherlv_7='guard'
			{
				newLeafNode(otherlv_7, grammarAccess.getTransitionAccess().getGuardKeyword_6_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTransitionAccess().getGuardExpressionParserRuleCall_6_1_0());
					}
					lv_guard_8_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTransitionRule());
						}
						set(
							$current,
							"guard",
							lv_guard_8_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			otherlv_9='action'
			{
				newLeafNode(otherlv_9, grammarAccess.getTransitionAccess().getActionKeyword_7_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTransitionAccess().getActionActionParserRuleCall_7_1_0());
					}
					lv_action_10_0=ruleAction
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTransitionRule());
						}
						set(
							$current,
							"action",
							lv_action_10_0,
							"org.thingml.xtext.ThingML.Action");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleInternalTransition
entryRuleInternalTransition returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getInternalTransitionRule()); }
	iv_ruleInternalTransition=ruleInternalTransition
	{ $current=$iv_ruleInternalTransition.current; }
	EOF;

// Rule InternalTransition
ruleInternalTransition returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getInternalTransitionAccess().getInternalTransitionAction_0(),
					$current);
			}
		)
		otherlv_1='internal'
		{
			newLeafNode(otherlv_1, grammarAccess.getInternalTransitionAccess().getInternalKeyword_1());
		}
		(
			(
				lv_name_2_0=RULE_ID
				{
					newLeafNode(lv_name_2_0, grammarAccess.getInternalTransitionAccess().getNameIDTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getInternalTransitionRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_2_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getInternalTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
				}
				lv_annotations_3_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getInternalTransitionRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_3_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			otherlv_4='event'
			{
				newLeafNode(otherlv_4, grammarAccess.getInternalTransitionAccess().getEventKeyword_4_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getInternalTransitionAccess().getEventEventParserRuleCall_4_1_0());
					}
					lv_event_5_0=ruleEvent
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getInternalTransitionRule());
						}
						add(
							$current,
							"event",
							lv_event_5_0,
							"org.thingml.xtext.ThingML.Event");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		(
			otherlv_6='guard'
			{
				newLeafNode(otherlv_6, grammarAccess.getInternalTransitionAccess().getGuardKeyword_5_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getInternalTransitionAccess().getGuardExpressionParserRuleCall_5_1_0());
					}
					lv_guard_7_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getInternalTransitionRule());
						}
						set(
							$current,
							"guard",
							lv_guard_7_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			otherlv_8='action'
			{
				newLeafNode(otherlv_8, grammarAccess.getInternalTransitionAccess().getActionKeyword_6_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getInternalTransitionAccess().getActionActionParserRuleCall_6_1_0());
					}
					lv_action_9_0=ruleAction
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getInternalTransitionRule());
						}
						set(
							$current,
							"action",
							lv_action_9_0,
							"org.thingml.xtext.ThingML.Action");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleCompositeState
entryRuleCompositeState returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getCompositeStateRule()); }
	iv_ruleCompositeState=ruleCompositeState
	{ $current=$iv_ruleCompositeState.current; }
	EOF;

// Rule CompositeState
ruleCompositeState returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='composite'
		{
			newLeafNode(otherlv_0, grammarAccess.getCompositeStateAccess().getCompositeKeyword_0());
		}
		otherlv_1='state'
		{
			newLeafNode(otherlv_1, grammarAccess.getCompositeStateAccess().getStateKeyword_1());
		}
		(
			(
				lv_name_2_0=RULE_ID
				{
					newLeafNode(lv_name_2_0, grammarAccess.getCompositeStateAccess().getNameIDTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getCompositeStateRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_2_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		otherlv_3='init'
		{
			newLeafNode(otherlv_3, grammarAccess.getCompositeStateAccess().getInitKeyword_3());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getCompositeStateRule());
					}
				}
				otherlv_4=RULE_ID
				{
					newLeafNode(otherlv_4, grammarAccess.getCompositeStateAccess().getInitialStateCrossReference_4_0());
				}
			)
		)
		(
			otherlv_5='keeps'
			{
				newLeafNode(otherlv_5, grammarAccess.getCompositeStateAccess().getKeepsKeyword_5_0());
			}
			(
				(
					lv_history_6_0='history'
					{
						newLeafNode(lv_history_6_0, grammarAccess.getCompositeStateAccess().getHistoryHistoryKeyword_5_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCompositeStateRule());
						}
						setWithLastConsumed($current, "history", true, "history");
					}
				)
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getCompositeStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
				}
				lv_annotations_7_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCompositeStateRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_7_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_8='{'
		{
			newLeafNode(otherlv_8, grammarAccess.getCompositeStateAccess().getLeftCurlyBracketKeyword_7());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getCompositeStateAccess().getPropertiesPropertyParserRuleCall_8_0());
				}
				lv_properties_9_0=ruleProperty
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCompositeStateRule());
					}
					add(
						$current,
						"properties",
						lv_properties_9_0,
						"org.thingml.xtext.ThingML.Property");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			otherlv_10='on'
			{
				newLeafNode(otherlv_10, grammarAccess.getCompositeStateAccess().getOnKeyword_9_0());
			}
			otherlv_11='entry'
			{
				newLeafNode(otherlv_11, grammarAccess.getCompositeStateAccess().getEntryKeyword_9_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getCompositeStateAccess().getEntryActionParserRuleCall_9_2_0());
					}
					lv_entry_12_0=ruleAction
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getCompositeStateRule());
						}
						set(
							$current,
							"entry",
							lv_entry_12_0,
							"org.thingml.xtext.ThingML.Action");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			otherlv_13='on'
			{
				newLeafNode(otherlv_13, grammarAccess.getCompositeStateAccess().getOnKeyword_10_0());
			}
			otherlv_14='exit'
			{
				newLeafNode(otherlv_14, grammarAccess.getCompositeStateAccess().getExitKeyword_10_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getCompositeStateAccess().getExitActionParserRuleCall_10_2_0());
					}
					lv_exit_15_0=ruleAction
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getCompositeStateRule());
						}
						set(
							$current,
							"exit",
							lv_exit_15_0,
							"org.thingml.xtext.ThingML.Action");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getCompositeStateAccess().getSubstateStateParserRuleCall_11_0_0());
					}
					lv_substate_16_0=ruleState
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getCompositeStateRule());
						}
						add(
							$current,
							"substate",
							lv_substate_16_0,
							"org.thingml.xtext.ThingML.State");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getCompositeStateAccess().getInternalInternalTransitionParserRuleCall_11_1_0());
					}
					lv_internal_17_0=ruleInternalTransition
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getCompositeStateRule());
						}
						add(
							$current,
							"internal",
							lv_internal_17_0,
							"org.thingml.xtext.ThingML.InternalTransition");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getCompositeStateAccess().getOutgoingTransitionParserRuleCall_11_2_0());
					}
					lv_outgoing_18_0=ruleTransition
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getCompositeStateRule());
						}
						add(
							$current,
							"outgoing",
							lv_outgoing_18_0,
							"org.thingml.xtext.ThingML.Transition");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getCompositeStateAccess().getRegionRegionParserRuleCall_12_0_0());
					}
					lv_region_19_0=ruleRegion
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getCompositeStateRule());
						}
						add(
							$current,
							"region",
							lv_region_19_0,
							"org.thingml.xtext.ThingML.Region");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getCompositeStateAccess().getSessionSessionParserRuleCall_12_1_0());
					}
					lv_session_20_0=ruleSession
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getCompositeStateRule());
						}
						add(
							$current,
							"session",
							lv_session_20_0,
							"org.thingml.xtext.ThingML.Session");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_21='}'
		{
			newLeafNode(otherlv_21, grammarAccess.getCompositeStateAccess().getRightCurlyBracketKeyword_13());
		}
	)
;

// Entry rule entryRuleStateMachine
entryRuleStateMachine returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getStateMachineRule()); }
	iv_ruleStateMachine=ruleStateMachine
	{ $current=$iv_ruleStateMachine.current; }
	EOF;

// Rule StateMachine
ruleStateMachine returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='statechart'
		{
			newLeafNode(otherlv_0, grammarAccess.getStateMachineAccess().getStatechartKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getStateMachineAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getStateMachineRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)?
		otherlv_2='init'
		{
			newLeafNode(otherlv_2, grammarAccess.getStateMachineAccess().getInitKeyword_2());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getStateMachineRule());
					}
				}
				otherlv_3=RULE_ID
				{
					newLeafNode(otherlv_3, grammarAccess.getStateMachineAccess().getInitialStateCrossReference_3_0());
				}
			)
		)
		(
			otherlv_4='keeps'
			{
				newLeafNode(otherlv_4, grammarAccess.getStateMachineAccess().getKeepsKeyword_4_0());
			}
			(
				(
					lv_history_5_0='history'
					{
						newLeafNode(lv_history_5_0, grammarAccess.getStateMachineAccess().getHistoryHistoryKeyword_4_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getStateMachineRule());
						}
						setWithLastConsumed($current, "history", true, "history");
					}
				)
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getStateMachineAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
				}
				lv_annotations_6_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getStateMachineRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_6_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_7='{'
		{
			newLeafNode(otherlv_7, grammarAccess.getStateMachineAccess().getLeftCurlyBracketKeyword_6());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getStateMachineAccess().getPropertiesPropertyParserRuleCall_7_0());
				}
				lv_properties_8_0=ruleProperty
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getStateMachineRule());
					}
					add(
						$current,
						"properties",
						lv_properties_8_0,
						"org.thingml.xtext.ThingML.Property");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			otherlv_9='on'
			{
				newLeafNode(otherlv_9, grammarAccess.getStateMachineAccess().getOnKeyword_8_0());
			}
			otherlv_10='entry'
			{
				newLeafNode(otherlv_10, grammarAccess.getStateMachineAccess().getEntryKeyword_8_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getStateMachineAccess().getEntryActionParserRuleCall_8_2_0());
					}
					lv_entry_11_0=ruleAction
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getStateMachineRule());
						}
						set(
							$current,
							"entry",
							lv_entry_11_0,
							"org.thingml.xtext.ThingML.Action");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			otherlv_12='on'
			{
				newLeafNode(otherlv_12, grammarAccess.getStateMachineAccess().getOnKeyword_9_0());
			}
			otherlv_13='exit'
			{
				newLeafNode(otherlv_13, grammarAccess.getStateMachineAccess().getExitKeyword_9_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getStateMachineAccess().getExitActionParserRuleCall_9_2_0());
					}
					lv_exit_14_0=ruleAction
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getStateMachineRule());
						}
						set(
							$current,
							"exit",
							lv_exit_14_0,
							"org.thingml.xtext.ThingML.Action");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getStateMachineAccess().getSubstateStateParserRuleCall_10_0_0());
					}
					lv_substate_15_0=ruleState
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getStateMachineRule());
						}
						add(
							$current,
							"substate",
							lv_substate_15_0,
							"org.thingml.xtext.ThingML.State");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getStateMachineAccess().getInternalInternalTransitionParserRuleCall_10_1_0());
					}
					lv_internal_16_0=ruleInternalTransition
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getStateMachineRule());
						}
						add(
							$current,
							"internal",
							lv_internal_16_0,
							"org.thingml.xtext.ThingML.InternalTransition");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getStateMachineAccess().getRegionRegionParserRuleCall_11_0_0());
					}
					lv_region_17_0=ruleRegion
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getStateMachineRule());
						}
						add(
							$current,
							"region",
							lv_region_17_0,
							"org.thingml.xtext.ThingML.Region");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getStateMachineAccess().getSessionSessionParserRuleCall_11_1_0());
					}
					lv_session_18_0=ruleSession
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getStateMachineRule());
						}
						add(
							$current,
							"session",
							lv_session_18_0,
							"org.thingml.xtext.ThingML.Session");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_19='}'
		{
			newLeafNode(otherlv_19, grammarAccess.getStateMachineAccess().getRightCurlyBracketKeyword_12());
		}
	)
;

// Entry rule entryRuleSession
entryRuleSession returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSessionRule()); }
	iv_ruleSession=ruleSession
	{ $current=$iv_ruleSession.current; }
	EOF;

// Rule Session
ruleSession returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='session'
		{
			newLeafNode(otherlv_0, grammarAccess.getSessionAccess().getSessionKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getSessionAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSessionRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		(
			otherlv_2='<'
			{
				newLeafNode(otherlv_2, grammarAccess.getSessionAccess().getLessThanSignKeyword_2_0());
			}
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getSessionAccess().getMaxInstancesIntegerLiteralParserRuleCall_2_1_0_0());
						}
						lv_maxInstances_3_1=ruleIntegerLiteral
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSessionRule());
							}
							set(
								$current,
								"maxInstances",
								lv_maxInstances_3_1,
								"org.thingml.xtext.ThingML.IntegerLiteral");
							afterParserOrEnumRuleCall();
						}
						    |
						{
							newCompositeNode(grammarAccess.getSessionAccess().getMaxInstancesPropertyReferenceParserRuleCall_2_1_0_1());
						}
						lv_maxInstances_3_2=rulePropertyReference
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSessionRule());
							}
							set(
								$current,
								"maxInstances",
								lv_maxInstances_3_2,
								"org.thingml.xtext.ThingML.PropertyReference");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			otherlv_4='>'
			{
				newLeafNode(otherlv_4, grammarAccess.getSessionAccess().getGreaterThanSignKeyword_2_2());
			}
		)?
		otherlv_5='init'
		{
			newLeafNode(otherlv_5, grammarAccess.getSessionAccess().getInitKeyword_3());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSessionRule());
					}
				}
				otherlv_6=RULE_ID
				{
					newLeafNode(otherlv_6, grammarAccess.getSessionAccess().getInitialStateCrossReference_4_0());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getSessionAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
				}
				lv_annotations_7_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSessionRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_7_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_8='{'
		{
			newLeafNode(otherlv_8, grammarAccess.getSessionAccess().getLeftCurlyBracketKeyword_6());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSessionAccess().getSubstateStateParserRuleCall_7_0());
				}
				lv_substate_9_0=ruleState
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSessionRule());
					}
					add(
						$current,
						"substate",
						lv_substate_9_0,
						"org.thingml.xtext.ThingML.State");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_10='}'
		{
			newLeafNode(otherlv_10, grammarAccess.getSessionAccess().getRightCurlyBracketKeyword_8());
		}
	)
;

// Entry rule entryRuleRegion
entryRuleRegion returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getRegionRule()); }
	iv_ruleRegion=ruleRegion
	{ $current=$iv_ruleRegion.current; }
	EOF;

// Rule Region
ruleRegion returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='region'
		{
			newLeafNode(otherlv_0, grammarAccess.getRegionAccess().getRegionKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getRegionAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getRegionRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)?
		otherlv_2='init'
		{
			newLeafNode(otherlv_2, grammarAccess.getRegionAccess().getInitKeyword_2());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getRegionRule());
					}
				}
				otherlv_3=RULE_ID
				{
					newLeafNode(otherlv_3, grammarAccess.getRegionAccess().getInitialStateCrossReference_3_0());
				}
			)
		)
		(
			otherlv_4='keeps'
			{
				newLeafNode(otherlv_4, grammarAccess.getRegionAccess().getKeepsKeyword_4_0());
			}
			(
				(
					lv_history_5_0='history'
					{
						newLeafNode(lv_history_5_0, grammarAccess.getRegionAccess().getHistoryHistoryKeyword_4_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getRegionRule());
						}
						setWithLastConsumed($current, "history", true, "history");
					}
				)
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getRegionAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
				}
				lv_annotations_6_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getRegionRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_6_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_7='{'
		{
			newLeafNode(otherlv_7, grammarAccess.getRegionAccess().getLeftCurlyBracketKeyword_6());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getRegionAccess().getSubstateStateParserRuleCall_7_0());
				}
				lv_substate_8_0=ruleState
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getRegionRule());
					}
					add(
						$current,
						"substate",
						lv_substate_8_0,
						"org.thingml.xtext.ThingML.State");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_9='}'
		{
			newLeafNode(otherlv_9, grammarAccess.getRegionAccess().getRightCurlyBracketKeyword_8());
		}
	)
;

// Entry rule entryRuleFinalState
entryRuleFinalState returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getFinalStateRule()); }
	iv_ruleFinalState=ruleFinalState
	{ $current=$iv_ruleFinalState.current; }
	EOF;

// Rule FinalState
ruleFinalState returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='final'
		{
			newLeafNode(otherlv_0, grammarAccess.getFinalStateAccess().getFinalKeyword_0());
		}
		otherlv_1='state'
		{
			newLeafNode(otherlv_1, grammarAccess.getFinalStateAccess().getStateKeyword_1());
		}
		(
			(
				lv_name_2_0=RULE_ID
				{
					newLeafNode(lv_name_2_0, grammarAccess.getFinalStateAccess().getNameIDTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFinalStateRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_2_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getFinalStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
				}
				lv_annotations_3_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFinalStateRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_3_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_4='{'
		{
			newLeafNode(otherlv_4, grammarAccess.getFinalStateAccess().getLeftCurlyBracketKeyword_4());
		}
		(
			otherlv_5='on'
			{
				newLeafNode(otherlv_5, grammarAccess.getFinalStateAccess().getOnKeyword_5_0());
			}
			otherlv_6='entry'
			{
				newLeafNode(otherlv_6, grammarAccess.getFinalStateAccess().getEntryKeyword_5_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getFinalStateAccess().getEntryActionParserRuleCall_5_2_0());
					}
					lv_entry_7_0=ruleAction
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getFinalStateRule());
						}
						set(
							$current,
							"entry",
							lv_entry_7_0,
							"org.thingml.xtext.ThingML.Action");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		otherlv_8='}'
		{
			newLeafNode(otherlv_8, grammarAccess.getFinalStateAccess().getRightCurlyBracketKeyword_6());
		}
	)
;

// Entry rule entryRuleStateContainer
entryRuleStateContainer returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getStateContainerRule()); }
	iv_ruleStateContainer=ruleStateContainer
	{ $current=$iv_ruleStateContainer.current; }
	EOF;

// Rule StateContainer
ruleStateContainer returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getStateContainerAccess().getCompositeStateParserRuleCall_0());
		}
		this_CompositeState_0=ruleCompositeState
		{
			$current = $this_CompositeState_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getStateContainerAccess().getRegionParserRuleCall_1());
		}
		this_Region_1=ruleRegion
		{
			$current = $this_Region_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getStateContainerAccess().getSessionParserRuleCall_2());
		}
		this_Session_2=ruleSession
		{
			$current = $this_Session_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			otherlv_3='keeps'
			{
				newLeafNode(otherlv_3, grammarAccess.getStateContainerAccess().getKeepsKeyword_3_0());
			}
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getStateContainerRule());
						}
					}
					otherlv_4=RULE_ID
					{
						newLeafNode(otherlv_4, grammarAccess.getStateContainerAccess().getInitialStateCrossReference_3_1_0());
					}
				)
			)
			(
				otherlv_5='keeps'
				{
					newLeafNode(otherlv_5, grammarAccess.getStateContainerAccess().getKeepsKeyword_3_2_0());
				}
				(
					(
						lv_history_6_0='history'
						{
							newLeafNode(lv_history_6_0, grammarAccess.getStateContainerAccess().getHistoryHistoryKeyword_3_2_1_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getStateContainerRule());
							}
							setWithLastConsumed($current, "history", true, "history");
						}
					)
				)
			)?
			otherlv_7='{'
			{
				newLeafNode(otherlv_7, grammarAccess.getStateContainerAccess().getLeftCurlyBracketKeyword_3_3());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getStateContainerAccess().getSubstateStateParserRuleCall_3_4_0());
					}
					lv_substate_8_0=ruleState
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getStateContainerRule());
						}
						add(
							$current,
							"substate",
							lv_substate_8_0,
							"org.thingml.xtext.ThingML.State");
						afterParserOrEnumRuleCall();
					}
				)
			)*
			otherlv_9='}'
			{
				newLeafNode(otherlv_9, grammarAccess.getStateContainerAccess().getRightCurlyBracketKeyword_3_5());
			}
		)
	)
;

// Entry rule entryRuleEvent
entryRuleEvent returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getEventRule()); }
	iv_ruleEvent=ruleEvent
	{ $current=$iv_ruleEvent.current; }
	EOF;

// Rule Event
ruleEvent returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	{
		newCompositeNode(grammarAccess.getEventAccess().getReceiveMessageParserRuleCall());
	}
	this_ReceiveMessage_0=ruleReceiveMessage
	{
		$current = $this_ReceiveMessage_0.current;
		afterParserOrEnumRuleCall();
	}
;

// Entry rule entryRuleReceiveMessage
entryRuleReceiveMessage returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getReceiveMessageRule()); }
	iv_ruleReceiveMessage=ruleReceiveMessage
	{ $current=$iv_ruleReceiveMessage.current; }
	EOF;

// Rule ReceiveMessage
ruleReceiveMessage returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				(
					lv_name_0_0=RULE_ID
					{
						newLeafNode(lv_name_0_0, grammarAccess.getReceiveMessageAccess().getNameIDTerminalRuleCall_0_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getReceiveMessageRule());
						}
						setWithLastConsumed(
							$current,
							"name",
							lv_name_0_0,
							"org.thingml.xtext.ThingML.ID");
					}
				)
			)
			otherlv_1=':'
			{
				newLeafNode(otherlv_1, grammarAccess.getReceiveMessageAccess().getColonKeyword_0_1());
			}
		)?
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getReceiveMessageRule());
					}
				}
				otherlv_2=RULE_ID
				{
					newLeafNode(otherlv_2, grammarAccess.getReceiveMessageAccess().getPortPortCrossReference_1_0());
				}
			)
		)
		otherlv_3='?'
		{
			newLeafNode(otherlv_3, grammarAccess.getReceiveMessageAccess().getQuestionMarkKeyword_2());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getReceiveMessageRule());
					}
				}
				otherlv_4=RULE_ID
				{
					newLeafNode(otherlv_4, grammarAccess.getReceiveMessageAccess().getMessageMessageCrossReference_3_0());
				}
			)
		)
	)
;

// Entry rule entryRuleAction
entryRuleAction returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getActionRule()); }
	iv_ruleAction=ruleAction
	{ $current=$iv_ruleAction.current; }
	EOF;

// Rule Action
ruleAction returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getActionAccess().getActionBlockParserRuleCall_0());
		}
		this_ActionBlock_0=ruleActionBlock
		{
			$current = $this_ActionBlock_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getExternStatementParserRuleCall_1());
		}
		this_ExternStatement_1=ruleExternStatement
		{
			$current = $this_ExternStatement_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getSendActionParserRuleCall_2());
		}
		this_SendAction_2=ruleSendAction
		{
			$current = $this_SendAction_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getVariableAssignmentParserRuleCall_3());
		}
		this_VariableAssignment_3=ruleVariableAssignment
		{
			$current = $this_VariableAssignment_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getIncrementParserRuleCall_4());
		}
		this_Increment_4=ruleIncrement
		{
			$current = $this_Increment_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getDecrementParserRuleCall_5());
		}
		this_Decrement_5=ruleDecrement
		{
			$current = $this_Decrement_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getLoopActionParserRuleCall_6());
		}
		this_LoopAction_6=ruleLoopAction
		{
			$current = $this_LoopAction_6.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getConditionalActionParserRuleCall_7());
		}
		this_ConditionalAction_7=ruleConditionalAction
		{
			$current = $this_ConditionalAction_7.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getReturnActionParserRuleCall_8());
		}
		this_ReturnAction_8=ruleReturnAction
		{
			$current = $this_ReturnAction_8.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getPrintActionParserRuleCall_9());
		}
		this_PrintAction_9=rulePrintAction
		{
			$current = $this_PrintAction_9.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getErrorActionParserRuleCall_10());
		}
		this_ErrorAction_10=ruleErrorAction
		{
			$current = $this_ErrorAction_10.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getStartSessionParserRuleCall_11());
		}
		this_StartSession_11=ruleStartSession
		{
			$current = $this_StartSession_11.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getFunctionCallStatementParserRuleCall_12());
		}
		this_FunctionCallStatement_12=ruleFunctionCallStatement
		{
			$current = $this_FunctionCallStatement_12.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getActionAccess().getLocalVariableParserRuleCall_13());
		}
		this_LocalVariable_13=ruleLocalVariable
		{
			$current = $this_LocalVariable_13.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleActionBlock
entryRuleActionBlock returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getActionBlockRule()); }
	iv_ruleActionBlock=ruleActionBlock
	{ $current=$iv_ruleActionBlock.current; }
	EOF;

// Rule ActionBlock
ruleActionBlock returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getActionBlockAccess().getActionBlockAction_0(),
					$current);
			}
		)
		otherlv_1='do'
		{
			newLeafNode(otherlv_1, grammarAccess.getActionBlockAccess().getDoKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getActionBlockAccess().getActionsActionParserRuleCall_2_0());
				}
				lv_actions_2_0=ruleAction
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getActionBlockRule());
					}
					add(
						$current,
						"actions",
						lv_actions_2_0,
						"org.thingml.xtext.ThingML.Action");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_3='end'
		{
			newLeafNode(otherlv_3, grammarAccess.getActionBlockAccess().getEndKeyword_3());
		}
	)
;

// Entry rule entryRuleExternStatement
entryRuleExternStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExternStatementRule()); }
	iv_ruleExternStatement=ruleExternStatement
	{ $current=$iv_ruleExternStatement.current; }
	EOF;

// Rule ExternStatement
ruleExternStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_statement_0_0=RULE_STRING_EXT
				{
					newLeafNode(lv_statement_0_0, grammarAccess.getExternStatementAccess().getStatementSTRING_EXTTerminalRuleCall_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getExternStatementRule());
					}
					setWithLastConsumed(
						$current,
						"statement",
						lv_statement_0_0,
						"org.thingml.xtext.ThingML.STRING_EXT");
				}
			)
		)
		(
			otherlv_1='&'
			{
				newLeafNode(otherlv_1, grammarAccess.getExternStatementAccess().getAmpersandKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getExternStatementAccess().getSegmentsExpressionParserRuleCall_1_1_0());
					}
					lv_segments_2_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getExternStatementRule());
						}
						add(
							$current,
							"segments",
							lv_segments_2_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleLocalVariable
entryRuleLocalVariable returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLocalVariableRule()); }
	iv_ruleLocalVariable=ruleLocalVariable
	{ $current=$iv_ruleLocalVariable.current; }
	EOF;

// Rule LocalVariable
ruleLocalVariable returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_readonly_0_0='readonly'
				{
					newLeafNode(lv_readonly_0_0, grammarAccess.getLocalVariableAccess().getReadonlyReadonlyKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getLocalVariableRule());
					}
					setWithLastConsumed($current, "readonly", true, "readonly");
				}
			)
		)?
		otherlv_1='var'
		{
			newLeafNode(otherlv_1, grammarAccess.getLocalVariableAccess().getVarKeyword_1());
		}
		(
			(
				lv_name_2_0=RULE_ID
				{
					newLeafNode(lv_name_2_0, grammarAccess.getLocalVariableAccess().getNameIDTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getLocalVariableRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_2_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		otherlv_3=':'
		{
			newLeafNode(otherlv_3, grammarAccess.getLocalVariableAccess().getColonKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getLocalVariableAccess().getTypeRefTypeRefParserRuleCall_4_0());
				}
				lv_typeRef_4_0=ruleTypeRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getLocalVariableRule());
					}
					set(
						$current,
						"typeRef",
						lv_typeRef_4_0,
						"org.thingml.xtext.ThingML.TypeRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_5='='
			{
				newLeafNode(otherlv_5, grammarAccess.getLocalVariableAccess().getEqualsSignKeyword_5_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getLocalVariableAccess().getInitExpressionParserRuleCall_5_1_0());
					}
					lv_init_6_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getLocalVariableRule());
						}
						set(
							$current,
							"init",
							lv_init_6_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getLocalVariableAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
				}
				lv_annotations_7_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getLocalVariableRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_7_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleSendAction
entryRuleSendAction returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSendActionRule()); }
	iv_ruleSendAction=ruleSendAction
	{ $current=$iv_ruleSendAction.current; }
	EOF;

// Rule SendAction
ruleSendAction returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSendActionRule());
					}
				}
				otherlv_0=RULE_ID
				{
					newLeafNode(otherlv_0, grammarAccess.getSendActionAccess().getPortPortCrossReference_0_0());
				}
			)
		)
		otherlv_1='!'
		{
			newLeafNode(otherlv_1, grammarAccess.getSendActionAccess().getExclamationMarkKeyword_1());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSendActionRule());
					}
				}
				otherlv_2=RULE_ID
				{
					newLeafNode(otherlv_2, grammarAccess.getSendActionAccess().getMessageMessageCrossReference_2_0());
				}
			)
		)
		otherlv_3='('
		{
			newLeafNode(otherlv_3, grammarAccess.getSendActionAccess().getLeftParenthesisKeyword_3());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_0_0());
					}
					lv_parameters_4_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSendActionRule());
						}
						add(
							$current,
							"parameters",
							lv_parameters_4_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_5=','
				{
					newLeafNode(otherlv_5, grammarAccess.getSendActionAccess().getCommaKeyword_4_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_1_1_0());
						}
						lv_parameters_6_0=ruleExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getSendActionRule());
							}
							add(
								$current,
								"parameters",
								lv_parameters_6_0,
								"org.thingml.xtext.ThingML.Expression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_7=')'
		{
			newLeafNode(otherlv_7, grammarAccess.getSendActionAccess().getRightParenthesisKeyword_5());
		}
	)
;

// Entry rule entryRuleVariableAssignment
entryRuleVariableAssignment returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getVariableAssignmentRule()); }
	iv_ruleVariableAssignment=ruleVariableAssignment
	{ $current=$iv_ruleVariableAssignment.current; }
	EOF;

// Rule VariableAssignment
ruleVariableAssignment returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getVariableAssignmentRule());
					}
				}
				otherlv_0=RULE_ID
				{
					newLeafNode(otherlv_0, grammarAccess.getVariableAssignmentAccess().getPropertyVariableCrossReference_0_0());
				}
			)
		)
		(
			otherlv_1='['
			{
				newLeafNode(otherlv_1, grammarAccess.getVariableAssignmentAccess().getLeftSquareBracketKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVariableAssignmentAccess().getIndexExpressionParserRuleCall_1_1_0());
					}
					lv_index_2_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVariableAssignmentRule());
						}
						add(
							$current,
							"index",
							lv_index_2_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_3=']'
			{
				newLeafNode(otherlv_3, grammarAccess.getVariableAssignmentAccess().getRightSquareBracketKeyword_1_2());
			}
		)*
		otherlv_4='='
		{
			newLeafNode(otherlv_4, grammarAccess.getVariableAssignmentAccess().getEqualsSignKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getVariableAssignmentAccess().getExpressionExpressionParserRuleCall_3_0());
				}
				lv_expression_5_0=ruleExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableAssignmentRule());
					}
					set(
						$current,
						"expression",
						lv_expression_5_0,
						"org.thingml.xtext.ThingML.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleIncrement
entryRuleIncrement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getIncrementRule()); }
	iv_ruleIncrement=ruleIncrement
	{ $current=$iv_ruleIncrement.current; }
	EOF;

// Rule Increment
ruleIncrement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getIncrementRule());
					}
				}
				otherlv_0=RULE_ID
				{
					newLeafNode(otherlv_0, grammarAccess.getIncrementAccess().getVarVariableCrossReference_0_0());
				}
			)
		)
		otherlv_1='++'
		{
			newLeafNode(otherlv_1, grammarAccess.getIncrementAccess().getPlusSignPlusSignKeyword_1());
		}
	)
;

// Entry rule entryRuleDecrement
entryRuleDecrement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getDecrementRule()); }
	iv_ruleDecrement=ruleDecrement
	{ $current=$iv_ruleDecrement.current; }
	EOF;

// Rule Decrement
ruleDecrement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getDecrementRule());
					}
				}
				otherlv_0=RULE_ID
				{
					newLeafNode(otherlv_0, grammarAccess.getDecrementAccess().getVarVariableCrossReference_0_0());
				}
			)
		)
		otherlv_1='--'
		{
			newLeafNode(otherlv_1, grammarAccess.getDecrementAccess().getHyphenMinusHyphenMinusKeyword_1());
		}
	)
;

// Entry rule entryRuleLoopAction
entryRuleLoopAction returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLoopActionRule()); }
	iv_ruleLoopAction=ruleLoopAction
	{ $current=$iv_ruleLoopAction.current; }
	EOF;

// Rule LoopAction
ruleLoopAction returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='while'
		{
			newLeafNode(otherlv_0, grammarAccess.getLoopActionAccess().getWhileKeyword_0());
		}
		otherlv_1='('
		{
			newLeafNode(otherlv_1, grammarAccess.getLoopActionAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getLoopActionAccess().getConditionExpressionParserRuleCall_2_0());
				}
				lv_condition_2_0=ruleExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getLoopActionRule());
					}
					set(
						$current,
						"condition",
						lv_condition_2_0,
						"org.thingml.xtext.ThingML.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=')'
		{
			newLeafNode(otherlv_3, grammarAccess.getLoopActionAccess().getRightParenthesisKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getLoopActionAccess().getActionActionParserRuleCall_4_0());
				}
				lv_action_4_0=ruleAction
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getLoopActionRule());
					}
					set(
						$current,
						"action",
						lv_action_4_0,
						"org.thingml.xtext.ThingML.Action");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleConditionalAction
entryRuleConditionalAction returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getConditionalActionRule()); }
	iv_ruleConditionalAction=ruleConditionalAction
	{ $current=$iv_ruleConditionalAction.current; }
	EOF;

// Rule ConditionalAction
ruleConditionalAction returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='if'
		{
			newLeafNode(otherlv_0, grammarAccess.getConditionalActionAccess().getIfKeyword_0());
		}
		otherlv_1='('
		{
			newLeafNode(otherlv_1, grammarAccess.getConditionalActionAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getConditionalActionAccess().getConditionExpressionParserRuleCall_2_0());
				}
				lv_condition_2_0=ruleExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getConditionalActionRule());
					}
					set(
						$current,
						"condition",
						lv_condition_2_0,
						"org.thingml.xtext.ThingML.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=')'
		{
			newLeafNode(otherlv_3, grammarAccess.getConditionalActionAccess().getRightParenthesisKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getConditionalActionAccess().getActionActionParserRuleCall_4_0());
				}
				lv_action_4_0=ruleAction
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getConditionalActionRule());
					}
					set(
						$current,
						"action",
						lv_action_4_0,
						"org.thingml.xtext.ThingML.Action");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_5='else'
			{
				newLeafNode(otherlv_5, grammarAccess.getConditionalActionAccess().getElseKeyword_5_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getConditionalActionAccess().getElseActionActionParserRuleCall_5_1_0());
					}
					lv_elseAction_6_0=ruleAction
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConditionalActionRule());
						}
						set(
							$current,
							"elseAction",
							lv_elseAction_6_0,
							"org.thingml.xtext.ThingML.Action");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleReturnAction
entryRuleReturnAction returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getReturnActionRule()); }
	iv_ruleReturnAction=ruleReturnAction
	{ $current=$iv_ruleReturnAction.current; }
	EOF;

// Rule ReturnAction
ruleReturnAction returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='return'
		{
			newLeafNode(otherlv_0, grammarAccess.getReturnActionAccess().getReturnKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getReturnActionAccess().getExpExpressionParserRuleCall_1_0());
				}
				lv_exp_1_0=ruleExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getReturnActionRule());
					}
					set(
						$current,
						"exp",
						lv_exp_1_0,
						"org.thingml.xtext.ThingML.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRulePrintAction
entryRulePrintAction returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPrintActionRule()); }
	iv_rulePrintAction=rulePrintAction
	{ $current=$iv_rulePrintAction.current; }
	EOF;

// Rule PrintAction
rulePrintAction returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='print'
		{
			newLeafNode(otherlv_0, grammarAccess.getPrintActionAccess().getPrintKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getPrintActionAccess().getMsgExpressionParserRuleCall_1_0());
				}
				lv_msg_1_0=ruleExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPrintActionRule());
					}
					set(
						$current,
						"msg",
						lv_msg_1_0,
						"org.thingml.xtext.ThingML.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleErrorAction
entryRuleErrorAction returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getErrorActionRule()); }
	iv_ruleErrorAction=ruleErrorAction
	{ $current=$iv_ruleErrorAction.current; }
	EOF;

// Rule ErrorAction
ruleErrorAction returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='error'
		{
			newLeafNode(otherlv_0, grammarAccess.getErrorActionAccess().getErrorKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getErrorActionAccess().getMsgExpressionParserRuleCall_1_0());
				}
				lv_msg_1_0=ruleExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getErrorActionRule());
					}
					set(
						$current,
						"msg",
						lv_msg_1_0,
						"org.thingml.xtext.ThingML.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleStartSession
entryRuleStartSession returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getStartSessionRule()); }
	iv_ruleStartSession=ruleStartSession
	{ $current=$iv_ruleStartSession.current; }
	EOF;

// Rule StartSession
ruleStartSession returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='fork'
		{
			newLeafNode(otherlv_0, grammarAccess.getStartSessionAccess().getForkKeyword_0());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getStartSessionRule());
					}
				}
				otherlv_1=RULE_ID
				{
					newLeafNode(otherlv_1, grammarAccess.getStartSessionAccess().getSessionSessionCrossReference_1_0());
				}
			)
		)
	)
;

// Entry rule entryRuleFunctionCallStatement
entryRuleFunctionCallStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getFunctionCallStatementRule()); }
	iv_ruleFunctionCallStatement=ruleFunctionCallStatement
	{ $current=$iv_ruleFunctionCallStatement.current; }
	EOF;

// Rule FunctionCallStatement
ruleFunctionCallStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionCallStatementRule());
					}
				}
				otherlv_0=RULE_ID
				{
					newLeafNode(otherlv_0, grammarAccess.getFunctionCallStatementAccess().getFunctionFunctionCrossReference_0_0());
				}
			)
		)
		otherlv_1='('
		{
			newLeafNode(otherlv_1, grammarAccess.getFunctionCallStatementAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_0_0());
					}
					lv_parameters_2_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getFunctionCallStatementRule());
						}
						add(
							$current,
							"parameters",
							lv_parameters_2_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_3=','
				{
					newLeafNode(otherlv_3, grammarAccess.getFunctionCallStatementAccess().getCommaKeyword_2_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_1_1_0());
						}
						lv_parameters_4_0=ruleExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getFunctionCallStatementRule());
							}
							add(
								$current,
								"parameters",
								lv_parameters_4_0,
								"org.thingml.xtext.ThingML.Expression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_5=')'
		{
			newLeafNode(otherlv_5, grammarAccess.getFunctionCallStatementAccess().getRightParenthesisKeyword_3());
		}
	)
;

// Entry rule entryRuleExpression
entryRuleExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExpressionRule()); }
	iv_ruleExpression=ruleExpression
	{ $current=$iv_ruleExpression.current; }
	EOF;

// Rule Expression
ruleExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	{
		newCompositeNode(grammarAccess.getExpressionAccess().getCastExpressionParserRuleCall());
	}
	this_CastExpression_0=ruleCastExpression
	{
		$current = $this_CastExpression_0.current;
		afterParserOrEnumRuleCall();
	}
;

// Entry rule entryRuleCastExpression
entryRuleCastExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getCastExpressionRule()); }
	iv_ruleCastExpression=ruleCastExpression
	{ $current=$iv_ruleCastExpression.current; }
	EOF;

// Rule CastExpression
ruleCastExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getCastExpressionAccess().getOrExpressionParserRuleCall_0());
		}
		this_OrExpression_0=ruleOrExpression
		{
			$current = $this_OrExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndSet(
						grammarAccess.getCastExpressionAccess().getCastExpressionTermAction_1_0(),
						$current);
				}
			)
			otherlv_2='as'
			{
				newLeafNode(otherlv_2, grammarAccess.getCastExpressionAccess().getAsKeyword_1_1());
			}
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCastExpressionRule());
						}
					}
					otherlv_3=RULE_ID
					{
						newLeafNode(otherlv_3, grammarAccess.getCastExpressionAccess().getTypeTypeCrossReference_1_2_0());
					}
				)
			)
			(
				(
					(
						lv_isArray_4_0='['
						{
							newLeafNode(lv_isArray_4_0, grammarAccess.getCastExpressionAccess().getIsArrayLeftSquareBracketKeyword_1_3_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getCastExpressionRule());
							}
							setWithLastConsumed($current, "isArray", true, "[");
						}
					)
				)
				otherlv_5=']'
				{
					newLeafNode(otherlv_5, grammarAccess.getCastExpressionAccess().getRightSquareBracketKeyword_1_3_1());
				}
			)?
		)?
	)
;

// Entry rule entryRuleOrExpression
entryRuleOrExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getOrExpressionRule()); }
	iv_ruleOrExpression=ruleOrExpression
	{ $current=$iv_ruleOrExpression.current; }
	EOF;

// Rule OrExpression
ruleOrExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getOrExpressionAccess().getAndExpressionParserRuleCall_0());
		}
		this_AndExpression_0=ruleAndExpression
		{
			$current = $this_AndExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndSet(
						grammarAccess.getOrExpressionAccess().getOrExpressionLhsAction_1_0(),
						$current);
				}
			)
			otherlv_2='or'
			{
				newLeafNode(otherlv_2, grammarAccess.getOrExpressionAccess().getOrKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getOrExpressionAccess().getRhsAndExpressionParserRuleCall_1_2_0());
					}
					lv_rhs_3_0=ruleAndExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getOrExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.thingml.xtext.ThingML.AndExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleAndExpression
entryRuleAndExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAndExpressionRule()); }
	iv_ruleAndExpression=ruleAndExpression
	{ $current=$iv_ruleAndExpression.current; }
	EOF;

// Rule AndExpression
ruleAndExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAndExpressionAccess().getEqualityParserRuleCall_0());
		}
		this_Equality_0=ruleEquality
		{
			$current = $this_Equality_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndSet(
						grammarAccess.getAndExpressionAccess().getAndExpressionLhsAction_1_0(),
						$current);
				}
			)
			otherlv_2='and'
			{
				newLeafNode(otherlv_2, grammarAccess.getAndExpressionAccess().getAndKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getAndExpressionAccess().getRhsEqualityParserRuleCall_1_2_0());
					}
					lv_rhs_3_0=ruleEquality
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getAndExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.thingml.xtext.ThingML.Equality");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleEquality
entryRuleEquality returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getEqualityRule()); }
	iv_ruleEquality=ruleEquality
	{ $current=$iv_ruleEquality.current; }
	EOF;

// Rule Equality
ruleEquality returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getEqualityAccess().getComparaisonParserRuleCall_0());
		}
		this_Comparaison_0=ruleComparaison
		{
			$current = $this_Comparaison_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getEqualityAccess().getEqualsExpressionLhsAction_1_0_0(),
							$current);
					}
				)
				otherlv_2='=='
				{
					newLeafNode(otherlv_2, grammarAccess.getEqualityAccess().getEqualsSignEqualsSignKeyword_1_0_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_0_2_0());
						}
						lv_rhs_3_0=ruleComparaison
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getEqualityRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_3_0,
								"org.thingml.xtext.ThingML.Comparaison");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getEqualityAccess().getNotEqualsExpressionLhsAction_1_1_0(),
							$current);
					}
				)
				otherlv_5='!='
				{
					newLeafNode(otherlv_5, grammarAccess.getEqualityAccess().getExclamationMarkEqualsSignKeyword_1_1_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_1_2_0());
						}
						lv_rhs_6_0=ruleComparaison
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getEqualityRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_6_0,
								"org.thingml.xtext.ThingML.Comparaison");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)*
	)
;

// Entry rule entryRuleComparaison
entryRuleComparaison returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getComparaisonRule()); }
	iv_ruleComparaison=ruleComparaison
	{ $current=$iv_ruleComparaison.current; }
	EOF;

// Rule Comparaison
ruleComparaison returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getComparaisonAccess().getAdditionParserRuleCall_0());
		}
		this_Addition_0=ruleAddition
		{
			$current = $this_Addition_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getComparaisonAccess().getGreaterExpressionLhsAction_1_0_0(),
							$current);
					}
				)
				otherlv_2='>'
				{
					newLeafNode(otherlv_2, grammarAccess.getComparaisonAccess().getGreaterThanSignKeyword_1_0_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_0_2_0());
						}
						lv_rhs_3_0=ruleAddition
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getComparaisonRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_3_0,
								"org.thingml.xtext.ThingML.Addition");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getComparaisonAccess().getLowerExpressionLhsAction_1_1_0(),
							$current);
					}
				)
				otherlv_5='<'
				{
					newLeafNode(otherlv_5, grammarAccess.getComparaisonAccess().getLessThanSignKeyword_1_1_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_1_2_0());
						}
						lv_rhs_6_0=ruleAddition
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getComparaisonRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_6_0,
								"org.thingml.xtext.ThingML.Addition");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getComparaisonAccess().getGreaterOrEqualExpressionLhsAction_1_2_0(),
							$current);
					}
				)
				otherlv_8='>='
				{
					newLeafNode(otherlv_8, grammarAccess.getComparaisonAccess().getGreaterThanSignEqualsSignKeyword_1_2_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_2_2_0());
						}
						lv_rhs_9_0=ruleAddition
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getComparaisonRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_9_0,
								"org.thingml.xtext.ThingML.Addition");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getComparaisonAccess().getLowerOrEqualExpressionLhsAction_1_3_0(),
							$current);
					}
				)
				otherlv_11='<='
				{
					newLeafNode(otherlv_11, grammarAccess.getComparaisonAccess().getLessThanSignEqualsSignKeyword_1_3_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_3_2_0());
						}
						lv_rhs_12_0=ruleAddition
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getComparaisonRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_12_0,
								"org.thingml.xtext.ThingML.Addition");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)*
	)
;

// Entry rule entryRuleAddition
entryRuleAddition returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAdditionRule()); }
	iv_ruleAddition=ruleAddition
	{ $current=$iv_ruleAddition.current; }
	EOF;

// Rule Addition
ruleAddition returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0());
		}
		this_Multiplication_0=ruleMultiplication
		{
			$current = $this_Multiplication_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAdditionAccess().getPlusExpressionLhsAction_1_0_0(),
							$current);
					}
				)
				otherlv_2='+'
				{
					newLeafNode(otherlv_2, grammarAccess.getAdditionAccess().getPlusSignKeyword_1_0_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_0_2_0());
						}
						lv_rhs_3_0=ruleMultiplication
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAdditionRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_3_0,
								"org.thingml.xtext.ThingML.Multiplication");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAdditionAccess().getMinusExpressionLhsAction_1_1_0(),
							$current);
					}
				)
				otherlv_5='-'
				{
					newLeafNode(otherlv_5, grammarAccess.getAdditionAccess().getHyphenMinusKeyword_1_1_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_1_2_0());
						}
						lv_rhs_6_0=ruleMultiplication
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAdditionRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_6_0,
								"org.thingml.xtext.ThingML.Multiplication");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)*
	)
;

// Entry rule entryRuleMultiplication
entryRuleMultiplication returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getMultiplicationRule()); }
	iv_ruleMultiplication=ruleMultiplication
	{ $current=$iv_ruleMultiplication.current; }
	EOF;

// Rule Multiplication
ruleMultiplication returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getMultiplicationAccess().getModuloParserRuleCall_0());
		}
		this_Modulo_0=ruleModulo
		{
			$current = $this_Modulo_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getMultiplicationAccess().getTimesExpressionLhsAction_1_0_0(),
							$current);
					}
				)
				otherlv_2='*'
				{
					newLeafNode(otherlv_2, grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_0_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_0_2_0());
						}
						lv_rhs_3_0=ruleModulo
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getMultiplicationRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_3_0,
								"org.thingml.xtext.ThingML.Modulo");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getMultiplicationAccess().getDivExpressionLhsAction_1_1_0(),
							$current);
					}
				)
				otherlv_5='/'
				{
					newLeafNode(otherlv_5, grammarAccess.getMultiplicationAccess().getSolidusKeyword_1_1_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_1_2_0());
						}
						lv_rhs_6_0=ruleModulo
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getMultiplicationRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_6_0,
								"org.thingml.xtext.ThingML.Modulo");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)*
	)
;

// Entry rule entryRuleModulo
entryRuleModulo returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getModuloRule()); }
	iv_ruleModulo=ruleModulo
	{ $current=$iv_ruleModulo.current; }
	EOF;

// Rule Modulo
ruleModulo returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getModuloAccess().getPrimaryParserRuleCall_0());
		}
		this_Primary_0=rulePrimary
		{
			$current = $this_Primary_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndSet(
						grammarAccess.getModuloAccess().getModExpressionLhsAction_1_0(),
						$current);
				}
			)
			otherlv_2='%'
			{
				newLeafNode(otherlv_2, grammarAccess.getModuloAccess().getPercentSignKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getModuloAccess().getRhsExpressionParserRuleCall_1_2_0());
					}
					lv_rhs_3_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getModuloRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRulePrimary
entryRulePrimary returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPrimaryRule()); }
	iv_rulePrimary=rulePrimary
	{ $current=$iv_rulePrimary.current; }
	EOF;

// Rule Primary
rulePrimary returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getPrimaryAccess().getExpressionGroupAction_0_0(),
						$current);
				}
			)
			otherlv_1='('
			{
				newLeafNode(otherlv_1, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_0_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getPrimaryAccess().getTermExpressionParserRuleCall_0_2_0());
					}
					lv_term_2_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getPrimaryRule());
						}
						set(
							$current,
							"term",
							lv_term_2_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_3=')'
			{
				newLeafNode(otherlv_3, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_0_3());
			}
		)
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getPrimaryAccess().getNotExpressionAction_1_0(),
						$current);
				}
			)
			otherlv_5='not'
			{
				newLeafNode(otherlv_5, grammarAccess.getPrimaryAccess().getNotKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getPrimaryAccess().getTermPrimaryParserRuleCall_1_2_0());
					}
					lv_term_6_0=rulePrimary
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getPrimaryRule());
						}
						set(
							$current,
							"term",
							lv_term_6_0,
							"org.thingml.xtext.ThingML.Primary");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getPrimaryAccess().getUnaryMinusAction_2_0(),
						$current);
				}
			)
			otherlv_8='-'
			{
				newLeafNode(otherlv_8, grammarAccess.getPrimaryAccess().getHyphenMinusKeyword_2_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getPrimaryAccess().getTermPrimaryParserRuleCall_2_2_0());
					}
					lv_term_9_0=rulePrimary
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getPrimaryRule());
						}
						set(
							$current,
							"term",
							lv_term_9_0,
							"org.thingml.xtext.ThingML.Primary");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryAccess().getArrayIndexPostfixParserRuleCall_3());
		}
		this_ArrayIndexPostfix_10=ruleArrayIndexPostfix
		{
			$current = $this_ArrayIndexPostfix_10.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleArrayIndexPostfix
entryRuleArrayIndexPostfix returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getArrayIndexPostfixRule()); }
	iv_ruleArrayIndexPostfix=ruleArrayIndexPostfix
	{ $current=$iv_ruleArrayIndexPostfix.current; }
	EOF;

// Rule ArrayIndexPostfix
ruleArrayIndexPostfix returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getArrayIndexPostfixAccess().getAtomicExpressionParserRuleCall_0());
		}
		this_AtomicExpression_0=ruleAtomicExpression
		{
			$current = $this_AtomicExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndSet(
						grammarAccess.getArrayIndexPostfixAccess().getArrayIndexArrayAction_1_0(),
						$current);
				}
			)
			otherlv_2='['
			{
				newLeafNode(otherlv_2, grammarAccess.getArrayIndexPostfixAccess().getLeftSquareBracketKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getArrayIndexPostfixAccess().getIndexExpressionParserRuleCall_1_2_0());
					}
					lv_index_3_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArrayIndexPostfixRule());
						}
						set(
							$current,
							"index",
							lv_index_3_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_4=']'
			{
				newLeafNode(otherlv_4, grammarAccess.getArrayIndexPostfixAccess().getRightSquareBracketKeyword_1_3());
			}
		)?
	)
;

// Entry rule entryRuleAtomicExpression
entryRuleAtomicExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAtomicExpressionRule()); }
	iv_ruleAtomicExpression=ruleAtomicExpression
	{ $current=$iv_ruleAtomicExpression.current; }
	EOF;

// Rule AtomicExpression
ruleAtomicExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getExternExpressionParserRuleCall_0());
		}
		this_ExternExpression_0=ruleExternExpression
		{
			$current = $this_ExternExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getEnumLiteralRefParserRuleCall_1());
		}
		this_EnumLiteralRef_1=ruleEnumLiteralRef
		{
			$current = $this_EnumLiteralRef_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getIntegerLiteralParserRuleCall_2());
		}
		this_IntegerLiteral_2=ruleIntegerLiteral
		{
			$current = $this_IntegerLiteral_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getBooleanLiteralParserRuleCall_3());
		}
		this_BooleanLiteral_3=ruleBooleanLiteral
		{
			$current = $this_BooleanLiteral_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getStringLiteralParserRuleCall_4());
		}
		this_StringLiteral_4=ruleStringLiteral
		{
			$current = $this_StringLiteral_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getDoubleLiteralParserRuleCall_5());
		}
		this_DoubleLiteral_5=ruleDoubleLiteral
		{
			$current = $this_DoubleLiteral_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getPropertyReferenceParserRuleCall_6());
		}
		this_PropertyReference_6=rulePropertyReference
		{
			$current = $this_PropertyReference_6.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getFunctionCallExpressionParserRuleCall_7());
		}
		this_FunctionCallExpression_7=ruleFunctionCallExpression
		{
			$current = $this_FunctionCallExpression_7.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getEventReferenceParserRuleCall_8());
		}
		this_EventReference_8=ruleEventReference
		{
			$current = $this_EventReference_8.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleExternExpression
entryRuleExternExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExternExpressionRule()); }
	iv_ruleExternExpression=ruleExternExpression
	{ $current=$iv_ruleExternExpression.current; }
	EOF;

// Rule ExternExpression
ruleExternExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_expression_0_0=RULE_STRING_EXT
				{
					newLeafNode(lv_expression_0_0, grammarAccess.getExternExpressionAccess().getExpressionSTRING_EXTTerminalRuleCall_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getExternExpressionRule());
					}
					setWithLastConsumed(
						$current,
						"expression",
						lv_expression_0_0,
						"org.thingml.xtext.ThingML.STRING_EXT");
				}
			)
		)
		(
			otherlv_1='&'
			{
				newLeafNode(otherlv_1, grammarAccess.getExternExpressionAccess().getAmpersandKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getExternExpressionAccess().getSegmentsExpressionParserRuleCall_1_1_0());
					}
					lv_segments_2_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getExternExpressionRule());
						}
						add(
							$current,
							"segments",
							lv_segments_2_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleEnumLiteralRef
entryRuleEnumLiteralRef returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getEnumLiteralRefRule()); }
	iv_ruleEnumLiteralRef=ruleEnumLiteralRef
	{ $current=$iv_ruleEnumLiteralRef.current; }
	EOF;

// Rule EnumLiteralRef
ruleEnumLiteralRef returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getEnumLiteralRefRule());
					}
				}
				otherlv_0=RULE_ID
				{
					newLeafNode(otherlv_0, grammarAccess.getEnumLiteralRefAccess().getEnumEnumerationCrossReference_0_0());
				}
			)
		)
		otherlv_1=':'
		{
			newLeafNode(otherlv_1, grammarAccess.getEnumLiteralRefAccess().getColonKeyword_1());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getEnumLiteralRefRule());
					}
				}
				otherlv_2=RULE_ID
				{
					newLeafNode(otherlv_2, grammarAccess.getEnumLiteralRefAccess().getLiteralEnumerationLiteralCrossReference_2_0());
				}
			)
		)
	)
;

// Entry rule entryRuleIntegerLiteral
entryRuleIntegerLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getIntegerLiteralRule()); }
	iv_ruleIntegerLiteral=ruleIntegerLiteral
	{ $current=$iv_ruleIntegerLiteral.current; }
	EOF;

// Rule IntegerLiteral
ruleIntegerLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_intValue_0_0=RULE_INT
			{
				newLeafNode(lv_intValue_0_0, grammarAccess.getIntegerLiteralAccess().getIntValueINTTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getIntegerLiteralRule());
				}
				setWithLastConsumed(
					$current,
					"intValue",
					lv_intValue_0_0,
					"org.thingml.xtext.ThingML.INT");
			}
		)
	)
;

// Entry rule entryRuleBooleanLiteral
entryRuleBooleanLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBooleanLiteralRule()); }
	iv_ruleBooleanLiteral=ruleBooleanLiteral
	{ $current=$iv_ruleBooleanLiteral.current; }
	EOF;

// Rule BooleanLiteral
ruleBooleanLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_boolValue_0_0='true'
				{
					newLeafNode(lv_boolValue_0_0, grammarAccess.getBooleanLiteralAccess().getBoolValueTrueKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getBooleanLiteralRule());
					}
					setWithLastConsumed($current, "boolValue", true, "true");
				}
			)
		)
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getBooleanLiteralAccess().getBooleanLiteralAction_1_0(),
						$current);
				}
			)
			otherlv_2='false'
			{
				newLeafNode(otherlv_2, grammarAccess.getBooleanLiteralAccess().getFalseKeyword_1_1());
			}
		)
	)
;

// Entry rule entryRuleStringLiteral
entryRuleStringLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getStringLiteralRule()); }
	iv_ruleStringLiteral=ruleStringLiteral
	{ $current=$iv_ruleStringLiteral.current; }
	EOF;

// Rule StringLiteral
ruleStringLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_stringValue_0_0=RULE_STRING_LIT
			{
				newLeafNode(lv_stringValue_0_0, grammarAccess.getStringLiteralAccess().getStringValueSTRING_LITTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getStringLiteralRule());
				}
				setWithLastConsumed(
					$current,
					"stringValue",
					lv_stringValue_0_0,
					"org.thingml.xtext.ThingML.STRING_LIT");
			}
		)
	)
;

// Entry rule entryRuleDoubleLiteral
entryRuleDoubleLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getDoubleLiteralRule()); }
	iv_ruleDoubleLiteral=ruleDoubleLiteral
	{ $current=$iv_ruleDoubleLiteral.current; }
	EOF;

// Rule DoubleLiteral
ruleDoubleLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_doubleValue_0_0=RULE_FLOAT
			{
				newLeafNode(lv_doubleValue_0_0, grammarAccess.getDoubleLiteralAccess().getDoubleValueFLOATTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getDoubleLiteralRule());
				}
				setWithLastConsumed(
					$current,
					"doubleValue",
					lv_doubleValue_0_0,
					"org.thingml.xtext.ThingML.FLOAT");
			}
		)
	)
;

// Entry rule entryRulePropertyReference
entryRulePropertyReference returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPropertyReferenceRule()); }
	iv_rulePropertyReference=rulePropertyReference
	{ $current=$iv_rulePropertyReference.current; }
	EOF;

// Rule PropertyReference
rulePropertyReference returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getPropertyReferenceRule());
				}
			}
			otherlv_0=RULE_ID
			{
				newLeafNode(otherlv_0, grammarAccess.getPropertyReferenceAccess().getPropertyVariableCrossReference_0());
			}
		)
	)
;

// Entry rule entryRuleEventReference
entryRuleEventReference returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getEventReferenceRule()); }
	iv_ruleEventReference=ruleEventReference
	{ $current=$iv_ruleEventReference.current; }
	EOF;

// Rule EventReference
ruleEventReference returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getEventReferenceRule());
					}
				}
				otherlv_0=RULE_ID
				{
					newLeafNode(otherlv_0, grammarAccess.getEventReferenceAccess().getReceiveMsgEventCrossReference_0_0());
				}
			)
		)
		otherlv_1='.'
		{
			newLeafNode(otherlv_1, grammarAccess.getEventReferenceAccess().getFullStopKeyword_1());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getEventReferenceRule());
					}
				}
				otherlv_2=RULE_ID
				{
					newLeafNode(otherlv_2, grammarAccess.getEventReferenceAccess().getParameterParameterCrossReference_2_0());
				}
			)
		)
	)
;

// Entry rule entryRuleFunctionCallExpression
entryRuleFunctionCallExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getFunctionCallExpressionRule()); }
	iv_ruleFunctionCallExpression=ruleFunctionCallExpression
	{ $current=$iv_ruleFunctionCallExpression.current; }
	EOF;

// Rule FunctionCallExpression
ruleFunctionCallExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionCallExpressionRule());
					}
				}
				otherlv_0=RULE_ID
				{
					newLeafNode(otherlv_0, grammarAccess.getFunctionCallExpressionAccess().getFunctionFunctionCrossReference_0_0());
				}
			)
		)
		otherlv_1='('
		{
			newLeafNode(otherlv_1, grammarAccess.getFunctionCallExpressionAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getFunctionCallExpressionAccess().getParametersExpressionParserRuleCall_2_0_0());
					}
					lv_parameters_2_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getFunctionCallExpressionRule());
						}
						add(
							$current,
							"parameters",
							lv_parameters_2_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_3=','
				{
					newLeafNode(otherlv_3, grammarAccess.getFunctionCallExpressionAccess().getCommaKeyword_2_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getFunctionCallExpressionAccess().getParametersExpressionParserRuleCall_2_1_1_0());
						}
						lv_parameters_4_0=ruleExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getFunctionCallExpressionRule());
							}
							add(
								$current,
								"parameters",
								lv_parameters_4_0,
								"org.thingml.xtext.ThingML.Expression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_5=')'
		{
			newLeafNode(otherlv_5, grammarAccess.getFunctionCallExpressionAccess().getRightParenthesisKeyword_3());
		}
	)
;

// Entry rule entryRuleConfiguration
entryRuleConfiguration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getConfigurationRule()); }
	iv_ruleConfiguration=ruleConfiguration
	{ $current=$iv_ruleConfiguration.current; }
	EOF;

// Rule Configuration
ruleConfiguration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='configuration'
		{
			newLeafNode(otherlv_0, grammarAccess.getConfigurationAccess().getConfigurationKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getConfigurationAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getConfigurationRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getConfigurationAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
				}
				lv_annotations_2_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getConfigurationRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_2_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_3='{'
		{
			newLeafNode(otherlv_3, grammarAccess.getConfigurationAccess().getLeftCurlyBracketKeyword_3());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getConfigurationAccess().getInstancesInstanceParserRuleCall_4_0_0());
					}
					lv_instances_4_0=ruleInstance
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConfigurationRule());
						}
						add(
							$current,
							"instances",
							lv_instances_4_0,
							"org.thingml.xtext.ThingML.Instance");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getConfigurationAccess().getConnectorsAbstractConnectorParserRuleCall_4_1_0());
					}
					lv_connectors_5_0=ruleAbstractConnector
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConfigurationRule());
						}
						add(
							$current,
							"connectors",
							lv_connectors_5_0,
							"org.thingml.xtext.ThingML.AbstractConnector");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getConfigurationAccess().getPropassignsConfigPropertyAssignParserRuleCall_4_2_0());
					}
					lv_propassigns_6_0=ruleConfigPropertyAssign
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConfigurationRule());
						}
						add(
							$current,
							"propassigns",
							lv_propassigns_6_0,
							"org.thingml.xtext.ThingML.ConfigPropertyAssign");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_7='}'
		{
			newLeafNode(otherlv_7, grammarAccess.getConfigurationAccess().getRightCurlyBracketKeyword_5());
		}
	)
;

// Entry rule entryRuleInstance
entryRuleInstance returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getInstanceRule()); }
	iv_ruleInstance=ruleInstance
	{ $current=$iv_ruleInstance.current; }
	EOF;

// Rule Instance
ruleInstance returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='instance'
		{
			newLeafNode(otherlv_0, grammarAccess.getInstanceAccess().getInstanceKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getInstanceAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getInstanceRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)
		otherlv_2=':'
		{
			newLeafNode(otherlv_2, grammarAccess.getInstanceAccess().getColonKeyword_2());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getInstanceRule());
					}
				}
				otherlv_3=RULE_ID
				{
					newLeafNode(otherlv_3, grammarAccess.getInstanceAccess().getTypeThingCrossReference_3_0());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getInstanceAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
				}
				lv_annotations_4_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getInstanceRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_4_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleConfigPropertyAssign
entryRuleConfigPropertyAssign returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getConfigPropertyAssignRule()); }
	iv_ruleConfigPropertyAssign=ruleConfigPropertyAssign
	{ $current=$iv_ruleConfigPropertyAssign.current; }
	EOF;

// Rule ConfigPropertyAssign
ruleConfigPropertyAssign returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='set'
		{
			newLeafNode(otherlv_0, grammarAccess.getConfigPropertyAssignAccess().getSetKeyword_0());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getConfigPropertyAssignRule());
					}
				}
				otherlv_1=RULE_ID
				{
					newLeafNode(otherlv_1, grammarAccess.getConfigPropertyAssignAccess().getInstanceInstanceCrossReference_1_0());
				}
			)
		)
		otherlv_2='.'
		{
			newLeafNode(otherlv_2, grammarAccess.getConfigPropertyAssignAccess().getFullStopKeyword_2());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getConfigPropertyAssignRule());
					}
				}
				otherlv_3=RULE_ID
				{
					newLeafNode(otherlv_3, grammarAccess.getConfigPropertyAssignAccess().getPropertyPropertyCrossReference_3_0());
				}
			)
		)
		(
			otherlv_4='['
			{
				newLeafNode(otherlv_4, grammarAccess.getConfigPropertyAssignAccess().getLeftSquareBracketKeyword_4_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getIndexExpressionParserRuleCall_4_1_0());
					}
					lv_index_5_0=ruleExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConfigPropertyAssignRule());
						}
						add(
							$current,
							"index",
							lv_index_5_0,
							"org.thingml.xtext.ThingML.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_6=']'
			{
				newLeafNode(otherlv_6, grammarAccess.getConfigPropertyAssignAccess().getRightSquareBracketKeyword_4_2());
			}
		)*
		otherlv_7='='
		{
			newLeafNode(otherlv_7, grammarAccess.getConfigPropertyAssignAccess().getEqualsSignKeyword_5());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getInitExpressionParserRuleCall_6_0());
				}
				lv_init_8_0=ruleExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getConfigPropertyAssignRule());
					}
					set(
						$current,
						"init",
						lv_init_8_0,
						"org.thingml.xtext.ThingML.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0());
				}
				lv_annotations_9_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getConfigPropertyAssignRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_9_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleAbstractConnector
entryRuleAbstractConnector returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAbstractConnectorRule()); }
	iv_ruleAbstractConnector=ruleAbstractConnector
	{ $current=$iv_ruleAbstractConnector.current; }
	EOF;

// Rule AbstractConnector
ruleAbstractConnector returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAbstractConnectorAccess().getConnectorParserRuleCall_0());
		}
		this_Connector_0=ruleConnector
		{
			$current = $this_Connector_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAbstractConnectorAccess().getExternalConnectorParserRuleCall_1());
		}
		this_ExternalConnector_1=ruleExternalConnector
		{
			$current = $this_ExternalConnector_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleConnector
entryRuleConnector returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getConnectorRule()); }
	iv_ruleConnector=ruleConnector
	{ $current=$iv_ruleConnector.current; }
	EOF;

// Rule Connector
ruleConnector returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='connector'
		{
			newLeafNode(otherlv_0, grammarAccess.getConnectorAccess().getConnectorKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getConnectorAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getConnectorRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)?
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getConnectorRule());
					}
				}
				otherlv_2=RULE_ID
				{
					newLeafNode(otherlv_2, grammarAccess.getConnectorAccess().getCliInstanceCrossReference_2_0());
				}
			)
		)
		otherlv_3='.'
		{
			newLeafNode(otherlv_3, grammarAccess.getConnectorAccess().getFullStopKeyword_3());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getConnectorRule());
					}
				}
				otherlv_4=RULE_ID
				{
					newLeafNode(otherlv_4, grammarAccess.getConnectorAccess().getRequiredRequiredPortCrossReference_4_0());
				}
			)
		)
		otherlv_5='=>'
		{
			newLeafNode(otherlv_5, grammarAccess.getConnectorAccess().getEqualsSignGreaterThanSignKeyword_5());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getConnectorRule());
					}
				}
				otherlv_6=RULE_ID
				{
					newLeafNode(otherlv_6, grammarAccess.getConnectorAccess().getSrvInstanceCrossReference_6_0());
				}
			)
		)
		otherlv_7='.'
		{
			newLeafNode(otherlv_7, grammarAccess.getConnectorAccess().getFullStopKeyword_7());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getConnectorRule());
					}
				}
				otherlv_8=RULE_ID
				{
					newLeafNode(otherlv_8, grammarAccess.getConnectorAccess().getProvidedProvidedPortCrossReference_8_0());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getConnectorAccess().getAnnotationsPlatformAnnotationParserRuleCall_9_0());
				}
				lv_annotations_9_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getConnectorRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_9_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleExternalConnector
entryRuleExternalConnector returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExternalConnectorRule()); }
	iv_ruleExternalConnector=ruleExternalConnector
	{ $current=$iv_ruleExternalConnector.current; }
	EOF;

// Rule ExternalConnector
ruleExternalConnector returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='connector'
		{
			newLeafNode(otherlv_0, grammarAccess.getExternalConnectorAccess().getConnectorKeyword_0());
		}
		(
			(
				lv_name_1_0=RULE_ID
				{
					newLeafNode(lv_name_1_0, grammarAccess.getExternalConnectorAccess().getNameIDTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getExternalConnectorRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_1_0,
						"org.thingml.xtext.ThingML.ID");
				}
			)
		)?
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getExternalConnectorRule());
					}
				}
				otherlv_2=RULE_ID
				{
					newLeafNode(otherlv_2, grammarAccess.getExternalConnectorAccess().getInstInstanceCrossReference_2_0());
				}
			)
		)
		otherlv_3='.'
		{
			newLeafNode(otherlv_3, grammarAccess.getExternalConnectorAccess().getFullStopKeyword_3());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getExternalConnectorRule());
					}
				}
				otherlv_4=RULE_ID
				{
					newLeafNode(otherlv_4, grammarAccess.getExternalConnectorAccess().getPortPortCrossReference_4_0());
				}
			)
		)
		otherlv_5='over'
		{
			newLeafNode(otherlv_5, grammarAccess.getExternalConnectorAccess().getOverKeyword_5());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getExternalConnectorRule());
					}
				}
				otherlv_6=RULE_ID
				{
					newLeafNode(otherlv_6, grammarAccess.getExternalConnectorAccess().getProtocolProtocolCrossReference_6_0());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getExternalConnectorAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0());
				}
				lv_annotations_7_0=rulePlatformAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExternalConnectorRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_7_0,
						"org.thingml.xtext.ThingML.PlatformAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

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
