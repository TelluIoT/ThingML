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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING_LIT", "RULE_ID", "RULE_INT", "RULE_STRING_EXT", "RULE_FLOAT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'import'", "'@'", "'datatype'", "'<'", "'>'", "';'", "'object'", "'enumeration'", "'{'", "'}'", "'thing'", "'fragment'", "'includes'", "','", "'set'", "'['", "']'", "'='", "'protocol'", "'function'", "'('", "')'", "':'", "'property'", "'message'", "'optional'", "'required'", "'port'", "'sends'", "'receives'", "'provided'", "'internal'", "'stream'", "'from'", "'select'", "'produce'", "'join'", "'&'", "'->'", "'::'", "'merge'", "'|'", "'keep'", "'if'", "'buffer'", "'by'", "'during'", "'length'", "'statechart'", "'init'", "'keeps'", "'history'", "'on'", "'entry'", "'exit'", "'final'", "'state'", "'composite'", "'session'", "'region'", "'transition'", "'event'", "'guard'", "'action'", "'?'", "'do'", "'end'", "'readonly'", "'var'", "'!'", "'++'", "'--'", "'while'", "'else'", "'return'", "'print'", "'error'", "'spawn'", "'or'", "'and'", "'=='", "'!='", "'>='", "'<='", "'+'", "'-'", "'*'", "'/'", "'%'", "'not'", "'enum'", "'true'", "'false'", "'.'", "'configuration'", "'instance'", "'connector'", "'=>'", "'over'"
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
    public static final int T__111=111;
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
                case 15:
                case 19:
                case 20:
                case 23:
                    {
                    alt2=1;
                    }
                    break;
                case 31:
                    {
                    alt2=2;
                    }
                    break;
                case 107:
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
            case 15:
                {
                alt3=1;
                }
                break;
            case 19:
                {
                alt3=2;
                }
                break;
            case 20:
                {
                alt3=3;
                }
                break;
            case 23:
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
            otherlv_0=(Token)match(input,15,FOLLOW_6); 

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

            otherlv_2=(Token)match(input,16,FOLLOW_8); 

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

            otherlv_4=(Token)match(input,17,FOLLOW_10); 

            			newLeafNode(otherlv_4, grammarAccess.getPrimitiveTypeAccess().getGreaterThanSignKeyword_4());
            		
            // InternalThingML.g:380:3: ( (lv_annotations_5_0= rulePlatformAnnotation ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==14) ) {
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

            otherlv_6=(Token)match(input,18,FOLLOW_2); 

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
            otherlv_0=(Token)match(input,19,FOLLOW_6); 

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

                if ( (LA5_0==14) ) {
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

            otherlv_3=(Token)match(input,18,FOLLOW_2); 

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
            otherlv_0=(Token)match(input,20,FOLLOW_6); 

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

                if ( (LA6_0==14) ) {
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

            otherlv_3=(Token)match(input,21,FOLLOW_12); 

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

            otherlv_5=(Token)match(input,22,FOLLOW_2); 

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

                if ( (LA8_0==14) ) {
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
            otherlv_0=(Token)match(input,23,FOLLOW_14); 

            			newLeafNode(otherlv_0, grammarAccess.getThingAccess().getThingKeyword_0());
            		
            // InternalThingML.g:633:3: ( (lv_fragment_1_0= 'fragment' ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==24) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalThingML.g:634:4: (lv_fragment_1_0= 'fragment' )
                    {
                    // InternalThingML.g:634:4: (lv_fragment_1_0= 'fragment' )
                    // InternalThingML.g:635:5: lv_fragment_1_0= 'fragment'
                    {
                    lv_fragment_1_0=(Token)match(input,24,FOLLOW_6); 

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

            if ( (LA11_0==25) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalThingML.g:666:4: otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )*
                    {
                    otherlv_3=(Token)match(input,25,FOLLOW_6); 

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

                        if ( (LA10_0==26) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // InternalThingML.g:684:5: otherlv_5= ',' ( (otherlv_6= RULE_ID ) )
                    	    {
                    	    otherlv_5=(Token)match(input,26,FOLLOW_6); 

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

                if ( (LA12_0==14) ) {
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

            otherlv_8=(Token)match(input,21,FOLLOW_17); 

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
                case 32:
                    {
                    alt13=4;
                    }
                    break;
                case 27:
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

            otherlv_16=(Token)match(input,22,FOLLOW_2); 

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
            otherlv_0=(Token)match(input,27,FOLLOW_6); 

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

                if ( (LA14_0==28) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalThingML.g:908:4: otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']'
            	    {
            	    otherlv_2=(Token)match(input,28,FOLLOW_19); 

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

            	    otherlv_4=(Token)match(input,29,FOLLOW_18); 

            	    				newLeafNode(otherlv_4, grammarAccess.getPropertyAssignAccess().getRightSquareBracketKeyword_2_2());
            	    			

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            otherlv_5=(Token)match(input,30,FOLLOW_19); 

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

                if ( (LA15_0==14) ) {
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
            otherlv_0=(Token)match(input,31,FOLLOW_6); 

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

                if ( (LA16_0==14) ) {
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

            otherlv_3=(Token)match(input,18,FOLLOW_2); 

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
    // InternalThingML.g:1053:1: ruleFunction returns [EObject current=null] : (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (otherlv_8= RULE_ID ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) ) ;
    public final EObject ruleFunction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;

        EObject lv_annotations_9_0 = null;

        EObject lv_body_10_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1059:2: ( (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (otherlv_8= RULE_ID ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) ) )
            // InternalThingML.g:1060:2: (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (otherlv_8= RULE_ID ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) )
            {
            // InternalThingML.g:1060:2: (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (otherlv_8= RULE_ID ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) )
            // InternalThingML.g:1061:3: otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (otherlv_8= RULE_ID ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) )
            {
            otherlv_0=(Token)match(input,32,FOLLOW_6); 

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

            otherlv_2=(Token)match(input,33,FOLLOW_22); 

            			newLeafNode(otherlv_2, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_2());
            		
            // InternalThingML.g:1087:3: ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_ID) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalThingML.g:1088:4: ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    {
                    // InternalThingML.g:1088:4: ( (lv_parameters_3_0= ruleParameter ) )
                    // InternalThingML.g:1089:5: (lv_parameters_3_0= ruleParameter )
                    {
                    // InternalThingML.g:1089:5: (lv_parameters_3_0= ruleParameter )
                    // InternalThingML.g:1090:6: lv_parameters_3_0= ruleParameter
                    {

                    						newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_3_0_0());
                    					
                    pushFollow(FOLLOW_23);
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

                    // InternalThingML.g:1107:4: (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==26) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // InternalThingML.g:1108:5: otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) )
                    	    {
                    	    otherlv_4=(Token)match(input,26,FOLLOW_6); 

                    	    					newLeafNode(otherlv_4, grammarAccess.getFunctionAccess().getCommaKeyword_3_1_0());
                    	    				
                    	    // InternalThingML.g:1112:5: ( (lv_parameters_5_0= ruleParameter ) )
                    	    // InternalThingML.g:1113:6: (lv_parameters_5_0= ruleParameter )
                    	    {
                    	    // InternalThingML.g:1113:6: (lv_parameters_5_0= ruleParameter )
                    	    // InternalThingML.g:1114:7: lv_parameters_5_0= ruleParameter
                    	    {

                    	    							newCompositeNode(grammarAccess.getFunctionAccess().getParametersParameterParserRuleCall_3_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_23);
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


                    }
                    break;

            }

            otherlv_6=(Token)match(input,34,FOLLOW_24); 

            			newLeafNode(otherlv_6, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_4());
            		
            // InternalThingML.g:1137:3: (otherlv_7= ':' ( (otherlv_8= RULE_ID ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==35) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalThingML.g:1138:4: otherlv_7= ':' ( (otherlv_8= RULE_ID ) )
                    {
                    otherlv_7=(Token)match(input,35,FOLLOW_6); 

                    				newLeafNode(otherlv_7, grammarAccess.getFunctionAccess().getColonKeyword_5_0());
                    			
                    // InternalThingML.g:1142:4: ( (otherlv_8= RULE_ID ) )
                    // InternalThingML.g:1143:5: (otherlv_8= RULE_ID )
                    {
                    // InternalThingML.g:1143:5: (otherlv_8= RULE_ID )
                    // InternalThingML.g:1144:6: otherlv_8= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getFunctionRule());
                    						}
                    					
                    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_24); 

                    						newLeafNode(otherlv_8, grammarAccess.getFunctionAccess().getTypeTypeCrossReference_5_1_0());
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalThingML.g:1156:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==14) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalThingML.g:1157:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1157:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1158:5: lv_annotations_9_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getFunctionAccess().getAnnotationsPlatformAnnotationParserRuleCall_6_0());
            	    				
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
            	    break loop20;
                }
            } while (true);

            // InternalThingML.g:1175:3: ( (lv_body_10_0= ruleAction ) )
            // InternalThingML.g:1176:4: (lv_body_10_0= ruleAction )
            {
            // InternalThingML.g:1176:4: (lv_body_10_0= ruleAction )
            // InternalThingML.g:1177:5: lv_body_10_0= ruleAction
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
    // InternalThingML.g:1198:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // InternalThingML.g:1198:49: (iv_ruleProperty= ruleProperty EOF )
            // InternalThingML.g:1199:2: iv_ruleProperty= ruleProperty EOF
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
    // InternalThingML.g:1205:1: ruleProperty returns [EObject current=null] : (otherlv_0= 'property' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:1211:2: ( (otherlv_0= 'property' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:1212:2: (otherlv_0= 'property' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:1212:2: (otherlv_0= 'property' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:1213:3: otherlv_0= 'property' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,36,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getPropertyAccess().getPropertyKeyword_0());
            		
            // InternalThingML.g:1217:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1218:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1218:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1219:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_25); 

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

            otherlv_2=(Token)match(input,35,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getPropertyAccess().getColonKeyword_2());
            		
            // InternalThingML.g:1239:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:1240:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:1240:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:1241:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPropertyRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_26); 

            					newLeafNode(otherlv_3, grammarAccess.getPropertyAccess().getTypeTypeCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:1252:3: (otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==30) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalThingML.g:1253:4: otherlv_4= '=' ( (lv_init_5_0= ruleExpression ) )
                    {
                    otherlv_4=(Token)match(input,30,FOLLOW_19); 

                    				newLeafNode(otherlv_4, grammarAccess.getPropertyAccess().getEqualsSignKeyword_4_0());
                    			
                    // InternalThingML.g:1257:4: ( (lv_init_5_0= ruleExpression ) )
                    // InternalThingML.g:1258:5: (lv_init_5_0= ruleExpression )
                    {
                    // InternalThingML.g:1258:5: (lv_init_5_0= ruleExpression )
                    // InternalThingML.g:1259:6: lv_init_5_0= ruleExpression
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

            // InternalThingML.g:1277:3: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==14) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalThingML.g:1278:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1278:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1279:5: lv_annotations_6_0= rulePlatformAnnotation
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
            	    break loop22;
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
    // InternalThingML.g:1300:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // InternalThingML.g:1300:48: (iv_ruleMessage= ruleMessage EOF )
            // InternalThingML.g:1301:2: iv_ruleMessage= ruleMessage EOF
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
    // InternalThingML.g:1307:1: ruleMessage returns [EObject current=null] : (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= ';' ) ;
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
            // InternalThingML.g:1313:2: ( (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= ';' ) )
            // InternalThingML.g:1314:2: (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= ';' )
            {
            // InternalThingML.g:1314:2: (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= ';' )
            // InternalThingML.g:1315:3: otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= ';'
            {
            otherlv_0=(Token)match(input,37,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getMessageAccess().getMessageKeyword_0());
            		
            // InternalThingML.g:1319:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1320:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1320:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1321:5: lv_name_1_0= RULE_ID
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

            otherlv_2=(Token)match(input,33,FOLLOW_22); 

            			newLeafNode(otherlv_2, grammarAccess.getMessageAccess().getLeftParenthesisKeyword_2());
            		
            // InternalThingML.g:1341:3: ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==RULE_ID) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalThingML.g:1342:4: ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    {
                    // InternalThingML.g:1342:4: ( (lv_parameters_3_0= ruleParameter ) )
                    // InternalThingML.g:1343:5: (lv_parameters_3_0= ruleParameter )
                    {
                    // InternalThingML.g:1343:5: (lv_parameters_3_0= ruleParameter )
                    // InternalThingML.g:1344:6: lv_parameters_3_0= ruleParameter
                    {

                    						newCompositeNode(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_0_0());
                    					
                    pushFollow(FOLLOW_23);
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

                    // InternalThingML.g:1361:4: (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    loop23:
                    do {
                        int alt23=2;
                        int LA23_0 = input.LA(1);

                        if ( (LA23_0==26) ) {
                            alt23=1;
                        }


                        switch (alt23) {
                    	case 1 :
                    	    // InternalThingML.g:1362:5: otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) )
                    	    {
                    	    otherlv_4=(Token)match(input,26,FOLLOW_6); 

                    	    					newLeafNode(otherlv_4, grammarAccess.getMessageAccess().getCommaKeyword_3_1_0());
                    	    				
                    	    // InternalThingML.g:1366:5: ( (lv_parameters_5_0= ruleParameter ) )
                    	    // InternalThingML.g:1367:6: (lv_parameters_5_0= ruleParameter )
                    	    {
                    	    // InternalThingML.g:1367:6: (lv_parameters_5_0= ruleParameter )
                    	    // InternalThingML.g:1368:7: lv_parameters_5_0= ruleParameter
                    	    {

                    	    							newCompositeNode(grammarAccess.getMessageAccess().getParametersParameterParserRuleCall_3_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_23);
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
                    	    break loop23;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,34,FOLLOW_10); 

            			newLeafNode(otherlv_6, grammarAccess.getMessageAccess().getRightParenthesisKeyword_4());
            		
            // InternalThingML.g:1391:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==14) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalThingML.g:1392:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1392:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1393:5: lv_annotations_7_0= rulePlatformAnnotation
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
            	    break loop25;
                }
            } while (true);

            otherlv_8=(Token)match(input,18,FOLLOW_2); 

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
    // InternalThingML.g:1418:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalThingML.g:1418:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalThingML.g:1419:2: iv_ruleParameter= ruleParameter EOF
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
    // InternalThingML.g:1425:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject lv_annotations_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1431:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:1432:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:1432:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:1433:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:1433:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:1434:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:1434:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:1435:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_25); 

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
            		
            // InternalThingML.g:1455:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:1456:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:1456:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:1457:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getParameterRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_13); 

            					newLeafNode(otherlv_2, grammarAccess.getParameterAccess().getTypeTypeCrossReference_2_0());
            				

            }


            }

            // InternalThingML.g:1468:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==14) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalThingML.g:1469:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1469:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1470:5: lv_annotations_3_0= rulePlatformAnnotation
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
            	    break loop26;
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
    // InternalThingML.g:1491:1: entryRulePort returns [EObject current=null] : iv_rulePort= rulePort EOF ;
    public final EObject entryRulePort() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePort = null;


        try {
            // InternalThingML.g:1491:45: (iv_rulePort= rulePort EOF )
            // InternalThingML.g:1492:2: iv_rulePort= rulePort EOF
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
    // InternalThingML.g:1498:1: rulePort returns [EObject current=null] : (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort ) ;
    public final EObject rulePort() throws RecognitionException {
        EObject current = null;

        EObject this_RequiredPort_0 = null;

        EObject this_ProvidedPort_1 = null;

        EObject this_InternalPort_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:1504:2: ( (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort ) )
            // InternalThingML.g:1505:2: (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort )
            {
            // InternalThingML.g:1505:2: (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort )
            int alt27=3;
            switch ( input.LA(1) ) {
            case 38:
            case 39:
                {
                alt27=1;
                }
                break;
            case 43:
                {
                alt27=2;
                }
                break;
            case 44:
                {
                alt27=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // InternalThingML.g:1506:3: this_RequiredPort_0= ruleRequiredPort
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
                    // InternalThingML.g:1515:3: this_ProvidedPort_1= ruleProvidedPort
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
                    // InternalThingML.g:1524:3: this_InternalPort_2= ruleInternalPort
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
    // InternalThingML.g:1536:1: entryRuleRequiredPort returns [EObject current=null] : iv_ruleRequiredPort= ruleRequiredPort EOF ;
    public final EObject entryRuleRequiredPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequiredPort = null;


        try {
            // InternalThingML.g:1536:53: (iv_ruleRequiredPort= ruleRequiredPort EOF )
            // InternalThingML.g:1537:2: iv_ruleRequiredPort= ruleRequiredPort EOF
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
    // InternalThingML.g:1543:1: ruleRequiredPort returns [EObject current=null] : ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' ) ;
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
            // InternalThingML.g:1549:2: ( ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' ) )
            // InternalThingML.g:1550:2: ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' )
            {
            // InternalThingML.g:1550:2: ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' )
            // InternalThingML.g:1551:3: ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}'
            {
            // InternalThingML.g:1551:3: ( (lv_optional_0_0= 'optional' ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==38) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalThingML.g:1552:4: (lv_optional_0_0= 'optional' )
                    {
                    // InternalThingML.g:1552:4: (lv_optional_0_0= 'optional' )
                    // InternalThingML.g:1553:5: lv_optional_0_0= 'optional'
                    {
                    lv_optional_0_0=(Token)match(input,38,FOLLOW_27); 

                    					newLeafNode(lv_optional_0_0, grammarAccess.getRequiredPortAccess().getOptionalOptionalKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getRequiredPortRule());
                    					}
                    					setWithLastConsumed(current, "optional", true, "optional");
                    				

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,39,FOLLOW_28); 

            			newLeafNode(otherlv_1, grammarAccess.getRequiredPortAccess().getRequiredKeyword_1());
            		
            otherlv_2=(Token)match(input,40,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getRequiredPortAccess().getPortKeyword_2());
            		
            // InternalThingML.g:1573:3: ( (lv_name_3_0= RULE_ID ) )
            // InternalThingML.g:1574:4: (lv_name_3_0= RULE_ID )
            {
            // InternalThingML.g:1574:4: (lv_name_3_0= RULE_ID )
            // InternalThingML.g:1575:5: lv_name_3_0= RULE_ID
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

            // InternalThingML.g:1591:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==14) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalThingML.g:1592:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1592:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1593:5: lv_annotations_4_0= rulePlatformAnnotation
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
            	    break loop29;
                }
            } while (true);

            otherlv_5=(Token)match(input,21,FOLLOW_29); 

            			newLeafNode(otherlv_5, grammarAccess.getRequiredPortAccess().getLeftCurlyBracketKeyword_5());
            		
            // InternalThingML.g:1614:3: ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )*
            loop32:
            do {
                int alt32=3;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==41) ) {
                    alt32=1;
                }
                else if ( (LA32_0==42) ) {
                    alt32=2;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalThingML.g:1615:4: (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1615:4: (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* )
            	    // InternalThingML.g:1616:5: otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )*
            	    {
            	    otherlv_6=(Token)match(input,41,FOLLOW_6); 

            	    					newLeafNode(otherlv_6, grammarAccess.getRequiredPortAccess().getSendsKeyword_6_0_0());
            	    				
            	    // InternalThingML.g:1620:5: ( (otherlv_7= RULE_ID ) )
            	    // InternalThingML.g:1621:6: (otherlv_7= RULE_ID )
            	    {
            	    // InternalThingML.g:1621:6: (otherlv_7= RULE_ID )
            	    // InternalThingML.g:1622:7: otherlv_7= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getRequiredPortRule());
            	    							}
            	    						
            	    otherlv_7=(Token)match(input,RULE_ID,FOLLOW_30); 

            	    							newLeafNode(otherlv_7, grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1633:5: (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )*
            	    loop30:
            	    do {
            	        int alt30=2;
            	        int LA30_0 = input.LA(1);

            	        if ( (LA30_0==26) ) {
            	            alt30=1;
            	        }


            	        switch (alt30) {
            	    	case 1 :
            	    	    // InternalThingML.g:1634:6: otherlv_8= ',' ( (otherlv_9= RULE_ID ) )
            	    	    {
            	    	    otherlv_8=(Token)match(input,26,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_8, grammarAccess.getRequiredPortAccess().getCommaKeyword_6_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:1638:6: ( (otherlv_9= RULE_ID ) )
            	    	    // InternalThingML.g:1639:7: (otherlv_9= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1639:7: (otherlv_9= RULE_ID )
            	    	    // InternalThingML.g:1640:8: otherlv_9= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getRequiredPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_9=(Token)match(input,RULE_ID,FOLLOW_30); 

            	    	    								newLeafNode(otherlv_9, grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_2_1_0());
            	    	    							

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
            	case 2 :
            	    // InternalThingML.g:1654:4: (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1654:4: (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* )
            	    // InternalThingML.g:1655:5: otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )*
            	    {
            	    otherlv_10=(Token)match(input,42,FOLLOW_6); 

            	    					newLeafNode(otherlv_10, grammarAccess.getRequiredPortAccess().getReceivesKeyword_6_1_0());
            	    				
            	    // InternalThingML.g:1659:5: ( (otherlv_11= RULE_ID ) )
            	    // InternalThingML.g:1660:6: (otherlv_11= RULE_ID )
            	    {
            	    // InternalThingML.g:1660:6: (otherlv_11= RULE_ID )
            	    // InternalThingML.g:1661:7: otherlv_11= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getRequiredPortRule());
            	    							}
            	    						
            	    otherlv_11=(Token)match(input,RULE_ID,FOLLOW_30); 

            	    							newLeafNode(otherlv_11, grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1672:5: (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )*
            	    loop31:
            	    do {
            	        int alt31=2;
            	        int LA31_0 = input.LA(1);

            	        if ( (LA31_0==26) ) {
            	            alt31=1;
            	        }


            	        switch (alt31) {
            	    	case 1 :
            	    	    // InternalThingML.g:1673:6: otherlv_12= ',' ( (otherlv_13= RULE_ID ) )
            	    	    {
            	    	    otherlv_12=(Token)match(input,26,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_12, grammarAccess.getRequiredPortAccess().getCommaKeyword_6_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:1677:6: ( (otherlv_13= RULE_ID ) )
            	    	    // InternalThingML.g:1678:7: (otherlv_13= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1678:7: (otherlv_13= RULE_ID )
            	    	    // InternalThingML.g:1679:8: otherlv_13= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getRequiredPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_13=(Token)match(input,RULE_ID,FOLLOW_30); 

            	    	    								newLeafNode(otherlv_13, grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop31;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);

            otherlv_14=(Token)match(input,22,FOLLOW_2); 

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
    // InternalThingML.g:1701:1: entryRuleProvidedPort returns [EObject current=null] : iv_ruleProvidedPort= ruleProvidedPort EOF ;
    public final EObject entryRuleProvidedPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProvidedPort = null;


        try {
            // InternalThingML.g:1701:53: (iv_ruleProvidedPort= ruleProvidedPort EOF )
            // InternalThingML.g:1702:2: iv_ruleProvidedPort= ruleProvidedPort EOF
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
    // InternalThingML.g:1708:1: ruleProvidedPort returns [EObject current=null] : (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) ;
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
            // InternalThingML.g:1714:2: ( (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) )
            // InternalThingML.g:1715:2: (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            {
            // InternalThingML.g:1715:2: (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            // InternalThingML.g:1716:3: otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,43,FOLLOW_28); 

            			newLeafNode(otherlv_0, grammarAccess.getProvidedPortAccess().getProvidedKeyword_0());
            		
            otherlv_1=(Token)match(input,40,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getProvidedPortAccess().getPortKeyword_1());
            		
            // InternalThingML.g:1724:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:1725:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:1725:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:1726:5: lv_name_2_0= RULE_ID
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

            // InternalThingML.g:1742:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==14) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalThingML.g:1743:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1743:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1744:5: lv_annotations_3_0= rulePlatformAnnotation
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
            	    break loop33;
                }
            } while (true);

            otherlv_4=(Token)match(input,21,FOLLOW_29); 

            			newLeafNode(otherlv_4, grammarAccess.getProvidedPortAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalThingML.g:1765:3: ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )*
            loop36:
            do {
                int alt36=3;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==41) ) {
                    alt36=1;
                }
                else if ( (LA36_0==42) ) {
                    alt36=2;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalThingML.g:1766:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1766:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    // InternalThingML.g:1767:5: otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    {
            	    otherlv_5=(Token)match(input,41,FOLLOW_6); 

            	    					newLeafNode(otherlv_5, grammarAccess.getProvidedPortAccess().getSendsKeyword_5_0_0());
            	    				
            	    // InternalThingML.g:1771:5: ( (otherlv_6= RULE_ID ) )
            	    // InternalThingML.g:1772:6: (otherlv_6= RULE_ID )
            	    {
            	    // InternalThingML.g:1772:6: (otherlv_6= RULE_ID )
            	    // InternalThingML.g:1773:7: otherlv_6= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getProvidedPortRule());
            	    							}
            	    						
            	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_30); 

            	    							newLeafNode(otherlv_6, grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1784:5: (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    loop34:
            	    do {
            	        int alt34=2;
            	        int LA34_0 = input.LA(1);

            	        if ( (LA34_0==26) ) {
            	            alt34=1;
            	        }


            	        switch (alt34) {
            	    	case 1 :
            	    	    // InternalThingML.g:1785:6: otherlv_7= ',' ( (otherlv_8= RULE_ID ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,26,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_7, grammarAccess.getProvidedPortAccess().getCommaKeyword_5_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:1789:6: ( (otherlv_8= RULE_ID ) )
            	    	    // InternalThingML.g:1790:7: (otherlv_8= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1790:7: (otherlv_8= RULE_ID )
            	    	    // InternalThingML.g:1791:8: otherlv_8= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getProvidedPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_30); 

            	    	    								newLeafNode(otherlv_8, grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_2_1_0());
            	    	    							

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
            	case 2 :
            	    // InternalThingML.g:1805:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1805:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    // InternalThingML.g:1806:5: otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    {
            	    otherlv_9=(Token)match(input,42,FOLLOW_6); 

            	    					newLeafNode(otherlv_9, grammarAccess.getProvidedPortAccess().getReceivesKeyword_5_1_0());
            	    				
            	    // InternalThingML.g:1810:5: ( (otherlv_10= RULE_ID ) )
            	    // InternalThingML.g:1811:6: (otherlv_10= RULE_ID )
            	    {
            	    // InternalThingML.g:1811:6: (otherlv_10= RULE_ID )
            	    // InternalThingML.g:1812:7: otherlv_10= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getProvidedPortRule());
            	    							}
            	    						
            	    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_30); 

            	    							newLeafNode(otherlv_10, grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1823:5: (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    loop35:
            	    do {
            	        int alt35=2;
            	        int LA35_0 = input.LA(1);

            	        if ( (LA35_0==26) ) {
            	            alt35=1;
            	        }


            	        switch (alt35) {
            	    	case 1 :
            	    	    // InternalThingML.g:1824:6: otherlv_11= ',' ( (otherlv_12= RULE_ID ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,26,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_11, grammarAccess.getProvidedPortAccess().getCommaKeyword_5_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:1828:6: ( (otherlv_12= RULE_ID ) )
            	    	    // InternalThingML.g:1829:7: (otherlv_12= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1829:7: (otherlv_12= RULE_ID )
            	    	    // InternalThingML.g:1830:8: otherlv_12= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getProvidedPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_12=(Token)match(input,RULE_ID,FOLLOW_30); 

            	    	    								newLeafNode(otherlv_12, grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop35;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);

            otherlv_13=(Token)match(input,22,FOLLOW_2); 

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
    // InternalThingML.g:1852:1: entryRuleInternalPort returns [EObject current=null] : iv_ruleInternalPort= ruleInternalPort EOF ;
    public final EObject entryRuleInternalPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInternalPort = null;


        try {
            // InternalThingML.g:1852:53: (iv_ruleInternalPort= ruleInternalPort EOF )
            // InternalThingML.g:1853:2: iv_ruleInternalPort= ruleInternalPort EOF
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
    // InternalThingML.g:1859:1: ruleInternalPort returns [EObject current=null] : (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) ;
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
            // InternalThingML.g:1865:2: ( (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) )
            // InternalThingML.g:1866:2: (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            {
            // InternalThingML.g:1866:2: (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            // InternalThingML.g:1867:3: otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,44,FOLLOW_28); 

            			newLeafNode(otherlv_0, grammarAccess.getInternalPortAccess().getInternalKeyword_0());
            		
            otherlv_1=(Token)match(input,40,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getInternalPortAccess().getPortKeyword_1());
            		
            // InternalThingML.g:1875:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:1876:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:1876:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:1877:5: lv_name_2_0= RULE_ID
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

            // InternalThingML.g:1893:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==14) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalThingML.g:1894:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1894:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1895:5: lv_annotations_3_0= rulePlatformAnnotation
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
            	    break loop37;
                }
            } while (true);

            otherlv_4=(Token)match(input,21,FOLLOW_29); 

            			newLeafNode(otherlv_4, grammarAccess.getInternalPortAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalThingML.g:1916:3: ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )*
            loop40:
            do {
                int alt40=3;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==41) ) {
                    alt40=1;
                }
                else if ( (LA40_0==42) ) {
                    alt40=2;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalThingML.g:1917:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1917:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    // InternalThingML.g:1918:5: otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    {
            	    otherlv_5=(Token)match(input,41,FOLLOW_6); 

            	    					newLeafNode(otherlv_5, grammarAccess.getInternalPortAccess().getSendsKeyword_5_0_0());
            	    				
            	    // InternalThingML.g:1922:5: ( (otherlv_6= RULE_ID ) )
            	    // InternalThingML.g:1923:6: (otherlv_6= RULE_ID )
            	    {
            	    // InternalThingML.g:1923:6: (otherlv_6= RULE_ID )
            	    // InternalThingML.g:1924:7: otherlv_6= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getInternalPortRule());
            	    							}
            	    						
            	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_30); 

            	    							newLeafNode(otherlv_6, grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1935:5: (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    loop38:
            	    do {
            	        int alt38=2;
            	        int LA38_0 = input.LA(1);

            	        if ( (LA38_0==26) ) {
            	            alt38=1;
            	        }


            	        switch (alt38) {
            	    	case 1 :
            	    	    // InternalThingML.g:1936:6: otherlv_7= ',' ( (otherlv_8= RULE_ID ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,26,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_7, grammarAccess.getInternalPortAccess().getCommaKeyword_5_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:1940:6: ( (otherlv_8= RULE_ID ) )
            	    	    // InternalThingML.g:1941:7: (otherlv_8= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1941:7: (otherlv_8= RULE_ID )
            	    	    // InternalThingML.g:1942:8: otherlv_8= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getInternalPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_30); 

            	    	    								newLeafNode(otherlv_8, grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_2_1_0());
            	    	    							

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
            	case 2 :
            	    // InternalThingML.g:1956:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1956:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    // InternalThingML.g:1957:5: otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    {
            	    otherlv_9=(Token)match(input,42,FOLLOW_6); 

            	    					newLeafNode(otherlv_9, grammarAccess.getInternalPortAccess().getReceivesKeyword_5_1_0());
            	    				
            	    // InternalThingML.g:1961:5: ( (otherlv_10= RULE_ID ) )
            	    // InternalThingML.g:1962:6: (otherlv_10= RULE_ID )
            	    {
            	    // InternalThingML.g:1962:6: (otherlv_10= RULE_ID )
            	    // InternalThingML.g:1963:7: otherlv_10= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getInternalPortRule());
            	    							}
            	    						
            	    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_30); 

            	    							newLeafNode(otherlv_10, grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1974:5: (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    loop39:
            	    do {
            	        int alt39=2;
            	        int LA39_0 = input.LA(1);

            	        if ( (LA39_0==26) ) {
            	            alt39=1;
            	        }


            	        switch (alt39) {
            	    	case 1 :
            	    	    // InternalThingML.g:1975:6: otherlv_11= ',' ( (otherlv_12= RULE_ID ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,26,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_11, grammarAccess.getInternalPortAccess().getCommaKeyword_5_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:1979:6: ( (otherlv_12= RULE_ID ) )
            	    	    // InternalThingML.g:1980:7: (otherlv_12= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1980:7: (otherlv_12= RULE_ID )
            	    	    // InternalThingML.g:1981:8: otherlv_12= RULE_ID
            	    	    {

            	    	    								if (current==null) {
            	    	    									current = createModelElement(grammarAccess.getInternalPortRule());
            	    	    								}
            	    	    							
            	    	    otherlv_12=(Token)match(input,RULE_ID,FOLLOW_30); 

            	    	    								newLeafNode(otherlv_12, grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_2_1_0());
            	    	    							

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop39;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);

            otherlv_13=(Token)match(input,22,FOLLOW_2); 

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
    // InternalThingML.g:2003:1: entryRuleStream returns [EObject current=null] : iv_ruleStream= ruleStream EOF ;
    public final EObject entryRuleStream() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStream = null;


        try {
            // InternalThingML.g:2003:47: (iv_ruleStream= ruleStream EOF )
            // InternalThingML.g:2004:2: iv_ruleStream= ruleStream EOF
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
    // InternalThingML.g:2010:1: ruleStream returns [EObject current=null] : (otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) ) ) ;
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
            // InternalThingML.g:2016:2: ( (otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) ) ) )
            // InternalThingML.g:2017:2: (otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) ) )
            {
            // InternalThingML.g:2017:2: (otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) ) )
            // InternalThingML.g:2018:3: otherlv_0= 'stream' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= 'from' ( (lv_input_4_0= ruleSource ) ) (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )? otherlv_9= 'produce' ( (lv_output_10_0= ruleSendAction ) )
            {
            otherlv_0=(Token)match(input,45,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getStreamAccess().getStreamKeyword_0());
            		
            // InternalThingML.g:2022:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:2023:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:2023:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:2024:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_31); 

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

            // InternalThingML.g:2040:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==14) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalThingML.g:2041:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2041:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2042:5: lv_annotations_2_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getStreamAccess().getAnnotationsPlatformAnnotationParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_31);
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
            	    break loop41;
                }
            } while (true);

            otherlv_3=(Token)match(input,46,FOLLOW_32); 

            			newLeafNode(otherlv_3, grammarAccess.getStreamAccess().getFromKeyword_3());
            		
            // InternalThingML.g:2063:3: ( (lv_input_4_0= ruleSource ) )
            // InternalThingML.g:2064:4: (lv_input_4_0= ruleSource )
            {
            // InternalThingML.g:2064:4: (lv_input_4_0= ruleSource )
            // InternalThingML.g:2065:5: lv_input_4_0= ruleSource
            {

            					newCompositeNode(grammarAccess.getStreamAccess().getInputSourceParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_33);
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

            // InternalThingML.g:2082:3: (otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )* )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==47) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalThingML.g:2083:4: otherlv_5= 'select' ( (lv_selection_6_0= ruleLocalVariable ) ) (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )*
                    {
                    otherlv_5=(Token)match(input,47,FOLLOW_24); 

                    				newLeafNode(otherlv_5, grammarAccess.getStreamAccess().getSelectKeyword_5_0());
                    			
                    // InternalThingML.g:2087:4: ( (lv_selection_6_0= ruleLocalVariable ) )
                    // InternalThingML.g:2088:5: (lv_selection_6_0= ruleLocalVariable )
                    {
                    // InternalThingML.g:2088:5: (lv_selection_6_0= ruleLocalVariable )
                    // InternalThingML.g:2089:6: lv_selection_6_0= ruleLocalVariable
                    {

                    						newCompositeNode(grammarAccess.getStreamAccess().getSelectionLocalVariableParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_34);
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

                    // InternalThingML.g:2106:4: (otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) ) )*
                    loop42:
                    do {
                        int alt42=2;
                        int LA42_0 = input.LA(1);

                        if ( (LA42_0==26) ) {
                            alt42=1;
                        }


                        switch (alt42) {
                    	case 1 :
                    	    // InternalThingML.g:2107:5: otherlv_7= ',' ( (lv_selection_8_0= ruleLocalVariable ) )
                    	    {
                    	    otherlv_7=(Token)match(input,26,FOLLOW_24); 

                    	    					newLeafNode(otherlv_7, grammarAccess.getStreamAccess().getCommaKeyword_5_2_0());
                    	    				
                    	    // InternalThingML.g:2111:5: ( (lv_selection_8_0= ruleLocalVariable ) )
                    	    // InternalThingML.g:2112:6: (lv_selection_8_0= ruleLocalVariable )
                    	    {
                    	    // InternalThingML.g:2112:6: (lv_selection_8_0= ruleLocalVariable )
                    	    // InternalThingML.g:2113:7: lv_selection_8_0= ruleLocalVariable
                    	    {

                    	    							newCompositeNode(grammarAccess.getStreamAccess().getSelectionLocalVariableParserRuleCall_5_2_1_0());
                    	    						
                    	    pushFollow(FOLLOW_34);
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
                    	    break loop42;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_9=(Token)match(input,48,FOLLOW_6); 

            			newLeafNode(otherlv_9, grammarAccess.getStreamAccess().getProduceKeyword_6());
            		
            // InternalThingML.g:2136:3: ( (lv_output_10_0= ruleSendAction ) )
            // InternalThingML.g:2137:4: (lv_output_10_0= ruleSendAction )
            {
            // InternalThingML.g:2137:4: (lv_output_10_0= ruleSendAction )
            // InternalThingML.g:2138:5: lv_output_10_0= ruleSendAction
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
    // InternalThingML.g:2159:1: entryRuleSource returns [EObject current=null] : iv_ruleSource= ruleSource EOF ;
    public final EObject entryRuleSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSource = null;


        try {
            // InternalThingML.g:2159:47: (iv_ruleSource= ruleSource EOF )
            // InternalThingML.g:2160:2: iv_ruleSource= ruleSource EOF
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
    // InternalThingML.g:2166:1: ruleSource returns [EObject current=null] : (this_JoinSources_0= ruleJoinSources | this_MergeSources_1= ruleMergeSources | this_SimpleSource_2= ruleSimpleSource ) ;
    public final EObject ruleSource() throws RecognitionException {
        EObject current = null;

        EObject this_JoinSources_0 = null;

        EObject this_MergeSources_1 = null;

        EObject this_SimpleSource_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:2172:2: ( (this_JoinSources_0= ruleJoinSources | this_MergeSources_1= ruleMergeSources | this_SimpleSource_2= ruleSimpleSource ) )
            // InternalThingML.g:2173:2: (this_JoinSources_0= ruleJoinSources | this_MergeSources_1= ruleMergeSources | this_SimpleSource_2= ruleSimpleSource )
            {
            // InternalThingML.g:2173:2: (this_JoinSources_0= ruleJoinSources | this_MergeSources_1= ruleMergeSources | this_SimpleSource_2= ruleSimpleSource )
            int alt44=3;
            switch ( input.LA(1) ) {
            case 49:
                {
                alt44=1;
                }
                break;
            case 53:
                {
                alt44=2;
                }
                break;
            case RULE_ID:
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
                    // InternalThingML.g:2174:3: this_JoinSources_0= ruleJoinSources
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
                    // InternalThingML.g:2183:3: this_MergeSources_1= ruleMergeSources
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
                    // InternalThingML.g:2192:3: this_SimpleSource_2= ruleSimpleSource
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
    // InternalThingML.g:2204:1: entryRuleViewSource returns [EObject current=null] : iv_ruleViewSource= ruleViewSource EOF ;
    public final EObject entryRuleViewSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleViewSource = null;


        try {
            // InternalThingML.g:2204:51: (iv_ruleViewSource= ruleViewSource EOF )
            // InternalThingML.g:2205:2: iv_ruleViewSource= ruleViewSource EOF
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
    // InternalThingML.g:2211:1: ruleViewSource returns [EObject current=null] : (this_Filter_0= ruleFilter | this_LengthWindow_1= ruleLengthWindow | this_TimeWindow_2= ruleTimeWindow ) ;
    public final EObject ruleViewSource() throws RecognitionException {
        EObject current = null;

        EObject this_Filter_0 = null;

        EObject this_LengthWindow_1 = null;

        EObject this_TimeWindow_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:2217:2: ( (this_Filter_0= ruleFilter | this_LengthWindow_1= ruleLengthWindow | this_TimeWindow_2= ruleTimeWindow ) )
            // InternalThingML.g:2218:2: (this_Filter_0= ruleFilter | this_LengthWindow_1= ruleLengthWindow | this_TimeWindow_2= ruleTimeWindow )
            {
            // InternalThingML.g:2218:2: (this_Filter_0= ruleFilter | this_LengthWindow_1= ruleLengthWindow | this_TimeWindow_2= ruleTimeWindow )
            int alt45=3;
            switch ( input.LA(1) ) {
            case 55:
                {
                alt45=1;
                }
                break;
            case 57:
                {
                alt45=2;
                }
                break;
            case 59:
                {
                alt45=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }

            switch (alt45) {
                case 1 :
                    // InternalThingML.g:2219:3: this_Filter_0= ruleFilter
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
                    // InternalThingML.g:2228:3: this_LengthWindow_1= ruleLengthWindow
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
                    // InternalThingML.g:2237:3: this_TimeWindow_2= ruleTimeWindow
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
    // InternalThingML.g:2249:1: entryRuleElmtProperty returns [EObject current=null] : iv_ruleElmtProperty= ruleElmtProperty EOF ;
    public final EObject entryRuleElmtProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleElmtProperty = null;


        try {
            // InternalThingML.g:2249:53: (iv_ruleElmtProperty= ruleElmtProperty EOF )
            // InternalThingML.g:2250:2: iv_ruleElmtProperty= ruleElmtProperty EOF
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
    // InternalThingML.g:2256:1: ruleElmtProperty returns [EObject current=null] : (this_SimpleParamRef_0= ruleSimpleParamRef | this_ArrayParamRef_1= ruleArrayParamRef | this_LengthArray_2= ruleLengthArray ) ;
    public final EObject ruleElmtProperty() throws RecognitionException {
        EObject current = null;

        EObject this_SimpleParamRef_0 = null;

        EObject this_ArrayParamRef_1 = null;

        EObject this_LengthArray_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:2262:2: ( (this_SimpleParamRef_0= ruleSimpleParamRef | this_ArrayParamRef_1= ruleArrayParamRef | this_LengthArray_2= ruleLengthArray ) )
            // InternalThingML.g:2263:2: (this_SimpleParamRef_0= ruleSimpleParamRef | this_ArrayParamRef_1= ruleArrayParamRef | this_LengthArray_2= ruleLengthArray )
            {
            // InternalThingML.g:2263:2: (this_SimpleParamRef_0= ruleSimpleParamRef | this_ArrayParamRef_1= ruleArrayParamRef | this_LengthArray_2= ruleLengthArray )
            int alt46=3;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==RULE_ID) ) {
                int LA46_1 = input.LA(2);

                if ( (LA46_1==28) ) {
                    alt46=2;
                }
                else if ( (LA46_1==EOF||LA46_1==RULE_ID||LA46_1==RULE_STRING_EXT||LA46_1==14||(LA46_1>=16 && LA46_1<=17)||LA46_1==22||(LA46_1>=26 && LA46_1<=27)||LA46_1==29||LA46_1==32||LA46_1==34||(LA46_1>=36 && LA46_1<=39)||(LA46_1>=43 && LA46_1<=45)||(LA46_1>=47 && LA46_1<=48)||(LA46_1>=50 && LA46_1<=52)||LA46_1==54||LA46_1==56||LA46_1==58||LA46_1==61||LA46_1==65||(LA46_1>=68 && LA46_1<=73)||LA46_1==76||(LA46_1>=78 && LA46_1<=81)||(LA46_1>=85 && LA46_1<=101)||(LA46_1>=108 && LA46_1<=109)) ) {
                    alt46=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 46, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA46_0==60) ) {
                alt46=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }
            switch (alt46) {
                case 1 :
                    // InternalThingML.g:2264:3: this_SimpleParamRef_0= ruleSimpleParamRef
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
                    // InternalThingML.g:2273:3: this_ArrayParamRef_1= ruleArrayParamRef
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
                    // InternalThingML.g:2282:3: this_LengthArray_2= ruleLengthArray
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
    // InternalThingML.g:2294:1: entryRuleJoinSources returns [EObject current=null] : iv_ruleJoinSources= ruleJoinSources EOF ;
    public final EObject entryRuleJoinSources() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJoinSources = null;


        try {
            // InternalThingML.g:2294:52: (iv_ruleJoinSources= ruleJoinSources EOF )
            // InternalThingML.g:2295:2: iv_ruleJoinSources= ruleJoinSources EOF
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
    // InternalThingML.g:2301:1: ruleJoinSources returns [EObject current=null] : (otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )* ) ;
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
            // InternalThingML.g:2307:2: ( (otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )* ) )
            // InternalThingML.g:2308:2: (otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )* )
            {
            // InternalThingML.g:2308:2: (otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )* )
            // InternalThingML.g:2309:3: otherlv_0= 'join' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= '(' ( (lv_rules_10_0= ruleExpression ) ) (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )* otherlv_13= ')' otherlv_14= ']' (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )*
            {
            otherlv_0=(Token)match(input,49,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getJoinSourcesAccess().getJoinKeyword_0());
            		
            // InternalThingML.g:2313:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:2314:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:2314:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:2315:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_25); 

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

            otherlv_2=(Token)match(input,35,FOLLOW_35); 

            			newLeafNode(otherlv_2, grammarAccess.getJoinSourcesAccess().getColonKeyword_2());
            		
            otherlv_3=(Token)match(input,28,FOLLOW_32); 

            			newLeafNode(otherlv_3, grammarAccess.getJoinSourcesAccess().getLeftSquareBracketKeyword_3());
            		
            // InternalThingML.g:2339:3: ( (lv_sources_4_0= ruleSource ) )
            // InternalThingML.g:2340:4: (lv_sources_4_0= ruleSource )
            {
            // InternalThingML.g:2340:4: (lv_sources_4_0= ruleSource )
            // InternalThingML.g:2341:5: lv_sources_4_0= ruleSource
            {

            					newCompositeNode(grammarAccess.getJoinSourcesAccess().getSourcesSourceParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_36);
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

            // InternalThingML.g:2358:3: (otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) ) )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==50) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalThingML.g:2359:4: otherlv_5= '&' ( (lv_sources_6_0= ruleSource ) )
            	    {
            	    otherlv_5=(Token)match(input,50,FOLLOW_32); 

            	    				newLeafNode(otherlv_5, grammarAccess.getJoinSourcesAccess().getAmpersandKeyword_5_0());
            	    			
            	    // InternalThingML.g:2363:4: ( (lv_sources_6_0= ruleSource ) )
            	    // InternalThingML.g:2364:5: (lv_sources_6_0= ruleSource )
            	    {
            	    // InternalThingML.g:2364:5: (lv_sources_6_0= ruleSource )
            	    // InternalThingML.g:2365:6: lv_sources_6_0= ruleSource
            	    {

            	    						newCompositeNode(grammarAccess.getJoinSourcesAccess().getSourcesSourceParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_36);
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
            	    break loop47;
                }
            } while (true);

            otherlv_7=(Token)match(input,51,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getJoinSourcesAccess().getHyphenMinusGreaterThanSignKeyword_6());
            		
            // InternalThingML.g:2387:3: ( (otherlv_8= RULE_ID ) )
            // InternalThingML.g:2388:4: (otherlv_8= RULE_ID )
            {
            // InternalThingML.g:2388:4: (otherlv_8= RULE_ID )
            // InternalThingML.g:2389:5: otherlv_8= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getJoinSourcesRule());
            					}
            				
            otherlv_8=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_8, grammarAccess.getJoinSourcesAccess().getResultMessageMessageCrossReference_7_0());
            				

            }


            }

            otherlv_9=(Token)match(input,33,FOLLOW_19); 

            			newLeafNode(otherlv_9, grammarAccess.getJoinSourcesAccess().getLeftParenthesisKeyword_8());
            		
            // InternalThingML.g:2404:3: ( (lv_rules_10_0= ruleExpression ) )
            // InternalThingML.g:2405:4: (lv_rules_10_0= ruleExpression )
            {
            // InternalThingML.g:2405:4: (lv_rules_10_0= ruleExpression )
            // InternalThingML.g:2406:5: lv_rules_10_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getJoinSourcesAccess().getRulesExpressionParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_23);
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

            // InternalThingML.g:2423:3: (otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==26) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalThingML.g:2424:4: otherlv_11= ',' ( (lv_rules_12_0= ruleExpression ) )
            	    {
            	    otherlv_11=(Token)match(input,26,FOLLOW_19); 

            	    				newLeafNode(otherlv_11, grammarAccess.getJoinSourcesAccess().getCommaKeyword_10_0());
            	    			
            	    // InternalThingML.g:2428:4: ( (lv_rules_12_0= ruleExpression ) )
            	    // InternalThingML.g:2429:5: (lv_rules_12_0= ruleExpression )
            	    {
            	    // InternalThingML.g:2429:5: (lv_rules_12_0= ruleExpression )
            	    // InternalThingML.g:2430:6: lv_rules_12_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getJoinSourcesAccess().getRulesExpressionParserRuleCall_10_1_0());
            	    					
            	    pushFollow(FOLLOW_23);
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
            	    break loop48;
                }
            } while (true);

            otherlv_13=(Token)match(input,34,FOLLOW_20); 

            			newLeafNode(otherlv_13, grammarAccess.getJoinSourcesAccess().getRightParenthesisKeyword_11());
            		
            otherlv_14=(Token)match(input,29,FOLLOW_37); 

            			newLeafNode(otherlv_14, grammarAccess.getJoinSourcesAccess().getRightSquareBracketKeyword_12());
            		
            // InternalThingML.g:2456:3: (otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==52) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalThingML.g:2457:4: otherlv_15= '::' ( (lv_operators_16_0= ruleViewSource ) )
            	    {
            	    otherlv_15=(Token)match(input,52,FOLLOW_38); 

            	    				newLeafNode(otherlv_15, grammarAccess.getJoinSourcesAccess().getColonColonKeyword_13_0());
            	    			
            	    // InternalThingML.g:2461:4: ( (lv_operators_16_0= ruleViewSource ) )
            	    // InternalThingML.g:2462:5: (lv_operators_16_0= ruleViewSource )
            	    {
            	    // InternalThingML.g:2462:5: (lv_operators_16_0= ruleViewSource )
            	    // InternalThingML.g:2463:6: lv_operators_16_0= ruleViewSource
            	    {

            	    						newCompositeNode(grammarAccess.getJoinSourcesAccess().getOperatorsViewSourceParserRuleCall_13_1_0());
            	    					
            	    pushFollow(FOLLOW_37);
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
    // $ANTLR end "ruleJoinSources"


    // $ANTLR start "entryRuleMergeSources"
    // InternalThingML.g:2485:1: entryRuleMergeSources returns [EObject current=null] : iv_ruleMergeSources= ruleMergeSources EOF ;
    public final EObject entryRuleMergeSources() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMergeSources = null;


        try {
            // InternalThingML.g:2485:53: (iv_ruleMergeSources= ruleMergeSources EOF )
            // InternalThingML.g:2486:2: iv_ruleMergeSources= ruleMergeSources EOF
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
    // InternalThingML.g:2492:1: ruleMergeSources returns [EObject current=null] : (otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )* ) ;
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
            // InternalThingML.g:2498:2: ( (otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )* ) )
            // InternalThingML.g:2499:2: (otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )* )
            {
            // InternalThingML.g:2499:2: (otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )* )
            // InternalThingML.g:2500:3: otherlv_0= 'merge' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' otherlv_3= '[' ( (lv_sources_4_0= ruleSource ) ) (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )* otherlv_7= '->' ( (otherlv_8= RULE_ID ) ) otherlv_9= ']' (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )*
            {
            otherlv_0=(Token)match(input,53,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getMergeSourcesAccess().getMergeKeyword_0());
            		
            // InternalThingML.g:2504:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:2505:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:2505:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:2506:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_25); 

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

            otherlv_2=(Token)match(input,35,FOLLOW_35); 

            			newLeafNode(otherlv_2, grammarAccess.getMergeSourcesAccess().getColonKeyword_2());
            		
            otherlv_3=(Token)match(input,28,FOLLOW_32); 

            			newLeafNode(otherlv_3, grammarAccess.getMergeSourcesAccess().getLeftSquareBracketKeyword_3());
            		
            // InternalThingML.g:2530:3: ( (lv_sources_4_0= ruleSource ) )
            // InternalThingML.g:2531:4: (lv_sources_4_0= ruleSource )
            {
            // InternalThingML.g:2531:4: (lv_sources_4_0= ruleSource )
            // InternalThingML.g:2532:5: lv_sources_4_0= ruleSource
            {

            					newCompositeNode(grammarAccess.getMergeSourcesAccess().getSourcesSourceParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_39);
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

            // InternalThingML.g:2549:3: (otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==54) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalThingML.g:2550:4: otherlv_5= '|' ( (lv_sources_6_0= ruleSource ) )
            	    {
            	    otherlv_5=(Token)match(input,54,FOLLOW_32); 

            	    				newLeafNode(otherlv_5, grammarAccess.getMergeSourcesAccess().getVerticalLineKeyword_5_0());
            	    			
            	    // InternalThingML.g:2554:4: ( (lv_sources_6_0= ruleSource ) )
            	    // InternalThingML.g:2555:5: (lv_sources_6_0= ruleSource )
            	    {
            	    // InternalThingML.g:2555:5: (lv_sources_6_0= ruleSource )
            	    // InternalThingML.g:2556:6: lv_sources_6_0= ruleSource
            	    {

            	    						newCompositeNode(grammarAccess.getMergeSourcesAccess().getSourcesSourceParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_39);
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
            	    break loop50;
                }
            } while (true);

            otherlv_7=(Token)match(input,51,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getMergeSourcesAccess().getHyphenMinusGreaterThanSignKeyword_6());
            		
            // InternalThingML.g:2578:3: ( (otherlv_8= RULE_ID ) )
            // InternalThingML.g:2579:4: (otherlv_8= RULE_ID )
            {
            // InternalThingML.g:2579:4: (otherlv_8= RULE_ID )
            // InternalThingML.g:2580:5: otherlv_8= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMergeSourcesRule());
            					}
            				
            otherlv_8=(Token)match(input,RULE_ID,FOLLOW_20); 

            					newLeafNode(otherlv_8, grammarAccess.getMergeSourcesAccess().getResultMessageMessageCrossReference_7_0());
            				

            }


            }

            otherlv_9=(Token)match(input,29,FOLLOW_37); 

            			newLeafNode(otherlv_9, grammarAccess.getMergeSourcesAccess().getRightSquareBracketKeyword_8());
            		
            // InternalThingML.g:2595:3: (otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==52) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalThingML.g:2596:4: otherlv_10= '::' ( (lv_operators_11_0= ruleViewSource ) )
            	    {
            	    otherlv_10=(Token)match(input,52,FOLLOW_38); 

            	    				newLeafNode(otherlv_10, grammarAccess.getMergeSourcesAccess().getColonColonKeyword_9_0());
            	    			
            	    // InternalThingML.g:2600:4: ( (lv_operators_11_0= ruleViewSource ) )
            	    // InternalThingML.g:2601:5: (lv_operators_11_0= ruleViewSource )
            	    {
            	    // InternalThingML.g:2601:5: (lv_operators_11_0= ruleViewSource )
            	    // InternalThingML.g:2602:6: lv_operators_11_0= ruleViewSource
            	    {

            	    						newCompositeNode(grammarAccess.getMergeSourcesAccess().getOperatorsViewSourceParserRuleCall_9_1_0());
            	    					
            	    pushFollow(FOLLOW_37);
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
            	    break loop51;
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
    // InternalThingML.g:2624:1: entryRuleSimpleSource returns [EObject current=null] : iv_ruleSimpleSource= ruleSimpleSource EOF ;
    public final EObject entryRuleSimpleSource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleSource = null;


        try {
            // InternalThingML.g:2624:53: (iv_ruleSimpleSource= ruleSimpleSource EOF )
            // InternalThingML.g:2625:2: iv_ruleSimpleSource= ruleSimpleSource EOF
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
    // InternalThingML.g:2631:1: ruleSimpleSource returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )* ) ;
    public final EObject ruleSimpleSource() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_message_2_0 = null;

        EObject lv_operators_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2637:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )* ) )
            // InternalThingML.g:2638:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )* )
            {
            // InternalThingML.g:2638:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )* )
            // InternalThingML.g:2639:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_message_2_0= ruleReceiveMessage ) ) (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )*
            {
            // InternalThingML.g:2639:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:2640:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:2640:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:2641:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_25); 

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
            		
            // InternalThingML.g:2661:3: ( (lv_message_2_0= ruleReceiveMessage ) )
            // InternalThingML.g:2662:4: (lv_message_2_0= ruleReceiveMessage )
            {
            // InternalThingML.g:2662:4: (lv_message_2_0= ruleReceiveMessage )
            // InternalThingML.g:2663:5: lv_message_2_0= ruleReceiveMessage
            {

            					newCompositeNode(grammarAccess.getSimpleSourceAccess().getMessageReceiveMessageParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_37);
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

            // InternalThingML.g:2680:3: (otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) ) )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==52) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalThingML.g:2681:4: otherlv_3= '::' ( (lv_operators_4_0= ruleViewSource ) )
            	    {
            	    otherlv_3=(Token)match(input,52,FOLLOW_38); 

            	    				newLeafNode(otherlv_3, grammarAccess.getSimpleSourceAccess().getColonColonKeyword_3_0());
            	    			
            	    // InternalThingML.g:2685:4: ( (lv_operators_4_0= ruleViewSource ) )
            	    // InternalThingML.g:2686:5: (lv_operators_4_0= ruleViewSource )
            	    {
            	    // InternalThingML.g:2686:5: (lv_operators_4_0= ruleViewSource )
            	    // InternalThingML.g:2687:6: lv_operators_4_0= ruleViewSource
            	    {

            	    						newCompositeNode(grammarAccess.getSimpleSourceAccess().getOperatorsViewSourceParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_37);
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
            	    break loop52;
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
    // InternalThingML.g:2709:1: entryRuleFilter returns [EObject current=null] : iv_ruleFilter= ruleFilter EOF ;
    public final EObject entryRuleFilter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFilter = null;


        try {
            // InternalThingML.g:2709:47: (iv_ruleFilter= ruleFilter EOF )
            // InternalThingML.g:2710:2: iv_ruleFilter= ruleFilter EOF
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
    // InternalThingML.g:2716:1: ruleFilter returns [EObject current=null] : (otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) ) ) ;
    public final EObject ruleFilter() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_guard_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2722:2: ( (otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) ) ) )
            // InternalThingML.g:2723:2: (otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) ) )
            {
            // InternalThingML.g:2723:2: (otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) ) )
            // InternalThingML.g:2724:3: otherlv_0= 'keep' otherlv_1= 'if' ( (lv_guard_2_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,55,FOLLOW_40); 

            			newLeafNode(otherlv_0, grammarAccess.getFilterAccess().getKeepKeyword_0());
            		
            otherlv_1=(Token)match(input,56,FOLLOW_19); 

            			newLeafNode(otherlv_1, grammarAccess.getFilterAccess().getIfKeyword_1());
            		
            // InternalThingML.g:2732:3: ( (lv_guard_2_0= ruleExpression ) )
            // InternalThingML.g:2733:4: (lv_guard_2_0= ruleExpression )
            {
            // InternalThingML.g:2733:4: (lv_guard_2_0= ruleExpression )
            // InternalThingML.g:2734:5: lv_guard_2_0= ruleExpression
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
    // InternalThingML.g:2755:1: entryRuleLengthWindow returns [EObject current=null] : iv_ruleLengthWindow= ruleLengthWindow EOF ;
    public final EObject entryRuleLengthWindow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLengthWindow = null;


        try {
            // InternalThingML.g:2755:53: (iv_ruleLengthWindow= ruleLengthWindow EOF )
            // InternalThingML.g:2756:2: iv_ruleLengthWindow= ruleLengthWindow EOF
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
    // InternalThingML.g:2762:1: ruleLengthWindow returns [EObject current=null] : (otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? ) ;
    public final EObject ruleLengthWindow() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_size_1_0 = null;

        EObject lv_step_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2768:2: ( (otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? ) )
            // InternalThingML.g:2769:2: (otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? )
            {
            // InternalThingML.g:2769:2: (otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? )
            // InternalThingML.g:2770:3: otherlv_0= 'buffer' ( (lv_size_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )?
            {
            otherlv_0=(Token)match(input,57,FOLLOW_19); 

            			newLeafNode(otherlv_0, grammarAccess.getLengthWindowAccess().getBufferKeyword_0());
            		
            // InternalThingML.g:2774:3: ( (lv_size_1_0= ruleExpression ) )
            // InternalThingML.g:2775:4: (lv_size_1_0= ruleExpression )
            {
            // InternalThingML.g:2775:4: (lv_size_1_0= ruleExpression )
            // InternalThingML.g:2776:5: lv_size_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getLengthWindowAccess().getSizeExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_41);
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

            // InternalThingML.g:2793:3: (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==58) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalThingML.g:2794:4: otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) )
                    {
                    otherlv_2=(Token)match(input,58,FOLLOW_19); 

                    				newLeafNode(otherlv_2, grammarAccess.getLengthWindowAccess().getByKeyword_2_0());
                    			
                    // InternalThingML.g:2798:4: ( (lv_step_3_0= ruleExpression ) )
                    // InternalThingML.g:2799:5: (lv_step_3_0= ruleExpression )
                    {
                    // InternalThingML.g:2799:5: (lv_step_3_0= ruleExpression )
                    // InternalThingML.g:2800:6: lv_step_3_0= ruleExpression
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
    // InternalThingML.g:2822:1: entryRuleTimeWindow returns [EObject current=null] : iv_ruleTimeWindow= ruleTimeWindow EOF ;
    public final EObject entryRuleTimeWindow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTimeWindow = null;


        try {
            // InternalThingML.g:2822:51: (iv_ruleTimeWindow= ruleTimeWindow EOF )
            // InternalThingML.g:2823:2: iv_ruleTimeWindow= ruleTimeWindow EOF
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
    // InternalThingML.g:2829:1: ruleTimeWindow returns [EObject current=null] : (otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? ) ;
    public final EObject ruleTimeWindow() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_duration_1_0 = null;

        EObject lv_step_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:2835:2: ( (otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? ) )
            // InternalThingML.g:2836:2: (otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? )
            {
            // InternalThingML.g:2836:2: (otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )? )
            // InternalThingML.g:2837:3: otherlv_0= 'during' ( (lv_duration_1_0= ruleExpression ) ) (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )?
            {
            otherlv_0=(Token)match(input,59,FOLLOW_19); 

            			newLeafNode(otherlv_0, grammarAccess.getTimeWindowAccess().getDuringKeyword_0());
            		
            // InternalThingML.g:2841:3: ( (lv_duration_1_0= ruleExpression ) )
            // InternalThingML.g:2842:4: (lv_duration_1_0= ruleExpression )
            {
            // InternalThingML.g:2842:4: (lv_duration_1_0= ruleExpression )
            // InternalThingML.g:2843:5: lv_duration_1_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getTimeWindowAccess().getDurationExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_41);
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

            // InternalThingML.g:2860:3: (otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==58) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // InternalThingML.g:2861:4: otherlv_2= 'by' ( (lv_step_3_0= ruleExpression ) )
                    {
                    otherlv_2=(Token)match(input,58,FOLLOW_19); 

                    				newLeafNode(otherlv_2, grammarAccess.getTimeWindowAccess().getByKeyword_2_0());
                    			
                    // InternalThingML.g:2865:4: ( (lv_step_3_0= ruleExpression ) )
                    // InternalThingML.g:2866:5: (lv_step_3_0= ruleExpression )
                    {
                    // InternalThingML.g:2866:5: (lv_step_3_0= ruleExpression )
                    // InternalThingML.g:2867:6: lv_step_3_0= ruleExpression
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
    // InternalThingML.g:2889:1: entryRuleMessageParameter returns [EObject current=null] : iv_ruleMessageParameter= ruleMessageParameter EOF ;
    public final EObject entryRuleMessageParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageParameter = null;


        try {
            // InternalThingML.g:2889:57: (iv_ruleMessageParameter= ruleMessageParameter EOF )
            // InternalThingML.g:2890:2: iv_ruleMessageParameter= ruleMessageParameter EOF
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
    // InternalThingML.g:2896:1: ruleMessageParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleMessageParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:2902:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalThingML.g:2903:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalThingML.g:2903:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) )
            // InternalThingML.g:2904:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) )
            {
            // InternalThingML.g:2904:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:2905:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:2905:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:2906:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_25); 

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
            		
            // InternalThingML.g:2926:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:2927:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:2927:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:2928:5: otherlv_2= RULE_ID
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
    // InternalThingML.g:2943:1: entryRuleSimpleParamRef returns [EObject current=null] : iv_ruleSimpleParamRef= ruleSimpleParamRef EOF ;
    public final EObject entryRuleSimpleParamRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSimpleParamRef = null;


        try {
            // InternalThingML.g:2943:55: (iv_ruleSimpleParamRef= ruleSimpleParamRef EOF )
            // InternalThingML.g:2944:2: iv_ruleSimpleParamRef= ruleSimpleParamRef EOF
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
    // InternalThingML.g:2950:1: ruleSimpleParamRef returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleSimpleParamRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalThingML.g:2956:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalThingML.g:2957:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalThingML.g:2957:2: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:2958:3: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:2958:3: (otherlv_0= RULE_ID )
            // InternalThingML.g:2959:4: otherlv_0= RULE_ID
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
    // InternalThingML.g:2973:1: entryRuleArrayParamRef returns [EObject current=null] : iv_ruleArrayParamRef= ruleArrayParamRef EOF ;
    public final EObject entryRuleArrayParamRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayParamRef = null;


        try {
            // InternalThingML.g:2973:54: (iv_ruleArrayParamRef= ruleArrayParamRef EOF )
            // InternalThingML.g:2974:2: iv_ruleArrayParamRef= ruleArrayParamRef EOF
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
    // InternalThingML.g:2980:1: ruleArrayParamRef returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']' ) ;
    public final EObject ruleArrayParamRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:2986:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']' ) )
            // InternalThingML.g:2987:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']' )
            {
            // InternalThingML.g:2987:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']' )
            // InternalThingML.g:2988:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '[' otherlv_2= ']'
            {
            // InternalThingML.g:2988:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:2989:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:2989:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:2990:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getArrayParamRefRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_35); 

            					newLeafNode(otherlv_0, grammarAccess.getArrayParamRefAccess().getParameterRefParameterCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,28,FOLLOW_20); 

            			newLeafNode(otherlv_1, grammarAccess.getArrayParamRefAccess().getLeftSquareBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,29,FOLLOW_2); 

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
    // InternalThingML.g:3013:1: entryRuleLengthArray returns [EObject current=null] : iv_ruleLengthArray= ruleLengthArray EOF ;
    public final EObject entryRuleLengthArray() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLengthArray = null;


        try {
            // InternalThingML.g:3013:52: (iv_ruleLengthArray= ruleLengthArray EOF )
            // InternalThingML.g:3014:2: iv_ruleLengthArray= ruleLengthArray EOF
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
    // InternalThingML.g:3020:1: ruleLengthArray returns [EObject current=null] : ( () otherlv_1= 'length' ) ;
    public final EObject ruleLengthArray() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:3026:2: ( ( () otherlv_1= 'length' ) )
            // InternalThingML.g:3027:2: ( () otherlv_1= 'length' )
            {
            // InternalThingML.g:3027:2: ( () otherlv_1= 'length' )
            // InternalThingML.g:3028:3: () otherlv_1= 'length'
            {
            // InternalThingML.g:3028:3: ()
            // InternalThingML.g:3029:4: 
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
    // InternalThingML.g:3043:1: entryRuleStateMachine returns [EObject current=null] : iv_ruleStateMachine= ruleStateMachine EOF ;
    public final EObject entryRuleStateMachine() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStateMachine = null;


        try {
            // InternalThingML.g:3043:53: (iv_ruleStateMachine= ruleStateMachine EOF )
            // InternalThingML.g:3044:2: iv_ruleStateMachine= ruleStateMachine EOF
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
    // InternalThingML.g:3050:1: ruleStateMachine returns [EObject current=null] : (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' ) ;
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
            // InternalThingML.g:3056:2: ( (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' ) )
            // InternalThingML.g:3057:2: (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' )
            {
            // InternalThingML.g:3057:2: (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' )
            // InternalThingML.g:3058:3: otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}'
            {
            otherlv_0=(Token)match(input,61,FOLLOW_42); 

            			newLeafNode(otherlv_0, grammarAccess.getStateMachineAccess().getStatechartKeyword_0());
            		
            // InternalThingML.g:3062:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==RULE_ID) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalThingML.g:3063:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:3063:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:3064:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_43); 

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
            		
            // InternalThingML.g:3084:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:3085:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:3085:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:3086:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStateMachineRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_44); 

            					newLeafNode(otherlv_3, grammarAccess.getStateMachineAccess().getInitialStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:3097:3: (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==63) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // InternalThingML.g:3098:4: otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) )
                    {
                    otherlv_4=(Token)match(input,63,FOLLOW_45); 

                    				newLeafNode(otherlv_4, grammarAccess.getStateMachineAccess().getKeepsKeyword_4_0());
                    			
                    // InternalThingML.g:3102:4: ( (lv_history_5_0= 'history' ) )
                    // InternalThingML.g:3103:5: (lv_history_5_0= 'history' )
                    {
                    // InternalThingML.g:3103:5: (lv_history_5_0= 'history' )
                    // InternalThingML.g:3104:6: lv_history_5_0= 'history'
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

            // InternalThingML.g:3117:3: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==14) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // InternalThingML.g:3118:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3118:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3119:5: lv_annotations_6_0= rulePlatformAnnotation
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
            	    break loop57;
                }
            } while (true);

            otherlv_7=(Token)match(input,21,FOLLOW_46); 

            			newLeafNode(otherlv_7, grammarAccess.getStateMachineAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:3140:3: (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==65) ) {
                int LA58_1 = input.LA(2);

                if ( (LA58_1==66) ) {
                    alt58=1;
                }
            }
            switch (alt58) {
                case 1 :
                    // InternalThingML.g:3141:4: otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) )
                    {
                    otherlv_8=(Token)match(input,65,FOLLOW_47); 

                    				newLeafNode(otherlv_8, grammarAccess.getStateMachineAccess().getOnKeyword_7_0());
                    			
                    otherlv_9=(Token)match(input,66,FOLLOW_24); 

                    				newLeafNode(otherlv_9, grammarAccess.getStateMachineAccess().getEntryKeyword_7_1());
                    			
                    // InternalThingML.g:3149:4: ( (lv_entry_10_0= ruleAction ) )
                    // InternalThingML.g:3150:5: (lv_entry_10_0= ruleAction )
                    {
                    // InternalThingML.g:3150:5: (lv_entry_10_0= ruleAction )
                    // InternalThingML.g:3151:6: lv_entry_10_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getStateMachineAccess().getEntryActionParserRuleCall_7_2_0());
                    					
                    pushFollow(FOLLOW_46);
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

            // InternalThingML.g:3169:3: (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==65) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalThingML.g:3170:4: otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) )
                    {
                    otherlv_11=(Token)match(input,65,FOLLOW_48); 

                    				newLeafNode(otherlv_11, grammarAccess.getStateMachineAccess().getOnKeyword_8_0());
                    			
                    otherlv_12=(Token)match(input,67,FOLLOW_24); 

                    				newLeafNode(otherlv_12, grammarAccess.getStateMachineAccess().getExitKeyword_8_1());
                    			
                    // InternalThingML.g:3178:4: ( (lv_exit_13_0= ruleAction ) )
                    // InternalThingML.g:3179:5: (lv_exit_13_0= ruleAction )
                    {
                    // InternalThingML.g:3179:5: (lv_exit_13_0= ruleAction )
                    // InternalThingML.g:3180:6: lv_exit_13_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getStateMachineAccess().getExitActionParserRuleCall_8_2_0());
                    					
                    pushFollow(FOLLOW_49);
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

            // InternalThingML.g:3198:3: ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )*
            loop60:
            do {
                int alt60=4;
                switch ( input.LA(1) ) {
                case 36:
                    {
                    alt60=1;
                    }
                    break;
                case 61:
                case 68:
                case 69:
                case 70:
                case 71:
                    {
                    alt60=2;
                    }
                    break;
                case 44:
                    {
                    alt60=3;
                    }
                    break;

                }

                switch (alt60) {
            	case 1 :
            	    // InternalThingML.g:3199:4: ( (lv_properties_14_0= ruleProperty ) )
            	    {
            	    // InternalThingML.g:3199:4: ( (lv_properties_14_0= ruleProperty ) )
            	    // InternalThingML.g:3200:5: (lv_properties_14_0= ruleProperty )
            	    {
            	    // InternalThingML.g:3200:5: (lv_properties_14_0= ruleProperty )
            	    // InternalThingML.g:3201:6: lv_properties_14_0= ruleProperty
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getPropertiesPropertyParserRuleCall_9_0_0());
            	    					
            	    pushFollow(FOLLOW_49);
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
            	    // InternalThingML.g:3219:4: ( (lv_substate_15_0= ruleState ) )
            	    {
            	    // InternalThingML.g:3219:4: ( (lv_substate_15_0= ruleState ) )
            	    // InternalThingML.g:3220:5: (lv_substate_15_0= ruleState )
            	    {
            	    // InternalThingML.g:3220:5: (lv_substate_15_0= ruleState )
            	    // InternalThingML.g:3221:6: lv_substate_15_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getSubstateStateParserRuleCall_9_1_0());
            	    					
            	    pushFollow(FOLLOW_49);
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
            	    // InternalThingML.g:3239:4: ( (lv_internal_16_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:3239:4: ( (lv_internal_16_0= ruleInternalTransition ) )
            	    // InternalThingML.g:3240:5: (lv_internal_16_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:3240:5: (lv_internal_16_0= ruleInternalTransition )
            	    // InternalThingML.g:3241:6: lv_internal_16_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getInternalInternalTransitionParserRuleCall_9_2_0());
            	    					
            	    pushFollow(FOLLOW_49);
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
            	    break loop60;
                }
            } while (true);

            // InternalThingML.g:3259:3: ( (lv_region_17_0= ruleParallelRegion ) )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==72) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // InternalThingML.g:3260:4: (lv_region_17_0= ruleParallelRegion )
            	    {
            	    // InternalThingML.g:3260:4: (lv_region_17_0= ruleParallelRegion )
            	    // InternalThingML.g:3261:5: lv_region_17_0= ruleParallelRegion
            	    {

            	    					newCompositeNode(grammarAccess.getStateMachineAccess().getRegionParallelRegionParserRuleCall_10_0());
            	    				
            	    pushFollow(FOLLOW_50);
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
            	    break loop61;
                }
            } while (true);

            otherlv_18=(Token)match(input,22,FOLLOW_2); 

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
    // InternalThingML.g:3286:1: entryRuleFinalState returns [EObject current=null] : iv_ruleFinalState= ruleFinalState EOF ;
    public final EObject entryRuleFinalState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFinalState = null;


        try {
            // InternalThingML.g:3286:51: (iv_ruleFinalState= ruleFinalState EOF )
            // InternalThingML.g:3287:2: iv_ruleFinalState= ruleFinalState EOF
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
    // InternalThingML.g:3293:1: ruleFinalState returns [EObject current=null] : (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= ';' ) ;
    public final EObject ruleFinalState() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_4=null;
        EObject lv_annotations_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3299:2: ( (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= ';' ) )
            // InternalThingML.g:3300:2: (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= ';' )
            {
            // InternalThingML.g:3300:2: (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= ';' )
            // InternalThingML.g:3301:3: otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= ';'
            {
            otherlv_0=(Token)match(input,68,FOLLOW_51); 

            			newLeafNode(otherlv_0, grammarAccess.getFinalStateAccess().getFinalKeyword_0());
            		
            otherlv_1=(Token)match(input,69,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getFinalStateAccess().getStateKeyword_1());
            		
            // InternalThingML.g:3309:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:3310:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:3310:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:3311:5: lv_name_2_0= RULE_ID
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

            // InternalThingML.g:3327:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==14) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // InternalThingML.g:3328:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3328:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3329:5: lv_annotations_3_0= rulePlatformAnnotation
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
            	    break loop62;
                }
            } while (true);

            otherlv_4=(Token)match(input,18,FOLLOW_2); 

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
    // InternalThingML.g:3354:1: entryRuleCompositeState returns [EObject current=null] : iv_ruleCompositeState= ruleCompositeState EOF ;
    public final EObject entryRuleCompositeState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCompositeState = null;


        try {
            // InternalThingML.g:3354:55: (iv_ruleCompositeState= ruleCompositeState EOF )
            // InternalThingML.g:3355:2: iv_ruleCompositeState= ruleCompositeState EOF
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
    // InternalThingML.g:3361:1: ruleCompositeState returns [EObject current=null] : (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) )? otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_properties_15_0= ruleProperty ) ) | ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' ) ;
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
            // InternalThingML.g:3367:2: ( (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) )? otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_properties_15_0= ruleProperty ) ) | ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' ) )
            // InternalThingML.g:3368:2: (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) )? otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_properties_15_0= ruleProperty ) ) | ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' )
            {
            // InternalThingML.g:3368:2: (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) )? otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_properties_15_0= ruleProperty ) ) | ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' )
            // InternalThingML.g:3369:3: otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) )? otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_properties_15_0= ruleProperty ) ) | ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}'
            {
            otherlv_0=(Token)match(input,70,FOLLOW_51); 

            			newLeafNode(otherlv_0, grammarAccess.getCompositeStateAccess().getCompositeKeyword_0());
            		
            otherlv_1=(Token)match(input,69,FOLLOW_42); 

            			newLeafNode(otherlv_1, grammarAccess.getCompositeStateAccess().getStateKeyword_1());
            		
            // InternalThingML.g:3377:3: ( (lv_name_2_0= RULE_ID ) )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==RULE_ID) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalThingML.g:3378:4: (lv_name_2_0= RULE_ID )
                    {
                    // InternalThingML.g:3378:4: (lv_name_2_0= RULE_ID )
                    // InternalThingML.g:3379:5: lv_name_2_0= RULE_ID
                    {
                    lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_43); 

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
            		
            // InternalThingML.g:3399:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:3400:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:3400:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:3401:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCompositeStateRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_44); 

            					newLeafNode(otherlv_4, grammarAccess.getCompositeStateAccess().getInitialStateCrossReference_4_0());
            				

            }


            }

            // InternalThingML.g:3412:3: (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==63) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalThingML.g:3413:4: otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) )
                    {
                    otherlv_5=(Token)match(input,63,FOLLOW_45); 

                    				newLeafNode(otherlv_5, grammarAccess.getCompositeStateAccess().getKeepsKeyword_5_0());
                    			
                    // InternalThingML.g:3417:4: ( (lv_history_6_0= 'history' ) )
                    // InternalThingML.g:3418:5: (lv_history_6_0= 'history' )
                    {
                    // InternalThingML.g:3418:5: (lv_history_6_0= 'history' )
                    // InternalThingML.g:3419:6: lv_history_6_0= 'history'
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

            // InternalThingML.g:3432:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==14) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // InternalThingML.g:3433:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3433:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3434:5: lv_annotations_7_0= rulePlatformAnnotation
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
            	    break loop65;
                }
            } while (true);

            otherlv_8=(Token)match(input,21,FOLLOW_52); 

            			newLeafNode(otherlv_8, grammarAccess.getCompositeStateAccess().getLeftCurlyBracketKeyword_7());
            		
            // InternalThingML.g:3455:3: (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==65) ) {
                int LA66_1 = input.LA(2);

                if ( (LA66_1==66) ) {
                    alt66=1;
                }
            }
            switch (alt66) {
                case 1 :
                    // InternalThingML.g:3456:4: otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) )
                    {
                    otherlv_9=(Token)match(input,65,FOLLOW_47); 

                    				newLeafNode(otherlv_9, grammarAccess.getCompositeStateAccess().getOnKeyword_8_0());
                    			
                    otherlv_10=(Token)match(input,66,FOLLOW_24); 

                    				newLeafNode(otherlv_10, grammarAccess.getCompositeStateAccess().getEntryKeyword_8_1());
                    			
                    // InternalThingML.g:3464:4: ( (lv_entry_11_0= ruleAction ) )
                    // InternalThingML.g:3465:5: (lv_entry_11_0= ruleAction )
                    {
                    // InternalThingML.g:3465:5: (lv_entry_11_0= ruleAction )
                    // InternalThingML.g:3466:6: lv_entry_11_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getCompositeStateAccess().getEntryActionParserRuleCall_8_2_0());
                    					
                    pushFollow(FOLLOW_52);
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

            // InternalThingML.g:3484:3: (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==65) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalThingML.g:3485:4: otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) )
                    {
                    otherlv_12=(Token)match(input,65,FOLLOW_48); 

                    				newLeafNode(otherlv_12, grammarAccess.getCompositeStateAccess().getOnKeyword_9_0());
                    			
                    otherlv_13=(Token)match(input,67,FOLLOW_24); 

                    				newLeafNode(otherlv_13, grammarAccess.getCompositeStateAccess().getExitKeyword_9_1());
                    			
                    // InternalThingML.g:3493:4: ( (lv_exit_14_0= ruleAction ) )
                    // InternalThingML.g:3494:5: (lv_exit_14_0= ruleAction )
                    {
                    // InternalThingML.g:3494:5: (lv_exit_14_0= ruleAction )
                    // InternalThingML.g:3495:6: lv_exit_14_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getCompositeStateAccess().getExitActionParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_53);
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

            // InternalThingML.g:3513:3: ( ( (lv_properties_15_0= ruleProperty ) ) | ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )*
            loop68:
            do {
                int alt68=5;
                switch ( input.LA(1) ) {
                case 36:
                    {
                    alt68=1;
                    }
                    break;
                case 61:
                case 68:
                case 69:
                case 70:
                case 71:
                    {
                    alt68=2;
                    }
                    break;
                case 44:
                    {
                    alt68=3;
                    }
                    break;
                case 73:
                    {
                    alt68=4;
                    }
                    break;

                }

                switch (alt68) {
            	case 1 :
            	    // InternalThingML.g:3514:4: ( (lv_properties_15_0= ruleProperty ) )
            	    {
            	    // InternalThingML.g:3514:4: ( (lv_properties_15_0= ruleProperty ) )
            	    // InternalThingML.g:3515:5: (lv_properties_15_0= ruleProperty )
            	    {
            	    // InternalThingML.g:3515:5: (lv_properties_15_0= ruleProperty )
            	    // InternalThingML.g:3516:6: lv_properties_15_0= ruleProperty
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getPropertiesPropertyParserRuleCall_10_0_0());
            	    					
            	    pushFollow(FOLLOW_53);
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
            	    // InternalThingML.g:3534:4: ( (lv_substate_16_0= ruleState ) )
            	    {
            	    // InternalThingML.g:3534:4: ( (lv_substate_16_0= ruleState ) )
            	    // InternalThingML.g:3535:5: (lv_substate_16_0= ruleState )
            	    {
            	    // InternalThingML.g:3535:5: (lv_substate_16_0= ruleState )
            	    // InternalThingML.g:3536:6: lv_substate_16_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getSubstateStateParserRuleCall_10_1_0());
            	    					
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
            	case 3 :
            	    // InternalThingML.g:3554:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:3554:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    // InternalThingML.g:3555:5: (lv_internal_17_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:3555:5: (lv_internal_17_0= ruleInternalTransition )
            	    // InternalThingML.g:3556:6: lv_internal_17_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getInternalInternalTransitionParserRuleCall_10_2_0());
            	    					
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
            	case 4 :
            	    // InternalThingML.g:3574:4: ( (lv_outgoing_18_0= ruleTransition ) )
            	    {
            	    // InternalThingML.g:3574:4: ( (lv_outgoing_18_0= ruleTransition ) )
            	    // InternalThingML.g:3575:5: (lv_outgoing_18_0= ruleTransition )
            	    {
            	    // InternalThingML.g:3575:5: (lv_outgoing_18_0= ruleTransition )
            	    // InternalThingML.g:3576:6: lv_outgoing_18_0= ruleTransition
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getOutgoingTransitionParserRuleCall_10_3_0());
            	    					
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
            	    break loop68;
                }
            } while (true);

            // InternalThingML.g:3594:3: ( (lv_region_19_0= ruleParallelRegion ) )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==72) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // InternalThingML.g:3595:4: (lv_region_19_0= ruleParallelRegion )
            	    {
            	    // InternalThingML.g:3595:4: (lv_region_19_0= ruleParallelRegion )
            	    // InternalThingML.g:3596:5: lv_region_19_0= ruleParallelRegion
            	    {

            	    					newCompositeNode(grammarAccess.getCompositeStateAccess().getRegionParallelRegionParserRuleCall_11_0());
            	    				
            	    pushFollow(FOLLOW_50);
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
            	    break loop69;
                }
            } while (true);

            otherlv_20=(Token)match(input,22,FOLLOW_2); 

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
    // InternalThingML.g:3621:1: entryRuleSession returns [EObject current=null] : iv_ruleSession= ruleSession EOF ;
    public final EObject entryRuleSession() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSession = null;


        try {
            // InternalThingML.g:3621:48: (iv_ruleSession= ruleSession EOF )
            // InternalThingML.g:3622:2: iv_ruleSession= ruleSession EOF
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
    // InternalThingML.g:3628:1: ruleSession returns [EObject current=null] : (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' (otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) ) )? (otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) ) )? ( ( (lv_properties_12_0= ruleProperty ) ) | ( (lv_substate_13_0= ruleState ) ) | ( (lv_internal_14_0= ruleInternalTransition ) ) )* ( (lv_region_15_0= ruleParallelRegion ) )* otherlv_16= '}' ) ;
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
            // InternalThingML.g:3634:2: ( (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' (otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) ) )? (otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) ) )? ( ( (lv_properties_12_0= ruleProperty ) ) | ( (lv_substate_13_0= ruleState ) ) | ( (lv_internal_14_0= ruleInternalTransition ) ) )* ( (lv_region_15_0= ruleParallelRegion ) )* otherlv_16= '}' ) )
            // InternalThingML.g:3635:2: (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' (otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) ) )? (otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) ) )? ( ( (lv_properties_12_0= ruleProperty ) ) | ( (lv_substate_13_0= ruleState ) ) | ( (lv_internal_14_0= ruleInternalTransition ) ) )* ( (lv_region_15_0= ruleParallelRegion ) )* otherlv_16= '}' )
            {
            // InternalThingML.g:3635:2: (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' (otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) ) )? (otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) ) )? ( ( (lv_properties_12_0= ruleProperty ) ) | ( (lv_substate_13_0= ruleState ) ) | ( (lv_internal_14_0= ruleInternalTransition ) ) )* ( (lv_region_15_0= ruleParallelRegion ) )* otherlv_16= '}' )
            // InternalThingML.g:3636:3: otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' (otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) ) )? (otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) ) )? ( ( (lv_properties_12_0= ruleProperty ) ) | ( (lv_substate_13_0= ruleState ) ) | ( (lv_internal_14_0= ruleInternalTransition ) ) )* ( (lv_region_15_0= ruleParallelRegion ) )* otherlv_16= '}'
            {
            otherlv_0=(Token)match(input,71,FOLLOW_42); 

            			newLeafNode(otherlv_0, grammarAccess.getSessionAccess().getSessionKeyword_0());
            		
            // InternalThingML.g:3640:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==RULE_ID) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // InternalThingML.g:3641:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:3641:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:3642:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_43); 

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
            		
            // InternalThingML.g:3662:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:3663:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:3663:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:3664:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSessionRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_11); 

            					newLeafNode(otherlv_3, grammarAccess.getSessionAccess().getInitialStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:3675:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==14) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // InternalThingML.g:3676:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3676:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3677:5: lv_annotations_4_0= rulePlatformAnnotation
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
            	    break loop71;
                }
            } while (true);

            otherlv_5=(Token)match(input,21,FOLLOW_46); 

            			newLeafNode(otherlv_5, grammarAccess.getSessionAccess().getLeftCurlyBracketKeyword_5());
            		
            // InternalThingML.g:3698:3: (otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) ) )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==65) ) {
                int LA72_1 = input.LA(2);

                if ( (LA72_1==66) ) {
                    alt72=1;
                }
            }
            switch (alt72) {
                case 1 :
                    // InternalThingML.g:3699:4: otherlv_6= 'on' otherlv_7= 'entry' ( (lv_entry_8_0= ruleAction ) )
                    {
                    otherlv_6=(Token)match(input,65,FOLLOW_47); 

                    				newLeafNode(otherlv_6, grammarAccess.getSessionAccess().getOnKeyword_6_0());
                    			
                    otherlv_7=(Token)match(input,66,FOLLOW_24); 

                    				newLeafNode(otherlv_7, grammarAccess.getSessionAccess().getEntryKeyword_6_1());
                    			
                    // InternalThingML.g:3707:4: ( (lv_entry_8_0= ruleAction ) )
                    // InternalThingML.g:3708:5: (lv_entry_8_0= ruleAction )
                    {
                    // InternalThingML.g:3708:5: (lv_entry_8_0= ruleAction )
                    // InternalThingML.g:3709:6: lv_entry_8_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getSessionAccess().getEntryActionParserRuleCall_6_2_0());
                    					
                    pushFollow(FOLLOW_46);
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

            // InternalThingML.g:3727:3: (otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) ) )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==65) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // InternalThingML.g:3728:4: otherlv_9= 'on' otherlv_10= 'exit' ( (lv_exit_11_0= ruleAction ) )
                    {
                    otherlv_9=(Token)match(input,65,FOLLOW_48); 

                    				newLeafNode(otherlv_9, grammarAccess.getSessionAccess().getOnKeyword_7_0());
                    			
                    otherlv_10=(Token)match(input,67,FOLLOW_24); 

                    				newLeafNode(otherlv_10, grammarAccess.getSessionAccess().getExitKeyword_7_1());
                    			
                    // InternalThingML.g:3736:4: ( (lv_exit_11_0= ruleAction ) )
                    // InternalThingML.g:3737:5: (lv_exit_11_0= ruleAction )
                    {
                    // InternalThingML.g:3737:5: (lv_exit_11_0= ruleAction )
                    // InternalThingML.g:3738:6: lv_exit_11_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getSessionAccess().getExitActionParserRuleCall_7_2_0());
                    					
                    pushFollow(FOLLOW_49);
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

            // InternalThingML.g:3756:3: ( ( (lv_properties_12_0= ruleProperty ) ) | ( (lv_substate_13_0= ruleState ) ) | ( (lv_internal_14_0= ruleInternalTransition ) ) )*
            loop74:
            do {
                int alt74=4;
                switch ( input.LA(1) ) {
                case 36:
                    {
                    alt74=1;
                    }
                    break;
                case 61:
                case 68:
                case 69:
                case 70:
                case 71:
                    {
                    alt74=2;
                    }
                    break;
                case 44:
                    {
                    alt74=3;
                    }
                    break;

                }

                switch (alt74) {
            	case 1 :
            	    // InternalThingML.g:3757:4: ( (lv_properties_12_0= ruleProperty ) )
            	    {
            	    // InternalThingML.g:3757:4: ( (lv_properties_12_0= ruleProperty ) )
            	    // InternalThingML.g:3758:5: (lv_properties_12_0= ruleProperty )
            	    {
            	    // InternalThingML.g:3758:5: (lv_properties_12_0= ruleProperty )
            	    // InternalThingML.g:3759:6: lv_properties_12_0= ruleProperty
            	    {

            	    						newCompositeNode(grammarAccess.getSessionAccess().getPropertiesPropertyParserRuleCall_8_0_0());
            	    					
            	    pushFollow(FOLLOW_49);
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
            	    // InternalThingML.g:3777:4: ( (lv_substate_13_0= ruleState ) )
            	    {
            	    // InternalThingML.g:3777:4: ( (lv_substate_13_0= ruleState ) )
            	    // InternalThingML.g:3778:5: (lv_substate_13_0= ruleState )
            	    {
            	    // InternalThingML.g:3778:5: (lv_substate_13_0= ruleState )
            	    // InternalThingML.g:3779:6: lv_substate_13_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getSessionAccess().getSubstateStateParserRuleCall_8_1_0());
            	    					
            	    pushFollow(FOLLOW_49);
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
            	    // InternalThingML.g:3797:4: ( (lv_internal_14_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:3797:4: ( (lv_internal_14_0= ruleInternalTransition ) )
            	    // InternalThingML.g:3798:5: (lv_internal_14_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:3798:5: (lv_internal_14_0= ruleInternalTransition )
            	    // InternalThingML.g:3799:6: lv_internal_14_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getSessionAccess().getInternalInternalTransitionParserRuleCall_8_2_0());
            	    					
            	    pushFollow(FOLLOW_49);
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
            	    break loop74;
                }
            } while (true);

            // InternalThingML.g:3817:3: ( (lv_region_15_0= ruleParallelRegion ) )*
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( (LA75_0==72) ) {
                    alt75=1;
                }


                switch (alt75) {
            	case 1 :
            	    // InternalThingML.g:3818:4: (lv_region_15_0= ruleParallelRegion )
            	    {
            	    // InternalThingML.g:3818:4: (lv_region_15_0= ruleParallelRegion )
            	    // InternalThingML.g:3819:5: lv_region_15_0= ruleParallelRegion
            	    {

            	    					newCompositeNode(grammarAccess.getSessionAccess().getRegionParallelRegionParserRuleCall_9_0());
            	    				
            	    pushFollow(FOLLOW_50);
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
            	    break loop75;
                }
            } while (true);

            otherlv_16=(Token)match(input,22,FOLLOW_2); 

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
    // InternalThingML.g:3844:1: entryRuleParallelRegion returns [EObject current=null] : iv_ruleParallelRegion= ruleParallelRegion EOF ;
    public final EObject entryRuleParallelRegion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParallelRegion = null;


        try {
            // InternalThingML.g:3844:55: (iv_ruleParallelRegion= ruleParallelRegion EOF )
            // InternalThingML.g:3845:2: iv_ruleParallelRegion= ruleParallelRegion EOF
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
    // InternalThingML.g:3851:1: ruleParallelRegion returns [EObject current=null] : (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) ;
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
            // InternalThingML.g:3857:2: ( (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) )
            // InternalThingML.g:3858:2: (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' )
            {
            // InternalThingML.g:3858:2: (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' )
            // InternalThingML.g:3859:3: otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,72,FOLLOW_42); 

            			newLeafNode(otherlv_0, grammarAccess.getParallelRegionAccess().getRegionKeyword_0());
            		
            // InternalThingML.g:3863:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==RULE_ID) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // InternalThingML.g:3864:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:3864:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:3865:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_43); 

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
            		
            // InternalThingML.g:3885:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:3886:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:3886:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:3887:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getParallelRegionRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_44); 

            					newLeafNode(otherlv_3, grammarAccess.getParallelRegionAccess().getInitialStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:3898:3: (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==63) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // InternalThingML.g:3899:4: otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) )
                    {
                    otherlv_4=(Token)match(input,63,FOLLOW_45); 

                    				newLeafNode(otherlv_4, grammarAccess.getParallelRegionAccess().getKeepsKeyword_4_0());
                    			
                    // InternalThingML.g:3903:4: ( (lv_history_5_0= 'history' ) )
                    // InternalThingML.g:3904:5: (lv_history_5_0= 'history' )
                    {
                    // InternalThingML.g:3904:5: (lv_history_5_0= 'history' )
                    // InternalThingML.g:3905:6: lv_history_5_0= 'history'
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

            // InternalThingML.g:3918:3: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            loop78:
            do {
                int alt78=2;
                int LA78_0 = input.LA(1);

                if ( (LA78_0==14) ) {
                    alt78=1;
                }


                switch (alt78) {
            	case 1 :
            	    // InternalThingML.g:3919:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3919:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3920:5: lv_annotations_6_0= rulePlatformAnnotation
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
            	    break loop78;
                }
            } while (true);

            otherlv_7=(Token)match(input,21,FOLLOW_54); 

            			newLeafNode(otherlv_7, grammarAccess.getParallelRegionAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:3941:3: ( (lv_substate_8_0= ruleState ) )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==61||(LA79_0>=68 && LA79_0<=71)) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // InternalThingML.g:3942:4: (lv_substate_8_0= ruleState )
            	    {
            	    // InternalThingML.g:3942:4: (lv_substate_8_0= ruleState )
            	    // InternalThingML.g:3943:5: lv_substate_8_0= ruleState
            	    {

            	    					newCompositeNode(grammarAccess.getParallelRegionAccess().getSubstateStateParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_54);
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
            	    break loop79;
                }
            } while (true);

            otherlv_9=(Token)match(input,22,FOLLOW_2); 

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
    // InternalThingML.g:3968:1: entryRuleState returns [EObject current=null] : iv_ruleState= ruleState EOF ;
    public final EObject entryRuleState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleState = null;


        try {
            // InternalThingML.g:3968:46: (iv_ruleState= ruleState EOF )
            // InternalThingML.g:3969:2: iv_ruleState= ruleState EOF
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
    // InternalThingML.g:3975:1: ruleState returns [EObject current=null] : (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) ) ;
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
            // InternalThingML.g:3981:2: ( (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) ) )
            // InternalThingML.g:3982:2: (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) )
            {
            // InternalThingML.g:3982:2: (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) )
            int alt84=5;
            switch ( input.LA(1) ) {
            case 61:
                {
                alt84=1;
                }
                break;
            case 68:
                {
                alt84=2;
                }
                break;
            case 70:
                {
                alt84=3;
                }
                break;
            case 71:
                {
                alt84=4;
                }
                break;
            case 69:
                {
                alt84=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }

            switch (alt84) {
                case 1 :
                    // InternalThingML.g:3983:3: this_StateMachine_0= ruleStateMachine
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
                    // InternalThingML.g:3992:3: this_FinalState_1= ruleFinalState
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
                    // InternalThingML.g:4001:3: this_CompositeState_2= ruleCompositeState
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
                    // InternalThingML.g:4010:3: this_Session_3= ruleSession
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
                    // InternalThingML.g:4019:3: (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' )
                    {
                    // InternalThingML.g:4019:3: (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' )
                    // InternalThingML.g:4020:4: otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )? (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )? ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}'
                    {
                    otherlv_4=(Token)match(input,69,FOLLOW_6); 

                    				newLeafNode(otherlv_4, grammarAccess.getStateAccess().getStateKeyword_4_0());
                    			
                    // InternalThingML.g:4024:4: ( (lv_name_5_0= RULE_ID ) )
                    // InternalThingML.g:4025:5: (lv_name_5_0= RULE_ID )
                    {
                    // InternalThingML.g:4025:5: (lv_name_5_0= RULE_ID )
                    // InternalThingML.g:4026:6: lv_name_5_0= RULE_ID
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

                    // InternalThingML.g:4042:4: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
                    loop80:
                    do {
                        int alt80=2;
                        int LA80_0 = input.LA(1);

                        if ( (LA80_0==14) ) {
                            alt80=1;
                        }


                        switch (alt80) {
                    	case 1 :
                    	    // InternalThingML.g:4043:5: (lv_annotations_6_0= rulePlatformAnnotation )
                    	    {
                    	    // InternalThingML.g:4043:5: (lv_annotations_6_0= rulePlatformAnnotation )
                    	    // InternalThingML.g:4044:6: lv_annotations_6_0= rulePlatformAnnotation
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
                    	    break loop80;
                        }
                    } while (true);

                    otherlv_7=(Token)match(input,21,FOLLOW_55); 

                    				newLeafNode(otherlv_7, grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_4_3());
                    			
                    // InternalThingML.g:4065:4: (otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) ) )?
                    int alt81=2;
                    int LA81_0 = input.LA(1);

                    if ( (LA81_0==65) ) {
                        int LA81_1 = input.LA(2);

                        if ( (LA81_1==66) ) {
                            alt81=1;
                        }
                    }
                    switch (alt81) {
                        case 1 :
                            // InternalThingML.g:4066:5: otherlv_8= 'on' otherlv_9= 'entry' ( (lv_entry_10_0= ruleAction ) )
                            {
                            otherlv_8=(Token)match(input,65,FOLLOW_47); 

                            					newLeafNode(otherlv_8, grammarAccess.getStateAccess().getOnKeyword_4_4_0());
                            				
                            otherlv_9=(Token)match(input,66,FOLLOW_24); 

                            					newLeafNode(otherlv_9, grammarAccess.getStateAccess().getEntryKeyword_4_4_1());
                            				
                            // InternalThingML.g:4074:5: ( (lv_entry_10_0= ruleAction ) )
                            // InternalThingML.g:4075:6: (lv_entry_10_0= ruleAction )
                            {
                            // InternalThingML.g:4075:6: (lv_entry_10_0= ruleAction )
                            // InternalThingML.g:4076:7: lv_entry_10_0= ruleAction
                            {

                            							newCompositeNode(grammarAccess.getStateAccess().getEntryActionParserRuleCall_4_4_2_0());
                            						
                            pushFollow(FOLLOW_55);
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

                    // InternalThingML.g:4094:4: (otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) ) )?
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==65) ) {
                        alt82=1;
                    }
                    switch (alt82) {
                        case 1 :
                            // InternalThingML.g:4095:5: otherlv_11= 'on' otherlv_12= 'exit' ( (lv_exit_13_0= ruleAction ) )
                            {
                            otherlv_11=(Token)match(input,65,FOLLOW_48); 

                            					newLeafNode(otherlv_11, grammarAccess.getStateAccess().getOnKeyword_4_5_0());
                            				
                            otherlv_12=(Token)match(input,67,FOLLOW_24); 

                            					newLeafNode(otherlv_12, grammarAccess.getStateAccess().getExitKeyword_4_5_1());
                            				
                            // InternalThingML.g:4103:5: ( (lv_exit_13_0= ruleAction ) )
                            // InternalThingML.g:4104:6: (lv_exit_13_0= ruleAction )
                            {
                            // InternalThingML.g:4104:6: (lv_exit_13_0= ruleAction )
                            // InternalThingML.g:4105:7: lv_exit_13_0= ruleAction
                            {

                            							newCompositeNode(grammarAccess.getStateAccess().getExitActionParserRuleCall_4_5_2_0());
                            						
                            pushFollow(FOLLOW_56);
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

                    // InternalThingML.g:4123:4: ( ( (lv_properties_14_0= ruleProperty ) ) | ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )*
                    loop83:
                    do {
                        int alt83=4;
                        switch ( input.LA(1) ) {
                        case 36:
                            {
                            alt83=1;
                            }
                            break;
                        case 44:
                            {
                            alt83=2;
                            }
                            break;
                        case 73:
                            {
                            alt83=3;
                            }
                            break;

                        }

                        switch (alt83) {
                    	case 1 :
                    	    // InternalThingML.g:4124:5: ( (lv_properties_14_0= ruleProperty ) )
                    	    {
                    	    // InternalThingML.g:4124:5: ( (lv_properties_14_0= ruleProperty ) )
                    	    // InternalThingML.g:4125:6: (lv_properties_14_0= ruleProperty )
                    	    {
                    	    // InternalThingML.g:4125:6: (lv_properties_14_0= ruleProperty )
                    	    // InternalThingML.g:4126:7: lv_properties_14_0= ruleProperty
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getPropertiesPropertyParserRuleCall_4_6_0_0());
                    	    						
                    	    pushFollow(FOLLOW_56);
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
                    	    // InternalThingML.g:4144:5: ( (lv_internal_15_0= ruleInternalTransition ) )
                    	    {
                    	    // InternalThingML.g:4144:5: ( (lv_internal_15_0= ruleInternalTransition ) )
                    	    // InternalThingML.g:4145:6: (lv_internal_15_0= ruleInternalTransition )
                    	    {
                    	    // InternalThingML.g:4145:6: (lv_internal_15_0= ruleInternalTransition )
                    	    // InternalThingML.g:4146:7: lv_internal_15_0= ruleInternalTransition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getInternalInternalTransitionParserRuleCall_4_6_1_0());
                    	    						
                    	    pushFollow(FOLLOW_56);
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
                    	    // InternalThingML.g:4164:5: ( (lv_outgoing_16_0= ruleTransition ) )
                    	    {
                    	    // InternalThingML.g:4164:5: ( (lv_outgoing_16_0= ruleTransition ) )
                    	    // InternalThingML.g:4165:6: (lv_outgoing_16_0= ruleTransition )
                    	    {
                    	    // InternalThingML.g:4165:6: (lv_outgoing_16_0= ruleTransition )
                    	    // InternalThingML.g:4166:7: lv_outgoing_16_0= ruleTransition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getOutgoingTransitionParserRuleCall_4_6_2_0());
                    	    						
                    	    pushFollow(FOLLOW_56);
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
                    	    break loop83;
                        }
                    } while (true);

                    otherlv_17=(Token)match(input,22,FOLLOW_2); 

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
    // InternalThingML.g:4193:1: entryRuleTransition returns [EObject current=null] : iv_ruleTransition= ruleTransition EOF ;
    public final EObject entryRuleTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransition = null;


        try {
            // InternalThingML.g:4193:51: (iv_ruleTransition= ruleTransition EOF )
            // InternalThingML.g:4194:2: iv_ruleTransition= ruleTransition EOF
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
    // InternalThingML.g:4200:1: ruleTransition returns [EObject current=null] : (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? ) ;
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
            // InternalThingML.g:4206:2: ( (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? ) )
            // InternalThingML.g:4207:2: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:4207:2: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? )
            // InternalThingML.g:4208:3: otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )?
            {
            otherlv_0=(Token)match(input,73,FOLLOW_57); 

            			newLeafNode(otherlv_0, grammarAccess.getTransitionAccess().getTransitionKeyword_0());
            		
            // InternalThingML.g:4212:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==RULE_ID) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // InternalThingML.g:4213:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:4213:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:4214:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_58); 

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
            		
            // InternalThingML.g:4234:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:4235:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:4235:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:4236:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTransitionRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_59); 

            					newLeafNode(otherlv_3, grammarAccess.getTransitionAccess().getTargetStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:4247:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( (LA86_0==14) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // InternalThingML.g:4248:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:4248:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:4249:5: lv_annotations_4_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_59);
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
            	    break loop86;
                }
            } while (true);

            // InternalThingML.g:4266:3: (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )*
            loop87:
            do {
                int alt87=2;
                int LA87_0 = input.LA(1);

                if ( (LA87_0==74) ) {
                    alt87=1;
                }


                switch (alt87) {
            	case 1 :
            	    // InternalThingML.g:4267:4: otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) )
            	    {
            	    otherlv_5=(Token)match(input,74,FOLLOW_6); 

            	    				newLeafNode(otherlv_5, grammarAccess.getTransitionAccess().getEventKeyword_5_0());
            	    			
            	    // InternalThingML.g:4271:4: ( (lv_event_6_0= ruleEvent ) )
            	    // InternalThingML.g:4272:5: (lv_event_6_0= ruleEvent )
            	    {
            	    // InternalThingML.g:4272:5: (lv_event_6_0= ruleEvent )
            	    // InternalThingML.g:4273:6: lv_event_6_0= ruleEvent
            	    {

            	    						newCompositeNode(grammarAccess.getTransitionAccess().getEventEventParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_60);
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
            	    break loop87;
                }
            } while (true);

            // InternalThingML.g:4291:3: (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==75) ) {
                alt88=1;
            }
            switch (alt88) {
                case 1 :
                    // InternalThingML.g:4292:4: otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) )
                    {
                    otherlv_7=(Token)match(input,75,FOLLOW_19); 

                    				newLeafNode(otherlv_7, grammarAccess.getTransitionAccess().getGuardKeyword_6_0());
                    			
                    // InternalThingML.g:4296:4: ( (lv_guard_8_0= ruleExpression ) )
                    // InternalThingML.g:4297:5: (lv_guard_8_0= ruleExpression )
                    {
                    // InternalThingML.g:4297:5: (lv_guard_8_0= ruleExpression )
                    // InternalThingML.g:4298:6: lv_guard_8_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getTransitionAccess().getGuardExpressionParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_61);
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

            // InternalThingML.g:4316:3: (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==76) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // InternalThingML.g:4317:4: otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) )
                    {
                    otherlv_9=(Token)match(input,76,FOLLOW_24); 

                    				newLeafNode(otherlv_9, grammarAccess.getTransitionAccess().getActionKeyword_7_0());
                    			
                    // InternalThingML.g:4321:4: ( (lv_action_10_0= ruleAction ) )
                    // InternalThingML.g:4322:5: (lv_action_10_0= ruleAction )
                    {
                    // InternalThingML.g:4322:5: (lv_action_10_0= ruleAction )
                    // InternalThingML.g:4323:6: lv_action_10_0= ruleAction
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
    // InternalThingML.g:4345:1: entryRuleInternalTransition returns [EObject current=null] : iv_ruleInternalTransition= ruleInternalTransition EOF ;
    public final EObject entryRuleInternalTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInternalTransition = null;


        try {
            // InternalThingML.g:4345:59: (iv_ruleInternalTransition= ruleInternalTransition EOF )
            // InternalThingML.g:4346:2: iv_ruleInternalTransition= ruleInternalTransition EOF
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
    // InternalThingML.g:4352:1: ruleInternalTransition returns [EObject current=null] : ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? ) ;
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
            // InternalThingML.g:4358:2: ( ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? ) )
            // InternalThingML.g:4359:2: ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:4359:2: ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? )
            // InternalThingML.g:4360:3: () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )?
            {
            // InternalThingML.g:4360:3: ()
            // InternalThingML.g:4361:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getInternalTransitionAccess().getInternalTransitionAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,44,FOLLOW_62); 

            			newLeafNode(otherlv_1, grammarAccess.getInternalTransitionAccess().getInternalKeyword_1());
            		
            // InternalThingML.g:4371:3: ( (lv_name_2_0= RULE_ID ) )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==RULE_ID) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // InternalThingML.g:4372:4: (lv_name_2_0= RULE_ID )
                    {
                    // InternalThingML.g:4372:4: (lv_name_2_0= RULE_ID )
                    // InternalThingML.g:4373:5: lv_name_2_0= RULE_ID
                    {
                    lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_59); 

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

            // InternalThingML.g:4389:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( (LA91_0==14) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // InternalThingML.g:4390:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:4390:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:4391:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getInternalTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_59);
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
            	    break loop91;
                }
            } while (true);

            // InternalThingML.g:4408:3: (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( (LA92_0==74) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // InternalThingML.g:4409:4: otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) )
            	    {
            	    otherlv_4=(Token)match(input,74,FOLLOW_6); 

            	    				newLeafNode(otherlv_4, grammarAccess.getInternalTransitionAccess().getEventKeyword_4_0());
            	    			
            	    // InternalThingML.g:4413:4: ( (lv_event_5_0= ruleEvent ) )
            	    // InternalThingML.g:4414:5: (lv_event_5_0= ruleEvent )
            	    {
            	    // InternalThingML.g:4414:5: (lv_event_5_0= ruleEvent )
            	    // InternalThingML.g:4415:6: lv_event_5_0= ruleEvent
            	    {

            	    						newCompositeNode(grammarAccess.getInternalTransitionAccess().getEventEventParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_60);
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
            	    break loop92;
                }
            } while (true);

            // InternalThingML.g:4433:3: (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==75) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // InternalThingML.g:4434:4: otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) )
                    {
                    otherlv_6=(Token)match(input,75,FOLLOW_19); 

                    				newLeafNode(otherlv_6, grammarAccess.getInternalTransitionAccess().getGuardKeyword_5_0());
                    			
                    // InternalThingML.g:4438:4: ( (lv_guard_7_0= ruleExpression ) )
                    // InternalThingML.g:4439:5: (lv_guard_7_0= ruleExpression )
                    {
                    // InternalThingML.g:4439:5: (lv_guard_7_0= ruleExpression )
                    // InternalThingML.g:4440:6: lv_guard_7_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getInternalTransitionAccess().getGuardExpressionParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_61);
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

            // InternalThingML.g:4458:3: (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==76) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // InternalThingML.g:4459:4: otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) )
                    {
                    otherlv_8=(Token)match(input,76,FOLLOW_24); 

                    				newLeafNode(otherlv_8, grammarAccess.getInternalTransitionAccess().getActionKeyword_6_0());
                    			
                    // InternalThingML.g:4463:4: ( (lv_action_9_0= ruleAction ) )
                    // InternalThingML.g:4464:5: (lv_action_9_0= ruleAction )
                    {
                    // InternalThingML.g:4464:5: (lv_action_9_0= ruleAction )
                    // InternalThingML.g:4465:6: lv_action_9_0= ruleAction
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
    // InternalThingML.g:4487:1: entryRuleEvent returns [EObject current=null] : iv_ruleEvent= ruleEvent EOF ;
    public final EObject entryRuleEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEvent = null;


        try {
            // InternalThingML.g:4487:46: (iv_ruleEvent= ruleEvent EOF )
            // InternalThingML.g:4488:2: iv_ruleEvent= ruleEvent EOF
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
    // InternalThingML.g:4494:1: ruleEvent returns [EObject current=null] : this_ReceiveMessage_0= ruleReceiveMessage ;
    public final EObject ruleEvent() throws RecognitionException {
        EObject current = null;

        EObject this_ReceiveMessage_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4500:2: (this_ReceiveMessage_0= ruleReceiveMessage )
            // InternalThingML.g:4501:2: this_ReceiveMessage_0= ruleReceiveMessage
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
    // InternalThingML.g:4512:1: entryRuleReceiveMessage returns [EObject current=null] : iv_ruleReceiveMessage= ruleReceiveMessage EOF ;
    public final EObject entryRuleReceiveMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReceiveMessage = null;


        try {
            // InternalThingML.g:4512:55: (iv_ruleReceiveMessage= ruleReceiveMessage EOF )
            // InternalThingML.g:4513:2: iv_ruleReceiveMessage= ruleReceiveMessage EOF
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
    // InternalThingML.g:4519:1: ruleReceiveMessage returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) ) ;
    public final EObject ruleReceiveMessage() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;


        	enterRule();

        try {
            // InternalThingML.g:4525:2: ( ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) ) )
            // InternalThingML.g:4526:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) )
            {
            // InternalThingML.g:4526:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) )
            // InternalThingML.g:4527:3: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) )
            {
            // InternalThingML.g:4527:3: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==RULE_ID) ) {
                int LA95_1 = input.LA(2);

                if ( (LA95_1==35) ) {
                    alt95=1;
                }
            }
            switch (alt95) {
                case 1 :
                    // InternalThingML.g:4528:4: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':'
                    {
                    // InternalThingML.g:4528:4: ( (lv_name_0_0= RULE_ID ) )
                    // InternalThingML.g:4529:5: (lv_name_0_0= RULE_ID )
                    {
                    // InternalThingML.g:4529:5: (lv_name_0_0= RULE_ID )
                    // InternalThingML.g:4530:6: lv_name_0_0= RULE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_25); 

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

            // InternalThingML.g:4551:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:4552:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:4552:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:4553:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReceiveMessageRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_63); 

            					newLeafNode(otherlv_2, grammarAccess.getReceiveMessageAccess().getPortPortCrossReference_1_0());
            				

            }


            }

            otherlv_3=(Token)match(input,77,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getReceiveMessageAccess().getQuestionMarkKeyword_2());
            		
            // InternalThingML.g:4568:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:4569:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:4569:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:4570:5: otherlv_4= RULE_ID
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
    // InternalThingML.g:4585:1: entryRuleAction returns [EObject current=null] : iv_ruleAction= ruleAction EOF ;
    public final EObject entryRuleAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAction = null;


        try {
            // InternalThingML.g:4585:47: (iv_ruleAction= ruleAction EOF )
            // InternalThingML.g:4586:2: iv_ruleAction= ruleAction EOF
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
    // InternalThingML.g:4592:1: ruleAction returns [EObject current=null] : (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable ) ;
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
            // InternalThingML.g:4598:2: ( (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable ) )
            // InternalThingML.g:4599:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )
            {
            // InternalThingML.g:4599:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )
            int alt96=14;
            alt96 = dfa96.predict(input);
            switch (alt96) {
                case 1 :
                    // InternalThingML.g:4600:3: this_ActionBlock_0= ruleActionBlock
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
                    // InternalThingML.g:4609:3: this_ExternStatement_1= ruleExternStatement
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
                    // InternalThingML.g:4618:3: this_SendAction_2= ruleSendAction
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
                    // InternalThingML.g:4627:3: this_VariableAssignment_3= ruleVariableAssignment
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
                    // InternalThingML.g:4636:3: this_Increment_4= ruleIncrement
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
                    // InternalThingML.g:4645:3: this_Decrement_5= ruleDecrement
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
                    // InternalThingML.g:4654:3: this_LoopAction_6= ruleLoopAction
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
                    // InternalThingML.g:4663:3: this_ConditionalAction_7= ruleConditionalAction
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
                    // InternalThingML.g:4672:3: this_ReturnAction_8= ruleReturnAction
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
                    // InternalThingML.g:4681:3: this_PrintAction_9= rulePrintAction
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
                    // InternalThingML.g:4690:3: this_ErrorAction_10= ruleErrorAction
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
                    // InternalThingML.g:4699:3: this_StartSession_11= ruleStartSession
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
                    // InternalThingML.g:4708:3: this_FunctionCallStatement_12= ruleFunctionCallStatement
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
                    // InternalThingML.g:4717:3: this_LocalVariable_13= ruleLocalVariable
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
    // InternalThingML.g:4729:1: entryRuleActionBlock returns [EObject current=null] : iv_ruleActionBlock= ruleActionBlock EOF ;
    public final EObject entryRuleActionBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActionBlock = null;


        try {
            // InternalThingML.g:4729:52: (iv_ruleActionBlock= ruleActionBlock EOF )
            // InternalThingML.g:4730:2: iv_ruleActionBlock= ruleActionBlock EOF
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
    // InternalThingML.g:4736:1: ruleActionBlock returns [EObject current=null] : ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' ) ;
    public final EObject ruleActionBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_actions_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4742:2: ( ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' ) )
            // InternalThingML.g:4743:2: ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' )
            {
            // InternalThingML.g:4743:2: ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' )
            // InternalThingML.g:4744:3: () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end'
            {
            // InternalThingML.g:4744:3: ()
            // InternalThingML.g:4745:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getActionBlockAccess().getActionBlockAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,78,FOLLOW_64); 

            			newLeafNode(otherlv_1, grammarAccess.getActionBlockAccess().getDoKeyword_1());
            		
            // InternalThingML.g:4755:3: ( (lv_actions_2_0= ruleAction ) )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( (LA97_0==RULE_ID||LA97_0==RULE_STRING_EXT||LA97_0==56||LA97_0==78||(LA97_0>=80 && LA97_0<=81)||LA97_0==85||(LA97_0>=87 && LA97_0<=90)) ) {
                    alt97=1;
                }


                switch (alt97) {
            	case 1 :
            	    // InternalThingML.g:4756:4: (lv_actions_2_0= ruleAction )
            	    {
            	    // InternalThingML.g:4756:4: (lv_actions_2_0= ruleAction )
            	    // InternalThingML.g:4757:5: lv_actions_2_0= ruleAction
            	    {

            	    					newCompositeNode(grammarAccess.getActionBlockAccess().getActionsActionParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_64);
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
            	    break loop97;
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
    // InternalThingML.g:4782:1: entryRuleExternStatement returns [EObject current=null] : iv_ruleExternStatement= ruleExternStatement EOF ;
    public final EObject entryRuleExternStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternStatement = null;


        try {
            // InternalThingML.g:4782:56: (iv_ruleExternStatement= ruleExternStatement EOF )
            // InternalThingML.g:4783:2: iv_ruleExternStatement= ruleExternStatement EOF
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
    // InternalThingML.g:4789:1: ruleExternStatement returns [EObject current=null] : ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) ;
    public final EObject ruleExternStatement() throws RecognitionException {
        EObject current = null;

        Token lv_statement_0_0=null;
        Token otherlv_1=null;
        EObject lv_segments_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4795:2: ( ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) )
            // InternalThingML.g:4796:2: ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            {
            // InternalThingML.g:4796:2: ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            // InternalThingML.g:4797:3: ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            {
            // InternalThingML.g:4797:3: ( (lv_statement_0_0= RULE_STRING_EXT ) )
            // InternalThingML.g:4798:4: (lv_statement_0_0= RULE_STRING_EXT )
            {
            // InternalThingML.g:4798:4: (lv_statement_0_0= RULE_STRING_EXT )
            // InternalThingML.g:4799:5: lv_statement_0_0= RULE_STRING_EXT
            {
            lv_statement_0_0=(Token)match(input,RULE_STRING_EXT,FOLLOW_65); 

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

            // InternalThingML.g:4815:3: (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            loop98:
            do {
                int alt98=2;
                int LA98_0 = input.LA(1);

                if ( (LA98_0==50) ) {
                    alt98=1;
                }


                switch (alt98) {
            	case 1 :
            	    // InternalThingML.g:4816:4: otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,50,FOLLOW_19); 

            	    				newLeafNode(otherlv_1, grammarAccess.getExternStatementAccess().getAmpersandKeyword_1_0());
            	    			
            	    // InternalThingML.g:4820:4: ( (lv_segments_2_0= ruleExpression ) )
            	    // InternalThingML.g:4821:5: (lv_segments_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:4821:5: (lv_segments_2_0= ruleExpression )
            	    // InternalThingML.g:4822:6: lv_segments_2_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getExternStatementAccess().getSegmentsExpressionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_65);
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
    // $ANTLR end "ruleExternStatement"


    // $ANTLR start "entryRuleLocalVariable"
    // InternalThingML.g:4844:1: entryRuleLocalVariable returns [EObject current=null] : iv_ruleLocalVariable= ruleLocalVariable EOF ;
    public final EObject entryRuleLocalVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalVariable = null;


        try {
            // InternalThingML.g:4844:54: (iv_ruleLocalVariable= ruleLocalVariable EOF )
            // InternalThingML.g:4845:2: iv_ruleLocalVariable= ruleLocalVariable EOF
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
    // InternalThingML.g:4851:1: ruleLocalVariable returns [EObject current=null] : ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (otherlv_4= RULE_ID ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:4857:2: ( ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (otherlv_4= RULE_ID ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:4858:2: ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (otherlv_4= RULE_ID ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:4858:2: ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (otherlv_4= RULE_ID ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:4859:3: ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (otherlv_4= RULE_ID ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:4859:3: ( (lv_changeable_0_0= 'readonly' ) )?
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==80) ) {
                alt99=1;
            }
            switch (alt99) {
                case 1 :
                    // InternalThingML.g:4860:4: (lv_changeable_0_0= 'readonly' )
                    {
                    // InternalThingML.g:4860:4: (lv_changeable_0_0= 'readonly' )
                    // InternalThingML.g:4861:5: lv_changeable_0_0= 'readonly'
                    {
                    lv_changeable_0_0=(Token)match(input,80,FOLLOW_66); 

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
            		
            // InternalThingML.g:4877:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:4878:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:4878:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:4879:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_25); 

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
            		
            // InternalThingML.g:4899:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:4900:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:4900:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:4901:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLocalVariableRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_26); 

            					newLeafNode(otherlv_4, grammarAccess.getLocalVariableAccess().getTypeTypeCrossReference_4_0());
            				

            }


            }

            // InternalThingML.g:4912:3: (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )?
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==30) ) {
                alt100=1;
            }
            switch (alt100) {
                case 1 :
                    // InternalThingML.g:4913:4: otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) )
                    {
                    otherlv_5=(Token)match(input,30,FOLLOW_19); 

                    				newLeafNode(otherlv_5, grammarAccess.getLocalVariableAccess().getEqualsSignKeyword_5_0());
                    			
                    // InternalThingML.g:4917:4: ( (lv_init_6_0= ruleExpression ) )
                    // InternalThingML.g:4918:5: (lv_init_6_0= ruleExpression )
                    {
                    // InternalThingML.g:4918:5: (lv_init_6_0= ruleExpression )
                    // InternalThingML.g:4919:6: lv_init_6_0= ruleExpression
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

            // InternalThingML.g:4937:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop101:
            do {
                int alt101=2;
                int LA101_0 = input.LA(1);

                if ( (LA101_0==14) ) {
                    alt101=1;
                }


                switch (alt101) {
            	case 1 :
            	    // InternalThingML.g:4938:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:4938:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:4939:5: lv_annotations_7_0= rulePlatformAnnotation
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
    // $ANTLR end "ruleLocalVariable"


    // $ANTLR start "entryRuleSendAction"
    // InternalThingML.g:4960:1: entryRuleSendAction returns [EObject current=null] : iv_ruleSendAction= ruleSendAction EOF ;
    public final EObject entryRuleSendAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSendAction = null;


        try {
            // InternalThingML.g:4960:51: (iv_ruleSendAction= ruleSendAction EOF )
            // InternalThingML.g:4961:2: iv_ruleSendAction= ruleSendAction EOF
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
    // InternalThingML.g:4967:1: ruleSendAction returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' ) ;
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
            // InternalThingML.g:4973:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' ) )
            // InternalThingML.g:4974:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' )
            {
            // InternalThingML.g:4974:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' )
            // InternalThingML.g:4975:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')'
            {
            // InternalThingML.g:4975:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4976:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4976:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4977:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSendActionRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_67); 

            					newLeafNode(otherlv_0, grammarAccess.getSendActionAccess().getPortPortCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,82,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getSendActionAccess().getExclamationMarkKeyword_1());
            		
            // InternalThingML.g:4992:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:4993:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:4993:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:4994:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSendActionRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_2, grammarAccess.getSendActionAccess().getMessageMessageCrossReference_2_0());
            				

            }


            }

            otherlv_3=(Token)match(input,33,FOLLOW_68); 

            			newLeafNode(otherlv_3, grammarAccess.getSendActionAccess().getLeftParenthesisKeyword_3());
            		
            // InternalThingML.g:5009:3: ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )?
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( ((LA103_0>=RULE_STRING_LIT && LA103_0<=RULE_FLOAT)||LA103_0==33||LA103_0==98||(LA103_0>=102 && LA103_0<=105)) ) {
                alt103=1;
            }
            switch (alt103) {
                case 1 :
                    // InternalThingML.g:5010:4: ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )*
                    {
                    // InternalThingML.g:5010:4: ( (lv_parameters_4_0= ruleExpression ) )
                    // InternalThingML.g:5011:5: (lv_parameters_4_0= ruleExpression )
                    {
                    // InternalThingML.g:5011:5: (lv_parameters_4_0= ruleExpression )
                    // InternalThingML.g:5012:6: lv_parameters_4_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_0_0());
                    					
                    pushFollow(FOLLOW_23);
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

                    // InternalThingML.g:5029:4: (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )*
                    loop102:
                    do {
                        int alt102=2;
                        int LA102_0 = input.LA(1);

                        if ( (LA102_0==26) ) {
                            alt102=1;
                        }


                        switch (alt102) {
                    	case 1 :
                    	    // InternalThingML.g:5030:5: otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) )
                    	    {
                    	    otherlv_5=(Token)match(input,26,FOLLOW_19); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getSendActionAccess().getCommaKeyword_4_1_0());
                    	    				
                    	    // InternalThingML.g:5034:5: ( (lv_parameters_6_0= ruleExpression ) )
                    	    // InternalThingML.g:5035:6: (lv_parameters_6_0= ruleExpression )
                    	    {
                    	    // InternalThingML.g:5035:6: (lv_parameters_6_0= ruleExpression )
                    	    // InternalThingML.g:5036:7: lv_parameters_6_0= ruleExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getSendActionAccess().getParametersExpressionParserRuleCall_4_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_23);
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
                    	    break loop102;
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
    // InternalThingML.g:5063:1: entryRuleVariableAssignment returns [EObject current=null] : iv_ruleVariableAssignment= ruleVariableAssignment EOF ;
    public final EObject entryRuleVariableAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableAssignment = null;


        try {
            // InternalThingML.g:5063:59: (iv_ruleVariableAssignment= ruleVariableAssignment EOF )
            // InternalThingML.g:5064:2: iv_ruleVariableAssignment= ruleVariableAssignment EOF
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
    // InternalThingML.g:5070:1: ruleVariableAssignment returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) ) ;
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
            // InternalThingML.g:5076:2: ( ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) ) )
            // InternalThingML.g:5077:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) )
            {
            // InternalThingML.g:5077:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) )
            // InternalThingML.g:5078:3: ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) )
            {
            // InternalThingML.g:5078:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5079:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5079:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5080:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVariableAssignmentRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_18); 

            					newLeafNode(otherlv_0, grammarAccess.getVariableAssignmentAccess().getPropertyVariableCrossReference_0_0());
            				

            }


            }

            // InternalThingML.g:5091:3: (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )*
            loop104:
            do {
                int alt104=2;
                int LA104_0 = input.LA(1);

                if ( (LA104_0==28) ) {
                    alt104=1;
                }


                switch (alt104) {
            	case 1 :
            	    // InternalThingML.g:5092:4: otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']'
            	    {
            	    otherlv_1=(Token)match(input,28,FOLLOW_19); 

            	    				newLeafNode(otherlv_1, grammarAccess.getVariableAssignmentAccess().getLeftSquareBracketKeyword_1_0());
            	    			
            	    // InternalThingML.g:5096:4: ( (lv_index_2_0= ruleExpression ) )
            	    // InternalThingML.g:5097:5: (lv_index_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:5097:5: (lv_index_2_0= ruleExpression )
            	    // InternalThingML.g:5098:6: lv_index_2_0= ruleExpression
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

            	    otherlv_3=(Token)match(input,29,FOLLOW_18); 

            	    				newLeafNode(otherlv_3, grammarAccess.getVariableAssignmentAccess().getRightSquareBracketKeyword_1_2());
            	    			

            	    }
            	    break;

            	default :
            	    break loop104;
                }
            } while (true);

            otherlv_4=(Token)match(input,30,FOLLOW_19); 

            			newLeafNode(otherlv_4, grammarAccess.getVariableAssignmentAccess().getEqualsSignKeyword_2());
            		
            // InternalThingML.g:5124:3: ( (lv_expression_5_0= ruleExpression ) )
            // InternalThingML.g:5125:4: (lv_expression_5_0= ruleExpression )
            {
            // InternalThingML.g:5125:4: (lv_expression_5_0= ruleExpression )
            // InternalThingML.g:5126:5: lv_expression_5_0= ruleExpression
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
    // InternalThingML.g:5147:1: entryRuleIncrement returns [EObject current=null] : iv_ruleIncrement= ruleIncrement EOF ;
    public final EObject entryRuleIncrement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIncrement = null;


        try {
            // InternalThingML.g:5147:50: (iv_ruleIncrement= ruleIncrement EOF )
            // InternalThingML.g:5148:2: iv_ruleIncrement= ruleIncrement EOF
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
    // InternalThingML.g:5154:1: ruleIncrement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' ) ;
    public final EObject ruleIncrement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:5160:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' ) )
            // InternalThingML.g:5161:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' )
            {
            // InternalThingML.g:5161:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' )
            // InternalThingML.g:5162:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '++'
            {
            // InternalThingML.g:5162:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5163:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5163:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5164:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getIncrementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_69); 

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
    // InternalThingML.g:5183:1: entryRuleDecrement returns [EObject current=null] : iv_ruleDecrement= ruleDecrement EOF ;
    public final EObject entryRuleDecrement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDecrement = null;


        try {
            // InternalThingML.g:5183:50: (iv_ruleDecrement= ruleDecrement EOF )
            // InternalThingML.g:5184:2: iv_ruleDecrement= ruleDecrement EOF
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
    // InternalThingML.g:5190:1: ruleDecrement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' ) ;
    public final EObject ruleDecrement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:5196:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' ) )
            // InternalThingML.g:5197:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' )
            {
            // InternalThingML.g:5197:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' )
            // InternalThingML.g:5198:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '--'
            {
            // InternalThingML.g:5198:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5199:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5199:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5200:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDecrementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_70); 

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
    // InternalThingML.g:5219:1: entryRuleLoopAction returns [EObject current=null] : iv_ruleLoopAction= ruleLoopAction EOF ;
    public final EObject entryRuleLoopAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLoopAction = null;


        try {
            // InternalThingML.g:5219:51: (iv_ruleLoopAction= ruleLoopAction EOF )
            // InternalThingML.g:5220:2: iv_ruleLoopAction= ruleLoopAction EOF
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
    // InternalThingML.g:5226:1: ruleLoopAction returns [EObject current=null] : (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) ) ;
    public final EObject ruleLoopAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_condition_2_0 = null;

        EObject lv_action_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5232:2: ( (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) ) )
            // InternalThingML.g:5233:2: (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) )
            {
            // InternalThingML.g:5233:2: (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) )
            // InternalThingML.g:5234:3: otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) )
            {
            otherlv_0=(Token)match(input,85,FOLLOW_21); 

            			newLeafNode(otherlv_0, grammarAccess.getLoopActionAccess().getWhileKeyword_0());
            		
            otherlv_1=(Token)match(input,33,FOLLOW_19); 

            			newLeafNode(otherlv_1, grammarAccess.getLoopActionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:5242:3: ( (lv_condition_2_0= ruleExpression ) )
            // InternalThingML.g:5243:4: (lv_condition_2_0= ruleExpression )
            {
            // InternalThingML.g:5243:4: (lv_condition_2_0= ruleExpression )
            // InternalThingML.g:5244:5: lv_condition_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getLoopActionAccess().getConditionExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_71);
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

            otherlv_3=(Token)match(input,34,FOLLOW_24); 

            			newLeafNode(otherlv_3, grammarAccess.getLoopActionAccess().getRightParenthesisKeyword_3());
            		
            // InternalThingML.g:5265:3: ( (lv_action_4_0= ruleAction ) )
            // InternalThingML.g:5266:4: (lv_action_4_0= ruleAction )
            {
            // InternalThingML.g:5266:4: (lv_action_4_0= ruleAction )
            // InternalThingML.g:5267:5: lv_action_4_0= ruleAction
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
    // InternalThingML.g:5288:1: entryRuleConditionalAction returns [EObject current=null] : iv_ruleConditionalAction= ruleConditionalAction EOF ;
    public final EObject entryRuleConditionalAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionalAction = null;


        try {
            // InternalThingML.g:5288:58: (iv_ruleConditionalAction= ruleConditionalAction EOF )
            // InternalThingML.g:5289:2: iv_ruleConditionalAction= ruleConditionalAction EOF
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
    // InternalThingML.g:5295:1: ruleConditionalAction returns [EObject current=null] : (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? ) ;
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
            // InternalThingML.g:5301:2: ( (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? ) )
            // InternalThingML.g:5302:2: (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:5302:2: (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? )
            // InternalThingML.g:5303:3: otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )?
            {
            otherlv_0=(Token)match(input,56,FOLLOW_21); 

            			newLeafNode(otherlv_0, grammarAccess.getConditionalActionAccess().getIfKeyword_0());
            		
            otherlv_1=(Token)match(input,33,FOLLOW_19); 

            			newLeafNode(otherlv_1, grammarAccess.getConditionalActionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:5311:3: ( (lv_condition_2_0= ruleExpression ) )
            // InternalThingML.g:5312:4: (lv_condition_2_0= ruleExpression )
            {
            // InternalThingML.g:5312:4: (lv_condition_2_0= ruleExpression )
            // InternalThingML.g:5313:5: lv_condition_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getConditionalActionAccess().getConditionExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_71);
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

            otherlv_3=(Token)match(input,34,FOLLOW_24); 

            			newLeafNode(otherlv_3, grammarAccess.getConditionalActionAccess().getRightParenthesisKeyword_3());
            		
            // InternalThingML.g:5334:3: ( (lv_action_4_0= ruleAction ) )
            // InternalThingML.g:5335:4: (lv_action_4_0= ruleAction )
            {
            // InternalThingML.g:5335:4: (lv_action_4_0= ruleAction )
            // InternalThingML.g:5336:5: lv_action_4_0= ruleAction
            {

            					newCompositeNode(grammarAccess.getConditionalActionAccess().getActionActionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_72);
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

            // InternalThingML.g:5353:3: (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==86) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // InternalThingML.g:5354:4: otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) )
                    {
                    otherlv_5=(Token)match(input,86,FOLLOW_24); 

                    				newLeafNode(otherlv_5, grammarAccess.getConditionalActionAccess().getElseKeyword_5_0());
                    			
                    // InternalThingML.g:5358:4: ( (lv_elseAction_6_0= ruleAction ) )
                    // InternalThingML.g:5359:5: (lv_elseAction_6_0= ruleAction )
                    {
                    // InternalThingML.g:5359:5: (lv_elseAction_6_0= ruleAction )
                    // InternalThingML.g:5360:6: lv_elseAction_6_0= ruleAction
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
    // InternalThingML.g:5382:1: entryRuleReturnAction returns [EObject current=null] : iv_ruleReturnAction= ruleReturnAction EOF ;
    public final EObject entryRuleReturnAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReturnAction = null;


        try {
            // InternalThingML.g:5382:53: (iv_ruleReturnAction= ruleReturnAction EOF )
            // InternalThingML.g:5383:2: iv_ruleReturnAction= ruleReturnAction EOF
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
    // InternalThingML.g:5389:1: ruleReturnAction returns [EObject current=null] : (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) ) ;
    public final EObject ruleReturnAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_exp_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5395:2: ( (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:5396:2: (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:5396:2: (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) )
            // InternalThingML.g:5397:3: otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,87,FOLLOW_19); 

            			newLeafNode(otherlv_0, grammarAccess.getReturnActionAccess().getReturnKeyword_0());
            		
            // InternalThingML.g:5401:3: ( (lv_exp_1_0= ruleExpression ) )
            // InternalThingML.g:5402:4: (lv_exp_1_0= ruleExpression )
            {
            // InternalThingML.g:5402:4: (lv_exp_1_0= ruleExpression )
            // InternalThingML.g:5403:5: lv_exp_1_0= ruleExpression
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
    // InternalThingML.g:5424:1: entryRulePrintAction returns [EObject current=null] : iv_rulePrintAction= rulePrintAction EOF ;
    public final EObject entryRulePrintAction() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrintAction = null;


        try {
            // InternalThingML.g:5424:52: (iv_rulePrintAction= rulePrintAction EOF )
            // InternalThingML.g:5425:2: iv_rulePrintAction= rulePrintAction EOF
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
    // InternalThingML.g:5431:1: rulePrintAction returns [EObject current=null] : (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) ) ;
    public final EObject rulePrintAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_msg_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5437:2: ( (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:5438:2: (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:5438:2: (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) )
            // InternalThingML.g:5439:3: otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,88,FOLLOW_19); 

            			newLeafNode(otherlv_0, grammarAccess.getPrintActionAccess().getPrintKeyword_0());
            		
            // InternalThingML.g:5443:3: ( (lv_msg_1_0= ruleExpression ) )
            // InternalThingML.g:5444:4: (lv_msg_1_0= ruleExpression )
            {
            // InternalThingML.g:5444:4: (lv_msg_1_0= ruleExpression )
            // InternalThingML.g:5445:5: lv_msg_1_0= ruleExpression
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
    // InternalThingML.g:5466:1: entryRuleErrorAction returns [EObject current=null] : iv_ruleErrorAction= ruleErrorAction EOF ;
    public final EObject entryRuleErrorAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleErrorAction = null;


        try {
            // InternalThingML.g:5466:52: (iv_ruleErrorAction= ruleErrorAction EOF )
            // InternalThingML.g:5467:2: iv_ruleErrorAction= ruleErrorAction EOF
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
    // InternalThingML.g:5473:1: ruleErrorAction returns [EObject current=null] : (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) ) ;
    public final EObject ruleErrorAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_msg_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5479:2: ( (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:5480:2: (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:5480:2: (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) )
            // InternalThingML.g:5481:3: otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,89,FOLLOW_19); 

            			newLeafNode(otherlv_0, grammarAccess.getErrorActionAccess().getErrorKeyword_0());
            		
            // InternalThingML.g:5485:3: ( (lv_msg_1_0= ruleExpression ) )
            // InternalThingML.g:5486:4: (lv_msg_1_0= ruleExpression )
            {
            // InternalThingML.g:5486:4: (lv_msg_1_0= ruleExpression )
            // InternalThingML.g:5487:5: lv_msg_1_0= ruleExpression
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
    // InternalThingML.g:5508:1: entryRuleStartSession returns [EObject current=null] : iv_ruleStartSession= ruleStartSession EOF ;
    public final EObject entryRuleStartSession() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStartSession = null;


        try {
            // InternalThingML.g:5508:53: (iv_ruleStartSession= ruleStartSession EOF )
            // InternalThingML.g:5509:2: iv_ruleStartSession= ruleStartSession EOF
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
    // InternalThingML.g:5515:1: ruleStartSession returns [EObject current=null] : (otherlv_0= 'spawn' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' ( (lv_constructor_3_0= rulePropertyAssign ) )* otherlv_4= '}' ) ;
    public final EObject ruleStartSession() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_constructor_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5521:2: ( (otherlv_0= 'spawn' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' ( (lv_constructor_3_0= rulePropertyAssign ) )* otherlv_4= '}' ) )
            // InternalThingML.g:5522:2: (otherlv_0= 'spawn' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' ( (lv_constructor_3_0= rulePropertyAssign ) )* otherlv_4= '}' )
            {
            // InternalThingML.g:5522:2: (otherlv_0= 'spawn' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' ( (lv_constructor_3_0= rulePropertyAssign ) )* otherlv_4= '}' )
            // InternalThingML.g:5523:3: otherlv_0= 'spawn' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' ( (lv_constructor_3_0= rulePropertyAssign ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,90,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getStartSessionAccess().getSpawnKeyword_0());
            		
            // InternalThingML.g:5527:3: ( (otherlv_1= RULE_ID ) )
            // InternalThingML.g:5528:4: (otherlv_1= RULE_ID )
            {
            // InternalThingML.g:5528:4: (otherlv_1= RULE_ID )
            // InternalThingML.g:5529:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStartSessionRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_73); 

            					newLeafNode(otherlv_1, grammarAccess.getStartSessionAccess().getSessionSessionCrossReference_1_0());
            				

            }


            }

            otherlv_2=(Token)match(input,21,FOLLOW_74); 

            			newLeafNode(otherlv_2, grammarAccess.getStartSessionAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalThingML.g:5544:3: ( (lv_constructor_3_0= rulePropertyAssign ) )*
            loop106:
            do {
                int alt106=2;
                int LA106_0 = input.LA(1);

                if ( (LA106_0==27) ) {
                    alt106=1;
                }


                switch (alt106) {
            	case 1 :
            	    // InternalThingML.g:5545:4: (lv_constructor_3_0= rulePropertyAssign )
            	    {
            	    // InternalThingML.g:5545:4: (lv_constructor_3_0= rulePropertyAssign )
            	    // InternalThingML.g:5546:5: lv_constructor_3_0= rulePropertyAssign
            	    {

            	    					newCompositeNode(grammarAccess.getStartSessionAccess().getConstructorPropertyAssignParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_74);
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
            	    break loop106;
                }
            } while (true);

            otherlv_4=(Token)match(input,22,FOLLOW_2); 

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
    // InternalThingML.g:5571:1: entryRuleFunctionCallStatement returns [EObject current=null] : iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF ;
    public final EObject entryRuleFunctionCallStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCallStatement = null;


        try {
            // InternalThingML.g:5571:62: (iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF )
            // InternalThingML.g:5572:2: iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF
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
    // InternalThingML.g:5578:1: ruleFunctionCallStatement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) ;
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
            // InternalThingML.g:5584:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) )
            // InternalThingML.g:5585:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            {
            // InternalThingML.g:5585:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            // InternalThingML.g:5586:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')'
            {
            // InternalThingML.g:5586:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5587:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5587:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5588:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFunctionCallStatementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_0, grammarAccess.getFunctionCallStatementAccess().getFunctionFunctionCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,33,FOLLOW_68); 

            			newLeafNode(otherlv_1, grammarAccess.getFunctionCallStatementAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:5603:3: ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )?
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( ((LA108_0>=RULE_STRING_LIT && LA108_0<=RULE_FLOAT)||LA108_0==33||LA108_0==98||(LA108_0>=102 && LA108_0<=105)) ) {
                alt108=1;
            }
            switch (alt108) {
                case 1 :
                    // InternalThingML.g:5604:4: ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    {
                    // InternalThingML.g:5604:4: ( (lv_parameters_2_0= ruleExpression ) )
                    // InternalThingML.g:5605:5: (lv_parameters_2_0= ruleExpression )
                    {
                    // InternalThingML.g:5605:5: (lv_parameters_2_0= ruleExpression )
                    // InternalThingML.g:5606:6: lv_parameters_2_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_23);
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

                    // InternalThingML.g:5623:4: (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    loop107:
                    do {
                        int alt107=2;
                        int LA107_0 = input.LA(1);

                        if ( (LA107_0==26) ) {
                            alt107=1;
                        }


                        switch (alt107) {
                    	case 1 :
                    	    // InternalThingML.g:5624:5: otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,26,FOLLOW_19); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getFunctionCallStatementAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalThingML.g:5628:5: ( (lv_parameters_4_0= ruleExpression ) )
                    	    // InternalThingML.g:5629:6: (lv_parameters_4_0= ruleExpression )
                    	    {
                    	    // InternalThingML.g:5629:6: (lv_parameters_4_0= ruleExpression )
                    	    // InternalThingML.g:5630:7: lv_parameters_4_0= ruleExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getFunctionCallStatementAccess().getParametersExpressionParserRuleCall_2_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_23);
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
                    	    break loop107;
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
    // InternalThingML.g:5657:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // InternalThingML.g:5657:51: (iv_ruleExpression= ruleExpression EOF )
            // InternalThingML.g:5658:2: iv_ruleExpression= ruleExpression EOF
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
    // InternalThingML.g:5664:1: ruleExpression returns [EObject current=null] : this_OrExpression_0= ruleOrExpression ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject this_OrExpression_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5670:2: (this_OrExpression_0= ruleOrExpression )
            // InternalThingML.g:5671:2: this_OrExpression_0= ruleOrExpression
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
    // InternalThingML.g:5682:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // InternalThingML.g:5682:53: (iv_ruleOrExpression= ruleOrExpression EOF )
            // InternalThingML.g:5683:2: iv_ruleOrExpression= ruleOrExpression EOF
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
    // InternalThingML.g:5689:1: ruleOrExpression returns [EObject current=null] : (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_AndExpression_0 = null;

        EObject lv_rhs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5695:2: ( (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* ) )
            // InternalThingML.g:5696:2: (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* )
            {
            // InternalThingML.g:5696:2: (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* )
            // InternalThingML.g:5697:3: this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getOrExpressionAccess().getAndExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_75);
            this_AndExpression_0=ruleAndExpression();

            state._fsp--;


            			current = this_AndExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5705:3: ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )*
            loop109:
            do {
                int alt109=2;
                int LA109_0 = input.LA(1);

                if ( (LA109_0==91) ) {
                    alt109=1;
                }


                switch (alt109) {
            	case 1 :
            	    // InternalThingML.g:5706:4: () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) )
            	    {
            	    // InternalThingML.g:5706:4: ()
            	    // InternalThingML.g:5707:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getOrExpressionAccess().getOrExpressionLhsAction_1_0(),
            	    						current);
            	    				

            	    }

            	    otherlv_2=(Token)match(input,91,FOLLOW_19); 

            	    				newLeafNode(otherlv_2, grammarAccess.getOrExpressionAccess().getOrKeyword_1_1());
            	    			
            	    // InternalThingML.g:5717:4: ( (lv_rhs_3_0= ruleAndExpression ) )
            	    // InternalThingML.g:5718:5: (lv_rhs_3_0= ruleAndExpression )
            	    {
            	    // InternalThingML.g:5718:5: (lv_rhs_3_0= ruleAndExpression )
            	    // InternalThingML.g:5719:6: lv_rhs_3_0= ruleAndExpression
            	    {

            	    						newCompositeNode(grammarAccess.getOrExpressionAccess().getRhsAndExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_75);
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
    // $ANTLR end "ruleOrExpression"


    // $ANTLR start "entryRuleAndExpression"
    // InternalThingML.g:5741:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // InternalThingML.g:5741:54: (iv_ruleAndExpression= ruleAndExpression EOF )
            // InternalThingML.g:5742:2: iv_ruleAndExpression= ruleAndExpression EOF
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
    // InternalThingML.g:5748:1: ruleAndExpression returns [EObject current=null] : (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Equality_0 = null;

        EObject lv_rhs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5754:2: ( (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* ) )
            // InternalThingML.g:5755:2: (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* )
            {
            // InternalThingML.g:5755:2: (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* )
            // InternalThingML.g:5756:3: this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )*
            {

            			newCompositeNode(grammarAccess.getAndExpressionAccess().getEqualityParserRuleCall_0());
            		
            pushFollow(FOLLOW_76);
            this_Equality_0=ruleEquality();

            state._fsp--;


            			current = this_Equality_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5764:3: ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )*
            loop110:
            do {
                int alt110=2;
                int LA110_0 = input.LA(1);

                if ( (LA110_0==92) ) {
                    alt110=1;
                }


                switch (alt110) {
            	case 1 :
            	    // InternalThingML.g:5765:4: () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) )
            	    {
            	    // InternalThingML.g:5765:4: ()
            	    // InternalThingML.g:5766:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAndExpressionAccess().getAndExpressionLhsAction_1_0(),
            	    						current);
            	    				

            	    }

            	    otherlv_2=(Token)match(input,92,FOLLOW_19); 

            	    				newLeafNode(otherlv_2, grammarAccess.getAndExpressionAccess().getAndKeyword_1_1());
            	    			
            	    // InternalThingML.g:5776:4: ( (lv_rhs_3_0= ruleEquality ) )
            	    // InternalThingML.g:5777:5: (lv_rhs_3_0= ruleEquality )
            	    {
            	    // InternalThingML.g:5777:5: (lv_rhs_3_0= ruleEquality )
            	    // InternalThingML.g:5778:6: lv_rhs_3_0= ruleEquality
            	    {

            	    						newCompositeNode(grammarAccess.getAndExpressionAccess().getRhsEqualityParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_76);
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
            	    break loop110;
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
    // InternalThingML.g:5800:1: entryRuleEquality returns [EObject current=null] : iv_ruleEquality= ruleEquality EOF ;
    public final EObject entryRuleEquality() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEquality = null;


        try {
            // InternalThingML.g:5800:49: (iv_ruleEquality= ruleEquality EOF )
            // InternalThingML.g:5801:2: iv_ruleEquality= ruleEquality EOF
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
    // InternalThingML.g:5807:1: ruleEquality returns [EObject current=null] : (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* ) ;
    public final EObject ruleEquality() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Comparaison_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5813:2: ( (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* ) )
            // InternalThingML.g:5814:2: (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* )
            {
            // InternalThingML.g:5814:2: (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* )
            // InternalThingML.g:5815:3: this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getEqualityAccess().getComparaisonParserRuleCall_0());
            		
            pushFollow(FOLLOW_77);
            this_Comparaison_0=ruleComparaison();

            state._fsp--;


            			current = this_Comparaison_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5823:3: ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )*
            loop111:
            do {
                int alt111=3;
                int LA111_0 = input.LA(1);

                if ( (LA111_0==93) ) {
                    alt111=1;
                }
                else if ( (LA111_0==94) ) {
                    alt111=2;
                }


                switch (alt111) {
            	case 1 :
            	    // InternalThingML.g:5824:4: ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) )
            	    {
            	    // InternalThingML.g:5824:4: ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) )
            	    // InternalThingML.g:5825:5: () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) )
            	    {
            	    // InternalThingML.g:5825:5: ()
            	    // InternalThingML.g:5826:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getEqualityAccess().getEqualsExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,93,FOLLOW_19); 

            	    					newLeafNode(otherlv_2, grammarAccess.getEqualityAccess().getEqualsSignEqualsSignKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:5836:5: ( (lv_rhs_3_0= ruleComparaison ) )
            	    // InternalThingML.g:5837:6: (lv_rhs_3_0= ruleComparaison )
            	    {
            	    // InternalThingML.g:5837:6: (lv_rhs_3_0= ruleComparaison )
            	    // InternalThingML.g:5838:7: lv_rhs_3_0= ruleComparaison
            	    {

            	    							newCompositeNode(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_77);
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
            	    // InternalThingML.g:5857:4: ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) )
            	    {
            	    // InternalThingML.g:5857:4: ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) )
            	    // InternalThingML.g:5858:5: () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) )
            	    {
            	    // InternalThingML.g:5858:5: ()
            	    // InternalThingML.g:5859:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getEqualityAccess().getNotEqualsExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,94,FOLLOW_19); 

            	    					newLeafNode(otherlv_5, grammarAccess.getEqualityAccess().getExclamationMarkEqualsSignKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:5869:5: ( (lv_rhs_6_0= ruleComparaison ) )
            	    // InternalThingML.g:5870:6: (lv_rhs_6_0= ruleComparaison )
            	    {
            	    // InternalThingML.g:5870:6: (lv_rhs_6_0= ruleComparaison )
            	    // InternalThingML.g:5871:7: lv_rhs_6_0= ruleComparaison
            	    {

            	    							newCompositeNode(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_77);
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
            	    break loop111;
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
    // InternalThingML.g:5894:1: entryRuleComparaison returns [EObject current=null] : iv_ruleComparaison= ruleComparaison EOF ;
    public final EObject entryRuleComparaison() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComparaison = null;


        try {
            // InternalThingML.g:5894:52: (iv_ruleComparaison= ruleComparaison EOF )
            // InternalThingML.g:5895:2: iv_ruleComparaison= ruleComparaison EOF
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
    // InternalThingML.g:5901:1: ruleComparaison returns [EObject current=null] : (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* ) ;
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
            // InternalThingML.g:5907:2: ( (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* ) )
            // InternalThingML.g:5908:2: (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* )
            {
            // InternalThingML.g:5908:2: (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* )
            // InternalThingML.g:5909:3: this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getComparaisonAccess().getAdditionParserRuleCall_0());
            		
            pushFollow(FOLLOW_78);
            this_Addition_0=ruleAddition();

            state._fsp--;


            			current = this_Addition_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5917:3: ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )*
            loop112:
            do {
                int alt112=5;
                switch ( input.LA(1) ) {
                case 17:
                    {
                    alt112=1;
                    }
                    break;
                case 16:
                    {
                    alt112=2;
                    }
                    break;
                case 95:
                    {
                    alt112=3;
                    }
                    break;
                case 96:
                    {
                    alt112=4;
                    }
                    break;

                }

                switch (alt112) {
            	case 1 :
            	    // InternalThingML.g:5918:4: ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:5918:4: ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) )
            	    // InternalThingML.g:5919:5: () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:5919:5: ()
            	    // InternalThingML.g:5920:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getGreaterExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,17,FOLLOW_19); 

            	    					newLeafNode(otherlv_2, grammarAccess.getComparaisonAccess().getGreaterThanSignKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:5930:5: ( (lv_rhs_3_0= ruleAddition ) )
            	    // InternalThingML.g:5931:6: (lv_rhs_3_0= ruleAddition )
            	    {
            	    // InternalThingML.g:5931:6: (lv_rhs_3_0= ruleAddition )
            	    // InternalThingML.g:5932:7: lv_rhs_3_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_78);
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
            	    // InternalThingML.g:5951:4: ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:5951:4: ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) )
            	    // InternalThingML.g:5952:5: () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:5952:5: ()
            	    // InternalThingML.g:5953:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getLowerExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,16,FOLLOW_19); 

            	    					newLeafNode(otherlv_5, grammarAccess.getComparaisonAccess().getLessThanSignKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:5963:5: ( (lv_rhs_6_0= ruleAddition ) )
            	    // InternalThingML.g:5964:6: (lv_rhs_6_0= ruleAddition )
            	    {
            	    // InternalThingML.g:5964:6: (lv_rhs_6_0= ruleAddition )
            	    // InternalThingML.g:5965:7: lv_rhs_6_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_78);
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
            	    // InternalThingML.g:5984:4: ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:5984:4: ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) )
            	    // InternalThingML.g:5985:5: () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:5985:5: ()
            	    // InternalThingML.g:5986:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getGreaterOrEqualExpressionLhsAction_1_2_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_8=(Token)match(input,95,FOLLOW_19); 

            	    					newLeafNode(otherlv_8, grammarAccess.getComparaisonAccess().getGreaterThanSignEqualsSignKeyword_1_2_1());
            	    				
            	    // InternalThingML.g:5996:5: ( (lv_rhs_9_0= ruleAddition ) )
            	    // InternalThingML.g:5997:6: (lv_rhs_9_0= ruleAddition )
            	    {
            	    // InternalThingML.g:5997:6: (lv_rhs_9_0= ruleAddition )
            	    // InternalThingML.g:5998:7: lv_rhs_9_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_2_2_0());
            	    						
            	    pushFollow(FOLLOW_78);
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
            	    // InternalThingML.g:6017:4: ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:6017:4: ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) )
            	    // InternalThingML.g:6018:5: () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:6018:5: ()
            	    // InternalThingML.g:6019:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getLowerOrEqualExpressionLhsAction_1_3_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_11=(Token)match(input,96,FOLLOW_19); 

            	    					newLeafNode(otherlv_11, grammarAccess.getComparaisonAccess().getLessThanSignEqualsSignKeyword_1_3_1());
            	    				
            	    // InternalThingML.g:6029:5: ( (lv_rhs_12_0= ruleAddition ) )
            	    // InternalThingML.g:6030:6: (lv_rhs_12_0= ruleAddition )
            	    {
            	    // InternalThingML.g:6030:6: (lv_rhs_12_0= ruleAddition )
            	    // InternalThingML.g:6031:7: lv_rhs_12_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_3_2_0());
            	    						
            	    pushFollow(FOLLOW_78);
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
    // $ANTLR end "ruleComparaison"


    // $ANTLR start "entryRuleAddition"
    // InternalThingML.g:6054:1: entryRuleAddition returns [EObject current=null] : iv_ruleAddition= ruleAddition EOF ;
    public final EObject entryRuleAddition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAddition = null;


        try {
            // InternalThingML.g:6054:49: (iv_ruleAddition= ruleAddition EOF )
            // InternalThingML.g:6055:2: iv_ruleAddition= ruleAddition EOF
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
    // InternalThingML.g:6061:1: ruleAddition returns [EObject current=null] : (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* ) ;
    public final EObject ruleAddition() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Multiplication_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6067:2: ( (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* ) )
            // InternalThingML.g:6068:2: (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* )
            {
            // InternalThingML.g:6068:2: (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* )
            // InternalThingML.g:6069:3: this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0());
            		
            pushFollow(FOLLOW_79);
            this_Multiplication_0=ruleMultiplication();

            state._fsp--;


            			current = this_Multiplication_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:6077:3: ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )*
            loop113:
            do {
                int alt113=3;
                int LA113_0 = input.LA(1);

                if ( (LA113_0==97) ) {
                    alt113=1;
                }
                else if ( (LA113_0==98) ) {
                    alt113=2;
                }


                switch (alt113) {
            	case 1 :
            	    // InternalThingML.g:6078:4: ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) )
            	    {
            	    // InternalThingML.g:6078:4: ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) )
            	    // InternalThingML.g:6079:5: () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) )
            	    {
            	    // InternalThingML.g:6079:5: ()
            	    // InternalThingML.g:6080:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getAdditionAccess().getPlusExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,97,FOLLOW_19); 

            	    					newLeafNode(otherlv_2, grammarAccess.getAdditionAccess().getPlusSignKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:6090:5: ( (lv_rhs_3_0= ruleMultiplication ) )
            	    // InternalThingML.g:6091:6: (lv_rhs_3_0= ruleMultiplication )
            	    {
            	    // InternalThingML.g:6091:6: (lv_rhs_3_0= ruleMultiplication )
            	    // InternalThingML.g:6092:7: lv_rhs_3_0= ruleMultiplication
            	    {

            	    							newCompositeNode(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_79);
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
            	    // InternalThingML.g:6111:4: ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) )
            	    {
            	    // InternalThingML.g:6111:4: ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) )
            	    // InternalThingML.g:6112:5: () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) )
            	    {
            	    // InternalThingML.g:6112:5: ()
            	    // InternalThingML.g:6113:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getAdditionAccess().getMinusExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,98,FOLLOW_19); 

            	    					newLeafNode(otherlv_5, grammarAccess.getAdditionAccess().getHyphenMinusKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:6123:5: ( (lv_rhs_6_0= ruleMultiplication ) )
            	    // InternalThingML.g:6124:6: (lv_rhs_6_0= ruleMultiplication )
            	    {
            	    // InternalThingML.g:6124:6: (lv_rhs_6_0= ruleMultiplication )
            	    // InternalThingML.g:6125:7: lv_rhs_6_0= ruleMultiplication
            	    {

            	    							newCompositeNode(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_79);
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
    // $ANTLR end "ruleAddition"


    // $ANTLR start "entryRuleMultiplication"
    // InternalThingML.g:6148:1: entryRuleMultiplication returns [EObject current=null] : iv_ruleMultiplication= ruleMultiplication EOF ;
    public final EObject entryRuleMultiplication() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplication = null;


        try {
            // InternalThingML.g:6148:55: (iv_ruleMultiplication= ruleMultiplication EOF )
            // InternalThingML.g:6149:2: iv_ruleMultiplication= ruleMultiplication EOF
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
    // InternalThingML.g:6155:1: ruleMultiplication returns [EObject current=null] : (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* ) ;
    public final EObject ruleMultiplication() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Modulo_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6161:2: ( (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* ) )
            // InternalThingML.g:6162:2: (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* )
            {
            // InternalThingML.g:6162:2: (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* )
            // InternalThingML.g:6163:3: this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getMultiplicationAccess().getModuloParserRuleCall_0());
            		
            pushFollow(FOLLOW_80);
            this_Modulo_0=ruleModulo();

            state._fsp--;


            			current = this_Modulo_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:6171:3: ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )*
            loop114:
            do {
                int alt114=3;
                int LA114_0 = input.LA(1);

                if ( (LA114_0==99) ) {
                    alt114=1;
                }
                else if ( (LA114_0==100) ) {
                    alt114=2;
                }


                switch (alt114) {
            	case 1 :
            	    // InternalThingML.g:6172:4: ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) )
            	    {
            	    // InternalThingML.g:6172:4: ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) )
            	    // InternalThingML.g:6173:5: () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) )
            	    {
            	    // InternalThingML.g:6173:5: ()
            	    // InternalThingML.g:6174:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getMultiplicationAccess().getTimesExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,99,FOLLOW_19); 

            	    					newLeafNode(otherlv_2, grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:6184:5: ( (lv_rhs_3_0= ruleModulo ) )
            	    // InternalThingML.g:6185:6: (lv_rhs_3_0= ruleModulo )
            	    {
            	    // InternalThingML.g:6185:6: (lv_rhs_3_0= ruleModulo )
            	    // InternalThingML.g:6186:7: lv_rhs_3_0= ruleModulo
            	    {

            	    							newCompositeNode(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_80);
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
            	    // InternalThingML.g:6205:4: ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) )
            	    {
            	    // InternalThingML.g:6205:4: ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) )
            	    // InternalThingML.g:6206:5: () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) )
            	    {
            	    // InternalThingML.g:6206:5: ()
            	    // InternalThingML.g:6207:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getMultiplicationAccess().getDivExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,100,FOLLOW_19); 

            	    					newLeafNode(otherlv_5, grammarAccess.getMultiplicationAccess().getSolidusKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:6217:5: ( (lv_rhs_6_0= ruleModulo ) )
            	    // InternalThingML.g:6218:6: (lv_rhs_6_0= ruleModulo )
            	    {
            	    // InternalThingML.g:6218:6: (lv_rhs_6_0= ruleModulo )
            	    // InternalThingML.g:6219:7: lv_rhs_6_0= ruleModulo
            	    {

            	    							newCompositeNode(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_80);
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
    // $ANTLR end "ruleMultiplication"


    // $ANTLR start "entryRuleModulo"
    // InternalThingML.g:6242:1: entryRuleModulo returns [EObject current=null] : iv_ruleModulo= ruleModulo EOF ;
    public final EObject entryRuleModulo() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModulo = null;


        try {
            // InternalThingML.g:6242:47: (iv_ruleModulo= ruleModulo EOF )
            // InternalThingML.g:6243:2: iv_ruleModulo= ruleModulo EOF
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
    // InternalThingML.g:6249:1: ruleModulo returns [EObject current=null] : (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? ) ;
    public final EObject ruleModulo() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Primary_0 = null;

        EObject lv_rhs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6255:2: ( (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? ) )
            // InternalThingML.g:6256:2: (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? )
            {
            // InternalThingML.g:6256:2: (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? )
            // InternalThingML.g:6257:3: this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )?
            {

            			newCompositeNode(grammarAccess.getModuloAccess().getPrimaryParserRuleCall_0());
            		
            pushFollow(FOLLOW_81);
            this_Primary_0=rulePrimary();

            state._fsp--;


            			current = this_Primary_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:6265:3: ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )?
            int alt115=2;
            int LA115_0 = input.LA(1);

            if ( (LA115_0==101) ) {
                alt115=1;
            }
            switch (alt115) {
                case 1 :
                    // InternalThingML.g:6266:4: () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) )
                    {
                    // InternalThingML.g:6266:4: ()
                    // InternalThingML.g:6267:5: 
                    {

                    					current = forceCreateModelElementAndSet(
                    						grammarAccess.getModuloAccess().getModExpressionLhsAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_2=(Token)match(input,101,FOLLOW_19); 

                    				newLeafNode(otherlv_2, grammarAccess.getModuloAccess().getPercentSignKeyword_1_1());
                    			
                    // InternalThingML.g:6277:4: ( (lv_rhs_3_0= ruleExpression ) )
                    // InternalThingML.g:6278:5: (lv_rhs_3_0= ruleExpression )
                    {
                    // InternalThingML.g:6278:5: (lv_rhs_3_0= ruleExpression )
                    // InternalThingML.g:6279:6: lv_rhs_3_0= ruleExpression
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
    // InternalThingML.g:6301:1: entryRulePrimary returns [EObject current=null] : iv_rulePrimary= rulePrimary EOF ;
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary = null;


        try {
            // InternalThingML.g:6301:48: (iv_rulePrimary= rulePrimary EOF )
            // InternalThingML.g:6302:2: iv_rulePrimary= rulePrimary EOF
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
    // InternalThingML.g:6308:1: rulePrimary returns [EObject current=null] : ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_AtomicExpression_9= ruleAtomicExpression ) ;
    public final EObject rulePrimary() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_7=null;
        EObject this_Expression_1 = null;

        EObject lv_term_5_0 = null;

        EObject lv_term_8_0 = null;

        EObject this_AtomicExpression_9 = null;



        	enterRule();

        try {
            // InternalThingML.g:6314:2: ( ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_AtomicExpression_9= ruleAtomicExpression ) )
            // InternalThingML.g:6315:2: ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_AtomicExpression_9= ruleAtomicExpression )
            {
            // InternalThingML.g:6315:2: ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_AtomicExpression_9= ruleAtomicExpression )
            int alt116=4;
            switch ( input.LA(1) ) {
            case 33:
                {
                alt116=1;
                }
                break;
            case 102:
                {
                alt116=2;
                }
                break;
            case 98:
                {
                alt116=3;
                }
                break;
            case RULE_STRING_LIT:
            case RULE_ID:
            case RULE_INT:
            case RULE_STRING_EXT:
            case RULE_FLOAT:
            case 103:
            case 104:
            case 105:
                {
                alt116=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }

            switch (alt116) {
                case 1 :
                    // InternalThingML.g:6316:3: (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' )
                    {
                    // InternalThingML.g:6316:3: (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' )
                    // InternalThingML.g:6317:4: otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')'
                    {
                    otherlv_0=(Token)match(input,33,FOLLOW_19); 

                    				newLeafNode(otherlv_0, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_0_0());
                    			

                    				newCompositeNode(grammarAccess.getPrimaryAccess().getExpressionParserRuleCall_0_1());
                    			
                    pushFollow(FOLLOW_71);
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
                    // InternalThingML.g:6335:3: ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) )
                    {
                    // InternalThingML.g:6335:3: ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) )
                    // InternalThingML.g:6336:4: () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) )
                    {
                    // InternalThingML.g:6336:4: ()
                    // InternalThingML.g:6337:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPrimaryAccess().getNotExpressionAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_4=(Token)match(input,102,FOLLOW_19); 

                    				newLeafNode(otherlv_4, grammarAccess.getPrimaryAccess().getNotKeyword_1_1());
                    			
                    // InternalThingML.g:6347:4: ( (lv_term_5_0= rulePrimary ) )
                    // InternalThingML.g:6348:5: (lv_term_5_0= rulePrimary )
                    {
                    // InternalThingML.g:6348:5: (lv_term_5_0= rulePrimary )
                    // InternalThingML.g:6349:6: lv_term_5_0= rulePrimary
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
                    // InternalThingML.g:6368:3: ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) )
                    {
                    // InternalThingML.g:6368:3: ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) )
                    // InternalThingML.g:6369:4: () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) )
                    {
                    // InternalThingML.g:6369:4: ()
                    // InternalThingML.g:6370:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPrimaryAccess().getUnaryMinusAction_2_0(),
                    						current);
                    				

                    }

                    otherlv_7=(Token)match(input,98,FOLLOW_19); 

                    				newLeafNode(otherlv_7, grammarAccess.getPrimaryAccess().getHyphenMinusKeyword_2_1());
                    			
                    // InternalThingML.g:6380:4: ( (lv_term_8_0= rulePrimary ) )
                    // InternalThingML.g:6381:5: (lv_term_8_0= rulePrimary )
                    {
                    // InternalThingML.g:6381:5: (lv_term_8_0= rulePrimary )
                    // InternalThingML.g:6382:6: lv_term_8_0= rulePrimary
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
                    // InternalThingML.g:6401:3: this_AtomicExpression_9= ruleAtomicExpression
                    {

                    			newCompositeNode(grammarAccess.getPrimaryAccess().getAtomicExpressionParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_AtomicExpression_9=ruleAtomicExpression();

                    state._fsp--;


                    			current = this_AtomicExpression_9;
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


    // $ANTLR start "entryRuleAtomicExpression"
    // InternalThingML.g:6413:1: entryRuleAtomicExpression returns [EObject current=null] : iv_ruleAtomicExpression= ruleAtomicExpression EOF ;
    public final EObject entryRuleAtomicExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtomicExpression = null;


        try {
            // InternalThingML.g:6413:57: (iv_ruleAtomicExpression= ruleAtomicExpression EOF )
            // InternalThingML.g:6414:2: iv_ruleAtomicExpression= ruleAtomicExpression EOF
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
    // InternalThingML.g:6420:1: ruleAtomicExpression returns [EObject current=null] : (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_Reference_7= ruleReference | this_FunctionCallExpression_8= ruleFunctionCallExpression | this_MessageParameter_9= ruleMessageParameter ) ;
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

        EObject this_MessageParameter_9 = null;



        	enterRule();

        try {
            // InternalThingML.g:6426:2: ( (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_Reference_7= ruleReference | this_FunctionCallExpression_8= ruleFunctionCallExpression | this_MessageParameter_9= ruleMessageParameter ) )
            // InternalThingML.g:6427:2: (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_Reference_7= ruleReference | this_FunctionCallExpression_8= ruleFunctionCallExpression | this_MessageParameter_9= ruleMessageParameter )
            {
            // InternalThingML.g:6427:2: (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_Reference_7= ruleReference | this_FunctionCallExpression_8= ruleFunctionCallExpression | this_MessageParameter_9= ruleMessageParameter )
            int alt117=10;
            alt117 = dfa117.predict(input);
            switch (alt117) {
                case 1 :
                    // InternalThingML.g:6428:3: this_ExternExpression_0= ruleExternExpression
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
                    // InternalThingML.g:6437:3: this_EnumLiteralRef_1= ruleEnumLiteralRef
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
                    // InternalThingML.g:6446:3: this_IntegerLiteral_2= ruleIntegerLiteral
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
                    // InternalThingML.g:6455:3: this_BooleanLiteral_3= ruleBooleanLiteral
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
                    // InternalThingML.g:6464:3: this_StringLiteral_4= ruleStringLiteral
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
                    // InternalThingML.g:6473:3: this_DoubleLiteral_5= ruleDoubleLiteral
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
                    // InternalThingML.g:6482:3: this_PropertyReference_6= rulePropertyReference
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
                    // InternalThingML.g:6491:3: this_Reference_7= ruleReference
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
                    // InternalThingML.g:6500:3: this_FunctionCallExpression_8= ruleFunctionCallExpression
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getFunctionCallExpressionParserRuleCall_8());
                    		
                    pushFollow(FOLLOW_2);
                    this_FunctionCallExpression_8=ruleFunctionCallExpression();

                    state._fsp--;


                    			current = this_FunctionCallExpression_8;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 10 :
                    // InternalThingML.g:6509:3: this_MessageParameter_9= ruleMessageParameter
                    {

                    			newCompositeNode(grammarAccess.getAtomicExpressionAccess().getMessageParameterParserRuleCall_9());
                    		
                    pushFollow(FOLLOW_2);
                    this_MessageParameter_9=ruleMessageParameter();

                    state._fsp--;


                    			current = this_MessageParameter_9;
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
    // InternalThingML.g:6521:1: entryRuleExternExpression returns [EObject current=null] : iv_ruleExternExpression= ruleExternExpression EOF ;
    public final EObject entryRuleExternExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternExpression = null;


        try {
            // InternalThingML.g:6521:57: (iv_ruleExternExpression= ruleExternExpression EOF )
            // InternalThingML.g:6522:2: iv_ruleExternExpression= ruleExternExpression EOF
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
    // InternalThingML.g:6528:1: ruleExternExpression returns [EObject current=null] : ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) ;
    public final EObject ruleExternExpression() throws RecognitionException {
        EObject current = null;

        Token lv_expression_0_0=null;
        Token otherlv_1=null;
        EObject lv_segments_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6534:2: ( ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) )
            // InternalThingML.g:6535:2: ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            {
            // InternalThingML.g:6535:2: ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            // InternalThingML.g:6536:3: ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            {
            // InternalThingML.g:6536:3: ( (lv_expression_0_0= RULE_STRING_EXT ) )
            // InternalThingML.g:6537:4: (lv_expression_0_0= RULE_STRING_EXT )
            {
            // InternalThingML.g:6537:4: (lv_expression_0_0= RULE_STRING_EXT )
            // InternalThingML.g:6538:5: lv_expression_0_0= RULE_STRING_EXT
            {
            lv_expression_0_0=(Token)match(input,RULE_STRING_EXT,FOLLOW_65); 

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

            // InternalThingML.g:6554:3: (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            loop118:
            do {
                int alt118=2;
                int LA118_0 = input.LA(1);

                if ( (LA118_0==50) ) {
                    alt118=1;
                }


                switch (alt118) {
            	case 1 :
            	    // InternalThingML.g:6555:4: otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,50,FOLLOW_19); 

            	    				newLeafNode(otherlv_1, grammarAccess.getExternExpressionAccess().getAmpersandKeyword_1_0());
            	    			
            	    // InternalThingML.g:6559:4: ( (lv_segments_2_0= ruleExpression ) )
            	    // InternalThingML.g:6560:5: (lv_segments_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:6560:5: (lv_segments_2_0= ruleExpression )
            	    // InternalThingML.g:6561:6: lv_segments_2_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getExternExpressionAccess().getSegmentsExpressionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_65);
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
    // $ANTLR end "ruleExternExpression"


    // $ANTLR start "entryRuleEnumLiteralRef"
    // InternalThingML.g:6583:1: entryRuleEnumLiteralRef returns [EObject current=null] : iv_ruleEnumLiteralRef= ruleEnumLiteralRef EOF ;
    public final EObject entryRuleEnumLiteralRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumLiteralRef = null;


        try {
            // InternalThingML.g:6583:55: (iv_ruleEnumLiteralRef= ruleEnumLiteralRef EOF )
            // InternalThingML.g:6584:2: iv_ruleEnumLiteralRef= ruleEnumLiteralRef EOF
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
    // InternalThingML.g:6590:1: ruleEnumLiteralRef returns [EObject current=null] : (otherlv_0= 'enum' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ) ;
    public final EObject ruleEnumLiteralRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalThingML.g:6596:2: ( (otherlv_0= 'enum' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ) )
            // InternalThingML.g:6597:2: (otherlv_0= 'enum' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) )
            {
            // InternalThingML.g:6597:2: (otherlv_0= 'enum' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) )
            // InternalThingML.g:6598:3: otherlv_0= 'enum' ( (otherlv_1= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,103,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getEnumLiteralRefAccess().getEnumKeyword_0());
            		
            // InternalThingML.g:6602:3: ( (otherlv_1= RULE_ID ) )
            // InternalThingML.g:6603:4: (otherlv_1= RULE_ID )
            {
            // InternalThingML.g:6603:4: (otherlv_1= RULE_ID )
            // InternalThingML.g:6604:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEnumLiteralRefRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_25); 

            					newLeafNode(otherlv_1, grammarAccess.getEnumLiteralRefAccess().getEnumEnumerationCrossReference_1_0());
            				

            }


            }

            otherlv_2=(Token)match(input,35,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getEnumLiteralRefAccess().getColonKeyword_2());
            		
            // InternalThingML.g:6619:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:6620:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:6620:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:6621:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEnumLiteralRefRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_3, grammarAccess.getEnumLiteralRefAccess().getLiteralEnumerationLiteralCrossReference_3_0());
            				

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
    // InternalThingML.g:6636:1: entryRuleIntegerLiteral returns [EObject current=null] : iv_ruleIntegerLiteral= ruleIntegerLiteral EOF ;
    public final EObject entryRuleIntegerLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIntegerLiteral = null;


        try {
            // InternalThingML.g:6636:55: (iv_ruleIntegerLiteral= ruleIntegerLiteral EOF )
            // InternalThingML.g:6637:2: iv_ruleIntegerLiteral= ruleIntegerLiteral EOF
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
    // InternalThingML.g:6643:1: ruleIntegerLiteral returns [EObject current=null] : ( (lv_intValue_0_0= RULE_INT ) ) ;
    public final EObject ruleIntegerLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_intValue_0_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6649:2: ( ( (lv_intValue_0_0= RULE_INT ) ) )
            // InternalThingML.g:6650:2: ( (lv_intValue_0_0= RULE_INT ) )
            {
            // InternalThingML.g:6650:2: ( (lv_intValue_0_0= RULE_INT ) )
            // InternalThingML.g:6651:3: (lv_intValue_0_0= RULE_INT )
            {
            // InternalThingML.g:6651:3: (lv_intValue_0_0= RULE_INT )
            // InternalThingML.g:6652:4: lv_intValue_0_0= RULE_INT
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
    // InternalThingML.g:6671:1: entryRuleBooleanLiteral returns [EObject current=null] : iv_ruleBooleanLiteral= ruleBooleanLiteral EOF ;
    public final EObject entryRuleBooleanLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBooleanLiteral = null;


        try {
            // InternalThingML.g:6671:55: (iv_ruleBooleanLiteral= ruleBooleanLiteral EOF )
            // InternalThingML.g:6672:2: iv_ruleBooleanLiteral= ruleBooleanLiteral EOF
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
    // InternalThingML.g:6678:1: ruleBooleanLiteral returns [EObject current=null] : ( ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) ) ) ;
    public final EObject ruleBooleanLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_boolValue_0_1=null;
        Token lv_boolValue_0_2=null;


        	enterRule();

        try {
            // InternalThingML.g:6684:2: ( ( ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) ) ) )
            // InternalThingML.g:6685:2: ( ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) ) )
            {
            // InternalThingML.g:6685:2: ( ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) ) )
            // InternalThingML.g:6686:3: ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) )
            {
            // InternalThingML.g:6686:3: ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) )
            // InternalThingML.g:6687:4: (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' )
            {
            // InternalThingML.g:6687:4: (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' )
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==104) ) {
                alt119=1;
            }
            else if ( (LA119_0==105) ) {
                alt119=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 119, 0, input);

                throw nvae;
            }
            switch (alt119) {
                case 1 :
                    // InternalThingML.g:6688:5: lv_boolValue_0_1= 'true'
                    {
                    lv_boolValue_0_1=(Token)match(input,104,FOLLOW_2); 

                    					newLeafNode(lv_boolValue_0_1, grammarAccess.getBooleanLiteralAccess().getBoolValueTrueKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getBooleanLiteralRule());
                    					}
                    					setWithLastConsumed(current, "boolValue", lv_boolValue_0_1, null);
                    				

                    }
                    break;
                case 2 :
                    // InternalThingML.g:6699:5: lv_boolValue_0_2= 'false'
                    {
                    lv_boolValue_0_2=(Token)match(input,105,FOLLOW_2); 

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
    // InternalThingML.g:6715:1: entryRuleStringLiteral returns [EObject current=null] : iv_ruleStringLiteral= ruleStringLiteral EOF ;
    public final EObject entryRuleStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStringLiteral = null;


        try {
            // InternalThingML.g:6715:54: (iv_ruleStringLiteral= ruleStringLiteral EOF )
            // InternalThingML.g:6716:2: iv_ruleStringLiteral= ruleStringLiteral EOF
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
    // InternalThingML.g:6722:1: ruleStringLiteral returns [EObject current=null] : ( (lv_stringValue_0_0= RULE_STRING_LIT ) ) ;
    public final EObject ruleStringLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_stringValue_0_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6728:2: ( ( (lv_stringValue_0_0= RULE_STRING_LIT ) ) )
            // InternalThingML.g:6729:2: ( (lv_stringValue_0_0= RULE_STRING_LIT ) )
            {
            // InternalThingML.g:6729:2: ( (lv_stringValue_0_0= RULE_STRING_LIT ) )
            // InternalThingML.g:6730:3: (lv_stringValue_0_0= RULE_STRING_LIT )
            {
            // InternalThingML.g:6730:3: (lv_stringValue_0_0= RULE_STRING_LIT )
            // InternalThingML.g:6731:4: lv_stringValue_0_0= RULE_STRING_LIT
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
    // InternalThingML.g:6750:1: entryRuleDoubleLiteral returns [EObject current=null] : iv_ruleDoubleLiteral= ruleDoubleLiteral EOF ;
    public final EObject entryRuleDoubleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoubleLiteral = null;


        try {
            // InternalThingML.g:6750:54: (iv_ruleDoubleLiteral= ruleDoubleLiteral EOF )
            // InternalThingML.g:6751:2: iv_ruleDoubleLiteral= ruleDoubleLiteral EOF
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
    // InternalThingML.g:6757:1: ruleDoubleLiteral returns [EObject current=null] : ( (lv_doubleValue_0_0= RULE_FLOAT ) ) ;
    public final EObject ruleDoubleLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_doubleValue_0_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6763:2: ( ( (lv_doubleValue_0_0= RULE_FLOAT ) ) )
            // InternalThingML.g:6764:2: ( (lv_doubleValue_0_0= RULE_FLOAT ) )
            {
            // InternalThingML.g:6764:2: ( (lv_doubleValue_0_0= RULE_FLOAT ) )
            // InternalThingML.g:6765:3: (lv_doubleValue_0_0= RULE_FLOAT )
            {
            // InternalThingML.g:6765:3: (lv_doubleValue_0_0= RULE_FLOAT )
            // InternalThingML.g:6766:4: lv_doubleValue_0_0= RULE_FLOAT
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
    // InternalThingML.g:6785:1: entryRulePropertyReference returns [EObject current=null] : iv_rulePropertyReference= rulePropertyReference EOF ;
    public final EObject entryRulePropertyReference() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePropertyReference = null;


        try {
            // InternalThingML.g:6785:58: (iv_rulePropertyReference= rulePropertyReference EOF )
            // InternalThingML.g:6786:2: iv_rulePropertyReference= rulePropertyReference EOF
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
    // InternalThingML.g:6792:1: rulePropertyReference returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject rulePropertyReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6798:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalThingML.g:6799:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalThingML.g:6799:2: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:6800:3: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:6800:3: (otherlv_0= RULE_ID )
            // InternalThingML.g:6801:4: otherlv_0= RULE_ID
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
    // InternalThingML.g:6815:1: entryRuleReference returns [EObject current=null] : iv_ruleReference= ruleReference EOF ;
    public final EObject entryRuleReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReference = null;


        try {
            // InternalThingML.g:6815:50: (iv_ruleReference= ruleReference EOF )
            // InternalThingML.g:6816:2: iv_ruleReference= ruleReference EOF
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
    // InternalThingML.g:6822:1: ruleReference returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (lv_parameter_2_0= ruleElmtProperty ) ) ) ;
    public final EObject ruleReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_parameter_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6828:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (lv_parameter_2_0= ruleElmtProperty ) ) ) )
            // InternalThingML.g:6829:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (lv_parameter_2_0= ruleElmtProperty ) ) )
            {
            // InternalThingML.g:6829:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (lv_parameter_2_0= ruleElmtProperty ) ) )
            // InternalThingML.g:6830:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (lv_parameter_2_0= ruleElmtProperty ) )
            {
            // InternalThingML.g:6830:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:6831:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:6831:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:6832:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReferenceRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_82); 

            					newLeafNode(otherlv_0, grammarAccess.getReferenceAccess().getReferenceReferencedElmtCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,106,FOLLOW_83); 

            			newLeafNode(otherlv_1, grammarAccess.getReferenceAccess().getFullStopKeyword_1());
            		
            // InternalThingML.g:6847:3: ( (lv_parameter_2_0= ruleElmtProperty ) )
            // InternalThingML.g:6848:4: (lv_parameter_2_0= ruleElmtProperty )
            {
            // InternalThingML.g:6848:4: (lv_parameter_2_0= ruleElmtProperty )
            // InternalThingML.g:6849:5: lv_parameter_2_0= ruleElmtProperty
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
    // InternalThingML.g:6870:1: entryRuleFunctionCallExpression returns [EObject current=null] : iv_ruleFunctionCallExpression= ruleFunctionCallExpression EOF ;
    public final EObject entryRuleFunctionCallExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCallExpression = null;


        try {
            // InternalThingML.g:6870:63: (iv_ruleFunctionCallExpression= ruleFunctionCallExpression EOF )
            // InternalThingML.g:6871:2: iv_ruleFunctionCallExpression= ruleFunctionCallExpression EOF
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
    // InternalThingML.g:6877:1: ruleFunctionCallExpression returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) ;
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
            // InternalThingML.g:6883:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) )
            // InternalThingML.g:6884:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            {
            // InternalThingML.g:6884:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            // InternalThingML.g:6885:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')'
            {
            // InternalThingML.g:6885:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:6886:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:6886:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:6887:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFunctionCallExpressionRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_0, grammarAccess.getFunctionCallExpressionAccess().getFunctionFunctionCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,33,FOLLOW_68); 

            			newLeafNode(otherlv_1, grammarAccess.getFunctionCallExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:6902:3: ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )?
            int alt121=2;
            int LA121_0 = input.LA(1);

            if ( ((LA121_0>=RULE_STRING_LIT && LA121_0<=RULE_FLOAT)||LA121_0==33||LA121_0==98||(LA121_0>=102 && LA121_0<=105)) ) {
                alt121=1;
            }
            switch (alt121) {
                case 1 :
                    // InternalThingML.g:6903:4: ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    {
                    // InternalThingML.g:6903:4: ( (lv_parameters_2_0= ruleExpression ) )
                    // InternalThingML.g:6904:5: (lv_parameters_2_0= ruleExpression )
                    {
                    // InternalThingML.g:6904:5: (lv_parameters_2_0= ruleExpression )
                    // InternalThingML.g:6905:6: lv_parameters_2_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getFunctionCallExpressionAccess().getParametersExpressionParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_23);
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

                    // InternalThingML.g:6922:4: (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    loop120:
                    do {
                        int alt120=2;
                        int LA120_0 = input.LA(1);

                        if ( (LA120_0==26) ) {
                            alt120=1;
                        }


                        switch (alt120) {
                    	case 1 :
                    	    // InternalThingML.g:6923:5: otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,26,FOLLOW_19); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getFunctionCallExpressionAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalThingML.g:6927:5: ( (lv_parameters_4_0= ruleExpression ) )
                    	    // InternalThingML.g:6928:6: (lv_parameters_4_0= ruleExpression )
                    	    {
                    	    // InternalThingML.g:6928:6: (lv_parameters_4_0= ruleExpression )
                    	    // InternalThingML.g:6929:7: lv_parameters_4_0= ruleExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getFunctionCallExpressionAccess().getParametersExpressionParserRuleCall_2_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_23);
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
                    	    break loop120;
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
    // InternalThingML.g:6956:1: entryRuleConfiguration returns [EObject current=null] : iv_ruleConfiguration= ruleConfiguration EOF ;
    public final EObject entryRuleConfiguration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConfiguration = null;


        try {
            // InternalThingML.g:6956:54: (iv_ruleConfiguration= ruleConfiguration EOF )
            // InternalThingML.g:6957:2: iv_ruleConfiguration= ruleConfiguration EOF
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
    // InternalThingML.g:6963:1: ruleConfiguration returns [EObject current=null] : (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' ) ;
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
            // InternalThingML.g:6969:2: ( (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' ) )
            // InternalThingML.g:6970:2: (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' )
            {
            // InternalThingML.g:6970:2: (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' )
            // InternalThingML.g:6971:3: otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,107,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConfigurationAccess().getConfigurationKeyword_0());
            		
            // InternalThingML.g:6975:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:6976:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:6976:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:6977:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:6993:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop122:
            do {
                int alt122=2;
                int LA122_0 = input.LA(1);

                if ( (LA122_0==14) ) {
                    alt122=1;
                }


                switch (alt122) {
            	case 1 :
            	    // InternalThingML.g:6994:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:6994:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:6995:5: lv_annotations_2_0= rulePlatformAnnotation
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
            	    break loop122;
                }
            } while (true);

            otherlv_3=(Token)match(input,21,FOLLOW_84); 

            			newLeafNode(otherlv_3, grammarAccess.getConfigurationAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalThingML.g:7016:3: ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )*
            loop123:
            do {
                int alt123=4;
                switch ( input.LA(1) ) {
                case 108:
                    {
                    alt123=1;
                    }
                    break;
                case 109:
                    {
                    alt123=2;
                    }
                    break;
                case 27:
                    {
                    alt123=3;
                    }
                    break;

                }

                switch (alt123) {
            	case 1 :
            	    // InternalThingML.g:7017:4: ( (lv_instances_4_0= ruleInstance ) )
            	    {
            	    // InternalThingML.g:7017:4: ( (lv_instances_4_0= ruleInstance ) )
            	    // InternalThingML.g:7018:5: (lv_instances_4_0= ruleInstance )
            	    {
            	    // InternalThingML.g:7018:5: (lv_instances_4_0= ruleInstance )
            	    // InternalThingML.g:7019:6: lv_instances_4_0= ruleInstance
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
            	    // InternalThingML.g:7037:4: ( (lv_connectors_5_0= ruleAbstractConnector ) )
            	    {
            	    // InternalThingML.g:7037:4: ( (lv_connectors_5_0= ruleAbstractConnector ) )
            	    // InternalThingML.g:7038:5: (lv_connectors_5_0= ruleAbstractConnector )
            	    {
            	    // InternalThingML.g:7038:5: (lv_connectors_5_0= ruleAbstractConnector )
            	    // InternalThingML.g:7039:6: lv_connectors_5_0= ruleAbstractConnector
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
            	    // InternalThingML.g:7057:4: ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) )
            	    {
            	    // InternalThingML.g:7057:4: ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) )
            	    // InternalThingML.g:7058:5: (lv_propassigns_6_0= ruleConfigPropertyAssign )
            	    {
            	    // InternalThingML.g:7058:5: (lv_propassigns_6_0= ruleConfigPropertyAssign )
            	    // InternalThingML.g:7059:6: lv_propassigns_6_0= ruleConfigPropertyAssign
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
            	    break loop123;
                }
            } while (true);

            otherlv_7=(Token)match(input,22,FOLLOW_2); 

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
    // InternalThingML.g:7085:1: entryRuleInstance returns [EObject current=null] : iv_ruleInstance= ruleInstance EOF ;
    public final EObject entryRuleInstance() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInstance = null;


        try {
            // InternalThingML.g:7085:49: (iv_ruleInstance= ruleInstance EOF )
            // InternalThingML.g:7086:2: iv_ruleInstance= ruleInstance EOF
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
    // InternalThingML.g:7092:1: ruleInstance returns [EObject current=null] : (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleInstance() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject lv_annotations_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:7098:2: ( (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:7099:2: (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:7099:2: (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:7100:3: otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,108,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getInstanceAccess().getInstanceKeyword_0());
            		
            // InternalThingML.g:7104:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:7105:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:7105:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:7106:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_25); 

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
            		
            // InternalThingML.g:7126:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:7127:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:7127:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:7128:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getInstanceRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_13); 

            					newLeafNode(otherlv_3, grammarAccess.getInstanceAccess().getTypeThingCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:7139:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop124:
            do {
                int alt124=2;
                int LA124_0 = input.LA(1);

                if ( (LA124_0==14) ) {
                    alt124=1;
                }


                switch (alt124) {
            	case 1 :
            	    // InternalThingML.g:7140:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:7140:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:7141:5: lv_annotations_4_0= rulePlatformAnnotation
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
    // $ANTLR end "ruleInstance"


    // $ANTLR start "entryRuleConfigPropertyAssign"
    // InternalThingML.g:7162:1: entryRuleConfigPropertyAssign returns [EObject current=null] : iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF ;
    public final EObject entryRuleConfigPropertyAssign() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConfigPropertyAssign = null;


        try {
            // InternalThingML.g:7162:61: (iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF )
            // InternalThingML.g:7163:2: iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF
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
    // InternalThingML.g:7169:1: ruleConfigPropertyAssign returns [EObject current=null] : (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:7175:2: ( (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:7176:2: (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:7176:2: (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:7177:3: otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,27,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConfigPropertyAssignAccess().getSetKeyword_0());
            		
            // InternalThingML.g:7181:3: ( (lv_instance_1_0= ruleInstanceRef ) )
            // InternalThingML.g:7182:4: (lv_instance_1_0= ruleInstanceRef )
            {
            // InternalThingML.g:7182:4: (lv_instance_1_0= ruleInstanceRef )
            // InternalThingML.g:7183:5: lv_instance_1_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getInstanceInstanceRefParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_82);
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

            otherlv_2=(Token)match(input,106,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getConfigPropertyAssignAccess().getFullStopKeyword_2());
            		
            // InternalThingML.g:7204:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:7205:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:7205:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:7206:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConfigPropertyAssignRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_18); 

            					newLeafNode(otherlv_3, grammarAccess.getConfigPropertyAssignAccess().getPropertyPropertyCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:7217:3: (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )*
            loop125:
            do {
                int alt125=2;
                int LA125_0 = input.LA(1);

                if ( (LA125_0==28) ) {
                    alt125=1;
                }


                switch (alt125) {
            	case 1 :
            	    // InternalThingML.g:7218:4: otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']'
            	    {
            	    otherlv_4=(Token)match(input,28,FOLLOW_19); 

            	    				newLeafNode(otherlv_4, grammarAccess.getConfigPropertyAssignAccess().getLeftSquareBracketKeyword_4_0());
            	    			
            	    // InternalThingML.g:7222:4: ( (lv_index_5_0= ruleExpression ) )
            	    // InternalThingML.g:7223:5: (lv_index_5_0= ruleExpression )
            	    {
            	    // InternalThingML.g:7223:5: (lv_index_5_0= ruleExpression )
            	    // InternalThingML.g:7224:6: lv_index_5_0= ruleExpression
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

            	    otherlv_6=(Token)match(input,29,FOLLOW_18); 

            	    				newLeafNode(otherlv_6, grammarAccess.getConfigPropertyAssignAccess().getRightSquareBracketKeyword_4_2());
            	    			

            	    }
            	    break;

            	default :
            	    break loop125;
                }
            } while (true);

            otherlv_7=(Token)match(input,30,FOLLOW_19); 

            			newLeafNode(otherlv_7, grammarAccess.getConfigPropertyAssignAccess().getEqualsSignKeyword_5());
            		
            // InternalThingML.g:7250:3: ( (lv_init_8_0= ruleExpression ) )
            // InternalThingML.g:7251:4: (lv_init_8_0= ruleExpression )
            {
            // InternalThingML.g:7251:4: (lv_init_8_0= ruleExpression )
            // InternalThingML.g:7252:5: lv_init_8_0= ruleExpression
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

            // InternalThingML.g:7269:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop126:
            do {
                int alt126=2;
                int LA126_0 = input.LA(1);

                if ( (LA126_0==14) ) {
                    alt126=1;
                }


                switch (alt126) {
            	case 1 :
            	    // InternalThingML.g:7270:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:7270:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:7271:5: lv_annotations_9_0= rulePlatformAnnotation
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
            	    break loop126;
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
    // InternalThingML.g:7292:1: entryRuleAbstractConnector returns [EObject current=null] : iv_ruleAbstractConnector= ruleAbstractConnector EOF ;
    public final EObject entryRuleAbstractConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAbstractConnector = null;


        try {
            // InternalThingML.g:7292:58: (iv_ruleAbstractConnector= ruleAbstractConnector EOF )
            // InternalThingML.g:7293:2: iv_ruleAbstractConnector= ruleAbstractConnector EOF
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
    // InternalThingML.g:7299:1: ruleAbstractConnector returns [EObject current=null] : (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector ) ;
    public final EObject ruleAbstractConnector() throws RecognitionException {
        EObject current = null;

        EObject this_Connector_0 = null;

        EObject this_ExternalConnector_1 = null;



        	enterRule();

        try {
            // InternalThingML.g:7305:2: ( (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector ) )
            // InternalThingML.g:7306:2: (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector )
            {
            // InternalThingML.g:7306:2: (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector )
            int alt127=2;
            int LA127_0 = input.LA(1);

            if ( (LA127_0==109) ) {
                int LA127_1 = input.LA(2);

                if ( (LA127_1==RULE_ID) ) {
                    int LA127_2 = input.LA(3);

                    if ( (LA127_2==RULE_ID) ) {
                        int LA127_3 = input.LA(4);

                        if ( (LA127_3==106) ) {
                            int LA127_4 = input.LA(5);

                            if ( (LA127_4==RULE_ID) ) {
                                int LA127_5 = input.LA(6);

                                if ( (LA127_5==111) ) {
                                    alt127=2;
                                }
                                else if ( (LA127_5==110) ) {
                                    alt127=1;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 127, 5, input);

                                    throw nvae;
                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 127, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 127, 3, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA127_2==106) ) {
                        int LA127_4 = input.LA(4);

                        if ( (LA127_4==RULE_ID) ) {
                            int LA127_5 = input.LA(5);

                            if ( (LA127_5==111) ) {
                                alt127=2;
                            }
                            else if ( (LA127_5==110) ) {
                                alt127=1;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 127, 5, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 127, 4, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 127, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 127, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 127, 0, input);

                throw nvae;
            }
            switch (alt127) {
                case 1 :
                    // InternalThingML.g:7307:3: this_Connector_0= ruleConnector
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
                    // InternalThingML.g:7316:3: this_ExternalConnector_1= ruleExternalConnector
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
    // InternalThingML.g:7328:1: entryRuleConnector returns [EObject current=null] : iv_ruleConnector= ruleConnector EOF ;
    public final EObject entryRuleConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConnector = null;


        try {
            // InternalThingML.g:7328:50: (iv_ruleConnector= ruleConnector EOF )
            // InternalThingML.g:7329:2: iv_ruleConnector= ruleConnector EOF
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
    // InternalThingML.g:7335:1: ruleConnector returns [EObject current=null] : (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:7341:2: ( (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:7342:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:7342:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:7343:3: otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,109,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConnectorAccess().getConnectorKeyword_0());
            		
            // InternalThingML.g:7347:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt128=2;
            int LA128_0 = input.LA(1);

            if ( (LA128_0==RULE_ID) ) {
                int LA128_1 = input.LA(2);

                if ( (LA128_1==RULE_ID) ) {
                    alt128=1;
                }
            }
            switch (alt128) {
                case 1 :
                    // InternalThingML.g:7348:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:7348:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:7349:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:7365:3: ( (lv_cli_2_0= ruleInstanceRef ) )
            // InternalThingML.g:7366:4: (lv_cli_2_0= ruleInstanceRef )
            {
            // InternalThingML.g:7366:4: (lv_cli_2_0= ruleInstanceRef )
            // InternalThingML.g:7367:5: lv_cli_2_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getConnectorAccess().getCliInstanceRefParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_82);
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

            otherlv_3=(Token)match(input,106,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getConnectorAccess().getFullStopKeyword_3());
            		
            // InternalThingML.g:7388:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:7389:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:7389:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:7390:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConnectorRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_85); 

            					newLeafNode(otherlv_4, grammarAccess.getConnectorAccess().getRequiredRequiredPortCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,110,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getConnectorAccess().getEqualsSignGreaterThanSignKeyword_5());
            		
            // InternalThingML.g:7405:3: ( (lv_srv_6_0= ruleInstanceRef ) )
            // InternalThingML.g:7406:4: (lv_srv_6_0= ruleInstanceRef )
            {
            // InternalThingML.g:7406:4: (lv_srv_6_0= ruleInstanceRef )
            // InternalThingML.g:7407:5: lv_srv_6_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getConnectorAccess().getSrvInstanceRefParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_82);
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

            otherlv_7=(Token)match(input,106,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getConnectorAccess().getFullStopKeyword_7());
            		
            // InternalThingML.g:7428:3: ( (otherlv_8= RULE_ID ) )
            // InternalThingML.g:7429:4: (otherlv_8= RULE_ID )
            {
            // InternalThingML.g:7429:4: (otherlv_8= RULE_ID )
            // InternalThingML.g:7430:5: otherlv_8= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConnectorRule());
            					}
            				
            otherlv_8=(Token)match(input,RULE_ID,FOLLOW_13); 

            					newLeafNode(otherlv_8, grammarAccess.getConnectorAccess().getProvidedProvidedPortCrossReference_8_0());
            				

            }


            }

            // InternalThingML.g:7441:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop129:
            do {
                int alt129=2;
                int LA129_0 = input.LA(1);

                if ( (LA129_0==14) ) {
                    alt129=1;
                }


                switch (alt129) {
            	case 1 :
            	    // InternalThingML.g:7442:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:7442:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:7443:5: lv_annotations_9_0= rulePlatformAnnotation
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
    // $ANTLR end "ruleConnector"


    // $ANTLR start "entryRuleExternalConnector"
    // InternalThingML.g:7464:1: entryRuleExternalConnector returns [EObject current=null] : iv_ruleExternalConnector= ruleExternalConnector EOF ;
    public final EObject entryRuleExternalConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternalConnector = null;


        try {
            // InternalThingML.g:7464:58: (iv_ruleExternalConnector= ruleExternalConnector EOF )
            // InternalThingML.g:7465:2: iv_ruleExternalConnector= ruleExternalConnector EOF
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
    // InternalThingML.g:7471:1: ruleExternalConnector returns [EObject current=null] : (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:7477:2: ( (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:7478:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:7478:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:7479:3: otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,109,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getExternalConnectorAccess().getConnectorKeyword_0());
            		
            // InternalThingML.g:7483:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt130=2;
            int LA130_0 = input.LA(1);

            if ( (LA130_0==RULE_ID) ) {
                int LA130_1 = input.LA(2);

                if ( (LA130_1==RULE_ID) ) {
                    alt130=1;
                }
            }
            switch (alt130) {
                case 1 :
                    // InternalThingML.g:7484:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:7484:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:7485:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:7501:3: ( (lv_inst_2_0= ruleInstanceRef ) )
            // InternalThingML.g:7502:4: (lv_inst_2_0= ruleInstanceRef )
            {
            // InternalThingML.g:7502:4: (lv_inst_2_0= ruleInstanceRef )
            // InternalThingML.g:7503:5: lv_inst_2_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getExternalConnectorAccess().getInstInstanceRefParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_82);
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

            otherlv_3=(Token)match(input,106,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getExternalConnectorAccess().getFullStopKeyword_3());
            		
            // InternalThingML.g:7524:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:7525:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:7525:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:7526:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalConnectorRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_86); 

            					newLeafNode(otherlv_4, grammarAccess.getExternalConnectorAccess().getPortPortCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,111,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getExternalConnectorAccess().getOverKeyword_5());
            		
            // InternalThingML.g:7541:3: ( (otherlv_6= RULE_ID ) )
            // InternalThingML.g:7542:4: (otherlv_6= RULE_ID )
            {
            // InternalThingML.g:7542:4: (otherlv_6= RULE_ID )
            // InternalThingML.g:7543:5: otherlv_6= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalConnectorRule());
            					}
            				
            otherlv_6=(Token)match(input,RULE_ID,FOLLOW_13); 

            					newLeafNode(otherlv_6, grammarAccess.getExternalConnectorAccess().getProtocolProtocolCrossReference_6_0());
            				

            }


            }

            // InternalThingML.g:7554:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop131:
            do {
                int alt131=2;
                int LA131_0 = input.LA(1);

                if ( (LA131_0==14) ) {
                    alt131=1;
                }


                switch (alt131) {
            	case 1 :
            	    // InternalThingML.g:7555:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:7555:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:7556:5: lv_annotations_7_0= rulePlatformAnnotation
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
    // $ANTLR end "ruleExternalConnector"


    // $ANTLR start "entryRuleInstanceRef"
    // InternalThingML.g:7577:1: entryRuleInstanceRef returns [EObject current=null] : iv_ruleInstanceRef= ruleInstanceRef EOF ;
    public final EObject entryRuleInstanceRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInstanceRef = null;


        try {
            // InternalThingML.g:7577:52: (iv_ruleInstanceRef= ruleInstanceRef EOF )
            // InternalThingML.g:7578:2: iv_ruleInstanceRef= ruleInstanceRef EOF
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
    // InternalThingML.g:7584:1: ruleInstanceRef returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleInstanceRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalThingML.g:7590:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalThingML.g:7591:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalThingML.g:7591:2: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:7592:3: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:7592:3: (otherlv_0= RULE_ID )
            // InternalThingML.g:7593:4: otherlv_0= RULE_ID
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


    protected DFA96 dfa96 = new DFA96(this);
    protected DFA117 dfa117 = new DFA117(this);
    static final String dfa_1s = "\20\uffff";
    static final String dfa_2s = "\1\5\2\uffff\1\34\14\uffff";
    static final String dfa_3s = "\1\132\2\uffff\1\124\14\uffff";
    static final String dfa_4s = "\1\uffff\1\1\1\2\1\uffff\1\7\1\10\1\11\1\12\1\13\1\14\1\16\1\3\1\6\1\5\1\4\1\15";
    static final String dfa_5s = "\20\uffff}>";
    static final String[] dfa_6s = {
            "\1\3\1\uffff\1\2\60\uffff\1\5\25\uffff\1\1\1\uffff\2\12\3\uffff\1\4\1\uffff\1\6\1\7\1\10\1\11",
            "",
            "",
            "\1\16\1\uffff\1\16\2\uffff\1\17\60\uffff\1\13\1\15\1\14",
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

    class DFA96 extends DFA {

        public DFA96(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 96;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "4599:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )";
        }
    }
    static final String dfa_7s = "\14\uffff";
    static final String dfa_8s = "\7\uffff\1\12\4\uffff";
    static final String dfa_9s = "\1\4\6\uffff\1\5\4\uffff";
    static final String dfa_10s = "\1\151\6\uffff\1\155\4\uffff";
    static final String dfa_11s = "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\uffff\1\10\1\12\1\7\1\11";
    static final String dfa_12s = "\14\uffff}>";
    static final String[] dfa_13s = {
            "\1\5\1\7\1\3\1\1\1\6\136\uffff\1\2\2\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\12\1\uffff\1\12\6\uffff\1\12\1\uffff\2\12\4\uffff\1\12\3\uffff\2\12\1\uffff\1\12\2\uffff\1\12\1\13\1\12\1\11\4\12\3\uffff\3\12\1\uffff\2\12\1\uffff\3\12\1\uffff\1\12\1\uffff\1\12\1\uffff\1\12\2\uffff\1\12\3\uffff\1\12\2\uffff\6\12\2\uffff\1\12\1\uffff\4\12\3\uffff\21\12\4\uffff\1\10\1\uffff\2\12",
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

    class DFA117 extends DFA {

        public DFA117(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 117;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "6427:2: (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_Reference_7= ruleReference | this_FunctionCallExpression_8= ruleFunctionCallExpression | this_MessageParameter_9= ruleMessageParameter )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x000000008098A002L,0x0000080000000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000080988002L,0x0000080000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000044000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000204000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000400020L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000001000020L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000002204000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000004204000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x200038F108400000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000050000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x00000002000001F0L,0x000003C400000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000400000020L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000404000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x01000008000040A0L,0x0000000007A34000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000040004002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000060000400000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000060004400000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000400000004000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0022000000000020L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0001800000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0001000004000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x000C000000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0010000000000002L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0A80000000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0048000000000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0400000000000002L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x4000000000000020L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x8000000000204000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x2000101000400000L,0x00000000000001F2L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x2000101000400000L,0x00000000000001F0L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000400000L,0x0000000000000100L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x2000101000400000L,0x00000000000003F2L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x2000101000400000L,0x00000000000003F0L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x2000000000400000L,0x00000000000000F0L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x2000101000400000L,0x00000000000002F2L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x2000101000400000L,0x00000000000002F0L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0008000000000020L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000004002L,0x0000000000001C00L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001C00L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000004022L,0x0000000000001C00L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x01000008000040A0L,0x0000000007A3C000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x00000006000001F0L,0x000003C400000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000008400000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000002L,0x0000000060000000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000030002L,0x0000000180000000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000000002L,0x0000000600000000L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000000002L,0x0000001800000000L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x1000000000000020L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000008400000L,0x0000300000000000L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});

}