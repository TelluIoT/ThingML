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
package org.thingml.compilers.javascript;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.compilers.configuration.CfgMainGenerator;
import org.thingml.compilers.javascript.cepHelper.JSCepViewCompiler;
import org.thingml.compilers.javascript.cepHelper.JSGenerateSourceDeclaration;
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;

/**
 * Created by ffl on 25.11.14.
 */
public class EspruinoCompiler extends JSCompiler {

    public EspruinoCompiler() {
        super(new EspruinoThingActionCompiler(), new EspruinoThingApiCompiler(), new JSCfgMainGenerator(),
                new JSCfgBuildCompiler(), new JSThingImplCompiler(),
                new JSThingCepCompiler(new JSCepViewCompiler(), new JSGenerateSourceDeclaration()));
    }

    public EspruinoCompiler(ThingActionCompiler thingActionCompiler, ThingApiCompiler thingApiCompiler, CfgMainGenerator mainCompiler, CfgBuildCompiler cfgBuildCompiler, FSMBasedThingImplCompiler thingImplCompiler, JSThingCepCompiler cepCompiler) {
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
