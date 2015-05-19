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
package org.thingml.compilers.main;

import org.sintef.thingml.*;
import org.thingml.compilers.Context;

/**
 * Created by bmori on 10.12.2014.
 */
public class PlantUMLMainGenerator extends MainGenerator {

    @Override
    public void generate(Configuration cfg, ThingMLModel model, Context ctx) {
        final StringBuilder builder = ctx.getBuilder(cfg.getName() + "/docs/" + cfg.getName() + ".plantuml");
        builder.append("@startuml\n");
        builder.append("node \"" + cfg.getName() + "\"{\n");
        for (Instance i : cfg.allInstances()) {
            for(Port p: i.getType().allPorts()) {
                if (p instanceof ProvidedPort) {
                    builder.append(p.getName() + "_" + i.getName() + " - [" + i.getName() + "]\n");
                }
            }
        }
        for (Connector c : cfg.allConnectors()) {
            builder.append("[" + c.getCli().getInstance().getName() + "] -> " + c.getProvided().getName() + "_" + c.getSrv().getInstance().getName() + " : " + c.getRequired().getName() + "\n");
        }
        builder.append("}\n");
        builder.append("@enduml");
    }
}
