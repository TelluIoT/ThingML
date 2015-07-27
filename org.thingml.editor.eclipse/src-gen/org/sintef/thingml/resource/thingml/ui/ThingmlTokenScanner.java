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
package org.sintef.thingml.resource.thingml.ui;

/**
 * An adapter from the Eclipse
 * <code>org.eclipse.jface.text.rules.ITokenScanner</code> interface to the
 * generated lexer.
 */
public class ThingmlTokenScanner implements org.eclipse.jface.text.rules.ITokenScanner {
	
	private org.sintef.thingml.resource.thingml.IThingmlTextScanner lexer;
	private org.sintef.thingml.resource.thingml.IThingmlTextToken currentToken;
	private java.util.List<org.sintef.thingml.resource.thingml.IThingmlTextToken> nextTokens;
	private int offset;
	private String languageId;
	private org.eclipse.jface.preference.IPreferenceStore store;
	private org.sintef.thingml.resource.thingml.ui.ThingmlColorManager colorManager;
	private org.sintef.thingml.resource.thingml.IThingmlTextResource resource;
	
	/**
	 * 
	 * @param colorManager A manager to obtain color objects
	 */
	public ThingmlTokenScanner(org.sintef.thingml.resource.thingml.IThingmlTextResource resource, org.sintef.thingml.resource.thingml.ui.ThingmlColorManager colorManager) {
		this.resource = resource;
		this.colorManager = colorManager;
		this.lexer = new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().createLexer();
		this.languageId = new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().getSyntaxName();
		org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin plugin = org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.getDefault();
		if (plugin != null) {
			this.store = plugin.getPreferenceStore();
		}
		this.nextTokens = new java.util.ArrayList<org.sintef.thingml.resource.thingml.IThingmlTextToken>();
	}
	
	public int getTokenLength() {
		return currentToken.getLength();
	}
	
	public int getTokenOffset() {
		return offset + currentToken.getOffset();
	}
	
	public org.eclipse.jface.text.rules.IToken nextToken() {
		boolean isOriginalToken = true;
		if (!nextTokens.isEmpty()) {
			currentToken = nextTokens.remove(0);
			isOriginalToken = false;
		} else {
			currentToken = lexer.getNextToken();
		}
		if (currentToken == null || !currentToken.canBeUsedForSyntaxHighlighting()) {
			return org.eclipse.jface.text.rules.Token.EOF;
		}
		
		if (isOriginalToken) {
			splitCurrentToken();
		}
		
		org.eclipse.jface.text.TextAttribute textAttribute = null;
		String tokenName = currentToken.getName();
		if (tokenName != null) {
			org.sintef.thingml.resource.thingml.IThingmlTokenStyle staticStyle = getStaticTokenStyle();
			// now call dynamic token styler to allow to apply modifications to the static
			// style
			org.sintef.thingml.resource.thingml.IThingmlTokenStyle dynamicStyle = getDynamicTokenStyle(staticStyle);
			if (dynamicStyle != null) {
				textAttribute = getTextAttribute(dynamicStyle);
			}
		}
		
		return new org.eclipse.jface.text.rules.Token(textAttribute);
	}
	
	public void setRange(org.eclipse.jface.text.IDocument document, int offset, int length) {
		this.offset = offset;
		try {
			lexer.setText(document.get(offset, length));
		} catch (org.eclipse.jface.text.BadLocationException e) {
			// ignore this error. It might occur during editing when locations are outdated
			// quickly.
		}
	}
	
	public String getTokenText() {
		return currentToken.getText();
	}
	
	public int[] convertToIntArray(org.eclipse.swt.graphics.RGB rgb) {
		if (rgb == null) {
			return null;
		}
		return new int[] {rgb.red, rgb.green, rgb.blue};
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTokenStyle getStaticTokenStyle() {
		org.sintef.thingml.resource.thingml.IThingmlTokenStyle staticStyle = null;
		String tokenName = currentToken.getName();
		String enableKey = org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageId, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.ENABLE);
		boolean enabled = store.getBoolean(enableKey);
		if (enabled) {
			String colorKey = org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageId, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.COLOR);
			org.eclipse.swt.graphics.RGB foregroundRGB = org.eclipse.jface.preference.PreferenceConverter.getColor(store, colorKey);
			org.eclipse.swt.graphics.RGB backgroundRGB = null;
			boolean bold = store.getBoolean(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageId, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.BOLD));
			boolean italic = store.getBoolean(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageId, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.ITALIC));
			boolean strikethrough = store.getBoolean(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageId, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.STRIKETHROUGH));
			boolean underline = store.getBoolean(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageId, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.UNDERLINE));
			staticStyle = new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(convertToIntArray(foregroundRGB), convertToIntArray(backgroundRGB), bold, italic, strikethrough, underline);
		}
		return staticStyle;
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlTokenStyle getDynamicTokenStyle(org.sintef.thingml.resource.thingml.IThingmlTokenStyle staticStyle) {
		org.sintef.thingml.resource.thingml.mopp.ThingmlDynamicTokenStyler dynamicTokenStyler = new org.sintef.thingml.resource.thingml.mopp.ThingmlDynamicTokenStyler();
		org.sintef.thingml.resource.thingml.IThingmlTokenStyle dynamicStyle = dynamicTokenStyler.getDynamicTokenStyle(resource, currentToken, staticStyle);
		return dynamicStyle;
	}
	
	public org.eclipse.jface.text.TextAttribute getTextAttribute(org.sintef.thingml.resource.thingml.IThingmlTokenStyle tokeStyle) {
		int[] foregroundColorArray = tokeStyle.getColorAsRGB();
		org.eclipse.swt.graphics.Color foregroundColor = null;
		if (colorManager != null) {
			foregroundColor = colorManager.getColor(new org.eclipse.swt.graphics.RGB(foregroundColorArray[0], foregroundColorArray[1], foregroundColorArray[2]));
		}
		int[] backgroundColorArray = tokeStyle.getBackgroundColorAsRGB();
		org.eclipse.swt.graphics.Color backgroundColor = null;
		if (backgroundColorArray != null) {
			org.eclipse.swt.graphics.RGB backgroundRGB = new org.eclipse.swt.graphics.RGB(backgroundColorArray[0], backgroundColorArray[1], backgroundColorArray[2]);
			if (colorManager != null) {
				backgroundColor = colorManager.getColor(backgroundRGB);
			}
		}
		int style = org.eclipse.swt.SWT.NORMAL;
		if (tokeStyle.isBold()) {
			style = style | org.eclipse.swt.SWT.BOLD;
		}
		if (tokeStyle.isItalic()) {
			style = style | org.eclipse.swt.SWT.ITALIC;
		}
		if (tokeStyle.isStrikethrough()) {
			style = style | org.eclipse.jface.text.TextAttribute.STRIKETHROUGH;
		}
		if (tokeStyle.isUnderline()) {
			style = style | org.eclipse.jface.text.TextAttribute.UNDERLINE;
		}
		return new org.eclipse.jface.text.TextAttribute(foregroundColor, backgroundColor, style);
	}
	
	/**
	 * Tries to split the current token if it contains task items.
	 */
	public void splitCurrentToken() {
		final String text = currentToken.getText();
		final String name = currentToken.getName();
		final int line = currentToken.getLine();
		final int charStart = currentToken.getOffset();
		final int column = currentToken.getColumn();
		
		java.util.List<org.sintef.thingml.resource.thingml.mopp.ThingmlTaskItem> taskItems = new org.sintef.thingml.resource.thingml.mopp.ThingmlTaskItemDetector().findTaskItems(text, line, charStart);
		
		// this is the offset for the next token to be added
		int offset = charStart;
		int itemBeginRelative;
		java.util.List<org.sintef.thingml.resource.thingml.IThingmlTextToken> newItems = new java.util.ArrayList<org.sintef.thingml.resource.thingml.IThingmlTextToken>();
		for (org.sintef.thingml.resource.thingml.mopp.ThingmlTaskItem taskItem : taskItems) {
			int itemBegin = taskItem.getCharStart();
			int itemLine = taskItem.getLine();
			int itemColumn = 0;
			
			itemBeginRelative = itemBegin - charStart;
			// create token before task item (TODO if required)
			String textBefore = text.substring(offset - charStart, itemBeginRelative);
			int textBeforeLength = textBefore.length();
			newItems.add(new org.sintef.thingml.resource.thingml.mopp.ThingmlTextToken(name, textBefore, offset, textBeforeLength, line, column, true));
			
			// create token for the task item itself
			offset = offset + textBeforeLength;
			String itemText = taskItem.getKeyword();
			int itemTextLength = itemText.length();
			newItems.add(new org.sintef.thingml.resource.thingml.mopp.ThingmlTextToken(org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyleInformationProvider.TASK_ITEM_TOKEN_NAME, itemText, offset, itemTextLength, itemLine, itemColumn, true));
			
			offset = offset + itemTextLength;
		}
		
		if (!taskItems.isEmpty()) {
			// create token after last task item (TODO if required)
			String textAfter = text.substring(offset - charStart);
			newItems.add(new org.sintef.thingml.resource.thingml.mopp.ThingmlTextToken(name, textAfter, offset, textAfter.length(), line, column, true));
		}
		
		if (!newItems.isEmpty()) {
			// replace tokens
			currentToken = newItems.remove(0);
			nextTokens = newItems;
		}
		
	}
}
