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

import org.sintef.thingml.Expression;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.SendAction;
import org.thingml.compilers.Context;

/**
 * Created by bmori on 01.12.2014.
 */
public class EspruinoThingActionCompiler extends JSThingActionCompiler {

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
        builder.append("send" + ctx.firstToUpper(action.getMessage().getName()) + "On" + ctx.firstToUpper(action.getPort().getName()) + "(");
        int i = 0;
        for (Expression p : action.getParameters()) {
            int j = 0;
            for (Parameter fp : action.getMessage().getParameters()) {
                if (i == j) {//parameter p corresponds to formal parameter fp
                    if (i > 0)
                        builder.append(", ");
                    generate(p, builder, ctx);
                    break;
                }
                j++;
            }
            i++;
        }
        builder.append(");\n");
    }
}
