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
package org.thingml.xtext.validation.rules;

import java.util.ArrayList;

import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.validation.Checker;
import org.thingml.xtext.validation.Rule;

/**
 * Created by Alexandre Rio on 6/9/16.
 */
public class DuplicatedMessageInPort extends Rule {

	@Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.ERROR;
    }

    @Override
    public String getName() {
        return "Duplicated message in port";
    }

    @Override
    public String getDescription() {
        return "Check that there's no duplicated message in port definition";
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            for (Port p : t.getPorts()) {
                ArrayList<String> rcvList = new ArrayList<>();
                ArrayList<String> sendList = new ArrayList<>();
                for (Message m : p.getReceives()) {
                    if (!rcvList.contains(m.getName()))
                        rcvList.add(m.getName());
                    else {
                    	final String msg = "Multiple definition of message " + m.getName(); 
                        checker.addError(msg, p);
                    }
                }
                for (Message m : p.getSends()) {
                    if (!sendList.contains(m.getName()))
                        sendList.add(m.getName());
                    else {
                    	final String msg = "Multiple definition of message " + m.getName(); 
                        checker.addError(msg, p);
                    }
                }
            }
        }
    }
}
