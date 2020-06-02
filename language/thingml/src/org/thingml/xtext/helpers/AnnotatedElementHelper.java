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

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.AnnotatedElement;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.CharLiteral;
import org.thingml.xtext.thingML.DoubleLiteral;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.ExternLiteral;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.Literal;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;

/**
 * Created by ffl on 10.05.2016.
 */
public class AnnotatedElementHelper {

	public static String toString(Literal value) {
		if (value instanceof StringLiteral) return ((StringLiteral)value).getStringValue();
		if (value instanceof ExternLiteral) return ((ExternLiteral)value).getStringValue();
		if (value instanceof ByteLiteral) return Byte.toString(((ByteLiteral)value).getByteValue());
		if (value instanceof IntegerLiteral) return Long.toString(((IntegerLiteral)value).getIntValue());
		if (value instanceof DoubleLiteral) return Double.toString(((DoubleLiteral)value).getDoubleValue());
		if (value instanceof BooleanLiteral) return Boolean.toString(((BooleanLiteral)value).isBoolValue());
		if (value instanceof CharLiteral) return Byte.toString(((CharLiteral)value).getCharValue());
		if (value instanceof EnumLiteralRef) return toString(((EnumLiteralRef)value).getLiteral().getInit());
		return null;
	}
	
    public static List<String> allAnnotation(Thing self, String name) {
    	final List<String> annotations = new ArrayList<>();
    	for (Thing t : ThingMLHelpers.allThingFragments(self)) {
    		for(PlatformAnnotation a : t.getAnnotations()) {
    			if (a.getName().equals(name) && a.getValue() != null)
    				annotations.add(toString(a.getValue()));	
    		}
    		
    	}    	
        return annotations;
    }


    public static boolean isDefined(AnnotatedElement self, String annotation, String value) {
        for (PlatformAnnotation a : self.getAnnotations()) {
            if (a.getName().equals(annotation)) {
            	if (a.getValue() == null)
            		continue;
                if (value.equals(toString(a.getValue())))
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
                result.add(toString(a.getValue()));
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
