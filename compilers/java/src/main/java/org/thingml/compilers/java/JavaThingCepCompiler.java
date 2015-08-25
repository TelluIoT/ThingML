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
package org.thingml.compilers.java;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.cepHelper.UnsupportedException;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.ThingCepCompiler;
import org.thingml.compilers.thing.ThingCepSourceDeclaration;
import org.thingml.compilers.thing.ThingCepViewCompiler;

import java.util.List;

/**
 * @author ludovic
 */
public class JavaThingCepCompiler extends ThingCepCompiler {
    public JavaThingCepCompiler(ThingCepViewCompiler cepViewCompiler, ThingCepSourceDeclaration sourceDeclaration) {
        super(cepViewCompiler, sourceDeclaration);
    }


    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        sourceDeclaration.generate(stream, stream.getInput(), builder, ctx);
        if(stream.getInput() instanceof SimpleSource) {
            SimpleSource source = (SimpleSource) stream.getInput();
            Message outPut = source.getMessage().getMessage();
            generateSubscription(stream, builder, ctx, outPut, source.qname("_") + "_observable");
        } else if(stream.getInput() instanceof SourceComposition) {
            Message outPut = stream.getOutput().getMessage();
            generateSubscription(stream, builder, ctx, outPut, stream.qname("_"));
        } else {
            throw UnsupportedException.sourceException(stream.getClass().getName());
        }
    }

   private static void generateSubscription(Stream stream, StringBuilder builder, Context context, Message outPut, String name) {
        String outPutName = outPut.getName();
        String messageType = context.firstToUpper(outPutName) + "MessageType." + context.firstToUpper(outPutName) + "Message";
        String outPutType = messageType;


        List<ViewSource> operators = stream.getInput().getOperators();
       boolean lastOpIsWindow = false;
       if(operators.size() > 0) {
           ViewSource lastOp = operators.get(operators.size() - 1);
           lastOpIsWindow =  lastOp instanceof WindowView;
//           lastOpIsWindow =  lastOp instanceof LengthWindow || lastOp instanceof TimeWindow;
       }
        if (lastOpIsWindow) {
            outPutType = "List<" + outPutType + ">";
        }

        builder.append(name + ".subscribe(new Action1<" + outPutType + ">() {\n")
                .append("@Override\n")
                .append("public void call(" + outPutType + " " + outPutName + ") {\n");
        if(lastOpIsWindow) {
            builder.append("int i;\n");
            for(Parameter parameter : outPut.getParameters()) {
               builder.append("int[] " + outPutName + parameter.getName() + " = new int[" + outPutName + ".size()];\n")
                       .append("i = 0;\n")
                       .append("for(" + messageType + " " + outPutName + "_msg : " + outPutName + ") {\n")
                       .append(outPutName + parameter.getName() + "[i] = " + outPutName + "_msg." + parameter.getName() + ";\n")
                       .append("i++;\n")
                       .append("}");
           }
        }
        context.getCompiler().getThingActionCompiler().generate(stream.getOutput(), builder, context);

       builder.append("}\n")
                .append("});\n");
    }


}
