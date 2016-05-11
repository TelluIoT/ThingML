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
