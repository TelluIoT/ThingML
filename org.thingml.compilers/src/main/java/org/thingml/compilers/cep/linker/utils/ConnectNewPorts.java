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
package org.thingml.compilers.cep.linker.utils;

import org.sintef.thingml.*;

/**
 * @author ludovic
 */
public class ConnectNewPorts {
    private RequiredPort requiredPort;
    private ProvidedPort providedPort;
    private Thing owner;

    public ConnectNewPorts(String requiredPortName, String providedPortName, Thing owner) {
        this.owner = owner;

        requiredPort = ThingmlFactory.eINSTANCE.createRequiredPort();
        requiredPort.setName(requiredPortName);
        requiredPort.setOwner(this.owner);

        providedPort = ThingmlFactory.eINSTANCE.createProvidedPort();
        providedPort.setName(providedPortName);
        providedPort.setOwner(this.owner);
    }

    public void connect(Configuration conf) {
        InstanceRef instanceRefSend = ThingmlFactory.eINSTANCE.createInstanceRef();
        InstanceRef instanceRefRcve = ThingmlFactory.eINSTANCE.createInstanceRef();
        for(Instance instance : conf.getInstances()) {
            if(instance.getType().getName().equals(owner.getName())) {
                instanceRefSend.setInstance(instance);
                instanceRefRcve.setInstance(instance);
                break;
            }
        }

        Connector connector = ThingmlFactory.eINSTANCE.createConnector();
        connector.setProvided(providedPort);
        connector.setRequired(requiredPort);
        connector.setCli(instanceRefSend);
        connector.setSrv(instanceRefRcve);
        conf.getConnectors().add(connector);
    }

    public RequiredPort getRequiredPort() {
        return requiredPort;
    }

    public ProvidedPort getProvidedPort() {
        return providedPort;
    }
}
