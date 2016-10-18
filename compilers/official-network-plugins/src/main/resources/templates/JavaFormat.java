package org.thingml.generated.network;

import org.thingml.java.ext.*;

import java.util.Arrays;

public interface Format<T> {
    Event instantiate(T payload);
    T format(Event e);
}