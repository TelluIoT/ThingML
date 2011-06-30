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
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlTextToken implements org.sintef.thingml.resource.thingml.IThingmlTextToken {
	
	private final org.sintef.thingml.resource.thingml.IThingmlMetaInformation metaInformation = new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation();
	private final org.antlr.runtime3_3_0.Token antlrToken;
	
	public ThingmlTextToken(org.antlr.runtime3_3_0.Token antlrToken) {
		super();
		this.antlrToken = antlrToken;
	}
	
	public String getName() {
		return getTokenName(metaInformation.getTokenNames(), antlrToken.getType());
	}
	
	public int getOffset() {
		return ((org.antlr.runtime3_3_0.CommonToken) antlrToken).getStartIndex();
	}
	
	public int getLength() {
		return ((org.antlr.runtime3_3_0.CommonToken) antlrToken).getStopIndex() - ((org.antlr.runtime3_3_0.CommonToken) antlrToken).getStartIndex() + 1;
	}
	
	public int getLine() {
		return antlrToken.getLine();
	}
	
	public int getColumn() {
		return antlrToken.getCharPositionInLine();
	}
	
	public boolean canBeUsedForSyntaxHighlighting() {
		return canBeUsedForSyntaxHighlighting(antlrToken.getType());
	}
	
	public boolean canBeUsedForSyntaxHighlighting(int tokenType) {
		if (tokenType < 0 || tokenType == org.antlr.runtime3_3_0.Token.EOF) {
			return false;
		}
		if (tokenType == org.antlr.runtime3_3_0.Token.UP) {
			return false;
		}
		if (tokenType == org.antlr.runtime3_3_0.Token.DOWN) {
			return false;
		}
		if (tokenType == org.antlr.runtime3_3_0.Token.EOR_TOKEN_TYPE) {
			return false;
		}
		if (tokenType == org.antlr.runtime3_3_0.Token.INVALID_TOKEN_TYPE) {
			return false;
		}
		return true;
	}
	
	public String getText() {
		return antlrToken.getText();
	}
	
	public String getTokenName(String[] tokenNames, int index) {
		if (tokenNames == null) {
			return null;
		}
		String tokenName = tokenNames[index];
		if (tokenName != null && tokenName.startsWith("'")) {
			tokenName = tokenName.substring(1, tokenName.length() - 1).trim();
		}
		return tokenName;
	}
	
}
