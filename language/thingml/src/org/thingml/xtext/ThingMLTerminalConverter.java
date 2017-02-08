package org.thingml.xtext;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import com.google.inject.Inject;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.IValueConverter;

public class ThingMLTerminalConverter extends DefaultTerminalConverters   {
	
	 @Inject
     private AnnotationIDValueConverter annotationIDValueConverter;
       
    @ValueConverter(rule = "ANNOTATION_ID")
     public IValueConverter<String> ANNOTATION_ID() {
            return annotationIDValueConverter;
    }
}
