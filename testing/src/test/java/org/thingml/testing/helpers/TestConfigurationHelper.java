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
package org.thingml.testing.helpers;

import java.util.ArrayList;
import java.util.Collection;

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;

public class TestConfigurationHelper {
	public static class FileDumper {
		private FileDumper() {}
		
		private Thing thing;
		private ActionBlock dumpWrite;
		private Property path;
		
		public Thing getThing() { return thing; }
		public ActionBlock getDumpWrite() { return dumpWrite; }
		public Property getPath() { return path; }
		
		static FileDumper create(Thing dumper) {
			FileDumper dump = new FileDumper();
			dump.thing = dumper;
			// Find references to the functions
			for (Function func : dumper.getFunctions()) {
				switch (func.getName()) {
					case "DumpWrite":
						dump.dumpWrite = (ActionBlock)func.getBody();
						break;
				}
			}
			// Find property references
			for (Property prop : dumper.getProperties()) {
				switch (prop.getName()) {
					case "DumpPath":
						dump.path = prop;
						break;
				}
			}
			
			return dump;
		}
	}
	
	public static FileDumper getFileDumperReference(Configuration config) {
		for (Thing thing : ThingMLHelpers.allThings(ThingMLHelpers.findContainingModel(config))) {
			if (thing.isFragment() && thing.getName().equals("FileDumper"))
				return FileDumper.create(thing);
		}
		return null;
	}
	
	public static Collection<ActionBlock> getStopExecutionFunctionBodies(Configuration config) {
		Collection<ActionBlock> bodies = new ArrayList<ActionBlock>();
		for (Thing thing : ThingMLHelpers.allThings(ThingMLHelpers.findContainingModel(config))) {
			for (Function func : ThingMLHelpers.allFunctions(thing)) {
				if (func.getName().equals("StopExecution")
					&& func.getBody() instanceof ActionBlock
					&& func.getParameters().size() == 1
					&& func.getParameters().get(0).getName().equals("Code")
				   ) {
					bodies.add((ActionBlock)func.getBody());
				}
			}
		}
		
		return bodies;
	}
}
