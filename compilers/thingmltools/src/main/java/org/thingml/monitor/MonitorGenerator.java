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
package org.thingml.monitor;

import java.io.File;
import java.util.NoSuchElementException;

import org.thingml.thingmltools.ThingMLTool;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Import;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.RequiredPort;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.TypeRef;

/**
 *
 * @author Brice Morin
 */
public class MonitorGenerator extends ThingMLTool {

	public MonitorGenerator() {
        super();
    }

    @Override
    public String getID() {
    	return "monitor";    	
    }

    @Override
    public String getName() {    	
    	return "Monitor Generator";    	
    }

    @Override
    public String getDescription() {
        return "[UNDER ACTIVE DEVELOPMENT] Generates monitoring based on @monitor annotations";
    }

    @Override
    public ThingMLTool clone() {
        return new MonitorGenerator();
    }

    @Override
    public boolean compile(ThingMLModel model, String... options) {
    	ThingMLModel copy = model;//ThingMLHelpers.flattenModel(model);
    	    			
        System.out.println("Generate ThingML from model");
        
        Import log_import = ThingMLFactory.eINSTANCE.createImport();
        log_import.setFrom("stl");
        log_import.setImportURI("log.thingml");        
        copy.getImports().add(log_import);

        copy = ThingMLHelpers.flattenModel(copy);
        final File tempFile = new File(outDir, "monitor/temp.thingml");
        try {        	
        	ByteHelper.save(copy, tempFile.getAbsolutePath());
        	copy = ByteHelper.load(tempFile);
        } catch (Exception e) {
        	System.err.println("Error while saving the instrumented model...");
        	e.printStackTrace();
        	System.exit(1);
        }
        
    	Type stringType = null;
    	for (Type t : copy.getTypes()) {
            if (t.getName().equals("String")) {
              	stringType = t;
               	break;
            }            
        }
    	if (stringType == null) throw new NoSuchElementException("Cannot find String/Byte type");
    	
    	final TypeRef stringTypeRef = ThingMLFactory.eINSTANCE.createTypeRef();
    	stringTypeRef.setType(stringType);
        
        Thing logAPI = null;
        for(Thing t : ThingMLHelpers.allThings(copy)) {
        	if (t.getName().equals("WithLog")) {
        		logAPI = t;
        		break;
        	}
        }        
        if (logAPI == null) throw new NoSuchElementException("Cannot find WithLog thing");
                
        final Property id = logAPI.getProperties().get(0);
    	final RequiredPort monitoringPort = (RequiredPort) logAPI.getPorts().get(0);
    	        
        for (Thing t : ThingMLHelpers.allThings(copy)) {
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "not")) continue;
        	if (!AnnotatedElementHelper.hasAnnotation(t, "monitor")) continue;
        	
        	//FIXME: do not include it if it has already been included manually
        	t.getIncludes().add(logAPI);
        	
        	//////////////////////////////////////////
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "events")) {
        		Message msg_lost = null;
        		for (Message m : monitoringPort.getSends()) {
        			if (m.getName().equals("message_lost")) {
        				msg_lost = m;
        				break;
        			}
        		}
        		Message msg_handled = null;
        		for (Message m : monitoringPort.getSends()) {
        			if (m.getName().equals("message_handled")) {
        				msg_handled = m;
        				break;
        			}
        		}        		
        		Message msg_sent = null;
        		for (Message m : monitoringPort.getSends()) {
        			if (m.getName().equals("message_sent")) {
        				msg_sent = m;
        				break;
        			}
        		}
        		if (msg_lost == null) throw new NoSuchElementException("Cannot find message_lost message");
        		if (msg_handled == null) throw new NoSuchElementException("Cannot find message_handled message");
        		if (msg_sent == null) throw new NoSuchElementException("Cannot find message_sent message");
        		new EventMonitoring(t, id, monitoringPort, msg_lost, msg_handled, msg_sent, stringTypeRef).monitor();
        	}
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "functions")) {
        		Message msg = null;
        		for (Message m : monitoringPort.getSends()) {
        			if (m.getName().equals("function_called")) {
        				msg = m;
        				break;
        			}
        		}
        		if (msg == null) throw new NoSuchElementException("Cannot find function_called message");
        		new FunctionMonitoring(t, id, monitoringPort, msg, stringTypeRef).monitor();        	
        	}
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "properties")) {
        		Message msg = null;
        		for (Message m : monitoringPort.getSends()) {
        			if (m.getName().equals("property_changed")) {
        				msg = m;
        				break;
        			}
        		}
        		if (msg == null) throw new NoSuchElementException("Cannot find property_changed message");
        		new PropertyMonitoring(t, id, monitoringPort, msg, stringTypeRef).monitor();
        	}        	
        }
        
        final File monitoringFile = new File(outDir, "monitor/merged.thingml");
        try {        	
        	ByteHelper.save(ThingMLHelpers.flattenModel(copy), monitoringFile.getAbsolutePath());
        } catch (Exception e) {
        	System.err.println("Error while saving the instrumented model...");
        	e.printStackTrace();
        	System.exit(1);
        }
        return true;
    }
    
}
