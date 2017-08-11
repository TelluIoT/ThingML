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

public class ThingMLTerminalConverter extends DefaultTerminalConverters   {
	
	 @Inject
     private AnnotationIDValueConverter annotationIDValueConverter;
	 
	 @Inject
     private StringExtValueConverter stringExtValueConverter;
	 
	 @Inject
     private StringLitValueConverter stringLitValueConverter;
       
    @ValueConverter(rule = "ANNOTATION_ID")
     public IValueConverter<String> ANNOTATION_ID() {
            return annotationIDValueConverter;
    }
    
    @ValueConverter(rule = "STRING_EXT")
    public IValueConverter<String> STRING_EXT() {
           return stringExtValueConverter;
   }
    
    @ValueConverter(rule = "STRING_LIT")
    public IValueConverter<String> STRING_LIT() {
           return stringLitValueConverter;
   }
}
