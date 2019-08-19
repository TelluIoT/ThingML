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
package org.thingml.xtext.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.AnnotatedElement;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Thing;

/**
 * Created by ffl on 10.05.2016.
 */
public class AnnotatedElementHelper {

	private static Pattern leadingTrailingNewlines = Pattern.compile("^\\s+\\n|\\n\\s+$");

	private static String cleanAnnotation(String value) {
		// Remove surrounding double-quotes
		String unquoted = value;
		if (value.startsWith("\"") && value.endsWith("\"")) {
			unquoted = value.substring(1, value.length()-1);
		}
		// Remove leading and trailing patterns of newlines an spaces
		// These commonly occur when a annotation is split over multiple lines
		Matcher m = leadingTrailingNewlines.matcher(unquoted);
		StringBuffer stripped = new StringBuffer(unquoted.length());
		while (m.find())
			m.appendReplacement(stripped, "");
		m.appendTail(stripped);
		// Done
		return stripped.toString();
	}
	
    public static List<String> allAnnotation(Thing self, String name) {
    	final List<String> annotations = new ArrayList<>();
    	for (Thing t : ThingMLHelpers.allThingFragments(self)) {
    		for(PlatformAnnotation a : t.getAnnotations()) {
    			if (a.getName().equals(name) && a.getValue() != null)
    				annotations.add(a.getValue());	
    		}
    		
    	}    	
        return annotations;
    }


    public static boolean isDefined(AnnotatedElement self, String annotation, String value) {
        for (PlatformAnnotation a : self.getAnnotations()) {
            if (a.getName().equals(annotation)) {
            	if (a.getValue() == null)
            		continue;
                if (value.equals(cleanAnnotation(a.getValue())))
                    return true;
            }
        }
        return false;
    }


    public static boolean hasAnnotation(AnnotatedElement self, String name) {
        for (PlatformAnnotation a : self.getAnnotations()) {
            if (a.getName().equals(name) && a.getValue() != null) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasFlag(AnnotatedElement self, String name) {
        for (PlatformAnnotation a : self.getAnnotations()) {
            if (a.getName().equals(name) && a.getValue() == null) {
                return true;
            }
            if (a.getName().equals(name) && a.getValue().equals("true")) {
            	return true;
            }
        }
        return false;
    }


    public static List<String> annotation(AnnotatedElement self, String name) {
        List<String> result = new ArrayList<String>();
        for (PlatformAnnotation a : self.getAnnotations()) {
            if (a.getName().equals(name) && a.getValue() != null) {
                result.add(cleanAnnotation(a.getValue()));
            }
        }
        return result;
    }

    public static String annotationOrElse(AnnotatedElement self, String name, String defaultValue) {
        List<String> result = annotation(self, name);
        if (result.isEmpty())
            return defaultValue;
        else
            return result.get(0);
    }
    
    public static String firstAnnotation(AnnotatedElement self, String name) {
    	return annotationOrElse(self, name, null);
    }
}
