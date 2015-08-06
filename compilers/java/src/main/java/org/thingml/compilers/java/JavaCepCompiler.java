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
import org.thingml.compilers.CepCompiler;
import org.thingml.compilers.Context;
import org.thingml.compilers.java.cepHelper.JavaCepViewCompiler;
import org.thingml.compilers.java.cepHelper.JavaGenerateSourceDeclaration;

/**
 * @author ludovic
 */
public class JavaCepCompiler extends CepCompiler {
    private JavaCepViewCompiler javaCepViewCompiler = new JavaCepViewCompiler();

    public JavaCepViewCompiler getJavaCepViewCompiler() {
        return javaCepViewCompiler;
    }

    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        JavaGenerateSourceDeclaration.generate(stream, stream.getInput(), builder, ctx);
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
        String outPutType = context.firstToUpper(outPutName) + "MessageType." + context.firstToUpper(outPutName) + "Message";

        builder.append(name + ".subscribe(new Action1<" + outPutType + ">() {\n")
                .append("@Override\n")
                .append("public void call(" + outPutType + " " + outPutName + ") {\n");

        context.getCompiler().getThingActionCompiler().generate(stream.getOutput(), builder, context);

        builder.append("}\n")
                .append("});\n");
    }


}
