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

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

public class PlatformHelpers {
	
	public static Thing findFileDumperThing(ThingMLModel model) {
		for (Thing thing : ThingMLHelpers.allThings(model))
			if (thing.isFragment() && thing.getName().equals("FileDumper"))
				return thing;
		return null;
	}
	public static ActionBlock findFileDumperFunctionBody(ThingMLModel model) {
		Thing dumper = findFileDumperThing(model);
		if (dumper != null)
			for (Function func : dumper.getFunctions())
				if (func.getName().equals("DumpWrite")
					&& func.getBody() instanceof ActionBlock
					&& func.getParameters().size() == 1
					&& func.getParameters().get(0).getName().equals("C")
				   )
						return (ActionBlock)func.getBody();
		return null;
	}
	public static Property findFileDumperPathProperty(ThingMLModel model) {
		Thing dumper = findFileDumperThing(model);
		if (dumper != null)
			for (Property prop : dumper.getProperties())
				if (prop.getName().equals("DumpPath"))
					return prop;
		return null;
	}
	
	
	public static Collection<Entry<Thing,ActionBlock>> findStopExecutionFunctionBodies(ThingMLModel model) {
		Collection<Entry<Thing,ActionBlock>> bodies = new ArrayList<Entry<Thing,ActionBlock>>();
		for (Thing thing : ThingMLHelpers.allThings(model))
			for (Function func : ThingMLHelpers.allFunctions(thing))
				if (func.getName().equals("StopExecution")
					&& func.getBody() instanceof ActionBlock
					&& func.getParameters().size() == 1
					&& func.getParameters().get(0).getName().equals("Code")
				   )
						bodies.add(new SimpleEntry<>(thing, (ActionBlock)func.getBody()));
		return bodies;
	}
}
