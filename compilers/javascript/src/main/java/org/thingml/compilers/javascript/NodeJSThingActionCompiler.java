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
import org.thingml.compilers.thing.common.CommonThingActionCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.ConfigPropertyAssign;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.EqualsExpression;
import org.thingml.xtext.thingML.ErrorAction;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.FunctionCallStatement;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PrintAction;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.StartSession;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.VariableAssignment;

/**
 * Created by jakobho on 28.03.2017.
 */
public class NodeJSThingActionCompiler extends JSThingActionCompiler {

    @Override
    public void traceVariablePost(VariableAssignment action, StringBuilder builder, Context ctx) {
        if (action.getProperty().eContainer() instanceof Thing) {//we can only listen to properties of a Thing, not all local variables, etc
            builder.append("//notify listeners of that attribute\n");
            if(((NodeJSCompiler)ctx.getCompiler()).multiThreaded) {
                builder.append("process.send({lc:'updated', property:'" + action.getProperty().getName() + "', value: this." + ctx.getVariableName(action.getProperty()) + "});\n");
            } else {
                super.traceVariablePost(action, builder, ctx);
            }
        }
    }

    @Override
    public void generate(SendAction action, StringBuilder builder, Context ctx) {
        if(((NodeJSCompiler)ctx.getCompiler()).multiThreaded) {
            builder.append("process.send({_port: '" + action.getPort().getName() + "', _msg: '" + action.getMessage().getName() + "'");
            int i = 0;
            for(Parameter param : action.getMessage().getParameters()) {
                builder.append(", " + param.getName() + ": ");
                generate(action.getParameters().get(i), builder, ctx);
                i++;
            }
            builder.append("});\n");
        } else {
            super.generate(action, builder, ctx);
        }
    }

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
