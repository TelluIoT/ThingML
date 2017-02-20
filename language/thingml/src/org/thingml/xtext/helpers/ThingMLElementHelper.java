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

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.*;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ffl on 10.05.2016.
 */
public class ThingMLElementHelper {


    public static ThingMLModel findContainingModel(EObject self) {
        return ThingMLHelpers.findContainingModel(self);
    }


    public static Thing findContainingThing(EObject self) {
        return ThingMLHelpers.findContainingThing(self);
    }


    public static Configuration findContainingConfiguration(EObject self) {
        return ThingMLHelpers.findContainingConfiguration(self);
    }

    public static State findContainingState(EObject self) {
        return ThingMLHelpers.findContainingState(self);
    }


    public static Region findContainingRegion(EObject self) {
        return ThingMLHelpers.findContainingRegion(self);
    }


    public static Handler findContainingHandler(EObject self) {
        return ThingMLHelpers.findContainingHandler(self);
    }


    public static String qname(EObject self, String separator) {
        if (separator == null) {
            separator = "::";
        }
        
        String result = null;
        EObject elem  = self;
        String name = null;
        while(elem != null) {
            name = getName(elem);
            if (name == null || name == "") name = elem.getClass().getName();
            if (result == null) result = name;
            else result = name + separator + result;
            if (elem.eContainer() != null && elem.eContainer() instanceof EObject)
                elem = elem.eContainer();
            else elem = null;
        }
        return result;
    }
    
    public static String getName(EObject self) {
    	
    	if (self instanceof NamedElement) return ((NamedElement)self).getName();
    	else return null;
    	
    }

}
