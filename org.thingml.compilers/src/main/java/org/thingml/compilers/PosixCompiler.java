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
package org.thingml.compilers;

import org.sintef.thingml.Configuration;
import org.thingml.cgenerator.CGenerator;
import org.thingml.compilers.actions.ActionCompiler;
import org.thingml.compilers.api.ApiCompiler;
import org.thingml.compilers.behavior.BehaviorCompiler;
import org.thingml.compilers.build.BuildCompiler;
import org.thingml.compilers.main.MainGenerator;

/**
 * Created by ffl on 25.11.14.
 */
public class PosixCompiler extends OpaqueThingMLCompiler {

    public PosixCompiler() {
        super(new ActionCompiler(), new ApiCompiler(), new MainGenerator(), new BuildCompiler(), new BehaviorCompiler());
    }

    @Override
    public ThingMLCompiler clone() {
        return new PosixCompiler();
    }

    @Override
    public String getPlatform() {
        return "posix";
    }

    @Override
    public String getName() {
        return "C/C++ for Linux / Posix";
    }

    public String getDescription() {
        return "Generates C/C++ code for Linux or other Posix runtime environments (GCC compiler).";
    }

    @Override
    public void do_call_compiler(Configuration cfg, String... options) {
        CGenerator.opaqueCompileToLinux(cfg, this);
    }
}
