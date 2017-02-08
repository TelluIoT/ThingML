package org.thingml.xtext;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;

public class AnnotationIDValueConverter extends AbstractLexerBasedConverter<String> {

	@Override
	public String toValue(String string, INode node) throws ValueConverterException {
		if (string.startsWith("@")) return string.substring(1);
		else return string;
	}

	@Override
	public String toString(String value) {
		return "@" + super.toString(value);
	}
	
	

}
