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
package org.thingml.testing.tests.general;

import java.io.Serializable;

import org.thingml.xtext.thingML.Thing;

public class GeneralTestInputOutput implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected GeneralTestInputOutput() {}
	
	public String typeName;
	public String dumpPath;
	public String input;
	public String expectedOutput;

	public static GeneralTestInputOutput create(Thing type, String annotation, String dumpPath) {
		if (!annotation.isEmpty()) {
			String[] inputOutput = annotation.split("#");
			if (inputOutput.length == 2) {
				GeneralTestInputOutput inst = new GeneralTestInputOutput();
				inst.typeName = type.getName();
				inst.dumpPath = dumpPath;
				inst.input = inputOutput[0].trim();
				inst.expectedOutput = inputOutput[1].trim();
				return inst;
			}
		}
		return null;
	}
}
