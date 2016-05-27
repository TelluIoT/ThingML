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

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ffl on 10.05.2016.
 */
public class ThingMLElementHelper {


    public static ThingMLModel findContainingModel(ThingMLElement self) {
        return ThingMLHelpers.findContainingModel(self);
    }


    public static Thing findContainingThing(ThingMLElement self) {
        return ThingMLHelpers.findContainingThing(self);
    }


    public static Configuration findContainingConfiguration(ThingMLElement self) {
        return ThingMLHelpers.findContainingConfiguration(self);
    }

    public static State findContainingState(ThingMLElement self) {
        return ThingMLHelpers.findContainingState(self);
    }


    public static Region findContainingRegion(ThingMLElement self) {
        return ThingMLHelpers.findContainingRegion(self);
    }


    public static Handler findContainingHandler(ThingMLElement self) {
        return ThingMLHelpers.findContainingHandler(self);
    }


    public static String qname(ThingMLElement self, String separator) {
        if (separator == null) {
            separator = "::";
        }
        String result = null;
        ThingMLElement elem  = self;
        String name = null;
        while(elem != null) {
            name = elem.getName();
            if (name == null || name == "") name = elem.getClass().getName();
            if (result == null) result = name;
            else result = name + separator + result;
            if (elem.eContainer() != null && elem.eContainer() instanceof ThingMLElement)
                elem = (ThingMLElement)elem.eContainer();
            else elem = null;
        }
        return result;
    }

}
