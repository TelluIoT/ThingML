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
import org.thingml.compilers.thing.ThingActionCompiler;
import org.thingml.compilers.thing.ThingApiCompiler;
import org.thingml.compilers.thing.ThingCepCompiler;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;

/**
 * Created by bmori on 09.01.2017.
 */
public class JSMTCompiler extends JSCompiler {

    public JSMTCompiler() {
        multiThreaded = true;
    }

    public JSMTCompiler(ThingActionCompiler thingActionCompiler, ThingApiCompiler thingApiCompiler, CfgMainGenerator mainCompiler, CfgBuildCompiler cfgBuildCompiler, FSMBasedThingImplCompiler thingImplCompiler, ThingCepCompiler cepCompiler) {
        super(thingActionCompiler, thingApiCompiler, mainCompiler, cfgBuildCompiler, thingImplCompiler, cepCompiler);
        multiThreaded = true;
    }

    @Override
    public ThingMLCompiler clone() {
        return new JSMTCompiler();
    }

    @Override
    public String getID() {
        return "nodejsMT";
    }

    @Override
    public String getName() {
        return "Multi-process Javascript for NodeJS";
    }

    public String getDescription() {
        return "Generates Multi-Process Javascript code (one nodejs process per instance) for the NodeJS platform.";
    }
}
