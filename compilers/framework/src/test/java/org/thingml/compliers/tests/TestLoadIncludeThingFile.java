package org.thingml.compliers.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.xtext.thingML.ThingMLModel;

public class TestLoadIncludeThingFile extends LoadModelTestsCommon {

	@Test
	public void test() {
		// Get the .thingml file from resources
		File test = new File(this.getClass().getResource("/SimpleIncludeModel.thingml").getFile());
		
		// Load the model
		ThingMLModel model = ThingMLCompiler.loadModel(test);
		assertFalse("Loaded model is not null", model == null);
		
		//checkSimpleModel(model);
	}

}
