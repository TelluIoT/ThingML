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
package org.sintef.thingml;

import jsyntaxpane.Lexer;
import jsyntaxpane.Token;
import jsyntaxpane.TokenType;
import org.sintef.thingml.resource.thingml.IThingmlTextToken;
import org.sintef.thingml.resource.thingml.mopp.ThingmlAntlrScanner;
import org.sintef.thingml.resource.thingml.mopp.ThingmlLexer;

import javax.swing.text.Segment;
import java.util.List;

/**
 * Created by bmori on 26.05.2015.
 */
public class ThingMLJSyntaxLexerWrapper implements Lexer {

    @Override
    public void parse(Segment segment, int i, List<Token> list) {
        list.clear();
        org.antlr.runtime3_4_0.Lexer lexer = new ThingmlLexer();
        ThingmlAntlrScanner tokens = new ThingmlAntlrScanner(lexer);
        tokens.setText(segment.toString());

        IThingmlTextToken token = tokens.getNextToken();
        while (token != null) {
            TokenType newtype = getType(token);
            Token newtok = new Token(newtype, token.getOffset(), token.getLength());
            token = tokens.getNextToken();
            list.add(newtok);
        }
    }


    private TokenType getType(IThingmlTextToken tok) {
        switch (tok.getName()) {
            case "SL_COMMENT":
                return TokenType.COMMENT;
            case "ML_COMMENT":
                return TokenType.COMMENT;
            case "ANNOTATION":
                return TokenType.REGEX;
            case "STRING_LITERAL":
                return TokenType.STRING;
            default:
                TokenType n = ThingMLStyle.styles.get(tok.getName());
                if (n == null)
                    n = TokenType.DEFAULT;
                return n;
        }
    }
}

