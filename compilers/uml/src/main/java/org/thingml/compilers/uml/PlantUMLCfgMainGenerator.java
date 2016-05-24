/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingml.compilers.uml;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgMainGenerator;

/**
 * Created by bmori on 10.12.2014.
 */
public class PlantUMLCfgMainGenerator extends CfgMainGenerator {

    @Override
    public void generateMainAndInit(Configuration cfg, ThingMLModel model, Context ctx) {
        final StringBuilder builder = ctx.getBuilder(cfg.getName() + "/docs/" + cfg.getName() + ".plantuml");
        builder.append("@startuml\n");
        builder.append("node \"" + cfg.getName() + "\"{\n");
        for (Instance i : ConfigurationHelper.allInstances(cfg)) {
            boolean hasPort = false;
            for (Port p : ThingMLHelpers.allPorts(i.getType())) {
                if (p instanceof ProvidedPort) {
                    builder.append(p.getName() + "_" + i.getName() + " - [" + i.getName() + "]\n");
                    hasPort = true;
                }
            }
            if (!hasPort) {
                builder.append("[" + i.getName() + "]\n");
            }
        }
        for (Connector c : ConfigurationHelper.allConnectors(cfg)) {
            builder.append("[" + c.getCli().getInstance().getName() + "] -> " + c.getProvided().getName() + "_" + c.getSrv().getInstance().getName() + " : " + c.getRequired().getName() + "\n");
        }
        for (ExternalConnector eco : ConfigurationHelper.getExternalConnectors(cfg)) {
            builder.append("[" + cfg.getName() + "_" + eco.getInst().getInstance().getName() + "] ..> " + eco.getProtocol() + "\n");
        }
        builder.append("}\n");
        builder.append("@enduml");
    }
}
