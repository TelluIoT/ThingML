package org.thingml.generated.network;

import no.sintef.jasm.ext.*;

import java.util.Arrays;

public interface Format<T> {
    Event instantiate(T payload);
    T format(Event e);
}