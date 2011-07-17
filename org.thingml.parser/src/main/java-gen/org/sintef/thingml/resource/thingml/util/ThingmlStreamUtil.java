/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.util;


public class ThingmlStreamUtil {
	
	private final static int IO_BUFFER_SIZE = 4 * 1024;
	
	public static void copy(java.io.InputStream in, java.io.OutputStream out) throws java.io.IOException {
		byte[] b = new byte[IO_BUFFER_SIZE];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
		out.flush();
	}
	
	public static String getContent(java.io.InputStream inputStream) throws java.io.IOException {
		StringBuffer content = new StringBuffer();
		java.io.InputStreamReader reader = new java.io.InputStreamReader(inputStream);
		int next = -1;
		while ((next = reader.read()) >= 0) {
			content.append((char) next);
		}
		return content.toString();
	}
}
