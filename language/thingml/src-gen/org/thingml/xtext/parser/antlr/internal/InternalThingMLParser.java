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



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

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
@SuppressWarnings("all")
public class InternalThingMLParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING_LIT", "RULE_ANNOTATION_ID", "RULE_ID", "RULE_INT", "RULE_STRING_EXT", "RULE_FLOAT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'import'", "'var'", "':'", "'['", "']'", "'datatype'", "'<'", "'>'", "';'", "'object'", "'enumeration'", "'{'", "'}'", "'thing'", "'fragment'", "'includes'", "','", "'set'", "'='", "'protocol'", "'function'", "'('", "')'", "'abstract'", "'readonly'", "'property'", "'message'", "'optional'", "'required'", "'port'", "'sends'", "'receives'", "'provided'", "'internal'", "'state'", "'on'", "'entry'", "'exit'", "'transition'", "'->'", "'event'", "'guard'", "'action'", "'composite'", "'init'", "'keeps'", "'history'", "'statechart'", "'session'", "'region'", "'final'", "'?'", "'do'", "'end'", "'&'", "'!'", "'++'", "'--'", "'while'", "'if'", "'else'", "'return'", "'print'", "'error'", "'fork'", "'as'", "'or'", "'and'", "'=='", "'!='", "'>='", "'<='", "'+'", "'-'", "'*'", "'/'", "'%'", "'not'", "'true'", "'false'", "'.'", "'configuration'", "'instance'", "'connector'", "'=>'", "'over'"
    };
    public static final int T__50=50;
    public static final int T__59=59;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=6;
    public static final int RULE_INT=7;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=10;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int RULE_STRING_EXT=8;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int RULE_FLOAT=9;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__90=90;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__99=99;
    public static final int T__14=14;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int RULE_STRING_LIT=4;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_SL_COMMENT=11;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=12;
    public static final int RULE_ANY_OTHER=13;
    public static final int RULE_ANNOTATION_ID=5;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__84=84;
    public static final int T__85=85;
    public static final int T__86=86;
    public static final int T__87=87;

    // delegates
    // delegators


        public InternalThingMLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalThingMLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalThingMLParser.tokenNames; }
    public String getGrammarFileName() { return "InternalThingML.g"; }



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




    // $ANTLR start "entryRuleThingMLModel"
    // InternalThingML.g:77:1: entryRuleThingMLModel returns [EObject current=null] : iv_ruleThingMLModel= ruleThingMLModel EOF ;
    public final EObject entryRuleThingMLModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThingMLModel = null;


        try {
            // InternalThingML.g:77:53: (iv_ruleThingMLModel= ruleThingMLModel EOF )
            // InternalThingML.g:78:2: iv_ruleThingMLModel= ruleThingMLModel EOF
            {
             newCompositeNode(grammarAccess.getThingMLModelRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleThingMLModel=ruleThingMLModel();

            state._fsp--;

             current =iv_ruleThingMLModel; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleThingMLModel"


    // $ANTLR start "ruleThingMLModel"
    // InternalThingML.g:84:1: ruleThingMLModel returns [EObject current=null] : ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )* ( ( (lv_types_2_0= ruleType ) ) | ( (lv_protocols_3_0= ruleProtocol ) ) | ( (lv_configs_4_0= ruleConfiguration ) ) )* ) ;
    public final EObject ruleThingMLModel() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_importURI_1_0=null;
        EObject lv_types_2_0 = null;

        EObject lv_protocols_3_0 = null;

        EObject lv_configs_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:90:2: ( ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )* ( ( (lv_types_2_0= ruleType ) ) | ( (lv_protocols_3_0= ruleProtocol ) ) | ( (lv_configs_4_0= ruleConfiguration ) ) )* ) )
            // InternalThingML.g:91:2: ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )* ( ( (lv_types_2_0= ruleType ) ) | ( (lv_protocols_3_0= ruleProtocol ) ) | ( (lv_configs_4_0= ruleConfiguration ) ) )* )
            {
            // InternalThingML.g:91:2: ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )* ( ( (lv_types_2_0= ruleType ) ) | ( (lv_protocols_3_0= ruleProtocol ) ) | ( (lv_configs_4_0= ruleConfiguration ) ) )* )
            // InternalThingML.g:92:3: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )* ( ( (lv_types_2_0= ruleType ) ) | ( (lv_protocols_3_0= ruleProtocol ) ) | ( (lv_configs_4_0= ruleConfiguration ) ) )*
            {
            // InternalThingML.g:92:3: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==14) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalThingML.g:93:4: otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) )
            	    {
            	    otherlv_0=(Token)match(input,14,FOLLOW_3); 

            	    				newLeafNode(otherlv_0, grammarAccess.getThingMLModelAccess().getImportKeyword_0_0());
            	    			
            	    // InternalThingML.g:97:4: ( (lv_importURI_1_0= RULE_STRING_LIT ) )
            	    // InternalThingML.g:98:5: (lv_importURI_1_0= RULE_STRING_LIT )
            	    {
            	    // InternalThingML.g:98:5: (lv_importURI_1_0= RULE_STRING_LIT )
            	    // InternalThingML.g:99:6: lv_importURI_1_0= RULE_STRING_LIT
            	    {
            	    lv_importURI_1_0=(Token)match(input,RULE_STRING_LIT,FOLLOW_4); 

            	    						newLeafNode(lv_importURI_1_0, grammarAccess.getThingMLModelAccess().getImportURISTRING_LITTerminalRuleCall_0_1_0());
            	    					

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getThingMLModelRule());
            	    						}
            	    						addWithLastConsumed(
            	    							current,
            	    							"importURI",
            	    							lv_importURI_1_0,
            	    							"org.thingml.xtext.ThingML.STRING_LIT");
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // InternalThingML.g:116:3: ( ( (lv_types_2_0= ruleType ) ) | ( (lv_protocols_3_0= ruleProtocol ) ) | ( (lv_configs_4_0= ruleConfiguration ) ) )*
            loop2:
            do {
                int alt2=4;
                switch ( input.LA(1) ) {
                case 19:
                case 23:
                case 24:
                case 27:
                    {
                    alt2=1;
                    }
                    break;
                case 33:
                    {
                    alt2=2;
                    }
                    break;
                case 95:
                    {
                    alt2=3;
                    }
                    break;

                }

                switch (alt2) {
            	case 1 :
            	    // InternalThingML.g:117:4: ( (lv_types_2_0= ruleType ) )
            	    {
            	    // InternalThingML.g:117:4: ( (lv_types_2_0= ruleType ) )
            	    // InternalThingML.g:118:5: (lv_types_2_0= ruleType )
            	    {
            	    // InternalThingML.g:118:5: (lv_types_2_0= ruleType )
            	    // InternalThingML.g:119:6: lv_types_2_0= ruleType
            	    {

            	    						newCompositeNode(grammarAccess.getThingMLModelAccess().getTypesTypeParserRuleCall_1_0_0());
            	    					
            	    pushFollow(FOLLOW_5);
            	    lv_types_2_0=ruleType();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingMLModelRule());
            	    						}
            	    						add(
            	    							current,
            	    							"types",
            	    							lv_types_2_0,
            	    							"org.thingml.xtext.ThingML.Type");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:137:4: ( (lv_protocols_3_0= ruleProtocol ) )
            	    {
            	    // InternalThingML.g:137:4: ( (lv_protocols_3_0= ruleProtocol ) )
            	    // InternalThingML.g:138:5: (lv_protocols_3_0= ruleProtocol )
            	    {
            	    // InternalThingML.g:138:5: (lv_protocols_3_0= ruleProtocol )
            	    // InternalThingML.g:139:6: lv_protocols_3_0= ruleProtocol
            	    {

            	    						newCompositeNode(grammarAccess.getThingMLModelAccess().getProtocolsProtocolParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_5);
            	    lv_protocols_3_0=ruleProtocol();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingMLModelRule());
            	    						}
            	    						add(
            	    							current,
            	    							"protocols",
            	    							lv_protocols_3_0,
            	    							"org.thingml.xtext.ThingML.Protocol");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalThingML.g:157:4: ( (lv_configs_4_0= ruleConfiguration ) )
            	    {
            	    // InternalThingML.g:157:4: ( (lv_configs_4_0= ruleConfiguration ) )
            	    // InternalThingML.g:158:5: (lv_configs_4_0= ruleConfiguration )
            	    {
            	    // InternalThingML.g:158:5: (lv_configs_4_0= ruleConfiguration )
            	    // InternalThingML.g:159:6: lv_configs_4_0= ruleConfiguration
            	    {

            	    						newCompositeNode(grammarAccess.getThingMLModelAccess().getConfigsConfigurationParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_5);
            	    lv_configs_4_0=ruleConfiguration();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingMLModelRule());
            	    						}
            	    						add(
            	    							current,
            	    							"configs",
            	    							lv_configs_4_0,
            	    							"org.thingml.xtext.ThingML.Configuration");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleThingMLModel"


    // $ANTLR start "entryRulePlatformAnnotation"
    // InternalThingML.g:181:1: entryRulePlatformAnnotation returns [EObject current=null] : iv_rulePlatformAnnotation= rulePlatformAnnotation EOF ;
    public final EObject entryRulePlatformAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePlatformAnnotation = null;


        try {
            // InternalThingML.g:181:59: (iv_rulePlatformAnnotation= rulePlatformAnnotation EOF )
            // InternalThingML.g:182:2: iv_rulePlatformAnnotation= rulePlatformAnnotation EOF
            {
             newCompositeNode(grammarAccess.getPlatformAnnotationRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePlatformAnnotation=rulePlatformAnnotation();

            state._fsp--;

             current =iv_rulePlatformAnnotation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePlatformAnnotation"


    // $ANTLR start "rulePlatformAnnotation"
    // InternalThingML.g:188:1: rulePlatformAnnotation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) ( (lv_value_1_0= RULE_STRING_LIT ) ) ) ;
    public final EObject rulePlatformAnnotation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_value_1_0=null;


        	enterRule();

        try {
            // InternalThingML.g:194:2: ( ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) ( (lv_value_1_0= RULE_STRING_LIT ) ) ) )
            // InternalThingML.g:195:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) ( (lv_value_1_0= RULE_STRING_LIT ) ) )
            {
            // InternalThingML.g:195:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) ( (lv_value_1_0= RULE_STRING_LIT ) ) )
            // InternalThingML.g:196:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) ( (lv_value_1_0= RULE_STRING_LIT ) )
            {
            // InternalThingML.g:196:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) )
            // InternalThingML.g:197:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            {
            // InternalThingML.g:197:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            // InternalThingML.g:198:5: lv_name_0_0= RULE_ANNOTATION_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ANNOTATION_ID,FOLLOW_3); 

            					newLeafNode(lv_name_0_0, grammarAccess.getPlatformAnnotationAccess().getNameANNOTATION_IDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPlatformAnnotationRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"org.thingml.xtext.ThingML.ANNOTATION_ID");
            				

            }


            }

            // InternalThingML.g:214:3: ( (lv_value_1_0= RULE_STRING_LIT ) )
            // InternalThingML.g:215:4: (lv_value_1_0= RULE_STRING_LIT )
            {
            // InternalThingML.g:215:4: (lv_value_1_0= RULE_STRING_LIT )
            // InternalThingML.g:216:5: lv_value_1_0= RULE_STRING_LIT
            {
            lv_value_1_0=(Token)match(input,RULE_STRING_LIT,FOLLOW_2); 

            					newLeafNode(lv_value_1_0, grammarAccess.getPlatformAnnotationAccess().getValueSTRING_LITTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPlatformAnnotationRule());
            					}
            					setWithLastConsumed(
            						current,
            						"value",
            						lv_value_1_0,
            						"org.thingml.xtext.ThingML.STRING_LIT");
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePlatformAnnotation"


    // $ANTLR start "entryRuleVariable"
    // InternalThingML.g:236:1: entryRuleVariable returns [EObject current=null] : iv_ruleVariable= ruleVariable EOF ;
    public final EObject entryRuleVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariable = null;


        try {
            // InternalThingML.g:236:49: (iv_ruleVariable= ruleVariable EOF )
            // InternalThingML.g:237:2: iv_ruleVariable= ruleVariable EOF
            {
             newCompositeNode(grammarAccess.getVariableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariable=ruleVariable();

            state._fsp--;

             current =iv_ruleVariable; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariable"


    // $ANTLR start "ruleVariable"
    // InternalThingML.g:243:1: ruleVariable returns [EObject current=null] : ( (otherlv_0= 'var' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_typeRef_3_0= ruleTypeRef ) ) ) | this_LocalVariable_4= ruleLocalVariable | this_Property_5= ruleProperty | this_Parameter_6= ruleParameter ) ;
    public final EObject ruleVariable() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        EObject lv_typeRef_3_0 = null;

        EObject this_LocalVariable_4 = null;

        EObject this_Property_5 = null;

        EObject this_Parameter_6 = null;



        	enterRule();

        try {
            // InternalThingML.g:249:2: ( ( (otherlv_0= 'var' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_typeRef_3_0= ruleTypeRef ) ) ) | this_LocalVariable_4= ruleLocalVariable | this_Property_5= ruleProperty | this_Parameter_6= ruleParameter ) )
            // InternalThingML.g:250:2: ( (otherlv_0= 'var' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_typeRef_3_0= ruleTypeRef ) ) ) | this_LocalVariable_4= ruleLocalVariable | this_Property_5= ruleProperty | this_Parameter_6= ruleParameter )
            {
            // InternalThingML.g:250:2: ( (otherlv_0= 'var' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_typeRef_3_0= ruleTypeRef ) ) ) | this_LocalVariable_4= ruleLocalVariable | this_Property_5= ruleProperty | this_Parameter_6= ruleParameter )
            int alt3=4;
            switch ( input.LA(1) ) {
            case 15:
                {
                int LA3_1 = input.LA(2);

                if ( (LA3_1==RULE_ID) ) {
                    int LA3_5 = input.LA(3);

                    if ( (LA3_5==16) ) {
                        int LA3_7 = input.LA(4);

                        if ( (LA3_7==RULE_ID) ) {
                            alt3=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 7, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 5, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
                }
                break;
            case 38:
                {
                int LA3_2 = input.LA(2);

                if ( (LA3_2==15) ) {
                    alt3=2;
                }
                else if ( (LA3_2==39) ) {
                    alt3=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 2, input);

                    throw nvae;
                }
                }
                break;
            case 39:
                {
                alt3=3;
                }
                break;
            case RULE_ID:
                {
                alt3=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // InternalThingML.g:251:3: (otherlv_0= 'var' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_typeRef_3_0= ruleTypeRef ) ) )
                    {
                    // InternalThingML.g:251:3: (otherlv_0= 'var' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_typeRef_3_0= ruleTypeRef ) ) )
                    // InternalThingML.g:252:4: otherlv_0= 'var' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_typeRef_3_0= ruleTypeRef ) )
                    {
                    otherlv_0=(Token)match(input,15,FOLLOW_6); 

                    				newLeafNode(otherlv_0, grammarAccess.getVariableAccess().getVarKeyword_0_0());
                    			
                    // InternalThingML.g:256:4: ( (lv_name_1_0= RULE_ID ) )
                    // InternalThingML.g:257:5: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:257:5: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:258:6: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_7); 

                    						newLeafNode(lv_name_1_0, grammarAccess.getVariableAccess().getNameIDTerminalRuleCall_0_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getVariableRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"name",
                    							lv_name_1_0,
                    							"org.thingml.xtext.ThingML.ID");
                    					

                    }


                    }

                    otherlv_2=(Token)match(input,16,FOLLOW_6); 

                    				newLeafNode(otherlv_2, grammarAccess.getVariableAccess().getColonKeyword_0_2());
                    			
                    // InternalThingML.g:278:4: ( (lv_typeRef_3_0= ruleTypeRef ) )
                    // InternalThingML.g:279:5: (lv_typeRef_3_0= ruleTypeRef )
                    {
                    // InternalThingML.g:279:5: (lv_typeRef_3_0= ruleTypeRef )
                    // InternalThingML.g:280:6: lv_typeRef_3_0= ruleTypeRef
                    {

                    						newCompositeNode(grammarAccess.getVariableAccess().getTypeRefTypeRefParserRuleCall_0_3_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_typeRef_3_0=ruleTypeRef();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVariableRule());
                    						}
                    						set(
                    							current,
                    							"typeRef",
                    							lv_typeRef_3_0,
                    							"org.thingml.xtext.ThingML.TypeRef");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalThingML.g:299:3: this_LocalVariable_4= ruleLocalVariable
                    {

                    			newCompositeNode(grammarAccess.getVariableAccess().getLocalVariableParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_LocalVariable_4=ruleLocalVariable();

                    state._fsp--;


                    			current = this_LocalVariable_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalThingML.g:308:3: this_Property_5= ruleProperty
                    {

                    			newCompositeNode(grammarAccess.getVariableAccess().getPropertyParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Property_5=ruleProperty();

                    state._fsp--;


                    			current = this_Property_5;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalThingML.g:317:3: this_Parameter_6= ruleParameter
                    {

                    			newCompositeNode(grammarAccess.getVariableAccess().getParameterParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_Parameter_6=ruleParameter();

                    state._fsp--;


                    			current = this_Parameter_6;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariable"


    // $ANTLR start "entryRuleTypeRef"
    // InternalThingML.g:329:1: entryRuleTypeRef returns [EObject current=null] : iv_ruleTypeRef= ruleTypeRef EOF ;
    public final EObject entryRuleTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeRef = null;


        try {
            // InternalThingML.g:329:48: (iv_ruleTypeRef= ruleTypeRef EOF )
            // InternalThingML.g:330:2: iv_ruleTypeRef= ruleTypeRef EOF
            {
             newCompositeNode(grammarAccess.getTypeRefRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTypeRef=ruleTypeRef();

            state._fsp--;

             current =iv_ruleTypeRef; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeRef"


    // $ANTLR start "ruleTypeRef"
    // InternalThingML.g:336:1: ruleTypeRef returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )? ) ;
    public final EObject ruleTypeRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_isArray_1_0=null;
        Token otherlv_3=null;
        EObject lv_cardinality_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:342:2: ( ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )? ) )
            // InternalThingML.g:343:2: ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )? )
            {
            // InternalThingML.g:343:2: ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )? )
            // InternalThingML.g:344:3: ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )?
            {
            // InternalThingML.g:344:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:345:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:345:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:346:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTypeRefRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_8); 

            					newLeafNode(otherlv_0, grammarAccess.getTypeRefAccess().getTypeTypeCrossReference_0_0());
            				

            }


            }

            // InternalThingML.g:357:3: ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==17) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalThingML.g:358:4: ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']'
                    {
                    // InternalThingML.g:358:4: ( (lv_isArray_1_0= '[' ) )
                    // InternalThingML.g:359:5: (lv_isArray_1_0= '[' )
                    {
                    // InternalThingML.g:359:5: (lv_isArray_1_0= '[' )
                    // InternalThingML.g:360:6: lv_isArray_1_0= '['
                    {
                    lv_isArray_1_0=(Token)match(input,17,FOLLOW_9); 

                    						newLeafNode(lv_isArray_1_0, grammarAccess.getTypeRefAccess().getIsArrayLeftSquareBracketKeyword_1_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTypeRefRule());
                    						}
                    						setWithLastConsumed(current, "isArray", true, "[");
                    					

                    }


                    }

                    // InternalThingML.g:372:4: ( (lv_cardinality_2_0= ruleExpression ) )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==RULE_STRING_LIT||(LA4_0>=RULE_ID && LA4_0<=RULE_FLOAT)||LA4_0==35||LA4_0==87||(LA4_0>=91 && LA4_0<=93)) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // InternalThingML.g:373:5: (lv_cardinality_2_0= ruleExpression )
                            {
                            // InternalThingML.g:373:5: (lv_cardinality_2_0= ruleExpression )
                            // InternalThingML.g:374:6: lv_cardinality_2_0= ruleExpression
                            {

                            						newCompositeNode(grammarAccess.getTypeRefAccess().getCardinalityExpressionParserRuleCall_1_1_0());
                            					
                            pushFollow(FOLLOW_10);
                            lv_cardinality_2_0=ruleExpression();

                            state._fsp--;


                            						if (current==null) {
                            							current = createModelElementForParent(grammarAccess.getTypeRefRule());
                            						}
                            						set(
                            							current,
                            							"cardinality",
                            							lv_cardinality_2_0,
                            							"org.thingml.xtext.ThingML.Expression");
                            						afterParserOrEnumRuleCall();
                            					

                            }


                            }
                            break;

                    }

                    otherlv_3=(Token)match(input,18,FOLLOW_2); 

                    				newLeafNode(otherlv_3, grammarAccess.getTypeRefAccess().getRightSquareBracketKeyword_1_2());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeRef"


    // $ANTLR start "entryRuleType"
    // InternalThingML.g:400:1: entryRuleType returns [EObject current=null] : iv_ruleType= ruleType EOF ;
    public final EObject entryRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleType = null;


        try {
            // InternalThingML.g:400:45: (iv_ruleType= ruleType EOF )
            // InternalThingML.g:401:2: iv_ruleType= ruleType EOF
            {
             newCompositeNode(grammarAccess.getTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleType=ruleType();

            state._fsp--;

             current =iv_ruleType; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleType"


    // $ANTLR start "ruleType"
    // InternalThingML.g:407:1: ruleType returns [EObject current=null] : (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing ) ;
    public final EObject ruleType() throws RecognitionException {
        EObject current = null;

        EObject this_PrimitiveType_0 = null;

        EObject this_ObjectType_1 = null;

        EObject this_Enumeration_2 = null;

        EObject this_Thing_3 = null;



        	enterRule();

        try {
            // InternalThingML.g:413:2: ( (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing ) )
            // InternalThingML.g:414:2: (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing )
            {
            // InternalThingML.g:414:2: (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing )
            int alt6=4;
            switch ( input.LA(1) ) {
            case 19:
                {
                alt6=1;
                }
                break;
            case 23:
                {
                alt6=2;
                }
                break;
            case 24:
                {
                alt6=3;
                }
                break;
            case 27:
                {
                alt6=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalThingML.g:415:3: this_PrimitiveType_0= rulePrimitiveType
                    {

                    			newCompositeNode(grammarAccess.getTypeAccess().getPrimitiveTypeParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_PrimitiveType_0=rulePrimitiveType();

                    state._fsp--;


                    			current = this_PrimitiveType_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalThingML.g:424:3: this_ObjectType_1= ruleObjectType
                    {

                    			newCompositeNode(grammarAccess.getTypeAccess().getObjectTypeParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_ObjectType_1=ruleObjectType();

                    state._fsp--;


                    			current = this_ObjectType_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalThingML.g:433:3: this_Enumeration_2= ruleEnumeration
                    {

                    			newCompositeNode(grammarAccess.getTypeAccess().getEnumerationParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Enumeration_2=ruleEnumeration();

                    state._fsp--;


                    			current = this_Enumeration_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalThingML.g:442:3: this_Thing_3= ruleThing
                    {

                    			newCompositeNode(grammarAccess.getTypeAccess().getThingParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_Thing_3=ruleThing();

                    state._fsp--;


                    			current = this_Thing_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleType"


    // $ANTLR start "entryRulePrimitiveType"
    // InternalThingML.g:454:1: entryRulePrimitiveType returns [EObject current=null] : iv_rulePrimitiveType= rulePrimitiveType EOF ;
    public final EObject entryRulePrimitiveType() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimitiveType = null;


        try {
            // InternalThingML.g:454:54: (iv_rulePrimitiveType= rulePrimitiveType EOF )
            // InternalThingML.g:455:2: iv_rulePrimitiveType= rulePrimitiveType EOF
            {
             newCompositeNode(grammarAccess.getPrimitiveTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePrimitiveType=rulePrimitiveType();

            state._fsp--;

             current =iv_rulePrimitiveType; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimitiveType"


    // $ANTLR start "rulePrimitiveType"
    // InternalThingML.g:461:1: rulePrimitiveType returns [EObject current=null] : (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )? ) ;
    public final EObject rulePrimitiveType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token lv_ByteSize_3_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_annotations_5_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:467:2: ( (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )? ) )
            // InternalThingML.g:468:2: (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )? )
            {
            // InternalThingML.g:468:2: (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )? )
            // InternalThingML.g:469:3: otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )?
            {
            otherlv_0=(Token)match(input,19,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getPrimitiveTypeAccess().getDatatypeKeyword_0());
            		
            // InternalThingML.g:473:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:474:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:474:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:475:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_11); 

            					newLeafNode(lv_name_1_0, grammarAccess.getPrimitiveTypeAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPrimitiveTypeRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,20,FOLLOW_12); 

            			newLeafNode(otherlv_2, grammarAccess.getPrimitiveTypeAccess().getLessThanSignKeyword_2());
            		
            // InternalThingML.g:495:3: ( (lv_ByteSize_3_0= RULE_INT ) )
            // InternalThingML.g:496:4: (lv_ByteSize_3_0= RULE_INT )
            {
            // InternalThingML.g:496:4: (lv_ByteSize_3_0= RULE_INT )
            // InternalThingML.g:497:5: lv_ByteSize_3_0= RULE_INT
            {
            lv_ByteSize_3_0=(Token)match(input,RULE_INT,FOLLOW_13); 

            					newLeafNode(lv_ByteSize_3_0, grammarAccess.getPrimitiveTypeAccess().getByteSizeINTTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPrimitiveTypeRule());
            					}
            					setWithLastConsumed(
            						current,
            						"ByteSize",
            						lv_ByteSize_3_0,
            						"org.thingml.xtext.ThingML.INT");
            				

            }


            }

            otherlv_4=(Token)match(input,21,FOLLOW_14); 

            			newLeafNode(otherlv_4, grammarAccess.getPrimitiveTypeAccess().getGreaterThanSignKeyword_4());
            		
            // InternalThingML.g:517:3: ( (lv_annotations_5_0= rulePlatformAnnotation ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==RULE_ANNOTATION_ID) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalThingML.g:518:4: (lv_annotations_5_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:518:4: (lv_annotations_5_0= rulePlatformAnnotation )
            	    // InternalThingML.g:519:5: lv_annotations_5_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_14);
            	    lv_annotations_5_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getPrimitiveTypeRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_5_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            // InternalThingML.g:536:3: (otherlv_6= ';' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==22) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalThingML.g:537:4: otherlv_6= ';'
                    {
                    otherlv_6=(Token)match(input,22,FOLLOW_2); 

                    				newLeafNode(otherlv_6, grammarAccess.getPrimitiveTypeAccess().getSemicolonKeyword_6());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimitiveType"


    // $ANTLR start "entryRuleObjectType"
    // InternalThingML.g:546:1: entryRuleObjectType returns [EObject current=null] : iv_ruleObjectType= ruleObjectType EOF ;
    public final EObject entryRuleObjectType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleObjectType = null;


        try {
            // InternalThingML.g:546:51: (iv_ruleObjectType= ruleObjectType EOF )
            // InternalThingML.g:547:2: iv_ruleObjectType= ruleObjectType EOF
            {
             newCompositeNode(grammarAccess.getObjectTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleObjectType=ruleObjectType();

            state._fsp--;

             current =iv_ruleObjectType; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleObjectType"


    // $ANTLR start "ruleObjectType"
    // InternalThingML.g:553:1: ruleObjectType returns [EObject current=null] : (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )? ) ;
    public final EObject ruleObjectType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        EObject lv_annotations_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:559:2: ( (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )? ) )
            // InternalThingML.g:560:2: (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )? )
            {
            // InternalThingML.g:560:2: (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )? )
            // InternalThingML.g:561:3: otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )?
            {
            otherlv_0=(Token)match(input,23,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getObjectTypeAccess().getObjectKeyword_0());
            		
            // InternalThingML.g:565:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:566:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:566:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:567:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_14); 

            					newLeafNode(lv_name_1_0, grammarAccess.getObjectTypeAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getObjectTypeRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:583:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==RULE_ANNOTATION_ID) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalThingML.g:584:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:584:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:585:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getObjectTypeAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_14);
            	    lv_annotations_2_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getObjectTypeRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_2_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            // InternalThingML.g:602:3: (otherlv_3= ';' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==22) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalThingML.g:603:4: otherlv_3= ';'
                    {
                    otherlv_3=(Token)match(input,22,FOLLOW_2); 

                    				newLeafNode(otherlv_3, grammarAccess.getObjectTypeAccess().getSemicolonKeyword_3());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleObjectType"


    // $ANTLR start "entryRuleEnumeration"
    // InternalThingML.g:612:1: entryRuleEnumeration returns [EObject current=null] : iv_ruleEnumeration= ruleEnumeration EOF ;
    public final EObject entryRuleEnumeration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumeration = null;


        try {
            // InternalThingML.g:612:52: (iv_ruleEnumeration= ruleEnumeration EOF )
            // InternalThingML.g:613:2: iv_ruleEnumeration= ruleEnumeration EOF
            {
             newCompositeNode(grammarAccess.getEnumerationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEnumeration=ruleEnumeration();

            state._fsp--;

             current =iv_ruleEnumeration; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEnumeration"


    // $ANTLR start "ruleEnumeration"
    // InternalThingML.g:619:1: ruleEnumeration returns [EObject current=null] : (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' ) ;
    public final EObject ruleEnumeration() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_annotations_2_0 = null;

        EObject lv_literals_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:625:2: ( (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' ) )
            // InternalThingML.g:626:2: (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' )
            {
            // InternalThingML.g:626:2: (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' )
            // InternalThingML.g:627:3: otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,24,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getEnumerationAccess().getEnumerationKeyword_0());
            		
            // InternalThingML.g:631:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:632:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:632:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:633:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_15); 

            					newLeafNode(lv_name_1_0, grammarAccess.getEnumerationAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEnumerationRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:649:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==RULE_ANNOTATION_ID) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalThingML.g:650:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:650:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:651:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getEnumerationAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_annotations_2_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getEnumerationRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_2_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            otherlv_3=(Token)match(input,25,FOLLOW_16); 

            			newLeafNode(otherlv_3, grammarAccess.getEnumerationAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalThingML.g:672:3: ( (lv_literals_4_0= ruleEnumerationLiteral ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==RULE_ID) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalThingML.g:673:4: (lv_literals_4_0= ruleEnumerationLiteral )
            	    {
            	    // InternalThingML.g:673:4: (lv_literals_4_0= ruleEnumerationLiteral )
            	    // InternalThingML.g:674:5: lv_literals_4_0= ruleEnumerationLiteral
            	    {

            	    					newCompositeNode(grammarAccess.getEnumerationAccess().getLiteralsEnumerationLiteralParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_16);
            	    lv_literals_4_0=ruleEnumerationLiteral();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getEnumerationRule());
            	    					}
            	    					add(
            	    						current,
            	    						"literals",
            	    						lv_literals_4_0,
            	    						"org.thingml.xtext.ThingML.EnumerationLiteral");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            otherlv_5=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getEnumerationAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEnumeration"


    // $ANTLR start "entryRuleEnumerationLiteral"
    // InternalThingML.g:699:1: entryRuleEnumerationLiteral returns [EObject current=null] : iv_ruleEnumerationLiteral= ruleEnumerationLiteral EOF ;
    public final EObject entryRuleEnumerationLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumerationLiteral = null;


        try {
            // InternalThingML.g:699:59: (iv_ruleEnumerationLiteral= ruleEnumerationLiteral EOF )
            // InternalThingML.g:700:2: iv_ruleEnumerationLiteral= ruleEnumerationLiteral EOF
            {
             newCompositeNode(grammarAccess.getEnumerationLiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEnumerationLiteral=ruleEnumerationLiteral();

            state._fsp--;

             current =iv_ruleEnumerationLiteral; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEnumerationLiteral"


    // $ANTLR start "ruleEnumerationLiteral"
    // InternalThingML.g:706:1: ruleEnumerationLiteral returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleEnumerationLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        EObject lv_annotations_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:712:2: ( ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:713:2: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:713:2: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:714:3: ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:714:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:715:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:715:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:716:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_17); 

            					newLeafNode(lv_name_0_0, grammarAccess.getEnumerationLiteralAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEnumerationLiteralRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:732:3: ( (lv_annotations_1_0= rulePlatformAnnotation ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==RULE_ANNOTATION_ID) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalThingML.g:733:4: (lv_annotations_1_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:733:4: (lv_annotations_1_0= rulePlatformAnnotation )
            	    // InternalThingML.g:734:5: lv_annotations_1_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getEnumerationLiteralAccess().getAnnotationsPlatformAnnotationParserRuleCall_1_0());
            	    				
            	    pushFollow(FOLLOW_17);
            	    lv_annotations_1_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getEnumerationLiteralRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_1_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEnumerationLiteral"


    // $ANTLR start "entryRuleThing"
    // InternalThingML.g:755:1: entryRuleThing returns [EObject current=null] : iv_ruleThing= ruleThing EOF ;
    public final EObject entryRuleThing() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThing = null;


        try {
            // InternalThingML.g:755:46: (iv_ruleThing= ruleThing EOF )
            // InternalThingML.g:756:2: iv_ruleThing= ruleThing EOF
            {
             newCompositeNode(grammarAccess.getThingRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleThing=ruleThing();

            state._fsp--;

             current =iv_ruleThing; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleThing"


    // $ANTLR start "ruleThing"
    // InternalThingML.g:762:1: ruleThing returns [EObject current=null] : (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) )* otherlv_15= '}' ) ;
    public final EObject ruleThing() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_fragment_1_0=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_15=null;
        EObject lv_annotations_7_0 = null;

        EObject lv_messages_9_0 = null;

        EObject lv_ports_10_0 = null;

        EObject lv_properties_11_0 = null;

        EObject lv_functions_12_0 = null;

        EObject lv_assign_13_0 = null;

        EObject lv_behaviour_14_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:768:2: ( (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) )* otherlv_15= '}' ) )
            // InternalThingML.g:769:2: (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) )* otherlv_15= '}' )
            {
            // InternalThingML.g:769:2: (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) )* otherlv_15= '}' )
            // InternalThingML.g:770:3: otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) )* otherlv_15= '}'
            {
            otherlv_0=(Token)match(input,27,FOLLOW_18); 

            			newLeafNode(otherlv_0, grammarAccess.getThingAccess().getThingKeyword_0());
            		
            // InternalThingML.g:774:3: ( (lv_fragment_1_0= 'fragment' ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==28) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalThingML.g:775:4: (lv_fragment_1_0= 'fragment' )
                    {
                    // InternalThingML.g:775:4: (lv_fragment_1_0= 'fragment' )
                    // InternalThingML.g:776:5: lv_fragment_1_0= 'fragment'
                    {
                    lv_fragment_1_0=(Token)match(input,28,FOLLOW_6); 

                    					newLeafNode(lv_fragment_1_0, grammarAccess.getThingAccess().getFragmentFragmentKeyword_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getThingRule());
                    					}
                    					setWithLastConsumed(current, "fragment", true, "fragment");
                    				

                    }


                    }
                    break;

            }

            // InternalThingML.g:788:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:789:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:789:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:790:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_19); 

            					newLeafNode(lv_name_2_0, grammarAccess.getThingAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getThingRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:806:3: (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==29) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalThingML.g:807:4: otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )*
                    {
                    otherlv_3=(Token)match(input,29,FOLLOW_6); 

                    				newLeafNode(otherlv_3, grammarAccess.getThingAccess().getIncludesKeyword_3_0());
                    			
                    // InternalThingML.g:811:4: ( (otherlv_4= RULE_ID ) )
                    // InternalThingML.g:812:5: (otherlv_4= RULE_ID )
                    {
                    // InternalThingML.g:812:5: (otherlv_4= RULE_ID )
                    // InternalThingML.g:813:6: otherlv_4= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getThingRule());
                    						}
                    					
                    otherlv_4=(Token)match(input,RULE_ID,FOLLOW_20); 

                    						newLeafNode(otherlv_4, grammarAccess.getThingAccess().getIncludesThingCrossReference_3_1_0());
                    					

                    }


                    }

                    // InternalThingML.g:824:4: (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0==30) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // InternalThingML.g:825:5: otherlv_5= ',' ( (otherlv_6= RULE_ID ) )
                    	    {
                    	    otherlv_5=(Token)match(input,30,FOLLOW_6); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getThingAccess().getCommaKeyword_3_2_0());
                    	    				
                    	    // InternalThingML.g:829:5: ( (otherlv_6= RULE_ID ) )
                    	    // InternalThingML.g:830:6: (otherlv_6= RULE_ID )
                    	    {
                    	    // InternalThingML.g:830:6: (otherlv_6= RULE_ID )
                    	    // InternalThingML.g:831:7: otherlv_6= RULE_ID
                    	    {

                    	    							if (current==null) {
                    	    								current = createModelElement(grammarAccess.getThingRule());
                    	    							}
                    	    						
                    	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_20); 

                    	    							newLeafNode(otherlv_6, grammarAccess.getThingAccess().getIncludesThingCrossReference_3_2_1_0());
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);


                    }
                    break;

            }

            // InternalThingML.g:844:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==RULE_ANNOTATION_ID) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalThingML.g:845:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:845:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:846:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getThingAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_annotations_7_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getThingRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_7_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            otherlv_8=(Token)match(input,25,FOLLOW_21); 

            			newLeafNode(otherlv_8, grammarAccess.getThingAccess().getLeftCurlyBracketKeyword_5());
            		
            // InternalThingML.g:867:3: ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) )*
            loop18:
            do {
                int alt18=7;
                switch ( input.LA(1) ) {
                case 40:
                    {
                    alt18=1;
                    }
                    break;
                case 41:
                case 42:
                case 46:
                case 47:
                    {
                    alt18=2;
                    }
                    break;
                case 38:
                case 39:
                    {
                    alt18=3;
                    }
                    break;
                case 34:
                case 37:
                    {
                    alt18=4;
                    }
                    break;
                case 31:
                    {
                    alt18=5;
                    }
                    break;
                case 61:
                    {
                    alt18=6;
                    }
                    break;

                }

                switch (alt18) {
            	case 1 :
            	    // InternalThingML.g:868:4: ( (lv_messages_9_0= ruleMessage ) )
            	    {
            	    // InternalThingML.g:868:4: ( (lv_messages_9_0= ruleMessage ) )
            	    // InternalThingML.g:869:5: (lv_messages_9_0= ruleMessage )
            	    {
            	    // InternalThingML.g:869:5: (lv_messages_9_0= ruleMessage )
            	    // InternalThingML.g:870:6: lv_messages_9_0= ruleMessage
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getMessagesMessageParserRuleCall_6_0_0());
            	    					
            	    pushFollow(FOLLOW_21);
            	    lv_messages_9_0=ruleMessage();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingRule());
            	    						}
            	    						add(
            	    							current,
            	    							"messages",
            	    							lv_messages_9_0,
            	    							"org.thingml.xtext.ThingML.Message");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:888:4: ( (lv_ports_10_0= rulePort ) )
            	    {
            	    // InternalThingML.g:888:4: ( (lv_ports_10_0= rulePort ) )
            	    // InternalThingML.g:889:5: (lv_ports_10_0= rulePort )
            	    {
            	    // InternalThingML.g:889:5: (lv_ports_10_0= rulePort )
            	    // InternalThingML.g:890:6: lv_ports_10_0= rulePort
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getPortsPortParserRuleCall_6_1_0());
            	    					
            	    pushFollow(FOLLOW_21);
            	    lv_ports_10_0=rulePort();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingRule());
            	    						}
            	    						add(
            	    							current,
            	    							"ports",
            	    							lv_ports_10_0,
            	    							"org.thingml.xtext.ThingML.Port");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalThingML.g:908:4: ( (lv_properties_11_0= ruleProperty ) )
            	    {
            	    // InternalThingML.g:908:4: ( (lv_properties_11_0= ruleProperty ) )
            	    // InternalThingML.g:909:5: (lv_properties_11_0= ruleProperty )
            	    {
            	    // InternalThingML.g:909:5: (lv_properties_11_0= ruleProperty )
            	    // InternalThingML.g:910:6: lv_properties_11_0= ruleProperty
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getPropertiesPropertyParserRuleCall_6_2_0());
            	    					
            	    pushFollow(FOLLOW_21);
            	    lv_properties_11_0=ruleProperty();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingRule());
            	    						}
            	    						add(
            	    							current,
            	    							"properties",
            	    							lv_properties_11_0,
            	    							"org.thingml.xtext.ThingML.Property");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalThingML.g:928:4: ( (lv_functions_12_0= ruleFunction ) )
            	    {
            	    // InternalThingML.g:928:4: ( (lv_functions_12_0= ruleFunction ) )
            	    // InternalThingML.g:929:5: (lv_functions_12_0= ruleFunction )
            	    {
            	    // InternalThingML.g:929:5: (lv_functions_12_0= ruleFunction )
            	    // InternalThingML.g:930:6: lv_functions_12_0= ruleFunction
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getFunctionsFunctionParserRuleCall_6_3_0());
            	    					
            	    pushFollow(FOLLOW_21);
            	    lv_functions_12_0=ruleFunction();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingRule());
            	    						}
            	    						add(
            	    							current,
            	    							"functions",
            	    							lv_functions_12_0,
            	    							"org.thingml.xtext.ThingML.Function");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 5 :
            	    // InternalThingML.g:948:4: ( (lv_assign_13_0= rulePropertyAssign ) )
            	    {
            	    // InternalThingML.g:948:4: ( (lv_assign_13_0= rulePropertyAssign ) )
            	    // InternalThingML.g:949:5: (lv_assign_13_0= rulePropertyAssign )
            	    {
            	    // InternalThingML.g:949:5: (lv_assign_13_0= rulePropertyAssign )
            	    // InternalThingML.g:950:6: lv_assign_13_0= rulePropertyAssign
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getAssignPropertyAssignParserRuleCall_6_4_0());
            	    					
            	    pushFollow(FOLLOW_21);
            	    lv_assign_13_0=rulePropertyAssign();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingRule());
            	    						}
            	    						add(
            	    							current,
            	    							"assign",
            	    							lv_assign_13_0,
            	    							"org.thingml.xtext.ThingML.PropertyAssign");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 6 :
            	    // InternalThingML.g:968:4: ( (lv_behaviour_14_0= ruleStateMachine ) )
            	    {
            	    // InternalThingML.g:968:4: ( (lv_behaviour_14_0= ruleStateMachine ) )
            	    // InternalThingML.g:969:5: (lv_behaviour_14_0= ruleStateMachine )
            	    {
            	    // InternalThingML.g:969:5: (lv_behaviour_14_0= ruleStateMachine )
            	    // InternalThingML.g:970:6: lv_behaviour_14_0= ruleStateMachine
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getBehaviourStateMachineParserRuleCall_6_5_0());
            	    					
            	    pushFollow(FOLLOW_21);
            	    lv_behaviour_14_0=ruleStateMachine();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingRule());
            	    						}
            	    						add(
            	    							current,
            	    							"behaviour",
            	    							lv_behaviour_14_0,
            	    							"org.thingml.xtext.ThingML.StateMachine");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            otherlv_15=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_15, grammarAccess.getThingAccess().getRightCurlyBracketKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleThing"


    // $ANTLR start "entryRulePropertyAssign"
    // InternalThingML.g:996:1: entryRulePropertyAssign returns [EObject current=null] : iv_rulePropertyAssign= rulePropertyAssign EOF ;
    public final EObject entryRulePropertyAssign() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePropertyAssign = null;


        try {
            // InternalThingML.g:996:55: (iv_rulePropertyAssign= rulePropertyAssign EOF )
            // InternalThingML.g:997:2: iv_rulePropertyAssign= rulePropertyAssign EOF
            {
             newCompositeNode(grammarAccess.getPropertyAssignRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePropertyAssign=rulePropertyAssign();

            state._fsp--;

             current =iv_rulePropertyAssign; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePropertyAssign"


    // $ANTLR start "rulePropertyAssign"
    // InternalThingML.g:1003:1: rulePropertyAssign returns [EObject current=null] : (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
    public final EObject rulePropertyAssign() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        EObject lv_index_3_0 = null;

        EObject lv_init_6_0 = null;

        EObject lv_annotations_7_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1009:2: ( (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:1010:2: (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:1010:2: (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:1011:3: otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,31,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getPropertyAssignAccess().getSetKeyword_0());
            		
            // InternalThingML.g:1015:3: ( (otherlv_1= RULE_ID ) )
            // InternalThingML.g:1016:4: (otherlv_1= RULE_ID )
            {
            // InternalThingML.g:1016:4: (otherlv_1= RULE_ID )
            // InternalThingML.g:1017:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPropertyAssignRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_22); 

            					newLeafNode(otherlv_1, grammarAccess.getPropertyAssignAccess().getPropertyPropertyCrossReference_1_0());
            				

            }


            }

            // InternalThingML.g:1028:3: (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==17) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalThingML.g:1029:4: otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']'
            	    {
            	    otherlv_2=(Token)match(input,17,FOLLOW_23); 

            	    				newLeafNode(otherlv_2, grammarAccess.getPropertyAssignAccess().getLeftSquareBracketKeyword_2_0());
            	    			
            	    // InternalThingML.g:1033:4: ( (lv_index_3_0= ruleExpression ) )
            	    // InternalThingML.g:1034:5: (lv_index_3_0= ruleExpression )
            	    {
            	    // InternalThingML.g:1034:5: (lv_index_3_0= ruleExpression )
            	    // InternalThingML.g:1035:6: lv_index_3_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getPropertyAssignAccess().getIndexExpressionParserRuleCall_2_1_0());
            	    					
            	    pushFollow(FOLLOW_10);
            	    lv_index_3_0=ruleExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getPropertyAssignRule());
            	    						}
            	    						add(
            	    							current,
            	    							"index",
            	    							lv_index_3_0,
            	    							"org.thingml.xtext.ThingML.Expression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    otherlv_4=(Token)match(input,18,FOLLOW_22); 

            	    				newLeafNode(otherlv_4, grammarAccess.getPropertyAssignAccess().getRightSquareBracketKeyword_2_2());
            	    			

            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

            otherlv_5=(Token)match(input,32,FOLLOW_23); 

            			newLeafNode(otherlv_5, grammarAccess.getPropertyAssignAccess().getEqualsSignKeyword_3());
            		
            // InternalThingML.g:1061:3: ( (lv_init_6_0= ruleExpression ) )
            // InternalThingML.g:1062:4: (lv_init_6_0= ruleExpression )
            {
            // InternalThingML.g:1062:4: (lv_init_6_0= ruleExpression )
            // InternalThingML.g:1063:5: lv_init_6_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getPropertyAssignAccess().getInitExpressionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_17);
            lv_init_6_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPropertyAssignRule());
            					}
            					set(
            						current,
            						"init",
            						lv_init_6_0,
            						"org.thingml.xtext.ThingML.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:1080:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==RULE_ANNOTATION_ID) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalThingML.g:1081:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1081:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1082:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getPropertyAssignAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_17);
            	    lv_annotations_7_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getPropertyAssignRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_7_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePropertyAssign"


    // $ANTLR start "entryRuleProtocol"
    // InternalThingML.g:1103:1: entryRuleProtocol returns [EObject current=null] : iv_ruleProtocol= ruleProtocol EOF ;
    public final EObject entryRuleProtocol() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProtocol = null;


        try {
            // InternalThingML.g:1103:49: (iv_ruleProtocol= ruleProtocol EOF )
            // InternalThingML.g:1104:2: iv_ruleProtocol= ruleProtocol EOF
            {
             newCompositeNode(grammarAccess.getProtocolRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleProtocol=ruleProtocol();

            state._fsp--;

             current =iv_ruleProtocol; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleProtocol"


    // $ANTLR start "ruleProtocol"
    // InternalThingML.g:1110:1: ruleProtocol returns [EObject current=null] : (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' ) ;
    public final EObject ruleProtocol() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        EObject lv_annotations_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1116:2: ( (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' ) )
            // InternalThingML.g:1117:2: (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' )
            {
            // InternalThingML.g:1117:2: (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' )
            // InternalThingML.g:1118:3: otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';'
            {
            otherlv_0=(Token)match(input,33,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getProtocolAccess().getProtocolKeyword_0());
            		
            // InternalThingML.g:1122:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1123:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1123:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1124:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_24); 

            					newLeafNode(lv_name_1_0, grammarAccess.getProtocolAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProtocolRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:1140:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==RULE_ANNOTATION_ID) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalThingML.g:1141:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1141:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1142:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getProtocolAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_24);
            	    lv_annotations_2_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getProtocolRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_2_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

            otherlv_3=(Token)match(input,22,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getProtocolAccess().getSemicolonKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProtocol"


    // $ANTLR start "entryRuleFunction"
    // InternalThingML.g:1167:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalThingML.g:1167:49: (iv_ruleFunction= ruleFunction EOF )
            // InternalThingML.g:1168:2: iv_ruleFunction= ruleFunction EOF
            {
             newCompositeNode(grammarAccess.getFunctionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFunction=ruleFunction();

            state._fsp--;

             current =iv_ruleFunction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunction"


    // $ANTLR start "ruleFunction"
    // InternalThingML.g:1174:1: ruleFunction returns [EObject current=null] : ( (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) ) | this_AbstractFunction_11= ruleAbstractFunction ) ;
    public final EObject ruleFunction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;

        EObject lv_typeRef_8_0 = null;

        EObject lv_annotations_9_0 = null;

        EObject lv_body_10_0 = null;

        EObject this_AbstractFunction_11 = null;



        	enterRule();

        try {
            // InternalThingML.g:1180:2: ( ( (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) ) | this_AbstractFunction_11= ruleAbstractFunction ) )
            // InternalThingML.g:1181:2: ( (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) ) | this_AbstractFunction_11= ruleAbstractFunction )
            {
            // InternalThingML.g:1181:2: ( (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) ) | this_AbstractFunction_11= ruleAbstractFunction )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==34) ) {
                alt26=1;
            }
            else if ( (LA26_0==37) ) {
                alt26=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // InternalThingML.g:1182:3: (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) )
                    {
                    // InternalThingML.g:1182:3: (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) )
                    // InternalThingML.g:1183:4: otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) )
                    {
                    otherlv_0=(Token)match(input,34,FOLLOW_6); 

                    				newLeafNode(otherlv_0, grammarAccess.getFunctionAccess().getFunctionKeyword_0_0());
                    			
                    // InternalThingML.g:1187:4: ( (lv_name_1_0= RULE_ID ) )
                    // InternalThingML.g:1188:5: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:1188:5: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:1189:6: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_25); 

                    						newLeafNode(lv_name_1_0, grammarAccess.getFunctionAccess().getNameIDTerminalRuleCall_0_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getFunctionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"name",
                    							lv_name_1_0,
                    							"org.thingml.xtext.ThingML.ID");
                    					

                    }


                    }

                    otherlv_2=(Token)match(input,35,FOLLOW_26); 

                    				newLeafNode(otherlv_2, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_0_2());
                    			
                    // InternalThingML.g:1209:4: ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==RULE_ID) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalThingML.g:1210:5: ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                            {
                            // InternalThingML.g:1210:5: ( (lv_parameters_3_0= ruleParameter ) )
                            // InternalThingML.g:1211:6: (lv_parameters_3_0= ruleParameter )
                            {
                            // InternalThingML.g:1211:6: (lv_parameters_3_0= ruleParameter )
                            // InternalThingML.g:1212:7: lv_parameters_3_0= ruleParameter
                            {

                            							newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_0_3_0_0());
                            						
                            pushFollow(FOLLOW_27);
                            lv_parameters_3_0=ruleParameter();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getFunctionRule());
                            							}
                            							add(
                            								current,
                            								"parameters",
                            								lv_parameters_3_0,
                            								"org.thingml.xtext.ThingML.Parameter");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }

                            // InternalThingML.g:1229:5: (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                            loop22:
                            do {
                                int alt22=2;
                                int LA22_0 = input.LA(1);

                                if ( (LA22_0==30) ) {
                                    alt22=1;
                                }


                                switch (alt22) {
                            	case 1 :
                            	    // InternalThingML.g:1230:6: otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) )
                            	    {
                            	    otherlv_4=(Token)match(input,30,FOLLOW_6); 

                            	    						newLeafNode(otherlv_4, grammarAccess.getFunctionAccess().getCommaKeyword_0_3_1_0());
                            	    					
                            	    // InternalThingML.g:1234:6: ( (lv_parameters_5_0= ruleParameter ) )
                            	    // InternalThingML.g:1235:7: (lv_parameters_5_0= ruleParameter )
                            	    {
                            	    // InternalThingML.g:1235:7: (lv_parameters_5_0= ruleParameter )
                            	    // InternalThingML.g:1236:8: lv_parameters_5_0= ruleParameter
                            	    {

                            	    								newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_0_3_1_1_0());
                            	    							
                            	    pushFollow(FOLLOW_27);
                            	    lv_parameters_5_0=ruleParameter();

                            	    state._fsp--;


                            	    								if (current==null) {
                            	    									current = createModelElementForParent(grammarAccess.getFunctionRule());
                            	    								}
                            	    								add(
                            	    									current,
                            	    									"parameters",
                            	    									lv_parameters_5_0,
                            	    									"org.thingml.xtext.ThingML.Parameter");
                            	    								afterParserOrEnumRuleCall();
                            	    							

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop22;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_6=(Token)match(input,36,FOLLOW_28); 

                    				newLeafNode(otherlv_6, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_0_4());
                    			
                    // InternalThingML.g:1259:4: (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==16) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // InternalThingML.g:1260:5: otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) )
                            {
                            otherlv_7=(Token)match(input,16,FOLLOW_6); 

                            					newLeafNode(otherlv_7, grammarAccess.getFunctionAccess().getColonKeyword_0_5_0());
                            				
                            // InternalThingML.g:1264:5: ( (lv_typeRef_8_0= ruleTypeRef ) )
                            // InternalThingML.g:1265:6: (lv_typeRef_8_0= ruleTypeRef )
                            {
                            // InternalThingML.g:1265:6: (lv_typeRef_8_0= ruleTypeRef )
                            // InternalThingML.g:1266:7: lv_typeRef_8_0= ruleTypeRef
                            {

                            							newCompositeNode(grammarAccess.getFunctionAccess().getTypeRefTypeRefParserRuleCall_0_5_1_0());
                            						
                            pushFollow(FOLLOW_28);
                            lv_typeRef_8_0=ruleTypeRef();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getFunctionRule());
                            							}
                            							set(
                            								current,
                            								"typeRef",
                            								lv_typeRef_8_0,
                            								"org.thingml.xtext.ThingML.TypeRef");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }


                            }
                            break;

                    }

                    // InternalThingML.g:1284:4: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==RULE_ANNOTATION_ID) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // InternalThingML.g:1285:5: (lv_annotations_9_0= rulePlatformAnnotation )
                    	    {
                    	    // InternalThingML.g:1285:5: (lv_annotations_9_0= rulePlatformAnnotation )
                    	    // InternalThingML.g:1286:6: lv_annotations_9_0= rulePlatformAnnotation
                    	    {

                    	    						newCompositeNode(grammarAccess.getFunctionAccess().getAnnotationsPlatformAnnotationParserRuleCall_0_6_0());
                    	    					
                    	    pushFollow(FOLLOW_28);
                    	    lv_annotations_9_0=rulePlatformAnnotation();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getFunctionRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"annotations",
                    	    							lv_annotations_9_0,
                    	    							"org.thingml.xtext.ThingML.PlatformAnnotation");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);

                    // InternalThingML.g:1303:4: ( (lv_body_10_0= ruleAction ) )
                    // InternalThingML.g:1304:5: (lv_body_10_0= ruleAction )
                    {
                    // InternalThingML.g:1304:5: (lv_body_10_0= ruleAction )
                    // InternalThingML.g:1305:6: lv_body_10_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getFunctionAccess().getBodyActionParserRuleCall_0_7_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_body_10_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFunctionRule());
                    						}
                    						set(
                    							current,
                    							"body",
                    							lv_body_10_0,
                    							"org.thingml.xtext.ThingML.Action");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalThingML.g:1324:3: this_AbstractFunction_11= ruleAbstractFunction
                    {

                    			newCompositeNode(grammarAccess.getFunctionAccess().getAbstractFunctionParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_AbstractFunction_11=ruleAbstractFunction();

                    state._fsp--;


                    			current = this_AbstractFunction_11;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFunction"


    // $ANTLR start "entryRuleAbstractFunction"
    // InternalThingML.g:1336:1: entryRuleAbstractFunction returns [EObject current=null] : iv_ruleAbstractFunction= ruleAbstractFunction EOF ;
    public final EObject entryRuleAbstractFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAbstractFunction = null;


        try {
            // InternalThingML.g:1336:57: (iv_ruleAbstractFunction= ruleAbstractFunction EOF )
            // InternalThingML.g:1337:2: iv_ruleAbstractFunction= ruleAbstractFunction EOF
            {
             newCompositeNode(grammarAccess.getAbstractFunctionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAbstractFunction=ruleAbstractFunction();

            state._fsp--;

             current =iv_ruleAbstractFunction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAbstractFunction"


    // $ANTLR start "ruleAbstractFunction"
    // InternalThingML.g:1343:1: ruleAbstractFunction returns [EObject current=null] : ( ( (lv_abstract_0_0= 'abstract' ) ) otherlv_1= 'function' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )? otherlv_7= ')' (otherlv_8= ':' ( (lv_typeRef_9_0= ruleTypeRef ) ) )? ( (lv_annotations_10_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleAbstractFunction() throws RecognitionException {
        EObject current = null;

        Token lv_abstract_0_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        EObject lv_parameters_4_0 = null;

        EObject lv_parameters_6_0 = null;

        EObject lv_typeRef_9_0 = null;

        EObject lv_annotations_10_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1349:2: ( ( ( (lv_abstract_0_0= 'abstract' ) ) otherlv_1= 'function' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )? otherlv_7= ')' (otherlv_8= ':' ( (lv_typeRef_9_0= ruleTypeRef ) ) )? ( (lv_annotations_10_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:1350:2: ( ( (lv_abstract_0_0= 'abstract' ) ) otherlv_1= 'function' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )? otherlv_7= ')' (otherlv_8= ':' ( (lv_typeRef_9_0= ruleTypeRef ) ) )? ( (lv_annotations_10_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:1350:2: ( ( (lv_abstract_0_0= 'abstract' ) ) otherlv_1= 'function' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )? otherlv_7= ')' (otherlv_8= ':' ( (lv_typeRef_9_0= ruleTypeRef ) ) )? ( (lv_annotations_10_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:1351:3: ( (lv_abstract_0_0= 'abstract' ) ) otherlv_1= 'function' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )? otherlv_7= ')' (otherlv_8= ':' ( (lv_typeRef_9_0= ruleTypeRef ) ) )? ( (lv_annotations_10_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:1351:3: ( (lv_abstract_0_0= 'abstract' ) )
            // InternalThingML.g:1352:4: (lv_abstract_0_0= 'abstract' )
            {
            // InternalThingML.g:1352:4: (lv_abstract_0_0= 'abstract' )
            // InternalThingML.g:1353:5: lv_abstract_0_0= 'abstract'
            {
            lv_abstract_0_0=(Token)match(input,37,FOLLOW_29); 

            					newLeafNode(lv_abstract_0_0, grammarAccess.getAbstractFunctionAccess().getAbstractAbstractKeyword_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getAbstractFunctionRule());
            					}
            					setWithLastConsumed(current, "abstract", true, "abstract");
            				

            }


            }

            otherlv_1=(Token)match(input,34,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getAbstractFunctionAccess().getFunctionKeyword_1());
            		
            // InternalThingML.g:1369:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:1370:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:1370:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:1371:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_25); 

            					newLeafNode(lv_name_2_0, grammarAccess.getAbstractFunctionAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getAbstractFunctionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_3=(Token)match(input,35,FOLLOW_26); 

            			newLeafNode(otherlv_3, grammarAccess.getAbstractFunctionAccess().getLeftParenthesisKeyword_3());
            		
            // InternalThingML.g:1391:3: ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==RULE_ID) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalThingML.g:1392:4: ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )*
                    {
                    // InternalThingML.g:1392:4: ( (lv_parameters_4_0= ruleParameter ) )
                    // InternalThingML.g:1393:5: (lv_parameters_4_0= ruleParameter )
                    {
                    // InternalThingML.g:1393:5: (lv_parameters_4_0= ruleParameter )
                    // InternalThingML.g:1394:6: lv_parameters_4_0= ruleParameter
                    {

                    						newCompositeNode(grammarAccess.getAbstractFunctionAccess().getParametersParameterParserRuleCall_4_0_0());
                    					
                    pushFollow(FOLLOW_27);
                    lv_parameters_4_0=ruleParameter();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getAbstractFunctionRule());
                    						}
                    						add(
                    							current,
                    							"parameters",
                    							lv_parameters_4_0,
                    							"org.thingml.xtext.ThingML.Parameter");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalThingML.g:1411:4: (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )*
                    loop27:
                    do {
                        int alt27=2;
                        int LA27_0 = input.LA(1);

                        if ( (LA27_0==30) ) {
                            alt27=1;
                        }


                        switch (alt27) {
                    	case 1 :
                    	    // InternalThingML.g:1412:5: otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) )
                    	    {
                    	    otherlv_5=(Token)match(input,30,FOLLOW_6); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getAbstractFunctionAccess().getCommaKeyword_4_1_0());
                    	    				
                    	    // InternalThingML.g:1416:5: ( (lv_parameters_6_0= ruleParameter ) )
                    	    // InternalThingML.g:1417:6: (lv_parameters_6_0= ruleParameter )
                    	    {
                    	    // InternalThingML.g:1417:6: (lv_parameters_6_0= ruleParameter )
                    	    // InternalThingML.g:1418:7: lv_parameters_6_0= ruleParameter
                    	    {

                    	    							newCompositeNode(grammarAccess.getAbstractFunctionAccess().getParametersParameterParserRuleCall_4_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_27);
                    	    lv_parameters_6_0=ruleParameter();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getAbstractFunctionRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"parameters",
                    	    								lv_parameters_6_0,
                    	    								"org.thingml.xtext.ThingML.Parameter");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop27;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_7=(Token)match(input,36,FOLLOW_30); 

            			newLeafNode(otherlv_7, grammarAccess.getAbstractFunctionAccess().getRightParenthesisKeyword_5());
            		
            // InternalThingML.g:1441:3: (otherlv_8= ':' ( (lv_typeRef_9_0= ruleTypeRef ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==16) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalThingML.g:1442:4: otherlv_8= ':' ( (lv_typeRef_9_0= ruleTypeRef ) )
                    {
                    otherlv_8=(Token)match(input,16,FOLLOW_6); 

                    				newLeafNode(otherlv_8, grammarAccess.getAbstractFunctionAccess().getColonKeyword_6_0());
                    			
                    // InternalThingML.g:1446:4: ( (lv_typeRef_9_0= ruleTypeRef ) )
                    // InternalThingML.g:1447:5: (lv_typeRef_9_0= ruleTypeRef )
                    {
                    // InternalThingML.g:1447:5: (lv_typeRef_9_0= ruleTypeRef )
                    // InternalThingML.g:1448:6: lv_typeRef_9_0= ruleTypeRef
                    {

                    						newCompositeNode(grammarAccess.getAbstractFunctionAccess().getTypeRefTypeRefParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_typeRef_9_0=ruleTypeRef();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getAbstractFunctionRule());
                    						}
                    						set(
                    							current,
                    							"typeRef",
                    							lv_typeRef_9_0,
                    							"org.thingml.xtext.ThingML.TypeRef");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:1466:3: ( (lv_annotations_10_0= rulePlatformAnnotation ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==RULE_ANNOTATION_ID) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalThingML.g:1467:4: (lv_annotations_10_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1467:4: (lv_annotations_10_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1468:5: lv_annotations_10_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getAbstractFunctionAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_17);
            	    lv_annotations_10_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getAbstractFunctionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_10_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAbstractFunction"


    // $ANTLR start "entryRuleProperty"
    // InternalThingML.g:1489:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // InternalThingML.g:1489:49: (iv_ruleProperty= ruleProperty EOF )
            // InternalThingML.g:1490:2: iv_ruleProperty= ruleProperty EOF
            {
             newCompositeNode(grammarAccess.getPropertyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleProperty=ruleProperty();

            state._fsp--;

             current =iv_ruleProperty; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleProperty"


    // $ANTLR start "ruleProperty"
    // InternalThingML.g:1496:1: ruleProperty returns [EObject current=null] : ( ( (lv_readonly_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        Token lv_readonly_0_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_typeRef_4_0 = null;

        EObject lv_init_6_0 = null;

        EObject lv_annotations_7_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1502:2: ( ( ( (lv_readonly_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:1503:2: ( ( (lv_readonly_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:1503:2: ( ( (lv_readonly_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:1504:3: ( (lv_readonly_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:1504:3: ( (lv_readonly_0_0= 'readonly' ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==38) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalThingML.g:1505:4: (lv_readonly_0_0= 'readonly' )
                    {
                    // InternalThingML.g:1505:4: (lv_readonly_0_0= 'readonly' )
                    // InternalThingML.g:1506:5: lv_readonly_0_0= 'readonly'
                    {
                    lv_readonly_0_0=(Token)match(input,38,FOLLOW_31); 

                    					newLeafNode(lv_readonly_0_0, grammarAccess.getPropertyAccess().getReadonlyReadonlyKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getPropertyRule());
                    					}
                    					setWithLastConsumed(current, "readonly", true, "readonly");
                    				

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,39,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getPropertyAccess().getPropertyKeyword_1());
            		
            // InternalThingML.g:1522:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:1523:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:1523:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:1524:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(lv_name_2_0, grammarAccess.getPropertyAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPropertyRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_3=(Token)match(input,16,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getPropertyAccess().getColonKeyword_3());
            		
            // InternalThingML.g:1544:3: ( (lv_typeRef_4_0= ruleTypeRef ) )
            // InternalThingML.g:1545:4: (lv_typeRef_4_0= ruleTypeRef )
            {
            // InternalThingML.g:1545:4: (lv_typeRef_4_0= ruleTypeRef )
            // InternalThingML.g:1546:5: lv_typeRef_4_0= ruleTypeRef
            {

            					newCompositeNode(grammarAccess.getPropertyAccess().getTypeRefTypeRefParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_32);
            lv_typeRef_4_0=ruleTypeRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPropertyRule());
            					}
            					set(
            						current,
            						"typeRef",
            						lv_typeRef_4_0,
            						"org.thingml.xtext.ThingML.TypeRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:1563:3: (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==32) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalThingML.g:1564:4: otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) )
                    {
                    otherlv_5=(Token)match(input,32,FOLLOW_23); 

                    				newLeafNode(otherlv_5, grammarAccess.getPropertyAccess().getEqualsSignKeyword_5_0());
                    			
                    // InternalThingML.g:1568:4: ( (lv_init_6_0= ruleExpression ) )
                    // InternalThingML.g:1569:5: (lv_init_6_0= ruleExpression )
                    {
                    // InternalThingML.g:1569:5: (lv_init_6_0= ruleExpression )
                    // InternalThingML.g:1570:6: lv_init_6_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getPropertyAccess().getInitExpressionParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_init_6_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPropertyRule());
                    						}
                    						set(
                    							current,
                    							"init",
                    							lv_init_6_0,
                    							"org.thingml.xtext.ThingML.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:1588:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==RULE_ANNOTATION_ID) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalThingML.g:1589:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1589:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1590:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getPropertyAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_17);
            	    lv_annotations_7_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getPropertyRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_7_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProperty"


    // $ANTLR start "entryRuleMessage"
    // InternalThingML.g:1611:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // InternalThingML.g:1611:48: (iv_ruleMessage= ruleMessage EOF )
            // InternalThingML.g:1612:2: iv_ruleMessage= ruleMessage EOF
            {
             newCompositeNode(grammarAccess.getMessageRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMessage=ruleMessage();

            state._fsp--;

             current =iv_ruleMessage; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMessage"


    // $ANTLR start "ruleMessage"
    // InternalThingML.g:1618:1: ruleMessage returns [EObject current=null] : (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )? ) ;
    public final EObject ruleMessage() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;

        EObject lv_annotations_7_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1624:2: ( (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )? ) )
            // InternalThingML.g:1625:2: (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )? )
            {
            // InternalThingML.g:1625:2: (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )? )
            // InternalThingML.g:1626:3: otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )?
            {
            otherlv_0=(Token)match(input,40,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getMessageAccess().getMessageKeyword_0());
            		
            // InternalThingML.g:1630:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1631:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1631:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1632:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_25); 

            					newLeafNode(lv_name_1_0, grammarAccess.getMessageAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMessageRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,35,FOLLOW_26); 

            			newLeafNode(otherlv_2, grammarAccess.getMessageAccess().getLeftParenthesisKeyword_2());
            		
            // InternalThingML.g:1652:3: ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==RULE_ID) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalThingML.g:1653:4: ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    {
                    // InternalThingML.g:1653:4: ( (lv_parameters_3_0= ruleParameter ) )
                    // InternalThingML.g:1654:5: (lv_parameters_3_0= ruleParameter )
                    {
                    // InternalThingML.g:1654:5: (lv_parameters_3_0= ruleParameter )
                    // InternalThingML.g:1655:6: lv_parameters_3_0= ruleParameter
                    {

                    						newCompositeNode(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_0_0());
                    					
                    pushFollow(FOLLOW_27);
                    lv_parameters_3_0=ruleParameter();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getMessageRule());
                    						}
                    						add(
                    							current,
                    							"parameters",
                    							lv_parameters_3_0,
                    							"org.thingml.xtext.ThingML.Parameter");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalThingML.g:1672:4: (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( (LA34_0==30) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // InternalThingML.g:1673:5: otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) )
                    	    {
                    	    otherlv_4=(Token)match(input,30,FOLLOW_6); 

                    	    					newLeafNode(otherlv_4, grammarAccess.getMessageAccess().getCommaKeyword_3_1_0());
                    	    				
                    	    // InternalThingML.g:1677:5: ( (lv_parameters_5_0= ruleParameter ) )
                    	    // InternalThingML.g:1678:6: (lv_parameters_5_0= ruleParameter )
                    	    {
                    	    // InternalThingML.g:1678:6: (lv_parameters_5_0= ruleParameter )
                    	    // InternalThingML.g:1679:7: lv_parameters_5_0= ruleParameter
                    	    {

                    	    							newCompositeNode(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_27);
                    	    lv_parameters_5_0=ruleParameter();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getMessageRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"parameters",
                    	    								lv_parameters_5_0,
                    	    								"org.thingml.xtext.ThingML.Parameter");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop34;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,36,FOLLOW_14); 

            			newLeafNode(otherlv_6, grammarAccess.getMessageAccess().getRightParenthesisKeyword_4());
            		
            // InternalThingML.g:1702:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==RULE_ANNOTATION_ID) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalThingML.g:1703:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1703:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1704:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getMessageAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_14);
            	    lv_annotations_7_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getMessageRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_7_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);

            // InternalThingML.g:1721:3: (otherlv_8= ';' )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==22) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalThingML.g:1722:4: otherlv_8= ';'
                    {
                    otherlv_8=(Token)match(input,22,FOLLOW_2); 

                    				newLeafNode(otherlv_8, grammarAccess.getMessageAccess().getSemicolonKeyword_6());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMessage"


    // $ANTLR start "entryRuleParameter"
    // InternalThingML.g:1731:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalThingML.g:1731:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalThingML.g:1732:2: iv_ruleParameter= ruleParameter EOF
            {
             newCompositeNode(grammarAccess.getParameterRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParameter=ruleParameter();

            state._fsp--;

             current =iv_ruleParameter; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameter"


    // $ANTLR start "ruleParameter"
    // InternalThingML.g:1738:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_typeRef_2_0 = null;

        EObject lv_annotations_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1744:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:1745:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:1745:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:1746:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:1746:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:1747:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:1747:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:1748:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(lv_name_0_0, grammarAccess.getParameterAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getParameterRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_1=(Token)match(input,16,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getParameterAccess().getColonKeyword_1());
            		
            // InternalThingML.g:1768:3: ( (lv_typeRef_2_0= ruleTypeRef ) )
            // InternalThingML.g:1769:4: (lv_typeRef_2_0= ruleTypeRef )
            {
            // InternalThingML.g:1769:4: (lv_typeRef_2_0= ruleTypeRef )
            // InternalThingML.g:1770:5: lv_typeRef_2_0= ruleTypeRef
            {

            					newCompositeNode(grammarAccess.getParameterAccess().getTypeRefTypeRefParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_17);
            lv_typeRef_2_0=ruleTypeRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getParameterRule());
            					}
            					set(
            						current,
            						"typeRef",
            						lv_typeRef_2_0,
            						"org.thingml.xtext.ThingML.TypeRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:1787:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==RULE_ANNOTATION_ID) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalThingML.g:1788:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1788:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1789:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getParameterAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_17);
            	    lv_annotations_3_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getParameterRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_3_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameter"


    // $ANTLR start "entryRulePort"
    // InternalThingML.g:1810:1: entryRulePort returns [EObject current=null] : iv_rulePort= rulePort EOF ;
    public final EObject entryRulePort() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePort = null;


        try {
            // InternalThingML.g:1810:45: (iv_rulePort= rulePort EOF )
            // InternalThingML.g:1811:2: iv_rulePort= rulePort EOF
            {
             newCompositeNode(grammarAccess.getPortRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePort=rulePort();

            state._fsp--;

             current =iv_rulePort; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePort"


    // $ANTLR start "rulePort"
    // InternalThingML.g:1817:1: rulePort returns [EObject current=null] : (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort ) ;
    public final EObject rulePort() throws RecognitionException {
        EObject current = null;

        EObject this_RequiredPort_0 = null;

        EObject this_ProvidedPort_1 = null;

        EObject this_InternalPort_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:1823:2: ( (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort ) )
            // InternalThingML.g:1824:2: (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort )
            {
            // InternalThingML.g:1824:2: (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort )
            int alt39=3;
            switch ( input.LA(1) ) {
            case 41:
            case 42:
                {
                alt39=1;
                }
                break;
            case 46:
                {
                alt39=2;
                }
                break;
            case 47:
                {
                alt39=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }

            switch (alt39) {
                case 1 :
                    // InternalThingML.g:1825:3: this_RequiredPort_0= ruleRequiredPort
                    {

                    			newCompositeNode(grammarAccess.getPortAccess().getRequiredPortParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_RequiredPort_0=ruleRequiredPort();

                    state._fsp--;


                    			current = this_RequiredPort_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalThingML.g:1834:3: this_ProvidedPort_1= ruleProvidedPort
                    {

                    			newCompositeNode(grammarAccess.getPortAccess().getProvidedPortParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_ProvidedPort_1=ruleProvidedPort();

                    state._fsp--;


                    			current = this_ProvidedPort_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalThingML.g:1843:3: this_InternalPort_2= ruleInternalPort
                    {

                    			newCompositeNode(grammarAccess.getPortAccess().getInternalPortParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_InternalPort_2=ruleInternalPort();

                    state._fsp--;


                    			current = this_InternalPort_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePort"


    // $ANTLR start "entryRuleRequiredPort"
    // InternalThingML.g:1855:1: entryRuleRequiredPort returns [EObject current=null] : iv_ruleRequiredPort= ruleRequiredPort EOF ;
    public final EObject entryRuleRequiredPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequiredPort = null;


        try {
            // InternalThingML.g:1855:53: (iv_ruleRequiredPort= ruleRequiredPort EOF )
            // InternalThingML.g:1856:2: iv_ruleRequiredPort= ruleRequiredPort EOF
            {
             newCompositeNode(grammarAccess.getRequiredPortRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRequiredPort=ruleRequiredPort();

            state._fsp--;

             current =iv_ruleRequiredPort; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRequiredPort"


    // $ANTLR start "ruleRequiredPort"
    // InternalThingML.g:1862:1: ruleRequiredPort returns [EObject current=null] : ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' ) ;
    public final EObject ruleRequiredPort() throws RecognitionException {
        EObject current = null;

        Token lv_optional_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_name_3_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        EObject lv_annotations_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1868:2: ( ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' ) )
            // InternalThingML.g:1869:2: ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' )
            {
            // InternalThingML.g:1869:2: ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' )
            // InternalThingML.g:1870:3: ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}'
            {
            // InternalThingML.g:1870:3: ( (lv_optional_0_0= 'optional' ) )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==41) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalThingML.g:1871:4: (lv_optional_0_0= 'optional' )
                    {
                    // InternalThingML.g:1871:4: (lv_optional_0_0= 'optional' )
                    // InternalThingML.g:1872:5: lv_optional_0_0= 'optional'
                    {
                    lv_optional_0_0=(Token)match(input,41,FOLLOW_33); 

                    					newLeafNode(lv_optional_0_0, grammarAccess.getRequiredPortAccess().getOptionalOptionalKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getRequiredPortRule());
                    					}
                    					setWithLastConsumed(current, "optional", true, "optional");
                    				

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,42,FOLLOW_34); 

            			newLeafNode(otherlv_1, grammarAccess.getRequiredPortAccess().getRequiredKeyword_1());
            		
            otherlv_2=(Token)match(input,43,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getRequiredPortAccess().getPortKeyword_2());
            		
            // InternalThingML.g:1892:3: ( (lv_name_3_0= RULE_ID ) )
            // InternalThingML.g:1893:4: (lv_name_3_0= RULE_ID )
            {
            // InternalThingML.g:1893:4: (lv_name_3_0= RULE_ID )
            // InternalThingML.g:1894:5: lv_name_3_0= RULE_ID
            {
            lv_name_3_0=(Token)match(input,RULE_ID,FOLLOW_15); 

            					newLeafNode(lv_name_3_0, grammarAccess.getRequiredPortAccess().getNameIDTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRequiredPortRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_3_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:1910:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==RULE_ANNOTATION_ID) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalThingML.g:1911:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1911:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1912:5: lv_annotations_4_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getRequiredPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_annotations_4_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getRequiredPortRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_4_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);

            otherlv_5=(Token)match(input,25,FOLLOW_35); 

            			newLeafNode(otherlv_5, grammarAccess.getRequiredPortAccess().getLeftCurlyBracketKeyword_5());
            		
            // InternalThingML.g:1933:3: ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )*
            loop44:
            do {
                int alt44=3;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==44) ) {
                    alt44=1;
                }
                else if ( (LA44_0==45) ) {
                    alt44=2;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalThingML.g:1934:4: (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1934:4: (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* )
            	    // InternalThingML.g:1935:5: otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )*
            	    {
            	    otherlv_6=(Token)match(input,44,FOLLOW_6); 

            	    					newLeafNode(otherlv_6, grammarAccess.getRequiredPortAccess().getSendsKeyword_6_0_0());
            	    				
            	    // InternalThingML.g:1939:5: ( (otherlv_7= RULE_ID ) )
            	    // InternalThingML.g:1940:6: (otherlv_7= RULE_ID )
            	    {
            	    // InternalThingML.g:1940:6: (otherlv_7= RULE_ID )
            	    // InternalThingML.g:1941:7: otherlv_7= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getRequiredPortRule());
            	    							}
            	    						
            	    otherlv_7=(Token)match(input,RULE_ID,FOLLOW_36); 

            	    							newLeafNode(otherlv_7, grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1952:5: (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )*
            	    loop42:
            	    do {
            	        int alt42=2;
            	        int LA42_0 = input.LA(1);

            	        if ( (LA42_0==30) ) {
            	            alt42=1;
            	        }


            	        switch (alt42) {
            	    	case 1 :
            	    	    // InternalThingML.g:1953:6: otherlv_8= ',' ( (otherlv_9= RULE_ID ) )
            	    	    {
            	    	    otherlv_8=(Token)match(input,30,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_8, grammarAccess.getRequiredPortAccess().getCommaKeyword_6_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:1957:6: ( (otherlv_9= RULE_ID ) )
            	    	    // InternalThingML.g:1958:7: (otherlv_9= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1958:7: (otherlv_9= RULE_ID )
            	    	    // InternalThingML.g:1959:8: otherlv_9= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getRequiredPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_9=(Token)match(input,RULE_ID,FOLLOW_36); 

            	    	    								newLeafNode(otherlv_9, grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop42;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:1973:4: (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1973:4: (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* )
            	    // InternalThingML.g:1974:5: otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )*
            	    {
            	    otherlv_10=(Token)match(input,45,FOLLOW_6); 

            	    					newLeafNode(otherlv_10, grammarAccess.getRequiredPortAccess().getReceivesKeyword_6_1_0());
            	    				
            	    // InternalThingML.g:1978:5: ( (otherlv_11= RULE_ID ) )
            	    // InternalThingML.g:1979:6: (otherlv_11= RULE_ID )
            	    {
            	    // InternalThingML.g:1979:6: (otherlv_11= RULE_ID )
            	    // InternalThingML.g:1980:7: otherlv_11= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getRequiredPortRule());
            	    							}
            	    						
            	    otherlv_11=(Token)match(input,RULE_ID,FOLLOW_36); 

            	    							newLeafNode(otherlv_11, grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1991:5: (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )*
            	    loop43:
            	    do {
            	        int alt43=2;
            	        int LA43_0 = input.LA(1);

            	        if ( (LA43_0==30) ) {
            	            alt43=1;
            	        }


            	        switch (alt43) {
            	    	case 1 :
            	    	    // InternalThingML.g:1992:6: otherlv_12= ',' ( (otherlv_13= RULE_ID ) )
            	    	    {
            	    	    otherlv_12=(Token)match(input,30,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_12, grammarAccess.getRequiredPortAccess().getCommaKeyword_6_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:1996:6: ( (otherlv_13= RULE_ID ) )
            	    	    // InternalThingML.g:1997:7: (otherlv_13= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1997:7: (otherlv_13= RULE_ID )
            	    	    // InternalThingML.g:1998:8: otherlv_13= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getRequiredPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_13=(Token)match(input,RULE_ID,FOLLOW_36); 

            	    	    								newLeafNode(otherlv_13, grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop43;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

            otherlv_14=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_14, grammarAccess.getRequiredPortAccess().getRightCurlyBracketKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRequiredPort"


    // $ANTLR start "entryRuleProvidedPort"
    // InternalThingML.g:2020:1: entryRuleProvidedPort returns [EObject current=null] : iv_ruleProvidedPort= ruleProvidedPort EOF ;
    public final EObject entryRuleProvidedPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProvidedPort = null;


        try {
            // InternalThingML.g:2020:53: (iv_ruleProvidedPort= ruleProvidedPort EOF )
            // InternalThingML.g:2021:2: iv_ruleProvidedPort= ruleProvidedPort EOF
            {
             newCompositeNode(grammarAccess.getProvidedPortRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleProvidedPort=ruleProvidedPort();

            state._fsp--;

             current =iv_ruleProvidedPort; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleProvidedPort"


    // $ANTLR start "ruleProvidedPort"
    // InternalThingML.g:2027:1: ruleProvidedPort returns [EObject current=null] : (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) ;
    public final EObject ruleProvidedPort() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        EObject lv_annotations_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2033:2: ( (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) )
            // InternalThingML.g:2034:2: (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            {
            // InternalThingML.g:2034:2: (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            // InternalThingML.g:2035:3: otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,46,FOLLOW_34); 

            			newLeafNode(otherlv_0, grammarAccess.getProvidedPortAccess().getProvidedKeyword_0());
            		
            otherlv_1=(Token)match(input,43,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getProvidedPortAccess().getPortKeyword_1());
            		
            // InternalThingML.g:2043:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:2044:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:2044:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:2045:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_15); 

            					newLeafNode(lv_name_2_0, grammarAccess.getProvidedPortAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProvidedPortRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:2061:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==RULE_ANNOTATION_ID) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalThingML.g:2062:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2062:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2063:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getProvidedPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_annotations_3_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getProvidedPortRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_3_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

            otherlv_4=(Token)match(input,25,FOLLOW_35); 

            			newLeafNode(otherlv_4, grammarAccess.getProvidedPortAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalThingML.g:2084:3: ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )*
            loop48:
            do {
                int alt48=3;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==44) ) {
                    alt48=1;
                }
                else if ( (LA48_0==45) ) {
                    alt48=2;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalThingML.g:2085:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:2085:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    // InternalThingML.g:2086:5: otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    {
            	    otherlv_5=(Token)match(input,44,FOLLOW_6); 

            	    					newLeafNode(otherlv_5, grammarAccess.getProvidedPortAccess().getSendsKeyword_5_0_0());
            	    				
            	    // InternalThingML.g:2090:5: ( (otherlv_6= RULE_ID ) )
            	    // InternalThingML.g:2091:6: (otherlv_6= RULE_ID )
            	    {
            	    // InternalThingML.g:2091:6: (otherlv_6= RULE_ID )
            	    // InternalThingML.g:2092:7: otherlv_6= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getProvidedPortRule());
            	    							}
            	    						
            	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_36); 

            	    							newLeafNode(otherlv_6, grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:2103:5: (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    loop46:
            	    do {
            	        int alt46=2;
            	        int LA46_0 = input.LA(1);

            	        if ( (LA46_0==30) ) {
            	            alt46=1;
            	        }


            	        switch (alt46) {
            	    	case 1 :
            	    	    // InternalThingML.g:2104:6: otherlv_7= ',' ( (otherlv_8= RULE_ID ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,30,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_7, grammarAccess.getProvidedPortAccess().getCommaKeyword_5_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:2108:6: ( (otherlv_8= RULE_ID ) )
            	    	    // InternalThingML.g:2109:7: (otherlv_8= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:2109:7: (otherlv_8= RULE_ID )
            	    	    // InternalThingML.g:2110:8: otherlv_8= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getProvidedPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_36); 

            	    	    								newLeafNode(otherlv_8, grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop46;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:2124:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:2124:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    // InternalThingML.g:2125:5: otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    {
            	    otherlv_9=(Token)match(input,45,FOLLOW_6); 

            	    					newLeafNode(otherlv_9, grammarAccess.getProvidedPortAccess().getReceivesKeyword_5_1_0());
            	    				
            	    // InternalThingML.g:2129:5: ( (otherlv_10= RULE_ID ) )
            	    // InternalThingML.g:2130:6: (otherlv_10= RULE_ID )
            	    {
            	    // InternalThingML.g:2130:6: (otherlv_10= RULE_ID )
            	    // InternalThingML.g:2131:7: otherlv_10= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getProvidedPortRule());
            	    							}
            	    						
            	    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_36); 

            	    							newLeafNode(otherlv_10, grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:2142:5: (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    loop47:
            	    do {
            	        int alt47=2;
            	        int LA47_0 = input.LA(1);

            	        if ( (LA47_0==30) ) {
            	            alt47=1;
            	        }


            	        switch (alt47) {
            	    	case 1 :
            	    	    // InternalThingML.g:2143:6: otherlv_11= ',' ( (otherlv_12= RULE_ID ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,30,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_11, grammarAccess.getProvidedPortAccess().getCommaKeyword_5_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:2147:6: ( (otherlv_12= RULE_ID ) )
            	    	    // InternalThingML.g:2148:7: (otherlv_12= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:2148:7: (otherlv_12= RULE_ID )
            	    	    // InternalThingML.g:2149:8: otherlv_12= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getProvidedPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_12=(Token)match(input,RULE_ID,FOLLOW_36); 

            	    	    								newLeafNode(otherlv_12, grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop47;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);

            otherlv_13=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_13, grammarAccess.getProvidedPortAccess().getRightCurlyBracketKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProvidedPort"


    // $ANTLR start "entryRuleInternalPort"
    // InternalThingML.g:2171:1: entryRuleInternalPort returns [EObject current=null] : iv_ruleInternalPort= ruleInternalPort EOF ;
    public final EObject entryRuleInternalPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInternalPort = null;


        try {
            // InternalThingML.g:2171:53: (iv_ruleInternalPort= ruleInternalPort EOF )
            // InternalThingML.g:2172:2: iv_ruleInternalPort= ruleInternalPort EOF
            {
             newCompositeNode(grammarAccess.getInternalPortRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInternalPort=ruleInternalPort();

            state._fsp--;

             current =iv_ruleInternalPort; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInternalPort"


    // $ANTLR start "ruleInternalPort"
    // InternalThingML.g:2178:1: ruleInternalPort returns [EObject current=null] : (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) ;
    public final EObject ruleInternalPort() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        EObject lv_annotations_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2184:2: ( (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) )
            // InternalThingML.g:2185:2: (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            {
            // InternalThingML.g:2185:2: (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            // InternalThingML.g:2186:3: otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,47,FOLLOW_34); 

            			newLeafNode(otherlv_0, grammarAccess.getInternalPortAccess().getInternalKeyword_0());
            		
            otherlv_1=(Token)match(input,43,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getInternalPortAccess().getPortKeyword_1());
            		
            // InternalThingML.g:2194:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:2195:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:2195:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:2196:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_15); 

            					newLeafNode(lv_name_2_0, grammarAccess.getInternalPortAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getInternalPortRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:2212:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==RULE_ANNOTATION_ID) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalThingML.g:2213:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2213:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2214:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getInternalPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_annotations_3_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getInternalPortRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_3_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);

            otherlv_4=(Token)match(input,25,FOLLOW_35); 

            			newLeafNode(otherlv_4, grammarAccess.getInternalPortAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalThingML.g:2235:3: ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )*
            loop52:
            do {
                int alt52=3;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==44) ) {
                    alt52=1;
                }
                else if ( (LA52_0==45) ) {
                    alt52=2;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalThingML.g:2236:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:2236:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    // InternalThingML.g:2237:5: otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    {
            	    otherlv_5=(Token)match(input,44,FOLLOW_6); 

            	    					newLeafNode(otherlv_5, grammarAccess.getInternalPortAccess().getSendsKeyword_5_0_0());
            	    				
            	    // InternalThingML.g:2241:5: ( (otherlv_6= RULE_ID ) )
            	    // InternalThingML.g:2242:6: (otherlv_6= RULE_ID )
            	    {
            	    // InternalThingML.g:2242:6: (otherlv_6= RULE_ID )
            	    // InternalThingML.g:2243:7: otherlv_6= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getInternalPortRule());
            	    							}
            	    						
            	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_36); 

            	    							newLeafNode(otherlv_6, grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:2254:5: (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    loop50:
            	    do {
            	        int alt50=2;
            	        int LA50_0 = input.LA(1);

            	        if ( (LA50_0==30) ) {
            	            alt50=1;
            	        }


            	        switch (alt50) {
            	    	case 1 :
            	    	    // InternalThingML.g:2255:6: otherlv_7= ',' ( (otherlv_8= RULE_ID ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,30,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_7, grammarAccess.getInternalPortAccess().getCommaKeyword_5_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:2259:6: ( (otherlv_8= RULE_ID ) )
            	    	    // InternalThingML.g:2260:7: (otherlv_8= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:2260:7: (otherlv_8= RULE_ID )
            	    	    // InternalThingML.g:2261:8: otherlv_8= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getInternalPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_36); 

            	    	    								newLeafNode(otherlv_8, grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop50;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:2275:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:2275:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    // InternalThingML.g:2276:5: otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    {
            	    otherlv_9=(Token)match(input,45,FOLLOW_6); 

            	    					newLeafNode(otherlv_9, grammarAccess.getInternalPortAccess().getReceivesKeyword_5_1_0());
            	    				
            	    // InternalThingML.g:2280:5: ( (otherlv_10= RULE_ID ) )
            	    // InternalThingML.g:2281:6: (otherlv_10= RULE_ID )
            	    {
            	    // InternalThingML.g:2281:6: (otherlv_10= RULE_ID )
            	    // InternalThingML.g:2282:7: otherlv_10= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getInternalPortRule());
            	    							}
            	    						
            	    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_36); 

            	    							newLeafNode(otherlv_10, grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:2293:5: (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    loop51:
            	    do {
            	        int alt51=2;
            	        int LA51_0 = input.LA(1);

            	        if ( (LA51_0==30) ) {
            	            alt51=1;
            	        }


            	        switch (alt51) {
            	    	case 1 :
            	    	    // InternalThingML.g:2294:6: otherlv_11= ',' ( (otherlv_12= RULE_ID ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,30,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_11, grammarAccess.getInternalPortAccess().getCommaKeyword_5_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:2298:6: ( (otherlv_12= RULE_ID ) )
            	    	    // InternalThingML.g:2299:7: (otherlv_12= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:2299:7: (otherlv_12= RULE_ID )
            	    	    // InternalThingML.g:2300:8: otherlv_12= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getInternalPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_12=(Token)match(input,RULE_ID,FOLLOW_36); 

            	    	    								newLeafNode(otherlv_12, grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop51;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop52;
                }
            } while (true);

            otherlv_13=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_13, grammarAccess.getInternalPortAccess().getRightCurlyBracketKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInternalPort"


    // $ANTLR start "entryRuleState"
    // InternalThingML.g:2322:1: entryRuleState returns [EObject current=null] : iv_ruleState= ruleState EOF ;
    public final EObject entryRuleState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleState = null;


        try {
            // InternalThingML.g:2322:46: (iv_ruleState= ruleState EOF )
            // InternalThingML.g:2323:2: iv_ruleState= ruleState EOF
            {
             newCompositeNode(grammarAccess.getStateRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleState=ruleState();

            state._fsp--;

             current =iv_ruleState; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleState"


    // $ANTLR start "ruleState"
    // InternalThingML.g:2329:1: ruleState returns [EObject current=null] : (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | (otherlv_3= 'state' ( (lv_name_4_0= RULE_ID ) ) ( (lv_annotations_5_0= rulePlatformAnnotation ) )* otherlv_6= '{' ( (lv_properties_7_0= ruleProperty ) )* (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_internal_14_0= ruleInternalTransition ) ) | ( (lv_outgoing_15_0= ruleTransition ) ) )* otherlv_16= '}' ) ) ;
    public final EObject ruleState() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token lv_name_4_0=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_16=null;
        EObject this_StateMachine_0 = null;

        EObject this_FinalState_1 = null;

        EObject this_CompositeState_2 = null;

        EObject lv_annotations_5_0 = null;

        EObject lv_properties_7_0 = null;

        EObject lv_entry_10_0 = null;

        EObject lv_exit_13_0 = null;

        EObject lv_internal_14_0 = null;

        EObject lv_outgoing_15_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2335:2: ( (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | (otherlv_3= 'state' ( (lv_name_4_0= RULE_ID ) ) ( (lv_annotations_5_0= rulePlatformAnnotation ) )* otherlv_6= '{' ( (lv_properties_7_0= ruleProperty ) )* (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_internal_14_0= ruleInternalTransition ) ) | ( (lv_outgoing_15_0= ruleTransition ) ) )* otherlv_16= '}' ) ) )
            // InternalThingML.g:2336:2: (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | (otherlv_3= 'state' ( (lv_name_4_0= RULE_ID ) ) ( (lv_annotations_5_0= rulePlatformAnnotation ) )* otherlv_6= '{' ( (lv_properties_7_0= ruleProperty ) )* (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_internal_14_0= ruleInternalTransition ) ) | ( (lv_outgoing_15_0= ruleTransition ) ) )* otherlv_16= '}' ) )
            {
            // InternalThingML.g:2336:2: (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | (otherlv_3= 'state' ( (lv_name_4_0= RULE_ID ) ) ( (lv_annotations_5_0= rulePlatformAnnotation ) )* otherlv_6= '{' ( (lv_properties_7_0= ruleProperty ) )* (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_internal_14_0= ruleInternalTransition ) ) | ( (lv_outgoing_15_0= ruleTransition ) ) )* otherlv_16= '}' ) )
            int alt58=4;
            switch ( input.LA(1) ) {
            case 61:
                {
                alt58=1;
                }
                break;
            case 64:
                {
                alt58=2;
                }
                break;
            case 57:
                {
                alt58=3;
                }
                break;
            case 48:
                {
                alt58=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }

            switch (alt58) {
                case 1 :
                    // InternalThingML.g:2337:3: this_StateMachine_0= ruleStateMachine
                    {

                    			newCompositeNode(grammarAccess.getStateAccess().getStateMachineParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_StateMachine_0=ruleStateMachine();

                    state._fsp--;


                    			current = this_StateMachine_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalThingML.g:2346:3: this_FinalState_1= ruleFinalState
                    {

                    			newCompositeNode(grammarAccess.getStateAccess().getFinalStateParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_FinalState_1=ruleFinalState();

                    state._fsp--;


                    			current = this_FinalState_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalThingML.g:2355:3: this_CompositeState_2= ruleCompositeState
                    {

                    			newCompositeNode(grammarAccess.getStateAccess().getCompositeStateParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_CompositeState_2=ruleCompositeState();

                    state._fsp--;


                    			current = this_CompositeState_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalThingML.g:2364:3: (otherlv_3= 'state' ( (lv_name_4_0= RULE_ID ) ) ( (lv_annotations_5_0= rulePlatformAnnotation ) )* otherlv_6= '{' ( (lv_properties_7_0= ruleProperty ) )* (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_internal_14_0= ruleInternalTransition ) ) | ( (lv_outgoing_15_0= ruleTransition ) ) )* otherlv_16= '}' )
                    {
                    // InternalThingML.g:2364:3: (otherlv_3= 'state' ( (lv_name_4_0= RULE_ID ) ) ( (lv_annotations_5_0= rulePlatformAnnotation ) )* otherlv_6= '{' ( (lv_properties_7_0= ruleProperty ) )* (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_internal_14_0= ruleInternalTransition ) ) | ( (lv_outgoing_15_0= ruleTransition ) ) )* otherlv_16= '}' )
                    // InternalThingML.g:2365:4: otherlv_3= 'state' ( (lv_name_4_0= RULE_ID ) ) ( (lv_annotations_5_0= rulePlatformAnnotation ) )* otherlv_6= '{' ( (lv_properties_7_0= ruleProperty ) )* (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_internal_14_0= ruleInternalTransition ) ) | ( (lv_outgoing_15_0= ruleTransition ) ) )* otherlv_16= '}'
                    {
                    otherlv_3=(Token)match(input,48,FOLLOW_6); 

                    				newLeafNode(otherlv_3, grammarAccess.getStateAccess().getStateKeyword_3_0());
                    			
                    // InternalThingML.g:2369:4: ( (lv_name_4_0= RULE_ID ) )
                    // InternalThingML.g:2370:5: (lv_name_4_0= RULE_ID )
                    {
                    // InternalThingML.g:2370:5: (lv_name_4_0= RULE_ID )
                    // InternalThingML.g:2371:6: lv_name_4_0= RULE_ID
                    {
                    lv_name_4_0=(Token)match(input,RULE_ID,FOLLOW_15); 

                    						newLeafNode(lv_name_4_0, grammarAccess.getStateAccess().getNameIDTerminalRuleCall_3_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getStateRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"name",
                    							lv_name_4_0,
                    							"org.thingml.xtext.ThingML.ID");
                    					

                    }


                    }

                    // InternalThingML.g:2387:4: ( (lv_annotations_5_0= rulePlatformAnnotation ) )*
                    loop53:
                    do {
                        int alt53=2;
                        int LA53_0 = input.LA(1);

                        if ( (LA53_0==RULE_ANNOTATION_ID) ) {
                            alt53=1;
                        }


                        switch (alt53) {
                    	case 1 :
                    	    // InternalThingML.g:2388:5: (lv_annotations_5_0= rulePlatformAnnotation )
                    	    {
                    	    // InternalThingML.g:2388:5: (lv_annotations_5_0= rulePlatformAnnotation )
                    	    // InternalThingML.g:2389:6: lv_annotations_5_0= rulePlatformAnnotation
                    	    {

                    	    						newCompositeNode(grammarAccess.getStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_2_0());
                    	    					
                    	    pushFollow(FOLLOW_15);
                    	    lv_annotations_5_0=rulePlatformAnnotation();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getStateRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"annotations",
                    	    							lv_annotations_5_0,
                    	    							"org.thingml.xtext.ThingML.PlatformAnnotation");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop53;
                        }
                    } while (true);

                    otherlv_6=(Token)match(input,25,FOLLOW_37); 

                    				newLeafNode(otherlv_6, grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_3_3());
                    			
                    // InternalThingML.g:2410:4: ( (lv_properties_7_0= ruleProperty ) )*
                    loop54:
                    do {
                        int alt54=2;
                        int LA54_0 = input.LA(1);

                        if ( ((LA54_0>=38 && LA54_0<=39)) ) {
                            alt54=1;
                        }


                        switch (alt54) {
                    	case 1 :
                    	    // InternalThingML.g:2411:5: (lv_properties_7_0= ruleProperty )
                    	    {
                    	    // InternalThingML.g:2411:5: (lv_properties_7_0= ruleProperty )
                    	    // InternalThingML.g:2412:6: lv_properties_7_0= ruleProperty
                    	    {

                    	    						newCompositeNode(grammarAccess.getStateAccess().getPropertiesPropertyParserRuleCall_3_4_0());
                    	    					
                    	    pushFollow(FOLLOW_37);
                    	    lv_properties_7_0=ruleProperty();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getStateRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"properties",
                    	    							lv_properties_7_0,
                    	    							"org.thingml.xtext.ThingML.Property");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop54;
                        }
                    } while (true);

                    // InternalThingML.g:2429:4: (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )?
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( (LA55_0==49) ) {
                        int LA55_1 = input.LA(2);

                        if ( (LA55_1==50) ) {
                            alt55=1;
                        }
                    }
                    switch (alt55) {
                        case 1 :
                            // InternalThingML.g:2430:5: otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) )
                            {
                            otherlv_8=(Token)match(input,49,FOLLOW_38); 

                            					newLeafNode(otherlv_8, grammarAccess.getStateAccess().getOnKeyword_3_5_0());
                            				
                            otherlv_9=(Token)match(input,50,FOLLOW_28); 

                            					newLeafNode(otherlv_9, grammarAccess.getStateAccess().getEntryKeyword_3_5_1());
                            				
                            // InternalThingML.g:2438:5: ( (lv_entry_10_0= ruleAction ) )
                            // InternalThingML.g:2439:6: (lv_entry_10_0= ruleAction )
                            {
                            // InternalThingML.g:2439:6: (lv_entry_10_0= ruleAction )
                            // InternalThingML.g:2440:7: lv_entry_10_0= ruleAction
                            {

                            							newCompositeNode(grammarAccess.getStateAccess().getEntryActionParserRuleCall_3_5_2_0());
                            						
                            pushFollow(FOLLOW_39);
                            lv_entry_10_0=ruleAction();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getStateRule());
                            							}
                            							set(
                            								current,
                            								"entry",
                            								lv_entry_10_0,
                            								"org.thingml.xtext.ThingML.Action");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }


                            }
                            break;

                    }

                    // InternalThingML.g:2458:4: (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )?
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==49) ) {
                        alt56=1;
                    }
                    switch (alt56) {
                        case 1 :
                            // InternalThingML.g:2459:5: otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) )
                            {
                            otherlv_11=(Token)match(input,49,FOLLOW_40); 

                            					newLeafNode(otherlv_11, grammarAccess.getStateAccess().getOnKeyword_3_6_0());
                            				
                            otherlv_12=(Token)match(input,51,FOLLOW_28); 

                            					newLeafNode(otherlv_12, grammarAccess.getStateAccess().getExitKeyword_3_6_1());
                            				
                            // InternalThingML.g:2467:5: ( (lv_exit_13_0= ruleAction ) )
                            // InternalThingML.g:2468:6: (lv_exit_13_0= ruleAction )
                            {
                            // InternalThingML.g:2468:6: (lv_exit_13_0= ruleAction )
                            // InternalThingML.g:2469:7: lv_exit_13_0= ruleAction
                            {

                            							newCompositeNode(grammarAccess.getStateAccess().getExitActionParserRuleCall_3_6_2_0());
                            						
                            pushFollow(FOLLOW_41);
                            lv_exit_13_0=ruleAction();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getStateRule());
                            							}
                            							set(
                            								current,
                            								"exit",
                            								lv_exit_13_0,
                            								"org.thingml.xtext.ThingML.Action");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }


                            }
                            break;

                    }

                    // InternalThingML.g:2487:4: ( ( (lv_internal_14_0= ruleInternalTransition ) ) | ( (lv_outgoing_15_0= ruleTransition ) ) )*
                    loop57:
                    do {
                        int alt57=3;
                        int LA57_0 = input.LA(1);

                        if ( (LA57_0==47) ) {
                            alt57=1;
                        }
                        else if ( (LA57_0==52) ) {
                            alt57=2;
                        }


                        switch (alt57) {
                    	case 1 :
                    	    // InternalThingML.g:2488:5: ( (lv_internal_14_0= ruleInternalTransition ) )
                    	    {
                    	    // InternalThingML.g:2488:5: ( (lv_internal_14_0= ruleInternalTransition ) )
                    	    // InternalThingML.g:2489:6: (lv_internal_14_0= ruleInternalTransition )
                    	    {
                    	    // InternalThingML.g:2489:6: (lv_internal_14_0= ruleInternalTransition )
                    	    // InternalThingML.g:2490:7: lv_internal_14_0= ruleInternalTransition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getInternalInternalTransitionParserRuleCall_3_7_0_0());
                    	    						
                    	    pushFollow(FOLLOW_41);
                    	    lv_internal_14_0=ruleInternalTransition();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStateRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"internal",
                    	    								lv_internal_14_0,
                    	    								"org.thingml.xtext.ThingML.InternalTransition");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalThingML.g:2508:5: ( (lv_outgoing_15_0= ruleTransition ) )
                    	    {
                    	    // InternalThingML.g:2508:5: ( (lv_outgoing_15_0= ruleTransition ) )
                    	    // InternalThingML.g:2509:6: (lv_outgoing_15_0= ruleTransition )
                    	    {
                    	    // InternalThingML.g:2509:6: (lv_outgoing_15_0= ruleTransition )
                    	    // InternalThingML.g:2510:7: lv_outgoing_15_0= ruleTransition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getOutgoingTransitionParserRuleCall_3_7_1_0());
                    	    						
                    	    pushFollow(FOLLOW_41);
                    	    lv_outgoing_15_0=ruleTransition();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStateRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"outgoing",
                    	    								lv_outgoing_15_0,
                    	    								"org.thingml.xtext.ThingML.Transition");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop57;
                        }
                    } while (true);

                    otherlv_16=(Token)match(input,26,FOLLOW_2); 

                    				newLeafNode(otherlv_16, grammarAccess.getStateAccess().getRightCurlyBracketKeyword_3_8());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleState"


    // $ANTLR start "entryRuleHandler"
    // InternalThingML.g:2537:1: entryRuleHandler returns [EObject current=null] : iv_ruleHandler= ruleHandler EOF ;
    public final EObject entryRuleHandler() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHandler = null;


        try {
            // InternalThingML.g:2537:48: (iv_ruleHandler= ruleHandler EOF )
            // InternalThingML.g:2538:2: iv_ruleHandler= ruleHandler EOF
            {
             newCompositeNode(grammarAccess.getHandlerRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleHandler=ruleHandler();

            state._fsp--;

             current =iv_ruleHandler; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleHandler"


    // $ANTLR start "ruleHandler"
    // InternalThingML.g:2544:1: ruleHandler returns [EObject current=null] : (this_Transition_0= ruleTransition | this_InternalTransition_1= ruleInternalTransition ) ;
    public final EObject ruleHandler() throws RecognitionException {
        EObject current = null;

        EObject this_Transition_0 = null;

        EObject this_InternalTransition_1 = null;



        	enterRule();

        try {
            // InternalThingML.g:2550:2: ( (this_Transition_0= ruleTransition | this_InternalTransition_1= ruleInternalTransition ) )
            // InternalThingML.g:2551:2: (this_Transition_0= ruleTransition | this_InternalTransition_1= ruleInternalTransition )
            {
            // InternalThingML.g:2551:2: (this_Transition_0= ruleTransition | this_InternalTransition_1= ruleInternalTransition )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==52) ) {
                alt59=1;
            }
            else if ( (LA59_0==47) ) {
                alt59=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }
            switch (alt59) {
                case 1 :
                    // InternalThingML.g:2552:3: this_Transition_0= ruleTransition
                    {

                    			newCompositeNode(grammarAccess.getHandlerAccess().getTransitionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Transition_0=ruleTransition();

                    state._fsp--;


                    			current = this_Transition_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalThingML.g:2561:3: this_InternalTransition_1= ruleInternalTransition
                    {

                    			newCompositeNode(grammarAccess.getHandlerAccess().getInternalTransitionParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_InternalTransition_1=ruleInternalTransition();

                    state._fsp--;


                    			current = this_InternalTransition_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleHandler"


    // $ANTLR start "entryRuleTransition"
    // InternalThingML.g:2573:1: entryRuleTransition returns [EObject current=null] : iv_ruleTransition= ruleTransition EOF ;
    public final EObject entryRuleTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransition = null;


        try {
            // InternalThingML.g:2573:51: (iv_ruleTransition= ruleTransition EOF )
            // InternalThingML.g:2574:2: iv_ruleTransition= ruleTransition EOF
            {
             newCompositeNode(grammarAccess.getTransitionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTransition=ruleTransition();

            state._fsp--;

             current =iv_ruleTransition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTransition"


    // $ANTLR start "ruleTransition"
    // InternalThingML.g:2580:1: ruleTransition returns [EObject current=null] : (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? ) ;
    public final EObject ruleTransition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject lv_annotations_4_0 = null;

        EObject lv_event_6_0 = null;

        EObject lv_guard_8_0 = null;

        EObject lv_action_10_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2586:2: ( (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? ) )
            // InternalThingML.g:2587:2: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:2587:2: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? )
            // InternalThingML.g:2588:3: otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )?
            {
            otherlv_0=(Token)match(input,52,FOLLOW_42); 

            			newLeafNode(otherlv_0, grammarAccess.getTransitionAccess().getTransitionKeyword_0());
            		
            // InternalThingML.g:2592:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==RULE_ID) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // InternalThingML.g:2593:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:2593:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:2594:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_43); 

                    					newLeafNode(lv_name_1_0, grammarAccess.getTransitionAccess().getNameIDTerminalRuleCall_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getTransitionRule());
                    					}
                    					setWithLastConsumed(
                    						current,
                    						"name",
                    						lv_name_1_0,
                    						"org.thingml.xtext.ThingML.ID");
                    				

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,53,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getTransitionAccess().getHyphenMinusGreaterThanSignKeyword_2());
            		
            // InternalThingML.g:2614:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:2615:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:2615:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:2616:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTransitionRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_44); 

            					newLeafNode(otherlv_3, grammarAccess.getTransitionAccess().getTargetStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:2627:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==RULE_ANNOTATION_ID) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // InternalThingML.g:2628:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2628:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2629:5: lv_annotations_4_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_44);
            	    lv_annotations_4_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getTransitionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_4_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop61;
                }
            } while (true);

            // InternalThingML.g:2646:3: (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==54) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // InternalThingML.g:2647:4: otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) )
            	    {
            	    otherlv_5=(Token)match(input,54,FOLLOW_6); 

            	    				newLeafNode(otherlv_5, grammarAccess.getTransitionAccess().getEventKeyword_5_0());
            	    			
            	    // InternalThingML.g:2651:4: ( (lv_event_6_0= ruleEvent ) )
            	    // InternalThingML.g:2652:5: (lv_event_6_0= ruleEvent )
            	    {
            	    // InternalThingML.g:2652:5: (lv_event_6_0= ruleEvent )
            	    // InternalThingML.g:2653:6: lv_event_6_0= ruleEvent
            	    {

            	    						newCompositeNode(grammarAccess.getTransitionAccess().getEventEventParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_45);
            	    lv_event_6_0=ruleEvent();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getTransitionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"event",
            	    							lv_event_6_0,
            	    							"org.thingml.xtext.ThingML.Event");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop62;
                }
            } while (true);

            // InternalThingML.g:2671:3: (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==55) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalThingML.g:2672:4: otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) )
                    {
                    otherlv_7=(Token)match(input,55,FOLLOW_23); 

                    				newLeafNode(otherlv_7, grammarAccess.getTransitionAccess().getGuardKeyword_6_0());
                    			
                    // InternalThingML.g:2676:4: ( (lv_guard_8_0= ruleExpression ) )
                    // InternalThingML.g:2677:5: (lv_guard_8_0= ruleExpression )
                    {
                    // InternalThingML.g:2677:5: (lv_guard_8_0= ruleExpression )
                    // InternalThingML.g:2678:6: lv_guard_8_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getTransitionAccess().getGuardExpressionParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_46);
                    lv_guard_8_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTransitionRule());
                    						}
                    						set(
                    							current,
                    							"guard",
                    							lv_guard_8_0,
                    							"org.thingml.xtext.ThingML.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:2696:3: (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==56) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalThingML.g:2697:4: otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) )
                    {
                    otherlv_9=(Token)match(input,56,FOLLOW_28); 

                    				newLeafNode(otherlv_9, grammarAccess.getTransitionAccess().getActionKeyword_7_0());
                    			
                    // InternalThingML.g:2701:4: ( (lv_action_10_0= ruleAction ) )
                    // InternalThingML.g:2702:5: (lv_action_10_0= ruleAction )
                    {
                    // InternalThingML.g:2702:5: (lv_action_10_0= ruleAction )
                    // InternalThingML.g:2703:6: lv_action_10_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getTransitionAccess().getActionActionParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_action_10_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTransitionRule());
                    						}
                    						set(
                    							current,
                    							"action",
                    							lv_action_10_0,
                    							"org.thingml.xtext.ThingML.Action");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTransition"


    // $ANTLR start "entryRuleInternalTransition"
    // InternalThingML.g:2725:1: entryRuleInternalTransition returns [EObject current=null] : iv_ruleInternalTransition= ruleInternalTransition EOF ;
    public final EObject entryRuleInternalTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInternalTransition = null;


        try {
            // InternalThingML.g:2725:59: (iv_ruleInternalTransition= ruleInternalTransition EOF )
            // InternalThingML.g:2726:2: iv_ruleInternalTransition= ruleInternalTransition EOF
            {
             newCompositeNode(grammarAccess.getInternalTransitionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInternalTransition=ruleInternalTransition();

            state._fsp--;

             current =iv_ruleInternalTransition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInternalTransition"


    // $ANTLR start "ruleInternalTransition"
    // InternalThingML.g:2732:1: ruleInternalTransition returns [EObject current=null] : ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? ) ;
    public final EObject ruleInternalTransition() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject lv_annotations_3_0 = null;

        EObject lv_event_5_0 = null;

        EObject lv_guard_7_0 = null;

        EObject lv_action_9_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2738:2: ( ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? ) )
            // InternalThingML.g:2739:2: ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:2739:2: ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? )
            // InternalThingML.g:2740:3: () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )?
            {
            // InternalThingML.g:2740:3: ()
            // InternalThingML.g:2741:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getInternalTransitionAccess().getInternalTransitionAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,47,FOLLOW_47); 

            			newLeafNode(otherlv_1, grammarAccess.getInternalTransitionAccess().getInternalKeyword_1());
            		
            // InternalThingML.g:2751:3: ( (lv_name_2_0= RULE_ID ) )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==RULE_ID) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalThingML.g:2752:4: (lv_name_2_0= RULE_ID )
                    {
                    // InternalThingML.g:2752:4: (lv_name_2_0= RULE_ID )
                    // InternalThingML.g:2753:5: lv_name_2_0= RULE_ID
                    {
                    lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_44); 

                    					newLeafNode(lv_name_2_0, grammarAccess.getInternalTransitionAccess().getNameIDTerminalRuleCall_2_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getInternalTransitionRule());
                    					}
                    					setWithLastConsumed(
                    						current,
                    						"name",
                    						lv_name_2_0,
                    						"org.thingml.xtext.ThingML.ID");
                    				

                    }


                    }
                    break;

            }

            // InternalThingML.g:2769:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==RULE_ANNOTATION_ID) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // InternalThingML.g:2770:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2770:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2771:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getInternalTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_44);
            	    lv_annotations_3_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getInternalTransitionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_3_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop66;
                }
            } while (true);

            // InternalThingML.g:2788:3: (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==54) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // InternalThingML.g:2789:4: otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) )
            	    {
            	    otherlv_4=(Token)match(input,54,FOLLOW_6); 

            	    				newLeafNode(otherlv_4, grammarAccess.getInternalTransitionAccess().getEventKeyword_4_0());
            	    			
            	    // InternalThingML.g:2793:4: ( (lv_event_5_0= ruleEvent ) )
            	    // InternalThingML.g:2794:5: (lv_event_5_0= ruleEvent )
            	    {
            	    // InternalThingML.g:2794:5: (lv_event_5_0= ruleEvent )
            	    // InternalThingML.g:2795:6: lv_event_5_0= ruleEvent
            	    {

            	    						newCompositeNode(grammarAccess.getInternalTransitionAccess().getEventEventParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_45);
            	    lv_event_5_0=ruleEvent();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getInternalTransitionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"event",
            	    							lv_event_5_0,
            	    							"org.thingml.xtext.ThingML.Event");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);

            // InternalThingML.g:2813:3: (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==55) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalThingML.g:2814:4: otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) )
                    {
                    otherlv_6=(Token)match(input,55,FOLLOW_23); 

                    				newLeafNode(otherlv_6, grammarAccess.getInternalTransitionAccess().getGuardKeyword_5_0());
                    			
                    // InternalThingML.g:2818:4: ( (lv_guard_7_0= ruleExpression ) )
                    // InternalThingML.g:2819:5: (lv_guard_7_0= ruleExpression )
                    {
                    // InternalThingML.g:2819:5: (lv_guard_7_0= ruleExpression )
                    // InternalThingML.g:2820:6: lv_guard_7_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getInternalTransitionAccess().getGuardExpressionParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_46);
                    lv_guard_7_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getInternalTransitionRule());
                    						}
                    						set(
                    							current,
                    							"guard",
                    							lv_guard_7_0,
                    							"org.thingml.xtext.ThingML.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:2838:3: (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==56) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalThingML.g:2839:4: otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) )
                    {
                    otherlv_8=(Token)match(input,56,FOLLOW_28); 

                    				newLeafNode(otherlv_8, grammarAccess.getInternalTransitionAccess().getActionKeyword_6_0());
                    			
                    // InternalThingML.g:2843:4: ( (lv_action_9_0= ruleAction ) )
                    // InternalThingML.g:2844:5: (lv_action_9_0= ruleAction )
                    {
                    // InternalThingML.g:2844:5: (lv_action_9_0= ruleAction )
                    // InternalThingML.g:2845:6: lv_action_9_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getInternalTransitionAccess().getActionActionParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_action_9_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getInternalTransitionRule());
                    						}
                    						set(
                    							current,
                    							"action",
                    							lv_action_9_0,
                    							"org.thingml.xtext.ThingML.Action");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInternalTransition"


    // $ANTLR start "entryRuleCompositeState"
    // InternalThingML.g:2867:1: entryRuleCompositeState returns [EObject current=null] : iv_ruleCompositeState= ruleCompositeState EOF ;
    public final EObject entryRuleCompositeState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCompositeState = null;


        try {
            // InternalThingML.g:2867:55: (iv_ruleCompositeState= ruleCompositeState EOF )
            // InternalThingML.g:2868:2: iv_ruleCompositeState= ruleCompositeState EOF
            {
             newCompositeNode(grammarAccess.getCompositeStateRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCompositeState=ruleCompositeState();

            state._fsp--;

             current =iv_ruleCompositeState; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCompositeState"


    // $ANTLR start "ruleCompositeState"
    // InternalThingML.g:2874:1: ruleCompositeState returns [EObject current=null] : (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( ( (lv_region_19_0= ruleRegion ) ) | ( (lv_session_20_0= ruleSession ) ) )* otherlv_21= '}' ) ;
    public final EObject ruleCompositeState() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_history_6_0=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token otherlv_21=null;
        EObject lv_annotations_7_0 = null;

        EObject lv_properties_9_0 = null;

        EObject lv_entry_12_0 = null;

        EObject lv_exit_15_0 = null;

        EObject lv_substate_16_0 = null;

        EObject lv_internal_17_0 = null;

        EObject lv_outgoing_18_0 = null;

        EObject lv_region_19_0 = null;

        EObject lv_session_20_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2880:2: ( (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( ( (lv_region_19_0= ruleRegion ) ) | ( (lv_session_20_0= ruleSession ) ) )* otherlv_21= '}' ) )
            // InternalThingML.g:2881:2: (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( ( (lv_region_19_0= ruleRegion ) ) | ( (lv_session_20_0= ruleSession ) ) )* otherlv_21= '}' )
            {
            // InternalThingML.g:2881:2: (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( ( (lv_region_19_0= ruleRegion ) ) | ( (lv_session_20_0= ruleSession ) ) )* otherlv_21= '}' )
            // InternalThingML.g:2882:3: otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( ( (lv_region_19_0= ruleRegion ) ) | ( (lv_session_20_0= ruleSession ) ) )* otherlv_21= '}'
            {
            otherlv_0=(Token)match(input,57,FOLLOW_48); 

            			newLeafNode(otherlv_0, grammarAccess.getCompositeStateAccess().getCompositeKeyword_0());
            		
            otherlv_1=(Token)match(input,48,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getCompositeStateAccess().getStateKeyword_1());
            		
            // InternalThingML.g:2890:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:2891:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:2891:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:2892:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_49); 

            					newLeafNode(lv_name_2_0, grammarAccess.getCompositeStateAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCompositeStateRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_3=(Token)match(input,58,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getCompositeStateAccess().getInitKeyword_3());
            		
            // InternalThingML.g:2912:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:2913:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:2913:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:2914:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCompositeStateRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_50); 

            					newLeafNode(otherlv_4, grammarAccess.getCompositeStateAccess().getInitialStateCrossReference_4_0());
            				

            }


            }

            // InternalThingML.g:2925:3: (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==59) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // InternalThingML.g:2926:4: otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) )
                    {
                    otherlv_5=(Token)match(input,59,FOLLOW_51); 

                    				newLeafNode(otherlv_5, grammarAccess.getCompositeStateAccess().getKeepsKeyword_5_0());
                    			
                    // InternalThingML.g:2930:4: ( (lv_history_6_0= 'history' ) )
                    // InternalThingML.g:2931:5: (lv_history_6_0= 'history' )
                    {
                    // InternalThingML.g:2931:5: (lv_history_6_0= 'history' )
                    // InternalThingML.g:2932:6: lv_history_6_0= 'history'
                    {
                    lv_history_6_0=(Token)match(input,60,FOLLOW_15); 

                    						newLeafNode(lv_history_6_0, grammarAccess.getCompositeStateAccess().getHistoryHistoryKeyword_5_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getCompositeStateRule());
                    						}
                    						setWithLastConsumed(current, "history", true, "history");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:2945:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==RULE_ANNOTATION_ID) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // InternalThingML.g:2946:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2946:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2947:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getCompositeStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_annotations_7_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getCompositeStateRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_7_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop71;
                }
            } while (true);

            otherlv_8=(Token)match(input,25,FOLLOW_52); 

            			newLeafNode(otherlv_8, grammarAccess.getCompositeStateAccess().getLeftCurlyBracketKeyword_7());
            		
            // InternalThingML.g:2968:3: ( (lv_properties_9_0= ruleProperty ) )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( ((LA72_0>=38 && LA72_0<=39)) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // InternalThingML.g:2969:4: (lv_properties_9_0= ruleProperty )
            	    {
            	    // InternalThingML.g:2969:4: (lv_properties_9_0= ruleProperty )
            	    // InternalThingML.g:2970:5: lv_properties_9_0= ruleProperty
            	    {

            	    					newCompositeNode(grammarAccess.getCompositeStateAccess().getPropertiesPropertyParserRuleCall_8_0());
            	    				
            	    pushFollow(FOLLOW_52);
            	    lv_properties_9_0=ruleProperty();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getCompositeStateRule());
            	    					}
            	    					add(
            	    						current,
            	    						"properties",
            	    						lv_properties_9_0,
            	    						"org.thingml.xtext.ThingML.Property");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop72;
                }
            } while (true);

            // InternalThingML.g:2987:3: (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==49) ) {
                int LA73_1 = input.LA(2);

                if ( (LA73_1==50) ) {
                    alt73=1;
                }
            }
            switch (alt73) {
                case 1 :
                    // InternalThingML.g:2988:4: otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) )
                    {
                    otherlv_10=(Token)match(input,49,FOLLOW_38); 

                    				newLeafNode(otherlv_10, grammarAccess.getCompositeStateAccess().getOnKeyword_9_0());
                    			
                    otherlv_11=(Token)match(input,50,FOLLOW_28); 

                    				newLeafNode(otherlv_11, grammarAccess.getCompositeStateAccess().getEntryKeyword_9_1());
                    			
                    // InternalThingML.g:2996:4: ( (lv_entry_12_0= ruleAction ) )
                    // InternalThingML.g:2997:5: (lv_entry_12_0= ruleAction )
                    {
                    // InternalThingML.g:2997:5: (lv_entry_12_0= ruleAction )
                    // InternalThingML.g:2998:6: lv_entry_12_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getCompositeStateAccess().getEntryActionParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_52);
                    lv_entry_12_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getCompositeStateRule());
                    						}
                    						set(
                    							current,
                    							"entry",
                    							lv_entry_12_0,
                    							"org.thingml.xtext.ThingML.Action");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:3016:3: (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==49) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // InternalThingML.g:3017:4: otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) )
                    {
                    otherlv_13=(Token)match(input,49,FOLLOW_40); 

                    				newLeafNode(otherlv_13, grammarAccess.getCompositeStateAccess().getOnKeyword_10_0());
                    			
                    otherlv_14=(Token)match(input,51,FOLLOW_28); 

                    				newLeafNode(otherlv_14, grammarAccess.getCompositeStateAccess().getExitKeyword_10_1());
                    			
                    // InternalThingML.g:3025:4: ( (lv_exit_15_0= ruleAction ) )
                    // InternalThingML.g:3026:5: (lv_exit_15_0= ruleAction )
                    {
                    // InternalThingML.g:3026:5: (lv_exit_15_0= ruleAction )
                    // InternalThingML.g:3027:6: lv_exit_15_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getCompositeStateAccess().getExitActionParserRuleCall_10_2_0());
                    					
                    pushFollow(FOLLOW_53);
                    lv_exit_15_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getCompositeStateRule());
                    						}
                    						set(
                    							current,
                    							"exit",
                    							lv_exit_15_0,
                    							"org.thingml.xtext.ThingML.Action");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:3045:3: ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )*
            loop75:
            do {
                int alt75=4;
                switch ( input.LA(1) ) {
                case 48:
                case 57:
                case 61:
                case 64:
                    {
                    alt75=1;
                    }
                    break;
                case 47:
                    {
                    alt75=2;
                    }
                    break;
                case 52:
                    {
                    alt75=3;
                    }
                    break;

                }

                switch (alt75) {
            	case 1 :
            	    // InternalThingML.g:3046:4: ( (lv_substate_16_0= ruleState ) )
            	    {
            	    // InternalThingML.g:3046:4: ( (lv_substate_16_0= ruleState ) )
            	    // InternalThingML.g:3047:5: (lv_substate_16_0= ruleState )
            	    {
            	    // InternalThingML.g:3047:5: (lv_substate_16_0= ruleState )
            	    // InternalThingML.g:3048:6: lv_substate_16_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getSubstateStateParserRuleCall_11_0_0());
            	    					
            	    pushFollow(FOLLOW_53);
            	    lv_substate_16_0=ruleState();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCompositeStateRule());
            	    						}
            	    						add(
            	    							current,
            	    							"substate",
            	    							lv_substate_16_0,
            	    							"org.thingml.xtext.ThingML.State");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:3066:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:3066:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    // InternalThingML.g:3067:5: (lv_internal_17_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:3067:5: (lv_internal_17_0= ruleInternalTransition )
            	    // InternalThingML.g:3068:6: lv_internal_17_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getInternalInternalTransitionParserRuleCall_11_1_0());
            	    					
            	    pushFollow(FOLLOW_53);
            	    lv_internal_17_0=ruleInternalTransition();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCompositeStateRule());
            	    						}
            	    						add(
            	    							current,
            	    							"internal",
            	    							lv_internal_17_0,
            	    							"org.thingml.xtext.ThingML.InternalTransition");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalThingML.g:3086:4: ( (lv_outgoing_18_0= ruleTransition ) )
            	    {
            	    // InternalThingML.g:3086:4: ( (lv_outgoing_18_0= ruleTransition ) )
            	    // InternalThingML.g:3087:5: (lv_outgoing_18_0= ruleTransition )
            	    {
            	    // InternalThingML.g:3087:5: (lv_outgoing_18_0= ruleTransition )
            	    // InternalThingML.g:3088:6: lv_outgoing_18_0= ruleTransition
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getOutgoingTransitionParserRuleCall_11_2_0());
            	    					
            	    pushFollow(FOLLOW_53);
            	    lv_outgoing_18_0=ruleTransition();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCompositeStateRule());
            	    						}
            	    						add(
            	    							current,
            	    							"outgoing",
            	    							lv_outgoing_18_0,
            	    							"org.thingml.xtext.ThingML.Transition");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop75;
                }
            } while (true);

            // InternalThingML.g:3106:3: ( ( (lv_region_19_0= ruleRegion ) ) | ( (lv_session_20_0= ruleSession ) ) )*
            loop76:
            do {
                int alt76=3;
                int LA76_0 = input.LA(1);

                if ( (LA76_0==63) ) {
                    alt76=1;
                }
                else if ( (LA76_0==62) ) {
                    alt76=2;
                }


                switch (alt76) {
            	case 1 :
            	    // InternalThingML.g:3107:4: ( (lv_region_19_0= ruleRegion ) )
            	    {
            	    // InternalThingML.g:3107:4: ( (lv_region_19_0= ruleRegion ) )
            	    // InternalThingML.g:3108:5: (lv_region_19_0= ruleRegion )
            	    {
            	    // InternalThingML.g:3108:5: (lv_region_19_0= ruleRegion )
            	    // InternalThingML.g:3109:6: lv_region_19_0= ruleRegion
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getRegionRegionParserRuleCall_12_0_0());
            	    					
            	    pushFollow(FOLLOW_54);
            	    lv_region_19_0=ruleRegion();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCompositeStateRule());
            	    						}
            	    						add(
            	    							current,
            	    							"region",
            	    							lv_region_19_0,
            	    							"org.thingml.xtext.ThingML.Region");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:3127:4: ( (lv_session_20_0= ruleSession ) )
            	    {
            	    // InternalThingML.g:3127:4: ( (lv_session_20_0= ruleSession ) )
            	    // InternalThingML.g:3128:5: (lv_session_20_0= ruleSession )
            	    {
            	    // InternalThingML.g:3128:5: (lv_session_20_0= ruleSession )
            	    // InternalThingML.g:3129:6: lv_session_20_0= ruleSession
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getSessionSessionParserRuleCall_12_1_0());
            	    					
            	    pushFollow(FOLLOW_54);
            	    lv_session_20_0=ruleSession();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCompositeStateRule());
            	    						}
            	    						add(
            	    							current,
            	    							"session",
            	    							lv_session_20_0,
            	    							"org.thingml.xtext.ThingML.Session");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop76;
                }
            } while (true);

            otherlv_21=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_21, grammarAccess.getCompositeStateAccess().getRightCurlyBracketKeyword_13());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCompositeState"


    // $ANTLR start "entryRuleStateMachine"
    // InternalThingML.g:3155:1: entryRuleStateMachine returns [EObject current=null] : iv_ruleStateMachine= ruleStateMachine EOF ;
    public final EObject entryRuleStateMachine() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStateMachine = null;


        try {
            // InternalThingML.g:3155:53: (iv_ruleStateMachine= ruleStateMachine EOF )
            // InternalThingML.g:3156:2: iv_ruleStateMachine= ruleStateMachine EOF
            {
             newCompositeNode(grammarAccess.getStateMachineRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStateMachine=ruleStateMachine();

            state._fsp--;

             current =iv_ruleStateMachine; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStateMachine"


    // $ANTLR start "ruleStateMachine"
    // InternalThingML.g:3162:1: ruleStateMachine returns [EObject current=null] : (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( ( (lv_region_17_0= ruleRegion ) ) | ( (lv_session_18_0= ruleSession ) ) )* otherlv_19= '}' ) ;
    public final EObject ruleStateMachine() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_history_5_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_19=null;
        EObject lv_annotations_6_0 = null;

        EObject lv_properties_8_0 = null;

        EObject lv_entry_11_0 = null;

        EObject lv_exit_14_0 = null;

        EObject lv_substate_15_0 = null;

        EObject lv_internal_16_0 = null;

        EObject lv_region_17_0 = null;

        EObject lv_session_18_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3168:2: ( (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( ( (lv_region_17_0= ruleRegion ) ) | ( (lv_session_18_0= ruleSession ) ) )* otherlv_19= '}' ) )
            // InternalThingML.g:3169:2: (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( ( (lv_region_17_0= ruleRegion ) ) | ( (lv_session_18_0= ruleSession ) ) )* otherlv_19= '}' )
            {
            // InternalThingML.g:3169:2: (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( ( (lv_region_17_0= ruleRegion ) ) | ( (lv_session_18_0= ruleSession ) ) )* otherlv_19= '}' )
            // InternalThingML.g:3170:3: otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( ( (lv_region_17_0= ruleRegion ) ) | ( (lv_session_18_0= ruleSession ) ) )* otherlv_19= '}'
            {
            otherlv_0=(Token)match(input,61,FOLLOW_55); 

            			newLeafNode(otherlv_0, grammarAccess.getStateMachineAccess().getStatechartKeyword_0());
            		
            // InternalThingML.g:3174:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==RULE_ID) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // InternalThingML.g:3175:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:3175:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:3176:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_49); 

                    					newLeafNode(lv_name_1_0, grammarAccess.getStateMachineAccess().getNameIDTerminalRuleCall_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getStateMachineRule());
                    					}
                    					setWithLastConsumed(
                    						current,
                    						"name",
                    						lv_name_1_0,
                    						"org.thingml.xtext.ThingML.ID");
                    				

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,58,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getStateMachineAccess().getInitKeyword_2());
            		
            // InternalThingML.g:3196:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:3197:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:3197:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:3198:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStateMachineRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_50); 

            					newLeafNode(otherlv_3, grammarAccess.getStateMachineAccess().getInitialStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:3209:3: (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==59) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // InternalThingML.g:3210:4: otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) )
                    {
                    otherlv_4=(Token)match(input,59,FOLLOW_51); 

                    				newLeafNode(otherlv_4, grammarAccess.getStateMachineAccess().getKeepsKeyword_4_0());
                    			
                    // InternalThingML.g:3214:4: ( (lv_history_5_0= 'history' ) )
                    // InternalThingML.g:3215:5: (lv_history_5_0= 'history' )
                    {
                    // InternalThingML.g:3215:5: (lv_history_5_0= 'history' )
                    // InternalThingML.g:3216:6: lv_history_5_0= 'history'
                    {
                    lv_history_5_0=(Token)match(input,60,FOLLOW_15); 

                    						newLeafNode(lv_history_5_0, grammarAccess.getStateMachineAccess().getHistoryHistoryKeyword_4_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getStateMachineRule());
                    						}
                    						setWithLastConsumed(current, "history", true, "history");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:3229:3: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==RULE_ANNOTATION_ID) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // InternalThingML.g:3230:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3230:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3231:5: lv_annotations_6_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getStateMachineAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_annotations_6_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_6_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop79;
                }
            } while (true);

            otherlv_7=(Token)match(input,25,FOLLOW_56); 

            			newLeafNode(otherlv_7, grammarAccess.getStateMachineAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:3252:3: ( (lv_properties_8_0= ruleProperty ) )*
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( ((LA80_0>=38 && LA80_0<=39)) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // InternalThingML.g:3253:4: (lv_properties_8_0= ruleProperty )
            	    {
            	    // InternalThingML.g:3253:4: (lv_properties_8_0= ruleProperty )
            	    // InternalThingML.g:3254:5: lv_properties_8_0= ruleProperty
            	    {

            	    					newCompositeNode(grammarAccess.getStateMachineAccess().getPropertiesPropertyParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_56);
            	    lv_properties_8_0=ruleProperty();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    					}
            	    					add(
            	    						current,
            	    						"properties",
            	    						lv_properties_8_0,
            	    						"org.thingml.xtext.ThingML.Property");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop80;
                }
            } while (true);

            // InternalThingML.g:3271:3: (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==49) ) {
                int LA81_1 = input.LA(2);

                if ( (LA81_1==50) ) {
                    alt81=1;
                }
            }
            switch (alt81) {
                case 1 :
                    // InternalThingML.g:3272:4: otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) )
                    {
                    otherlv_9=(Token)match(input,49,FOLLOW_38); 

                    				newLeafNode(otherlv_9, grammarAccess.getStateMachineAccess().getOnKeyword_8_0());
                    			
                    otherlv_10=(Token)match(input,50,FOLLOW_28); 

                    				newLeafNode(otherlv_10, grammarAccess.getStateMachineAccess().getEntryKeyword_8_1());
                    			
                    // InternalThingML.g:3280:4: ( (lv_entry_11_0= ruleAction ) )
                    // InternalThingML.g:3281:5: (lv_entry_11_0= ruleAction )
                    {
                    // InternalThingML.g:3281:5: (lv_entry_11_0= ruleAction )
                    // InternalThingML.g:3282:6: lv_entry_11_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getStateMachineAccess().getEntryActionParserRuleCall_8_2_0());
                    					
                    pushFollow(FOLLOW_56);
                    lv_entry_11_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getStateMachineRule());
                    						}
                    						set(
                    							current,
                    							"entry",
                    							lv_entry_11_0,
                    							"org.thingml.xtext.ThingML.Action");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:3300:3: (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==49) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // InternalThingML.g:3301:4: otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) )
                    {
                    otherlv_12=(Token)match(input,49,FOLLOW_40); 

                    				newLeafNode(otherlv_12, grammarAccess.getStateMachineAccess().getOnKeyword_9_0());
                    			
                    otherlv_13=(Token)match(input,51,FOLLOW_28); 

                    				newLeafNode(otherlv_13, grammarAccess.getStateMachineAccess().getExitKeyword_9_1());
                    			
                    // InternalThingML.g:3309:4: ( (lv_exit_14_0= ruleAction ) )
                    // InternalThingML.g:3310:5: (lv_exit_14_0= ruleAction )
                    {
                    // InternalThingML.g:3310:5: (lv_exit_14_0= ruleAction )
                    // InternalThingML.g:3311:6: lv_exit_14_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getStateMachineAccess().getExitActionParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_57);
                    lv_exit_14_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getStateMachineRule());
                    						}
                    						set(
                    							current,
                    							"exit",
                    							lv_exit_14_0,
                    							"org.thingml.xtext.ThingML.Action");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:3329:3: ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )*
            loop83:
            do {
                int alt83=3;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==48||LA83_0==57||LA83_0==61||LA83_0==64) ) {
                    alt83=1;
                }
                else if ( (LA83_0==47) ) {
                    alt83=2;
                }


                switch (alt83) {
            	case 1 :
            	    // InternalThingML.g:3330:4: ( (lv_substate_15_0= ruleState ) )
            	    {
            	    // InternalThingML.g:3330:4: ( (lv_substate_15_0= ruleState ) )
            	    // InternalThingML.g:3331:5: (lv_substate_15_0= ruleState )
            	    {
            	    // InternalThingML.g:3331:5: (lv_substate_15_0= ruleState )
            	    // InternalThingML.g:3332:6: lv_substate_15_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getSubstateStateParserRuleCall_10_0_0());
            	    					
            	    pushFollow(FOLLOW_57);
            	    lv_substate_15_0=ruleState();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    						}
            	    						add(
            	    							current,
            	    							"substate",
            	    							lv_substate_15_0,
            	    							"org.thingml.xtext.ThingML.State");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:3350:4: ( (lv_internal_16_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:3350:4: ( (lv_internal_16_0= ruleInternalTransition ) )
            	    // InternalThingML.g:3351:5: (lv_internal_16_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:3351:5: (lv_internal_16_0= ruleInternalTransition )
            	    // InternalThingML.g:3352:6: lv_internal_16_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getInternalInternalTransitionParserRuleCall_10_1_0());
            	    					
            	    pushFollow(FOLLOW_57);
            	    lv_internal_16_0=ruleInternalTransition();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    						}
            	    						add(
            	    							current,
            	    							"internal",
            	    							lv_internal_16_0,
            	    							"org.thingml.xtext.ThingML.InternalTransition");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop83;
                }
            } while (true);

            // InternalThingML.g:3370:3: ( ( (lv_region_17_0= ruleRegion ) ) | ( (lv_session_18_0= ruleSession ) ) )*
            loop84:
            do {
                int alt84=3;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==63) ) {
                    alt84=1;
                }
                else if ( (LA84_0==62) ) {
                    alt84=2;
                }


                switch (alt84) {
            	case 1 :
            	    // InternalThingML.g:3371:4: ( (lv_region_17_0= ruleRegion ) )
            	    {
            	    // InternalThingML.g:3371:4: ( (lv_region_17_0= ruleRegion ) )
            	    // InternalThingML.g:3372:5: (lv_region_17_0= ruleRegion )
            	    {
            	    // InternalThingML.g:3372:5: (lv_region_17_0= ruleRegion )
            	    // InternalThingML.g:3373:6: lv_region_17_0= ruleRegion
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getRegionRegionParserRuleCall_11_0_0());
            	    					
            	    pushFollow(FOLLOW_54);
            	    lv_region_17_0=ruleRegion();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    						}
            	    						add(
            	    							current,
            	    							"region",
            	    							lv_region_17_0,
            	    							"org.thingml.xtext.ThingML.Region");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:3391:4: ( (lv_session_18_0= ruleSession ) )
            	    {
            	    // InternalThingML.g:3391:4: ( (lv_session_18_0= ruleSession ) )
            	    // InternalThingML.g:3392:5: (lv_session_18_0= ruleSession )
            	    {
            	    // InternalThingML.g:3392:5: (lv_session_18_0= ruleSession )
            	    // InternalThingML.g:3393:6: lv_session_18_0= ruleSession
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getSessionSessionParserRuleCall_11_1_0());
            	    					
            	    pushFollow(FOLLOW_54);
            	    lv_session_18_0=ruleSession();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    						}
            	    						add(
            	    							current,
            	    							"session",
            	    							lv_session_18_0,
            	    							"org.thingml.xtext.ThingML.Session");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop84;
                }
            } while (true);

            otherlv_19=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_19, grammarAccess.getStateMachineAccess().getRightCurlyBracketKeyword_12());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStateMachine"


    // $ANTLR start "entryRuleSession"
    // InternalThingML.g:3419:1: entryRuleSession returns [EObject current=null] : iv_ruleSession= ruleSession EOF ;
    public final EObject entryRuleSession() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSession = null;


        try {
            // InternalThingML.g:3419:48: (iv_ruleSession= ruleSession EOF )
            // InternalThingML.g:3420:2: iv_ruleSession= ruleSession EOF
            {
             newCompositeNode(grammarAccess.getSessionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSession=ruleSession();

            state._fsp--;

             current =iv_ruleSession; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSession"


    // $ANTLR start "ruleSession"
    // InternalThingML.g:3426:1: ruleSession returns [EObject current=null] : (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( ( (lv_maxInstances_3_1= ruleIntegerLiteral | lv_maxInstances_3_2= rulePropertyReference ) ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_substate_9_0= ruleState ) )* otherlv_10= '}' ) ;
    public final EObject ruleSession() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        EObject lv_maxInstances_3_1 = null;

        EObject lv_maxInstances_3_2 = null;

        EObject lv_annotations_7_0 = null;

        EObject lv_substate_9_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3432:2: ( (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( ( (lv_maxInstances_3_1= ruleIntegerLiteral | lv_maxInstances_3_2= rulePropertyReference ) ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_substate_9_0= ruleState ) )* otherlv_10= '}' ) )
            // InternalThingML.g:3433:2: (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( ( (lv_maxInstances_3_1= ruleIntegerLiteral | lv_maxInstances_3_2= rulePropertyReference ) ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_substate_9_0= ruleState ) )* otherlv_10= '}' )
            {
            // InternalThingML.g:3433:2: (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( ( (lv_maxInstances_3_1= ruleIntegerLiteral | lv_maxInstances_3_2= rulePropertyReference ) ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_substate_9_0= ruleState ) )* otherlv_10= '}' )
            // InternalThingML.g:3434:3: otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( ( (lv_maxInstances_3_1= ruleIntegerLiteral | lv_maxInstances_3_2= rulePropertyReference ) ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_substate_9_0= ruleState ) )* otherlv_10= '}'
            {
            otherlv_0=(Token)match(input,62,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getSessionAccess().getSessionKeyword_0());
            		
            // InternalThingML.g:3438:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:3439:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:3439:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:3440:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_58); 

            					newLeafNode(lv_name_1_0, grammarAccess.getSessionAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSessionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:3456:3: (otherlv_2= '<' ( ( (lv_maxInstances_3_1= ruleIntegerLiteral | lv_maxInstances_3_2= rulePropertyReference ) ) ) otherlv_4= '>' )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==20) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // InternalThingML.g:3457:4: otherlv_2= '<' ( ( (lv_maxInstances_3_1= ruleIntegerLiteral | lv_maxInstances_3_2= rulePropertyReference ) ) ) otherlv_4= '>'
                    {
                    otherlv_2=(Token)match(input,20,FOLLOW_59); 

                    				newLeafNode(otherlv_2, grammarAccess.getSessionAccess().getLessThanSignKeyword_2_0());
                    			
                    // InternalThingML.g:3461:4: ( ( (lv_maxInstances_3_1= ruleIntegerLiteral | lv_maxInstances_3_2= rulePropertyReference ) ) )
                    // InternalThingML.g:3462:5: ( (lv_maxInstances_3_1= ruleIntegerLiteral | lv_maxInstances_3_2= rulePropertyReference ) )
                    {
                    // InternalThingML.g:3462:5: ( (lv_maxInstances_3_1= ruleIntegerLiteral | lv_maxInstances_3_2= rulePropertyReference ) )
                    // InternalThingML.g:3463:6: (lv_maxInstances_3_1= ruleIntegerLiteral | lv_maxInstances_3_2= rulePropertyReference )
                    {
                    // InternalThingML.g:3463:6: (lv_maxInstances_3_1= ruleIntegerLiteral | lv_maxInstances_3_2= rulePropertyReference )
                    int alt85=2;
                    int LA85_0 = input.LA(1);

                    if ( (LA85_0==RULE_INT) ) {
                        alt85=1;
                    }
                    else if ( (LA85_0==RULE_ID) ) {
                        alt85=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 85, 0, input);

                        throw nvae;
                    }
                    switch (alt85) {
                        case 1 :
                            // InternalThingML.g:3464:7: lv_maxInstances_3_1= ruleIntegerLiteral
                            {

                            							newCompositeNode(grammarAccess.getSessionAccess().getMaxInstancesIntegerLiteralParserRuleCall_2_1_0_0());
                            						
                            pushFollow(FOLLOW_13);
                            lv_maxInstances_3_1=ruleIntegerLiteral();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getSessionRule());
                            							}
                            							set(
                            								current,
                            								"maxInstances",
                            								lv_maxInstances_3_1,
                            								"org.thingml.xtext.ThingML.IntegerLiteral");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;
                        case 2 :
                            // InternalThingML.g:3480:7: lv_maxInstances_3_2= rulePropertyReference
                            {

                            							newCompositeNode(grammarAccess.getSessionAccess().getMaxInstancesPropertyReferenceParserRuleCall_2_1_0_1());
                            						
                            pushFollow(FOLLOW_13);
                            lv_maxInstances_3_2=rulePropertyReference();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getSessionRule());
                            							}
                            							set(
                            								current,
                            								"maxInstances",
                            								lv_maxInstances_3_2,
                            								"org.thingml.xtext.ThingML.PropertyReference");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;

                    }


                    }


                    }

                    otherlv_4=(Token)match(input,21,FOLLOW_49); 

                    				newLeafNode(otherlv_4, grammarAccess.getSessionAccess().getGreaterThanSignKeyword_2_2());
                    			

                    }
                    break;

            }

            otherlv_5=(Token)match(input,58,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getSessionAccess().getInitKeyword_3());
            		
            // InternalThingML.g:3507:3: ( (otherlv_6= RULE_ID ) )
            // InternalThingML.g:3508:4: (otherlv_6= RULE_ID )
            {
            // InternalThingML.g:3508:4: (otherlv_6= RULE_ID )
            // InternalThingML.g:3509:5: otherlv_6= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSessionRule());
            					}
            				
            otherlv_6=(Token)match(input,RULE_ID,FOLLOW_15); 

            					newLeafNode(otherlv_6, grammarAccess.getSessionAccess().getInitialStateCrossReference_4_0());
            				

            }


            }

            // InternalThingML.g:3520:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop87:
            do {
                int alt87=2;
                int LA87_0 = input.LA(1);

                if ( (LA87_0==RULE_ANNOTATION_ID) ) {
                    alt87=1;
                }


                switch (alt87) {
            	case 1 :
            	    // InternalThingML.g:3521:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3521:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3522:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getSessionAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_annotations_7_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getSessionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_7_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop87;
                }
            } while (true);

            otherlv_8=(Token)match(input,25,FOLLOW_60); 

            			newLeafNode(otherlv_8, grammarAccess.getSessionAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:3543:3: ( (lv_substate_9_0= ruleState ) )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( (LA88_0==48||LA88_0==57||LA88_0==61||LA88_0==64) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // InternalThingML.g:3544:4: (lv_substate_9_0= ruleState )
            	    {
            	    // InternalThingML.g:3544:4: (lv_substate_9_0= ruleState )
            	    // InternalThingML.g:3545:5: lv_substate_9_0= ruleState
            	    {

            	    					newCompositeNode(grammarAccess.getSessionAccess().getSubstateStateParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_60);
            	    lv_substate_9_0=ruleState();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getSessionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"substate",
            	    						lv_substate_9_0,
            	    						"org.thingml.xtext.ThingML.State");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop88;
                }
            } while (true);

            otherlv_10=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_10, grammarAccess.getSessionAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSession"


    // $ANTLR start "entryRuleRegion"
    // InternalThingML.g:3570:1: entryRuleRegion returns [EObject current=null] : iv_ruleRegion= ruleRegion EOF ;
    public final EObject entryRuleRegion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRegion = null;


        try {
            // InternalThingML.g:3570:47: (iv_ruleRegion= ruleRegion EOF )
            // InternalThingML.g:3571:2: iv_ruleRegion= ruleRegion EOF
            {
             newCompositeNode(grammarAccess.getRegionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRegion=ruleRegion();

            state._fsp--;

             current =iv_ruleRegion; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRegion"


    // $ANTLR start "ruleRegion"
    // InternalThingML.g:3577:1: ruleRegion returns [EObject current=null] : (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) ;
    public final EObject ruleRegion() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_history_5_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject lv_annotations_6_0 = null;

        EObject lv_substate_8_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3583:2: ( (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) )
            // InternalThingML.g:3584:2: (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' )
            {
            // InternalThingML.g:3584:2: (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' )
            // InternalThingML.g:3585:3: otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,63,FOLLOW_55); 

            			newLeafNode(otherlv_0, grammarAccess.getRegionAccess().getRegionKeyword_0());
            		
            // InternalThingML.g:3589:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==RULE_ID) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // InternalThingML.g:3590:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:3590:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:3591:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_49); 

                    					newLeafNode(lv_name_1_0, grammarAccess.getRegionAccess().getNameIDTerminalRuleCall_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getRegionRule());
                    					}
                    					setWithLastConsumed(
                    						current,
                    						"name",
                    						lv_name_1_0,
                    						"org.thingml.xtext.ThingML.ID");
                    				

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,58,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getRegionAccess().getInitKeyword_2());
            		
            // InternalThingML.g:3611:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:3612:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:3612:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:3613:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRegionRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_50); 

            					newLeafNode(otherlv_3, grammarAccess.getRegionAccess().getInitialStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:3624:3: (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==59) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // InternalThingML.g:3625:4: otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) )
                    {
                    otherlv_4=(Token)match(input,59,FOLLOW_51); 

                    				newLeafNode(otherlv_4, grammarAccess.getRegionAccess().getKeepsKeyword_4_0());
                    			
                    // InternalThingML.g:3629:4: ( (lv_history_5_0= 'history' ) )
                    // InternalThingML.g:3630:5: (lv_history_5_0= 'history' )
                    {
                    // InternalThingML.g:3630:5: (lv_history_5_0= 'history' )
                    // InternalThingML.g:3631:6: lv_history_5_0= 'history'
                    {
                    lv_history_5_0=(Token)match(input,60,FOLLOW_15); 

                    						newLeafNode(lv_history_5_0, grammarAccess.getRegionAccess().getHistoryHistoryKeyword_4_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getRegionRule());
                    						}
                    						setWithLastConsumed(current, "history", true, "history");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:3644:3: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( (LA91_0==RULE_ANNOTATION_ID) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // InternalThingML.g:3645:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3645:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3646:5: lv_annotations_6_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getRegionAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_annotations_6_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getRegionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_6_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop91;
                }
            } while (true);

            otherlv_7=(Token)match(input,25,FOLLOW_60); 

            			newLeafNode(otherlv_7, grammarAccess.getRegionAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:3667:3: ( (lv_substate_8_0= ruleState ) )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( (LA92_0==48||LA92_0==57||LA92_0==61||LA92_0==64) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // InternalThingML.g:3668:4: (lv_substate_8_0= ruleState )
            	    {
            	    // InternalThingML.g:3668:4: (lv_substate_8_0= ruleState )
            	    // InternalThingML.g:3669:5: lv_substate_8_0= ruleState
            	    {

            	    					newCompositeNode(grammarAccess.getRegionAccess().getSubstateStateParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_60);
            	    lv_substate_8_0=ruleState();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getRegionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"substate",
            	    						lv_substate_8_0,
            	    						"org.thingml.xtext.ThingML.State");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop92;
                }
            } while (true);

            otherlv_9=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getRegionAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRegion"


    // $ANTLR start "entryRuleFinalState"
    // InternalThingML.g:3694:1: entryRuleFinalState returns [EObject current=null] : iv_ruleFinalState= ruleFinalState EOF ;
    public final EObject entryRuleFinalState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFinalState = null;


        try {
            // InternalThingML.g:3694:51: (iv_ruleFinalState= ruleFinalState EOF )
            // InternalThingML.g:3695:2: iv_ruleFinalState= ruleFinalState EOF
            {
             newCompositeNode(grammarAccess.getFinalStateRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFinalState=ruleFinalState();

            state._fsp--;

             current =iv_ruleFinalState; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFinalState"


    // $ANTLR start "ruleFinalState"
    // InternalThingML.g:3701:1: ruleFinalState returns [EObject current=null] : (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}' ) ;
    public final EObject ruleFinalState() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject lv_annotations_3_0 = null;

        EObject lv_entry_7_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3707:2: ( (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}' ) )
            // InternalThingML.g:3708:2: (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}' )
            {
            // InternalThingML.g:3708:2: (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}' )
            // InternalThingML.g:3709:3: otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,64,FOLLOW_48); 

            			newLeafNode(otherlv_0, grammarAccess.getFinalStateAccess().getFinalKeyword_0());
            		
            otherlv_1=(Token)match(input,48,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getFinalStateAccess().getStateKeyword_1());
            		
            // InternalThingML.g:3717:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:3718:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:3718:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:3719:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_15); 

            					newLeafNode(lv_name_2_0, grammarAccess.getFinalStateAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFinalStateRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:3735:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( (LA93_0==RULE_ANNOTATION_ID) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // InternalThingML.g:3736:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3736:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3737:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getFinalStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_annotations_3_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getFinalStateRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_3_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop93;
                }
            } while (true);

            otherlv_4=(Token)match(input,25,FOLLOW_61); 

            			newLeafNode(otherlv_4, grammarAccess.getFinalStateAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalThingML.g:3758:3: (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==49) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // InternalThingML.g:3759:4: otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) )
                    {
                    otherlv_5=(Token)match(input,49,FOLLOW_38); 

                    				newLeafNode(otherlv_5, grammarAccess.getFinalStateAccess().getOnKeyword_5_0());
                    			
                    otherlv_6=(Token)match(input,50,FOLLOW_28); 

                    				newLeafNode(otherlv_6, grammarAccess.getFinalStateAccess().getEntryKeyword_5_1());
                    			
                    // InternalThingML.g:3767:4: ( (lv_entry_7_0= ruleAction ) )
                    // InternalThingML.g:3768:5: (lv_entry_7_0= ruleAction )
                    {
                    // InternalThingML.g:3768:5: (lv_entry_7_0= ruleAction )
                    // InternalThingML.g:3769:6: lv_entry_7_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getFinalStateAccess().getEntryActionParserRuleCall_5_2_0());
                    					
                    pushFollow(FOLLOW_62);
                    lv_entry_7_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFinalStateRule());
                    						}
                    						set(
                    							current,
                    							"entry",
                    							lv_entry_7_0,
                    							"org.thingml.xtext.ThingML.Action");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_8=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getFinalStateAccess().getRightCurlyBracketKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFinalState"


    // $ANTLR start "entryRuleStateContainer"
    // InternalThingML.g:3795:1: entryRuleStateContainer returns [EObject current=null] : iv_ruleStateContainer= ruleStateContainer EOF ;
    public final EObject entryRuleStateContainer() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStateContainer = null;


        try {
            // InternalThingML.g:3795:55: (iv_ruleStateContainer= ruleStateContainer EOF )
            // InternalThingML.g:3796:2: iv_ruleStateContainer= ruleStateContainer EOF
            {
             newCompositeNode(grammarAccess.getStateContainerRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStateContainer=ruleStateContainer();

            state._fsp--;

             current =iv_ruleStateContainer; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStateContainer"


    // $ANTLR start "ruleStateContainer"
    // InternalThingML.g:3802:1: ruleStateContainer returns [EObject current=null] : (this_CompositeState_0= ruleCompositeState | this_Region_1= ruleRegion | this_Session_2= ruleSession | (otherlv_3= 'keeps' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) ) ;
    public final EObject ruleStateContainer() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_history_6_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject this_CompositeState_0 = null;

        EObject this_Region_1 = null;

        EObject this_Session_2 = null;

        EObject lv_substate_8_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3808:2: ( (this_CompositeState_0= ruleCompositeState | this_Region_1= ruleRegion | this_Session_2= ruleSession | (otherlv_3= 'keeps' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) ) )
            // InternalThingML.g:3809:2: (this_CompositeState_0= ruleCompositeState | this_Region_1= ruleRegion | this_Session_2= ruleSession | (otherlv_3= 'keeps' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) )
            {
            // InternalThingML.g:3809:2: (this_CompositeState_0= ruleCompositeState | this_Region_1= ruleRegion | this_Session_2= ruleSession | (otherlv_3= 'keeps' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) )
            int alt97=4;
            switch ( input.LA(1) ) {
            case 57:
                {
                alt97=1;
                }
                break;
            case 63:
                {
                alt97=2;
                }
                break;
            case 62:
                {
                alt97=3;
                }
                break;
            case 59:
                {
                alt97=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 97, 0, input);

                throw nvae;
            }

            switch (alt97) {
                case 1 :
                    // InternalThingML.g:3810:3: this_CompositeState_0= ruleCompositeState
                    {

                    			newCompositeNode(grammarAccess.getStateContainerAccess().getCompositeStateParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_CompositeState_0=ruleCompositeState();

                    state._fsp--;


                    			current = this_CompositeState_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalThingML.g:3819:3: this_Region_1= ruleRegion
                    {

                    			newCompositeNode(grammarAccess.getStateContainerAccess().getRegionParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Region_1=ruleRegion();

                    state._fsp--;


                    			current = this_Region_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalThingML.g:3828:3: this_Session_2= ruleSession
                    {

                    			newCompositeNode(grammarAccess.getStateContainerAccess().getSessionParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Session_2=ruleSession();

                    state._fsp--;


                    			current = this_Session_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalThingML.g:3837:3: (otherlv_3= 'keeps' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' )
                    {
                    // InternalThingML.g:3837:3: (otherlv_3= 'keeps' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' )
                    // InternalThingML.g:3838:4: otherlv_3= 'keeps' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}'
                    {
                    otherlv_3=(Token)match(input,59,FOLLOW_6); 

                    				newLeafNode(otherlv_3, grammarAccess.getStateContainerAccess().getKeepsKeyword_3_0());
                    			
                    // InternalThingML.g:3842:4: ( (otherlv_4= RULE_ID ) )
                    // InternalThingML.g:3843:5: (otherlv_4= RULE_ID )
                    {
                    // InternalThingML.g:3843:5: (otherlv_4= RULE_ID )
                    // InternalThingML.g:3844:6: otherlv_4= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getStateContainerRule());
                    						}
                    					
                    otherlv_4=(Token)match(input,RULE_ID,FOLLOW_63); 

                    						newLeafNode(otherlv_4, grammarAccess.getStateContainerAccess().getInitialStateCrossReference_3_1_0());
                    					

                    }


                    }

                    // InternalThingML.g:3855:4: (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )?
                    int alt95=2;
                    int LA95_0 = input.LA(1);

                    if ( (LA95_0==59) ) {
                        alt95=1;
                    }
                    switch (alt95) {
                        case 1 :
                            // InternalThingML.g:3856:5: otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) )
                            {
                            otherlv_5=(Token)match(input,59,FOLLOW_51); 

                            					newLeafNode(otherlv_5, grammarAccess.getStateContainerAccess().getKeepsKeyword_3_2_0());
                            				
                            // InternalThingML.g:3860:5: ( (lv_history_6_0= 'history' ) )
                            // InternalThingML.g:3861:6: (lv_history_6_0= 'history' )
                            {
                            // InternalThingML.g:3861:6: (lv_history_6_0= 'history' )
                            // InternalThingML.g:3862:7: lv_history_6_0= 'history'
                            {
                            lv_history_6_0=(Token)match(input,60,FOLLOW_64); 

                            							newLeafNode(lv_history_6_0, grammarAccess.getStateContainerAccess().getHistoryHistoryKeyword_3_2_1_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getStateContainerRule());
                            							}
                            							setWithLastConsumed(current, "history", true, "history");
                            						

                            }


                            }


                            }
                            break;

                    }

                    otherlv_7=(Token)match(input,25,FOLLOW_60); 

                    				newLeafNode(otherlv_7, grammarAccess.getStateContainerAccess().getLeftCurlyBracketKeyword_3_3());
                    			
                    // InternalThingML.g:3879:4: ( (lv_substate_8_0= ruleState ) )*
                    loop96:
                    do {
                        int alt96=2;
                        int LA96_0 = input.LA(1);

                        if ( (LA96_0==48||LA96_0==57||LA96_0==61||LA96_0==64) ) {
                            alt96=1;
                        }


                        switch (alt96) {
                    	case 1 :
                    	    // InternalThingML.g:3880:5: (lv_substate_8_0= ruleState )
                    	    {
                    	    // InternalThingML.g:3880:5: (lv_substate_8_0= ruleState )
                    	    // InternalThingML.g:3881:6: lv_substate_8_0= ruleState
                    	    {

                    	    						newCompositeNode(grammarAccess.getStateContainerAccess().getSubstateStateParserRuleCall_3_4_0());
                    	    					
                    	    pushFollow(FOLLOW_60);
                    	    lv_substate_8_0=ruleState();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getStateContainerRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"substate",
                    	    							lv_substate_8_0,
                    	    							"org.thingml.xtext.ThingML.State");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop96;
                        }
                    } while (true);

                    otherlv_9=(Token)match(input,26,FOLLOW_2); 

                    				newLeafNode(otherlv_9, grammarAccess.getStateContainerAccess().getRightCurlyBracketKeyword_3_5());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStateContainer"


    // $ANTLR start "entryRuleEvent"
    // InternalThingML.g:3907:1: entryRuleEvent returns [EObject current=null] : iv_ruleEvent= ruleEvent EOF ;
    public final EObject entryRuleEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEvent = null;


        try {
            // InternalThingML.g:3907:46: (iv_ruleEvent= ruleEvent EOF )
            // InternalThingML.g:3908:2: iv_ruleEvent= ruleEvent EOF
            {
             newCompositeNode(grammarAccess.getEventRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEvent=ruleEvent();

            state._fsp--;

             current =iv_ruleEvent; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEvent"


    // $ANTLR start "ruleEvent"
    // InternalThingML.g:3914:1: ruleEvent returns [EObject current=null] : this_ReceiveMessage_0= ruleReceiveMessage ;
    public final EObject ruleEvent() throws RecognitionException {
        EObject current = null;

        EObject this_ReceiveMessage_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3920:2: (this_ReceiveMessage_0= ruleReceiveMessage )
            // InternalThingML.g:3921:2: this_ReceiveMessage_0= ruleReceiveMessage
            {

            		newCompositeNode(grammarAccess.getEventAccess().getReceiveMessageParserRuleCall());
            	
            pushFollow(FOLLOW_2);
            this_ReceiveMessage_0=ruleReceiveMessage();

            state._fsp--;


            		current = this_ReceiveMessage_0;
            		afterParserOrEnumRuleCall();
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEvent"


    // $ANTLR start "entryRuleReceiveMessage"
    // InternalThingML.g:3932:1: entryRuleReceiveMessage returns [EObject current=null] : iv_ruleReceiveMessage= ruleReceiveMessage EOF ;
    public final EObject entryRuleReceiveMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReceiveMessage = null;


        try {
            // InternalThingML.g:3932:55: (iv_ruleReceiveMessage= ruleReceiveMessage EOF )
            // InternalThingML.g:3933:2: iv_ruleReceiveMessage= ruleReceiveMessage EOF
            {
             newCompositeNode(grammarAccess.getReceiveMessageRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleReceiveMessage=ruleReceiveMessage();

            state._fsp--;

             current =iv_ruleReceiveMessage; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleReceiveMessage"


    // $ANTLR start "ruleReceiveMessage"
    // InternalThingML.g:3939:1: ruleReceiveMessage returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) ) ;
    public final EObject ruleReceiveMessage() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;


        	enterRule();

        try {
            // InternalThingML.g:3945:2: ( ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) ) )
            // InternalThingML.g:3946:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) )
            {
            // InternalThingML.g:3946:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) )
            // InternalThingML.g:3947:3: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) )
            {
            // InternalThingML.g:3947:3: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )?
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==RULE_ID) ) {
                int LA98_1 = input.LA(2);

                if ( (LA98_1==16) ) {
                    alt98=1;
                }
            }
            switch (alt98) {
                case 1 :
                    // InternalThingML.g:3948:4: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':'
                    {
                    // InternalThingML.g:3948:4: ( (lv_name_0_0= RULE_ID ) )
                    // InternalThingML.g:3949:5: (lv_name_0_0= RULE_ID )
                    {
                    // InternalThingML.g:3949:5: (lv_name_0_0= RULE_ID )
                    // InternalThingML.g:3950:6: lv_name_0_0= RULE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_7); 

                    						newLeafNode(lv_name_0_0, grammarAccess.getReceiveMessageAccess().getNameIDTerminalRuleCall_0_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getReceiveMessageRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"name",
                    							lv_name_0_0,
                    							"org.thingml.xtext.ThingML.ID");
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,16,FOLLOW_6); 

                    				newLeafNode(otherlv_1, grammarAccess.getReceiveMessageAccess().getColonKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalThingML.g:3971:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:3972:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:3972:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:3973:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReceiveMessageRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_65); 

            					newLeafNode(otherlv_2, grammarAccess.getReceiveMessageAccess().getPortPortCrossReference_1_0());
            				

            }


            }

            otherlv_3=(Token)match(input,65,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getReceiveMessageAccess().getQuestionMarkKeyword_2());
            		
            // InternalThingML.g:3988:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:3989:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:3989:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:3990:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReceiveMessageRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_4, grammarAccess.getReceiveMessageAccess().getMessageMessageCrossReference_3_0());
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleReceiveMessage"


    // $ANTLR start "entryRuleAction"
    // InternalThingML.g:4005:1: entryRuleAction returns [EObject current=null] : iv_ruleAction= ruleAction EOF ;
    public final EObject entryRuleAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAction = null;


        try {
            // InternalThingML.g:4005:47: (iv_ruleAction= ruleAction EOF )
            // InternalThingML.g:4006:2: iv_ruleAction= ruleAction EOF
            {
             newCompositeNode(grammarAccess.getActionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAction=ruleAction();

            state._fsp--;

             current =iv_ruleAction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAction"


    // $ANTLR start "ruleAction"
    // InternalThingML.g:4012:1: ruleAction returns [EObject current=null] : (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable ) ;
    public final EObject ruleAction() throws RecognitionException {
        EObject current = null;

        EObject this_ActionBlock_0 = null;

        EObject this_ExternStatement_1 = null;

        EObject this_SendAction_2 = null;

        EObject this_VariableAssignment_3 = null;

        EObject this_Increment_4 = null;

        EObject this_Decrement_5 = null;

        EObject this_LoopAction_6 = null;

        EObject this_ConditionalAction_7 = null;

        EObject this_ReturnAction_8 = null;

        EObject this_PrintAction_9 = null;

        EObject this_ErrorAction_10 = null;

        EObject this_StartSession_11 = null;

        EObject this_FunctionCallStatement_12 = null;

        EObject this_LocalVariable_13 = null;



        	enterRule();

        try {
            // InternalThingML.g:4018:2: ( (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable ) )
            // InternalThingML.g:4019:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )
            {
            // InternalThingML.g:4019:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )
            int alt99=14;
            alt99 = dfa99.predict(input);
            switch (alt99) {
                case 1 :
                    // InternalThingML.g:4020:3: this_ActionBlock_0= ruleActionBlock
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getActionBlockParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_ActionBlock_0=ruleActionBlock();

                    state._fsp--;


                    			current = this_ActionBlock_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalThingML.g:4029:3: this_ExternStatement_1= ruleExternStatement
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getExternStatementParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExternStatement_1=ruleExternStatement();

                    state._fsp--;


                    			current = this_ExternStatement_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalThingML.g:4038:3: this_SendAction_2= ruleSendAction
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getSendActionParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_SendAction_2=ruleSendAction();

                    state._fsp--;


                    			current = this_SendAction_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalThingML.g:4047:3: this_VariableAssignment_3= ruleVariableAssignment
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getVariableAssignmentParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_VariableAssignment_3=ruleVariableAssignment();

                    state._fsp--;


                    			current = this_VariableAssignment_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalThingML.g:4056:3: this_Increment_4= ruleIncrement
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getIncrementParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_Increment_4=ruleIncrement();

                    state._fsp--;


                    			current = this_Increment_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 6 :
                    // InternalThingML.g:4065:3: this_Decrement_5= ruleDecrement
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getDecrementParserRuleCall_5());
                    		
                    pushFollow(FOLLOW_2);
                    this_Decrement_5=ruleDecrement();

                    state._fsp--;


                    			current = this_Decrement_5;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 7 :
                    // InternalThingML.g:4074:3: this_LoopAction_6= ruleLoopAction
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getLoopActionParserRuleCall_6());
                    		
                    pushFollow(FOLLOW_2);
                    this_LoopAction_6=ruleLoopAction();

                    state._fsp--;


                    			current = this_LoopAction_6;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 8 :
                    // InternalThingML.g:4083:3: this_ConditionalAction_7= ruleConditionalAction
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getConditionalActionParserRuleCall_7());
                    		
                    pushFollow(FOLLOW_2);
                    this_ConditionalAction_7=ruleConditionalAction();

                    state._fsp--;


                    			current = this_ConditionalAction_7;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 9 :
                    // InternalThingML.g:4092:3: this_ReturnAction_8= ruleReturnAction
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getReturnActionParserRuleCall_8());
                    		
                    pushFollow(FOLLOW_2);
                    this_ReturnAction_8=ruleReturnAction();

                    state._fsp--;


                    			current = this_ReturnAction_8;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 10 :
                    // InternalThingML.g:4101:3: this_PrintAction_9= rulePrintAction
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getPrintActionParserRuleCall_9());
                    		
                    pushFollow(FOLLOW_2);
                    this_PrintAction_9=rulePrintAction();

                    state._fsp--;


                    			current = this_PrintAction_9;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 11 :
                    // InternalThingML.g:4110:3: this_ErrorAction_10= ruleErrorAction
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getErrorActionParserRuleCall_10());
                    		
                    pushFollow(FOLLOW_2);
                    this_ErrorAction_10=ruleErrorAction();

                    state._fsp--;


                    			current = this_ErrorAction_10;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 12 :
                    // InternalThingML.g:4119:3: this_StartSession_11= ruleStartSession
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getStartSessionParserRuleCall_11());
                    		
                    pushFollow(FOLLOW_2);
                    this_StartSession_11=ruleStartSession();

                    state._fsp--;


                    			current = this_StartSession_11;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 13 :
                    // InternalThingML.g:4128:3: this_FunctionCallStatement_12= ruleFunctionCallStatement
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getFunctionCallStatementParserRuleCall_12());
                    		
                    pushFollow(FOLLOW_2);
                    this_FunctionCallStatement_12=ruleFunctionCallStatement();

                    state._fsp--;


                    			current = this_FunctionCallStatement_12;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 14 :
                    // InternalThingML.g:4137:3: this_LocalVariable_13= ruleLocalVariable
                    {

                    			newCompositeNode(grammarAccess.getActionAccess().getLocalVariableParserRuleCall_13());
                    		
                    pushFollow(FOLLOW_2);
                    this_LocalVariable_13=ruleLocalVariable();

                    state._fsp--;


                    			current = this_LocalVariable_13;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAction"


    // $ANTLR start "entryRuleActionBlock"
    // InternalThingML.g:4149:1: entryRuleActionBlock returns [EObject current=null] : iv_ruleActionBlock= ruleActionBlock EOF ;
    public final EObject entryRuleActionBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActionBlock = null;


        try {
            // InternalThingML.g:4149:52: (iv_ruleActionBlock= ruleActionBlock EOF )
            // InternalThingML.g:4150:2: iv_ruleActionBlock= ruleActionBlock EOF
            {
             newCompositeNode(grammarAccess.getActionBlockRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleActionBlock=ruleActionBlock();

            state._fsp--;

             current =iv_ruleActionBlock; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActionBlock"


    // $ANTLR start "ruleActionBlock"
    // InternalThingML.g:4156:1: ruleActionBlock returns [EObject current=null] : ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' ) ;
    public final EObject ruleActionBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_actions_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4162:2: ( ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' ) )
            // InternalThingML.g:4163:2: ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' )
            {
            // InternalThingML.g:4163:2: ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' )
            // InternalThingML.g:4164:3: () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end'
            {
            // InternalThingML.g:4164:3: ()
            // InternalThingML.g:4165:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getActionBlockAccess().getActionBlockAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,66,FOLLOW_66); 

            			newLeafNode(otherlv_1, grammarAccess.getActionBlockAccess().getDoKeyword_1());
            		
            // InternalThingML.g:4175:3: ( (lv_actions_2_0= ruleAction ) )*
            loop100:
            do {
                int alt100=2;
                int LA100_0 = input.LA(1);

                if ( (LA100_0==RULE_ID||LA100_0==RULE_STRING_EXT||LA100_0==15||LA100_0==38||LA100_0==66||(LA100_0>=72 && LA100_0<=73)||(LA100_0>=75 && LA100_0<=78)) ) {
                    alt100=1;
                }


                switch (alt100) {
            	case 1 :
            	    // InternalThingML.g:4176:4: (lv_actions_2_0= ruleAction )
            	    {
            	    // InternalThingML.g:4176:4: (lv_actions_2_0= ruleAction )
            	    // InternalThingML.g:4177:5: lv_actions_2_0= ruleAction
            	    {

            	    					newCompositeNode(grammarAccess.getActionBlockAccess().getActionsActionParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_66);
            	    lv_actions_2_0=ruleAction();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getActionBlockRule());
            	    					}
            	    					add(
            	    						current,
            	    						"actions",
            	    						lv_actions_2_0,
            	    						"org.thingml.xtext.ThingML.Action");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop100;
                }
            } while (true);

            otherlv_3=(Token)match(input,67,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getActionBlockAccess().getEndKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActionBlock"


    // $ANTLR start "entryRuleExternStatement"
    // InternalThingML.g:4202:1: entryRuleExternStatement returns [EObject current=null] : iv_ruleExternStatement= ruleExternStatement EOF ;
    public final EObject entryRuleExternStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternStatement = null;


        try {
            // InternalThingML.g:4202:56: (iv_ruleExternStatement= ruleExternStatement EOF )
            // InternalThingML.g:4203:2: iv_ruleExternStatement= ruleExternStatement EOF
            {
             newCompositeNode(grammarAccess.getExternStatementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExternStatement=ruleExternStatement();

            state._fsp--;

             current =iv_ruleExternStatement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExternStatement"


    // $ANTLR start "ruleExternStatement"
    // InternalThingML.g:4209:1: ruleExternStatement returns [EObject current=null] : ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) ;
    public final EObject ruleExternStatement() throws RecognitionException {
        EObject current = null;

        Token lv_statement_0_0=null;
        Token otherlv_1=null;
        EObject lv_segments_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4215:2: ( ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) )
            // InternalThingML.g:4216:2: ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            {
            // InternalThingML.g:4216:2: ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            // InternalThingML.g:4217:3: ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            {
            // InternalThingML.g:4217:3: ( (lv_statement_0_0= RULE_STRING_EXT ) )
            // InternalThingML.g:4218:4: (lv_statement_0_0= RULE_STRING_EXT )
            {
            // InternalThingML.g:4218:4: (lv_statement_0_0= RULE_STRING_EXT )
            // InternalThingML.g:4219:5: lv_statement_0_0= RULE_STRING_EXT
            {
            lv_statement_0_0=(Token)match(input,RULE_STRING_EXT,FOLLOW_67); 

            					newLeafNode(lv_statement_0_0, grammarAccess.getExternStatementAccess().getStatementSTRING_EXTTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternStatementRule());
            					}
            					setWithLastConsumed(
            						current,
            						"statement",
            						lv_statement_0_0,
            						"org.thingml.xtext.ThingML.STRING_EXT");
            				

            }


            }

            // InternalThingML.g:4235:3: (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            loop101:
            do {
                int alt101=2;
                int LA101_0 = input.LA(1);

                if ( (LA101_0==68) ) {
                    alt101=1;
                }


                switch (alt101) {
            	case 1 :
            	    // InternalThingML.g:4236:4: otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,68,FOLLOW_23); 

            	    				newLeafNode(otherlv_1, grammarAccess.getExternStatementAccess().getAmpersandKeyword_1_0());
            	    			
            	    // InternalThingML.g:4240:4: ( (lv_segments_2_0= ruleExpression ) )
            	    // InternalThingML.g:4241:5: (lv_segments_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:4241:5: (lv_segments_2_0= ruleExpression )
            	    // InternalThingML.g:4242:6: lv_segments_2_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getExternStatementAccess().getSegmentsExpressionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_67);
            	    lv_segments_2_0=ruleExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getExternStatementRule());
            	    						}
            	    						add(
            	    							current,
            	    							"segments",
            	    							lv_segments_2_0,
            	    							"org.thingml.xtext.ThingML.Expression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop101;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExternStatement"


    // $ANTLR start "entryRuleLocalVariable"
    // InternalThingML.g:4264:1: entryRuleLocalVariable returns [EObject current=null] : iv_ruleLocalVariable= ruleLocalVariable EOF ;
    public final EObject entryRuleLocalVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalVariable = null;


        try {
            // InternalThingML.g:4264:54: (iv_ruleLocalVariable= ruleLocalVariable EOF )
            // InternalThingML.g:4265:2: iv_ruleLocalVariable= ruleLocalVariable EOF
            {
             newCompositeNode(grammarAccess.getLocalVariableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLocalVariable=ruleLocalVariable();

            state._fsp--;

             current =iv_ruleLocalVariable; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalVariable"


    // $ANTLR start "ruleLocalVariable"
    // InternalThingML.g:4271:1: ruleLocalVariable returns [EObject current=null] : ( ( (lv_readonly_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleLocalVariable() throws RecognitionException {
        EObject current = null;

        Token lv_readonly_0_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_typeRef_4_0 = null;

        EObject lv_init_6_0 = null;

        EObject lv_annotations_7_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4277:2: ( ( ( (lv_readonly_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:4278:2: ( ( (lv_readonly_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:4278:2: ( ( (lv_readonly_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:4279:3: ( (lv_readonly_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:4279:3: ( (lv_readonly_0_0= 'readonly' ) )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==38) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // InternalThingML.g:4280:4: (lv_readonly_0_0= 'readonly' )
                    {
                    // InternalThingML.g:4280:4: (lv_readonly_0_0= 'readonly' )
                    // InternalThingML.g:4281:5: lv_readonly_0_0= 'readonly'
                    {
                    lv_readonly_0_0=(Token)match(input,38,FOLLOW_68); 

                    					newLeafNode(lv_readonly_0_0, grammarAccess.getLocalVariableAccess().getReadonlyReadonlyKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getLocalVariableRule());
                    					}
                    					setWithLastConsumed(current, "readonly", true, "readonly");
                    				

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,15,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getLocalVariableAccess().getVarKeyword_1());
            		
            // InternalThingML.g:4297:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:4298:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:4298:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:4299:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(lv_name_2_0, grammarAccess.getLocalVariableAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLocalVariableRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_3=(Token)match(input,16,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getLocalVariableAccess().getColonKeyword_3());
            		
            // InternalThingML.g:4319:3: ( (lv_typeRef_4_0= ruleTypeRef ) )
            // InternalThingML.g:4320:4: (lv_typeRef_4_0= ruleTypeRef )
            {
            // InternalThingML.g:4320:4: (lv_typeRef_4_0= ruleTypeRef )
            // InternalThingML.g:4321:5: lv_typeRef_4_0= ruleTypeRef
            {

            					newCompositeNode(grammarAccess.getLocalVariableAccess().getTypeRefTypeRefParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_32);
            lv_typeRef_4_0=ruleTypeRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLocalVariableRule());
            					}
            					set(
            						current,
            						"typeRef",
            						lv_typeRef_4_0,
            						"org.thingml.xtext.ThingML.TypeRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:4338:3: (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )?
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==32) ) {
                alt103=1;
            }
            switch (alt103) {
                case 1 :
                    // InternalThingML.g:4339:4: otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) )
                    {
                    otherlv_5=(Token)match(input,32,FOLLOW_23); 

                    				newLeafNode(otherlv_5, grammarAccess.getLocalVariableAccess().getEqualsSignKeyword_5_0());
                    			
                    // InternalThingML.g:4343:4: ( (lv_init_6_0= ruleExpression ) )
                    // InternalThingML.g:4344:5: (lv_init_6_0= ruleExpression )
                    {
                    // InternalThingML.g:4344:5: (lv_init_6_0= ruleExpression )
                    // InternalThingML.g:4345:6: lv_init_6_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getLocalVariableAccess().getInitExpressionParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_init_6_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLocalVariableRule());
                    						}
                    						set(
                    							current,
                    							"init",
                    							lv_init_6_0,
                    							"org.thingml.xtext.ThingML.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:4363:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop104:
            do {
                int alt104=2;
                int LA104_0 = input.LA(1);

                if ( (LA104_0==RULE_ANNOTATION_ID) ) {
                    alt104=1;
                }


                switch (alt104) {
            	case 1 :
            	    // InternalThingML.g:4364:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:4364:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:4365:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getLocalVariableAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_17);
            	    lv_annotations_7_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getLocalVariableRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_7_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop104;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalVariable"


    // $ANTLR start "entryRuleSendAction"
    // InternalThingML.g:4386:1: entryRuleSendAction returns [EObject current=null] : iv_ruleSendAction= ruleSendAction EOF ;
    public final EObject entryRuleSendAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSendAction = null;


        try {
            // InternalThingML.g:4386:51: (iv_ruleSendAction= ruleSendAction EOF )
            // InternalThingML.g:4387:2: iv_ruleSendAction= ruleSendAction EOF
            {
             newCompositeNode(grammarAccess.getSendActionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSendAction=ruleSendAction();

            state._fsp--;

             current =iv_ruleSendAction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSendAction"


    // $ANTLR start "ruleSendAction"
    // InternalThingML.g:4393:1: ruleSendAction returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' ) ;
    public final EObject ruleSendAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_parameters_4_0 = null;

        EObject lv_parameters_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4399:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' ) )
            // InternalThingML.g:4400:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' )
            {
            // InternalThingML.g:4400:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' )
            // InternalThingML.g:4401:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')'
            {
            // InternalThingML.g:4401:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4402:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4402:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4403:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSendActionRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_69); 

            					newLeafNode(otherlv_0, grammarAccess.getSendActionAccess().getPortPortCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,69,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getSendActionAccess().getExclamationMarkKeyword_1());
            		
            // InternalThingML.g:4418:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:4419:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:4419:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:4420:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSendActionRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_25); 

            					newLeafNode(otherlv_2, grammarAccess.getSendActionAccess().getMessageMessageCrossReference_2_0());
            				

            }


            }

            otherlv_3=(Token)match(input,35,FOLLOW_70); 

            			newLeafNode(otherlv_3, grammarAccess.getSendActionAccess().getLeftParenthesisKeyword_3());
            		
            // InternalThingML.g:4435:3: ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==RULE_STRING_LIT||(LA106_0>=RULE_ID && LA106_0<=RULE_FLOAT)||LA106_0==35||LA106_0==87||(LA106_0>=91 && LA106_0<=93)) ) {
                alt106=1;
            }
            switch (alt106) {
                case 1 :
                    // InternalThingML.g:4436:4: ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )*
                    {
                    // InternalThingML.g:4436:4: ( (lv_parameters_4_0= ruleExpression ) )
                    // InternalThingML.g:4437:5: (lv_parameters_4_0= ruleExpression )
                    {
                    // InternalThingML.g:4437:5: (lv_parameters_4_0= ruleExpression )
                    // InternalThingML.g:4438:6: lv_parameters_4_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_0_0());
                    					
                    pushFollow(FOLLOW_27);
                    lv_parameters_4_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getSendActionRule());
                    						}
                    						add(
                    							current,
                    							"parameters",
                    							lv_parameters_4_0,
                    							"org.thingml.xtext.ThingML.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalThingML.g:4455:4: (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )*
                    loop105:
                    do {
                        int alt105=2;
                        int LA105_0 = input.LA(1);

                        if ( (LA105_0==30) ) {
                            alt105=1;
                        }


                        switch (alt105) {
                    	case 1 :
                    	    // InternalThingML.g:4456:5: otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) )
                    	    {
                    	    otherlv_5=(Token)match(input,30,FOLLOW_23); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getSendActionAccess().getCommaKeyword_4_1_0());
                    	    				
                    	    // InternalThingML.g:4460:5: ( (lv_parameters_6_0= ruleExpression ) )
                    	    // InternalThingML.g:4461:6: (lv_parameters_6_0= ruleExpression )
                    	    {
                    	    // InternalThingML.g:4461:6: (lv_parameters_6_0= ruleExpression )
                    	    // InternalThingML.g:4462:7: lv_parameters_6_0= ruleExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_27);
                    	    lv_parameters_6_0=ruleExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getSendActionRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"parameters",
                    	    								lv_parameters_6_0,
                    	    								"org.thingml.xtext.ThingML.Expression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop105;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_7=(Token)match(input,36,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getSendActionAccess().getRightParenthesisKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSendAction"


    // $ANTLR start "entryRuleVariableAssignment"
    // InternalThingML.g:4489:1: entryRuleVariableAssignment returns [EObject current=null] : iv_ruleVariableAssignment= ruleVariableAssignment EOF ;
    public final EObject entryRuleVariableAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableAssignment = null;


        try {
            // InternalThingML.g:4489:59: (iv_ruleVariableAssignment= ruleVariableAssignment EOF )
            // InternalThingML.g:4490:2: iv_ruleVariableAssignment= ruleVariableAssignment EOF
            {
             newCompositeNode(grammarAccess.getVariableAssignmentRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariableAssignment=ruleVariableAssignment();

            state._fsp--;

             current =iv_ruleVariableAssignment; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariableAssignment"


    // $ANTLR start "ruleVariableAssignment"
    // InternalThingML.g:4496:1: ruleVariableAssignment returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) ) ;
    public final EObject ruleVariableAssignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        EObject lv_index_2_0 = null;

        EObject lv_expression_5_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4502:2: ( ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) ) )
            // InternalThingML.g:4503:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) )
            {
            // InternalThingML.g:4503:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) )
            // InternalThingML.g:4504:3: ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) )
            {
            // InternalThingML.g:4504:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4505:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4505:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4506:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVariableAssignmentRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_22); 

            					newLeafNode(otherlv_0, grammarAccess.getVariableAssignmentAccess().getPropertyVariableCrossReference_0_0());
            				

            }


            }

            // InternalThingML.g:4517:3: (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )*
            loop107:
            do {
                int alt107=2;
                int LA107_0 = input.LA(1);

                if ( (LA107_0==17) ) {
                    alt107=1;
                }


                switch (alt107) {
            	case 1 :
            	    // InternalThingML.g:4518:4: otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']'
            	    {
            	    otherlv_1=(Token)match(input,17,FOLLOW_23); 

            	    				newLeafNode(otherlv_1, grammarAccess.getVariableAssignmentAccess().getLeftSquareBracketKeyword_1_0());
            	    			
            	    // InternalThingML.g:4522:4: ( (lv_index_2_0= ruleExpression ) )
            	    // InternalThingML.g:4523:5: (lv_index_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:4523:5: (lv_index_2_0= ruleExpression )
            	    // InternalThingML.g:4524:6: lv_index_2_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getVariableAssignmentAccess().getIndexExpressionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_10);
            	    lv_index_2_0=ruleExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getVariableAssignmentRule());
            	    						}
            	    						add(
            	    							current,
            	    							"index",
            	    							lv_index_2_0,
            	    							"org.thingml.xtext.ThingML.Expression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    otherlv_3=(Token)match(input,18,FOLLOW_22); 

            	    				newLeafNode(otherlv_3, grammarAccess.getVariableAssignmentAccess().getRightSquareBracketKeyword_1_2());
            	    			

            	    }
            	    break;

            	default :
            	    break loop107;
                }
            } while (true);

            otherlv_4=(Token)match(input,32,FOLLOW_23); 

            			newLeafNode(otherlv_4, grammarAccess.getVariableAssignmentAccess().getEqualsSignKeyword_2());
            		
            // InternalThingML.g:4550:3: ( (lv_expression_5_0= ruleExpression ) )
            // InternalThingML.g:4551:4: (lv_expression_5_0= ruleExpression )
            {
            // InternalThingML.g:4551:4: (lv_expression_5_0= ruleExpression )
            // InternalThingML.g:4552:5: lv_expression_5_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getVariableAssignmentAccess().getExpressionExpressionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_expression_5_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getVariableAssignmentRule());
            					}
            					set(
            						current,
            						"expression",
            						lv_expression_5_0,
            						"org.thingml.xtext.ThingML.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariableAssignment"


    // $ANTLR start "entryRuleIncrement"
    // InternalThingML.g:4573:1: entryRuleIncrement returns [EObject current=null] : iv_ruleIncrement= ruleIncrement EOF ;
    public final EObject entryRuleIncrement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIncrement = null;


        try {
            // InternalThingML.g:4573:50: (iv_ruleIncrement= ruleIncrement EOF )
            // InternalThingML.g:4574:2: iv_ruleIncrement= ruleIncrement EOF
            {
             newCompositeNode(grammarAccess.getIncrementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIncrement=ruleIncrement();

            state._fsp--;

             current =iv_ruleIncrement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIncrement"


    // $ANTLR start "ruleIncrement"
    // InternalThingML.g:4580:1: ruleIncrement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' ) ;
    public final EObject ruleIncrement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:4586:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' ) )
            // InternalThingML.g:4587:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' )
            {
            // InternalThingML.g:4587:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' )
            // InternalThingML.g:4588:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '++'
            {
            // InternalThingML.g:4588:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4589:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4589:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4590:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getIncrementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_71); 

            					newLeafNode(otherlv_0, grammarAccess.getIncrementAccess().getVarVariableCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,70,FOLLOW_2); 

            			newLeafNode(otherlv_1, grammarAccess.getIncrementAccess().getPlusSignPlusSignKeyword_1());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIncrement"


    // $ANTLR start "entryRuleDecrement"
    // InternalThingML.g:4609:1: entryRuleDecrement returns [EObject current=null] : iv_ruleDecrement= ruleDecrement EOF ;
    public final EObject entryRuleDecrement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDecrement = null;


        try {
            // InternalThingML.g:4609:50: (iv_ruleDecrement= ruleDecrement EOF )
            // InternalThingML.g:4610:2: iv_ruleDecrement= ruleDecrement EOF
            {
             newCompositeNode(grammarAccess.getDecrementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDecrement=ruleDecrement();

            state._fsp--;

             current =iv_ruleDecrement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDecrement"


    // $ANTLR start "ruleDecrement"
    // InternalThingML.g:4616:1: ruleDecrement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' ) ;
    public final EObject ruleDecrement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:4622:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' ) )
            // InternalThingML.g:4623:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' )
            {
            // InternalThingML.g:4623:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' )
            // InternalThingML.g:4624:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '--'
            {
            // InternalThingML.g:4624:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4625:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4625:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4626:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDecrementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_72); 

            					newLeafNode(otherlv_0, grammarAccess.getDecrementAccess().getVarVariableCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,71,FOLLOW_2); 

            			newLeafNode(otherlv_1, grammarAccess.getDecrementAccess().getHyphenMinusHyphenMinusKeyword_1());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDecrement"


    // $ANTLR start "entryRuleLoopAction"
    // InternalThingML.g:4645:1: entryRuleLoopAction returns [EObject current=null] : iv_ruleLoopAction= ruleLoopAction EOF ;
    public final EObject entryRuleLoopAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLoopAction = null;


        try {
            // InternalThingML.g:4645:51: (iv_ruleLoopAction= ruleLoopAction EOF )
            // InternalThingML.g:4646:2: iv_ruleLoopAction= ruleLoopAction EOF
            {
             newCompositeNode(grammarAccess.getLoopActionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLoopAction=ruleLoopAction();

            state._fsp--;

             current =iv_ruleLoopAction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLoopAction"


    // $ANTLR start "ruleLoopAction"
    // InternalThingML.g:4652:1: ruleLoopAction returns [EObject current=null] : (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) ) ;
    public final EObject ruleLoopAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_condition_2_0 = null;

        EObject lv_action_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4658:2: ( (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) ) )
            // InternalThingML.g:4659:2: (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) )
            {
            // InternalThingML.g:4659:2: (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) )
            // InternalThingML.g:4660:3: otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) )
            {
            otherlv_0=(Token)match(input,72,FOLLOW_25); 

            			newLeafNode(otherlv_0, grammarAccess.getLoopActionAccess().getWhileKeyword_0());
            		
            otherlv_1=(Token)match(input,35,FOLLOW_23); 

            			newLeafNode(otherlv_1, grammarAccess.getLoopActionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:4668:3: ( (lv_condition_2_0= ruleExpression ) )
            // InternalThingML.g:4669:4: (lv_condition_2_0= ruleExpression )
            {
            // InternalThingML.g:4669:4: (lv_condition_2_0= ruleExpression )
            // InternalThingML.g:4670:5: lv_condition_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getLoopActionAccess().getConditionExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_73);
            lv_condition_2_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLoopActionRule());
            					}
            					set(
            						current,
            						"condition",
            						lv_condition_2_0,
            						"org.thingml.xtext.ThingML.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,36,FOLLOW_28); 

            			newLeafNode(otherlv_3, grammarAccess.getLoopActionAccess().getRightParenthesisKeyword_3());
            		
            // InternalThingML.g:4691:3: ( (lv_action_4_0= ruleAction ) )
            // InternalThingML.g:4692:4: (lv_action_4_0= ruleAction )
            {
            // InternalThingML.g:4692:4: (lv_action_4_0= ruleAction )
            // InternalThingML.g:4693:5: lv_action_4_0= ruleAction
            {

            					newCompositeNode(grammarAccess.getLoopActionAccess().getActionActionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_2);
            lv_action_4_0=ruleAction();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLoopActionRule());
            					}
            					set(
            						current,
            						"action",
            						lv_action_4_0,
            						"org.thingml.xtext.ThingML.Action");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLoopAction"


    // $ANTLR start "entryRuleConditionalAction"
    // InternalThingML.g:4714:1: entryRuleConditionalAction returns [EObject current=null] : iv_ruleConditionalAction= ruleConditionalAction EOF ;
    public final EObject entryRuleConditionalAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionalAction = null;


        try {
            // InternalThingML.g:4714:58: (iv_ruleConditionalAction= ruleConditionalAction EOF )
            // InternalThingML.g:4715:2: iv_ruleConditionalAction= ruleConditionalAction EOF
            {
             newCompositeNode(grammarAccess.getConditionalActionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConditionalAction=ruleConditionalAction();

            state._fsp--;

             current =iv_ruleConditionalAction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConditionalAction"


    // $ANTLR start "ruleConditionalAction"
    // InternalThingML.g:4721:1: ruleConditionalAction returns [EObject current=null] : (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? ) ;
    public final EObject ruleConditionalAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_condition_2_0 = null;

        EObject lv_action_4_0 = null;

        EObject lv_elseAction_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4727:2: ( (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? ) )
            // InternalThingML.g:4728:2: (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:4728:2: (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? )
            // InternalThingML.g:4729:3: otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )?
            {
            otherlv_0=(Token)match(input,73,FOLLOW_25); 

            			newLeafNode(otherlv_0, grammarAccess.getConditionalActionAccess().getIfKeyword_0());
            		
            otherlv_1=(Token)match(input,35,FOLLOW_23); 

            			newLeafNode(otherlv_1, grammarAccess.getConditionalActionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:4737:3: ( (lv_condition_2_0= ruleExpression ) )
            // InternalThingML.g:4738:4: (lv_condition_2_0= ruleExpression )
            {
            // InternalThingML.g:4738:4: (lv_condition_2_0= ruleExpression )
            // InternalThingML.g:4739:5: lv_condition_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getConditionalActionAccess().getConditionExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_73);
            lv_condition_2_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConditionalActionRule());
            					}
            					set(
            						current,
            						"condition",
            						lv_condition_2_0,
            						"org.thingml.xtext.ThingML.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,36,FOLLOW_28); 

            			newLeafNode(otherlv_3, grammarAccess.getConditionalActionAccess().getRightParenthesisKeyword_3());
            		
            // InternalThingML.g:4760:3: ( (lv_action_4_0= ruleAction ) )
            // InternalThingML.g:4761:4: (lv_action_4_0= ruleAction )
            {
            // InternalThingML.g:4761:4: (lv_action_4_0= ruleAction )
            // InternalThingML.g:4762:5: lv_action_4_0= ruleAction
            {

            					newCompositeNode(grammarAccess.getConditionalActionAccess().getActionActionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_74);
            lv_action_4_0=ruleAction();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConditionalActionRule());
            					}
            					set(
            						current,
            						"action",
            						lv_action_4_0,
            						"org.thingml.xtext.ThingML.Action");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:4779:3: (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )?
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==74) ) {
                alt108=1;
            }
            switch (alt108) {
                case 1 :
                    // InternalThingML.g:4780:4: otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) )
                    {
                    otherlv_5=(Token)match(input,74,FOLLOW_28); 

                    				newLeafNode(otherlv_5, grammarAccess.getConditionalActionAccess().getElseKeyword_5_0());
                    			
                    // InternalThingML.g:4784:4: ( (lv_elseAction_6_0= ruleAction ) )
                    // InternalThingML.g:4785:5: (lv_elseAction_6_0= ruleAction )
                    {
                    // InternalThingML.g:4785:5: (lv_elseAction_6_0= ruleAction )
                    // InternalThingML.g:4786:6: lv_elseAction_6_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getConditionalActionAccess().getElseActionActionParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_elseAction_6_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getConditionalActionRule());
                    						}
                    						set(
                    							current,
                    							"elseAction",
                    							lv_elseAction_6_0,
                    							"org.thingml.xtext.ThingML.Action");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConditionalAction"


    // $ANTLR start "entryRuleReturnAction"
    // InternalThingML.g:4808:1: entryRuleReturnAction returns [EObject current=null] : iv_ruleReturnAction= ruleReturnAction EOF ;
    public final EObject entryRuleReturnAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReturnAction = null;


        try {
            // InternalThingML.g:4808:53: (iv_ruleReturnAction= ruleReturnAction EOF )
            // InternalThingML.g:4809:2: iv_ruleReturnAction= ruleReturnAction EOF
            {
             newCompositeNode(grammarAccess.getReturnActionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleReturnAction=ruleReturnAction();

            state._fsp--;

             current =iv_ruleReturnAction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleReturnAction"


    // $ANTLR start "ruleReturnAction"
    // InternalThingML.g:4815:1: ruleReturnAction returns [EObject current=null] : (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) ) ;
    public final EObject ruleReturnAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_exp_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4821:2: ( (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:4822:2: (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:4822:2: (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) )
            // InternalThingML.g:4823:3: otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,75,FOLLOW_23); 

            			newLeafNode(otherlv_0, grammarAccess.getReturnActionAccess().getReturnKeyword_0());
            		
            // InternalThingML.g:4827:3: ( (lv_exp_1_0= ruleExpression ) )
            // InternalThingML.g:4828:4: (lv_exp_1_0= ruleExpression )
            {
            // InternalThingML.g:4828:4: (lv_exp_1_0= ruleExpression )
            // InternalThingML.g:4829:5: lv_exp_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getReturnActionAccess().getExpExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_exp_1_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getReturnActionRule());
            					}
            					set(
            						current,
            						"exp",
            						lv_exp_1_0,
            						"org.thingml.xtext.ThingML.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleReturnAction"


    // $ANTLR start "entryRulePrintAction"
    // InternalThingML.g:4850:1: entryRulePrintAction returns [EObject current=null] : iv_rulePrintAction= rulePrintAction EOF ;
    public final EObject entryRulePrintAction() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrintAction = null;


        try {
            // InternalThingML.g:4850:52: (iv_rulePrintAction= rulePrintAction EOF )
            // InternalThingML.g:4851:2: iv_rulePrintAction= rulePrintAction EOF
            {
             newCompositeNode(grammarAccess.getPrintActionRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePrintAction=rulePrintAction();

            state._fsp--;

             current =iv_rulePrintAction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrintAction"


    // $ANTLR start "rulePrintAction"
    // InternalThingML.g:4857:1: rulePrintAction returns [EObject current=null] : (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) ) ;
    public final EObject rulePrintAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_msg_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4863:2: ( (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:4864:2: (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:4864:2: (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) )
            // InternalThingML.g:4865:3: otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,76,FOLLOW_23); 

            			newLeafNode(otherlv_0, grammarAccess.getPrintActionAccess().getPrintKeyword_0());
            		
            // InternalThingML.g:4869:3: ( (lv_msg_1_0= ruleExpression ) )
            // InternalThingML.g:4870:4: (lv_msg_1_0= ruleExpression )
            {
            // InternalThingML.g:4870:4: (lv_msg_1_0= ruleExpression )
            // InternalThingML.g:4871:5: lv_msg_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getPrintActionAccess().getMsgExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_msg_1_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPrintActionRule());
            					}
            					set(
            						current,
            						"msg",
            						lv_msg_1_0,
            						"org.thingml.xtext.ThingML.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrintAction"


    // $ANTLR start "entryRuleErrorAction"
    // InternalThingML.g:4892:1: entryRuleErrorAction returns [EObject current=null] : iv_ruleErrorAction= ruleErrorAction EOF ;
    public final EObject entryRuleErrorAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleErrorAction = null;


        try {
            // InternalThingML.g:4892:52: (iv_ruleErrorAction= ruleErrorAction EOF )
            // InternalThingML.g:4893:2: iv_ruleErrorAction= ruleErrorAction EOF
            {
             newCompositeNode(grammarAccess.getErrorActionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleErrorAction=ruleErrorAction();

            state._fsp--;

             current =iv_ruleErrorAction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleErrorAction"


    // $ANTLR start "ruleErrorAction"
    // InternalThingML.g:4899:1: ruleErrorAction returns [EObject current=null] : (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) ) ;
    public final EObject ruleErrorAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_msg_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4905:2: ( (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:4906:2: (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:4906:2: (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) )
            // InternalThingML.g:4907:3: otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,77,FOLLOW_23); 

            			newLeafNode(otherlv_0, grammarAccess.getErrorActionAccess().getErrorKeyword_0());
            		
            // InternalThingML.g:4911:3: ( (lv_msg_1_0= ruleExpression ) )
            // InternalThingML.g:4912:4: (lv_msg_1_0= ruleExpression )
            {
            // InternalThingML.g:4912:4: (lv_msg_1_0= ruleExpression )
            // InternalThingML.g:4913:5: lv_msg_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getErrorActionAccess().getMsgExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_msg_1_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getErrorActionRule());
            					}
            					set(
            						current,
            						"msg",
            						lv_msg_1_0,
            						"org.thingml.xtext.ThingML.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleErrorAction"


    // $ANTLR start "entryRuleStartSession"
    // InternalThingML.g:4934:1: entryRuleStartSession returns [EObject current=null] : iv_ruleStartSession= ruleStartSession EOF ;
    public final EObject entryRuleStartSession() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStartSession = null;


        try {
            // InternalThingML.g:4934:53: (iv_ruleStartSession= ruleStartSession EOF )
            // InternalThingML.g:4935:2: iv_ruleStartSession= ruleStartSession EOF
            {
             newCompositeNode(grammarAccess.getStartSessionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStartSession=ruleStartSession();

            state._fsp--;

             current =iv_ruleStartSession; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStartSession"


    // $ANTLR start "ruleStartSession"
    // InternalThingML.g:4941:1: ruleStartSession returns [EObject current=null] : (otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) ) ) ;
    public final EObject ruleStartSession() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:4947:2: ( (otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) ) ) )
            // InternalThingML.g:4948:2: (otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) ) )
            {
            // InternalThingML.g:4948:2: (otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) ) )
            // InternalThingML.g:4949:3: otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,78,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getStartSessionAccess().getForkKeyword_0());
            		
            // InternalThingML.g:4953:3: ( (otherlv_1= RULE_ID ) )
            // InternalThingML.g:4954:4: (otherlv_1= RULE_ID )
            {
            // InternalThingML.g:4954:4: (otherlv_1= RULE_ID )
            // InternalThingML.g:4955:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStartSessionRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_1, grammarAccess.getStartSessionAccess().getSessionSessionCrossReference_1_0());
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStartSession"


    // $ANTLR start "entryRuleFunctionCallStatement"
    // InternalThingML.g:4970:1: entryRuleFunctionCallStatement returns [EObject current=null] : iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF ;
    public final EObject entryRuleFunctionCallStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCallStatement = null;


        try {
            // InternalThingML.g:4970:62: (iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF )
            // InternalThingML.g:4971:2: iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF
            {
             newCompositeNode(grammarAccess.getFunctionCallStatementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFunctionCallStatement=ruleFunctionCallStatement();

            state._fsp--;

             current =iv_ruleFunctionCallStatement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunctionCallStatement"


    // $ANTLR start "ruleFunctionCallStatement"
    // InternalThingML.g:4977:1: ruleFunctionCallStatement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) ;
    public final EObject ruleFunctionCallStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4983:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) )
            // InternalThingML.g:4984:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            {
            // InternalThingML.g:4984:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            // InternalThingML.g:4985:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')'
            {
            // InternalThingML.g:4985:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4986:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4986:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4987:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFunctionCallStatementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_25); 

            					newLeafNode(otherlv_0, grammarAccess.getFunctionCallStatementAccess().getFunctionFunctionCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,35,FOLLOW_70); 

            			newLeafNode(otherlv_1, grammarAccess.getFunctionCallStatementAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:5002:3: ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )?
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==RULE_STRING_LIT||(LA110_0>=RULE_ID && LA110_0<=RULE_FLOAT)||LA110_0==35||LA110_0==87||(LA110_0>=91 && LA110_0<=93)) ) {
                alt110=1;
            }
            switch (alt110) {
                case 1 :
                    // InternalThingML.g:5003:4: ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    {
                    // InternalThingML.g:5003:4: ( (lv_parameters_2_0= ruleExpression ) )
                    // InternalThingML.g:5004:5: (lv_parameters_2_0= ruleExpression )
                    {
                    // InternalThingML.g:5004:5: (lv_parameters_2_0= ruleExpression )
                    // InternalThingML.g:5005:6: lv_parameters_2_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_27);
                    lv_parameters_2_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFunctionCallStatementRule());
                    						}
                    						add(
                    							current,
                    							"parameters",
                    							lv_parameters_2_0,
                    							"org.thingml.xtext.ThingML.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalThingML.g:5022:4: (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    loop109:
                    do {
                        int alt109=2;
                        int LA109_0 = input.LA(1);

                        if ( (LA109_0==30) ) {
                            alt109=1;
                        }


                        switch (alt109) {
                    	case 1 :
                    	    // InternalThingML.g:5023:5: otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,30,FOLLOW_23); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getFunctionCallStatementAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalThingML.g:5027:5: ( (lv_parameters_4_0= ruleExpression ) )
                    	    // InternalThingML.g:5028:6: (lv_parameters_4_0= ruleExpression )
                    	    {
                    	    // InternalThingML.g:5028:6: (lv_parameters_4_0= ruleExpression )
                    	    // InternalThingML.g:5029:7: lv_parameters_4_0= ruleExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_27);
                    	    lv_parameters_4_0=ruleExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getFunctionCallStatementRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"parameters",
                    	    								lv_parameters_4_0,
                    	    								"org.thingml.xtext.ThingML.Expression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop109;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,36,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getFunctionCallStatementAccess().getRightParenthesisKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFunctionCallStatement"


    // $ANTLR start "entryRuleExpression"
    // InternalThingML.g:5056:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // InternalThingML.g:5056:51: (iv_ruleExpression= ruleExpression EOF )
            // InternalThingML.g:5057:2: iv_ruleExpression= ruleExpression EOF
            {
             newCompositeNode(grammarAccess.getExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpression=ruleExpression();

            state._fsp--;

             current =iv_ruleExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpression"


    // $ANTLR start "ruleExpression"
    // InternalThingML.g:5063:1: ruleExpression returns [EObject current=null] : this_CastExpression_0= ruleCastExpression ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject this_CastExpression_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5069:2: (this_CastExpression_0= ruleCastExpression )
            // InternalThingML.g:5070:2: this_CastExpression_0= ruleCastExpression
            {

            		newCompositeNode(grammarAccess.getExpressionAccess().getCastExpressionParserRuleCall());
            	
            pushFollow(FOLLOW_2);
            this_CastExpression_0=ruleCastExpression();

            state._fsp--;


            		current = this_CastExpression_0;
            		afterParserOrEnumRuleCall();
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpression"


    // $ANTLR start "entryRuleCastExpression"
    // InternalThingML.g:5081:1: entryRuleCastExpression returns [EObject current=null] : iv_ruleCastExpression= ruleCastExpression EOF ;
    public final EObject entryRuleCastExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCastExpression = null;


        try {
            // InternalThingML.g:5081:55: (iv_ruleCastExpression= ruleCastExpression EOF )
            // InternalThingML.g:5082:2: iv_ruleCastExpression= ruleCastExpression EOF
            {
             newCompositeNode(grammarAccess.getCastExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCastExpression=ruleCastExpression();

            state._fsp--;

             current =iv_ruleCastExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCastExpression"


    // $ANTLR start "ruleCastExpression"
    // InternalThingML.g:5088:1: ruleCastExpression returns [EObject current=null] : (this_OrExpression_0= ruleOrExpression ( () otherlv_2= 'as' ( (otherlv_3= RULE_ID ) ) ( ( (lv_isArray_4_0= '[' ) ) otherlv_5= ']' )? )? ) ;
    public final EObject ruleCastExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_isArray_4_0=null;
        Token otherlv_5=null;
        EObject this_OrExpression_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5094:2: ( (this_OrExpression_0= ruleOrExpression ( () otherlv_2= 'as' ( (otherlv_3= RULE_ID ) ) ( ( (lv_isArray_4_0= '[' ) ) otherlv_5= ']' )? )? ) )
            // InternalThingML.g:5095:2: (this_OrExpression_0= ruleOrExpression ( () otherlv_2= 'as' ( (otherlv_3= RULE_ID ) ) ( ( (lv_isArray_4_0= '[' ) ) otherlv_5= ']' )? )? )
            {
            // InternalThingML.g:5095:2: (this_OrExpression_0= ruleOrExpression ( () otherlv_2= 'as' ( (otherlv_3= RULE_ID ) ) ( ( (lv_isArray_4_0= '[' ) ) otherlv_5= ']' )? )? )
            // InternalThingML.g:5096:3: this_OrExpression_0= ruleOrExpression ( () otherlv_2= 'as' ( (otherlv_3= RULE_ID ) ) ( ( (lv_isArray_4_0= '[' ) ) otherlv_5= ']' )? )?
            {

            			newCompositeNode(grammarAccess.getCastExpressionAccess().getOrExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_75);
            this_OrExpression_0=ruleOrExpression();

            state._fsp--;


            			current = this_OrExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5104:3: ( () otherlv_2= 'as' ( (otherlv_3= RULE_ID ) ) ( ( (lv_isArray_4_0= '[' ) ) otherlv_5= ']' )? )?
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==79) ) {
                alt112=1;
            }
            switch (alt112) {
                case 1 :
                    // InternalThingML.g:5105:4: () otherlv_2= 'as' ( (otherlv_3= RULE_ID ) ) ( ( (lv_isArray_4_0= '[' ) ) otherlv_5= ']' )?
                    {
                    // InternalThingML.g:5105:4: ()
                    // InternalThingML.g:5106:5: 
                    {

                    					current = forceCreateModelElementAndSet(
                    						grammarAccess.getCastExpressionAccess().getCastExpressionTermAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_2=(Token)match(input,79,FOLLOW_6); 

                    				newLeafNode(otherlv_2, grammarAccess.getCastExpressionAccess().getAsKeyword_1_1());
                    			
                    // InternalThingML.g:5116:4: ( (otherlv_3= RULE_ID ) )
                    // InternalThingML.g:5117:5: (otherlv_3= RULE_ID )
                    {
                    // InternalThingML.g:5117:5: (otherlv_3= RULE_ID )
                    // InternalThingML.g:5118:6: otherlv_3= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getCastExpressionRule());
                    						}
                    					
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_8); 

                    						newLeafNode(otherlv_3, grammarAccess.getCastExpressionAccess().getTypeTypeCrossReference_1_2_0());
                    					

                    }


                    }

                    // InternalThingML.g:5129:4: ( ( (lv_isArray_4_0= '[' ) ) otherlv_5= ']' )?
                    int alt111=2;
                    int LA111_0 = input.LA(1);

                    if ( (LA111_0==17) ) {
                        int LA111_1 = input.LA(2);

                        if ( (LA111_1==18) ) {
                            alt111=1;
                        }
                    }
                    switch (alt111) {
                        case 1 :
                            // InternalThingML.g:5130:5: ( (lv_isArray_4_0= '[' ) ) otherlv_5= ']'
                            {
                            // InternalThingML.g:5130:5: ( (lv_isArray_4_0= '[' ) )
                            // InternalThingML.g:5131:6: (lv_isArray_4_0= '[' )
                            {
                            // InternalThingML.g:5131:6: (lv_isArray_4_0= '[' )
                            // InternalThingML.g:5132:7: lv_isArray_4_0= '['
                            {
                            lv_isArray_4_0=(Token)match(input,17,FOLLOW_10); 

                            							newLeafNode(lv_isArray_4_0, grammarAccess.getCastExpressionAccess().getIsArrayLeftSquareBracketKeyword_1_3_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getCastExpressionRule());
                            							}
                            							setWithLastConsumed(current, "isArray", true, "[");
                            						

                            }


                            }

                            otherlv_5=(Token)match(input,18,FOLLOW_2); 

                            					newLeafNode(otherlv_5, grammarAccess.getCastExpressionAccess().getRightSquareBracketKeyword_1_3_1());
                            				

                            }
                            break;

                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCastExpression"


    // $ANTLR start "entryRuleOrExpression"
    // InternalThingML.g:5154:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // InternalThingML.g:5154:53: (iv_ruleOrExpression= ruleOrExpression EOF )
            // InternalThingML.g:5155:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             newCompositeNode(grammarAccess.getOrExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOrExpression=ruleOrExpression();

            state._fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOrExpression"


    // $ANTLR start "ruleOrExpression"
    // InternalThingML.g:5161:1: ruleOrExpression returns [EObject current=null] : (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_AndExpression_0 = null;

        EObject lv_rhs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5167:2: ( (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* ) )
            // InternalThingML.g:5168:2: (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* )
            {
            // InternalThingML.g:5168:2: (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* )
            // InternalThingML.g:5169:3: this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getOrExpressionAccess().getAndExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_76);
            this_AndExpression_0=ruleAndExpression();

            state._fsp--;


            			current = this_AndExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5177:3: ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )*
            loop113:
            do {
                int alt113=2;
                int LA113_0 = input.LA(1);

                if ( (LA113_0==80) ) {
                    alt113=1;
                }


                switch (alt113) {
            	case 1 :
            	    // InternalThingML.g:5178:4: () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) )
            	    {
            	    // InternalThingML.g:5178:4: ()
            	    // InternalThingML.g:5179:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getOrExpressionAccess().getOrExpressionLhsAction_1_0(),
            	    						current);
            	    				

            	    }

            	    otherlv_2=(Token)match(input,80,FOLLOW_23); 

            	    				newLeafNode(otherlv_2, grammarAccess.getOrExpressionAccess().getOrKeyword_1_1());
            	    			
            	    // InternalThingML.g:5189:4: ( (lv_rhs_3_0= ruleAndExpression ) )
            	    // InternalThingML.g:5190:5: (lv_rhs_3_0= ruleAndExpression )
            	    {
            	    // InternalThingML.g:5190:5: (lv_rhs_3_0= ruleAndExpression )
            	    // InternalThingML.g:5191:6: lv_rhs_3_0= ruleAndExpression
            	    {

            	    						newCompositeNode(grammarAccess.getOrExpressionAccess().getRhsAndExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_76);
            	    lv_rhs_3_0=ruleAndExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getOrExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"rhs",
            	    							lv_rhs_3_0,
            	    							"org.thingml.xtext.ThingML.AndExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop113;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrExpression"


    // $ANTLR start "entryRuleAndExpression"
    // InternalThingML.g:5213:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // InternalThingML.g:5213:54: (iv_ruleAndExpression= ruleAndExpression EOF )
            // InternalThingML.g:5214:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             newCompositeNode(grammarAccess.getAndExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAndExpression=ruleAndExpression();

            state._fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAndExpression"


    // $ANTLR start "ruleAndExpression"
    // InternalThingML.g:5220:1: ruleAndExpression returns [EObject current=null] : (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Equality_0 = null;

        EObject lv_rhs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5226:2: ( (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* ) )
            // InternalThingML.g:5227:2: (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* )
            {
            // InternalThingML.g:5227:2: (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* )
            // InternalThingML.g:5228:3: this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )*
            {

            			newCompositeNode(grammarAccess.getAndExpressionAccess().getEqualityParserRuleCall_0());
            		
            pushFollow(FOLLOW_77);
            this_Equality_0=ruleEquality();

            state._fsp--;


            			current = this_Equality_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5236:3: ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )*
            loop114:
            do {
                int alt114=2;
                int LA114_0 = input.LA(1);

                if ( (LA114_0==81) ) {
                    alt114=1;
                }


                switch (alt114) {
            	case 1 :
            	    // InternalThingML.g:5237:4: () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) )
            	    {
            	    // InternalThingML.g:5237:4: ()
            	    // InternalThingML.g:5238:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAndExpressionAccess().getAndExpressionLhsAction_1_0(),
            	    						current);
            	    				

            	    }

            	    otherlv_2=(Token)match(input,81,FOLLOW_23); 

            	    				newLeafNode(otherlv_2, grammarAccess.getAndExpressionAccess().getAndKeyword_1_1());
            	    			
            	    // InternalThingML.g:5248:4: ( (lv_rhs_3_0= ruleEquality ) )
            	    // InternalThingML.g:5249:5: (lv_rhs_3_0= ruleEquality )
            	    {
            	    // InternalThingML.g:5249:5: (lv_rhs_3_0= ruleEquality )
            	    // InternalThingML.g:5250:6: lv_rhs_3_0= ruleEquality
            	    {

            	    						newCompositeNode(grammarAccess.getAndExpressionAccess().getRhsEqualityParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_77);
            	    lv_rhs_3_0=ruleEquality();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAndExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"rhs",
            	    							lv_rhs_3_0,
            	    							"org.thingml.xtext.ThingML.Equality");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop114;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAndExpression"


    // $ANTLR start "entryRuleEquality"
    // InternalThingML.g:5272:1: entryRuleEquality returns [EObject current=null] : iv_ruleEquality= ruleEquality EOF ;
    public final EObject entryRuleEquality() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEquality = null;


        try {
            // InternalThingML.g:5272:49: (iv_ruleEquality= ruleEquality EOF )
            // InternalThingML.g:5273:2: iv_ruleEquality= ruleEquality EOF
            {
             newCompositeNode(grammarAccess.getEqualityRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEquality=ruleEquality();

            state._fsp--;

             current =iv_ruleEquality; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEquality"


    // $ANTLR start "ruleEquality"
    // InternalThingML.g:5279:1: ruleEquality returns [EObject current=null] : (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* ) ;
    public final EObject ruleEquality() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Comparaison_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5285:2: ( (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* ) )
            // InternalThingML.g:5286:2: (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* )
            {
            // InternalThingML.g:5286:2: (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* )
            // InternalThingML.g:5287:3: this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getEqualityAccess().getComparaisonParserRuleCall_0());
            		
            pushFollow(FOLLOW_78);
            this_Comparaison_0=ruleComparaison();

            state._fsp--;


            			current = this_Comparaison_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5295:3: ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )*
            loop115:
            do {
                int alt115=3;
                int LA115_0 = input.LA(1);

                if ( (LA115_0==82) ) {
                    alt115=1;
                }
                else if ( (LA115_0==83) ) {
                    alt115=2;
                }


                switch (alt115) {
            	case 1 :
            	    // InternalThingML.g:5296:4: ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) )
            	    {
            	    // InternalThingML.g:5296:4: ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) )
            	    // InternalThingML.g:5297:5: () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) )
            	    {
            	    // InternalThingML.g:5297:5: ()
            	    // InternalThingML.g:5298:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getEqualityAccess().getEqualsExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,82,FOLLOW_23); 

            	    					newLeafNode(otherlv_2, grammarAccess.getEqualityAccess().getEqualsSignEqualsSignKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:5308:5: ( (lv_rhs_3_0= ruleComparaison ) )
            	    // InternalThingML.g:5309:6: (lv_rhs_3_0= ruleComparaison )
            	    {
            	    // InternalThingML.g:5309:6: (lv_rhs_3_0= ruleComparaison )
            	    // InternalThingML.g:5310:7: lv_rhs_3_0= ruleComparaison
            	    {

            	    							newCompositeNode(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_78);
            	    lv_rhs_3_0=ruleComparaison();

            	    state._fsp--;


            	    							if (current==null) {
            	    								current = createModelElementForParent(grammarAccess.getEqualityRule());
            	    							}
            	    							set(
            	    								current,
            	    								"rhs",
            	    								lv_rhs_3_0,
            	    								"org.thingml.xtext.ThingML.Comparaison");
            	    							afterParserOrEnumRuleCall();
            	    						

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:5329:4: ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) )
            	    {
            	    // InternalThingML.g:5329:4: ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) )
            	    // InternalThingML.g:5330:5: () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) )
            	    {
            	    // InternalThingML.g:5330:5: ()
            	    // InternalThingML.g:5331:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getEqualityAccess().getNotEqualsExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,83,FOLLOW_23); 

            	    					newLeafNode(otherlv_5, grammarAccess.getEqualityAccess().getExclamationMarkEqualsSignKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:5341:5: ( (lv_rhs_6_0= ruleComparaison ) )
            	    // InternalThingML.g:5342:6: (lv_rhs_6_0= ruleComparaison )
            	    {
            	    // InternalThingML.g:5342:6: (lv_rhs_6_0= ruleComparaison )
            	    // InternalThingML.g:5343:7: lv_rhs_6_0= ruleComparaison
            	    {

            	    							newCompositeNode(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_78);
            	    lv_rhs_6_0=ruleComparaison();

            	    state._fsp--;


            	    							if (current==null) {
            	    								current = createModelElementForParent(grammarAccess.getEqualityRule());
            	    							}
            	    							set(
            	    								current,
            	    								"rhs",
            	    								lv_rhs_6_0,
            	    								"org.thingml.xtext.ThingML.Comparaison");
            	    							afterParserOrEnumRuleCall();
            	    						

            	    }


            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop115;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEquality"


    // $ANTLR start "entryRuleComparaison"
    // InternalThingML.g:5366:1: entryRuleComparaison returns [EObject current=null] : iv_ruleComparaison= ruleComparaison EOF ;
    public final EObject entryRuleComparaison() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComparaison = null;


        try {
            // InternalThingML.g:5366:52: (iv_ruleComparaison= ruleComparaison EOF )
            // InternalThingML.g:5367:2: iv_ruleComparaison= ruleComparaison EOF
            {
             newCompositeNode(grammarAccess.getComparaisonRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleComparaison=ruleComparaison();

            state._fsp--;

             current =iv_ruleComparaison; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleComparaison"


    // $ANTLR start "ruleComparaison"
    // InternalThingML.g:5373:1: ruleComparaison returns [EObject current=null] : (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* ) ;
    public final EObject ruleComparaison() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        Token otherlv_11=null;
        EObject this_Addition_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;

        EObject lv_rhs_9_0 = null;

        EObject lv_rhs_12_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5379:2: ( (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* ) )
            // InternalThingML.g:5380:2: (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* )
            {
            // InternalThingML.g:5380:2: (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* )
            // InternalThingML.g:5381:3: this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getComparaisonAccess().getAdditionParserRuleCall_0());
            		
            pushFollow(FOLLOW_79);
            this_Addition_0=ruleAddition();

            state._fsp--;


            			current = this_Addition_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5389:3: ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )*
            loop116:
            do {
                int alt116=5;
                switch ( input.LA(1) ) {
                case 21:
                    {
                    alt116=1;
                    }
                    break;
                case 20:
                    {
                    alt116=2;
                    }
                    break;
                case 84:
                    {
                    alt116=3;
                    }
                    break;
                case 85:
                    {
                    alt116=4;
                    }
                    break;

                }

                switch (alt116) {
            	case 1 :
            	    // InternalThingML.g:5390:4: ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:5390:4: ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) )
            	    // InternalThingML.g:5391:5: () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:5391:5: ()
            	    // InternalThingML.g:5392:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getGreaterExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,21,FOLLOW_23); 

            	    					newLeafNode(otherlv_2, grammarAccess.getComparaisonAccess().getGreaterThanSignKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:5402:5: ( (lv_rhs_3_0= ruleAddition ) )
            	    // InternalThingML.g:5403:6: (lv_rhs_3_0= ruleAddition )
            	    {
            	    // InternalThingML.g:5403:6: (lv_rhs_3_0= ruleAddition )
            	    // InternalThingML.g:5404:7: lv_rhs_3_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_79);
            	    lv_rhs_3_0=ruleAddition();

            	    state._fsp--;


            	    							if (current==null) {
            	    								current = createModelElementForParent(grammarAccess.getComparaisonRule());
            	    							}
            	    							set(
            	    								current,
            	    								"rhs",
            	    								lv_rhs_3_0,
            	    								"org.thingml.xtext.ThingML.Addition");
            	    							afterParserOrEnumRuleCall();
            	    						

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:5423:4: ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:5423:4: ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) )
            	    // InternalThingML.g:5424:5: () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:5424:5: ()
            	    // InternalThingML.g:5425:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getLowerExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,20,FOLLOW_23); 

            	    					newLeafNode(otherlv_5, grammarAccess.getComparaisonAccess().getLessThanSignKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:5435:5: ( (lv_rhs_6_0= ruleAddition ) )
            	    // InternalThingML.g:5436:6: (lv_rhs_6_0= ruleAddition )
            	    {
            	    // InternalThingML.g:5436:6: (lv_rhs_6_0= ruleAddition )
            	    // InternalThingML.g:5437:7: lv_rhs_6_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_79);
            	    lv_rhs_6_0=ruleAddition();

            	    state._fsp--;


            	    							if (current==null) {
            	    								current = createModelElementForParent(grammarAccess.getComparaisonRule());
            	    							}
            	    							set(
            	    								current,
            	    								"rhs",
            	    								lv_rhs_6_0,
            	    								"org.thingml.xtext.ThingML.Addition");
            	    							afterParserOrEnumRuleCall();
            	    						

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalThingML.g:5456:4: ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:5456:4: ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) )
            	    // InternalThingML.g:5457:5: () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:5457:5: ()
            	    // InternalThingML.g:5458:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getGreaterOrEqualExpressionLhsAction_1_2_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_8=(Token)match(input,84,FOLLOW_23); 

            	    					newLeafNode(otherlv_8, grammarAccess.getComparaisonAccess().getGreaterThanSignEqualsSignKeyword_1_2_1());
            	    				
            	    // InternalThingML.g:5468:5: ( (lv_rhs_9_0= ruleAddition ) )
            	    // InternalThingML.g:5469:6: (lv_rhs_9_0= ruleAddition )
            	    {
            	    // InternalThingML.g:5469:6: (lv_rhs_9_0= ruleAddition )
            	    // InternalThingML.g:5470:7: lv_rhs_9_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_2_2_0());
            	    						
            	    pushFollow(FOLLOW_79);
            	    lv_rhs_9_0=ruleAddition();

            	    state._fsp--;


            	    							if (current==null) {
            	    								current = createModelElementForParent(grammarAccess.getComparaisonRule());
            	    							}
            	    							set(
            	    								current,
            	    								"rhs",
            	    								lv_rhs_9_0,
            	    								"org.thingml.xtext.ThingML.Addition");
            	    							afterParserOrEnumRuleCall();
            	    						

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalThingML.g:5489:4: ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:5489:4: ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) )
            	    // InternalThingML.g:5490:5: () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:5490:5: ()
            	    // InternalThingML.g:5491:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getLowerOrEqualExpressionLhsAction_1_3_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_11=(Token)match(input,85,FOLLOW_23); 

            	    					newLeafNode(otherlv_11, grammarAccess.getComparaisonAccess().getLessThanSignEqualsSignKeyword_1_3_1());
            	    				
            	    // InternalThingML.g:5501:5: ( (lv_rhs_12_0= ruleAddition ) )
            	    // InternalThingML.g:5502:6: (lv_rhs_12_0= ruleAddition )
            	    {
            	    // InternalThingML.g:5502:6: (lv_rhs_12_0= ruleAddition )
            	    // InternalThingML.g:5503:7: lv_rhs_12_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_3_2_0());
            	    						
            	    pushFollow(FOLLOW_79);
            	    lv_rhs_12_0=ruleAddition();

            	    state._fsp--;


            	    							if (current==null) {
            	    								current = createModelElementForParent(grammarAccess.getComparaisonRule());
            	    							}
            	    							set(
            	    								current,
            	    								"rhs",
            	    								lv_rhs_12_0,
            	    								"org.thingml.xtext.ThingML.Addition");
            	    							afterParserOrEnumRuleCall();
            	    						

            	    }


            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop116;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleComparaison"


    // $ANTLR start "entryRuleAddition"
    // InternalThingML.g:5526:1: entryRuleAddition returns [EObject current=null] : iv_ruleAddition= ruleAddition EOF ;
    public final EObject entryRuleAddition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAddition = null;


        try {
            // InternalThingML.g:5526:49: (iv_ruleAddition= ruleAddition EOF )
            // InternalThingML.g:5527:2: iv_ruleAddition= ruleAddition EOF
            {
             newCompositeNode(grammarAccess.getAdditionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAddition=ruleAddition();

            state._fsp--;

             current =iv_ruleAddition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAddition"


    // $ANTLR start "ruleAddition"
    // InternalThingML.g:5533:1: ruleAddition returns [EObject current=null] : (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* ) ;
    public final EObject ruleAddition() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Multiplication_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5539:2: ( (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* ) )
            // InternalThingML.g:5540:2: (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* )
            {
            // InternalThingML.g:5540:2: (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* )
            // InternalThingML.g:5541:3: this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0());
            		
            pushFollow(FOLLOW_80);
            this_Multiplication_0=ruleMultiplication();

            state._fsp--;


            			current = this_Multiplication_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5549:3: ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )*
            loop117:
            do {
                int alt117=3;
                int LA117_0 = input.LA(1);

                if ( (LA117_0==86) ) {
                    alt117=1;
                }
                else if ( (LA117_0==87) ) {
                    alt117=2;
                }


                switch (alt117) {
            	case 1 :
            	    // InternalThingML.g:5550:4: ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) )
            	    {
            	    // InternalThingML.g:5550:4: ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) )
            	    // InternalThingML.g:5551:5: () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) )
            	    {
            	    // InternalThingML.g:5551:5: ()
            	    // InternalThingML.g:5552:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getAdditionAccess().getPlusExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,86,FOLLOW_23); 

            	    					newLeafNode(otherlv_2, grammarAccess.getAdditionAccess().getPlusSignKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:5562:5: ( (lv_rhs_3_0= ruleMultiplication ) )
            	    // InternalThingML.g:5563:6: (lv_rhs_3_0= ruleMultiplication )
            	    {
            	    // InternalThingML.g:5563:6: (lv_rhs_3_0= ruleMultiplication )
            	    // InternalThingML.g:5564:7: lv_rhs_3_0= ruleMultiplication
            	    {

            	    							newCompositeNode(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_80);
            	    lv_rhs_3_0=ruleMultiplication();

            	    state._fsp--;


            	    							if (current==null) {
            	    								current = createModelElementForParent(grammarAccess.getAdditionRule());
            	    							}
            	    							set(
            	    								current,
            	    								"rhs",
            	    								lv_rhs_3_0,
            	    								"org.thingml.xtext.ThingML.Multiplication");
            	    							afterParserOrEnumRuleCall();
            	    						

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:5583:4: ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) )
            	    {
            	    // InternalThingML.g:5583:4: ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) )
            	    // InternalThingML.g:5584:5: () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) )
            	    {
            	    // InternalThingML.g:5584:5: ()
            	    // InternalThingML.g:5585:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getAdditionAccess().getMinusExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,87,FOLLOW_23); 

            	    					newLeafNode(otherlv_5, grammarAccess.getAdditionAccess().getHyphenMinusKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:5595:5: ( (lv_rhs_6_0= ruleMultiplication ) )
            	    // InternalThingML.g:5596:6: (lv_rhs_6_0= ruleMultiplication )
            	    {
            	    // InternalThingML.g:5596:6: (lv_rhs_6_0= ruleMultiplication )
            	    // InternalThingML.g:5597:7: lv_rhs_6_0= ruleMultiplication
            	    {

            	    							newCompositeNode(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_80);
            	    lv_rhs_6_0=ruleMultiplication();

            	    state._fsp--;


            	    							if (current==null) {
            	    								current = createModelElementForParent(grammarAccess.getAdditionRule());
            	    							}
            	    							set(
            	    								current,
            	    								"rhs",
            	    								lv_rhs_6_0,
            	    								"org.thingml.xtext.ThingML.Multiplication");
            	    							afterParserOrEnumRuleCall();
            	    						

            	    }


            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop117;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAddition"


    // $ANTLR start "entryRuleMultiplication"
    // InternalThingML.g:5620:1: entryRuleMultiplication returns [EObject current=null] : iv_ruleMultiplication= ruleMultiplication EOF ;
    public final EObject entryRuleMultiplication() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplication = null;


        try {
            // InternalThingML.g:5620:55: (iv_ruleMultiplication= ruleMultiplication EOF )
            // InternalThingML.g:5621:2: iv_ruleMultiplication= ruleMultiplication EOF
            {
             newCompositeNode(grammarAccess.getMultiplicationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMultiplication=ruleMultiplication();

            state._fsp--;

             current =iv_ruleMultiplication; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMultiplication"


    // $ANTLR start "ruleMultiplication"
    // InternalThingML.g:5627:1: ruleMultiplication returns [EObject current=null] : (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* ) ;
    public final EObject ruleMultiplication() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Modulo_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5633:2: ( (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* ) )
            // InternalThingML.g:5634:2: (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* )
            {
            // InternalThingML.g:5634:2: (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* )
            // InternalThingML.g:5635:3: this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getMultiplicationAccess().getModuloParserRuleCall_0());
            		
            pushFollow(FOLLOW_81);
            this_Modulo_0=ruleModulo();

            state._fsp--;


            			current = this_Modulo_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5643:3: ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )*
            loop118:
            do {
                int alt118=3;
                int LA118_0 = input.LA(1);

                if ( (LA118_0==88) ) {
                    alt118=1;
                }
                else if ( (LA118_0==89) ) {
                    alt118=2;
                }


                switch (alt118) {
            	case 1 :
            	    // InternalThingML.g:5644:4: ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) )
            	    {
            	    // InternalThingML.g:5644:4: ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) )
            	    // InternalThingML.g:5645:5: () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) )
            	    {
            	    // InternalThingML.g:5645:5: ()
            	    // InternalThingML.g:5646:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getMultiplicationAccess().getTimesExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,88,FOLLOW_23); 

            	    					newLeafNode(otherlv_2, grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:5656:5: ( (lv_rhs_3_0= ruleModulo ) )
            	    // InternalThingML.g:5657:6: (lv_rhs_3_0= ruleModulo )
            	    {
            	    // InternalThingML.g:5657:6: (lv_rhs_3_0= ruleModulo )
            	    // InternalThingML.g:5658:7: lv_rhs_3_0= ruleModulo
            	    {

            	    							newCompositeNode(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_81);
            	    lv_rhs_3_0=ruleModulo();

            	    state._fsp--;


            	    							if (current==null) {
            	    								current = createModelElementForParent(grammarAccess.getMultiplicationRule());
            	    							}
            	    							set(
            	    								current,
            	    								"rhs",
            	    								lv_rhs_3_0,
            	    								"org.thingml.xtext.ThingML.Modulo");
            	    							afterParserOrEnumRuleCall();
            	    						

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:5677:4: ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) )
            	    {
            	    // InternalThingML.g:5677:4: ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) )
            	    // InternalThingML.g:5678:5: () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) )
            	    {
            	    // InternalThingML.g:5678:5: ()
            	    // InternalThingML.g:5679:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getMultiplicationAccess().getDivExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,89,FOLLOW_23); 

            	    					newLeafNode(otherlv_5, grammarAccess.getMultiplicationAccess().getSolidusKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:5689:5: ( (lv_rhs_6_0= ruleModulo ) )
            	    // InternalThingML.g:5690:6: (lv_rhs_6_0= ruleModulo )
            	    {
            	    // InternalThingML.g:5690:6: (lv_rhs_6_0= ruleModulo )
            	    // InternalThingML.g:5691:7: lv_rhs_6_0= ruleModulo
            	    {

            	    							newCompositeNode(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_81);
            	    lv_rhs_6_0=ruleModulo();

            	    state._fsp--;


            	    							if (current==null) {
            	    								current = createModelElementForParent(grammarAccess.getMultiplicationRule());
            	    							}
            	    							set(
            	    								current,
            	    								"rhs",
            	    								lv_rhs_6_0,
            	    								"org.thingml.xtext.ThingML.Modulo");
            	    							afterParserOrEnumRuleCall();
            	    						

            	    }


            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop118;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMultiplication"


    // $ANTLR start "entryRuleModulo"
    // InternalThingML.g:5714:1: entryRuleModulo returns [EObject current=null] : iv_ruleModulo= ruleModulo EOF ;
    public final EObject entryRuleModulo() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModulo = null;


        try {
            // InternalThingML.g:5714:47: (iv_ruleModulo= ruleModulo EOF )
            // InternalThingML.g:5715:2: iv_ruleModulo= ruleModulo EOF
            {
             newCompositeNode(grammarAccess.getModuloRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleModulo=ruleModulo();

            state._fsp--;

             current =iv_ruleModulo; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModulo"


    // $ANTLR start "ruleModulo"
    // InternalThingML.g:5721:1: ruleModulo returns [EObject current=null] : (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? ) ;
    public final EObject ruleModulo() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Primary_0 = null;

        EObject lv_rhs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5727:2: ( (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? ) )
            // InternalThingML.g:5728:2: (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? )
            {
            // InternalThingML.g:5728:2: (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? )
            // InternalThingML.g:5729:3: this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )?
            {

            			newCompositeNode(grammarAccess.getModuloAccess().getPrimaryParserRuleCall_0());
            		
            pushFollow(FOLLOW_82);
            this_Primary_0=rulePrimary();

            state._fsp--;


            			current = this_Primary_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5737:3: ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )?
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==90) ) {
                alt119=1;
            }
            switch (alt119) {
                case 1 :
                    // InternalThingML.g:5738:4: () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) )
                    {
                    // InternalThingML.g:5738:4: ()
                    // InternalThingML.g:5739:5: 
                    {

                    					current = forceCreateModelElementAndSet(
                    						grammarAccess.getModuloAccess().getModExpressionLhsAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_2=(Token)match(input,90,FOLLOW_23); 

                    				newLeafNode(otherlv_2, grammarAccess.getModuloAccess().getPercentSignKeyword_1_1());
                    			
                    // InternalThingML.g:5749:4: ( (lv_rhs_3_0= ruleExpression ) )
                    // InternalThingML.g:5750:5: (lv_rhs_3_0= ruleExpression )
                    {
                    // InternalThingML.g:5750:5: (lv_rhs_3_0= ruleExpression )
                    // InternalThingML.g:5751:6: lv_rhs_3_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getModuloAccess().getRhsExpressionParserRuleCall_1_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_rhs_3_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getModuloRule());
                    						}
                    						set(
                    							current,
                    							"rhs",
                    							lv_rhs_3_0,
                    							"org.thingml.xtext.ThingML.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModulo"


    // $ANTLR start "entryRulePrimary"
    // InternalThingML.g:5773:1: entryRulePrimary returns [EObject current=null] : iv_rulePrimary= rulePrimary EOF ;
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary = null;


        try {
            // InternalThingML.g:5773:48: (iv_rulePrimary= rulePrimary EOF )
            // InternalThingML.g:5774:2: iv_rulePrimary= rulePrimary EOF
            {
             newCompositeNode(grammarAccess.getPrimaryRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePrimary=rulePrimary();

            state._fsp--;

             current =iv_rulePrimary; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimary"


    // $ANTLR start "rulePrimary"
    // InternalThingML.g:5780:1: rulePrimary returns [EObject current=null] : ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_ArrayIndexPostfix_9= ruleArrayIndexPostfix ) ;
    public final EObject rulePrimary() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_7=null;
        EObject this_Expression_1 = null;

        EObject lv_term_5_0 = null;

        EObject lv_term_8_0 = null;

        EObject this_ArrayIndexPostfix_9 = null;



        	enterRule();

        try {
            // InternalThingML.g:5786:2: ( ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_ArrayIndexPostfix_9= ruleArrayIndexPostfix ) )
            // InternalThingML.g:5787:2: ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_ArrayIndexPostfix_9= ruleArrayIndexPostfix )
            {
            // InternalThingML.g:5787:2: ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_ArrayIndexPostfix_9= ruleArrayIndexPostfix )
            int alt120=4;
            switch ( input.LA(1) ) {
            case 35:
                {
                alt120=1;
                }
                break;
            case 91:
                {
                alt120=2;
                }
                break;
            case 87:
                {
                alt120=3;
                }
                break;
            case RULE_STRING_LIT:
            case RULE_ID:
            case RULE_INT:
            case RULE_STRING_EXT:
            case RULE_FLOAT:
            case 92:
            case 93:
                {
                alt120=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 120, 0, input);

                throw nvae;
            }

            switch (alt120) {
                case 1 :
                    // InternalThingML.g:5788:3: (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' )
                    {
                    // InternalThingML.g:5788:3: (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' )
                    // InternalThingML.g:5789:4: otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')'
                    {
                    otherlv_0=(Token)match(input,35,FOLLOW_23); 

                    				newLeafNode(otherlv_0, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_0_0());
                    			

                    				newCompositeNode(grammarAccess.getPrimaryAccess().getExpressionParserRuleCall_0_1());
                    			
                    pushFollow(FOLLOW_73);
                    this_Expression_1=ruleExpression();

                    state._fsp--;


                    				current = this_Expression_1;
                    				afterParserOrEnumRuleCall();
                    			
                    otherlv_2=(Token)match(input,36,FOLLOW_2); 

                    				newLeafNode(otherlv_2, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_0_2());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalThingML.g:5807:3: ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) )
                    {
                    // InternalThingML.g:5807:3: ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) )
                    // InternalThingML.g:5808:4: () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) )
                    {
                    // InternalThingML.g:5808:4: ()
                    // InternalThingML.g:5809:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPrimaryAccess().getNotExpressionAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_4=(Token)match(input,91,FOLLOW_23); 

                    				newLeafNode(otherlv_4, grammarAccess.getPrimaryAccess().getNotKeyword_1_1());
                    			
                    // InternalThingML.g:5819:4: ( (lv_term_5_0= rulePrimary ) )
                    // InternalThingML.g:5820:5: (lv_term_5_0= rulePrimary )
                    {
                    // InternalThingML.g:5820:5: (lv_term_5_0= rulePrimary )
                    // InternalThingML.g:5821:6: lv_term_5_0= rulePrimary
                    {

                    						newCompositeNode(grammarAccess.getPrimaryAccess().getTermPrimaryParserRuleCall_1_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_term_5_0=rulePrimary();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPrimaryRule());
                    						}
                    						set(
                    							current,
                    							"term",
                    							lv_term_5_0,
                    							"org.thingml.xtext.ThingML.Primary");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalThingML.g:5840:3: ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) )
                    {
                    // InternalThingML.g:5840:3: ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) )
                    // InternalThingML.g:5841:4: () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) )
                    {
                    // InternalThingML.g:5841:4: ()
                    // InternalThingML.g:5842:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPrimaryAccess().getUnaryMinusAction_2_0(),
                    						current);
                    				

                    }

                    otherlv_7=(Token)match(input,87,FOLLOW_23); 

                    				newLeafNode(otherlv_7, grammarAccess.getPrimaryAccess().getHyphenMinusKeyword_2_1());
                    			
                    // InternalThingML.g:5852:4: ( (lv_term_8_0= rulePrimary ) )
                    // InternalThingML.g:5853:5: (lv_term_8_0= rulePrimary )
                    {
                    // InternalThingML.g:5853:5: (lv_term_8_0= rulePrimary )
                    // InternalThingML.g:5854:6: lv_term_8_0= rulePrimary
                    {

                    						newCompositeNode(grammarAccess.getPrimaryAccess().getTermPrimaryParserRuleCall_2_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_term_8_0=rulePrimary();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPrimaryRule());
                    						}
                    						set(
                    							current,
                    							"term",
                    							lv_term_8_0,
                    							"org.thingml.xtext.ThingML.Primary");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalThingML.g:5873:3: this_ArrayIndexPostfix_9= ruleArrayIndexPostfix
                    {

                    			newCompositeNode(grammarAccess.getPrimaryAccess().getArrayIndexPostfixParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_ArrayIndexPostfix_9=ruleArrayIndexPostfix();

                    state._fsp--;


                    			current = this_ArrayIndexPostfix_9;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimary"


    // $ANTLR start "entryRuleArrayIndexPostfix"
    // InternalThingML.g:5885:1: entryRuleArrayIndexPostfix returns [EObject current=null] : iv_ruleArrayIndexPostfix= ruleArrayIndexPostfix EOF ;
    public final EObject entryRuleArrayIndexPostfix() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayIndexPostfix = null;


        try {
            // InternalThingML.g:5885:58: (iv_ruleArrayIndexPostfix= ruleArrayIndexPostfix EOF )
            // InternalThingML.g:5886:2: iv_ruleArrayIndexPostfix= ruleArrayIndexPostfix EOF
            {
             newCompositeNode(grammarAccess.getArrayIndexPostfixRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleArrayIndexPostfix=ruleArrayIndexPostfix();

            state._fsp--;

             current =iv_ruleArrayIndexPostfix; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArrayIndexPostfix"


    // $ANTLR start "ruleArrayIndexPostfix"
    // InternalThingML.g:5892:1: ruleArrayIndexPostfix returns [EObject current=null] : (this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )? ) ;
    public final EObject ruleArrayIndexPostfix() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_AtomicExpression_0 = null;

        EObject lv_index_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5898:2: ( (this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )? ) )
            // InternalThingML.g:5899:2: (this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )? )
            {
            // InternalThingML.g:5899:2: (this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )? )
            // InternalThingML.g:5900:3: this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )?
            {

            			newCompositeNode(grammarAccess.getArrayIndexPostfixAccess().getAtomicExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_8);
            this_AtomicExpression_0=ruleAtomicExpression();

            state._fsp--;


            			current = this_AtomicExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5908:3: ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )?
            int alt121=2;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==17) ) {
                alt121=1;
            }
            switch (alt121) {
                case 1 :
                    // InternalThingML.g:5909:4: () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']'
                    {
                    // InternalThingML.g:5909:4: ()
                    // InternalThingML.g:5910:5: 
                    {

                    					current = forceCreateModelElementAndSet(
                    						grammarAccess.getArrayIndexPostfixAccess().getArrayIndexArrayAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_2=(Token)match(input,17,FOLLOW_23); 

                    				newLeafNode(otherlv_2, grammarAccess.getArrayIndexPostfixAccess().getLeftSquareBracketKeyword_1_1());
                    			
                    // InternalThingML.g:5920:4: ( (lv_index_3_0= ruleExpression ) )
                    // InternalThingML.g:5921:5: (lv_index_3_0= ruleExpression )
                    {
                    // InternalThingML.g:5921:5: (lv_index_3_0= ruleExpression )
                    // InternalThingML.g:5922:6: lv_index_3_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getArrayIndexPostfixAccess().getIndexExpressionParserRuleCall_1_2_0());
                    					
                    pushFollow(FOLLOW_10);
                    lv_index_3_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getArrayIndexPostfixRule());
                    						}
                    						set(
                    							current,
                    							"index",
                    							lv_index_3_0,
                    							"org.thingml.xtext.ThingML.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_4=(Token)match(input,18,FOLLOW_2); 

                    				newLeafNode(otherlv_4, grammarAccess.getArrayIndexPostfixAccess().getRightSquareBracketKeyword_1_3());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArrayIndexPostfix"


    // $ANTLR start "entryRuleAtomicExpression"
    // InternalThingML.g:5948:1: entryRuleAtomicExpression returns [EObject current=null] : iv_ruleAtomicExpression= ruleAtomicExpression EOF ;
    public final EObject entryRuleAtomicExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtomicExpression = null;


        try {
            // InternalThingML.g:5948:57: (iv_ruleAtomicExpression= ruleAtomicExpression EOF )
            // InternalThingML.g:5949:2: iv_ruleAtomicExpression= ruleAtomicExpression EOF
            {
             newCompositeNode(grammarAccess.getAtomicExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAtomicExpression=ruleAtomicExpression();

            state._fsp--;

             current =iv_ruleAtomicExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAtomicExpression"


    // $ANTLR start "ruleAtomicExpression"
    // InternalThingML.g:5955:1: ruleAtomicExpression returns [EObject current=null] : (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_FunctionCallExpression_7= ruleFunctionCallExpression | this_EventReference_8= ruleEventReference ) ;
    public final EObject ruleAtomicExpression() throws RecognitionException {
        EObject current = null;

        EObject this_ExternExpression_0 = null;

        EObject this_EnumLiteralRef_1 = null;

        EObject this_IntegerLiteral_2 = null;

        EObject this_BooleanLiteral_3 = null;

        EObject this_StringLiteral_4 = null;

        EObject this_DoubleLiteral_5 = null;

        EObject this_PropertyReference_6 = null;

        EObject this_FunctionCallExpression_7 = null;

        EObject this_EventReference_8 = null;



        	enterRule();

        try {
            // InternalThingML.g:5961:2: ( (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_FunctionCallExpression_7= ruleFunctionCallExpression | this_EventReference_8= ruleEventReference ) )
            // InternalThingML.g:5962:2: (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_FunctionCallExpression_7= ruleFunctionCallExpression | this_EventReference_8= ruleEventReference )
            {
            // InternalThingML.g:5962:2: (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_FunctionCallExpression_7= ruleFunctionCallExpression | this_EventReference_8= ruleEventReference )
            int alt122=9;
            alt122 = dfa122.predict(input);
            switch (alt122) {
                case 1 :
                    // InternalThingML.g:5963:3: this_ExternExpression_0= ruleExternExpression
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getExternExpressionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExternExpression_0=ruleExternExpression();

                    state._fsp--;


                    			current = this_ExternExpression_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalThingML.g:5972:3: this_EnumLiteralRef_1= ruleEnumLiteralRef
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getEnumLiteralRefParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_EnumLiteralRef_1=ruleEnumLiteralRef();

                    state._fsp--;


                    			current = this_EnumLiteralRef_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalThingML.g:5981:3: this_IntegerLiteral_2= ruleIntegerLiteral
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getIntegerLiteralParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_IntegerLiteral_2=ruleIntegerLiteral();

                    state._fsp--;


                    			current = this_IntegerLiteral_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalThingML.g:5990:3: this_BooleanLiteral_3= ruleBooleanLiteral
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getBooleanLiteralParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_BooleanLiteral_3=ruleBooleanLiteral();

                    state._fsp--;


                    			current = this_BooleanLiteral_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalThingML.g:5999:3: this_StringLiteral_4= ruleStringLiteral
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getStringLiteralParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_StringLiteral_4=ruleStringLiteral();

                    state._fsp--;


                    			current = this_StringLiteral_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 6 :
                    // InternalThingML.g:6008:3: this_DoubleLiteral_5= ruleDoubleLiteral
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getDoubleLiteralParserRuleCall_5());
                    		
                    pushFollow(FOLLOW_2);
                    this_DoubleLiteral_5=ruleDoubleLiteral();

                    state._fsp--;


                    			current = this_DoubleLiteral_5;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 7 :
                    // InternalThingML.g:6017:3: this_PropertyReference_6= rulePropertyReference
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getPropertyReferenceParserRuleCall_6());
                    		
                    pushFollow(FOLLOW_2);
                    this_PropertyReference_6=rulePropertyReference();

                    state._fsp--;


                    			current = this_PropertyReference_6;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 8 :
                    // InternalThingML.g:6026:3: this_FunctionCallExpression_7= ruleFunctionCallExpression
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getFunctionCallExpressionParserRuleCall_7());
                    		
                    pushFollow(FOLLOW_2);
                    this_FunctionCallExpression_7=ruleFunctionCallExpression();

                    state._fsp--;


                    			current = this_FunctionCallExpression_7;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 9 :
                    // InternalThingML.g:6035:3: this_EventReference_8= ruleEventReference
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getEventReferenceParserRuleCall_8());
                    		
                    pushFollow(FOLLOW_2);
                    this_EventReference_8=ruleEventReference();

                    state._fsp--;


                    			current = this_EventReference_8;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAtomicExpression"


    // $ANTLR start "entryRuleExternExpression"
    // InternalThingML.g:6047:1: entryRuleExternExpression returns [EObject current=null] : iv_ruleExternExpression= ruleExternExpression EOF ;
    public final EObject entryRuleExternExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternExpression = null;


        try {
            // InternalThingML.g:6047:57: (iv_ruleExternExpression= ruleExternExpression EOF )
            // InternalThingML.g:6048:2: iv_ruleExternExpression= ruleExternExpression EOF
            {
             newCompositeNode(grammarAccess.getExternExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExternExpression=ruleExternExpression();

            state._fsp--;

             current =iv_ruleExternExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExternExpression"


    // $ANTLR start "ruleExternExpression"
    // InternalThingML.g:6054:1: ruleExternExpression returns [EObject current=null] : ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) ;
    public final EObject ruleExternExpression() throws RecognitionException {
        EObject current = null;

        Token lv_expression_0_0=null;
        Token otherlv_1=null;
        EObject lv_segments_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6060:2: ( ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) )
            // InternalThingML.g:6061:2: ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            {
            // InternalThingML.g:6061:2: ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            // InternalThingML.g:6062:3: ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            {
            // InternalThingML.g:6062:3: ( (lv_expression_0_0= RULE_STRING_EXT ) )
            // InternalThingML.g:6063:4: (lv_expression_0_0= RULE_STRING_EXT )
            {
            // InternalThingML.g:6063:4: (lv_expression_0_0= RULE_STRING_EXT )
            // InternalThingML.g:6064:5: lv_expression_0_0= RULE_STRING_EXT
            {
            lv_expression_0_0=(Token)match(input,RULE_STRING_EXT,FOLLOW_67); 

            					newLeafNode(lv_expression_0_0, grammarAccess.getExternExpressionAccess().getExpressionSTRING_EXTTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternExpressionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"expression",
            						lv_expression_0_0,
            						"org.thingml.xtext.ThingML.STRING_EXT");
            				

            }


            }

            // InternalThingML.g:6080:3: (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            loop123:
            do {
                int alt123=2;
                int LA123_0 = input.LA(1);

                if ( (LA123_0==68) ) {
                    alt123=1;
                }


                switch (alt123) {
            	case 1 :
            	    // InternalThingML.g:6081:4: otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,68,FOLLOW_23); 

            	    				newLeafNode(otherlv_1, grammarAccess.getExternExpressionAccess().getAmpersandKeyword_1_0());
            	    			
            	    // InternalThingML.g:6085:4: ( (lv_segments_2_0= ruleExpression ) )
            	    // InternalThingML.g:6086:5: (lv_segments_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:6086:5: (lv_segments_2_0= ruleExpression )
            	    // InternalThingML.g:6087:6: lv_segments_2_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getExternExpressionAccess().getSegmentsExpressionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_67);
            	    lv_segments_2_0=ruleExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getExternExpressionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"segments",
            	    							lv_segments_2_0,
            	    							"org.thingml.xtext.ThingML.Expression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop123;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExternExpression"


    // $ANTLR start "entryRuleEnumLiteralRef"
    // InternalThingML.g:6109:1: entryRuleEnumLiteralRef returns [EObject current=null] : iv_ruleEnumLiteralRef= ruleEnumLiteralRef EOF ;
    public final EObject entryRuleEnumLiteralRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumLiteralRef = null;


        try {
            // InternalThingML.g:6109:55: (iv_ruleEnumLiteralRef= ruleEnumLiteralRef EOF )
            // InternalThingML.g:6110:2: iv_ruleEnumLiteralRef= ruleEnumLiteralRef EOF
            {
             newCompositeNode(grammarAccess.getEnumLiteralRefRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEnumLiteralRef=ruleEnumLiteralRef();

            state._fsp--;

             current =iv_ruleEnumLiteralRef; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEnumLiteralRef"


    // $ANTLR start "ruleEnumLiteralRef"
    // InternalThingML.g:6116:1: ruleEnumLiteralRef returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleEnumLiteralRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:6122:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalThingML.g:6123:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalThingML.g:6123:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) )
            // InternalThingML.g:6124:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) )
            {
            // InternalThingML.g:6124:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:6125:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:6125:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:6126:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEnumLiteralRefRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(otherlv_0, grammarAccess.getEnumLiteralRefAccess().getEnumEnumerationCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,16,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getEnumLiteralRefAccess().getColonKeyword_1());
            		
            // InternalThingML.g:6141:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:6142:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:6142:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:6143:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEnumLiteralRefRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_2, grammarAccess.getEnumLiteralRefAccess().getLiteralEnumerationLiteralCrossReference_2_0());
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEnumLiteralRef"


    // $ANTLR start "entryRuleIntegerLiteral"
    // InternalThingML.g:6158:1: entryRuleIntegerLiteral returns [EObject current=null] : iv_ruleIntegerLiteral= ruleIntegerLiteral EOF ;
    public final EObject entryRuleIntegerLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIntegerLiteral = null;


        try {
            // InternalThingML.g:6158:55: (iv_ruleIntegerLiteral= ruleIntegerLiteral EOF )
            // InternalThingML.g:6159:2: iv_ruleIntegerLiteral= ruleIntegerLiteral EOF
            {
             newCompositeNode(grammarAccess.getIntegerLiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIntegerLiteral=ruleIntegerLiteral();

            state._fsp--;

             current =iv_ruleIntegerLiteral; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIntegerLiteral"


    // $ANTLR start "ruleIntegerLiteral"
    // InternalThingML.g:6165:1: ruleIntegerLiteral returns [EObject current=null] : ( (lv_intValue_0_0= RULE_INT ) ) ;
    public final EObject ruleIntegerLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_intValue_0_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6171:2: ( ( (lv_intValue_0_0= RULE_INT ) ) )
            // InternalThingML.g:6172:2: ( (lv_intValue_0_0= RULE_INT ) )
            {
            // InternalThingML.g:6172:2: ( (lv_intValue_0_0= RULE_INT ) )
            // InternalThingML.g:6173:3: (lv_intValue_0_0= RULE_INT )
            {
            // InternalThingML.g:6173:3: (lv_intValue_0_0= RULE_INT )
            // InternalThingML.g:6174:4: lv_intValue_0_0= RULE_INT
            {
            lv_intValue_0_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            				newLeafNode(lv_intValue_0_0, grammarAccess.getIntegerLiteralAccess().getIntValueINTTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getIntegerLiteralRule());
            				}
            				setWithLastConsumed(
            					current,
            					"intValue",
            					lv_intValue_0_0,
            					"org.thingml.xtext.ThingML.INT");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIntegerLiteral"


    // $ANTLR start "entryRuleBooleanLiteral"
    // InternalThingML.g:6193:1: entryRuleBooleanLiteral returns [EObject current=null] : iv_ruleBooleanLiteral= ruleBooleanLiteral EOF ;
    public final EObject entryRuleBooleanLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBooleanLiteral = null;


        try {
            // InternalThingML.g:6193:55: (iv_ruleBooleanLiteral= ruleBooleanLiteral EOF )
            // InternalThingML.g:6194:2: iv_ruleBooleanLiteral= ruleBooleanLiteral EOF
            {
             newCompositeNode(grammarAccess.getBooleanLiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBooleanLiteral=ruleBooleanLiteral();

            state._fsp--;

             current =iv_ruleBooleanLiteral; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBooleanLiteral"


    // $ANTLR start "ruleBooleanLiteral"
    // InternalThingML.g:6200:1: ruleBooleanLiteral returns [EObject current=null] : ( ( (lv_boolValue_0_0= 'true' ) ) | ( () otherlv_2= 'false' ) ) ;
    public final EObject ruleBooleanLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_boolValue_0_0=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:6206:2: ( ( ( (lv_boolValue_0_0= 'true' ) ) | ( () otherlv_2= 'false' ) ) )
            // InternalThingML.g:6207:2: ( ( (lv_boolValue_0_0= 'true' ) ) | ( () otherlv_2= 'false' ) )
            {
            // InternalThingML.g:6207:2: ( ( (lv_boolValue_0_0= 'true' ) ) | ( () otherlv_2= 'false' ) )
            int alt124=2;
            int LA124_0 = input.LA(1);

            if ( (LA124_0==92) ) {
                alt124=1;
            }
            else if ( (LA124_0==93) ) {
                alt124=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 124, 0, input);

                throw nvae;
            }
            switch (alt124) {
                case 1 :
                    // InternalThingML.g:6208:3: ( (lv_boolValue_0_0= 'true' ) )
                    {
                    // InternalThingML.g:6208:3: ( (lv_boolValue_0_0= 'true' ) )
                    // InternalThingML.g:6209:4: (lv_boolValue_0_0= 'true' )
                    {
                    // InternalThingML.g:6209:4: (lv_boolValue_0_0= 'true' )
                    // InternalThingML.g:6210:5: lv_boolValue_0_0= 'true'
                    {
                    lv_boolValue_0_0=(Token)match(input,92,FOLLOW_2); 

                    					newLeafNode(lv_boolValue_0_0, grammarAccess.getBooleanLiteralAccess().getBoolValueTrueKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getBooleanLiteralRule());
                    					}
                    					setWithLastConsumed(current, "boolValue", true, "true");
                    				

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalThingML.g:6223:3: ( () otherlv_2= 'false' )
                    {
                    // InternalThingML.g:6223:3: ( () otherlv_2= 'false' )
                    // InternalThingML.g:6224:4: () otherlv_2= 'false'
                    {
                    // InternalThingML.g:6224:4: ()
                    // InternalThingML.g:6225:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getBooleanLiteralAccess().getBooleanLiteralAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_2=(Token)match(input,93,FOLLOW_2); 

                    				newLeafNode(otherlv_2, grammarAccess.getBooleanLiteralAccess().getFalseKeyword_1_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBooleanLiteral"


    // $ANTLR start "entryRuleStringLiteral"
    // InternalThingML.g:6240:1: entryRuleStringLiteral returns [EObject current=null] : iv_ruleStringLiteral= ruleStringLiteral EOF ;
    public final EObject entryRuleStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStringLiteral = null;


        try {
            // InternalThingML.g:6240:54: (iv_ruleStringLiteral= ruleStringLiteral EOF )
            // InternalThingML.g:6241:2: iv_ruleStringLiteral= ruleStringLiteral EOF
            {
             newCompositeNode(grammarAccess.getStringLiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStringLiteral=ruleStringLiteral();

            state._fsp--;

             current =iv_ruleStringLiteral; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStringLiteral"


    // $ANTLR start "ruleStringLiteral"
    // InternalThingML.g:6247:1: ruleStringLiteral returns [EObject current=null] : ( (lv_stringValue_0_0= RULE_STRING_LIT ) ) ;
    public final EObject ruleStringLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_stringValue_0_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6253:2: ( ( (lv_stringValue_0_0= RULE_STRING_LIT ) ) )
            // InternalThingML.g:6254:2: ( (lv_stringValue_0_0= RULE_STRING_LIT ) )
            {
            // InternalThingML.g:6254:2: ( (lv_stringValue_0_0= RULE_STRING_LIT ) )
            // InternalThingML.g:6255:3: (lv_stringValue_0_0= RULE_STRING_LIT )
            {
            // InternalThingML.g:6255:3: (lv_stringValue_0_0= RULE_STRING_LIT )
            // InternalThingML.g:6256:4: lv_stringValue_0_0= RULE_STRING_LIT
            {
            lv_stringValue_0_0=(Token)match(input,RULE_STRING_LIT,FOLLOW_2); 

            				newLeafNode(lv_stringValue_0_0, grammarAccess.getStringLiteralAccess().getStringValueSTRING_LITTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getStringLiteralRule());
            				}
            				setWithLastConsumed(
            					current,
            					"stringValue",
            					lv_stringValue_0_0,
            					"org.thingml.xtext.ThingML.STRING_LIT");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStringLiteral"


    // $ANTLR start "entryRuleDoubleLiteral"
    // InternalThingML.g:6275:1: entryRuleDoubleLiteral returns [EObject current=null] : iv_ruleDoubleLiteral= ruleDoubleLiteral EOF ;
    public final EObject entryRuleDoubleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoubleLiteral = null;


        try {
            // InternalThingML.g:6275:54: (iv_ruleDoubleLiteral= ruleDoubleLiteral EOF )
            // InternalThingML.g:6276:2: iv_ruleDoubleLiteral= ruleDoubleLiteral EOF
            {
             newCompositeNode(grammarAccess.getDoubleLiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDoubleLiteral=ruleDoubleLiteral();

            state._fsp--;

             current =iv_ruleDoubleLiteral; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDoubleLiteral"


    // $ANTLR start "ruleDoubleLiteral"
    // InternalThingML.g:6282:1: ruleDoubleLiteral returns [EObject current=null] : ( (lv_doubleValue_0_0= RULE_FLOAT ) ) ;
    public final EObject ruleDoubleLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_doubleValue_0_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6288:2: ( ( (lv_doubleValue_0_0= RULE_FLOAT ) ) )
            // InternalThingML.g:6289:2: ( (lv_doubleValue_0_0= RULE_FLOAT ) )
            {
            // InternalThingML.g:6289:2: ( (lv_doubleValue_0_0= RULE_FLOAT ) )
            // InternalThingML.g:6290:3: (lv_doubleValue_0_0= RULE_FLOAT )
            {
            // InternalThingML.g:6290:3: (lv_doubleValue_0_0= RULE_FLOAT )
            // InternalThingML.g:6291:4: lv_doubleValue_0_0= RULE_FLOAT
            {
            lv_doubleValue_0_0=(Token)match(input,RULE_FLOAT,FOLLOW_2); 

            				newLeafNode(lv_doubleValue_0_0, grammarAccess.getDoubleLiteralAccess().getDoubleValueFLOATTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getDoubleLiteralRule());
            				}
            				setWithLastConsumed(
            					current,
            					"doubleValue",
            					lv_doubleValue_0_0,
            					"org.thingml.xtext.ThingML.FLOAT");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDoubleLiteral"


    // $ANTLR start "entryRulePropertyReference"
    // InternalThingML.g:6310:1: entryRulePropertyReference returns [EObject current=null] : iv_rulePropertyReference= rulePropertyReference EOF ;
    public final EObject entryRulePropertyReference() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePropertyReference = null;


        try {
            // InternalThingML.g:6310:58: (iv_rulePropertyReference= rulePropertyReference EOF )
            // InternalThingML.g:6311:2: iv_rulePropertyReference= rulePropertyReference EOF
            {
             newCompositeNode(grammarAccess.getPropertyReferenceRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePropertyReference=rulePropertyReference();

            state._fsp--;

             current =iv_rulePropertyReference; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePropertyReference"


    // $ANTLR start "rulePropertyReference"
    // InternalThingML.g:6317:1: rulePropertyReference returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject rulePropertyReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6323:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalThingML.g:6324:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalThingML.g:6324:2: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:6325:3: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:6325:3: (otherlv_0= RULE_ID )
            // InternalThingML.g:6326:4: otherlv_0= RULE_ID
            {

            				if (current==null) {
            					current = createModelElement(grammarAccess.getPropertyReferenceRule());
            				}
            			
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(otherlv_0, grammarAccess.getPropertyReferenceAccess().getPropertyVariableCrossReference_0());
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePropertyReference"


    // $ANTLR start "entryRuleEventReference"
    // InternalThingML.g:6340:1: entryRuleEventReference returns [EObject current=null] : iv_ruleEventReference= ruleEventReference EOF ;
    public final EObject entryRuleEventReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEventReference = null;


        try {
            // InternalThingML.g:6340:55: (iv_ruleEventReference= ruleEventReference EOF )
            // InternalThingML.g:6341:2: iv_ruleEventReference= ruleEventReference EOF
            {
             newCompositeNode(grammarAccess.getEventReferenceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEventReference=ruleEventReference();

            state._fsp--;

             current =iv_ruleEventReference; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEventReference"


    // $ANTLR start "ruleEventReference"
    // InternalThingML.g:6347:1: ruleEventReference returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleEventReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:6353:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalThingML.g:6354:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalThingML.g:6354:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) )
            // InternalThingML.g:6355:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) )
            {
            // InternalThingML.g:6355:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:6356:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:6356:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:6357:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEventReferenceRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_83); 

            					newLeafNode(otherlv_0, grammarAccess.getEventReferenceAccess().getReceiveMsgEventCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,94,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getEventReferenceAccess().getFullStopKeyword_1());
            		
            // InternalThingML.g:6372:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:6373:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:6373:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:6374:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEventReferenceRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_2, grammarAccess.getEventReferenceAccess().getParameterParameterCrossReference_2_0());
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEventReference"


    // $ANTLR start "entryRuleFunctionCallExpression"
    // InternalThingML.g:6389:1: entryRuleFunctionCallExpression returns [EObject current=null] : iv_ruleFunctionCallExpression= ruleFunctionCallExpression EOF ;
    public final EObject entryRuleFunctionCallExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCallExpression = null;


        try {
            // InternalThingML.g:6389:63: (iv_ruleFunctionCallExpression= ruleFunctionCallExpression EOF )
            // InternalThingML.g:6390:2: iv_ruleFunctionCallExpression= ruleFunctionCallExpression EOF
            {
             newCompositeNode(grammarAccess.getFunctionCallExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFunctionCallExpression=ruleFunctionCallExpression();

            state._fsp--;

             current =iv_ruleFunctionCallExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunctionCallExpression"


    // $ANTLR start "ruleFunctionCallExpression"
    // InternalThingML.g:6396:1: ruleFunctionCallExpression returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) ;
    public final EObject ruleFunctionCallExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6402:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) )
            // InternalThingML.g:6403:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            {
            // InternalThingML.g:6403:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            // InternalThingML.g:6404:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')'
            {
            // InternalThingML.g:6404:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:6405:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:6405:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:6406:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFunctionCallExpressionRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_25); 

            					newLeafNode(otherlv_0, grammarAccess.getFunctionCallExpressionAccess().getFunctionFunctionCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,35,FOLLOW_70); 

            			newLeafNode(otherlv_1, grammarAccess.getFunctionCallExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:6421:3: ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )?
            int alt126=2;
            int LA126_0 = input.LA(1);

            if ( (LA126_0==RULE_STRING_LIT||(LA126_0>=RULE_ID && LA126_0<=RULE_FLOAT)||LA126_0==35||LA126_0==87||(LA126_0>=91 && LA126_0<=93)) ) {
                alt126=1;
            }
            switch (alt126) {
                case 1 :
                    // InternalThingML.g:6422:4: ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    {
                    // InternalThingML.g:6422:4: ( (lv_parameters_2_0= ruleExpression ) )
                    // InternalThingML.g:6423:5: (lv_parameters_2_0= ruleExpression )
                    {
                    // InternalThingML.g:6423:5: (lv_parameters_2_0= ruleExpression )
                    // InternalThingML.g:6424:6: lv_parameters_2_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getFunctionCallExpressionAccess().getParametersExpressionParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_27);
                    lv_parameters_2_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFunctionCallExpressionRule());
                    						}
                    						add(
                    							current,
                    							"parameters",
                    							lv_parameters_2_0,
                    							"org.thingml.xtext.ThingML.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalThingML.g:6441:4: (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    loop125:
                    do {
                        int alt125=2;
                        int LA125_0 = input.LA(1);

                        if ( (LA125_0==30) ) {
                            alt125=1;
                        }


                        switch (alt125) {
                    	case 1 :
                    	    // InternalThingML.g:6442:5: otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,30,FOLLOW_23); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getFunctionCallExpressionAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalThingML.g:6446:5: ( (lv_parameters_4_0= ruleExpression ) )
                    	    // InternalThingML.g:6447:6: (lv_parameters_4_0= ruleExpression )
                    	    {
                    	    // InternalThingML.g:6447:6: (lv_parameters_4_0= ruleExpression )
                    	    // InternalThingML.g:6448:7: lv_parameters_4_0= ruleExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getFunctionCallExpressionAccess().getParametersExpressionParserRuleCall_2_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_27);
                    	    lv_parameters_4_0=ruleExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getFunctionCallExpressionRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"parameters",
                    	    								lv_parameters_4_0,
                    	    								"org.thingml.xtext.ThingML.Expression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop125;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,36,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getFunctionCallExpressionAccess().getRightParenthesisKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFunctionCallExpression"


    // $ANTLR start "entryRuleConfiguration"
    // InternalThingML.g:6475:1: entryRuleConfiguration returns [EObject current=null] : iv_ruleConfiguration= ruleConfiguration EOF ;
    public final EObject entryRuleConfiguration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConfiguration = null;


        try {
            // InternalThingML.g:6475:54: (iv_ruleConfiguration= ruleConfiguration EOF )
            // InternalThingML.g:6476:2: iv_ruleConfiguration= ruleConfiguration EOF
            {
             newCompositeNode(grammarAccess.getConfigurationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConfiguration=ruleConfiguration();

            state._fsp--;

             current =iv_ruleConfiguration; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConfiguration"


    // $ANTLR start "ruleConfiguration"
    // InternalThingML.g:6482:1: ruleConfiguration returns [EObject current=null] : (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' ) ;
    public final EObject ruleConfiguration() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        Token otherlv_7=null;
        EObject lv_annotations_2_0 = null;

        EObject lv_instances_4_0 = null;

        EObject lv_connectors_5_0 = null;

        EObject lv_propassigns_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6488:2: ( (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' ) )
            // InternalThingML.g:6489:2: (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' )
            {
            // InternalThingML.g:6489:2: (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' )
            // InternalThingML.g:6490:3: otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,95,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConfigurationAccess().getConfigurationKeyword_0());
            		
            // InternalThingML.g:6494:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:6495:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:6495:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:6496:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_15); 

            					newLeafNode(lv_name_1_0, grammarAccess.getConfigurationAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConfigurationRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:6512:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop127:
            do {
                int alt127=2;
                int LA127_0 = input.LA(1);

                if ( (LA127_0==RULE_ANNOTATION_ID) ) {
                    alt127=1;
                }


                switch (alt127) {
            	case 1 :
            	    // InternalThingML.g:6513:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:6513:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:6514:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getConfigurationAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_annotations_2_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getConfigurationRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_2_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop127;
                }
            } while (true);

            otherlv_3=(Token)match(input,25,FOLLOW_84); 

            			newLeafNode(otherlv_3, grammarAccess.getConfigurationAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalThingML.g:6535:3: ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )*
            loop128:
            do {
                int alt128=4;
                switch ( input.LA(1) ) {
                case 96:
                    {
                    alt128=1;
                    }
                    break;
                case 97:
                    {
                    alt128=2;
                    }
                    break;
                case 31:
                    {
                    alt128=3;
                    }
                    break;

                }

                switch (alt128) {
            	case 1 :
            	    // InternalThingML.g:6536:4: ( (lv_instances_4_0= ruleInstance ) )
            	    {
            	    // InternalThingML.g:6536:4: ( (lv_instances_4_0= ruleInstance ) )
            	    // InternalThingML.g:6537:5: (lv_instances_4_0= ruleInstance )
            	    {
            	    // InternalThingML.g:6537:5: (lv_instances_4_0= ruleInstance )
            	    // InternalThingML.g:6538:6: lv_instances_4_0= ruleInstance
            	    {

            	    						newCompositeNode(grammarAccess.getConfigurationAccess().getInstancesInstanceParserRuleCall_4_0_0());
            	    					
            	    pushFollow(FOLLOW_84);
            	    lv_instances_4_0=ruleInstance();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getConfigurationRule());
            	    						}
            	    						add(
            	    							current,
            	    							"instances",
            	    							lv_instances_4_0,
            	    							"org.thingml.xtext.ThingML.Instance");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:6556:4: ( (lv_connectors_5_0= ruleAbstractConnector ) )
            	    {
            	    // InternalThingML.g:6556:4: ( (lv_connectors_5_0= ruleAbstractConnector ) )
            	    // InternalThingML.g:6557:5: (lv_connectors_5_0= ruleAbstractConnector )
            	    {
            	    // InternalThingML.g:6557:5: (lv_connectors_5_0= ruleAbstractConnector )
            	    // InternalThingML.g:6558:6: lv_connectors_5_0= ruleAbstractConnector
            	    {

            	    						newCompositeNode(grammarAccess.getConfigurationAccess().getConnectorsAbstractConnectorParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_84);
            	    lv_connectors_5_0=ruleAbstractConnector();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getConfigurationRule());
            	    						}
            	    						add(
            	    							current,
            	    							"connectors",
            	    							lv_connectors_5_0,
            	    							"org.thingml.xtext.ThingML.AbstractConnector");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalThingML.g:6576:4: ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) )
            	    {
            	    // InternalThingML.g:6576:4: ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) )
            	    // InternalThingML.g:6577:5: (lv_propassigns_6_0= ruleConfigPropertyAssign )
            	    {
            	    // InternalThingML.g:6577:5: (lv_propassigns_6_0= ruleConfigPropertyAssign )
            	    // InternalThingML.g:6578:6: lv_propassigns_6_0= ruleConfigPropertyAssign
            	    {

            	    						newCompositeNode(grammarAccess.getConfigurationAccess().getPropassignsConfigPropertyAssignParserRuleCall_4_2_0());
            	    					
            	    pushFollow(FOLLOW_84);
            	    lv_propassigns_6_0=ruleConfigPropertyAssign();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getConfigurationRule());
            	    						}
            	    						add(
            	    							current,
            	    							"propassigns",
            	    							lv_propassigns_6_0,
            	    							"org.thingml.xtext.ThingML.ConfigPropertyAssign");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop128;
                }
            } while (true);

            otherlv_7=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getConfigurationAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConfiguration"


    // $ANTLR start "entryRuleInstance"
    // InternalThingML.g:6604:1: entryRuleInstance returns [EObject current=null] : iv_ruleInstance= ruleInstance EOF ;
    public final EObject entryRuleInstance() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInstance = null;


        try {
            // InternalThingML.g:6604:49: (iv_ruleInstance= ruleInstance EOF )
            // InternalThingML.g:6605:2: iv_ruleInstance= ruleInstance EOF
            {
             newCompositeNode(grammarAccess.getInstanceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInstance=ruleInstance();

            state._fsp--;

             current =iv_ruleInstance; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInstance"


    // $ANTLR start "ruleInstance"
    // InternalThingML.g:6611:1: ruleInstance returns [EObject current=null] : (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleInstance() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject lv_annotations_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6617:2: ( (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:6618:2: (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:6618:2: (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:6619:3: otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,96,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getInstanceAccess().getInstanceKeyword_0());
            		
            // InternalThingML.g:6623:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:6624:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:6624:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:6625:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(lv_name_1_0, grammarAccess.getInstanceAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getInstanceRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,16,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getInstanceAccess().getColonKeyword_2());
            		
            // InternalThingML.g:6645:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:6646:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:6646:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:6647:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getInstanceRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_17); 

            					newLeafNode(otherlv_3, grammarAccess.getInstanceAccess().getTypeThingCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:6658:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop129:
            do {
                int alt129=2;
                int LA129_0 = input.LA(1);

                if ( (LA129_0==RULE_ANNOTATION_ID) ) {
                    alt129=1;
                }


                switch (alt129) {
            	case 1 :
            	    // InternalThingML.g:6659:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:6659:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:6660:5: lv_annotations_4_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getInstanceAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_17);
            	    lv_annotations_4_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getInstanceRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_4_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop129;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInstance"


    // $ANTLR start "entryRuleConfigPropertyAssign"
    // InternalThingML.g:6681:1: entryRuleConfigPropertyAssign returns [EObject current=null] : iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF ;
    public final EObject entryRuleConfigPropertyAssign() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConfigPropertyAssign = null;


        try {
            // InternalThingML.g:6681:61: (iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF )
            // InternalThingML.g:6682:2: iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF
            {
             newCompositeNode(grammarAccess.getConfigPropertyAssignRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConfigPropertyAssign=ruleConfigPropertyAssign();

            state._fsp--;

             current =iv_ruleConfigPropertyAssign; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConfigPropertyAssign"


    // $ANTLR start "ruleConfigPropertyAssign"
    // InternalThingML.g:6688:1: ruleConfigPropertyAssign returns [EObject current=null] : (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleConfigPropertyAssign() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        EObject lv_index_5_0 = null;

        EObject lv_init_8_0 = null;

        EObject lv_annotations_9_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6694:2: ( (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:6695:2: (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:6695:2: (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:6696:3: otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,31,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConfigPropertyAssignAccess().getSetKeyword_0());
            		
            // InternalThingML.g:6700:3: ( (otherlv_1= RULE_ID ) )
            // InternalThingML.g:6701:4: (otherlv_1= RULE_ID )
            {
            // InternalThingML.g:6701:4: (otherlv_1= RULE_ID )
            // InternalThingML.g:6702:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConfigPropertyAssignRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_83); 

            					newLeafNode(otherlv_1, grammarAccess.getConfigPropertyAssignAccess().getInstanceInstanceCrossReference_1_0());
            				

            }


            }

            otherlv_2=(Token)match(input,94,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getConfigPropertyAssignAccess().getFullStopKeyword_2());
            		
            // InternalThingML.g:6717:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:6718:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:6718:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:6719:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConfigPropertyAssignRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_22); 

            					newLeafNode(otherlv_3, grammarAccess.getConfigPropertyAssignAccess().getPropertyPropertyCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:6730:3: (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )*
            loop130:
            do {
                int alt130=2;
                int LA130_0 = input.LA(1);

                if ( (LA130_0==17) ) {
                    alt130=1;
                }


                switch (alt130) {
            	case 1 :
            	    // InternalThingML.g:6731:4: otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']'
            	    {
            	    otherlv_4=(Token)match(input,17,FOLLOW_23); 

            	    				newLeafNode(otherlv_4, grammarAccess.getConfigPropertyAssignAccess().getLeftSquareBracketKeyword_4_0());
            	    			
            	    // InternalThingML.g:6735:4: ( (lv_index_5_0= ruleExpression ) )
            	    // InternalThingML.g:6736:5: (lv_index_5_0= ruleExpression )
            	    {
            	    // InternalThingML.g:6736:5: (lv_index_5_0= ruleExpression )
            	    // InternalThingML.g:6737:6: lv_index_5_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getIndexExpressionParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_10);
            	    lv_index_5_0=ruleExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getConfigPropertyAssignRule());
            	    						}
            	    						add(
            	    							current,
            	    							"index",
            	    							lv_index_5_0,
            	    							"org.thingml.xtext.ThingML.Expression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    otherlv_6=(Token)match(input,18,FOLLOW_22); 

            	    				newLeafNode(otherlv_6, grammarAccess.getConfigPropertyAssignAccess().getRightSquareBracketKeyword_4_2());
            	    			

            	    }
            	    break;

            	default :
            	    break loop130;
                }
            } while (true);

            otherlv_7=(Token)match(input,32,FOLLOW_23); 

            			newLeafNode(otherlv_7, grammarAccess.getConfigPropertyAssignAccess().getEqualsSignKeyword_5());
            		
            // InternalThingML.g:6763:3: ( (lv_init_8_0= ruleExpression ) )
            // InternalThingML.g:6764:4: (lv_init_8_0= ruleExpression )
            {
            // InternalThingML.g:6764:4: (lv_init_8_0= ruleExpression )
            // InternalThingML.g:6765:5: lv_init_8_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getInitExpressionParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_17);
            lv_init_8_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConfigPropertyAssignRule());
            					}
            					set(
            						current,
            						"init",
            						lv_init_8_0,
            						"org.thingml.xtext.ThingML.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:6782:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop131:
            do {
                int alt131=2;
                int LA131_0 = input.LA(1);

                if ( (LA131_0==RULE_ANNOTATION_ID) ) {
                    alt131=1;
                }


                switch (alt131) {
            	case 1 :
            	    // InternalThingML.g:6783:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:6783:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:6784:5: lv_annotations_9_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_17);
            	    lv_annotations_9_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getConfigPropertyAssignRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_9_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop131;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConfigPropertyAssign"


    // $ANTLR start "entryRuleAbstractConnector"
    // InternalThingML.g:6805:1: entryRuleAbstractConnector returns [EObject current=null] : iv_ruleAbstractConnector= ruleAbstractConnector EOF ;
    public final EObject entryRuleAbstractConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAbstractConnector = null;


        try {
            // InternalThingML.g:6805:58: (iv_ruleAbstractConnector= ruleAbstractConnector EOF )
            // InternalThingML.g:6806:2: iv_ruleAbstractConnector= ruleAbstractConnector EOF
            {
             newCompositeNode(grammarAccess.getAbstractConnectorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAbstractConnector=ruleAbstractConnector();

            state._fsp--;

             current =iv_ruleAbstractConnector; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAbstractConnector"


    // $ANTLR start "ruleAbstractConnector"
    // InternalThingML.g:6812:1: ruleAbstractConnector returns [EObject current=null] : (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector ) ;
    public final EObject ruleAbstractConnector() throws RecognitionException {
        EObject current = null;

        EObject this_Connector_0 = null;

        EObject this_ExternalConnector_1 = null;



        	enterRule();

        try {
            // InternalThingML.g:6818:2: ( (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector ) )
            // InternalThingML.g:6819:2: (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector )
            {
            // InternalThingML.g:6819:2: (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector )
            int alt132=2;
            int LA132_0 = input.LA(1);

            if ( (LA132_0==97) ) {
                int LA132_1 = input.LA(2);

                if ( (LA132_1==RULE_ID) ) {
                    int LA132_2 = input.LA(3);

                    if ( (LA132_2==RULE_ID) ) {
                        int LA132_3 = input.LA(4);

                        if ( (LA132_3==94) ) {
                            int LA132_4 = input.LA(5);

                            if ( (LA132_4==RULE_ID) ) {
                                int LA132_5 = input.LA(6);

                                if ( (LA132_5==99) ) {
                                    alt132=2;
                                }
                                else if ( (LA132_5==98) ) {
                                    alt132=1;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 132, 5, input);

                                    throw nvae;
                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 132, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 132, 3, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA132_2==94) ) {
                        int LA132_4 = input.LA(4);

                        if ( (LA132_4==RULE_ID) ) {
                            int LA132_5 = input.LA(5);

                            if ( (LA132_5==99) ) {
                                alt132=2;
                            }
                            else if ( (LA132_5==98) ) {
                                alt132=1;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 132, 5, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 132, 4, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 132, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 132, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 132, 0, input);

                throw nvae;
            }
            switch (alt132) {
                case 1 :
                    // InternalThingML.g:6820:3: this_Connector_0= ruleConnector
                    {

                    			newCompositeNode(grammarAccess.getAbstractConnectorAccess().getConnectorParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Connector_0=ruleConnector();

                    state._fsp--;


                    			current = this_Connector_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalThingML.g:6829:3: this_ExternalConnector_1= ruleExternalConnector
                    {

                    			newCompositeNode(grammarAccess.getAbstractConnectorAccess().getExternalConnectorParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExternalConnector_1=ruleExternalConnector();

                    state._fsp--;


                    			current = this_ExternalConnector_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAbstractConnector"


    // $ANTLR start "entryRuleConnector"
    // InternalThingML.g:6841:1: entryRuleConnector returns [EObject current=null] : iv_ruleConnector= ruleConnector EOF ;
    public final EObject entryRuleConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConnector = null;


        try {
            // InternalThingML.g:6841:50: (iv_ruleConnector= ruleConnector EOF )
            // InternalThingML.g:6842:2: iv_ruleConnector= ruleConnector EOF
            {
             newCompositeNode(grammarAccess.getConnectorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConnector=ruleConnector();

            state._fsp--;

             current =iv_ruleConnector; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConnector"


    // $ANTLR start "ruleConnector"
    // InternalThingML.g:6848:1: ruleConnector returns [EObject current=null] : (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (otherlv_6= RULE_ID ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleConnector() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        EObject lv_annotations_9_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6854:2: ( (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (otherlv_6= RULE_ID ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:6855:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (otherlv_6= RULE_ID ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:6855:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (otherlv_6= RULE_ID ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:6856:3: otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (otherlv_6= RULE_ID ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,97,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConnectorAccess().getConnectorKeyword_0());
            		
            // InternalThingML.g:6860:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt133=2;
            int LA133_0 = input.LA(1);

            if ( (LA133_0==RULE_ID) ) {
                int LA133_1 = input.LA(2);

                if ( (LA133_1==RULE_ID) ) {
                    alt133=1;
                }
            }
            switch (alt133) {
                case 1 :
                    // InternalThingML.g:6861:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:6861:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:6862:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_6); 

                    					newLeafNode(lv_name_1_0, grammarAccess.getConnectorAccess().getNameIDTerminalRuleCall_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getConnectorRule());
                    					}
                    					setWithLastConsumed(
                    						current,
                    						"name",
                    						lv_name_1_0,
                    						"org.thingml.xtext.ThingML.ID");
                    				

                    }


                    }
                    break;

            }

            // InternalThingML.g:6878:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:6879:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:6879:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:6880:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConnectorRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_83); 

            					newLeafNode(otherlv_2, grammarAccess.getConnectorAccess().getCliInstanceCrossReference_2_0());
            				

            }


            }

            otherlv_3=(Token)match(input,94,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getConnectorAccess().getFullStopKeyword_3());
            		
            // InternalThingML.g:6895:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:6896:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:6896:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:6897:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConnectorRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_85); 

            					newLeafNode(otherlv_4, grammarAccess.getConnectorAccess().getRequiredRequiredPortCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,98,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getConnectorAccess().getEqualsSignGreaterThanSignKeyword_5());
            		
            // InternalThingML.g:6912:3: ( (otherlv_6= RULE_ID ) )
            // InternalThingML.g:6913:4: (otherlv_6= RULE_ID )
            {
            // InternalThingML.g:6913:4: (otherlv_6= RULE_ID )
            // InternalThingML.g:6914:5: otherlv_6= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConnectorRule());
            					}
            				
            otherlv_6=(Token)match(input,RULE_ID,FOLLOW_83); 

            					newLeafNode(otherlv_6, grammarAccess.getConnectorAccess().getSrvInstanceCrossReference_6_0());
            				

            }


            }

            otherlv_7=(Token)match(input,94,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getConnectorAccess().getFullStopKeyword_7());
            		
            // InternalThingML.g:6929:3: ( (otherlv_8= RULE_ID ) )
            // InternalThingML.g:6930:4: (otherlv_8= RULE_ID )
            {
            // InternalThingML.g:6930:4: (otherlv_8= RULE_ID )
            // InternalThingML.g:6931:5: otherlv_8= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConnectorRule());
            					}
            				
            otherlv_8=(Token)match(input,RULE_ID,FOLLOW_17); 

            					newLeafNode(otherlv_8, grammarAccess.getConnectorAccess().getProvidedProvidedPortCrossReference_8_0());
            				

            }


            }

            // InternalThingML.g:6942:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop134:
            do {
                int alt134=2;
                int LA134_0 = input.LA(1);

                if ( (LA134_0==RULE_ANNOTATION_ID) ) {
                    alt134=1;
                }


                switch (alt134) {
            	case 1 :
            	    // InternalThingML.g:6943:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:6943:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:6944:5: lv_annotations_9_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getConnectorAccess().getAnnotationsPlatformAnnotationParserRuleCall_9_0());
            	    				
            	    pushFollow(FOLLOW_17);
            	    lv_annotations_9_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getConnectorRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_9_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop134;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConnector"


    // $ANTLR start "entryRuleExternalConnector"
    // InternalThingML.g:6965:1: entryRuleExternalConnector returns [EObject current=null] : iv_ruleExternalConnector= ruleExternalConnector EOF ;
    public final EObject entryRuleExternalConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternalConnector = null;


        try {
            // InternalThingML.g:6965:58: (iv_ruleExternalConnector= ruleExternalConnector EOF )
            // InternalThingML.g:6966:2: iv_ruleExternalConnector= ruleExternalConnector EOF
            {
             newCompositeNode(grammarAccess.getExternalConnectorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExternalConnector=ruleExternalConnector();

            state._fsp--;

             current =iv_ruleExternalConnector; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExternalConnector"


    // $ANTLR start "ruleExternalConnector"
    // InternalThingML.g:6972:1: ruleExternalConnector returns [EObject current=null] : (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleExternalConnector() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        EObject lv_annotations_7_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6978:2: ( (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:6979:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:6979:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:6980:3: otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,97,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getExternalConnectorAccess().getConnectorKeyword_0());
            		
            // InternalThingML.g:6984:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt135=2;
            int LA135_0 = input.LA(1);

            if ( (LA135_0==RULE_ID) ) {
                int LA135_1 = input.LA(2);

                if ( (LA135_1==RULE_ID) ) {
                    alt135=1;
                }
            }
            switch (alt135) {
                case 1 :
                    // InternalThingML.g:6985:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:6985:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:6986:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_6); 

                    					newLeafNode(lv_name_1_0, grammarAccess.getExternalConnectorAccess().getNameIDTerminalRuleCall_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getExternalConnectorRule());
                    					}
                    					setWithLastConsumed(
                    						current,
                    						"name",
                    						lv_name_1_0,
                    						"org.thingml.xtext.ThingML.ID");
                    				

                    }


                    }
                    break;

            }

            // InternalThingML.g:7002:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:7003:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:7003:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:7004:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalConnectorRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_83); 

            					newLeafNode(otherlv_2, grammarAccess.getExternalConnectorAccess().getInstInstanceCrossReference_2_0());
            				

            }


            }

            otherlv_3=(Token)match(input,94,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getExternalConnectorAccess().getFullStopKeyword_3());
            		
            // InternalThingML.g:7019:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:7020:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:7020:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:7021:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalConnectorRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_86); 

            					newLeafNode(otherlv_4, grammarAccess.getExternalConnectorAccess().getPortPortCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,99,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getExternalConnectorAccess().getOverKeyword_5());
            		
            // InternalThingML.g:7036:3: ( (otherlv_6= RULE_ID ) )
            // InternalThingML.g:7037:4: (otherlv_6= RULE_ID )
            {
            // InternalThingML.g:7037:4: (otherlv_6= RULE_ID )
            // InternalThingML.g:7038:5: otherlv_6= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalConnectorRule());
            					}
            				
            otherlv_6=(Token)match(input,RULE_ID,FOLLOW_17); 

            					newLeafNode(otherlv_6, grammarAccess.getExternalConnectorAccess().getProtocolProtocolCrossReference_6_0());
            				

            }


            }

            // InternalThingML.g:7049:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop136:
            do {
                int alt136=2;
                int LA136_0 = input.LA(1);

                if ( (LA136_0==RULE_ANNOTATION_ID) ) {
                    alt136=1;
                }


                switch (alt136) {
            	case 1 :
            	    // InternalThingML.g:7050:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:7050:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:7051:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getExternalConnectorAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_17);
            	    lv_annotations_7_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getExternalConnectorRule());
            	    					}
            	    					add(
            	    						current,
            	    						"annotations",
            	    						lv_annotations_7_0,
            	    						"org.thingml.xtext.ThingML.PlatformAnnotation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop136;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExternalConnector"

    // Delegated rules


    protected DFA99 dfa99 = new DFA99(this);
    protected DFA122 dfa122 = new DFA122(this);
    static final String dfa_1s = "\20\uffff";
    static final String dfa_2s = "\1\6\2\uffff\1\21\14\uffff";
    static final String dfa_3s = "\1\116\2\uffff\1\107\14\uffff";
    static final String dfa_4s = "\1\uffff\1\1\1\2\1\uffff\1\7\1\10\1\11\1\12\1\13\1\14\1\16\1\4\1\3\1\6\1\15\1\5";
    static final String dfa_5s = "\20\uffff}>";
    static final String[] dfa_6s = {
            "\1\3\1\uffff\1\2\6\uffff\1\12\26\uffff\1\12\33\uffff\1\1\5\uffff\1\4\1\5\1\uffff\1\6\1\7\1\10\1\11",
            "",
            "",
            "\1\13\16\uffff\1\13\2\uffff\1\16\41\uffff\1\14\1\17\1\15",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA99 extends DFA {

        public DFA99(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 99;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "4019:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )";
        }
    }
    static final String dfa_7s = "\13\uffff";
    static final String dfa_8s = "\2\uffff\1\10\10\uffff";
    static final String dfa_9s = "\1\4\1\uffff\1\5\10\uffff";
    static final String dfa_10s = "\1\135\1\uffff\1\141\10\uffff";
    static final String dfa_11s = "\1\uffff\1\1\1\uffff\1\3\1\4\1\5\1\6\1\10\1\7\1\11\1\2";
    static final String dfa_12s = "\13\uffff}>";
    static final String[] dfa_13s = {
            "\1\5\1\uffff\1\2\1\3\1\1\1\6\122\uffff\2\4",
            "",
            "\2\10\1\uffff\1\10\6\uffff\1\10\1\12\2\10\1\uffff\2\10\4\uffff\1\10\3\uffff\2\10\2\uffff\1\10\1\7\7\10\3\uffff\4\10\2\uffff\1\10\3\uffff\2\10\3\uffff\4\10\1\uffff\3\10\3\uffff\23\10\3\uffff\1\11\1\uffff\2\10",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_7 = DFA.unpackEncodedString(dfa_7s);
    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA122 extends DFA {

        public DFA122(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 122;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "5962:2: (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_FunctionCallExpression_7= ruleFunctionCallExpression | this_EventReference_8= ruleEventReference )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000209884002L,0x0000000080000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000209880002L,0x0000000080000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x00000008000403D0L,0x0000000038800000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000400022L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000002000020L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000004000040L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000010000040L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000022000020L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000042000020L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x2000C7E484000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000100020000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x00000008000003D0L,0x0000000038800000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000400020L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000001000000040L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000001040000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000004000018160L,0x0000000000007B04L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000010022L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000100000022L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000300004000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000300044000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x001280C004000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0012800004000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0010800004000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0020000000000040L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x01C0000000000022L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x01C0000000000002L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x01C0000000000062L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0800000002000020L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0xE213C7E484000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0xE211C7E484000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0xC000000004000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0400000000000040L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0xE203C7E484000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0xE201C7E484000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0400000000100000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x2201C7E484000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0002000004000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0800000002000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000004000018160L,0x0000000000007B0CL});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x00000018000003D0L,0x0000000038800000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000000002L,0x0000000000010000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000300002L,0x0000000000300000L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000000002L,0x0000000000C00000L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000000002L,0x0000000003000000L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000084000000L,0x0000000300000000L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});

}