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
package org.thingml.xtext.validation.rules;

import org.eclipse.xtext.validation.ValidationMessageAcceptor;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.validation.AbstractThingMLValidator;
import org.thingml.xtext.validation.Checker;
import org.thingml.xtext.validation.Rule;

/**
 *
 * @author sintef
 */
public class InternalTransitions extends Rule {

    public InternalTransitions(AbstractThingMLValidator v) {
		super(v);
	}

	@Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.ERROR;
    }

    @Override
    public String getName() {
        return "Internal Transitions";
    }

    @Override
    public String getDescription() {
        return "Check that Internal transition are triggered by event and/or have guard";
    }

    @Override
    public void check(ThingMLModel model, Checker checker) {
        for (Thing thing : ThingMLHelpers.allThings(model)) {
            check(thing, checker);
        }
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            check(t, checker);
        }
    }

    private void check(Thing t, Checker checker) {
        for (CompositeState sm : ThingMLHelpers.allStateMachines(t)) {
            for (Handler h : StateHelper.allEmptyHandlers(sm)) {
                if (h instanceof InternalTransition) {
                    if (h.getGuard() == null) {
                    	final String msg = "Empty Internal Transition without guard.";
                        checker.addGenericError(msg, h);
    					validator.acceptError(msg, h, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
                    } else {
                        checker.addGenericNotice("Empty Internal Transition.", h);
                    }
                }
            }
        }
    }

}
