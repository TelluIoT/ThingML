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
package org.thingml.compilers.cpp.arduinomf;

import java.io.File;
import java.util.ArrayList;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.cpp.arduino.CCompilerContextArduino;

/**
 * Created by ffl on 11.06.15.
 */
public class CCompilerContextArduinomf extends CCompilerContextArduino {

    public CCompilerContextArduinomf(ThingMLCompiler c) {
        super(c);
    }

   
    @Override
    public void writeGeneratedCodeToFiles() {

        // COMBINE ALL THE GENERATED CODE IN A SINGLE PDE FILE

        ArrayList<String> headers = new ArrayList<String>();
        ArrayList<String> modules = new ArrayList<String>();
        String main = getCurrentConfiguration().getName() + "_cfg.c";

        StringBuilder mainpart = new StringBuilder();
        
        for (String filename : generatedCode.keySet()) {
            if (filename.endsWith(".h")) {
            	mainpart.append("#include \"" + filename +"\"\n");
                headers.add(filename);
                //System.out.println("Adding " + filename + " to headers");
            }
            if (filename.endsWith(".c") && !filename.equals(main)) {
                modules.add(filename);
                //System.out.println("Adding " + filename + " to modules");
            }
        }

        StringBuilder pde = new StringBuilder();

        for (String f : headers) {
        	writeTextFile(getCurrentConfiguration().getName() + File.separatorChar + f, headerWrapper(generatedCode.get(f).toString(), f));
            pde.append(generatedCode.get(f).toString());
        }

        for (String f : modules) {
        	writeTextFile(getCurrentConfiguration().getName() + File.separatorChar + f + "pp","#include \"" +f.substring(0,f.length() - 1)   +"h\"\n"+ generatedCode.get(f).toString());
            pde.append(generatedCode.get(f).toString());
        }

        pde.append(generatedCode.get(main).toString());
        writeTextFile(getCurrentConfiguration().getName() + File.separatorChar + getCurrentConfiguration().getName() + ".ino", mainpart.toString() + generatedCode.get(main).toString());
        //writeTextFile(getCurrentConfiguration().getName() + ".pde", pde.toString());
        writeTextFile(getCurrentConfiguration().getName()+"all" + File.separatorChar + getCurrentConfiguration().getName() + "all.ino", pde.toString());

    }

    // TODO : generate it in the file constructor
    private String headerWrapper(String header, String filename){
    	header = "#ifndef " + filename.substring(0,filename.length() - 2) + "\n#define "+ filename.substring(0,filename.length() - 2) +"\n#include <stdint.h>\n#include <Arduino.h>\n" + header + "\n#endif";
    	return header;
    }
    
}
