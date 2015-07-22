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
package org.thingml.compilers.java.cepHelper;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.java.JavaThingActionCompiler;

import java.util.Iterator;

/**
 * @author ludovic
 */
public class JavaCepCompilerHelper {


    public static void generateJoinOutput(JoinedStream stream, StringBuilder builder, Context ctx) {
        StreamOutput newOutput = ThingmlFactory.eINSTANCE.createStreamOutput();
        newOutput.setMessage(stream.getOutput().getMessage());
        newOutput.setPort(stream.getOutput().getPort());

        ReceiveMessage rm = ThingmlFactory.eINSTANCE.createReceiveMessage();
        rm.setMessage(stream.getOutput().getMessage());
        rm.setPort(stream.getOutput().getPort());

        for(Parameter p : stream.getOutput().getMessage().getParameters()) {
            EventReference eRef = ThingmlFactory.eINSTANCE.createEventReference();
            eRef.setMsgRef(rm);
            eRef.setParamRef(p);
            StreamExpression se = ThingmlFactory.eINSTANCE.createStreamExpression();
            se.setExpression(eRef);
            newOutput.getParameters().add(se);
        }

        ctx.getCompiler().getThingActionCompiler().generate(newOutput,builder,ctx);
    }

    public static void generateWaitFunction(StringBuilder builder) {
        builder.append("\n");
        builder.append("Func1 wait = new Func1() {\n" +
                "@Override\n" +
                "public Object call(Object o) {\n" +
                "return rx.Observable.timer(50, TimeUnit.MILLISECONDS);\n" +
                "}\n" +
                "};\n");
    }

    public static void generateJoinSources(JoinedStream stream, StringBuilder builder) {
        for(ReceiveMessage source : stream.getInputs()) {
            String name = stream.qname("_") + "_" + source.getMessage().getName();
            builder.append("PublishSubject " + name + " = " +
                    "PublishSubject.create();\n")
                    .append("cepDispatcher.addSubs(new NullEvent(" + source.getMessage().getName() + "Type," +
                            source.getPort().getName() + "_port),"+ name +");\n");

        }
    }

    public static void generateJoinObservable(JoinedStream stream, StringBuilder builder, Context ctx) {
        String message1Name = stream.getInputs().get(1).getMessage().getName(),
                message2Name = stream.getInputs().get(0).getMessage().getName();
        String nameStream1 = stream.qname("_") + "_" + message1Name, //currently : join is between two streams
                nameStream2 = stream.qname("_") + "_" + message2Name;
        String eventMessage1 = ctx.firstToUpper(message1Name) + "MessageType." + ctx.firstToUpper(message1Name) + "Message";
        String eventMessage2 = ctx.firstToUpper(message2Name) + "MessageType." + ctx.firstToUpper(message2Name) + "Message";

        Message outPut = stream.getOutput().getMessage();
        String outPutName = outPut.getName();
        String outPutType = ctx.firstToUpper(outPutName) + "MessageType." + ctx.firstToUpper(outPutName) + "Message";

        builder.append(nameStream1 + ".join(" + nameStream2 + ",wait,wait,\n")
                .append("new Func2<" + eventMessage1 + ", " + eventMessage2 + ", " + outPutType +">(){\n")
                .append("@Override\n")
                .append("public " + outPutType + " call(" + eventMessage1 + " " +message1Name + ", " + eventMessage2 + " " + message2Name + ") {\n")
                .append("return (" + outPutType + ") " + outPutName + "Type.instantiate("+ stream.getOutput().getPort().getName() + "_port");

        Iterator<StreamExpression> itStreamExpression = stream.getSelection().iterator();
        Iterator<Parameter> itParameter = stream.getOutput().getMessage().getParameters().iterator();

        while(itStreamExpression.hasNext() && itParameter.hasNext()) {
            Parameter fp = itParameter.next();
            StreamExpression se = itStreamExpression.next();
            builder.append(", ");
            ((JavaThingActionCompiler) ctx.getCompiler().getThingActionCompiler()).cast(fp.getType(),
                    fp.getCardinality() != null, se.getExpression(), builder, ctx);
        }

        builder.append(");\n");

        builder.append("}\n")
                .append("}\n")
                .append(").subscribe(new Action1<" + outPutType + ">() {\n")
                .append("@Override\n")
                .append("public void call(" + outPutType + " " + outPutName + ") {\n");

        generateJoinOutput(stream, builder, ctx);

        builder.append("}\n")
                .append("});\n");
    }

}
