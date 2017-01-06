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



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalThingMLParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'thing'", "';'"
    };
    public static final int RULE_ID=4;
    public static final int RULE_WS=9;
    public static final int RULE_STRING=6;
    public static final int RULE_ANY_OTHER=10;
    public static final int RULE_SL_COMMENT=8;
    public static final int RULE_INT=5;
    public static final int T__11=11;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__12=12;
    public static final int EOF=-1;

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



    // $ANTLR start "entryRuleModel"
    // InternalThingML.g:53:1: entryRuleModel : ruleModel EOF ;
    public final void entryRuleModel() throws RecognitionException {
        try {
            // InternalThingML.g:54:1: ( ruleModel EOF )
            // InternalThingML.g:55:1: ruleModel EOF
            {
             before(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_1);
            ruleModel();

            state._fsp--;

             after(grammarAccess.getModelRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalThingML.g:62:1: ruleModel : ( ( rule__Model__GreetingsAssignment )* ) ;
    public final void ruleModel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalThingML.g:66:2: ( ( ( rule__Model__GreetingsAssignment )* ) )
            // InternalThingML.g:67:2: ( ( rule__Model__GreetingsAssignment )* )
            {
            // InternalThingML.g:67:2: ( ( rule__Model__GreetingsAssignment )* )
            // InternalThingML.g:68:3: ( rule__Model__GreetingsAssignment )*
            {
             before(grammarAccess.getModelAccess().getGreetingsAssignment()); 
            // InternalThingML.g:69:3: ( rule__Model__GreetingsAssignment )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==11) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalThingML.g:69:4: rule__Model__GreetingsAssignment
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__Model__GreetingsAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

             after(grammarAccess.getModelAccess().getGreetingsAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleThing"
    // InternalThingML.g:78:1: entryRuleThing : ruleThing EOF ;
    public final void entryRuleThing() throws RecognitionException {
        try {
            // InternalThingML.g:79:1: ( ruleThing EOF )
            // InternalThingML.g:80:1: ruleThing EOF
            {
             before(grammarAccess.getThingRule()); 
            pushFollow(FOLLOW_1);
            ruleThing();

            state._fsp--;

             after(grammarAccess.getThingRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleThing"


    // $ANTLR start "ruleThing"
    // InternalThingML.g:87:1: ruleThing : ( ( rule__Thing__Group__0 ) ) ;
    public final void ruleThing() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalThingML.g:91:2: ( ( ( rule__Thing__Group__0 ) ) )
            // InternalThingML.g:92:2: ( ( rule__Thing__Group__0 ) )
            {
            // InternalThingML.g:92:2: ( ( rule__Thing__Group__0 ) )
            // InternalThingML.g:93:3: ( rule__Thing__Group__0 )
            {
             before(grammarAccess.getThingAccess().getGroup()); 
            // InternalThingML.g:94:3: ( rule__Thing__Group__0 )
            // InternalThingML.g:94:4: rule__Thing__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Thing__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getThingAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleThing"


    // $ANTLR start "rule__Thing__Group__0"
    // InternalThingML.g:102:1: rule__Thing__Group__0 : rule__Thing__Group__0__Impl rule__Thing__Group__1 ;
    public final void rule__Thing__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalThingML.g:106:1: ( rule__Thing__Group__0__Impl rule__Thing__Group__1 )
            // InternalThingML.g:107:2: rule__Thing__Group__0__Impl rule__Thing__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__Thing__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Thing__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Thing__Group__0"


    // $ANTLR start "rule__Thing__Group__0__Impl"
    // InternalThingML.g:114:1: rule__Thing__Group__0__Impl : ( 'thing' ) ;
    public final void rule__Thing__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalThingML.g:118:1: ( ( 'thing' ) )
            // InternalThingML.g:119:1: ( 'thing' )
            {
            // InternalThingML.g:119:1: ( 'thing' )
            // InternalThingML.g:120:2: 'thing'
            {
             before(grammarAccess.getThingAccess().getThingKeyword_0()); 
            match(input,11,FOLLOW_2); 
             after(grammarAccess.getThingAccess().getThingKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Thing__Group__0__Impl"


    // $ANTLR start "rule__Thing__Group__1"
    // InternalThingML.g:129:1: rule__Thing__Group__1 : rule__Thing__Group__1__Impl rule__Thing__Group__2 ;
    public final void rule__Thing__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalThingML.g:133:1: ( rule__Thing__Group__1__Impl rule__Thing__Group__2 )
            // InternalThingML.g:134:2: rule__Thing__Group__1__Impl rule__Thing__Group__2
            {
            pushFollow(FOLLOW_5);
            rule__Thing__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Thing__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Thing__Group__1"


    // $ANTLR start "rule__Thing__Group__1__Impl"
    // InternalThingML.g:141:1: rule__Thing__Group__1__Impl : ( ( rule__Thing__NameAssignment_1 ) ) ;
    public final void rule__Thing__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalThingML.g:145:1: ( ( ( rule__Thing__NameAssignment_1 ) ) )
            // InternalThingML.g:146:1: ( ( rule__Thing__NameAssignment_1 ) )
            {
            // InternalThingML.g:146:1: ( ( rule__Thing__NameAssignment_1 ) )
            // InternalThingML.g:147:2: ( rule__Thing__NameAssignment_1 )
            {
             before(grammarAccess.getThingAccess().getNameAssignment_1()); 
            // InternalThingML.g:148:2: ( rule__Thing__NameAssignment_1 )
            // InternalThingML.g:148:3: rule__Thing__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Thing__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getThingAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Thing__Group__1__Impl"


    // $ANTLR start "rule__Thing__Group__2"
    // InternalThingML.g:156:1: rule__Thing__Group__2 : rule__Thing__Group__2__Impl ;
    public final void rule__Thing__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalThingML.g:160:1: ( rule__Thing__Group__2__Impl )
            // InternalThingML.g:161:2: rule__Thing__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Thing__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Thing__Group__2"


    // $ANTLR start "rule__Thing__Group__2__Impl"
    // InternalThingML.g:167:1: rule__Thing__Group__2__Impl : ( ';' ) ;
    public final void rule__Thing__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalThingML.g:171:1: ( ( ';' ) )
            // InternalThingML.g:172:1: ( ';' )
            {
            // InternalThingML.g:172:1: ( ';' )
            // InternalThingML.g:173:2: ';'
            {
             before(grammarAccess.getThingAccess().getSemicolonKeyword_2()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getThingAccess().getSemicolonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Thing__Group__2__Impl"


    // $ANTLR start "rule__Model__GreetingsAssignment"
    // InternalThingML.g:183:1: rule__Model__GreetingsAssignment : ( ruleThing ) ;
    public final void rule__Model__GreetingsAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalThingML.g:187:1: ( ( ruleThing ) )
            // InternalThingML.g:188:2: ( ruleThing )
            {
            // InternalThingML.g:188:2: ( ruleThing )
            // InternalThingML.g:189:3: ruleThing
            {
             before(grammarAccess.getModelAccess().getGreetingsThingParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleThing();

            state._fsp--;

             after(grammarAccess.getModelAccess().getGreetingsThingParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__GreetingsAssignment"


    // $ANTLR start "rule__Thing__NameAssignment_1"
    // InternalThingML.g:198:1: rule__Thing__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Thing__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalThingML.g:202:1: ( ( RULE_ID ) )
            // InternalThingML.g:203:2: ( RULE_ID )
            {
            // InternalThingML.g:203:2: ( RULE_ID )
            // InternalThingML.g:204:3: RULE_ID
            {
             before(grammarAccess.getThingAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getThingAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Thing__NameAssignment_1"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000001000L});

}