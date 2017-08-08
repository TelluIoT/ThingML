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

import org.thingml.compilers.Context;
import org.thingml.xtext.thingML.ErrorAction;
import org.thingml.xtext.thingML.PrintAction;

/**
 * Created by jakobho on 28.03.2017.
 */
public class NodeJSThingActionCompiler extends JSThingActionCompiler {

    @Override
    public void generate(ErrorAction action, StringBuilder builder, Context ctx) {
        builder.append("process.stderr.write(''+");
        generate(action.getMsg(), builder, ctx);
        builder.append(");\n");
    }

    @Override
    public void generate(PrintAction action, StringBuilder builder, Context ctx) {
        builder.append("process.stdout.write(''+");
        generate(action.getMsg(), builder, ctx);
        builder.append(");\n");
    }
}
