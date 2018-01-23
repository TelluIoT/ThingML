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

import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.Type;

/**
 * Created by ffl on 10.05.2016.
 */
public class TyperHelper {


    public static Type getBroadType(Type self) {
        if (AnnotatedElementHelper.hasAnnotation(self, "type_checker")) {
            final String ty = AnnotatedElementHelper.annotation(self, "type_checker").get(0);
            if (ty.equals("Integer"))
                return Types.INTEGER_TYPE;
            else if (ty.equals("Real"))
                return Types.REAL_TYPE;
            else if (ty.equals("Boolean"))
                return Types.BOOLEAN_TYPE;
            else if (ty.equals("Character"))
                return Types.CHARACTER_TYPE;
            else if (ty.equals("String"))
                return Types.STRING_TYPE;
            else if (ty.equals("Object"))
                return Types.OBJECT_TYPE;
            else if (ty.equals("Error"))
                return Types.ERROR_TYPE;
            else if (ty.equals("Void"))
                return Types.VOID_TYPE;
            else
                return Types.ANY_TYPE;
        } else if (self instanceof ObjectType) {
            return Types.OBJECT_TYPE;
        }
        return Types.ANY_TYPE;
    }

    public static boolean isA(Type self, Type t) {
    	self = getBroadType(self);
    	t = getBroadType(t);
        if (self == t) // T is a T
            return true;
    	if (t == Types.OBJECT_TYPE) //Only String and Object are Object
    		return self == Types.OBJECT_TYPE || self == Types.STRING_TYPE; 
        if (self == Types.ANY_TYPE)//Any is anything (except Object, see above)
            return t != Types.ERROR_TYPE;
        if (t == Types.ANY_TYPE)//anything (except Object) is an Any
            return self != Types.OBJECT_TYPE && self != Types.ERROR_TYPE;
        if (self == Types.INTEGER_TYPE && t == Types.REAL_TYPE) //an Integer is a Real
            return true;        
        return false;
    }
}
