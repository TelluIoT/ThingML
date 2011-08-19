/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.util;

/**
 * A UnicodeConverter can read an input stream and convert unicode escape
 * sequences (backslash + uXXXX) to actual unicode characters. Each escaped
 * unicode sequence (6 bytes) is replaced by the respective UTF-8 byte sequence (1
 * to 4 bytes).
 */
public class ThingmlUnicodeConverter extends org.sintef.thingml.resource.thingml.mopp.ThingmlInputStreamProcessor {
	
	private int[] stack = new int[4];
	private int stackPosition = -1;
	
	private static final char BACKSLASH = '\\';
	
	/**
	 * The original input stream.
	 */
	private java.io.InputStream inputStream;
	
	/**
	 * Creates a new UnicodeConverter that reads from the given stream.
	 * 
	 * @param inputStream the original stream to read from
	 */
	public ThingmlUnicodeConverter(java.io.InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * Reads one character from the stream. Escaped unicode characters are converted
	 * to UTF-8 byte sequences (i.e., up to four bytes).
	 */
	@Override	public int read() throws java.io.IOException {
		if (!stackIsEmpty()) {
			int result = pop();
			return result;
		}
		int read = inputStream.read();
		
		// Must have format \\uXXXX where XXXX is a hex number
		if (read >= 0) {
			char c = (char) read;
			if (c == BACKSLASH) {
				int next = inputStream.read();
				char nextChar = (char) next;
				if (nextChar == 'u') {
					// Now we found the 'u' we need to find another 4 hex digits
					// Note: shifting left by 4 is the same as multiplying by 16
					int v = 0; // Accumulator
					boolean complete = true;
					int j = 0;
					while (j < 4) {
						next = inputStream.read();
						nextChar = (char) next;
						if (nextChar == 'u') {
							// ignore more u characters
							continue;
						}
						j++;
						if (next < 0) {
							complete = false;
							break;
						}
						switch (nextChar) {
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
							v = ((v << 4) + nextChar) - 48;
							break;
							
							case 97: // 'a'
							case 98: // 'b'
							case 99: // 'c'
							case 100: // 'd'
							case 101: // 'e'
							case 102: // 'f'
							v = ((v << 4) + 10 + nextChar) - 97;
							break;
							
							case 65: // 'A'
							case 66: // 'B'
							case 67: // 'C'
							case 68: // 'D'
							case 69: // 'E'
							case 70: // 'F'
							v = ((v << 4) + 10 + nextChar) - 65;
							break;
							default:							// this case can never happen if the unicode escape sequences are correct
							v = 0;
							// clear the accumulator
							break;
						}
					}
					// for each of the 4 digits
					
					if (complete) {
						// We got a full conversion
						return encodePushAndReturn(v);
					}
				} else {
					// was: lookAheadCharacter = next;
					encodePush(next);
				}
			} else {
				return encodePushAndReturn(read);
			}
		}
		// do not encode negative numbers, because they signal EOF
		return read;
	}
	
	private int encodePushAndReturn(int next) {
		byte[] encoded = encode(next);
		// we must add the bytes backwards because we use a stack
		// we do not push the first byte since it is returned immediately
		for (int i = encoded.length - 1; i >= 1; i--) {
			push(unsignedByteToInt(encoded[i]));
		}
		return unsignedByteToInt(encoded[0]);
	}
	
	private void encodePush(int next) {
		byte[] encoded = encode(next);
		// we must add the bytes backwards because we use a stack
		for (int i = encoded.length - 1; i >= 0; i--) {
			push(unsignedByteToInt(encoded[i]));
		}
	}
	
	private int pop() {
		assert stackPosition >= 0;
		int result = stack[stackPosition];
		stackPosition--;
		return result;
	}
	
	private void push(int aByte) {
		stackPosition++;
		assert stackPosition < stack.length;
		stack[stackPosition] = aByte;
	}
	
	private boolean stackIsEmpty() {
		return stackPosition < 0;
	}
	
	public static int unsignedByteToInt(byte b) {
		return (int) b & 0xFF;
	}
	
	public static byte[] encode(int ch) {
		// return encode(new int[]{ch});
		int bytesNeeded = 0;
		if (ch < 0x80) {
			++bytesNeeded;
		} else if (ch < 0x0800) {
			bytesNeeded += 2;
		} else if (ch < 0x10000) {
			bytesNeeded += 3;
		} else {
			bytesNeeded += 4;
		}
		// allocate a byte[] of the necessary size
		byte[] utf8 = new byte[bytesNeeded];
		// do the conversion from character code points to utf-8
		int bytes = 0;
		if (ch < 0x80) {
			utf8[bytes++] = (byte) ch;
		} else if (ch < 0x0800) {
			utf8[bytes++] = (byte) (ch >> 6 | 0xC0);
			utf8[bytes++] = (byte) (ch & 0x3F | 0x80);
		} else if (ch < 0x10000) {
			utf8[bytes++] = (byte) (ch >> 12 | 0xE0);
			utf8[bytes++] = (byte) (ch >> 6 & 0x3F | 0x80);
			utf8[bytes++] = (byte) (ch & 0x3F | 0x80);
		} else {
			utf8[bytes++] = (byte) (ch >> 18 | 0xF0);
			utf8[bytes++] = (byte) (ch >> 12 & 0x3F | 0x80);
			utf8[bytes++] = (byte) (ch >> 6 & 0x3F | 0x80);
			utf8[bytes++] = (byte) (ch & 0x3F | 0x80);
		}
		return utf8;
	}
	
	public static byte[] encode(int[] ch) {
		// determine how many bytes are needed for the complete conversion
		int bytesNeeded = 0;
		for (int i = 0; i < ch.length; i++) {
			if (ch[i] < 0x80) {
				++bytesNeeded;
			} else if (ch[i] < 0x0800) {
				bytesNeeded += 2;
			} else if (ch[i] < 0x10000) {
				bytesNeeded += 3;
			} else {
				bytesNeeded += 4;
			}
		}
		// allocate a byte[] of the necessary size
		byte[] utf8 = new byte[bytesNeeded];
		// do the conversion from character code points to utf-8
		for (int i = 0, bytes = 0; i < ch.length; i++) {
			if (ch[i] < 0x80) {
				utf8[bytes++] = (byte) ch[i];
			} else if (ch[i] < 0x0800) {
				utf8[bytes++] = (byte) (ch[i] >> 6 | 0xC0);
				utf8[bytes++] = (byte) (ch[i] & 0x3F | 0x80);
			} else if (ch[i] < 0x10000) {
				utf8[bytes++] = (byte) (ch[i] >> 12 | 0xE0);
				utf8[bytes++] = (byte) (ch[i] >> 6 & 0x3F | 0x80);
				utf8[bytes++] = (byte) (ch[i] & 0x3F | 0x80);
			} else {
				utf8[bytes++] = (byte) (ch[i] >> 18 | 0xF0);
				utf8[bytes++] = (byte) (ch[i] >> 12 & 0x3F | 0x80);
				utf8[bytes++] = (byte) (ch[i] >> 6 & 0x3F | 0x80);
				utf8[bytes++] = (byte) (ch[i] & 0x3F | 0x80);
			}
		}
		return utf8;
	}
	
	public String getOutputEncoding() {
		return "UTF-8";
	}
}
