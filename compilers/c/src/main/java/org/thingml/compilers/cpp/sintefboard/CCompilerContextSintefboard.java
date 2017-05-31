/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.thingml.compilers.cpp.sintefboard;

import java.util.ArrayList;
import java.util.Collections;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.CCompilerContext;

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
            } else if (filename.startsWith(configName)) {
                // These files will be handled later
                System.out.println("Special handling2 of " + filename);
            } else if (filename.contentEquals("hashdefines")) {
                // These files will be handled later
                System.out.println("Special handling3 of " + filename);
            } else if (filename.contentEquals("rcdportinfo")) {
                // These files will be handled later
                System.out.println("Special handling4 of " + filename);
            } else if (filename.endsWith(".h")) {
                headers.add(filename);
                System.out.println("Adding " + filename + " to headers");
            } else if (filename.endsWith(".c")) {
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

        
        Collections.sort(headers); // Sort the headers to control order of the declarations
        for (String f : headers) {
            System.out.println("Appending " + f + " to builderHeader");
            builderHeader.append(generatedCode.get(f).toString());
        }

        for (String f : impl) {
            builderImpl.append(generatedCode.get(f).toString());
        }

        for (String f : init) {
            builderInit.append(generatedCode.get(f).toString());
        }

        String stringHeader = generatedCode.get(configName + ".h").toString();
        stringHeader = stringHeader.replace("/*HASH_DEFINES*/", generatedCode.get("hashdefines").toString());
        stringHeader = stringHeader.replace("/*NAME*/", configName);
        stringHeader = stringHeader.replace("/*RUNTIME_CLASS*/", generatedCode.get("runtime.h").toString());
        stringHeader = stringHeader.replace("/*HEADER_CONTEXT*/", generatedCode.get(configName + ".h_ctx").toString());
        stringHeader = stringHeader.replace("/*HEADER_CLASS*/", builderHeader.toString());
        stringHeader = stringHeader.replace("/*CFG_CPPNAME_SCOPE*/", cfgCppnameScope);
        writeTextFile(configName + ".hpp", stringHeader);

        String stringImpl = generatedCode.get(configName + "_cfg.c").toString();
        
        StringBuilder gcSb = generatedCode.get("rcdportinfo");
        if (gcSb != null) stringImpl = stringImpl.replace("/*RCDPORTINFO*/", gcSb.toString());
        
        stringImpl = stringImpl.replace("/*NAME*/", configName);
        stringImpl = stringImpl.replace("/*RUNTIME_CLASS*/", generatedCode.get("runtime.c").toString());
        stringImpl = stringImpl.replace("/*CODE*/", builderImpl.toString());
        stringImpl = stringImpl.replace("/*THING_INIT_CODE*/", builderInit.toString());
        stringImpl = stringImpl.replace("/*CFG_CPPNAME_SCOPE*/", cfgCppnameScope);
        writeTextFile(configName + ".cpp", stringImpl);


        //GENERATE Posix test framework
        String stringMake = generatedCode.get("Makefile").toString();
        writeTextFile("Makefile", stringMake);

        String stringTestMain = generatedCode.get(configName + "Posix.cpp").toString();
        writeTextFile(configName + "Posix.cpp", stringTestMain);


    }


}
