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
package org.thingml.compilers.javascript.cepHelper;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;

import java.util.ArrayList;
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
        ctx.getCompiler().getThingActionCompiler().generate(streamOutput, builder, ctx);
    }

   public static void generateSimpleMergedStream(Stream stream, StringBuilder builder, Context ctx, String eventName, String nameParam, JSActionCompilerCepAlternative actionCompiler) {
        builder.append("var " + stream.qname("_") + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + eventName + "')");
        builder.append(".subscribe(\n\t" +
                "function(" + nameParam + ") {\n");

       List<StreamExpression> newParameters = new ArrayList<>();
       for (StreamExpression se : stream.getSelection()) {
           builder.append("\t\tvar " + se.getName() + " = ");
           actionCompiler.generate(se.getExpression(),builder,ctx);
           builder.append(";\n");
           newParameters.add(JSCepCompilerHelper.generateStreamExpression(se.getName()));
       }
       builder.append(";\n");
       builder.append("\t\t\t");

       JSCepCompilerHelper.generateOutPut(stream, builder, ctx, newParameters);

       builder.append("\t});\n");
    }
}
