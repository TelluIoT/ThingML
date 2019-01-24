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
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.ProvidedPort;
import org.thingml.xtext.thingML.RequiredPort;
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
        return "[UNDER ACTIVE DEVELOPMENT] Generates monitoring based on @monitor annotations";
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
        	monitoringMsgs.setName(t.getName() + "MonitoringMsgs");
        	monitoringMsgs.setFragment(true);
        	final Property id = ThingMLFactory.eINSTANCE.createProperty(); //FIXME: this should be refined in the configuration, so as to integrate the instance name
        	id.setTypeRef(EcoreUtil.copy(stringTypeRef));
        	id.setName("monitoringID");
        	id.setReadonly(true);
        	monitoringMsgs.getProperties().add(id);
        	final StringLiteral defaultID = ThingMLFactory.eINSTANCE.createStringLiteral();
        	defaultID.setStringValue(t.getName());
        	id.setInit(defaultID);
        	copy.getTypes().add(monitoringMsgs);
        	t.getIncludes().add(monitoringMsgs);
        	
        	
        	//FIXME: check if a monitor port already exists to avoid clashes with names
        	final RequiredPort monitoringPort = ThingMLFactory.eINSTANCE.createRequiredPort();
        	monitoringPort.setName("monitor");
        	monitoringPort.setOptional(true);
        	t.getPorts().add(monitoringPort);
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "events")) {
        		//TODO
        	}
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "functions")) {
        		new FunctionMonitoring().monitor(t, monitoringPort, monitoringMsgs, stringTypeRef);
        	}
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "properties")) {
        		new PropertyMonitoring().monitor(t, monitoringPort, monitoringMsgs, stringTypeRef);
        	}
        	
        	//Create MQTT proxy
        	//FIXME: ideally, we should be able to use other network libs, like serial, etc
        	final Thing mqtt = ThingMLFactory.eINSTANCE.createThing();
        	mqtt.setName(t.getName() + "MQTTMonitoringProxy");
        	mqtt.setFragment(true);
        	mqtt.getIncludes().add(monitoringMsgs);
        	final ProvidedPort mqttPort = ThingMLFactory.eINSTANCE.createProvidedPort();
        	mqttPort.setName("monitor");
        	mqttPort.getReceives().addAll(monitoringPort.getSends());
        	final PlatformAnnotation sync_send = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
        	sync_send.setName("sync_send");
        	sync_send.setValue("true");
        	mqttPort.getAnnotations().add(sync_send);
        	final PlatformAnnotation ext1 = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
        	ext1.setName("external");
        	ext1.setValue("posixmqttjson");
        	mqttPort.getAnnotations().add(ext1);
        	final PlatformAnnotation ext2 = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
        	ext2.setName("external");
        	ext2.setValue("javamqttjson");
        	mqttPort.getAnnotations().add(ext2);
        	final PlatformAnnotation ext3 = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
        	ext3.setName("external");
        	ext3.setValue("javascriptmqttjson");
        	mqttPort.getAnnotations().add(ext3);
        	final PlatformAnnotation ext4 = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
        	ext4.setName("external");
        	ext4.setValue("gomqttjson");
        	mqttPort.getAnnotations().add(ext4);
        	mqtt.getPorts().add(mqttPort);
        	copy.getTypes().add(mqtt);
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

}
