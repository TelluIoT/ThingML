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
package org.thingml.compilers.cpp.sintefboard;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.CCompilerContext;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ffl on 11.06.15.
 */
public class CCompilerContextSintefboard extends CCompilerContext {

    public CCompilerContextSintefboard(ThingMLCompiler c) {
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

        
        // COMBINE ALL THE GENERATED CODE IN TWO FILES "cfg.hpp" + cfg.cpp"
        
        String configName = getCurrentConfiguration().getName();
        String cfgCppnameScope = configName + "::";
        System.out.println("Processing config name " + configName);

        ArrayList<String> headers = new ArrayList<String>();
        ArrayList<String> impl = new ArrayList<String>();
        ArrayList<String> init = new ArrayList<String>();

        for (String filename : generatedCode.keySet()) {
            if (filename.startsWith("runtime")) {
                // These files will be handled later
                System.out.println("Special handling1 of " + filename);
            }
            else if (filename.startsWith(configName)) {
                // These files will be handled later
                System.out.println("Special handling2 of " + filename);
            } 
            else if (filename.endsWith(".h")) {
                headers.add(filename);
                System.out.println("Adding " + filename + " to headers");
            } 
            else if (filename.endsWith(".c")) {
                if (filename.endsWith("init.c")) {
                    init.add(filename);
                    System.out.println("Adding " + filename + " to init");
                } else {
                    impl.add(filename);
                    System.out.println("Adding " + filename + " to impl");
                }
            }
        }

        StringBuilder builderHeader = new StringBuilder();
        StringBuilder builderImpl = new StringBuilder();
        StringBuilder builderInit = new StringBuilder();

        for (String f : headers) {
            builderHeader.append(generatedCode.get(f).toString());
        }

        for (String f : impl) {
            builderImpl.append(generatedCode.get(f).toString());
        }

        for (String f : init) {
            builderInit.append(generatedCode.get(f).toString());
        }

        String stringHeader = generatedCode.get(configName + ".h").toString();
        stringHeader = stringHeader.replace("/*NAME*/", configName);
        stringHeader = stringHeader.replace("/*RUNTIME_CLASS*/", generatedCode.get("runtime.h").toString());
        stringHeader = stringHeader.replace("/*HEADER_CLASS*/", builderHeader.toString());
        stringHeader = stringHeader.replace("/*CFG_CPPNAME_SCOPE*/", cfgCppnameScope);
        writeTextFile(configName + ".hpp", stringHeader);
        
        String stringImpl = generatedCode.get(configName + "_cfg.c").toString();
        stringImpl = stringImpl.replace("/*NAME*/", configName);
        stringImpl = stringImpl.replace("/*RUNTIME_CLASS*/", generatedCode.get("runtime.c").toString());
        stringImpl = stringImpl.replace("/*CODE*/", builderImpl.toString());
        stringImpl = stringImpl.replace("/*THING_INIT_CODE*/", builderInit.toString());
        stringImpl = stringImpl.replace("/*CFG_CPPNAME_SCOPE*/", cfgCppnameScope);
        writeTextFile(configName + ".cpp", stringImpl);

    }


}
