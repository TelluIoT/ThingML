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

import org.sintef.thingml.Parameter;
import org.sintef.thingml.Stream;
import org.thingml.compilers.Context;

/**
 * @author ludovic
 */
public class JSCepCompiler extends CepCompiler {
    @Override
    public void generateStream(Stream stream, StringBuilder builder, Context ctx) {
        builder.append("var " + stream.qname("_") + " = Rx.Observable.fromEvent(this.eventEmitterForStream" + ", '" + stream.qname("_") +"')");

        if(stream.isFinalStream()) {
            builder.append(".subscribe(\n\t" +
                            "function(x) {\n\t\t" +
                                "var json = JSON.parse(x);\n\t\t" +
                                "console.log(\"Hack!! \"");

            for(Parameter p : stream.getInputs().get(0).getMessage().getParameters()) {
                builder.append(" + \"" + p.getName() + "= \" + json." + p.getName() + "+ \"; \"");
            }
            builder.append(");\n");
            ctx.getCompiler().getActionCompiler().generate(stream.getOutput(),builder,ctx);

            builder.append("});\n");
        }

    }
}
