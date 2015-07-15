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
package org.thingml.compilers.thing;

import org.sintef.thingml.Thing;
import org.thingml.compilers.Context;


public class ThingImplCompiler {

    public void generateImplementation(Thing thing, Context ctx) {
        throw (new UnsupportedOperationException("Implementations are platform-specific. Cannot generateMainAndInit API for Thing " + thing.getName()));
    }

}
