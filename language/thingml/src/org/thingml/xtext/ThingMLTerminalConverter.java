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

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;

import com.google.inject.Inject;

public class ThingMLTerminalConverter extends DefaultTerminalConverters {
	
	@Inject
	private AnnotationIDValueConverter annotationIDValueConverter;
	@ValueConverter(rule = "ANNOTATION_ID")
	public IValueConverter<String> ANNOTATION_ID() {
		return annotationIDValueConverter;
	}
	
	@Inject
	private StringValueConverter stringValueConverter;
	@ValueConverter(rule = "STRING")
	public IValueConverter<String> STRING() {
		return stringValueConverter;
	}
	
	@Inject
	private ExternValueConverter externValueConverter;
	@ValueConverter(rule = "EXTERN")
	public IValueConverter<String> EXTERN() {
		return externValueConverter;
	}
	
	@Inject
	private ByteValueConverter byteValueConverter;
	@ValueConverter(rule = "BYTE")
	public IValueConverter<Byte> BYTE() {
		return byteValueConverter;
	}
	
	@Inject
	private CharValueConverter charValueConverter;
	@ValueConverter(rule = "CHAR")
	public IValueConverter<Byte> CHAR() {
		return charValueConverter;
	}
	
	@Inject
	private IntValueConverter intValueConverter;
	@ValueConverter(rule = "INT")
	public IValueConverter<Long> LINT() {
		return intValueConverter;
	}
}
