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
import org.thingml.compilers.CepCompiler;
import org.thingml.compilers.Context;

/**
 * @author ludovic
 */
public class JavaCepCompiler extends CepCompiler {
    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        if(stream instanceof SimpleStream) {
            generateStream((SimpleStream)stream,builder,ctx);
        } else {
            throw new UnsupportedOperationException("This stream (" + stream.getClass().getName() + ") is unknown... Please update your cep compilers as a new stream might have been introduced in ThingML");
        }
    }

    public void generateStream(SimpleStream stream, StringBuilder builder, Context ctx) {
        builder.append("PublishSubject<Event> subject = PublishSubject.create();");
        builder.append("subject.subscribe(new Action1<Event>() {\n" +
                "@Override\n" +
                "public void call(Event event) {\n");

        Message message = stream.getInputs().get(0).getMessage();
        Port port = stream.getInputs().get(0).getPort();

        if(message.getParameters().size() > 0) {
            builder.append("final " + ctx.firstToUpper(message.getName()) + "MessageType." + ctx.firstToUpper(message.getName()) + "Message ce = (" + ctx.firstToUpper(message.getName()) + "MessageType." + ctx.firstToUpper(message.getName()) + "Message) event;\n");
        }
        ctx.getCompiler().getThingActionCompiler().generate(stream.getOutput(),builder,ctx);
        builder.append("}\n" +
                "});");

        builder.append("cepDispatcher.addSubs(new NullEvent("+ message.getName() + "Type," + port.getName() + "_port),subject);");
    }

}
