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
package org.thingml.xtext.helpers;

import org.thingml.xtext.thingML.*;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ffl on 10.05.2016.
 */
public class HandlerHelper {


    public static Set<AbstractMap.SimpleImmutableEntry<Port, ReceiveMessage>> allTriggeringPorts(Handler self) {
        Set<AbstractMap.SimpleImmutableEntry<Port, ReceiveMessage>> result = new HashSet<AbstractMap.SimpleImmutableEntry<Port,ReceiveMessage>>();
        for(Event e : self.getEvent()) {
            if (e instanceof  ReceiveMessage) {
                ReceiveMessage r = (ReceiveMessage)e;
                result.add(new AbstractMap.SimpleImmutableEntry<Port, ReceiveMessage>(r.getPort(), r));
            }
        }
        return result;
    }

}
