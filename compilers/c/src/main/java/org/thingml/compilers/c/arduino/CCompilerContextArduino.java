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
package org.thingml.compilers.c.arduino;

import java.io.File;
import java.util.ArrayList;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;

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
    public String getCType(Type t) {    	
        if (AnnotatedElementHelper.hasAnnotation(t, "arduino_type")) {
            return AnnotatedElementHelper.annotation(t, "arduino_type").get(0);
        } 
        return super.getCType(t);
    }
    
    @Override
    public void writeGeneratedCodeToFiles() {

        // COMBINE ALL THE GENERATED CODE IN A SINGLE PDE FILE

        ArrayList<String> headers = new ArrayList<String>();
        ArrayList<String> modules = new ArrayList<String>();
        String main = getCurrentConfiguration().getName() + "_cfg.c";

        
        for (String filename : generatedCode.keySet()) {
            if (filename.endsWith(".h")) {
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
        	pde.append(generatedCode.get(f).toString());
        }

        for (String f : modules) {
        	pde.append(generatedCode.get(f).toString());
        }

        pde.append(generatedCode.get(main).toString());
        //writeTextFile(getCurrentConfiguration().getName() + ".pde", pde.toString());
        writeTextFile(getCurrentConfiguration().getName() + File.separatorChar + getCurrentConfiguration().getName() + ".ino", "#include <stdint.h>\n#include <Arduino.h>\n"+pde.toString());

    }
 
    @Override
    public void generatePSPollingCode(Configuration cfg, StringBuilder builder) {
        ThingMLModel model = ThingMLHelpers.findContainingModel(cfg);

        // FIXME: Extract the arduino specific part bellow

        Thing arduino_scheduler = null;
        for (Thing t : ThingMLHelpers.allThings(model)) {
            if (t.getName().equals("ThingMLScheduler")) {
                arduino_scheduler = t;
                break;
            }
        }
        if (arduino_scheduler != null) {
            Message poll_msg = null;
            for (Message m : ThingMLHelpers.allMessages(arduino_scheduler)) {
                if (m.getName().equals("poll")) {
                    poll_msg = m;
                    break;
                }
            }

            if (poll_msg != null) {
                // Send a poll message to all components which can receive it
                for (Instance i : ConfigurationHelper.allInstances(cfg)) {
                    for (Port p : ThingMLHelpers.allPorts(i.getType())) {
                        if (p.getReceives().contains(poll_msg)) {
                            builder.append(this.getHandlerName(i.getType(), p, poll_msg) + "(&" + this.getInstanceVarName(i) + ");\n");
                        }
                    }
                }

            }
        }
    }

}
