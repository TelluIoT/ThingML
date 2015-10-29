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
package org.thingml.compilers.c.arduino;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.CCompilerContext;

import java.util.ArrayList;

/**
 * Created by ffl on 11.06.15.
 */
public class CCompilerContextArduino extends CCompilerContext {

    public CCompilerContextArduino(ThingMLCompiler c) {
        super(c);
    }

    public boolean sync_fifo() {
        return false;
    }

    public int fifoSize() {
        return 256;
    }

    @Override
    public void writeGeneratedCodeToFiles() {

        // COMBINE ALL THE GENERATED CODE IN A SINGLE PDE FILE

        ArrayList<String> headers = new ArrayList<String>();
        ArrayList<String> modules = new ArrayList<String>();
        String main = getCurrentConfiguration().getName() + "_cfg.c";

        for (String filename : generatedCode.keySet()) {
            if (filename.endsWith(".h")) headers.add(filename);
            if (filename.endsWith(".c") && !filename.equals(main)) modules.add(filename);
        }

        StringBuilder pde = new StringBuilder();

        for (String f : headers) {
            pde.append(generatedCode.get(f).toString());
        }

        for (String f : modules) {
            pde.append(generatedCode.get(f).toString());
        }

        pde.append(generatedCode.get(main).toString());

        writeTextFile(getCurrentConfiguration().getName() + ".pde", pde.toString());

    }


}
