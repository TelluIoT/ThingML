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
package org.sintef.thingml;

import jsyntaxpane.TokenType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bmori on 26.05.2015.
 */
public class ThingMLStyle {

    private static ThingMLStyle ourInstance = new ThingMLStyle();

    public static ThingMLStyle getInstance() {
        return ourInstance;
    }

    private ThingMLStyle() {
    }

    public static Map<String, TokenType> styles = new HashMap<String, TokenType>();

    public static org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyleInformationProvider infoProvider = new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyleInformationProvider();
}