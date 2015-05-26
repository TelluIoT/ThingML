/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sintef.thingml;

import jsyntaxpane.DefaultSyntaxKit;
import jsyntaxpane.Lexer;
import jsyntaxpane.SyntaxStyle;
import jsyntaxpane.TokenType;
import org.sintef.thingml.resource.thingml.IThingmlTokenStyle;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Created by bmori on 26.05.2015.
 */
public class ThingMLJSyntaxKit extends DefaultSyntaxKit {
    public ThingMLJSyntaxKit() {
        this(new ThingMLJSyntaxLexerWrapper());
    }

    public ThingMLJSyntaxKit(Lexer lexer) {
        super(new ThingMLJSyntaxLexerWrapper());


        Properties config = new java.util.Properties();
        config.setProperty("RightMarginColumn", "80");
        config.setProperty("RightMarginColor", "0xdddddd");

        config.setProperty("Action.indent.WordRegex", "\\w+|\\/(\\*)+");
        // config.setProperty("Action.combo-completion", "org.kevoree.tools.marShellGUI.KevsComboCompletionAction, control SPACE")
        config.setProperty("Action.combo-completion.MenuText", "Completions");
        config.setProperty("Action.double-quotes", "jsyntaxpane.actions.PairAction, typed \"");
        // config.setProperty("Action.double-quotes", "jsyntaxpane.actions.PairAction, typed \"")

        //config.setProperty("LineNumbers.CurrentBack","0x333300")



        SyntaxStyle STRINGSTYLE = new SyntaxStyle(new Color(204, 102, 0), false, true);
        jsyntaxpane.SyntaxStyles.getInstance().put(TokenType.STRING,STRINGSTYLE);
        SyntaxStyle ANNOTSTYLE = new SyntaxStyle(Color.BLUE, true, false);
        jsyntaxpane.SyntaxStyles.getInstance().put(TokenType.REGEX,ANNOTSTYLE);
        SyntaxStyle COMMENTSTYLE = new SyntaxStyle(new Color(51,153,3), true, false);
        jsyntaxpane.SyntaxStyles.getInstance().put(TokenType.COMMENT,COMMENTSTYLE);


        //UGLY REFLECTIVE GENERATION
        try {
            Class<?> infoProvider = Class.forName("org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider");
            for (Field field : infoProvider.getDeclaredFields()) {
                if (field.getType().equals(Class.forName("org.sintef.thingml.resource.thingml.grammar.ThingmlKeyword"))) {
                    org.sintef.thingml.resource.thingml.grammar.ThingmlKeyword kw = (org.sintef.thingml.resource.thingml.grammar.ThingmlKeyword) field.get(null);
                    IThingmlTokenStyle tStyle = ThingMLStyle.infoProvider.getDefaultTokenStyle(kw.getValue());
                    TokenType newTType = TokenType.KEYWORD;
                    if (tStyle != null) {
                        int[] colorTab = tStyle.getColorAsRGB();
                        ThingMLStyle.styles.put(kw.getValue(), newTType);
                        SyntaxStyle sstyle = new SyntaxStyle(new Color(colorTab[0], colorTab[1], colorTab[2]), tStyle.isBold(),tStyle.isItalic());
                        jsyntaxpane.SyntaxStyles.getInstance().put(newTType, sstyle);
                    } else {
                        ThingMLStyle.styles.put(kw.getValue(), newTType);
                        SyntaxStyle sstyle = new SyntaxStyle(Color.gray, true, false);
                        jsyntaxpane.SyntaxStyles.getInstance().put(newTType, sstyle);

                    }
                }
            }
            this.setConfig(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getContentType() {
        return "text/thingml; charset=UTF-8";
    }
}
