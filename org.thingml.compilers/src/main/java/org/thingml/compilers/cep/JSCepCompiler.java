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
import org.thingml.compilers.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ludovic
 */
public class JSCepCompiler extends CepCompiler {
    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        /*if (stream.getInputs().size() == 1) {
            builder.append("var " + stream.qname("_") + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + stream.qname("_") + "_" + stream.getInputs().get(0).getMessage().getName() + "')");
                builder.append(".subscribe(\n\t" +
                        "function(x) {\n\t\t" +
                        "var json = JSON.parse(x);\n\t\t" +
                        "console.log(\"Hack!! \"");

                for (Parameter p : stream.getInputs().get(0).getMessage().getParameters()) {
                    builder.append(" + \"" + p.getName() + "= \" + json." + p.getName() + "+ \"; \"");
                }

                builder.append(");\n");
                ctx.getCompiler().getActionCompiler().generate(stream.getOutput(), builder, ctx);
            builder.append("});\n");
        } else if (stream.getInputs().size() > 1 && stream.getOutput().getParameters().size() == 0) {
            builder.append("var " + stream.qname("_") + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + stream.qname("_") + "')");
                builder.append(".subscribe(\n\t" +
                        "function(x) {\n\t\t" +
                        "var json = JSON.parse(x);\n\t\t" +
                        "console.log(\"Hack!! \"");

                for(ReceiveMessage rm : stream.getInputs()) {
                    for (Parameter p : rm.getMessage().getParameters()){
                        builder.append(" + \"" + p.getName() + "= \" + json." + p.getName() + "+ \"; \"");
                    }
                }

                builder.append(");\n");
                ctx.getCompiler().getActionCompiler().generate(stream.getOutput(), builder, ctx);

            builder.append("});\n");
        } else if (stream.getInputs().size() == 2 && stream.getOutput().getParameters().size() > 0) {
            builder.append("function wait1() { return Rx.Observable.timer(50); }\n");
            for (ReceiveMessage rm : stream.getInputs()) {
                builder.append("var " + stream.qname("_") + "_" + rm.getMessage().getName() + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + stream.qname("_") + "_" + rm.getMessage().getName() + "');\n");
            }
            String nameSTream1 = stream.qname("_") + "_" + stream.getInputs().get(1).getMessage().getName(),
                    nameSTream2 = stream.qname("_") + "_" + stream.getInputs().get(0).getMessage().getName();
            builder.append("var " + stream.qname("_") + " = " + nameSTream1 + ".join(" + nameSTream2 + ",wait1,wait1,\n" +
                    "\tfunction(m1,m2) {\n" + //fixme
                    "\t\tvar m1J = JSON.parse(m1);\n" + //fixme
                    "\t\tvar m2J = JSON.parse(m2);\n"); //fixme

            List<StreamExpression> newParameters = new ArrayList<>();
            String returnString = "'{ ";
            int i = 0;
            for (StreamExpression se : stream.getSelection()) {

                builder.append("\t\tvar " + se.getName() + " = ");
                ctx.getCompiler().getActionCompiler().generate(TransformExpression.copyExpression(se.getExpression()), builder, ctx);
                builder.append(";\n");
                if (i == 0)
                    returnString += "\"" + se.getName() + "\": ' + " + se.getName() + " + '";
                else
                    returnString += ", \"" + se.getName() + "\" : ' + " + se.getName() + " + '";

                ExternExpression externExpression = ThingmlFactory.eINSTANCE.createExternExpression();
                externExpression.setExpression("json." + se.getName());
                StreamExpression streamExpression = ThingmlFactory.eINSTANCE.createStreamExpression();
                streamExpression.setExpression(externExpression);
                streamExpression.setName(se.getName());
                newParameters.add(streamExpression);

                i++;
            }

            returnString += "}';";
            builder.append("\t\treturn " + returnString + "\n");
            builder.append("\t}).subscribe(\n\t" +
                    "\t\tfunction(x) {\n" +
                    "\t\t\tvar json = JSON.parse(x);\n");
            builder.append("\t\t\t");

            stream.getOutput().getParameters().clear();
            stream.getOutput().getParameters().addAll(newParameters);
            ctx.getCompiler().getActionCompiler().generate(stream.getOutput(), builder, ctx);


            builder.append("\t});\n");
        } else {
            throw new UnsupportedOperationException("Incorrect number of inputs or parameters for stream " + stream.getName());
        }*/

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
        ReceiveMessage receiveMessage = stream.getInputs().get(0);
        builder.append("var " + stream.qname("_") + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + stream.qname("_") + "_" + receiveMessage.getPort().getName() + "_" + receiveMessage.getMessage().getName() + "')");
        builder.append(".subscribe(\n" +
                "\tfunction(x) {\n" +
                "\t\tvar "+ receiveMessage.getMessage().getName() + "J = JSON.parse(x);\n" +
                "\t\tconsole.log(\"Hack!! \""); //fixme delete for final version (useful for debugging)

        for (Parameter p : stream.getInputs().get(0).getMessage().getParameters()) { //fixme delete for final version (useful for debugging)
            builder.append(" + \"" + p.getName() + "= \" + " + receiveMessage.getMessage().getName() + "J." + p.getName() + "+ \"; \"");
        }
        builder.append(");\n");//fixme delete for final version (useful for debugging)



        List<StreamExpression> newParameters = new ArrayList<>();
        for (StreamExpression se : stream.getSelection()) {

            builder.append("\t\tvar " + se.getName() + " = ");
            ctx.getCompiler().getActionCompiler().generate(TransformExpression.copyExpression(se.getExpression()), builder, ctx);

            ExternExpression externExpression = ThingmlFactory.eINSTANCE.createExternExpression();
            externExpression.setExpression(se.getName());
            StreamExpression streamExpression = ThingmlFactory.eINSTANCE.createStreamExpression();
            streamExpression.setExpression(externExpression);
            streamExpression.setName(se.getName());
            newParameters.add(streamExpression);
        }

        builder.append(";\n");
        builder.append("\t\t\t");
        stream.getOutput().getParameters().clear();
        stream.getOutput().getParameters().addAll(newParameters);
        ctx.getCompiler().getActionCompiler().generate(stream.getOutput(), builder, ctx);


        builder.append("\t});\n");
    }

    public void generateStream(MergedStream stream, StringBuilder builder, Context ctx) {
        builder.append("var " + stream.qname("_") + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + stream.qname("_") + "')");
        builder.append(".subscribe(\n\t" +
                "function(x) {\n");

        for(ReceiveMessage rm : stream.getInputs()) {
            builder.append("\t\tvar " + rm.getMessage().getName() + "J = JSON.parse(x);\n");
        }
        builder.append("\t\tconsole.log(\"Hack!! \""); //fixme delete for final version (useful for debugging)

        for(ReceiveMessage rm : stream.getInputs()) { //fixme delete for final version (useful for debugging)
            for(Parameter p : rm.getMessage().getParameters()) {
                builder.append(" + \"" + p.getName() + "= \" + " + rm.getMessage().getName() + "J." + p.getName() + "+ \"; \"");
            }
        }
        builder.append(");\n"); //fixme delete for final version (useful for debugging)

        for(ReceiveMessage rm : stream.getInputs()) {
            if(rm.getMessage().getParameters().size() > 0) {
                Parameter first = rm.getMessage().getParameters().get(0);
                builder.append("\t\tif(!(typeof " + rm.getMessage().getName() + "J." + first.getName() + "=== 'undefined')) { \n");
                builder.append("\t\t\t");
                for (Parameter p : rm.getMessage().getParameters()) {
                    ExternExpression externExpression = ThingmlFactory.eINSTANCE.createExternExpression();
                    externExpression.setExpression(rm.getMessage().getName() + "J." + p.getName());
                    StreamExpression streamExpression = ThingmlFactory.eINSTANCE.createStreamExpression();
                    streamExpression.setExpression(externExpression);
                    stream.getOutput().getParameters().add(streamExpression);
                }
                ctx.getCompiler().getActionCompiler().generate(stream.getOutput(), builder, ctx); //fixme copy
                stream.getOutput().getParameters().clear();
                builder.append("\t\t}\n");
            }
            else {
                ctx.getCompiler().getActionCompiler().generate(stream.getOutput(), builder, ctx);
                break;
            }
        }

        builder.append("\t});\n");
    }

    public void generateStream(JoinedStream stream, StringBuilder builder, Context ctx) {
        builder.append("\n");
        builder.append("function wait1() { return Rx.Observable.timer(50); }\n");
        for (ReceiveMessage rm : stream.getInputs()) {
            builder.append("var " + stream.qname("_") + "_" + rm.getMessage().getName() + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + stream.qname("_") + "_" + rm.getPort().getName() + "_" + rm.getMessage().getName() + "');\n");
        }


        String nameSTream1 = stream.qname("_") + "_" + stream.getInputs().get(1).getMessage().getName(), //currently : join is between two streams
                nameSTream2 = stream.qname("_") + "_" + stream.getInputs().get(0).getMessage().getName();

        builder.append("var " + stream.qname("_") + " = " + nameSTream1 + ".join(" + nameSTream2 + ",wait1,wait1,\n" +
                "\tfunction(m1,m2) {\n" + //fixme
                "\t\tvar m1J = JSON.parse(m1);\n" + //fixme
                "\t\tvar m2J = JSON.parse(m2);\n"); //fixme

        List<StreamExpression> newParameters = new ArrayList<>();
        String returnString = "'{ ";
        int i = 0;
        for (StreamExpression se : stream.getSelection()) {

            builder.append("\t\tvar " + se.getName() + " = ");
            ctx.getCompiler().getActionCompiler().generate(TransformExpression.copyExpression(se.getExpression()), builder, ctx);
            builder.append(";\n");
            if (i == 0)
                returnString += "\"" + se.getName() + "\": ' + " + se.getName() + " + '";
            else
                returnString += ", \"" + se.getName() + "\" : ' + " + se.getName() + " + '";

            ExternExpression externExpression = ThingmlFactory.eINSTANCE.createExternExpression();
            externExpression.setExpression("json." + se.getName());
            StreamExpression streamExpression = ThingmlFactory.eINSTANCE.createStreamExpression();
            streamExpression.setExpression(externExpression);
            streamExpression.setName(se.getName());
            newParameters.add(streamExpression);

            i++;
        }

        returnString += "}';";
        builder.append("\t\treturn " + returnString + "\n");
        builder.append("\t}).subscribe(\n\t" +
                "\t\tfunction(x) {\n" +
                "\t\t\tvar json = JSON.parse(x);\n");
        builder.append("\t\t\t");

        stream.getOutput().getParameters().clear();
        stream.getOutput().getParameters().addAll(newParameters);
        ctx.getCompiler().getActionCompiler().generate(stream.getOutput(), builder, ctx);


        builder.append("\t});\n");
    }


}
