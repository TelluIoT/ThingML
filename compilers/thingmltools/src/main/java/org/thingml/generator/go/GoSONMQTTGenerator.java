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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.generator.go;

import java.io.File;

import org.thingml.thingmltools.ThingMLTool;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;


public class GoSONMQTTGenerator extends ThingMLTool {

    public GoSONMQTTGenerator() {
        super();
    }

    @Override
    public String getID() {
        return "gomqttjson";
    }

    @Override
    public String getName() {
        return "ThingML Go generator for messaging JSON over MQTT";
    }

    @Override
    public String getDescription() {
        return "Generate JSON serialization for messages to be sent over MQTT. Go specific.";
    }

    @Override
    public ThingMLTool clone() {
        return new GoSONMQTTGenerator();
    }

    @Override
    public void generateThingMLFrom(ThingMLModel model) {
        System.out.println("[gomqttjson] Processing Model " + model.eResource().getURI().toString() + "\n");
        
        setOutputDirectory(new File(model.eResource().getURI().path()).getParentFile());
        
        // Loop over all the Things
        for (Thing t : ThingMLHelpers.allThings(model)) {
        	// Loop over ports
        	for (Port p : ThingMLHelpers.allPorts(t)) {
        		
        		if (AnnotatedElementHelper.isDefined(p, "external", "gomqttjson")) {
        			System.out.println("[gomqttjson] Generating for Thing: " + t.getName() + " Port: " + p.getName() + "\n");
        			generateAdapterFor(model, t, p);
            	}
        	}
        }
        System.out.println("[gomqttjson] Done. (Model " + model.eResource().getURI().toString() + ")\n");
        writeGeneratedCodeToFiles();
    }
    
    public void generateAdapterFor(ThingMLModel model, Thing t, Port p) {
    	
    	String imports = "import \"" + model.eResource().getURI().lastSegment() + "\"\n";
    	String thing_name = t.getName() +"_GoMqttJson_Impl";
    	String thing_includes = t.getName() + ", MQTTAdapterMsgs";
    	
    	StringBuilder subscriptions = new StringBuilder();
    	
    	for (Message m : p.getSends()) {
    		subscriptions.append("\t\tsubscribe_for_message(\""+ m.getName()+"\")\n");	
    	}
    	
    	StringBuilder sendfunctions = new StringBuilder();
    	
    	for (Message m : p.getReceives()) {
    		generate_send_handler(p, m, sendfunctions);
    	}
    	
    	String template = getTemplateByID("gomqttjson/GoMqttJson.thingml");
    	template = template.replace("/*IMPORTS*/", imports);
    	template = template.replace("/*THING_NAME*/", thing_name);
    	template = template.replace("/*THING_INCLUDES*/", thing_includes);
    	template = template.replace("/*SUBSCRIBE*/", subscriptions.toString());
    	template = template.replace("/*SEND_HANDLERS*/", sendfunctions.toString());
    	template = template.replace("/*MQTT_PORT_NAME*/", p.getName());
    
    	StringBuilder parsemsgs = new StringBuilder();
    	for (Message m : p.getSends()) {
    		generate_parsing_msg(p, m ,parsemsgs);
    	}
    	template = template.replace("/*PARSEMSG*/", parsemsgs.toString());
    	
    	StringBuilder builder = new StringBuilder(template);
    	
    	String genfilename = t.getName() + "GoMqttJson.thingml";
    	generatedCode.put(genfilename, builder);
    }

    public void generate_send_handler(Port p, Message m, StringBuilder b) {
    	b.append("\t\tinternal event e:" + p.getName() + "?" + m.getName() + " action do\n");
    	
    	b.append("\t\t\t// Sending for " + p.getName() + "!" + m.getName() + "\n");
    	
    	final Thing t = ThingMLHelpers.findContainingThing(m);
    	String msg_name = (t.isFragment()) ? "Fragment" + t.getName() + "Msg" + m.getName() : t.getName() + "Msg" + m.getName();
    	
    	b.append("\t\t\t`j := " + msg_name + "{");
    	for (Parameter param : m.getParameters()) {
    		b.append("` & e."+param.getName()+" & `,");
    	}
    	b.append("}\n");
    	b.append("\t\t\tpayload, err := json.Marshal(j)\n");//TODO: what to do if err? (though it should, in principle, not happen...
    	b.append("\t\t\tif (err == nil){`\n");
    	b.append("\t\t\t\tpublish_message(\""+ m.getName()+"\", `payload` as Buffer, `len(payload)` as UInt32)\n");
    	b.append("\t\t\t`}`\n");
    	
    	b.append("\t\tend\n\n");
    }
    
    public void generate_parsing_msg(Port p, Message m, StringBuilder b) {
    	
    	b.append("\n\t\t\tcase \"" + m.getName() + "\" :\n");
    	
    	final Thing t = ThingMLHelpers.findContainingThing(m);
    	String msg_name = (t.isFragment()) ? "Fragment" + t.getName() + "Msg" + m.getName() : t.getName() + "Msg" + m.getName();
    	
    	b.append("\t\t\tj := " + msg_name + "{}\n");
    	b.append("err := json.Unmarshal([]byte(` & payload & `), &j)\n");
    	b.append("if (err != nil){\nreturn false\n}\n");
    	for (Parameter param : m.getParameters()) {    	
    		String param_name = "";
			if (param.getName().length()>1)
				param_name = param.getName().substring(0, 1).toUpperCase() + param.getName().substring(1);
			else
				param_name = param.getName().substring(0, 1).toUpperCase();
    		b.append("\t\t\t\t___" + param.getName() + " := j." + param_name + "\n");
    	}
    	b.append("`"+ p.getName() +"!" + m.getName() + "(");
    	for (Parameter param : m.getParameters()) {
    		if (!param.equals(m.getParameters().get(0))) b.append(", ");
    		b.append("`___" + param.getName()+"` as " + param.getTypeRef().getType().getName());
    	}
    	b.append(")`\n");
    	b.append("break\n");
    }
    
}
