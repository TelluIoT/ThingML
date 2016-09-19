/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sintef.thingml.constraints;

import org.thingml.xtext.thingML.*;
import org.thingml.xtext.thingML.impl.ThingMLPackageImpl;

/**
 * Created by bmori on 04.12.2015.
 */
public class Types {
    public static Type ANY_TYPE;
    public static Type ERROR_TYPE;
    public static Type INTEGER_TYPE;
    public static Type BOOLEAN_TYPE;
    public static Type CHARACTER_TYPE;
    public static Type STRING_TYPE;
    public static Type REAL_TYPE;
    public static Type VOID_TYPE;
    public static Type OBJECT_TYPE;

    static {
        ThingMLFactory factory = ThingMLPackageImpl.init().getThingMLFactory();
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
