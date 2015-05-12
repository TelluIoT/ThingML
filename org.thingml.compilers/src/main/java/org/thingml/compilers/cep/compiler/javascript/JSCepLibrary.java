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
package org.thingml.compilers.cep.compiler.javascript;

import org.sintef.thingml.Parameter;
import org.thingml.compilers.Context;
import org.thingml.compilers.cep.architecture.JoinStream;
import org.thingml.compilers.cep.architecture.SimpleStream;
import org.thingml.compilers.cep.architecture.Stream;
import org.thingml.compilers.cep.architecture.TimeStream;
import org.thingml.compilers.cep.compiler.CepLibrary;

/**
 * @author ludovic
 */
public class JSCepLibrary extends CepLibrary {
    private JSCepLibrary(){}

    public static final JSCepLibrary instance = new JSCepLibrary();

    @Override
    public String createStreamFromEvent(SimpleStream stream, Context ctx) {
        String result = "var " + stream.getName() +
                " = Rx.Observable.fromEvent(this." + ctx.getVariableName(stream.getEventProperty()) +",'"+ stream.getEventProperty().getName() +"')";

        if(stream.isWithSubscribe()) {
            result += ".subscribe(\n\t" +
                    "function(x){\n\t\t" +
                            "var json = JSON.parse(x);\n\t\t" +
                            "console.log(\"Hack!! \"";

            for (Parameter param : stream.getMessage().getMessage().getParameters()) {
                result += " + \"" + param.getName() + "= \" + json." + param.getName() + "+ \"; \"" ;
            }

            String functionCall = "send" + ctx.firstToUpper(stream.getStreamMessage().getName()) + "On" + ctx.firstToUpper(stream.getPortSend().getName());
            result  += ");\n\t\t" +
                    "process.nextTick("+functionCall+".bind(_this";

            for (Parameter param : stream.getMessage().getMessage().getParameters()) {
                result += ", json." + param.getName();
            }

            result += "));\n\t" +
                    "},\n\t" +
                    "function(err){console.log(\"Error : \" + err)},\n\t" +
                    "function(){console.log(\"End\")})";
        }

        result += ";\n\n";

        return result;
    }

    @Override
    public String createStreamFromEvent(TimeStream stream, Context ctx) {
        return "Rx.Observable.interval("+ stream.getTimeMs() +");";
    }

    @Override
    public String createStreamFromEvent(JoinStream stream, Context ctx) {
        String result = "var " + stream.getName() + " = " + stream.getStreams().get(0).getName() + ".join("+ stream.getStreams().get(1).getName() +",\n" +
                "function(){ return " + createStreamFromEvent(stream.getWaitStream(),ctx) +"},\n" +
                "function(){ return " + createStreamFromEvent(stream.getWaitStream(),ctx) +"},\n" +
                stream.getJoinFunction().getName() + ")";

        if(stream.isWithSubscribe()) {
            SimpleStream s = (SimpleStream) stream.getStreams().get(0); //fixme
            String functionCall = "send" + ctx.firstToUpper(s.getStreamMessage().getName()) + "On" + ctx.firstToUpper(s.getPortSend().getName()); //fixme

            result += ".subscribe(function(x){ \n" +
                    "\tconsole.log(x)\n" +
                    "\tvar json = JSON.parse(x);\n" +
                    "process.nextTick("+functionCall+".bind(_this";

            for (Parameter param : s.getStreamMessage().getParameters()) { //fixme
                result += ", json." + param.getName();
            }

            result += "));\n\t" +
                    "});";
        }
        result += ";\n\n";


        return result;
    }
}
