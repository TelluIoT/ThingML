package org.sintef.thingml.helpers;

import org.sintef.thingml.Event;
import org.sintef.thingml.Handler;
import org.sintef.thingml.Port;
import org.sintef.thingml.ReceiveMessage;

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
