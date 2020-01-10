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

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.thingmltools.ThingMLTool;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Import;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
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
public class BinaryMonitorGenerator extends ThingMLTool {

    public BinaryMonitorGenerator() {
        super();
    }

    @Override
    public String getID() {
   		return "monitor-bin";
    }

    @Override
    public String getName() {
   		return "Binary Monitor Generator";
    }

    @Override
    public String getDescription() {
        return "[UNDER ACTIVE DEVELOPMENT] Generates monitoring based on @monitor annotations";
    }

    @Override
    public ThingMLTool clone() {
        return new BinaryMonitorGenerator();
    }

    @Override
    public boolean compile(ThingMLModel model, String... options) {
    	ThingMLModel copy = model;//ThingMLHelpers.flattenModel(model);
    	ByteHelper.reset();
    	    			
        System.out.println("Generate ThingML from model");
        
        Import log_import = ThingMLFactory.eINSTANCE.createImport();
        log_import.setFrom("stl");
       	log_import.setImportURI("logbinary.thingml");
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
            if (t.getName().equals("Byte")) {
                stringType = t;
                break;
            }
        }
    	if (stringType == null) throw new NoSuchElementException("Cannot find String/Byte type");
    	
    	final TreeIterator<EObject> allContent = EcoreUtil.getAllContents(copy, true);
    	while(allContent.hasNext()) {
    		final EObject o = allContent.next();
    		if (o instanceof Message) {
    			final Message m = (Message) o;
    			final PlatformAnnotation ma = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
    			ma.setName("id");
    			ma.setValue(Byte.toString(ByteHelper.messageID()));
    			m.getAnnotations().add(ma);
    		} else if (o instanceof Port) {
    			final Port port = (Port) o;    			
    			final PlatformAnnotation ma = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
    			ma.setName("id");
    			ma.setValue(Byte.toString(ByteHelper.portID()));
    			port.getAnnotations().add(ma);
    		}else if (o instanceof Thing) {
    			final Thing thing = (Thing) o;
    			if (AnnotatedElementHelper.isDefined(thing, "monitor", "not") || !AnnotatedElementHelper.hasAnnotation(thing, "monitor")) continue;
    			final PlatformAnnotation ma = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
    			ma.setName("id");
    			ma.setValue(Byte.toString(ByteHelper.thingID()));
    			thing.getAnnotations().add(ma);
    		} else if (o instanceof Function) {
    			final Function f = (Function) o;
    			if (f.isAbstract() || AnnotatedElementHelper.isDefined(f, "monitor", "not")) continue;
    			final PlatformAnnotation ma = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
    			ma.setName("id");
    			ma.setValue(Byte.toString(ByteHelper.functionID()));
    			f.getAnnotations().add(ma);
    		} else if (o instanceof Property) {
    			final Property p = (Property) o;
    			if (AnnotatedElementHelper.isDefined(p, "monitor", "not")) continue;
    			final PlatformAnnotation ma = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
    			ma.setName("id");
    			ma.setValue(Byte.toString(ByteHelper.varID()));
    			p.getAnnotations().add(ma);
    		} else if (o instanceof Type) {
    			final Type t = (Type) o;    			
    			final PlatformAnnotation ma = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
    			ma.setName("id");
    			ma.setValue(Byte.toString(ByteHelper.datatypeID()));
    			t.getAnnotations().add(ma);
    		} 
    	}
    	
    	final TypeRef stringTypeRef = ThingMLFactory.eINSTANCE.createTypeRef();
    	stringTypeRef.setType(stringType);
        
        Thing logAPI = null;
        for(Thing t : ThingMLHelpers.allThings(copy)) {
        	if (t.getName().equals("WithBinaryLog")) {
        		logAPI = t;
        		break;
        	}
        }        
        if (logAPI == null) throw new NoSuchElementException("Cannot find WithLog thing");
                
        final Property id = logAPI.getProperties().get(0);
    	final RequiredPort monitoringPort = (RequiredPort) logAPI.getPorts().get(0);
    	Message msg = null;
		for (Message m : monitoringPort.getSends()) {
			if (m.getName().equals("log")) {
				msg = m;
				break;
			}
		}
		if (msg == null) throw new NoSuchElementException("Cannot find log message");
    	        
        for (Thing t : ThingMLHelpers.allThings(copy)) {
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "not")) continue;
        	if (!AnnotatedElementHelper.hasAnnotation(t, "monitor")) continue;
        	
        	//FIXME: do not include it if it has already been included manually
        	t.getIncludes().add(logAPI);
        	        	
        	        	
        	//////////////////////////////////////////
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "events")) {
        		new EventMonitoringBinary(t, id, monitoringPort, msg, stringTypeRef).monitor();
        	}
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "functions")) {        		  			
        		new FunctionMonitoringBinary(t, id, monitoringPort, msg, stringTypeRef).monitor();
        	}
        	
        	if (AnnotatedElementHelper.isDefined(t, "monitor", "properties")) {        		
        		new PropertyMonitoringBinary(t, id, monitoringPort, msg, stringTypeRef).monitor();
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
