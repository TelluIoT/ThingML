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
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.TypeRef;

/**
 * Created by ffl on 10.05.2016.
 */
public class TyperHelper {
	
	public static boolean isSerializable(Type t) {
		if (t instanceof PrimitiveType) return true;
		if (AnnotatedElementHelper.hasFlag(t, "serializable")) return true;
		if (t instanceof Enumeration) 
			return (((Enumeration)t).getTypeRef() == null)? false : isSerializable(((Enumeration)t).getTypeRef().getType());		
		return false;
	}

	private static TypeRef getType(String ty, boolean isArray) {
        if (ty.equals("Byte"))
        	return Types.getTypeRef(Types.BYTE_TYPEREF, isArray);
        else if (ty.equals("Integer"))
            return Types.getTypeRef(Types.INTEGER_TYPEREF, isArray);
        else if (ty.equals("Real"))
            return Types.getTypeRef(Types.REAL_TYPEREF, isArray);
        else if (ty.equals("Boolean"))
            return Types.getTypeRef(Types.BOOLEAN_TYPEREF, isArray);
        else if (ty.equals("Character"))
            return Types.getTypeRef(Types.CHARACTER_TYPEREF, isArray);
        else if (ty.equals("String"))
            return Types.getTypeRef(Types.STRING_TYPEREF, isArray);
        else if (ty.equals("Object"))
            return Types.getTypeRef(Types.OBJECT_TYPEREF, isArray);
        else if (ty.equals("Error"))
            return Types.getTypeRef(Types.ERROR_TYPEREF, isArray);
        else if (ty.equals("Void"))
            return Types.getTypeRef(Types.VOID_TYPEREF, isArray);
        else
            return Types.getTypeRef(Types.ANY_TYPEREF, isArray);
	}
	
    public static TypeRef getBroadType(TypeRef self) {
    	if (self.getType() instanceof ObjectType) {
    		if (self.getType().getName().equals("String"))
    			return Types.getTypeRef(Types.STRING_TYPEREF, self.isIsArray());
            return Types.getTypeRef(Types.OBJECT_TYPEREF, self.isIsArray());
        }
    	if (self instanceof Enumeration) {
    		final Enumeration e = (Enumeration)self;
    		if (e.getTypeRef() != null && e.getTypeRef().getType() != null) {
    			return getBroadType(e.getTypeRef());
    		}
    		return self;
    	}
        if (AnnotatedElementHelper.hasAnnotation(self.getType(), "type_checker")) {
            final String ty = AnnotatedElementHelper.annotation(self.getType(), "type_checker").get(0);
            return getType(ty, self.isIsArray());
        } 
        return Types.getTypeRef(Types.ANY_TYPEREF, self.isIsArray());
    }

    public static boolean isA(TypeRef self, TypeRef t) {
    	if (self.isIsArray() ^ t.isIsArray()) return false;
    	
    	self = getBroadType(self);
    	t = getBroadType(t);
        if (self == t) // T is a T
            return true;
        if (t == Types.STRING_TYPEREF) //Anything is (can be casted to) a String //That is a workaround so that we can use + to concatenate vars in a String (since we do not have a str concat in ThingML, but + is widely used)
    		return true;
    	if (t == Types.OBJECT_TYPEREF) //Only String, Object and Any are Object
    		return self == Types.ANY_TYPEREF || self == Types.OBJECT_TYPEREF || self == Types.STRING_TYPEREF; 
        if (self == Types.ANY_TYPEREF)//Any is anything
            return t != Types.ERROR_TYPEREF;
        if (t == Types.ANY_TYPEREF)//anything is an Any
            return /*self != Types.OBJECT_TYPE &&*/ self != Types.ERROR_TYPEREF;
        if (self == Types.BYTE_TYPEREF && t == Types.INTEGER_TYPEREF) //a Byte is an Integer
        	return true;
        if ((self == Types.INTEGER_TYPEREF || self == Types.BYTE_TYPEREF) && t == Types.REAL_TYPEREF) //an Integer or a Byte is a Real
            return true;
    	if (self instanceof Enumeration) {
    		if (((Enumeration) self).getTypeRef() != null) {
    			return isA(((Enumeration) self).getTypeRef(), t);
    		}
    		if (AnnotatedElementHelper.hasAnnotation(self.getType(), "type_checker")) {
    			final String ty = AnnotatedElementHelper.annotation(self.getType(), "type_checker").get(0);
    			final TypeRef typ = getType(ty, self.isIsArray()); 
    			return isA(typ, t);
    		}
    	}
        return false;
    }
}
