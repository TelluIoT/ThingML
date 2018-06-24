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
package org.thingml.xtext.constraints;

import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.Type;

/**
 * Created by bmori on 04.12.2015.
 */
public class Types {
    public static Type ANY_TYPE;
    public static Type ERROR_TYPE;
    public static Type BYTE_TYPE;
    public static Type INTEGER_TYPE;
    public static Type BOOLEAN_TYPE;
    public static Type CHARACTER_TYPE;
    public static Type STRING_TYPE;
    public static Type REAL_TYPE;
    public static Type VOID_TYPE;
    public static Type OBJECT_TYPE;

    static {
    	ThingMLFactory factory = ThingMLFactory.eINSTANCE;
    	
        ANY_TYPE = factory.createPrimitiveType();
        ANY_TYPE.setName("Any");
        PlatformAnnotation any = factory.createPlatformAnnotation();
        any.setName("type_checker");
        any.setValue("Any");
        ANY_TYPE.getAnnotations().add(any);
        
        ERROR_TYPE = factory.createPrimitiveType();
        ERROR_TYPE.setName("Error");
        PlatformAnnotation error = factory.createPlatformAnnotation();
        error.setName("type_checker");
        error.setValue("Error");
        ERROR_TYPE.getAnnotations().add(error);
        
        BYTE_TYPE = factory.createPrimitiveType();
        BYTE_TYPE.setName("Byte");
        PlatformAnnotation _byte = factory.createPlatformAnnotation();
        _byte.setName("type_checker");
        _byte.setValue("Byte");
        BYTE_TYPE.getAnnotations().add(_byte);
        
        INTEGER_TYPE = factory.createPrimitiveType();
        INTEGER_TYPE.setName("Integer");
        PlatformAnnotation integer = factory.createPlatformAnnotation();
        integer.setName("type_checker");
        integer.setValue("Integer");
        INTEGER_TYPE.getAnnotations().add(integer);
        
        BOOLEAN_TYPE = factory.createPrimitiveType();
        BOOLEAN_TYPE.setName("Boolean");
        PlatformAnnotation bool = factory.createPlatformAnnotation();
        bool.setName("type_checker");
        bool.setValue("Boolean");
        BOOLEAN_TYPE.getAnnotations().add(bool);
        
        CHARACTER_TYPE = factory.createPrimitiveType();
        CHARACTER_TYPE.setName("Character");
        PlatformAnnotation character = factory.createPlatformAnnotation();
        character.setName("type_checker");
        character.setValue("Character");
        CHARACTER_TYPE.getAnnotations().add(character);
        
        STRING_TYPE = factory.createPrimitiveType();
        STRING_TYPE.setName("String");
        PlatformAnnotation string = factory.createPlatformAnnotation();
        string.setName("type_checker");
        string.setValue("String");
        STRING_TYPE.getAnnotations().add(string);
        
        REAL_TYPE = factory.createPrimitiveType();
        REAL_TYPE.setName("Real");
        PlatformAnnotation real = factory.createPlatformAnnotation();
        real.setName("type_checker");
        real.setValue("Real");
        REAL_TYPE.getAnnotations().add(real);
        
        VOID_TYPE = factory.createPrimitiveType();
        VOID_TYPE.setName("Void");
        PlatformAnnotation _void = factory.createPlatformAnnotation();
        _void.setName("type_checker");
        _void.setValue("Void");
        VOID_TYPE.getAnnotations().add(_void);
        
        OBJECT_TYPE = factory.createPrimitiveType();
        OBJECT_TYPE.setName("Object");
        PlatformAnnotation _object = factory.createPlatformAnnotation();
        _object.setName("type_checker");
        _object.setValue("Object");
        OBJECT_TYPE.getAnnotations().add(_object);
    }
}
