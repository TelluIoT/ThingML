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
package org.thingml.xtext.helpers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.ISerializer;
import org.thingml.xtext.ThingMLStandaloneSetup;

import com.google.inject.Inject;

public class ThingMLSerializer {

	// TODO: This Serializer should use the ThingMLFormatter to produce
	// code that is properly pretty printed (same as Ctrl+Shift+F in eclipse
	// and the saving of EMF Resources which actually works).
	// I do not understand why this serializer defaults to OneWhitespaceFormatter
	// See issue #272.
	
	@Inject
	ISerializer serializer;
	
	private static ThingMLSerializer _instance = null;
	
	public static ThingMLSerializer getInstance() {
		if (_instance == null) _instance = new ThingMLSerializer();
		return _instance;
	}
	
	
	private ThingMLSerializer() {
		ThingMLStandaloneSetup setup = new ThingMLStandaloneSetup();
		setup.createInjectorAndDoEMFRegistration();
		setup.createInjector().injectMembers(this);
	}
	
	public String toString(EObject object) {
		return serializer.serialize(object, SaveOptions.newBuilder().format().getOptions());
	}
	
}
