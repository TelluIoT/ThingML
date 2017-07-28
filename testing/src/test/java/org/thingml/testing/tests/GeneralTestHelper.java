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
package org.thingml.testing.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.eclipse.xtext.EcoreUtil2;
import org.thingml.testing.helpers.TestTypesHelper;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.AbstractConnector;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.ConditionalAction;
import org.thingml.xtext.thingML.ConfigPropertyAssign;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.ExternExpression;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.NotExpression;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ProvidedPort;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.RequiredPort;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.VariableAssignment;

public class GeneralTestHelper {
	ThingMLModel model;
	Configuration config;
	Collection<GeneralTestInputOutput> inputoutputs;
	// Keep some references so we don't have to traverse the whole model all the time
	Thing testFragment;
	RequiredPort testPort;
	
	Thing testMsgs;
	Message testIn;
	Message testOut;
	Message testFailure;
	Message testDone;
	
	Thing fileDumper;
	Property fileDumpPath;
	
	Thing testHarness;
	ActionBlock testHarnesSendCharsEntry;
	
	Thing testSynchronizer;
	State testSynchronizerListen;
	Function testSynchronizerCheckCompleted;
	
	PrimitiveType boolType;
	
	public GeneralTestHelper(Configuration configuration) {
		config = configuration;
		model = ThingMLHelpers.findContainingModel(configuration);
		
		testFragment = getFragment("Test");
		testPort = (RequiredPort)getTestPort(testFragment, "Test");
		
		testMsgs = getFragment("TestMsgs");
		testIn = getTestMessage(testMsgs, "In");
		testOut = getTestMessage(testMsgs, "Out");
		testFailure = getTestMessage(testMsgs, "Failure");
		testDone = getTestMessage(testMsgs, "Done");
		
		fileDumper = getFragment("FileDumper");
		fileDumpPath = getProperty(fileDumper, "DumpPath");
		
		testHarness = getThing("TestHarness");
		testHarnesSendCharsEntry = (ActionBlock)findState(testHarness, "Chars").getEntry();
		
		testSynchronizer = getThing("Synchronizer");
		testSynchronizerListen = findState(testSynchronizer, "Listen");
		testSynchronizerCheckCompleted = getFunction(testSynchronizer, "CheckCompleted");
		boolType = getPrimitiveType("Boolean");
		
		inputoutputs = GeneralTestInputOutput.getAllInputOutputs(this);
	}
	// TODO: Return some assertion errors from the void methods?
	
	/* --- Helper methods for finding references to things --- */
	public PrimitiveType getPrimitiveType(String name) {
		for (Type type : ThingMLHelpers.allTypes(model)) {
			if (type instanceof PrimitiveType) {
				PrimitiveType pType = (PrimitiveType)type;
				if (pType.getName().equals(name))
					return pType;
			}
		}
		return null;
	}
	
	public Thing getThing(String name) {
		for (Thing thing : ThingMLHelpers.allThings(model)) {
			if (thing.getName().equals(name))
				return thing;
		}
		return null;
	}
	
	public Thing getFragment(String name) {
		for (Thing thing : ThingMLHelpers.allThings(model)) {
			if (thing.getName().equals(name) && thing.isFragment())
				return thing;
		}
		return null;
	}

	public Port getTestPort(Thing thing, String name) {
		for (Port port : thing.getPorts()) {
			if (port.getName().equals(name)) return port;
		}
		return null;
	}
	
	public Message getTestMessage(Thing testMsgs, String name) {
		for (Message msg : testMsgs.getMessages()) {
			if (msg.getName().equals(name)) return msg;
		}
		return null;
	}
	
	public boolean isTestThing(Thing thing) {
		return (!thing.isFragment() 
				&& AnnotatedElementHelper.hasAnnotation(thing, "test")
				&& ThingMLHelpers.allThingFragments(thing).contains(testFragment)
				);
	}

	public Property getProperty(Thing thing, String name) {
		for (Property prop : thing.getProperties()) {
			if (prop.getName().equals(name)) return prop;
		}
		return null;
	}
	
	public Function getFunction(Thing thing, String name) {
		for(Function func : ThingMLHelpers.allFunctions(thing)) {
			if (func.getName().equals(name))
				return func;
		}
		return null;
	}
	
	public State findState(Collection<? extends StateContainer> containers, String name) {
		for (StateContainer container : containers) {
			// Check if composite
			if (container instanceof CompositeState && container.getName().equals(name)) return (State)container;
			
			// Check all sub-states
			Collection<CompositeState> subComposits = new ArrayList<CompositeState>();
			for (State sub : container.getSubstate()) {
				if (sub.getName().equals(name)) return sub;
				if (sub instanceof CompositeState) subComposits.add((CompositeState)sub);
			}
			State subCompositsFind = findState(subComposits, name);
			if (subCompositsFind != null) return subCompositsFind;
			
			// Check regions and sessions
			if (container instanceof CompositeState) {
				CompositeState comp = (CompositeState)container;
				State regionSessionFind = findState(comp.getRegion(), name);
				if (regionSessionFind != null) return regionSessionFind;
				regionSessionFind = findState(comp.getSession(), name);
				if (regionSessionFind != null) return regionSessionFind;
			}
		}
		
		return null;
	}
	
	public State findState(Thing thing, String name) {
		return findState(thing.getBehaviour(),name);
	}
	
	/* --- Helper methods for finding types/instances --- */
	
	// Returns all Things that includes the Test-type and has defined input/output chars
	public Collection<Thing> allTestThings() {
		Collection<Thing> testThings = new ArrayList<Thing>();
		
		for (Thing thing : ThingMLHelpers.allThings(model)) {
			if (isTestThing(thing)) testThings.add(thing);
		}
		
		return testThings;
	}
	
	// Returns all instances of Things that includes the Test-type and has defined input/output chars
	public Collection<Instance> allTestThingInstances() {
		Collection<Instance> testInstances = new ArrayList<Instance>();
		
		for (Instance instance : ThingMLHelpers.allInstances(config)) {
			Thing thing = instance.getType();
			if (isTestThing(thing)) testInstances.add(instance);
		}
		
		return testInstances;
	}
	
	// Adds instances for all test things
	public void addTestTingInstances() {
		// Add plain instances for the tests that don't have an instance yet
		for (GeneralTestInputOutput inputOutput : inputoutputs) {
			if (inputOutput.instance == null) {
				Instance instance = ThingMLFactory.eINSTANCE.createInstance();
				instance.setName(inputOutput.name);
				instance.setType(inputOutput.type);
				config.getInstances().add(instance);
				
				inputOutput.instance = instance;
			}
		}
		// Copy instances so that each test input-output has its own copy
		for (GeneralTestInputOutput inputOutput : inputoutputs) {
			boolean first = true;
			for (GeneralTestInputOutput.Test test : inputOutput.getTests()) {
				if (first) {
					// The first test can use the original instance
					test.setInstance(inputOutput.instance);
					first = false;
				} else {
					// For the rest, we need to make a full copy
					test.setInstance(copyInstanceConfiguration(inputOutput.instance, test));
				}
			}
		}
	}
	private Instance copyInstanceConfiguration(Instance original, GeneralTestInputOutput.Test test) {
		// Create a copy of the original instance
		Instance instance = EcoreUtil2.copy(original);
		instance.setName(test.createInstanceName(original));
		config.getInstances().add(instance);
		
		// Create copies of all property assignments
		Collection<ConfigPropertyAssign> newPropAssigns = new ArrayList<ConfigPropertyAssign>();
		for (ConfigPropertyAssign propAssign : config.getPropassigns()) {
			if (propAssign.getInstance().equals(original)) {
				ConfigPropertyAssign copy = EcoreUtil2.copy(propAssign);
				copy.setInstance(instance);
				newPropAssigns.add(copy);
			}
		}
		config.getPropassigns().addAll(newPropAssigns);
		
		// Create copies of all connectors
		Collection<AbstractConnector> newConnectors = new ArrayList<AbstractConnector>();
		for (AbstractConnector connector : config.getConnectors()) {
			if (connector instanceof Connector) {
				if (((Connector)connector).getCli().equals(original)) {
					// Receiving end of connector
					Connector copy = EcoreUtil2.copy((Connector)connector);
					copy.setCli(instance);
					newConnectors.add(copy);
				} else if (((Connector)connector).getSrv().equals(original)) {
					// Sending end of connector
					Connector copy = EcoreUtil2.copy((Connector)connector);
					copy.setSrv(instance);
					newConnectors.add(copy);
				}
			} else if (connector instanceof ExternalConnector) {
				if (((ExternalConnector)connector).getInst().equals(original)) {
					ExternalConnector copy = EcoreUtil2.copy((ExternalConnector)connector);
					copy.setInst(instance);
					newConnectors.add(copy);
				}
			}
		}
		config.getConnectors().addAll(newConnectors);
		
		return instance;
	}

	// Generates a thing to send input to all the test thing instances
	public void addTestThingHarness() {
		// Add a harness instance
		Instance harness = ThingMLFactory.eINSTANCE.createInstance();
		harness.setName("harness_generated");
		harness.setType(testHarness);
		config.getInstances().add(harness);
		
		// Add code to send input to all test instances
		for (GeneralTestInputOutput inputOutput : inputoutputs) {
			for (GeneralTestInputOutput.Test test : inputOutput.getTests()) {
				// Add a port for this instance
				ProvidedPort port = ThingMLFactory.eINSTANCE.createProvidedPort();
				port.setName(test.getInstance().getName());
				port.getSends().add(testIn);
				port.getSends().add(testDone);
				testHarness.getPorts().add(port);
				// Add messages to send in the entry block
				for (char c : test.getInput().toCharArray()) {
					SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
					send.setPort(port);
					send.setMessage(testIn);
					ExternExpression character = ThingMLFactory.eINSTANCE.createExternExpression();
					character.setExpression("\'"+c+"\'");
					send.getParameters().add(character);
					testHarnesSendCharsEntry.getActions().add(send);
				}
				SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
				send.setPort(port);
				send.setMessage(testDone);
				testHarnesSendCharsEntry.getActions().add(send);
				// Add a connector between harness and this instance
				Connector con = ThingMLFactory.eINSTANCE.createConnector();
				con.setSrv(harness); con.setProvided(port);
				con.setCli(test.getInstance()); con.setRequired(testPort);
				config.getConnectors().add(con);
			}
		}
	}
	
	// Generates all the file dumpers for tests
	public void addTestFileDumpers() {
		for (GeneralTestInputOutput inputOutput : inputoutputs) {
			for (GeneralTestInputOutput.Test test : inputOutput.getTests()) {
				// Set path property
				ConfigPropertyAssign pathAssign = ThingMLFactory.eINSTANCE.createConfigPropertyAssign();
				pathAssign.setInstance(test.instance);
				pathAssign.setProperty(fileDumpPath);
				StringLiteral pathLiteral = ThingMLFactory.eINSTANCE.createStringLiteral();
				pathLiteral.setStringValue(inputOutput.getDumpfileName(test));
				pathAssign.setInit(pathLiteral);
				config.getPropassigns().add(pathAssign);
			}
		}
	}

	// Generate the synchronizer to stop program when all tests are finished
	public void addSynchronizer() {
		// Find the completed variable in the checkCompleted function
		LocalVariable completedVar = null;
		ActionBlock checkCompletedBody = (ActionBlock)testSynchronizerCheckCompleted.getBody();
		for (Action act : checkCompletedBody.getActions()) {
			if (act instanceof LocalVariable)
				completedVar = (LocalVariable)act;
		}
		
		// Add instance of the synchronizer
		Instance syncronizerInstance = ThingMLFactory.eINSTANCE.createInstance();
		syncronizerInstance.setName("synchronizer_generated");
		syncronizerInstance.setType(testSynchronizer);
		config.getInstances().add(syncronizerInstance);
		
		// Add stuff for all test thing instances
		for (Instance testInstance : allTestThingInstances()) {
			// Add property to set if this test is finished
			Property completed = ThingMLFactory.eINSTANCE.createProperty();
			completed.setName(testInstance.getName()+"_finished");
			TypeRef completedTypeRef = ThingMLFactory.eINSTANCE.createTypeRef();
			completedTypeRef.setType(boolType);
			completed.setTypeRef(completedTypeRef);
			BooleanLiteral completedInit = ThingMLFactory.eINSTANCE.createBooleanLiteral();
			completedInit.setBoolValue(false);
			completed.setInit(completedInit);
			testSynchronizer.getProperties().add(completed);
			// Add port to receive testEnd or testFailure from instance
			ProvidedPort port = ThingMLFactory.eINSTANCE.createProvidedPort();
			port.setName(testInstance.getName()+"_result");
			port.getReceives().add(testDone);
			testSynchronizer.getPorts().add(port);
			// Add internal event to set finished property on reception of done message
			InternalTransition transition = ThingMLFactory.eINSTANCE.createInternalTransition();
			ReceiveMessage doneEvent = ThingMLFactory.eINSTANCE.createReceiveMessage();
			doneEvent.setPort(port);
			doneEvent.setMessage(testDone);
			transition.getEvent().add(doneEvent);;
			VariableAssignment setTrue = ThingMLFactory.eINSTANCE.createVariableAssignment();
			setTrue.setProperty(completed);
			BooleanLiteral completedTrue = ThingMLFactory.eINSTANCE.createBooleanLiteral();
			completedTrue.setBoolValue(true);
			setTrue.setExpression(completedTrue);
			transition.setAction(setTrue);
			testSynchronizerListen.getInternal().add(transition);
			// Add check to the property in the checkCompleted function
			ConditionalAction checkIf = ThingMLFactory.eINSTANCE.createConditionalAction();
			NotExpression notExpr = ThingMLFactory.eINSTANCE.createNotExpression();
			PropertyReference propRef = ThingMLFactory.eINSTANCE.createPropertyReference();
			propRef.setProperty(completed);
			notExpr.setTerm(propRef);
			checkIf.setCondition(notExpr);
			VariableAssignment setFalse = ThingMLFactory.eINSTANCE.createVariableAssignment();
			setFalse.setProperty(completedVar);
			BooleanLiteral completedVarFalse = ThingMLFactory.eINSTANCE.createBooleanLiteral();
			completedVarFalse.setBoolValue(false);
			setFalse.setExpression(completedVarFalse);
			checkIf.setAction(setFalse);
			checkCompletedBody.getActions().add(checkCompletedBody.getActions().size()-1, checkIf);
			// Add connectors in configuration
			Connector endCon = ThingMLFactory.eINSTANCE.createConnector();
			endCon.setCli(testInstance); endCon.setRequired(testPort);
			endCon.setSrv(syncronizerInstance); endCon.setProvided(port);
			config.getConnectors().add(endCon);
		}
	}
	
	
	// Helper class to keep track of result input-outputs when the testcase is run
	public static class GeneralTestInputOutput {
		private Thing type;
		private String name;
		private Instance instance;
		
		private Collection<Test> tests;
		
		private GeneralTestInputOutput(Instance instance) {
			this(instance.getType(), instance.getName());
			this.instance = instance;
		}
		private GeneralTestInputOutput(Thing instanceType, String instanceName) {
			type = instanceType;
			name = instanceName;
			
			tests = new ArrayList<GeneralTestInputOutput.Test>();
			
			// Get the inputs and expected outputs
			int index = 1;
			for (String annotation : AnnotatedElementHelper.annotation(type, "test")) {
				String[] inputOutput = annotation.split("#");
				if (inputOutput.length == 2) {
					tests.add(new Test("test"+index,inputOutput[0],inputOutput[1]));
					index++;
				}
			}
		}
		
		public Collection<Test> getTests() { return tests; };
		
		public String getFrom() { return instance.getName() + " : " + type.getName(); }
		public String getDumpfileName(Test test) { return name+"_"+test.name+".dump"; } 
		public String getInput(Test test) { return test.getInput(); }
		public String getExpected(Test test) { return test.getExpected(); }
		
		public static Collection<GeneralTestInputOutput> getAllInputOutputs(GeneralTestHelper helper) {
			Collection<GeneralTestInputOutput> result = new ArrayList<GeneralTestInputOutput>();
			
			// Find all existing instances of test things
			Collection<Instance> existing = ThingMLHelpers.allInstances(helper.config);
			for (Instance instance : existing) {
				if (helper.isTestThing(instance.getType()))
					result.add(new GeneralTestInputOutput(instance));
			}
			
			// Get all names, so that we can avoid collisions
			Set<String> names = TestTypesHelper.getAllNames(existing);
			
			// Add the test things that doesn't have instances
			for (Thing testThing : helper.allTestThings()) {
				// Check if there is already an instance
				if (TestTypesHelper.instancesHasThing(existing, testThing))
					continue;
				// Generate a new name
				String instanceName = TestTypesHelper.generateAndAddNewName("testThing", names);
				result.add(new GeneralTestInputOutput(testThing, instanceName));
			}
			
			return result;
		}
	
		public static class Test {
			private String name;
			private String input;
			private String output;
			private Instance instance;
			
			public Test(String fileName, String testInput, String testOutput) {
				name = fileName;
				input = testInput.trim();
				output = testOutput.trim();
				instance = null;
			}
			
			public String getInput() { return input; }
			public String getExpected() { return output; }
			
			public String createInstanceName(Instance original) { return original.getName()+"_"+name; }
			public String getInstanceName() { return instance.getName(); }
			
			public Instance getInstance() { return instance; }
			public void setInstance(Instance testInstance) {
				if (instance != null) throw new RuntimeException("Instance already set");
				instance = testInstance;
			}
		}
	}
}
