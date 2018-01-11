package org.thingml.generated.gui;

import no.sintef.jasm.Port;
import no.sintef.jasm.ext.EventType;

import java.util.Map;

public class Command {

    private Port port;
    private EventType event;
    private Map<String, Object> params;

    public Command(Port port, EventType event, Map<String, Object> params) {
        this.port = port;
        this.event = event;
        this.params = params;
    }

    public void execute() {
        port.send(event.instantiate(params));
    }

    @Override
    public String toString() {
        String result = port.getName() + "!" + event.getName() + "(";
        int i = 0;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (i > 0)
                result += ",";
            result += entry.getKey() + "=" + entry.getValue();
            i++;
        }
        result += ")";
        return result;
    }
}
