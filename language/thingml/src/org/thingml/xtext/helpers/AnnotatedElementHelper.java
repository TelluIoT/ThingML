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

import org.thingml.xtext.thingML.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ffl on 10.05.2016.
 */
public class AnnotatedElementHelper {


	private static String cleanAnnotation(String value) {
		if (value.startsWith("\"") && value.endsWith("\"")) {
			return value.substring(1, value.length()-1);
		}
		return value;
	}
	
    public static List<PlatformAnnotation> allAnnotations(AnnotatedElement self) {
        return self.getAnnotations();
    }


    public static boolean isDefined(AnnotatedElement self, String annotation, String value) {
        for (PlatformAnnotation a : allAnnotations(self)) {
            if (a.getName().equals(annotation)) {
                if (cleanAnnotation(a.getValue()).equals(value))
                    return true;
            }
        }
        return false;
    }


    public static boolean hasAnnotation(AnnotatedElement self, String name) {
        for (PlatformAnnotation a : allAnnotations(self)) {
            if (a.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }


    public static List<String> annotation(AnnotatedElement self, String name) {
        List<String> result = new ArrayList<String>();
        for (PlatformAnnotation a : self.getAnnotations()) {
            if (a.getName().equals(name)) {
                result.add(cleanAnnotation(a.getValue()));
            }
        }
        return result;
    }

    public static String annotationOrElse(AnnotatedElement self, String name, String defaultValue) {
        List<String> result = new ArrayList<String>();
        for (PlatformAnnotation a : self.getAnnotations()) {
            if (a.getName().equals(name)) {
                result.add(cleanAnnotation(a.getValue()));
            }
        }
        if (result.isEmpty())
            return defaultValue;
        else
            return result.get(0);
    }




}
