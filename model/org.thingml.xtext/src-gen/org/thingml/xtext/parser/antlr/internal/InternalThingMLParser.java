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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING_LIT", "RULE_ID", "RULE_INT", "RULE_STRING_EXT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'import'", "'@'", "'datatype'", "'<'", "'>'", "';'", "'object'", "'enumeration'", "'{'", "'}'", "'thing'", "'fragment'", "'includes'", "','", "'set'", "'['", "']'", "'='", "'protocol'", "'function'", "'('", "')'", "':'", "'is'", "'property'", "'message'", "'optional'", "'required'", "'port'", "'sends'", "'receives'", "'provided'", "'internal'", "'stream'", "'from'", "'select'", "'produce'", "'join'", "'&'", "'->'", "'::'", "'merge'", "'|'", "'keep'", "'if'", "'buffer'", "'by'", "'during'", "'length'", "'statechart'", "'init'", "'keeps'", "'history'", "'on'", "'entry'", "'exit'", "'final'", "'state'", "'composite'", "'session'", "'region'", "'transition'", "'event'", "'guard'", "'action'", "'?'", "'do'", "'end'", "'readonly'", "'var'", "'!'", "'++'", "'--'", "'while'", "'else'", "'return'", "'print'", "'error'", "'spawn'", "'configuration'", "'instance'", "'.'", "'connector'", "'=>'", "'over'"
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
    public static final int RULE_ML_COMMENT=8;
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
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__95=95;
    public static final int T__96=96;
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
    public static final int RULE_SL_COMMENT=9;
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
    public static final int RULE_WS=10;
    public static final int RULE_ANY_OTHER=11;
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

                if ( (LA1_0==12) ) {
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
                case 14:
                case 18:
                case 19:
                case 22:
                    {
                    alt2=1;
                    }
                    break;
                case 30:
                    {
                    alt2=2;
                    }
                    break;
                case 91:
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
            otherlv_0=(Token)match(input,12,FOLLOW_5); 

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
            otherlv_0=(Token)match(input,13,FOLLOW_6); 

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


    // $ANTLR start "entryRuleType"
    // InternalThingML.g:263:1: entryRuleType returns [EObject current=null] : iv_ruleType= ruleType EOF ;
    public final EObject entryRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleType = null;


        try {
            // InternalThingML.g:263:45: (iv_ruleType= ruleType EOF )
            // InternalThingML.g:264:2: iv_ruleType= ruleType EOF
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
    // InternalThingML.g:270:1: ruleType returns [EObject current=null] : (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing ) ;
    public final EObject ruleType() throws RecognitionException {
        EObject current = null;

        EObject this_PrimitiveType_0 = null;

        EObject this_ObjectType_1 = null;

        EObject this_Enumeration_2 = null;

        EObject this_Thing_3 = null;



        	enterRule();

        try {
            // InternalThingML.g:276:2: ( (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing ) )
            // InternalThingML.g:277:2: (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing )
            {
            // InternalThingML.g:277:2: (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing )
            int alt3=4;
            switch ( input.LA(1) ) {
            case 14:
                {
                alt3=1;
                }
                break;
            case 18:
                {
                alt3=2;
                }
                break;
            case 19:
                {
                alt3=3;
                }
                break;
            case 22:
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
                    // InternalThingML.g:278:3: this_PrimitiveType_0= rulePrimitiveType
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
                    // InternalThingML.g:287:3: this_ObjectType_1= ruleObjectType
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
                    // InternalThingML.g:296:3: this_Enumeration_2= ruleEnumeration
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
                    // InternalThingML.g:305:3: this_Thing_3= ruleThing
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
    // InternalThingML.g:317:1: entryRulePrimitiveType returns [EObject current=null] : iv_rulePrimitiveType= rulePrimitiveType EOF ;
    public final EObject entryRulePrimitiveType() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimitiveType = null;


        try {
            // InternalThingML.g:317:54: (iv_rulePrimitiveType= rulePrimitiveType EOF )
            // InternalThingML.g:318:2: iv_rulePrimitiveType= rulePrimitiveType EOF
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
    // InternalThingML.g:324:1: rulePrimitiveType returns [EObject current=null] : (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* otherlv_6= ';' ) ;
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
            // InternalThingML.g:330:2: ( (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* otherlv_6= ';' ) )
            // InternalThingML.g:331:2: (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* otherlv_6= ';' )
            {
            // InternalThingML.g:331:2: (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* otherlv_6= ';' )
            // InternalThingML.g:332:3: otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* otherlv_6= ';'
            {
            otherlv_0=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getPrimitiveTypeAccess().getDatatypeKeyword_0());
            		
            // InternalThingML.g:336:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:337:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:337:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:338:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_7); 

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

            otherlv_2=(Token)match(input,15,FOLLOW_8); 

            			newLeafNode(otherlv_2, grammarAccess.getPrimitiveTypeAccess().getLessThanSignKeyword_2());
            		
            // InternalThingML.g:358:3: ( (lv_ByteSize_3_0= RULE_INT ) )
            // InternalThingML.g:359:4: (lv_ByteSize_3_0= RULE_INT )
            {
            // InternalThingML.g:359:4: (lv_ByteSize_3_0= RULE_INT )
            // InternalThingML.g:360:5: lv_ByteSize_3_0= RULE_INT
            {
            lv_ByteSize_3_0=(Token)match(input,RULE_INT,FOLLOW_9); 

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

            otherlv_4=(Token)match(input,16,FOLLOW_10); 

            			newLeafNode(otherlv_4, grammarAccess.getPrimitiveTypeAccess().getGreaterThanSignKeyword_4());
            		
            // InternalThingML.g:380:3: ( (lv_annotations_5_0= rulePlatformAnnotation ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==13) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalThingML.g:381:4: (lv_annotations_5_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:381:4: (lv_annotations_5_0= rulePlatformAnnotation )
            	    // InternalThingML.g:382:5: lv_annotations_5_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_10);
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
            	    break loop4;
                }
            } while (true);

            otherlv_6=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getPrimitiveTypeAccess().getSemicolonKeyword_6());
            		

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
    // InternalThingML.g:407:1: entryRuleObjectType returns [EObject current=null] : iv_ruleObjectType= ruleObjectType EOF ;
    public final EObject entryRuleObjectType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleObjectType = null;


        try {
            // InternalThingML.g:407:51: (iv_ruleObjectType= ruleObjectType EOF )
            // InternalThingML.g:408:2: iv_ruleObjectType= ruleObjectType EOF
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
    // InternalThingML.g:414:1: ruleObjectType returns [EObject current=null] : (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' ) ;
    public final EObject ruleObjectType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        EObject lv_annotations_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:420:2: ( (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' ) )
            // InternalThingML.g:421:2: (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' )
            {
            // InternalThingML.g:421:2: (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' )
            // InternalThingML.g:422:3: otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';'
            {
            otherlv_0=(Token)match(input,18,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getObjectTypeAccess().getObjectKeyword_0());
            		
            // InternalThingML.g:426:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:427:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:427:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:428:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_10); 

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

            // InternalThingML.g:444:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==13) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalThingML.g:445:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:445:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:446:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getObjectTypeAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_10);
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
            	    break loop5;
                }
            } while (true);

            otherlv_3=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getObjectTypeAccess().getSemicolonKeyword_3());
            		

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
    // InternalThingML.g:471:1: entryRuleEnumeration returns [EObject current=null] : iv_ruleEnumeration= ruleEnumeration EOF ;
    public final EObject entryRuleEnumeration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumeration = null;


        try {
            // InternalThingML.g:471:52: (iv_ruleEnumeration= ruleEnumeration EOF )
            // InternalThingML.g:472:2: iv_ruleEnumeration= ruleEnumeration EOF
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
    // InternalThingML.g:478:1: ruleEnumeration returns [EObject current=null] : (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' ) ;
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
            // InternalThingML.g:484:2: ( (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' ) )
            // InternalThingML.g:485:2: (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' )
            {
            // InternalThingML.g:485:2: (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' )
            // InternalThingML.g:486:3: otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getEnumerationAccess().getEnumerationKeyword_0());
            		
            // InternalThingML.g:490:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:491:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:491:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:492:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_11); 

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

            // InternalThingML.g:508:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==13) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalThingML.g:509:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:509:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:510:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getEnumerationAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_11);
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
            	    break loop6;
                }
            } while (true);

            otherlv_3=(Token)match(input,20,FOLLOW_12); 

            			newLeafNode(otherlv_3, grammarAccess.getEnumerationAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalThingML.g:531:3: ( (lv_literals_4_0= ruleEnumerationLiteral ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==RULE_ID) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalThingML.g:532:4: (lv_literals_4_0= ruleEnumerationLiteral )
            	    {
            	    // InternalThingML.g:532:4: (lv_literals_4_0= ruleEnumerationLiteral )
            	    // InternalThingML.g:533:5: lv_literals_4_0= ruleEnumerationLiteral
            	    {

            	    					newCompositeNode(grammarAccess.getEnumerationAccess().getLiteralsEnumerationLiteralParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_12);
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
            	    break loop7;
                }
            } while (true);

            otherlv_5=(Token)match(input,21,FOLLOW_2); 

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
    // InternalThingML.g:558:1: entryRuleEnumerationLiteral returns [EObject current=null] : iv_ruleEnumerationLiteral= ruleEnumerationLiteral EOF ;
    public final EObject entryRuleEnumerationLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumerationLiteral = null;


        try {
            // InternalThingML.g:558:59: (iv_ruleEnumerationLiteral= ruleEnumerationLiteral EOF )
            // InternalThingML.g:559:2: iv_ruleEnumerationLiteral= ruleEnumerationLiteral EOF
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
    // InternalThingML.g:565:1: ruleEnumerationLiteral returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleEnumerationLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        EObject lv_annotations_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:571:2: ( ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:572:2: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:572:2: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:573:3: ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:573:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:574:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:574:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:575:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_13); 

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

            // InternalThingML.g:591:3: ( (lv_annotations_1_0= rulePlatformAnnotation ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==13) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalThingML.g:592:4: (lv_annotations_1_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:592:4: (lv_annotations_1_0= rulePlatformAnnotation )
            	    // InternalThingML.g:593:5: lv_annotations_1_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getEnumerationLiteralAccess().getAnnotationsPlatformAnnotationParserRuleCall_1_0());
            	    				
            	    pushFollow(FOLLOW_13);
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
            	    break loop8;
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
    // InternalThingML.g:614:1: entryRuleThing returns [EObject current=null] : iv_ruleThing= ruleThing EOF ;
    public final EObject entryRuleThing() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThing = null;


        try {
            // InternalThingML.g:614:46: (iv_ruleThing= ruleThing EOF )
            // InternalThingML.g:615:2: iv_ruleThing= ruleThing EOF
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
    // InternalThingML.g:621:1: ruleThing returns [EObject current=null] : (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) | ( (lv_streams_15_0= ruleStream ) ) )* otherlv_16= '}' ) ;
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
            // InternalThingML.g:627:2: ( (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) | ( (lv_streams_15_0= ruleStream ) ) )* otherlv_16= '}' ) )
            // InternalThingML.g:628:2: (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) | ( (lv_streams_15_0= ruleStream ) ) )* otherlv_16= '}' )
            {
            // InternalThingML.g:628:2: (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) | ( (lv_streams_15_0= ruleStream ) ) )* otherlv_16= '}' )
            // InternalThingML.g:629:3: otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) | ( (lv_streams_15_0= ruleStream ) ) )* otherlv_16= '}'
            {
            otherlv_0=(Token)match(input,22,FOLLOW_14); 

            			newLeafNode(otherlv_0, grammarAccess.getThingAccess().getThingKeyword_0());
            		
            // InternalThingML.g:633:3: ( (lv_fragment_1_0= 'fragment' ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==23) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalThingML.g:634:4: (lv_fragment_1_0= 'fragment' )
                    {
                    // InternalThingML.g:634:4: (lv_fragment_1_0= 'fragment' )
                    // InternalThingML.g:635:5: lv_fragment_1_0= 'fragment'
                    {
                    lv_fragment_1_0=(Token)match(input,23,FOLLOW_6); 

                    					newLeafNode(lv_fragment_1_0, grammarAccess.getThingAccess().getFragmentFragmentKeyword_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getThingRule());
                    					}
                    					setWithLastConsumed(current, "fragment", true, "fragment");
                    				

                    }


                    }
                    break;

            }

            // InternalThingML.g:647:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:648:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:648:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:649:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_15); 

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

            // InternalThingML.g:665:3: (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==24) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalThingML.g:666:4: otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )*
                    {
                    otherlv_3=(Token)match(input,24,FOLLOW_6); 

                    				newLeafNode(otherlv_3, grammarAccess.getThingAccess().getIncludesKeyword_3_0());
                    			
                    // InternalThingML.g:670:4: ( (otherlv_4= RULE_ID ) )
                    // InternalThingML.g:671:5: (otherlv_4= RULE_ID )
                    {
                    // InternalThingML.g:671:5: (otherlv_4= RULE_ID )
                    // InternalThingML.g:672:6: otherlv_4= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getThingRule());
                    						}
                    					
                    otherlv_4=(Token)match(input,RULE_ID,FOLLOW_16); 

                    						newLeafNode(otherlv_4, grammarAccess.getThingAccess().getIncludesThingCrossReference_3_1_0());
                    					

                    }


                    }

                    // InternalThingML.g:683:4: (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==25) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // InternalThingML.g:684:5: otherlv_5= ',' ( (otherlv_6= RULE_ID ) )
                    	    {
                    	    otherlv_5=(Token)match(input,25,FOLLOW_6); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getThingAccess().getCommaKeyword_3_2_0());
                    	    				
                    	    // InternalThingML.g:688:5: ( (otherlv_6= RULE_ID ) )
                    	    // InternalThingML.g:689:6: (otherlv_6= RULE_ID )
                    	    {
                    	    // InternalThingML.g:689:6: (otherlv_6= RULE_ID )
                    	    // InternalThingML.g:690:7: otherlv_6= RULE_ID
                    	    {

                    	    							if (current==null) {
                    	    								current = createModelElement(grammarAccess.getThingRule());
                    	    							}
                    	    						
                    	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_16); 

                    	    							newLeafNode(otherlv_6, grammarAccess.getThingAccess().getIncludesThingCrossReference_3_2_1_0());
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);


                    }
                    break;

            }

            // InternalThingML.g:703:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==13) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalThingML.g:704:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:704:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:705:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getThingAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_11);
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
            	    break loop12;
                }
            } while (true);

            otherlv_8=(Token)match(input,20,FOLLOW_17); 

            			newLeafNode(otherlv_8, grammarAccess.getThingAccess().getLeftCurlyBracketKeyword_5());
            		
            // InternalThingML.g:726:3: ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) | ( (lv_streams_15_0= ruleStream ) ) )*
            loop13:
            do {
                int alt13=8;
                switch ( input.LA(1) ) {
                case 37:
                    {
                    alt13=1;
                    }
                    break;
                case 38:
                case 39:
                case 43:
                case 44:
                    {
                    alt13=2;
                    }
                    break;
                case 36:
                    {
                    alt13=3;
                    }
                    break;
                case 31:
                    {
                    alt13=4;
                    }
                    break;
                case 26:
                    {
                    alt13=5;
                    }
                    break;
                case 61:
                    {
                    alt13=6;
                    }
                    break;
                case 45:
                    {
                    alt13=7;
                    }
                    break;

                }

                switch (alt13) {
            	case 1 :
            	    // InternalThingML.g:727:4: ( (lv_messages_9_0= ruleMessage ) )
            	    {
            	    // InternalThingML.g:727:4: ( (lv_messages_9_0= ruleMessage ) )
            	    // InternalThingML.g:728:5: (lv_messages_9_0= ruleMessage )
            	    {
            	    // InternalThingML.g:728:5: (lv_messages_9_0= ruleMessage )
            	    // InternalThingML.g:729:6: lv_messages_9_0= ruleMessage
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getMessagesMessageParserRuleCall_6_0_0());
            	    					
            	    pushFollow(FOLLOW_17);
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
            	    // InternalThingML.g:747:4: ( (lv_ports_10_0= rulePort ) )
            	    {
            	    // InternalThingML.g:747:4: ( (lv_ports_10_0= rulePort ) )
            	    // InternalThingML.g:748:5: (lv_ports_10_0= rulePort )
            	    {
            	    // InternalThingML.g:748:5: (lv_ports_10_0= rulePort )
            	    // InternalThingML.g:749:6: lv_ports_10_0= rulePort
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getPortsPortParserRuleCall_6_1_0());
            	    					
            	    pushFollow(FOLLOW_17);
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
            	    // InternalThingML.g:767:4: ( (lv_properties_11_0= ruleProperty ) )
            	    {
            	    // InternalThingML.g:767:4: ( (lv_properties_11_0= ruleProperty ) )
            	    // InternalThingML.g:768:5: (lv_properties_11_0= ruleProperty )
            	    {
            	    // InternalThingML.g:768:5: (lv_properties_11_0= ruleProperty )
            	    // InternalThingML.g:769:6: lv_properties_11_0= ruleProperty
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getPropertiesPropertyParserRuleCall_6_2_0());
            	    					
            	    pushFollow(FOLLOW_17);
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
            	    // InternalThingML.g:787:4: ( (lv_functions_12_0= ruleFunction ) )
            	    {
            	    // InternalThingML.g:787:4: ( (lv_functions_12_0= ruleFunction ) )
            	    // InternalThingML.g:788:5: (lv_functions_12_0= ruleFunction )
            	    {
            	    // InternalThingML.g:788:5: (lv_functions_12_0= ruleFunction )
            	    // InternalThingML.g:789:6: lv_functions_12_0= ruleFunction
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getFunctionsFunctionParserRuleCall_6_3_0());
            	    					
            	    pushFollow(FOLLOW_17);
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
            	    // InternalThingML.g:807:4: ( (lv_assign_13_0= rulePropertyAssign ) )
            	    {
            	    // InternalThingML.g:807:4: ( (lv_assign_13_0= rulePropertyAssign ) )
            	    // InternalThingML.g:808:5: (lv_assign_13_0= rulePropertyAssign )
            	    {
            	    // InternalThingML.g:808:5: (lv_assign_13_0= rulePropertyAssign )
            	    // InternalThingML.g:809:6: lv_assign_13_0= rulePropertyAssign
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getAssignPropertyAssignParserRuleCall_6_4_0());
            	    					
            	    pushFollow(FOLLOW_17);
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
            	    // InternalThingML.g:827:4: ( (lv_behaviour_14_0= ruleStateMachine ) )
            	    {
            	    // InternalThingML.g:827:4: ( (lv_behaviour_14_0= ruleStateMachine ) )
            	    // InternalThingML.g:828:5: (lv_behaviour_14_0= ruleStateMachine )
            	    {
            	    // InternalThingML.g:828:5: (lv_behaviour_14_0= ruleStateMachine )
            	    // InternalThingML.g:829:6: lv_behaviour_14_0= ruleStateMachine
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getBehaviourStateMachineParserRuleCall_6_5_0());
            	    					
            	    pushFollow(FOLLOW_17);
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
            	    // InternalThingML.g:847:4: ( (lv_streams_15_0= ruleStream ) )
            	    {
            	    // InternalThingML.g:847:4: ( (lv_streams_15_0= ruleStream ) )
            	    // InternalThingML.g:848:5: (lv_streams_15_0= ruleStream )
            	    {
            	    // InternalThingML.g:848:5: (lv_streams_15_0= ruleStream )
            	    // InternalThingML.g:849:6: lv_streams_15_0= ruleStream
            	    {

            	    						newCompositeNode(grammarAccess.getThingAccess().getStreamsStreamParserRuleCall_6_6_0());
            	    					
            	    pushFollow(FOLLOW_17);
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
            	    break loop13;
                }
            } while (true);

            otherlv_16=(Token)match(input,21,FOLLOW_2); 

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
    // InternalThingML.g:875:1: entryRulePropertyAssign returns [EObject current=null] : iv_rulePropertyAssign= rulePropertyAssign EOF ;
    public final EObject entryRulePropertyAssign() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePropertyAssign = null;


        try {
            // InternalThingML.g:875:55: (iv_rulePropertyAssign= rulePropertyAssign EOF )
            // InternalThingML.g:876:2: iv_rulePropertyAssign= rulePropertyAssign EOF
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
    // InternalThingML.g:882:1: rulePropertyAssign returns [EObject current=null] : (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:888:2: ( (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:889:2: (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:889:2: (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:890:3: otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,26,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getPropertyAssignAccess().getSetKeyword_0());
            		
            // InternalThingML.g:894:3: ( (otherlv_1= RULE_ID ) )
            // InternalThingML.g:895:4: (otherlv_1= RULE_ID )
            {
            // InternalThingML.g:895:4: (otherlv_1= RULE_ID )
            // InternalThingML.g:896:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPropertyAssignRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_18); 

            					newLeafNode(otherlv_1, grammarAccess.getPropertyAssignAccess().getPropertyPropertyCrossReference_1_0());
            				

            }


            }

            // InternalThingML.g:907:3: (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==27) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalThingML.g:908:4: otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']'
            	    {
            	    otherlv_2=(Token)match(input,27,FOLLOW_19); 

            	    				newLeafNode(otherlv_2, grammarAccess.getPropertyAssignAccess().getLeftSquareBracketKeyword_2_0());
            	    			
            	    // InternalThingML.g:912:4: ( (lv_index_3_0= ruleExpression ) )
            	    // InternalThingML.g:913:5: (lv_index_3_0= ruleExpression )
            	    {
            	    // InternalThingML.g:913:5: (lv_index_3_0= ruleExpression )
            	    // InternalThingML.g:914:6: lv_index_3_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getPropertyAssignAccess().getIndexExpressionParserRuleCall_2_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
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

            	    otherlv_4=(Token)match(input,28,FOLLOW_18); 

            	    				newLeafNode(otherlv_4, grammarAccess.getPropertyAssignAccess().getRightSquareBracketKeyword_2_2());
            	    			

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            otherlv_5=(Token)match(input,29,FOLLOW_19); 

            			newLeafNode(otherlv_5, grammarAccess.getPropertyAssignAccess().getEqualsSignKeyword_3());
            		
            // InternalThingML.g:940:3: ( (lv_init_6_0= ruleExpression ) )
            // InternalThingML.g:941:4: (lv_init_6_0= ruleExpression )
            {
            // InternalThingML.g:941:4: (lv_init_6_0= ruleExpression )
            // InternalThingML.g:942:5: lv_init_6_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getPropertyAssignAccess().getInitExpressionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_13);
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

            // InternalThingML.g:959:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==13) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalThingML.g:960:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:960:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:961:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getPropertyAssignAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_13);
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
            	    break loop15;
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
    // InternalThingML.g:982:1: entryRuleProtocol returns [EObject current=null] : iv_ruleProtocol= ruleProtocol EOF ;
    public final EObject entryRuleProtocol() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProtocol = null;


        try {
            // InternalThingML.g:982:49: (iv_ruleProtocol= ruleProtocol EOF )
            // InternalThingML.g:983:2: iv_ruleProtocol= ruleProtocol EOF
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
    // InternalThingML.g:989:1: ruleProtocol returns [EObject current=null] : (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' ) ;
    public final EObject ruleProtocol() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        EObject lv_annotations_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:995:2: ( (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' ) )
            // InternalThingML.g:996:2: (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' )
            {
            // InternalThingML.g:996:2: (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' )
            // InternalThingML.g:997:3: otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';'
            {
            otherlv_0=(Token)match(input,30,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getProtocolAccess().getProtocolKeyword_0());
            		
            // InternalThingML.g:1001:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1002:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1002:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1003:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_10); 

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

            // InternalThingML.g:1019:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==13) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalThingML.g:1020:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1020:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1021:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getProtocolAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_10);
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
            	    break loop16;
                }
            } while (true);

            otherlv_3=(Token)match(input,17,FOLLOW_2); 

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
    // InternalThingML.g:1046:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalThingML.g:1046:49: (iv_ruleFunction= ruleFunction EOF )
            // InternalThingML.g:1047:2: iv_ruleFunction= ruleFunction EOF
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
    // InternalThingML.g:1053:1: ruleFunction returns [EObject current=null] : (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* otherlv_6= ')' (otherlv_7= ':' ( (otherlv_8= RULE_ID ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* otherlv_10= 'is' ( (lv_body_11_0= ruleAction ) ) ) ;
    public final EObject ruleFunction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;

        EObject lv_annotations_9_0 = null;

        EObject lv_body_11_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1059:2: ( (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* otherlv_6= ')' (otherlv_7= ':' ( (otherlv_8= RULE_ID ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* otherlv_10= 'is' ( (lv_body_11_0= ruleAction ) ) ) )
            // InternalThingML.g:1060:2: (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* otherlv_6= ')' (otherlv_7= ':' ( (otherlv_8= RULE_ID ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* otherlv_10= 'is' ( (lv_body_11_0= ruleAction ) ) )
            {
            // InternalThingML.g:1060:2: (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* otherlv_6= ')' (otherlv_7= ':' ( (otherlv_8= RULE_ID ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* otherlv_10= 'is' ( (lv_body_11_0= ruleAction ) ) )
            // InternalThingML.g:1061:3: otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* otherlv_6= ')' (otherlv_7= ':' ( (otherlv_8= RULE_ID ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* otherlv_10= 'is' ( (lv_body_11_0= ruleAction ) )
            {
            otherlv_0=(Token)match(input,31,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getFunctionAccess().getFunctionKeyword_0());
            		
            // InternalThingML.g:1065:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1066:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1066:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1067:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_21); 

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

            otherlv_2=(Token)match(input,32,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_2());
            		
            // InternalThingML.g:1087:3: ( (lv_parameters_3_0= ruleParameter ) )
            // InternalThingML.g:1088:4: (lv_parameters_3_0= ruleParameter )
            {
            // InternalThingML.g:1088:4: (lv_parameters_3_0= ruleParameter )
            // InternalThingML.g:1089:5: lv_parameters_3_0= ruleParameter
            {

            					newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_22);
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

            // InternalThingML.g:1106:3: (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==25) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalThingML.g:1107:4: otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) )
            	    {
            	    otherlv_4=(Token)match(input,25,FOLLOW_6); 

            	    				newLeafNode(otherlv_4, grammarAccess.getFunctionAccess().getCommaKeyword_4_0());
            	    			
            	    // InternalThingML.g:1111:4: ( (lv_parameters_5_0= ruleParameter ) )
            	    // InternalThingML.g:1112:5: (lv_parameters_5_0= ruleParameter )
            	    {
            	    // InternalThingML.g:1112:5: (lv_parameters_5_0= ruleParameter )
            	    // InternalThingML.g:1113:6: lv_parameters_5_0= ruleParameter
            	    {

            	    						newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_22);
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
            	    break loop17;
                }
            } while (true);

            otherlv_6=(Token)match(input,33,FOLLOW_23); 

            			newLeafNode(otherlv_6, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_5());
            		
            // InternalThingML.g:1135:3: (otherlv_7= ':' ( (otherlv_8= RULE_ID ) ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==34) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalThingML.g:1136:4: otherlv_7= ':' ( (otherlv_8= RULE_ID ) )
                    {
                    otherlv_7=(Token)match(input,34,FOLLOW_6); 

                    				newLeafNode(otherlv_7, grammarAccess.getFunctionAccess().getColonKeyword_6_0());
                    			
                    // InternalThingML.g:1140:4: ( (otherlv_8= RULE_ID ) )
                    // InternalThingML.g:1141:5: (otherlv_8= RULE_ID )
                    {
                    // InternalThingML.g:1141:5: (otherlv_8= RULE_ID )
                    // InternalThingML.g:1142:6: otherlv_8= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getFunctionRule());
                    						}
                    					
                    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_24); 

                    						newLeafNode(otherlv_8, grammarAccess.getFunctionAccess().getTypeTypeCrossReference_6_1_0());
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:1154:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==13) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalThingML.g:1155:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1155:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1156:5: lv_annotations_9_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getFunctionAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_24);
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
            	    break loop19;
                }
            } while (true);

            otherlv_10=(Token)match(input,35,FOLLOW_25); 

            			newLeafNode(otherlv_10, grammarAccess.getFunctionAccess().getIsKeyword_8());
            		
            // InternalThingML.g:1177:3: ( (lv_body_11_0= ruleAction ) )
            // InternalThingML.g:1178:4: (lv_body_11_0= ruleAction )
            {
            // InternalThingML.g:1178:4: (lv_body_11_0= ruleAction )
            // InternalThingML.g:1179:5: lv_body_11_0= ruleAction
            {

            					newCompositeNode(grammarAccess.getFunctionAccess().getBodyActionParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_2);
            lv_body_11_0=ruleAction();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFunctionRule());
            					}
            					set(
            						current,
            						"body",
            						lv_body_11_0,
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
    // InternalThingML.g:1200:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // InternalThingML.g:1200:49: (iv_ruleProperty= ruleProperty EOF )
            // InternalThingML.g:1201:2: iv_ruleProperty= ruleProperty EOF
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
    // InternalThingML.g:1207:1: ruleProperty returns [EObject current=null] : (otherlv_0= 'property' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        EObject lv_init_5_0 = null;

        EObject lv_annotations_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1213:2: ( (otherlv_0= 'property' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:1214:2: (otherlv_0= 'property' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:1214:2: (otherlv_0= 'property' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:1215:3: otherlv_0= 'property' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,36,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getPropertyAccess().getPropertyKeyword_0());
            		
            // InternalThingML.g:1219:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1220:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1220:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1221:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_26); 

            					newLeafNode(lv_name_1_0, grammarAccess.getPropertyAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPropertyRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.thingml.xtext.ThingML.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,34,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getPropertyAccess().getColonKeyword_2());
            		
            // InternalThingML.g:1241:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:1242:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:1242:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:1243:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPropertyRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_27); 

            					newLeafNode(otherlv_3, grammarAccess.getPropertyAccess().getTypeTypeCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:1254:3: (otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) ) )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==29) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalThingML.g:1255:4: otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) )
                    {
                    otherlv_4=(Token)match(input,29,FOLLOW_19); 

                    				newLeafNode(otherlv_4, grammarAccess.getPropertyAccess().getEqualsSignKeyword_4_0());
                    			
                    // InternalThingML.g:1259:4: ( (lv_init_5_0= ruleExpression ) )
                    // InternalThingML.g:1260:5: (lv_init_5_0= ruleExpression )
                    {
                    // InternalThingML.g:1260:5: (lv_init_5_0= ruleExpression )
                    // InternalThingML.g:1261:6: lv_init_5_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getPropertyAccess().getInitExpressionParserRuleCall_4_1_0());
                    					
                    pushFollow(FOLLOW_13);
                    lv_init_5_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPropertyRule());
                    						}
                    						set(
                    							current,
                    							"init",
                    							lv_init_5_0,
                    							"org.thingml.xtext.ThingML.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:1279:3: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==13) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalThingML.g:1280:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1280:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1281:5: lv_annotations_6_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getPropertyAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_13);
            	    lv_annotations_6_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getPropertyRule());
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
            	    break loop21;
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
    // InternalThingML.g:1302:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // InternalThingML.g:1302:48: (iv_ruleMessage= ruleMessage EOF )
            // InternalThingML.g:1303:2: iv_ruleMessage= ruleMessage EOF
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
    // InternalThingML.g:1309:1: ruleMessage returns [EObject current=null] : (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= ';' ) ;
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
            // InternalThingML.g:1315:2: ( (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= ';' ) )
            // InternalThingML.g:1316:2: (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= ';' )
            {
            // InternalThingML.g:1316:2: (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= ';' )
            // InternalThingML.g:1317:3: otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= ';'
            {
            otherlv_0=(Token)match(input,37,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getMessageAccess().getMessageKeyword_0());
            		
            // InternalThingML.g:1321:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1322:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1322:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1323:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_21); 

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

            otherlv_2=(Token)match(input,32,FOLLOW_28); 

            			newLeafNode(otherlv_2, grammarAccess.getMessageAccess().getLeftParenthesisKeyword_2());
            		
            // InternalThingML.g:1343:3: ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==RULE_ID) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalThingML.g:1344:4: ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    {
                    // InternalThingML.g:1344:4: ( (lv_parameters_3_0= ruleParameter ) )
                    // InternalThingML.g:1345:5: (lv_parameters_3_0= ruleParameter )
                    {
                    // InternalThingML.g:1345:5: (lv_parameters_3_0= ruleParameter )
                    // InternalThingML.g:1346:6: lv_parameters_3_0= ruleParameter
                    {

                    						newCompositeNode(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_0_0());
                    					
                    pushFollow(FOLLOW_22);
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

                    // InternalThingML.g:1363:4: (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( (LA22_0==25) ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // InternalThingML.g:1364:5: otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) )
                    	    {
                    	    otherlv_4=(Token)match(input,25,FOLLOW_6); 

                    	    					newLeafNode(otherlv_4, grammarAccess.getMessageAccess().getCommaKeyword_3_1_0());
                    	    				
                    	    // InternalThingML.g:1368:5: ( (lv_parameters_5_0= ruleParameter ) )
                    	    // InternalThingML.g:1369:6: (lv_parameters_5_0= ruleParameter )
                    	    {
                    	    // InternalThingML.g:1369:6: (lv_parameters_5_0= ruleParameter )
                    	    // InternalThingML.g:1370:7: lv_parameters_5_0= ruleParameter
                    	    {

                    	    							newCompositeNode(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_22);
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
                    	    break loop22;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,33,FOLLOW_10); 

            			newLeafNode(otherlv_6, grammarAccess.getMessageAccess().getRightParenthesisKeyword_4());
            		
            // InternalThingML.g:1393:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==13) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalThingML.g:1394:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1394:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1395:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getMessageAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_10);
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
            	    break loop24;
                }
            } while (true);

            otherlv_8=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getMessageAccess().getSemicolonKeyword_6());
            		

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
    // InternalThingML.g:1420:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalThingML.g:1420:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalThingML.g:1421:2: iv_ruleParameter= ruleParameter EOF
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
    // InternalThingML.g:1427:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject lv_annotations_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1433:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:1434:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:1434:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:1435:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:1435:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:1436:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:1436:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:1437:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_26); 

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

            otherlv_1=(Token)match(input,34,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getParameterAccess().getColonKeyword_1());
            		
            // InternalThingML.g:1457:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:1458:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:1458:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:1459:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getParameterRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_13); 

            					newLeafNode(otherlv_2, grammarAccess.getParameterAccess().getTypeTypeCrossReference_2_0());
            				

            }


            }

            // InternalThingML.g:1470:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==13) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalThingML.g:1471:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1471:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1472:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getParameterAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_13);
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
            	    break loop25;
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
    // InternalThingML.g:1493:1: entryRulePort returns [EObject current=null] : iv_rulePort= rulePort EOF ;
    public final EObject entryRulePort() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePort = null;


        try {
            // InternalThingML.g:1493:45: (iv_rulePort= rulePort EOF )
            // InternalThingML.g:1494:2: iv_rulePort= rulePort EOF
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
    // InternalThingML.g:1500:1: rulePort returns [EObject current=null] : (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort ) ;
    public final EObject rulePort() throws RecognitionException {
        EObject current = null;

        EObject this_RequiredPort_0 = null;

        EObject this_ProvidedPort_1 = null;

        EObject this_InternalPort_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:1506:2: ( (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort ) )
            // InternalThingML.g:1507:2: (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort )
            {
            // InternalThingML.g:1507:2: (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort )
            int alt26=3;
            switch ( input.LA(1) ) {
            case 38:
            case 39:
                {
                alt26=1;
                }
                break;
            case 43:
                {
                alt26=2;
                }
                break;
            case 44:
                {
                alt26=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // InternalThingML.g:1508:3: this_RequiredPort_0= ruleRequiredPort
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
                    // InternalThingML.g:1517:3: this_ProvidedPort_1= ruleProvidedPort
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
                    // InternalThingML.g:1526:3: this_InternalPort_2= ruleInternalPort
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
    // InternalThingML.g:1538:1: entryRuleRequiredPort returns [EObject current=null] : iv_ruleRequiredPort= ruleRequiredPort EOF ;
    public final EObject entryRuleRequiredPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequiredPort = null;


        try {
            // InternalThingML.g:1538:53: (iv_ruleRequiredPort= ruleRequiredPort EOF )
            // InternalThingML.g:1539:2: iv_ruleRequiredPort= ruleRequiredPort EOF
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
    // InternalThingML.g:1545:1: ruleRequiredPort returns [EObject current=null] : ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' ) ;
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
            // InternalThingML.g:1551:2: ( ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' ) )
            // InternalThingML.g:1552:2: ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' )
            {
            // InternalThingML.g:1552:2: ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' )
            // InternalThingML.g:1553:3: ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}'
            {
            // InternalThingML.g:1553:3: ( (lv_optional_0_0= 'optional' ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==38) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalThingML.g:1554:4: (lv_optional_0_0= 'optional' )
                    {
                    // InternalThingML.g:1554:4: (lv_optional_0_0= 'optional' )
                    // InternalThingML.g:1555:5: lv_optional_0_0= 'optional'
                    {
                    lv_optional_0_0=(Token)match(input,38,FOLLOW_29); 

                    					newLeafNode(lv_optional_0_0, grammarAccess.getRequiredPortAccess().getOptionalOptionalKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getRequiredPortRule());
                    					}
                    					setWithLastConsumed(current, "optional", true, "optional");
                    				

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,39,FOLLOW_30); 

            			newLeafNode(otherlv_1, grammarAccess.getRequiredPortAccess().getRequiredKeyword_1());
            		
            otherlv_2=(Token)match(input,40,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getRequiredPortAccess().getPortKeyword_2());
            		
            // InternalThingML.g:1575:3: ( (lv_name_3_0= RULE_ID ) )
            // InternalThingML.g:1576:4: (lv_name_3_0= RULE_ID )
            {
            // InternalThingML.g:1576:4: (lv_name_3_0= RULE_ID )
            // InternalThingML.g:1577:5: lv_name_3_0= RULE_ID
            {
            lv_name_3_0=(Token)match(input,RULE_ID,FOLLOW_11); 

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

            // InternalThingML.g:1593:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==13) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalThingML.g:1594:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1594:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1595:5: lv_annotations_4_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getRequiredPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_11);
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
            	    break loop28;
                }
            } while (true);

            otherlv_5=(Token)match(input,20,FOLLOW_31); 

            			newLeafNode(otherlv_5, grammarAccess.getRequiredPortAccess().getLeftCurlyBracketKeyword_5());
            		
            // InternalThingML.g:1616:3: ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )*
            loop31:
            do {
                int alt31=3;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==41) ) {
                    alt31=1;
                }
                else if ( (LA31_0==42) ) {
                    alt31=2;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalThingML.g:1617:4: (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1617:4: (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* )
            	    // InternalThingML.g:1618:5: otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )*
            	    {
            	    otherlv_6=(Token)match(input,41,FOLLOW_6); 

            	    					newLeafNode(otherlv_6, grammarAccess.getRequiredPortAccess().getSendsKeyword_6_0_0());
            	    				
            	    // InternalThingML.g:1622:5: ( (otherlv_7= RULE_ID ) )
            	    // InternalThingML.g:1623:6: (otherlv_7= RULE_ID )
            	    {
            	    // InternalThingML.g:1623:6: (otherlv_7= RULE_ID )
            	    // InternalThingML.g:1624:7: otherlv_7= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getRequiredPortRule());
            	    							}
            	    						
            	    otherlv_7=(Token)match(input,RULE_ID,FOLLOW_32); 

            	    							newLeafNode(otherlv_7, grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1635:5: (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )*
            	    loop29:
            	    do {
            	        int alt29=2;
            	        int LA29_0 = input.LA(1);

            	        if ( (LA29_0==25) ) {
            	            alt29=1;
            	        }


            	        switch (alt29) {
            	    	case 1 :
            	    	    // InternalThingML.g:1636:6: otherlv_8= ',' ( (otherlv_9= RULE_ID ) )
            	    	    {
            	    	    otherlv_8=(Token)match(input,25,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_8, grammarAccess.getRequiredPortAccess().getCommaKeyword_6_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:1640:6: ( (otherlv_9= RULE_ID ) )
            	    	    // InternalThingML.g:1641:7: (otherlv_9= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1641:7: (otherlv_9= RULE_ID )
            	    	    // InternalThingML.g:1642:8: otherlv_9= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getRequiredPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_9=(Token)match(input,RULE_ID,FOLLOW_32); 

            	    	    								newLeafNode(otherlv_9, grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop29;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:1656:4: (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1656:4: (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* )
            	    // InternalThingML.g:1657:5: otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )*
            	    {
            	    otherlv_10=(Token)match(input,42,FOLLOW_6); 

            	    					newLeafNode(otherlv_10, grammarAccess.getRequiredPortAccess().getReceivesKeyword_6_1_0());
            	    				
            	    // InternalThingML.g:1661:5: ( (otherlv_11= RULE_ID ) )
            	    // InternalThingML.g:1662:6: (otherlv_11= RULE_ID )
            	    {
            	    // InternalThingML.g:1662:6: (otherlv_11= RULE_ID )
            	    // InternalThingML.g:1663:7: otherlv_11= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getRequiredPortRule());
            	    							}
            	    						
            	    otherlv_11=(Token)match(input,RULE_ID,FOLLOW_32); 

            	    							newLeafNode(otherlv_11, grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1674:5: (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )*
            	    loop30:
            	    do {
            	        int alt30=2;
            	        int LA30_0 = input.LA(1);

            	        if ( (LA30_0==25) ) {
            	            alt30=1;
            	        }


            	        switch (alt30) {
            	    	case 1 :
            	    	    // InternalThingML.g:1675:6: otherlv_12= ',' ( (otherlv_13= RULE_ID ) )
            	    	    {
            	    	    otherlv_12=(Token)match(input,25,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_12, grammarAccess.getRequiredPortAccess().getCommaKeyword_6_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:1679:6: ( (otherlv_13= RULE_ID ) )
            	    	    // InternalThingML.g:1680:7: (otherlv_13= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1680:7: (otherlv_13= RULE_ID )
            	    	    // InternalThingML.g:1681:8: otherlv_13= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getRequiredPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_13=(Token)match(input,RULE_ID,FOLLOW_32); 

            	    	    								newLeafNode(otherlv_13, grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop30;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

            otherlv_14=(Token)match(input,21,FOLLOW_2); 

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
    // InternalThingML.g:1703:1: entryRuleProvidedPort returns [EObject current=null] : iv_ruleProvidedPort= ruleProvidedPort EOF ;
    public final EObject entryRuleProvidedPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProvidedPort = null;


        try {
            // InternalThingML.g:1703:53: (iv_ruleProvidedPort= ruleProvidedPort EOF )
            // InternalThingML.g:1704:2: iv_ruleProvidedPort= ruleProvidedPort EOF
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
    // InternalThingML.g:1710:1: ruleProvidedPort returns [EObject current=null] : (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) ;
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
            // InternalThingML.g:1716:2: ( (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) )
            // InternalThingML.g:1717:2: (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            {
            // InternalThingML.g:1717:2: (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            // InternalThingML.g:1718:3: otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,43,FOLLOW_30); 

            			newLeafNode(otherlv_0, grammarAccess.getProvidedPortAccess().getProvidedKeyword_0());
            		
            otherlv_1=(Token)match(input,40,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getProvidedPortAccess().getPortKeyword_1());
            		
            // InternalThingML.g:1726:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:1727:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:1727:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:1728:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_11); 

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

            // InternalThingML.g:1744:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==13) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalThingML.g:1745:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1745:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1746:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getProvidedPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_11);
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
            	    break loop32;
                }
            } while (true);

            otherlv_4=(Token)match(input,20,FOLLOW_31); 

            			newLeafNode(otherlv_4, grammarAccess.getProvidedPortAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalThingML.g:1767:3: ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )*
            loop35:
            do {
                int alt35=3;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==41) ) {
                    alt35=1;
                }
                else if ( (LA35_0==42) ) {
                    alt35=2;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalThingML.g:1768:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1768:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    // InternalThingML.g:1769:5: otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    {
            	    otherlv_5=(Token)match(input,41,FOLLOW_6); 

            	    					newLeafNode(otherlv_5, grammarAccess.getProvidedPortAccess().getSendsKeyword_5_0_0());
            	    				
            	    // InternalThingML.g:1773:5: ( (otherlv_6= RULE_ID ) )
            	    // InternalThingML.g:1774:6: (otherlv_6= RULE_ID )
            	    {
            	    // InternalThingML.g:1774:6: (otherlv_6= RULE_ID )
            	    // InternalThingML.g:1775:7: otherlv_6= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getProvidedPortRule());
            	    							}
            	    						
            	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_32); 

            	    							newLeafNode(otherlv_6, grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1786:5: (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    loop33:
            	    do {
            	        int alt33=2;
            	        int LA33_0 = input.LA(1);

            	        if ( (LA33_0==25) ) {
            	            alt33=1;
            	        }


            	        switch (alt33) {
            	    	case 1 :
            	    	    // InternalThingML.g:1787:6: otherlv_7= ',' ( (otherlv_8= RULE_ID ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,25,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_7, grammarAccess.getProvidedPortAccess().getCommaKeyword_5_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:1791:6: ( (otherlv_8= RULE_ID ) )
            	    	    // InternalThingML.g:1792:7: (otherlv_8= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1792:7: (otherlv_8= RULE_ID )
            	    	    // InternalThingML.g:1793:8: otherlv_8= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getProvidedPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_32); 

            	    	    								newLeafNode(otherlv_8, grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop33;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:1807:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1807:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    // InternalThingML.g:1808:5: otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    {
            	    otherlv_9=(Token)match(input,42,FOLLOW_6); 

            	    					newLeafNode(otherlv_9, grammarAccess.getProvidedPortAccess().getReceivesKeyword_5_1_0());
            	    				
            	    // InternalThingML.g:1812:5: ( (otherlv_10= RULE_ID ) )
            	    // InternalThingML.g:1813:6: (otherlv_10= RULE_ID )
            	    {
            	    // InternalThingML.g:1813:6: (otherlv_10= RULE_ID )
            	    // InternalThingML.g:1814:7: otherlv_10= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getProvidedPortRule());
            	    							}
            	    						
            	    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_32); 

            	    							newLeafNode(otherlv_10, grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1825:5: (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    loop34:
            	    do {
            	        int alt34=2;
            	        int LA34_0 = input.LA(1);

            	        if ( (LA34_0==25) ) {
            	            alt34=1;
            	        }


            	        switch (alt34) {
            	    	case 1 :
            	    	    // InternalThingML.g:1826:6: otherlv_11= ',' ( (otherlv_12= RULE_ID ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,25,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_11, grammarAccess.getProvidedPortAccess().getCommaKeyword_5_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:1830:6: ( (otherlv_12= RULE_ID ) )
            	    	    // InternalThingML.g:1831:7: (otherlv_12= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1831:7: (otherlv_12= RULE_ID )
            	    	    // InternalThingML.g:1832:8: otherlv_12= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getProvidedPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_12=(Token)match(input,RULE_ID,FOLLOW_32); 

            	    	    								newLeafNode(otherlv_12, grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop34;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);

            otherlv_13=(Token)match(input,21,FOLLOW_2); 

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
    // InternalThingML.g:1854:1: entryRuleInternalPort returns [EObject current=null] : iv_ruleInternalPort= ruleInternalPort EOF ;
    public final EObject entryRuleInternalPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInternalPort = null;


        try {
            // InternalThingML.g:1854:53: (iv_ruleInternalPort= ruleInternalPort EOF )
            // InternalThingML.g:1855:2: iv_ruleInternalPort= ruleInternalPort EOF
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
    // InternalThingML.g:1861:1: ruleInternalPort returns [EObject current=null] : (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) ;
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
            // InternalThingML.g:1867:2: ( (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) )
            // InternalThingML.g:1868:2: (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            {
            // InternalThingML.g:1868:2: (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            // InternalThingML.g:1869:3: otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,44,FOLLOW_30); 

            			newLeafNode(otherlv_0, grammarAccess.getInternalPortAccess().getInternalKeyword_0());
            		
            otherlv_1=(Token)match(input,40,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getInternalPortAccess().getPortKeyword_1());
            		
            // InternalThingML.g:1877:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:1878:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:1878:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:1879:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_11); 

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

            // InternalThingML.g:1895:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==13) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalThingML.g:1896:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1896:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1897:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getInternalPortAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_11);
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
            	    break loop36;
                }
            } while (true);

            otherlv_4=(Token)match(input,20,FOLLOW_31); 

            			newLeafNode(otherlv_4, grammarAccess.getInternalPortAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalThingML.g:1918:3: ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )*
            loop39:
            do {
                int alt39=3;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==41) ) {
                    alt39=1;
                }
                else if ( (LA39_0==42) ) {
                    alt39=2;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalThingML.g:1919:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1919:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    // InternalThingML.g:1920:5: otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    {
            	    otherlv_5=(Token)match(input,41,FOLLOW_6); 

            	    					newLeafNode(otherlv_5, grammarAccess.getInternalPortAccess().getSendsKeyword_5_0_0());
            	    				
            	    // InternalThingML.g:1924:5: ( (otherlv_6= RULE_ID ) )
            	    // InternalThingML.g:1925:6: (otherlv_6= RULE_ID )
            	    {
            	    // InternalThingML.g:1925:6: (otherlv_6= RULE_ID )
            	    // InternalThingML.g:1926:7: otherlv_6= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getInternalPortRule());
            	    							}
            	    						
            	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_32); 

            	    							newLeafNode(otherlv_6, grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1937:5: (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    loop37:
            	    do {
            	        int alt37=2;
            	        int LA37_0 = input.LA(1);

            	        if ( (LA37_0==25) ) {
            	            alt37=1;
            	        }


            	        switch (alt37) {
            	    	case 1 :
            	    	    // InternalThingML.g:1938:6: otherlv_7= ',' ( (otherlv_8= RULE_ID ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,25,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_7, grammarAccess.getInternalPortAccess().getCommaKeyword_5_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:1942:6: ( (otherlv_8= RULE_ID ) )
            	    	    // InternalThingML.g:1943:7: (otherlv_8= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1943:7: (otherlv_8= RULE_ID )
            	    	    // InternalThingML.g:1944:8: otherlv_8= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getInternalPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_32); 

            	    	    								newLeafNode(otherlv_8, grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_2_1_0());
            	    	    							

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
            	case 2 :
            	    // InternalThingML.g:1958:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1958:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    // InternalThingML.g:1959:5: otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    {
            	    otherlv_9=(Token)match(input,42,FOLLOW_6); 

            	    					newLeafNode(otherlv_9, grammarAccess.getInternalPortAccess().getReceivesKeyword_5_1_0());
            	    				
            	    // InternalThingML.g:1963:5: ( (otherlv_10= RULE_ID ) )
            	    // InternalThingML.g:1964:6: (otherlv_10= RULE_ID )
            	    {
            	    // InternalThingML.g:1964:6: (otherlv_10= RULE_ID )
            	    // InternalThingML.g:1965:7: otherlv_10= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getInternalPortRule());
            	    							}
            	    						
            	    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_32); 

            	    							newLeafNode(otherlv_10, grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1976:5: (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    loop38:
            	    do {
            	        int alt38=2;
            	        int LA38_0 = input.LA(1);

            	        if ( (LA38_0==25) ) {
            	            alt38=1;
            	        }


            	        switch (alt38) {
            	    	case 1 :
            	    	    // InternalThingML.g:1977:6: otherlv_11= ',' ( (otherlv_12= RULE_ID ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,25,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_11, grammarAccess.getInternalPortAccess().getCommaKeyword_5_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:1981:6: ( (otherlv_12= RULE_ID ) )
            	    	    // InternalThingML.g:1982:7: (otherlv_12= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1982:7: (otherlv_12= RULE_ID )
            	    	    // InternalThingML.g:1983:8: otherlv_12= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getInternalPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_12=(Token)match(input,RULE_ID,FOLLOW_32); 

            	    	    								newLeafNode(otherlv_12, grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop38;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop39;
                }
            } while (true);

            otherlv_13=(Token)match(input,21,FOLLOW_2); 

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
    // InternalThingML.g:2005:1: entryRuleStream returns [EObject current=null] : iv_ruleStream= ruleStream EOF ;
    public final EObject entryRuleStream() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStream = null;


        try {
            // InternalThingML.g:2005:47: (iv_ruleStream= ruleStream EOF )
            // InternalThingML.g:2006:2: iv_ruleStream= ruleStream EOF
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
    // InternalThingML.g:2012:1: ruleStream returns [EObject current=null] : (otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) ) ) ;
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
            // InternalThingML.g:2018:2: ( (otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) ) ) )
            // InternalThingML.g:2019:2: (otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) ) )
            {
            // InternalThingML.g:2019:2: (otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) ) )
            // InternalThingML.g:2020:3: otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) )
            {
            otherlv_0=(Token)match(input,45,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getStreamAccess().getStreamKeyword_0());
            		
            // InternalThingML.g:2024:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:2025:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:2025:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:2026:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_33); 

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

            // InternalThingML.g:2042:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==13) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalThingML.g:2043:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2043:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2044:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getStreamAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_33);
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
            	    break loop40;
                }
            } while (true);

            otherlv_3=(Token)match(input,46,FOLLOW_34); 

            			newLeafNode(otherlv_3, grammarAccess.getStreamAccess().getFromKeyword_3());
            		
            // InternalThingML.g:2065:3: ( (lv_input_4_0= ruleSource ) )
            // InternalThingML.g:2066:4: (lv_input_4_0= ruleSource )
            {
            // InternalThingML.g:2066:4: (lv_input_4_0= ruleSource )
            // InternalThingML.g:2067:5: lv_input_4_0= ruleSource
            {

            					newCompositeNode(grammarAccess.getStreamAccess().getInputSourceParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_35);
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

            // InternalThingML.g:2084:3: (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==47) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalThingML.g:2085:4: otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )*
                    {
                    otherlv_5=(Token)match(input,47,FOLLOW_25); 

                    				newLeafNode(otherlv_5, grammarAccess.getStreamAccess().getSelectKeyword_5_0());
                    			
                    // InternalThingML.g:2089:4: ( (lv_selection_6_0= ruleLocalVariable ) )
                    // InternalThingML.g:2090:5: (lv_selection_6_0= ruleLocalVariable )
                    {
                    // InternalThingML.g:2090:5: (lv_selection_6_0= ruleLocalVariable )
                    // InternalThingML.g:2091:6: lv_selection_6_0= ruleLocalVariable
                    {

                    						newCompositeNode(grammarAccess.getStreamAccess().getSelectionLocalVariableParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_36);
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

                    // InternalThingML.g:2108:4: (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )*
                    loop41:
                    do {
                        int alt41=2;
                        int LA41_0 = input.LA(1);

                        if ( (LA41_0==25) ) {
                            alt41=1;
                        }


                        switch (alt41) {
                    	case 1 :
                    	    // InternalThingML.g:2109:5: otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) )
                    	    {
                    	    otherlv_7=(Token)match(input,25,FOLLOW_25); 

                    	    					newLeafNode(otherlv_7, grammarAccess.getStreamAccess().getCommaKeyword_5_2_0());
                    	    				
                    	    // InternalThingML.g:2113:5: ( (lv_selection_8_0= ruleLocalVariable ) )
                    	    // InternalThingML.g:2114:6: (lv_selection_8_0= ruleLocalVariable )
                    	    {
                    	    // InternalThingML.g:2114:6: (lv_selection_8_0= ruleLocalVariable )
                    	    // InternalThingML.g:2115:7: lv_selection_8_0= ruleLocalVariable
                    	    {

                    	    							newCompositeNode(grammarAccess.getStreamAccess().getSelectionLocalVariableParserRuleCall_5_2_1_0());
                    	    						
                    	    pushFollow(FOLLOW_36);
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
                    	    break loop41;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_9=(Token)match(input,48,FOLLOW_6); 

            			newLeafNode(otherlv_9, grammarAccess.getStreamAccess().getProduceKeyword_6());
            		
            // InternalThingML.g:2138:3: ( (lv_output_10_0= ruleSendAction ) )
            // InternalThingML.g:2139:4: (lv_output_10_0= ruleSendAction )
            {
            // InternalThingML.g:2139:4: (lv_output_10_0= ruleSendAction )
            // InternalThingML.g:2140:5: lv_output_10_0= ruleSendAction
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
    // InternalThingML.g:2161:1: entryRuleSource returns [EObject current=null] : iv_ruleSource= ruleSource EOF ;
    public final EObject entryRuleSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSource = null;


        try {
            // InternalThingML.g:2161:47: (iv_ruleSource= ruleSource EOF )
            // InternalThingML.g:2162:2: iv_ruleSource= ruleSource EOF
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
    // InternalThingML.g:2168:1: ruleSource returns [EObject current=null] : (this_JoinSources_0= ruleJoinSources | this_MergeSources_1= ruleMergeSources | this_SimpleSource_2= ruleSimpleSource ) ;
    public final EObject ruleSource() throws RecognitionException {
        EObject current = null;

        EObject this_JoinSources_0 = null;

        EObject this_MergeSources_1 = null;

        EObject this_SimpleSource_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:2174:2: ( (this_JoinSources_0= ruleJoinSources | this_MergeSources_1= ruleMergeSources | this_SimpleSource_2= ruleSimpleSource ) )
            // InternalThingML.g:2175:2: (this_JoinSources_0= ruleJoinSources | this_MergeSources_1= ruleMergeSources | this_SimpleSource_2= ruleSimpleSource )
            {
            // InternalThingML.g:2175:2: (this_JoinSources_0= ruleJoinSources | this_MergeSources_1= ruleMergeSources | this_SimpleSource_2= ruleSimpleSource )
            int alt43=3;
            switch ( input.LA(1) ) {
            case 49:
                {
                alt43=1;
                }
                break;
            case 53:
                {
                alt43=2;
                }
                break;
            case RULE_ID:
                {
                alt43=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }

            switch (alt43) {
                case 1 :
                    // InternalThingML.g:2176:3: this_JoinSources_0= ruleJoinSources
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
                    // InternalThingML.g:2185:3: this_MergeSources_1= ruleMergeSources
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
                    // InternalThingML.g:2194:3: this_SimpleSource_2= ruleSimpleSource
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
    // InternalThingML.g:2206:1: entryRuleViewSource returns [EObject current=null] : iv_ruleViewSource= ruleViewSource EOF ;
    public final EObject entryRuleViewSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleViewSource = null;


        try {
            // InternalThingML.g:2206:51: (iv_ruleViewSource= ruleViewSource EOF )
            // InternalThingML.g:2207:2: iv_ruleViewSource= ruleViewSource EOF
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
    // InternalThingML.g:2213:1: ruleViewSource returns [EObject current=null] : (this_Filter_0= ruleFilter | this_LengthWindow_1= ruleLengthWindow | this_TimeWindow_2= ruleTimeWindow ) ;
    public final EObject ruleViewSource() throws RecognitionException {
        EObject current = null;

        EObject this_Filter_0 = null;

        EObject this_LengthWindow_1 = null;

        EObject this_TimeWindow_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:2219:2: ( (this_Filter_0= ruleFilter | this_LengthWindow_1= ruleLengthWindow | this_TimeWindow_2= ruleTimeWindow ) )
            // InternalThingML.g:2220:2: (this_Filter_0= ruleFilter | this_LengthWindow_1= ruleLengthWindow | this_TimeWindow_2= ruleTimeWindow )
            {
            // InternalThingML.g:2220:2: (this_Filter_0= ruleFilter | this_LengthWindow_1= ruleLengthWindow | this_TimeWindow_2= ruleTimeWindow )
            int alt44=3;
            switch ( input.LA(1) ) {
            case 55:
                {
                alt44=1;
                }
                break;
            case 57:
                {
                alt44=2;
                }
                break;
            case 59:
                {
                alt44=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }

            switch (alt44) {
                case 1 :
                    // InternalThingML.g:2221:3: this_Filter_0= ruleFilter
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
                    // InternalThingML.g:2230:3: this_LengthWindow_1= ruleLengthWindow
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
                    // InternalThingML.g:2239:3: this_TimeWindow_2= ruleTimeWindow
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


    // $ANTLR start "entryRuleJoinSources"
    // InternalThingML.g:2251:1: entryRuleJoinSources returns [EObject current=null] : iv_ruleJoinSources= ruleJoinSources EOF ;
    public final EObject entryRuleJoinSources() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJoinSources = null;


        try {
            // InternalThingML.g:2251:52: (iv_ruleJoinSources= ruleJoinSources EOF )
            // InternalThingML.g:2252:2: iv_ruleJoinSources= ruleJoinSources EOF
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
    // InternalThingML.g:2258:1: ruleJoinSources returns [EObject current=null] : (otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )* ) ;
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
            // InternalThingML.g:2264:2: ( (otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )* ) )
            // InternalThingML.g:2265:2: (otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )* )
            {
            // InternalThingML.g:2265:2: (otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )* )
            // InternalThingML.g:2266:3: otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )*
            {
            otherlv_0=(Token)match(input,49,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getJoinSourcesAccess().getJoinKeyword_0());
            		
            // InternalThingML.g:2270:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:2271:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:2271:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:2272:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_26); 

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

            otherlv_2=(Token)match(input,34,FOLLOW_37); 

            			newLeafNode(otherlv_2, grammarAccess.getJoinSourcesAccess().getColonKeyword_2());
            		
            otherlv_3=(Token)match(input,27,FOLLOW_34); 

            			newLeafNode(otherlv_3, grammarAccess.getJoinSourcesAccess().getLeftSquareBracketKeyword_3());
            		
            // InternalThingML.g:2296:3: ( (lv_sources_4_0= ruleSource ) )
            // InternalThingML.g:2297:4: (lv_sources_4_0= ruleSource )
            {
            // InternalThingML.g:2297:4: (lv_sources_4_0= ruleSource )
            // InternalThingML.g:2298:5: lv_sources_4_0= ruleSource
            {

            					newCompositeNode(grammarAccess.getJoinSourcesAccess().getSourcesSourceParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_38);
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

            // InternalThingML.g:2315:3: (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==50) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalThingML.g:2316:4: otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) )
            	    {
            	    otherlv_5=(Token)match(input,50,FOLLOW_34); 

            	    				newLeafNode(otherlv_5, grammarAccess.getJoinSourcesAccess().getAmpersandKeyword_5_0());
            	    			
            	    // InternalThingML.g:2320:4: ( (lv_sources_6_0= ruleSource ) )
            	    // InternalThingML.g:2321:5: (lv_sources_6_0= ruleSource )
            	    {
            	    // InternalThingML.g:2321:5: (lv_sources_6_0= ruleSource )
            	    // InternalThingML.g:2322:6: lv_sources_6_0= ruleSource
            	    {

            	    						newCompositeNode(grammarAccess.getJoinSourcesAccess().getSourcesSourceParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_38);
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
            	    break loop45;
                }
            } while (true);

            otherlv_7=(Token)match(input,51,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getJoinSourcesAccess().getHyphenMinusGreaterThanSignKeyword_6());
            		
            // InternalThingML.g:2344:3: ( (otherlv_8= RULE_ID ) )
            // InternalThingML.g:2345:4: (otherlv_8= RULE_ID )
            {
            // InternalThingML.g:2345:4: (otherlv_8= RULE_ID )
            // InternalThingML.g:2346:5: otherlv_8= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getJoinSourcesRule());
            					}
            				
            otherlv_8=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_8, grammarAccess.getJoinSourcesAccess().getResultMessageMessageCrossReference_7_0());
            				

            }


            }

            otherlv_9=(Token)match(input,32,FOLLOW_19); 

            			newLeafNode(otherlv_9, grammarAccess.getJoinSourcesAccess().getLeftParenthesisKeyword_8());
            		
            // InternalThingML.g:2361:3: ( (lv_rules_10_0= ruleExpression ) )
            // InternalThingML.g:2362:4: (lv_rules_10_0= ruleExpression )
            {
            // InternalThingML.g:2362:4: (lv_rules_10_0= ruleExpression )
            // InternalThingML.g:2363:5: lv_rules_10_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getJoinSourcesAccess().getRulesExpressionParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_22);
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

            // InternalThingML.g:2380:3: (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==25) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalThingML.g:2381:4: otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) )
            	    {
            	    otherlv_11=(Token)match(input,25,FOLLOW_19); 

            	    				newLeafNode(otherlv_11, grammarAccess.getJoinSourcesAccess().getCommaKeyword_10_0());
            	    			
            	    // InternalThingML.g:2385:4: ( (lv_rules_12_0= ruleExpression ) )
            	    // InternalThingML.g:2386:5: (lv_rules_12_0= ruleExpression )
            	    {
            	    // InternalThingML.g:2386:5: (lv_rules_12_0= ruleExpression )
            	    // InternalThingML.g:2387:6: lv_rules_12_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getJoinSourcesAccess().getRulesExpressionParserRuleCall_10_1_0());
            	    					
            	    pushFollow(FOLLOW_22);
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
            	    break loop46;
                }
            } while (true);

            otherlv_13=(Token)match(input,33,FOLLOW_20); 

            			newLeafNode(otherlv_13, grammarAccess.getJoinSourcesAccess().getRightParenthesisKeyword_11());
            		
            otherlv_14=(Token)match(input,28,FOLLOW_39); 

            			newLeafNode(otherlv_14, grammarAccess.getJoinSourcesAccess().getRightSquareBracketKeyword_12());
            		
            // InternalThingML.g:2413:3: (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==52) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalThingML.g:2414:4: otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) )
            	    {
            	    otherlv_15=(Token)match(input,52,FOLLOW_40); 

            	    				newLeafNode(otherlv_15, grammarAccess.getJoinSourcesAccess().getColonColonKeyword_13_0());
            	    			
            	    // InternalThingML.g:2418:4: ( (lv_operators_16_0= ruleViewSource ) )
            	    // InternalThingML.g:2419:5: (lv_operators_16_0= ruleViewSource )
            	    {
            	    // InternalThingML.g:2419:5: (lv_operators_16_0= ruleViewSource )
            	    // InternalThingML.g:2420:6: lv_operators_16_0= ruleViewSource
            	    {

            	    						newCompositeNode(grammarAccess.getJoinSourcesAccess().getOperatorsViewSourceParserRuleCall_13_1_0());
            	    					
            	    pushFollow(FOLLOW_39);
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
            	    break loop47;
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
    // InternalThingML.g:2442:1: entryRuleMergeSources returns [EObject current=null] : iv_ruleMergeSources= ruleMergeSources EOF ;
    public final EObject entryRuleMergeSources() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMergeSources = null;


        try {
            // InternalThingML.g:2442:53: (iv_ruleMergeSources= ruleMergeSources EOF )
            // InternalThingML.g:2443:2: iv_ruleMergeSources= ruleMergeSources EOF
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
    // InternalThingML.g:2449:1: ruleMergeSources returns [EObject current=null] : (otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )* ) ;
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
            // InternalThingML.g:2455:2: ( (otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )* ) )
            // InternalThingML.g:2456:2: (otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )* )
            {
            // InternalThingML.g:2456:2: (otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )* )
            // InternalThingML.g:2457:3: otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )*
            {
            otherlv_0=(Token)match(input,53,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getMergeSourcesAccess().getMergeKeyword_0());
            		
            // InternalThingML.g:2461:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:2462:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:2462:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:2463:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_26); 

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

            otherlv_2=(Token)match(input,34,FOLLOW_37); 

            			newLeafNode(otherlv_2, grammarAccess.getMergeSourcesAccess().getColonKeyword_2());
            		
            otherlv_3=(Token)match(input,27,FOLLOW_34); 

            			newLeafNode(otherlv_3, grammarAccess.getMergeSourcesAccess().getLeftSquareBracketKeyword_3());
            		
            // InternalThingML.g:2487:3: ( (lv_sources_4_0= ruleSource ) )
            // InternalThingML.g:2488:4: (lv_sources_4_0= ruleSource )
            {
            // InternalThingML.g:2488:4: (lv_sources_4_0= ruleSource )
            // InternalThingML.g:2489:5: lv_sources_4_0= ruleSource
            {

            					newCompositeNode(grammarAccess.getMergeSourcesAccess().getSourcesSourceParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_41);
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

            // InternalThingML.g:2506:3: (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==54) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalThingML.g:2507:4: otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) )
            	    {
            	    otherlv_5=(Token)match(input,54,FOLLOW_34); 

            	    				newLeafNode(otherlv_5, grammarAccess.getMergeSourcesAccess().getVerticalLineKeyword_5_0());
            	    			
            	    // InternalThingML.g:2511:4: ( (lv_sources_6_0= ruleSource ) )
            	    // InternalThingML.g:2512:5: (lv_sources_6_0= ruleSource )
            	    {
            	    // InternalThingML.g:2512:5: (lv_sources_6_0= ruleSource )
            	    // InternalThingML.g:2513:6: lv_sources_6_0= ruleSource
            	    {

            	    						newCompositeNode(grammarAccess.getMergeSourcesAccess().getSourcesSourceParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_41);
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
            	    break loop48;
                }
            } while (true);

            otherlv_7=(Token)match(input,51,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getMergeSourcesAccess().getHyphenMinusGreaterThanSignKeyword_6());
            		
            // InternalThingML.g:2535:3: ( (otherlv_8= RULE_ID ) )
            // InternalThingML.g:2536:4: (otherlv_8= RULE_ID )
            {
            // InternalThingML.g:2536:4: (otherlv_8= RULE_ID )
            // InternalThingML.g:2537:5: otherlv_8= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMergeSourcesRule());
            					}
            				
            otherlv_8=(Token)match(input,RULE_ID,FOLLOW_20); 

            					newLeafNode(otherlv_8, grammarAccess.getMergeSourcesAccess().getResultMessageMessageCrossReference_7_0());
            				

            }


            }

            otherlv_9=(Token)match(input,28,FOLLOW_39); 

            			newLeafNode(otherlv_9, grammarAccess.getMergeSourcesAccess().getRightSquareBracketKeyword_8());
            		
            // InternalThingML.g:2552:3: (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==52) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalThingML.g:2553:4: otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) )
            	    {
            	    otherlv_10=(Token)match(input,52,FOLLOW_40); 

            	    				newLeafNode(otherlv_10, grammarAccess.getMergeSourcesAccess().getColonColonKeyword_9_0());
            	    			
            	    // InternalThingML.g:2557:4: ( (lv_operators_11_0= ruleViewSource ) )
            	    // InternalThingML.g:2558:5: (lv_operators_11_0= ruleViewSource )
            	    {
            	    // InternalThingML.g:2558:5: (lv_operators_11_0= ruleViewSource )
            	    // InternalThingML.g:2559:6: lv_operators_11_0= ruleViewSource
            	    {

            	    						newCompositeNode(grammarAccess.getMergeSourcesAccess().getOperatorsViewSourceParserRuleCall_9_1_0());
            	    					
            	    pushFollow(FOLLOW_39);
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
            	    break loop49;
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
    // InternalThingML.g:2581:1: entryRuleSimpleSource returns [EObject current=null] : iv_ruleSimpleSource= ruleSimpleSource EOF ;
    public final EObject entryRuleSimpleSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleSource = null;


        try {
            // InternalThingML.g:2581:53: (iv_ruleSimpleSource= ruleSimpleSource EOF )
            // InternalThingML.g:2582:2: iv_ruleSimpleSource= ruleSimpleSource EOF
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
    // InternalThingML.g:2588:1: ruleSimpleSource returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )* ) ;
    public final EObject ruleSimpleSource() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_message_2_0 = null;

        EObject lv_operators_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2594:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )* ) )
            // InternalThingML.g:2595:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )* )
            {
            // InternalThingML.g:2595:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )* )
            // InternalThingML.g:2596:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )*
            {
            // InternalThingML.g:2596:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:2597:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:2597:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:2598:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_26); 

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

            otherlv_1=(Token)match(input,34,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getSimpleSourceAccess().getColonKeyword_1());
            		
            // InternalThingML.g:2618:3: ( (lv_message_2_0= ruleReceiveMessage ) )
            // InternalThingML.g:2619:4: (lv_message_2_0= ruleReceiveMessage )
            {
            // InternalThingML.g:2619:4: (lv_message_2_0= ruleReceiveMessage )
            // InternalThingML.g:2620:5: lv_message_2_0= ruleReceiveMessage
            {

            					newCompositeNode(grammarAccess.getSimpleSourceAccess().getMessageReceiveMessageParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_39);
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

            // InternalThingML.g:2637:3: (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==52) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalThingML.g:2638:4: otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) )
            	    {
            	    otherlv_3=(Token)match(input,52,FOLLOW_40); 

            	    				newLeafNode(otherlv_3, grammarAccess.getSimpleSourceAccess().getColonColonKeyword_3_0());
            	    			
            	    // InternalThingML.g:2642:4: ( (lv_operators_4_0= ruleViewSource ) )
            	    // InternalThingML.g:2643:5: (lv_operators_4_0= ruleViewSource )
            	    {
            	    // InternalThingML.g:2643:5: (lv_operators_4_0= ruleViewSource )
            	    // InternalThingML.g:2644:6: lv_operators_4_0= ruleViewSource
            	    {

            	    						newCompositeNode(grammarAccess.getSimpleSourceAccess().getOperatorsViewSourceParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_39);
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
            	    break loop50;
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
    // InternalThingML.g:2666:1: entryRuleFilter returns [EObject current=null] : iv_ruleFilter= ruleFilter EOF ;
    public final EObject entryRuleFilter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFilter = null;


        try {
            // InternalThingML.g:2666:47: (iv_ruleFilter= ruleFilter EOF )
            // InternalThingML.g:2667:2: iv_ruleFilter= ruleFilter EOF
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
    // InternalThingML.g:2673:1: ruleFilter returns [EObject current=null] : (otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) ) ) ;
    public final EObject ruleFilter() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_guard_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2679:2: ( (otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) ) ) )
            // InternalThingML.g:2680:2: (otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) ) )
            {
            // InternalThingML.g:2680:2: (otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) ) )
            // InternalThingML.g:2681:3: otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,55,FOLLOW_42); 

            			newLeafNode(otherlv_0, grammarAccess.getFilterAccess().getKeepKeyword_0());
            		
            otherlv_1=(Token)match(input,56,FOLLOW_19); 

            			newLeafNode(otherlv_1, grammarAccess.getFilterAccess().getIfKeyword_1());
            		
            // InternalThingML.g:2689:3: ( (lv_guard_2_0= ruleExpression ) )
            // InternalThingML.g:2690:4: (lv_guard_2_0= ruleExpression )
            {
            // InternalThingML.g:2690:4: (lv_guard_2_0= ruleExpression )
            // InternalThingML.g:2691:5: lv_guard_2_0= ruleExpression
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
    // InternalThingML.g:2712:1: entryRuleLengthWindow returns [EObject current=null] : iv_ruleLengthWindow= ruleLengthWindow EOF ;
    public final EObject entryRuleLengthWindow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLengthWindow = null;


        try {
            // InternalThingML.g:2712:53: (iv_ruleLengthWindow= ruleLengthWindow EOF )
            // InternalThingML.g:2713:2: iv_ruleLengthWindow= ruleLengthWindow EOF
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
    // InternalThingML.g:2719:1: ruleLengthWindow returns [EObject current=null] : (otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? ) ;
    public final EObject ruleLengthWindow() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_size_1_0 = null;

        EObject lv_step_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2725:2: ( (otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? ) )
            // InternalThingML.g:2726:2: (otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? )
            {
            // InternalThingML.g:2726:2: (otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? )
            // InternalThingML.g:2727:3: otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )?
            {
            otherlv_0=(Token)match(input,57,FOLLOW_19); 

            			newLeafNode(otherlv_0, grammarAccess.getLengthWindowAccess().getBufferKeyword_0());
            		
            // InternalThingML.g:2731:3: ( (lv_size_1_0= ruleExpression ) )
            // InternalThingML.g:2732:4: (lv_size_1_0= ruleExpression )
            {
            // InternalThingML.g:2732:4: (lv_size_1_0= ruleExpression )
            // InternalThingML.g:2733:5: lv_size_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getLengthWindowAccess().getSizeExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_43);
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

            // InternalThingML.g:2750:3: (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==58) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // InternalThingML.g:2751:4: otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) )
                    {
                    otherlv_2=(Token)match(input,58,FOLLOW_19); 

                    				newLeafNode(otherlv_2, grammarAccess.getLengthWindowAccess().getByKeyword_2_0());
                    			
                    // InternalThingML.g:2755:4: ( (lv_step_3_0= ruleExpression ) )
                    // InternalThingML.g:2756:5: (lv_step_3_0= ruleExpression )
                    {
                    // InternalThingML.g:2756:5: (lv_step_3_0= ruleExpression )
                    // InternalThingML.g:2757:6: lv_step_3_0= ruleExpression
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
    // InternalThingML.g:2779:1: entryRuleTimeWindow returns [EObject current=null] : iv_ruleTimeWindow= ruleTimeWindow EOF ;
    public final EObject entryRuleTimeWindow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTimeWindow = null;


        try {
            // InternalThingML.g:2779:51: (iv_ruleTimeWindow= ruleTimeWindow EOF )
            // InternalThingML.g:2780:2: iv_ruleTimeWindow= ruleTimeWindow EOF
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
    // InternalThingML.g:2786:1: ruleTimeWindow returns [EObject current=null] : (otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? ) ;
    public final EObject ruleTimeWindow() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_duration_1_0 = null;

        EObject lv_step_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2792:2: ( (otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? ) )
            // InternalThingML.g:2793:2: (otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? )
            {
            // InternalThingML.g:2793:2: (otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? )
            // InternalThingML.g:2794:3: otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )?
            {
            otherlv_0=(Token)match(input,59,FOLLOW_19); 

            			newLeafNode(otherlv_0, grammarAccess.getTimeWindowAccess().getDuringKeyword_0());
            		
            // InternalThingML.g:2798:3: ( (lv_duration_1_0= ruleExpression ) )
            // InternalThingML.g:2799:4: (lv_duration_1_0= ruleExpression )
            {
            // InternalThingML.g:2799:4: (lv_duration_1_0= ruleExpression )
            // InternalThingML.g:2800:5: lv_duration_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getTimeWindowAccess().getDurationExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_43);
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

            // InternalThingML.g:2817:3: (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==58) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalThingML.g:2818:4: otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) )
                    {
                    otherlv_2=(Token)match(input,58,FOLLOW_19); 

                    				newLeafNode(otherlv_2, grammarAccess.getTimeWindowAccess().getByKeyword_2_0());
                    			
                    // InternalThingML.g:2822:4: ( (lv_step_3_0= ruleExpression ) )
                    // InternalThingML.g:2823:5: (lv_step_3_0= ruleExpression )
                    {
                    // InternalThingML.g:2823:5: (lv_step_3_0= ruleExpression )
                    // InternalThingML.g:2824:6: lv_step_3_0= ruleExpression
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
    // InternalThingML.g:2846:1: entryRuleMessageParameter returns [EObject current=null] : iv_ruleMessageParameter= ruleMessageParameter EOF ;
    public final EObject entryRuleMessageParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageParameter = null;


        try {
            // InternalThingML.g:2846:57: (iv_ruleMessageParameter= ruleMessageParameter EOF )
            // InternalThingML.g:2847:2: iv_ruleMessageParameter= ruleMessageParameter EOF
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
    // InternalThingML.g:2853:1: ruleMessageParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleMessageParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:2859:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalThingML.g:2860:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalThingML.g:2860:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) )
            // InternalThingML.g:2861:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) )
            {
            // InternalThingML.g:2861:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:2862:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:2862:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:2863:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_26); 

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

            otherlv_1=(Token)match(input,34,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getMessageParameterAccess().getColonKeyword_1());
            		
            // InternalThingML.g:2883:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:2884:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:2884:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:2885:5: otherlv_2= RULE_ID
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
    // InternalThingML.g:2900:1: entryRuleSimpleParamRef returns [EObject current=null] : iv_ruleSimpleParamRef= ruleSimpleParamRef EOF ;
    public final EObject entryRuleSimpleParamRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleParamRef = null;


        try {
            // InternalThingML.g:2900:55: (iv_ruleSimpleParamRef= ruleSimpleParamRef EOF )
            // InternalThingML.g:2901:2: iv_ruleSimpleParamRef= ruleSimpleParamRef EOF
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
    // InternalThingML.g:2907:1: ruleSimpleParamRef returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleSimpleParamRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalThingML.g:2913:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalThingML.g:2914:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalThingML.g:2914:2: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:2915:3: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:2915:3: (otherlv_0= RULE_ID )
            // InternalThingML.g:2916:4: otherlv_0= RULE_ID
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
    // InternalThingML.g:2930:1: entryRuleArrayParamRef returns [EObject current=null] : iv_ruleArrayParamRef= ruleArrayParamRef EOF ;
    public final EObject entryRuleArrayParamRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayParamRef = null;


        try {
            // InternalThingML.g:2930:54: (iv_ruleArrayParamRef= ruleArrayParamRef EOF )
            // InternalThingML.g:2931:2: iv_ruleArrayParamRef= ruleArrayParamRef EOF
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
    // InternalThingML.g:2937:1: ruleArrayParamRef returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']' ) ;
    public final EObject ruleArrayParamRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:2943:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']' ) )
            // InternalThingML.g:2944:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']' )
            {
            // InternalThingML.g:2944:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']' )
            // InternalThingML.g:2945:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']'
            {
            // InternalThingML.g:2945:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:2946:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:2946:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:2947:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getArrayParamRefRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_37); 

            					newLeafNode(otherlv_0, grammarAccess.getArrayParamRefAccess().getParameterRefParameterCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,27,FOLLOW_20); 

            			newLeafNode(otherlv_1, grammarAccess.getArrayParamRefAccess().getLeftSquareBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,28,FOLLOW_2); 

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
    // InternalThingML.g:2970:1: entryRuleLengthArray returns [EObject current=null] : iv_ruleLengthArray= ruleLengthArray EOF ;
    public final EObject entryRuleLengthArray() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLengthArray = null;


        try {
            // InternalThingML.g:2970:52: (iv_ruleLengthArray= ruleLengthArray EOF )
            // InternalThingML.g:2971:2: iv_ruleLengthArray= ruleLengthArray EOF
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
    // InternalThingML.g:2977:1: ruleLengthArray returns [EObject current=null] : ( () otherlv_1= 'length' ) ;
    public final EObject ruleLengthArray() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:2983:2: ( ( () otherlv_1= 'length' ) )
            // InternalThingML.g:2984:2: ( () otherlv_1= 'length' )
            {
            // InternalThingML.g:2984:2: ( () otherlv_1= 'length' )
            // InternalThingML.g:2985:3: () otherlv_1= 'length'
            {
            // InternalThingML.g:2985:3: ()
            // InternalThingML.g:2986:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getLengthArrayAccess().getLengthArrayAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,60,FOLLOW_2); 

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
    // InternalThingML.g:3000:1: entryRuleStateMachine returns [EObject current=null] : iv_ruleStateMachine= ruleStateMachine EOF ;
    public final EObject entryRuleStateMachine() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStateMachine = null;


        try {
            // InternalThingML.g:3000:53: (iv_ruleStateMachine= ruleStateMachine EOF )
            // InternalThingML.g:3001:2: iv_ruleStateMachine= ruleStateMachine EOF
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
    // InternalThingML.g:3007:1: ruleStateMachine returns [EObject current=null] : (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' ) ;
    public final EObject ruleStateMachine() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_history_5_0=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_18=null;
        EObject lv_annotations_6_0 = null;

        EObject lv_entry_10_0 = null;

        EObject lv_exit_13_0 = null;

        EObject lv_properties_14_0 = null;

        EObject lv_substate_15_0 = null;

        EObject lv_internal_16_0 = null;

        EObject lv_region_17_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3013:2: ( (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' ) )
            // InternalThingML.g:3014:2: (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' )
            {
            // InternalThingML.g:3014:2: (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' )
            // InternalThingML.g:3015:3: otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}'
            {
            otherlv_0=(Token)match(input,61,FOLLOW_44); 

            			newLeafNode(otherlv_0, grammarAccess.getStateMachineAccess().getStatechartKeyword_0());
            		
            // InternalThingML.g:3019:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==RULE_ID) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalThingML.g:3020:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:3020:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:3021:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_45); 

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

            otherlv_2=(Token)match(input,62,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getStateMachineAccess().getInitKeyword_2());
            		
            // InternalThingML.g:3041:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:3042:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:3042:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:3043:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStateMachineRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_46); 

            					newLeafNode(otherlv_3, grammarAccess.getStateMachineAccess().getInitialStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:3054:3: (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==63) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // InternalThingML.g:3055:4: otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) )
                    {
                    otherlv_4=(Token)match(input,63,FOLLOW_47); 

                    				newLeafNode(otherlv_4, grammarAccess.getStateMachineAccess().getKeepsKeyword_4_0());
                    			
                    // InternalThingML.g:3059:4: ( (lv_history_5_0= 'history' ) )
                    // InternalThingML.g:3060:5: (lv_history_5_0= 'history' )
                    {
                    // InternalThingML.g:3060:5: (lv_history_5_0= 'history' )
                    // InternalThingML.g:3061:6: lv_history_5_0= 'history'
                    {
                    lv_history_5_0=(Token)match(input,64,FOLLOW_11); 

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

            // InternalThingML.g:3074:3: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==13) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalThingML.g:3075:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3075:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3076:5: lv_annotations_6_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getStateMachineAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_11);
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
            	    break loop55;
                }
            } while (true);

            otherlv_7=(Token)match(input,20,FOLLOW_48); 

            			newLeafNode(otherlv_7, grammarAccess.getStateMachineAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:3097:3: (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==65) ) {
                int LA56_1 = input.LA(2);

                if ( (LA56_1==66) ) {
                    alt56=1;
                }
            }
            switch (alt56) {
                case 1 :
                    // InternalThingML.g:3098:4: otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) )
                    {
                    otherlv_8=(Token)match(input,65,FOLLOW_49); 

                    				newLeafNode(otherlv_8, grammarAccess.getStateMachineAccess().getOnKeyword_7_0());
                    			
                    otherlv_9=(Token)match(input,66,FOLLOW_25); 

                    				newLeafNode(otherlv_9, grammarAccess.getStateMachineAccess().getEntryKeyword_7_1());
                    			
                    // InternalThingML.g:3106:4: ( (lv_entry_10_0= ruleAction ) )
                    // InternalThingML.g:3107:5: (lv_entry_10_0= ruleAction )
                    {
                    // InternalThingML.g:3107:5: (lv_entry_10_0= ruleAction )
                    // InternalThingML.g:3108:6: lv_entry_10_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getStateMachineAccess().getEntryActionParserRuleCall_7_2_0());
                    					
                    pushFollow(FOLLOW_48);
                    lv_entry_10_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getStateMachineRule());
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

            // InternalThingML.g:3126:3: (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==65) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalThingML.g:3127:4: otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) )
                    {
                    otherlv_11=(Token)match(input,65,FOLLOW_50); 

                    				newLeafNode(otherlv_11, grammarAccess.getStateMachineAccess().getOnKeyword_8_0());
                    			
                    otherlv_12=(Token)match(input,67,FOLLOW_25); 

                    				newLeafNode(otherlv_12, grammarAccess.getStateMachineAccess().getExitKeyword_8_1());
                    			
                    // InternalThingML.g:3135:4: ( (lv_exit_13_0= ruleAction ) )
                    // InternalThingML.g:3136:5: (lv_exit_13_0= ruleAction )
                    {
                    // InternalThingML.g:3136:5: (lv_exit_13_0= ruleAction )
                    // InternalThingML.g:3137:6: lv_exit_13_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getStateMachineAccess().getExitActionParserRuleCall_8_2_0());
                    					
                    pushFollow(FOLLOW_51);
                    lv_exit_13_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getStateMachineRule());
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

            // InternalThingML.g:3155:3: ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )*
            loop58:
            do {
                int alt58=4;
                switch ( input.LA(1) ) {
                case 36:
                    {
                    alt58=1;
                    }
                    break;
                case 61:
                case 68:
                case 69:
                case 70:
                case 71:
                    {
                    alt58=2;
                    }
                    break;
                case 44:
                    {
                    alt58=3;
                    }
                    break;

                }

                switch (alt58) {
            	case 1 :
            	    // InternalThingML.g:3156:4: ( (lv_properties_14_0= ruleProperty ) )
            	    {
            	    // InternalThingML.g:3156:4: ( (lv_properties_14_0= ruleProperty ) )
            	    // InternalThingML.g:3157:5: (lv_properties_14_0= ruleProperty )
            	    {
            	    // InternalThingML.g:3157:5: (lv_properties_14_0= ruleProperty )
            	    // InternalThingML.g:3158:6: lv_properties_14_0= ruleProperty
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getPropertiesPropertyParserRuleCall_9_0_0());
            	    					
            	    pushFollow(FOLLOW_51);
            	    lv_properties_14_0=ruleProperty();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    						}
            	    						add(
            	    							current,
            	    							"properties",
            	    							lv_properties_14_0,
            	    							"org.thingml.xtext.ThingML.Property");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:3176:4: ( (lv_substate_15_0= ruleState ) )
            	    {
            	    // InternalThingML.g:3176:4: ( (lv_substate_15_0= ruleState ) )
            	    // InternalThingML.g:3177:5: (lv_substate_15_0= ruleState )
            	    {
            	    // InternalThingML.g:3177:5: (lv_substate_15_0= ruleState )
            	    // InternalThingML.g:3178:6: lv_substate_15_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getSubstateStateParserRuleCall_9_1_0());
            	    					
            	    pushFollow(FOLLOW_51);
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
            	case 3 :
            	    // InternalThingML.g:3196:4: ( (lv_internal_16_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:3196:4: ( (lv_internal_16_0= ruleInternalTransition ) )
            	    // InternalThingML.g:3197:5: (lv_internal_16_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:3197:5: (lv_internal_16_0= ruleInternalTransition )
            	    // InternalThingML.g:3198:6: lv_internal_16_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getInternalInternalTransitionParserRuleCall_9_2_0());
            	    					
            	    pushFollow(FOLLOW_51);
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
            	    break loop58;
                }
            } while (true);

            // InternalThingML.g:3216:3: ( (lv_region_17_0= ruleParallelRegion ) )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==72) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // InternalThingML.g:3217:4: (lv_region_17_0= ruleParallelRegion )
            	    {
            	    // InternalThingML.g:3217:4: (lv_region_17_0= ruleParallelRegion )
            	    // InternalThingML.g:3218:5: lv_region_17_0= ruleParallelRegion
            	    {

            	    					newCompositeNode(grammarAccess.getStateMachineAccess().getRegionParallelRegionParserRuleCall_10_0());
            	    				
            	    pushFollow(FOLLOW_52);
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
            	    break loop59;
                }
            } while (true);

            otherlv_18=(Token)match(input,21,FOLLOW_2); 

            			newLeafNode(otherlv_18, grammarAccess.getStateMachineAccess().getRightCurlyBracketKeyword_11());
            		

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
    // InternalThingML.g:3243:1: entryRuleFinalState returns [EObject current=null] : iv_ruleFinalState= ruleFinalState EOF ;
    public final EObject entryRuleFinalState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFinalState = null;


        try {
            // InternalThingML.g:3243:51: (iv_ruleFinalState= ruleFinalState EOF )
            // InternalThingML.g:3244:2: iv_ruleFinalState= ruleFinalState EOF
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
    // InternalThingML.g:3250:1: ruleFinalState returns [EObject current=null] : (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= ';' ) ;
    public final EObject ruleFinalState() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_4=null;
        EObject lv_annotations_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3256:2: ( (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= ';' ) )
            // InternalThingML.g:3257:2: (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= ';' )
            {
            // InternalThingML.g:3257:2: (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= ';' )
            // InternalThingML.g:3258:3: otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= ';'
            {
            otherlv_0=(Token)match(input,68,FOLLOW_53); 

            			newLeafNode(otherlv_0, grammarAccess.getFinalStateAccess().getFinalKeyword_0());
            		
            otherlv_1=(Token)match(input,69,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getFinalStateAccess().getStateKeyword_1());
            		
            // InternalThingML.g:3266:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:3267:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:3267:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:3268:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_10); 

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

            // InternalThingML.g:3284:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==13) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // InternalThingML.g:3285:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3285:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3286:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getFinalStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_10);
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
            	    break loop60;
                }
            } while (true);

            otherlv_4=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getFinalStateAccess().getSemicolonKeyword_4());
            		

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
    // InternalThingML.g:3311:1: entryRuleCompositeState returns [EObject current=null] : iv_ruleCompositeState= ruleCompositeState EOF ;
    public final EObject entryRuleCompositeState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCompositeState = null;


        try {
            // InternalThingML.g:3311:55: (iv_ruleCompositeState= ruleCompositeState EOF )
            // InternalThingML.g:3312:2: iv_ruleCompositeState= ruleCompositeState EOF
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
    // InternalThingML.g:3318:1: ruleCompositeState returns [EObject current=null] : (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) )? otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_properties_15_0= ruleProperty ) ) | ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' ) ;
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
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_20=null;
        EObject lv_annotations_7_0 = null;

        EObject lv_entry_11_0 = null;

        EObject lv_exit_14_0 = null;

        EObject lv_properties_15_0 = null;

        EObject lv_substate_16_0 = null;

        EObject lv_internal_17_0 = null;

        EObject lv_outgoing_18_0 = null;

        EObject lv_region_19_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3324:2: ( (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) )? otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_properties_15_0= ruleProperty ) ) | ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' ) )
            // InternalThingML.g:3325:2: (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) )? otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_properties_15_0= ruleProperty ) ) | ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' )
            {
            // InternalThingML.g:3325:2: (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) )? otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_properties_15_0= ruleProperty ) ) | ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' )
            // InternalThingML.g:3326:3: otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) )? otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_properties_15_0= ruleProperty ) ) | ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}'
            {
            otherlv_0=(Token)match(input,70,FOLLOW_53); 

            			newLeafNode(otherlv_0, grammarAccess.getCompositeStateAccess().getCompositeKeyword_0());
            		
            otherlv_1=(Token)match(input,69,FOLLOW_44); 

            			newLeafNode(otherlv_1, grammarAccess.getCompositeStateAccess().getStateKeyword_1());
            		
            // InternalThingML.g:3334:3: ( (lv_name_2_0= RULE_ID ) )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==RULE_ID) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalThingML.g:3335:4: (lv_name_2_0= RULE_ID )
                    {
                    // InternalThingML.g:3335:4: (lv_name_2_0= RULE_ID )
                    // InternalThingML.g:3336:5: lv_name_2_0= RULE_ID
                    {
                    lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_45); 

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
                    break;

            }

            otherlv_3=(Token)match(input,62,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getCompositeStateAccess().getInitKeyword_3());
            		
            // InternalThingML.g:3356:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:3357:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:3357:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:3358:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCompositeStateRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_46); 

            					newLeafNode(otherlv_4, grammarAccess.getCompositeStateAccess().getInitialStateCrossReference_4_0());
            				

            }


            }

            // InternalThingML.g:3369:3: (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==63) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalThingML.g:3370:4: otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) )
                    {
                    otherlv_5=(Token)match(input,63,FOLLOW_47); 

                    				newLeafNode(otherlv_5, grammarAccess.getCompositeStateAccess().getKeepsKeyword_5_0());
                    			
                    // InternalThingML.g:3374:4: ( (lv_history_6_0= 'history' ) )
                    // InternalThingML.g:3375:5: (lv_history_6_0= 'history' )
                    {
                    // InternalThingML.g:3375:5: (lv_history_6_0= 'history' )
                    // InternalThingML.g:3376:6: lv_history_6_0= 'history'
                    {
                    lv_history_6_0=(Token)match(input,64,FOLLOW_11); 

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

            // InternalThingML.g:3389:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==13) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // InternalThingML.g:3390:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3390:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3391:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getCompositeStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_11);
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
            	    break loop63;
                }
            } while (true);

            otherlv_8=(Token)match(input,20,FOLLOW_54); 

            			newLeafNode(otherlv_8, grammarAccess.getCompositeStateAccess().getLeftCurlyBracketKeyword_7());
            		
            // InternalThingML.g:3412:3: (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==65) ) {
                int LA64_1 = input.LA(2);

                if ( (LA64_1==66) ) {
                    alt64=1;
                }
            }
            switch (alt64) {
                case 1 :
                    // InternalThingML.g:3413:4: otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) )
                    {
                    otherlv_9=(Token)match(input,65,FOLLOW_49); 

                    				newLeafNode(otherlv_9, grammarAccess.getCompositeStateAccess().getOnKeyword_8_0());
                    			
                    otherlv_10=(Token)match(input,66,FOLLOW_25); 

                    				newLeafNode(otherlv_10, grammarAccess.getCompositeStateAccess().getEntryKeyword_8_1());
                    			
                    // InternalThingML.g:3421:4: ( (lv_entry_11_0= ruleAction ) )
                    // InternalThingML.g:3422:5: (lv_entry_11_0= ruleAction )
                    {
                    // InternalThingML.g:3422:5: (lv_entry_11_0= ruleAction )
                    // InternalThingML.g:3423:6: lv_entry_11_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getCompositeStateAccess().getEntryActionParserRuleCall_8_2_0());
                    					
                    pushFollow(FOLLOW_54);
                    lv_entry_11_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getCompositeStateRule());
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

            // InternalThingML.g:3441:3: (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==65) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalThingML.g:3442:4: otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) )
                    {
                    otherlv_12=(Token)match(input,65,FOLLOW_50); 

                    				newLeafNode(otherlv_12, grammarAccess.getCompositeStateAccess().getOnKeyword_9_0());
                    			
                    otherlv_13=(Token)match(input,67,FOLLOW_25); 

                    				newLeafNode(otherlv_13, grammarAccess.getCompositeStateAccess().getExitKeyword_9_1());
                    			
                    // InternalThingML.g:3450:4: ( (lv_exit_14_0= ruleAction ) )
                    // InternalThingML.g:3451:5: (lv_exit_14_0= ruleAction )
                    {
                    // InternalThingML.g:3451:5: (lv_exit_14_0= ruleAction )
                    // InternalThingML.g:3452:6: lv_exit_14_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getCompositeStateAccess().getExitActionParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_55);
                    lv_exit_14_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getCompositeStateRule());
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

            // InternalThingML.g:3470:3: ( ( (lv_properties_15_0= ruleProperty ) ) | ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )*
            loop66:
            do {
                int alt66=5;
                switch ( input.LA(1) ) {
                case 36:
                    {
                    alt66=1;
                    }
                    break;
                case 61:
                case 68:
                case 69:
                case 70:
                case 71:
                    {
                    alt66=2;
                    }
                    break;
                case 44:
                    {
                    alt66=3;
                    }
                    break;
                case 73:
                    {
                    alt66=4;
                    }
                    break;

                }

                switch (alt66) {
            	case 1 :
            	    // InternalThingML.g:3471:4: ( (lv_properties_15_0= ruleProperty ) )
            	    {
            	    // InternalThingML.g:3471:4: ( (lv_properties_15_0= ruleProperty ) )
            	    // InternalThingML.g:3472:5: (lv_properties_15_0= ruleProperty )
            	    {
            	    // InternalThingML.g:3472:5: (lv_properties_15_0= ruleProperty )
            	    // InternalThingML.g:3473:6: lv_properties_15_0= ruleProperty
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getPropertiesPropertyParserRuleCall_10_0_0());
            	    					
            	    pushFollow(FOLLOW_55);
            	    lv_properties_15_0=ruleProperty();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCompositeStateRule());
            	    						}
            	    						add(
            	    							current,
            	    							"properties",
            	    							lv_properties_15_0,
            	    							"org.thingml.xtext.ThingML.Property");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:3491:4: ( (lv_substate_16_0= ruleState ) )
            	    {
            	    // InternalThingML.g:3491:4: ( (lv_substate_16_0= ruleState ) )
            	    // InternalThingML.g:3492:5: (lv_substate_16_0= ruleState )
            	    {
            	    // InternalThingML.g:3492:5: (lv_substate_16_0= ruleState )
            	    // InternalThingML.g:3493:6: lv_substate_16_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getSubstateStateParserRuleCall_10_1_0());
            	    					
            	    pushFollow(FOLLOW_55);
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
            	case 3 :
            	    // InternalThingML.g:3511:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:3511:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    // InternalThingML.g:3512:5: (lv_internal_17_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:3512:5: (lv_internal_17_0= ruleInternalTransition )
            	    // InternalThingML.g:3513:6: lv_internal_17_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getInternalInternalTransitionParserRuleCall_10_2_0());
            	    					
            	    pushFollow(FOLLOW_55);
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
            	case 4 :
            	    // InternalThingML.g:3531:4: ( (lv_outgoing_18_0= ruleTransition ) )
            	    {
            	    // InternalThingML.g:3531:4: ( (lv_outgoing_18_0= ruleTransition ) )
            	    // InternalThingML.g:3532:5: (lv_outgoing_18_0= ruleTransition )
            	    {
            	    // InternalThingML.g:3532:5: (lv_outgoing_18_0= ruleTransition )
            	    // InternalThingML.g:3533:6: lv_outgoing_18_0= ruleTransition
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getOutgoingTransitionParserRuleCall_10_3_0());
            	    					
            	    pushFollow(FOLLOW_55);
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
            	    break loop66;
                }
            } while (true);

            // InternalThingML.g:3551:3: ( (lv_region_19_0= ruleParallelRegion ) )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==72) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // InternalThingML.g:3552:4: (lv_region_19_0= ruleParallelRegion )
            	    {
            	    // InternalThingML.g:3552:4: (lv_region_19_0= ruleParallelRegion )
            	    // InternalThingML.g:3553:5: lv_region_19_0= ruleParallelRegion
            	    {

            	    					newCompositeNode(grammarAccess.getCompositeStateAccess().getRegionParallelRegionParserRuleCall_11_0());
            	    				
            	    pushFollow(FOLLOW_52);
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
            	    break loop67;
                }
            } while (true);

            otherlv_20=(Token)match(input,21,FOLLOW_2); 

            			newLeafNode(otherlv_20, grammarAccess.getCompositeStateAccess().getRightCurlyBracketKeyword_12());
            		

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
    // InternalThingML.g:3578:1: entryRuleSession returns [EObject current=null] : iv_ruleSession= ruleSession EOF ;
    public final EObject entryRuleSession() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSession = null;


        try {
            // InternalThingML.g:3578:48: (iv_ruleSession= ruleSession EOF )
            // InternalThingML.g:3579:2: iv_ruleSession= ruleSession EOF
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
    // InternalThingML.g:3585:1: ruleSession returns [EObject current=null] : (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' (otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) ) )? (otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) ) )? ( ( (lv_properties_12_0= ruleProperty ) ) | ( (lv_substate_13_0= ruleState ) ) | ( (lv_internal_14_0= ruleInternalTransition ) ) )* ( (lv_region_15_0= ruleParallelRegion ) )* otherlv_16= '}' ) ;
    public final EObject ruleSession() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_16=null;
        EObject lv_annotations_4_0 = null;

        EObject lv_entry_8_0 = null;

        EObject lv_exit_11_0 = null;

        EObject lv_properties_12_0 = null;

        EObject lv_substate_13_0 = null;

        EObject lv_internal_14_0 = null;

        EObject lv_region_15_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3591:2: ( (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' (otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) ) )? (otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) ) )? ( ( (lv_properties_12_0= ruleProperty ) ) | ( (lv_substate_13_0= ruleState ) ) | ( (lv_internal_14_0= ruleInternalTransition ) ) )* ( (lv_region_15_0= ruleParallelRegion ) )* otherlv_16= '}' ) )
            // InternalThingML.g:3592:2: (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' (otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) ) )? (otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) ) )? ( ( (lv_properties_12_0= ruleProperty ) ) | ( (lv_substate_13_0= ruleState ) ) | ( (lv_internal_14_0= ruleInternalTransition ) ) )* ( (lv_region_15_0= ruleParallelRegion ) )* otherlv_16= '}' )
            {
            // InternalThingML.g:3592:2: (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' (otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) ) )? (otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) ) )? ( ( (lv_properties_12_0= ruleProperty ) ) | ( (lv_substate_13_0= ruleState ) ) | ( (lv_internal_14_0= ruleInternalTransition ) ) )* ( (lv_region_15_0= ruleParallelRegion ) )* otherlv_16= '}' )
            // InternalThingML.g:3593:3: otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' (otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) ) )? (otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) ) )? ( ( (lv_properties_12_0= ruleProperty ) ) | ( (lv_substate_13_0= ruleState ) ) | ( (lv_internal_14_0= ruleInternalTransition ) ) )* ( (lv_region_15_0= ruleParallelRegion ) )* otherlv_16= '}'
            {
            otherlv_0=(Token)match(input,71,FOLLOW_44); 

            			newLeafNode(otherlv_0, grammarAccess.getSessionAccess().getSessionKeyword_0());
            		
            // InternalThingML.g:3597:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==RULE_ID) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalThingML.g:3598:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:3598:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:3599:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_45); 

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
                    break;

            }

            otherlv_2=(Token)match(input,62,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getSessionAccess().getInitKeyword_2());
            		
            // InternalThingML.g:3619:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:3620:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:3620:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:3621:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSessionRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_11); 

            					newLeafNode(otherlv_3, grammarAccess.getSessionAccess().getInitialStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:3632:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==13) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // InternalThingML.g:3633:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3633:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3634:5: lv_annotations_4_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getSessionAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_11);
            	    lv_annotations_4_0=rulePlatformAnnotation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getSessionRule());
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
            	    break loop69;
                }
            } while (true);

            otherlv_5=(Token)match(input,20,FOLLOW_48); 

            			newLeafNode(otherlv_5, grammarAccess.getSessionAccess().getLeftCurlyBracketKeyword_5());
            		
            // InternalThingML.g:3655:3: (otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) ) )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==65) ) {
                int LA70_1 = input.LA(2);

                if ( (LA70_1==66) ) {
                    alt70=1;
                }
            }
            switch (alt70) {
                case 1 :
                    // InternalThingML.g:3656:4: otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) )
                    {
                    otherlv_6=(Token)match(input,65,FOLLOW_49); 

                    				newLeafNode(otherlv_6, grammarAccess.getSessionAccess().getOnKeyword_6_0());
                    			
                    otherlv_7=(Token)match(input,66,FOLLOW_25); 

                    				newLeafNode(otherlv_7, grammarAccess.getSessionAccess().getEntryKeyword_6_1());
                    			
                    // InternalThingML.g:3664:4: ( (lv_entry_8_0= ruleAction ) )
                    // InternalThingML.g:3665:5: (lv_entry_8_0= ruleAction )
                    {
                    // InternalThingML.g:3665:5: (lv_entry_8_0= ruleAction )
                    // InternalThingML.g:3666:6: lv_entry_8_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getSessionAccess().getEntryActionParserRuleCall_6_2_0());
                    					
                    pushFollow(FOLLOW_48);
                    lv_entry_8_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getSessionRule());
                    						}
                    						set(
                    							current,
                    							"entry",
                    							lv_entry_8_0,
                    							"org.thingml.xtext.ThingML.Action");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:3684:3: (otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==65) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalThingML.g:3685:4: otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) )
                    {
                    otherlv_9=(Token)match(input,65,FOLLOW_50); 

                    				newLeafNode(otherlv_9, grammarAccess.getSessionAccess().getOnKeyword_7_0());
                    			
                    otherlv_10=(Token)match(input,67,FOLLOW_25); 

                    				newLeafNode(otherlv_10, grammarAccess.getSessionAccess().getExitKeyword_7_1());
                    			
                    // InternalThingML.g:3693:4: ( (lv_exit_11_0= ruleAction ) )
                    // InternalThingML.g:3694:5: (lv_exit_11_0= ruleAction )
                    {
                    // InternalThingML.g:3694:5: (lv_exit_11_0= ruleAction )
                    // InternalThingML.g:3695:6: lv_exit_11_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getSessionAccess().getExitActionParserRuleCall_7_2_0());
                    					
                    pushFollow(FOLLOW_51);
                    lv_exit_11_0=ruleAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getSessionRule());
                    						}
                    						set(
                    							current,
                    							"exit",
                    							lv_exit_11_0,
                    							"org.thingml.xtext.ThingML.Action");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:3713:3: ( ( (lv_properties_12_0= ruleProperty ) ) | ( (lv_substate_13_0= ruleState ) ) | ( (lv_internal_14_0= ruleInternalTransition ) ) )*
            loop72:
            do {
                int alt72=4;
                switch ( input.LA(1) ) {
                case 36:
                    {
                    alt72=1;
                    }
                    break;
                case 61:
                case 68:
                case 69:
                case 70:
                case 71:
                    {
                    alt72=2;
                    }
                    break;
                case 44:
                    {
                    alt72=3;
                    }
                    break;

                }

                switch (alt72) {
            	case 1 :
            	    // InternalThingML.g:3714:4: ( (lv_properties_12_0= ruleProperty ) )
            	    {
            	    // InternalThingML.g:3714:4: ( (lv_properties_12_0= ruleProperty ) )
            	    // InternalThingML.g:3715:5: (lv_properties_12_0= ruleProperty )
            	    {
            	    // InternalThingML.g:3715:5: (lv_properties_12_0= ruleProperty )
            	    // InternalThingML.g:3716:6: lv_properties_12_0= ruleProperty
            	    {

            	    						newCompositeNode(grammarAccess.getSessionAccess().getPropertiesPropertyParserRuleCall_8_0_0());
            	    					
            	    pushFollow(FOLLOW_51);
            	    lv_properties_12_0=ruleProperty();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getSessionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"properties",
            	    							lv_properties_12_0,
            	    							"org.thingml.xtext.ThingML.Property");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalThingML.g:3734:4: ( (lv_substate_13_0= ruleState ) )
            	    {
            	    // InternalThingML.g:3734:4: ( (lv_substate_13_0= ruleState ) )
            	    // InternalThingML.g:3735:5: (lv_substate_13_0= ruleState )
            	    {
            	    // InternalThingML.g:3735:5: (lv_substate_13_0= ruleState )
            	    // InternalThingML.g:3736:6: lv_substate_13_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getSessionAccess().getSubstateStateParserRuleCall_8_1_0());
            	    					
            	    pushFollow(FOLLOW_51);
            	    lv_substate_13_0=ruleState();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getSessionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"substate",
            	    							lv_substate_13_0,
            	    							"org.thingml.xtext.ThingML.State");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalThingML.g:3754:4: ( (lv_internal_14_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:3754:4: ( (lv_internal_14_0= ruleInternalTransition ) )
            	    // InternalThingML.g:3755:5: (lv_internal_14_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:3755:5: (lv_internal_14_0= ruleInternalTransition )
            	    // InternalThingML.g:3756:6: lv_internal_14_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getSessionAccess().getInternalInternalTransitionParserRuleCall_8_2_0());
            	    					
            	    pushFollow(FOLLOW_51);
            	    lv_internal_14_0=ruleInternalTransition();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getSessionRule());
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

            	default :
            	    break loop72;
                }
            } while (true);

            // InternalThingML.g:3774:3: ( (lv_region_15_0= ruleParallelRegion ) )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( (LA73_0==72) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // InternalThingML.g:3775:4: (lv_region_15_0= ruleParallelRegion )
            	    {
            	    // InternalThingML.g:3775:4: (lv_region_15_0= ruleParallelRegion )
            	    // InternalThingML.g:3776:5: lv_region_15_0= ruleParallelRegion
            	    {

            	    					newCompositeNode(grammarAccess.getSessionAccess().getRegionParallelRegionParserRuleCall_9_0());
            	    				
            	    pushFollow(FOLLOW_52);
            	    lv_region_15_0=ruleParallelRegion();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getSessionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"region",
            	    						lv_region_15_0,
            	    						"org.thingml.xtext.ThingML.ParallelRegion");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop73;
                }
            } while (true);

            otherlv_16=(Token)match(input,21,FOLLOW_2); 

            			newLeafNode(otherlv_16, grammarAccess.getSessionAccess().getRightCurlyBracketKeyword_10());
            		

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
    // InternalThingML.g:3801:1: entryRuleParallelRegion returns [EObject current=null] : iv_ruleParallelRegion= ruleParallelRegion EOF ;
    public final EObject entryRuleParallelRegion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParallelRegion = null;


        try {
            // InternalThingML.g:3801:55: (iv_ruleParallelRegion= ruleParallelRegion EOF )
            // InternalThingML.g:3802:2: iv_ruleParallelRegion= ruleParallelRegion EOF
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
    // InternalThingML.g:3808:1: ruleParallelRegion returns [EObject current=null] : (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) ;
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
            // InternalThingML.g:3814:2: ( (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) )
            // InternalThingML.g:3815:2: (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' )
            {
            // InternalThingML.g:3815:2: (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' )
            // InternalThingML.g:3816:3: otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,72,FOLLOW_44); 

            			newLeafNode(otherlv_0, grammarAccess.getParallelRegionAccess().getRegionKeyword_0());
            		
            // InternalThingML.g:3820:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==RULE_ID) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // InternalThingML.g:3821:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:3821:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:3822:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_45); 

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

            otherlv_2=(Token)match(input,62,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getParallelRegionAccess().getInitKeyword_2());
            		
            // InternalThingML.g:3842:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:3843:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:3843:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:3844:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getParallelRegionRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_46); 

            					newLeafNode(otherlv_3, grammarAccess.getParallelRegionAccess().getInitialStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:3855:3: (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==63) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // InternalThingML.g:3856:4: otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) )
                    {
                    otherlv_4=(Token)match(input,63,FOLLOW_47); 

                    				newLeafNode(otherlv_4, grammarAccess.getParallelRegionAccess().getKeepsKeyword_4_0());
                    			
                    // InternalThingML.g:3860:4: ( (lv_history_5_0= 'history' ) )
                    // InternalThingML.g:3861:5: (lv_history_5_0= 'history' )
                    {
                    // InternalThingML.g:3861:5: (lv_history_5_0= 'history' )
                    // InternalThingML.g:3862:6: lv_history_5_0= 'history'
                    {
                    lv_history_5_0=(Token)match(input,64,FOLLOW_11); 

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

            // InternalThingML.g:3875:3: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            loop76:
            do {
                int alt76=2;
                int LA76_0 = input.LA(1);

                if ( (LA76_0==13) ) {
                    alt76=1;
                }


                switch (alt76) {
            	case 1 :
            	    // InternalThingML.g:3876:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3876:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3877:5: lv_annotations_6_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getParallelRegionAccess().getAnnotationsPlatformAnnotationParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_11);
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
            	    break loop76;
                }
            } while (true);

            otherlv_7=(Token)match(input,20,FOLLOW_56); 

            			newLeafNode(otherlv_7, grammarAccess.getParallelRegionAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:3898:3: ( (lv_substate_8_0= ruleState ) )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==61||(LA77_0>=68 && LA77_0<=71)) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // InternalThingML.g:3899:4: (lv_substate_8_0= ruleState )
            	    {
            	    // InternalThingML.g:3899:4: (lv_substate_8_0= ruleState )
            	    // InternalThingML.g:3900:5: lv_substate_8_0= ruleState
            	    {

            	    					newCompositeNode(grammarAccess.getParallelRegionAccess().getSubstateStateParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_56);
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
            	    break loop77;
                }
            } while (true);

            otherlv_9=(Token)match(input,21,FOLLOW_2); 

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
    // InternalThingML.g:3925:1: entryRuleState returns [EObject current=null] : iv_ruleState= ruleState EOF ;
    public final EObject entryRuleState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleState = null;


        try {
            // InternalThingML.g:3925:46: (iv_ruleState= ruleState EOF )
            // InternalThingML.g:3926:2: iv_ruleState= ruleState EOF
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
    // InternalThingML.g:3932:1: ruleState returns [EObject current=null] : (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) ) ;
    public final EObject ruleState() throws RecognitionException {
        EObject current = null;

        Token otherlv_4=null;
        Token lv_name_5_0=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_17=null;
        EObject this_StateMachine_0 = null;

        EObject this_FinalState_1 = null;

        EObject this_CompositeState_2 = null;

        EObject this_Session_3 = null;

        EObject lv_annotations_6_0 = null;

        EObject lv_entry_10_0 = null;

        EObject lv_exit_13_0 = null;

        EObject lv_properties_14_0 = null;

        EObject lv_internal_15_0 = null;

        EObject lv_outgoing_16_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3938:2: ( (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) ) )
            // InternalThingML.g:3939:2: (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) )
            {
            // InternalThingML.g:3939:2: (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) )
            int alt82=5;
            switch ( input.LA(1) ) {
            case 61:
                {
                alt82=1;
                }
                break;
            case 68:
                {
                alt82=2;
                }
                break;
            case 70:
                {
                alt82=3;
                }
                break;
            case 71:
                {
                alt82=4;
                }
                break;
            case 69:
                {
                alt82=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }

            switch (alt82) {
                case 1 :
                    // InternalThingML.g:3940:3: this_StateMachine_0= ruleStateMachine
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
                    // InternalThingML.g:3949:3: this_FinalState_1= ruleFinalState
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
                    // InternalThingML.g:3958:3: this_CompositeState_2= ruleCompositeState
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
                    // InternalThingML.g:3967:3: this_Session_3= ruleSession
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
                    // InternalThingML.g:3976:3: (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' )
                    {
                    // InternalThingML.g:3976:3: (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' )
                    // InternalThingML.g:3977:4: otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}'
                    {
                    otherlv_4=(Token)match(input,69,FOLLOW_6); 

                    				newLeafNode(otherlv_4, grammarAccess.getStateAccess().getStateKeyword_4_0());
                    			
                    // InternalThingML.g:3981:4: ( (lv_name_5_0= RULE_ID ) )
                    // InternalThingML.g:3982:5: (lv_name_5_0= RULE_ID )
                    {
                    // InternalThingML.g:3982:5: (lv_name_5_0= RULE_ID )
                    // InternalThingML.g:3983:6: lv_name_5_0= RULE_ID
                    {
                    lv_name_5_0=(Token)match(input,RULE_ID,FOLLOW_11); 

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

                    // InternalThingML.g:3999:4: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
                    loop78:
                    do {
                        int alt78=2;
                        int LA78_0 = input.LA(1);

                        if ( (LA78_0==13) ) {
                            alt78=1;
                        }


                        switch (alt78) {
                    	case 1 :
                    	    // InternalThingML.g:4000:5: (lv_annotations_6_0= rulePlatformAnnotation )
                    	    {
                    	    // InternalThingML.g:4000:5: (lv_annotations_6_0= rulePlatformAnnotation )
                    	    // InternalThingML.g:4001:6: lv_annotations_6_0= rulePlatformAnnotation
                    	    {

                    	    						newCompositeNode(grammarAccess.getStateAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_2_0());
                    	    					
                    	    pushFollow(FOLLOW_11);
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
                    	    break loop78;
                        }
                    } while (true);

                    otherlv_7=(Token)match(input,20,FOLLOW_57); 

                    				newLeafNode(otherlv_7, grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_4_3());
                    			
                    // InternalThingML.g:4022:4: (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )?
                    int alt79=2;
                    int LA79_0 = input.LA(1);

                    if ( (LA79_0==65) ) {
                        int LA79_1 = input.LA(2);

                        if ( (LA79_1==66) ) {
                            alt79=1;
                        }
                    }
                    switch (alt79) {
                        case 1 :
                            // InternalThingML.g:4023:5: otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) )
                            {
                            otherlv_8=(Token)match(input,65,FOLLOW_49); 

                            					newLeafNode(otherlv_8, grammarAccess.getStateAccess().getOnKeyword_4_4_0());
                            				
                            otherlv_9=(Token)match(input,66,FOLLOW_25); 

                            					newLeafNode(otherlv_9, grammarAccess.getStateAccess().getEntryKeyword_4_4_1());
                            				
                            // InternalThingML.g:4031:5: ( (lv_entry_10_0= ruleAction ) )
                            // InternalThingML.g:4032:6: (lv_entry_10_0= ruleAction )
                            {
                            // InternalThingML.g:4032:6: (lv_entry_10_0= ruleAction )
                            // InternalThingML.g:4033:7: lv_entry_10_0= ruleAction
                            {

                            							newCompositeNode(grammarAccess.getStateAccess().getEntryActionParserRuleCall_4_4_2_0());
                            						
                            pushFollow(FOLLOW_57);
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

                    // InternalThingML.g:4051:4: (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )?
                    int alt80=2;
                    int LA80_0 = input.LA(1);

                    if ( (LA80_0==65) ) {
                        alt80=1;
                    }
                    switch (alt80) {
                        case 1 :
                            // InternalThingML.g:4052:5: otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) )
                            {
                            otherlv_11=(Token)match(input,65,FOLLOW_50); 

                            					newLeafNode(otherlv_11, grammarAccess.getStateAccess().getOnKeyword_4_5_0());
                            				
                            otherlv_12=(Token)match(input,67,FOLLOW_25); 

                            					newLeafNode(otherlv_12, grammarAccess.getStateAccess().getExitKeyword_4_5_1());
                            				
                            // InternalThingML.g:4060:5: ( (lv_exit_13_0= ruleAction ) )
                            // InternalThingML.g:4061:6: (lv_exit_13_0= ruleAction )
                            {
                            // InternalThingML.g:4061:6: (lv_exit_13_0= ruleAction )
                            // InternalThingML.g:4062:7: lv_exit_13_0= ruleAction
                            {

                            							newCompositeNode(grammarAccess.getStateAccess().getExitActionParserRuleCall_4_5_2_0());
                            						
                            pushFollow(FOLLOW_58);
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

                    // InternalThingML.g:4080:4: ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )*
                    loop81:
                    do {
                        int alt81=4;
                        switch ( input.LA(1) ) {
                        case 36:
                            {
                            alt81=1;
                            }
                            break;
                        case 44:
                            {
                            alt81=2;
                            }
                            break;
                        case 73:
                            {
                            alt81=3;
                            }
                            break;

                        }

                        switch (alt81) {
                    	case 1 :
                    	    // InternalThingML.g:4081:5: ( (lv_properties_14_0= ruleProperty ) )
                    	    {
                    	    // InternalThingML.g:4081:5: ( (lv_properties_14_0= ruleProperty ) )
                    	    // InternalThingML.g:4082:6: (lv_properties_14_0= ruleProperty )
                    	    {
                    	    // InternalThingML.g:4082:6: (lv_properties_14_0= ruleProperty )
                    	    // InternalThingML.g:4083:7: lv_properties_14_0= ruleProperty
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getPropertiesPropertyParserRuleCall_4_6_0_0());
                    	    						
                    	    pushFollow(FOLLOW_58);
                    	    lv_properties_14_0=ruleProperty();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStateRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"properties",
                    	    								lv_properties_14_0,
                    	    								"org.thingml.xtext.ThingML.Property");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalThingML.g:4101:5: ( (lv_internal_15_0= ruleInternalTransition ) )
                    	    {
                    	    // InternalThingML.g:4101:5: ( (lv_internal_15_0= ruleInternalTransition ) )
                    	    // InternalThingML.g:4102:6: (lv_internal_15_0= ruleInternalTransition )
                    	    {
                    	    // InternalThingML.g:4102:6: (lv_internal_15_0= ruleInternalTransition )
                    	    // InternalThingML.g:4103:7: lv_internal_15_0= ruleInternalTransition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getInternalInternalTransitionParserRuleCall_4_6_1_0());
                    	    						
                    	    pushFollow(FOLLOW_58);
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
                    	case 3 :
                    	    // InternalThingML.g:4121:5: ( (lv_outgoing_16_0= ruleTransition ) )
                    	    {
                    	    // InternalThingML.g:4121:5: ( (lv_outgoing_16_0= ruleTransition ) )
                    	    // InternalThingML.g:4122:6: (lv_outgoing_16_0= ruleTransition )
                    	    {
                    	    // InternalThingML.g:4122:6: (lv_outgoing_16_0= ruleTransition )
                    	    // InternalThingML.g:4123:7: lv_outgoing_16_0= ruleTransition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getOutgoingTransitionParserRuleCall_4_6_2_0());
                    	    						
                    	    pushFollow(FOLLOW_58);
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
                    	    break loop81;
                        }
                    } while (true);

                    otherlv_17=(Token)match(input,21,FOLLOW_2); 

                    				newLeafNode(otherlv_17, grammarAccess.getStateAccess().getRightCurlyBracketKeyword_4_7());
                    			

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


    // $ANTLR start "entryRuleTransition"
    // InternalThingML.g:4150:1: entryRuleTransition returns [EObject current=null] : iv_ruleTransition= ruleTransition EOF ;
    public final EObject entryRuleTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransition = null;


        try {
            // InternalThingML.g:4150:51: (iv_ruleTransition= ruleTransition EOF )
            // InternalThingML.g:4151:2: iv_ruleTransition= ruleTransition EOF
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
    // InternalThingML.g:4157:1: ruleTransition returns [EObject current=null] : (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? ) ;
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
            // InternalThingML.g:4163:2: ( (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? ) )
            // InternalThingML.g:4164:2: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:4164:2: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? )
            // InternalThingML.g:4165:3: otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )?
            {
            otherlv_0=(Token)match(input,73,FOLLOW_59); 

            			newLeafNode(otherlv_0, grammarAccess.getTransitionAccess().getTransitionKeyword_0());
            		
            // InternalThingML.g:4169:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==RULE_ID) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // InternalThingML.g:4170:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:4170:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:4171:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_60); 

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

            otherlv_2=(Token)match(input,51,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getTransitionAccess().getHyphenMinusGreaterThanSignKeyword_2());
            		
            // InternalThingML.g:4191:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:4192:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:4192:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:4193:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTransitionRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_61); 

            					newLeafNode(otherlv_3, grammarAccess.getTransitionAccess().getTargetStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:4204:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==13) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // InternalThingML.g:4205:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:4205:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:4206:5: lv_annotations_4_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_61);
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
            	    break loop84;
                }
            } while (true);

            // InternalThingML.g:4223:3: (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )*
            loop85:
            do {
                int alt85=2;
                int LA85_0 = input.LA(1);

                if ( (LA85_0==74) ) {
                    alt85=1;
                }


                switch (alt85) {
            	case 1 :
            	    // InternalThingML.g:4224:4: otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) )
            	    {
            	    otherlv_5=(Token)match(input,74,FOLLOW_6); 

            	    				newLeafNode(otherlv_5, grammarAccess.getTransitionAccess().getEventKeyword_5_0());
            	    			
            	    // InternalThingML.g:4228:4: ( (lv_event_6_0= ruleEvent ) )
            	    // InternalThingML.g:4229:5: (lv_event_6_0= ruleEvent )
            	    {
            	    // InternalThingML.g:4229:5: (lv_event_6_0= ruleEvent )
            	    // InternalThingML.g:4230:6: lv_event_6_0= ruleEvent
            	    {

            	    						newCompositeNode(grammarAccess.getTransitionAccess().getEventEventParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_62);
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
            	    break loop85;
                }
            } while (true);

            // InternalThingML.g:4248:3: (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==75) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // InternalThingML.g:4249:4: otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) )
                    {
                    otherlv_7=(Token)match(input,75,FOLLOW_19); 

                    				newLeafNode(otherlv_7, grammarAccess.getTransitionAccess().getGuardKeyword_6_0());
                    			
                    // InternalThingML.g:4253:4: ( (lv_guard_8_0= ruleExpression ) )
                    // InternalThingML.g:4254:5: (lv_guard_8_0= ruleExpression )
                    {
                    // InternalThingML.g:4254:5: (lv_guard_8_0= ruleExpression )
                    // InternalThingML.g:4255:6: lv_guard_8_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getTransitionAccess().getGuardExpressionParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_63);
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

            // InternalThingML.g:4273:3: (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==76) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // InternalThingML.g:4274:4: otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) )
                    {
                    otherlv_9=(Token)match(input,76,FOLLOW_25); 

                    				newLeafNode(otherlv_9, grammarAccess.getTransitionAccess().getActionKeyword_7_0());
                    			
                    // InternalThingML.g:4278:4: ( (lv_action_10_0= ruleAction ) )
                    // InternalThingML.g:4279:5: (lv_action_10_0= ruleAction )
                    {
                    // InternalThingML.g:4279:5: (lv_action_10_0= ruleAction )
                    // InternalThingML.g:4280:6: lv_action_10_0= ruleAction
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
    // InternalThingML.g:4302:1: entryRuleInternalTransition returns [EObject current=null] : iv_ruleInternalTransition= ruleInternalTransition EOF ;
    public final EObject entryRuleInternalTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInternalTransition = null;


        try {
            // InternalThingML.g:4302:59: (iv_ruleInternalTransition= ruleInternalTransition EOF )
            // InternalThingML.g:4303:2: iv_ruleInternalTransition= ruleInternalTransition EOF
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
    // InternalThingML.g:4309:1: ruleInternalTransition returns [EObject current=null] : ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? ) ;
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
            // InternalThingML.g:4315:2: ( ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? ) )
            // InternalThingML.g:4316:2: ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:4316:2: ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? )
            // InternalThingML.g:4317:3: () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )?
            {
            // InternalThingML.g:4317:3: ()
            // InternalThingML.g:4318:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getInternalTransitionAccess().getInternalTransitionAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,44,FOLLOW_64); 

            			newLeafNode(otherlv_1, grammarAccess.getInternalTransitionAccess().getInternalKeyword_1());
            		
            // InternalThingML.g:4328:3: ( (lv_name_2_0= RULE_ID ) )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==RULE_ID) ) {
                alt88=1;
            }
            switch (alt88) {
                case 1 :
                    // InternalThingML.g:4329:4: (lv_name_2_0= RULE_ID )
                    {
                    // InternalThingML.g:4329:4: (lv_name_2_0= RULE_ID )
                    // InternalThingML.g:4330:5: lv_name_2_0= RULE_ID
                    {
                    lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_61); 

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

            // InternalThingML.g:4346:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==13) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // InternalThingML.g:4347:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:4347:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:4348:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getInternalTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_61);
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
            	    break loop89;
                }
            } while (true);

            // InternalThingML.g:4365:3: (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )*
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( (LA90_0==74) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // InternalThingML.g:4366:4: otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) )
            	    {
            	    otherlv_4=(Token)match(input,74,FOLLOW_6); 

            	    				newLeafNode(otherlv_4, grammarAccess.getInternalTransitionAccess().getEventKeyword_4_0());
            	    			
            	    // InternalThingML.g:4370:4: ( (lv_event_5_0= ruleEvent ) )
            	    // InternalThingML.g:4371:5: (lv_event_5_0= ruleEvent )
            	    {
            	    // InternalThingML.g:4371:5: (lv_event_5_0= ruleEvent )
            	    // InternalThingML.g:4372:6: lv_event_5_0= ruleEvent
            	    {

            	    						newCompositeNode(grammarAccess.getInternalTransitionAccess().getEventEventParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_62);
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
            	    break loop90;
                }
            } while (true);

            // InternalThingML.g:4390:3: (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==75) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // InternalThingML.g:4391:4: otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) )
                    {
                    otherlv_6=(Token)match(input,75,FOLLOW_19); 

                    				newLeafNode(otherlv_6, grammarAccess.getInternalTransitionAccess().getGuardKeyword_5_0());
                    			
                    // InternalThingML.g:4395:4: ( (lv_guard_7_0= ruleExpression ) )
                    // InternalThingML.g:4396:5: (lv_guard_7_0= ruleExpression )
                    {
                    // InternalThingML.g:4396:5: (lv_guard_7_0= ruleExpression )
                    // InternalThingML.g:4397:6: lv_guard_7_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getInternalTransitionAccess().getGuardExpressionParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_63);
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

            // InternalThingML.g:4415:3: (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )?
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==76) ) {
                alt92=1;
            }
            switch (alt92) {
                case 1 :
                    // InternalThingML.g:4416:4: otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) )
                    {
                    otherlv_8=(Token)match(input,76,FOLLOW_25); 

                    				newLeafNode(otherlv_8, grammarAccess.getInternalTransitionAccess().getActionKeyword_6_0());
                    			
                    // InternalThingML.g:4420:4: ( (lv_action_9_0= ruleAction ) )
                    // InternalThingML.g:4421:5: (lv_action_9_0= ruleAction )
                    {
                    // InternalThingML.g:4421:5: (lv_action_9_0= ruleAction )
                    // InternalThingML.g:4422:6: lv_action_9_0= ruleAction
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
    // InternalThingML.g:4444:1: entryRuleEvent returns [EObject current=null] : iv_ruleEvent= ruleEvent EOF ;
    public final EObject entryRuleEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEvent = null;


        try {
            // InternalThingML.g:4444:46: (iv_ruleEvent= ruleEvent EOF )
            // InternalThingML.g:4445:2: iv_ruleEvent= ruleEvent EOF
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
    // InternalThingML.g:4451:1: ruleEvent returns [EObject current=null] : this_ReceiveMessage_0= ruleReceiveMessage ;
    public final EObject ruleEvent() throws RecognitionException {
        EObject current = null;

        EObject this_ReceiveMessage_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4457:2: (this_ReceiveMessage_0= ruleReceiveMessage )
            // InternalThingML.g:4458:2: this_ReceiveMessage_0= ruleReceiveMessage
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
    // InternalThingML.g:4469:1: entryRuleReceiveMessage returns [EObject current=null] : iv_ruleReceiveMessage= ruleReceiveMessage EOF ;
    public final EObject entryRuleReceiveMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReceiveMessage = null;


        try {
            // InternalThingML.g:4469:55: (iv_ruleReceiveMessage= ruleReceiveMessage EOF )
            // InternalThingML.g:4470:2: iv_ruleReceiveMessage= ruleReceiveMessage EOF
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
    // InternalThingML.g:4476:1: ruleReceiveMessage returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '?' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleReceiveMessage() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:4482:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '?' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalThingML.g:4483:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '?' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalThingML.g:4483:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '?' ( (otherlv_2= RULE_ID ) ) )
            // InternalThingML.g:4484:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '?' ( (otherlv_2= RULE_ID ) )
            {
            // InternalThingML.g:4484:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4485:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4485:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4486:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReceiveMessageRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_65); 

            					newLeafNode(otherlv_0, grammarAccess.getReceiveMessageAccess().getPortPortCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,77,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getReceiveMessageAccess().getQuestionMarkKeyword_1());
            		
            // InternalThingML.g:4501:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:4502:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:4502:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:4503:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReceiveMessageRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_2, grammarAccess.getReceiveMessageAccess().getMessageMessageCrossReference_2_0());
            				

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
    // InternalThingML.g:4518:1: entryRuleAction returns [EObject current=null] : iv_ruleAction= ruleAction EOF ;
    public final EObject entryRuleAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAction = null;


        try {
            // InternalThingML.g:4518:47: (iv_ruleAction= ruleAction EOF )
            // InternalThingML.g:4519:2: iv_ruleAction= ruleAction EOF
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
    // InternalThingML.g:4525:1: ruleAction returns [EObject current=null] : (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable ) ;
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
            // InternalThingML.g:4531:2: ( (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable ) )
            // InternalThingML.g:4532:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )
            {
            // InternalThingML.g:4532:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )
            int alt93=14;
            alt93 = dfa93.predict(input);
            switch (alt93) {
                case 1 :
                    // InternalThingML.g:4533:3: this_ActionBlock_0= ruleActionBlock
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
                    // InternalThingML.g:4542:3: this_ExternStatement_1= ruleExternStatement
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
                    // InternalThingML.g:4551:3: this_SendAction_2= ruleSendAction
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
                    // InternalThingML.g:4560:3: this_VariableAssignment_3= ruleVariableAssignment
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
                    // InternalThingML.g:4569:3: this_Increment_4= ruleIncrement
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
                    // InternalThingML.g:4578:3: this_Decrement_5= ruleDecrement
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
                    // InternalThingML.g:4587:3: this_LoopAction_6= ruleLoopAction
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
                    // InternalThingML.g:4596:3: this_ConditionalAction_7= ruleConditionalAction
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
                    // InternalThingML.g:4605:3: this_ReturnAction_8= ruleReturnAction
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
                    // InternalThingML.g:4614:3: this_PrintAction_9= rulePrintAction
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
                    // InternalThingML.g:4623:3: this_ErrorAction_10= ruleErrorAction
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
                    // InternalThingML.g:4632:3: this_StartSession_11= ruleStartSession
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
                    // InternalThingML.g:4641:3: this_FunctionCallStatement_12= ruleFunctionCallStatement
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
                    // InternalThingML.g:4650:3: this_LocalVariable_13= ruleLocalVariable
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
    // InternalThingML.g:4662:1: entryRuleActionBlock returns [EObject current=null] : iv_ruleActionBlock= ruleActionBlock EOF ;
    public final EObject entryRuleActionBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActionBlock = null;


        try {
            // InternalThingML.g:4662:52: (iv_ruleActionBlock= ruleActionBlock EOF )
            // InternalThingML.g:4663:2: iv_ruleActionBlock= ruleActionBlock EOF
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
    // InternalThingML.g:4669:1: ruleActionBlock returns [EObject current=null] : ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' ) ;
    public final EObject ruleActionBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_actions_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4675:2: ( ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' ) )
            // InternalThingML.g:4676:2: ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' )
            {
            // InternalThingML.g:4676:2: ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' )
            // InternalThingML.g:4677:3: () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end'
            {
            // InternalThingML.g:4677:3: ()
            // InternalThingML.g:4678:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getActionBlockAccess().getActionBlockAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,78,FOLLOW_66); 

            			newLeafNode(otherlv_1, grammarAccess.getActionBlockAccess().getDoKeyword_1());
            		
            // InternalThingML.g:4688:3: ( (lv_actions_2_0= ruleAction ) )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==RULE_ID||LA94_0==RULE_STRING_EXT||LA94_0==56||LA94_0==78||(LA94_0>=80 && LA94_0<=81)||LA94_0==85||(LA94_0>=87 && LA94_0<=90)) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // InternalThingML.g:4689:4: (lv_actions_2_0= ruleAction )
            	    {
            	    // InternalThingML.g:4689:4: (lv_actions_2_0= ruleAction )
            	    // InternalThingML.g:4690:5: lv_actions_2_0= ruleAction
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
            	    break loop94;
                }
            } while (true);

            otherlv_3=(Token)match(input,79,FOLLOW_2); 

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
    // InternalThingML.g:4715:1: entryRuleExternStatement returns [EObject current=null] : iv_ruleExternStatement= ruleExternStatement EOF ;
    public final EObject entryRuleExternStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternStatement = null;


        try {
            // InternalThingML.g:4715:56: (iv_ruleExternStatement= ruleExternStatement EOF )
            // InternalThingML.g:4716:2: iv_ruleExternStatement= ruleExternStatement EOF
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
    // InternalThingML.g:4722:1: ruleExternStatement returns [EObject current=null] : ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) ;
    public final EObject ruleExternStatement() throws RecognitionException {
        EObject current = null;

        Token lv_statement_0_0=null;
        Token otherlv_1=null;
        EObject lv_segments_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4728:2: ( ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) )
            // InternalThingML.g:4729:2: ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            {
            // InternalThingML.g:4729:2: ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            // InternalThingML.g:4730:3: ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            {
            // InternalThingML.g:4730:3: ( (lv_statement_0_0= RULE_STRING_EXT ) )
            // InternalThingML.g:4731:4: (lv_statement_0_0= RULE_STRING_EXT )
            {
            // InternalThingML.g:4731:4: (lv_statement_0_0= RULE_STRING_EXT )
            // InternalThingML.g:4732:5: lv_statement_0_0= RULE_STRING_EXT
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

            // InternalThingML.g:4748:3: (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            loop95:
            do {
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( (LA95_0==50) ) {
                    alt95=1;
                }


                switch (alt95) {
            	case 1 :
            	    // InternalThingML.g:4749:4: otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,50,FOLLOW_19); 

            	    				newLeafNode(otherlv_1, grammarAccess.getExternStatementAccess().getAmpersandKeyword_1_0());
            	    			
            	    // InternalThingML.g:4753:4: ( (lv_segments_2_0= ruleExpression ) )
            	    // InternalThingML.g:4754:5: (lv_segments_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:4754:5: (lv_segments_2_0= ruleExpression )
            	    // InternalThingML.g:4755:6: lv_segments_2_0= ruleExpression
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
            	    break loop95;
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
    // InternalThingML.g:4777:1: entryRuleLocalVariable returns [EObject current=null] : iv_ruleLocalVariable= ruleLocalVariable EOF ;
    public final EObject entryRuleLocalVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalVariable = null;


        try {
            // InternalThingML.g:4777:54: (iv_ruleLocalVariable= ruleLocalVariable EOF )
            // InternalThingML.g:4778:2: iv_ruleLocalVariable= ruleLocalVariable EOF
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
    // InternalThingML.g:4784:1: ruleLocalVariable returns [EObject current=null] : ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (otherlv_4= RULE_ID ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleLocalVariable() throws RecognitionException {
        EObject current = null;

        Token lv_changeable_0_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        EObject lv_init_6_0 = null;

        EObject lv_annotations_7_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4790:2: ( ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (otherlv_4= RULE_ID ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:4791:2: ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (otherlv_4= RULE_ID ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:4791:2: ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (otherlv_4= RULE_ID ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:4792:3: ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (otherlv_4= RULE_ID ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:4792:3: ( (lv_changeable_0_0= 'readonly' ) )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==80) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // InternalThingML.g:4793:4: (lv_changeable_0_0= 'readonly' )
                    {
                    // InternalThingML.g:4793:4: (lv_changeable_0_0= 'readonly' )
                    // InternalThingML.g:4794:5: lv_changeable_0_0= 'readonly'
                    {
                    lv_changeable_0_0=(Token)match(input,80,FOLLOW_68); 

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
            		
            // InternalThingML.g:4810:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:4811:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:4811:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:4812:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_26); 

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

            otherlv_3=(Token)match(input,34,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getLocalVariableAccess().getColonKeyword_3());
            		
            // InternalThingML.g:4832:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:4833:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:4833:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:4834:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLocalVariableRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_27); 

            					newLeafNode(otherlv_4, grammarAccess.getLocalVariableAccess().getTypeTypeCrossReference_4_0());
            				

            }


            }

            // InternalThingML.g:4845:3: (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )?
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==29) ) {
                alt97=1;
            }
            switch (alt97) {
                case 1 :
                    // InternalThingML.g:4846:4: otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) )
                    {
                    otherlv_5=(Token)match(input,29,FOLLOW_19); 

                    				newLeafNode(otherlv_5, grammarAccess.getLocalVariableAccess().getEqualsSignKeyword_5_0());
                    			
                    // InternalThingML.g:4850:4: ( (lv_init_6_0= ruleExpression ) )
                    // InternalThingML.g:4851:5: (lv_init_6_0= ruleExpression )
                    {
                    // InternalThingML.g:4851:5: (lv_init_6_0= ruleExpression )
                    // InternalThingML.g:4852:6: lv_init_6_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getLocalVariableAccess().getInitExpressionParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_13);
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

            // InternalThingML.g:4870:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop98:
            do {
                int alt98=2;
                int LA98_0 = input.LA(1);

                if ( (LA98_0==13) ) {
                    alt98=1;
                }


                switch (alt98) {
            	case 1 :
            	    // InternalThingML.g:4871:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:4871:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:4872:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getLocalVariableAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_13);
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
            	    break loop98;
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
    // InternalThingML.g:4893:1: entryRuleSendAction returns [EObject current=null] : iv_ruleSendAction= ruleSendAction EOF ;
    public final EObject entryRuleSendAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSendAction = null;


        try {
            // InternalThingML.g:4893:51: (iv_ruleSendAction= ruleSendAction EOF )
            // InternalThingML.g:4894:2: iv_ruleSendAction= ruleSendAction EOF
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
    // InternalThingML.g:4900:1: ruleSendAction returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* otherlv_7= ')' ) ;
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
            // InternalThingML.g:4906:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* otherlv_7= ')' ) )
            // InternalThingML.g:4907:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* otherlv_7= ')' )
            {
            // InternalThingML.g:4907:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* otherlv_7= ')' )
            // InternalThingML.g:4908:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* otherlv_7= ')'
            {
            // InternalThingML.g:4908:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4909:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4909:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4910:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSendActionRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_69); 

            					newLeafNode(otherlv_0, grammarAccess.getSendActionAccess().getPortPortCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,82,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getSendActionAccess().getExclamationMarkKeyword_1());
            		
            // InternalThingML.g:4925:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:4926:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:4926:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:4927:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSendActionRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_2, grammarAccess.getSendActionAccess().getMessageMessageCrossReference_2_0());
            				

            }


            }

            otherlv_3=(Token)match(input,32,FOLLOW_19); 

            			newLeafNode(otherlv_3, grammarAccess.getSendActionAccess().getLeftParenthesisKeyword_3());
            		
            // InternalThingML.g:4942:3: ( (lv_parameters_4_0= ruleExpression ) )
            // InternalThingML.g:4943:4: (lv_parameters_4_0= ruleExpression )
            {
            // InternalThingML.g:4943:4: (lv_parameters_4_0= ruleExpression )
            // InternalThingML.g:4944:5: lv_parameters_4_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_22);
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

            // InternalThingML.g:4961:3: (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( (LA99_0==25) ) {
                    alt99=1;
                }


                switch (alt99) {
            	case 1 :
            	    // InternalThingML.g:4962:4: otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) )
            	    {
            	    otherlv_5=(Token)match(input,25,FOLLOW_19); 

            	    				newLeafNode(otherlv_5, grammarAccess.getSendActionAccess().getCommaKeyword_5_0());
            	    			
            	    // InternalThingML.g:4966:4: ( (lv_parameters_6_0= ruleExpression ) )
            	    // InternalThingML.g:4967:5: (lv_parameters_6_0= ruleExpression )
            	    {
            	    // InternalThingML.g:4967:5: (lv_parameters_6_0= ruleExpression )
            	    // InternalThingML.g:4968:6: lv_parameters_6_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_22);
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
            	    break loop99;
                }
            } while (true);

            otherlv_7=(Token)match(input,33,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getSendActionAccess().getRightParenthesisKeyword_6());
            		

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
    // InternalThingML.g:4994:1: entryRuleVariableAssignment returns [EObject current=null] : iv_ruleVariableAssignment= ruleVariableAssignment EOF ;
    public final EObject entryRuleVariableAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableAssignment = null;


        try {
            // InternalThingML.g:4994:59: (iv_ruleVariableAssignment= ruleVariableAssignment EOF )
            // InternalThingML.g:4995:2: iv_ruleVariableAssignment= ruleVariableAssignment EOF
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
    // InternalThingML.g:5001:1: ruleVariableAssignment returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) ) ;
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
            // InternalThingML.g:5007:2: ( ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) ) )
            // InternalThingML.g:5008:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) )
            {
            // InternalThingML.g:5008:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) )
            // InternalThingML.g:5009:3: ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) )
            {
            // InternalThingML.g:5009:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5010:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5010:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5011:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVariableAssignmentRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_18); 

            					newLeafNode(otherlv_0, grammarAccess.getVariableAssignmentAccess().getPropertyVariableCrossReference_0_0());
            				

            }


            }

            // InternalThingML.g:5022:3: (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )*
            loop100:
            do {
                int alt100=2;
                int LA100_0 = input.LA(1);

                if ( (LA100_0==27) ) {
                    alt100=1;
                }


                switch (alt100) {
            	case 1 :
            	    // InternalThingML.g:5023:4: otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']'
            	    {
            	    otherlv_1=(Token)match(input,27,FOLLOW_19); 

            	    				newLeafNode(otherlv_1, grammarAccess.getVariableAssignmentAccess().getLeftSquareBracketKeyword_1_0());
            	    			
            	    // InternalThingML.g:5027:4: ( (lv_index_2_0= ruleExpression ) )
            	    // InternalThingML.g:5028:5: (lv_index_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:5028:5: (lv_index_2_0= ruleExpression )
            	    // InternalThingML.g:5029:6: lv_index_2_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getVariableAssignmentAccess().getIndexExpressionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
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

            	    otherlv_3=(Token)match(input,28,FOLLOW_18); 

            	    				newLeafNode(otherlv_3, grammarAccess.getVariableAssignmentAccess().getRightSquareBracketKeyword_1_2());
            	    			

            	    }
            	    break;

            	default :
            	    break loop100;
                }
            } while (true);

            otherlv_4=(Token)match(input,29,FOLLOW_19); 

            			newLeafNode(otherlv_4, grammarAccess.getVariableAssignmentAccess().getEqualsSignKeyword_2());
            		
            // InternalThingML.g:5055:3: ( (lv_expression_5_0= ruleExpression ) )
            // InternalThingML.g:5056:4: (lv_expression_5_0= ruleExpression )
            {
            // InternalThingML.g:5056:4: (lv_expression_5_0= ruleExpression )
            // InternalThingML.g:5057:5: lv_expression_5_0= ruleExpression
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
    // InternalThingML.g:5078:1: entryRuleIncrement returns [EObject current=null] : iv_ruleIncrement= ruleIncrement EOF ;
    public final EObject entryRuleIncrement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIncrement = null;


        try {
            // InternalThingML.g:5078:50: (iv_ruleIncrement= ruleIncrement EOF )
            // InternalThingML.g:5079:2: iv_ruleIncrement= ruleIncrement EOF
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
    // InternalThingML.g:5085:1: ruleIncrement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' ) ;
    public final EObject ruleIncrement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:5091:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' ) )
            // InternalThingML.g:5092:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' )
            {
            // InternalThingML.g:5092:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' )
            // InternalThingML.g:5093:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '++'
            {
            // InternalThingML.g:5093:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5094:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5094:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5095:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getIncrementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_70); 

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
    // InternalThingML.g:5114:1: entryRuleDecrement returns [EObject current=null] : iv_ruleDecrement= ruleDecrement EOF ;
    public final EObject entryRuleDecrement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDecrement = null;


        try {
            // InternalThingML.g:5114:50: (iv_ruleDecrement= ruleDecrement EOF )
            // InternalThingML.g:5115:2: iv_ruleDecrement= ruleDecrement EOF
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
    // InternalThingML.g:5121:1: ruleDecrement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' ) ;
    public final EObject ruleDecrement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:5127:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' ) )
            // InternalThingML.g:5128:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' )
            {
            // InternalThingML.g:5128:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' )
            // InternalThingML.g:5129:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '--'
            {
            // InternalThingML.g:5129:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5130:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5130:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5131:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDecrementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_71); 

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
    // InternalThingML.g:5150:1: entryRuleLoopAction returns [EObject current=null] : iv_ruleLoopAction= ruleLoopAction EOF ;
    public final EObject entryRuleLoopAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLoopAction = null;


        try {
            // InternalThingML.g:5150:51: (iv_ruleLoopAction= ruleLoopAction EOF )
            // InternalThingML.g:5151:2: iv_ruleLoopAction= ruleLoopAction EOF
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
    // InternalThingML.g:5157:1: ruleLoopAction returns [EObject current=null] : (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) ) ;
    public final EObject ruleLoopAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_condition_2_0 = null;

        EObject lv_action_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5163:2: ( (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) ) )
            // InternalThingML.g:5164:2: (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) )
            {
            // InternalThingML.g:5164:2: (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) )
            // InternalThingML.g:5165:3: otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) )
            {
            otherlv_0=(Token)match(input,85,FOLLOW_21); 

            			newLeafNode(otherlv_0, grammarAccess.getLoopActionAccess().getWhileKeyword_0());
            		
            otherlv_1=(Token)match(input,32,FOLLOW_19); 

            			newLeafNode(otherlv_1, grammarAccess.getLoopActionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:5173:3: ( (lv_condition_2_0= ruleExpression ) )
            // InternalThingML.g:5174:4: (lv_condition_2_0= ruleExpression )
            {
            // InternalThingML.g:5174:4: (lv_condition_2_0= ruleExpression )
            // InternalThingML.g:5175:5: lv_condition_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getLoopActionAccess().getConditionExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_72);
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

            otherlv_3=(Token)match(input,33,FOLLOW_25); 

            			newLeafNode(otherlv_3, grammarAccess.getLoopActionAccess().getRightParenthesisKeyword_3());
            		
            // InternalThingML.g:5196:3: ( (lv_action_4_0= ruleAction ) )
            // InternalThingML.g:5197:4: (lv_action_4_0= ruleAction )
            {
            // InternalThingML.g:5197:4: (lv_action_4_0= ruleAction )
            // InternalThingML.g:5198:5: lv_action_4_0= ruleAction
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
    // InternalThingML.g:5219:1: entryRuleConditionalAction returns [EObject current=null] : iv_ruleConditionalAction= ruleConditionalAction EOF ;
    public final EObject entryRuleConditionalAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionalAction = null;


        try {
            // InternalThingML.g:5219:58: (iv_ruleConditionalAction= ruleConditionalAction EOF )
            // InternalThingML.g:5220:2: iv_ruleConditionalAction= ruleConditionalAction EOF
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
    // InternalThingML.g:5226:1: ruleConditionalAction returns [EObject current=null] : (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? ) ;
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
            // InternalThingML.g:5232:2: ( (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? ) )
            // InternalThingML.g:5233:2: (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:5233:2: (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? )
            // InternalThingML.g:5234:3: otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )?
            {
            otherlv_0=(Token)match(input,56,FOLLOW_21); 

            			newLeafNode(otherlv_0, grammarAccess.getConditionalActionAccess().getIfKeyword_0());
            		
            otherlv_1=(Token)match(input,32,FOLLOW_19); 

            			newLeafNode(otherlv_1, grammarAccess.getConditionalActionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:5242:3: ( (lv_condition_2_0= ruleExpression ) )
            // InternalThingML.g:5243:4: (lv_condition_2_0= ruleExpression )
            {
            // InternalThingML.g:5243:4: (lv_condition_2_0= ruleExpression )
            // InternalThingML.g:5244:5: lv_condition_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getConditionalActionAccess().getConditionExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_72);
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

            otherlv_3=(Token)match(input,33,FOLLOW_25); 

            			newLeafNode(otherlv_3, grammarAccess.getConditionalActionAccess().getRightParenthesisKeyword_3());
            		
            // InternalThingML.g:5265:3: ( (lv_action_4_0= ruleAction ) )
            // InternalThingML.g:5266:4: (lv_action_4_0= ruleAction )
            {
            // InternalThingML.g:5266:4: (lv_action_4_0= ruleAction )
            // InternalThingML.g:5267:5: lv_action_4_0= ruleAction
            {

            					newCompositeNode(grammarAccess.getConditionalActionAccess().getActionActionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_73);
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

            // InternalThingML.g:5284:3: (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )?
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==86) ) {
                alt101=1;
            }
            switch (alt101) {
                case 1 :
                    // InternalThingML.g:5285:4: otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) )
                    {
                    otherlv_5=(Token)match(input,86,FOLLOW_25); 

                    				newLeafNode(otherlv_5, grammarAccess.getConditionalActionAccess().getElseKeyword_5_0());
                    			
                    // InternalThingML.g:5289:4: ( (lv_elseAction_6_0= ruleAction ) )
                    // InternalThingML.g:5290:5: (lv_elseAction_6_0= ruleAction )
                    {
                    // InternalThingML.g:5290:5: (lv_elseAction_6_0= ruleAction )
                    // InternalThingML.g:5291:6: lv_elseAction_6_0= ruleAction
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
    // InternalThingML.g:5313:1: entryRuleReturnAction returns [EObject current=null] : iv_ruleReturnAction= ruleReturnAction EOF ;
    public final EObject entryRuleReturnAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReturnAction = null;


        try {
            // InternalThingML.g:5313:53: (iv_ruleReturnAction= ruleReturnAction EOF )
            // InternalThingML.g:5314:2: iv_ruleReturnAction= ruleReturnAction EOF
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
    // InternalThingML.g:5320:1: ruleReturnAction returns [EObject current=null] : (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) ) ;
    public final EObject ruleReturnAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_exp_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5326:2: ( (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:5327:2: (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:5327:2: (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) )
            // InternalThingML.g:5328:3: otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,87,FOLLOW_19); 

            			newLeafNode(otherlv_0, grammarAccess.getReturnActionAccess().getReturnKeyword_0());
            		
            // InternalThingML.g:5332:3: ( (lv_exp_1_0= ruleExpression ) )
            // InternalThingML.g:5333:4: (lv_exp_1_0= ruleExpression )
            {
            // InternalThingML.g:5333:4: (lv_exp_1_0= ruleExpression )
            // InternalThingML.g:5334:5: lv_exp_1_0= ruleExpression
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
    // InternalThingML.g:5355:1: entryRulePrintAction returns [EObject current=null] : iv_rulePrintAction= rulePrintAction EOF ;
    public final EObject entryRulePrintAction() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrintAction = null;


        try {
            // InternalThingML.g:5355:52: (iv_rulePrintAction= rulePrintAction EOF )
            // InternalThingML.g:5356:2: iv_rulePrintAction= rulePrintAction EOF
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
    // InternalThingML.g:5362:1: rulePrintAction returns [EObject current=null] : (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) ) ;
    public final EObject rulePrintAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_msg_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5368:2: ( (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:5369:2: (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:5369:2: (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) )
            // InternalThingML.g:5370:3: otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,88,FOLLOW_19); 

            			newLeafNode(otherlv_0, grammarAccess.getPrintActionAccess().getPrintKeyword_0());
            		
            // InternalThingML.g:5374:3: ( (lv_msg_1_0= ruleExpression ) )
            // InternalThingML.g:5375:4: (lv_msg_1_0= ruleExpression )
            {
            // InternalThingML.g:5375:4: (lv_msg_1_0= ruleExpression )
            // InternalThingML.g:5376:5: lv_msg_1_0= ruleExpression
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
    // InternalThingML.g:5397:1: entryRuleErrorAction returns [EObject current=null] : iv_ruleErrorAction= ruleErrorAction EOF ;
    public final EObject entryRuleErrorAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleErrorAction = null;


        try {
            // InternalThingML.g:5397:52: (iv_ruleErrorAction= ruleErrorAction EOF )
            // InternalThingML.g:5398:2: iv_ruleErrorAction= ruleErrorAction EOF
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
    // InternalThingML.g:5404:1: ruleErrorAction returns [EObject current=null] : (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) ) ;
    public final EObject ruleErrorAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_msg_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5410:2: ( (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:5411:2: (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:5411:2: (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) )
            // InternalThingML.g:5412:3: otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,89,FOLLOW_19); 

            			newLeafNode(otherlv_0, grammarAccess.getErrorActionAccess().getErrorKeyword_0());
            		
            // InternalThingML.g:5416:3: ( (lv_msg_1_0= ruleExpression ) )
            // InternalThingML.g:5417:4: (lv_msg_1_0= ruleExpression )
            {
            // InternalThingML.g:5417:4: (lv_msg_1_0= ruleExpression )
            // InternalThingML.g:5418:5: lv_msg_1_0= ruleExpression
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
    // InternalThingML.g:5439:1: entryRuleStartSession returns [EObject current=null] : iv_ruleStartSession= ruleStartSession EOF ;
    public final EObject entryRuleStartSession() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStartSession = null;


        try {
            // InternalThingML.g:5439:53: (iv_ruleStartSession= ruleStartSession EOF )
            // InternalThingML.g:5440:2: iv_ruleStartSession= ruleStartSession EOF
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
    // InternalThingML.g:5446:1: ruleStartSession returns [EObject current=null] : (otherlv_0= 'spawn' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' ( (lv_constructor_3_0= rulePropertyAssign ) )* otherlv_4= '}' ) ;
    public final EObject ruleStartSession() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_constructor_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5452:2: ( (otherlv_0= 'spawn' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' ( (lv_constructor_3_0= rulePropertyAssign ) )* otherlv_4= '}' ) )
            // InternalThingML.g:5453:2: (otherlv_0= 'spawn' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' ( (lv_constructor_3_0= rulePropertyAssign ) )* otherlv_4= '}' )
            {
            // InternalThingML.g:5453:2: (otherlv_0= 'spawn' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' ( (lv_constructor_3_0= rulePropertyAssign ) )* otherlv_4= '}' )
            // InternalThingML.g:5454:3: otherlv_0= 'spawn' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' ( (lv_constructor_3_0= rulePropertyAssign ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,90,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getStartSessionAccess().getSpawnKeyword_0());
            		
            // InternalThingML.g:5458:3: ( (otherlv_1= RULE_ID ) )
            // InternalThingML.g:5459:4: (otherlv_1= RULE_ID )
            {
            // InternalThingML.g:5459:4: (otherlv_1= RULE_ID )
            // InternalThingML.g:5460:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStartSessionRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_74); 

            					newLeafNode(otherlv_1, grammarAccess.getStartSessionAccess().getSessionSessionCrossReference_1_0());
            				

            }


            }

            otherlv_2=(Token)match(input,20,FOLLOW_75); 

            			newLeafNode(otherlv_2, grammarAccess.getStartSessionAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalThingML.g:5475:3: ( (lv_constructor_3_0= rulePropertyAssign ) )*
            loop102:
            do {
                int alt102=2;
                int LA102_0 = input.LA(1);

                if ( (LA102_0==26) ) {
                    alt102=1;
                }


                switch (alt102) {
            	case 1 :
            	    // InternalThingML.g:5476:4: (lv_constructor_3_0= rulePropertyAssign )
            	    {
            	    // InternalThingML.g:5476:4: (lv_constructor_3_0= rulePropertyAssign )
            	    // InternalThingML.g:5477:5: lv_constructor_3_0= rulePropertyAssign
            	    {

            	    					newCompositeNode(grammarAccess.getStartSessionAccess().getConstructorPropertyAssignParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_75);
            	    lv_constructor_3_0=rulePropertyAssign();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getStartSessionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"constructor",
            	    						lv_constructor_3_0,
            	    						"org.thingml.xtext.ThingML.PropertyAssign");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop102;
                }
            } while (true);

            otherlv_4=(Token)match(input,21,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getStartSessionAccess().getRightCurlyBracketKeyword_4());
            		

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
    // InternalThingML.g:5502:1: entryRuleFunctionCallStatement returns [EObject current=null] : iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF ;
    public final EObject entryRuleFunctionCallStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCallStatement = null;


        try {
            // InternalThingML.g:5502:62: (iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF )
            // InternalThingML.g:5503:2: iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF
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
    // InternalThingML.g:5509:1: ruleFunctionCallStatement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* otherlv_5= ')' ) ;
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
            // InternalThingML.g:5515:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* otherlv_5= ')' ) )
            // InternalThingML.g:5516:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* otherlv_5= ')' )
            {
            // InternalThingML.g:5516:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* otherlv_5= ')' )
            // InternalThingML.g:5517:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* otherlv_5= ')'
            {
            // InternalThingML.g:5517:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5518:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5518:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5519:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFunctionCallStatementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_0, grammarAccess.getFunctionCallStatementAccess().getFunctionFunctionCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,32,FOLLOW_19); 

            			newLeafNode(otherlv_1, grammarAccess.getFunctionCallStatementAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:5534:3: ( (lv_parameters_2_0= ruleExpression ) )
            // InternalThingML.g:5535:4: (lv_parameters_2_0= ruleExpression )
            {
            // InternalThingML.g:5535:4: (lv_parameters_2_0= ruleExpression )
            // InternalThingML.g:5536:5: lv_parameters_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_22);
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

            // InternalThingML.g:5553:3: (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
            loop103:
            do {
                int alt103=2;
                int LA103_0 = input.LA(1);

                if ( (LA103_0==25) ) {
                    alt103=1;
                }


                switch (alt103) {
            	case 1 :
            	    // InternalThingML.g:5554:4: otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) )
            	    {
            	    otherlv_3=(Token)match(input,25,FOLLOW_19); 

            	    				newLeafNode(otherlv_3, grammarAccess.getFunctionCallStatementAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalThingML.g:5558:4: ( (lv_parameters_4_0= ruleExpression ) )
            	    // InternalThingML.g:5559:5: (lv_parameters_4_0= ruleExpression )
            	    {
            	    // InternalThingML.g:5559:5: (lv_parameters_4_0= ruleExpression )
            	    // InternalThingML.g:5560:6: lv_parameters_4_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_22);
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
            	    break loop103;
                }
            } while (true);

            otherlv_5=(Token)match(input,33,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getFunctionCallStatementAccess().getRightParenthesisKeyword_4());
            		

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
    // InternalThingML.g:5586:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // InternalThingML.g:5586:51: (iv_ruleExpression= ruleExpression EOF )
            // InternalThingML.g:5587:2: iv_ruleExpression= ruleExpression EOF
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
    // InternalThingML.g:5593:1: ruleExpression returns [EObject current=null] : this_ExternExpression_0= ruleExternExpression ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject this_ExternExpression_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5599:2: (this_ExternExpression_0= ruleExternExpression )
            // InternalThingML.g:5600:2: this_ExternExpression_0= ruleExternExpression
            {

            		newCompositeNode(grammarAccess.getExpressionAccess().getExternExpressionParserRuleCall());
            	
            pushFollow(FOLLOW_2);
            this_ExternExpression_0=ruleExternExpression();

            state._fsp--;


            		current = this_ExternExpression_0;
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


    // $ANTLR start "entryRuleExternExpression"
    // InternalThingML.g:5611:1: entryRuleExternExpression returns [EObject current=null] : iv_ruleExternExpression= ruleExternExpression EOF ;
    public final EObject entryRuleExternExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternExpression = null;


        try {
            // InternalThingML.g:5611:57: (iv_ruleExternExpression= ruleExternExpression EOF )
            // InternalThingML.g:5612:2: iv_ruleExternExpression= ruleExternExpression EOF
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
    // InternalThingML.g:5618:1: ruleExternExpression returns [EObject current=null] : ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) ;
    public final EObject ruleExternExpression() throws RecognitionException {
        EObject current = null;

        Token lv_expression_0_0=null;
        Token otherlv_1=null;
        EObject lv_segments_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5624:2: ( ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) )
            // InternalThingML.g:5625:2: ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            {
            // InternalThingML.g:5625:2: ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            // InternalThingML.g:5626:3: ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            {
            // InternalThingML.g:5626:3: ( (lv_expression_0_0= RULE_STRING_EXT ) )
            // InternalThingML.g:5627:4: (lv_expression_0_0= RULE_STRING_EXT )
            {
            // InternalThingML.g:5627:4: (lv_expression_0_0= RULE_STRING_EXT )
            // InternalThingML.g:5628:5: lv_expression_0_0= RULE_STRING_EXT
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

            // InternalThingML.g:5644:3: (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            loop104:
            do {
                int alt104=2;
                int LA104_0 = input.LA(1);

                if ( (LA104_0==50) ) {
                    alt104=1;
                }


                switch (alt104) {
            	case 1 :
            	    // InternalThingML.g:5645:4: otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,50,FOLLOW_19); 

            	    				newLeafNode(otherlv_1, grammarAccess.getExternExpressionAccess().getAmpersandKeyword_1_0());
            	    			
            	    // InternalThingML.g:5649:4: ( (lv_segments_2_0= ruleExpression ) )
            	    // InternalThingML.g:5650:5: (lv_segments_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:5650:5: (lv_segments_2_0= ruleExpression )
            	    // InternalThingML.g:5651:6: lv_segments_2_0= ruleExpression
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
    // $ANTLR end "ruleExternExpression"


    // $ANTLR start "entryRuleConfiguration"
    // InternalThingML.g:5673:1: entryRuleConfiguration returns [EObject current=null] : iv_ruleConfiguration= ruleConfiguration EOF ;
    public final EObject entryRuleConfiguration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConfiguration = null;


        try {
            // InternalThingML.g:5673:54: (iv_ruleConfiguration= ruleConfiguration EOF )
            // InternalThingML.g:5674:2: iv_ruleConfiguration= ruleConfiguration EOF
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
    // InternalThingML.g:5680:1: ruleConfiguration returns [EObject current=null] : (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' ) ;
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
            // InternalThingML.g:5686:2: ( (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' ) )
            // InternalThingML.g:5687:2: (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' )
            {
            // InternalThingML.g:5687:2: (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' )
            // InternalThingML.g:5688:3: otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,91,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConfigurationAccess().getConfigurationKeyword_0());
            		
            // InternalThingML.g:5692:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:5693:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:5693:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:5694:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_11); 

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

            // InternalThingML.g:5710:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop105:
            do {
                int alt105=2;
                int LA105_0 = input.LA(1);

                if ( (LA105_0==13) ) {
                    alt105=1;
                }


                switch (alt105) {
            	case 1 :
            	    // InternalThingML.g:5711:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:5711:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:5712:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getConfigurationAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_11);
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
            	    break loop105;
                }
            } while (true);

            otherlv_3=(Token)match(input,20,FOLLOW_76); 

            			newLeafNode(otherlv_3, grammarAccess.getConfigurationAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalThingML.g:5733:3: ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )*
            loop106:
            do {
                int alt106=4;
                switch ( input.LA(1) ) {
                case 92:
                    {
                    alt106=1;
                    }
                    break;
                case 94:
                    {
                    alt106=2;
                    }
                    break;
                case 26:
                    {
                    alt106=3;
                    }
                    break;

                }

                switch (alt106) {
            	case 1 :
            	    // InternalThingML.g:5734:4: ( (lv_instances_4_0= ruleInstance ) )
            	    {
            	    // InternalThingML.g:5734:4: ( (lv_instances_4_0= ruleInstance ) )
            	    // InternalThingML.g:5735:5: (lv_instances_4_0= ruleInstance )
            	    {
            	    // InternalThingML.g:5735:5: (lv_instances_4_0= ruleInstance )
            	    // InternalThingML.g:5736:6: lv_instances_4_0= ruleInstance
            	    {

            	    						newCompositeNode(grammarAccess.getConfigurationAccess().getInstancesInstanceParserRuleCall_4_0_0());
            	    					
            	    pushFollow(FOLLOW_76);
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
            	    // InternalThingML.g:5754:4: ( (lv_connectors_5_0= ruleAbstractConnector ) )
            	    {
            	    // InternalThingML.g:5754:4: ( (lv_connectors_5_0= ruleAbstractConnector ) )
            	    // InternalThingML.g:5755:5: (lv_connectors_5_0= ruleAbstractConnector )
            	    {
            	    // InternalThingML.g:5755:5: (lv_connectors_5_0= ruleAbstractConnector )
            	    // InternalThingML.g:5756:6: lv_connectors_5_0= ruleAbstractConnector
            	    {

            	    						newCompositeNode(grammarAccess.getConfigurationAccess().getConnectorsAbstractConnectorParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_76);
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
            	    // InternalThingML.g:5774:4: ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) )
            	    {
            	    // InternalThingML.g:5774:4: ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) )
            	    // InternalThingML.g:5775:5: (lv_propassigns_6_0= ruleConfigPropertyAssign )
            	    {
            	    // InternalThingML.g:5775:5: (lv_propassigns_6_0= ruleConfigPropertyAssign )
            	    // InternalThingML.g:5776:6: lv_propassigns_6_0= ruleConfigPropertyAssign
            	    {

            	    						newCompositeNode(grammarAccess.getConfigurationAccess().getPropassignsConfigPropertyAssignParserRuleCall_4_2_0());
            	    					
            	    pushFollow(FOLLOW_76);
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
            	    break loop106;
                }
            } while (true);

            otherlv_7=(Token)match(input,21,FOLLOW_2); 

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
    // InternalThingML.g:5802:1: entryRuleInstance returns [EObject current=null] : iv_ruleInstance= ruleInstance EOF ;
    public final EObject entryRuleInstance() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInstance = null;


        try {
            // InternalThingML.g:5802:49: (iv_ruleInstance= ruleInstance EOF )
            // InternalThingML.g:5803:2: iv_ruleInstance= ruleInstance EOF
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
    // InternalThingML.g:5809:1: ruleInstance returns [EObject current=null] : (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleInstance() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject lv_annotations_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5815:2: ( (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:5816:2: (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:5816:2: (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:5817:3: otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,92,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getInstanceAccess().getInstanceKeyword_0());
            		
            // InternalThingML.g:5821:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:5822:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:5822:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:5823:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_26); 

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

            otherlv_2=(Token)match(input,34,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getInstanceAccess().getColonKeyword_2());
            		
            // InternalThingML.g:5843:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:5844:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:5844:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:5845:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getInstanceRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_13); 

            					newLeafNode(otherlv_3, grammarAccess.getInstanceAccess().getTypeThingCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:5856:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop107:
            do {
                int alt107=2;
                int LA107_0 = input.LA(1);

                if ( (LA107_0==13) ) {
                    alt107=1;
                }


                switch (alt107) {
            	case 1 :
            	    // InternalThingML.g:5857:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:5857:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:5858:5: lv_annotations_4_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getInstanceAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_13);
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
            	    break loop107;
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
    // InternalThingML.g:5879:1: entryRuleConfigPropertyAssign returns [EObject current=null] : iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF ;
    public final EObject entryRuleConfigPropertyAssign() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConfigPropertyAssign = null;


        try {
            // InternalThingML.g:5879:61: (iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF )
            // InternalThingML.g:5880:2: iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF
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
    // InternalThingML.g:5886:1: ruleConfigPropertyAssign returns [EObject current=null] : (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:5892:2: ( (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:5893:2: (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:5893:2: (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:5894:3: otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,26,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConfigPropertyAssignAccess().getSetKeyword_0());
            		
            // InternalThingML.g:5898:3: ( (lv_instance_1_0= ruleInstanceRef ) )
            // InternalThingML.g:5899:4: (lv_instance_1_0= ruleInstanceRef )
            {
            // InternalThingML.g:5899:4: (lv_instance_1_0= ruleInstanceRef )
            // InternalThingML.g:5900:5: lv_instance_1_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getInstanceInstanceRefParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_77);
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

            otherlv_2=(Token)match(input,93,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getConfigPropertyAssignAccess().getFullStopKeyword_2());
            		
            // InternalThingML.g:5921:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:5922:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:5922:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:5923:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConfigPropertyAssignRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_18); 

            					newLeafNode(otherlv_3, grammarAccess.getConfigPropertyAssignAccess().getPropertyPropertyCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:5934:3: (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )*
            loop108:
            do {
                int alt108=2;
                int LA108_0 = input.LA(1);

                if ( (LA108_0==27) ) {
                    alt108=1;
                }


                switch (alt108) {
            	case 1 :
            	    // InternalThingML.g:5935:4: otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']'
            	    {
            	    otherlv_4=(Token)match(input,27,FOLLOW_19); 

            	    				newLeafNode(otherlv_4, grammarAccess.getConfigPropertyAssignAccess().getLeftSquareBracketKeyword_4_0());
            	    			
            	    // InternalThingML.g:5939:4: ( (lv_index_5_0= ruleExpression ) )
            	    // InternalThingML.g:5940:5: (lv_index_5_0= ruleExpression )
            	    {
            	    // InternalThingML.g:5940:5: (lv_index_5_0= ruleExpression )
            	    // InternalThingML.g:5941:6: lv_index_5_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getIndexExpressionParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
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

            	    otherlv_6=(Token)match(input,28,FOLLOW_18); 

            	    				newLeafNode(otherlv_6, grammarAccess.getConfigPropertyAssignAccess().getRightSquareBracketKeyword_4_2());
            	    			

            	    }
            	    break;

            	default :
            	    break loop108;
                }
            } while (true);

            otherlv_7=(Token)match(input,29,FOLLOW_19); 

            			newLeafNode(otherlv_7, grammarAccess.getConfigPropertyAssignAccess().getEqualsSignKeyword_5());
            		
            // InternalThingML.g:5967:3: ( (lv_init_8_0= ruleExpression ) )
            // InternalThingML.g:5968:4: (lv_init_8_0= ruleExpression )
            {
            // InternalThingML.g:5968:4: (lv_init_8_0= ruleExpression )
            // InternalThingML.g:5969:5: lv_init_8_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getInitExpressionParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_13);
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

            // InternalThingML.g:5986:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop109:
            do {
                int alt109=2;
                int LA109_0 = input.LA(1);

                if ( (LA109_0==13) ) {
                    alt109=1;
                }


                switch (alt109) {
            	case 1 :
            	    // InternalThingML.g:5987:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:5987:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:5988:5: lv_annotations_9_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_13);
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
    // $ANTLR end "ruleConfigPropertyAssign"


    // $ANTLR start "entryRuleAbstractConnector"
    // InternalThingML.g:6009:1: entryRuleAbstractConnector returns [EObject current=null] : iv_ruleAbstractConnector= ruleAbstractConnector EOF ;
    public final EObject entryRuleAbstractConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAbstractConnector = null;


        try {
            // InternalThingML.g:6009:58: (iv_ruleAbstractConnector= ruleAbstractConnector EOF )
            // InternalThingML.g:6010:2: iv_ruleAbstractConnector= ruleAbstractConnector EOF
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
    // InternalThingML.g:6016:1: ruleAbstractConnector returns [EObject current=null] : (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector ) ;
    public final EObject ruleAbstractConnector() throws RecognitionException {
        EObject current = null;

        EObject this_Connector_0 = null;

        EObject this_ExternalConnector_1 = null;



        	enterRule();

        try {
            // InternalThingML.g:6022:2: ( (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector ) )
            // InternalThingML.g:6023:2: (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector )
            {
            // InternalThingML.g:6023:2: (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector )
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==94) ) {
                int LA110_1 = input.LA(2);

                if ( (LA110_1==RULE_ID) ) {
                    int LA110_2 = input.LA(3);

                    if ( (LA110_2==RULE_ID) ) {
                        int LA110_3 = input.LA(4);

                        if ( (LA110_3==93) ) {
                            int LA110_4 = input.LA(5);

                            if ( (LA110_4==RULE_ID) ) {
                                int LA110_5 = input.LA(6);

                                if ( (LA110_5==95) ) {
                                    alt110=1;
                                }
                                else if ( (LA110_5==96) ) {
                                    alt110=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 110, 5, input);

                                    throw nvae;
                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 110, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 110, 3, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA110_2==93) ) {
                        int LA110_4 = input.LA(4);

                        if ( (LA110_4==RULE_ID) ) {
                            int LA110_5 = input.LA(5);

                            if ( (LA110_5==95) ) {
                                alt110=1;
                            }
                            else if ( (LA110_5==96) ) {
                                alt110=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 110, 5, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 110, 4, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 110, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 110, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 110, 0, input);

                throw nvae;
            }
            switch (alt110) {
                case 1 :
                    // InternalThingML.g:6024:3: this_Connector_0= ruleConnector
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
                    // InternalThingML.g:6033:3: this_ExternalConnector_1= ruleExternalConnector
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
    // InternalThingML.g:6045:1: entryRuleConnector returns [EObject current=null] : iv_ruleConnector= ruleConnector EOF ;
    public final EObject entryRuleConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConnector = null;


        try {
            // InternalThingML.g:6045:50: (iv_ruleConnector= ruleConnector EOF )
            // InternalThingML.g:6046:2: iv_ruleConnector= ruleConnector EOF
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
    // InternalThingML.g:6052:1: ruleConnector returns [EObject current=null] : (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:6058:2: ( (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:6059:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:6059:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:6060:3: otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,94,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConnectorAccess().getConnectorKeyword_0());
            		
            // InternalThingML.g:6064:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==RULE_ID) ) {
                int LA111_1 = input.LA(2);

                if ( (LA111_1==RULE_ID) ) {
                    alt111=1;
                }
            }
            switch (alt111) {
                case 1 :
                    // InternalThingML.g:6065:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:6065:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:6066:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:6082:3: ( (lv_cli_2_0= ruleInstanceRef ) )
            // InternalThingML.g:6083:4: (lv_cli_2_0= ruleInstanceRef )
            {
            // InternalThingML.g:6083:4: (lv_cli_2_0= ruleInstanceRef )
            // InternalThingML.g:6084:5: lv_cli_2_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getConnectorAccess().getCliInstanceRefParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_77);
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

            otherlv_3=(Token)match(input,93,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getConnectorAccess().getFullStopKeyword_3());
            		
            // InternalThingML.g:6105:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:6106:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:6106:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:6107:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConnectorRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_78); 

            					newLeafNode(otherlv_4, grammarAccess.getConnectorAccess().getRequiredRequiredPortCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,95,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getConnectorAccess().getEqualsSignGreaterThanSignKeyword_5());
            		
            // InternalThingML.g:6122:3: ( (lv_srv_6_0= ruleInstanceRef ) )
            // InternalThingML.g:6123:4: (lv_srv_6_0= ruleInstanceRef )
            {
            // InternalThingML.g:6123:4: (lv_srv_6_0= ruleInstanceRef )
            // InternalThingML.g:6124:5: lv_srv_6_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getConnectorAccess().getSrvInstanceRefParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_77);
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

            otherlv_7=(Token)match(input,93,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getConnectorAccess().getFullStopKeyword_7());
            		
            // InternalThingML.g:6145:3: ( (otherlv_8= RULE_ID ) )
            // InternalThingML.g:6146:4: (otherlv_8= RULE_ID )
            {
            // InternalThingML.g:6146:4: (otherlv_8= RULE_ID )
            // InternalThingML.g:6147:5: otherlv_8= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConnectorRule());
            					}
            				
            otherlv_8=(Token)match(input,RULE_ID,FOLLOW_13); 

            					newLeafNode(otherlv_8, grammarAccess.getConnectorAccess().getProvidedProvidedPortCrossReference_8_0());
            				

            }


            }

            // InternalThingML.g:6158:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop112:
            do {
                int alt112=2;
                int LA112_0 = input.LA(1);

                if ( (LA112_0==13) ) {
                    alt112=1;
                }


                switch (alt112) {
            	case 1 :
            	    // InternalThingML.g:6159:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:6159:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:6160:5: lv_annotations_9_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getConnectorAccess().getAnnotationsPlatformAnnotationParserRuleCall_9_0());
            	    				
            	    pushFollow(FOLLOW_13);
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
    // $ANTLR end "ruleConnector"


    // $ANTLR start "entryRuleExternalConnector"
    // InternalThingML.g:6181:1: entryRuleExternalConnector returns [EObject current=null] : iv_ruleExternalConnector= ruleExternalConnector EOF ;
    public final EObject entryRuleExternalConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternalConnector = null;


        try {
            // InternalThingML.g:6181:58: (iv_ruleExternalConnector= ruleExternalConnector EOF )
            // InternalThingML.g:6182:2: iv_ruleExternalConnector= ruleExternalConnector EOF
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
    // InternalThingML.g:6188:1: ruleExternalConnector returns [EObject current=null] : (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:6194:2: ( (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:6195:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:6195:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:6196:3: otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,94,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getExternalConnectorAccess().getConnectorKeyword_0());
            		
            // InternalThingML.g:6200:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==RULE_ID) ) {
                int LA113_1 = input.LA(2);

                if ( (LA113_1==RULE_ID) ) {
                    alt113=1;
                }
            }
            switch (alt113) {
                case 1 :
                    // InternalThingML.g:6201:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:6201:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:6202:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:6218:3: ( (lv_inst_2_0= ruleInstanceRef ) )
            // InternalThingML.g:6219:4: (lv_inst_2_0= ruleInstanceRef )
            {
            // InternalThingML.g:6219:4: (lv_inst_2_0= ruleInstanceRef )
            // InternalThingML.g:6220:5: lv_inst_2_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getExternalConnectorAccess().getInstInstanceRefParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_77);
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

            otherlv_3=(Token)match(input,93,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getExternalConnectorAccess().getFullStopKeyword_3());
            		
            // InternalThingML.g:6241:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:6242:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:6242:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:6243:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalConnectorRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_79); 

            					newLeafNode(otherlv_4, grammarAccess.getExternalConnectorAccess().getPortPortCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,96,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getExternalConnectorAccess().getOverKeyword_5());
            		
            // InternalThingML.g:6258:3: ( (otherlv_6= RULE_ID ) )
            // InternalThingML.g:6259:4: (otherlv_6= RULE_ID )
            {
            // InternalThingML.g:6259:4: (otherlv_6= RULE_ID )
            // InternalThingML.g:6260:5: otherlv_6= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalConnectorRule());
            					}
            				
            otherlv_6=(Token)match(input,RULE_ID,FOLLOW_13); 

            					newLeafNode(otherlv_6, grammarAccess.getExternalConnectorAccess().getProtocolProtocolCrossReference_6_0());
            				

            }


            }

            // InternalThingML.g:6271:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop114:
            do {
                int alt114=2;
                int LA114_0 = input.LA(1);

                if ( (LA114_0==13) ) {
                    alt114=1;
                }


                switch (alt114) {
            	case 1 :
            	    // InternalThingML.g:6272:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:6272:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:6273:5: lv_annotations_7_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getExternalConnectorAccess().getAnnotationsPlatformAnnotationParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_13);
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
    // $ANTLR end "ruleExternalConnector"


    // $ANTLR start "entryRuleInstanceRef"
    // InternalThingML.g:6294:1: entryRuleInstanceRef returns [EObject current=null] : iv_ruleInstanceRef= ruleInstanceRef EOF ;
    public final EObject entryRuleInstanceRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInstanceRef = null;


        try {
            // InternalThingML.g:6294:52: (iv_ruleInstanceRef= ruleInstanceRef EOF )
            // InternalThingML.g:6295:2: iv_ruleInstanceRef= ruleInstanceRef EOF
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
    // InternalThingML.g:6301:1: ruleInstanceRef returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleInstanceRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6307:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalThingML.g:6308:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalThingML.g:6308:2: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:6309:3: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:6309:3: (otherlv_0= RULE_ID )
            // InternalThingML.g:6310:4: otherlv_0= RULE_ID
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


    protected DFA93 dfa93 = new DFA93(this);
    static final String dfa_1s = "\20\uffff";
    static final String dfa_2s = "\1\5\2\uffff\1\33\14\uffff";
    static final String dfa_3s = "\1\132\2\uffff\1\124\14\uffff";
    static final String dfa_4s = "\1\uffff\1\1\1\2\1\uffff\1\7\1\10\1\11\1\12\1\13\1\14\1\16\1\6\1\5\1\4\1\15\1\3";
    static final String dfa_5s = "\20\uffff}>";
    static final String[] dfa_6s = {
            "\1\3\1\uffff\1\2\60\uffff\1\5\25\uffff\1\1\1\uffff\2\12\3\uffff\1\4\1\uffff\1\6\1\7\1\10\1\11",
            "",
            "",
            "\1\15\1\uffff\1\15\2\uffff\1\16\61\uffff\1\17\1\14\1\13",
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

    class DFA93 extends DFA {

        public DFA93(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 93;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "4532:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x00000000404C5002L,0x0000000008000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x00000000404C4002L,0x0000000008000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000022000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000200020L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000800020L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000001102000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000002102000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x200038F084200000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000028000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000202000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000C00002000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000800002000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x01000000000000A0L,0x0000000007A34000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000020002002L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000200000020L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000060000200000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000060002200000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000400000002000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0022000000000020L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0001800000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0001000002000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x000C000000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0010000000000002L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0A80000000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0048000000000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0400000000000002L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x4000000000000020L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x8000000000102000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x2000101000200000L,0x00000000000001F2L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x2000101000200000L,0x00000000000001F0L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000200000L,0x0000000000000100L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x2000101000200000L,0x00000000000003F2L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x2000101000200000L,0x00000000000003F0L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x2000000000200000L,0x00000000000000F0L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x2000101000200000L,0x00000000000002F2L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x2000101000200000L,0x00000000000002F0L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0008000000000020L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000002002L,0x0000000000001C00L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001C00L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000002022L,0x0000000000001C00L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x01000000000000A0L,0x0000000007A3C000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000004200000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000004200000L,0x0000000050000000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});

}