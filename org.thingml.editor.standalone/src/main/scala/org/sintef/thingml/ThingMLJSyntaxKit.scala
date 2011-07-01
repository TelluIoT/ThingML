/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
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
/**
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
package org.sintef.thingml

import java.awt.{Graphics, Color}
import javax.swing.text.{TabExpander, Segment}
import jsyntaxpane._
import java.lang.Boolean

/**
 * User: ffouquet
 * Date: 29/06/11
 * Time: 15:24
 */

class ThingMLJSyntaxKit extends DefaultSyntaxKit(new ThingMLJSyntaxLexerWrapper()) {

  StaticConfig.staticConfig = new Boolean(true);

  override def getContentType = "text/thingml; charset=UTF-8"

  var config = new java.util.Properties
  config.setProperty("RightMarginColumn", "80")
  config.setProperty("RightMarginColor", "0xdddddd")

  config.setProperty("Action.indent.WordRegex", "\\w+|\\/(\\*)+")
  // config.setProperty("Action.combo-completion", "org.kevoree.tools.marShellGUI.KevsComboCompletionAction, control SPACE")
  config.setProperty("Action.combo-completion.MenuText", "Completions")
  config.setProperty("Action.double-quotes", "jsyntaxpane.actions.PairAction, typed \"")
  // config.setProperty("Action.double-quotes", "jsyntaxpane.actions.PairAction, typed \"")

  //config.setProperty("LineNumbers.CurrentBack","0x333300")



  val STRINGSTYLE = new SyntaxStyle(new Color(204, 102, 0), false, true)
  jsyntaxpane.SyntaxStyles.getInstance().put(TokenTypes.STRING,STRINGSTYLE)
  val ANNOTSTYLE = new SyntaxStyle(Color.BLUE, true, false)
  jsyntaxpane.SyntaxStyles.getInstance().put(TokenTypes.ANNOTATION,ANNOTSTYLE)
  val COMMENTSTYLE = new SyntaxStyle(new Color(51,153,3), true, false)
  jsyntaxpane.SyntaxStyles.getInstance().put(TokenTypes.COMMENT,COMMENTSTYLE)


  //UGLY REFLECTIVE GENERATION
  var infoProvider = classOf[org.sintef.thingml.resource.thingml.grammar.ThingmlGrammarInformationProvider]
  infoProvider.getDeclaredFields.foreach {
    field =>

      if (field.getType == classOf[org.sintef.thingml.resource.thingml.grammar.ThingmlKeyword]) {
        val kw = field.get(null).asInstanceOf[org.sintef.thingml.resource.thingml.grammar.ThingmlKeyword]
        val tStyle = ThingMLStyle.infoProvider.getDefaultTokenStyle(kw.getValue)
        val newTType = new TokenType {
          override def toString: String = "Style" + kw.getValue
        }
        if (tStyle != null) {
          val colorTab = tStyle.getColorAsRGB
          ThingMLStyle.styles.put(kw.getValue, newTType)
          val sstyle = new SyntaxStyle(new Color(colorTab(0), colorTab(1), colorTab(2)), tStyle.isBold, tStyle.isItalic)
          jsyntaxpane.SyntaxStyles.getInstance().put(newTType,sstyle)
        } else {

          ThingMLStyle.styles.put(kw.getValue, newTType)
          val sstyle = new SyntaxStyle(Color.gray, true, false)
          jsyntaxpane.SyntaxStyles.getInstance().put(newTType,sstyle)

        }
      }
  }

  /*
  config.setProperty("Style.IDENTIFIER", "0xFFCE89, 1")
  config.setProperty("Style.DELIMITER", "0xFFFFFF, 1")
  config.setProperty("Style.KEYWORD", "0xFC6C1D, 1")
  config.setProperty("Style.KEYWORD2", "0xFC6C1D, 3")
  config.setProperty("CaretColor", "0xFFFFFF")
  config.setProperty("TokenMarker.Color", "0x6D788F")
  config.setProperty("PairMarker.Color", "0x6D788F")
 */


  /*

 #
# These are the various Attributes for each TokenType.
# The keys of this map are the TokenType Strings, and the values are:
# color (hex, or integer), Font.Style attribute
# Style is one of: 0 = plain, 1=bold, 2=italic, 3=bold/italic
Style.OPERATOR = 0x000000, 0
Style.DELIMITER = 0x000000, 1
Style.KEYWORD = 0x3333ee, 0
Style.KEYWORD2 = 0x3333ee, 3
Style.TYPE = 0x000000, 2
Style.TYPE2 = 0x000000, 1
Style.TYPE3 = 0x000000, 3
Style.STRING = 0xcc6600, 0
Style.STRING2 = 0xcc6600, 1
Style.NUMBER = 0x999933, 1
Style.REGEX = 0xcc6600, 0
Style.IDENTIFIER = 0x000000, 0
Style.COMMENT = 0x339933, 2
Style.COMMENT2 = 0x339933, 3
Style.DEFAULT = 0x000000, 0
Style.WARNING = 0xCC0000, 0
Style.ERROR = 0xCC0000, 3

  */


  this.setConfig(config)


}