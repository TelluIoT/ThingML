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
package org.thingml.compilers.cep.parser;

/**
 * @author ludovic
 */
public class JoinResult {
    private String portName;
    private String messageName;
    private String timeToWait;
    private String methodName;

    public JoinResult(String portName, String messageName, String timeToWait, String methodName) {
        this.portName = portName;
        this.messageName = messageName;
        this.timeToWait = timeToWait;
        this.methodName = methodName;
    }

    public String getPortName() {
        return portName;
    }

    public String getMessageName() {
        return messageName;
    }

    public String getTimeToWait() {
        return timeToWait;
    }

    public String getMethodName() {
        return methodName;
    }
}
