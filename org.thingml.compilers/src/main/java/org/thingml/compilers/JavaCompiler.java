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
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.thingml.compilers.actions.ActionCompiler;
import org.thingml.compilers.actions.JavaActionCompiler;
import org.thingml.compilers.api.JavaApiCompiler;
import org.thingml.compilers.build.JavaBuildCompiler;
import org.thingml.compilers.main.JavaMainGenerator;
import org.thingml.javagenerator.JavaGenerator;

import java.util.ArrayList;

/**
 * Created by ffl on 25.11.14.
 */
public class JavaCompiler extends OpaqueThingMLCompiler {

    public JavaCompiler() {
        super(new JavaActionCompiler(), new JavaApiCompiler(), new JavaMainGenerator(), new JavaBuildCompiler());
    }

    @Override
    public ThingMLCompiler clone() {
        return new JavaCompiler();
    }

    @Override
    public String getPlatform() {
        return "java";
    }

    @Override
    public String getName() {
        return "Plain Java";
    }

    public String getDescription() {
        return "Generates plain Java code.";
    }

    @Override
    public void do_call_compiler(Configuration cfg) {
        Context ctx = new Context(this, "match", "requires", "type", "abstract", "do", "finally", "import", "object", "throw", "case", "else", "for", "lazy", "override", "return", "trait", "catch", "extends", "forSome", "match", "package", "sealed", "try", "while", "class", "false", "if", "new", "private", "super", "true", "final", "null", "protected", "this", "_", ":", "=", "=>", "<-", "<:", "<%", ">:", "#", "@");
        JavaGenerator.compileAndRun(cfg, ThingMLHelpers.findContainingModel(cfg), false, getOutputDirectory(), ctx);
    }
}
