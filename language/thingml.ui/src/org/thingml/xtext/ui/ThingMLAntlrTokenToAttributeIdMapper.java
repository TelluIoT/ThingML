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
package org.thingml.xtext.ui;

import java.util.HashSet;
import java.util.regex.Pattern;

import org.eclipse.xtext.ide.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.thingml.xtext.ide.contentassist.antlr.internal.InternalThingMLLexer;


public class ThingMLAntlrTokenToAttributeIdMapper extends AbstractAntlrTokenToAttributeIdMapper {


	HashSet<String> typesAndMessages = new HashSet<String>(java.util.Arrays.asList(new String[]
					{"'thing'", "'fragment'", "'readonly'", "'includes'", "'datatype'", "'object'", "'enumeration'"
							, "'sends'", "'receives'", "'port'", "'provided'", "'required'", "'protocol'"
							, "'optional'", "'message'", "'property'"}));
	
	HashSet<String> statemachine = new HashSet<String>(java.util.Arrays.asList(new String[]
			{"'function'", "'abstract'", "'state'", "'final'", "'composite'", "'statechart'", "'event'", "'guard'"
					, "'action'", "'on'", "'entry'", "'exit'", "'region'"
					, "'init'", "'keeps'", "'history'", "'exit'", "'->'"
					, "'session'", "'internal'", "'transition'", "'?'"}));
	
	HashSet<String> action = new HashSet<String>(java.util.Arrays.asList(new String[]
			{"'var'", "'return'", "'do'", "'end'", "'if'", "'else'", "'while'", "'for'", "'in'"
					, "'print'", "'println'", "'error'", "'errorln'", "'not'", "'and'", "'or'"
					, "'init'", "'keeps'", "'history'", "'exit'", "'->'"
					, "'fork'", "'set'", "'as'", "'!'"}));
	
	HashSet<String> configuration = new HashSet<String>(java.util.Arrays.asList(new String[]
			{"'configuration'", "'instance'", "'connector'", "'over'", "'=>'"}));
	
	@Override
	protected String calculateId(String tokenName, int tokenType) {
		
		switch(tokenType) {
		case InternalThingMLLexer.RULE_ANNOTATION_ID:
			return ThingMLHighlightingConfiguration.ANNOTATIONS_ID;
		case InternalThingMLLexer.RULE_FLOAT:
		case InternalThingMLLexer.RULE_INT:
		case InternalThingMLLexer.RULE_CHAR:
		case InternalThingMLLexer.RULE_BYTE:
		case InternalThingMLLexer.RULE_STRING:
			return ThingMLHighlightingConfiguration.DEFAULT_LITERAL_ID;
		case InternalThingMLLexer.RULE_ML_COMMENT:
		case InternalThingMLLexer.RULE_SL_COMMENT:
			return ThingMLHighlightingConfiguration.COMMENTS_ID;
		case InternalThingMLLexer.RULE_EXTERN:
			return ThingMLHighlightingConfiguration.EXTERN_ID;
		case InternalThingMLLexer.RULE_ANY_OTHER: 
			return ThingMLHighlightingConfiguration.DEFAULT_TEXT_ID;
		default:
			break;
		}
			
		if (typesAndMessages.contains(tokenName)) 
			return ThingMLHighlightingConfiguration.TYPES_AND_MESSAGES_ID;
		
		if (statemachine.contains(tokenName)) 
			return ThingMLHighlightingConfiguration.STATEMACHINE_ID;
		
		if (action.contains(tokenName)) 
			return ThingMLHighlightingConfiguration.ACTIONS_ID;
		
		if (configuration.contains(tokenName)) 
			return ThingMLHighlightingConfiguration.CONFIGURATION_ID;
		
		return ThingMLHighlightingConfiguration.DEFAULT_TEXT_ID;
	}

}
