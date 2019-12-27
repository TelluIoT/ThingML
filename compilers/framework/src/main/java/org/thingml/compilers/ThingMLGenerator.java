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
package org.thingml.compilers;

import java.io.File;
import java.io.OutputStream;

import org.thingml.xtext.thingML.ThingMLModel;

public interface ThingMLGenerator {

	
	ThingMLGenerator clone();

    /**
     * ***********************************************************
     * META-DATA about this particular compiler
     * ************************************************************
     */
    String getID();

    String getName();

    String getDescription();

    /**
     * ***********************************************************
     * Entry point of the compiler
     * ************************************************************
     */
    boolean compile(ThingMLModel model, String... options);
    
    OutputStream getErrorStream();
    void setErrorStream(OutputStream errorStream);

    OutputStream getMessageStream();
    void setMessageStream(OutputStream messageStream);

    File getOutputDirectory();
    void setOutputDirectory(File outDir);
  
    File getInputDirectory();
    void setInputDirectory(File inDir);	
}
