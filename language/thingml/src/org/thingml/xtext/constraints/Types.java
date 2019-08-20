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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.TypeRef;

/**
 * Created by bmori on 04.12.2015.
 */
public class Types {
    public static TypeRef ANY_TYPEREF;
    public static TypeRef ERROR_TYPEREF;
    public static TypeRef VOID_TYPEREF;
    
    public static TypeRef BYTE_TYPEREF;
    public static TypeRef INTEGER_TYPEREF;
    public static TypeRef BOOLEAN_TYPEREF;
    public static TypeRef CHARACTER_TYPEREF;
    public static TypeRef STRING_TYPEREF;
    public static TypeRef REAL_TYPEREF;    
    public static TypeRef OBJECT_TYPEREF;
    
    public static TypeRef ARRAY_ANY_TYPEREF;
    public static TypeRef ARRAY_BYTE_TYPEREF;
    public static TypeRef ARRAY_INTEGER_TYPEREF;
    public static TypeRef ARRAY_BOOLEAN_TYPEREF;
    public static TypeRef ARRAY_CHARACTER_TYPEREF;
    public static TypeRef ARRAY_STRING_TYPEREF;
    public static TypeRef ARRAY_REAL_TYPEREF;    
    public static TypeRef ARRAY_OBJECT_TYPEREF;

    private static Map<String, TypeRef> scalars = new HashMap<>();
    private static Map<String, TypeRef> arrays = new HashMap<>();
    
    static {
    	final ThingMLFactory factory = ThingMLFactory.eINSTANCE;
    	
    	ANY_TYPEREF = factory.createTypeRef();
        final Type ANY_TYPE = factory.createPrimitiveType();
        ANY_TYPE.setName("Any");
        final PlatformAnnotation any = factory.createPlatformAnnotation();
        any.setName("type_checker");
        any.setValue("Any");
        ANY_TYPE.getAnnotations().add(any);
        ANY_TYPEREF.setType(ANY_TYPE);
        ARRAY_ANY_TYPEREF = EcoreUtil.copy(ANY_TYPEREF);
        ARRAY_ANY_TYPEREF.setIsArray(true);
        scalars.put(ANY_TYPEREF.getType().getName(), ANY_TYPEREF);
        arrays.put(ANY_TYPEREF.getType().getName(), ARRAY_ANY_TYPEREF);
        
        ERROR_TYPEREF = factory.createTypeRef();
        final Type ERROR_TYPE = factory.createPrimitiveType();
        ERROR_TYPE.setName("Error");
        final PlatformAnnotation error = factory.createPlatformAnnotation();
        error.setName("type_checker");
        error.setValue("Error");
        ERROR_TYPE.getAnnotations().add(error);
        ERROR_TYPEREF.setType(ERROR_TYPE);
        scalars.put(ERROR_TYPEREF.getType().getName(), ERROR_TYPEREF);
        arrays.put(ERROR_TYPEREF.getType().getName(), ERROR_TYPEREF);
        
        VOID_TYPEREF = factory.createTypeRef();
        final Type VOID_TYPE = factory.createPrimitiveType();
        VOID_TYPE.setName("Void");
        final PlatformAnnotation _void = factory.createPlatformAnnotation();
        _void.setName("type_checker");
        _void.setValue("Void");
        VOID_TYPE.getAnnotations().add(_void);
        VOID_TYPEREF.setType(VOID_TYPE);
        scalars.put(VOID_TYPEREF.getType().getName(), VOID_TYPEREF);
        arrays.put(VOID_TYPEREF.getType().getName(), ERROR_TYPEREF);
        
        //////////////////////////////////////////////////////////////////////
        
        BYTE_TYPEREF = factory.createTypeRef();
        final Type BYTE_TYPE = factory.createPrimitiveType();
        BYTE_TYPE.setName("Byte");
        final PlatformAnnotation _byte = factory.createPlatformAnnotation();
        _byte.setName("type_checker");
        _byte.setValue("Byte");
        BYTE_TYPE.getAnnotations().add(_byte);
        BYTE_TYPEREF.setType(BYTE_TYPE);
        ARRAY_BYTE_TYPEREF = EcoreUtil.copy(BYTE_TYPEREF);
        ARRAY_BYTE_TYPEREF.setIsArray(true);
        scalars.put(BYTE_TYPEREF.getType().getName(), BYTE_TYPEREF);
        arrays.put(BYTE_TYPEREF.getType().getName(), ARRAY_BYTE_TYPEREF);
        
        INTEGER_TYPEREF = factory.createTypeRef();
        final Type INTEGER_TYPE = factory.createPrimitiveType();
        INTEGER_TYPE.setName("Integer");
        final PlatformAnnotation integer = factory.createPlatformAnnotation();
        integer.setName("type_checker");
        integer.setValue("Integer");
        INTEGER_TYPE.getAnnotations().add(integer);
        INTEGER_TYPEREF.setType(INTEGER_TYPE);
        ARRAY_INTEGER_TYPEREF = EcoreUtil.copy(INTEGER_TYPEREF);
        ARRAY_INTEGER_TYPEREF.setIsArray(true);
        scalars.put(INTEGER_TYPEREF.getType().getName(), INTEGER_TYPEREF);
        arrays.put(INTEGER_TYPEREF.getType().getName(), ARRAY_INTEGER_TYPEREF);
        
        BOOLEAN_TYPEREF = factory.createTypeRef();
        final Type BOOLEAN_TYPE = factory.createPrimitiveType();
        BOOLEAN_TYPE.setName("Boolean");
        final PlatformAnnotation bool = factory.createPlatformAnnotation();
        bool.setName("type_checker");
        bool.setValue("Boolean");
        BOOLEAN_TYPE.getAnnotations().add(bool);
        BOOLEAN_TYPEREF.setType(BOOLEAN_TYPE);
        ARRAY_BOOLEAN_TYPEREF = EcoreUtil.copy(BOOLEAN_TYPEREF);
        ARRAY_BOOLEAN_TYPEREF.setIsArray(true);
        scalars.put(BOOLEAN_TYPEREF.getType().getName(), BOOLEAN_TYPEREF);
        arrays.put(BOOLEAN_TYPEREF.getType().getName(), ARRAY_BOOLEAN_TYPEREF);
        
        CHARACTER_TYPEREF = factory.createTypeRef();
        final Type CHARACTER_TYPE = factory.createPrimitiveType();
        CHARACTER_TYPE.setName("Character");
        final PlatformAnnotation character = factory.createPlatformAnnotation();
        character.setName("type_checker");
        character.setValue("Character");
        CHARACTER_TYPE.getAnnotations().add(character);
        CHARACTER_TYPEREF.setType(CHARACTER_TYPE);
        ARRAY_CHARACTER_TYPEREF = EcoreUtil.copy(CHARACTER_TYPEREF);
        ARRAY_CHARACTER_TYPEREF.setIsArray(true);
        scalars.put(CHARACTER_TYPEREF.getType().getName(), CHARACTER_TYPEREF);
        arrays.put(CHARACTER_TYPEREF.getType().getName(), ARRAY_CHARACTER_TYPEREF);
        
        STRING_TYPEREF = factory.createTypeRef();
        final Type STRING_TYPE = factory.createPrimitiveType();
        STRING_TYPE.setName("String");
        final PlatformAnnotation string = factory.createPlatformAnnotation();
        string.setName("type_checker");
        string.setValue("String");
        STRING_TYPE.getAnnotations().add(string);
        STRING_TYPEREF.setType(STRING_TYPE);
        ARRAY_STRING_TYPEREF = EcoreUtil.copy(STRING_TYPEREF);
        ARRAY_STRING_TYPEREF.setIsArray(true);
        scalars.put(STRING_TYPEREF.getType().getName(), STRING_TYPEREF);
        arrays.put(STRING_TYPEREF.getType().getName(), ARRAY_STRING_TYPEREF);
        
        REAL_TYPEREF = factory.createTypeRef();
        final Type REAL_TYPE = factory.createPrimitiveType();
        REAL_TYPE.setName("Real");
        final PlatformAnnotation real = factory.createPlatformAnnotation();
        real.setName("type_checker");
        real.setValue("Real");
        REAL_TYPE.getAnnotations().add(real);
        REAL_TYPEREF.setType(REAL_TYPE);
        ARRAY_REAL_TYPEREF = EcoreUtil.copy(REAL_TYPEREF);
        ARRAY_REAL_TYPEREF.setIsArray(true);
        scalars.put(REAL_TYPEREF.getType().getName(), REAL_TYPEREF);
        arrays.put(REAL_TYPEREF.getType().getName(), ARRAY_REAL_TYPEREF);
        
        OBJECT_TYPEREF = factory.createTypeRef();
        final Type OBJECT_TYPE = factory.createPrimitiveType();
        OBJECT_TYPE.setName("Object");
        final PlatformAnnotation _object = factory.createPlatformAnnotation();
        _object.setName("type_checker");
        _object.setValue("Object");
        OBJECT_TYPE.getAnnotations().add(_object);
        OBJECT_TYPEREF.setType(OBJECT_TYPE);
        ARRAY_OBJECT_TYPEREF = EcoreUtil.copy(OBJECT_TYPEREF);
        ARRAY_OBJECT_TYPEREF.setIsArray(true);
        scalars.put(OBJECT_TYPEREF.getType().getName(), OBJECT_TYPEREF);
        arrays.put(OBJECT_TYPEREF.getType().getName(), ARRAY_OBJECT_TYPEREF);
    }
    
    public static TypeRef getTypeRef(TypeRef t, boolean isArray) {
    	if (isArray) return arrays.getOrDefault(t.getType().getName(), ERROR_TYPEREF);
    	return scalars.getOrDefault(t.getType().getName(), ERROR_TYPEREF);
    }
    
    public static String toString(TypeRef t) {
    	if (t.isIsArray())
    		return t.getType().getName() + "[]";
    	else
    		return t.getType().getName();
    }
}
