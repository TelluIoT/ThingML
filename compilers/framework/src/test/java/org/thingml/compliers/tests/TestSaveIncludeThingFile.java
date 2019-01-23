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
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.ThingMLModel;

public class TestSaveIncludeThingFile extends LoadModelTestsCommon {

	@Test
	public void test() {
		// Get the .thingml file from resources
		File test = new File(this.getClass().getResource("/SimpleIncludeModel.thingml").getFile());
		
	
		// Load the model
		ThingMLModel model = ThingMLCompiler.loadModel(test);
		assertFalse("Loaded model is not null", model == null);
		
		// Check that the model is correct
		checkSimpleIncludeModel(model);
		
		// Test the saving
		try {
			// Create temporary file to save to
			File tmp = File.createTempFile("thingml-model", ".thingml");
			
			ThingMLModel flatmodel = ThingMLHelpers.flattenModel(model);
			
			// Save the ThingML model
			ThingMLCompiler.saveAsThingML(flatmodel, tmp.toString());
			
			// Re-load the saved model
			ThingMLModel savedModel = ThingMLCompiler.loadModel(tmp);
			
			// Check that the model is correct
			checkSimpleIncludeModelFlat(savedModel);
			
		} catch (IOException e) {
			fail("Could not create temporary file to save to");
		}
	}

}
