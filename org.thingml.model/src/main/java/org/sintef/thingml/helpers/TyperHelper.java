package org.sintef.thingml.helpers;

import org.sintef.thingml.AnnotatedElement;
import org.sintef.thingml.ObjectType;
import org.sintef.thingml.Type;
import org.sintef.thingml.constraints.Types;

/**
 * Created by ffl on 10.05.2016.
 */
public class TyperHelper {


    public Type getBroadType(Type self) {
        if (AnnotatedElementHelper.hasAnnotation(self, "type_checker")) {
            String ty = AnnotatedElementHelper.annotation(self, "type_checker").get(0);
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
            else if (ty.equals("Error"))
                return Types.ERROR_TYPE;
            else if (ty.equals("Void"))
                return Types.VOID_TYPE;
            else
                return Types.ANY_TYPE;
        } else if (this instanceof ObjectType) {
            return Types.OBJECT_TYPE;
        }
        return Types.ANY_TYPE;
    }

    public boolean isA(Type self, Type t) {
        if (t.getBroadType() == Types.ANY_TYPE)//anything is an Any
            return true;
        if (getBroadType(self) == t.getBroadType())
            return true;
        if (getBroadType(self) == Types.INTEGER_TYPE && t.getBroadType() == Types.REAL_TYPE) //an Integer is a Real
            return true;
        if (getBroadType(self) == Types.STRING_TYPE && t.getBroadType() == Types.OBJECT_TYPE)//a String is an Object
            return true;
        return false;
    }
}
