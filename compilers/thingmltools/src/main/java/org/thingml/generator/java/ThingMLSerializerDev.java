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
package org.thingml.generator.java;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.formatting.IFormatter;
import org.eclipse.xtext.formatting2.IFormatter2;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.ISerializer;
import org.thingml.xtext.ThingMLStandaloneSetup;

import com.google.inject.Inject;

public class ThingMLSerializerDev {

	@Inject
	ISerializer serializer;
	
	private static ThingMLSerializerDev _instance = null;
	
	public static ThingMLSerializerDev getInstance() {
		if (_instance == null) _instance = new ThingMLSerializerDev();
		return _instance;
	}
	
	
	private ThingMLSerializerDev() {
		ThingMLStandaloneSetup setup = new ThingMLStandaloneSetup();
		setup.createInjectorAndDoEMFRegistration().injectMembers(this);
	}
	
	public String toString(EObject object) {
		return serializer.serialize(object);
		//return serializer.serialize(object, SaveOptions.newBuilder().format().getOptions());
		//return serializer.serialize(object, SaveOptions.defaultOptions());
	}
	
}
