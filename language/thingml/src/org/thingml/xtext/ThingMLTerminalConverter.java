package org.thingml.xtext;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import com.google.inject.Inject;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.IValueConverter;

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
