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
package org.thingml.compilers.checker.genericRules;

import org.sintef.thingml.Configuration;
import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.sintef.thingml.Thing;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

import java.util.ArrayList;

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
                    else
                        checker.addError("Multiple definition of message " + m.getName(), p);
                }
                for (Message m : p.getSends()) {
                    if (!sendList.contains(m.getName()))
                        sendList.add(m.getName());
                    else
                        checker.addError("Multiple definition of message " + m.getName(), p);
                }
            }
        }
    }
}
