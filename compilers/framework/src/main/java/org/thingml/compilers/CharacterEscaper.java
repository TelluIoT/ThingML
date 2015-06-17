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
package org.thingml.compilers;

/*******************************************************************************
 * Copyright (c) 2006-2009 
 * Software Technology Group, Dresden University of Technology
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * See the GNU Lesser General Public License for more details. You should have
 * received a copy of the GNU Lesser General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA  02111-1307 USA
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany 
 *   - initial API and implementation
 ******************************************************************************/

/**
 * A CharacterEscaper can be used to escape and unescape special characters
 * in Java strings and character literals. Among these special characters are
 * tabs, single and double quotes, line breaks and backslashes.
 */
public class CharacterEscaper {

	private static final char BACKSLASH = '\\';
	
	/**
	   * Given the input string with escaped unicode characters convert them
	   * to their native unicode characters and return the result. This is quite
	   * similar to the functionality found in property file handling. White space
	   * escapes are not processed (as they are consumed by the template library).
	   * Any bogus escape codes will remain in place.
	   * <p>
	   * When files are provided in another encoding, they can be converted to ascii using
	   * the native2ascii tool (a java sdk binary). This tool will escape all the
	   * non Latin1 ASCII characters and convert the file into Latin1 with unicode escapes.
	   * 
	   * This code is from http://www.antlr.org/wiki/display/ST/unicode_escapes but was
	   * modified and extended to support other escaped characters.
	   *
	   * @param source
	   *      string with unicode escapes
	   * @return
	   *      string with all unicode characters, all unicode escapes expanded.
	   *
	   * @author Caleb Lyness (modified by Mirko Seifert)
	   */
	public static String unescapeEscapedCharacters(String source) {
	     /* could use regular expression, but not this time... */
	     final int srcLen = source.length();
	     char c;

	     StringBuffer buffer = new StringBuffer(srcLen);

	     // Must have format \\uXXXX where XXXX is a hexadecimal number
	     int i = 0;
	     while (i < srcLen) {

	            c = source.charAt(i++);

	            if (c == BACKSLASH) {
	                char nc = source.charAt(i);
	                switch (nc) {
		                case 'u' : {
		                    // Now we found the 'u' we need to find another 4 hex digits
		                    // Note: shifting left by 4 is the same as multiplying by 16
		                    int v = 0; // Accumulator
		                    for (int j=1; j < 5; j++) {
		                        nc = source.charAt(i+j);
		                        switch (nc)
		                        {
		                            case 48: // '0'
		                            case 49: // '1'
		                            case 50: // '2'
		                            case 51: // '3'
		                            case 52: // '4'
		                            case 53: // '5'
		                            case 54: // '6'
		                            case 55: // '7'
		                            case 56: // '8'
		                            case 57: // '9'
		                                v = ((v << 4) + nc) - 48;
		                                break;
	
		                            case 97: // 'a'
		                            case 98: // 'b'
		                            case 99: // 'c'
		                            case 100: // 'd'
		                            case 101: // 'e'
		                            case 102: // 'f'
		                                v = ((v << 4)+10+nc)-97;
		                                break;
	
		                            case 65: // 'A'
		                            case 66: // 'B'
		                            case 67: // 'C'
		                            case 68: // 'D'
		                            case 69: // 'E'
		                            case 70: // 'F'
		                                v = ((v << 4)+10+nc)-65;
		                                break;
		                            default:
		                                // almost but no go
		                                j = 6;  // terminate the loop
		                                v = 0;  // clear the accumulator
		                                break;
		                        }
		                    } // for each of the 4 digits
	
		                    if (v > 0) {      // We got a full conversion
		                        c = (char) v;  // Use the converted char
		                        i += 5;       // skip the numeric values
		                    }
			                break;
		                }
		        		// octal characters: \0 to \377
		                case '0': 
		                case '1': 
		                case '2': 
		                case '3': {
		                    // Now we found the '0' we need to find up to 3 octal digits
		                    // Note: shifting left by 3 is the same as multiplying by 8
		                    int v = 0; // Accumulator
		                    int j;
		                    boolean stop = false;
		                    for (j = 0; j < 3 && !stop; j++) {
		                    	if (i + j < source.length()) {
			                        nc = source.charAt(i + j);
			                        switch (nc)
			                        {
			                            case 48: // '0'
			                            case 49: // '1'
			                            case 50: // '2'
			                            case 51: // '3'
			                            case 52: // '4'
			                            case 53: // '5'
			                            case 54: // '6'
			                            case 55: // '7'
			                                v = ((v << 3) + nc) - 48;
			                                break;
			                            default:
			                            	// some other character
			                                // almost but no go
			                            	stop = true;
			                            	// we have to go back one character, because we've read to far
			                            	j--;
			                                break;
			                        }
		                    	}
		                    } // for each of the digits
	
		                    if (v >= 0) {      // We got a full conversion
		                        c = (char) v;  // Use the converted char
		                        i += j;       // skip the numeric values
		                    }
		                	break;
		                }
		        		// escape sequences: \b \t \n \f \r \" \' \\
		                case BACKSLASH: {
		                	// if the next character is a backslash we have an
		                	// escaped backslash - not an unicode sequence
	                		// skip the second backslash
	                		i++;
	                		break;
		                }
		                case 'b': {
		                	c = '\b';
	                		i++;
	                		break;
		                }
		                case 't': {
		                	c = '\t';
	                		i++;
	                		break;
		                }
		                case 'n': {
		                	c = '\n';
	                		i++;
	                		break;
		                }
		                case 'f': {
		                	c = '\f';
	                		i++;
	                		break;
		                }
		                case 'r': {
		                	c = '\r';
	                		i++;
	                		break;
		                }
		                case '\"': {
		                	c = '\"';
	                		i++;
	                		break;
		                }
		                case '\'': {
		                	c = '\'';
	                		i++;
	                		break;
		                }
	                }
	            }
	            buffer.append(c);
	        }
			
		// Fill in the remaining characters from the buffer
		while (i < srcLen) {
			buffer.append(source.charAt(i++));
		}		
		return buffer.toString();
	}
	
	public static String escapeEscapedCharacters(String source) {
		
		source = source.replaceAll("\\\\", "\\\\\\\\");
		source = source.replaceAll("\\\b", "\\\\b");
		source = source.replaceAll("\\\t", "\\\\t");
		source = source.replaceAll("\\\n", "\\\\n");
		source = source.replaceAll("\\\f", "\\\\f");
		source = source.replaceAll("\\\r", "\\\\r");
		source = source.replaceAll("\"", "\\\\\"");
		source = source.replaceAll("\'", "\\\\\'");

		return source;
	}

}
