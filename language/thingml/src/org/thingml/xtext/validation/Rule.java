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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.xtext.validation;



import org.eclipse.xtext.validation.Check;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.validation.Checker.InfoType;

/**
 *
 * @author sintef
 */
public abstract class Rule {

	protected AbstractThingMLValidator validator;
	
    public Rule(AbstractThingMLValidator v) {
    	validator = v;
    }

    public abstract InfoType getHighestLevel();

    public abstract String getName();

    public abstract String getDescription();

    @Check
    public abstract void check(Configuration cfg, Checker checker);

    @Check
    public void check(ThingMLModel model, Checker checker) {
        for (Configuration c : ThingMLHelpers.allConfigurations(model)) {
            //checker.do_check(c);
        	// FIXME: Why was this implemented like this?
        	// It should call the check of the rules shouldn't it?
        	// The way it was before broke calls to do_generic_checks from a Checker instance
        	check(c, checker);
        }
    }

}
