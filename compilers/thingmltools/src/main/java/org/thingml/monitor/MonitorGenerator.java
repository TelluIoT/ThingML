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
package org.thingml.monitor;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.SaveOptions;
import org.thingml.thingmltools.ThingMLTool;
import org.thingml.xtext.ThingMLStandaloneSetup;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.RequiredPort;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.StringLiteral;
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
        return "[UNDER ACTIVE DEVELOPMENT] Generates monotiring base on @monitor annotations";
    }

    @Override
    public ThingMLTool clone() {
        return new MonitorGenerator();
    }

    @Override
    public void generateThingMLFrom(ThingMLModel model) {
    	final ThingMLModel copy = ThingMLHelpers.flattenModel(model);
    	Type stringType = null;
    	for (Type t : copy.getTypes()) {
            if (t instanceof ObjectType) {
                if (t.getName().equals("String"))
                	stringType = t;
            }
        }
    	final TypeRef stringTypeRef = ThingMLFactory.eINSTANCE.createTypeRef();
    	stringTypeRef.setType(stringType);
    	    			
        System.out.println("Generate ThingML from model");
        for (Thing t : ThingMLHelpers.allThings(copy)) {
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "not")) continue;
        	if (!AnnotatedElementHelper.hasAnnotation(t, "monitor")) continue;
        	
        	//Create monitoring API
        	final Thing monitoringMsgs = ThingMLFactory.eINSTANCE.createThing();
        	monitoringMsgs.setName("MonitoringMsgs");
        	monitoringMsgs.setFragment(true);
        	copy.getTypes().add(monitoringMsgs);
        	t.getIncludes().add(monitoringMsgs);
        	
        	
        	//FIXME: check if a monitor port already exists
        	final RequiredPort monitoringPort = ThingMLFactory.eINSTANCE.createRequiredPort();
        	monitoringPort.setName("monitor");
        	monitoringPort.setOptional(true);        	
        	t.getPorts().add(monitoringPort);
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "events"))
        		monitorEvents(t, monitoringPort, monitoringMsgs, stringTypeRef);
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "functions"))
        		monitorFunctions(t, monitoringPort, monitoringMsgs, stringTypeRef);
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "properties"))
        		monitorProperties(t, monitoringPort, monitoringMsgs, stringTypeRef);
        }

        try {
        	final File monitoringFile = new File(outDir, "monitor/merged.thingml");
        	save(ThingMLHelpers.flattenModel(copy), monitoringFile.getAbsolutePath());
        } catch (Exception e) {
        	System.err.println("Error while saving the instrumented model...");
        	e.printStackTrace();
        }
    }
    
    private void save(ThingMLModel model, String location) throws IOException {
    	System.out.println("Saving to " + location + " : " + model);
    	ThingMLStandaloneSetup.doSetup();    	
    	if (!model.getImports().isEmpty())
    		throw new Error("Only models without imports can be saved with this method. Use the 'flattenModel' method first.");
    	
        ResourceSet rs = new ResourceSetImpl();
        Resource res = rs.createResource(URI.createFileURI(location));

        System.out.println("debug res = " + res);
        System.out.println("debug res.getContents() = " + res.getContents());
        
        res.getContents().add(model);
        EcoreUtil.resolveAll(res);
        
        SaveOptions opt = SaveOptions.newBuilder().format().noValidation().getOptions();
        res.save(opt.toOptionsMap());
    }

    private void monitorEvents(Thing thing, Port monitoringPort, Thing monitoringMsgs, TypeRef stringTypeRef) {
    	
    }
    
    private void monitorFunctions(Thing thing, Port monitoringPort, Thing monitoringMsgs, TypeRef stringTypeRef) {
    	for(Function f : thing.getFunctions()) {
    		if (f.isAbstract()) continue;
    		if (AnnotatedElementHelper.isDefined(f, "monitor", "not")) continue;
    		
    		//Update monitoring API
    		final Message onFunctionCalled = ThingMLFactory.eINSTANCE.createMessage();
        	onFunctionCalled.setName(f.getName() + "_called");
        	monitoringMsgs.getMessages().add(onFunctionCalled);
        	monitoringPort.getSends().add(onFunctionCalled);
        	final Parameter onFunctionCalled_name = ThingMLFactory.eINSTANCE.createParameter(); //FIXME?: somehow a useless parameter... repeating the name of message
        	onFunctionCalled_name.setName("name");        
        	onFunctionCalled_name.setTypeRef(EcoreUtil.copy(stringTypeRef));
        	onFunctionCalled.getParameters().add(onFunctionCalled_name);
        	if (f.getTypeRef()!=null) {
            	final Parameter onFunctionCalled_type = ThingMLFactory.eINSTANCE.createParameter();
            	onFunctionCalled_type.setName("type");        
            	onFunctionCalled_type.setTypeRef(EcoreUtil.copy(stringTypeRef));
            	onFunctionCalled.getParameters().add(onFunctionCalled_type);
            	
            	final Parameter onFunctionCalled_return = ThingMLFactory.eINSTANCE.createParameter();
            	onFunctionCalled_return.setName("returns");        
            	onFunctionCalled_return.setTypeRef(EcoreUtil.copy(f.getTypeRef()));
            	onFunctionCalled.getParameters().add(onFunctionCalled_return);
        	}
        	for(Parameter p : f.getParameters()) {
        		final Parameter onFunctionCalled_p = ThingMLFactory.eINSTANCE.createParameter();
            	onFunctionCalled_p.setName(p.getName());        
            	onFunctionCalled_p.setTypeRef(EcoreUtil.copy(p.getTypeRef()));
            	onFunctionCalled.getParameters().add(onFunctionCalled_p);
        	}
        	
        	//Send monitoring message before each return statement (or as the first statement in the function)
        	//FIXME: consider one-statement functions which do not declare a block for their bodies        	
        	if (f.getTypeRef()==null) {
        		final ActionBlock block = (ActionBlock) f.getBody();
        		final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
        		send.setMessage(onFunctionCalled);
        		send.setPort(monitoringPort);
        		final StringLiteral name_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
        		name_exp.setStringValue(f.getName());
        		send.getParameters().add(name_exp);
        		
        		for(Parameter p : f.getParameters()) {
        			final PropertyReference p_exp = ThingMLFactory.eINSTANCE.createPropertyReference();
        			p_exp.setProperty(p);
        			send.getParameters().add(p_exp);
        		}
        		
        		block.getActions().add(0, send);
        	} else {//FIXME: store return expression in a variable, so as to avoid side effect e.g. if it returns a function call (which would then be called twice)
        		//TODO
        	}
    	}
    }
    
    private void monitorProperties(Thing thing, Port monitoringPort, Thing monitoringMsgs, TypeRef stringTypeRef) {
    	
    }
    

}
