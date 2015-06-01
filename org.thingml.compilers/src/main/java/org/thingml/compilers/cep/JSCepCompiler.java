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
package org.thingml.compilers.cep;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.thingml.compilers.Context;
import org.thingml.compilers.cep.helper.JSCepCompilerHelper;
import org.thingml.compilers.cep.helper.TransformEventRef;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ludovic
 */
public class JSCepCompiler extends CepCompiler {
    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
       if(stream instanceof SimpleStream) {
            generateStream((SimpleStream)stream,builder,ctx);
        } else if(stream instanceof MergedStream) {
            generateStream((MergedStream)stream,builder,ctx);
        } else if (stream instanceof JoinedStream) {
            generateStream((JoinedStream)stream,builder,ctx);
        } else {
            throw new UnsupportedOperationException("This stream (" + stream.getClass().getName() + ") is unknown... Please update your action compilers as a new action/expression might have been introduced in ThingML");
        }

    }


    public void generateStream(SimpleStream stream, StringBuilder builder, Context ctx) {
        JSCepCompilerHelper.generateBeginingStream(stream, builder, ctx, ThingMLHelpers.getEventName(stream, stream.getInputs().get(0)));

        List<StreamExpression> newParameters = new ArrayList<>();
        for (StreamExpression se : stream.getSelection()) {

            builder.append("\t\tvar " + se.getName() + " = ");
            ctx.getCompiler().getActionCompiler().generate(TransformEventRef.instance.copyExpression(se.getExpression()), builder, ctx);
            builder.append(";\n");
            newParameters.add(JSCepCompilerHelper.generateStreamExpression(se.getName()));
        }
        builder.append(";\n");
        builder.append("\t\t\t");

        JSCepCompilerHelper.generateOutPut(stream, builder, ctx, newParameters);

        builder.append("\t});\n");
    }



    public void generateStream(MergedStream stream, StringBuilder builder, Context ctx) {
        JSCepCompilerHelper.generateBeginingStream(stream, builder, ctx, ThingMLHelpers.getEventName(stream, null));

        if(stream.getInputs().get(0).getMessage().getParameters().size() == 0) {
            ctx.getCompiler().getActionCompiler().generate(stream.getOutput(), builder, ctx);
        } else {
            List<StreamExpression> newParamters = new ArrayList<>();
            for(ReceiveMessage rm : stream.getInputs()) {
                Parameter first = rm.getMessage().getParameters().get(0);
                builder.append("\t\tif(!(typeof " + rm.getMessage().getName() + "J." + first.getName() + "=== 'undefined')) { \n");
                builder.append("\t\t\t");
                for (Parameter p : rm.getMessage().getParameters()) {
                    newParamters.add(JSCepCompilerHelper.generateStreamExpression(/*rm.getMessage().getName() + "J." + p.getName()*/JSCepCompilerHelper.generateJsonAccessParam(rm,p)));
                }
                JSCepCompilerHelper.generateOutPut(stream, builder, ctx, newParamters);
                builder.append("\t\t}\n");
                newParamters.clear();
            }
        }

        builder.append("\t});\n");
    }

    public void generateStream(JoinedStream stream, StringBuilder builder, Context ctx) {
        builder.append("\n");
        builder.append("function wait1() { return Rx.Observable.timer(50); }\n");

        for (ReceiveMessage rm : stream.getInputs()) {
            builder.append("var " + stream.qname("_") + "_" + rm.getMessage().getName() + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + /*stream.qname("_") + "_" + rm.getPort().getName() + "_" + rm.getMessage().getName()*/ThingMLHelpers.getEventName(stream, rm) + "');\n");
        }


        String nameSTream1 = stream.qname("_") + "_" + stream.getInputs().get(1).getMessage().getName(), //currently : join is between two streams
                nameSTream2 = stream.qname("_") + "_" + stream.getInputs().get(0).getMessage().getName();

        builder.append("var " + stream.qname("_") + " = " + nameSTream1 + ".join(" + nameSTream2 + ",wait1,wait1,\n" +
                "\tfunction(m1,m2) {\n" +
                "\t\tvar " + stream.getInputs().get(1).getMessage().getName() + "J = JSON.parse(m1);\n" + //fixme
                "\t\tvar "+  stream.getInputs().get(0).getMessage().getName() + "J = JSON.parse(m2);\n"); //fixme

        List<StreamExpression> newParameters = new ArrayList<>();
        String returnString = "'{ ";
        int i = 0;
        for (StreamExpression se : stream.getSelection()) {

            builder.append("\t\tvar " + se.getName() + " = ");
            ctx.getCompiler().getActionCompiler().generate(TransformEventRef.instance.copyExpression(se.getExpression()), builder, ctx);
            builder.append(";\n");
            if(i>0) {
                returnString += ", ";
            }
            returnString += "\"" + se.getName() + "\": ' + " + se.getName() + " + '";

            newParameters.add(JSCepCompilerHelper.generateStreamExpression("json." + se.getName()));

            i++;
        }

        returnString += "}';";
        builder.append("\t\treturn " + returnString + "\n");
        builder.append("\t}).subscribe(\n\t" +
                "\t\tfunction(x) {\n" +
                "\t\t\tvar json = JSON.parse(x);\n");
        builder.append("\t\t\t");

        JSCepCompilerHelper.generateOutPut(stream, builder, ctx, newParameters);

        builder.append("\t});\n");
    }


}
