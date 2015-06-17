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
package org.thingml.compilers.javascript;

import org.thingml.compilers.CepCompiler;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;

/**
 * Created by ffl on 25.11.14.
 */
public class EspruinoCompiler extends JavaScriptCompiler {

   public EspruinoCompiler() {
        super(new EspruinoThingActionCompiler(), new EspruinoThingApiCompiler(), new JSCfgMainGenerator(), new JSCfgBuildCompiler(), new JSThingImplCompiler(), new JSCepCompiler());
    }

    public EspruinoCompiler(ThingActionCompiler thingActionCompiler, ThingApiCompiler thingApiCompiler, CfgMainGenerator mainCompiler, CfgBuildCompiler cfgBuildCompiler, FSMBasedThingImplCompiler thingImplCompiler, JSCepCompiler cepCompiler) {
        super(thingActionCompiler, thingApiCompiler, mainCompiler, cfgBuildCompiler, thingImplCompiler, cepCompiler);
    }

    @Override
    public ThingMLCompiler clone() {
        return new EspruinoCompiler();
    }

    @Override
    public String getID() {
        return "espruino";
    }

    @Override
    public String getName() {
        return "Javascript for Espruino";
    }

    public String getDescription() {
        return "Generates Javascript code for the Espruino platform.";
    }
}
