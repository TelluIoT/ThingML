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
package org.sintef.thingml.resource.thingml.ui;

/**
 * A class used to initialize default preference values.
 */
public class ThingmlPreferenceInitializer extends org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer {
	
	private final static org.sintef.thingml.resource.thingml.ui.ThingmlAntlrTokenHelper tokenHelper = new org.sintef.thingml.resource.thingml.ui.ThingmlAntlrTokenHelper();
	
	public void initializeDefaultPreferences() {
		
		initializeDefaultSyntaxHighlighting();
		initializeDefaultBrackets();
		
		org.eclipse.jface.preference.IPreferenceStore store = org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.getDefault().getPreferenceStore();
		// Set default value for matching brackets
		store.setDefault(org.sintef.thingml.resource.thingml.ui.ThingmlPreferenceConstants.EDITOR_MATCHING_BRACKETS_COLOR, "192,192,192");
		store.setDefault(org.sintef.thingml.resource.thingml.ui.ThingmlPreferenceConstants.EDITOR_MATCHING_BRACKETS_CHECKBOX, true);
		
	}
	
	private void initializeDefaultBrackets() {
		org.eclipse.jface.preference.IPreferenceStore store = org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.getDefault().getPreferenceStore();
		initializeDefaultBrackets(store, new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation());
	}
	
	public void initializeDefaultSyntaxHighlighting() {
		org.eclipse.jface.preference.IPreferenceStore store = org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.getDefault().getPreferenceStore();
		initializeDefaultSyntaxHighlighting(store, new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation());
	}
	
	private void initializeDefaultBrackets(org.eclipse.jface.preference.IPreferenceStore store, org.sintef.thingml.resource.thingml.IThingmlMetaInformation metaInformation) {
		String languageId = metaInformation.getSyntaxName();
		// set default brackets for ITextResource bracket set
		org.sintef.thingml.resource.thingml.ui.ThingmlBracketSet bracketSet = new org.sintef.thingml.resource.thingml.ui.ThingmlBracketSet(null, null);
		final java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlBracketPair> bracketPairs = metaInformation.getBracketPairs();
		if (bracketPairs != null) {
			for (org.sintef.thingml.resource.thingml.IThingmlBracketPair bracketPair : bracketPairs) {
				bracketSet.addBracketPair(bracketPair.getOpeningBracket(), bracketPair.getClosingBracket(), bracketPair.isClosingEnabledInside());
			}
		}
		store.setDefault(languageId + org.sintef.thingml.resource.thingml.ui.ThingmlPreferenceConstants.EDITOR_BRACKETS_SUFFIX, bracketSet.getBracketString());
	}
	
	private void initializeDefaultSyntaxHighlighting(org.eclipse.jface.preference.IPreferenceStore store, org.sintef.thingml.resource.thingml.IThingmlMetaInformation metaInformation) {
		String languageId = metaInformation.getSyntaxName();
		String[] tokenNames = metaInformation.getTokenNames();
		if (tokenNames == null) {
			return;
		}
		for (int i = 0; i < tokenNames.length; i++) {
			if (!tokenHelper.canBeUsedForSyntaxHighlighting(i)) {
				continue;
			}
			
			String tokenName = tokenHelper.getTokenName(tokenNames, i);
			if (tokenName == null) {
				continue;
			}
			org.sintef.thingml.resource.thingml.IThingmlTokenStyle style = metaInformation.getDefaultTokenStyle(tokenName);
			if (style != null) {
				String color = getColorString(style.getColorAsRGB());
				setProperties(store, languageId, tokenName, color, style.isBold(), true, style.isItalic(), style.isStrikethrough(), style.isUnderline());
			} else {
				setProperties(store, languageId, tokenName, "0,0,0", false, false, false, false, false);
			}
		}
	}
	
	private void setProperties(org.eclipse.jface.preference.IPreferenceStore store, String languageID, String tokenName, String color, boolean bold, boolean enable, boolean italic, boolean strikethrough, boolean underline) {
		store.setDefault(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageID, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.BOLD), bold);
		store.setDefault(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageID, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.COLOR), color);
		store.setDefault(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageID, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.ENABLE), enable);
		store.setDefault(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageID, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.ITALIC), italic);
		store.setDefault(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageID, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.STRIKETHROUGH), strikethrough);
		store.setDefault(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageID, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.UNDERLINE), underline);
	}
	
	private String getColorString(int[] colorAsRGB) {
		if (colorAsRGB == null) {
			return "0,0,0";
		}
		if (colorAsRGB.length != 3) {
			return "0,0,0";
		}
		return colorAsRGB[0] + "," +colorAsRGB[1] + ","+ colorAsRGB[2];
	}
}
