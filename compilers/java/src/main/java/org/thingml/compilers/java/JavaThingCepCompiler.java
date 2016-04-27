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

    //TODO: remove output param as we do not really it (and method is called with wrong args in case of Join/Merge....)
   private void generateSubscription(Stream stream, StringBuilder builder, Context context, Message outPut, String name) {
       String outPutName = stream.getInput().getName();
       String messageType = "";
       if (stream.getInput() instanceof SourceComposition) {
           outPut = ((SourceComposition) stream.getInput()).getResultMessage();
           messageType = context.firstToUpper( ((SourceComposition)stream.getInput()).getResultMessage().getName()) + "MessageType." + context.firstToUpper(((SourceComposition)stream.getInput()).getResultMessage().getName()) + "Message";
       } else if (stream.getInput() instanceof SimpleSource) {
           outPut = ((SimpleSource)stream.getInput()).getMessage().getMessage();
           messageType = context.firstToUpper(outPut.getName()) + "MessageType." + context.firstToUpper(outPut.getName()) + "Message";
       }

        List<ViewSource> operators = stream.getInput().getOperators();
       boolean hasWindow = false;
       for(ViewSource vs : operators) {
           hasWindow =  (vs instanceof TimeWindow) || (vs instanceof LengthWindow);
           if (hasWindow)
               break;
       }
        if (hasWindow) {
            messageType = "List<" + messageType + ">";
        }


        builder.append("this.sub_" + name.replace("_observable", "") + " = ");
        builder.append("new Action1<" + messageType + ">() {\n")
               .append("@Override\n")
               .append("public void call(" + messageType + " " + outPutName + ") {\n");

       if(hasWindow) {
           builder.append("int i;\n");
           for(Parameter parameter : outPut.getParameters()) {
               builder.append(JavaHelper.getJavaType(parameter.getType(), false, context) + "[] " + outPutName + parameter.getName() + " = new " + JavaHelper.getJavaType(parameter.getType(), false, context) + "[" + outPutName + ".size()];\n")
                       .append("i = 0;\n")
                       .append("for(" + messageType.replace("List<","").replace(">", "") + " " + outPutName + "_msg : " + outPutName + ") {\n")//FIXME: replace stuff is a dirty hack!
                       .append(outPutName + parameter.getName() + "[i] = " + outPutName + "_msg." + parameter.getName() + ";\n")
                       .append("i++;\n")
                       .append("}\n");
           }
       }

       for(LocalVariable lv : stream.getSelection()) {
           context.getCompiler().getThingActionCompiler().generate(lv, builder, context);
       }

       context.getCompiler().getThingActionCompiler().generate(stream.getOutput(), builder, context);

       builder.append("}};\n");


        if (!stream.isDynamic()) {
            builder.append("start" + stream.getInput().qname("_") + "();\n");
        }
    }


}
