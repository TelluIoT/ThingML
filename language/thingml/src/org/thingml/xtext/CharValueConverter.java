/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.thingml.xtext;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;

public class CharValueConverter extends AbstractLexerBasedConverter<Byte> {

	@Override
	public Byte toValue(String string, INode node) throws ValueConverterException {
		if (string.length() == 3)
			return (byte)string.charAt(1);
		else {
			String str = string.substring(1, 3);
			if (str.equals("\\0"))
				return 0;
			else if (str.equals("\\t"))
				return 9;
			else if (str.equals("\\n"))
				return 10;
			else if (str.equals("\\r"))
				return 13;
			else if (str.equals("\\'"))
				return 39;
			else if (str.equals("\\\\"))
				return 92;
			else
				throw new ValueConverterException("Invalid character literal", node, null);
		}
	}
	
	public static String ToString(Byte value) {
		if (value >= 32 && value <= 126 && value != 39 && value != 92)
			return "'"+(char)(byte)value+"'";
		else if (value == 0)
			return "'\\0'";
		else if (value == 9)
			return "'\\t'";
		else if (value == 10)
			return "'\\n'";
		else if (value == 13)
			return "'\\r'";
		else if (value == 39)
			return "'\\''";
		else if (value == 92)
			return "'\\\\'";
		else
			return "''";
	}
	
	@Override
	public String toString(Byte value) {
		return ToString(value);
	}

}
