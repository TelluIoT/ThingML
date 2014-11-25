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
package org.thingml.compilers;

import org.sintef.thingml.Configuration;
import org.thingml.cgenerator.CGenerator;

import java.io.PrintStream;

/**
 * Created by ffl on 25.11.14.
 */
public class ArduinoCompiler extends OpaqueThingMLCompiler {
    @Override
    public String getPlatform() {
        return "C/C++";
    }

    @Override
    public String getName() {
        return "Arduino";
    }

    @Override
    public void do_call_compiler(Configuration cfg) {
        CGenerator.opaqueArduinoCodeGenerator(cfg, this);
    }
}
