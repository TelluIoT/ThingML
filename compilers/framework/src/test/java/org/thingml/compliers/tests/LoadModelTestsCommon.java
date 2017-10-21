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
package org.thingml.compliers.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.AnnotatedElement;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.ProvidedPort;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;

public abstract class LoadModelTestsCommon {
	
	/* --- Some helper functions --- */
	private void checkAnnotation(AnnotatedElement e, String type, String name, String value) {
		for (PlatformAnnotation a : e.getAnnotations()) {
			if (a.getName().equals(name) && a.getValue().equals(value))
				return;
		}
		fail("'"+type+"' has annotation @"+name+"='"+value+"'");
	}

	private PrimitiveType checkPrimitiveType(ThingMLModel m, String name, int size) {
		for (Type t : m.getTypes()) {
			if (t.getName().equals(name)) {
				assertTrue("'"+name+"' is PrimitiveType", t instanceof PrimitiveType);
				PrimitiveType ct = (PrimitiveType)t;
				assertTrue("'"+name+"' has byte size "+size, ct.getByteSize() == size);
				return ct;
			}
		}
		fail("Model has type '"+name+"'");
		return null;
	}
	
	private ObjectType checkObjectType(ThingMLModel m, String name) {
		for (Type t : m.getTypes()) {
			if (t.getName().equals(name)) {
				assertTrue("'"+name+"' is ObjectType", t instanceof ObjectType);
				return (ObjectType)t;
			}
		}
		fail("Model has type '"+name+"'");
		return null;
	}
	
	private Thing checkThing(ThingMLModel m, String name) {
		for (Type t : m.getTypes()) {
			if (t.getName().equals(name)) {
				assertTrue("'"+name+"' is Thing", t instanceof Thing);
				return (Thing)t;
			}
		}
		fail("Model has type '"+name+"'");
		return null;
	}
	
	/* --- Datatypes check --- */
	private class modelTestDatatypes {
		PrimitiveType b;
		PrimitiveType i;
		
		public modelTestDatatypes(PrimitiveType b, PrimitiveType i) {
			this.b = b;
			this.i = i;
		}
	}
	private modelTestDatatypes checkDatatypes(ThingMLModel m) {
		PrimitiveType b = checkPrimitiveType(m, "Byte", 1);
		checkAnnotation(b, "Byte", "c_type", "uint8_t");
		
		PrimitiveType i = checkPrimitiveType(m, "Int", 2);
		checkAnnotation(i, "Int", "c_type", "int16_t");
		
		checkObjectType(m, "String");
		
		return new modelTestDatatypes(b, i);		
	}
	
	/* --- Messages check --- */
	private class modelTestMessages {
		Thing tm;
		Message testMessage;
		
		public modelTestMessages(Thing tm, Message testMessage) {
			this.tm = tm;
			this.testMessage = testMessage;
		}
	}
	private modelTestMessages checkMessages(ThingMLModel m, modelTestDatatypes d) {
		Thing tm = checkThing(m, "TestMessages");
		assertTrue("'TestMessages' is fragment",tm.isFragment());
		List<Message> tmMsgs = tm.getMessages();
		assertTrue("'TestMessages' has 1 message", tmMsgs.size() == 1);
		Message testMessage = tmMsgs.get(0);
		assertTrue("'TestMessage' is named 'TestMessage'", testMessage.getName().equals("TestMessage"));
		List<Parameter> tmParams = testMessage.getParameters();
		assertTrue("'TestMessage' has 2 parameters", tmParams.size() == 2);
		Parameter paramA = tmParams.get(0);
		assertTrue("'TestMessage[0]' is named 'ParamA'", paramA.getName().equals("ParamA"));
		assertTrue("'TestMessage[0]' has type 'Byte'", paramA.getTypeRef().getType() == d.b);
		Parameter paramB = tmParams.get(1);
		assertTrue("'TestMessage[1]' is named 'ParamB'", paramB.getName().equals("ParamB"));
		assertTrue("'TestMessage[1]' has type 'Int'", paramB.getTypeRef().getType() == d.i);
		
		return new modelTestMessages(tm, testMessage);
	}
	
	/* --- Thing + config check --- */
	private void checkThingConfig(ThingMLModel m, modelTestDatatypes d, modelTestMessages msgs) {
		Thing tt = checkThing(m, "TestThing");
		assertFalse("'TestThing' is not fragment",tt.isFragment());
		List<Thing> ttIncl = tt.getIncludes();
		assertTrue("'TestThing' includes 1 thing", ttIncl.size() == 1);
		assertTrue("'TestThing' includes 'TestMessages'", ttIncl.get(0) == msgs.tm);
		List<Port> ttPorts = tt.getPorts();
		assertTrue("'TestThing' has 1 port", ttPorts.size() == 1);
		Port testPort = ttPorts.get(0);
		assertTrue("'TestPort' is named 'TestPort'", testPort.getName().equals("TestPort"));
		assertTrue("'TestPort' is a provided port", testPort instanceof ProvidedPort);
		List<Message> testPortSends = testPort.getSends();
		assertTrue("'TestPort' sends 0 messages", testPortSends.size() == 0);
		List<Message> testPortReceives = testPort.getReceives();
		assertTrue("'TestPort' receives 1 message", testPortReceives.size() == 1);
		assertTrue("'TestPort' receives 'TestMessage'", testPortReceives.get(0) == msgs.testMessage);
		assertTrue("'TestThing' has a statechart", tt.getBehaviour() != null);
		CompositeState statechart = tt.getBehaviour();
		assertTrue("'TestThing' has a statechart named 'TestChart'", statechart.getName().equals("TestChart"));
		List<State> states = statechart.getSubstate();
		assertTrue("'TestChart' has 1 state", states.size() == 1);
		State start = states.get(0);
		assertTrue("'Start' state is named 'Start'", start.getName().equals("Start"));
		assertTrue("'TestChart' has 'Start' as initial", statechart.getInitial() == start);
		
		List<Configuration> cfgs = m.getConfigs();
		assertTrue("The model contains 1 configuration", cfgs.size() == 1);
		Configuration cfg = cfgs.get(0);
		assertTrue("The configuration is named 'TestConfiguration'", cfg.getName().equals("TestConfiguration"));
		List<Instance> insts = cfg.getInstances();
		assertTrue("'TestConfiguration' has 1 instance", insts.size() == 1);
		Instance testInst = insts.get(0);
		assertTrue("'test' instance is named 'test'", testInst.getName().equals("test"));
		assertTrue("'test' instance is of type 'TestThing'", testInst.getType() == tt);
	}
	
	
	
	
	/* ---------- Methods called from the JUnit tests ---------- */
	protected void checkSimpleModel(ThingMLModel m) {
		// Everything is in the same model
		// Check the contents
		modelTestDatatypes d = checkDatatypes(m);
		modelTestMessages msgs = checkMessages(m, d);
		checkThingConfig(m, d, msgs);
	}
	
	protected void checkSimpleIncludeModel(ThingMLModel m) {
		// This one is split into three models, one for each file. 
		// Find the associated models for the three files
		List<ThingMLModel> models = ThingMLHelpers.allThingMLModelModels(m);
		
		ThingMLModel topModel = null;
		ThingMLModel msgsModel = null;
		ThingMLModel dataModel = null;
		
		for (ThingMLModel model : models) {
			String uri = model.eResource().getURI().toString();
			if (uri.endsWith("SimpleIncludeModel.thingml")) {
				topModel = model;
			} else if (uri.endsWith("SimpleIncludeModelMessages.thingml")) {
				msgsModel = model;
			} else if (uri.endsWith("SimpleIncludeModelDatatypes.thingml")) {
				dataModel = model;
			}
		}
		
		// Check the contents of the three models
		modelTestDatatypes d = checkDatatypes(dataModel);
		modelTestMessages msgs = checkMessages(msgsModel, d);
		checkThingConfig(topModel, d, msgs);
	}
	
	protected void checkSimpleIncludeModelFlat(ThingMLModel m) {
		modelTestDatatypes d = checkDatatypes(m);
		modelTestMessages msgs = checkMessages(m, d);
		checkThingConfig(m, d, msgs);
	}
}
