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
package org.sintef.thingml.resource.thingml.util;

/**
 * A utility class that provides some common methods to work with Strings.
 */
public class ThingmlStringUtil {
	
	public final static String HEX_DIGIT_REGEXP = "[0-9a-fA-F]";
	public final static String UNICODE_SEQUENCE_REGEXP = "\\\\u" + HEX_DIGIT_REGEXP + HEX_DIGIT_REGEXP + HEX_DIGIT_REGEXP + HEX_DIGIT_REGEXP;
	public final static String ESC_OTHER = "\\\\(n|r|t|b|f|\"|'|>)";
	public final static String ESC_REGEXP = "\\A((" + UNICODE_SEQUENCE_REGEXP + ")|(" + ESC_OTHER + ")).*";
	
	/**
	 * Capitalizes the first letter of the given string.
	 * 
	 * @param text the string to capitalize.
	 * 
	 * @return the modified string.
	 */
	public static String capitalize(String text) {
		String h = text.substring(0, 1).toUpperCase();
		String t = text.substring(1);
		return h + t;
	}
	
	/**
	 * Returns the part of 'tail' that is not present at the end of 'text'. For
	 * example if text = 'abc' and tail = 'cd' this method returns 'd'. If 'tail' can
	 * not be found at the end of 'text', 'tail' is returned as is.
	 */
	public static String getMissingTail(String text, String tail) {
		for (int i = 1; i < tail.length(); i++) {
			int endIndex = text.length();
			int end = Math.max(0, endIndex);
			int start = Math.max(0, end - i);
			String contentTail = text.substring(start, end);
			String proposalHead = tail.substring(0, i);
			if (contentTail.equals(proposalHead)) {
				return tail.substring(i);
			}
		}
		return tail;
	}
	
	/**
	 * Converts a string that contains upper-case letter and underscores (e.g.,
	 * constant names) to a camel-case string. For example, MY_CONSTANT is converted
	 * to myConstant.
	 * 
	 * @param text the string to convert
	 * 
	 * @return a camel-case version of text
	 */
	public static String convertAllCapsToLowerCamelCase(String text) {
		String lowerCase = text.toLowerCase();
		while (true) {
			int i = lowerCase.indexOf('_');
			if (i < 0) {
				break;
			}
			String head = lowerCase.substring(0, i);
			if (i + 1 == lowerCase.length()) {
				lowerCase = head;
				break;
			} else {
				char charAfterUnderscore = lowerCase.charAt(i + 1);
				char upperCase = Character.toUpperCase(charAfterUnderscore);
				if (i + 2 < lowerCase.length()) {
					String tail = lowerCase.substring(i + 2, lowerCase.length());
					lowerCase = head + upperCase + tail;
				} else {
					lowerCase = head + upperCase;
					break;
				}
			}
		}
		return lowerCase;
	}
	
	/**
	 * Concatenates the given parts and puts 'glue' between them.
	 */
	public static String explode(java.util.Collection<? extends Object> parts, String glue) {
		return explode(parts.toArray(new Object[parts.size()]), glue);
	}
	
	/**
	 * Concatenates the given parts and puts 'glue' between them.
	 */
	public static String explode(Object[] parts, String glue) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			Object next = parts[i];
			sb.append(next.toString());
			if (i < parts.length - 1) {
				sb.append(glue);
			}
		}
		return sb.toString();
	}
	
	/**
	 * Removes single quotes at the start and end of tokenName.
	 */
	public static String formatTokenName(String tokenName) {
		if (tokenName.length() > 0 && tokenName.startsWith("'")) {
			tokenName = tokenName.substring(1, tokenName.length());
		}
		if (tokenName.length() > 0 && tokenName.endsWith("'")) {
			tokenName = tokenName.substring(0, tokenName.length() - 1);
		}
		return tokenName;
	}
	
	public static int getLine(String text, int offset) {
		return getLineAndCharPosition(text, offset)[0];
	}
	
	public static int getCharPositionInLine(String text, int offset) {
		return getLineAndCharPosition(text, offset)[1];
	}
	
	public static Integer[] getLineAndCharPosition(String text, int offset) {
		int index = 0;
		int line = 0;
		int positionInLine = 0;
		while (true) {
			line++;
			positionInLine = offset - index + 1;
			int nextN = text.indexOf("\n", index);
			int nextR = text.indexOf("\r", index);
			int nextNorR = Integer.MAX_VALUE;
			if (nextN >= 0) {
				nextNorR = nextN;
			} else if (nextR >= 0 && nextR < nextNorR) {
				nextNorR = nextR;
			} else {
				// found no EOL character
				break;
			}
			
			index = nextNorR + 1;
			if (index == nextN) {
				index++;
			}
			if (index == nextR) {
				index++;
			}
			if (index > offset) {
				break;
			}
		}
		return new Integer[] {line, positionInLine};
	}
	
	public static String escapeQuotes(String s) {
		s = s.replace("\\", "\\\\");
		s = s.replace("\"", "\\\"");
		
		return s;
	}
	
	public static String convertCamelCaseToAllCaps(String qualifiedClassName) {
		StringBuffer sb = new StringBuffer();
		final char[] charArray = qualifiedClassName.toCharArray();
		for (int c = 0; c < charArray.length; c++) {
			char character = charArray[c];
			final boolean isEnd = c + 1 == charArray.length;
			boolean nextIsUpper = !isEnd && Character.isUpperCase(charArray[c + 1]);
			boolean nextNextIsLower = c + 2 < (charArray.length) && Character.isLowerCase(charArray[c + 2]);
			
			sb.append(Character.toUpperCase(character));
			if (Character.isLowerCase(character) && nextIsUpper) {
				sb.append('_');
			} else {
				if (nextIsUpper && nextNextIsLower) {
					sb.append('_');
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * Escapes the given text such that it can be safely embedded in a string literal
	 * in Java source code.
	 * 
	 * @param text the text to escape
	 * 
	 * @return the escaped text
	 */
	public static String escapeToJavaString(String text) {
		if (text == null) {
			return null;
		}
		String result = text.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"").replace("\b", "\\b").replace("\f", "\\f").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
		StringBuilder complete = new StringBuilder();
		for (int i = 0; i < result.length(); i++) {
			int codePointI = result.codePointAt(i);
			if (codePointI >= 32 && codePointI <= 127) {
				complete.append(Character.toChars(codePointI));
			} else {
				// use Unicode representation
				complete.append("\\u");
				String hex = Integer.toHexString(codePointI);
				complete.append(getRepeatingString(4 - hex.length(), '0'));
				complete.append(hex);
			}
		}
		return complete.toString();
	}
	
	/**
	 * Escapes the given text such that it can be safely embedded in an ANTLR grammar
	 * as keyword (i.e., an in-line token). Single quotes are escaped using a
	 * backslash. Backslashes are escaped using a backslash.
	 * 
	 * @param value the text to escape
	 * 
	 * @return the escaped text
	 */
	public static String escapeToANTLRKeyword(String value) {
		return escapeToJavaString(value).replace("'", "\\'").replace("%", "\\u0025");
	}
	
	public static boolean isUnicodeSequence(String text) {
		return text.matches(UNICODE_SEQUENCE_REGEXP);
	}
	
	public static String matchCamelCase(String query, String str) {
		if (!query.matches("[A-Za-z\\*]+")) {
			return null;
		}
		String head = "";
		int i;
		for (i = 0; i < query.length(); i++) {
			char charI = query.charAt(i);
			if (Character.isLowerCase(charI)) {
				head += charI;
			} else {
				break;
			}
		}
		if (i > 0) {
			head += "[^A-Z]*";
		}
		String tail = query.substring(i);
		String re = "\\b(";
		tail = tail.replaceAll("\\*", ".*?");
		re += head + tail.replaceAll("([A-Z][^A-Z]*)", "$1[^A-Z]*");
		re +=  ".*?)\\b";
		java.util.regex.Pattern regex = java.util.regex.Pattern.compile(re);
		java.util.regex.Matcher m = regex.matcher(str);
		if (m.find()) {
			return m.group();
		} else {
			return null;
		}
	}
	
	public static String getRepeatingString(int count, char character) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < count; i++) {
			result.append(character);
		}
		return result.toString();
	}
	
	private static int minimum(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}
	
	public static int computeLevenshteinDistance(CharSequence str1, CharSequence str2) {
		int[][] distance = new int[str1.length() + 1][str2.length() + 1];
		for (int i = 0; i <= str1.length(); i++) {
			distance[i][0] = i;
		}
		for (int j = 0; j <= str2.length(); j++) {
			distance[0][j] = j;
		}
		for (int i = 1; i <= str1.length(); i++) {
			for (int j = 1; j <= str2.length(); j++) {
				distance[i][j] = minimum(distance[i - 1][j] + 1, distance[i][j - 1] + 1, distance[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));
			}
		}
		return distance[str1.length()][str2.length()];
	}
	
	public static String encode(char delimiter, String[] parts) {
		java.util.List<String> partList = new java.util.ArrayList<String>();
		for (String part : parts) {
			partList.add(part);
		}
		return encode(delimiter, partList);
	}
	
	public static String encode(char delimiter, Iterable<String> parts) {
		StringBuilder result = new StringBuilder();
		for (String part : parts) {
			String encodedPart = part.replace("\\", "\\\\");
			encodedPart = encodedPart.replace("" + delimiter, "\\" + delimiter);
			result.append(encodedPart);
			result.append(delimiter);
		}
		return result.toString();
	}
	
	public static java.util.List<String> decode(String text, char delimiter) {
		java.util.List<String> parts = new java.util.ArrayList<String>();
		
		boolean escapeMode = false;
		String part = "";
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == delimiter) {
				if (escapeMode) {
					part += delimiter;
					escapeMode = false;
				} else {
					// end of part
					parts.add(part);
					part = "";
				}
			} else if (c == '\\') {
				if (escapeMode) {
					part += '\\';
					escapeMode = false;
				} else {
					escapeMode = true;
				}
			} else {
				part += c;
			}
		}
		return parts;
	}
	
	public static String convertToString(java.util.Map<String, Object> properties) {
		java.util.List<String> parts = new java.util.ArrayList<String>();
		for (String key : properties.keySet()) {
			Object value = properties.get(key);
			if (value instanceof String) {
				parts.add(encode('=', new String[] {key, (String) value}));
			} else {
				throw new RuntimeException("Can't encode " + value);
			}
		}
		return encode(';', parts);
	}
	
	public static java.util.Map<String, String> convertFromString(String text) {
		java.util.Map<String, String> result = new java.util.LinkedHashMap<String, String>();
		java.util.List<String> keyValuePairs = decode(text, ';');
		for (String pair : keyValuePairs) {
			java.util.List<String> keyAndValue = decode(pair, '=');
			String key = keyAndValue.get(0);
			String value = keyAndValue.get(1);
			result.put(key, value);
		}
		return result;
	}
	
}
