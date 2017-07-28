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
import java.util.HashSet;
import java.util.Set;

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.NamedElement;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

public class TestTypesHelper {
	
	// Returns test fragments given by name
	public static Thing getTestFragment(ThingMLModel model, String name) {
		for (Thing thing : ThingMLHelpers.allThings(model)) {
			if (thing.getName().equals(name) && thing.isFragment())
				return thing;
		}
		return null;
	}
	
	// Returns the 'thing fragment Test' type
	public static Thing getTestThingFragment(ThingMLModel model) { return getTestFragment(model, "Test"); }
	public static Thing getTestThingFragment(Configuration config) {
		ThingMLModel model = ThingMLHelpers.findContainingModel(config);
		return getTestThingFragment(model);
	}
	
	// Checks if a Thing type is a Test thing
	
	
	// Checks if a collection of instances have an instance for a given Thing type
	public static boolean instancesHasThing(Collection<Instance> instances, Thing type) {
		for (Instance instance : instances) {
			if (instance.getType().equals(type)) return true;
		}
		return false;
	}
	
	// Returns all instances of a given Thing type
	public static Collection<Instance> getAllInstancesOf(Configuration config, Thing type) {
		Collection<Instance> instances = new ArrayList<Instance>();
		for (Instance instance : ThingMLHelpers.allInstances(config)) {
			if (type.isFragment() && ThingMLHelpers.allThingFragments(instance.getType()).contains(type))
				instances.add(instance);
			else if (!type.isFragment() && instance.getType().equals(type))
				instances.add(instance);
		}
		return instances;
	}
	
	// Returns the set of names of a list of named elements
	public static Set<String> getAllNames(Collection<? extends NamedElement> elements) {
		Set<String> names = new HashSet<String>();
		for (NamedElement element : elements) {
			names.add(element.getName());
		}
		return names;
	}
	
	// Generates a new name based on a set of already used names, and a given prefix
	// Also adds the name to the list - so that we don't have to do it manually later
	public static String generateAndAddNewName(String prefix, Set<String> existingNames) {
		int number = 0;
		String newName;
		do {
			number ++;
			newName = prefix + number + "_generated";
		} while (existingNames.contains(newName));
		existingNames.add(newName);
		return newName;
	}
}