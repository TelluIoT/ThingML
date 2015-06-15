package org.thingml.generated.gui;

public class StringHelper {

    public static Object toObject(Class clazz, String value) {
        if (Boolean.TYPE == clazz || Boolean.class == clazz) return Boolean.parseBoolean(value);
        else if (Byte.TYPE == clazz || Byte.class == clazz) return Byte.parseByte(value);
        else if (Short.TYPE == clazz || Short.class == clazz) return Short.parseShort(value);
        else if (Integer.TYPE == clazz || Integer.class == clazz) return Integer.parseInt(value);
        else if (Long.TYPE == clazz || Long.class == clazz) return Long.parseLong(value);
        else if (Float.TYPE == clazz || Float.class == clazz) return Float.parseFloat(value);
        else if (Double.TYPE == clazz || Double.class == clazz) return Double.parseDouble(value);
        return value;
    }

}