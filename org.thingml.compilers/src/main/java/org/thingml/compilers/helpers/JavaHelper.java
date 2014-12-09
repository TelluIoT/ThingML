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
package org.thingml.compilers.helpers;

import org.sintef.thingml.Enumeration;
import org.sintef.thingml.Type;
import org.thingml.compilers.Context;

/**
 * Created by bmori on 09.12.2014.
 */
public class JavaHelper {

    public static String getJavaType(final Type type, final boolean isArray, final Context ctx) {
        StringBuilder builder = new StringBuilder();
        if (type == null) {//void
            builder.append("void");
        } else if (type instanceof Enumeration){//enumeration
            builder.append(ctx.firstToUpper(type.getName()) + "_ENUM");
        } else {
            if (type.hasAnnotation("java_type")) {
                builder.append(type.annotation("java_type").toArray()[0]);
            } else {
                System.err.println("WARNING: no Java type defined for ThingML datatype " + type.getName());
                builder.append("/*No Java type was explicitly defined*/ Object");
            }
            if (isArray) {//array
                builder.append("[]");
            }
        }
        return builder.toString();
    }

}
