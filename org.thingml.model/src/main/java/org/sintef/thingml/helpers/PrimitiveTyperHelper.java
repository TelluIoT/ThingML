package org.sintef.thingml.helpers;

import org.sintef.thingml.PrimitiveType;

/**
 * Created by ffl on 10.05.2016.
 */
public class PrimitiveTyperHelper {


    public static boolean isNumber(PrimitiveType self) {
        return self.getName().contains("Int") || self.getName().contains("Long") || self.getName().contains("Float") || self.getName().contains("Double");
    }

    public static boolean isBoolean(PrimitiveType self) {
        return self.getName().contains("Bool");
    }

    public static boolean isString(PrimitiveType self) {
        return self.getName().contains("String");
    }

    public static boolean isChar(PrimitiveType self) {
        return self.getName().contains("Char");
    }

    public static boolean isByte(PrimitiveType self) {
        return self.getName().contains("Byte");
    }


}
