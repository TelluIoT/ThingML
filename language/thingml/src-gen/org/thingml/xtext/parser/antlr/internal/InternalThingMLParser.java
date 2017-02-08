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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING_LIT", "RULE_ID", "RULE_INT", "RULE_STRING_EXT", "RULE_FLOAT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'import'", "'@'", "'['", "']'", "'datatype'", "'<'", "'>'", "';'", "'object'", "'enumeration'", "'{'", "'}'", "'thing'", "'fragment'", "'includes'", "','", "'set'", "'='", "'protocol'", "'function'", "'('", "')'", "':'", "'readonly'", "'property'", "'message'", "'optional'", "'required'", "'port'", "'sends'", "'receives'", "'provided'", "'internal'", "'statechart'", "'init'", "'keeps'", "'history'", "'on'", "'entry'", "'exit'", "'final'", "'state'", "'composite'", "'session'", "'region'", "'transition'", "'->'", "'event'", "'guard'", "'action'", "'?'", "'do'", "'end'", "'&'", "'var'", "'!'", "'++'", "'--'", "'while'", "'if'", "'else'", "'return'", "'print'", "'error'", "'fork'", "'or'", "'and'", "'=='", "'!='", "'>='", "'<='", "'+'", "'-'", "'*'", "'/'", "'%'", "'not'", "'true'", "'false'", "'.'", "'configuration'", "'instance'", "'connector'", "'=>'", "'over'"
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
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__90=90;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
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
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=11;
    public static final int RULE_ANY_OTHER=12;
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
    // InternalThingML.g:71:1: ruleThingMLModel returns [EObject current=null] : ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )* ( ( (lv_types_2_0= ruleType ) ) | ( (lv_protocols_3_0= ruleProtocol ) ) | ( (lv_configs_4_0= ruleConfiguration ) ) )* ) ;
    public final EObject ruleThingMLModel() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_importURI_1_0=null;
        EObject lv_types_2_0 = null;

        EObject lv_protocols_3_0 = null;

        EObject lv_configs_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:77:2: ( ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )* ( ( (lv_types_2_0= ruleType ) ) | ( (lv_protocols_3_0= ruleProtocol ) ) | ( (lv_configs_4_0= ruleConfiguration ) ) )* ) )
            // InternalThingML.g:78:2: ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )* ( ( (lv_types_2_0= ruleType ) ) | ( (lv_protocols_3_0= ruleProtocol ) ) | ( (lv_configs_4_0= ruleConfiguration ) ) )* )
            {
            // InternalThingML.g:78:2: ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )* ( ( (lv_types_2_0= ruleType ) ) | ( (lv_protocols_3_0= ruleProtocol ) ) | ( (lv_configs_4_0= ruleConfiguration ) ) )* )
            // InternalThingML.g:79:3: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )* ( ( (lv_types_2_0= ruleType ) ) | ( (lv_protocols_3_0= ruleProtocol ) ) | ( (lv_configs_4_0= ruleConfiguration ) ) )*
            {
            // InternalThingML.g:79:3: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==13) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalThingML.g:80:4: otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING_LIT ) )
            	    {
            	    otherlv_0=(Token)match(input,13,FOLLOW_3); 

            	    				newLeafNode(otherlv_0, grammarAccess.getThingMLModelAccess().getImportKeyword_0_0());
            	    			
            	    // InternalThingML.g:84:4: ( (lv_importURI_1_0= RULE_STRING_LIT ) )
            	    // InternalThingML.g:85:5: (lv_importURI_1_0= RULE_STRING_LIT )
            	    {
            	    // InternalThingML.g:85:5: (lv_importURI_1_0= RULE_STRING_LIT )
            	    // InternalThingML.g:86:6: lv_importURI_1_0= RULE_STRING_LIT
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

            // InternalThingML.g:103:3: ( ( (lv_types_2_0= ruleType ) ) | ( (lv_protocols_3_0= ruleProtocol ) ) | ( (lv_configs_4_0= ruleConfiguration ) ) )*
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
                case 93:
                    {
                    alt2=3;
                    }
                    break;

                }

                switch (alt2) {
            	case 1 :
            	    // InternalThingML.g:104:4: ( (lv_types_2_0= ruleType ) )
            	    {
            	    // InternalThingML.g:104:4: ( (lv_types_2_0= ruleType ) )
            	    // InternalThingML.g:105:5: (lv_types_2_0= ruleType )
            	    {
            	    // InternalThingML.g:105:5: (lv_types_2_0= ruleType )
            	    // InternalThingML.g:106:6: lv_types_2_0= ruleType
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
            	    // InternalThingML.g:124:4: ( (lv_protocols_3_0= ruleProtocol ) )
            	    {
            	    // InternalThingML.g:124:4: ( (lv_protocols_3_0= ruleProtocol ) )
            	    // InternalThingML.g:125:5: (lv_protocols_3_0= ruleProtocol )
            	    {
            	    // InternalThingML.g:125:5: (lv_protocols_3_0= ruleProtocol )
            	    // InternalThingML.g:126:6: lv_protocols_3_0= ruleProtocol
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
            	    // InternalThingML.g:144:4: ( (lv_configs_4_0= ruleConfiguration ) )
            	    {
            	    // InternalThingML.g:144:4: ( (lv_configs_4_0= ruleConfiguration ) )
            	    // InternalThingML.g:145:5: (lv_configs_4_0= ruleConfiguration )
            	    {
            	    // InternalThingML.g:145:5: (lv_configs_4_0= ruleConfiguration )
            	    // InternalThingML.g:146:6: lv_configs_4_0= ruleConfiguration
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
    // InternalThingML.g:168:1: entryRulePlatformAnnotation returns [EObject current=null] : iv_rulePlatformAnnotation= rulePlatformAnnotation EOF ;
    public final EObject entryRulePlatformAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePlatformAnnotation = null;


        try {
            // InternalThingML.g:168:59: (iv_rulePlatformAnnotation= rulePlatformAnnotation EOF )
            // InternalThingML.g:169:2: iv_rulePlatformAnnotation= rulePlatformAnnotation EOF
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
    // InternalThingML.g:175:1: rulePlatformAnnotation returns [EObject current=null] : (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) ( (lv_value_2_0= RULE_STRING_LIT ) ) ) ;
    public final EObject rulePlatformAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token lv_value_2_0=null;


        	enterRule();

        try {
            // InternalThingML.g:181:2: ( (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) ( (lv_value_2_0= RULE_STRING_LIT ) ) ) )
            // InternalThingML.g:182:2: (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) ( (lv_value_2_0= RULE_STRING_LIT ) ) )
            {
            // InternalThingML.g:182:2: (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) ( (lv_value_2_0= RULE_STRING_LIT ) ) )
            // InternalThingML.g:183:3: otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) ( (lv_value_2_0= RULE_STRING_LIT ) )
            {
            otherlv_0=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getPlatformAnnotationAccess().getCommercialAtKeyword_0());
            		
            // InternalThingML.g:187:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:188:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:188:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:189:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_3); 

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

            // InternalThingML.g:205:3: ( (lv_value_2_0= RULE_STRING_LIT ) )
            // InternalThingML.g:206:4: (lv_value_2_0= RULE_STRING_LIT )
            {
            // InternalThingML.g:206:4: (lv_value_2_0= RULE_STRING_LIT )
            // InternalThingML.g:207:5: lv_value_2_0= RULE_STRING_LIT
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
    // InternalThingML.g:227:1: entryRuleTypeRef returns [EObject current=null] : iv_ruleTypeRef= ruleTypeRef EOF ;
    public final EObject entryRuleTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeRef = null;


        try {
            // InternalThingML.g:227:48: (iv_ruleTypeRef= ruleTypeRef EOF )
            // InternalThingML.g:228:2: iv_ruleTypeRef= ruleTypeRef EOF
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
    // InternalThingML.g:234:1: ruleTypeRef returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )? ) ;
    public final EObject ruleTypeRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_isArray_1_0=null;
        Token otherlv_3=null;
        EObject lv_cardinality_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:240:2: ( ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )? ) )
            // InternalThingML.g:241:2: ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )? )
            {
            // InternalThingML.g:241:2: ( ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )? )
            // InternalThingML.g:242:3: ( (otherlv_0= RULE_ID ) ) ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )?
            {
            // InternalThingML.g:242:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:243:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:243:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:244:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTypeRefRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(otherlv_0, grammarAccess.getTypeRefAccess().getTypeTypeCrossReference_0_0());
            				

            }


            }

            // InternalThingML.g:255:3: ( ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==15) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalThingML.g:256:4: ( (lv_isArray_1_0= '[' ) ) ( (lv_cardinality_2_0= ruleExpression ) )? otherlv_3= ']'
                    {
                    // InternalThingML.g:256:4: ( (lv_isArray_1_0= '[' ) )
                    // InternalThingML.g:257:5: (lv_isArray_1_0= '[' )
                    {
                    // InternalThingML.g:257:5: (lv_isArray_1_0= '[' )
                    // InternalThingML.g:258:6: lv_isArray_1_0= '['
                    {
                    lv_isArray_1_0=(Token)match(input,15,FOLLOW_8); 

                    						newLeafNode(lv_isArray_1_0, grammarAccess.getTypeRefAccess().getIsArrayLeftSquareBracketKeyword_1_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTypeRefRule());
                    						}
                    						setWithLastConsumed(current, "isArray", true, "[");
                    					

                    }


                    }

                    // InternalThingML.g:270:4: ( (lv_cardinality_2_0= ruleExpression ) )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( ((LA3_0>=RULE_STRING_LIT && LA3_0<=RULE_FLOAT)||LA3_0==33||LA3_0==85||(LA3_0>=89 && LA3_0<=91)) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // InternalThingML.g:271:5: (lv_cardinality_2_0= ruleExpression )
                            {
                            // InternalThingML.g:271:5: (lv_cardinality_2_0= ruleExpression )
                            // InternalThingML.g:272:6: lv_cardinality_2_0= ruleExpression
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
    // InternalThingML.g:298:1: entryRuleType returns [EObject current=null] : iv_ruleType= ruleType EOF ;
    public final EObject entryRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleType = null;


        try {
            // InternalThingML.g:298:45: (iv_ruleType= ruleType EOF )
            // InternalThingML.g:299:2: iv_ruleType= ruleType EOF
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
    // InternalThingML.g:305:1: ruleType returns [EObject current=null] : (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing ) ;
    public final EObject ruleType() throws RecognitionException {
        EObject current = null;

        EObject this_PrimitiveType_0 = null;

        EObject this_ObjectType_1 = null;

        EObject this_Enumeration_2 = null;

        EObject this_Thing_3 = null;



        	enterRule();

        try {
            // InternalThingML.g:311:2: ( (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing ) )
            // InternalThingML.g:312:2: (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing )
            {
            // InternalThingML.g:312:2: (this_PrimitiveType_0= rulePrimitiveType | this_ObjectType_1= ruleObjectType | this_Enumeration_2= ruleEnumeration | this_Thing_3= ruleThing )
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
                    // InternalThingML.g:313:3: this_PrimitiveType_0= rulePrimitiveType
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
                    // InternalThingML.g:322:3: this_ObjectType_1= ruleObjectType
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
                    // InternalThingML.g:331:3: this_Enumeration_2= ruleEnumeration
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
                    // InternalThingML.g:340:3: this_Thing_3= ruleThing
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
    // InternalThingML.g:352:1: entryRulePrimitiveType returns [EObject current=null] : iv_rulePrimitiveType= rulePrimitiveType EOF ;
    public final EObject entryRulePrimitiveType() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimitiveType = null;


        try {
            // InternalThingML.g:352:54: (iv_rulePrimitiveType= rulePrimitiveType EOF )
            // InternalThingML.g:353:2: iv_rulePrimitiveType= rulePrimitiveType EOF
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
    // InternalThingML.g:359:1: rulePrimitiveType returns [EObject current=null] : (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )? ) ;
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
            // InternalThingML.g:365:2: ( (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )? ) )
            // InternalThingML.g:366:2: (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )? )
            {
            // InternalThingML.g:366:2: (otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )? )
            // InternalThingML.g:367:3: otherlv_0= 'datatype' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '<' ( (lv_ByteSize_3_0= RULE_INT ) ) otherlv_4= '>' ( (lv_annotations_5_0= rulePlatformAnnotation ) )* (otherlv_6= ';' )?
            {
            otherlv_0=(Token)match(input,17,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getPrimitiveTypeAccess().getDatatypeKeyword_0());
            		
            // InternalThingML.g:371:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:372:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:372:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:373:5: lv_name_1_0= RULE_ID
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
            		
            // InternalThingML.g:393:3: ( (lv_ByteSize_3_0= RULE_INT ) )
            // InternalThingML.g:394:4: (lv_ByteSize_3_0= RULE_INT )
            {
            // InternalThingML.g:394:4: (lv_ByteSize_3_0= RULE_INT )
            // InternalThingML.g:395:5: lv_ByteSize_3_0= RULE_INT
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
            		
            // InternalThingML.g:415:3: ( (lv_annotations_5_0= rulePlatformAnnotation ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==14) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalThingML.g:416:4: (lv_annotations_5_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:416:4: (lv_annotations_5_0= rulePlatformAnnotation )
            	    // InternalThingML.g:417:5: lv_annotations_5_0= rulePlatformAnnotation
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

            // InternalThingML.g:434:3: (otherlv_6= ';' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==20) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalThingML.g:435:4: otherlv_6= ';'
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
    // InternalThingML.g:444:1: entryRuleObjectType returns [EObject current=null] : iv_ruleObjectType= ruleObjectType EOF ;
    public final EObject entryRuleObjectType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleObjectType = null;


        try {
            // InternalThingML.g:444:51: (iv_ruleObjectType= ruleObjectType EOF )
            // InternalThingML.g:445:2: iv_ruleObjectType= ruleObjectType EOF
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
    // InternalThingML.g:451:1: ruleObjectType returns [EObject current=null] : (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )? ) ;
    public final EObject ruleObjectType() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        EObject lv_annotations_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:457:2: ( (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )? ) )
            // InternalThingML.g:458:2: (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )? )
            {
            // InternalThingML.g:458:2: (otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )? )
            // InternalThingML.g:459:3: otherlv_0= 'object' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* (otherlv_3= ';' )?
            {
            otherlv_0=(Token)match(input,21,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getObjectTypeAccess().getObjectKeyword_0());
            		
            // InternalThingML.g:463:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:464:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:464:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:465:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:481:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==14) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalThingML.g:482:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:482:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:483:5: lv_annotations_2_0= rulePlatformAnnotation
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

            // InternalThingML.g:500:3: (otherlv_3= ';' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==20) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalThingML.g:501:4: otherlv_3= ';'
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
    // InternalThingML.g:510:1: entryRuleEnumeration returns [EObject current=null] : iv_ruleEnumeration= ruleEnumeration EOF ;
    public final EObject entryRuleEnumeration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumeration = null;


        try {
            // InternalThingML.g:510:52: (iv_ruleEnumeration= ruleEnumeration EOF )
            // InternalThingML.g:511:2: iv_ruleEnumeration= ruleEnumeration EOF
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
    // InternalThingML.g:517:1: ruleEnumeration returns [EObject current=null] : (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' ) ;
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
            // InternalThingML.g:523:2: ( (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' ) )
            // InternalThingML.g:524:2: (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' )
            {
            // InternalThingML.g:524:2: (otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}' )
            // InternalThingML.g:525:3: otherlv_0= 'enumeration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( (lv_literals_4_0= ruleEnumerationLiteral ) )* otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,22,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getEnumerationAccess().getEnumerationKeyword_0());
            		
            // InternalThingML.g:529:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:530:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:530:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:531:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:547:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==14) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalThingML.g:548:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:548:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:549:5: lv_annotations_2_0= rulePlatformAnnotation
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
            		
            // InternalThingML.g:570:3: ( (lv_literals_4_0= ruleEnumerationLiteral ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==RULE_ID) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalThingML.g:571:4: (lv_literals_4_0= ruleEnumerationLiteral )
            	    {
            	    // InternalThingML.g:571:4: (lv_literals_4_0= ruleEnumerationLiteral )
            	    // InternalThingML.g:572:5: lv_literals_4_0= ruleEnumerationLiteral
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
    // InternalThingML.g:597:1: entryRuleEnumerationLiteral returns [EObject current=null] : iv_ruleEnumerationLiteral= ruleEnumerationLiteral EOF ;
    public final EObject entryRuleEnumerationLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumerationLiteral = null;


        try {
            // InternalThingML.g:597:59: (iv_ruleEnumerationLiteral= ruleEnumerationLiteral EOF )
            // InternalThingML.g:598:2: iv_ruleEnumerationLiteral= ruleEnumerationLiteral EOF
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
    // InternalThingML.g:604:1: ruleEnumerationLiteral returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleEnumerationLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        EObject lv_annotations_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:610:2: ( ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:611:2: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:611:2: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:612:3: ( (lv_name_0_0= RULE_ID ) ) ( (lv_annotations_1_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:612:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:613:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:613:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:614:5: lv_name_0_0= RULE_ID
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

            // InternalThingML.g:630:3: ( (lv_annotations_1_0= rulePlatformAnnotation ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==14) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalThingML.g:631:4: (lv_annotations_1_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:631:4: (lv_annotations_1_0= rulePlatformAnnotation )
            	    // InternalThingML.g:632:5: lv_annotations_1_0= rulePlatformAnnotation
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
    // InternalThingML.g:653:1: entryRuleThing returns [EObject current=null] : iv_ruleThing= ruleThing EOF ;
    public final EObject entryRuleThing() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThing = null;


        try {
            // InternalThingML.g:653:46: (iv_ruleThing= ruleThing EOF )
            // InternalThingML.g:654:2: iv_ruleThing= ruleThing EOF
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
    // InternalThingML.g:660:1: ruleThing returns [EObject current=null] : (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) )* otherlv_15= '}' ) ;
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
            // InternalThingML.g:666:2: ( (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) )* otherlv_15= '}' ) )
            // InternalThingML.g:667:2: (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) )* otherlv_15= '}' )
            {
            // InternalThingML.g:667:2: (otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) )* otherlv_15= '}' )
            // InternalThingML.g:668:3: otherlv_0= 'thing' ( (lv_fragment_1_0= 'fragment' ) )? ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) )* otherlv_15= '}'
            {
            otherlv_0=(Token)match(input,25,FOLLOW_17); 

            			newLeafNode(otherlv_0, grammarAccess.getThingAccess().getThingKeyword_0());
            		
            // InternalThingML.g:672:3: ( (lv_fragment_1_0= 'fragment' ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==26) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalThingML.g:673:4: (lv_fragment_1_0= 'fragment' )
                    {
                    // InternalThingML.g:673:4: (lv_fragment_1_0= 'fragment' )
                    // InternalThingML.g:674:5: lv_fragment_1_0= 'fragment'
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

            // InternalThingML.g:686:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:687:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:687:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:688:5: lv_name_2_0= RULE_ID
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

            // InternalThingML.g:704:3: (otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )* )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==27) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalThingML.g:705:4: otherlv_3= 'includes' ( (otherlv_4= RULE_ID ) ) (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )*
                    {
                    otherlv_3=(Token)match(input,27,FOLLOW_6); 

                    				newLeafNode(otherlv_3, grammarAccess.getThingAccess().getIncludesKeyword_3_0());
                    			
                    // InternalThingML.g:709:4: ( (otherlv_4= RULE_ID ) )
                    // InternalThingML.g:710:5: (otherlv_4= RULE_ID )
                    {
                    // InternalThingML.g:710:5: (otherlv_4= RULE_ID )
                    // InternalThingML.g:711:6: otherlv_4= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getThingRule());
                    						}
                    					
                    otherlv_4=(Token)match(input,RULE_ID,FOLLOW_19); 

                    						newLeafNode(otherlv_4, grammarAccess.getThingAccess().getIncludesThingCrossReference_3_1_0());
                    					

                    }


                    }

                    // InternalThingML.g:722:4: (otherlv_5= ',' ( (otherlv_6= RULE_ID ) ) )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==28) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // InternalThingML.g:723:5: otherlv_5= ',' ( (otherlv_6= RULE_ID ) )
                    	    {
                    	    otherlv_5=(Token)match(input,28,FOLLOW_6); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getThingAccess().getCommaKeyword_3_2_0());
                    	    				
                    	    // InternalThingML.g:727:5: ( (otherlv_6= RULE_ID ) )
                    	    // InternalThingML.g:728:6: (otherlv_6= RULE_ID )
                    	    {
                    	    // InternalThingML.g:728:6: (otherlv_6= RULE_ID )
                    	    // InternalThingML.g:729:7: otherlv_6= RULE_ID
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

            // InternalThingML.g:742:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==14) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalThingML.g:743:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:743:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:744:5: lv_annotations_7_0= rulePlatformAnnotation
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
            		
            // InternalThingML.g:765:3: ( ( (lv_messages_9_0= ruleMessage ) ) | ( (lv_ports_10_0= rulePort ) ) | ( (lv_properties_11_0= ruleProperty ) ) | ( (lv_functions_12_0= ruleFunction ) ) | ( (lv_assign_13_0= rulePropertyAssign ) ) | ( (lv_behaviour_14_0= ruleStateMachine ) ) )*
            loop17:
            do {
                int alt17=7;
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
                case 46:
                    {
                    alt17=6;
                    }
                    break;

                }

                switch (alt17) {
            	case 1 :
            	    // InternalThingML.g:766:4: ( (lv_messages_9_0= ruleMessage ) )
            	    {
            	    // InternalThingML.g:766:4: ( (lv_messages_9_0= ruleMessage ) )
            	    // InternalThingML.g:767:5: (lv_messages_9_0= ruleMessage )
            	    {
            	    // InternalThingML.g:767:5: (lv_messages_9_0= ruleMessage )
            	    // InternalThingML.g:768:6: lv_messages_9_0= ruleMessage
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
            	    // InternalThingML.g:786:4: ( (lv_ports_10_0= rulePort ) )
            	    {
            	    // InternalThingML.g:786:4: ( (lv_ports_10_0= rulePort ) )
            	    // InternalThingML.g:787:5: (lv_ports_10_0= rulePort )
            	    {
            	    // InternalThingML.g:787:5: (lv_ports_10_0= rulePort )
            	    // InternalThingML.g:788:6: lv_ports_10_0= rulePort
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
            	    // InternalThingML.g:806:4: ( (lv_properties_11_0= ruleProperty ) )
            	    {
            	    // InternalThingML.g:806:4: ( (lv_properties_11_0= ruleProperty ) )
            	    // InternalThingML.g:807:5: (lv_properties_11_0= ruleProperty )
            	    {
            	    // InternalThingML.g:807:5: (lv_properties_11_0= ruleProperty )
            	    // InternalThingML.g:808:6: lv_properties_11_0= ruleProperty
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
            	    // InternalThingML.g:826:4: ( (lv_functions_12_0= ruleFunction ) )
            	    {
            	    // InternalThingML.g:826:4: ( (lv_functions_12_0= ruleFunction ) )
            	    // InternalThingML.g:827:5: (lv_functions_12_0= ruleFunction )
            	    {
            	    // InternalThingML.g:827:5: (lv_functions_12_0= ruleFunction )
            	    // InternalThingML.g:828:6: lv_functions_12_0= ruleFunction
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
            	    // InternalThingML.g:846:4: ( (lv_assign_13_0= rulePropertyAssign ) )
            	    {
            	    // InternalThingML.g:846:4: ( (lv_assign_13_0= rulePropertyAssign ) )
            	    // InternalThingML.g:847:5: (lv_assign_13_0= rulePropertyAssign )
            	    {
            	    // InternalThingML.g:847:5: (lv_assign_13_0= rulePropertyAssign )
            	    // InternalThingML.g:848:6: lv_assign_13_0= rulePropertyAssign
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
            	    // InternalThingML.g:866:4: ( (lv_behaviour_14_0= ruleStateMachine ) )
            	    {
            	    // InternalThingML.g:866:4: ( (lv_behaviour_14_0= ruleStateMachine ) )
            	    // InternalThingML.g:867:5: (lv_behaviour_14_0= ruleStateMachine )
            	    {
            	    // InternalThingML.g:867:5: (lv_behaviour_14_0= ruleStateMachine )
            	    // InternalThingML.g:868:6: lv_behaviour_14_0= ruleStateMachine
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

            	default :
            	    break loop17;
                }
            } while (true);

            otherlv_15=(Token)match(input,24,FOLLOW_2); 

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
    // InternalThingML.g:894:1: entryRulePropertyAssign returns [EObject current=null] : iv_rulePropertyAssign= rulePropertyAssign EOF ;
    public final EObject entryRulePropertyAssign() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePropertyAssign = null;


        try {
            // InternalThingML.g:894:55: (iv_rulePropertyAssign= rulePropertyAssign EOF )
            // InternalThingML.g:895:2: iv_rulePropertyAssign= rulePropertyAssign EOF
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
    // InternalThingML.g:901:1: rulePropertyAssign returns [EObject current=null] : (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:907:2: ( (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:908:2: (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:908:2: (otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:909:3: otherlv_0= 'set' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )* otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,29,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getPropertyAssignAccess().getSetKeyword_0());
            		
            // InternalThingML.g:913:3: ( (otherlv_1= RULE_ID ) )
            // InternalThingML.g:914:4: (otherlv_1= RULE_ID )
            {
            // InternalThingML.g:914:4: (otherlv_1= RULE_ID )
            // InternalThingML.g:915:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPropertyAssignRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_1, grammarAccess.getPropertyAssignAccess().getPropertyPropertyCrossReference_1_0());
            				

            }


            }

            // InternalThingML.g:926:3: (otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==15) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalThingML.g:927:4: otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']'
            	    {
            	    otherlv_2=(Token)match(input,15,FOLLOW_22); 

            	    				newLeafNode(otherlv_2, grammarAccess.getPropertyAssignAccess().getLeftSquareBracketKeyword_2_0());
            	    			
            	    // InternalThingML.g:931:4: ( (lv_index_3_0= ruleExpression ) )
            	    // InternalThingML.g:932:5: (lv_index_3_0= ruleExpression )
            	    {
            	    // InternalThingML.g:932:5: (lv_index_3_0= ruleExpression )
            	    // InternalThingML.g:933:6: lv_index_3_0= ruleExpression
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
            		
            // InternalThingML.g:959:3: ( (lv_init_6_0= ruleExpression ) )
            // InternalThingML.g:960:4: (lv_init_6_0= ruleExpression )
            {
            // InternalThingML.g:960:4: (lv_init_6_0= ruleExpression )
            // InternalThingML.g:961:5: lv_init_6_0= ruleExpression
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

            // InternalThingML.g:978:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==14) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalThingML.g:979:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:979:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:980:5: lv_annotations_7_0= rulePlatformAnnotation
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
    // InternalThingML.g:1001:1: entryRuleProtocol returns [EObject current=null] : iv_ruleProtocol= ruleProtocol EOF ;
    public final EObject entryRuleProtocol() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProtocol = null;


        try {
            // InternalThingML.g:1001:49: (iv_ruleProtocol= ruleProtocol EOF )
            // InternalThingML.g:1002:2: iv_ruleProtocol= ruleProtocol EOF
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
    // InternalThingML.g:1008:1: ruleProtocol returns [EObject current=null] : (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' ) ;
    public final EObject ruleProtocol() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        EObject lv_annotations_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1014:2: ( (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' ) )
            // InternalThingML.g:1015:2: (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' )
            {
            // InternalThingML.g:1015:2: (otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';' )
            // InternalThingML.g:1016:3: otherlv_0= 'protocol' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= ';'
            {
            otherlv_0=(Token)match(input,31,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getProtocolAccess().getProtocolKeyword_0());
            		
            // InternalThingML.g:1020:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1021:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1021:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1022:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:1038:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==14) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalThingML.g:1039:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1039:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1040:5: lv_annotations_2_0= rulePlatformAnnotation
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
    // InternalThingML.g:1065:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalThingML.g:1065:49: (iv_ruleFunction= ruleFunction EOF )
            // InternalThingML.g:1066:2: iv_ruleFunction= ruleFunction EOF
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
    // InternalThingML.g:1072:1: ruleFunction returns [EObject current=null] : (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) ) ;
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
            // InternalThingML.g:1078:2: ( (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) ) )
            // InternalThingML.g:1079:2: (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) )
            {
            // InternalThingML.g:1079:2: (otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) ) )
            // InternalThingML.g:1080:3: otherlv_0= 'function' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )? ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ( (lv_body_10_0= ruleAction ) )
            {
            otherlv_0=(Token)match(input,32,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getFunctionAccess().getFunctionKeyword_0());
            		
            // InternalThingML.g:1084:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1085:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1085:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1086:5: lv_name_1_0= RULE_ID
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
            		
            // InternalThingML.g:1106:3: ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==RULE_ID) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalThingML.g:1107:4: ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    {
                    // InternalThingML.g:1107:4: ( (lv_parameters_3_0= ruleParameter ) )
                    // InternalThingML.g:1108:5: (lv_parameters_3_0= ruleParameter )
                    {
                    // InternalThingML.g:1108:5: (lv_parameters_3_0= ruleParameter )
                    // InternalThingML.g:1109:6: lv_parameters_3_0= ruleParameter
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

                    // InternalThingML.g:1126:4: (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==28) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // InternalThingML.g:1127:5: otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) )
                    	    {
                    	    otherlv_4=(Token)match(input,28,FOLLOW_6); 

                    	    					newLeafNode(otherlv_4, grammarAccess.getFunctionAccess().getCommaKeyword_3_1_0());
                    	    				
                    	    // InternalThingML.g:1131:5: ( (lv_parameters_5_0= ruleParameter ) )
                    	    // InternalThingML.g:1132:6: (lv_parameters_5_0= ruleParameter )
                    	    {
                    	    // InternalThingML.g:1132:6: (lv_parameters_5_0= ruleParameter )
                    	    // InternalThingML.g:1133:7: lv_parameters_5_0= ruleParameter
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
            		
            // InternalThingML.g:1156:3: (otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==35) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalThingML.g:1157:4: otherlv_7= ':' ( (lv_typeRef_8_0= ruleTypeRef ) )
                    {
                    otherlv_7=(Token)match(input,35,FOLLOW_6); 

                    				newLeafNode(otherlv_7, grammarAccess.getFunctionAccess().getColonKeyword_5_0());
                    			
                    // InternalThingML.g:1161:4: ( (lv_typeRef_8_0= ruleTypeRef ) )
                    // InternalThingML.g:1162:5: (lv_typeRef_8_0= ruleTypeRef )
                    {
                    // InternalThingML.g:1162:5: (lv_typeRef_8_0= ruleTypeRef )
                    // InternalThingML.g:1163:6: lv_typeRef_8_0= ruleTypeRef
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

            // InternalThingML.g:1181:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==14) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalThingML.g:1182:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1182:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1183:5: lv_annotations_9_0= rulePlatformAnnotation
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

            // InternalThingML.g:1200:3: ( (lv_body_10_0= ruleAction ) )
            // InternalThingML.g:1201:4: (lv_body_10_0= ruleAction )
            {
            // InternalThingML.g:1201:4: (lv_body_10_0= ruleAction )
            // InternalThingML.g:1202:5: lv_body_10_0= ruleAction
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
    // InternalThingML.g:1223:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // InternalThingML.g:1223:49: (iv_ruleProperty= ruleProperty EOF )
            // InternalThingML.g:1224:2: iv_ruleProperty= ruleProperty EOF
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
    // InternalThingML.g:1230:1: ruleProperty returns [EObject current=null] : ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:1236:2: ( ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:1237:2: ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:1237:2: ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:1238:3: ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'property' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:1238:3: ( (lv_changeable_0_0= 'readonly' ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==36) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalThingML.g:1239:4: (lv_changeable_0_0= 'readonly' )
                    {
                    // InternalThingML.g:1239:4: (lv_changeable_0_0= 'readonly' )
                    // InternalThingML.g:1240:5: lv_changeable_0_0= 'readonly'
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
            		
            // InternalThingML.g:1256:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:1257:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:1257:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:1258:5: lv_name_2_0= RULE_ID
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
            		
            // InternalThingML.g:1278:3: ( (lv_typeRef_4_0= ruleTypeRef ) )
            // InternalThingML.g:1279:4: (lv_typeRef_4_0= ruleTypeRef )
            {
            // InternalThingML.g:1279:4: (lv_typeRef_4_0= ruleTypeRef )
            // InternalThingML.g:1280:5: lv_typeRef_4_0= ruleTypeRef
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

            // InternalThingML.g:1297:3: (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==30) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalThingML.g:1298:4: otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) )
                    {
                    otherlv_5=(Token)match(input,30,FOLLOW_22); 

                    				newLeafNode(otherlv_5, grammarAccess.getPropertyAccess().getEqualsSignKeyword_5_0());
                    			
                    // InternalThingML.g:1302:4: ( (lv_init_6_0= ruleExpression ) )
                    // InternalThingML.g:1303:5: (lv_init_6_0= ruleExpression )
                    {
                    // InternalThingML.g:1303:5: (lv_init_6_0= ruleExpression )
                    // InternalThingML.g:1304:6: lv_init_6_0= ruleExpression
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

            // InternalThingML.g:1322:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==14) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalThingML.g:1323:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1323:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1324:5: lv_annotations_7_0= rulePlatformAnnotation
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
    // InternalThingML.g:1345:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // InternalThingML.g:1345:48: (iv_ruleMessage= ruleMessage EOF )
            // InternalThingML.g:1346:2: iv_ruleMessage= ruleMessage EOF
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
    // InternalThingML.g:1352:1: ruleMessage returns [EObject current=null] : (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )? ) ;
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
            // InternalThingML.g:1358:2: ( (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )? ) )
            // InternalThingML.g:1359:2: (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )? )
            {
            // InternalThingML.g:1359:2: (otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )? )
            // InternalThingML.g:1360:3: otherlv_0= 'message' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' ( (lv_annotations_7_0= rulePlatformAnnotation ) )* (otherlv_8= ';' )?
            {
            otherlv_0=(Token)match(input,38,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getMessageAccess().getMessageKeyword_0());
            		
            // InternalThingML.g:1364:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:1365:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:1365:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:1366:5: lv_name_1_0= RULE_ID
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
            		
            // InternalThingML.g:1386:3: ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==RULE_ID) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalThingML.g:1387:4: ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    {
                    // InternalThingML.g:1387:4: ( (lv_parameters_3_0= ruleParameter ) )
                    // InternalThingML.g:1388:5: (lv_parameters_3_0= ruleParameter )
                    {
                    // InternalThingML.g:1388:5: (lv_parameters_3_0= ruleParameter )
                    // InternalThingML.g:1389:6: lv_parameters_3_0= ruleParameter
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

                    // InternalThingML.g:1406:4: (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                    loop28:
                    do {
                        int alt28=2;
                        int LA28_0 = input.LA(1);

                        if ( (LA28_0==28) ) {
                            alt28=1;
                        }


                        switch (alt28) {
                    	case 1 :
                    	    // InternalThingML.g:1407:5: otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) )
                    	    {
                    	    otherlv_4=(Token)match(input,28,FOLLOW_6); 

                    	    					newLeafNode(otherlv_4, grammarAccess.getMessageAccess().getCommaKeyword_3_1_0());
                    	    				
                    	    // InternalThingML.g:1411:5: ( (lv_parameters_5_0= ruleParameter ) )
                    	    // InternalThingML.g:1412:6: (lv_parameters_5_0= ruleParameter )
                    	    {
                    	    // InternalThingML.g:1412:6: (lv_parameters_5_0= ruleParameter )
                    	    // InternalThingML.g:1413:7: lv_parameters_5_0= ruleParameter
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
            		
            // InternalThingML.g:1436:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==14) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalThingML.g:1437:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1437:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1438:5: lv_annotations_7_0= rulePlatformAnnotation
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

            // InternalThingML.g:1455:3: (otherlv_8= ';' )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==20) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalThingML.g:1456:4: otherlv_8= ';'
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
    // InternalThingML.g:1465:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalThingML.g:1465:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalThingML.g:1466:2: iv_ruleParameter= ruleParameter EOF
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
    // InternalThingML.g:1472:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_typeRef_2_0 = null;

        EObject lv_annotations_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:1478:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:1479:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:1479:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:1480:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_typeRef_2_0= ruleTypeRef ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:1480:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalThingML.g:1481:4: (lv_name_0_0= RULE_ID )
            {
            // InternalThingML.g:1481:4: (lv_name_0_0= RULE_ID )
            // InternalThingML.g:1482:5: lv_name_0_0= RULE_ID
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
            		
            // InternalThingML.g:1502:3: ( (lv_typeRef_2_0= ruleTypeRef ) )
            // InternalThingML.g:1503:4: (lv_typeRef_2_0= ruleTypeRef )
            {
            // InternalThingML.g:1503:4: (lv_typeRef_2_0= ruleTypeRef )
            // InternalThingML.g:1504:5: lv_typeRef_2_0= ruleTypeRef
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

            // InternalThingML.g:1521:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==14) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalThingML.g:1522:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1522:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1523:5: lv_annotations_3_0= rulePlatformAnnotation
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
    // InternalThingML.g:1544:1: entryRulePort returns [EObject current=null] : iv_rulePort= rulePort EOF ;
    public final EObject entryRulePort() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePort = null;


        try {
            // InternalThingML.g:1544:45: (iv_rulePort= rulePort EOF )
            // InternalThingML.g:1545:2: iv_rulePort= rulePort EOF
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
    // InternalThingML.g:1551:1: rulePort returns [EObject current=null] : (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort ) ;
    public final EObject rulePort() throws RecognitionException {
        EObject current = null;

        EObject this_RequiredPort_0 = null;

        EObject this_ProvidedPort_1 = null;

        EObject this_InternalPort_2 = null;



        	enterRule();

        try {
            // InternalThingML.g:1557:2: ( (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort ) )
            // InternalThingML.g:1558:2: (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort )
            {
            // InternalThingML.g:1558:2: (this_RequiredPort_0= ruleRequiredPort | this_ProvidedPort_1= ruleProvidedPort | this_InternalPort_2= ruleInternalPort )
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
                    // InternalThingML.g:1559:3: this_RequiredPort_0= ruleRequiredPort
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
                    // InternalThingML.g:1568:3: this_ProvidedPort_1= ruleProvidedPort
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
                    // InternalThingML.g:1577:3: this_InternalPort_2= ruleInternalPort
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
    // InternalThingML.g:1589:1: entryRuleRequiredPort returns [EObject current=null] : iv_ruleRequiredPort= ruleRequiredPort EOF ;
    public final EObject entryRuleRequiredPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequiredPort = null;


        try {
            // InternalThingML.g:1589:53: (iv_ruleRequiredPort= ruleRequiredPort EOF )
            // InternalThingML.g:1590:2: iv_ruleRequiredPort= ruleRequiredPort EOF
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
    // InternalThingML.g:1596:1: ruleRequiredPort returns [EObject current=null] : ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' ) ;
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
            // InternalThingML.g:1602:2: ( ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' ) )
            // InternalThingML.g:1603:2: ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' )
            {
            // InternalThingML.g:1603:2: ( ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}' )
            // InternalThingML.g:1604:3: ( (lv_optional_0_0= 'optional' ) )? otherlv_1= 'required' otherlv_2= 'port' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* otherlv_5= '{' ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )* otherlv_14= '}'
            {
            // InternalThingML.g:1604:3: ( (lv_optional_0_0= 'optional' ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==39) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalThingML.g:1605:4: (lv_optional_0_0= 'optional' )
                    {
                    // InternalThingML.g:1605:4: (lv_optional_0_0= 'optional' )
                    // InternalThingML.g:1606:5: lv_optional_0_0= 'optional'
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
            		
            // InternalThingML.g:1626:3: ( (lv_name_3_0= RULE_ID ) )
            // InternalThingML.g:1627:4: (lv_name_3_0= RULE_ID )
            {
            // InternalThingML.g:1627:4: (lv_name_3_0= RULE_ID )
            // InternalThingML.g:1628:5: lv_name_3_0= RULE_ID
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

            // InternalThingML.g:1644:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==14) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalThingML.g:1645:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1645:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1646:5: lv_annotations_4_0= rulePlatformAnnotation
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
            		
            // InternalThingML.g:1667:3: ( (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* ) | (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* ) )*
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
            	    // InternalThingML.g:1668:4: (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1668:4: (otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )* )
            	    // InternalThingML.g:1669:5: otherlv_6= 'sends' ( (otherlv_7= RULE_ID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )*
            	    {
            	    otherlv_6=(Token)match(input,42,FOLLOW_6); 

            	    					newLeafNode(otherlv_6, grammarAccess.getRequiredPortAccess().getSendsKeyword_6_0_0());
            	    				
            	    // InternalThingML.g:1673:5: ( (otherlv_7= RULE_ID ) )
            	    // InternalThingML.g:1674:6: (otherlv_7= RULE_ID )
            	    {
            	    // InternalThingML.g:1674:6: (otherlv_7= RULE_ID )
            	    // InternalThingML.g:1675:7: otherlv_7= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getRequiredPortRule());
            	    							}
            	    						
            	    otherlv_7=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    							newLeafNode(otherlv_7, grammarAccess.getRequiredPortAccess().getSendsMessageCrossReference_6_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1686:5: (otherlv_8= ',' ( (otherlv_9= RULE_ID ) ) )*
            	    loop36:
            	    do {
            	        int alt36=2;
            	        int LA36_0 = input.LA(1);

            	        if ( (LA36_0==28) ) {
            	            alt36=1;
            	        }


            	        switch (alt36) {
            	    	case 1 :
            	    	    // InternalThingML.g:1687:6: otherlv_8= ',' ( (otherlv_9= RULE_ID ) )
            	    	    {
            	    	    otherlv_8=(Token)match(input,28,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_8, grammarAccess.getRequiredPortAccess().getCommaKeyword_6_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:1691:6: ( (otherlv_9= RULE_ID ) )
            	    	    // InternalThingML.g:1692:7: (otherlv_9= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1692:7: (otherlv_9= RULE_ID )
            	    	    // InternalThingML.g:1693:8: otherlv_9= RULE_ID
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
            	    // InternalThingML.g:1707:4: (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1707:4: (otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )* )
            	    // InternalThingML.g:1708:5: otherlv_10= 'receives' ( (otherlv_11= RULE_ID ) ) (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )*
            	    {
            	    otherlv_10=(Token)match(input,43,FOLLOW_6); 

            	    					newLeafNode(otherlv_10, grammarAccess.getRequiredPortAccess().getReceivesKeyword_6_1_0());
            	    				
            	    // InternalThingML.g:1712:5: ( (otherlv_11= RULE_ID ) )
            	    // InternalThingML.g:1713:6: (otherlv_11= RULE_ID )
            	    {
            	    // InternalThingML.g:1713:6: (otherlv_11= RULE_ID )
            	    // InternalThingML.g:1714:7: otherlv_11= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getRequiredPortRule());
            	    							}
            	    						
            	    otherlv_11=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    							newLeafNode(otherlv_11, grammarAccess.getRequiredPortAccess().getReceivesMessageCrossReference_6_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1725:5: (otherlv_12= ',' ( (otherlv_13= RULE_ID ) ) )*
            	    loop37:
            	    do {
            	        int alt37=2;
            	        int LA37_0 = input.LA(1);

            	        if ( (LA37_0==28) ) {
            	            alt37=1;
            	        }


            	        switch (alt37) {
            	    	case 1 :
            	    	    // InternalThingML.g:1726:6: otherlv_12= ',' ( (otherlv_13= RULE_ID ) )
            	    	    {
            	    	    otherlv_12=(Token)match(input,28,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_12, grammarAccess.getRequiredPortAccess().getCommaKeyword_6_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:1730:6: ( (otherlv_13= RULE_ID ) )
            	    	    // InternalThingML.g:1731:7: (otherlv_13= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1731:7: (otherlv_13= RULE_ID )
            	    	    // InternalThingML.g:1732:8: otherlv_13= RULE_ID
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
    // InternalThingML.g:1754:1: entryRuleProvidedPort returns [EObject current=null] : iv_ruleProvidedPort= ruleProvidedPort EOF ;
    public final EObject entryRuleProvidedPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProvidedPort = null;


        try {
            // InternalThingML.g:1754:53: (iv_ruleProvidedPort= ruleProvidedPort EOF )
            // InternalThingML.g:1755:2: iv_ruleProvidedPort= ruleProvidedPort EOF
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
    // InternalThingML.g:1761:1: ruleProvidedPort returns [EObject current=null] : (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) ;
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
            // InternalThingML.g:1767:2: ( (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) )
            // InternalThingML.g:1768:2: (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            {
            // InternalThingML.g:1768:2: (otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            // InternalThingML.g:1769:3: otherlv_0= 'provided' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,44,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getProvidedPortAccess().getProvidedKeyword_0());
            		
            otherlv_1=(Token)match(input,41,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getProvidedPortAccess().getPortKeyword_1());
            		
            // InternalThingML.g:1777:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:1778:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:1778:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:1779:5: lv_name_2_0= RULE_ID
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

            // InternalThingML.g:1795:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==14) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalThingML.g:1796:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1796:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1797:5: lv_annotations_3_0= rulePlatformAnnotation
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
            		
            // InternalThingML.g:1818:3: ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )*
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
            	    // InternalThingML.g:1819:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1819:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    // InternalThingML.g:1820:5: otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    {
            	    otherlv_5=(Token)match(input,42,FOLLOW_6); 

            	    					newLeafNode(otherlv_5, grammarAccess.getProvidedPortAccess().getSendsKeyword_5_0_0());
            	    				
            	    // InternalThingML.g:1824:5: ( (otherlv_6= RULE_ID ) )
            	    // InternalThingML.g:1825:6: (otherlv_6= RULE_ID )
            	    {
            	    // InternalThingML.g:1825:6: (otherlv_6= RULE_ID )
            	    // InternalThingML.g:1826:7: otherlv_6= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getProvidedPortRule());
            	    							}
            	    						
            	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    							newLeafNode(otherlv_6, grammarAccess.getProvidedPortAccess().getSendsMessageCrossReference_5_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1837:5: (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    loop40:
            	    do {
            	        int alt40=2;
            	        int LA40_0 = input.LA(1);

            	        if ( (LA40_0==28) ) {
            	            alt40=1;
            	        }


            	        switch (alt40) {
            	    	case 1 :
            	    	    // InternalThingML.g:1838:6: otherlv_7= ',' ( (otherlv_8= RULE_ID ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,28,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_7, grammarAccess.getProvidedPortAccess().getCommaKeyword_5_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:1842:6: ( (otherlv_8= RULE_ID ) )
            	    	    // InternalThingML.g:1843:7: (otherlv_8= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1843:7: (otherlv_8= RULE_ID )
            	    	    // InternalThingML.g:1844:8: otherlv_8= RULE_ID
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
            	    // InternalThingML.g:1858:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1858:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    // InternalThingML.g:1859:5: otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    {
            	    otherlv_9=(Token)match(input,43,FOLLOW_6); 

            	    					newLeafNode(otherlv_9, grammarAccess.getProvidedPortAccess().getReceivesKeyword_5_1_0());
            	    				
            	    // InternalThingML.g:1863:5: ( (otherlv_10= RULE_ID ) )
            	    // InternalThingML.g:1864:6: (otherlv_10= RULE_ID )
            	    {
            	    // InternalThingML.g:1864:6: (otherlv_10= RULE_ID )
            	    // InternalThingML.g:1865:7: otherlv_10= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getProvidedPortRule());
            	    							}
            	    						
            	    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    							newLeafNode(otherlv_10, grammarAccess.getProvidedPortAccess().getReceivesMessageCrossReference_5_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1876:5: (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    loop41:
            	    do {
            	        int alt41=2;
            	        int LA41_0 = input.LA(1);

            	        if ( (LA41_0==28) ) {
            	            alt41=1;
            	        }


            	        switch (alt41) {
            	    	case 1 :
            	    	    // InternalThingML.g:1877:6: otherlv_11= ',' ( (otherlv_12= RULE_ID ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,28,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_11, grammarAccess.getProvidedPortAccess().getCommaKeyword_5_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:1881:6: ( (otherlv_12= RULE_ID ) )
            	    	    // InternalThingML.g:1882:7: (otherlv_12= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1882:7: (otherlv_12= RULE_ID )
            	    	    // InternalThingML.g:1883:8: otherlv_12= RULE_ID
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
    // InternalThingML.g:1905:1: entryRuleInternalPort returns [EObject current=null] : iv_ruleInternalPort= ruleInternalPort EOF ;
    public final EObject entryRuleInternalPort() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInternalPort = null;


        try {
            // InternalThingML.g:1905:53: (iv_ruleInternalPort= ruleInternalPort EOF )
            // InternalThingML.g:1906:2: iv_ruleInternalPort= ruleInternalPort EOF
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
    // InternalThingML.g:1912:1: ruleInternalPort returns [EObject current=null] : (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) ;
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
            // InternalThingML.g:1918:2: ( (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' ) )
            // InternalThingML.g:1919:2: (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            {
            // InternalThingML.g:1919:2: (otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}' )
            // InternalThingML.g:1920:3: otherlv_0= 'internal' otherlv_1= 'port' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )* otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,45,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getInternalPortAccess().getInternalKeyword_0());
            		
            otherlv_1=(Token)match(input,41,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getInternalPortAccess().getPortKeyword_1());
            		
            // InternalThingML.g:1928:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:1929:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:1929:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:1930:5: lv_name_2_0= RULE_ID
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

            // InternalThingML.g:1946:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==14) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalThingML.g:1947:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:1947:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:1948:5: lv_annotations_3_0= rulePlatformAnnotation
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
            		
            // InternalThingML.g:1969:3: ( (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* ) | (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* ) )*
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
            	    // InternalThingML.g:1970:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:1970:4: (otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )* )
            	    // InternalThingML.g:1971:5: otherlv_5= 'sends' ( (otherlv_6= RULE_ID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    {
            	    otherlv_5=(Token)match(input,42,FOLLOW_6); 

            	    					newLeafNode(otherlv_5, grammarAccess.getInternalPortAccess().getSendsKeyword_5_0_0());
            	    				
            	    // InternalThingML.g:1975:5: ( (otherlv_6= RULE_ID ) )
            	    // InternalThingML.g:1976:6: (otherlv_6= RULE_ID )
            	    {
            	    // InternalThingML.g:1976:6: (otherlv_6= RULE_ID )
            	    // InternalThingML.g:1977:7: otherlv_6= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getInternalPortRule());
            	    							}
            	    						
            	    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    							newLeafNode(otherlv_6, grammarAccess.getInternalPortAccess().getSendsMessageCrossReference_5_0_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:1988:5: (otherlv_7= ',' ( (otherlv_8= RULE_ID ) ) )*
            	    loop44:
            	    do {
            	        int alt44=2;
            	        int LA44_0 = input.LA(1);

            	        if ( (LA44_0==28) ) {
            	            alt44=1;
            	        }


            	        switch (alt44) {
            	    	case 1 :
            	    	    // InternalThingML.g:1989:6: otherlv_7= ',' ( (otherlv_8= RULE_ID ) )
            	    	    {
            	    	    otherlv_7=(Token)match(input,28,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_7, grammarAccess.getInternalPortAccess().getCommaKeyword_5_0_2_0());
            	    	    					
            	    	    // InternalThingML.g:1993:6: ( (otherlv_8= RULE_ID ) )
            	    	    // InternalThingML.g:1994:7: (otherlv_8= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:1994:7: (otherlv_8= RULE_ID )
            	    	    // InternalThingML.g:1995:8: otherlv_8= RULE_ID
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
            	    // InternalThingML.g:2009:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    {
            	    // InternalThingML.g:2009:4: (otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )* )
            	    // InternalThingML.g:2010:5: otherlv_9= 'receives' ( (otherlv_10= RULE_ID ) ) (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    {
            	    otherlv_9=(Token)match(input,43,FOLLOW_6); 

            	    					newLeafNode(otherlv_9, grammarAccess.getInternalPortAccess().getReceivesKeyword_5_1_0());
            	    				
            	    // InternalThingML.g:2014:5: ( (otherlv_10= RULE_ID ) )
            	    // InternalThingML.g:2015:6: (otherlv_10= RULE_ID )
            	    {
            	    // InternalThingML.g:2015:6: (otherlv_10= RULE_ID )
            	    // InternalThingML.g:2016:7: otherlv_10= RULE_ID
            	    {

            	    							if (current==null) {
            	    								current = createModelElement(grammarAccess.getInternalPortRule());
            	    							}
            	    						
            	    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_34); 

            	    							newLeafNode(otherlv_10, grammarAccess.getInternalPortAccess().getReceivesMessageCrossReference_5_1_1_0());
            	    						

            	    }


            	    }

            	    // InternalThingML.g:2027:5: (otherlv_11= ',' ( (otherlv_12= RULE_ID ) ) )*
            	    loop45:
            	    do {
            	        int alt45=2;
            	        int LA45_0 = input.LA(1);

            	        if ( (LA45_0==28) ) {
            	            alt45=1;
            	        }


            	        switch (alt45) {
            	    	case 1 :
            	    	    // InternalThingML.g:2028:6: otherlv_11= ',' ( (otherlv_12= RULE_ID ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,28,FOLLOW_6); 

            	    	    						newLeafNode(otherlv_11, grammarAccess.getInternalPortAccess().getCommaKeyword_5_1_2_0());
            	    	    					
            	    	    // InternalThingML.g:2032:6: ( (otherlv_12= RULE_ID ) )
            	    	    // InternalThingML.g:2033:7: (otherlv_12= RULE_ID )
            	    	    {
            	    	    // InternalThingML.g:2033:7: (otherlv_12= RULE_ID )
            	    	    // InternalThingML.g:2034:8: otherlv_12= RULE_ID
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


    // $ANTLR start "entryRuleStateMachine"
    // InternalThingML.g:2056:1: entryRuleStateMachine returns [EObject current=null] : iv_ruleStateMachine= ruleStateMachine EOF ;
    public final EObject entryRuleStateMachine() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStateMachine = null;


        try {
            // InternalThingML.g:2056:53: (iv_ruleStateMachine= ruleStateMachine EOF )
            // InternalThingML.g:2057:2: iv_ruleStateMachine= ruleStateMachine EOF
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
    // InternalThingML.g:2063:1: ruleStateMachine returns [EObject current=null] : (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' ) ;
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
            // InternalThingML.g:2069:2: ( (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' ) )
            // InternalThingML.g:2070:2: (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' )
            {
            // InternalThingML.g:2070:2: (otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}' )
            // InternalThingML.g:2071:3: otherlv_0= 'statechart' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )* ( (lv_region_17_0= ruleParallelRegion ) )* otherlv_18= '}'
            {
            otherlv_0=(Token)match(input,46,FOLLOW_35); 

            			newLeafNode(otherlv_0, grammarAccess.getStateMachineAccess().getStatechartKeyword_0());
            		
            // InternalThingML.g:2075:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==RULE_ID) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalThingML.g:2076:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:2076:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:2077:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_36); 

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

            otherlv_2=(Token)match(input,47,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getStateMachineAccess().getInitKeyword_2());
            		
            // InternalThingML.g:2097:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:2098:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:2098:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:2099:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStateMachineRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_37); 

            					newLeafNode(otherlv_3, grammarAccess.getStateMachineAccess().getInitialStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:2110:3: (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==48) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalThingML.g:2111:4: otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) )
                    {
                    otherlv_4=(Token)match(input,48,FOLLOW_38); 

                    				newLeafNode(otherlv_4, grammarAccess.getStateMachineAccess().getKeepsKeyword_4_0());
                    			
                    // InternalThingML.g:2115:4: ( (lv_history_5_0= 'history' ) )
                    // InternalThingML.g:2116:5: (lv_history_5_0= 'history' )
                    {
                    // InternalThingML.g:2116:5: (lv_history_5_0= 'history' )
                    // InternalThingML.g:2117:6: lv_history_5_0= 'history'
                    {
                    lv_history_5_0=(Token)match(input,49,FOLLOW_14); 

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

            // InternalThingML.g:2130:3: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==14) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalThingML.g:2131:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2131:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2132:5: lv_annotations_6_0= rulePlatformAnnotation
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
            	    break loop49;
                }
            } while (true);

            otherlv_7=(Token)match(input,23,FOLLOW_39); 

            			newLeafNode(otherlv_7, grammarAccess.getStateMachineAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:2153:3: ( (lv_properties_8_0= ruleProperty ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( ((LA50_0>=36 && LA50_0<=37)) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalThingML.g:2154:4: (lv_properties_8_0= ruleProperty )
            	    {
            	    // InternalThingML.g:2154:4: (lv_properties_8_0= ruleProperty )
            	    // InternalThingML.g:2155:5: lv_properties_8_0= ruleProperty
            	    {

            	    					newCompositeNode(grammarAccess.getStateMachineAccess().getPropertiesPropertyParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_39);
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
            	    break loop50;
                }
            } while (true);

            // InternalThingML.g:2172:3: (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==50) ) {
                int LA51_1 = input.LA(2);

                if ( (LA51_1==51) ) {
                    alt51=1;
                }
            }
            switch (alt51) {
                case 1 :
                    // InternalThingML.g:2173:4: otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) )
                    {
                    otherlv_9=(Token)match(input,50,FOLLOW_40); 

                    				newLeafNode(otherlv_9, grammarAccess.getStateMachineAccess().getOnKeyword_8_0());
                    			
                    otherlv_10=(Token)match(input,51,FOLLOW_27); 

                    				newLeafNode(otherlv_10, grammarAccess.getStateMachineAccess().getEntryKeyword_8_1());
                    			
                    // InternalThingML.g:2181:4: ( (lv_entry_11_0= ruleAction ) )
                    // InternalThingML.g:2182:5: (lv_entry_11_0= ruleAction )
                    {
                    // InternalThingML.g:2182:5: (lv_entry_11_0= ruleAction )
                    // InternalThingML.g:2183:6: lv_entry_11_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getStateMachineAccess().getEntryActionParserRuleCall_8_2_0());
                    					
                    pushFollow(FOLLOW_39);
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

            // InternalThingML.g:2201:3: (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==50) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalThingML.g:2202:4: otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) )
                    {
                    otherlv_12=(Token)match(input,50,FOLLOW_41); 

                    				newLeafNode(otherlv_12, grammarAccess.getStateMachineAccess().getOnKeyword_9_0());
                    			
                    otherlv_13=(Token)match(input,52,FOLLOW_27); 

                    				newLeafNode(otherlv_13, grammarAccess.getStateMachineAccess().getExitKeyword_9_1());
                    			
                    // InternalThingML.g:2210:4: ( (lv_exit_14_0= ruleAction ) )
                    // InternalThingML.g:2211:5: (lv_exit_14_0= ruleAction )
                    {
                    // InternalThingML.g:2211:5: (lv_exit_14_0= ruleAction )
                    // InternalThingML.g:2212:6: lv_exit_14_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getStateMachineAccess().getExitActionParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_42);
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

            // InternalThingML.g:2230:3: ( ( (lv_substate_15_0= ruleState ) ) | ( (lv_internal_16_0= ruleInternalTransition ) ) )*
            loop53:
            do {
                int alt53=3;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==46||(LA53_0>=53 && LA53_0<=56)) ) {
                    alt53=1;
                }
                else if ( (LA53_0==45) ) {
                    alt53=2;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalThingML.g:2231:4: ( (lv_substate_15_0= ruleState ) )
            	    {
            	    // InternalThingML.g:2231:4: ( (lv_substate_15_0= ruleState ) )
            	    // InternalThingML.g:2232:5: (lv_substate_15_0= ruleState )
            	    {
            	    // InternalThingML.g:2232:5: (lv_substate_15_0= ruleState )
            	    // InternalThingML.g:2233:6: lv_substate_15_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getSubstateStateParserRuleCall_10_0_0());
            	    					
            	    pushFollow(FOLLOW_42);
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
            	    // InternalThingML.g:2251:4: ( (lv_internal_16_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:2251:4: ( (lv_internal_16_0= ruleInternalTransition ) )
            	    // InternalThingML.g:2252:5: (lv_internal_16_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:2252:5: (lv_internal_16_0= ruleInternalTransition )
            	    // InternalThingML.g:2253:6: lv_internal_16_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getInternalInternalTransitionParserRuleCall_10_1_0());
            	    					
            	    pushFollow(FOLLOW_42);
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
            	    break loop53;
                }
            } while (true);

            // InternalThingML.g:2271:3: ( (lv_region_17_0= ruleParallelRegion ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==57) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalThingML.g:2272:4: (lv_region_17_0= ruleParallelRegion )
            	    {
            	    // InternalThingML.g:2272:4: (lv_region_17_0= ruleParallelRegion )
            	    // InternalThingML.g:2273:5: lv_region_17_0= ruleParallelRegion
            	    {

            	    					newCompositeNode(grammarAccess.getStateMachineAccess().getRegionParallelRegionParserRuleCall_11_0());
            	    				
            	    pushFollow(FOLLOW_43);
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
            	    break loop54;
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
    // InternalThingML.g:2298:1: entryRuleFinalState returns [EObject current=null] : iv_ruleFinalState= ruleFinalState EOF ;
    public final EObject entryRuleFinalState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFinalState = null;


        try {
            // InternalThingML.g:2298:51: (iv_ruleFinalState= ruleFinalState EOF )
            // InternalThingML.g:2299:2: iv_ruleFinalState= ruleFinalState EOF
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
    // InternalThingML.g:2305:1: ruleFinalState returns [EObject current=null] : (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}' ) ;
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
            // InternalThingML.g:2311:2: ( (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}' ) )
            // InternalThingML.g:2312:2: (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}' )
            {
            // InternalThingML.g:2312:2: (otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}' )
            // InternalThingML.g:2313:3: otherlv_0= 'final' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= rulePlatformAnnotation ) )* otherlv_4= '{' (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )? otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,53,FOLLOW_44); 

            			newLeafNode(otherlv_0, grammarAccess.getFinalStateAccess().getFinalKeyword_0());
            		
            otherlv_1=(Token)match(input,54,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getFinalStateAccess().getStateKeyword_1());
            		
            // InternalThingML.g:2321:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:2322:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:2322:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:2323:5: lv_name_2_0= RULE_ID
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

            // InternalThingML.g:2339:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==14) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalThingML.g:2340:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2340:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2341:5: lv_annotations_3_0= rulePlatformAnnotation
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
            	    break loop55;
                }
            } while (true);

            otherlv_4=(Token)match(input,23,FOLLOW_45); 

            			newLeafNode(otherlv_4, grammarAccess.getFinalStateAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalThingML.g:2362:3: (otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) ) )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==50) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // InternalThingML.g:2363:4: otherlv_5= 'on' otherlv_6= 'entry' ( (lv_entry_7_0= ruleAction ) )
                    {
                    otherlv_5=(Token)match(input,50,FOLLOW_40); 

                    				newLeafNode(otherlv_5, grammarAccess.getFinalStateAccess().getOnKeyword_5_0());
                    			
                    otherlv_6=(Token)match(input,51,FOLLOW_27); 

                    				newLeafNode(otherlv_6, grammarAccess.getFinalStateAccess().getEntryKeyword_5_1());
                    			
                    // InternalThingML.g:2371:4: ( (lv_entry_7_0= ruleAction ) )
                    // InternalThingML.g:2372:5: (lv_entry_7_0= ruleAction )
                    {
                    // InternalThingML.g:2372:5: (lv_entry_7_0= ruleAction )
                    // InternalThingML.g:2373:6: lv_entry_7_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getFinalStateAccess().getEntryActionParserRuleCall_5_2_0());
                    					
                    pushFollow(FOLLOW_46);
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
    // InternalThingML.g:2399:1: entryRuleCompositeState returns [EObject current=null] : iv_ruleCompositeState= ruleCompositeState EOF ;
    public final EObject entryRuleCompositeState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCompositeState = null;


        try {
            // InternalThingML.g:2399:55: (iv_ruleCompositeState= ruleCompositeState EOF )
            // InternalThingML.g:2400:2: iv_ruleCompositeState= ruleCompositeState EOF
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
    // InternalThingML.g:2406:1: ruleCompositeState returns [EObject current=null] : (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' ) ;
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
            // InternalThingML.g:2412:2: ( (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' ) )
            // InternalThingML.g:2413:2: (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' )
            {
            // InternalThingML.g:2413:2: (otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}' )
            // InternalThingML.g:2414:3: otherlv_0= 'composite' otherlv_1= 'state' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'init' ( (otherlv_4= RULE_ID ) ) (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )* ( (lv_region_19_0= ruleParallelRegion ) )* otherlv_20= '}'
            {
            otherlv_0=(Token)match(input,55,FOLLOW_44); 

            			newLeafNode(otherlv_0, grammarAccess.getCompositeStateAccess().getCompositeKeyword_0());
            		
            otherlv_1=(Token)match(input,54,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getCompositeStateAccess().getStateKeyword_1());
            		
            // InternalThingML.g:2422:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:2423:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:2423:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:2424:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_36); 

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

            otherlv_3=(Token)match(input,47,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getCompositeStateAccess().getInitKeyword_3());
            		
            // InternalThingML.g:2444:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:2445:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:2445:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:2446:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCompositeStateRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_37); 

            					newLeafNode(otherlv_4, grammarAccess.getCompositeStateAccess().getInitialStateCrossReference_4_0());
            				

            }


            }

            // InternalThingML.g:2457:3: (otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==48) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalThingML.g:2458:4: otherlv_5= 'keeps' ( (lv_history_6_0= 'history' ) )
                    {
                    otherlv_5=(Token)match(input,48,FOLLOW_38); 

                    				newLeafNode(otherlv_5, grammarAccess.getCompositeStateAccess().getKeepsKeyword_5_0());
                    			
                    // InternalThingML.g:2462:4: ( (lv_history_6_0= 'history' ) )
                    // InternalThingML.g:2463:5: (lv_history_6_0= 'history' )
                    {
                    // InternalThingML.g:2463:5: (lv_history_6_0= 'history' )
                    // InternalThingML.g:2464:6: lv_history_6_0= 'history'
                    {
                    lv_history_6_0=(Token)match(input,49,FOLLOW_14); 

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

            // InternalThingML.g:2477:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==14) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // InternalThingML.g:2478:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2478:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2479:5: lv_annotations_7_0= rulePlatformAnnotation
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
            	    break loop58;
                }
            } while (true);

            otherlv_8=(Token)match(input,23,FOLLOW_47); 

            			newLeafNode(otherlv_8, grammarAccess.getCompositeStateAccess().getLeftCurlyBracketKeyword_7());
            		
            // InternalThingML.g:2500:3: ( (lv_properties_9_0= ruleProperty ) )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( ((LA59_0>=36 && LA59_0<=37)) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // InternalThingML.g:2501:4: (lv_properties_9_0= ruleProperty )
            	    {
            	    // InternalThingML.g:2501:4: (lv_properties_9_0= ruleProperty )
            	    // InternalThingML.g:2502:5: lv_properties_9_0= ruleProperty
            	    {

            	    					newCompositeNode(grammarAccess.getCompositeStateAccess().getPropertiesPropertyParserRuleCall_8_0());
            	    				
            	    pushFollow(FOLLOW_47);
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
            	    break loop59;
                }
            } while (true);

            // InternalThingML.g:2519:3: (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==50) ) {
                int LA60_1 = input.LA(2);

                if ( (LA60_1==51) ) {
                    alt60=1;
                }
            }
            switch (alt60) {
                case 1 :
                    // InternalThingML.g:2520:4: otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) )
                    {
                    otherlv_10=(Token)match(input,50,FOLLOW_40); 

                    				newLeafNode(otherlv_10, grammarAccess.getCompositeStateAccess().getOnKeyword_9_0());
                    			
                    otherlv_11=(Token)match(input,51,FOLLOW_27); 

                    				newLeafNode(otherlv_11, grammarAccess.getCompositeStateAccess().getEntryKeyword_9_1());
                    			
                    // InternalThingML.g:2528:4: ( (lv_entry_12_0= ruleAction ) )
                    // InternalThingML.g:2529:5: (lv_entry_12_0= ruleAction )
                    {
                    // InternalThingML.g:2529:5: (lv_entry_12_0= ruleAction )
                    // InternalThingML.g:2530:6: lv_entry_12_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getCompositeStateAccess().getEntryActionParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_47);
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

            // InternalThingML.g:2548:3: (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==50) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalThingML.g:2549:4: otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) )
                    {
                    otherlv_13=(Token)match(input,50,FOLLOW_41); 

                    				newLeafNode(otherlv_13, grammarAccess.getCompositeStateAccess().getOnKeyword_10_0());
                    			
                    otherlv_14=(Token)match(input,52,FOLLOW_27); 

                    				newLeafNode(otherlv_14, grammarAccess.getCompositeStateAccess().getExitKeyword_10_1());
                    			
                    // InternalThingML.g:2557:4: ( (lv_exit_15_0= ruleAction ) )
                    // InternalThingML.g:2558:5: (lv_exit_15_0= ruleAction )
                    {
                    // InternalThingML.g:2558:5: (lv_exit_15_0= ruleAction )
                    // InternalThingML.g:2559:6: lv_exit_15_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getCompositeStateAccess().getExitActionParserRuleCall_10_2_0());
                    					
                    pushFollow(FOLLOW_48);
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

            // InternalThingML.g:2577:3: ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) | ( (lv_outgoing_18_0= ruleTransition ) ) )*
            loop62:
            do {
                int alt62=4;
                switch ( input.LA(1) ) {
                case 46:
                case 53:
                case 54:
                case 55:
                case 56:
                    {
                    alt62=1;
                    }
                    break;
                case 45:
                    {
                    alt62=2;
                    }
                    break;
                case 58:
                    {
                    alt62=3;
                    }
                    break;

                }

                switch (alt62) {
            	case 1 :
            	    // InternalThingML.g:2578:4: ( (lv_substate_16_0= ruleState ) )
            	    {
            	    // InternalThingML.g:2578:4: ( (lv_substate_16_0= ruleState ) )
            	    // InternalThingML.g:2579:5: (lv_substate_16_0= ruleState )
            	    {
            	    // InternalThingML.g:2579:5: (lv_substate_16_0= ruleState )
            	    // InternalThingML.g:2580:6: lv_substate_16_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getSubstateStateParserRuleCall_11_0_0());
            	    					
            	    pushFollow(FOLLOW_48);
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
            	    // InternalThingML.g:2598:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:2598:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    // InternalThingML.g:2599:5: (lv_internal_17_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:2599:5: (lv_internal_17_0= ruleInternalTransition )
            	    // InternalThingML.g:2600:6: lv_internal_17_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getInternalInternalTransitionParserRuleCall_11_1_0());
            	    					
            	    pushFollow(FOLLOW_48);
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
            	    // InternalThingML.g:2618:4: ( (lv_outgoing_18_0= ruleTransition ) )
            	    {
            	    // InternalThingML.g:2618:4: ( (lv_outgoing_18_0= ruleTransition ) )
            	    // InternalThingML.g:2619:5: (lv_outgoing_18_0= ruleTransition )
            	    {
            	    // InternalThingML.g:2619:5: (lv_outgoing_18_0= ruleTransition )
            	    // InternalThingML.g:2620:6: lv_outgoing_18_0= ruleTransition
            	    {

            	    						newCompositeNode(grammarAccess.getCompositeStateAccess().getOutgoingTransitionParserRuleCall_11_2_0());
            	    					
            	    pushFollow(FOLLOW_48);
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
            	    break loop62;
                }
            } while (true);

            // InternalThingML.g:2638:3: ( (lv_region_19_0= ruleParallelRegion ) )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==57) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // InternalThingML.g:2639:4: (lv_region_19_0= ruleParallelRegion )
            	    {
            	    // InternalThingML.g:2639:4: (lv_region_19_0= ruleParallelRegion )
            	    // InternalThingML.g:2640:5: lv_region_19_0= ruleParallelRegion
            	    {

            	    					newCompositeNode(grammarAccess.getCompositeStateAccess().getRegionParallelRegionParserRuleCall_12_0());
            	    				
            	    pushFollow(FOLLOW_43);
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
            	    break loop63;
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
    // InternalThingML.g:2665:1: entryRuleSession returns [EObject current=null] : iv_ruleSession= ruleSession EOF ;
    public final EObject entryRuleSession() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSession = null;


        try {
            // InternalThingML.g:2665:48: (iv_ruleSession= ruleSession EOF )
            // InternalThingML.g:2666:2: iv_ruleSession= ruleSession EOF
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
    // InternalThingML.g:2672:1: ruleSession returns [EObject current=null] : (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) )* ( (lv_region_18_0= ruleParallelRegion ) )* otherlv_19= '}' ) ;
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
            // InternalThingML.g:2678:2: ( (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) )* ( (lv_region_18_0= ruleParallelRegion ) )* otherlv_19= '}' ) )
            // InternalThingML.g:2679:2: (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) )* ( (lv_region_18_0= ruleParallelRegion ) )* otherlv_19= '}' )
            {
            // InternalThingML.g:2679:2: (otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) )* ( (lv_region_18_0= ruleParallelRegion ) )* otherlv_19= '}' )
            // InternalThingML.g:2680:3: otherlv_0= 'session' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>' )? otherlv_5= 'init' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* otherlv_8= '{' ( (lv_properties_9_0= ruleProperty ) )* (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )? (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )? ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) )* ( (lv_region_18_0= ruleParallelRegion ) )* otherlv_19= '}'
            {
            otherlv_0=(Token)match(input,56,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getSessionAccess().getSessionKeyword_0());
            		
            // InternalThingML.g:2684:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:2685:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:2685:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:2686:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_49); 

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

            // InternalThingML.g:2702:3: (otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>' )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==18) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalThingML.g:2703:4: otherlv_2= '<' ( (lv_maxInstances_3_0= RULE_INT ) ) otherlv_4= '>'
                    {
                    otherlv_2=(Token)match(input,18,FOLLOW_11); 

                    				newLeafNode(otherlv_2, grammarAccess.getSessionAccess().getLessThanSignKeyword_2_0());
                    			
                    // InternalThingML.g:2707:4: ( (lv_maxInstances_3_0= RULE_INT ) )
                    // InternalThingML.g:2708:5: (lv_maxInstances_3_0= RULE_INT )
                    {
                    // InternalThingML.g:2708:5: (lv_maxInstances_3_0= RULE_INT )
                    // InternalThingML.g:2709:6: lv_maxInstances_3_0= RULE_INT
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

                    otherlv_4=(Token)match(input,19,FOLLOW_36); 

                    				newLeafNode(otherlv_4, grammarAccess.getSessionAccess().getGreaterThanSignKeyword_2_2());
                    			

                    }
                    break;

            }

            otherlv_5=(Token)match(input,47,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getSessionAccess().getInitKeyword_3());
            		
            // InternalThingML.g:2734:3: ( (otherlv_6= RULE_ID ) )
            // InternalThingML.g:2735:4: (otherlv_6= RULE_ID )
            {
            // InternalThingML.g:2735:4: (otherlv_6= RULE_ID )
            // InternalThingML.g:2736:5: otherlv_6= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSessionRule());
            					}
            				
            otherlv_6=(Token)match(input,RULE_ID,FOLLOW_14); 

            					newLeafNode(otherlv_6, grammarAccess.getSessionAccess().getInitialStateCrossReference_4_0());
            				

            }


            }

            // InternalThingML.g:2747:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==14) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // InternalThingML.g:2748:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2748:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2749:5: lv_annotations_7_0= rulePlatformAnnotation
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
            	    break loop65;
                }
            } while (true);

            otherlv_8=(Token)match(input,23,FOLLOW_39); 

            			newLeafNode(otherlv_8, grammarAccess.getSessionAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:2770:3: ( (lv_properties_9_0= ruleProperty ) )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( ((LA66_0>=36 && LA66_0<=37)) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // InternalThingML.g:2771:4: (lv_properties_9_0= ruleProperty )
            	    {
            	    // InternalThingML.g:2771:4: (lv_properties_9_0= ruleProperty )
            	    // InternalThingML.g:2772:5: lv_properties_9_0= ruleProperty
            	    {

            	    					newCompositeNode(grammarAccess.getSessionAccess().getPropertiesPropertyParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_39);
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
            	    break loop66;
                }
            } while (true);

            // InternalThingML.g:2789:3: (otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) ) )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==50) ) {
                int LA67_1 = input.LA(2);

                if ( (LA67_1==51) ) {
                    alt67=1;
                }
            }
            switch (alt67) {
                case 1 :
                    // InternalThingML.g:2790:4: otherlv_10= 'on' otherlv_11= 'entry' ( (lv_entry_12_0= ruleAction ) )
                    {
                    otherlv_10=(Token)match(input,50,FOLLOW_40); 

                    				newLeafNode(otherlv_10, grammarAccess.getSessionAccess().getOnKeyword_8_0());
                    			
                    otherlv_11=(Token)match(input,51,FOLLOW_27); 

                    				newLeafNode(otherlv_11, grammarAccess.getSessionAccess().getEntryKeyword_8_1());
                    			
                    // InternalThingML.g:2798:4: ( (lv_entry_12_0= ruleAction ) )
                    // InternalThingML.g:2799:5: (lv_entry_12_0= ruleAction )
                    {
                    // InternalThingML.g:2799:5: (lv_entry_12_0= ruleAction )
                    // InternalThingML.g:2800:6: lv_entry_12_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getSessionAccess().getEntryActionParserRuleCall_8_2_0());
                    					
                    pushFollow(FOLLOW_39);
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

            // InternalThingML.g:2818:3: (otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) ) )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==50) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalThingML.g:2819:4: otherlv_13= 'on' otherlv_14= 'exit' ( (lv_exit_15_0= ruleAction ) )
                    {
                    otherlv_13=(Token)match(input,50,FOLLOW_41); 

                    				newLeafNode(otherlv_13, grammarAccess.getSessionAccess().getOnKeyword_9_0());
                    			
                    otherlv_14=(Token)match(input,52,FOLLOW_27); 

                    				newLeafNode(otherlv_14, grammarAccess.getSessionAccess().getExitKeyword_9_1());
                    			
                    // InternalThingML.g:2827:4: ( (lv_exit_15_0= ruleAction ) )
                    // InternalThingML.g:2828:5: (lv_exit_15_0= ruleAction )
                    {
                    // InternalThingML.g:2828:5: (lv_exit_15_0= ruleAction )
                    // InternalThingML.g:2829:6: lv_exit_15_0= ruleAction
                    {

                    						newCompositeNode(grammarAccess.getSessionAccess().getExitActionParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_42);
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

            // InternalThingML.g:2847:3: ( ( (lv_substate_16_0= ruleState ) ) | ( (lv_internal_17_0= ruleInternalTransition ) ) )*
            loop69:
            do {
                int alt69=3;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==46||(LA69_0>=53 && LA69_0<=56)) ) {
                    alt69=1;
                }
                else if ( (LA69_0==45) ) {
                    alt69=2;
                }


                switch (alt69) {
            	case 1 :
            	    // InternalThingML.g:2848:4: ( (lv_substate_16_0= ruleState ) )
            	    {
            	    // InternalThingML.g:2848:4: ( (lv_substate_16_0= ruleState ) )
            	    // InternalThingML.g:2849:5: (lv_substate_16_0= ruleState )
            	    {
            	    // InternalThingML.g:2849:5: (lv_substate_16_0= ruleState )
            	    // InternalThingML.g:2850:6: lv_substate_16_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getSessionAccess().getSubstateStateParserRuleCall_10_0_0());
            	    					
            	    pushFollow(FOLLOW_42);
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
            	    // InternalThingML.g:2868:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    {
            	    // InternalThingML.g:2868:4: ( (lv_internal_17_0= ruleInternalTransition ) )
            	    // InternalThingML.g:2869:5: (lv_internal_17_0= ruleInternalTransition )
            	    {
            	    // InternalThingML.g:2869:5: (lv_internal_17_0= ruleInternalTransition )
            	    // InternalThingML.g:2870:6: lv_internal_17_0= ruleInternalTransition
            	    {

            	    						newCompositeNode(grammarAccess.getSessionAccess().getInternalInternalTransitionParserRuleCall_10_1_0());
            	    					
            	    pushFollow(FOLLOW_42);
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
            	    break loop69;
                }
            } while (true);

            // InternalThingML.g:2888:3: ( (lv_region_18_0= ruleParallelRegion ) )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==57) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // InternalThingML.g:2889:4: (lv_region_18_0= ruleParallelRegion )
            	    {
            	    // InternalThingML.g:2889:4: (lv_region_18_0= ruleParallelRegion )
            	    // InternalThingML.g:2890:5: lv_region_18_0= ruleParallelRegion
            	    {

            	    					newCompositeNode(grammarAccess.getSessionAccess().getRegionParallelRegionParserRuleCall_11_0());
            	    				
            	    pushFollow(FOLLOW_43);
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
            	    break loop70;
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
    // InternalThingML.g:2915:1: entryRuleParallelRegion returns [EObject current=null] : iv_ruleParallelRegion= ruleParallelRegion EOF ;
    public final EObject entryRuleParallelRegion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParallelRegion = null;


        try {
            // InternalThingML.g:2915:55: (iv_ruleParallelRegion= ruleParallelRegion EOF )
            // InternalThingML.g:2916:2: iv_ruleParallelRegion= ruleParallelRegion EOF
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
    // InternalThingML.g:2922:1: ruleParallelRegion returns [EObject current=null] : (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) ;
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
            // InternalThingML.g:2928:2: ( (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' ) )
            // InternalThingML.g:2929:2: (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' )
            {
            // InternalThingML.g:2929:2: (otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}' )
            // InternalThingML.g:2930:3: otherlv_0= 'region' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= 'init' ( (otherlv_3= RULE_ID ) ) (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )? ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_substate_8_0= ruleState ) )* otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,57,FOLLOW_35); 

            			newLeafNode(otherlv_0, grammarAccess.getParallelRegionAccess().getRegionKeyword_0());
            		
            // InternalThingML.g:2934:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==RULE_ID) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalThingML.g:2935:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:2935:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:2936:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_36); 

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

            otherlv_2=(Token)match(input,47,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getParallelRegionAccess().getInitKeyword_2());
            		
            // InternalThingML.g:2956:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:2957:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:2957:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:2958:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getParallelRegionRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_37); 

            					newLeafNode(otherlv_3, grammarAccess.getParallelRegionAccess().getInitialStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:2969:3: (otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) ) )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==48) ) {
                alt72=1;
            }
            switch (alt72) {
                case 1 :
                    // InternalThingML.g:2970:4: otherlv_4= 'keeps' ( (lv_history_5_0= 'history' ) )
                    {
                    otherlv_4=(Token)match(input,48,FOLLOW_38); 

                    				newLeafNode(otherlv_4, grammarAccess.getParallelRegionAccess().getKeepsKeyword_4_0());
                    			
                    // InternalThingML.g:2974:4: ( (lv_history_5_0= 'history' ) )
                    // InternalThingML.g:2975:5: (lv_history_5_0= 'history' )
                    {
                    // InternalThingML.g:2975:5: (lv_history_5_0= 'history' )
                    // InternalThingML.g:2976:6: lv_history_5_0= 'history'
                    {
                    lv_history_5_0=(Token)match(input,49,FOLLOW_14); 

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

            // InternalThingML.g:2989:3: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( (LA73_0==14) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // InternalThingML.g:2990:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:2990:4: (lv_annotations_6_0= rulePlatformAnnotation )
            	    // InternalThingML.g:2991:5: lv_annotations_6_0= rulePlatformAnnotation
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
            	    break loop73;
                }
            } while (true);

            otherlv_7=(Token)match(input,23,FOLLOW_50); 

            			newLeafNode(otherlv_7, grammarAccess.getParallelRegionAccess().getLeftCurlyBracketKeyword_6());
            		
            // InternalThingML.g:3012:3: ( (lv_substate_8_0= ruleState ) )*
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( (LA74_0==46||(LA74_0>=53 && LA74_0<=56)) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // InternalThingML.g:3013:4: (lv_substate_8_0= ruleState )
            	    {
            	    // InternalThingML.g:3013:4: (lv_substate_8_0= ruleState )
            	    // InternalThingML.g:3014:5: lv_substate_8_0= ruleState
            	    {

            	    					newCompositeNode(grammarAccess.getParallelRegionAccess().getSubstateStateParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_50);
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
            	    break loop74;
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
    // InternalThingML.g:3039:1: entryRuleState returns [EObject current=null] : iv_ruleState= ruleState EOF ;
    public final EObject entryRuleState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleState = null;


        try {
            // InternalThingML.g:3039:46: (iv_ruleState= ruleState EOF )
            // InternalThingML.g:3040:2: iv_ruleState= ruleState EOF
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
    // InternalThingML.g:3046:1: ruleState returns [EObject current=null] : (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) ) ;
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
            // InternalThingML.g:3052:2: ( (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) ) )
            // InternalThingML.g:3053:2: (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) )
            {
            // InternalThingML.g:3053:2: (this_StateMachine_0= ruleStateMachine | this_FinalState_1= ruleFinalState | this_CompositeState_2= ruleCompositeState | this_Session_3= ruleSession | (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' ) )
            int alt80=5;
            switch ( input.LA(1) ) {
            case 46:
                {
                alt80=1;
                }
                break;
            case 53:
                {
                alt80=2;
                }
                break;
            case 55:
                {
                alt80=3;
                }
                break;
            case 56:
                {
                alt80=4;
                }
                break;
            case 54:
                {
                alt80=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }

            switch (alt80) {
                case 1 :
                    // InternalThingML.g:3054:3: this_StateMachine_0= ruleStateMachine
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
                    // InternalThingML.g:3063:3: this_FinalState_1= ruleFinalState
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
                    // InternalThingML.g:3072:3: this_CompositeState_2= ruleCompositeState
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
                    // InternalThingML.g:3081:3: this_Session_3= ruleSession
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
                    // InternalThingML.g:3090:3: (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' )
                    {
                    // InternalThingML.g:3090:3: (otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}' )
                    // InternalThingML.g:3091:4: otherlv_4= 'state' ( (lv_name_5_0= RULE_ID ) ) ( (lv_annotations_6_0= rulePlatformAnnotation ) )* otherlv_7= '{' ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )? (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )? ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )* otherlv_17= '}'
                    {
                    otherlv_4=(Token)match(input,54,FOLLOW_6); 

                    				newLeafNode(otherlv_4, grammarAccess.getStateAccess().getStateKeyword_4_0());
                    			
                    // InternalThingML.g:3095:4: ( (lv_name_5_0= RULE_ID ) )
                    // InternalThingML.g:3096:5: (lv_name_5_0= RULE_ID )
                    {
                    // InternalThingML.g:3096:5: (lv_name_5_0= RULE_ID )
                    // InternalThingML.g:3097:6: lv_name_5_0= RULE_ID
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

                    // InternalThingML.g:3113:4: ( (lv_annotations_6_0= rulePlatformAnnotation ) )*
                    loop75:
                    do {
                        int alt75=2;
                        int LA75_0 = input.LA(1);

                        if ( (LA75_0==14) ) {
                            alt75=1;
                        }


                        switch (alt75) {
                    	case 1 :
                    	    // InternalThingML.g:3114:5: (lv_annotations_6_0= rulePlatformAnnotation )
                    	    {
                    	    // InternalThingML.g:3114:5: (lv_annotations_6_0= rulePlatformAnnotation )
                    	    // InternalThingML.g:3115:6: lv_annotations_6_0= rulePlatformAnnotation
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
                    	    break loop75;
                        }
                    } while (true);

                    otherlv_7=(Token)match(input,23,FOLLOW_51); 

                    				newLeafNode(otherlv_7, grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_4_3());
                    			
                    // InternalThingML.g:3136:4: ( (lv_properties_8_0= ruleProperty ) )*
                    loop76:
                    do {
                        int alt76=2;
                        int LA76_0 = input.LA(1);

                        if ( ((LA76_0>=36 && LA76_0<=37)) ) {
                            alt76=1;
                        }


                        switch (alt76) {
                    	case 1 :
                    	    // InternalThingML.g:3137:5: (lv_properties_8_0= ruleProperty )
                    	    {
                    	    // InternalThingML.g:3137:5: (lv_properties_8_0= ruleProperty )
                    	    // InternalThingML.g:3138:6: lv_properties_8_0= ruleProperty
                    	    {

                    	    						newCompositeNode(grammarAccess.getStateAccess().getPropertiesPropertyParserRuleCall_4_4_0());
                    	    					
                    	    pushFollow(FOLLOW_51);
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
                    	    break loop76;
                        }
                    } while (true);

                    // InternalThingML.g:3155:4: (otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) ) )?
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==50) ) {
                        int LA77_1 = input.LA(2);

                        if ( (LA77_1==51) ) {
                            alt77=1;
                        }
                    }
                    switch (alt77) {
                        case 1 :
                            // InternalThingML.g:3156:5: otherlv_9= 'on' otherlv_10= 'entry' ( (lv_entry_11_0= ruleAction ) )
                            {
                            otherlv_9=(Token)match(input,50,FOLLOW_40); 

                            					newLeafNode(otherlv_9, grammarAccess.getStateAccess().getOnKeyword_4_5_0());
                            				
                            otherlv_10=(Token)match(input,51,FOLLOW_27); 

                            					newLeafNode(otherlv_10, grammarAccess.getStateAccess().getEntryKeyword_4_5_1());
                            				
                            // InternalThingML.g:3164:5: ( (lv_entry_11_0= ruleAction ) )
                            // InternalThingML.g:3165:6: (lv_entry_11_0= ruleAction )
                            {
                            // InternalThingML.g:3165:6: (lv_entry_11_0= ruleAction )
                            // InternalThingML.g:3166:7: lv_entry_11_0= ruleAction
                            {

                            							newCompositeNode(grammarAccess.getStateAccess().getEntryActionParserRuleCall_4_5_2_0());
                            						
                            pushFollow(FOLLOW_51);
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

                    // InternalThingML.g:3184:4: (otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) ) )?
                    int alt78=2;
                    int LA78_0 = input.LA(1);

                    if ( (LA78_0==50) ) {
                        alt78=1;
                    }
                    switch (alt78) {
                        case 1 :
                            // InternalThingML.g:3185:5: otherlv_12= 'on' otherlv_13= 'exit' ( (lv_exit_14_0= ruleAction ) )
                            {
                            otherlv_12=(Token)match(input,50,FOLLOW_41); 

                            					newLeafNode(otherlv_12, grammarAccess.getStateAccess().getOnKeyword_4_6_0());
                            				
                            otherlv_13=(Token)match(input,52,FOLLOW_27); 

                            					newLeafNode(otherlv_13, grammarAccess.getStateAccess().getExitKeyword_4_6_1());
                            				
                            // InternalThingML.g:3193:5: ( (lv_exit_14_0= ruleAction ) )
                            // InternalThingML.g:3194:6: (lv_exit_14_0= ruleAction )
                            {
                            // InternalThingML.g:3194:6: (lv_exit_14_0= ruleAction )
                            // InternalThingML.g:3195:7: lv_exit_14_0= ruleAction
                            {

                            							newCompositeNode(grammarAccess.getStateAccess().getExitActionParserRuleCall_4_6_2_0());
                            						
                            pushFollow(FOLLOW_52);
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

                    // InternalThingML.g:3213:4: ( ( (lv_internal_15_0= ruleInternalTransition ) ) | ( (lv_outgoing_16_0= ruleTransition ) ) )*
                    loop79:
                    do {
                        int alt79=3;
                        int LA79_0 = input.LA(1);

                        if ( (LA79_0==45) ) {
                            alt79=1;
                        }
                        else if ( (LA79_0==58) ) {
                            alt79=2;
                        }


                        switch (alt79) {
                    	case 1 :
                    	    // InternalThingML.g:3214:5: ( (lv_internal_15_0= ruleInternalTransition ) )
                    	    {
                    	    // InternalThingML.g:3214:5: ( (lv_internal_15_0= ruleInternalTransition ) )
                    	    // InternalThingML.g:3215:6: (lv_internal_15_0= ruleInternalTransition )
                    	    {
                    	    // InternalThingML.g:3215:6: (lv_internal_15_0= ruleInternalTransition )
                    	    // InternalThingML.g:3216:7: lv_internal_15_0= ruleInternalTransition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getInternalInternalTransitionParserRuleCall_4_7_0_0());
                    	    						
                    	    pushFollow(FOLLOW_52);
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
                    	    // InternalThingML.g:3234:5: ( (lv_outgoing_16_0= ruleTransition ) )
                    	    {
                    	    // InternalThingML.g:3234:5: ( (lv_outgoing_16_0= ruleTransition ) )
                    	    // InternalThingML.g:3235:6: (lv_outgoing_16_0= ruleTransition )
                    	    {
                    	    // InternalThingML.g:3235:6: (lv_outgoing_16_0= ruleTransition )
                    	    // InternalThingML.g:3236:7: lv_outgoing_16_0= ruleTransition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getOutgoingTransitionParserRuleCall_4_7_1_0());
                    	    						
                    	    pushFollow(FOLLOW_52);
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
                    	    break loop79;
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
    // InternalThingML.g:3263:1: entryRuleHandler returns [EObject current=null] : iv_ruleHandler= ruleHandler EOF ;
    public final EObject entryRuleHandler() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHandler = null;


        try {
            // InternalThingML.g:3263:48: (iv_ruleHandler= ruleHandler EOF )
            // InternalThingML.g:3264:2: iv_ruleHandler= ruleHandler EOF
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
    // InternalThingML.g:3270:1: ruleHandler returns [EObject current=null] : (this_Transition_0= ruleTransition | this_InternalTransition_1= ruleInternalTransition ) ;
    public final EObject ruleHandler() throws RecognitionException {
        EObject current = null;

        EObject this_Transition_0 = null;

        EObject this_InternalTransition_1 = null;



        	enterRule();

        try {
            // InternalThingML.g:3276:2: ( (this_Transition_0= ruleTransition | this_InternalTransition_1= ruleInternalTransition ) )
            // InternalThingML.g:3277:2: (this_Transition_0= ruleTransition | this_InternalTransition_1= ruleInternalTransition )
            {
            // InternalThingML.g:3277:2: (this_Transition_0= ruleTransition | this_InternalTransition_1= ruleInternalTransition )
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==58) ) {
                alt81=1;
            }
            else if ( (LA81_0==45) ) {
                alt81=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }
            switch (alt81) {
                case 1 :
                    // InternalThingML.g:3278:3: this_Transition_0= ruleTransition
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
                    // InternalThingML.g:3287:3: this_InternalTransition_1= ruleInternalTransition
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
    // InternalThingML.g:3299:1: entryRuleTransition returns [EObject current=null] : iv_ruleTransition= ruleTransition EOF ;
    public final EObject entryRuleTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransition = null;


        try {
            // InternalThingML.g:3299:51: (iv_ruleTransition= ruleTransition EOF )
            // InternalThingML.g:3300:2: iv_ruleTransition= ruleTransition EOF
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
    // InternalThingML.g:3306:1: ruleTransition returns [EObject current=null] : (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? ) ;
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
            // InternalThingML.g:3312:2: ( (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? ) )
            // InternalThingML.g:3313:2: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:3313:2: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )? )
            // InternalThingML.g:3314:3: otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )* (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )? (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )?
            {
            otherlv_0=(Token)match(input,58,FOLLOW_53); 

            			newLeafNode(otherlv_0, grammarAccess.getTransitionAccess().getTransitionKeyword_0());
            		
            // InternalThingML.g:3318:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==RULE_ID) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // InternalThingML.g:3319:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:3319:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:3320:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_54); 

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

            otherlv_2=(Token)match(input,59,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getTransitionAccess().getHyphenMinusGreaterThanSignKeyword_2());
            		
            // InternalThingML.g:3340:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:3341:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:3341:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:3342:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTransitionRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_55); 

            					newLeafNode(otherlv_3, grammarAccess.getTransitionAccess().getTargetStateCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:3353:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==14) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // InternalThingML.g:3354:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3354:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3355:5: lv_annotations_4_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_55);
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
            	    break loop83;
                }
            } while (true);

            // InternalThingML.g:3372:3: (otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) ) )*
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==60) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // InternalThingML.g:3373:4: otherlv_5= 'event' ( (lv_event_6_0= ruleEvent ) )
            	    {
            	    otherlv_5=(Token)match(input,60,FOLLOW_6); 

            	    				newLeafNode(otherlv_5, grammarAccess.getTransitionAccess().getEventKeyword_5_0());
            	    			
            	    // InternalThingML.g:3377:4: ( (lv_event_6_0= ruleEvent ) )
            	    // InternalThingML.g:3378:5: (lv_event_6_0= ruleEvent )
            	    {
            	    // InternalThingML.g:3378:5: (lv_event_6_0= ruleEvent )
            	    // InternalThingML.g:3379:6: lv_event_6_0= ruleEvent
            	    {

            	    						newCompositeNode(grammarAccess.getTransitionAccess().getEventEventParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_56);
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
            	    break loop84;
                }
            } while (true);

            // InternalThingML.g:3397:3: (otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) ) )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==61) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // InternalThingML.g:3398:4: otherlv_7= 'guard' ( (lv_guard_8_0= ruleExpression ) )
                    {
                    otherlv_7=(Token)match(input,61,FOLLOW_22); 

                    				newLeafNode(otherlv_7, grammarAccess.getTransitionAccess().getGuardKeyword_6_0());
                    			
                    // InternalThingML.g:3402:4: ( (lv_guard_8_0= ruleExpression ) )
                    // InternalThingML.g:3403:5: (lv_guard_8_0= ruleExpression )
                    {
                    // InternalThingML.g:3403:5: (lv_guard_8_0= ruleExpression )
                    // InternalThingML.g:3404:6: lv_guard_8_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getTransitionAccess().getGuardExpressionParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_57);
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

            // InternalThingML.g:3422:3: (otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) ) )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==62) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // InternalThingML.g:3423:4: otherlv_9= 'action' ( (lv_action_10_0= ruleAction ) )
                    {
                    otherlv_9=(Token)match(input,62,FOLLOW_27); 

                    				newLeafNode(otherlv_9, grammarAccess.getTransitionAccess().getActionKeyword_7_0());
                    			
                    // InternalThingML.g:3427:4: ( (lv_action_10_0= ruleAction ) )
                    // InternalThingML.g:3428:5: (lv_action_10_0= ruleAction )
                    {
                    // InternalThingML.g:3428:5: (lv_action_10_0= ruleAction )
                    // InternalThingML.g:3429:6: lv_action_10_0= ruleAction
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
    // InternalThingML.g:3451:1: entryRuleInternalTransition returns [EObject current=null] : iv_ruleInternalTransition= ruleInternalTransition EOF ;
    public final EObject entryRuleInternalTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInternalTransition = null;


        try {
            // InternalThingML.g:3451:59: (iv_ruleInternalTransition= ruleInternalTransition EOF )
            // InternalThingML.g:3452:2: iv_ruleInternalTransition= ruleInternalTransition EOF
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
    // InternalThingML.g:3458:1: ruleInternalTransition returns [EObject current=null] : ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? ) ;
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
            // InternalThingML.g:3464:2: ( ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? ) )
            // InternalThingML.g:3465:2: ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:3465:2: ( () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )? )
            // InternalThingML.g:3466:3: () otherlv_1= 'internal' ( (lv_name_2_0= RULE_ID ) )? ( (lv_annotations_3_0= rulePlatformAnnotation ) )* (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )* (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )? (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )?
            {
            // InternalThingML.g:3466:3: ()
            // InternalThingML.g:3467:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getInternalTransitionAccess().getInternalTransitionAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,45,FOLLOW_58); 

            			newLeafNode(otherlv_1, grammarAccess.getInternalTransitionAccess().getInternalKeyword_1());
            		
            // InternalThingML.g:3477:3: ( (lv_name_2_0= RULE_ID ) )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==RULE_ID) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // InternalThingML.g:3478:4: (lv_name_2_0= RULE_ID )
                    {
                    // InternalThingML.g:3478:4: (lv_name_2_0= RULE_ID )
                    // InternalThingML.g:3479:5: lv_name_2_0= RULE_ID
                    {
                    lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_55); 

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

            // InternalThingML.g:3495:3: ( (lv_annotations_3_0= rulePlatformAnnotation ) )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( (LA88_0==14) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // InternalThingML.g:3496:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:3496:4: (lv_annotations_3_0= rulePlatformAnnotation )
            	    // InternalThingML.g:3497:5: lv_annotations_3_0= rulePlatformAnnotation
            	    {

            	    					newCompositeNode(grammarAccess.getInternalTransitionAccess().getAnnotationsPlatformAnnotationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_55);
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
            	    break loop88;
                }
            } while (true);

            // InternalThingML.g:3514:3: (otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) ) )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==60) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // InternalThingML.g:3515:4: otherlv_4= 'event' ( (lv_event_5_0= ruleEvent ) )
            	    {
            	    otherlv_4=(Token)match(input,60,FOLLOW_6); 

            	    				newLeafNode(otherlv_4, grammarAccess.getInternalTransitionAccess().getEventKeyword_4_0());
            	    			
            	    // InternalThingML.g:3519:4: ( (lv_event_5_0= ruleEvent ) )
            	    // InternalThingML.g:3520:5: (lv_event_5_0= ruleEvent )
            	    {
            	    // InternalThingML.g:3520:5: (lv_event_5_0= ruleEvent )
            	    // InternalThingML.g:3521:6: lv_event_5_0= ruleEvent
            	    {

            	    						newCompositeNode(grammarAccess.getInternalTransitionAccess().getEventEventParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_56);
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
            	    break loop89;
                }
            } while (true);

            // InternalThingML.g:3539:3: (otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) ) )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==61) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // InternalThingML.g:3540:4: otherlv_6= 'guard' ( (lv_guard_7_0= ruleExpression ) )
                    {
                    otherlv_6=(Token)match(input,61,FOLLOW_22); 

                    				newLeafNode(otherlv_6, grammarAccess.getInternalTransitionAccess().getGuardKeyword_5_0());
                    			
                    // InternalThingML.g:3544:4: ( (lv_guard_7_0= ruleExpression ) )
                    // InternalThingML.g:3545:5: (lv_guard_7_0= ruleExpression )
                    {
                    // InternalThingML.g:3545:5: (lv_guard_7_0= ruleExpression )
                    // InternalThingML.g:3546:6: lv_guard_7_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getInternalTransitionAccess().getGuardExpressionParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_57);
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

            // InternalThingML.g:3564:3: (otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) ) )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==62) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // InternalThingML.g:3565:4: otherlv_8= 'action' ( (lv_action_9_0= ruleAction ) )
                    {
                    otherlv_8=(Token)match(input,62,FOLLOW_27); 

                    				newLeafNode(otherlv_8, grammarAccess.getInternalTransitionAccess().getActionKeyword_6_0());
                    			
                    // InternalThingML.g:3569:4: ( (lv_action_9_0= ruleAction ) )
                    // InternalThingML.g:3570:5: (lv_action_9_0= ruleAction )
                    {
                    // InternalThingML.g:3570:5: (lv_action_9_0= ruleAction )
                    // InternalThingML.g:3571:6: lv_action_9_0= ruleAction
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
    // InternalThingML.g:3593:1: entryRuleEvent returns [EObject current=null] : iv_ruleEvent= ruleEvent EOF ;
    public final EObject entryRuleEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEvent = null;


        try {
            // InternalThingML.g:3593:46: (iv_ruleEvent= ruleEvent EOF )
            // InternalThingML.g:3594:2: iv_ruleEvent= ruleEvent EOF
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
    // InternalThingML.g:3600:1: ruleEvent returns [EObject current=null] : this_ReceiveMessage_0= ruleReceiveMessage ;
    public final EObject ruleEvent() throws RecognitionException {
        EObject current = null;

        EObject this_ReceiveMessage_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3606:2: (this_ReceiveMessage_0= ruleReceiveMessage )
            // InternalThingML.g:3607:2: this_ReceiveMessage_0= ruleReceiveMessage
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
    // InternalThingML.g:3618:1: entryRuleReceiveMessage returns [EObject current=null] : iv_ruleReceiveMessage= ruleReceiveMessage EOF ;
    public final EObject entryRuleReceiveMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReceiveMessage = null;


        try {
            // InternalThingML.g:3618:55: (iv_ruleReceiveMessage= ruleReceiveMessage EOF )
            // InternalThingML.g:3619:2: iv_ruleReceiveMessage= ruleReceiveMessage EOF
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
    // InternalThingML.g:3625:1: ruleReceiveMessage returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) ) ;
    public final EObject ruleReceiveMessage() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;


        	enterRule();

        try {
            // InternalThingML.g:3631:2: ( ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) ) )
            // InternalThingML.g:3632:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) )
            {
            // InternalThingML.g:3632:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) ) )
            // InternalThingML.g:3633:3: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '?' ( (otherlv_4= RULE_ID ) )
            {
            // InternalThingML.g:3633:3: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )?
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==RULE_ID) ) {
                int LA92_1 = input.LA(2);

                if ( (LA92_1==35) ) {
                    alt92=1;
                }
            }
            switch (alt92) {
                case 1 :
                    // InternalThingML.g:3634:4: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':'
                    {
                    // InternalThingML.g:3634:4: ( (lv_name_0_0= RULE_ID ) )
                    // InternalThingML.g:3635:5: (lv_name_0_0= RULE_ID )
                    {
                    // InternalThingML.g:3635:5: (lv_name_0_0= RULE_ID )
                    // InternalThingML.g:3636:6: lv_name_0_0= RULE_ID
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

            // InternalThingML.g:3657:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:3658:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:3658:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:3659:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReceiveMessageRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_59); 

            					newLeafNode(otherlv_2, grammarAccess.getReceiveMessageAccess().getPortPortCrossReference_1_0());
            				

            }


            }

            otherlv_3=(Token)match(input,63,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getReceiveMessageAccess().getQuestionMarkKeyword_2());
            		
            // InternalThingML.g:3674:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:3675:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:3675:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:3676:5: otherlv_4= RULE_ID
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
    // InternalThingML.g:3691:1: entryRuleAction returns [EObject current=null] : iv_ruleAction= ruleAction EOF ;
    public final EObject entryRuleAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAction = null;


        try {
            // InternalThingML.g:3691:47: (iv_ruleAction= ruleAction EOF )
            // InternalThingML.g:3692:2: iv_ruleAction= ruleAction EOF
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
    // InternalThingML.g:3698:1: ruleAction returns [EObject current=null] : (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable ) ;
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
            // InternalThingML.g:3704:2: ( (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable ) )
            // InternalThingML.g:3705:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )
            {
            // InternalThingML.g:3705:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )
            int alt93=14;
            alt93 = dfa93.predict(input);
            switch (alt93) {
                case 1 :
                    // InternalThingML.g:3706:3: this_ActionBlock_0= ruleActionBlock
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
                    // InternalThingML.g:3715:3: this_ExternStatement_1= ruleExternStatement
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
                    // InternalThingML.g:3724:3: this_SendAction_2= ruleSendAction
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
                    // InternalThingML.g:3733:3: this_VariableAssignment_3= ruleVariableAssignment
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
                    // InternalThingML.g:3742:3: this_Increment_4= ruleIncrement
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
                    // InternalThingML.g:3751:3: this_Decrement_5= ruleDecrement
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
                    // InternalThingML.g:3760:3: this_LoopAction_6= ruleLoopAction
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
                    // InternalThingML.g:3769:3: this_ConditionalAction_7= ruleConditionalAction
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
                    // InternalThingML.g:3778:3: this_ReturnAction_8= ruleReturnAction
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
                    // InternalThingML.g:3787:3: this_PrintAction_9= rulePrintAction
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
                    // InternalThingML.g:3796:3: this_ErrorAction_10= ruleErrorAction
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
                    // InternalThingML.g:3805:3: this_StartSession_11= ruleStartSession
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
                    // InternalThingML.g:3814:3: this_FunctionCallStatement_12= ruleFunctionCallStatement
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
                    // InternalThingML.g:3823:3: this_LocalVariable_13= ruleLocalVariable
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
    // InternalThingML.g:3835:1: entryRuleActionBlock returns [EObject current=null] : iv_ruleActionBlock= ruleActionBlock EOF ;
    public final EObject entryRuleActionBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActionBlock = null;


        try {
            // InternalThingML.g:3835:52: (iv_ruleActionBlock= ruleActionBlock EOF )
            // InternalThingML.g:3836:2: iv_ruleActionBlock= ruleActionBlock EOF
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
    // InternalThingML.g:3842:1: ruleActionBlock returns [EObject current=null] : ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' ) ;
    public final EObject ruleActionBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_actions_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3848:2: ( ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' ) )
            // InternalThingML.g:3849:2: ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' )
            {
            // InternalThingML.g:3849:2: ( () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end' )
            // InternalThingML.g:3850:3: () otherlv_1= 'do' ( (lv_actions_2_0= ruleAction ) )* otherlv_3= 'end'
            {
            // InternalThingML.g:3850:3: ()
            // InternalThingML.g:3851:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getActionBlockAccess().getActionBlockAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,64,FOLLOW_60); 

            			newLeafNode(otherlv_1, grammarAccess.getActionBlockAccess().getDoKeyword_1());
            		
            // InternalThingML.g:3861:3: ( (lv_actions_2_0= ruleAction ) )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==RULE_ID||LA94_0==RULE_STRING_EXT||LA94_0==36||LA94_0==64||LA94_0==67||(LA94_0>=71 && LA94_0<=72)||(LA94_0>=74 && LA94_0<=77)) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // InternalThingML.g:3862:4: (lv_actions_2_0= ruleAction )
            	    {
            	    // InternalThingML.g:3862:4: (lv_actions_2_0= ruleAction )
            	    // InternalThingML.g:3863:5: lv_actions_2_0= ruleAction
            	    {

            	    					newCompositeNode(grammarAccess.getActionBlockAccess().getActionsActionParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_60);
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

            otherlv_3=(Token)match(input,65,FOLLOW_2); 

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
    // InternalThingML.g:3888:1: entryRuleExternStatement returns [EObject current=null] : iv_ruleExternStatement= ruleExternStatement EOF ;
    public final EObject entryRuleExternStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternStatement = null;


        try {
            // InternalThingML.g:3888:56: (iv_ruleExternStatement= ruleExternStatement EOF )
            // InternalThingML.g:3889:2: iv_ruleExternStatement= ruleExternStatement EOF
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
    // InternalThingML.g:3895:1: ruleExternStatement returns [EObject current=null] : ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) ;
    public final EObject ruleExternStatement() throws RecognitionException {
        EObject current = null;

        Token lv_statement_0_0=null;
        Token otherlv_1=null;
        EObject lv_segments_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:3901:2: ( ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) )
            // InternalThingML.g:3902:2: ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            {
            // InternalThingML.g:3902:2: ( ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            // InternalThingML.g:3903:3: ( (lv_statement_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            {
            // InternalThingML.g:3903:3: ( (lv_statement_0_0= RULE_STRING_EXT ) )
            // InternalThingML.g:3904:4: (lv_statement_0_0= RULE_STRING_EXT )
            {
            // InternalThingML.g:3904:4: (lv_statement_0_0= RULE_STRING_EXT )
            // InternalThingML.g:3905:5: lv_statement_0_0= RULE_STRING_EXT
            {
            lv_statement_0_0=(Token)match(input,RULE_STRING_EXT,FOLLOW_61); 

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

            // InternalThingML.g:3921:3: (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            loop95:
            do {
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( (LA95_0==66) ) {
                    alt95=1;
                }


                switch (alt95) {
            	case 1 :
            	    // InternalThingML.g:3922:4: otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,66,FOLLOW_22); 

            	    				newLeafNode(otherlv_1, grammarAccess.getExternStatementAccess().getAmpersandKeyword_1_0());
            	    			
            	    // InternalThingML.g:3926:4: ( (lv_segments_2_0= ruleExpression ) )
            	    // InternalThingML.g:3927:5: (lv_segments_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:3927:5: (lv_segments_2_0= ruleExpression )
            	    // InternalThingML.g:3928:6: lv_segments_2_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getExternStatementAccess().getSegmentsExpressionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_61);
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
    // InternalThingML.g:3950:1: entryRuleLocalVariable returns [EObject current=null] : iv_ruleLocalVariable= ruleLocalVariable EOF ;
    public final EObject entryRuleLocalVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalVariable = null;


        try {
            // InternalThingML.g:3950:54: (iv_ruleLocalVariable= ruleLocalVariable EOF )
            // InternalThingML.g:3951:2: iv_ruleLocalVariable= ruleLocalVariable EOF
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
    // InternalThingML.g:3957:1: ruleLocalVariable returns [EObject current=null] : ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:3963:2: ( ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:3964:2: ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:3964:2: ( ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:3965:3: ( (lv_changeable_0_0= 'readonly' ) )? otherlv_1= 'var' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( (lv_typeRef_4_0= ruleTypeRef ) ) (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )? ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            // InternalThingML.g:3965:3: ( (lv_changeable_0_0= 'readonly' ) )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==36) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // InternalThingML.g:3966:4: (lv_changeable_0_0= 'readonly' )
                    {
                    // InternalThingML.g:3966:4: (lv_changeable_0_0= 'readonly' )
                    // InternalThingML.g:3967:5: lv_changeable_0_0= 'readonly'
                    {
                    lv_changeable_0_0=(Token)match(input,36,FOLLOW_62); 

                    					newLeafNode(lv_changeable_0_0, grammarAccess.getLocalVariableAccess().getChangeableReadonlyKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getLocalVariableRule());
                    					}
                    					setWithLastConsumed(current, "changeable", true, "readonly");
                    				

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,67,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getLocalVariableAccess().getVarKeyword_1());
            		
            // InternalThingML.g:3983:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalThingML.g:3984:4: (lv_name_2_0= RULE_ID )
            {
            // InternalThingML.g:3984:4: (lv_name_2_0= RULE_ID )
            // InternalThingML.g:3985:5: lv_name_2_0= RULE_ID
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
            		
            // InternalThingML.g:4005:3: ( (lv_typeRef_4_0= ruleTypeRef ) )
            // InternalThingML.g:4006:4: (lv_typeRef_4_0= ruleTypeRef )
            {
            // InternalThingML.g:4006:4: (lv_typeRef_4_0= ruleTypeRef )
            // InternalThingML.g:4007:5: lv_typeRef_4_0= ruleTypeRef
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

            // InternalThingML.g:4024:3: (otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) ) )?
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==30) ) {
                alt97=1;
            }
            switch (alt97) {
                case 1 :
                    // InternalThingML.g:4025:4: otherlv_5= '=' ( (lv_init_6_0= ruleExpression ) )
                    {
                    otherlv_5=(Token)match(input,30,FOLLOW_22); 

                    				newLeafNode(otherlv_5, grammarAccess.getLocalVariableAccess().getEqualsSignKeyword_5_0());
                    			
                    // InternalThingML.g:4029:4: ( (lv_init_6_0= ruleExpression ) )
                    // InternalThingML.g:4030:5: (lv_init_6_0= ruleExpression )
                    {
                    // InternalThingML.g:4030:5: (lv_init_6_0= ruleExpression )
                    // InternalThingML.g:4031:6: lv_init_6_0= ruleExpression
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

            // InternalThingML.g:4049:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop98:
            do {
                int alt98=2;
                int LA98_0 = input.LA(1);

                if ( (LA98_0==14) ) {
                    alt98=1;
                }


                switch (alt98) {
            	case 1 :
            	    // InternalThingML.g:4050:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:4050:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:4051:5: lv_annotations_7_0= rulePlatformAnnotation
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
    // InternalThingML.g:4072:1: entryRuleSendAction returns [EObject current=null] : iv_ruleSendAction= ruleSendAction EOF ;
    public final EObject entryRuleSendAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSendAction = null;


        try {
            // InternalThingML.g:4072:51: (iv_ruleSendAction= ruleSendAction EOF )
            // InternalThingML.g:4073:2: iv_ruleSendAction= ruleSendAction EOF
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
    // InternalThingML.g:4079:1: ruleSendAction returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' ) ;
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
            // InternalThingML.g:4085:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' ) )
            // InternalThingML.g:4086:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' )
            {
            // InternalThingML.g:4086:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')' )
            // InternalThingML.g:4087:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '!' ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )? otherlv_7= ')'
            {
            // InternalThingML.g:4087:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4088:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4088:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4089:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSendActionRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_63); 

            					newLeafNode(otherlv_0, grammarAccess.getSendActionAccess().getPortPortCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,68,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getSendActionAccess().getExclamationMarkKeyword_1());
            		
            // InternalThingML.g:4104:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:4105:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:4105:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:4106:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSendActionRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_24); 

            					newLeafNode(otherlv_2, grammarAccess.getSendActionAccess().getMessageMessageCrossReference_2_0());
            				

            }


            }

            otherlv_3=(Token)match(input,33,FOLLOW_64); 

            			newLeafNode(otherlv_3, grammarAccess.getSendActionAccess().getLeftParenthesisKeyword_3());
            		
            // InternalThingML.g:4121:3: ( ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )* )?
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( ((LA100_0>=RULE_STRING_LIT && LA100_0<=RULE_FLOAT)||LA100_0==33||LA100_0==85||(LA100_0>=89 && LA100_0<=91)) ) {
                alt100=1;
            }
            switch (alt100) {
                case 1 :
                    // InternalThingML.g:4122:4: ( (lv_parameters_4_0= ruleExpression ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )*
                    {
                    // InternalThingML.g:4122:4: ( (lv_parameters_4_0= ruleExpression ) )
                    // InternalThingML.g:4123:5: (lv_parameters_4_0= ruleExpression )
                    {
                    // InternalThingML.g:4123:5: (lv_parameters_4_0= ruleExpression )
                    // InternalThingML.g:4124:6: lv_parameters_4_0= ruleExpression
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

                    // InternalThingML.g:4141:4: (otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) ) )*
                    loop99:
                    do {
                        int alt99=2;
                        int LA99_0 = input.LA(1);

                        if ( (LA99_0==28) ) {
                            alt99=1;
                        }


                        switch (alt99) {
                    	case 1 :
                    	    // InternalThingML.g:4142:5: otherlv_5= ',' ( (lv_parameters_6_0= ruleExpression ) )
                    	    {
                    	    otherlv_5=(Token)match(input,28,FOLLOW_22); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getSendActionAccess().getCommaKeyword_4_1_0());
                    	    				
                    	    // InternalThingML.g:4146:5: ( (lv_parameters_6_0= ruleExpression ) )
                    	    // InternalThingML.g:4147:6: (lv_parameters_6_0= ruleExpression )
                    	    {
                    	    // InternalThingML.g:4147:6: (lv_parameters_6_0= ruleExpression )
                    	    // InternalThingML.g:4148:7: lv_parameters_6_0= ruleExpression
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
                    	    break loop99;
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
    // InternalThingML.g:4175:1: entryRuleVariableAssignment returns [EObject current=null] : iv_ruleVariableAssignment= ruleVariableAssignment EOF ;
    public final EObject entryRuleVariableAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableAssignment = null;


        try {
            // InternalThingML.g:4175:59: (iv_ruleVariableAssignment= ruleVariableAssignment EOF )
            // InternalThingML.g:4176:2: iv_ruleVariableAssignment= ruleVariableAssignment EOF
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
    // InternalThingML.g:4182:1: ruleVariableAssignment returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) ) ;
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
            // InternalThingML.g:4188:2: ( ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) ) )
            // InternalThingML.g:4189:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) )
            {
            // InternalThingML.g:4189:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) ) )
            // InternalThingML.g:4190:3: ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )* otherlv_4= '=' ( (lv_expression_5_0= ruleExpression ) )
            {
            // InternalThingML.g:4190:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4191:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4191:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4192:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVariableAssignmentRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_0, grammarAccess.getVariableAssignmentAccess().getPropertyVariableCrossReference_0_0());
            				

            }


            }

            // InternalThingML.g:4203:3: (otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']' )*
            loop101:
            do {
                int alt101=2;
                int LA101_0 = input.LA(1);

                if ( (LA101_0==15) ) {
                    alt101=1;
                }


                switch (alt101) {
            	case 1 :
            	    // InternalThingML.g:4204:4: otherlv_1= '[' ( (lv_index_2_0= ruleExpression ) ) otherlv_3= ']'
            	    {
            	    otherlv_1=(Token)match(input,15,FOLLOW_22); 

            	    				newLeafNode(otherlv_1, grammarAccess.getVariableAssignmentAccess().getLeftSquareBracketKeyword_1_0());
            	    			
            	    // InternalThingML.g:4208:4: ( (lv_index_2_0= ruleExpression ) )
            	    // InternalThingML.g:4209:5: (lv_index_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:4209:5: (lv_index_2_0= ruleExpression )
            	    // InternalThingML.g:4210:6: lv_index_2_0= ruleExpression
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
            	    break loop101;
                }
            } while (true);

            otherlv_4=(Token)match(input,30,FOLLOW_22); 

            			newLeafNode(otherlv_4, grammarAccess.getVariableAssignmentAccess().getEqualsSignKeyword_2());
            		
            // InternalThingML.g:4236:3: ( (lv_expression_5_0= ruleExpression ) )
            // InternalThingML.g:4237:4: (lv_expression_5_0= ruleExpression )
            {
            // InternalThingML.g:4237:4: (lv_expression_5_0= ruleExpression )
            // InternalThingML.g:4238:5: lv_expression_5_0= ruleExpression
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
    // InternalThingML.g:4259:1: entryRuleIncrement returns [EObject current=null] : iv_ruleIncrement= ruleIncrement EOF ;
    public final EObject entryRuleIncrement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIncrement = null;


        try {
            // InternalThingML.g:4259:50: (iv_ruleIncrement= ruleIncrement EOF )
            // InternalThingML.g:4260:2: iv_ruleIncrement= ruleIncrement EOF
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
    // InternalThingML.g:4266:1: ruleIncrement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' ) ;
    public final EObject ruleIncrement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:4272:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' ) )
            // InternalThingML.g:4273:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' )
            {
            // InternalThingML.g:4273:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '++' )
            // InternalThingML.g:4274:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '++'
            {
            // InternalThingML.g:4274:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4275:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4275:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4276:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getIncrementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_65); 

            					newLeafNode(otherlv_0, grammarAccess.getIncrementAccess().getVarVariableCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,69,FOLLOW_2); 

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
    // InternalThingML.g:4295:1: entryRuleDecrement returns [EObject current=null] : iv_ruleDecrement= ruleDecrement EOF ;
    public final EObject entryRuleDecrement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDecrement = null;


        try {
            // InternalThingML.g:4295:50: (iv_ruleDecrement= ruleDecrement EOF )
            // InternalThingML.g:4296:2: iv_ruleDecrement= ruleDecrement EOF
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
    // InternalThingML.g:4302:1: ruleDecrement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' ) ;
    public final EObject ruleDecrement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:4308:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' ) )
            // InternalThingML.g:4309:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' )
            {
            // InternalThingML.g:4309:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '--' )
            // InternalThingML.g:4310:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '--'
            {
            // InternalThingML.g:4310:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4311:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4311:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4312:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDecrementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_66); 

            					newLeafNode(otherlv_0, grammarAccess.getDecrementAccess().getVarVariableCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,70,FOLLOW_2); 

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
    // InternalThingML.g:4331:1: entryRuleLoopAction returns [EObject current=null] : iv_ruleLoopAction= ruleLoopAction EOF ;
    public final EObject entryRuleLoopAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLoopAction = null;


        try {
            // InternalThingML.g:4331:51: (iv_ruleLoopAction= ruleLoopAction EOF )
            // InternalThingML.g:4332:2: iv_ruleLoopAction= ruleLoopAction EOF
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
    // InternalThingML.g:4338:1: ruleLoopAction returns [EObject current=null] : (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) ) ;
    public final EObject ruleLoopAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_condition_2_0 = null;

        EObject lv_action_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4344:2: ( (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) ) )
            // InternalThingML.g:4345:2: (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) )
            {
            // InternalThingML.g:4345:2: (otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) )
            // InternalThingML.g:4346:3: otherlv_0= 'while' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) )
            {
            otherlv_0=(Token)match(input,71,FOLLOW_24); 

            			newLeafNode(otherlv_0, grammarAccess.getLoopActionAccess().getWhileKeyword_0());
            		
            otherlv_1=(Token)match(input,33,FOLLOW_22); 

            			newLeafNode(otherlv_1, grammarAccess.getLoopActionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:4354:3: ( (lv_condition_2_0= ruleExpression ) )
            // InternalThingML.g:4355:4: (lv_condition_2_0= ruleExpression )
            {
            // InternalThingML.g:4355:4: (lv_condition_2_0= ruleExpression )
            // InternalThingML.g:4356:5: lv_condition_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getLoopActionAccess().getConditionExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_67);
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
            		
            // InternalThingML.g:4377:3: ( (lv_action_4_0= ruleAction ) )
            // InternalThingML.g:4378:4: (lv_action_4_0= ruleAction )
            {
            // InternalThingML.g:4378:4: (lv_action_4_0= ruleAction )
            // InternalThingML.g:4379:5: lv_action_4_0= ruleAction
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
    // InternalThingML.g:4400:1: entryRuleConditionalAction returns [EObject current=null] : iv_ruleConditionalAction= ruleConditionalAction EOF ;
    public final EObject entryRuleConditionalAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionalAction = null;


        try {
            // InternalThingML.g:4400:58: (iv_ruleConditionalAction= ruleConditionalAction EOF )
            // InternalThingML.g:4401:2: iv_ruleConditionalAction= ruleConditionalAction EOF
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
    // InternalThingML.g:4407:1: ruleConditionalAction returns [EObject current=null] : (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? ) ;
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
            // InternalThingML.g:4413:2: ( (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? ) )
            // InternalThingML.g:4414:2: (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? )
            {
            // InternalThingML.g:4414:2: (otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )? )
            // InternalThingML.g:4415:3: otherlv_0= 'if' otherlv_1= '(' ( (lv_condition_2_0= ruleExpression ) ) otherlv_3= ')' ( (lv_action_4_0= ruleAction ) ) (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )?
            {
            otherlv_0=(Token)match(input,72,FOLLOW_24); 

            			newLeafNode(otherlv_0, grammarAccess.getConditionalActionAccess().getIfKeyword_0());
            		
            otherlv_1=(Token)match(input,33,FOLLOW_22); 

            			newLeafNode(otherlv_1, grammarAccess.getConditionalActionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:4423:3: ( (lv_condition_2_0= ruleExpression ) )
            // InternalThingML.g:4424:4: (lv_condition_2_0= ruleExpression )
            {
            // InternalThingML.g:4424:4: (lv_condition_2_0= ruleExpression )
            // InternalThingML.g:4425:5: lv_condition_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getConditionalActionAccess().getConditionExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_67);
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
            		
            // InternalThingML.g:4446:3: ( (lv_action_4_0= ruleAction ) )
            // InternalThingML.g:4447:4: (lv_action_4_0= ruleAction )
            {
            // InternalThingML.g:4447:4: (lv_action_4_0= ruleAction )
            // InternalThingML.g:4448:5: lv_action_4_0= ruleAction
            {

            					newCompositeNode(grammarAccess.getConditionalActionAccess().getActionActionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_68);
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

            // InternalThingML.g:4465:3: (otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) ) )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==73) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // InternalThingML.g:4466:4: otherlv_5= 'else' ( (lv_elseAction_6_0= ruleAction ) )
                    {
                    otherlv_5=(Token)match(input,73,FOLLOW_27); 

                    				newLeafNode(otherlv_5, grammarAccess.getConditionalActionAccess().getElseKeyword_5_0());
                    			
                    // InternalThingML.g:4470:4: ( (lv_elseAction_6_0= ruleAction ) )
                    // InternalThingML.g:4471:5: (lv_elseAction_6_0= ruleAction )
                    {
                    // InternalThingML.g:4471:5: (lv_elseAction_6_0= ruleAction )
                    // InternalThingML.g:4472:6: lv_elseAction_6_0= ruleAction
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
    // InternalThingML.g:4494:1: entryRuleReturnAction returns [EObject current=null] : iv_ruleReturnAction= ruleReturnAction EOF ;
    public final EObject entryRuleReturnAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReturnAction = null;


        try {
            // InternalThingML.g:4494:53: (iv_ruleReturnAction= ruleReturnAction EOF )
            // InternalThingML.g:4495:2: iv_ruleReturnAction= ruleReturnAction EOF
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
    // InternalThingML.g:4501:1: ruleReturnAction returns [EObject current=null] : (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) ) ;
    public final EObject ruleReturnAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_exp_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4507:2: ( (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:4508:2: (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:4508:2: (otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) ) )
            // InternalThingML.g:4509:3: otherlv_0= 'return' ( (lv_exp_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,74,FOLLOW_22); 

            			newLeafNode(otherlv_0, grammarAccess.getReturnActionAccess().getReturnKeyword_0());
            		
            // InternalThingML.g:4513:3: ( (lv_exp_1_0= ruleExpression ) )
            // InternalThingML.g:4514:4: (lv_exp_1_0= ruleExpression )
            {
            // InternalThingML.g:4514:4: (lv_exp_1_0= ruleExpression )
            // InternalThingML.g:4515:5: lv_exp_1_0= ruleExpression
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
    // InternalThingML.g:4536:1: entryRulePrintAction returns [EObject current=null] : iv_rulePrintAction= rulePrintAction EOF ;
    public final EObject entryRulePrintAction() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrintAction = null;


        try {
            // InternalThingML.g:4536:52: (iv_rulePrintAction= rulePrintAction EOF )
            // InternalThingML.g:4537:2: iv_rulePrintAction= rulePrintAction EOF
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
    // InternalThingML.g:4543:1: rulePrintAction returns [EObject current=null] : (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) ) ;
    public final EObject rulePrintAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_msg_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4549:2: ( (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:4550:2: (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:4550:2: (otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) ) )
            // InternalThingML.g:4551:3: otherlv_0= 'print' ( (lv_msg_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,75,FOLLOW_22); 

            			newLeafNode(otherlv_0, grammarAccess.getPrintActionAccess().getPrintKeyword_0());
            		
            // InternalThingML.g:4555:3: ( (lv_msg_1_0= ruleExpression ) )
            // InternalThingML.g:4556:4: (lv_msg_1_0= ruleExpression )
            {
            // InternalThingML.g:4556:4: (lv_msg_1_0= ruleExpression )
            // InternalThingML.g:4557:5: lv_msg_1_0= ruleExpression
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
    // InternalThingML.g:4578:1: entryRuleErrorAction returns [EObject current=null] : iv_ruleErrorAction= ruleErrorAction EOF ;
    public final EObject entryRuleErrorAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleErrorAction = null;


        try {
            // InternalThingML.g:4578:52: (iv_ruleErrorAction= ruleErrorAction EOF )
            // InternalThingML.g:4579:2: iv_ruleErrorAction= ruleErrorAction EOF
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
    // InternalThingML.g:4585:1: ruleErrorAction returns [EObject current=null] : (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) ) ;
    public final EObject ruleErrorAction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_msg_1_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4591:2: ( (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) ) )
            // InternalThingML.g:4592:2: (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) )
            {
            // InternalThingML.g:4592:2: (otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) ) )
            // InternalThingML.g:4593:3: otherlv_0= 'error' ( (lv_msg_1_0= ruleExpression ) )
            {
            otherlv_0=(Token)match(input,76,FOLLOW_22); 

            			newLeafNode(otherlv_0, grammarAccess.getErrorActionAccess().getErrorKeyword_0());
            		
            // InternalThingML.g:4597:3: ( (lv_msg_1_0= ruleExpression ) )
            // InternalThingML.g:4598:4: (lv_msg_1_0= ruleExpression )
            {
            // InternalThingML.g:4598:4: (lv_msg_1_0= ruleExpression )
            // InternalThingML.g:4599:5: lv_msg_1_0= ruleExpression
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
    // InternalThingML.g:4620:1: entryRuleStartSession returns [EObject current=null] : iv_ruleStartSession= ruleStartSession EOF ;
    public final EObject entryRuleStartSession() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStartSession = null;


        try {
            // InternalThingML.g:4620:53: (iv_ruleStartSession= ruleStartSession EOF )
            // InternalThingML.g:4621:2: iv_ruleStartSession= ruleStartSession EOF
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
    // InternalThingML.g:4627:1: ruleStartSession returns [EObject current=null] : (otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) ) ) ;
    public final EObject ruleStartSession() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalThingML.g:4633:2: ( (otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) ) ) )
            // InternalThingML.g:4634:2: (otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) ) )
            {
            // InternalThingML.g:4634:2: (otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) ) )
            // InternalThingML.g:4635:3: otherlv_0= 'fork' ( (otherlv_1= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,77,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getStartSessionAccess().getForkKeyword_0());
            		
            // InternalThingML.g:4639:3: ( (otherlv_1= RULE_ID ) )
            // InternalThingML.g:4640:4: (otherlv_1= RULE_ID )
            {
            // InternalThingML.g:4640:4: (otherlv_1= RULE_ID )
            // InternalThingML.g:4641:5: otherlv_1= RULE_ID
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
    // InternalThingML.g:4656:1: entryRuleFunctionCallStatement returns [EObject current=null] : iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF ;
    public final EObject entryRuleFunctionCallStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCallStatement = null;


        try {
            // InternalThingML.g:4656:62: (iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF )
            // InternalThingML.g:4657:2: iv_ruleFunctionCallStatement= ruleFunctionCallStatement EOF
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
    // InternalThingML.g:4663:1: ruleFunctionCallStatement returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) ;
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
            // InternalThingML.g:4669:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) )
            // InternalThingML.g:4670:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            {
            // InternalThingML.g:4670:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            // InternalThingML.g:4671:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')'
            {
            // InternalThingML.g:4671:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:4672:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:4672:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:4673:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFunctionCallStatementRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_24); 

            					newLeafNode(otherlv_0, grammarAccess.getFunctionCallStatementAccess().getFunctionFunctionCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,33,FOLLOW_64); 

            			newLeafNode(otherlv_1, grammarAccess.getFunctionCallStatementAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:4688:3: ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( ((LA104_0>=RULE_STRING_LIT && LA104_0<=RULE_FLOAT)||LA104_0==33||LA104_0==85||(LA104_0>=89 && LA104_0<=91)) ) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // InternalThingML.g:4689:4: ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    {
                    // InternalThingML.g:4689:4: ( (lv_parameters_2_0= ruleExpression ) )
                    // InternalThingML.g:4690:5: (lv_parameters_2_0= ruleExpression )
                    {
                    // InternalThingML.g:4690:5: (lv_parameters_2_0= ruleExpression )
                    // InternalThingML.g:4691:6: lv_parameters_2_0= ruleExpression
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

                    // InternalThingML.g:4708:4: (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    loop103:
                    do {
                        int alt103=2;
                        int LA103_0 = input.LA(1);

                        if ( (LA103_0==28) ) {
                            alt103=1;
                        }


                        switch (alt103) {
                    	case 1 :
                    	    // InternalThingML.g:4709:5: otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,28,FOLLOW_22); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getFunctionCallStatementAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalThingML.g:4713:5: ( (lv_parameters_4_0= ruleExpression ) )
                    	    // InternalThingML.g:4714:6: (lv_parameters_4_0= ruleExpression )
                    	    {
                    	    // InternalThingML.g:4714:6: (lv_parameters_4_0= ruleExpression )
                    	    // InternalThingML.g:4715:7: lv_parameters_4_0= ruleExpression
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
                    	    break loop103;
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
    // InternalThingML.g:4742:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // InternalThingML.g:4742:51: (iv_ruleExpression= ruleExpression EOF )
            // InternalThingML.g:4743:2: iv_ruleExpression= ruleExpression EOF
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
    // InternalThingML.g:4749:1: ruleExpression returns [EObject current=null] : this_OrExpression_0= ruleOrExpression ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject this_OrExpression_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4755:2: (this_OrExpression_0= ruleOrExpression )
            // InternalThingML.g:4756:2: this_OrExpression_0= ruleOrExpression
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
    // InternalThingML.g:4767:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // InternalThingML.g:4767:53: (iv_ruleOrExpression= ruleOrExpression EOF )
            // InternalThingML.g:4768:2: iv_ruleOrExpression= ruleOrExpression EOF
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
    // InternalThingML.g:4774:1: ruleOrExpression returns [EObject current=null] : (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_AndExpression_0 = null;

        EObject lv_rhs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4780:2: ( (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* ) )
            // InternalThingML.g:4781:2: (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* )
            {
            // InternalThingML.g:4781:2: (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )* )
            // InternalThingML.g:4782:3: this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getOrExpressionAccess().getAndExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_69);
            this_AndExpression_0=ruleAndExpression();

            state._fsp--;


            			current = this_AndExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:4790:3: ( () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) ) )*
            loop105:
            do {
                int alt105=2;
                int LA105_0 = input.LA(1);

                if ( (LA105_0==78) ) {
                    alt105=1;
                }


                switch (alt105) {
            	case 1 :
            	    // InternalThingML.g:4791:4: () otherlv_2= 'or' ( (lv_rhs_3_0= ruleAndExpression ) )
            	    {
            	    // InternalThingML.g:4791:4: ()
            	    // InternalThingML.g:4792:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getOrExpressionAccess().getOrExpressionLhsAction_1_0(),
            	    						current);
            	    				

            	    }

            	    otherlv_2=(Token)match(input,78,FOLLOW_22); 

            	    				newLeafNode(otherlv_2, grammarAccess.getOrExpressionAccess().getOrKeyword_1_1());
            	    			
            	    // InternalThingML.g:4802:4: ( (lv_rhs_3_0= ruleAndExpression ) )
            	    // InternalThingML.g:4803:5: (lv_rhs_3_0= ruleAndExpression )
            	    {
            	    // InternalThingML.g:4803:5: (lv_rhs_3_0= ruleAndExpression )
            	    // InternalThingML.g:4804:6: lv_rhs_3_0= ruleAndExpression
            	    {

            	    						newCompositeNode(grammarAccess.getOrExpressionAccess().getRhsAndExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_69);
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
            	    break loop105;
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
    // InternalThingML.g:4826:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // InternalThingML.g:4826:54: (iv_ruleAndExpression= ruleAndExpression EOF )
            // InternalThingML.g:4827:2: iv_ruleAndExpression= ruleAndExpression EOF
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
    // InternalThingML.g:4833:1: ruleAndExpression returns [EObject current=null] : (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Equality_0 = null;

        EObject lv_rhs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4839:2: ( (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* ) )
            // InternalThingML.g:4840:2: (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* )
            {
            // InternalThingML.g:4840:2: (this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )* )
            // InternalThingML.g:4841:3: this_Equality_0= ruleEquality ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )*
            {

            			newCompositeNode(grammarAccess.getAndExpressionAccess().getEqualityParserRuleCall_0());
            		
            pushFollow(FOLLOW_70);
            this_Equality_0=ruleEquality();

            state._fsp--;


            			current = this_Equality_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:4849:3: ( () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) ) )*
            loop106:
            do {
                int alt106=2;
                int LA106_0 = input.LA(1);

                if ( (LA106_0==79) ) {
                    alt106=1;
                }


                switch (alt106) {
            	case 1 :
            	    // InternalThingML.g:4850:4: () otherlv_2= 'and' ( (lv_rhs_3_0= ruleEquality ) )
            	    {
            	    // InternalThingML.g:4850:4: ()
            	    // InternalThingML.g:4851:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAndExpressionAccess().getAndExpressionLhsAction_1_0(),
            	    						current);
            	    				

            	    }

            	    otherlv_2=(Token)match(input,79,FOLLOW_22); 

            	    				newLeafNode(otherlv_2, grammarAccess.getAndExpressionAccess().getAndKeyword_1_1());
            	    			
            	    // InternalThingML.g:4861:4: ( (lv_rhs_3_0= ruleEquality ) )
            	    // InternalThingML.g:4862:5: (lv_rhs_3_0= ruleEquality )
            	    {
            	    // InternalThingML.g:4862:5: (lv_rhs_3_0= ruleEquality )
            	    // InternalThingML.g:4863:6: lv_rhs_3_0= ruleEquality
            	    {

            	    						newCompositeNode(grammarAccess.getAndExpressionAccess().getRhsEqualityParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_70);
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
            	    break loop106;
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
    // InternalThingML.g:4885:1: entryRuleEquality returns [EObject current=null] : iv_ruleEquality= ruleEquality EOF ;
    public final EObject entryRuleEquality() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEquality = null;


        try {
            // InternalThingML.g:4885:49: (iv_ruleEquality= ruleEquality EOF )
            // InternalThingML.g:4886:2: iv_ruleEquality= ruleEquality EOF
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
    // InternalThingML.g:4892:1: ruleEquality returns [EObject current=null] : (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* ) ;
    public final EObject ruleEquality() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Comparaison_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:4898:2: ( (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* ) )
            // InternalThingML.g:4899:2: (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* )
            {
            // InternalThingML.g:4899:2: (this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )* )
            // InternalThingML.g:4900:3: this_Comparaison_0= ruleComparaison ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getEqualityAccess().getComparaisonParserRuleCall_0());
            		
            pushFollow(FOLLOW_71);
            this_Comparaison_0=ruleComparaison();

            state._fsp--;


            			current = this_Comparaison_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:4908:3: ( ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) ) | ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) ) )*
            loop107:
            do {
                int alt107=3;
                int LA107_0 = input.LA(1);

                if ( (LA107_0==80) ) {
                    alt107=1;
                }
                else if ( (LA107_0==81) ) {
                    alt107=2;
                }


                switch (alt107) {
            	case 1 :
            	    // InternalThingML.g:4909:4: ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) )
            	    {
            	    // InternalThingML.g:4909:4: ( () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) ) )
            	    // InternalThingML.g:4910:5: () otherlv_2= '==' ( (lv_rhs_3_0= ruleComparaison ) )
            	    {
            	    // InternalThingML.g:4910:5: ()
            	    // InternalThingML.g:4911:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getEqualityAccess().getEqualsExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,80,FOLLOW_22); 

            	    					newLeafNode(otherlv_2, grammarAccess.getEqualityAccess().getEqualsSignEqualsSignKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:4921:5: ( (lv_rhs_3_0= ruleComparaison ) )
            	    // InternalThingML.g:4922:6: (lv_rhs_3_0= ruleComparaison )
            	    {
            	    // InternalThingML.g:4922:6: (lv_rhs_3_0= ruleComparaison )
            	    // InternalThingML.g:4923:7: lv_rhs_3_0= ruleComparaison
            	    {

            	    							newCompositeNode(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_71);
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
            	    // InternalThingML.g:4942:4: ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) )
            	    {
            	    // InternalThingML.g:4942:4: ( () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) ) )
            	    // InternalThingML.g:4943:5: () otherlv_5= '!=' ( (lv_rhs_6_0= ruleComparaison ) )
            	    {
            	    // InternalThingML.g:4943:5: ()
            	    // InternalThingML.g:4944:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getEqualityAccess().getNotEqualsExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,81,FOLLOW_22); 

            	    					newLeafNode(otherlv_5, grammarAccess.getEqualityAccess().getExclamationMarkEqualsSignKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:4954:5: ( (lv_rhs_6_0= ruleComparaison ) )
            	    // InternalThingML.g:4955:6: (lv_rhs_6_0= ruleComparaison )
            	    {
            	    // InternalThingML.g:4955:6: (lv_rhs_6_0= ruleComparaison )
            	    // InternalThingML.g:4956:7: lv_rhs_6_0= ruleComparaison
            	    {

            	    							newCompositeNode(grammarAccess.getEqualityAccess().getRhsComparaisonParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_71);
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
    // $ANTLR end "ruleEquality"


    // $ANTLR start "entryRuleComparaison"
    // InternalThingML.g:4979:1: entryRuleComparaison returns [EObject current=null] : iv_ruleComparaison= ruleComparaison EOF ;
    public final EObject entryRuleComparaison() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComparaison = null;


        try {
            // InternalThingML.g:4979:52: (iv_ruleComparaison= ruleComparaison EOF )
            // InternalThingML.g:4980:2: iv_ruleComparaison= ruleComparaison EOF
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
    // InternalThingML.g:4986:1: ruleComparaison returns [EObject current=null] : (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* ) ;
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
            // InternalThingML.g:4992:2: ( (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* ) )
            // InternalThingML.g:4993:2: (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* )
            {
            // InternalThingML.g:4993:2: (this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )* )
            // InternalThingML.g:4994:3: this_Addition_0= ruleAddition ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getComparaisonAccess().getAdditionParserRuleCall_0());
            		
            pushFollow(FOLLOW_72);
            this_Addition_0=ruleAddition();

            state._fsp--;


            			current = this_Addition_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5002:3: ( ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) ) | ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) ) | ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) ) | ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) ) )*
            loop108:
            do {
                int alt108=5;
                switch ( input.LA(1) ) {
                case 19:
                    {
                    alt108=1;
                    }
                    break;
                case 18:
                    {
                    alt108=2;
                    }
                    break;
                case 82:
                    {
                    alt108=3;
                    }
                    break;
                case 83:
                    {
                    alt108=4;
                    }
                    break;

                }

                switch (alt108) {
            	case 1 :
            	    // InternalThingML.g:5003:4: ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:5003:4: ( () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) ) )
            	    // InternalThingML.g:5004:5: () otherlv_2= '>' ( (lv_rhs_3_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:5004:5: ()
            	    // InternalThingML.g:5005:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getGreaterExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,19,FOLLOW_22); 

            	    					newLeafNode(otherlv_2, grammarAccess.getComparaisonAccess().getGreaterThanSignKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:5015:5: ( (lv_rhs_3_0= ruleAddition ) )
            	    // InternalThingML.g:5016:6: (lv_rhs_3_0= ruleAddition )
            	    {
            	    // InternalThingML.g:5016:6: (lv_rhs_3_0= ruleAddition )
            	    // InternalThingML.g:5017:7: lv_rhs_3_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_72);
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
            	    // InternalThingML.g:5036:4: ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:5036:4: ( () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) ) )
            	    // InternalThingML.g:5037:5: () otherlv_5= '<' ( (lv_rhs_6_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:5037:5: ()
            	    // InternalThingML.g:5038:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getLowerExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,18,FOLLOW_22); 

            	    					newLeafNode(otherlv_5, grammarAccess.getComparaisonAccess().getLessThanSignKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:5048:5: ( (lv_rhs_6_0= ruleAddition ) )
            	    // InternalThingML.g:5049:6: (lv_rhs_6_0= ruleAddition )
            	    {
            	    // InternalThingML.g:5049:6: (lv_rhs_6_0= ruleAddition )
            	    // InternalThingML.g:5050:7: lv_rhs_6_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_72);
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
            	    // InternalThingML.g:5069:4: ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:5069:4: ( () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) ) )
            	    // InternalThingML.g:5070:5: () otherlv_8= '>=' ( (lv_rhs_9_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:5070:5: ()
            	    // InternalThingML.g:5071:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getGreaterOrEqualExpressionLhsAction_1_2_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_8=(Token)match(input,82,FOLLOW_22); 

            	    					newLeafNode(otherlv_8, grammarAccess.getComparaisonAccess().getGreaterThanSignEqualsSignKeyword_1_2_1());
            	    				
            	    // InternalThingML.g:5081:5: ( (lv_rhs_9_0= ruleAddition ) )
            	    // InternalThingML.g:5082:6: (lv_rhs_9_0= ruleAddition )
            	    {
            	    // InternalThingML.g:5082:6: (lv_rhs_9_0= ruleAddition )
            	    // InternalThingML.g:5083:7: lv_rhs_9_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_2_2_0());
            	    						
            	    pushFollow(FOLLOW_72);
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
            	    // InternalThingML.g:5102:4: ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) )
            	    {
            	    // InternalThingML.g:5102:4: ( () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) ) )
            	    // InternalThingML.g:5103:5: () otherlv_11= '<=' ( (lv_rhs_12_0= ruleAddition ) )
            	    {
            	    // InternalThingML.g:5103:5: ()
            	    // InternalThingML.g:5104:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getComparaisonAccess().getLowerOrEqualExpressionLhsAction_1_3_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_11=(Token)match(input,83,FOLLOW_22); 

            	    					newLeafNode(otherlv_11, grammarAccess.getComparaisonAccess().getLessThanSignEqualsSignKeyword_1_3_1());
            	    				
            	    // InternalThingML.g:5114:5: ( (lv_rhs_12_0= ruleAddition ) )
            	    // InternalThingML.g:5115:6: (lv_rhs_12_0= ruleAddition )
            	    {
            	    // InternalThingML.g:5115:6: (lv_rhs_12_0= ruleAddition )
            	    // InternalThingML.g:5116:7: lv_rhs_12_0= ruleAddition
            	    {

            	    							newCompositeNode(grammarAccess.getComparaisonAccess().getRhsAdditionParserRuleCall_1_3_2_0());
            	    						
            	    pushFollow(FOLLOW_72);
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
            	    break loop108;
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
    // InternalThingML.g:5139:1: entryRuleAddition returns [EObject current=null] : iv_ruleAddition= ruleAddition EOF ;
    public final EObject entryRuleAddition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAddition = null;


        try {
            // InternalThingML.g:5139:49: (iv_ruleAddition= ruleAddition EOF )
            // InternalThingML.g:5140:2: iv_ruleAddition= ruleAddition EOF
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
    // InternalThingML.g:5146:1: ruleAddition returns [EObject current=null] : (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* ) ;
    public final EObject ruleAddition() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Multiplication_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5152:2: ( (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* ) )
            // InternalThingML.g:5153:2: (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* )
            {
            // InternalThingML.g:5153:2: (this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )* )
            // InternalThingML.g:5154:3: this_Multiplication_0= ruleMultiplication ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0());
            		
            pushFollow(FOLLOW_73);
            this_Multiplication_0=ruleMultiplication();

            state._fsp--;


            			current = this_Multiplication_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5162:3: ( ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) ) | ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) ) )*
            loop109:
            do {
                int alt109=3;
                int LA109_0 = input.LA(1);

                if ( (LA109_0==84) ) {
                    alt109=1;
                }
                else if ( (LA109_0==85) ) {
                    alt109=2;
                }


                switch (alt109) {
            	case 1 :
            	    // InternalThingML.g:5163:4: ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) )
            	    {
            	    // InternalThingML.g:5163:4: ( () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) ) )
            	    // InternalThingML.g:5164:5: () otherlv_2= '+' ( (lv_rhs_3_0= ruleMultiplication ) )
            	    {
            	    // InternalThingML.g:5164:5: ()
            	    // InternalThingML.g:5165:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getAdditionAccess().getPlusExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,84,FOLLOW_22); 

            	    					newLeafNode(otherlv_2, grammarAccess.getAdditionAccess().getPlusSignKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:5175:5: ( (lv_rhs_3_0= ruleMultiplication ) )
            	    // InternalThingML.g:5176:6: (lv_rhs_3_0= ruleMultiplication )
            	    {
            	    // InternalThingML.g:5176:6: (lv_rhs_3_0= ruleMultiplication )
            	    // InternalThingML.g:5177:7: lv_rhs_3_0= ruleMultiplication
            	    {

            	    							newCompositeNode(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_73);
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
            	    // InternalThingML.g:5196:4: ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) )
            	    {
            	    // InternalThingML.g:5196:4: ( () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) ) )
            	    // InternalThingML.g:5197:5: () otherlv_5= '-' ( (lv_rhs_6_0= ruleMultiplication ) )
            	    {
            	    // InternalThingML.g:5197:5: ()
            	    // InternalThingML.g:5198:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getAdditionAccess().getMinusExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,85,FOLLOW_22); 

            	    					newLeafNode(otherlv_5, grammarAccess.getAdditionAccess().getHyphenMinusKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:5208:5: ( (lv_rhs_6_0= ruleMultiplication ) )
            	    // InternalThingML.g:5209:6: (lv_rhs_6_0= ruleMultiplication )
            	    {
            	    // InternalThingML.g:5209:6: (lv_rhs_6_0= ruleMultiplication )
            	    // InternalThingML.g:5210:7: lv_rhs_6_0= ruleMultiplication
            	    {

            	    							newCompositeNode(grammarAccess.getAdditionAccess().getRhsMultiplicationParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_73);
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
    // $ANTLR end "ruleAddition"


    // $ANTLR start "entryRuleMultiplication"
    // InternalThingML.g:5233:1: entryRuleMultiplication returns [EObject current=null] : iv_ruleMultiplication= ruleMultiplication EOF ;
    public final EObject entryRuleMultiplication() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplication = null;


        try {
            // InternalThingML.g:5233:55: (iv_ruleMultiplication= ruleMultiplication EOF )
            // InternalThingML.g:5234:2: iv_ruleMultiplication= ruleMultiplication EOF
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
    // InternalThingML.g:5240:1: ruleMultiplication returns [EObject current=null] : (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* ) ;
    public final EObject ruleMultiplication() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject this_Modulo_0 = null;

        EObject lv_rhs_3_0 = null;

        EObject lv_rhs_6_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5246:2: ( (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* ) )
            // InternalThingML.g:5247:2: (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* )
            {
            // InternalThingML.g:5247:2: (this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )* )
            // InternalThingML.g:5248:3: this_Modulo_0= ruleModulo ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )*
            {

            			newCompositeNode(grammarAccess.getMultiplicationAccess().getModuloParserRuleCall_0());
            		
            pushFollow(FOLLOW_74);
            this_Modulo_0=ruleModulo();

            state._fsp--;


            			current = this_Modulo_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5256:3: ( ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) ) | ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) ) )*
            loop110:
            do {
                int alt110=3;
                int LA110_0 = input.LA(1);

                if ( (LA110_0==86) ) {
                    alt110=1;
                }
                else if ( (LA110_0==87) ) {
                    alt110=2;
                }


                switch (alt110) {
            	case 1 :
            	    // InternalThingML.g:5257:4: ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) )
            	    {
            	    // InternalThingML.g:5257:4: ( () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) ) )
            	    // InternalThingML.g:5258:5: () otherlv_2= '*' ( (lv_rhs_3_0= ruleModulo ) )
            	    {
            	    // InternalThingML.g:5258:5: ()
            	    // InternalThingML.g:5259:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getMultiplicationAccess().getTimesExpressionLhsAction_1_0_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_2=(Token)match(input,86,FOLLOW_22); 

            	    					newLeafNode(otherlv_2, grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_0_1());
            	    				
            	    // InternalThingML.g:5269:5: ( (lv_rhs_3_0= ruleModulo ) )
            	    // InternalThingML.g:5270:6: (lv_rhs_3_0= ruleModulo )
            	    {
            	    // InternalThingML.g:5270:6: (lv_rhs_3_0= ruleModulo )
            	    // InternalThingML.g:5271:7: lv_rhs_3_0= ruleModulo
            	    {

            	    							newCompositeNode(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_0_2_0());
            	    						
            	    pushFollow(FOLLOW_74);
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
            	    // InternalThingML.g:5290:4: ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) )
            	    {
            	    // InternalThingML.g:5290:4: ( () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) ) )
            	    // InternalThingML.g:5291:5: () otherlv_5= '/' ( (lv_rhs_6_0= ruleModulo ) )
            	    {
            	    // InternalThingML.g:5291:5: ()
            	    // InternalThingML.g:5292:6: 
            	    {

            	    						current = forceCreateModelElementAndSet(
            	    							grammarAccess.getMultiplicationAccess().getDivExpressionLhsAction_1_1_0(),
            	    							current);
            	    					

            	    }

            	    otherlv_5=(Token)match(input,87,FOLLOW_22); 

            	    					newLeafNode(otherlv_5, grammarAccess.getMultiplicationAccess().getSolidusKeyword_1_1_1());
            	    				
            	    // InternalThingML.g:5302:5: ( (lv_rhs_6_0= ruleModulo ) )
            	    // InternalThingML.g:5303:6: (lv_rhs_6_0= ruleModulo )
            	    {
            	    // InternalThingML.g:5303:6: (lv_rhs_6_0= ruleModulo )
            	    // InternalThingML.g:5304:7: lv_rhs_6_0= ruleModulo
            	    {

            	    							newCompositeNode(grammarAccess.getMultiplicationAccess().getRhsModuloParserRuleCall_1_1_2_0());
            	    						
            	    pushFollow(FOLLOW_74);
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
    // $ANTLR end "ruleMultiplication"


    // $ANTLR start "entryRuleModulo"
    // InternalThingML.g:5327:1: entryRuleModulo returns [EObject current=null] : iv_ruleModulo= ruleModulo EOF ;
    public final EObject entryRuleModulo() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModulo = null;


        try {
            // InternalThingML.g:5327:47: (iv_ruleModulo= ruleModulo EOF )
            // InternalThingML.g:5328:2: iv_ruleModulo= ruleModulo EOF
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
    // InternalThingML.g:5334:1: ruleModulo returns [EObject current=null] : (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? ) ;
    public final EObject ruleModulo() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Primary_0 = null;

        EObject lv_rhs_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5340:2: ( (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? ) )
            // InternalThingML.g:5341:2: (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? )
            {
            // InternalThingML.g:5341:2: (this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )? )
            // InternalThingML.g:5342:3: this_Primary_0= rulePrimary ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )?
            {

            			newCompositeNode(grammarAccess.getModuloAccess().getPrimaryParserRuleCall_0());
            		
            pushFollow(FOLLOW_75);
            this_Primary_0=rulePrimary();

            state._fsp--;


            			current = this_Primary_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5350:3: ( () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) ) )?
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==88) ) {
                alt111=1;
            }
            switch (alt111) {
                case 1 :
                    // InternalThingML.g:5351:4: () otherlv_2= '%' ( (lv_rhs_3_0= ruleExpression ) )
                    {
                    // InternalThingML.g:5351:4: ()
                    // InternalThingML.g:5352:5: 
                    {

                    					current = forceCreateModelElementAndSet(
                    						grammarAccess.getModuloAccess().getModExpressionLhsAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_2=(Token)match(input,88,FOLLOW_22); 

                    				newLeafNode(otherlv_2, grammarAccess.getModuloAccess().getPercentSignKeyword_1_1());
                    			
                    // InternalThingML.g:5362:4: ( (lv_rhs_3_0= ruleExpression ) )
                    // InternalThingML.g:5363:5: (lv_rhs_3_0= ruleExpression )
                    {
                    // InternalThingML.g:5363:5: (lv_rhs_3_0= ruleExpression )
                    // InternalThingML.g:5364:6: lv_rhs_3_0= ruleExpression
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
    // InternalThingML.g:5386:1: entryRulePrimary returns [EObject current=null] : iv_rulePrimary= rulePrimary EOF ;
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary = null;


        try {
            // InternalThingML.g:5386:48: (iv_rulePrimary= rulePrimary EOF )
            // InternalThingML.g:5387:2: iv_rulePrimary= rulePrimary EOF
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
    // InternalThingML.g:5393:1: rulePrimary returns [EObject current=null] : ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_ArrayIndexPostfix_9= ruleArrayIndexPostfix ) ;
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
            // InternalThingML.g:5399:2: ( ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_ArrayIndexPostfix_9= ruleArrayIndexPostfix ) )
            // InternalThingML.g:5400:2: ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_ArrayIndexPostfix_9= ruleArrayIndexPostfix )
            {
            // InternalThingML.g:5400:2: ( (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' ) | ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) ) | ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) ) | this_ArrayIndexPostfix_9= ruleArrayIndexPostfix )
            int alt112=4;
            switch ( input.LA(1) ) {
            case 33:
                {
                alt112=1;
                }
                break;
            case 89:
                {
                alt112=2;
                }
                break;
            case 85:
                {
                alt112=3;
                }
                break;
            case RULE_STRING_LIT:
            case RULE_ID:
            case RULE_INT:
            case RULE_STRING_EXT:
            case RULE_FLOAT:
            case 90:
            case 91:
                {
                alt112=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 112, 0, input);

                throw nvae;
            }

            switch (alt112) {
                case 1 :
                    // InternalThingML.g:5401:3: (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' )
                    {
                    // InternalThingML.g:5401:3: (otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')' )
                    // InternalThingML.g:5402:4: otherlv_0= '(' this_Expression_1= ruleExpression otherlv_2= ')'
                    {
                    otherlv_0=(Token)match(input,33,FOLLOW_22); 

                    				newLeafNode(otherlv_0, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_0_0());
                    			

                    				newCompositeNode(grammarAccess.getPrimaryAccess().getExpressionParserRuleCall_0_1());
                    			
                    pushFollow(FOLLOW_67);
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
                    // InternalThingML.g:5420:3: ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) )
                    {
                    // InternalThingML.g:5420:3: ( () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) ) )
                    // InternalThingML.g:5421:4: () otherlv_4= 'not' ( (lv_term_5_0= rulePrimary ) )
                    {
                    // InternalThingML.g:5421:4: ()
                    // InternalThingML.g:5422:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPrimaryAccess().getNotExpressionAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_4=(Token)match(input,89,FOLLOW_22); 

                    				newLeafNode(otherlv_4, grammarAccess.getPrimaryAccess().getNotKeyword_1_1());
                    			
                    // InternalThingML.g:5432:4: ( (lv_term_5_0= rulePrimary ) )
                    // InternalThingML.g:5433:5: (lv_term_5_0= rulePrimary )
                    {
                    // InternalThingML.g:5433:5: (lv_term_5_0= rulePrimary )
                    // InternalThingML.g:5434:6: lv_term_5_0= rulePrimary
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
                    // InternalThingML.g:5453:3: ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) )
                    {
                    // InternalThingML.g:5453:3: ( () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) ) )
                    // InternalThingML.g:5454:4: () otherlv_7= '-' ( (lv_term_8_0= rulePrimary ) )
                    {
                    // InternalThingML.g:5454:4: ()
                    // InternalThingML.g:5455:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPrimaryAccess().getUnaryMinusAction_2_0(),
                    						current);
                    				

                    }

                    otherlv_7=(Token)match(input,85,FOLLOW_22); 

                    				newLeafNode(otherlv_7, grammarAccess.getPrimaryAccess().getHyphenMinusKeyword_2_1());
                    			
                    // InternalThingML.g:5465:4: ( (lv_term_8_0= rulePrimary ) )
                    // InternalThingML.g:5466:5: (lv_term_8_0= rulePrimary )
                    {
                    // InternalThingML.g:5466:5: (lv_term_8_0= rulePrimary )
                    // InternalThingML.g:5467:6: lv_term_8_0= rulePrimary
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
                    // InternalThingML.g:5486:3: this_ArrayIndexPostfix_9= ruleArrayIndexPostfix
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
    // InternalThingML.g:5498:1: entryRuleArrayIndexPostfix returns [EObject current=null] : iv_ruleArrayIndexPostfix= ruleArrayIndexPostfix EOF ;
    public final EObject entryRuleArrayIndexPostfix() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayIndexPostfix = null;


        try {
            // InternalThingML.g:5498:58: (iv_ruleArrayIndexPostfix= ruleArrayIndexPostfix EOF )
            // InternalThingML.g:5499:2: iv_ruleArrayIndexPostfix= ruleArrayIndexPostfix EOF
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
    // InternalThingML.g:5505:1: ruleArrayIndexPostfix returns [EObject current=null] : (this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )? ) ;
    public final EObject ruleArrayIndexPostfix() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_AtomicExpression_0 = null;

        EObject lv_index_3_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5511:2: ( (this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )? ) )
            // InternalThingML.g:5512:2: (this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )? )
            {
            // InternalThingML.g:5512:2: (this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )? )
            // InternalThingML.g:5513:3: this_AtomicExpression_0= ruleAtomicExpression ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )?
            {

            			newCompositeNode(grammarAccess.getArrayIndexPostfixAccess().getAtomicExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_7);
            this_AtomicExpression_0=ruleAtomicExpression();

            state._fsp--;


            			current = this_AtomicExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalThingML.g:5521:3: ( () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']' )?
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==15) ) {
                alt113=1;
            }
            switch (alt113) {
                case 1 :
                    // InternalThingML.g:5522:4: () otherlv_2= '[' ( (lv_index_3_0= ruleExpression ) ) otherlv_4= ']'
                    {
                    // InternalThingML.g:5522:4: ()
                    // InternalThingML.g:5523:5: 
                    {

                    					current = forceCreateModelElementAndSet(
                    						grammarAccess.getArrayIndexPostfixAccess().getArrayIndexArrayAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_2=(Token)match(input,15,FOLLOW_22); 

                    				newLeafNode(otherlv_2, grammarAccess.getArrayIndexPostfixAccess().getLeftSquareBracketKeyword_1_1());
                    			
                    // InternalThingML.g:5533:4: ( (lv_index_3_0= ruleExpression ) )
                    // InternalThingML.g:5534:5: (lv_index_3_0= ruleExpression )
                    {
                    // InternalThingML.g:5534:5: (lv_index_3_0= ruleExpression )
                    // InternalThingML.g:5535:6: lv_index_3_0= ruleExpression
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
    // InternalThingML.g:5561:1: entryRuleAtomicExpression returns [EObject current=null] : iv_ruleAtomicExpression= ruleAtomicExpression EOF ;
    public final EObject entryRuleAtomicExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtomicExpression = null;


        try {
            // InternalThingML.g:5561:57: (iv_ruleAtomicExpression= ruleAtomicExpression EOF )
            // InternalThingML.g:5562:2: iv_ruleAtomicExpression= ruleAtomicExpression EOF
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
    // InternalThingML.g:5568:1: ruleAtomicExpression returns [EObject current=null] : (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_FunctionCallExpression_7= ruleFunctionCallExpression | this_EventReference_8= ruleEventReference ) ;
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
            // InternalThingML.g:5574:2: ( (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_FunctionCallExpression_7= ruleFunctionCallExpression | this_EventReference_8= ruleEventReference ) )
            // InternalThingML.g:5575:2: (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_FunctionCallExpression_7= ruleFunctionCallExpression | this_EventReference_8= ruleEventReference )
            {
            // InternalThingML.g:5575:2: (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_FunctionCallExpression_7= ruleFunctionCallExpression | this_EventReference_8= ruleEventReference )
            int alt114=9;
            alt114 = dfa114.predict(input);
            switch (alt114) {
                case 1 :
                    // InternalThingML.g:5576:3: this_ExternExpression_0= ruleExternExpression
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
                    // InternalThingML.g:5585:3: this_EnumLiteralRef_1= ruleEnumLiteralRef
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
                    // InternalThingML.g:5594:3: this_IntegerLiteral_2= ruleIntegerLiteral
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
                    // InternalThingML.g:5603:3: this_BooleanLiteral_3= ruleBooleanLiteral
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
                    // InternalThingML.g:5612:3: this_StringLiteral_4= ruleStringLiteral
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
                    // InternalThingML.g:5621:3: this_DoubleLiteral_5= ruleDoubleLiteral
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
                    // InternalThingML.g:5630:3: this_PropertyReference_6= rulePropertyReference
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
                    // InternalThingML.g:5639:3: this_FunctionCallExpression_7= ruleFunctionCallExpression
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
                    // InternalThingML.g:5648:3: this_EventReference_8= ruleEventReference
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
    // InternalThingML.g:5660:1: entryRuleExternExpression returns [EObject current=null] : iv_ruleExternExpression= ruleExternExpression EOF ;
    public final EObject entryRuleExternExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternExpression = null;


        try {
            // InternalThingML.g:5660:57: (iv_ruleExternExpression= ruleExternExpression EOF )
            // InternalThingML.g:5661:2: iv_ruleExternExpression= ruleExternExpression EOF
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
    // InternalThingML.g:5667:1: ruleExternExpression returns [EObject current=null] : ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) ;
    public final EObject ruleExternExpression() throws RecognitionException {
        EObject current = null;

        Token lv_expression_0_0=null;
        Token otherlv_1=null;
        EObject lv_segments_2_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:5673:2: ( ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* ) )
            // InternalThingML.g:5674:2: ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            {
            // InternalThingML.g:5674:2: ( ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )* )
            // InternalThingML.g:5675:3: ( (lv_expression_0_0= RULE_STRING_EXT ) ) (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            {
            // InternalThingML.g:5675:3: ( (lv_expression_0_0= RULE_STRING_EXT ) )
            // InternalThingML.g:5676:4: (lv_expression_0_0= RULE_STRING_EXT )
            {
            // InternalThingML.g:5676:4: (lv_expression_0_0= RULE_STRING_EXT )
            // InternalThingML.g:5677:5: lv_expression_0_0= RULE_STRING_EXT
            {
            lv_expression_0_0=(Token)match(input,RULE_STRING_EXT,FOLLOW_61); 

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

            // InternalThingML.g:5693:3: (otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) ) )*
            loop115:
            do {
                int alt115=2;
                int LA115_0 = input.LA(1);

                if ( (LA115_0==66) ) {
                    alt115=1;
                }


                switch (alt115) {
            	case 1 :
            	    // InternalThingML.g:5694:4: otherlv_1= '&' ( (lv_segments_2_0= ruleExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,66,FOLLOW_22); 

            	    				newLeafNode(otherlv_1, grammarAccess.getExternExpressionAccess().getAmpersandKeyword_1_0());
            	    			
            	    // InternalThingML.g:5698:4: ( (lv_segments_2_0= ruleExpression ) )
            	    // InternalThingML.g:5699:5: (lv_segments_2_0= ruleExpression )
            	    {
            	    // InternalThingML.g:5699:5: (lv_segments_2_0= ruleExpression )
            	    // InternalThingML.g:5700:6: lv_segments_2_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getExternExpressionAccess().getSegmentsExpressionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_61);
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
    // $ANTLR end "ruleExternExpression"


    // $ANTLR start "entryRuleEnumLiteralRef"
    // InternalThingML.g:5722:1: entryRuleEnumLiteralRef returns [EObject current=null] : iv_ruleEnumLiteralRef= ruleEnumLiteralRef EOF ;
    public final EObject entryRuleEnumLiteralRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnumLiteralRef = null;


        try {
            // InternalThingML.g:5722:55: (iv_ruleEnumLiteralRef= ruleEnumLiteralRef EOF )
            // InternalThingML.g:5723:2: iv_ruleEnumLiteralRef= ruleEnumLiteralRef EOF
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
    // InternalThingML.g:5729:1: ruleEnumLiteralRef returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleEnumLiteralRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:5735:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalThingML.g:5736:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalThingML.g:5736:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) ) )
            // InternalThingML.g:5737:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= ':' ( (otherlv_2= RULE_ID ) )
            {
            // InternalThingML.g:5737:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5738:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5738:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5739:5: otherlv_0= RULE_ID
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
            		
            // InternalThingML.g:5754:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:5755:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:5755:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:5756:5: otherlv_2= RULE_ID
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
    // InternalThingML.g:5771:1: entryRuleIntegerLiteral returns [EObject current=null] : iv_ruleIntegerLiteral= ruleIntegerLiteral EOF ;
    public final EObject entryRuleIntegerLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIntegerLiteral = null;


        try {
            // InternalThingML.g:5771:55: (iv_ruleIntegerLiteral= ruleIntegerLiteral EOF )
            // InternalThingML.g:5772:2: iv_ruleIntegerLiteral= ruleIntegerLiteral EOF
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
    // InternalThingML.g:5778:1: ruleIntegerLiteral returns [EObject current=null] : ( (lv_intValue_0_0= RULE_INT ) ) ;
    public final EObject ruleIntegerLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_intValue_0_0=null;


        	enterRule();

        try {
            // InternalThingML.g:5784:2: ( ( (lv_intValue_0_0= RULE_INT ) ) )
            // InternalThingML.g:5785:2: ( (lv_intValue_0_0= RULE_INT ) )
            {
            // InternalThingML.g:5785:2: ( (lv_intValue_0_0= RULE_INT ) )
            // InternalThingML.g:5786:3: (lv_intValue_0_0= RULE_INT )
            {
            // InternalThingML.g:5786:3: (lv_intValue_0_0= RULE_INT )
            // InternalThingML.g:5787:4: lv_intValue_0_0= RULE_INT
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
    // InternalThingML.g:5806:1: entryRuleBooleanLiteral returns [EObject current=null] : iv_ruleBooleanLiteral= ruleBooleanLiteral EOF ;
    public final EObject entryRuleBooleanLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBooleanLiteral = null;


        try {
            // InternalThingML.g:5806:55: (iv_ruleBooleanLiteral= ruleBooleanLiteral EOF )
            // InternalThingML.g:5807:2: iv_ruleBooleanLiteral= ruleBooleanLiteral EOF
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
    // InternalThingML.g:5813:1: ruleBooleanLiteral returns [EObject current=null] : ( ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) ) ) ;
    public final EObject ruleBooleanLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_boolValue_0_1=null;
        Token lv_boolValue_0_2=null;


        	enterRule();

        try {
            // InternalThingML.g:5819:2: ( ( ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) ) ) )
            // InternalThingML.g:5820:2: ( ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) ) )
            {
            // InternalThingML.g:5820:2: ( ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) ) )
            // InternalThingML.g:5821:3: ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) )
            {
            // InternalThingML.g:5821:3: ( (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' ) )
            // InternalThingML.g:5822:4: (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' )
            {
            // InternalThingML.g:5822:4: (lv_boolValue_0_1= 'true' | lv_boolValue_0_2= 'false' )
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==90) ) {
                alt116=1;
            }
            else if ( (LA116_0==91) ) {
                alt116=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }
            switch (alt116) {
                case 1 :
                    // InternalThingML.g:5823:5: lv_boolValue_0_1= 'true'
                    {
                    lv_boolValue_0_1=(Token)match(input,90,FOLLOW_2); 

                    					newLeafNode(lv_boolValue_0_1, grammarAccess.getBooleanLiteralAccess().getBoolValueTrueKeyword_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getBooleanLiteralRule());
                    					}
                    					setWithLastConsumed(current, "boolValue", lv_boolValue_0_1, null);
                    				

                    }
                    break;
                case 2 :
                    // InternalThingML.g:5834:5: lv_boolValue_0_2= 'false'
                    {
                    lv_boolValue_0_2=(Token)match(input,91,FOLLOW_2); 

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
    // InternalThingML.g:5850:1: entryRuleStringLiteral returns [EObject current=null] : iv_ruleStringLiteral= ruleStringLiteral EOF ;
    public final EObject entryRuleStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStringLiteral = null;


        try {
            // InternalThingML.g:5850:54: (iv_ruleStringLiteral= ruleStringLiteral EOF )
            // InternalThingML.g:5851:2: iv_ruleStringLiteral= ruleStringLiteral EOF
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
    // InternalThingML.g:5857:1: ruleStringLiteral returns [EObject current=null] : ( (lv_stringValue_0_0= RULE_STRING_LIT ) ) ;
    public final EObject ruleStringLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_stringValue_0_0=null;


        	enterRule();

        try {
            // InternalThingML.g:5863:2: ( ( (lv_stringValue_0_0= RULE_STRING_LIT ) ) )
            // InternalThingML.g:5864:2: ( (lv_stringValue_0_0= RULE_STRING_LIT ) )
            {
            // InternalThingML.g:5864:2: ( (lv_stringValue_0_0= RULE_STRING_LIT ) )
            // InternalThingML.g:5865:3: (lv_stringValue_0_0= RULE_STRING_LIT )
            {
            // InternalThingML.g:5865:3: (lv_stringValue_0_0= RULE_STRING_LIT )
            // InternalThingML.g:5866:4: lv_stringValue_0_0= RULE_STRING_LIT
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
    // InternalThingML.g:5885:1: entryRuleDoubleLiteral returns [EObject current=null] : iv_ruleDoubleLiteral= ruleDoubleLiteral EOF ;
    public final EObject entryRuleDoubleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoubleLiteral = null;


        try {
            // InternalThingML.g:5885:54: (iv_ruleDoubleLiteral= ruleDoubleLiteral EOF )
            // InternalThingML.g:5886:2: iv_ruleDoubleLiteral= ruleDoubleLiteral EOF
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
    // InternalThingML.g:5892:1: ruleDoubleLiteral returns [EObject current=null] : ( (lv_doubleValue_0_0= RULE_FLOAT ) ) ;
    public final EObject ruleDoubleLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_doubleValue_0_0=null;


        	enterRule();

        try {
            // InternalThingML.g:5898:2: ( ( (lv_doubleValue_0_0= RULE_FLOAT ) ) )
            // InternalThingML.g:5899:2: ( (lv_doubleValue_0_0= RULE_FLOAT ) )
            {
            // InternalThingML.g:5899:2: ( (lv_doubleValue_0_0= RULE_FLOAT ) )
            // InternalThingML.g:5900:3: (lv_doubleValue_0_0= RULE_FLOAT )
            {
            // InternalThingML.g:5900:3: (lv_doubleValue_0_0= RULE_FLOAT )
            // InternalThingML.g:5901:4: lv_doubleValue_0_0= RULE_FLOAT
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
    // InternalThingML.g:5920:1: entryRulePropertyReference returns [EObject current=null] : iv_rulePropertyReference= rulePropertyReference EOF ;
    public final EObject entryRulePropertyReference() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePropertyReference = null;


        try {
            // InternalThingML.g:5920:58: (iv_rulePropertyReference= rulePropertyReference EOF )
            // InternalThingML.g:5921:2: iv_rulePropertyReference= rulePropertyReference EOF
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
    // InternalThingML.g:5927:1: rulePropertyReference returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject rulePropertyReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalThingML.g:5933:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalThingML.g:5934:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalThingML.g:5934:2: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5935:3: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5935:3: (otherlv_0= RULE_ID )
            // InternalThingML.g:5936:4: otherlv_0= RULE_ID
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
    // InternalThingML.g:5950:1: entryRuleEventReference returns [EObject current=null] : iv_ruleEventReference= ruleEventReference EOF ;
    public final EObject entryRuleEventReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEventReference = null;


        try {
            // InternalThingML.g:5950:55: (iv_ruleEventReference= ruleEventReference EOF )
            // InternalThingML.g:5951:2: iv_ruleEventReference= ruleEventReference EOF
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
    // InternalThingML.g:5957:1: ruleEventReference returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleEventReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalThingML.g:5963:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalThingML.g:5964:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalThingML.g:5964:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) ) )
            // InternalThingML.g:5965:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '.' ( (otherlv_2= RULE_ID ) )
            {
            // InternalThingML.g:5965:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:5966:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:5966:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:5967:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEventReferenceRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_76); 

            					newLeafNode(otherlv_0, grammarAccess.getEventReferenceAccess().getReceiveMsgEventCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,92,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getEventReferenceAccess().getFullStopKeyword_1());
            		
            // InternalThingML.g:5982:3: ( (otherlv_2= RULE_ID ) )
            // InternalThingML.g:5983:4: (otherlv_2= RULE_ID )
            {
            // InternalThingML.g:5983:4: (otherlv_2= RULE_ID )
            // InternalThingML.g:5984:5: otherlv_2= RULE_ID
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
    // InternalThingML.g:5999:1: entryRuleFunctionCallExpression returns [EObject current=null] : iv_ruleFunctionCallExpression= ruleFunctionCallExpression EOF ;
    public final EObject entryRuleFunctionCallExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCallExpression = null;


        try {
            // InternalThingML.g:5999:63: (iv_ruleFunctionCallExpression= ruleFunctionCallExpression EOF )
            // InternalThingML.g:6000:2: iv_ruleFunctionCallExpression= ruleFunctionCallExpression EOF
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
    // InternalThingML.g:6006:1: ruleFunctionCallExpression returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) ;
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
            // InternalThingML.g:6012:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' ) )
            // InternalThingML.g:6013:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            {
            // InternalThingML.g:6013:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')' )
            // InternalThingML.g:6014:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )? otherlv_5= ')'
            {
            // InternalThingML.g:6014:3: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:6015:4: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:6015:4: (otherlv_0= RULE_ID )
            // InternalThingML.g:6016:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFunctionCallExpressionRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_24); 

            					newLeafNode(otherlv_0, grammarAccess.getFunctionCallExpressionAccess().getFunctionFunctionCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,33,FOLLOW_64); 

            			newLeafNode(otherlv_1, grammarAccess.getFunctionCallExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalThingML.g:6031:3: ( ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )* )?
            int alt118=2;
            int LA118_0 = input.LA(1);

            if ( ((LA118_0>=RULE_STRING_LIT && LA118_0<=RULE_FLOAT)||LA118_0==33||LA118_0==85||(LA118_0>=89 && LA118_0<=91)) ) {
                alt118=1;
            }
            switch (alt118) {
                case 1 :
                    // InternalThingML.g:6032:4: ( (lv_parameters_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    {
                    // InternalThingML.g:6032:4: ( (lv_parameters_2_0= ruleExpression ) )
                    // InternalThingML.g:6033:5: (lv_parameters_2_0= ruleExpression )
                    {
                    // InternalThingML.g:6033:5: (lv_parameters_2_0= ruleExpression )
                    // InternalThingML.g:6034:6: lv_parameters_2_0= ruleExpression
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

                    // InternalThingML.g:6051:4: (otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) ) )*
                    loop117:
                    do {
                        int alt117=2;
                        int LA117_0 = input.LA(1);

                        if ( (LA117_0==28) ) {
                            alt117=1;
                        }


                        switch (alt117) {
                    	case 1 :
                    	    // InternalThingML.g:6052:5: otherlv_3= ',' ( (lv_parameters_4_0= ruleExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,28,FOLLOW_22); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getFunctionCallExpressionAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalThingML.g:6056:5: ( (lv_parameters_4_0= ruleExpression ) )
                    	    // InternalThingML.g:6057:6: (lv_parameters_4_0= ruleExpression )
                    	    {
                    	    // InternalThingML.g:6057:6: (lv_parameters_4_0= ruleExpression )
                    	    // InternalThingML.g:6058:7: lv_parameters_4_0= ruleExpression
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
                    	    break loop117;
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
    // InternalThingML.g:6085:1: entryRuleConfiguration returns [EObject current=null] : iv_ruleConfiguration= ruleConfiguration EOF ;
    public final EObject entryRuleConfiguration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConfiguration = null;


        try {
            // InternalThingML.g:6085:54: (iv_ruleConfiguration= ruleConfiguration EOF )
            // InternalThingML.g:6086:2: iv_ruleConfiguration= ruleConfiguration EOF
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
    // InternalThingML.g:6092:1: ruleConfiguration returns [EObject current=null] : (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' ) ;
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
            // InternalThingML.g:6098:2: ( (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' ) )
            // InternalThingML.g:6099:2: (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' )
            {
            // InternalThingML.g:6099:2: (otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}' )
            // InternalThingML.g:6100:3: otherlv_0= 'configuration' ( (lv_name_1_0= RULE_ID ) ) ( (lv_annotations_2_0= rulePlatformAnnotation ) )* otherlv_3= '{' ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )* otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,93,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConfigurationAccess().getConfigurationKeyword_0());
            		
            // InternalThingML.g:6104:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:6105:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:6105:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:6106:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:6122:3: ( (lv_annotations_2_0= rulePlatformAnnotation ) )*
            loop119:
            do {
                int alt119=2;
                int LA119_0 = input.LA(1);

                if ( (LA119_0==14) ) {
                    alt119=1;
                }


                switch (alt119) {
            	case 1 :
            	    // InternalThingML.g:6123:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:6123:4: (lv_annotations_2_0= rulePlatformAnnotation )
            	    // InternalThingML.g:6124:5: lv_annotations_2_0= rulePlatformAnnotation
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
            	    break loop119;
                }
            } while (true);

            otherlv_3=(Token)match(input,23,FOLLOW_77); 

            			newLeafNode(otherlv_3, grammarAccess.getConfigurationAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalThingML.g:6145:3: ( ( (lv_instances_4_0= ruleInstance ) ) | ( (lv_connectors_5_0= ruleAbstractConnector ) ) | ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) ) )*
            loop120:
            do {
                int alt120=4;
                switch ( input.LA(1) ) {
                case 94:
                    {
                    alt120=1;
                    }
                    break;
                case 95:
                    {
                    alt120=2;
                    }
                    break;
                case 29:
                    {
                    alt120=3;
                    }
                    break;

                }

                switch (alt120) {
            	case 1 :
            	    // InternalThingML.g:6146:4: ( (lv_instances_4_0= ruleInstance ) )
            	    {
            	    // InternalThingML.g:6146:4: ( (lv_instances_4_0= ruleInstance ) )
            	    // InternalThingML.g:6147:5: (lv_instances_4_0= ruleInstance )
            	    {
            	    // InternalThingML.g:6147:5: (lv_instances_4_0= ruleInstance )
            	    // InternalThingML.g:6148:6: lv_instances_4_0= ruleInstance
            	    {

            	    						newCompositeNode(grammarAccess.getConfigurationAccess().getInstancesInstanceParserRuleCall_4_0_0());
            	    					
            	    pushFollow(FOLLOW_77);
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
            	    // InternalThingML.g:6166:4: ( (lv_connectors_5_0= ruleAbstractConnector ) )
            	    {
            	    // InternalThingML.g:6166:4: ( (lv_connectors_5_0= ruleAbstractConnector ) )
            	    // InternalThingML.g:6167:5: (lv_connectors_5_0= ruleAbstractConnector )
            	    {
            	    // InternalThingML.g:6167:5: (lv_connectors_5_0= ruleAbstractConnector )
            	    // InternalThingML.g:6168:6: lv_connectors_5_0= ruleAbstractConnector
            	    {

            	    						newCompositeNode(grammarAccess.getConfigurationAccess().getConnectorsAbstractConnectorParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_77);
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
            	    // InternalThingML.g:6186:4: ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) )
            	    {
            	    // InternalThingML.g:6186:4: ( (lv_propassigns_6_0= ruleConfigPropertyAssign ) )
            	    // InternalThingML.g:6187:5: (lv_propassigns_6_0= ruleConfigPropertyAssign )
            	    {
            	    // InternalThingML.g:6187:5: (lv_propassigns_6_0= ruleConfigPropertyAssign )
            	    // InternalThingML.g:6188:6: lv_propassigns_6_0= ruleConfigPropertyAssign
            	    {

            	    						newCompositeNode(grammarAccess.getConfigurationAccess().getPropassignsConfigPropertyAssignParserRuleCall_4_2_0());
            	    					
            	    pushFollow(FOLLOW_77);
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
            	    break loop120;
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
    // InternalThingML.g:6214:1: entryRuleInstance returns [EObject current=null] : iv_ruleInstance= ruleInstance EOF ;
    public final EObject entryRuleInstance() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInstance = null;


        try {
            // InternalThingML.g:6214:49: (iv_ruleInstance= ruleInstance EOF )
            // InternalThingML.g:6215:2: iv_ruleInstance= ruleInstance EOF
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
    // InternalThingML.g:6221:1: ruleInstance returns [EObject current=null] : (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* ) ;
    public final EObject ruleInstance() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject lv_annotations_4_0 = null;



        	enterRule();

        try {
            // InternalThingML.g:6227:2: ( (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:6228:2: (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:6228:2: (otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:6229:3: otherlv_0= 'instance' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,94,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getInstanceAccess().getInstanceKeyword_0());
            		
            // InternalThingML.g:6233:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalThingML.g:6234:4: (lv_name_1_0= RULE_ID )
            {
            // InternalThingML.g:6234:4: (lv_name_1_0= RULE_ID )
            // InternalThingML.g:6235:5: lv_name_1_0= RULE_ID
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
            		
            // InternalThingML.g:6255:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:6256:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:6256:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:6257:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getInstanceRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_16); 

            					newLeafNode(otherlv_3, grammarAccess.getInstanceAccess().getTypeThingCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:6268:3: ( (lv_annotations_4_0= rulePlatformAnnotation ) )*
            loop121:
            do {
                int alt121=2;
                int LA121_0 = input.LA(1);

                if ( (LA121_0==14) ) {
                    alt121=1;
                }


                switch (alt121) {
            	case 1 :
            	    // InternalThingML.g:6269:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:6269:4: (lv_annotations_4_0= rulePlatformAnnotation )
            	    // InternalThingML.g:6270:5: lv_annotations_4_0= rulePlatformAnnotation
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
    // $ANTLR end "ruleInstance"


    // $ANTLR start "entryRuleConfigPropertyAssign"
    // InternalThingML.g:6291:1: entryRuleConfigPropertyAssign returns [EObject current=null] : iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF ;
    public final EObject entryRuleConfigPropertyAssign() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConfigPropertyAssign = null;


        try {
            // InternalThingML.g:6291:61: (iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF )
            // InternalThingML.g:6292:2: iv_ruleConfigPropertyAssign= ruleConfigPropertyAssign EOF
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
    // InternalThingML.g:6298:1: ruleConfigPropertyAssign returns [EObject current=null] : (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:6304:2: ( (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:6305:2: (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:6305:2: (otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:6306:3: otherlv_0= 'set' ( (lv_instance_1_0= ruleInstanceRef ) ) otherlv_2= '.' ( (otherlv_3= RULE_ID ) ) (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )* otherlv_7= '=' ( (lv_init_8_0= ruleExpression ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,29,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConfigPropertyAssignAccess().getSetKeyword_0());
            		
            // InternalThingML.g:6310:3: ( (lv_instance_1_0= ruleInstanceRef ) )
            // InternalThingML.g:6311:4: (lv_instance_1_0= ruleInstanceRef )
            {
            // InternalThingML.g:6311:4: (lv_instance_1_0= ruleInstanceRef )
            // InternalThingML.g:6312:5: lv_instance_1_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getConfigPropertyAssignAccess().getInstanceInstanceRefParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_76);
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

            otherlv_2=(Token)match(input,92,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getConfigPropertyAssignAccess().getFullStopKeyword_2());
            		
            // InternalThingML.g:6333:3: ( (otherlv_3= RULE_ID ) )
            // InternalThingML.g:6334:4: (otherlv_3= RULE_ID )
            {
            // InternalThingML.g:6334:4: (otherlv_3= RULE_ID )
            // InternalThingML.g:6335:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConfigPropertyAssignRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_21); 

            					newLeafNode(otherlv_3, grammarAccess.getConfigPropertyAssignAccess().getPropertyPropertyCrossReference_3_0());
            				

            }


            }

            // InternalThingML.g:6346:3: (otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']' )*
            loop122:
            do {
                int alt122=2;
                int LA122_0 = input.LA(1);

                if ( (LA122_0==15) ) {
                    alt122=1;
                }


                switch (alt122) {
            	case 1 :
            	    // InternalThingML.g:6347:4: otherlv_4= '[' ( (lv_index_5_0= ruleExpression ) ) otherlv_6= ']'
            	    {
            	    otherlv_4=(Token)match(input,15,FOLLOW_22); 

            	    				newLeafNode(otherlv_4, grammarAccess.getConfigPropertyAssignAccess().getLeftSquareBracketKeyword_4_0());
            	    			
            	    // InternalThingML.g:6351:4: ( (lv_index_5_0= ruleExpression ) )
            	    // InternalThingML.g:6352:5: (lv_index_5_0= ruleExpression )
            	    {
            	    // InternalThingML.g:6352:5: (lv_index_5_0= ruleExpression )
            	    // InternalThingML.g:6353:6: lv_index_5_0= ruleExpression
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
            	    break loop122;
                }
            } while (true);

            otherlv_7=(Token)match(input,30,FOLLOW_22); 

            			newLeafNode(otherlv_7, grammarAccess.getConfigPropertyAssignAccess().getEqualsSignKeyword_5());
            		
            // InternalThingML.g:6379:3: ( (lv_init_8_0= ruleExpression ) )
            // InternalThingML.g:6380:4: (lv_init_8_0= ruleExpression )
            {
            // InternalThingML.g:6380:4: (lv_init_8_0= ruleExpression )
            // InternalThingML.g:6381:5: lv_init_8_0= ruleExpression
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

            // InternalThingML.g:6398:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop123:
            do {
                int alt123=2;
                int LA123_0 = input.LA(1);

                if ( (LA123_0==14) ) {
                    alt123=1;
                }


                switch (alt123) {
            	case 1 :
            	    // InternalThingML.g:6399:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:6399:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:6400:5: lv_annotations_9_0= rulePlatformAnnotation
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
    // $ANTLR end "ruleConfigPropertyAssign"


    // $ANTLR start "entryRuleAbstractConnector"
    // InternalThingML.g:6421:1: entryRuleAbstractConnector returns [EObject current=null] : iv_ruleAbstractConnector= ruleAbstractConnector EOF ;
    public final EObject entryRuleAbstractConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAbstractConnector = null;


        try {
            // InternalThingML.g:6421:58: (iv_ruleAbstractConnector= ruleAbstractConnector EOF )
            // InternalThingML.g:6422:2: iv_ruleAbstractConnector= ruleAbstractConnector EOF
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
    // InternalThingML.g:6428:1: ruleAbstractConnector returns [EObject current=null] : (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector ) ;
    public final EObject ruleAbstractConnector() throws RecognitionException {
        EObject current = null;

        EObject this_Connector_0 = null;

        EObject this_ExternalConnector_1 = null;



        	enterRule();

        try {
            // InternalThingML.g:6434:2: ( (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector ) )
            // InternalThingML.g:6435:2: (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector )
            {
            // InternalThingML.g:6435:2: (this_Connector_0= ruleConnector | this_ExternalConnector_1= ruleExternalConnector )
            int alt124=2;
            int LA124_0 = input.LA(1);

            if ( (LA124_0==95) ) {
                int LA124_1 = input.LA(2);

                if ( (LA124_1==RULE_ID) ) {
                    int LA124_2 = input.LA(3);

                    if ( (LA124_2==92) ) {
                        int LA124_3 = input.LA(4);

                        if ( (LA124_3==RULE_ID) ) {
                            int LA124_5 = input.LA(5);

                            if ( (LA124_5==97) ) {
                                alt124=2;
                            }
                            else if ( (LA124_5==96) ) {
                                alt124=1;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 124, 5, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 124, 3, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA124_2==RULE_ID) ) {
                        int LA124_4 = input.LA(4);

                        if ( (LA124_4==92) ) {
                            int LA124_3 = input.LA(5);

                            if ( (LA124_3==RULE_ID) ) {
                                int LA124_5 = input.LA(6);

                                if ( (LA124_5==97) ) {
                                    alt124=2;
                                }
                                else if ( (LA124_5==96) ) {
                                    alt124=1;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 124, 5, input);

                                    throw nvae;
                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 124, 3, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 124, 4, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 124, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 124, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 124, 0, input);

                throw nvae;
            }
            switch (alt124) {
                case 1 :
                    // InternalThingML.g:6436:3: this_Connector_0= ruleConnector
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
                    // InternalThingML.g:6445:3: this_ExternalConnector_1= ruleExternalConnector
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
    // InternalThingML.g:6457:1: entryRuleConnector returns [EObject current=null] : iv_ruleConnector= ruleConnector EOF ;
    public final EObject entryRuleConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConnector = null;


        try {
            // InternalThingML.g:6457:50: (iv_ruleConnector= ruleConnector EOF )
            // InternalThingML.g:6458:2: iv_ruleConnector= ruleConnector EOF
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
    // InternalThingML.g:6464:1: ruleConnector returns [EObject current=null] : (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:6470:2: ( (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:6471:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:6471:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:6472:3: otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_cli_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= '=>' ( (lv_srv_6_0= ruleInstanceRef ) ) otherlv_7= '.' ( (otherlv_8= RULE_ID ) ) ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,95,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getConnectorAccess().getConnectorKeyword_0());
            		
            // InternalThingML.g:6476:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt125=2;
            int LA125_0 = input.LA(1);

            if ( (LA125_0==RULE_ID) ) {
                int LA125_1 = input.LA(2);

                if ( (LA125_1==RULE_ID) ) {
                    alt125=1;
                }
            }
            switch (alt125) {
                case 1 :
                    // InternalThingML.g:6477:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:6477:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:6478:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:6494:3: ( (lv_cli_2_0= ruleInstanceRef ) )
            // InternalThingML.g:6495:4: (lv_cli_2_0= ruleInstanceRef )
            {
            // InternalThingML.g:6495:4: (lv_cli_2_0= ruleInstanceRef )
            // InternalThingML.g:6496:5: lv_cli_2_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getConnectorAccess().getCliInstanceRefParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_76);
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

            otherlv_3=(Token)match(input,92,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getConnectorAccess().getFullStopKeyword_3());
            		
            // InternalThingML.g:6517:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:6518:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:6518:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:6519:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConnectorRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_78); 

            					newLeafNode(otherlv_4, grammarAccess.getConnectorAccess().getRequiredRequiredPortCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,96,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getConnectorAccess().getEqualsSignGreaterThanSignKeyword_5());
            		
            // InternalThingML.g:6534:3: ( (lv_srv_6_0= ruleInstanceRef ) )
            // InternalThingML.g:6535:4: (lv_srv_6_0= ruleInstanceRef )
            {
            // InternalThingML.g:6535:4: (lv_srv_6_0= ruleInstanceRef )
            // InternalThingML.g:6536:5: lv_srv_6_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getConnectorAccess().getSrvInstanceRefParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_76);
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

            otherlv_7=(Token)match(input,92,FOLLOW_6); 

            			newLeafNode(otherlv_7, grammarAccess.getConnectorAccess().getFullStopKeyword_7());
            		
            // InternalThingML.g:6557:3: ( (otherlv_8= RULE_ID ) )
            // InternalThingML.g:6558:4: (otherlv_8= RULE_ID )
            {
            // InternalThingML.g:6558:4: (otherlv_8= RULE_ID )
            // InternalThingML.g:6559:5: otherlv_8= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConnectorRule());
            					}
            				
            otherlv_8=(Token)match(input,RULE_ID,FOLLOW_16); 

            					newLeafNode(otherlv_8, grammarAccess.getConnectorAccess().getProvidedProvidedPortCrossReference_8_0());
            				

            }


            }

            // InternalThingML.g:6570:3: ( (lv_annotations_9_0= rulePlatformAnnotation ) )*
            loop126:
            do {
                int alt126=2;
                int LA126_0 = input.LA(1);

                if ( (LA126_0==14) ) {
                    alt126=1;
                }


                switch (alt126) {
            	case 1 :
            	    // InternalThingML.g:6571:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:6571:4: (lv_annotations_9_0= rulePlatformAnnotation )
            	    // InternalThingML.g:6572:5: lv_annotations_9_0= rulePlatformAnnotation
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
    // $ANTLR end "ruleConnector"


    // $ANTLR start "entryRuleExternalConnector"
    // InternalThingML.g:6593:1: entryRuleExternalConnector returns [EObject current=null] : iv_ruleExternalConnector= ruleExternalConnector EOF ;
    public final EObject entryRuleExternalConnector() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternalConnector = null;


        try {
            // InternalThingML.g:6593:58: (iv_ruleExternalConnector= ruleExternalConnector EOF )
            // InternalThingML.g:6594:2: iv_ruleExternalConnector= ruleExternalConnector EOF
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
    // InternalThingML.g:6600:1: ruleExternalConnector returns [EObject current=null] : (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) ;
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
            // InternalThingML.g:6606:2: ( (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* ) )
            // InternalThingML.g:6607:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            {
            // InternalThingML.g:6607:2: (otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )* )
            // InternalThingML.g:6608:3: otherlv_0= 'connector' ( (lv_name_1_0= RULE_ID ) )? ( (lv_inst_2_0= ruleInstanceRef ) ) otherlv_3= '.' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'over' ( (otherlv_6= RULE_ID ) ) ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            {
            otherlv_0=(Token)match(input,95,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getExternalConnectorAccess().getConnectorKeyword_0());
            		
            // InternalThingML.g:6612:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt127=2;
            int LA127_0 = input.LA(1);

            if ( (LA127_0==RULE_ID) ) {
                int LA127_1 = input.LA(2);

                if ( (LA127_1==RULE_ID) ) {
                    alt127=1;
                }
            }
            switch (alt127) {
                case 1 :
                    // InternalThingML.g:6613:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalThingML.g:6613:4: (lv_name_1_0= RULE_ID )
                    // InternalThingML.g:6614:5: lv_name_1_0= RULE_ID
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

            // InternalThingML.g:6630:3: ( (lv_inst_2_0= ruleInstanceRef ) )
            // InternalThingML.g:6631:4: (lv_inst_2_0= ruleInstanceRef )
            {
            // InternalThingML.g:6631:4: (lv_inst_2_0= ruleInstanceRef )
            // InternalThingML.g:6632:5: lv_inst_2_0= ruleInstanceRef
            {

            					newCompositeNode(grammarAccess.getExternalConnectorAccess().getInstInstanceRefParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_76);
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

            otherlv_3=(Token)match(input,92,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getExternalConnectorAccess().getFullStopKeyword_3());
            		
            // InternalThingML.g:6653:3: ( (otherlv_4= RULE_ID ) )
            // InternalThingML.g:6654:4: (otherlv_4= RULE_ID )
            {
            // InternalThingML.g:6654:4: (otherlv_4= RULE_ID )
            // InternalThingML.g:6655:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalConnectorRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_79); 

            					newLeafNode(otherlv_4, grammarAccess.getExternalConnectorAccess().getPortPortCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,97,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getExternalConnectorAccess().getOverKeyword_5());
            		
            // InternalThingML.g:6670:3: ( (otherlv_6= RULE_ID ) )
            // InternalThingML.g:6671:4: (otherlv_6= RULE_ID )
            {
            // InternalThingML.g:6671:4: (otherlv_6= RULE_ID )
            // InternalThingML.g:6672:5: otherlv_6= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalConnectorRule());
            					}
            				
            otherlv_6=(Token)match(input,RULE_ID,FOLLOW_16); 

            					newLeafNode(otherlv_6, grammarAccess.getExternalConnectorAccess().getProtocolProtocolCrossReference_6_0());
            				

            }


            }

            // InternalThingML.g:6683:3: ( (lv_annotations_7_0= rulePlatformAnnotation ) )*
            loop128:
            do {
                int alt128=2;
                int LA128_0 = input.LA(1);

                if ( (LA128_0==14) ) {
                    alt128=1;
                }


                switch (alt128) {
            	case 1 :
            	    // InternalThingML.g:6684:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    {
            	    // InternalThingML.g:6684:4: (lv_annotations_7_0= rulePlatformAnnotation )
            	    // InternalThingML.g:6685:5: lv_annotations_7_0= rulePlatformAnnotation
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
            	    break loop128;
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
    // InternalThingML.g:6706:1: entryRuleInstanceRef returns [EObject current=null] : iv_ruleInstanceRef= ruleInstanceRef EOF ;
    public final EObject entryRuleInstanceRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInstanceRef = null;


        try {
            // InternalThingML.g:6706:52: (iv_ruleInstanceRef= ruleInstanceRef EOF )
            // InternalThingML.g:6707:2: iv_ruleInstanceRef= ruleInstanceRef EOF
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
    // InternalThingML.g:6713:1: ruleInstanceRef returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleInstanceRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalThingML.g:6719:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalThingML.g:6720:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalThingML.g:6720:2: ( (otherlv_0= RULE_ID ) )
            // InternalThingML.g:6721:3: (otherlv_0= RULE_ID )
            {
            // InternalThingML.g:6721:3: (otherlv_0= RULE_ID )
            // InternalThingML.g:6722:4: otherlv_0= RULE_ID
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
    protected DFA114 dfa114 = new DFA114(this);
    static final String dfa_1s = "\20\uffff";
    static final String dfa_2s = "\1\5\2\uffff\1\17\14\uffff";
    static final String dfa_3s = "\1\115\2\uffff\1\106\14\uffff";
    static final String dfa_4s = "\1\uffff\1\1\1\2\1\uffff\1\7\1\10\1\11\1\12\1\13\1\14\1\16\1\4\1\3\1\6\1\15\1\5";
    static final String dfa_5s = "\20\uffff}>";
    static final String[] dfa_6s = {
            "\1\3\1\uffff\1\2\34\uffff\1\12\33\uffff\1\1\2\uffff\1\12\3\uffff\1\4\1\5\1\uffff\1\6\1\7\1\10\1\11",
            "",
            "",
            "\1\13\16\uffff\1\13\2\uffff\1\16\42\uffff\1\14\1\17\1\15",
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
            return "3705:2: (this_ActionBlock_0= ruleActionBlock | this_ExternStatement_1= ruleExternStatement | this_SendAction_2= ruleSendAction | this_VariableAssignment_3= ruleVariableAssignment | this_Increment_4= ruleIncrement | this_Decrement_5= ruleDecrement | this_LoopAction_6= ruleLoopAction | this_ConditionalAction_7= ruleConditionalAction | this_ReturnAction_8= ruleReturnAction | this_PrintAction_9= rulePrintAction | this_ErrorAction_10= ruleErrorAction | this_StartSession_11= ruleStartSession | this_FunctionCallStatement_12= ruleFunctionCallStatement | this_LocalVariable_13= ruleLocalVariable )";
        }
    }
    static final String dfa_7s = "\13\uffff";
    static final String dfa_8s = "\2\uffff\1\11\10\uffff";
    static final String dfa_9s = "\1\4\1\uffff\1\5\10\uffff";
    static final String dfa_10s = "\1\133\1\uffff\1\137\10\uffff";
    static final String dfa_11s = "\1\uffff\1\1\1\uffff\1\3\1\4\1\5\1\6\1\2\1\10\1\7\1\11";
    static final String dfa_12s = "\13\uffff}>";
    static final String[] dfa_13s = {
            "\1\5\1\2\1\3\1\1\1\6\121\uffff\2\4",
            "",
            "\1\11\1\uffff\1\11\6\uffff\3\11\1\uffff\2\11\4\uffff\1\11\3\uffff\2\11\2\uffff\1\11\1\10\1\11\1\7\5\11\3\uffff\3\11\3\uffff\1\11\2\uffff\6\11\3\uffff\1\11\1\uffff\4\11\3\uffff\22\11\3\uffff\1\12\1\uffff\2\11",
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

    class DFA114 extends DFA {

        public DFA114(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 114;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "5575:2: (this_ExternExpression_0= ruleExternExpression | this_EnumLiteralRef_1= ruleEnumLiteralRef | this_IntegerLiteral_2= ruleIntegerLiteral | this_BooleanLiteral_3= ruleBooleanLiteral | this_StringLiteral_4= ruleStringLiteral | this_DoubleLiteral_5= ruleDoubleLiteral | this_PropertyReference_6= rulePropertyReference | this_FunctionCallExpression_7= ruleFunctionCallExpression | this_EventReference_8= ruleEventReference )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000082622002L,0x0000000020000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000082620002L,0x0000000020000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x00000002000101F0L,0x000000000E200000L});
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
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x000071F121000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000040008000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x00000002000001F0L,0x000000000E200000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000104000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000400000020L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000410000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x00000018000040A0L,0x0000000000003D89L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000040004002L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x00000C0001000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x00000C0011000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000800000000020L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0001000000804000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x03E471F121000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x03E071F121000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0200000001000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0004000001000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x07E471F121000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x07E071F121000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000800000040000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x01E071F121000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x05E471F121000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x05E071F121000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0800000000000020L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x7000000000004002L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x7000000000000002L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x7000000000004022L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x00000018000040A0L,0x0000000000003D8BL});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x00000006000001F0L,0x000000000E200000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000002L,0x0000000000030000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x00000000000C0002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000000002L,0x0000000000300000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000002L,0x0000000000C00000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000021000000L,0x00000000C0000000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});

}