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
 * A container for all bracket pairs.
 */
public class ThingmlBracketSet {
	
	/**
	 * the separator between a bracket pair, should not contain escape needed
	 * character, it will be used as regular expression
	 */
	public final static String BRACKET_SEPARATOR = " and ";
	private final static org.sintef.thingml.resource.thingml.ui.ThingmlPositionHelper positionHelper = new org.sintef.thingml.resource.thingml.ui.ThingmlPositionHelper();
	private java.util.ArrayList<org.sintef.thingml.resource.thingml.IThingmlBracketPair> bracketPairs;
	private org.eclipse.jface.text.source.ISourceViewer viewer;
	private String languageID;
	private org.eclipse.swt.custom.StyledText textWidget;
	private org.eclipse.jface.preference.IPreferenceStore preferenceStore;
	
	/**
	 * A single pair of brackets.
	 */
	private class BracketPair implements org.sintef.thingml.resource.thingml.IThingmlBracketPair {
		
		private final String[] brackets;
		private boolean closingEnabledInside;
		
		public BracketPair(String opening, String closing, boolean closingEnabledInside) {
			brackets = new String[] { opening, closing };
			this.closingEnabledInside = closingEnabledInside;
		}
		
		public String getClosingBracket() {
			return brackets[1];
		}
		
		public String getOpeningBracket() {
			return brackets[0];
		}
		
		public boolean isClosingEnabledInside() {
			return closingEnabledInside;
		}
		
		public void setClosingEnabledInside(boolean closingEnabledInside) {
			this.closingEnabledInside=closingEnabledInside;
		}
	}
	
	/**
	 * A listener for the automatic closing.
	 */
	private class ClosingListener implements org.sintef.thingml.resource.thingml.ui.IThingmlBracketHandler, org.eclipse.swt.events.VerifyListener, org.eclipse.swt.events.ModifyListener, org.eclipse.swt.custom.VerifyKeyListener {
		private int closingLength = -1;
		private int addedPosition = -1;
		private boolean closingAdded = false;
		private boolean isEmbraced = false;
		private String closing;
		
		/**
		 * Automatic closing will be activated if the text about to insert is a bracket.
		 */
		public void verifyText(org.eclipse.swt.events.VerifyEvent e) {
			int caret = textWidget.getCaretOffset();
			if (!isOpeningBracket(e.text)) {
				return;
			}
			if (caret > 0 && caret < textWidget.getCharCount()) {
				org.sintef.thingml.resource.thingml.IThingmlBracketPair bracketPair = getBracketPair(textWidget.getTextRange(caret - 1, 1), textWidget.getTextRange(caret, 1));
				if (bracketPair != null && !bracketPair.isClosingEnabledInside()) {
					return;
				}
			}
			closingAdded = true;
			closing = getCounterpart(e.text);
			e.text += closing;
			closingLength = closing.length();
		}
		
		/**
		 * After a change there are two cases which have to be considered:
		 * 1) if an automatic closing happened the caret will be set between the bracket
		 * pair
		 * 2) if a bracket opening is deleted on the left side of the caret the bracket
		 * closing on the right side of this caret is deleted as well
		 */
		public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
			if (closingAdded) {
				closingAdded = false;
				addedPosition = textWidget.getCaretOffset() - closingLength;
				textWidget.setCaretOffset(addedPosition);
				closingLength = -1;
			}
			if (isEmbraced) {
				isEmbraced = false;
				textWidget.replaceTextRange(textWidget.getCaretOffset(), 1, "");
			}
		}
		
		/**
		 * This is for the Backspace key, if you want to delete a previous character.
		 */
		public void verifyKey(org.eclipse.swt.events.VerifyEvent e) {
			int caretOffset = textWidget.getCaretOffset();
			int caret = caretOffset;
			// Discard the closing bracket if there is one
			if (closing != null && closing.equals("" + e.character) && addedPosition == caret) {
				e.doit = false;
				textWidget.setCaretOffset(caret + 1);
			}
			// if the CTRL key is pressed to activate the code completion, we do clear the
			// information about the recently closed bracket.
			if ((e.keyCode & org.eclipse.swt.SWT.CTRL) != 0) {
				return;
			}
			closing = null;
			addedPosition = -1;
			
			if (caret == 0 || e.keyCode != org.eclipse.swt.SWT.BS || caret == textWidget.getCharCount()) {
				return;
			}
			String prevStr = textWidget.getTextRange(caretOffset - 1, 1);
			String nextStr = textWidget.getTextRange(caretOffset, 1);
			if (e.keyCode == org.eclipse.swt.SWT.BS && isOpeningBracket(prevStr) && getCounterpart(prevStr).equals(nextStr)) {
				isEmbraced = true;
			}
		}
		public boolean addedClosingBracket() {
			return closing != null;
		}
		
		public String getClosingBracket() {
			return closing;
		}
		
	}
	
	/**
	 * Creates a bracket set to manage the bracket pairs.
	 * 
	 * @param sourceViewer the source viewer for matching brackets
	 */
	public ThingmlBracketSet(org.sintef.thingml.resource.thingml.ui.ThingmlEditor editor, org.eclipse.jface.text.source.ISourceViewer sourceViewer) {
		languageID = new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().getSyntaxName();
		this.bracketPairs = new java.util.ArrayList<org.sintef.thingml.resource.thingml.IThingmlBracketPair>();
		if (sourceViewer != null) {
			viewer = sourceViewer;
			textWidget = viewer.getTextWidget();
		}
		preferenceStore = org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.getDefault().getPreferenceStore();
		if (sourceViewer != null && preferenceStore != null) {
			resetBrackets();
			addListeners(editor);
		}
	}
	
	/**
	 * Checks whether the given string is an open bracket.
	 */
	public boolean isOpeningBracket(String bracket) {
		for (org.sintef.thingml.resource.thingml.IThingmlBracketPair bracketPair : bracketPairs) {
			if (bracket.equals(bracketPair.getOpeningBracket())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the string is a bracket.
	 */
	public boolean isBracket(String bracket) {
		for (org.sintef.thingml.resource.thingml.IThingmlBracketPair bracketPair : bracketPairs) {
			if (bracket.equals(bracketPair.getOpeningBracket()) || bracket.equals(bracketPair.getClosingBracket())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the bracket pair with the given opening and closing.
	 */
	public org.sintef.thingml.resource.thingml.IThingmlBracketPair getBracketPair(String opening, String closing) {
		for (org.sintef.thingml.resource.thingml.IThingmlBracketPair bracketPair : bracketPairs) {
			if (bracketPair.getOpeningBracket().equals(opening) && bracketPair.getClosingBracket().equals(closing)) {
				return bracketPair;
			}
		}
		return null;
	}
	
	public org.sintef.thingml.resource.thingml.IThingmlBracketPair getBracketPair(int index) {
		try {
			return bracketPairs.get(index);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Adds the bracket pair to this bracket set.
	 */
	public boolean addBracketPair(String opening, String closing, boolean closingEnabledInside) {
		if (isBracket(opening) || isBracket(closing)) {
			return false;
		}
		bracketPairs.add(new BracketPair(opening, closing, closingEnabledInside));
		return true;
	}
	
	/**
	 * Sets whether other bracket pairs shall be automatically closed, when used
	 * inside of this bracket pair.
	 */
	public boolean setClosingEnabledInside(org.sintef.thingml.resource.thingml.IThingmlBracketPair bracketPair, boolean closingEnabledInside) {
		if (bracketPair instanceof BracketPair) {
			((BracketPair) bracketPair).setClosingEnabledInside(closingEnabledInside);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes all bracket pairs from this bracket set, reload the bracket set from
	 * the preference store.
	 */
	public boolean resetBrackets() {
		String bracketPairs = preferenceStore.getString(languageID + org.sintef.thingml.resource.thingml.ui.ThingmlPreferenceConstants.EDITOR_BRACKETS_SUFFIX);
		if (bracketPairs == null) {
			return false;
		}
		setBrackets(bracketPairs);
		return true;
	}
	
	/**
	 * Returns the counter part of a bracket.
	 */
	public String getCounterpart(String bracket) {
		for (org.sintef.thingml.resource.thingml.IThingmlBracketPair bracketPair : bracketPairs) {
			if (bracket.equals(bracketPair.getOpeningBracket())) {
				return bracketPair.getClosingBracket();
			}
			if (bracket.equals(bracketPair.getClosingBracket())) {
				return bracketPair.getOpeningBracket();
			}
		}
		return null;
	}
	
	public int size() {
		return bracketPairs.size();
	}
	
	/**
	 * Removes the given bracket pair.
	 */
	public org.sintef.thingml.resource.thingml.IThingmlBracketPair remove(String opening, String closing) {
		for (org.sintef.thingml.resource.thingml.IThingmlBracketPair bracketPair : bracketPairs) {
			if (bracketPair.getOpeningBracket().equals(opening) && bracketPair.getClosingBracket().equals(closing)) {
				bracketPairs.remove(bracketPair);
				return bracketPair;
			}
		}
		return null;
	}
	
	/**
	 * Removes pairs of brackets.
	 */
	public void removeBracketPairs(String[] bracketsAsArray) {
		for (String bracket : bracketsAsArray) {
			String[] tmp = bracket.split(BRACKET_SEPARATOR);
			remove(tmp[0], tmp[1]);
		}
	}
	
	/**
	 * Removes the old bracket set and sets the given bracket set. It is useful to
	 * take a stored <code>String</code> in a preference store. A bracket pair
	 * contains of opening, closing and isClosingEnabledInside = {'1','0'}.
	 * 
	 * @param bracketSet the bracket set as a <code>String</code> in the form
	 * "()0<>0[]1". This string must have length == 3*n
	 * 
	 * @return <code>true</code> if successful
	 */
	public boolean setBrackets(String bracketSet) {
		if (bracketSet.length() % 3 != 0) {
			return false;
		}
		bracketPairs = new java.util.ArrayList<org.sintef.thingml.resource.thingml.IThingmlBracketPair>();
		for (int i = 0; i < bracketSet.length() / 3; i++) {
			addBracketPair("" + bracketSet.charAt(i * 3), "" + bracketSet.charAt(i * 3 + 1), bracketSet.charAt(i * 3 + 2) != '0');
		}
		return true;
	}
	
	/**
	 * Returns a list of bracket pairs. This call is for the list in the preference
	 * page.
	 * 
	 * @return a list of bracket pairs in the form
	 * <code>String[]{"{BRACKET_SEPARATOR}","(BRACKET_SEPARATOR)"}</code>
	 */
	public String[] getBracketArray() {
		String[] ret = new String[bracketPairs.size()];
		int i = 0;
		for (org.sintef.thingml.resource.thingml.IThingmlBracketPair bracketPair : bracketPairs) {
			ret[i] = bracketPair.getOpeningBracket() + BRACKET_SEPARATOR + bracketPair.getClosingBracket();
			i++;
		}
		return ret;
	}
	
	/**
	 * Returns this bracket set as <code>String</code>. This is useful to store the
	 * set in the <code>org.eclipse.jface.preference.IPreferenceStore</code>.
	 * 
	 * @return String the bracket set in the form "()<>[]"
	 * 
	 * @see org.eclipse.jface.preference.IPreferenceStore
	 */
	public String getBracketString() {
		if (bracketPairs.size() < 1) {
			return "";
		}
		String result = "";
		for (org.sintef.thingml.resource.thingml.IThingmlBracketPair bracketPair : bracketPairs) {
			String isClosingStr = "0";
			if (bracketPair.isClosingEnabledInside()) {
				isClosingStr = "1";
			}
			result += bracketPair.getOpeningBracket() + bracketPair.getClosingBracket() + isClosingStr;
		}
		return result;
	}
	
	/**
	 * Adds listeners to handle bracket automatic closing.
	 */
	private void addListeners(org.sintef.thingml.resource.thingml.ui.ThingmlEditor editor) {
		ClosingListener closingListener = new ClosingListener();
		textWidget.addVerifyListener(closingListener);
		textWidget.addVerifyKeyListener(closingListener);
		textWidget.addModifyListener(closingListener);
		editor.setBracketHandler(closingListener);
	}
	
	/**
	 * Searches the matching bracket at the left side of the caret. The position
	 * information will be stored in the <code>org.eclipse.jface.text.IDocument</code>
	 * in the category <code>ExtensionConstants.PositionCategory.BRACKET</code>.
	 */
	public void matchingBrackets() {
		org.eclipse.jface.text.IDocument document = viewer.getDocument();
		org.eclipse.jface.text.source.projection.ProjectionViewer projectionViewer = null;
		if (viewer instanceof org.eclipse.jface.text.source.projection.ProjectionViewer) {
			projectionViewer = (org.eclipse.jface.text.source.projection.ProjectionViewer) viewer;
		}
		if (document == null) {
			return;
		}
		int caretOffset = textWidget.getCaretOffset();
		if (projectionViewer != null) {
			caretOffset = projectionViewer.widgetOffset2ModelOffset(caretOffset);
		}
		final String prevStr;
		if (caretOffset == 0) {
			return;
		}
		try {
			prevStr = "" + document.getChar(caretOffset - 1);
		} catch (org.eclipse.jface.text.BadLocationException e) {
			e.printStackTrace();
			return;
		}
		if (!isBracket(prevStr) || prevStr.equals(getCounterpart(prevStr))) {
			return;
		}
		boolean isForward = isOpeningBracket(prevStr);
		final String toFindStr = getCounterpart(prevStr);
		int boundary = isForward ? document.getLength() : -1;
		int position = isForward ? caretOffset : caretOffset - 2;
		String currentStr;
		int count = 0;
		try {
			while (position != boundary) {
				currentStr = "" + document.getChar(position);
				if (toFindStr.equals(currentStr) && count == 0) {
					break;
				} else if (prevStr.equals(currentStr)) {
					count++;
				} else if (currentStr.equals(toFindStr)) {
					count--;
				}
				position += isForward ? 1 : -1;
			}
		} catch (org.eclipse.jface.text.BadLocationException e) {
			e.printStackTrace();
			return;
		}
		if (position != -1 && position != document.getLength()) {
			positionHelper.addPosition(document, org.sintef.thingml.resource.thingml.ui.ThingmlPositionCategory.BRACKET.toString(), position, 1);
			positionHelper.addPosition(document, org.sintef.thingml.resource.thingml.ui.ThingmlPositionCategory.BRACKET.toString(), caretOffset - 1, 1);
		}
	}
	
}
