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
package org.thingml.compilers.cep.helper;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;

import java.util.List;

/**
 * @author ludovic
 */
public class JSCepCompilerHelper {

    public static StreamExpression generateStreamExpression(String expression) {
        ExternExpression externExpression = ThingmlFactory.eINSTANCE.createExternExpression();
        externExpression.setExpression(expression);
        StreamExpression streamExpression = ThingmlFactory.eINSTANCE.createStreamExpression();
        streamExpression.setExpression(externExpression);
        return streamExpression;
    }

    public static void generateOutPut(Stream stream, StringBuilder builder, Context ctx, List<StreamExpression> newParameters) {
        StreamOutput streamOutput = ThingmlFactory.eINSTANCE.createStreamOutput();
        streamOutput.setMessage(stream.getOutput().getMessage());
        streamOutput.setPort(stream.getOutput().getPort());
        streamOutput.getParameters().addAll(newParameters);
        ctx.getCompiler().getActionCompiler().generate(streamOutput, builder, ctx);
    }

    public static void generateBeginingStream(Stream stream, StringBuilder builder, Context ctx, String eventName) {
        builder.append("var " + stream.qname("_") + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + eventName + "')");
        builder.append(".subscribe(\n\t" +
                "function(x) {\n");

        for(ReceiveMessage rm : stream.getInputs()) {
            builder.append("\t\tvar " + rm.getMessage().getName() + "J = JSON.parse(x);\n");
        }

        /** Useful for debugging **/
        builder.append("\t\tconsole.log(\"Hack!! \"");
        for(ReceiveMessage rm : stream.getInputs()) {
            for(Parameter p : rm.getMessage().getParameters()) {
                builder.append(" + \"" + p.getName() + "= \" + " + rm.getMessage().getName() + "J." + p.getName() + "+ \"; \"");
            }
        }
        builder.append(");\n");
        /** END **/
    }

    public static String generateJsonAccessParam(ReceiveMessage rm, Parameter p) {
        return rm.getMessage().getName() + "J." + p.getName();
    }
}
