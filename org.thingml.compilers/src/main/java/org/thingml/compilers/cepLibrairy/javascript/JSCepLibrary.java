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
package org.thingml.compilers.cepLibrairy.javascript;

import org.sintef.thingml.Parameter;
import org.thingml.compilers.cepLibrairy.CepLibrary;

import java.util.List;

/**
 * @author ludovic
 */
public class JSCepLibrary extends CepLibrary {
    private JSCepLibrary(){}

    //todo delete hack message
    public static final JSCepLibrary instance = new JSCepLibrary();
    @Override
    public String createStreamFromEvent(List<Parameter> params, String eventPropertyName, String event, String functionCall) {
        String result = "Rx.Observable.fromEvent(this." + eventPropertyName +",'"+event+"').subscribe(\n\t" +
                "function(x){\n\t\t" +
                "var json = JSON.parse(x);\n\t\t" +
                "console.log(\"Hack!! \"";

        for (Parameter param : params) {
            result += " + \"" + param.getName() + "= \" + json." + param.getName() + "+ \"; \"" ;
        }



        result  += ");\n\t\t" +
                "process.nextTick("+functionCall+".bind(_this";

        for (Parameter param : params) {
            result += ", json." + param.getName();
        }

        result += "));\n\t" +
                "},\n\t" +
                "function(err){console.log(\"Error : \" + err)},\n\t" +
                "function(){console.log(\"End\")});\n\n";





        return result;
    }
}
