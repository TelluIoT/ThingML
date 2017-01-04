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

@SuppressWarnings("all")
public class InternalThingMLParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING_LIT", "RULE_ID", "RULE_INT", "RULE_STRING_EXT", "RULE_FLOAT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'import'", "'@'", "'['", "']'", "'datatype'", "'<'", "'>'", "';'", "'object'", "'enumeration'", "'{'", "'}'", "'thing'", "'fragment'", "'includes'", "','", "'set'", "'='", "'protocol'", "'function'", "'('", "')'", "':'", "'readonly'", "'property'", "'message'", "'optional'", "'required'", "'port'", "'sends'", "'receives'", "'provided'", "'internal'", "'stream'", "'from'", "'select'", "'produce'", "'join'", "'&'", "'->'", "'::'", "'merge'", "'|'", "'keep'", "'if'", "'buffer'", "'by'", "'during'", "'length'", "'statechart'", "'init'", "'keeps'", "'history'", "'on'", "'entry'", "'exit'", "'final'", "'state'", "'composite'", "'session'", "'region'", "'transition'", "'event'", "'guard'", "'action'", "'?'", "'do'", "'end'", "'var'", "'!'", "'++'", "'--'", "'while'", "'else'", "'return'", "'print'", "'error'", "'fork'", "'or'", "'and'", "'=='", "'!='", "'>='", "'<='", "'+'", "'-'", "'*'", "'/'", "'%'", "'not'", "'true'", "'false'", "'.'", "'configuration'", "'instance'", "'connector'", "'=>'", "'over'"
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
    public static final int RULE_ID=5;
    public static final int RULE_INT=6;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=9;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int RULE_STRING_EXT=7;
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
    public static final int RULE_FLOAT=8;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int T__90=90;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__99=99;
    public static final int T__13=13;
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
    public static final int RULE_SL_COMMENT=10;
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
    public static final int T__110=110;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=11;
    public static final int RULE_ANY_OTHER=12;
    public static final int T__88=88;
    public static final int T__108=108;
    public static final int T__89=89;
    public static final int T__107=107;
    public static final int T__109=109;
    public static final int T__84=84;
    public static final int T__104=104;
    public static final int T__85=85;
    public static final int T__103=103;
    public static final int T__86=86;
    public static final int T__106=106;
    public static final int T__87=87;
    public static final int T__105=105;

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
    // InternalThingML.g:64:1: entryRuleThingMLModel returns [EObject current=null] : iv_ruleThingMLModel= ruleThingMLModel EOF ;
    public final EObject entryRuleThingMLModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThingMLModel = null;


        try {
            // InternalThingML.g:64:53: (iv_ruleThingMLModel= ruleThingMLModel EOF )
            // InternalThingML.g:65:2: iv_ruleThingMLModel= ruleThingMLModel EOF
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
    // InternalThingML.g:71:1: ruleThingMLModel returns [EObject current=null] : ( ( (lv_imports_0_0= ruleImport ) )* ( ( (lv_types_1_0= ruleType ) ) | ( (lv_protocols_2_0= ruleProtocol ) ) | ( (lv_configs_3_0= ruleConfiguration ) ) )* ) ;
    public final EObject ruleThingMLModel() throws RecognitionException {
        EObject current = null;

        EObject lv_imports_0_0 = null;

        EObject lv_types_1_0 = null;

        EObject lv_protocols_2_0 = null;

        EObject lv_configs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:77:2: ( ( ( (lv_imports_0_0= ruleImport ) )* ( ( (lv_types_1_0= ruleType ) ) | ( (lv_protocols_2_0= ruleProtocol ) ) | ( (lv_configs_3_0= ruleConfiguration ) ) )* ) )
            // InternalThingML.g:78:2: ( ( (lv_imports_0_0= ruleImport ) )* ( ( (lv_types_1_0= ruleType ) ) | ( (lv_protocols_2_0= ruleProtocol ) ) | ( (lv_configs_3_0= ruleConfiguration ) ) )* )
            {
            // InternalThingML.g:78:2: ( ( (lv_imports_0_0= ruleImport ) )* ( ( (lv_types_1_0= ruleType ) ) | ( (lv_protocols_2_0= ruleProtocol ) ) | ( (lv_configs_3_0= ruleConfiguration ) ) )* )
            // InternalThingML.g:79:3: ( (lv_imports_0_0= ruleImport ) )* ( ( (lv_types_1_0= ruleType ) ) | ( (lv_protocols_2_0= ruleProtocol ) ) | ( (lv_configs_3_0= ruleConfiguration ) ) )*
            {
            // InternalThingML.g:79:3: ( (lv_imports_0_0= ruleImport ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==13) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalThingML.g:80:4: (lv_imports_0_0= ruleImport )
            	    {
            	    // InternalThingML.g:80:4: (lv_imports_0_0= ruleImport )
            	    // InternalThingML.g:81:5: lv_imports_0_0= ruleImport
            	    {

            	    					newCompositeNode(grammarAccess.getThingMLModelAccess().getImportsImportParserRuleCall_0_0());
            	    				
            	    pushFollow(FOLLOW_3);
            	    lv_imports_0_0=ruleImport();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getThingMLModelRule());
            	    					}
            	    					add(
            	    						current,
            	    						"imports",
            	    						lv_imports_0_0,
            	    						"org.thingml.xtext.ThingML.Import");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // InternalThingML.g:98:3: ( ( (lv_types_1_0= ruleType ) ) | ( (lv_protocols_2_0= ruleProtocol ) ) | ( (lv_configs_3_0= ruleConfiguration ) ) )*
            loop2:
            do {
                int alt2=4;
                switch ( input.LA(1) ) {
                case 17:
                case 21:
                case 22:
                case 25:
                    {
                    alt2=1;
                    }
                    break;
                case 31:
                    {
                    alt2=2;
                    }
                    break;
                case 106:
                    {
                    alt2=3;
                    }
                    break;

                }

                switch (alt2) {
            	case 1 :
            	    // InternalThingML.g:99:4: ( (lv_types_1_0= ruleType ) )
            	    {
            	    // InternalThingML.g:99:4: ( (lv_types_1_0= ruleType ) )
            	    // InternalThingML.g:100:5: (lv_types_1_0= ruleType )
            	    {
            	    // InternalThingML.g:100:5: (lv_types_1_0= ruleType )
            	    // InternalThingML.g:101:6: lv_types_1_0= ruleType
            	    {

            	    						newCompositeNode(grammarAccess.getThingMLModelAccess().getTypesTypeParserRuleCall_1_0_0());
            	    					
            	    pushFollow(FOLLOW_4);
            	    lv_types_1_0=ruleType();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingMLModelRule());
            	    						}
            	    						add(
            	    							current,
            	    							"types",
            	    							lv_types_1_0,
            	    							"org.thingml.xtext.ThingML.Type");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:119:4: ( (lv_protocols_2_0= ruleProtocol ) )
            	    {
            	    // InternalThingML.g:119:4: ( (lv_protocols_2_0= ruleProtocol ) )
            	    // InternalThingML.g:120:5: (lv_protocols_2_0= ruleProtocol )
            	    {
            	    // InternalThingML.g:120:5: (lv_protocols_2_0= ruleProtocol )
            	    // InternalThingML.g:121:6: lv_protocols_2_0= ruleProtocol
            	    {

            	    						newCompositeNode(grammarAccess.getThingMLModelAccess().getProtocolsProtocolParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_4);
            	    lv_protocols_2_0=ruleProtocol();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingMLModelRule());
            	    						}
            	    						add(
            	    							current,
            	    							"protocols",
            	    							lv_protocols_2_0,
            	    							"org.thingml.xtext.ThingML.Protocol");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalThingML.g:139:4: ( (lv_configs_3_0= ruleConfiguration ) )
            	    {
            	    // InternalThingML.g:139:4: ( (lv_configs_3_0= ruleConfiguration ) )
            	    // InternalThingML.g:140:5: (lv_configs_3_0= ruleConfiguration )
            	    {
            	    // InternalThingML.g:140:5: (lv_configs_3_0= ruleConfiguration )
            	    // InternalThingML.g:141:6: lv_configs_3_0= ruleConfiguration
            	    {

            	    						newCompositeNode(grammarAccess.getThingMLModelAccess().getConfigsConfigurationParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_4);
            	    lv_configs_3_0=ruleConfiguration();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingMLModelRule());
            	    						}
            	    						add(
            	    							current,
            	    							"configs",
            	    							lv_configs_3_0,
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


    // $ANTLR start "entryRuleImport"
    // InternalThingML.g:163:1: entryRuleImport returns [EObject current=null] : iv_ruleImport= ruleImport EOF ;
    public final EObject entryRuleImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImport = null;


        try {
            // InternalThingML.g:163:47: (iv_ruleImport= ruleImport EOF )
            // InternalThingML.g:164:2: iv_ruleImport= ruleImport EOF
            {
             newCompositeNode(grammarAccess.getImportRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleImport=ruleImport();

            state._fsp--;

             current =iv_ruleImport; 
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
    // $ANTLR end "entryRuleImport"


    // $ANTLR start "ruleImport"
    // InternalThingML.g:170:1: ruleImport returns [EObject current=null] : (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) ) ;
    public final EObject ruleImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_importURI_1_0=null;


        	enterRule();

        try {
            // InternalThingML.g:176:2: ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) ) )
            // InternalThingML.g:177:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )
            {
            // InternalThingML.g:177:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )
            // InternalThingML.g:178:3: otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) )
            {
            otherlv_0=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getImportAccess().getImportKeyword_0());
            		
            // InternalThingML.g:182:3: ( (lv_importURI_1_0= RULE_STRING_LIT ) )
            // InternalThingML.g:183:4: (lv_importURI_1_0= RULE_STRING_LIT )
            {
            // InternalThingML.g:183:4: (lv_importURI_1_0= RULE_STRING_LIT )
            // InternalThingML.g:184:5: lv_importURI_1_0= RULE_STRING_LIT
            {
            lv_importURI_1_0=(Token)match(input,RULE_STRING_LIT,FOLLOW_2); 

            					newLeafNode(lv_importURI_1_0, grammarAccess.getImportAccess().getImportURISTRING_LITTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getImportRule());
            					}
            					setWithLastConsumed(
            						current,
            						"importURI",
            						lv_importURI_1_0,
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
    // $ANTLR end "ruleImport"


    // $ANTLR start "entryRulePlatformAnnotation"
    // InternalThingML.g:204:1: entryRulePlatformAnnotation returns [EObject current=null] : iv_rulePlatformAnnotation= rulePlatformAnnotation EOF ;
    public final EObject entryRulePlatformAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePlatformAnnotation = null;


        try {
            // InternalThingML.g:204:59: (iv_rulePlatformAnnotation= rulePlatformAnnotation EOF )
            // InternalThingML.g:205:2: iv_rulePlatformAnnotation= rulePlatformAnnotation EOF
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
    // InternalThingML.g:211:1: rulePlatformAnnotation returns [EObject current=null] : (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) ( (lv_value_2_0= RULE_STRING_LIT ) ) ) ;
    public final EObject rulePlatformAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token lv_value_2_0=null;


        	enterRule();

        try {
            // InternalThingML.g:217:2: ( (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) ( (lv_value_2_0= RULE_STRING_LIT ) ) ) )
            // InternalThingML.g:218:2: (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) ( (lv_value_2_0= RULE_STRING_LIT ) ) )
            {
            // InternalThingML.g:218:2: (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) ( (lv_value_2_0= RULE_STRING_LIT ) ) )
            // InternalThingML.g:219:3: otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) ( (lv_value_2_0= RULE_STRING_LIT ) )
            {
            otherlv_0=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getPlatformAnnotationAccess().getCommercialAtKeyword_0());
            		
            // InternalThingML.g:223:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:224:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:224:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:225:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_5); 

            					newLeafNode(lv_name_1_0, grammarAccess.getPlatformAnnotationAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPlatformAnnotationRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:241:3: ( (lv_value_2_0= RULE_STRING_LIT ) )
            // InternalThingML.g:242:4: (lv_value_2_0= RULE_STRING_LIT )
            {
            // InternalThingML.g:242:4: (lv_value_2_0= RULE_STRING_LIT )
            // InternalThingML.g:243:5: lv_value_2_0= RULE_STRING_LIT
            {
            lv_value_2_0=(Token)match(input,RULE_STRING_LIT,FOLLOW_2); 

            					newLeafNode(lv_value_2_0, grammarAccess.getPlatformAnnotationAccess().getValueSTRING_LITTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPlatformAnnotationRule());
            					}
            					setWithLastConsumed(
            						current,
            						"value",
            						lv_value_2_0,
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


    // $ANTLR start "entryRuleTypeRef"
    // InternalThingML.g:263:1: entryRuleTypeRef returns [EObject current=null] : iv_ruleTypeRef= ruleTypeRef EOF ;
    public final EObject entryRuleTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeRef = null;


        try {
            // InternalThingML.g:263:48: (iv_ruleTypeRef= ruleTypeRef EOF )
            // InternalThingML.g:264:2: iv_ruleTypeRef= ruleTypeRef EOF
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
    // InternalThingML.g:270:1: ruleTypeRef returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )? ) ;
    public final EObject ruleTypeRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_isArray_1_0=null;
        Token otherlv_3=null;
        EObject lv_cardinality_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:276:2: ( ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )? ) )
            // InternalThingML.g:277:2: ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )? )
            {
            // InternalThingML.g:277:2: ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )? )
            // InternalThingML.g:278:3: ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )?
            {
            // InternalThingML.g:278:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:279:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:279:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:280:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTypeRefRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(otherlv_0, grammarAccess.getTypeRefAccess().getTypeTypeCrossReference_0_0());
            				

            }


            }

            // InternalThingML.g:291:3: ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==15) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalThingML.g:292:4: ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']'
                    {
                    // InternalThingML.g:292:4: ( (lv_isArray_1_0= '[' ) )
                    // InternalThingML.g:293:5: (lv_isArray_1_0= '[' )
                    {
                    // InternalThingML.g:293:5: (lv_isArray_1_0= '[' )
                    // InternalThingML.g:294:6: lv_isArray_1_0= '['
                    {
                    lv_isArray_1_0=(Token)match(input,15,FOLLOW_8); 

                    						newLeafNode(lv_isArray_1_0, grammarAccess.getTypeRefAccess().getIsArrayLeftSquareBracketKeyword_1_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTypeRefRule());
                    						}
                    						setWithLastConsumed(current, "isArray", true, "[");
                    					

                    }


                    }

                    // InternalThingML.g:306:4: ( (lv_cardinality_2_0= ruleExpression ) )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( ((LA3_0>=RULE_STRING_LIT && LA3_0<=RULE_FLOAT)||LA3_0==33||LA3_0==98||(LA3_0>=102 && LA3_0<=104)) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // InternalThingML.g:307:5: (lv_cardinality_2_0= ruleExpression )
                            {
                            // InternalThingML.g:307:5: (lv_cardinality_2_0= ruleExpression )
                            // InternalThingML.g:308:6: lv_cardinality_2_0= ruleExpression
                            {

                            						newCompositeNode(grammarAccess.getTypeRefAccess().getCardinalityExpressionParserRuleCall_1_1_0());
                            					
                            pushFollow(FOLLOW_9);
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

                    otherlv_3=(Token)match(input,16,FOLLOW_2); 

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
    // InternalThingML.g:334:1: entryRuleType returns [EObject current=null] : iv_ruleType= ruleType EOF ;
    public final EObject entryRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleType = null;


        try {
            // InternalThingML.g:334:45: (iv_ruleType= ruleType EOF )
            // InternalThingML.g:335:2: iv_ruleType= ruleType EOF
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
    // InternalThingML.g:341:1: ruleType returns [EObject current=null] : (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing ) ;
    public final EObject ruleType() throws RecognitionException {
        EObject current = null;

        EObject this_PrimitiveType_0 = null;

        EObject this_ObjectType_1 = null;

        EObject this_Enumeration_2 = null;

        EObject this_Thing_3 = null;



        	enterRule();

        try {
            // InternalThingML.g:347:2: ( (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing ) )
            // InternalThingML.g:348:2: (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing )
            {
            // InternalThingML.g:348:2: (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing )
            int alt5=4;
            switch ( input.LA(1) ) {
            case 17:
                {
                alt5=1;
                }
                break;
            case 21:
                {
                alt5=2;
                }
                break;
            case 22:
                {
                alt5=3;
                }
                break;
            case 25:
                {
                alt5=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // InternalThingML.g:349:3: this_PrimitiveType_0= rulePrimitiveType
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
                    // InternalThingML.g:358:3: this_ObjectType_1= ruleObjectType
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
                    // InternalThingML.g:367:3: this_Enumeration_2= ruleEnumeration
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
                    // InternalThingML.g:376:3: this_Thing_3= ruleThing
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
    // InternalThingML.g:388:1: entryRulePrimitiveType returns [EObject current=null] : iv_rulePrimitiveType= rulePrimitiveType EOF ;
    public final EObject entryRulePrimitiveType() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimitiveType = null;


        try {
            // InternalThingML.g:388:54: (iv_rulePrimitiveType= rulePrimitiveType EOF )
            // InternalThingML.g:389:2: iv_rulePrimitiveType= rulePrimitiveType EOF
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
    // InternalThingML.g:395:1: rulePrimitiveType returns [EObject current=null] : (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )? ) ;
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
            // InternalThingML.g:401:2: ( (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )? ) )
            // InternalThingML.g:402:2: (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )? )
            {
            // InternalThingML.g:402:2: (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )? )
            // InternalThingML.g:403:3: otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )?
            {
            otherlv_0=(Token)match(input,17,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getPrimitiveTypeAccess().getDatatypeKeyword_0());
            		
            // InternalThingML.g:407:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:408:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:408:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:409:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_10); 

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

            otherlv_2=(Token)match(input,18,FOLLOW_11); 

            			newLeafNode(otherlv_2, grammarAccess.getPrimitiveTypeAccess().getLessThanSignKeyword_2());
            		
            // InternalThingML.g:429:3: ( (lv_ByteSize_3_0= RULE_INT ) )
            // InternalThingML.g:430:4: (lv_ByteSize_3_0= RULE_INT )
            {
            // InternalThingML.g:430:4: (lv_ByteSize_3_0= RULE_INT )
            // InternalThingML.g:431:5: lv_ByteSize_3_0= RULE_INT
            {
            lv_ByteSize_3_0=(Token)match(input,RULE_INT,FOLLOW_12); 

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

            otherlv_4=(Token)match(input,19,FOLLOW_13); 

            			newLeafNode(otherlv_4, grammarAccess.getPrimitiveTypeAccess().getGreaterThanSignKeyword_4());
            		
            // InternalThingML.g:451:3: ( (lv_annotations_5_0= rulePlatformAnnotation ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==14) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalThingML.g:452:4: (lv_annotations_5_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:452:4: (lv_annotations_5_0= rulePlatformAnnotation )
            	    // InternalThingML.g:453:5: lv_annotations_5_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_13);
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
            	    break loop6;
                }
            } while (true);

            // InternalThingML.g:470:3: (otherlv_6= ';' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==20) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalThingML.g:471:4: otherlv_6= ';'
                    {
                    otherlv_6=(Token)match(input,20,FOLLOW_2); 

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
    // InternalThingML.g:480:1: entryRuleObjectType returns [EObject current=null] : iv_ruleObjectType= ruleObjectType EOF ;
    public final EObject entryRuleObjectType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleObjectType = null;


        try {
            // InternalThingML.g:480:51: (iv_ruleObjectType= ruleObjectType EOF )
            // InternalThingML.g:481:2: iv_ruleObjectType= ruleObjectType EOF
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
    // InternalThingML.g:487:1: ruleObjectType returns [EObject current=null] : (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )? ) ;
    public final EObject ruleObjectType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        EObject lv_annotations_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:493:2: ( (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )? ) )
            // InternalThingML.g:494:2: (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )? )
            {
            // InternalThingML.g:494:2: (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )? )
            // InternalThingML.g:495:3: otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )?
            {
            otherlv_0=(Token)match(input,21,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getObjectTypeAccess().getObjectKeyword_0());
            		
            // InternalThingML.g:499:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:500:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:500:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:501:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_13); 

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

            // InternalThingML.g:517:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==14) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalThingML.g:518:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:518:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:519:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getObjectTypeAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_13);
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
            	    break loop8;
                }
            } while (true);

            // InternalThingML.g:536:3: (otherlv_3= ';' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==20) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalThingML.g:537:4: otherlv_3= ';'
                    {
                    otherlv_3=(Token)match(input,20,FOLLOW_2); 

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
    // InternalThingML.g:546:1: entryRuleEnumeration returns [EObject current=null] : iv_ruleEnumeration= ruleEnumeration EOF ;
    public final EObject entryRuleEnumeration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumeration = null;


        try {
            // InternalThingML.g:546:52: (iv_ruleEnumeration= ruleEnumeration EOF )
            // InternalThingML.g:547:2: iv_ruleEnumeration= ruleEnumeration EOF
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
    // InternalThingML.g:553:1: ruleEnumeration returns [EObject current=null] : (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' ) ;
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
            // InternalThingML.g:559:2: ( (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' ) )
            // InternalThingML.g:560:2: (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' )
            {
            // InternalThingML.g:560:2: (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' )
            // InternalThingML.g:561:3: otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,22,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getEnumerationAccess().getEnumerationKeyword_0());
            		
            // InternalThingML.g:565:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:566:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:566:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:567:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_14); 

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

            // InternalThingML.g:583:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==14) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalThingML.g:584:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:584:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:585:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getEnumerationAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_14);
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
            	    break loop10;
                }
            } while (true);

            otherlv_3=(Token)match(input,23,FOLLOW_15); 

            			newLeafNode(otherlv_3, grammarAccess.getEnumerationAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalThingML.g:606:3: ( (lv_literals_4_0= ruleEnumerationLiteral ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==RULE_ID) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalThingML.g:607:4: (lv_literals_4_0= ruleEnumerationLiteral )
            	    {
            	    // InternalThingML.g:607:4: (lv_literals_4_0= ruleEnumerationLiteral )
            	    // InternalThingML.g:608:5: lv_literals_4_0= ruleEnumerationLiteral
            	    {

            	    					newCompositeNode(grammarAccess.getEnumerationAccess().getLiteralsEnumerationLiteralParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_15);
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
            	    break loop11;
                }
            } while (true);

            otherlv_5=(Token)match(input,24,FOLLOW_2); 

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
    // InternalThingML.g:633:1: entryRuleEnumerationLiteral returns [EObject current=null] : iv_ruleEnumerationLiteral= ruleEnumerationLiteral EOF ;
    public final EObject entryRuleEnumerationLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumerationLiteral = null;


        try {
            // InternalThingML.g:633:59: (iv_ruleEnumerationLiteral= ruleEnumerationLiteral EOF )
            // InternalThingML.g:634:2: iv_ruleEnumerationLiteral= ruleEnumerationLiteral EOF
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
    // InternalThingML.g:640:1: ruleEnumerationLiteral returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleEnumerationLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        EObject lv_annotations_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:646:2: ( ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:647:2: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:647:2: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:648:3: ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:648:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:649:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:649:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:650:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_16); 

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

            // InternalThingML.g:666:3: ( (lv_annotations_1_0= rulePlatformAnnotation ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==14) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalThingML.g:667:4: (lv_annotations_1_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:667:4: (lv_annotations_1_0= rulePlatformAnnotation )
            	    // InternalThingML.g:668:5: lv_annotations_1_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getEnumerationLiteralAccess().getAnnotationsPlatformAnnotationParserRuleCall_1_0());
            	    				
            	    pushFollow(FOLLOW_16);
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
            	    break loop12;
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
    // InternalThingML.g:689:1: entryRuleThing returns [EObject current=null] : iv_ruleThing= ruleThing EOF ;
    public final EObject entryRuleThing() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThing = null;


        try {
            // InternalThingML.g:689:46: (iv_ruleThing= ruleThing EOF )
            // InternalThingML.g:690:2: iv_ruleThing= ruleThing EOF
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
    // InternalThingML.g:696:1: ruleThing returns [EObject current=null] : (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) | ( (lv_streams_15_0= ruleStream ) ) )* otherlv_16= '}' ) ;
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
        Token otherlv_16=null;
        EObject lv_annotations_7_0 = null;

        EObject lv_messages_9_0 = null;

        EObject lv_ports_10_0 = null;

        EObject lv_properties_11_0 = null;

        EObject lv_functions_12_0 = null;

        EObject lv_assign_13_0 = null;

        EObject lv_behaviour_14_0 = null;

        EObject lv_streams_15_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:702:2: ( (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) | ( (lv_streams_15_0= ruleStream ) ) )* otherlv_16= '}' ) )
            // InternalThingML.g:703:2: (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) | ( (lv_streams_15_0= ruleStream ) ) )* otherlv_16= '}' )
            {
            // InternalThingML.g:703:2: (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) | ( (lv_streams_15_0= ruleStream ) ) )* otherlv_16= '}' )
            // InternalThingML.g:704:3: otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) | ( (lv_streams_15_0= ruleStream ) ) )* otherlv_16= '}'
            {
            otherlv_0=(Token)match(input,25,FOLLOW_17); 

            			newLeafNode(otherlv_0, grammarAccess.getThingAccess().getThingKeyword_0());
            		
            // InternalThingML.g:708:3: ( (lv_fragment_1_0= 'fragment' ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==26) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalThingML.g:709:4: (lv_fragment_1_0= 'fragment' )
                    {
                    // InternalThingML.g:709:4: (lv_fragment_1_0= 'fragment' )
                    // InternalThingML.g:710:5: lv_fragment_1_0= 'fragment'
                    {
                    lv_fragment_1_0=(Token)match(input,26,FOLLOW_6); 

                    					newLeafNode(lv_fragment_1_0, grammarAccess.getThingAccess().getFragmentFragmentKeyword_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getThingRule());
                    					}
                    					setWithLastConsumed(current, "fragment", true, "fragment");
                    				

                    }


                    }
                    break;

            }

            // InternalThingML.g:722:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:723:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:723:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:724:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_18); 

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

            // InternalThingML.g:740:3: (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==27) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalThingML.g:741:4: otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )*
                    {
                    otherlv_3=(Token)match(input,27,FOLLOW_6); 

                    				newLeafNode(otherlv_3, grammarAccess.getThingAccess().getIncludesKeyword_3_0());
                    			
                    // InternalThingML.g:745:4: ( (otherlv_4= RULE_ID ) )
                    // InternalThingML.g:746:5: (otherlv_4= RULE_ID )
                    {
                    // InternalThingML.g:746:5: (otherlv_4= RULE_ID )
                    // InternalThingML.g:747:6: otherlv_4= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getThingRule());
                    						}
                    					
                    otherlv_4=(Token)match(input,RULE_ID,FOLLOW_19); 

                    						newLeafNode(otherlv_4, grammarAccess.getThingAccess().getIncludesThingCrossReference_3_1_0());
                    					

                    }


                    }

                    // InternalThingML.g:758:4: (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==28) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // InternalThingML.g:759:5: otherlv_5= ',' ( (otherlv_6= RULE_ID ) )
                    	    {
                    	    otherlv_5=(Token)match(input,28,FOLLOW_6); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getThingAccess().getCommaKeyword_3_2_0());
                    	    				
                    	    // InternalThingML.g:763:5: ( (otherlv_6= RULE_ID ) )
                    	    // InternalThingML.g:764:6: (otherlv_6= RULE_ID )
                    	    {
                    	    // InternalThingML.g:764:6: (otherlv_6= RULE_ID )
                    	    // InternalThingML.g:765:7: otherlv_6= RULE_ID
                    	    {

                    	    							if (current==null) {
                    	    								current = createModelElement(grammarAccess.getThingRule());
                    	    							}
                    	    						
                    	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_19); 

                    	    							newLeafNode(otherlv_6, grammarAccess.getThingAccess().getIncludesThingCrossReference_3_2_1_0());
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);


                    }
                    break;

            }

            // InternalThingML.g:778:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==14) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalThingML.g:779:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:779:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:780:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getThingAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_14);
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
            	    break loop16;
                }
            } while (true);

            otherlv_8=(Token)match(input,23,FOLLOW_20); 

            			newLeafNode(otherlv_8, grammarAccess.getThingAccess().getLeftCurlyBracketKeyword_5());
            		
            // InternalThingML.g:801:3: ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) | ( (lv_streams_15_0= ruleStream ) ) )*
            loop17:
            do {
                int alt17=8;
                switch ( input.LA(1) ) {
                case 38:
                    {
                    alt17=1;
                    }
                    break;
                case 39:
                case 40:
                case 44:
                case 45:
                    {
                    alt17=2;
                    }
                    break;
                case 36:
                case 37:
                    {
                    alt17=3;
                    }
                    break;
                case 32:
                    {
                    alt17=4;
                    }
                    break;
                case 29:
                    {
                    alt17=5;
                    }
                    break;
                case 62:
                    {
                    alt17=6;
                    }
                    break;
                case 46:
                    {
                    alt17=7;
                    }
                    break;

                }

                switch (alt17) {
            	case 1 :
            	    // InternalThingML.g:802:4: ( (lv_messages_9_0= ruleMessage ) )
            	    {
            	    // InternalThingML.g:802:4: ( (lv_messages_9_0= ruleMessage ) )
            	    // InternalThingML.g:803:5: (lv_messages_9_0= ruleMessage )
            	    {
            	    // InternalThingML.g:803:5: (lv_messages_9_0= ruleMessage )
            	    // InternalThingML.g:804:6: lv_messages_9_0= ruleMessage
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getMessagesMessageParserRuleCall_6_0_0());
            	    					
            	    pushFollow(FOLLOW_20);
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
            	    // InternalThingML.g:822:4: ( (lv_ports_10_0= rulePort ) )
            	    {
            	    // InternalThingML.g:822:4: ( (lv_ports_10_0= rulePort ) )
            	    // InternalThingML.g:823:5: (lv_ports_10_0= rulePort )
            	    {
            	    // InternalThingML.g:823:5: (lv_ports_10_0= rulePort )
            	    // InternalThingML.g:824:6: lv_ports_10_0= rulePort
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getPortsPortParserRuleCall_6_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
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
            	    // InternalThingML.g:842:4: ( (lv_properties_11_0= ruleProperty ) )
            	    {
            	    // InternalThingML.g:842:4: ( (lv_properties_11_0= ruleProperty ) )
            	    // InternalThingML.g:843:5: (lv_properties_11_0= ruleProperty )
            	    {
            	    // InternalThingML.g:843:5: (lv_properties_11_0= ruleProperty )
            	    // InternalThingML.g:844:6: lv_properties_11_0= ruleProperty
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getPropertiesPropertyParserRuleCall_6_2_0());
            	    					
            	    pushFollow(FOLLOW_20);
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
            	    // InternalThingML.g:862:4: ( (lv_functions_12_0= ruleFunction ) )
            	    {
            	    // InternalThingML.g:862:4: ( (lv_functions_12_0= ruleFunction ) )
            	    // InternalThingML.g:863:5: (lv_functions_12_0= ruleFunction )
            	    {
            	    // InternalThingML.g:863:5: (lv_functions_12_0= ruleFunction )
            	    // InternalThingML.g:864:6: lv_functions_12_0= ruleFunction
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getFunctionsFunctionParserRuleCall_6_3_0());
            	    					
            	    pushFollow(FOLLOW_20);
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
            	    // InternalThingML.g:882:4: ( (lv_assign_13_0= rulePropertyAssign ) )
            	    {
            	    // InternalThingML.g:882:4: ( (lv_assign_13_0= rulePropertyAssign ) )
            	    // InternalThingML.g:883:5: (lv_assign_13_0= rulePropertyAssign )
            	    {
            	    // InternalThingML.g:883:5: (lv_assign_13_0= rulePropertyAssign )
            	    // InternalThingML.g:884:6: lv_assign_13_0= rulePropertyAssign
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getAssignPropertyAssignParserRuleCall_6_4_0());
            	    					
            	    pushFollow(FOLLOW_20);
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
            	    // InternalThingML.g:902:4: ( (lv_behaviour_14_0= ruleStateMachine ) )
            	    {
            	    // InternalThingML.g:902:4: ( (lv_behaviour_14_0= ruleStateMachine ) )
            	    // InternalThingML.g:903:5: (lv_behaviour_14_0= ruleStateMachine )
            	    {
            	    // InternalThingML.g:903:5: (lv_behaviour_14_0= ruleStateMachine )
            	    // InternalThingML.g:904:6: lv_behaviour_14_0= ruleStateMachine
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getBehaviourStateMachineParserRuleCall_6_5_0());
            	    					
            	    pushFollow(FOLLOW_20);
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
            	case 7 :
            	    // InternalThingML.g:922:4: ( (lv_streams_15_0= ruleStream ) )
            	    {
            	    // InternalThingML.g:922:4: ( (lv_streams_15_0= ruleStream ) )
            	    // InternalThingML.g:923:5: (lv_streams_15_0= ruleStream )
            	    {
            	    // InternalThingML.g:923:5: (lv_streams_15_0= ruleStream )
            	    // InternalThingML.g:924:6: lv_streams_15_0= ruleStream
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getStreamsStreamParserRuleCall_6_6_0());
            	    					
            	    pushFollow(FOLLOW_20);
            	    lv_streams_15_0=ruleStream();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getThingRule());
            	    						}
            	    						add(
            	    							current,
            	    							"streams",
            	    							lv_streams_15_0,
            	    							"org.thingml.xtext.ThingML.Stream");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            otherlv_16=(Token)match(input,24,FOLLOW_2); 

            			newLeafNode(otherlv_16, grammarAccess.getThingAccess().getRightCurlyBracketKeyword_7());
            		

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
    // InternalThingML.g:950:1: entryRulePropertyAssign returns [EObject current=null] : iv_rulePropertyAssign= rulePropertyAssign EOF ;
    public final EObject entryRulePropertyAssign() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePropertyAssign = null;


        try {
            // InternalThingML.g:950:55: (iv_rulePropertyAssign= rulePropertyAssign EOF )
            // InternalThingML.g:951:2: iv_rulePropertyAssign= rulePropertyAssign EOF
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
    // InternalThingML.g:957:1: rulePropertyAssign returns [EObject current=null] : (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:963:2: ( (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:964:2: (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:964:2: (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:965:3: otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,29,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getPropertyAssignAccess().getSetKeyword_0());
            		
            // InternalThingML.g:969:3: ( (otherlv_1= RULE_ID ) )
            // InternalThingML.g:970:4: (otherlv_1= RULE_ID )
            {
            // InternalThingML.g:970:4: (otherlv_1= RULE_ID )
            // InternalThingML.g:971:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPropertyAssignRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_1, grammarAccess.getPropertyAssignAccess().getPropertyPropertyCrossReference_1_0());
            				

            }


            }

            // InternalThingML.g:982:3: (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==15) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalThingML.g:983:4: otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']'
            	    {
            	    otherlv_2=(Token)match(input,15,FOLLOW_22); 

            	    				newLeafNode(otherlv_2, grammarAccess.getPropertyAssignAccess().getLeftSquareBracketKeyword_2_0());
            	    			
            	    // InternalThingML.g:987:4: ( (lv_index_3_0= ruleExpression ) )
            	    // InternalThingML.g:988:5: (lv_index_3_0= ruleExpression )
            	    {
            	    // InternalThingML.g:988:5: (lv_index_3_0= ruleExpression )
            	    // InternalThingML.g:989:6: lv_index_3_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getPropertyAssignAccess().getIndexExpressionParserRuleCall_2_1_0());
            	    					
            	    pushFollow(FOLLOW_9);
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

            	    otherlv_4=(Token)match(input,16,FOLLOW_21); 

            	    				newLeafNode(otherlv_4, grammarAccess.getPropertyAssignAccess().getRightSquareBracketKeyword_2_2());
            	    			

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            otherlv_5=(Token)match(input,30,FOLLOW_22); 

            			newLeafNode(otherlv_5, grammarAccess.getPropertyAssignAccess().getEqualsSignKeyword_3());
            		
            // InternalThingML.g:1015:3: ( (lv_init_6_0= ruleExpression ) )
            // InternalThingML.g:1016:4: (lv_init_6_0= ruleExpression )
            {
            // InternalThingML.g:1016:4: (lv_init_6_0= ruleExpression )
            // InternalThingML.g:1017:5: lv_init_6_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getPropertyAssignAccess().getInitExpressionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_16);
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

            // InternalThingML.g:1034:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==14) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalThingML.g:1035:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1035:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1036:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getPropertyAssignAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_16);
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
            	    break loop19;
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
    // InternalThingML.g:1057:1: entryRuleProtocol returns [EObject current=null] : iv_ruleProtocol= ruleProtocol EOF ;
    public final EObject entryRuleProtocol() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProtocol = null;


        try {
            // InternalThingML.g:1057:49: (iv_ruleProtocol= ruleProtocol EOF )
            // InternalThingML.g:1058:2: iv_ruleProtocol= ruleProtocol EOF
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
    // InternalThingML.g:1064:1: ruleProtocol returns [EObject current=null] : (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' ) ;
    public final EObject ruleProtocol() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        EObject lv_annotations_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1070:2: ( (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' ) )
            // InternalThingML.g:1071:2: (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' )
            {
            // InternalThingML.g:1071:2: (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' )
            // InternalThingML.g:1072:3: otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';'
            {
            otherlv_0=(Token)match(input,31,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getProtocolAccess().getProtocolKeyword_0());
            		
            // InternalThingML.g:1076:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1077:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1077:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1078:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_23); 

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

            // InternalThingML.g:1094:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==14) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalThingML.g:1095:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1095:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1096:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getProtocolAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_23);
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
            	    break loop20;
                }
            } while (true);

            otherlv_3=(Token)match(input,20,FOLLOW_2); 

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
    // InternalThingML.g:1121:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalThingML.g:1121:49: (iv_ruleFunction= ruleFunction EOF )
            // InternalThingML.g:1122:2: iv_ruleFunction= ruleFunction EOF
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
    // InternalThingML.g:1128:1: ruleFunction returns [EObject current=null] : (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) ) ;
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



        	enterRule();

        try {
            // InternalThingML.g:1134:2: ( (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) ) )
            // InternalThingML.g:1135:2: (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) )
            {
            // InternalThingML.g:1135:2: (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) )
            // InternalThingML.g:1136:3: otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) )
            {
            otherlv_0=(Token)match(input,32,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getFunctionAccess().getFunctionKeyword_0());
            		
            // InternalThingML.g:1140:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1141:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1141:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1142:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_24); 

            					newLeafNode(lv_name_1_0, grammarAccess.getFunctionAccess().getNameIDTerminalRuleCall_1_0());
            				

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

            otherlv_2=(Token)match(input,33,FOLLOW_25); 

            			newLeafNode(otherlv_2, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_2());
            		
            // InternalThingML.g:1162:3: ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==RULE_ID) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalThingML.g:1163:4: ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    {
                    // InternalThingML.g:1163:4: ( (lv_parameters_3_0= ruleParameter ) )
                    // InternalThingML.g:1164:5: (lv_parameters_3_0= ruleParameter )
                    {
                    // InternalThingML.g:1164:5: (lv_parameters_3_0= ruleParameter )
                    // InternalThingML.g:1165:6: lv_parameters_3_0= ruleParameter
                    {

                    						newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_3_0_0());
                    					
                    pushFollow(FOLLOW_26);
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

                    // InternalThingML.g:1182:4: (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==28) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // InternalThingML.g:1183:5: otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) )
                    	    {
                    	    otherlv_4=(Token)match(input,28,FOLLOW_6); 

                    	    					newLeafNode(otherlv_4, grammarAccess.getFunctionAccess().getCommaKeyword_3_1_0());
                    	    				
                    	    // InternalThingML.g:1187:5: ( (lv_parameters_5_0= ruleParameter ) )
                    	    // InternalThingML.g:1188:6: (lv_parameters_5_0= ruleParameter )
                    	    {
                    	    // InternalThingML.g:1188:6: (lv_parameters_5_0= ruleParameter )
                    	    // InternalThingML.g:1189:7: lv_parameters_5_0= ruleParameter
                    	    {

                    	    							newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_3_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
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
                    	    break loop21;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,34,FOLLOW_27); 

            			newLeafNode(otherlv_6, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_4());
            		
            // InternalThingML.g:1212:3: (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==35) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalThingML.g:1213:4: otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) )
                    {
                    otherlv_7=(Token)match(input,35,FOLLOW_6); 

                    				newLeafNode(otherlv_7, grammarAccess.getFunctionAccess().getColonKeyword_5_0());
                    			
                    // InternalThingML.g:1217:4: ( (lv_typeRef_8_0= ruleTypeRef ) )
                    // InternalThingML.g:1218:5: (lv_typeRef_8_0= ruleTypeRef )
                    {
                    // InternalThingML.g:1218:5: (lv_typeRef_8_0= ruleTypeRef )
                    // InternalThingML.g:1219:6: lv_typeRef_8_0= ruleTypeRef
                    {

                    						newCompositeNode(grammarAccess.getFunctionAccess().getTypeRefTypeRefParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_27);
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

            // InternalThingML.g:1237:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==14) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalThingML.g:1238:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1238:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1239:5: lv_annotations_9_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getFunctionAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_27);
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
            	    break loop24;
                }
            } while (true);

            // InternalThingML.g:1256:3: ( (lv_body_10_0= ruleAction ) )
            // InternalThingML.g:1257:4: (lv_body_10_0= ruleAction )
            {
            // InternalThingML.g:1257:4: (lv_body_10_0= ruleAction )
            // InternalThingML.g:1258:5: lv_body_10_0= ruleAction
            {

            					newCompositeNode(grammarAccess.getFunctionAccess().getBodyActionParserRuleCall_7_0());
            				
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


    // $ANTLR start "entryRuleProperty"
    // InternalThingML.g:1279:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // InternalThingML.g:1279:49: (iv_ruleProperty= ruleProperty EOF )
            // InternalThingML.g:1280:2: iv_ruleProperty= ruleProperty EOF
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
    // InternalThingML.g:1286:1: ruleProperty returns [EObject current=null] : ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        Token lv_changeable_0_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_typeRef_4_0 = null;

        EObject lv_init_6_0 = null;

        EObject lv_annotations_7_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1292:2: ( ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:1293:2: ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:1293:2: ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:1294:3: ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:1294:3: ( (lv_changeable_0_0= 'readonly' ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==36) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalThingML.g:1295:4: (lv_changeable_0_0= 'readonly' )
                    {
                    // InternalThingML.g:1295:4: (lv_changeable_0_0= 'readonly' )
                    // InternalThingML.g:1296:5: lv_changeable_0_0= 'readonly'
                    {
                    lv_changeable_0_0=(Token)match(input,36,FOLLOW_28); 

                    					newLeafNode(lv_changeable_0_0, grammarAccess.getPropertyAccess().getChangeableReadonlyKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getPropertyRule());
                    					}
                    					setWithLastConsumed(current, "changeable", true, "readonly");
                    				

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,37,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getPropertyAccess().getPropertyKeyword_1());
            		
            // InternalThingML.g:1312:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:1313:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:1313:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:1314:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_29); 

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

            otherlv_3=(Token)match(input,35,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getPropertyAccess().getColonKeyword_3());
            		
            // InternalThingML.g:1334:3: ( (lv_typeRef_4_0= ruleTypeRef ) )
            // InternalThingML.g:1335:4: (lv_typeRef_4_0= ruleTypeRef )
            {
            // InternalThingML.g:1335:4: (lv_typeRef_4_0= ruleTypeRef )
            // InternalThingML.g:1336:5: lv_typeRef_4_0= ruleTypeRef
            {

            					newCompositeNode(grammarAccess.getPropertyAccess().getTypeRefTypeRefParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_30);
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

            // InternalThingML.g:1353:3: (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==30) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalThingML.g:1354:4: otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) )
                    {
                    otherlv_5=(Token)match(input,30,FOLLOW_22); 

                    				newLeafNode(otherlv_5, grammarAccess.getPropertyAccess().getEqualsSignKeyword_5_0());
                    			
                    // InternalThingML.g:1358:4: ( (lv_init_6_0= ruleExpression ) )
                    // InternalThingML.g:1359:5: (lv_init_6_0= ruleExpression )
                    {
                    // InternalThingML.g:1359:5: (lv_init_6_0= ruleExpression )
                    // InternalThingML.g:1360:6: lv_init_6_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getPropertyAccess().getInitExpressionParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_16);
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

            // InternalThingML.g:1378:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==14) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalThingML.g:1379:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1379:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1380:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getPropertyAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_16);
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
            	    break loop27;
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
    // InternalThingML.g:1401:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // InternalThingML.g:1401:48: (iv_ruleMessage= ruleMessage EOF )
            // InternalThingML.g:1402:2: iv_ruleMessage= ruleMessage EOF
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
    // InternalThingML.g:1408:1: ruleMessage returns [EObject current=null] : (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )? ) ;
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
            // InternalThingML.g:1414:2: ( (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )? ) )
            // InternalThingML.g:1415:2: (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )? )
            {
            // InternalThingML.g:1415:2: (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )? )
            // InternalThingML.g:1416:3: otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )?
            {
            otherlv_0=(Token)match(input,38,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getMessageAccess().getMessageKeyword_0());
            		
            // InternalThingML.g:1420:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1421:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1421:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1422:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_24); 

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

            otherlv_2=(Token)match(input,33,FOLLOW_25); 

            			newLeafNode(otherlv_2, grammarAccess.getMessageAccess().getLeftParenthesisKeyword_2());
            		
            // InternalThingML.g:1442:3: ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==RULE_ID) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalThingML.g:1443:4: ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    {
                    // InternalThingML.g:1443:4: ( (lv_parameters_3_0= ruleParameter ) )
                    // InternalThingML.g:1444:5: (lv_parameters_3_0= ruleParameter )
                    {
                    // InternalThingML.g:1444:5: (lv_parameters_3_0= ruleParameter )
                    // InternalThingML.g:1445:6: lv_parameters_3_0= ruleParameter
                    {

                    						newCompositeNode(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_0_0());
                    					
                    pushFollow(FOLLOW_26);
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

                    // InternalThingML.g:1462:4: (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    loop28:
                    do {
                        int alt28=2;
                        int LA28_0 = input.LA(1);

                        if ( (LA28_0==28) ) {
                            alt28=1;
                        }


                        switch (alt28) {
                    	case 1 :
                    	    // InternalThingML.g:1463:5: otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) )
                    	    {
                    	    otherlv_4=(Token)match(input,28,FOLLOW_6); 

                    	    					newLeafNode(otherlv_4, grammarAccess.getMessageAccess().getCommaKeyword_3_1_0());
                    	    				
                    	    // InternalThingML.g:1467:5: ( (lv_parameters_5_0= ruleParameter ) )
                    	    // InternalThingML.g:1468:6: (lv_parameters_5_0= ruleParameter )
                    	    {
                    	    // InternalThingML.g:1468:6: (lv_parameters_5_0= ruleParameter )
                    	    // InternalThingML.g:1469:7: lv_parameters_5_0= ruleParameter
                    	    {

                    	    							newCompositeNode(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
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
                    	    break loop28;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,34,FOLLOW_13); 

            			newLeafNode(otherlv_6, grammarAccess.getMessageAccess().getRightParenthesisKeyword_4());
            		
            // InternalThingML.g:1492:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==14) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalThingML.g:1493:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1493:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1494:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getMessageAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_13);
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
            	    break loop30;
                }
            } while (true);

            // InternalThingML.g:1511:3: (otherlv_8= ';' )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==20) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalThingML.g:1512:4: otherlv_8= ';'
                    {
                    otherlv_8=(Token)match(input,20,FOLLOW_2); 

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
    // InternalThingML.g:1521:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalThingML.g:1521:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalThingML.g:1522:2: iv_ruleParameter= ruleParameter EOF
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
    // InternalThingML.g:1528:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_typeRef_2_0 = null;

        EObject lv_annotations_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1534:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:1535:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:1535:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:1536:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:1536:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:1537:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:1537:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:1538:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_29); 

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

            otherlv_1=(Token)match(input,35,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getParameterAccess().getColonKeyword_1());
            		
            // InternalThingML.g:1558:3: ( (lv_typeRef_2_0= ruleTypeRef ) )
            // InternalThingML.g:1559:4: (lv_typeRef_2_0= ruleTypeRef )
            {
            // InternalThingML.g:1559:4: (lv_typeRef_2_0= ruleTypeRef )
            // InternalThingML.g:1560:5: lv_typeRef_2_0= ruleTypeRef
            {

            					newCompositeNode(grammarAccess.getParameterAccess().getTypeRefTypeRefParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_16);
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

            // InternalThingML.g:1577:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==14) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalThingML.g:1578:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1578:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1579:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getParameterAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_16);
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
            	    break loop32;
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
    // InternalThingML.g:1600:1: entryRulePort returns [EObject current=null] : iv_rulePort= rulePort EOF ;
    public final EObject entryRulePort() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePort = null;


        try {
            // InternalThingML.g:1600:45: (iv_rulePort= rulePort EOF )
            // InternalThingML.g:1601:2: iv_rulePort= rulePort EOF
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
    // InternalThingML.g:1607:1: rulePort returns [EObject current=null] : (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort ) ;
    public final EObject rulePort() throws RecognitionException {
        EObject current = null;

        EObject this_RequiredPort_0 = null;

        EObject this_ProvidedPort_1 = null;

        EObject this_InternalPort_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:1613:2: ( (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort ) )
            // InternalThingML.g:1614:2: (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort )
            {
            // InternalThingML.g:1614:2: (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort )
            int alt33=3;
            switch ( input.LA(1) ) {
            case 39:
            case 40:
                {
                alt33=1;
                }
                break;
            case 44:
                {
                alt33=2;
                }
                break;
            case 45:
                {
                alt33=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;
            }

            switch (alt33) {
                case 1 :
                    // InternalThingML.g:1615:3: this_RequiredPort_0= ruleRequiredPort
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
                    // InternalThingML.g:1624:3: this_ProvidedPort_1= ruleProvidedPort
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
                    // InternalThingML.g:1633:3: this_InternalPort_2= ruleInternalPort
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
    // InternalThingML.g:1645:1: entryRuleRequiredPort returns [EObject current=null] : iv_ruleRequiredPort= ruleRequiredPort EOF ;
    public final EObject entryRuleRequiredPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequiredPort = null;


        try {
            // InternalThingML.g:1645:53: (iv_ruleRequiredPort= ruleRequiredPort EOF )
            // InternalThingML.g:1646:2: iv_ruleRequiredPort= ruleRequiredPort EOF
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
    // InternalThingML.g:1652:1: ruleRequiredPort returns [EObject current=null] : ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' ) ;
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
            // InternalThingML.g:1658:2: ( ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' ) )
            // InternalThingML.g:1659:2: ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' )
            {
            // InternalThingML.g:1659:2: ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' )
            // InternalThingML.g:1660:3: ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}'
            {
            // InternalThingML.g:1660:3: ( (lv_optional_0_0= 'optional' ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==39) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalThingML.g:1661:4: (lv_optional_0_0= 'optional' )
                    {
                    // InternalThingML.g:1661:4: (lv_optional_0_0= 'optional' )
                    // InternalThingML.g:1662:5: lv_optional_0_0= 'optional'
                    {
                    lv_optional_0_0=(Token)match(input,39,FOLLOW_31); 

                    					newLeafNode(lv_optional_0_0, grammarAccess.getRequiredPortAccess().getOptionalOptionalKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getRequiredPortRule());
                    					}
                    					setWithLastConsumed(current, "optional", true, "optional");
                    				

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,40,FOLLOW_32); 

            			newLeafNode(otherlv_1, grammarAccess.getRequiredPortAccess().getRequiredKeyword_1());
            		
            otherlv_2=(Token)match(input,41,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getRequiredPortAccess().getPortKeyword_2());
            		
            // InternalThingML.g:1682:3: ( (lv_name_3_0= RULE_ID ) )
            // InternalThingML.g:1683:4: (lv_name_3_0= RULE_ID )
            {
            // InternalThingML.g:1683:4: (lv_name_3_0= RULE_ID )
            // InternalThingML.g:1684:5: lv_name_3_0= RULE_ID
            {
            lv_name_3_0=(Token)match(input,RULE_ID,FOLLOW_14); 

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

            // InternalThingML.g:1700:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==14) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalThingML.g:1701:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1701:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1702:5: lv_annotations_4_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getRequiredPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_14);
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
            	    break loop35;
                }
            } while (true);

            otherlv_5=(Token)match(input,23,FOLLOW_33); 

            			newLeafNode(otherlv_5, grammarAccess.getRequiredPortAccess().getLeftCurlyBracketKeyword_5());
            		
            // InternalThingML.g:1723:3: ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )*
            loop38:
            do {
                int alt38=3;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==42) ) {
                    alt38=1;
                }
                else if ( (LA38_0==43) ) {
                    alt38=2;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalThingML.g:1724:4: (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1724:4: (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* )
            	    // InternalThingML.g:1725:5: otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )*
            	    {
            	    otherlv_6=(Token)match(input,42,FOLLOW_6); 

            	    					newLeafNode(otherlv_6, grammarAccess.getRequiredPortAccess().getSendsKeyword_6_0_0());
            	    				
            	    // InternalThingML.g:1729:5: ( (otherlv_7= RULE_ID ) )
            	    // InternalThingML.g:1730:6: (otherlv_7= RULE_ID )
            	    {
            	    // InternalThingML.g:1730:6: (otherlv_7= RULE_ID )
            	    // InternalThingML.g:1731:7: otherlv_7= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getRequiredPortRule());
            	    							}
            	    						
            	    otherlv_7=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    							newLeafNode(otherlv_7, grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1742:5: (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )*
            	    loop36:
            	    do {
            	        int alt36=2;
            	        int LA36_0 = input.LA(1);

            	        if ( (LA36_0==28) ) {
            	            alt36=1;
            	        }


            	        switch (alt36) {
            	    	case 1 :
            	    	    // InternalThingML.g:1743:6: otherlv_8= ',' ( (otherlv_9= RULE_ID ) )
            	    	    {
            	    	    otherlv_8=(Token)match(input,28,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_8, grammarAccess.getRequiredPortAccess().getCommaKeyword_6_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:1747:6: ( (otherlv_9= RULE_ID ) )
            	    	    // InternalThingML.g:1748:7: (otherlv_9= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1748:7: (otherlv_9= RULE_ID )
            	    	    // InternalThingML.g:1749:8: otherlv_9= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getRequiredPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_9=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    	    								newLeafNode(otherlv_9, grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop36;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:1763:4: (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1763:4: (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* )
            	    // InternalThingML.g:1764:5: otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )*
            	    {
            	    otherlv_10=(Token)match(input,43,FOLLOW_6); 

            	    					newLeafNode(otherlv_10, grammarAccess.getRequiredPortAccess().getReceivesKeyword_6_1_0());
            	    				
            	    // InternalThingML.g:1768:5: ( (otherlv_11= RULE_ID ) )
            	    // InternalThingML.g:1769:6: (otherlv_11= RULE_ID )
            	    {
            	    // InternalThingML.g:1769:6: (otherlv_11= RULE_ID )
            	    // InternalThingML.g:1770:7: otherlv_11= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getRequiredPortRule());
            	    							}
            	    						
            	    otherlv_11=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    							newLeafNode(otherlv_11, grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1781:5: (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )*
            	    loop37:
            	    do {
            	        int alt37=2;
            	        int LA37_0 = input.LA(1);

            	        if ( (LA37_0==28) ) {
            	            alt37=1;
            	        }


            	        switch (alt37) {
            	    	case 1 :
            	    	    // InternalThingML.g:1782:6: otherlv_12= ',' ( (otherlv_13= RULE_ID ) )
            	    	    {
            	    	    otherlv_12=(Token)match(input,28,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_12, grammarAccess.getRequiredPortAccess().getCommaKeyword_6_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:1786:6: ( (otherlv_13= RULE_ID ) )
            	    	    // InternalThingML.g:1787:7: (otherlv_13= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1787:7: (otherlv_13= RULE_ID )
            	    	    // InternalThingML.g:1788:8: otherlv_13= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getRequiredPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_13=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    	    								newLeafNode(otherlv_13, grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop37;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);

            otherlv_14=(Token)match(input,24,FOLLOW_2); 

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
    // InternalThingML.g:1810:1: entryRuleProvidedPort returns [EObject current=null] : iv_ruleProvidedPort= ruleProvidedPort EOF ;
    public final EObject entryRuleProvidedPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProvidedPort = null;


        try {
            // InternalThingML.g:1810:53: (iv_ruleProvidedPort= ruleProvidedPort EOF )
            // InternalThingML.g:1811:2: iv_ruleProvidedPort= ruleProvidedPort EOF
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
    // InternalThingML.g:1817:1: ruleProvidedPort returns [EObject current=null] : (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) ;
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
            // InternalThingML.g:1823:2: ( (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) )
            // InternalThingML.g:1824:2: (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            {
            // InternalThingML.g:1824:2: (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            // InternalThingML.g:1825:3: otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,44,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getProvidedPortAccess().getProvidedKeyword_0());
            		
            otherlv_1=(Token)match(input,41,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getProvidedPortAccess().getPortKeyword_1());
            		
            // InternalThingML.g:1833:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:1834:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:1834:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:1835:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_14); 

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

            // InternalThingML.g:1851:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==14) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalThingML.g:1852:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1852:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1853:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getProvidedPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_14);
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
            	    break loop39;
                }
            } while (true);

            otherlv_4=(Token)match(input,23,FOLLOW_33); 

            			newLeafNode(otherlv_4, grammarAccess.getProvidedPortAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalThingML.g:1874:3: ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )*
            loop42:
            do {
                int alt42=3;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==42) ) {
                    alt42=1;
                }
                else if ( (LA42_0==43) ) {
                    alt42=2;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalThingML.g:1875:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1875:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    // InternalThingML.g:1876:5: otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    {
            	    otherlv_5=(Token)match(input,42,FOLLOW_6); 

            	    					newLeafNode(otherlv_5, grammarAccess.getProvidedPortAccess().getSendsKeyword_5_0_0());
            	    				
            	    // InternalThingML.g:1880:5: ( (otherlv_6= RULE_ID ) )
            	    // InternalThingML.g:1881:6: (otherlv_6= RULE_ID )
            	    {
            	    // InternalThingML.g:1881:6: (otherlv_6= RULE_ID )
            	    // InternalThingML.g:1882:7: otherlv_6= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getProvidedPortRule());
            	    							}
            	    						
            	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    							newLeafNode(otherlv_6, grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1893:5: (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    loop40:
            	    do {
            	        int alt40=2;
            	        int LA40_0 = input.LA(1);

            	        if ( (LA40_0==28) ) {
            	            alt40=1;
            	        }


            	        switch (alt40) {
            	    	case 1 :
            	    	    // InternalThingML.g:1894:6: otherlv_7= ',' ( (otherlv_8= RULE_ID ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,28,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_7, grammarAccess.getProvidedPortAccess().getCommaKeyword_5_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:1898:6: ( (otherlv_8= RULE_ID ) )
            	    	    // InternalThingML.g:1899:7: (otherlv_8= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1899:7: (otherlv_8= RULE_ID )
            	    	    // InternalThingML.g:1900:8: otherlv_8= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getProvidedPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    	    								newLeafNode(otherlv_8, grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop40;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:1914:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1914:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    // InternalThingML.g:1915:5: otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    {
            	    otherlv_9=(Token)match(input,43,FOLLOW_6); 

            	    					newLeafNode(otherlv_9, grammarAccess.getProvidedPortAccess().getReceivesKeyword_5_1_0());
            	    				
            	    // InternalThingML.g:1919:5: ( (otherlv_10= RULE_ID ) )
            	    // InternalThingML.g:1920:6: (otherlv_10= RULE_ID )
            	    {
            	    // InternalThingML.g:1920:6: (otherlv_10= RULE_ID )
            	    // InternalThingML.g:1921:7: otherlv_10= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getProvidedPortRule());
            	    							}
            	    						
            	    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    							newLeafNode(otherlv_10, grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1932:5: (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    loop41:
            	    do {
            	        int alt41=2;
            	        int LA41_0 = input.LA(1);

            	        if ( (LA41_0==28) ) {
            	            alt41=1;
            	        }


            	        switch (alt41) {
            	    	case 1 :
            	    	    // InternalThingML.g:1933:6: otherlv_11= ',' ( (otherlv_12= RULE_ID ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,28,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_11, grammarAccess.getProvidedPortAccess().getCommaKeyword_5_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:1937:6: ( (otherlv_12= RULE_ID ) )
            	    	    // InternalThingML.g:1938:7: (otherlv_12= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1938:7: (otherlv_12= RULE_ID )
            	    	    // InternalThingML.g:1939:8: otherlv_12= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getProvidedPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_12=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    	    								newLeafNode(otherlv_12, grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop41;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);

            otherlv_13=(Token)match(input,24,FOLLOW_2); 

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
    // InternalThingML.g:1961:1: entryRuleInternalPort returns [EObject current=null] : iv_ruleInternalPort= ruleInternalPort EOF ;
    public final EObject entryRuleInternalPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInternalPort = null;


        try {
            // InternalThingML.g:1961:53: (iv_ruleInternalPort= ruleInternalPort EOF )
            // InternalThingML.g:1962:2: iv_ruleInternalPort= ruleInternalPort EOF
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
    // InternalThingML.g:1968:1: ruleInternalPort returns [EObject current=null] : (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) ;
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
            // InternalThingML.g:1974:2: ( (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) )
            // InternalThingML.g:1975:2: (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            {
            // InternalThingML.g:1975:2: (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            // InternalThingML.g:1976:3: otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,45,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getInternalPortAccess().getInternalKeyword_0());
            		
            otherlv_1=(Token)match(input,41,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getInternalPortAccess().getPortKeyword_1());
            		
            // InternalThingML.g:1984:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:1985:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:1985:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:1986:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_14); 

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

            // InternalThingML.g:2002:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==14) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalThingML.g:2003:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2003:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2004:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getInternalPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_14);
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
            	    break loop43;
                }
            } while (true);

            otherlv_4=(Token)match(input,23,FOLLOW_33); 

            			newLeafNode(otherlv_4, grammarAccess.getInternalPortAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalThingML.g:2025:3: ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )*
            loop46:
            do {
                int alt46=3;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==42) ) {
                    alt46=1;
                }
                else if ( (LA46_0==43) ) {
                    alt46=2;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalThingML.g:2026:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:2026:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    // InternalThingML.g:2027:5: otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    {
            	    otherlv_5=(Token)match(input,42,FOLLOW_6); 

            	    					newLeafNode(otherlv_5, grammarAccess.getInternalPortAccess().getSendsKeyword_5_0_0());
            	    				
            	    // InternalThingML.g:2031:5: ( (otherlv_6= RULE_ID ) )
            	    // InternalThingML.g:2032:6: (otherlv_6= RULE_ID )
            	    {
            	    // InternalThingML.g:2032:6: (otherlv_6= RULE_ID )
            	    // InternalThingML.g:2033:7: otherlv_6= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getInternalPortRule());
            	    							}
            	    						
            	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    							newLeafNode(otherlv_6, grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:2044:5: (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    loop44:
            	    do {
            	        int alt44=2;
            	        int LA44_0 = input.LA(1);

            	        if ( (LA44_0==28) ) {
            	            alt44=1;
            	        }


            	        switch (alt44) {
            	    	case 1 :
            	    	    // InternalThingML.g:2045:6: otherlv_7= ',' ( (otherlv_8= RULE_ID ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,28,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_7, grammarAccess.getInternalPortAccess().getCommaKeyword_5_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:2049:6: ( (otherlv_8= RULE_ID ) )
            	    	    // InternalThingML.g:2050:7: (otherlv_8= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:2050:7: (otherlv_8= RULE_ID )
            	    	    // InternalThingML.g:2051:8: otherlv_8= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getInternalPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    	    								newLeafNode(otherlv_8, grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop44;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:2065:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:2065:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    // InternalThingML.g:2066:5: otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    {
            	    otherlv_9=(Token)match(input,43,FOLLOW_6); 

            	    					newLeafNode(otherlv_9, grammarAccess.getInternalPortAccess().getReceivesKeyword_5_1_0());
            	    				
            	    // InternalThingML.g:2070:5: ( (otherlv_10= RULE_ID ) )
            	    // InternalThingML.g:2071:6: (otherlv_10= RULE_ID )
            	    {
            	    // InternalThingML.g:2071:6: (otherlv_10= RULE_ID )
            	    // InternalThingML.g:2072:7: otherlv_10= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getInternalPortRule());
            	    							}
            	    						
            	    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    							newLeafNode(otherlv_10, grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:2083:5: (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    loop45:
            	    do {
            	        int alt45=2;
            	        int LA45_0 = input.LA(1);

            	        if ( (LA45_0==28) ) {
            	            alt45=1;
            	        }


            	        switch (alt45) {
            	    	case 1 :
            	    	    // InternalThingML.g:2084:6: otherlv_11= ',' ( (otherlv_12= RULE_ID ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,28,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_11, grammarAccess.getInternalPortAccess().getCommaKeyword_5_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:2088:6: ( (otherlv_12= RULE_ID ) )
            	    	    // InternalThingML.g:2089:7: (otherlv_12= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:2089:7: (otherlv_12= RULE_ID )
            	    	    // InternalThingML.g:2090:8: otherlv_12= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getInternalPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_12=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    	    								newLeafNode(otherlv_12, grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop45;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);

            otherlv_13=(Token)match(input,24,FOLLOW_2); 

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


    // $ANTLR start "entryRuleStream"
    // InternalThingML.g:2112:1: entryRuleStream returns [EObject current=null] : iv_ruleStream= ruleStream EOF ;
    public final EObject entryRuleStream() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStream = null;


        try {
            // InternalThingML.g:2112:47: (iv_ruleStream= ruleStream EOF )
            // InternalThingML.g:2113:2: iv_ruleStream= ruleStream EOF
            {
             newCompositeNode(grammarAccess.getStreamRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStream=ruleStream();

            state._fsp--;

             current =iv_ruleStream; 
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
    // $ANTLR end "entryRuleStream"


    // $ANTLR start "ruleStream"
    // InternalThingML.g:2119:1: ruleStream returns [EObject current=null] : (otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) ) ) ;
    public final EObject ruleStream() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject lv_annotations_2_0 = null;

        EObject lv_input_4_0 = null;

        EObject lv_selection_6_0 = null;

        EObject lv_selection_8_0 = null;

        EObject lv_output_10_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2125:2: ( (otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) ) ) )
            // InternalThingML.g:2126:2: (otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) ) )
            {
            // InternalThingML.g:2126:2: (otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) ) )
            // InternalThingML.g:2127:3: otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) )
            {
            otherlv_0=(Token)match(input,46,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getStreamAccess().getStreamKeyword_0());
            		
            // InternalThingML.g:2131:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:2132:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:2132:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:2133:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_35); 

            					newLeafNode(lv_name_1_0, grammarAccess.getStreamAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStreamRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            // InternalThingML.g:2149:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==14) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalThingML.g:2150:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2150:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2151:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getStreamAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_35);
            	    lv_annotations_2_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getStreamRule());
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
            	    break loop47;
                }
            } while (true);

            otherlv_3=(Token)match(input,47,FOLLOW_36); 

            			newLeafNode(otherlv_3, grammarAccess.getStreamAccess().getFromKeyword_3());
            		
            // InternalThingML.g:2172:3: ( (lv_input_4_0= ruleSource ) )
            // InternalThingML.g:2173:4: (lv_input_4_0= ruleSource )
            {
            // InternalThingML.g:2173:4: (lv_input_4_0= ruleSource )
            // InternalThingML.g:2174:5: lv_input_4_0= ruleSource
            {

            					newCompositeNode(grammarAccess.getStreamAccess().getInputSourceParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_37);
            lv_input_4_0=ruleSource();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getStreamRule());
            					}
            					set(
            						current,
            						"input",
            						lv_input_4_0,
            						"org.thingml.xtext.ThingML.Source");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:2191:3: (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==48) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalThingML.g:2192:4: otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )*
                    {
                    otherlv_5=(Token)match(input,48,FOLLOW_27); 

                    				newLeafNode(otherlv_5, grammarAccess.getStreamAccess().getSelectKeyword_5_0());
                    			
                    // InternalThingML.g:2196:4: ( (lv_selection_6_0= ruleLocalVariable ) )
                    // InternalThingML.g:2197:5: (lv_selection_6_0= ruleLocalVariable )
                    {
                    // InternalThingML.g:2197:5: (lv_selection_6_0= ruleLocalVariable )
                    // InternalThingML.g:2198:6: lv_selection_6_0= ruleLocalVariable
                    {

                    						newCompositeNode(grammarAccess.getStreamAccess().getSelectionLocalVariableParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_38);
                    lv_selection_6_0=ruleLocalVariable();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getStreamRule());
                    						}
                    						add(
                    							current,
                    							"selection",
                    							lv_selection_6_0,
                    							"org.thingml.xtext.ThingML.LocalVariable");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalThingML.g:2215:4: (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )*
                    loop48:
                    do {
                        int alt48=2;
                        int LA48_0 = input.LA(1);

                        if ( (LA48_0==28) ) {
                            alt48=1;
                        }


                        switch (alt48) {
                    	case 1 :
                    	    // InternalThingML.g:2216:5: otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) )
                    	    {
                    	    otherlv_7=(Token)match(input,28,FOLLOW_27); 

                    	    					newLeafNode(otherlv_7, grammarAccess.getStreamAccess().getCommaKeyword_5_2_0());
                    	    				
                    	    // InternalThingML.g:2220:5: ( (lv_selection_8_0= ruleLocalVariable ) )
                    	    // InternalThingML.g:2221:6: (lv_selection_8_0= ruleLocalVariable )
                    	    {
                    	    // InternalThingML.g:2221:6: (lv_selection_8_0= ruleLocalVariable )
                    	    // InternalThingML.g:2222:7: lv_selection_8_0= ruleLocalVariable
                    	    {

                    	    							newCompositeNode(grammarAccess.getStreamAccess().getSelectionLocalVariableParserRuleCall_5_2_1_0());
                    	    						
                    	    pushFollow(FOLLOW_38);
                    	    lv_selection_8_0=ruleLocalVariable();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStreamRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"selection",
                    	    								lv_selection_8_0,
                    	    								"org.thingml.xtext.ThingML.LocalVariable");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop48;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_9=(Token)match(input,49,FOLLOW_6); 

            			newLeafNode(otherlv_9, grammarAccess.getStreamAccess().getProduceKeyword_6());
            		
            // InternalThingML.g:2245:3: ( (lv_output_10_0= ruleSendAction ) )
            // InternalThingML.g:2246:4: (lv_output_10_0= ruleSendAction )
            {
            // InternalThingML.g:2246:4: (lv_output_10_0= ruleSendAction )
            // InternalThingML.g:2247:5: lv_output_10_0= ruleSendAction
            {

            					newCompositeNode(grammarAccess.getStreamAccess().getOutputSendActionParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_2);
            lv_output_10_0=ruleSendAction();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getStreamRule());
            					}
            					set(
            						current,
            						"output",
            						lv_output_10_0,
            						"org.thingml.xtext.ThingML.SendAction");
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
    // $ANTLR end "ruleStream"


    // $ANTLR start "entryRuleSource"
    // InternalThingML.g:2268:1: entryRuleSource returns [EObject current=null] : iv_ruleSource= ruleSource EOF ;
    public final EObject entryRuleSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSource = null;


        try {
            // InternalThingML.g:2268:47: (iv_ruleSource= ruleSource EOF )
            // InternalThingML.g:2269:2: iv_ruleSource= ruleSource EOF
            {
             newCompositeNode(grammarAccess.getSourceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSource=ruleSource();

            state._fsp--;

             current =iv_ruleSource; 
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
    // $ANTLR end "entryRuleSource"


    // $ANTLR start "ruleSource"
    // InternalThingML.g:2275:1: ruleSource returns [EObject current=null] : (this_JoinSources_0= ruleJoinSources | this_MergeSources_1= ruleMergeSources | this_SimpleSource_2= ruleSimpleSource ) ;
    public final EObject ruleSource() throws RecognitionException {
        EObject current = null;

        EObject this_JoinSources_0 = null;

        EObject this_MergeSources_1 = null;

        EObject this_SimpleSource_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:2281:2: ( (this_JoinSources_0= ruleJoinSources | this_MergeSources_1= ruleMergeSources | this_SimpleSource_2= ruleSimpleSource ) )
            // InternalThingML.g:2282:2: (this_JoinSources_0= ruleJoinSources | this_MergeSources_1= ruleMergeSources | this_SimpleSource_2= ruleSimpleSource )
            {
            // InternalThingML.g:2282:2: (this_JoinSources_0= ruleJoinSources | this_MergeSources_1= ruleMergeSources | this_SimpleSource_2= ruleSimpleSource )
            int alt50=3;
            switch ( input.LA(1) ) {
            case 50:
                {
                alt50=1;
                }
                break;
            case 54:
                {
                alt50=2;
                }
                break;
            case RULE_ID:
                {
                alt50=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }

            switch (alt50) {
                case 1 :
                    // InternalThingML.g:2283:3: this_JoinSources_0= ruleJoinSources
                    {

                    			newCompositeNode(grammarAccess.getSourceAccess().getJoinSourcesParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_JoinSources_0=ruleJoinSources();

                    state._fsp--;


                    			current = this_JoinSources_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalThingML.g:2292:3: this_MergeSources_1= ruleMergeSources
                    {

                    			newCompositeNode(grammarAccess.getSourceAccess().getMergeSourcesParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_MergeSources_1=ruleMergeSources();

                    state._fsp--;


                    			current = this_MergeSources_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalThingML.g:2301:3: this_SimpleSource_2= ruleSimpleSource
                    {

                    			newCompositeNode(grammarAccess.getSourceAccess().getSimpleSourceParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_SimpleSource_2=ruleSimpleSource();

                    state._fsp--;


                    			current = this_SimpleSource_2;
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
    // $ANTLR end "ruleSource"


    // $ANTLR start "entryRuleViewSource"
    // InternalThingML.g:2313:1: entryRuleViewSource returns [EObject current=null] : iv_ruleViewSource= ruleViewSource EOF ;
    public final EObject entryRuleViewSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleViewSource = null;


        try {
            // InternalThingML.g:2313:51: (iv_ruleViewSource= ruleViewSource EOF )
            // InternalThingML.g:2314:2: iv_ruleViewSource= ruleViewSource EOF
            {
             newCompositeNode(grammarAccess.getViewSourceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleViewSource=ruleViewSource();

            state._fsp--;

             current =iv_ruleViewSource; 
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
    // $ANTLR end "entryRuleViewSource"


    // $ANTLR start "ruleViewSource"
    // InternalThingML.g:2320:1: ruleViewSource returns [EObject current=null] : (this_Filter_0= ruleFilter | this_LengthWindow_1= ruleLengthWindow | this_TimeWindow_2= ruleTimeWindow ) ;
    public final EObject ruleViewSource() throws RecognitionException {
        EObject current = null;

        EObject this_Filter_0 = null;

        EObject this_LengthWindow_1 = null;

        EObject this_TimeWindow_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:2326:2: ( (this_Filter_0= ruleFilter | this_LengthWindow_1= ruleLengthWindow | this_TimeWindow_2= ruleTimeWindow ) )
            // InternalThingML.g:2327:2: (this_Filter_0= ruleFilter | this_LengthWindow_1= ruleLengthWindow | this_TimeWindow_2= ruleTimeWindow )
            {
            // InternalThingML.g:2327:2: (this_Filter_0= ruleFilter | this_LengthWindow_1= ruleLengthWindow | this_TimeWindow_2= ruleTimeWindow )
            int alt51=3;
            switch ( input.LA(1) ) {
            case 56:
                {
                alt51=1;
                }
                break;
            case 58:
                {
                alt51=2;
                }
                break;
            case 60:
                {
                alt51=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }

            switch (alt51) {
                case 1 :
                    // InternalThingML.g:2328:3: this_Filter_0= ruleFilter
                    {

                    			newCompositeNode(grammarAccess.getViewSourceAccess().getFilterParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Filter_0=ruleFilter();

                    state._fsp--;


                    			current = this_Filter_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalThingML.g:2337:3: this_LengthWindow_1= ruleLengthWindow
                    {

                    			newCompositeNode(grammarAccess.getViewSourceAccess().getLengthWindowParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_LengthWindow_1=ruleLengthWindow();

                    state._fsp--;


                    			current = this_LengthWindow_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalThingML.g:2346:3: this_TimeWindow_2= ruleTimeWindow
                    {

                    			newCompositeNode(grammarAccess.getViewSourceAccess().getTimeWindowParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_TimeWindow_2=ruleTimeWindow();

                    state._fsp--;


                    			current = this_TimeWindow_2;
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
    // $ANTLR end "ruleViewSource"


    // $ANTLR start "entryRuleElmtProperty"
    // InternalThingML.g:2358:1: entryRuleElmtProperty returns [EObject current=null] : iv_ruleElmtProperty= ruleElmtProperty EOF ;
    public final EObject entryRuleElmtProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleElmtProperty = null;


        try {
            // InternalThingML.g:2358:53: (iv_ruleElmtProperty= ruleElmtProperty EOF )
            // InternalThingML.g:2359:2: iv_ruleElmtProperty= ruleElmtProperty EOF
            {
             newCompositeNode(grammarAccess.getElmtPropertyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleElmtProperty=ruleElmtProperty();

            state._fsp--;

             current =iv_ruleElmtProperty; 
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
    // $ANTLR end "entryRuleElmtProperty"


    // $ANTLR start "ruleElmtProperty"
    // InternalThingML.g:2365:1: ruleElmtProperty returns [EObject current=null] : (this_SimpleParamRef_0= ruleSimpleParamRef | this_ArrayParamRef_1= ruleArrayParamRef | this_LengthArray_2= ruleLengthArray ) ;
    public final EObject ruleElmtProperty() throws RecognitionException {
        EObject current = null;

        EObject this_SimpleParamRef_0 = null;

        EObject this_ArrayParamRef_1 = null;

        EObject this_LengthArray_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:2371:2: ( (this_SimpleParamRef_0= ruleSimpleParamRef | this_ArrayParamRef_1= ruleArrayParamRef | this_LengthArray_2= ruleLengthArray ) )
            // InternalThingML.g:2372:2: (this_SimpleParamRef_0= ruleSimpleParamRef | this_ArrayParamRef_1= ruleArrayParamRef | this_LengthArray_2= ruleLengthArray )
            {
            // InternalThingML.g:2372:2: (this_SimpleParamRef_0= ruleSimpleParamRef | this_ArrayParamRef_1= ruleArrayParamRef | this_LengthArray_2= ruleLengthArray )
            int alt52=3;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==RULE_ID) ) {
                int LA52_1 = input.LA(2);

                if ( (LA52_1==15) ) {
                    int LA52_3 = input.LA(3);

                    if ( ((LA52_3>=RULE_STRING_LIT && LA52_3<=RULE_FLOAT)||LA52_3==33||LA52_3==98||(LA52_3>=102 && LA52_3<=104)) ) {
                        alt52=1;
                    }
                    else if ( (LA52_3==16) ) {
                        alt52=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 52, 3, input);

                        throw nvae;
                    }
                }
                else if ( (LA52_1==EOF||LA52_1==RULE_ID||LA52_1==RULE_STRING_EXT||LA52_1==14||LA52_1==16||(LA52_1>=18 && LA52_1<=19)||LA52_1==24||(LA52_1>=28 && LA52_1<=29)||LA52_1==32||LA52_1==34||(LA52_1>=36 && LA52_1<=40)||(LA52_1>=44 && LA52_1<=46)||(LA52_1>=48 && LA52_1<=49)||(LA52_1>=51 && LA52_1<=53)||LA52_1==55||LA52_1==57||LA52_1==59||LA52_1==62||LA52_1==66||(LA52_1>=69 && LA52_1<=74)||LA52_1==77||(LA52_1>=79 && LA52_1<=81)||(LA52_1>=85 && LA52_1<=101)||(LA52_1>=107 && LA52_1<=108)) ) {
                    alt52=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 52, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA52_0==61) ) {
                alt52=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // InternalThingML.g:2373:3: this_SimpleParamRef_0= ruleSimpleParamRef
                    {

                    			newCompositeNode(grammarAccess.getElmtPropertyAccess().getSimpleParamRefParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_SimpleParamRef_0=ruleSimpleParamRef();

                    state._fsp--;


                    			current = this_SimpleParamRef_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalThingML.g:2382:3: this_ArrayParamRef_1= ruleArrayParamRef
                    {

                    			newCompositeNode(grammarAccess.getElmtPropertyAccess().getArrayParamRefParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_ArrayParamRef_1=ruleArrayParamRef();

                    state._fsp--;


                    			current = this_ArrayParamRef_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalThingML.g:2391:3: this_LengthArray_2= ruleLengthArray
                    {

                    			newCompositeNode(grammarAccess.getElmtPropertyAccess().getLengthArrayParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_LengthArray_2=ruleLengthArray();

                    state._fsp--;


                    			current = this_LengthArray_2;
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
    // $ANTLR end "ruleElmtProperty"


    // $ANTLR start "entryRuleJoinSources"
    // InternalThingML.g:2403:1: entryRuleJoinSources returns [EObject current=null] : iv_ruleJoinSources= ruleJoinSources EOF ;
    public final EObject entryRuleJoinSources() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJoinSources = null;


        try {
            // InternalThingML.g:2403:52: (iv_ruleJoinSources= ruleJoinSources EOF )
            // InternalThingML.g:2404:2: iv_ruleJoinSources= ruleJoinSources EOF
            {
             newCompositeNode(grammarAccess.getJoinSourcesRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJoinSources=ruleJoinSources();

            state._fsp--;

             current =iv_ruleJoinSources; 
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
    // $ANTLR end "entryRuleJoinSources"


    // $ANTLR start "ruleJoinSources"
    // InternalThingML.g:2410:1: ruleJoinSources returns [EObject current=null] : (otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )* ) ;
    public final EObject ruleJoinSources() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        EObject lv_sources_4_0 = null;

        EObject lv_sources_6_0 = null;

        EObject lv_rules_10_0 = null;

        EObject lv_rules_12_0 = null;

        EObject lv_operators_16_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2416:2: ( (otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )* ) )
            // InternalThingML.g:2417:2: (otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )* )
            {
            // InternalThingML.g:2417:2: (otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )* )
            // InternalThingML.g:2418:3: otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )*
            {
            otherlv_0=(Token)match(input,50,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getJoinSourcesAccess().getJoinKeyword_0());
            		
            // InternalThingML.g:2422:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:2423:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:2423:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:2424:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_29); 

            					newLeafNode(lv_name_1_0, grammarAccess.getJoinSourcesAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getJoinSourcesRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,35,FOLLOW_39); 

            			newLeafNode(otherlv_2, grammarAccess.getJoinSourcesAccess().getColonKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_36); 

            			newLeafNode(otherlv_3, grammarAccess.getJoinSourcesAccess().getLeftSquareBracketKeyword_3());
            		
            // InternalThingML.g:2448:3: ( (lv_sources_4_0= ruleSource ) )
            // InternalThingML.g:2449:4: (lv_sources_4_0= ruleSource )
            {
            // InternalThingML.g:2449:4: (lv_sources_4_0= ruleSource )
            // InternalThingML.g:2450:5: lv_sources_4_0= ruleSource
            {

            					newCompositeNode(grammarAccess.getJoinSourcesAccess().getSourcesSourceParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_40);
            lv_sources_4_0=ruleSource();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getJoinSourcesRule());
            					}
            					add(
            						current,
            						"sources",
            						lv_sources_4_0,
            						"org.thingml.xtext.ThingML.Source");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:2467:3: (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==51) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalThingML.g:2468:4: otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) )
            	    {
            	    otherlv_5=(Token)match(input,51,FOLLOW_36); 

            	    				newLeafNode(otherlv_5, grammarAccess.getJoinSourcesAccess().getAmpersandKeyword_5_0());
            	    			
            	    // InternalThingML.g:2472:4: ( (lv_sources_6_0= ruleSource ) )
            	    // InternalThingML.g:2473:5: (lv_sources_6_0= ruleSource )
            	    {
            	    // InternalThingML.g:2473:5: (lv_sources_6_0= ruleSource )
            	    // InternalThingML.g:2474:6: lv_sources_6_0= ruleSource
            	    {

            	    						newCompositeNode(grammarAccess.getJoinSourcesAccess().getSourcesSourceParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_40);
            	    lv_sources_6_0=ruleSource();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getJoinSourcesRule());
            	    						}
            	    						add(
            	    							current,
            	    							"sources",
            	    							lv_sources_6_0,
            	    							"org.thingml.xtext.ThingML.Source");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);

            otherlv_7=(Token)match(input,52,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getJoinSourcesAccess().getHyphenMinusGreaterThanSignKeyword_6());
            		
            // InternalThingML.g:2496:3: ( (otherlv_8= RULE_ID ) )
            // InternalThingML.g:2497:4: (otherlv_8= RULE_ID )
            {
            // InternalThingML.g:2497:4: (otherlv_8= RULE_ID )
            // InternalThingML.g:2498:5: otherlv_8= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getJoinSourcesRule());
            					}
            				
            otherlv_8=(Token)match(input,RULE_ID,FOLLOW_24); 

            					newLeafNode(otherlv_8, grammarAccess.getJoinSourcesAccess().getResultMessageMessageCrossReference_7_0());
            				

            }


            }

            otherlv_9=(Token)match(input,33,FOLLOW_22); 

            			newLeafNode(otherlv_9, grammarAccess.getJoinSourcesAccess().getLeftParenthesisKeyword_8());
            		
            // InternalThingML.g:2513:3: ( (lv_rules_10_0= ruleExpression ) )
            // InternalThingML.g:2514:4: (lv_rules_10_0= ruleExpression )
            {
            // InternalThingML.g:2514:4: (lv_rules_10_0= ruleExpression )
            // InternalThingML.g:2515:5: lv_rules_10_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getJoinSourcesAccess().getRulesExpressionParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_26);
            lv_rules_10_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getJoinSourcesRule());
            					}
            					add(
            						current,
            						"rules",
            						lv_rules_10_0,
            						"org.thingml.xtext.ThingML.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:2532:3: (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==28) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalThingML.g:2533:4: otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) )
            	    {
            	    otherlv_11=(Token)match(input,28,FOLLOW_22); 

            	    				newLeafNode(otherlv_11, grammarAccess.getJoinSourcesAccess().getCommaKeyword_10_0());
            	    			
            	    // InternalThingML.g:2537:4: ( (lv_rules_12_0= ruleExpression ) )
            	    // InternalThingML.g:2538:5: (lv_rules_12_0= ruleExpression )
            	    {
            	    // InternalThingML.g:2538:5: (lv_rules_12_0= ruleExpression )
            	    // InternalThingML.g:2539:6: lv_rules_12_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getJoinSourcesAccess().getRulesExpressionParserRuleCall_10_1_0());
            	    					
            	    pushFollow(FOLLOW_26);
            	    lv_rules_12_0=ruleExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getJoinSourcesRule());
            	    						}
            	    						add(
            	    							current,
            	    							"rules",
            	    							lv_rules_12_0,
            	    							"org.thingml.xtext.ThingML.Expression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop54;
                }
            } while (true);

            otherlv_13=(Token)match(input,34,FOLLOW_9); 

            			newLeafNode(otherlv_13, grammarAccess.getJoinSourcesAccess().getRightParenthesisKeyword_11());
            		
            otherlv_14=(Token)match(input,16,FOLLOW_41); 

            			newLeafNode(otherlv_14, grammarAccess.getJoinSourcesAccess().getRightSquareBracketKeyword_12());
            		
            // InternalThingML.g:2565:3: (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==53) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalThingML.g:2566:4: otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) )
            	    {
            	    otherlv_15=(Token)match(input,53,FOLLOW_42); 

            	    				newLeafNode(otherlv_15, grammarAccess.getJoinSourcesAccess().getColonColonKeyword_13_0());
            	    			
            	    // InternalThingML.g:2570:4: ( (lv_operators_16_0= ruleViewSource ) )
            	    // InternalThingML.g:2571:5: (lv_operators_16_0= ruleViewSource )
            	    {
            	    // InternalThingML.g:2571:5: (lv_operators_16_0= ruleViewSource )
            	    // InternalThingML.g:2572:6: lv_operators_16_0= ruleViewSource
            	    {

            	    						newCompositeNode(grammarAccess.getJoinSourcesAccess().getOperatorsViewSourceParserRuleCall_13_1_0());
            	    					
            	    pushFollow(FOLLOW_41);
            	    lv_operators_16_0=ruleViewSource();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getJoinSourcesRule());
            	    						}
            	    						add(
            	    							current,
            	    							"operators",
            	    							lv_operators_16_0,
            	    							"org.thingml.xtext.ThingML.ViewSource");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop55;
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
    // $ANTLR end "ruleJoinSources"


    // $ANTLR start "entryRuleMergeSources"
    // InternalThingML.g:2594:1: entryRuleMergeSources returns [EObject current=null] : iv_ruleMergeSources= ruleMergeSources EOF ;
    public final EObject entryRuleMergeSources() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMergeSources = null;


        try {
            // InternalThingML.g:2594:53: (iv_ruleMergeSources= ruleMergeSources EOF )
            // InternalThingML.g:2595:2: iv_ruleMergeSources= ruleMergeSources EOF
            {
             newCompositeNode(grammarAccess.getMergeSourcesRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMergeSources=ruleMergeSources();

            state._fsp--;

             current =iv_ruleMergeSources; 
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
    // $ANTLR end "entryRuleMergeSources"


    // $ANTLR start "ruleMergeSources"
    // InternalThingML.g:2601:1: ruleMergeSources returns [EObject current=null] : (otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )* ) ;
    public final EObject ruleMergeSources() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        EObject lv_sources_4_0 = null;

        EObject lv_sources_6_0 = null;

        EObject lv_operators_11_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2607:2: ( (otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )* ) )
            // InternalThingML.g:2608:2: (otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )* )
            {
            // InternalThingML.g:2608:2: (otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )* )
            // InternalThingML.g:2609:3: otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )*
            {
            otherlv_0=(Token)match(input,54,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getMergeSourcesAccess().getMergeKeyword_0());
            		
            // InternalThingML.g:2613:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:2614:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:2614:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:2615:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_29); 

            					newLeafNode(lv_name_1_0, grammarAccess.getMergeSourcesAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMergeSourcesRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,35,FOLLOW_39); 

            			newLeafNode(otherlv_2, grammarAccess.getMergeSourcesAccess().getColonKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_36); 

            			newLeafNode(otherlv_3, grammarAccess.getMergeSourcesAccess().getLeftSquareBracketKeyword_3());
            		
            // InternalThingML.g:2639:3: ( (lv_sources_4_0= ruleSource ) )
            // InternalThingML.g:2640:4: (lv_sources_4_0= ruleSource )
            {
            // InternalThingML.g:2640:4: (lv_sources_4_0= ruleSource )
            // InternalThingML.g:2641:5: lv_sources_4_0= ruleSource
            {

            					newCompositeNode(grammarAccess.getMergeSourcesAccess().getSourcesSourceParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_43);
            lv_sources_4_0=ruleSource();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMergeSourcesRule());
            					}
            					add(
            						current,
            						"sources",
            						lv_sources_4_0,
            						"org.thingml.xtext.ThingML.Source");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:2658:3: (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==55) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // InternalThingML.g:2659:4: otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) )
            	    {
            	    otherlv_5=(Token)match(input,55,FOLLOW_36); 

            	    				newLeafNode(otherlv_5, grammarAccess.getMergeSourcesAccess().getVerticalLineKeyword_5_0());
            	    			
            	    // InternalThingML.g:2663:4: ( (lv_sources_6_0= ruleSource ) )
            	    // InternalThingML.g:2664:5: (lv_sources_6_0= ruleSource )
            	    {
            	    // InternalThingML.g:2664:5: (lv_sources_6_0= ruleSource )
            	    // InternalThingML.g:2665:6: lv_sources_6_0= ruleSource
            	    {

            	    						newCompositeNode(grammarAccess.getMergeSourcesAccess().getSourcesSourceParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_43);
            	    lv_sources_6_0=ruleSource();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getMergeSourcesRule());
            	    						}
            	    						add(
            	    							current,
            	    							"sources",
            	    							lv_sources_6_0,
            	    							"org.thingml.xtext.ThingML.Source");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);

            otherlv_7=(Token)match(input,52,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getMergeSourcesAccess().getHyphenMinusGreaterThanSignKeyword_6());
            		
            // InternalThingML.g:2687:3: ( (otherlv_8= RULE_ID ) )
            // InternalThingML.g:2688:4: (otherlv_8= RULE_ID )
            {
            // InternalThingML.g:2688:4: (otherlv_8= RULE_ID )
            // InternalThingML.g:2689:5: otherlv_8= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMergeSourcesRule());
            					}
            				
            otherlv_8=(Token)match(input,RULE_ID,FOLLOW_9); 

            					newLeafNode(otherlv_8, grammarAccess.getMergeSourcesAccess().getResultMessageMessageCrossReference_7_0());
            				

            }


            }

            otherlv_9=(Token)match(input,16,FOLLOW_41); 

            			newLeafNode(otherlv_9, grammarAccess.getMergeSourcesAccess().getRightSquareBracketKeyword_8());
            		
            // InternalThingML.g:2704:3: (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==53) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // InternalThingML.g:2705:4: otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) )
            	    {
            	    otherlv_10=(Token)match(input,53,FOLLOW_42); 

            	    				newLeafNode(otherlv_10, grammarAccess.getMergeSourcesAccess().getColonColonKeyword_9_0());
            	    			
            	    // InternalThingML.g:2709:4: ( (lv_operators_11_0= ruleViewSource ) )
            	    // InternalThingML.g:2710:5: (lv_operators_11_0= ruleViewSource )
            	    {
            	    // InternalThingML.g:2710:5: (lv_operators_11_0= ruleViewSource )
            	    // InternalThingML.g:2711:6: lv_operators_11_0= ruleViewSource
            	    {

            	    						newCompositeNode(grammarAccess.getMergeSourcesAccess().getOperatorsViewSourceParserRuleCall_9_1_0());
            	    					
            	    pushFollow(FOLLOW_41);
            	    lv_operators_11_0=ruleViewSource();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getMergeSourcesRule());
            	    						}
            	    						add(
            	    							current,
            	    							"operators",
            	    							lv_operators_11_0,
            	    							"org.thingml.xtext.ThingML.ViewSource");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop57;
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
    // $ANTLR end "ruleMergeSources"


    // $ANTLR start "entryRuleSimpleSource"
    // InternalThingML.g:2733:1: entryRuleSimpleSource returns [EObject current=null] : iv_ruleSimpleSource= ruleSimpleSource EOF ;
    public final EObject entryRuleSimpleSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleSource = null;


        try {
            // InternalThingML.g:2733:53: (iv_ruleSimpleSource= ruleSimpleSource EOF )
            // InternalThingML.g:2734:2: iv_ruleSimpleSource= ruleSimpleSource EOF
            {
             newCompositeNode(grammarAccess.getSimpleSourceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSimpleSource=ruleSimpleSource();

            state._fsp--;

             current =iv_ruleSimpleSource; 
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
    // $ANTLR end "entryRuleSimpleSource"


    // $ANTLR start "ruleSimpleSource"
    // InternalThingML.g:2740:1: ruleSimpleSource returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )* ) ;
    public final EObject ruleSimpleSource() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_message_2_0 = null;

        EObject lv_operators_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2746:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )* ) )
            // InternalThingML.g:2747:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )* )
            {
            // InternalThingML.g:2747:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )* )
            // InternalThingML.g:2748:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )*
            {
            // InternalThingML.g:2748:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:2749:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:2749:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:2750:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_29); 

            					newLeafNode(lv_name_0_0, grammarAccess.getSimpleSourceAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSimpleSourceRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_1=(Token)match(input,35,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getSimpleSourceAccess().getColonKeyword_1());
            		
            // InternalThingML.g:2770:3: ( (lv_message_2_0= ruleReceiveMessage ) )
            // InternalThingML.g:2771:4: (lv_message_2_0= ruleReceiveMessage )
            {
            // InternalThingML.g:2771:4: (lv_message_2_0= ruleReceiveMessage )
            // InternalThingML.g:2772:5: lv_message_2_0= ruleReceiveMessage
            {

            					newCompositeNode(grammarAccess.getSimpleSourceAccess().getMessageReceiveMessageParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_41);
            lv_message_2_0=ruleReceiveMessage();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSimpleSourceRule());
            					}
            					set(
            						current,
            						"message",
            						lv_message_2_0,
            						"org.thingml.xtext.ThingML.ReceiveMessage");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:2789:3: (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==53) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // InternalThingML.g:2790:4: otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) )
            	    {
            	    otherlv_3=(Token)match(input,53,FOLLOW_42); 

            	    				newLeafNode(otherlv_3, grammarAccess.getSimpleSourceAccess().getColonColonKeyword_3_0());
            	    			
            	    // InternalThingML.g:2794:4: ( (lv_operators_4_0= ruleViewSource ) )
            	    // InternalThingML.g:2795:5: (lv_operators_4_0= ruleViewSource )
            	    {
            	    // InternalThingML.g:2795:5: (lv_operators_4_0= ruleViewSource )
            	    // InternalThingML.g:2796:6: lv_operators_4_0= ruleViewSource
            	    {

            	    						newCompositeNode(grammarAccess.getSimpleSourceAccess().getOperatorsViewSourceParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_41);
            	    lv_operators_4_0=ruleViewSource();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getSimpleSourceRule());
            	    						}
            	    						add(
            	    							current,
            	    							"operators",
            	    							lv_operators_4_0,
            	    							"org.thingml.xtext.ThingML.ViewSource");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop58;
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
    // $ANTLR end "ruleSimpleSource"


    // $ANTLR start "entryRuleFilter"
    // InternalThingML.g:2818:1: entryRuleFilter returns [EObject current=null] : iv_ruleFilter= ruleFilter EOF ;
    public final EObject entryRuleFilter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFilter = null;


        try {
            // InternalThingML.g:2818:47: (iv_ruleFilter= ruleFilter EOF )
            // InternalThingML.g:2819:2: iv_ruleFilter= ruleFilter EOF
            {
             newCompositeNode(grammarAccess.getFilterRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFilter=ruleFilter();

            state._fsp--;

             current =iv_ruleFilter; 
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
    // $ANTLR end "entryRuleFilter"


    // $ANTLR start "ruleFilter"
    // InternalThingML.g:2825:1: ruleFilter returns [EObject current=null] : (otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) ) ) ;
    public final EObject ruleFilter() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_guard_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2831:2: ( (otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) ) ) )
            // InternalThingML.g:2832:2: (otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) ) )
            {
            // InternalThingML.g:2832:2: (otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) ) )
            // InternalThingML.g:2833:3: otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,56,FOLLOW_44); 

            			newLeafNode(otherlv_0, grammarAccess.getFilterAccess().getKeepKeyword_0());
            		
            otherlv_1=(Token)match(input,57,FOLLOW_22); 

            			newLeafNode(otherlv_1, grammarAccess.getFilterAccess().getIfKeyword_1());
            		
            // InternalThingML.g:2841:3: ( (lv_guard_2_0= ruleExpression ) )
            // InternalThingML.g:2842:4: (lv_guard_2_0= ruleExpression )
            {
            // InternalThingML.g:2842:4: (lv_guard_2_0= ruleExpression )
            // InternalThingML.g:2843:5: lv_guard_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getFilterAccess().getGuardExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_guard_2_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFilterRule());
            					}
            					set(
            						current,
            						"guard",
            						lv_guard_2_0,
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
    // $ANTLR end "ruleFilter"


    // $ANTLR start "entryRuleLengthWindow"
    // InternalThingML.g:2864:1: entryRuleLengthWindow returns [EObject current=null] : iv_ruleLengthWindow= ruleLengthWindow EOF ;
    public final EObject entryRuleLengthWindow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLengthWindow = null;


        try {
            // InternalThingML.g:2864:53: (iv_ruleLengthWindow= ruleLengthWindow EOF )
            // InternalThingML.g:2865:2: iv_ruleLengthWindow= ruleLengthWindow EOF
            {
             newCompositeNode(grammarAccess.getLengthWindowRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLengthWindow=ruleLengthWindow();

            state._fsp--;

             current =iv_ruleLengthWindow; 
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
    // $ANTLR end "entryRuleLengthWindow"


    // $ANTLR start "ruleLengthWindow"
    // InternalThingML.g:2871:1: ruleLengthWindow returns [EObject current=null] : (otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? ) ;
    public final EObject ruleLengthWindow() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_size_1_0 = null;

        EObject lv_step_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2877:2: ( (otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? ) )
            // InternalThingML.g:2878:2: (otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? )
            {
            // InternalThingML.g:2878:2: (otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? )
            // InternalThingML.g:2879:3: otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )?
            {
            otherlv_0=(Token)match(input,58,FOLLOW_22); 

            			newLeafNode(otherlv_0, grammarAccess.getLengthWindowAccess().getBufferKeyword_0());
            		
            // InternalThingML.g:2883:3: ( (lv_size_1_0= ruleExpression ) )
            // InternalThingML.g:2884:4: (lv_size_1_0= ruleExpression )
            {
            // InternalThingML.g:2884:4: (lv_size_1_0= ruleExpression )
            // InternalThingML.g:2885:5: lv_size_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getLengthWindowAccess().getSizeExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_45);
            lv_size_1_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLengthWindowRule());
            					}
            					set(
            						current,
            						"size",
            						lv_size_1_0,
            						"org.thingml.xtext.ThingML.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:2902:3: (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==59) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalThingML.g:2903:4: otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) )
                    {
                    otherlv_2=(Token)match(input,59,FOLLOW_22); 

                    				newLeafNode(otherlv_2, grammarAccess.getLengthWindowAccess().getByKeyword_2_0());
                    			
                    // InternalThingML.g:2907:4: ( (lv_step_3_0= ruleExpression ) )
                    // InternalThingML.g:2908:5: (lv_step_3_0= ruleExpression )
                    {
                    // InternalThingML.g:2908:5: (lv_step_3_0= ruleExpression )
                    // InternalThingML.g:2909:6: lv_step_3_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getLengthWindowAccess().getStepExpressionParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_step_3_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLengthWindowRule());
                    						}
                    						set(
                    							current,
                    							"step",
                    							lv_step_3_0,
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
    // $ANTLR end "ruleLengthWindow"


    // $ANTLR start "entryRuleTimeWindow"
    // InternalThingML.g:2931:1: entryRuleTimeWindow returns [EObject current=null] : iv_ruleTimeWindow= ruleTimeWindow EOF ;
    public final EObject entryRuleTimeWindow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTimeWindow = null;


        try {
            // InternalThingML.g:2931:51: (iv_ruleTimeWindow= ruleTimeWindow EOF )
            // InternalThingML.g:2932:2: iv_ruleTimeWindow= ruleTimeWindow EOF
            {
             newCompositeNode(grammarAccess.getTimeWindowRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTimeWindow=ruleTimeWindow();

            state._fsp--;

             current =iv_ruleTimeWindow; 
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
    // $ANTLR end "entryRuleTimeWindow"


    // $ANTLR start "ruleTimeWindow"
    // InternalThingML.g:2938:1: ruleTimeWindow returns [EObject current=null] : (otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? ) ;
    public final EObject ruleTimeWindow() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_duration_1_0 = null;

        EObject lv_step_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2944:2: ( (otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? ) )
            // InternalThingML.g:2945:2: (otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? )
            {
            // InternalThingML.g:2945:2: (otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? )
            // InternalThingML.g:2946:3: otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )?
            {
            otherlv_0=(Token)match(input,60,FOLLOW_22); 

            			newLeafNode(otherlv_0, grammarAccess.getTimeWindowAccess().getDuringKeyword_0());
            		
            // InternalThingML.g:2950:3: ( (lv_duration_1_0= ruleExpression ) )
            // InternalThingML.g:2951:4: (lv_duration_1_0= ruleExpression )
            {
            // InternalThingML.g:2951:4: (lv_duration_1_0= ruleExpression )
            // InternalThingML.g:2952:5: lv_duration_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getTimeWindowAccess().getDurationExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_45);
            lv_duration_1_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTimeWindowRule());
            					}
            					set(
            						current,
            						"duration",
            						lv_duration_1_0,
            						"org.thingml.xtext.ThingML.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalThingML.g:2969:3: (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==59) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // InternalThingML.g:2970:4: otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) )
                    {
                    otherlv_2=(Token)match(input,59,FOLLOW_22); 

                    				newLeafNode(otherlv_2, grammarAccess.getTimeWindowAccess().getByKeyword_2_0());
                    			
                    // InternalThingML.g:2974:4: ( (lv_step_3_0= ruleExpression ) )
                    // InternalThingML.g:2975:5: (lv_step_3_0= ruleExpression )
                    {
                    // InternalThingML.g:2975:5: (lv_step_3_0= ruleExpression )
                    // InternalThingML.g:2976:6: lv_step_3_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getTimeWindowAccess().getStepExpressionParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_step_3_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTimeWindowRule());
                    						}
                    						set(
                    							current,
                    							"step",
                    							lv_step_3_0,
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
    // $ANTLR end "ruleTimeWindow"


    // $ANTLR start "entryRuleMessageParameter"
    // InternalThingML.g:2998:1: entryRuleMessageParameter returns [EObject current=null] : iv_ruleMessageParameter= ruleMessageParameter EOF ;
    public final EObject entryRuleMessageParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageParameter = null;


        try {
            // InternalThingML.g:2998:57: (iv_ruleMessageParameter= ruleMessageParameter EOF )
            // InternalThingML.g:2999:2: iv_ruleMessageParameter= ruleMessageParameter EOF
            {
             newCompositeNode(grammarAccess.getMessageParameterRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMessageParameter=ruleMessageParameter();

            state._fsp--;

             current =iv_ruleMessageParameter; 
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
    // $ANTLR end "entryRuleMessageParameter"


    // $ANTLR start "ruleMessageParameter"
    // InternalThingML.g:3005:1: ruleMessageParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleMessageParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:3011:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalThingML.g:3012:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalThingML.g:3012:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) )
            // InternalThingML.g:3013:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) )
            {
            // InternalThingML.g:3013:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:3014:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:3014:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:3015:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_29); 

            					newLeafNode(lv_name_0_0, grammarAccess.getMessageParameterAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMessageParameterRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_1=(Token)match(input,35,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getMessageParameterAccess().getColonKeyword_1());
            		
            // InternalThingML.g:3035:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:3036:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:3036:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:3037:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMessageParameterRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_2, grammarAccess.getMessageParameterAccess().getMsgRefMessageCrossReference_2_0());
            				

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
    // $ANTLR end "ruleMessageParameter"


    // $ANTLR start "entryRuleSimpleParamRef"
    // InternalThingML.g:3052:1: entryRuleSimpleParamRef returns [EObject current=null] : iv_ruleSimpleParamRef= ruleSimpleParamRef EOF ;
    public final EObject entryRuleSimpleParamRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleParamRef = null;


        try {
            // InternalThingML.g:3052:55: (iv_ruleSimpleParamRef= ruleSimpleParamRef EOF )
            // InternalThingML.g:3053:2: iv_ruleSimpleParamRef= ruleSimpleParamRef EOF
            {
             newCompositeNode(grammarAccess.getSimpleParamRefRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSimpleParamRef=ruleSimpleParamRef();

            state._fsp--;

             current =iv_ruleSimpleParamRef; 
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
    // $ANTLR end "entryRuleSimpleParamRef"


    // $ANTLR start "ruleSimpleParamRef"
    // InternalThingML.g:3059:1: ruleSimpleParamRef returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleSimpleParamRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalThingML.g:3065:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalThingML.g:3066:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalThingML.g:3066:2: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:3067:3: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:3067:3: (otherlv_0= RULE_ID )
            // InternalThingML.g:3068:4: otherlv_0= RULE_ID
            {

            				if (current==null) {
            					current = createModelElement(grammarAccess.getSimpleParamRefRule());
            				}
            			
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(otherlv_0, grammarAccess.getSimpleParamRefAccess().getParameterRefParameterCrossReference_0());
            			

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
    // $ANTLR end "ruleSimpleParamRef"


    // $ANTLR start "entryRuleArrayParamRef"
    // InternalThingML.g:3082:1: entryRuleArrayParamRef returns [EObject current=null] : iv_ruleArrayParamRef= ruleArrayParamRef EOF ;
    public final EObject entryRuleArrayParamRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayParamRef = null;


        try {
            // InternalThingML.g:3082:54: (iv_ruleArrayParamRef= ruleArrayParamRef EOF )
            // InternalThingML.g:3083:2: iv_ruleArrayParamRef= ruleArrayParamRef EOF
            {
             newCompositeNode(grammarAccess.getArrayParamRefRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleArrayParamRef=ruleArrayParamRef();

            state._fsp--;

             current =iv_ruleArrayParamRef; 
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
    // $ANTLR end "entryRuleArrayParamRef"


    // $ANTLR start "ruleArrayParamRef"
    // InternalThingML.g:3089:1: ruleArrayParamRef returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']' ) ;
    public final EObject ruleArrayParamRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:3095:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']' ) )
            // InternalThingML.g:3096:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']' )
            {
            // InternalThingML.g:3096:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']' )
            // InternalThingML.g:3097:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']'
            {
            // InternalThingML.g:3097:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:3098:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:3098:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:3099:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getArrayParamRefRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_39); 

            					newLeafNode(otherlv_0, grammarAccess.getArrayParamRefAccess().getParameterRefParameterCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,15,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getArrayParamRefAccess().getLeftSquareBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getArrayParamRefAccess().getRightSquareBracketKeyword_2());
            		

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
    // $ANTLR end "ruleArrayParamRef"


    // $ANTLR start "entryRuleLengthArray"
    // InternalThingML.g:3122:1: entryRuleLengthArray returns [EObject current=null] : iv_ruleLengthArray= ruleLengthArray EOF ;
    public final EObject entryRuleLengthArray() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLengthArray = null;


        try {
            // InternalThingML.g:3122:52: (iv_ruleLengthArray= ruleLengthArray EOF )
            // InternalThingML.g:3123:2: iv_ruleLengthArray= ruleLengthArray EOF
            {
             newCompositeNode(grammarAccess.getLengthArrayRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLengthArray=ruleLengthArray();

            state._fsp--;

             current =iv_ruleLengthArray; 
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
    // $ANTLR end "entryRuleLengthArray"


    // $ANTLR start "ruleLengthArray"
    // InternalThingML.g:3129:1: ruleLengthArray returns [EObject current=null] : ( () otherlv_1= 'length' ) ;
    public final EObject ruleLengthArray() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:3135:2: ( ( () otherlv_1= 'length' ) )
            // InternalThingML.g:3136:2: ( () otherlv_1= 'length' )
            {
            // InternalThingML.g:3136:2: ( () otherlv_1= 'length' )
            // InternalThingML.g:3137:3: () otherlv_1= 'length'
            {
            // InternalThingML.g:3137:3: ()
            // InternalThingML.g:3138:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getLengthArrayAccess().getLengthArrayAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,61,FOLLOW_2); 

            			newLeafNode(otherlv_1, grammarAccess.getLengthArrayAccess().getLengthKeyword_1());
            		

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
    // $ANTLR end "ruleLengthArray"


    // $ANTLR start "entryRuleStateMachine"
    // InternalThingML.g:3152:1: entryRuleStateMachine returns [EObject current=null] : iv_ruleStateMachine= ruleStateMachine EOF ;
    public final EObject entryRuleStateMachine() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStateMachine = null;


        try {
            // InternalThingML.g:3152:53: (iv_ruleStateMachine= ruleStateMachine EOF )
            // InternalThingML.g:3153:2: iv_ruleStateMachine= ruleStateMachine EOF
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
    // InternalThingML.g:3159:1: ruleStateMachine returns [EObject current=null] : (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' ) ;
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
        Token otherlv_18=null;
        EObject lv_annotations_6_0 = null;

        EObject lv_properties_8_0 = null;

        EObject lv_entry_11_0 = null;

        EObject lv_exit_14_0 = null;

        EObject lv_substate_15_0 = null;

        EObject lv_internal_16_0 = null;

        EObject lv_region_17_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3165:2: ( (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' ) )
            // InternalThingML.g:3166:2: (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' )
            {
            // InternalThingML.g:3166:2: (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' )
            // InternalThingML.g:3167:3: otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}'
            {
            otherlv_0=(Token)match(input,62,FOLLOW_46); 

            			newLeafNode(otherlv_0, grammarAccess.getStateMachineAccess().getStatechartKeyword_0());
            		
            // InternalThingML.g:3171:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==RULE_ID) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalThingML.g:3172:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:3172:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:3173:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_47); 

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

            otherlv_2=(Token)match(input,63,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getStateMachineAccess().getInitKeyword_2());
            		
            // InternalThingML.g:3193:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:3194:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:3194:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:3195:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStateMachineRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_48); 

            					newLeafNode(otherlv_3, grammarAccess.getStateMachineAccess().getInitialStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:3206:3: (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==64) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalThingML.g:3207:4: otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) )
                    {
                    otherlv_4=(Token)match(input,64,FOLLOW_49); 

                    				newLeafNode(otherlv_4, grammarAccess.getStateMachineAccess().getKeepsKeyword_4_0());
                    			
                    // InternalThingML.g:3211:4: ( (lv_history_5_0= 'history' ) )
                    // InternalThingML.g:3212:5: (lv_history_5_0= 'history' )
                    {
                    // InternalThingML.g:3212:5: (lv_history_5_0= 'history' )
                    // InternalThingML.g:3213:6: lv_history_5_0= 'history'
                    {
                    lv_history_5_0=(Token)match(input,65,FOLLOW_14); 

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

            // InternalThingML.g:3226:3: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==14) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // InternalThingML.g:3227:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3227:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3228:5: lv_annotations_6_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getStateMachineAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_14);
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
            	    break loop63;
                }
            } while (true);

            otherlv_7=(Token)match(input,23,FOLLOW_50); 

            			newLeafNode(otherlv_7, grammarAccess.getStateMachineAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:3249:3: ( (lv_properties_8_0= ruleProperty ) )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( ((LA64_0>=36 && LA64_0<=37)) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // InternalThingML.g:3250:4: (lv_properties_8_0= ruleProperty )
            	    {
            	    // InternalThingML.g:3250:4: (lv_properties_8_0= ruleProperty )
            	    // InternalThingML.g:3251:5: lv_properties_8_0= ruleProperty
            	    {

            	    					newCompositeNode(grammarAccess.getStateMachineAccess().getPropertiesPropertyParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_50);
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
            	    break loop64;
                }
            } while (true);

            // InternalThingML.g:3268:3: (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==66) ) {
                int LA65_1 = input.LA(2);

                if ( (LA65_1==67) ) {
                    alt65=1;
                }
            }
            switch (alt65) {
                case 1 :
                    // InternalThingML.g:3269:4: otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) )
                    {
                    otherlv_9=(Token)match(input,66,FOLLOW_51); 

                    				newLeafNode(otherlv_9, grammarAccess.getStateMachineAccess().getOnKeyword_8_0());
                    			
                    otherlv_10=(Token)match(input,67,FOLLOW_27); 

                    				newLeafNode(otherlv_10, grammarAccess.getStateMachineAccess().getEntryKeyword_8_1());
                    			
                    // InternalThingML.g:3277:4: ( (lv_entry_11_0= ruleAction ) )
                    // InternalThingML.g:3278:5: (lv_entry_11_0= ruleAction )
                    {
                    // InternalThingML.g:3278:5: (lv_entry_11_0= ruleAction )
                    // InternalThingML.g:3279:6: lv_entry_11_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getStateMachineAccess().getEntryActionParserRuleCall_8_2_0());
                    					
                    pushFollow(FOLLOW_52);
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

            // InternalThingML.g:3297:3: (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==66) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalThingML.g:3298:4: otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) )
                    {
                    otherlv_12=(Token)match(input,66,FOLLOW_53); 

                    				newLeafNode(otherlv_12, grammarAccess.getStateMachineAccess().getOnKeyword_9_0());
                    			
                    otherlv_13=(Token)match(input,68,FOLLOW_27); 

                    				newLeafNode(otherlv_13, grammarAccess.getStateMachineAccess().getExitKeyword_9_1());
                    			
                    // InternalThingML.g:3306:4: ( (lv_exit_14_0= ruleAction ) )
                    // InternalThingML.g:3307:5: (lv_exit_14_0= ruleAction )
                    {
                    // InternalThingML.g:3307:5: (lv_exit_14_0= ruleAction )
                    // InternalThingML.g:3308:6: lv_exit_14_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getStateMachineAccess().getExitActionParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_54);
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

            // InternalThingML.g:3326:3: ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )*
            loop67:
            do {
                int alt67=3;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==62||(LA67_0>=69 && LA67_0<=72)) ) {
                    alt67=1;
                }
                else if ( (LA67_0==45) ) {
                    alt67=2;
                }


                switch (alt67) {
            	case 1 :
            	    // InternalThingML.g:3327:4: ( (lv_substate_15_0= ruleState ) )
            	    {
            	    // InternalThingML.g:3327:4: ( (lv_substate_15_0= ruleState ) )
            	    // InternalThingML.g:3328:5: (lv_substate_15_0= ruleState )
            	    {
            	    // InternalThingML.g:3328:5: (lv_substate_15_0= ruleState )
            	    // InternalThingML.g:3329:6: lv_substate_15_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getSubstateStateParserRuleCall_10_0_0());
            	    					
            	    pushFollow(FOLLOW_54);
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
            	    // InternalThingML.g:3347:4: ( (lv_internal_16_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:3347:4: ( (lv_internal_16_0= ruleInternalTransition ) )
            	    // InternalThingML.g:3348:5: (lv_internal_16_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:3348:5: (lv_internal_16_0= ruleInternalTransition )
            	    // InternalThingML.g:3349:6: lv_internal_16_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getInternalInternalTransitionParserRuleCall_10_1_0());
            	    					
            	    pushFollow(FOLLOW_54);
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
            	    break loop67;
                }
            } while (true);

            // InternalThingML.g:3367:3: ( (lv_region_17_0= ruleParallelRegion ) )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==73) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // InternalThingML.g:3368:4: (lv_region_17_0= ruleParallelRegion )
            	    {
            	    // InternalThingML.g:3368:4: (lv_region_17_0= ruleParallelRegion )
            	    // InternalThingML.g:3369:5: lv_region_17_0= ruleParallelRegion
            	    {

            	    					newCompositeNode(grammarAccess.getStateMachineAccess().getRegionParallelRegionParserRuleCall_11_0());
            	    				
            	    pushFollow(FOLLOW_55);
            	    lv_region_17_0=ruleParallelRegion();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    					}
            	    					add(
            	    						current,
            	    						"region",
            	    						lv_region_17_0,
            	    						"org.thingml.xtext.ThingML.ParallelRegion");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop68;
                }
            } while (true);

            otherlv_18=(Token)match(input,24,FOLLOW_2); 

            			newLeafNode(otherlv_18, grammarAccess.getStateMachineAccess().getRightCurlyBracketKeyword_12());
            		

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


    // $ANTLR start "entryRuleFinalState"
    // InternalThingML.g:3394:1: entryRuleFinalState returns [EObject current=null] : iv_ruleFinalState= ruleFinalState EOF ;
    public final EObject entryRuleFinalState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFinalState = null;


        try {
            // InternalThingML.g:3394:51: (iv_ruleFinalState= ruleFinalState EOF )
            // InternalThingML.g:3395:2: iv_ruleFinalState= ruleFinalState EOF
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
    // InternalThingML.g:3401:1: ruleFinalState returns [EObject current=null] : (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}' ) ;
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
            // InternalThingML.g:3407:2: ( (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}' ) )
            // InternalThingML.g:3408:2: (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}' )
            {
            // InternalThingML.g:3408:2: (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}' )
            // InternalThingML.g:3409:3: otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,69,FOLLOW_56); 

            			newLeafNode(otherlv_0, grammarAccess.getFinalStateAccess().getFinalKeyword_0());
            		
            otherlv_1=(Token)match(input,70,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getFinalStateAccess().getStateKeyword_1());
            		
            // InternalThingML.g:3417:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:3418:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:3418:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:3419:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_14); 

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

            // InternalThingML.g:3435:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==14) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // InternalThingML.g:3436:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3436:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3437:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getFinalStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_14);
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
            	    break loop69;
                }
            } while (true);

            otherlv_4=(Token)match(input,23,FOLLOW_57); 

            			newLeafNode(otherlv_4, grammarAccess.getFinalStateAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalThingML.g:3458:3: (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==66) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // InternalThingML.g:3459:4: otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) )
                    {
                    otherlv_5=(Token)match(input,66,FOLLOW_51); 

                    				newLeafNode(otherlv_5, grammarAccess.getFinalStateAccess().getOnKeyword_5_0());
                    			
                    otherlv_6=(Token)match(input,67,FOLLOW_27); 

                    				newLeafNode(otherlv_6, grammarAccess.getFinalStateAccess().getEntryKeyword_5_1());
                    			
                    // InternalThingML.g:3467:4: ( (lv_entry_7_0= ruleAction ) )
                    // InternalThingML.g:3468:5: (lv_entry_7_0= ruleAction )
                    {
                    // InternalThingML.g:3468:5: (lv_entry_7_0= ruleAction )
                    // InternalThingML.g:3469:6: lv_entry_7_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getFinalStateAccess().getEntryActionParserRuleCall_5_2_0());
                    					
                    pushFollow(FOLLOW_58);
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

            otherlv_8=(Token)match(input,24,FOLLOW_2); 

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


    // $ANTLR start "entryRuleCompositeState"
    // InternalThingML.g:3495:1: entryRuleCompositeState returns [EObject current=null] : iv_ruleCompositeState= ruleCompositeState EOF ;
    public final EObject entryRuleCompositeState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCompositeState = null;


        try {
            // InternalThingML.g:3495:55: (iv_ruleCompositeState= ruleCompositeState EOF )
            // InternalThingML.g:3496:2: iv_ruleCompositeState= ruleCompositeState EOF
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
    // InternalThingML.g:3502:1: ruleCompositeState returns [EObject current=null] : (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' ) ;
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
        Token otherlv_20=null;
        EObject lv_annotations_7_0 = null;

        EObject lv_properties_9_0 = null;

        EObject lv_entry_12_0 = null;

        EObject lv_exit_15_0 = null;

        EObject lv_substate_16_0 = null;

        EObject lv_internal_17_0 = null;

        EObject lv_outgoing_18_0 = null;

        EObject lv_region_19_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3508:2: ( (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' ) )
            // InternalThingML.g:3509:2: (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' )
            {
            // InternalThingML.g:3509:2: (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' )
            // InternalThingML.g:3510:3: otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}'
            {
            otherlv_0=(Token)match(input,71,FOLLOW_56); 

            			newLeafNode(otherlv_0, grammarAccess.getCompositeStateAccess().getCompositeKeyword_0());
            		
            otherlv_1=(Token)match(input,70,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getCompositeStateAccess().getStateKeyword_1());
            		
            // InternalThingML.g:3518:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:3519:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:3519:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:3520:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_47); 

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

            otherlv_3=(Token)match(input,63,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getCompositeStateAccess().getInitKeyword_3());
            		
            // InternalThingML.g:3540:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:3541:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:3541:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:3542:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCompositeStateRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_48); 

            					newLeafNode(otherlv_4, grammarAccess.getCompositeStateAccess().getInitialStateCrossReference_4_0());
            				

            }


            }

            // InternalThingML.g:3553:3: (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==64) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalThingML.g:3554:4: otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) )
                    {
                    otherlv_5=(Token)match(input,64,FOLLOW_49); 

                    				newLeafNode(otherlv_5, grammarAccess.getCompositeStateAccess().getKeepsKeyword_5_0());
                    			
                    // InternalThingML.g:3558:4: ( (lv_history_6_0= 'history' ) )
                    // InternalThingML.g:3559:5: (lv_history_6_0= 'history' )
                    {
                    // InternalThingML.g:3559:5: (lv_history_6_0= 'history' )
                    // InternalThingML.g:3560:6: lv_history_6_0= 'history'
                    {
                    lv_history_6_0=(Token)match(input,65,FOLLOW_14); 

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

            // InternalThingML.g:3573:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( (LA72_0==14) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // InternalThingML.g:3574:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3574:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3575:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getCompositeStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_14);
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
            	    break loop72;
                }
            } while (true);

            otherlv_8=(Token)match(input,23,FOLLOW_59); 

            			newLeafNode(otherlv_8, grammarAccess.getCompositeStateAccess().getLeftCurlyBracketKeyword_7());
            		
            // InternalThingML.g:3596:3: ( (lv_properties_9_0= ruleProperty ) )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( ((LA73_0>=36 && LA73_0<=37)) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // InternalThingML.g:3597:4: (lv_properties_9_0= ruleProperty )
            	    {
            	    // InternalThingML.g:3597:4: (lv_properties_9_0= ruleProperty )
            	    // InternalThingML.g:3598:5: lv_properties_9_0= ruleProperty
            	    {

            	    					newCompositeNode(grammarAccess.getCompositeStateAccess().getPropertiesPropertyParserRuleCall_8_0());
            	    				
            	    pushFollow(FOLLOW_59);
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
            	    break loop73;
                }
            } while (true);

            // InternalThingML.g:3615:3: (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==66) ) {
                int LA74_1 = input.LA(2);

                if ( (LA74_1==67) ) {
                    alt74=1;
                }
            }
            switch (alt74) {
                case 1 :
                    // InternalThingML.g:3616:4: otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) )
                    {
                    otherlv_10=(Token)match(input,66,FOLLOW_51); 

                    				newLeafNode(otherlv_10, grammarAccess.getCompositeStateAccess().getOnKeyword_9_0());
                    			
                    otherlv_11=(Token)match(input,67,FOLLOW_27); 

                    				newLeafNode(otherlv_11, grammarAccess.getCompositeStateAccess().getEntryKeyword_9_1());
                    			
                    // InternalThingML.g:3624:4: ( (lv_entry_12_0= ruleAction ) )
                    // InternalThingML.g:3625:5: (lv_entry_12_0= ruleAction )
                    {
                    // InternalThingML.g:3625:5: (lv_entry_12_0= ruleAction )
                    // InternalThingML.g:3626:6: lv_entry_12_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getCompositeStateAccess().getEntryActionParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_60);
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

            // InternalThingML.g:3644:3: (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==66) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // InternalThingML.g:3645:4: otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) )
                    {
                    otherlv_13=(Token)match(input,66,FOLLOW_53); 

                    				newLeafNode(otherlv_13, grammarAccess.getCompositeStateAccess().getOnKeyword_10_0());
                    			
                    otherlv_14=(Token)match(input,68,FOLLOW_27); 

                    				newLeafNode(otherlv_14, grammarAccess.getCompositeStateAccess().getExitKeyword_10_1());
                    			
                    // InternalThingML.g:3653:4: ( (lv_exit_15_0= ruleAction ) )
                    // InternalThingML.g:3654:5: (lv_exit_15_0= ruleAction )
                    {
                    // InternalThingML.g:3654:5: (lv_exit_15_0= ruleAction )
                    // InternalThingML.g:3655:6: lv_exit_15_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getCompositeStateAccess().getExitActionParserRuleCall_10_2_0());
                    					
                    pushFollow(FOLLOW_61);
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

            // InternalThingML.g:3673:3: ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )*
            loop76:
            do {
                int alt76=4;
                switch ( input.LA(1) ) {
                case 62:
                case 69:
                case 70:
                case 71:
                case 72:
                    {
                    alt76=1;
                    }
                    break;
                case 45:
                    {
                    alt76=2;
                    }
                    break;
                case 74:
                    {
                    alt76=3;
                    }
                    break;

                }

                switch (alt76) {
            	case 1 :
            	    // InternalThingML.g:3674:4: ( (lv_substate_16_0= ruleState ) )
            	    {
            	    // InternalThingML.g:3674:4: ( (lv_substate_16_0= ruleState ) )
            	    // InternalThingML.g:3675:5: (lv_substate_16_0= ruleState )
            	    {
            	    // InternalThingML.g:3675:5: (lv_substate_16_0= ruleState )
            	    // InternalThingML.g:3676:6: lv_substate_16_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getSubstateStateParserRuleCall_11_0_0());
            	    					
            	    pushFollow(FOLLOW_61);
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
            	    // InternalThingML.g:3694:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:3694:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    // InternalThingML.g:3695:5: (lv_internal_17_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:3695:5: (lv_internal_17_0= ruleInternalTransition )
            	    // InternalThingML.g:3696:6: lv_internal_17_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getInternalInternalTransitionParserRuleCall_11_1_0());
            	    					
            	    pushFollow(FOLLOW_61);
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
            	    // InternalThingML.g:3714:4: ( (lv_outgoing_18_0= ruleTransition ) )
            	    {
            	    // InternalThingML.g:3714:4: ( (lv_outgoing_18_0= ruleTransition ) )
            	    // InternalThingML.g:3715:5: (lv_outgoing_18_0= ruleTransition )
            	    {
            	    // InternalThingML.g:3715:5: (lv_outgoing_18_0= ruleTransition )
            	    // InternalThingML.g:3716:6: lv_outgoing_18_0= ruleTransition
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getOutgoingTransitionParserRuleCall_11_2_0());
            	    					
            	    pushFollow(FOLLOW_61);
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
            	    break loop76;
                }
            } while (true);

            // InternalThingML.g:3734:3: ( (lv_region_19_0= ruleParallelRegion ) )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==73) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // InternalThingML.g:3735:4: (lv_region_19_0= ruleParallelRegion )
            	    {
            	    // InternalThingML.g:3735:4: (lv_region_19_0= ruleParallelRegion )
            	    // InternalThingML.g:3736:5: lv_region_19_0= ruleParallelRegion
            	    {

            	    					newCompositeNode(grammarAccess.getCompositeStateAccess().getRegionParallelRegionParserRuleCall_12_0());
            	    				
            	    pushFollow(FOLLOW_55);
            	    lv_region_19_0=ruleParallelRegion();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getCompositeStateRule());
            	    					}
            	    					add(
            	    						current,
            	    						"region",
            	    						lv_region_19_0,
            	    						"org.thingml.xtext.ThingML.ParallelRegion");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop77;
                }
            } while (true);

            otherlv_20=(Token)match(input,24,FOLLOW_2); 

            			newLeafNode(otherlv_20, grammarAccess.getCompositeStateAccess().getRightCurlyBracketKeyword_13());
            		

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


    // $ANTLR start "entryRuleSession"
    // InternalThingML.g:3761:1: entryRuleSession returns [EObject current=null] : iv_ruleSession= ruleSession EOF ;
    public final EObject entryRuleSession() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSession = null;


        try {
            // InternalThingML.g:3761:48: (iv_ruleSession= ruleSession EOF )
            // InternalThingML.g:3762:2: iv_ruleSession= ruleSession EOF
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
    // InternalThingML.g:3768:1: ruleSession returns [EObject current=null] : (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) )* ( (lv_region_18_0= ruleParallelRegion ) )* otherlv_19= '}' ) ;
    public final EObject ruleSession() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token lv_maxInstances_3_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token otherlv_19=null;
        EObject lv_annotations_7_0 = null;

        EObject lv_properties_9_0 = null;

        EObject lv_entry_12_0 = null;

        EObject lv_exit_15_0 = null;

        EObject lv_substate_16_0 = null;

        EObject lv_internal_17_0 = null;

        EObject lv_region_18_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3774:2: ( (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) )* ( (lv_region_18_0= ruleParallelRegion ) )* otherlv_19= '}' ) )
            // InternalThingML.g:3775:2: (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) )* ( (lv_region_18_0= ruleParallelRegion ) )* otherlv_19= '}' )
            {
            // InternalThingML.g:3775:2: (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) )* ( (lv_region_18_0= ruleParallelRegion ) )* otherlv_19= '}' )
            // InternalThingML.g:3776:3: otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) )* ( (lv_region_18_0= ruleParallelRegion ) )* otherlv_19= '}'
            {
            otherlv_0=(Token)match(input,72,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getSessionAccess().getSessionKeyword_0());
            		
            // InternalThingML.g:3780:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:3781:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:3781:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:3782:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_62); 

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

            // InternalThingML.g:3798:3: (otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>' )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==18) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // InternalThingML.g:3799:4: otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>'
                    {
                    otherlv_2=(Token)match(input,18,FOLLOW_11); 

                    				newLeafNode(otherlv_2, grammarAccess.getSessionAccess().getLessThanSignKeyword_2_0());
                    			
                    // InternalThingML.g:3803:4: ( (lv_maxInstances_3_0= RULE_INT ) )
                    // InternalThingML.g:3804:5: (lv_maxInstances_3_0= RULE_INT )
                    {
                    // InternalThingML.g:3804:5: (lv_maxInstances_3_0= RULE_INT )
                    // InternalThingML.g:3805:6: lv_maxInstances_3_0= RULE_INT
                    {
                    lv_maxInstances_3_0=(Token)match(input,RULE_INT,FOLLOW_12); 

                    						newLeafNode(lv_maxInstances_3_0, grammarAccess.getSessionAccess().getMaxInstancesINTTerminalRuleCall_2_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getSessionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"maxInstances",
                    							lv_maxInstances_3_0,
                    							"org.thingml.xtext.ThingML.INT");
                    					

                    }


                    }

                    otherlv_4=(Token)match(input,19,FOLLOW_47); 

                    				newLeafNode(otherlv_4, grammarAccess.getSessionAccess().getGreaterThanSignKeyword_2_2());
                    			

                    }
                    break;

            }

            otherlv_5=(Token)match(input,63,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getSessionAccess().getInitKeyword_3());
            		
            // InternalThingML.g:3830:3: ( (otherlv_6= RULE_ID ) )
            // InternalThingML.g:3831:4: (otherlv_6= RULE_ID )
            {
            // InternalThingML.g:3831:4: (otherlv_6= RULE_ID )
            // InternalThingML.g:3832:5: otherlv_6= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSessionRule());
            					}
            				
            otherlv_6=(Token)match(input,RULE_ID,FOLLOW_14); 

            					newLeafNode(otherlv_6, grammarAccess.getSessionAccess().getInitialStateCrossReference_4_0());
            				

            }


            }

            // InternalThingML.g:3843:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==14) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // InternalThingML.g:3844:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3844:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3845:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getSessionAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_14);
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
            	    break loop79;
                }
            } while (true);

            otherlv_8=(Token)match(input,23,FOLLOW_50); 

            			newLeafNode(otherlv_8, grammarAccess.getSessionAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:3866:3: ( (lv_properties_9_0= ruleProperty ) )*
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( ((LA80_0>=36 && LA80_0<=37)) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // InternalThingML.g:3867:4: (lv_properties_9_0= ruleProperty )
            	    {
            	    // InternalThingML.g:3867:4: (lv_properties_9_0= ruleProperty )
            	    // InternalThingML.g:3868:5: lv_properties_9_0= ruleProperty
            	    {

            	    					newCompositeNode(grammarAccess.getSessionAccess().getPropertiesPropertyParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_50);
            	    lv_properties_9_0=ruleProperty();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getSessionRule());
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
            	    break loop80;
                }
            } while (true);

            // InternalThingML.g:3885:3: (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==66) ) {
                int LA81_1 = input.LA(2);

                if ( (LA81_1==67) ) {
                    alt81=1;
                }
            }
            switch (alt81) {
                case 1 :
                    // InternalThingML.g:3886:4: otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) )
                    {
                    otherlv_10=(Token)match(input,66,FOLLOW_51); 

                    				newLeafNode(otherlv_10, grammarAccess.getSessionAccess().getOnKeyword_8_0());
                    			
                    otherlv_11=(Token)match(input,67,FOLLOW_27); 

                    				newLeafNode(otherlv_11, grammarAccess.getSessionAccess().getEntryKeyword_8_1());
                    			
                    // InternalThingML.g:3894:4: ( (lv_entry_12_0= ruleAction ) )
                    // InternalThingML.g:3895:5: (lv_entry_12_0= ruleAction )
                    {
                    // InternalThingML.g:3895:5: (lv_entry_12_0= ruleAction )
                    // InternalThingML.g:3896:6: lv_entry_12_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getSessionAccess().getEntryActionParserRuleCall_8_2_0());
                    					
                    pushFollow(FOLLOW_52);
                    lv_entry_12_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getSessionRule());
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

            // InternalThingML.g:3914:3: (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==66) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // InternalThingML.g:3915:4: otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) )
                    {
                    otherlv_13=(Token)match(input,66,FOLLOW_53); 

                    				newLeafNode(otherlv_13, grammarAccess.getSessionAccess().getOnKeyword_9_0());
                    			
                    otherlv_14=(Token)match(input,68,FOLLOW_27); 

                    				newLeafNode(otherlv_14, grammarAccess.getSessionAccess().getExitKeyword_9_1());
                    			
                    // InternalThingML.g:3923:4: ( (lv_exit_15_0= ruleAction ) )
                    // InternalThingML.g:3924:5: (lv_exit_15_0= ruleAction )
                    {
                    // InternalThingML.g:3924:5: (lv_exit_15_0= ruleAction )
                    // InternalThingML.g:3925:6: lv_exit_15_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getSessionAccess().getExitActionParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_54);
                    lv_exit_15_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getSessionRule());
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

            // InternalThingML.g:3943:3: ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) )*
            loop83:
            do {
                int alt83=3;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==62||(LA83_0>=69 && LA83_0<=72)) ) {
                    alt83=1;
                }
                else if ( (LA83_0==45) ) {
                    alt83=2;
                }


                switch (alt83) {
            	case 1 :
            	    // InternalThingML.g:3944:4: ( (lv_substate_16_0= ruleState ) )
            	    {
            	    // InternalThingML.g:3944:4: ( (lv_substate_16_0= ruleState ) )
            	    // InternalThingML.g:3945:5: (lv_substate_16_0= ruleState )
            	    {
            	    // InternalThingML.g:3945:5: (lv_substate_16_0= ruleState )
            	    // InternalThingML.g:3946:6: lv_substate_16_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getSessionAccess().getSubstateStateParserRuleCall_10_0_0());
            	    					
            	    pushFollow(FOLLOW_54);
            	    lv_substate_16_0=ruleState();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getSessionRule());
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
            	    // InternalThingML.g:3964:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:3964:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    // InternalThingML.g:3965:5: (lv_internal_17_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:3965:5: (lv_internal_17_0= ruleInternalTransition )
            	    // InternalThingML.g:3966:6: lv_internal_17_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getSessionAccess().getInternalInternalTransitionParserRuleCall_10_1_0());
            	    					
            	    pushFollow(FOLLOW_54);
            	    lv_internal_17_0=ruleInternalTransition();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getSessionRule());
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

            	default :
            	    break loop83;
                }
            } while (true);

            // InternalThingML.g:3984:3: ( (lv_region_18_0= ruleParallelRegion ) )*
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==73) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // InternalThingML.g:3985:4: (lv_region_18_0= ruleParallelRegion )
            	    {
            	    // InternalThingML.g:3985:4: (lv_region_18_0= ruleParallelRegion )
            	    // InternalThingML.g:3986:5: lv_region_18_0= ruleParallelRegion
            	    {

            	    					newCompositeNode(grammarAccess.getSessionAccess().getRegionParallelRegionParserRuleCall_11_0());
            	    				
            	    pushFollow(FOLLOW_55);
            	    lv_region_18_0=ruleParallelRegion();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getSessionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"region",
            	    						lv_region_18_0,
            	    						"org.thingml.xtext.ThingML.ParallelRegion");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop84;
                }
            } while (true);

            otherlv_19=(Token)match(input,24,FOLLOW_2); 

            			newLeafNode(otherlv_19, grammarAccess.getSessionAccess().getRightCurlyBracketKeyword_12());
            		

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


    // $ANTLR start "entryRuleParallelRegion"
    // InternalThingML.g:4011:1: entryRuleParallelRegion returns [EObject current=null] : iv_ruleParallelRegion= ruleParallelRegion EOF ;
    public final EObject entryRuleParallelRegion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParallelRegion = null;


        try {
            // InternalThingML.g:4011:55: (iv_ruleParallelRegion= ruleParallelRegion EOF )
            // InternalThingML.g:4012:2: iv_ruleParallelRegion= ruleParallelRegion EOF
            {
             newCompositeNode(grammarAccess.getParallelRegionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParallelRegion=ruleParallelRegion();

            state._fsp--;

             current =iv_ruleParallelRegion; 
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
    // $ANTLR end "entryRuleParallelRegion"


    // $ANTLR start "ruleParallelRegion"
    // InternalThingML.g:4018:1: ruleParallelRegion returns [EObject current=null] : (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) ;
    public final EObject ruleParallelRegion() throws RecognitionException {
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
            // InternalThingML.g:4024:2: ( (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) )
            // InternalThingML.g:4025:2: (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' )
            {
            // InternalThingML.g:4025:2: (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' )
            // InternalThingML.g:4026:3: otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,73,FOLLOW_46); 

            			newLeafNode(otherlv_0, grammarAccess.getParallelRegionAccess().getRegionKeyword_0());
            		
            // InternalThingML.g:4030:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==RULE_ID) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // InternalThingML.g:4031:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:4031:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:4032:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_47); 

                    					newLeafNode(lv_name_1_0, grammarAccess.getParallelRegionAccess().getNameIDTerminalRuleCall_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getParallelRegionRule());
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

            otherlv_2=(Token)match(input,63,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getParallelRegionAccess().getInitKeyword_2());
            		
            // InternalThingML.g:4052:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:4053:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:4053:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:4054:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getParallelRegionRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_48); 

            					newLeafNode(otherlv_3, grammarAccess.getParallelRegionAccess().getInitialStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:4065:3: (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==64) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // InternalThingML.g:4066:4: otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) )
                    {
                    otherlv_4=(Token)match(input,64,FOLLOW_49); 

                    				newLeafNode(otherlv_4, grammarAccess.getParallelRegionAccess().getKeepsKeyword_4_0());
                    			
                    // InternalThingML.g:4070:4: ( (lv_history_5_0= 'history' ) )
                    // InternalThingML.g:4071:5: (lv_history_5_0= 'history' )
                    {
                    // InternalThingML.g:4071:5: (lv_history_5_0= 'history' )
                    // InternalThingML.g:4072:6: lv_history_5_0= 'history'
                    {
                    lv_history_5_0=(Token)match(input,65,FOLLOW_14); 

                    						newLeafNode(lv_history_5_0, grammarAccess.getParallelRegionAccess().getHistoryHistoryKeyword_4_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getParallelRegionRule());
                    						}
                    						setWithLastConsumed(current, "history", true, "history");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:4085:3: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            loop87:
            do {
                int alt87=2;
                int LA87_0 = input.LA(1);

                if ( (LA87_0==14) ) {
                    alt87=1;
                }


                switch (alt87) {
            	case 1 :
            	    // InternalThingML.g:4086:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:4086:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    // InternalThingML.g:4087:5: lv_annotations_6_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getParallelRegionAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_14);
            	    lv_annotations_6_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getParallelRegionRule());
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
            	    break loop87;
                }
            } while (true);

            otherlv_7=(Token)match(input,23,FOLLOW_63); 

            			newLeafNode(otherlv_7, grammarAccess.getParallelRegionAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:4108:3: ( (lv_substate_8_0= ruleState ) )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( (LA88_0==62||(LA88_0>=69 && LA88_0<=72)) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // InternalThingML.g:4109:4: (lv_substate_8_0= ruleState )
            	    {
            	    // InternalThingML.g:4109:4: (lv_substate_8_0= ruleState )
            	    // InternalThingML.g:4110:5: lv_substate_8_0= ruleState
            	    {

            	    					newCompositeNode(grammarAccess.getParallelRegionAccess().getSubstateStateParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_63);
            	    lv_substate_8_0=ruleState();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getParallelRegionRule());
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
            	    break loop88;
                }
            } while (true);

            otherlv_9=(Token)match(input,24,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getParallelRegionAccess().getRightCurlyBracketKeyword_8());
            		

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
    // $ANTLR end "ruleParallelRegion"


    // $ANTLR start "entryRuleState"
    // InternalThingML.g:4135:1: entryRuleState returns [EObject current=null] : iv_ruleState= ruleState EOF ;
    public final EObject entryRuleState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleState = null;


        try {
            // InternalThingML.g:4135:46: (iv_ruleState= ruleState EOF )
            // InternalThingML.g:4136:2: iv_ruleState= ruleState EOF
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
    // InternalThingML.g:4142:1: ruleState returns [EObject current=null] : (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) ) ;
    public final EObject ruleState() throws RecognitionException {
        EObject current = null;

        Token otherlv_4=null;
        Token lv_name_5_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_17=null;
        EObject this_StateMachine_0 = null;

        EObject this_FinalState_1 = null;

        EObject this_CompositeState_2 = null;

        EObject this_Session_3 = null;

        EObject lv_annotations_6_0 = null;

        EObject lv_properties_8_0 = null;

        EObject lv_entry_11_0 = null;

        EObject lv_exit_14_0 = null;

        EObject lv_internal_15_0 = null;

        EObject lv_outgoing_16_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4148:2: ( (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) ) )
            // InternalThingML.g:4149:2: (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) )
            {
            // InternalThingML.g:4149:2: (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) )
            int alt94=5;
            switch ( input.LA(1) ) {
            case 62:
                {
                alt94=1;
                }
                break;
            case 69:
                {
                alt94=2;
                }
                break;
            case 71:
                {
                alt94=3;
                }
                break;
            case 72:
                {
                alt94=4;
                }
                break;
            case 70:
                {
                alt94=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 94, 0, input);

                throw nvae;
            }

            switch (alt94) {
                case 1 :
                    // InternalThingML.g:4150:3: this_StateMachine_0= ruleStateMachine
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
                    // InternalThingML.g:4159:3: this_FinalState_1= ruleFinalState
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
                    // InternalThingML.g:4168:3: this_CompositeState_2= ruleCompositeState
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
                    // InternalThingML.g:4177:3: this_Session_3= ruleSession
                    {

                    			newCompositeNode(grammarAccess.getStateAccess().getSessionParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_Session_3=ruleSession();

                    state._fsp--;


                    			current = this_Session_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalThingML.g:4186:3: (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' )
                    {
                    // InternalThingML.g:4186:3: (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' )
                    // InternalThingML.g:4187:4: otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}'
                    {
                    otherlv_4=(Token)match(input,70,FOLLOW_6); 

                    				newLeafNode(otherlv_4, grammarAccess.getStateAccess().getStateKeyword_4_0());
                    			
                    // InternalThingML.g:4191:4: ( (lv_name_5_0= RULE_ID ) )
                    // InternalThingML.g:4192:5: (lv_name_5_0= RULE_ID )
                    {
                    // InternalThingML.g:4192:5: (lv_name_5_0= RULE_ID )
                    // InternalThingML.g:4193:6: lv_name_5_0= RULE_ID
                    {
                    lv_name_5_0=(Token)match(input,RULE_ID,FOLLOW_14); 

                    						newLeafNode(lv_name_5_0, grammarAccess.getStateAccess().getNameIDTerminalRuleCall_4_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getStateRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"name",
                    							lv_name_5_0,
                    							"org.thingml.xtext.ThingML.ID");
                    					

                    }


                    }

                    // InternalThingML.g:4209:4: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
                    loop89:
                    do {
                        int alt89=2;
                        int LA89_0 = input.LA(1);

                        if ( (LA89_0==14) ) {
                            alt89=1;
                        }


                        switch (alt89) {
                    	case 1 :
                    	    // InternalThingML.g:4210:5: (lv_annotations_6_0= rulePlatformAnnotation )
                    	    {
                    	    // InternalThingML.g:4210:5: (lv_annotations_6_0= rulePlatformAnnotation )
                    	    // InternalThingML.g:4211:6: lv_annotations_6_0= rulePlatformAnnotation
                    	    {

                    	    						newCompositeNode(grammarAccess.getStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_2_0());
                    	    					
                    	    pushFollow(FOLLOW_14);
                    	    lv_annotations_6_0=rulePlatformAnnotation();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getStateRule());
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
                    	    break loop89;
                        }
                    } while (true);

                    otherlv_7=(Token)match(input,23,FOLLOW_64); 

                    				newLeafNode(otherlv_7, grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_4_3());
                    			
                    // InternalThingML.g:4232:4: ( (lv_properties_8_0= ruleProperty ) )*
                    loop90:
                    do {
                        int alt90=2;
                        int LA90_0 = input.LA(1);

                        if ( ((LA90_0>=36 && LA90_0<=37)) ) {
                            alt90=1;
                        }


                        switch (alt90) {
                    	case 1 :
                    	    // InternalThingML.g:4233:5: (lv_properties_8_0= ruleProperty )
                    	    {
                    	    // InternalThingML.g:4233:5: (lv_properties_8_0= ruleProperty )
                    	    // InternalThingML.g:4234:6: lv_properties_8_0= ruleProperty
                    	    {

                    	    						newCompositeNode(grammarAccess.getStateAccess().getPropertiesPropertyParserRuleCall_4_4_0());
                    	    					
                    	    pushFollow(FOLLOW_64);
                    	    lv_properties_8_0=ruleProperty();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getStateRule());
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
                    	    break loop90;
                        }
                    } while (true);

                    // InternalThingML.g:4251:4: (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )?
                    int alt91=2;
                    int LA91_0 = input.LA(1);

                    if ( (LA91_0==66) ) {
                        int LA91_1 = input.LA(2);

                        if ( (LA91_1==67) ) {
                            alt91=1;
                        }
                    }
                    switch (alt91) {
                        case 1 :
                            // InternalThingML.g:4252:5: otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) )
                            {
                            otherlv_9=(Token)match(input,66,FOLLOW_51); 

                            					newLeafNode(otherlv_9, grammarAccess.getStateAccess().getOnKeyword_4_5_0());
                            				
                            otherlv_10=(Token)match(input,67,FOLLOW_27); 

                            					newLeafNode(otherlv_10, grammarAccess.getStateAccess().getEntryKeyword_4_5_1());
                            				
                            // InternalThingML.g:4260:5: ( (lv_entry_11_0= ruleAction ) )
                            // InternalThingML.g:4261:6: (lv_entry_11_0= ruleAction )
                            {
                            // InternalThingML.g:4261:6: (lv_entry_11_0= ruleAction )
                            // InternalThingML.g:4262:7: lv_entry_11_0= ruleAction
                            {

                            							newCompositeNode(grammarAccess.getStateAccess().getEntryActionParserRuleCall_4_5_2_0());
                            						
                            pushFollow(FOLLOW_65);
                            lv_entry_11_0=ruleAction();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getStateRule());
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

                    // InternalThingML.g:4280:4: (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )?
                    int alt92=2;
                    int LA92_0 = input.LA(1);

                    if ( (LA92_0==66) ) {
                        alt92=1;
                    }
                    switch (alt92) {
                        case 1 :
                            // InternalThingML.g:4281:5: otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) )
                            {
                            otherlv_12=(Token)match(input,66,FOLLOW_53); 

                            					newLeafNode(otherlv_12, grammarAccess.getStateAccess().getOnKeyword_4_6_0());
                            				
                            otherlv_13=(Token)match(input,68,FOLLOW_27); 

                            					newLeafNode(otherlv_13, grammarAccess.getStateAccess().getExitKeyword_4_6_1());
                            				
                            // InternalThingML.g:4289:5: ( (lv_exit_14_0= ruleAction ) )
                            // InternalThingML.g:4290:6: (lv_exit_14_0= ruleAction )
                            {
                            // InternalThingML.g:4290:6: (lv_exit_14_0= ruleAction )
                            // InternalThingML.g:4291:7: lv_exit_14_0= ruleAction
                            {

                            							newCompositeNode(grammarAccess.getStateAccess().getExitActionParserRuleCall_4_6_2_0());
                            						
                            pushFollow(FOLLOW_66);
                            lv_exit_14_0=ruleAction();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getStateRule());
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

                    // InternalThingML.g:4309:4: ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )*
                    loop93:
                    do {
                        int alt93=3;
                        int LA93_0 = input.LA(1);

                        if ( (LA93_0==45) ) {
                            alt93=1;
                        }
                        else if ( (LA93_0==74) ) {
                            alt93=2;
                        }


                        switch (alt93) {
                    	case 1 :
                    	    // InternalThingML.g:4310:5: ( (lv_internal_15_0= ruleInternalTransition ) )
                    	    {
                    	    // InternalThingML.g:4310:5: ( (lv_internal_15_0= ruleInternalTransition ) )
                    	    // InternalThingML.g:4311:6: (lv_internal_15_0= ruleInternalTransition )
                    	    {
                    	    // InternalThingML.g:4311:6: (lv_internal_15_0= ruleInternalTransition )
                    	    // InternalThingML.g:4312:7: lv_internal_15_0= ruleInternalTransition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getInternalInternalTransitionParserRuleCall_4_7_0_0());
                    	    						
                    	    pushFollow(FOLLOW_66);
                    	    lv_internal_15_0=ruleInternalTransition();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStateRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"internal",
                    	    								lv_internal_15_0,
                    	    								"org.thingml.xtext.ThingML.InternalTransition");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalThingML.g:4330:5: ( (lv_outgoing_16_0= ruleTransition ) )
                    	    {
                    	    // InternalThingML.g:4330:5: ( (lv_outgoing_16_0= ruleTransition ) )
                    	    // InternalThingML.g:4331:6: (lv_outgoing_16_0= ruleTransition )
                    	    {
                    	    // InternalThingML.g:4331:6: (lv_outgoing_16_0= ruleTransition )
                    	    // InternalThingML.g:4332:7: lv_outgoing_16_0= ruleTransition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getOutgoingTransitionParserRuleCall_4_7_1_0());
                    	    						
                    	    pushFollow(FOLLOW_66);
                    	    lv_outgoing_16_0=ruleTransition();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStateRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"outgoing",
                    	    								lv_outgoing_16_0,
                    	    								"org.thingml.xtext.ThingML.Transition");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop93;
                        }
                    } while (true);

                    otherlv_17=(Token)match(input,24,FOLLOW_2); 

                    				newLeafNode(otherlv_17, grammarAccess.getStateAccess().getRightCurlyBracketKeyword_4_8());
                    			

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
    // InternalThingML.g:4359:1: entryRuleHandler returns [EObject current=null] : iv_ruleHandler= ruleHandler EOF ;
    public final EObject entryRuleHandler() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHandler = null;


        try {
            // InternalThingML.g:4359:48: (iv_ruleHandler= ruleHandler EOF )
            // InternalThingML.g:4360:2: iv_ruleHandler= ruleHandler EOF
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
    // InternalThingML.g:4366:1: ruleHandler returns [EObject current=null] : (this_Transition_0= ruleTransition | this_InternalTransition_1= ruleInternalTransition ) ;
    public final EObject ruleHandler() throws RecognitionException {
        EObject current = null;

        EObject this_Transition_0 = null;

        EObject this_InternalTransition_1 = null;



        	enterRule();

        try {
            // InternalThingML.g:4372:2: ( (this_Transition_0= ruleTransition | this_InternalTransition_1= ruleInternalTransition ) )
            // InternalThingML.g:4373:2: (this_Transition_0= ruleTransition | this_InternalTransition_1= ruleInternalTransition )
            {
            // InternalThingML.g:4373:2: (this_Transition_0= ruleTransition | this_InternalTransition_1= ruleInternalTransition )
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==74) ) {
                alt95=1;
            }
            else if ( (LA95_0==45) ) {
                alt95=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }
            switch (alt95) {
                case 1 :
                    // InternalThingML.g:4374:3: this_Transition_0= ruleTransition
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
                    // InternalThingML.g:4383:3: this_InternalTransition_1= ruleInternalTransition
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
    // InternalThingML.g:4395:1: entryRuleTransition returns [EObject current=null] : iv_ruleTransition= ruleTransition EOF ;
    public final EObject entryRuleTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransition = null;


        try {
            // InternalThingML.g:4395:51: (iv_ruleTransition= ruleTransition EOF )
            // InternalThingML.g:4396:2: iv_ruleTransition= ruleTransition EOF
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
    // InternalThingML.g:4402:1: ruleTransition returns [EObject current=null] : (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? ) ;
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
            // InternalThingML.g:4408:2: ( (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? ) )
            // InternalThingML.g:4409:2: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:4409:2: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? )
            // InternalThingML.g:4410:3: otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )?
            {
            otherlv_0=(Token)match(input,74,FOLLOW_67); 

            			newLeafNode(otherlv_0, grammarAccess.getTransitionAccess().getTransitionKeyword_0());
            		
            // InternalThingML.g:4414:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==RULE_ID) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // InternalThingML.g:4415:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:4415:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:4416:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_68); 

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

            otherlv_2=(Token)match(input,52,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getTransitionAccess().getHyphenMinusGreaterThanSignKeyword_2());
            		
            // InternalThingML.g:4436:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:4437:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:4437:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:4438:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTransitionRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_69); 

            					newLeafNode(otherlv_3, grammarAccess.getTransitionAccess().getTargetStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:4449:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( (LA97_0==14) ) {
                    alt97=1;
                }


                switch (alt97) {
            	case 1 :
            	    // InternalThingML.g:4450:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:4450:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:4451:5: lv_annotations_4_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_69);
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
            	    break loop97;
                }
            } while (true);

            // InternalThingML.g:4468:3: (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )*
            loop98:
            do {
                int alt98=2;
                int LA98_0 = input.LA(1);

                if ( (LA98_0==75) ) {
                    alt98=1;
                }


                switch (alt98) {
            	case 1 :
            	    // InternalThingML.g:4469:4: otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) )
            	    {
            	    otherlv_5=(Token)match(input,75,FOLLOW_6); 

            	    				newLeafNode(otherlv_5, grammarAccess.getTransitionAccess().getEventKeyword_5_0());
            	    			
            	    // InternalThingML.g:4473:4: ( (lv_event_6_0= ruleEvent ) )
            	    // InternalThingML.g:4474:5: (lv_event_6_0= ruleEvent )
            	    {
            	    // InternalThingML.g:4474:5: (lv_event_6_0= ruleEvent )
            	    // InternalThingML.g:4475:6: lv_event_6_0= ruleEvent
            	    {

            	    						newCompositeNode(grammarAccess.getTransitionAccess().getEventEventParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_70);
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
            	    break loop98;
                }
            } while (true);

            // InternalThingML.g:4493:3: (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )?
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==76) ) {
                alt99=1;
            }
            switch (alt99) {
                case 1 :
                    // InternalThingML.g:4494:4: otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) )
                    {
                    otherlv_7=(Token)match(input,76,FOLLOW_22); 

                    				newLeafNode(otherlv_7, grammarAccess.getTransitionAccess().getGuardKeyword_6_0());
                    			
                    // InternalThingML.g:4498:4: ( (lv_guard_8_0= ruleExpression ) )
                    // InternalThingML.g:4499:5: (lv_guard_8_0= ruleExpression )
                    {
                    // InternalThingML.g:4499:5: (lv_guard_8_0= ruleExpression )
                    // InternalThingML.g:4500:6: lv_guard_8_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getTransitionAccess().getGuardExpressionParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_71);
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

            // InternalThingML.g:4518:3: (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )?
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==77) ) {
                alt100=1;
            }
            switch (alt100) {
                case 1 :
                    // InternalThingML.g:4519:4: otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) )
                    {
                    otherlv_9=(Token)match(input,77,FOLLOW_27); 

                    				newLeafNode(otherlv_9, grammarAccess.getTransitionAccess().getActionKeyword_7_0());
                    			
                    // InternalThingML.g:4523:4: ( (lv_action_10_0= ruleAction ) )
                    // InternalThingML.g:4524:5: (lv_action_10_0= ruleAction )
                    {
                    // InternalThingML.g:4524:5: (lv_action_10_0= ruleAction )
                    // InternalThingML.g:4525:6: lv_action_10_0= ruleAction
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
    // InternalThingML.g:4547:1: entryRuleInternalTransition returns [EObject current=null] : iv_ruleInternalTransition= ruleInternalTransition EOF ;
    public final EObject entryRuleInternalTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInternalTransition = null;


        try {
            // InternalThingML.g:4547:59: (iv_ruleInternalTransition= ruleInternalTransition EOF )
            // InternalThingML.g:4548:2: iv_ruleInternalTransition= ruleInternalTransition EOF
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
    // InternalThingML.g:4554:1: ruleInternalTransition returns [EObject current=null] : ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? ) ;
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
            // InternalThingML.g:4560:2: ( ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? ) )
            // InternalThingML.g:4561:2: ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:4561:2: ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? )
            // InternalThingML.g:4562:3: () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )?
            {
            // InternalThingML.g:4562:3: ()
            // InternalThingML.g:4563:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getInternalTransitionAccess().getInternalTransitionAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,45,FOLLOW_72); 

            			newLeafNode(otherlv_1, grammarAccess.getInternalTransitionAccess().getInternalKeyword_1());
            		
            // InternalThingML.g:4573:3: ( (lv_name_2_0= RULE_ID ) )?
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==RULE_ID) ) {
                alt101=1;
            }
            switch (alt101) {
                case 1 :
                    // InternalThingML.g:4574:4: (lv_name_2_0= RULE_ID )
                    {
                    // InternalThingML.g:4574:4: (lv_name_2_0= RULE_ID )
                    // InternalThingML.g:4575:5: lv_name_2_0= RULE_ID
                    {
                    lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_69); 

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

            // InternalThingML.g:4591:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop102:
            do {
                int alt102=2;
                int LA102_0 = input.LA(1);

                if ( (LA102_0==14) ) {
                    alt102=1;
                }


                switch (alt102) {
            	case 1 :
            	    // InternalThingML.g:4592:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:4592:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:4593:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getInternalTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_69);
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
            	    break loop102;
                }
            } while (true);

            // InternalThingML.g:4610:3: (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )*
            loop103:
            do {
                int alt103=2;
                int LA103_0 = input.LA(1);

                if ( (LA103_0==75) ) {
                    alt103=1;
                }


                switch (alt103) {
            	case 1 :
            	    // InternalThingML.g:4611:4: otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) )
            	    {
            	    otherlv_4=(Token)match(input,75,FOLLOW_6); 

            	    				newLeafNode(otherlv_4, grammarAccess.getInternalTransitionAccess().getEventKeyword_4_0());
            	    			
            	    // InternalThingML.g:4615:4: ( (lv_event_5_0= ruleEvent ) )
            	    // InternalThingML.g:4616:5: (lv_event_5_0= ruleEvent )
            	    {
            	    // InternalThingML.g:4616:5: (lv_event_5_0= ruleEvent )
            	    // InternalThingML.g:4617:6: lv_event_5_0= ruleEvent
            	    {

            	    						newCompositeNode(grammarAccess.getInternalTransitionAccess().getEventEventParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_70);
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
            	    break loop103;
                }
            } while (true);

            // InternalThingML.g:4635:3: (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==76) ) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // InternalThingML.g:4636:4: otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) )
                    {
                    otherlv_6=(Token)match(input,76,FOLLOW_22); 

                    				newLeafNode(otherlv_6, grammarAccess.getInternalTransitionAccess().getGuardKeyword_5_0());
                    			
                    // InternalThingML.g:4640:4: ( (lv_guard_7_0= ruleExpression ) )
                    // InternalThingML.g:4641:5: (lv_guard_7_0= ruleExpression )
                    {
                    // InternalThingML.g:4641:5: (lv_guard_7_0= ruleExpression )
                    // InternalThingML.g:4642:6: lv_guard_7_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getInternalTransitionAccess().getGuardExpressionParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_71);
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

            // InternalThingML.g:4660:3: (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==77) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // InternalThingML.g:4661:4: otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) )
                    {
                    otherlv_8=(Token)match(input,77,FOLLOW_27); 

                    				newLeafNode(otherlv_8, grammarAccess.getInternalTransitionAccess().getActionKeyword_6_0());
                    			
                    // InternalThingML.g:4665:4: ( (lv_action_9_0= ruleAction ) )
                    // InternalThingML.g:4666:5: (lv_action_9_0= ruleAction )
                    {
                    // InternalThingML.g:4666:5: (lv_action_9_0= ruleAction )
                    // InternalThingML.g:4667:6: lv_action_9_0= ruleAction
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


    // $ANTLR start "entryRuleEvent"
    // InternalThingML.g:4689:1: entryRuleEvent returns [EObject current=null] : iv_ruleEvent= ruleEvent EOF ;
    public final EObject entryRuleEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEvent = null;


        try {
            // InternalThingML.g:4689:46: (iv_ruleEvent= ruleEvent EOF )
            // InternalThingML.g:4690:2: iv_ruleEvent= ruleEvent EOF
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
    // InternalThingML.g:4696:1: ruleEvent returns [EObject current=null] : this_ReceiveMessage_0= ruleReceiveMessage ;
    public final EObject ruleEvent() throws RecognitionException {
        EObject current = null;

        EObject this_ReceiveMessage_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4702:2: (this_ReceiveMessage_0= ruleReceiveMessage )
            // InternalThingML.g:4703:2: this_ReceiveMessage_0= ruleReceiveMessage
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
    // InternalThingML.g:4714:1: entryRuleReceiveMessage returns [EObject current=null] : iv_ruleReceiveMessage= ruleReceiveMessage EOF ;
    public final EObject entryRuleReceiveMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReceiveMessage = null;


        try {
            // InternalThingML.g:4714:55: (iv_ruleReceiveMessage= ruleReceiveMessage EOF )
            // InternalThingML.g:4715:2: iv_ruleReceiveMessage= ruleReceiveMessage EOF
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
    // InternalThingML.g:4721:1: ruleReceiveMessage returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) ) ;
    public final EObject ruleReceiveMessage() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;


        	enterRule();

        try {
            // InternalThingML.g:4727:2: ( ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) ) )
            // InternalThingML.g:4728:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) )
            {
            // InternalThingML.g:4728:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) )
            // InternalThingML.g:4729:3: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) )
            {
            // InternalThingML.g:4729:3: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==RULE_ID) ) {
                int LA106_1 = input.LA(2);

                if ( (LA106_1==35) ) {
                    alt106=1;
                }
            }
            switch (alt106) {
                case 1 :
                    // InternalThingML.g:4730:4: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':'
                    {
                    // InternalThingML.g:4730:4: ( (lv_name_0_0= RULE_ID ) )
                    // InternalThingML.g:4731:5: (lv_name_0_0= RULE_ID )
                    {
                    // InternalThingML.g:4731:5: (lv_name_0_0= RULE_ID )
                    // InternalThingML.g:4732:6: lv_name_0_0= RULE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_29); 

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

                    otherlv_1=(Token)match(input,35,FOLLOW_6); 

                    				newLeafNode(otherlv_1, grammarAccess.getReceiveMessageAccess().getColonKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalThingML.g:4753:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:4754:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:4754:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:4755:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReceiveMessageRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_73); 

            					newLeafNode(otherlv_2, grammarAccess.getReceiveMessageAccess().getPortPortCrossReference_1_0());
            				

            }


            }

            otherlv_3=(Token)match(input,78,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getReceiveMessageAccess().getQuestionMarkKeyword_2());
            		
            // InternalThingML.g:4770:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:4771:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:4771:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:4772:5: otherlv_4= RULE_ID
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
    // InternalThingML.g:4787:1: entryRuleAction returns [EObject current=null] : iv_ruleAction= ruleAction EOF ;
    public final EObject entryRuleAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAction = null;


        try {
            // InternalThingML.g:4787:47: (iv_ruleAction= ruleAction EOF )
            // InternalThingML.g:4788:2: iv_ruleAction= ruleAction EOF
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
    // InternalThingML.g:4794:1: ruleAction returns [EObject current=null] : (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable ) ;
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
            // InternalThingML.g:4800:2: ( (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable ) )
            // InternalThingML.g:4801:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )
            {
            // InternalThingML.g:4801:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )
            int alt107=14;
            alt107 = dfa107.predict(input);
            switch (alt107) {
                case 1 :
                    // InternalThingML.g:4802:3: this_ActionBlock_0= ruleActionBlock
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
                    // InternalThingML.g:4811:3: this_ExternStatement_1= ruleExternStatement
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
                    // InternalThingML.g:4820:3: this_SendAction_2= ruleSendAction
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
                    // InternalThingML.g:4829:3: this_VariableAssignment_3= ruleVariableAssignment
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
                    // InternalThingML.g:4838:3: this_Increment_4= ruleIncrement
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
                    // InternalThingML.g:4847:3: this_Decrement_5= ruleDecrement
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
                    // InternalThingML.g:4856:3: this_LoopAction_6= ruleLoopAction
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
                    // InternalThingML.g:4865:3: this_ConditionalAction_7= ruleConditionalAction
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
                    // InternalThingML.g:4874:3: this_ReturnAction_8= ruleReturnAction
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
                    // InternalThingML.g:4883:3: this_PrintAction_9= rulePrintAction
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
                    // InternalThingML.g:4892:3: this_ErrorAction_10= ruleErrorAction
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
                    // InternalThingML.g:4901:3: this_StartSession_11= ruleStartSession
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
                    // InternalThingML.g:4910:3: this_FunctionCallStatement_12= ruleFunctionCallStatement
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
                    // InternalThingML.g:4919:3: this_LocalVariable_13= ruleLocalVariable
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
    // InternalThingML.g:4931:1: entryRuleActionBlock returns [EObject current=null] : iv_ruleActionBlock= ruleActionBlock EOF ;
    public final EObject entryRuleActionBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActionBlock = null;


        try {
            // InternalThingML.g:4931:52: (iv_ruleActionBlock= ruleActionBlock EOF )
            // InternalThingML.g:4932:2: iv_ruleActionBlock= ruleActionBlock EOF
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
    // InternalThingML.g:4938:1: ruleActionBlock returns [EObject current=null] : ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' ) ;
    public final EObject ruleActionBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_actions_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4944:2: ( ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' ) )
            // InternalThingML.g:4945:2: ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' )
            {
            // InternalThingML.g:4945:2: ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' )
            // InternalThingML.g:4946:3: () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end'
            {
            // InternalThingML.g:4946:3: ()
            // InternalThingML.g:4947:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getActionBlockAccess().getActionBlockAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,79,FOLLOW_74); 

            			newLeafNode(otherlv_1, grammarAccess.getActionBlockAccess().getDoKeyword_1());
            		
            // InternalThingML.g:4957:3: ( (lv_actions_2_0= ruleAction ) )*
            loop108:
            do {
                int alt108=2;
                int LA108_0 = input.LA(1);

                if ( (LA108_0==RULE_ID||LA108_0==RULE_STRING_EXT||LA108_0==36||LA108_0==57||LA108_0==79||LA108_0==81||LA108_0==85||(LA108_0>=87 && LA108_0<=90)) ) {
                    alt108=1;
                }


                switch (alt108) {
            	case 1 :
            	    // InternalThingML.g:4958:4: (lv_actions_2_0= ruleAction )
            	    {
            	    // InternalThingML.g:4958:4: (lv_actions_2_0= ruleAction )
            	    // InternalThingML.g:4959:5: lv_actions_2_0= ruleAction
            	    {

            	    					newCompositeNode(grammarAccess.getActionBlockAccess().getActionsActionParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_74);
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
            	    break loop108;
                }
            } while (true);

            otherlv_3=(Token)match(input,80,FOLLOW_2); 

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
    // InternalThingML.g:4984:1: entryRuleExternStatement returns [EObject current=null] : iv_ruleExternStatement= ruleExternStatement EOF ;
    public final EObject entryRuleExternStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternStatement = null;


        try {
            // InternalThingML.g:4984:56: (iv_ruleExternStatement= ruleExternStatement EOF )
            // InternalThingML.g:4985:2: iv_ruleExternStatement= ruleExternStatement EOF
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
    // InternalThingML.g:4991:1: ruleExternStatement returns [EObject current=null] : ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) ;
    public final EObject ruleExternStatement() throws RecognitionException {
        EObject current = null;

        Token lv_statement_0_0=null;
        Token otherlv_1=null;
        EObject lv_segments_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4997:2: ( ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) )
            // InternalThingML.g:4998:2: ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            {
            // InternalThingML.g:4998:2: ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            // InternalThingML.g:4999:3: ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            {
            // InternalThingML.g:4999:3: ( (lv_statement_0_0= RULE_STRING_EXT ) )
            // InternalThingML.g:5000:4: (lv_statement_0_0= RULE_STRING_EXT )
            {
            // InternalThingML.g:5000:4: (lv_statement_0_0= RULE_STRING_EXT )
            // InternalThingML.g:5001:5: lv_statement_0_0= RULE_STRING_EXT
            {
            lv_statement_0_0=(Token)match(input,RULE_STRING_EXT,FOLLOW_75); 

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

            // InternalThingML.g:5017:3: (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            loop109:
            do {
                int alt109=2;
                int LA109_0 = input.LA(1);

                if ( (LA109_0==51) ) {
                    alt109=1;
                }


                switch (alt109) {
            	case 1 :
            	    // InternalThingML.g:5018:4: otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,51,FOLLOW_22); 

            	    				newLeafNode(otherlv_1, grammarAccess.getExternStatementAccess().getAmpersandKeyword_1_0());
            	    			
            	    // InternalThingML.g:5022:4: ( (lv_segments_2_0= ruleExpression ) )
            	    // InternalThingML.g:5023:5: (lv_segments_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:5023:5: (lv_segments_2_0= ruleExpression )
            	    // InternalThingML.g:5024:6: lv_segments_2_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getExternStatementAccess().getSegmentsExpressionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_75);
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
            	    break loop109;
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
    // InternalThingML.g:5046:1: entryRuleLocalVariable returns [EObject current=null] : iv_ruleLocalVariable= ruleLocalVariable EOF ;
    public final EObject entryRuleLocalVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalVariable = null;


        try {
            // InternalThingML.g:5046:54: (iv_ruleLocalVariable= ruleLocalVariable EOF )
            // InternalThingML.g:5047:2: iv_ruleLocalVariable= ruleLocalVariable EOF
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
    // InternalThingML.g:5053:1: ruleLocalVariable returns [EObject current=null] : ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleLocalVariable() throws RecognitionException {
        EObject current = null;

        Token lv_changeable_0_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_typeRef_4_0 = null;

        EObject lv_init_6_0 = null;

        EObject lv_annotations_7_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5059:2: ( ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:5060:2: ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:5060:2: ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:5061:3: ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:5061:3: ( (lv_changeable_0_0= 'readonly' ) )?
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==36) ) {
                alt110=1;
            }
            switch (alt110) {
                case 1 :
                    // InternalThingML.g:5062:4: (lv_changeable_0_0= 'readonly' )
                    {
                    // InternalThingML.g:5062:4: (lv_changeable_0_0= 'readonly' )
                    // InternalThingML.g:5063:5: lv_changeable_0_0= 'readonly'
                    {
                    lv_changeable_0_0=(Token)match(input,36,FOLLOW_76); 

                    					newLeafNode(lv_changeable_0_0, grammarAccess.getLocalVariableAccess().getChangeableReadonlyKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getLocalVariableRule());
                    					}
                    					setWithLastConsumed(current, "changeable", true, "readonly");
                    				

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,81,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getLocalVariableAccess().getVarKeyword_1());
            		
            // InternalThingML.g:5079:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:5080:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:5080:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:5081:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_29); 

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

            otherlv_3=(Token)match(input,35,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getLocalVariableAccess().getColonKeyword_3());
            		
            // InternalThingML.g:5101:3: ( (lv_typeRef_4_0= ruleTypeRef ) )
            // InternalThingML.g:5102:4: (lv_typeRef_4_0= ruleTypeRef )
            {
            // InternalThingML.g:5102:4: (lv_typeRef_4_0= ruleTypeRef )
            // InternalThingML.g:5103:5: lv_typeRef_4_0= ruleTypeRef
            {

            					newCompositeNode(grammarAccess.getLocalVariableAccess().getTypeRefTypeRefParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_30);
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

            // InternalThingML.g:5120:3: (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )?
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==30) ) {
                alt111=1;
            }
            switch (alt111) {
                case 1 :
                    // InternalThingML.g:5121:4: otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) )
                    {
                    otherlv_5=(Token)match(input,30,FOLLOW_22); 

                    				newLeafNode(otherlv_5, grammarAccess.getLocalVariableAccess().getEqualsSignKeyword_5_0());
                    			
                    // InternalThingML.g:5125:4: ( (lv_init_6_0= ruleExpression ) )
                    // InternalThingML.g:5126:5: (lv_init_6_0= ruleExpression )
                    {
                    // InternalThingML.g:5126:5: (lv_init_6_0= ruleExpression )
                    // InternalThingML.g:5127:6: lv_init_6_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getLocalVariableAccess().getInitExpressionParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_16);
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

            // InternalThingML.g:5145:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop112:
            do {
                int alt112=2;
                int LA112_0 = input.LA(1);

                if ( (LA112_0==14) ) {
                    alt112=1;
                }


                switch (alt112) {
            	case 1 :
            	    // InternalThingML.g:5146:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:5146:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:5147:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getLocalVariableAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_16);
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
            	    break loop112;
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
    // InternalThingML.g:5168:1: entryRuleSendAction returns [EObject current=null] : iv_ruleSendAction= ruleSendAction EOF ;
    public final EObject entryRuleSendAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSendAction = null;


        try {
            // InternalThingML.g:5168:51: (iv_ruleSendAction= ruleSendAction EOF )
            // InternalThingML.g:5169:2: iv_ruleSendAction= ruleSendAction EOF
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
    // InternalThingML.g:5175:1: ruleSendAction returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' ) ;
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
            // InternalThingML.g:5181:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' ) )
            // InternalThingML.g:5182:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' )
            {
            // InternalThingML.g:5182:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' )
            // InternalThingML.g:5183:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')'
            {
            // InternalThingML.g:5183:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5184:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5184:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5185:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSendActionRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_77); 

            					newLeafNode(otherlv_0, grammarAccess.getSendActionAccess().getPortPortCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,82,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getSendActionAccess().getExclamationMarkKeyword_1());
            		
            // InternalThingML.g:5200:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:5201:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:5201:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:5202:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSendActionRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_24); 

            					newLeafNode(otherlv_2, grammarAccess.getSendActionAccess().getMessageMessageCrossReference_2_0());
            				

            }


            }

            otherlv_3=(Token)match(input,33,FOLLOW_78); 

            			newLeafNode(otherlv_3, grammarAccess.getSendActionAccess().getLeftParenthesisKeyword_3());
            		
            // InternalThingML.g:5217:3: ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )?
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( ((LA114_0>=RULE_STRING_LIT && LA114_0<=RULE_FLOAT)||LA114_0==33||LA114_0==98||(LA114_0>=102 && LA114_0<=104)) ) {
                alt114=1;
            }
            switch (alt114) {
                case 1 :
                    // InternalThingML.g:5218:4: ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )*
                    {
                    // InternalThingML.g:5218:4: ( (lv_parameters_4_0= ruleExpression ) )
                    // InternalThingML.g:5219:5: (lv_parameters_4_0= ruleExpression )
                    {
                    // InternalThingML.g:5219:5: (lv_parameters_4_0= ruleExpression )
                    // InternalThingML.g:5220:6: lv_parameters_4_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_0_0());
                    					
                    pushFollow(FOLLOW_26);
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

                    // InternalThingML.g:5237:4: (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )*
                    loop113:
                    do {
                        int alt113=2;
                        int LA113_0 = input.LA(1);

                        if ( (LA113_0==28) ) {
                            alt113=1;
                        }


                        switch (alt113) {
                    	case 1 :
                    	    // InternalThingML.g:5238:5: otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) )
                    	    {
                    	    otherlv_5=(Token)match(input,28,FOLLOW_22); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getSendActionAccess().getCommaKeyword_4_1_0());
                    	    				
                    	    // InternalThingML.g:5242:5: ( (lv_parameters_6_0= ruleExpression ) )
                    	    // InternalThingML.g:5243:6: (lv_parameters_6_0= ruleExpression )
                    	    {
                    	    // InternalThingML.g:5243:6: (lv_parameters_6_0= ruleExpression )
                    	    // InternalThingML.g:5244:7: lv_parameters_6_0= ruleExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
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
                    	    break loop113;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_7=(Token)match(input,34,FOLLOW_2); 

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
    // InternalThingML.g:5271:1: entryRuleVariableAssignment returns [EObject current=null] : iv_ruleVariableAssignment= ruleVariableAssignment EOF ;
    public final EObject entryRuleVariableAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableAssignment = null;


        try {
            // InternalThingML.g:5271:59: (iv_ruleVariableAssignment= ruleVariableAssignment EOF )
            // InternalThingML.g:5272:2: iv_ruleVariableAssignment= ruleVariableAssignment EOF
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
    // InternalThingML.g:5278:1: ruleVariableAssignment returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) ) ;
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
            // InternalThingML.g:5284:2: ( ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) ) )
            // InternalThingML.g:5285:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) )
            {
            // InternalThingML.g:5285:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) )
            // InternalThingML.g:5286:3: ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) )
            {
            // InternalThingML.g:5286:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5287:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5287:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5288:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVariableAssignmentRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_0, grammarAccess.getVariableAssignmentAccess().getPropertyVariableCrossReference_0_0());
            				

            }


            }

            // InternalThingML.g:5299:3: (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )*
            loop115:
            do {
                int alt115=2;
                int LA115_0 = input.LA(1);

                if ( (LA115_0==15) ) {
                    alt115=1;
                }


                switch (alt115) {
            	case 1 :
            	    // InternalThingML.g:5300:4: otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']'
            	    {
            	    otherlv_1=(Token)match(input,15,FOLLOW_22); 

            	    				newLeafNode(otherlv_1, grammarAccess.getVariableAssignmentAccess().getLeftSquareBracketKeyword_1_0());
            	    			
            	    // InternalThingML.g:5304:4: ( (lv_index_2_0= ruleExpression ) )
            	    // InternalThingML.g:5305:5: (lv_index_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:5305:5: (lv_index_2_0= ruleExpression )
            	    // InternalThingML.g:5306:6: lv_index_2_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getVariableAssignmentAccess().getIndexExpressionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_9);
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

            	    otherlv_3=(Token)match(input,16,FOLLOW_21); 

            	    				newLeafNode(otherlv_3, grammarAccess.getVariableAssignmentAccess().getRightSquareBracketKeyword_1_2());
            	    			

            	    }
            	    break;

            	default :
            	    break loop115;
                }
            } while (true);

            otherlv_4=(Token)match(input,30,FOLLOW_22); 

            			newLeafNode(otherlv_4, grammarAccess.getVariableAssignmentAccess().getEqualsSignKeyword_2());
            		
            // InternalThingML.g:5332:3: ( (lv_expression_5_0= ruleExpression ) )
            // InternalThingML.g:5333:4: (lv_expression_5_0= ruleExpression )
            {
            // InternalThingML.g:5333:4: (lv_expression_5_0= ruleExpression )
            // InternalThingML.g:5334:5: lv_expression_5_0= ruleExpression
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
    // InternalThingML.g:5355:1: entryRuleIncrement returns [EObject current=null] : iv_ruleIncrement= ruleIncrement EOF ;
    public final EObject entryRuleIncrement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIncrement = null;


        try {
            // InternalThingML.g:5355:50: (iv_ruleIncrement= ruleIncrement EOF )
            // InternalThingML.g:5356:2: iv_ruleIncrement= ruleIncrement EOF
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
    // InternalThingML.g:5362:1: ruleIncrement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' ) ;
    public final EObject ruleIncrement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:5368:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' ) )
            // InternalThingML.g:5369:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' )
            {
            // InternalThingML.g:5369:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' )
            // InternalThingML.g:5370:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '++'
            {
            // InternalThingML.g:5370:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5371:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5371:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5372:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getIncrementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_79); 

            					newLeafNode(otherlv_0, grammarAccess.getIncrementAccess().getVarVariableCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,83,FOLLOW_2); 

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
    // InternalThingML.g:5391:1: entryRuleDecrement returns [EObject current=null] : iv_ruleDecrement= ruleDecrement EOF ;
    public final EObject entryRuleDecrement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDecrement = null;


        try {
            // InternalThingML.g:5391:50: (iv_ruleDecrement= ruleDecrement EOF )
            // InternalThingML.g:5392:2: iv_ruleDecrement= ruleDecrement EOF
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
    // InternalThingML.g:5398:1: ruleDecrement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' ) ;
    public final EObject ruleDecrement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:5404:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' ) )
            // InternalThingML.g:5405:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' )
            {
            // InternalThingML.g:5405:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' )
            // InternalThingML.g:5406:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '--'
            {
            // InternalThingML.g:5406:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5407:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5407:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5408:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDecrementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_80); 

            					newLeafNode(otherlv_0, grammarAccess.getDecrementAccess().getVarVariableCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,84,FOLLOW_2); 

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
    // InternalThingML.g:5427:1: entryRuleLoopAction returns [EObject current=null] : iv_ruleLoopAction= ruleLoopAction EOF ;
    public final EObject entryRuleLoopAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLoopAction = null;


        try {
            // InternalThingML.g:5427:51: (iv_ruleLoopAction= ruleLoopAction EOF )
            // InternalThingML.g:5428:2: iv_ruleLoopAction= ruleLoopAction EOF
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
    // InternalThingML.g:5434:1: ruleLoopAction returns [EObject current=null] : (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) ) ;
    public final EObject ruleLoopAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_condition_2_0 = null;

        EObject lv_action_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5440:2: ( (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) ) )
            // InternalThingML.g:5441:2: (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) )
            {
            // InternalThingML.g:5441:2: (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) )
            // InternalThingML.g:5442:3: otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) )
            {
            otherlv_0=(Token)match(input,85,FOLLOW_24); 

            			newLeafNode(otherlv_0, grammarAccess.getLoopActionAccess().getWhileKeyword_0());
            		
            otherlv_1=(Token)match(input,33,FOLLOW_22); 

            			newLeafNode(otherlv_1, grammarAccess.getLoopActionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:5450:3: ( (lv_condition_2_0= ruleExpression ) )
            // InternalThingML.g:5451:4: (lv_condition_2_0= ruleExpression )
            {
            // InternalThingML.g:5451:4: (lv_condition_2_0= ruleExpression )
            // InternalThingML.g:5452:5: lv_condition_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getLoopActionAccess().getConditionExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_81);
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

            otherlv_3=(Token)match(input,34,FOLLOW_27); 

            			newLeafNode(otherlv_3, grammarAccess.getLoopActionAccess().getRightParenthesisKeyword_3());
            		
            // InternalThingML.g:5473:3: ( (lv_action_4_0= ruleAction ) )
            // InternalThingML.g:5474:4: (lv_action_4_0= ruleAction )
            {
            // InternalThingML.g:5474:4: (lv_action_4_0= ruleAction )
            // InternalThingML.g:5475:5: lv_action_4_0= ruleAction
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
    // InternalThingML.g:5496:1: entryRuleConditionalAction returns [EObject current=null] : iv_ruleConditionalAction= ruleConditionalAction EOF ;
    public final EObject entryRuleConditionalAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionalAction = null;


        try {
            // InternalThingML.g:5496:58: (iv_ruleConditionalAction= ruleConditionalAction EOF )
            // InternalThingML.g:5497:2: iv_ruleConditionalAction= ruleConditionalAction EOF
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
    // InternalThingML.g:5503:1: ruleConditionalAction returns [EObject current=null] : (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? ) ;
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
            // InternalThingML.g:5509:2: ( (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? ) )
            // InternalThingML.g:5510:2: (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:5510:2: (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? )
            // InternalThingML.g:5511:3: otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )?
            {
            otherlv_0=(Token)match(input,57,FOLLOW_24); 

            			newLeafNode(otherlv_0, grammarAccess.getConditionalActionAccess().getIfKeyword_0());
            		
            otherlv_1=(Token)match(input,33,FOLLOW_22); 

            			newLeafNode(otherlv_1, grammarAccess.getConditionalActionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:5519:3: ( (lv_condition_2_0= ruleExpression ) )
            // InternalThingML.g:5520:4: (lv_condition_2_0= ruleExpression )
            {
            // InternalThingML.g:5520:4: (lv_condition_2_0= ruleExpression )
            // InternalThingML.g:5521:5: lv_condition_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getConditionalActionAccess().getConditionExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_81);
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

            otherlv_3=(Token)match(input,34,FOLLOW_27); 

            			newLeafNode(otherlv_3, grammarAccess.getConditionalActionAccess().getRightParenthesisKeyword_3());
            		
            // InternalThingML.g:5542:3: ( (lv_action_4_0= ruleAction ) )
            // InternalThingML.g:5543:4: (lv_action_4_0= ruleAction )
            {
            // InternalThingML.g:5543:4: (lv_action_4_0= ruleAction )
            // InternalThingML.g:5544:5: lv_action_4_0= ruleAction
            {

            					newCompositeNode(grammarAccess.getConditionalActionAccess().getActionActionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_82);
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

            // InternalThingML.g:5561:3: (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )?
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==86) ) {
                alt116=1;
            }
            switch (alt116) {
                case 1 :
                    // InternalThingML.g:5562:4: otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) )
                    {
                    otherlv_5=(Token)match(input,86,FOLLOW_27); 

                    				newLeafNode(otherlv_5, grammarAccess.getConditionalActionAccess().getElseKeyword_5_0());
                    			
                    // InternalThingML.g:5566:4: ( (lv_elseAction_6_0= ruleAction ) )
                    // InternalThingML.g:5567:5: (lv_elseAction_6_0= ruleAction )
                    {
                    // InternalThingML.g:5567:5: (lv_elseAction_6_0= ruleAction )
                    // InternalThingML.g:5568:6: lv_elseAction_6_0= ruleAction
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
    // InternalThingML.g:5590:1: entryRuleReturnAction returns [EObject current=null] : iv_ruleReturnAction= ruleReturnAction EOF ;
    public final EObject entryRuleReturnAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReturnAction = null;


        try {
            // InternalThingML.g:5590:53: (iv_ruleReturnAction= ruleReturnAction EOF )
            // InternalThingML.g:5591:2: iv_ruleReturnAction= ruleReturnAction EOF
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
    // InternalThingML.g:5597:1: ruleReturnAction returns [EObject current=null] : (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) ) ;
    public final EObject ruleReturnAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_exp_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5603:2: ( (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:5604:2: (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:5604:2: (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) )
            // InternalThingML.g:5605:3: otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,87,FOLLOW_22); 

            			newLeafNode(otherlv_0, grammarAccess.getReturnActionAccess().getReturnKeyword_0());
            		
            // InternalThingML.g:5609:3: ( (lv_exp_1_0= ruleExpression ) )
            // InternalThingML.g:5610:4: (lv_exp_1_0= ruleExpression )
            {
            // InternalThingML.g:5610:4: (lv_exp_1_0= ruleExpression )
            // InternalThingML.g:5611:5: lv_exp_1_0= ruleExpression
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
    // InternalThingML.g:5632:1: entryRulePrintAction returns [EObject current=null] : iv_rulePrintAction= rulePrintAction EOF ;
    public final EObject entryRulePrintAction() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrintAction = null;


        try {
            // InternalThingML.g:5632:52: (iv_rulePrintAction= rulePrintAction EOF )
            // InternalThingML.g:5633:2: iv_rulePrintAction= rulePrintAction EOF
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
    // InternalThingML.g:5639:1: rulePrintAction returns [EObject current=null] : (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) ) ;
    public final EObject rulePrintAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_msg_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5645:2: ( (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:5646:2: (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:5646:2: (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) )
            // InternalThingML.g:5647:3: otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,88,FOLLOW_22); 

            			newLeafNode(otherlv_0, grammarAccess.getPrintActionAccess().getPrintKeyword_0());
            		
            // InternalThingML.g:5651:3: ( (lv_msg_1_0= ruleExpression ) )
            // InternalThingML.g:5652:4: (lv_msg_1_0= ruleExpression )
            {
            // InternalThingML.g:5652:4: (lv_msg_1_0= ruleExpression )
            // InternalThingML.g:5653:5: lv_msg_1_0= ruleExpression
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
    // InternalThingML.g:5674:1: entryRuleErrorAction returns [EObject current=null] : iv_ruleErrorAction= ruleErrorAction EOF ;
    public final EObject entryRuleErrorAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleErrorAction = null;


        try {
            // InternalThingML.g:5674:52: (iv_ruleErrorAction= ruleErrorAction EOF )
            // InternalThingML.g:5675:2: iv_ruleErrorAction= ruleErrorAction EOF
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
    // InternalThingML.g:5681:1: ruleErrorAction returns [EObject current=null] : (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) ) ;
    public final EObject ruleErrorAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_msg_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5687:2: ( (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:5688:2: (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:5688:2: (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) )
            // InternalThingML.g:5689:3: otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,89,FOLLOW_22); 

            			newLeafNode(otherlv_0, grammarAccess.getErrorActionAccess().getErrorKeyword_0());
            		
            // InternalThingML.g:5693:3: ( (lv_msg_1_0= ruleExpression ) )
            // InternalThingML.g:5694:4: (lv_msg_1_0= ruleExpression )
            {
            // InternalThingML.g:5694:4: (lv_msg_1_0= ruleExpression )
            // InternalThingML.g:5695:5: lv_msg_1_0= ruleExpression
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
    // InternalThingML.g:5716:1: entryRuleStartSession returns [EObject current=null] : iv_ruleStartSession= ruleStartSession EOF ;
    public final EObject entryRuleStartSession() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStartSession = null;


        try {
            // InternalThingML.g:5716:53: (iv_ruleStartSession= ruleStartSession EOF )
            // InternalThingML.g:5717:2: iv_ruleStartSession= ruleStartSession EOF
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
    // InternalThingML.g:5723:1: ruleStartSession returns [EObject current=null] : (otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) ) ) ;
    public final EObject ruleStartSession() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:5729:2: ( (otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) ) ) )
            // InternalThingML.g:5730:2: (otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) ) )
            {
            // InternalThingML.g:5730:2: (otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) ) )
            // InternalThingML.g:5731:3: otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,90,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getStartSessionAccess().getForkKeyword_0());
            		
            // InternalThingML.g:5735:3: ( (otherlv_1= RULE_ID ) )
            // InternalThingML.g:5736:4: (otherlv_1= RULE_ID )
            {
            // InternalThingML.g:5736:4: (otherlv_1= RULE_ID )
            // InternalThingML.g:5737:5: otherlv_1= RULE_ID
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
    // InternalThingML.g:5752:1: entryRuleFunctionCallStatement returns [EObject current=null] : iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF ;
    public final EObject entryRuleFunctionCallStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCallStatement = null;


        try {
            // InternalThingML.g:5752:62: (iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF )
            // InternalThingML.g:5753:2: iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF
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
    // InternalThingML.g:5759:1: ruleFunctionCallStatement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) ;
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
            // InternalThingML.g:5765:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) )
            // InternalThingML.g:5766:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            {
            // InternalThingML.g:5766:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            // InternalThingML.g:5767:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')'
            {
            // InternalThingML.g:5767:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5768:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5768:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5769:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFunctionCallStatementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_24); 

            					newLeafNode(otherlv_0, grammarAccess.getFunctionCallStatementAccess().getFunctionFunctionCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,33,FOLLOW_78); 

            			newLeafNode(otherlv_1, grammarAccess.getFunctionCallStatementAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:5784:3: ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )?
            int alt118=2;
            int LA118_0 = input.LA(1);

            if ( ((LA118_0>=RULE_STRING_LIT && LA118_0<=RULE_FLOAT)||LA118_0==33||LA118_0==98||(LA118_0>=102 && LA118_0<=104)) ) {
                alt118=1;
            }
            switch (alt118) {
                case 1 :
                    // InternalThingML.g:5785:4: ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    {
                    // InternalThingML.g:5785:4: ( (lv_parameters_2_0= ruleExpression ) )
                    // InternalThingML.g:5786:5: (lv_parameters_2_0= ruleExpression )
                    {
                    // InternalThingML.g:5786:5: (lv_parameters_2_0= ruleExpression )
                    // InternalThingML.g:5787:6: lv_parameters_2_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_26);
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

                    // InternalThingML.g:5804:4: (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    loop117:
                    do {
                        int alt117=2;
                        int LA117_0 = input.LA(1);

                        if ( (LA117_0==28) ) {
                            alt117=1;
                        }


                        switch (alt117) {
                    	case 1 :
                    	    // InternalThingML.g:5805:5: otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,28,FOLLOW_22); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getFunctionCallStatementAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalThingML.g:5809:5: ( (lv_parameters_4_0= ruleExpression ) )
                    	    // InternalThingML.g:5810:6: (lv_parameters_4_0= ruleExpression )
                    	    {
                    	    // InternalThingML.g:5810:6: (lv_parameters_4_0= ruleExpression )
                    	    // InternalThingML.g:5811:7: lv_parameters_4_0= ruleExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
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
                    	    break loop117;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,34,FOLLOW_2); 

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
    // InternalThingML.g:5838:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // InternalThingML.g:5838:51: (iv_ruleExpression= ruleExpression EOF )
            // InternalThingML.g:5839:2: iv_ruleExpression= ruleExpression EOF
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
    // InternalThingML.g:5845:1: ruleExpression returns [EObject current=null] : this_OrExpression_0= ruleOrExpression ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject this_OrExpression_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5851:2: (this_OrExpression_0= ruleOrExpression )
            // InternalThingML.g:5852:2: this_OrExpression_0= ruleOrExpression
            {

            		newCompositeNode(grammarAccess.getExpressionAccess().getOrExpressionParserRuleCall());
            	
            pushFollow(FOLLOW_2);
            this_OrExpression_0=ruleOrExpression();

            state._fsp--;


            		current = this_OrExpression_0;
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


    // $ANTLR start "entryRuleOrExpression"
    // InternalThingML.g:5863:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // InternalThingML.g:5863:53: (iv_ruleOrExpression= ruleOrExpression EOF )
            // InternalThingML.g:5864:2: iv_ruleOrExpression= ruleOrExpression EOF
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
    // InternalThingML.g:5870:1: ruleOrExpression returns [EObject current=null] : (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_AndExpression_0 = null;

        EObject lv_rhs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5876:2: ( (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* ) )
            // InternalThingML.g:5877:2: (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* )
            {
            // InternalThingML.g:5877:2: (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* )
            // InternalThingML.g:5878:3: this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getOrExpressionAccess().getAndExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_83);
            this_AndExpression_0=ruleAndExpression();

            state._fsp--;


            			current = this_AndExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5886:3: ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )*
            loop119:
            do {
                int alt119=2;
                int LA119_0 = input.LA(1);

                if ( (LA119_0==91) ) {
                    alt119=1;
                }


                switch (alt119) {
            	case 1 :
            	    // InternalThingML.g:5887:4: () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) )
            	    {
            	    // InternalThingML.g:5887:4: ()
            	    // InternalThingML.g:5888:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getOrExpressionAccess().getOrExpressionLhsAction_1_0(),
            	    						current);
            	    				

            	    }

            	    otherlv_2=(Token)match(input,91,FOLLOW_22); 

            	    				newLeafNode(otherlv_2, grammarAccess.getOrExpressionAccess().getOrKeyword_1_1());
            	    			
            	    // InternalThingML.g:5898:4: ( (lv_rhs_3_0= ruleAndExpression ) )
            	    // InternalThingML.g:5899:5: (lv_rhs_3_0= ruleAndExpression )
            	    {
            	    // InternalThingML.g:5899:5: (lv_rhs_3_0= ruleAndExpression )
            	    // InternalThingML.g:5900:6: lv_rhs_3_0= ruleAndExpression
            	    {

            	    						newCompositeNode(grammarAccess.getOrExpressionAccess().getRhsAndExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_83);
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
            	    break loop119;
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
    // InternalThingML.g:5922:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // InternalThingML.g:5922:54: (iv_ruleAndExpression= ruleAndExpression EOF )
            // InternalThingML.g:5923:2: iv_ruleAndExpression= ruleAndExpression EOF
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
    // InternalThingML.g:5929:1: ruleAndExpression returns [EObject current=null] : (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Equality_0 = null;

        EObject lv_rhs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5935:2: ( (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* ) )
            // InternalThingML.g:5936:2: (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* )
            {
            // InternalThingML.g:5936:2: (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* )
            // InternalThingML.g:5937:3: this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )*
            {

            			newCompositeNode(grammarAccess.getAndExpressionAccess().getEqualityParserRuleCall_0());
            		
            pushFollow(FOLLOW_84);
            this_Equality_0=ruleEquality();

            state._fsp--;


            			current = this_Equality_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5945:3: ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )*
            loop120:
            do {
                int alt120=2;
                int LA120_0 = input.LA(1);

                if ( (LA120_0==92) ) {
                    alt120=1;
                }


                switch (alt120) {
            	case 1 :
            	    // InternalThingML.g:5946:4: () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) )
            	    {
            	    // InternalThingML.g:5946:4: ()
            	    // InternalThingML.g:5947:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAndExpressionAccess().getAndExpressionLhsAction_1_0(),
            	    						current);
            	    				

            	    }

            	    otherlv_2=(Token)match(input,92,FOLLOW_22); 

            	    				newLeafNode(otherlv_2, grammarAccess.getAndExpressionAccess().getAndKeyword_1_1());
            	    			
            	    // InternalThingML.g:5957:4: ( (lv_rhs_3_0= ruleEquality ) )
            	    // InternalThingML.g:5958:5: (lv_rhs_3_0= ruleEquality )
            	    {
            	    // InternalThingML.g:5958:5: (lv_rhs_3_0= ruleEquality )
            	    // InternalThingML.g:5959:6: lv_rhs_3_0= ruleEquality
            	    {

            	    						newCompositeNode(grammarAccess.getAndExpressionAccess().getRhsEqualityParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_84);
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
            	    break loop120;
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
    // InternalThingML.g:5981:1: entryRuleEquality returns [EObject current=null] : iv_ruleEquality= ruleEquality EOF ;
    public final EObject entryRuleEquality() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEquality = null;


        try {
            // InternalThingML.g:5981:49: (iv_ruleEquality= ruleEquality EOF )
            // InternalThingML.g:5982:2: iv_ruleEquality= ruleEquality EOF
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
    // InternalThingML.g:5988:1: ruleEquality returns [EObject current=null] : (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* ) ;
    public final EObject ruleEquality() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Comparaison_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5994:2: ( (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* ) )
            // InternalThingML.g:5995:2: (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* )
            {
            // InternalThingML.g:5995:2: (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* )
            // InternalThingML.g:5996:3: this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getEqualityAccess().getComparaisonParserRuleCall_0());
            		
            pushFollow(FOLLOW_85);
            this_Comparaison_0=ruleComparaison();

            state._fsp--;


            			current = this_Comparaison_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:6004:3: ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )*
            loop121:
            do {
                int alt121=3;
                int LA121_0 = input.LA(1);

                if ( (LA121_0==93) ) {
                    alt121=1;
                }
                else if ( (LA121_0==94) ) {
                    alt121=2;
                }


                switch (alt121) {
            	case 1 :
            	    // InternalThingML.g:6005:4: ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) )
            	    {
            	    // InternalThingML.g:6005:4: ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) )
            	    // InternalThingML.g:6006:5: () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) )
            	    {
            	    // InternalThingML.g:6006:5: ()
            	    // InternalThingML.g:6007:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getEqualityAccess().getEqualsExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,93,FOLLOW_22); 

            	    					newLeafNode(otherlv_2, grammarAccess.getEqualityAccess().getEqualsSignEqualsSignKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:6017:5: ( (lv_rhs_3_0= ruleComparaison ) )
            	    // InternalThingML.g:6018:6: (lv_rhs_3_0= ruleComparaison )
            	    {
            	    // InternalThingML.g:6018:6: (lv_rhs_3_0= ruleComparaison )
            	    // InternalThingML.g:6019:7: lv_rhs_3_0= ruleComparaison
            	    {

            	    							newCompositeNode(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_85);
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
            	    // InternalThingML.g:6038:4: ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) )
            	    {
            	    // InternalThingML.g:6038:4: ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) )
            	    // InternalThingML.g:6039:5: () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) )
            	    {
            	    // InternalThingML.g:6039:5: ()
            	    // InternalThingML.g:6040:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getEqualityAccess().getNotEqualsExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,94,FOLLOW_22); 

            	    					newLeafNode(otherlv_5, grammarAccess.getEqualityAccess().getExclamationMarkEqualsSignKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:6050:5: ( (lv_rhs_6_0= ruleComparaison ) )
            	    // InternalThingML.g:6051:6: (lv_rhs_6_0= ruleComparaison )
            	    {
            	    // InternalThingML.g:6051:6: (lv_rhs_6_0= ruleComparaison )
            	    // InternalThingML.g:6052:7: lv_rhs_6_0= ruleComparaison
            	    {

            	    							newCompositeNode(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_85);
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
            	    break loop121;
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
    // InternalThingML.g:6075:1: entryRuleComparaison returns [EObject current=null] : iv_ruleComparaison= ruleComparaison EOF ;
    public final EObject entryRuleComparaison() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComparaison = null;


        try {
            // InternalThingML.g:6075:52: (iv_ruleComparaison= ruleComparaison EOF )
            // InternalThingML.g:6076:2: iv_ruleComparaison= ruleComparaison EOF
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
    // InternalThingML.g:6082:1: ruleComparaison returns [EObject current=null] : (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* ) ;
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
            // InternalThingML.g:6088:2: ( (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* ) )
            // InternalThingML.g:6089:2: (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* )
            {
            // InternalThingML.g:6089:2: (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* )
            // InternalThingML.g:6090:3: this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getComparaisonAccess().getAdditionParserRuleCall_0());
            		
            pushFollow(FOLLOW_86);
            this_Addition_0=ruleAddition();

            state._fsp--;


            			current = this_Addition_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:6098:3: ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )*
            loop122:
            do {
                int alt122=5;
                switch ( input.LA(1) ) {
                case 19:
                    {
                    alt122=1;
                    }
                    break;
                case 18:
                    {
                    alt122=2;
                    }
                    break;
                case 95:
                    {
                    alt122=3;
                    }
                    break;
                case 96:
                    {
                    alt122=4;
                    }
                    break;

                }

                switch (alt122) {
            	case 1 :
            	    // InternalThingML.g:6099:4: ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:6099:4: ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) )
            	    // InternalThingML.g:6100:5: () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:6100:5: ()
            	    // InternalThingML.g:6101:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getGreaterExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,19,FOLLOW_22); 

            	    					newLeafNode(otherlv_2, grammarAccess.getComparaisonAccess().getGreaterThanSignKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:6111:5: ( (lv_rhs_3_0= ruleAddition ) )
            	    // InternalThingML.g:6112:6: (lv_rhs_3_0= ruleAddition )
            	    {
            	    // InternalThingML.g:6112:6: (lv_rhs_3_0= ruleAddition )
            	    // InternalThingML.g:6113:7: lv_rhs_3_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_86);
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
            	    // InternalThingML.g:6132:4: ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:6132:4: ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) )
            	    // InternalThingML.g:6133:5: () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:6133:5: ()
            	    // InternalThingML.g:6134:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getLowerExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,18,FOLLOW_22); 

            	    					newLeafNode(otherlv_5, grammarAccess.getComparaisonAccess().getLessThanSignKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:6144:5: ( (lv_rhs_6_0= ruleAddition ) )
            	    // InternalThingML.g:6145:6: (lv_rhs_6_0= ruleAddition )
            	    {
            	    // InternalThingML.g:6145:6: (lv_rhs_6_0= ruleAddition )
            	    // InternalThingML.g:6146:7: lv_rhs_6_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_86);
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
            	    // InternalThingML.g:6165:4: ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:6165:4: ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) )
            	    // InternalThingML.g:6166:5: () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:6166:5: ()
            	    // InternalThingML.g:6167:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getGreaterOrEqualExpressionLhsAction_1_2_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_8=(Token)match(input,95,FOLLOW_22); 

            	    					newLeafNode(otherlv_8, grammarAccess.getComparaisonAccess().getGreaterThanSignEqualsSignKeyword_1_2_1());
            	    				
            	    // InternalThingML.g:6177:5: ( (lv_rhs_9_0= ruleAddition ) )
            	    // InternalThingML.g:6178:6: (lv_rhs_9_0= ruleAddition )
            	    {
            	    // InternalThingML.g:6178:6: (lv_rhs_9_0= ruleAddition )
            	    // InternalThingML.g:6179:7: lv_rhs_9_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_2_2_0());
            	    						
            	    pushFollow(FOLLOW_86);
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
            	    // InternalThingML.g:6198:4: ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:6198:4: ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) )
            	    // InternalThingML.g:6199:5: () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:6199:5: ()
            	    // InternalThingML.g:6200:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getLowerOrEqualExpressionLhsAction_1_3_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_11=(Token)match(input,96,FOLLOW_22); 

            	    					newLeafNode(otherlv_11, grammarAccess.getComparaisonAccess().getLessThanSignEqualsSignKeyword_1_3_1());
            	    				
            	    // InternalThingML.g:6210:5: ( (lv_rhs_12_0= ruleAddition ) )
            	    // InternalThingML.g:6211:6: (lv_rhs_12_0= ruleAddition )
            	    {
            	    // InternalThingML.g:6211:6: (lv_rhs_12_0= ruleAddition )
            	    // InternalThingML.g:6212:7: lv_rhs_12_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_3_2_0());
            	    						
            	    pushFollow(FOLLOW_86);
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
            	    break loop122;
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
    // InternalThingML.g:6235:1: entryRuleAddition returns [EObject current=null] : iv_ruleAddition= ruleAddition EOF ;
    public final EObject entryRuleAddition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAddition = null;


        try {
            // InternalThingML.g:6235:49: (iv_ruleAddition= ruleAddition EOF )
            // InternalThingML.g:6236:2: iv_ruleAddition= ruleAddition EOF
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
    // InternalThingML.g:6242:1: ruleAddition returns [EObject current=null] : (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* ) ;
    public final EObject ruleAddition() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Multiplication_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6248:2: ( (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* ) )
            // InternalThingML.g:6249:2: (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* )
            {
            // InternalThingML.g:6249:2: (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* )
            // InternalThingML.g:6250:3: this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0());
            		
            pushFollow(FOLLOW_87);
            this_Multiplication_0=ruleMultiplication();

            state._fsp--;


            			current = this_Multiplication_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:6258:3: ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )*
            loop123:
            do {
                int alt123=3;
                int LA123_0 = input.LA(1);

                if ( (LA123_0==97) ) {
                    alt123=1;
                }
                else if ( (LA123_0==98) ) {
                    alt123=2;
                }


                switch (alt123) {
            	case 1 :
            	    // InternalThingML.g:6259:4: ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) )
            	    {
            	    // InternalThingML.g:6259:4: ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) )
            	    // InternalThingML.g:6260:5: () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) )
            	    {
            	    // InternalThingML.g:6260:5: ()
            	    // InternalThingML.g:6261:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getAdditionAccess().getPlusExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,97,FOLLOW_22); 

            	    					newLeafNode(otherlv_2, grammarAccess.getAdditionAccess().getPlusSignKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:6271:5: ( (lv_rhs_3_0= ruleMultiplication ) )
            	    // InternalThingML.g:6272:6: (lv_rhs_3_0= ruleMultiplication )
            	    {
            	    // InternalThingML.g:6272:6: (lv_rhs_3_0= ruleMultiplication )
            	    // InternalThingML.g:6273:7: lv_rhs_3_0= ruleMultiplication
            	    {

            	    							newCompositeNode(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_87);
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
            	    // InternalThingML.g:6292:4: ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) )
            	    {
            	    // InternalThingML.g:6292:4: ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) )
            	    // InternalThingML.g:6293:5: () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) )
            	    {
            	    // InternalThingML.g:6293:5: ()
            	    // InternalThingML.g:6294:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getAdditionAccess().getMinusExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,98,FOLLOW_22); 

            	    					newLeafNode(otherlv_5, grammarAccess.getAdditionAccess().getHyphenMinusKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:6304:5: ( (lv_rhs_6_0= ruleMultiplication ) )
            	    // InternalThingML.g:6305:6: (lv_rhs_6_0= ruleMultiplication )
            	    {
            	    // InternalThingML.g:6305:6: (lv_rhs_6_0= ruleMultiplication )
            	    // InternalThingML.g:6306:7: lv_rhs_6_0= ruleMultiplication
            	    {

            	    							newCompositeNode(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_87);
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
    // $ANTLR end "ruleAddition"


    // $ANTLR start "entryRuleMultiplication"
    // InternalThingML.g:6329:1: entryRuleMultiplication returns [EObject current=null] : iv_ruleMultiplication= ruleMultiplication EOF ;
    public final EObject entryRuleMultiplication() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplication = null;


        try {
            // InternalThingML.g:6329:55: (iv_ruleMultiplication= ruleMultiplication EOF )
            // InternalThingML.g:6330:2: iv_ruleMultiplication= ruleMultiplication EOF
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
    // InternalThingML.g:6336:1: ruleMultiplication returns [EObject current=null] : (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* ) ;
    public final EObject ruleMultiplication() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Modulo_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6342:2: ( (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* ) )
            // InternalThingML.g:6343:2: (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* )
            {
            // InternalThingML.g:6343:2: (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* )
            // InternalThingML.g:6344:3: this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getMultiplicationAccess().getModuloParserRuleCall_0());
            		
            pushFollow(FOLLOW_88);
            this_Modulo_0=ruleModulo();

            state._fsp--;


            			current = this_Modulo_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:6352:3: ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )*
            loop124:
            do {
                int alt124=3;
                int LA124_0 = input.LA(1);

                if ( (LA124_0==99) ) {
                    alt124=1;
                }
                else if ( (LA124_0==100) ) {
                    alt124=2;
                }


                switch (alt124) {
            	case 1 :
            	    // InternalThingML.g:6353:4: ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) )
            	    {
            	    // InternalThingML.g:6353:4: ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) )
            	    // InternalThingML.g:6354:5: () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) )
            	    {
            	    // InternalThingML.g:6354:5: ()
            	    // InternalThingML.g:6355:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getMultiplicationAccess().getTimesExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,99,FOLLOW_22); 

            	    					newLeafNode(otherlv_2, grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:6365:5: ( (lv_rhs_3_0= ruleModulo ) )
            	    // InternalThingML.g:6366:6: (lv_rhs_3_0= ruleModulo )
            	    {
            	    // InternalThingML.g:6366:6: (lv_rhs_3_0= ruleModulo )
            	    // InternalThingML.g:6367:7: lv_rhs_3_0= ruleModulo
            	    {

            	    							newCompositeNode(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_88);
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
            	    // InternalThingML.g:6386:4: ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) )
            	    {
            	    // InternalThingML.g:6386:4: ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) )
            	    // InternalThingML.g:6387:5: () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) )
            	    {
            	    // InternalThingML.g:6387:5: ()
            	    // InternalThingML.g:6388:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getMultiplicationAccess().getDivExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,100,FOLLOW_22); 

            	    					newLeafNode(otherlv_5, grammarAccess.getMultiplicationAccess().getSolidusKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:6398:5: ( (lv_rhs_6_0= ruleModulo ) )
            	    // InternalThingML.g:6399:6: (lv_rhs_6_0= ruleModulo )
            	    {
            	    // InternalThingML.g:6399:6: (lv_rhs_6_0= ruleModulo )
            	    // InternalThingML.g:6400:7: lv_rhs_6_0= ruleModulo
            	    {

            	    							newCompositeNode(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_88);
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
            	    break loop124;
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
    // InternalThingML.g:6423:1: entryRuleModulo returns [EObject current=null] : iv_ruleModulo= ruleModulo EOF ;
    public final EObject entryRuleModulo() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModulo = null;


        try {
            // InternalThingML.g:6423:47: (iv_ruleModulo= ruleModulo EOF )
            // InternalThingML.g:6424:2: iv_ruleModulo= ruleModulo EOF
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
    // InternalThingML.g:6430:1: ruleModulo returns [EObject current=null] : (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? ) ;
    public final EObject ruleModulo() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Primary_0 = null;

        EObject lv_rhs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6436:2: ( (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? ) )
            // InternalThingML.g:6437:2: (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? )
            {
            // InternalThingML.g:6437:2: (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? )
            // InternalThingML.g:6438:3: this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )?
            {

            			newCompositeNode(grammarAccess.getModuloAccess().getPrimaryParserRuleCall_0());
            		
            pushFollow(FOLLOW_89);
            this_Primary_0=rulePrimary();

            state._fsp--;


            			current = this_Primary_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:6446:3: ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )?
            int alt125=2;
            int LA125_0 = input.LA(1);

            if ( (LA125_0==101) ) {
                alt125=1;
            }
            switch (alt125) {
                case 1 :
                    // InternalThingML.g:6447:4: () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) )
                    {
                    // InternalThingML.g:6447:4: ()
                    // InternalThingML.g:6448:5: 
                    {

                    					current = forceCreateModelElementAndSet(
                    						grammarAccess.getModuloAccess().getModExpressionLhsAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_2=(Token)match(input,101,FOLLOW_22); 

                    				newLeafNode(otherlv_2, grammarAccess.getModuloAccess().getPercentSignKeyword_1_1());
                    			
                    // InternalThingML.g:6458:4: ( (lv_rhs_3_0= ruleExpression ) )
                    // InternalThingML.g:6459:5: (lv_rhs_3_0= ruleExpression )
                    {
                    // InternalThingML.g:6459:5: (lv_rhs_3_0= ruleExpression )
                    // InternalThingML.g:6460:6: lv_rhs_3_0= ruleExpression
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
    // InternalThingML.g:6482:1: entryRulePrimary returns [EObject current=null] : iv_rulePrimary= rulePrimary EOF ;
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary = null;


        try {
            // InternalThingML.g:6482:48: (iv_rulePrimary= rulePrimary EOF )
            // InternalThingML.g:6483:2: iv_rulePrimary= rulePrimary EOF
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
    // InternalThingML.g:6489:1: rulePrimary returns [EObject current=null] : ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_ArrayIndexPostfix_9= ruleArrayIndexPostfix ) ;
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
            // InternalThingML.g:6495:2: ( ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_ArrayIndexPostfix_9= ruleArrayIndexPostfix ) )
            // InternalThingML.g:6496:2: ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_ArrayIndexPostfix_9= ruleArrayIndexPostfix )
            {
            // InternalThingML.g:6496:2: ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_ArrayIndexPostfix_9= ruleArrayIndexPostfix )
            int alt126=4;
            switch ( input.LA(1) ) {
            case 33:
                {
                alt126=1;
                }
                break;
            case 102:
                {
                alt126=2;
                }
                break;
            case 98:
                {
                alt126=3;
                }
                break;
            case RULE_STRING_LIT:
            case RULE_ID:
            case RULE_INT:
            case RULE_STRING_EXT:
            case RULE_FLOAT:
            case 103:
            case 104:
                {
                alt126=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 126, 0, input);

                throw nvae;
            }

            switch (alt126) {
                case 1 :
                    // InternalThingML.g:6497:3: (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' )
                    {
                    // InternalThingML.g:6497:3: (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' )
                    // InternalThingML.g:6498:4: otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')'
                    {
                    otherlv_0=(Token)match(input,33,FOLLOW_22); 

                    				newLeafNode(otherlv_0, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_0_0());
                    			

                    				newCompositeNode(grammarAccess.getPrimaryAccess().getExpressionParserRuleCall_0_1());
                    			
                    pushFollow(FOLLOW_81);
                    this_Expression_1=ruleExpression();

                    state._fsp--;


                    				current = this_Expression_1;
                    				afterParserOrEnumRuleCall();
                    			
                    otherlv_2=(Token)match(input,34,FOLLOW_2); 

                    				newLeafNode(otherlv_2, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_0_2());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalThingML.g:6516:3: ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) )
                    {
                    // InternalThingML.g:6516:3: ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) )
                    // InternalThingML.g:6517:4: () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) )
                    {
                    // InternalThingML.g:6517:4: ()
                    // InternalThingML.g:6518:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPrimaryAccess().getNotExpressionAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_4=(Token)match(input,102,FOLLOW_22); 

                    				newLeafNode(otherlv_4, grammarAccess.getPrimaryAccess().getNotKeyword_1_1());
                    			
                    // InternalThingML.g:6528:4: ( (lv_term_5_0= rulePrimary ) )
                    // InternalThingML.g:6529:5: (lv_term_5_0= rulePrimary )
                    {
                    // InternalThingML.g:6529:5: (lv_term_5_0= rulePrimary )
                    // InternalThingML.g:6530:6: lv_term_5_0= rulePrimary
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
                    // InternalThingML.g:6549:3: ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) )
                    {
                    // InternalThingML.g:6549:3: ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) )
                    // InternalThingML.g:6550:4: () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) )
                    {
                    // InternalThingML.g:6550:4: ()
                    // InternalThingML.g:6551:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPrimaryAccess().getUnaryMinusAction_2_0(),
                    						current);
                    				

                    }

                    otherlv_7=(Token)match(input,98,FOLLOW_22); 

                    				newLeafNode(otherlv_7, grammarAccess.getPrimaryAccess().getHyphenMinusKeyword_2_1());
                    			
                    // InternalThingML.g:6561:4: ( (lv_term_8_0= rulePrimary ) )
                    // InternalThingML.g:6562:5: (lv_term_8_0= rulePrimary )
                    {
                    // InternalThingML.g:6562:5: (lv_term_8_0= rulePrimary )
                    // InternalThingML.g:6563:6: lv_term_8_0= rulePrimary
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
                    // InternalThingML.g:6582:3: this_ArrayIndexPostfix_9= ruleArrayIndexPostfix
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
    // InternalThingML.g:6594:1: entryRuleArrayIndexPostfix returns [EObject current=null] : iv_ruleArrayIndexPostfix= ruleArrayIndexPostfix EOF ;
    public final EObject entryRuleArrayIndexPostfix() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayIndexPostfix = null;


        try {
            // InternalThingML.g:6594:58: (iv_ruleArrayIndexPostfix= ruleArrayIndexPostfix EOF )
            // InternalThingML.g:6595:2: iv_ruleArrayIndexPostfix= ruleArrayIndexPostfix EOF
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
    // InternalThingML.g:6601:1: ruleArrayIndexPostfix returns [EObject current=null] : (this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )? ) ;
    public final EObject ruleArrayIndexPostfix() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_AtomicExpression_0 = null;

        EObject lv_index_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6607:2: ( (this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )? ) )
            // InternalThingML.g:6608:2: (this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )? )
            {
            // InternalThingML.g:6608:2: (this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )? )
            // InternalThingML.g:6609:3: this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )?
            {

            			newCompositeNode(grammarAccess.getArrayIndexPostfixAccess().getAtomicExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_7);
            this_AtomicExpression_0=ruleAtomicExpression();

            state._fsp--;


            			current = this_AtomicExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:6617:3: ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )?
            int alt127=2;
            int LA127_0 = input.LA(1);

            if ( (LA127_0==15) ) {
                alt127=1;
            }
            switch (alt127) {
                case 1 :
                    // InternalThingML.g:6618:4: () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']'
                    {
                    // InternalThingML.g:6618:4: ()
                    // InternalThingML.g:6619:5: 
                    {

                    					current = forceCreateModelElementAndSet(
                    						grammarAccess.getArrayIndexPostfixAccess().getArrayIndexArrayAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_2=(Token)match(input,15,FOLLOW_22); 

                    				newLeafNode(otherlv_2, grammarAccess.getArrayIndexPostfixAccess().getLeftSquareBracketKeyword_1_1());
                    			
                    // InternalThingML.g:6629:4: ( (lv_index_3_0= ruleExpression ) )
                    // InternalThingML.g:6630:5: (lv_index_3_0= ruleExpression )
                    {
                    // InternalThingML.g:6630:5: (lv_index_3_0= ruleExpression )
                    // InternalThingML.g:6631:6: lv_index_3_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getArrayIndexPostfixAccess().getIndexExpressionParserRuleCall_1_2_0());
                    					
                    pushFollow(FOLLOW_9);
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

                    otherlv_4=(Token)match(input,16,FOLLOW_2); 

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
    // InternalThingML.g:6657:1: entryRuleAtomicExpression returns [EObject current=null] : iv_ruleAtomicExpression= ruleAtomicExpression EOF ;
    public final EObject entryRuleAtomicExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtomicExpression = null;


        try {
            // InternalThingML.g:6657:57: (iv_ruleAtomicExpression= ruleAtomicExpression EOF )
            // InternalThingML.g:6658:2: iv_ruleAtomicExpression= ruleAtomicExpression EOF
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
    // InternalThingML.g:6664:1: ruleAtomicExpression returns [EObject current=null] : (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_Reference_7= ruleReference | this_FunctionCallExpression_8= ruleFunctionCallExpression ) ;
    public final EObject ruleAtomicExpression() throws RecognitionException {
        EObject current = null;

        EObject this_ExternExpression_0 = null;

        EObject this_EnumLiteralRef_1 = null;

        EObject this_IntegerLiteral_2 = null;

        EObject this_BooleanLiteral_3 = null;

        EObject this_StringLiteral_4 = null;

        EObject this_DoubleLiteral_5 = null;

        EObject this_PropertyReference_6 = null;

        EObject this_Reference_7 = null;

        EObject this_FunctionCallExpression_8 = null;



        	enterRule();

        try {
            // InternalThingML.g:6670:2: ( (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_Reference_7= ruleReference | this_FunctionCallExpression_8= ruleFunctionCallExpression ) )
            // InternalThingML.g:6671:2: (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_Reference_7= ruleReference | this_FunctionCallExpression_8= ruleFunctionCallExpression )
            {
            // InternalThingML.g:6671:2: (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_Reference_7= ruleReference | this_FunctionCallExpression_8= ruleFunctionCallExpression )
            int alt128=9;
            alt128 = dfa128.predict(input);
            switch (alt128) {
                case 1 :
                    // InternalThingML.g:6672:3: this_ExternExpression_0= ruleExternExpression
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
                    // InternalThingML.g:6681:3: this_EnumLiteralRef_1= ruleEnumLiteralRef
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
                    // InternalThingML.g:6690:3: this_IntegerLiteral_2= ruleIntegerLiteral
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
                    // InternalThingML.g:6699:3: this_BooleanLiteral_3= ruleBooleanLiteral
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
                    // InternalThingML.g:6708:3: this_StringLiteral_4= ruleStringLiteral
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
                    // InternalThingML.g:6717:3: this_DoubleLiteral_5= ruleDoubleLiteral
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
                    // InternalThingML.g:6726:3: this_PropertyReference_6= rulePropertyReference
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
                    // InternalThingML.g:6735:3: this_Reference_7= ruleReference
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getReferenceParserRuleCall_7());
                    		
                    pushFollow(FOLLOW_2);
                    this_Reference_7=ruleReference();

                    state._fsp--;


                    			current = this_Reference_7;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 9 :
                    // InternalThingML.g:6744:3: this_FunctionCallExpression_8= ruleFunctionCallExpression
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getFunctionCallExpressionParserRuleCall_8());
                    		
                    pushFollow(FOLLOW_2);
                    this_FunctionCallExpression_8=ruleFunctionCallExpression();

                    state._fsp--;


                    			current = this_FunctionCallExpression_8;
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
    // InternalThingML.g:6756:1: entryRuleExternExpression returns [EObject current=null] : iv_ruleExternExpression= ruleExternExpression EOF ;
    public final EObject entryRuleExternExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternExpression = null;


        try {
            // InternalThingML.g:6756:57: (iv_ruleExternExpression= ruleExternExpression EOF )
            // InternalThingML.g:6757:2: iv_ruleExternExpression= ruleExternExpression EOF
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
    // InternalThingML.g:6763:1: ruleExternExpression returns [EObject current=null] : ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) ;
    public final EObject ruleExternExpression() throws RecognitionException {
        EObject current = null;

        Token lv_expression_0_0=null;
        Token otherlv_1=null;
        EObject lv_segments_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6769:2: ( ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) )
            // InternalThingML.g:6770:2: ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            {
            // InternalThingML.g:6770:2: ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            // InternalThingML.g:6771:3: ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            {
            // InternalThingML.g:6771:3: ( (lv_expression_0_0= RULE_STRING_EXT ) )
            // InternalThingML.g:6772:4: (lv_expression_0_0= RULE_STRING_EXT )
            {
            // InternalThingML.g:6772:4: (lv_expression_0_0= RULE_STRING_EXT )
            // InternalThingML.g:6773:5: lv_expression_0_0= RULE_STRING_EXT
            {
            lv_expression_0_0=(Token)match(input,RULE_STRING_EXT,FOLLOW_75); 

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

            // InternalThingML.g:6789:3: (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            loop129:
            do {
                int alt129=2;
                int LA129_0 = input.LA(1);

                if ( (LA129_0==51) ) {
                    alt129=1;
                }


                switch (alt129) {
            	case 1 :
            	    // InternalThingML.g:6790:4: otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,51,FOLLOW_22); 

            	    				newLeafNode(otherlv_1, grammarAccess.getExternExpressionAccess().getAmpersandKeyword_1_0());
            	    			
            	    // InternalThingML.g:6794:4: ( (lv_segments_2_0= ruleExpression ) )
            	    // InternalThingML.g:6795:5: (lv_segments_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:6795:5: (lv_segments_2_0= ruleExpression )
            	    // InternalThingML.g:6796:6: lv_segments_2_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getExternExpressionAccess().getSegmentsExpressionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_75);
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
    // $ANTLR end "ruleExternExpression"


    // $ANTLR start "entryRuleEnumLiteralRef"
    // InternalThingML.g:6818:1: entryRuleEnumLiteralRef returns [EObject current=null] : iv_ruleEnumLiteralRef= ruleEnumLiteralRef EOF ;
    public final EObject entryRuleEnumLiteralRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumLiteralRef = null;


        try {
            // InternalThingML.g:6818:55: (iv_ruleEnumLiteralRef= ruleEnumLiteralRef EOF )
            // InternalThingML.g:6819:2: iv_ruleEnumLiteralRef= ruleEnumLiteralRef EOF
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
    // InternalThingML.g:6825:1: ruleEnumLiteralRef returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleEnumLiteralRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:6831:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalThingML.g:6832:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalThingML.g:6832:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) )
            // InternalThingML.g:6833:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) )
            {
            // InternalThingML.g:6833:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:6834:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:6834:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:6835:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEnumLiteralRefRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_29); 

            					newLeafNode(otherlv_0, grammarAccess.getEnumLiteralRefAccess().getEnumEnumerationCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,35,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getEnumLiteralRefAccess().getColonKeyword_1());
            		
            // InternalThingML.g:6850:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:6851:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:6851:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:6852:5: otherlv_2= RULE_ID
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
    // InternalThingML.g:6867:1: entryRuleIntegerLiteral returns [EObject current=null] : iv_ruleIntegerLiteral= ruleIntegerLiteral EOF ;
    public final EObject entryRuleIntegerLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIntegerLiteral = null;


        try {
            // InternalThingML.g:6867:55: (iv_ruleIntegerLiteral= ruleIntegerLiteral EOF )
            // InternalThingML.g:6868:2: iv_ruleIntegerLiteral= ruleIntegerLiteral EOF
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
    // InternalThingML.g:6874:1: ruleIntegerLiteral returns [EObject current=null] : ( (lv_intValue_0_0= RULE_INT ) ) ;
    public final EObject ruleIntegerLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_intValue_0_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6880:2: ( ( (lv_intValue_0_0= RULE_INT ) ) )
            // InternalThingML.g:6881:2: ( (lv_intValue_0_0= RULE_INT ) )
            {
            // InternalThingML.g:6881:2: ( (lv_intValue_0_0= RULE_INT ) )
            // InternalThingML.g:6882:3: (lv_intValue_0_0= RULE_INT )
            {
            // InternalThingML.g:6882:3: (lv_intValue_0_0= RULE_INT )
            // InternalThingML.g:6883:4: lv_intValue_0_0= RULE_INT
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
    // InternalThingML.g:6902:1: entryRuleBooleanLiteral returns [EObject current=null] : iv_ruleBooleanLiteral= ruleBooleanLiteral EOF ;
    public final EObject entryRuleBooleanLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBooleanLiteral = null;


        try {
            // InternalThingML.g:6902:55: (iv_ruleBooleanLiteral= ruleBooleanLiteral EOF )
            // InternalThingML.g:6903:2: iv_ruleBooleanLiteral= ruleBooleanLiteral EOF
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
    // InternalThingML.g:6909:1: ruleBooleanLiteral returns [EObject current=null] : ( ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) ) ) ;
    public final EObject ruleBooleanLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_boolValue_0_1=null;
        Token lv_boolValue_0_2=null;


        	enterRule();

        try {
            // InternalThingML.g:6915:2: ( ( ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) ) ) )
            // InternalThingML.g:6916:2: ( ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) ) )
            {
            // InternalThingML.g:6916:2: ( ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) ) )
            // InternalThingML.g:6917:3: ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) )
            {
            // InternalThingML.g:6917:3: ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) )
            // InternalThingML.g:6918:4: (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' )
            {
            // InternalThingML.g:6918:4: (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' )
            int alt130=2;
            int LA130_0 = input.LA(1);

            if ( (LA130_0==103) ) {
                alt130=1;
            }
            else if ( (LA130_0==104) ) {
                alt130=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 130, 0, input);

                throw nvae;
            }
            switch (alt130) {
                case 1 :
                    // InternalThingML.g:6919:5: lv_boolValue_0_1= 'true'
                    {
                    lv_boolValue_0_1=(Token)match(input,103,FOLLOW_2); 

                    					newLeafNode(lv_boolValue_0_1, grammarAccess.getBooleanLiteralAccess().getBoolValueTrueKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getBooleanLiteralRule());
                    					}
                    					setWithLastConsumed(current, "boolValue", lv_boolValue_0_1, null);
                    				

                    }
                    break;
                case 2 :
                    // InternalThingML.g:6930:5: lv_boolValue_0_2= 'false'
                    {
                    lv_boolValue_0_2=(Token)match(input,104,FOLLOW_2); 

                    					newLeafNode(lv_boolValue_0_2, grammarAccess.getBooleanLiteralAccess().getBoolValueFalseKeyword_0_1());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getBooleanLiteralRule());
                    					}
                    					setWithLastConsumed(current, "boolValue", lv_boolValue_0_2, null);
                    				

                    }
                    break;

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
    // $ANTLR end "ruleBooleanLiteral"


    // $ANTLR start "entryRuleStringLiteral"
    // InternalThingML.g:6946:1: entryRuleStringLiteral returns [EObject current=null] : iv_ruleStringLiteral= ruleStringLiteral EOF ;
    public final EObject entryRuleStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStringLiteral = null;


        try {
            // InternalThingML.g:6946:54: (iv_ruleStringLiteral= ruleStringLiteral EOF )
            // InternalThingML.g:6947:2: iv_ruleStringLiteral= ruleStringLiteral EOF
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
    // InternalThingML.g:6953:1: ruleStringLiteral returns [EObject current=null] : ( (lv_stringValue_0_0= RULE_STRING_LIT ) ) ;
    public final EObject ruleStringLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_stringValue_0_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6959:2: ( ( (lv_stringValue_0_0= RULE_STRING_LIT ) ) )
            // InternalThingML.g:6960:2: ( (lv_stringValue_0_0= RULE_STRING_LIT ) )
            {
            // InternalThingML.g:6960:2: ( (lv_stringValue_0_0= RULE_STRING_LIT ) )
            // InternalThingML.g:6961:3: (lv_stringValue_0_0= RULE_STRING_LIT )
            {
            // InternalThingML.g:6961:3: (lv_stringValue_0_0= RULE_STRING_LIT )
            // InternalThingML.g:6962:4: lv_stringValue_0_0= RULE_STRING_LIT
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
    // InternalThingML.g:6981:1: entryRuleDoubleLiteral returns [EObject current=null] : iv_ruleDoubleLiteral= ruleDoubleLiteral EOF ;
    public final EObject entryRuleDoubleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoubleLiteral = null;


        try {
            // InternalThingML.g:6981:54: (iv_ruleDoubleLiteral= ruleDoubleLiteral EOF )
            // InternalThingML.g:6982:2: iv_ruleDoubleLiteral= ruleDoubleLiteral EOF
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
    // InternalThingML.g:6988:1: ruleDoubleLiteral returns [EObject current=null] : ( (lv_doubleValue_0_0= RULE_FLOAT ) ) ;
    public final EObject ruleDoubleLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_doubleValue_0_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6994:2: ( ( (lv_doubleValue_0_0= RULE_FLOAT ) ) )
            // InternalThingML.g:6995:2: ( (lv_doubleValue_0_0= RULE_FLOAT ) )
            {
            // InternalThingML.g:6995:2: ( (lv_doubleValue_0_0= RULE_FLOAT ) )
            // InternalThingML.g:6996:3: (lv_doubleValue_0_0= RULE_FLOAT )
            {
            // InternalThingML.g:6996:3: (lv_doubleValue_0_0= RULE_FLOAT )
            // InternalThingML.g:6997:4: lv_doubleValue_0_0= RULE_FLOAT
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
    // InternalThingML.g:7016:1: entryRulePropertyReference returns [EObject current=null] : iv_rulePropertyReference= rulePropertyReference EOF ;
    public final EObject entryRulePropertyReference() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePropertyReference = null;


        try {
            // InternalThingML.g:7016:58: (iv_rulePropertyReference= rulePropertyReference EOF )
            // InternalThingML.g:7017:2: iv_rulePropertyReference= rulePropertyReference EOF
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
    // InternalThingML.g:7023:1: rulePropertyReference returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject rulePropertyReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalThingML.g:7029:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalThingML.g:7030:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalThingML.g:7030:2: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:7031:3: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:7031:3: (otherlv_0= RULE_ID )
            // InternalThingML.g:7032:4: otherlv_0= RULE_ID
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


    // $ANTLR start "entryRuleReference"
    // InternalThingML.g:7046:1: entryRuleReference returns [EObject current=null] : iv_ruleReference= ruleReference EOF ;
    public final EObject entryRuleReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReference = null;


        try {
            // InternalThingML.g:7046:50: (iv_ruleReference= ruleReference EOF )
            // InternalThingML.g:7047:2: iv_ruleReference= ruleReference EOF
            {
             newCompositeNode(grammarAccess.getReferenceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleReference=ruleReference();

            state._fsp--;

             current =iv_ruleReference; 
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
    // $ANTLR end "entryRuleReference"


    // $ANTLR start "ruleReference"
    // InternalThingML.g:7053:1: ruleReference returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (lv_parameter_2_0= ruleElmtProperty ) ) ) ;
    public final EObject ruleReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_parameter_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:7059:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (lv_parameter_2_0= ruleElmtProperty ) ) ) )
            // InternalThingML.g:7060:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (lv_parameter_2_0= ruleElmtProperty ) ) )
            {
            // InternalThingML.g:7060:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (lv_parameter_2_0= ruleElmtProperty ) ) )
            // InternalThingML.g:7061:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (lv_parameter_2_0= ruleElmtProperty ) )
            {
            // InternalThingML.g:7061:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:7062:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:7062:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:7063:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReferenceRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_90); 

            					newLeafNode(otherlv_0, grammarAccess.getReferenceAccess().getReferenceReferencedElmtCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,105,FOLLOW_91); 

            			newLeafNode(otherlv_1, grammarAccess.getReferenceAccess().getFullStopKeyword_1());
            		
            // InternalThingML.g:7078:3: ( (lv_parameter_2_0= ruleElmtProperty ) )
            // InternalThingML.g:7079:4: (lv_parameter_2_0= ruleElmtProperty )
            {
            // InternalThingML.g:7079:4: (lv_parameter_2_0= ruleElmtProperty )
            // InternalThingML.g:7080:5: lv_parameter_2_0= ruleElmtProperty
            {

            					newCompositeNode(grammarAccess.getReferenceAccess().getParameterElmtPropertyParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_parameter_2_0=ruleElmtProperty();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getReferenceRule());
            					}
            					set(
            						current,
            						"parameter",
            						lv_parameter_2_0,
            						"org.thingml.xtext.ThingML.ElmtProperty");
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
    // $ANTLR end "ruleReference"


    // $ANTLR start "entryRuleFunctionCallExpression"
    // InternalThingML.g:7101:1: entryRuleFunctionCallExpression returns [EObject current=null] : iv_ruleFunctionCallExpression= ruleFunctionCallExpression EOF ;
    public final EObject entryRuleFunctionCallExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCallExpression = null;


        try {
            // InternalThingML.g:7101:63: (iv_ruleFunctionCallExpression= ruleFunctionCallExpression EOF )
            // InternalThingML.g:7102:2: iv_ruleFunctionCallExpression= ruleFunctionCallExpression EOF
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
    // InternalThingML.g:7108:1: ruleFunctionCallExpression returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) ;
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
            // InternalThingML.g:7114:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) )
            // InternalThingML.g:7115:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            {
            // InternalThingML.g:7115:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            // InternalThingML.g:7116:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')'
            {
            // InternalThingML.g:7116:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:7117:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:7117:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:7118:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFunctionCallExpressionRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_24); 

            					newLeafNode(otherlv_0, grammarAccess.getFunctionCallExpressionAccess().getFunctionFunctionCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,33,FOLLOW_78); 

            			newLeafNode(otherlv_1, grammarAccess.getFunctionCallExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:7133:3: ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )?
            int alt132=2;
            int LA132_0 = input.LA(1);

            if ( ((LA132_0>=RULE_STRING_LIT && LA132_0<=RULE_FLOAT)||LA132_0==33||LA132_0==98||(LA132_0>=102 && LA132_0<=104)) ) {
                alt132=1;
            }
            switch (alt132) {
                case 1 :
                    // InternalThingML.g:7134:4: ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    {
                    // InternalThingML.g:7134:4: ( (lv_parameters_2_0= ruleExpression ) )
                    // InternalThingML.g:7135:5: (lv_parameters_2_0= ruleExpression )
                    {
                    // InternalThingML.g:7135:5: (lv_parameters_2_0= ruleExpression )
                    // InternalThingML.g:7136:6: lv_parameters_2_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getFunctionCallExpressionAccess().getParametersExpressionParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_26);
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

                    // InternalThingML.g:7153:4: (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    loop131:
                    do {
                        int alt131=2;
                        int LA131_0 = input.LA(1);

                        if ( (LA131_0==28) ) {
                            alt131=1;
                        }


                        switch (alt131) {
                    	case 1 :
                    	    // InternalThingML.g:7154:5: otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,28,FOLLOW_22); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getFunctionCallExpressionAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalThingML.g:7158:5: ( (lv_parameters_4_0= ruleExpression ) )
                    	    // InternalThingML.g:7159:6: (lv_parameters_4_0= ruleExpression )
                    	    {
                    	    // InternalThingML.g:7159:6: (lv_parameters_4_0= ruleExpression )
                    	    // InternalThingML.g:7160:7: lv_parameters_4_0= ruleExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getFunctionCallExpressionAccess().getParametersExpressionParserRuleCall_2_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
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
                    	    break loop131;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,34,FOLLOW_2); 

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
    // InternalThingML.g:7187:1: entryRuleConfiguration returns [EObject current=null] : iv_ruleConfiguration= ruleConfiguration EOF ;
    public final EObject entryRuleConfiguration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConfiguration = null;


        try {
            // InternalThingML.g:7187:54: (iv_ruleConfiguration= ruleConfiguration EOF )
            // InternalThingML.g:7188:2: iv_ruleConfiguration= ruleConfiguration EOF
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
    // InternalThingML.g:7194:1: ruleConfiguration returns [EObject current=null] : (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' ) ;
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
            // InternalThingML.g:7200:2: ( (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' ) )
            // InternalThingML.g:7201:2: (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' )
            {
            // InternalThingML.g:7201:2: (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' )
            // InternalThingML.g:7202:3: otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,106,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConfigurationAccess().getConfigurationKeyword_0());
            		
            // InternalThingML.g:7206:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:7207:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:7207:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:7208:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_14); 

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

            // InternalThingML.g:7224:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop133:
            do {
                int alt133=2;
                int LA133_0 = input.LA(1);

                if ( (LA133_0==14) ) {
                    alt133=1;
                }


                switch (alt133) {
            	case 1 :
            	    // InternalThingML.g:7225:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:7225:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:7226:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getConfigurationAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_14);
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
            	    break loop133;
                }
            } while (true);

            otherlv_3=(Token)match(input,23,FOLLOW_92); 

            			newLeafNode(otherlv_3, grammarAccess.getConfigurationAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalThingML.g:7247:3: ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )*
            loop134:
            do {
                int alt134=4;
                switch ( input.LA(1) ) {
                case 107:
                    {
                    alt134=1;
                    }
                    break;
                case 108:
                    {
                    alt134=2;
                    }
                    break;
                case 29:
                    {
                    alt134=3;
                    }
                    break;

                }

                switch (alt134) {
            	case 1 :
            	    // InternalThingML.g:7248:4: ( (lv_instances_4_0= ruleInstance ) )
            	    {
            	    // InternalThingML.g:7248:4: ( (lv_instances_4_0= ruleInstance ) )
            	    // InternalThingML.g:7249:5: (lv_instances_4_0= ruleInstance )
            	    {
            	    // InternalThingML.g:7249:5: (lv_instances_4_0= ruleInstance )
            	    // InternalThingML.g:7250:6: lv_instances_4_0= ruleInstance
            	    {

            	    						newCompositeNode(grammarAccess.getConfigurationAccess().getInstancesInstanceParserRuleCall_4_0_0());
            	    					
            	    pushFollow(FOLLOW_92);
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
            	    // InternalThingML.g:7268:4: ( (lv_connectors_5_0= ruleAbstractConnector ) )
            	    {
            	    // InternalThingML.g:7268:4: ( (lv_connectors_5_0= ruleAbstractConnector ) )
            	    // InternalThingML.g:7269:5: (lv_connectors_5_0= ruleAbstractConnector )
            	    {
            	    // InternalThingML.g:7269:5: (lv_connectors_5_0= ruleAbstractConnector )
            	    // InternalThingML.g:7270:6: lv_connectors_5_0= ruleAbstractConnector
            	    {

            	    						newCompositeNode(grammarAccess.getConfigurationAccess().getConnectorsAbstractConnectorParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_92);
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
            	    // InternalThingML.g:7288:4: ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) )
            	    {
            	    // InternalThingML.g:7288:4: ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) )
            	    // InternalThingML.g:7289:5: (lv_propassigns_6_0= ruleConfigPropertyAssign )
            	    {
            	    // InternalThingML.g:7289:5: (lv_propassigns_6_0= ruleConfigPropertyAssign )
            	    // InternalThingML.g:7290:6: lv_propassigns_6_0= ruleConfigPropertyAssign
            	    {

            	    						newCompositeNode(grammarAccess.getConfigurationAccess().getPropassignsConfigPropertyAssignParserRuleCall_4_2_0());
            	    					
            	    pushFollow(FOLLOW_92);
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
            	    break loop134;
                }
            } while (true);

            otherlv_7=(Token)match(input,24,FOLLOW_2); 

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
    // InternalThingML.g:7316:1: entryRuleInstance returns [EObject current=null] : iv_ruleInstance= ruleInstance EOF ;
    public final EObject entryRuleInstance() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInstance = null;


        try {
            // InternalThingML.g:7316:49: (iv_ruleInstance= ruleInstance EOF )
            // InternalThingML.g:7317:2: iv_ruleInstance= ruleInstance EOF
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
    // InternalThingML.g:7323:1: ruleInstance returns [EObject current=null] : (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleInstance() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject lv_annotations_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:7329:2: ( (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:7330:2: (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:7330:2: (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:7331:3: otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,107,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getInstanceAccess().getInstanceKeyword_0());
            		
            // InternalThingML.g:7335:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:7336:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:7336:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:7337:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_29); 

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

            otherlv_2=(Token)match(input,35,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getInstanceAccess().getColonKeyword_2());
            		
            // InternalThingML.g:7357:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:7358:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:7358:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:7359:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getInstanceRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_16); 

            					newLeafNode(otherlv_3, grammarAccess.getInstanceAccess().getTypeThingCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:7370:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop135:
            do {
                int alt135=2;
                int LA135_0 = input.LA(1);

                if ( (LA135_0==14) ) {
                    alt135=1;
                }


                switch (alt135) {
            	case 1 :
            	    // InternalThingML.g:7371:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:7371:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:7372:5: lv_annotations_4_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getInstanceAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_16);
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
            	    break loop135;
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
    // InternalThingML.g:7393:1: entryRuleConfigPropertyAssign returns [EObject current=null] : iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF ;
    public final EObject entryRuleConfigPropertyAssign() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConfigPropertyAssign = null;


        try {
            // InternalThingML.g:7393:61: (iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF )
            // InternalThingML.g:7394:2: iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF
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
    // InternalThingML.g:7400:1: ruleConfigPropertyAssign returns [EObject current=null] : (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleConfigPropertyAssign() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        EObject lv_instance_1_0 = null;

        EObject lv_index_5_0 = null;

        EObject lv_init_8_0 = null;

        EObject lv_annotations_9_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:7406:2: ( (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:7407:2: (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:7407:2: (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:7408:3: otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,29,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConfigPropertyAssignAccess().getSetKeyword_0());
            		
            // InternalThingML.g:7412:3: ( (lv_instance_1_0= ruleInstanceRef ) )
            // InternalThingML.g:7413:4: (lv_instance_1_0= ruleInstanceRef )
            {
            // InternalThingML.g:7413:4: (lv_instance_1_0= ruleInstanceRef )
            // InternalThingML.g:7414:5: lv_instance_1_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getInstanceInstanceRefParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_90);
            lv_instance_1_0=ruleInstanceRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConfigPropertyAssignRule());
            					}
            					set(
            						current,
            						"instance",
            						lv_instance_1_0,
            						"org.thingml.xtext.ThingML.InstanceRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,105,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getConfigPropertyAssignAccess().getFullStopKeyword_2());
            		
            // InternalThingML.g:7435:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:7436:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:7436:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:7437:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConfigPropertyAssignRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_3, grammarAccess.getConfigPropertyAssignAccess().getPropertyPropertyCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:7448:3: (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )*
            loop136:
            do {
                int alt136=2;
                int LA136_0 = input.LA(1);

                if ( (LA136_0==15) ) {
                    alt136=1;
                }


                switch (alt136) {
            	case 1 :
            	    // InternalThingML.g:7449:4: otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']'
            	    {
            	    otherlv_4=(Token)match(input,15,FOLLOW_22); 

            	    				newLeafNode(otherlv_4, grammarAccess.getConfigPropertyAssignAccess().getLeftSquareBracketKeyword_4_0());
            	    			
            	    // InternalThingML.g:7453:4: ( (lv_index_5_0= ruleExpression ) )
            	    // InternalThingML.g:7454:5: (lv_index_5_0= ruleExpression )
            	    {
            	    // InternalThingML.g:7454:5: (lv_index_5_0= ruleExpression )
            	    // InternalThingML.g:7455:6: lv_index_5_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getIndexExpressionParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_9);
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

            	    otherlv_6=(Token)match(input,16,FOLLOW_21); 

            	    				newLeafNode(otherlv_6, grammarAccess.getConfigPropertyAssignAccess().getRightSquareBracketKeyword_4_2());
            	    			

            	    }
            	    break;

            	default :
            	    break loop136;
                }
            } while (true);

            otherlv_7=(Token)match(input,30,FOLLOW_22); 

            			newLeafNode(otherlv_7, grammarAccess.getConfigPropertyAssignAccess().getEqualsSignKeyword_5());
            		
            // InternalThingML.g:7481:3: ( (lv_init_8_0= ruleExpression ) )
            // InternalThingML.g:7482:4: (lv_init_8_0= ruleExpression )
            {
            // InternalThingML.g:7482:4: (lv_init_8_0= ruleExpression )
            // InternalThingML.g:7483:5: lv_init_8_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getInitExpressionParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_16);
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

            // InternalThingML.g:7500:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop137:
            do {
                int alt137=2;
                int LA137_0 = input.LA(1);

                if ( (LA137_0==14) ) {
                    alt137=1;
                }


                switch (alt137) {
            	case 1 :
            	    // InternalThingML.g:7501:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:7501:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:7502:5: lv_annotations_9_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_16);
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
            	    break loop137;
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
    // InternalThingML.g:7523:1: entryRuleAbstractConnector returns [EObject current=null] : iv_ruleAbstractConnector= ruleAbstractConnector EOF ;
    public final EObject entryRuleAbstractConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAbstractConnector = null;


        try {
            // InternalThingML.g:7523:58: (iv_ruleAbstractConnector= ruleAbstractConnector EOF )
            // InternalThingML.g:7524:2: iv_ruleAbstractConnector= ruleAbstractConnector EOF
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
    // InternalThingML.g:7530:1: ruleAbstractConnector returns [EObject current=null] : (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector ) ;
    public final EObject ruleAbstractConnector() throws RecognitionException {
        EObject current = null;

        EObject this_Connector_0 = null;

        EObject this_ExternalConnector_1 = null;



        	enterRule();

        try {
            // InternalThingML.g:7536:2: ( (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector ) )
            // InternalThingML.g:7537:2: (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector )
            {
            // InternalThingML.g:7537:2: (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector )
            int alt138=2;
            int LA138_0 = input.LA(1);

            if ( (LA138_0==108) ) {
                int LA138_1 = input.LA(2);

                if ( (LA138_1==RULE_ID) ) {
                    int LA138_2 = input.LA(3);

                    if ( (LA138_2==RULE_ID) ) {
                        int LA138_3 = input.LA(4);

                        if ( (LA138_3==105) ) {
                            int LA138_4 = input.LA(5);

                            if ( (LA138_4==RULE_ID) ) {
                                int LA138_5 = input.LA(6);

                                if ( (LA138_5==109) ) {
                                    alt138=1;
                                }
                                else if ( (LA138_5==110) ) {
                                    alt138=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 138, 5, input);

                                    throw nvae;
                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 138, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 138, 3, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA138_2==105) ) {
                        int LA138_4 = input.LA(4);

                        if ( (LA138_4==RULE_ID) ) {
                            int LA138_5 = input.LA(5);

                            if ( (LA138_5==109) ) {
                                alt138=1;
                            }
                            else if ( (LA138_5==110) ) {
                                alt138=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 138, 5, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 138, 4, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 138, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 138, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 138, 0, input);

                throw nvae;
            }
            switch (alt138) {
                case 1 :
                    // InternalThingML.g:7538:3: this_Connector_0= ruleConnector
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
                    // InternalThingML.g:7547:3: this_ExternalConnector_1= ruleExternalConnector
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
    // InternalThingML.g:7559:1: entryRuleConnector returns [EObject current=null] : iv_ruleConnector= ruleConnector EOF ;
    public final EObject entryRuleConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConnector = null;


        try {
            // InternalThingML.g:7559:50: (iv_ruleConnector= ruleConnector EOF )
            // InternalThingML.g:7560:2: iv_ruleConnector= ruleConnector EOF
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
    // InternalThingML.g:7566:1: ruleConnector returns [EObject current=null] : (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleConnector() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        EObject lv_cli_2_0 = null;

        EObject lv_srv_6_0 = null;

        EObject lv_annotations_9_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:7572:2: ( (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:7573:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:7573:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:7574:3: otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,108,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConnectorAccess().getConnectorKeyword_0());
            		
            // InternalThingML.g:7578:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt139=2;
            int LA139_0 = input.LA(1);

            if ( (LA139_0==RULE_ID) ) {
                int LA139_1 = input.LA(2);

                if ( (LA139_1==RULE_ID) ) {
                    alt139=1;
                }
            }
            switch (alt139) {
                case 1 :
                    // InternalThingML.g:7579:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:7579:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:7580:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:7596:3: ( (lv_cli_2_0= ruleInstanceRef ) )
            // InternalThingML.g:7597:4: (lv_cli_2_0= ruleInstanceRef )
            {
            // InternalThingML.g:7597:4: (lv_cli_2_0= ruleInstanceRef )
            // InternalThingML.g:7598:5: lv_cli_2_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getConnectorAccess().getCliInstanceRefParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_90);
            lv_cli_2_0=ruleInstanceRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConnectorRule());
            					}
            					set(
            						current,
            						"cli",
            						lv_cli_2_0,
            						"org.thingml.xtext.ThingML.InstanceRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,105,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getConnectorAccess().getFullStopKeyword_3());
            		
            // InternalThingML.g:7619:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:7620:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:7620:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:7621:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConnectorRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_93); 

            					newLeafNode(otherlv_4, grammarAccess.getConnectorAccess().getRequiredRequiredPortCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,109,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getConnectorAccess().getEqualsSignGreaterThanSignKeyword_5());
            		
            // InternalThingML.g:7636:3: ( (lv_srv_6_0= ruleInstanceRef ) )
            // InternalThingML.g:7637:4: (lv_srv_6_0= ruleInstanceRef )
            {
            // InternalThingML.g:7637:4: (lv_srv_6_0= ruleInstanceRef )
            // InternalThingML.g:7638:5: lv_srv_6_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getConnectorAccess().getSrvInstanceRefParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_90);
            lv_srv_6_0=ruleInstanceRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConnectorRule());
            					}
            					set(
            						current,
            						"srv",
            						lv_srv_6_0,
            						"org.thingml.xtext.ThingML.InstanceRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,105,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getConnectorAccess().getFullStopKeyword_7());
            		
            // InternalThingML.g:7659:3: ( (otherlv_8= RULE_ID ) )
            // InternalThingML.g:7660:4: (otherlv_8= RULE_ID )
            {
            // InternalThingML.g:7660:4: (otherlv_8= RULE_ID )
            // InternalThingML.g:7661:5: otherlv_8= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConnectorRule());
            					}
            				
            otherlv_8=(Token)match(input,RULE_ID,FOLLOW_16); 

            					newLeafNode(otherlv_8, grammarAccess.getConnectorAccess().getProvidedProvidedPortCrossReference_8_0());
            				

            }


            }

            // InternalThingML.g:7672:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop140:
            do {
                int alt140=2;
                int LA140_0 = input.LA(1);

                if ( (LA140_0==14) ) {
                    alt140=1;
                }


                switch (alt140) {
            	case 1 :
            	    // InternalThingML.g:7673:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:7673:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:7674:5: lv_annotations_9_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getConnectorAccess().getAnnotationsPlatformAnnotationParserRuleCall_9_0());
            	    				
            	    pushFollow(FOLLOW_16);
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
            	    break loop140;
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
    // InternalThingML.g:7695:1: entryRuleExternalConnector returns [EObject current=null] : iv_ruleExternalConnector= ruleExternalConnector EOF ;
    public final EObject entryRuleExternalConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternalConnector = null;


        try {
            // InternalThingML.g:7695:58: (iv_ruleExternalConnector= ruleExternalConnector EOF )
            // InternalThingML.g:7696:2: iv_ruleExternalConnector= ruleExternalConnector EOF
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
    // InternalThingML.g:7702:1: ruleExternalConnector returns [EObject current=null] : (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleExternalConnector() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        EObject lv_inst_2_0 = null;

        EObject lv_annotations_7_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:7708:2: ( (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:7709:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:7709:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:7710:3: otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,108,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getExternalConnectorAccess().getConnectorKeyword_0());
            		
            // InternalThingML.g:7714:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt141=2;
            int LA141_0 = input.LA(1);

            if ( (LA141_0==RULE_ID) ) {
                int LA141_1 = input.LA(2);

                if ( (LA141_1==RULE_ID) ) {
                    alt141=1;
                }
            }
            switch (alt141) {
                case 1 :
                    // InternalThingML.g:7715:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:7715:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:7716:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:7732:3: ( (lv_inst_2_0= ruleInstanceRef ) )
            // InternalThingML.g:7733:4: (lv_inst_2_0= ruleInstanceRef )
            {
            // InternalThingML.g:7733:4: (lv_inst_2_0= ruleInstanceRef )
            // InternalThingML.g:7734:5: lv_inst_2_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getExternalConnectorAccess().getInstInstanceRefParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_90);
            lv_inst_2_0=ruleInstanceRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getExternalConnectorRule());
            					}
            					set(
            						current,
            						"inst",
            						lv_inst_2_0,
            						"org.thingml.xtext.ThingML.InstanceRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,105,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getExternalConnectorAccess().getFullStopKeyword_3());
            		
            // InternalThingML.g:7755:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:7756:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:7756:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:7757:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalConnectorRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_94); 

            					newLeafNode(otherlv_4, grammarAccess.getExternalConnectorAccess().getPortPortCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,110,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getExternalConnectorAccess().getOverKeyword_5());
            		
            // InternalThingML.g:7772:3: ( (otherlv_6= RULE_ID ) )
            // InternalThingML.g:7773:4: (otherlv_6= RULE_ID )
            {
            // InternalThingML.g:7773:4: (otherlv_6= RULE_ID )
            // InternalThingML.g:7774:5: otherlv_6= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalConnectorRule());
            					}
            				
            otherlv_6=(Token)match(input,RULE_ID,FOLLOW_16); 

            					newLeafNode(otherlv_6, grammarAccess.getExternalConnectorAccess().getProtocolProtocolCrossReference_6_0());
            				

            }


            }

            // InternalThingML.g:7785:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop142:
            do {
                int alt142=2;
                int LA142_0 = input.LA(1);

                if ( (LA142_0==14) ) {
                    alt142=1;
                }


                switch (alt142) {
            	case 1 :
            	    // InternalThingML.g:7786:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:7786:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:7787:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getExternalConnectorAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_16);
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
            	    break loop142;
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


    // $ANTLR start "entryRuleInstanceRef"
    // InternalThingML.g:7808:1: entryRuleInstanceRef returns [EObject current=null] : iv_ruleInstanceRef= ruleInstanceRef EOF ;
    public final EObject entryRuleInstanceRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInstanceRef = null;


        try {
            // InternalThingML.g:7808:52: (iv_ruleInstanceRef= ruleInstanceRef EOF )
            // InternalThingML.g:7809:2: iv_ruleInstanceRef= ruleInstanceRef EOF
            {
             newCompositeNode(grammarAccess.getInstanceRefRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInstanceRef=ruleInstanceRef();

            state._fsp--;

             current =iv_ruleInstanceRef; 
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
    // $ANTLR end "entryRuleInstanceRef"


    // $ANTLR start "ruleInstanceRef"
    // InternalThingML.g:7815:1: ruleInstanceRef returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleInstanceRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalThingML.g:7821:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalThingML.g:7822:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalThingML.g:7822:2: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:7823:3: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:7823:3: (otherlv_0= RULE_ID )
            // InternalThingML.g:7824:4: otherlv_0= RULE_ID
            {

            				if (current==null) {
            					current = createModelElement(grammarAccess.getInstanceRefRule());
            				}
            			
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(otherlv_0, grammarAccess.getInstanceRefAccess().getInstanceInstanceCrossReference_0());
            			

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
    // $ANTLR end "ruleInstanceRef"

    // Delegated rules


    protected DFA107 dfa107 = new DFA107(this);
    protected DFA128 dfa128 = new DFA128(this);
    static final String dfa_1s = "\20\uffff";
    static final String dfa_2s = "\1\5\2\uffff\1\17\14\uffff";
    static final String dfa_3s = "\1\132\2\uffff\1\124\14\uffff";
    static final String dfa_4s = "\1\uffff\1\1\1\2\1\uffff\1\7\1\10\1\11\1\12\1\13\1\14\1\16\1\4\1\3\1\6\1\15\1\5";
    static final String dfa_5s = "\20\uffff}>";
    static final String[] dfa_6s = {
            "\1\3\1\uffff\1\2\34\uffff\1\12\24\uffff\1\5\25\uffff\1\1\1\uffff\1\12\3\uffff\1\4\1\uffff\1\6\1\7\1\10\1\11",
            "",
            "",
            "\1\13\16\uffff\1\13\2\uffff\1\16\60\uffff\1\14\1\17\1\15",
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

    class DFA107 extends DFA {

        public DFA107(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 107;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "4801:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )";
        }
    }
    static final String dfa_7s = "\13\uffff";
    static final String dfa_8s = "\2\uffff\1\11\10\uffff";
    static final String dfa_9s = "\1\4\1\uffff\1\5\10\uffff";
    static final String dfa_10s = "\1\150\1\uffff\1\154\10\uffff";
    static final String dfa_11s = "\1\uffff\1\1\1\uffff\1\3\1\4\1\5\1\6\1\2\1\10\1\7\1\11";
    static final String dfa_12s = "\13\uffff}>";
    static final String[] dfa_13s = {
            "\1\5\1\2\1\3\1\1\1\6\136\uffff\2\4",
            "",
            "\1\11\1\uffff\1\11\6\uffff\3\11\1\uffff\2\11\4\uffff\1\11\3\uffff\2\11\2\uffff\1\11\1\12\1\11\1\7\5\11\3\uffff\3\11\1\uffff\2\11\1\uffff\3\11\1\uffff\1\11\1\uffff\1\11\1\uffff\1\11\2\uffff\1\11\3\uffff\1\11\2\uffff\6\11\2\uffff\1\11\1\uffff\3\11\3\uffff\21\11\3\uffff\1\10\1\uffff\2\11",
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

    class DFA128 extends DFA {

        public DFA128(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 128;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "6671:2: (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_Reference_7= ruleReference | this_FunctionCallExpression_8= ruleFunctionCallExpression )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000082622002L,0x0000040000000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000082620002L,0x0000040000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x00000002000101F0L,0x000001C400000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000104002L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000804000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000001000020L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000004000020L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000008804000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000010804000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x400071F121000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000040008000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x00000002000001F0L,0x000001C400000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000104000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000400000020L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000410000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x02000018000040A0L,0x0000000007A28000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000040004002L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x00000C0001000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x00000C0011000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000800000004000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0044000000000020L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0003000000000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0002000010000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0018000000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0020000000000002L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x1500000000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0090000000000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x8000000000000020L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000804000L,0x0000000000000001L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x4000203001000000L,0x00000000000003E4L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x4000200001000000L,0x00000000000003E4L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x4000200001000000L,0x00000000000003E0L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000001000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000001000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x4000203001000000L,0x00000000000007E4L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x4000200001000000L,0x00000000000007E4L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x4000200001000000L,0x00000000000007E0L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x8000000000040000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x4000000001000000L,0x00000000000001E0L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x4000203001000000L,0x00000000000005E4L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x4000200001000000L,0x00000000000005E4L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x4000200001000000L,0x00000000000005E0L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0010000000000020L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000004002L,0x0000000000003800L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000002L,0x0000000000003800L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000004022L,0x0000000000003800L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x02000018000040A0L,0x0000000007A38000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x00000006000001F0L,0x000001C400000000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000000000002L,0x0000000060000000L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x00000000000C0002L,0x0000000180000000L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000000000000002L,0x0000000600000000L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000000000000002L,0x0000001800000000L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x0000000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_91 = new BitSet(new long[]{0x2000000000000020L});
    public static final BitSet FOLLOW_92 = new BitSet(new long[]{0x0000000021000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_93 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_94 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});

}