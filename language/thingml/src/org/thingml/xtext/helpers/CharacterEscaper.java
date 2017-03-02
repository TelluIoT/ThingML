package org.thingml.xtext.helpers;

public class CharacterEscaper {

	private static final char BACKSLASH = '\\';
	
	public static String unescapeEscapedCharacters(String source) {
	
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