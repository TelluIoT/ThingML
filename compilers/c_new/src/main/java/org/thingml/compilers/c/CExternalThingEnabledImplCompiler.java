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
package org.thingml.compilers.c;

import org.thingml.compilers.Context;
import org.thingml.compilers.spi.ExternalThingPlugin;
import org.thingml.compilers.thing.ThingImplCompiler;
import org.thingml.xtext.thingML.Thing;

/**
 * Created by vassik on 01.11.16.
 */
public class CExternalThingEnabledImplCompiler extends ThingImplCompiler {

    private CThingImplCompiler impl_compiler;

    public CExternalThingEnabledImplCompiler(CThingImplCompiler _impl_compiler) {
        impl_compiler = _impl_compiler;
    }

    @Override
    public void generateImplementation(Thing thing, Context ctx) {
        if(ctx.getCompiler().isExternalThing(thing)) {
            ExternalThingPlugin plugin = ctx.getCompiler().getExternalThingPlugin(thing);
            if(plugin != null) {
                ThingImplCompiler impl_thing_compiler = plugin.getThingImplCompiler();
                impl_thing_compiler.generateImplementation(thing, ctx);
                return;
            }
        }

        impl_compiler.generateImplementation(thing, ctx);
    }
}
