package org.thingml.compilers.c.teensy;

import java.io.File;
import java.util.ArrayList;

import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
public class CCompilerContextTeensy extends CCompilerContext{

	public CCompilerContextTeensy(ThingMLCompiler c) {
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
        	writeTextFile(getCurrentConfiguration().getName() + File.separatorChar +"src"+ File.separatorChar + f, headerWrapper(generatedCode.get(f).toString(), f));
            pde.append(generatedCode.get(f).toString());
        }

        for (String f : modules) {
        	writeTextFile(getCurrentConfiguration().getName() + File.separatorChar +"src"+  File.separatorChar + f + "pp","#include \"" +f.substring(0,f.length() - 1)   +"h\"\n"+ generatedCode.get(f).toString());
            pde.append(generatedCode.get(f).toString());
        }

        pde.append(generatedCode.get(main).toString());
        writeTextFile(getCurrentConfiguration().getName() + File.separatorChar +"src"+  File.separatorChar + getCurrentConfiguration().getName() + ".cpp", mainpart.toString() + generatedCode.get(main).toString());
    }

    // TODO : generate it in the file constructor
    private String headerWrapper(String header, String filename){
    	header = "#ifndef " + filename.substring(0,filename.length() - 2) + "\n#define "+ filename.substring(0,filename.length() - 2) +"\n#include \"WProgram.h\"\n" + header + "\n#endif";
    	return header;
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
