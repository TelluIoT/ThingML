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
import java.io.IOException;
import java.util.NoSuchElementException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.thingml.thingmltools.ThingMLTool;
import org.thingml.xtext.ThingMLStandaloneSetup;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Import;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.ObjectType;
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

	private final boolean isBinary;

    public MonitorGenerator(boolean isBinary) {
        super();
        this.isBinary = isBinary;
    }

    @Override
    public String getID() {
    	if (!isBinary)
    		return "monitor";
    	else
    		return "monitor-bin";
    }

    @Override
    public String getName() {
    	if (!isBinary)
    		return "Monitor Generator";
    	else 
    		return "Binary Monitor Generator";
    }

    @Override
    public String getDescription() {
        return "[UNDER ACTIVE DEVELOPMENT] Generates monitoring based on @monitor annotations";
    }

    @Override
    public ThingMLTool clone() {
        return new MonitorGenerator(isBinary);
    }

    @Override
    public void generateThingMLFrom(ThingMLModel model) {
    	ThingMLModel copy = model;//ThingMLHelpers.flattenModel(model);
    	    			
        System.out.println("Generate ThingML from model");
        
        Import log_import = ThingMLFactory.eINSTANCE.createImport();
        log_import.setFrom("stl");
        log_import.setImportURI("log.thingml");
        copy.getImports().add(log_import);

        copy = ThingMLHelpers.flattenModel(copy);
        final File tempFile = new File(outDir, "monitor/temp.thingml");
        try {        	
        	save(copy, tempFile.getAbsolutePath());
        	copy = load(tempFile);
        } catch (Exception e) {
        	System.err.println("Error while saving the instrumented model...");
        	e.printStackTrace();
        	System.exit(1);
        }
        
    	Type stringType = null;
    	for (Type t : copy.getTypes()) {
            if (t instanceof ObjectType) {
                if (!isBinary && t.getName().equals("String")) {
                	stringType = t;
                	break;
                }
                if (isBinary && t.getName().equals("Byte")) {
                	stringType = t;
                	break;
                }
            }
        }
    	if (stringType == null) throw new NoSuchElementException("Cannot find String/Byte type");
    	
    	final TypeRef stringTypeRef = ThingMLFactory.eINSTANCE.createTypeRef();
    	stringTypeRef.setType(stringType);
        
        Thing logAPI = null;
        for(Thing t : ThingMLHelpers.allThings(copy)) {
        	if (!isBinary && t.getName().equals("WithLog")) {
        		logAPI = t;
        		break;
        	}
        	if (isBinary && t.getName().equals("WithBinaryLog")) {
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
        		if (!isBinary)
        			new FunctionMonitoring(t, id, monitoringPort, msg, stringTypeRef).monitor();
        		else
        			new FunctionMonitoringBinary(t, id, monitoringPort, msg, stringTypeRef).monitor();
        	}
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "properties")) {
        		Message msg = null;
        		for (Message m : monitoringPort.getSends()) {
        			if (m.getName().equals("property_changed")) {
        				msg = m;
        				break;
        			}
        		}
        		new PropertyMonitoring(t, id, monitoringPort, msg, stringTypeRef).monitor();
        	}        	
        }
        
        final File monitoringFile = new File(outDir, "monitor/merged.thingml");
        try {        	
        	save(ThingMLHelpers.flattenModel(copy), monitoringFile.getAbsolutePath());
        } catch (Exception e) {
        	System.err.println("Error while saving the instrumented model...");
        	e.printStackTrace();
        	System.exit(1);
        }
        
    }
    
    //FIXME: this has nothing to do here. load/save is currently in compiler framework, not accessible from here. This should be part of the thingml project, together with metamodel, etc
    private void save(ThingMLModel model, String location) throws IOException {
    	ThingMLStandaloneSetup.doSetup();    	
    	if (!model.getImports().isEmpty())
    		throw new Error("Only models without imports can be saved with this method. Use the 'flattenModel' method first.");
    	
        ResourceSet rs = new ResourceSetImpl();
        Resource res = rs.createResource(URI.createFileURI(location));

        res.getContents().add(model);
        EcoreUtil.resolveAll(res);
        
        SaveOptions opt = SaveOptions.newBuilder().format().noValidation().getOptions();
        res.save(opt.toOptionsMap());
    }

    //FIXME: this has nothing to do here. load/save is currently in compiler framework, not accessible from here. This should be part of the thingml project, together with metamodel, etc
    private ThingMLModel load(File file) throws IOException {
    	ThingMLStandaloneSetup.doSetup();    	
        ResourceSet rs = new ResourceSetImpl();
        URI xmiuri = URI.createFileURI(file.getAbsolutePath());
        Resource model = rs.createResource(xmiuri);
        XtextResource resource = (XtextResource) model;        
        model.load(null);
        EcoreUtil.resolveAll(model);
        ThingMLModel m = (ThingMLModel) model.getContents().get(0);
        return m;        
    }
}
