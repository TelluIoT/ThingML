package org.thingml.generated.network;

import org.thingml.java.ext.*;

import java.util.Arrays;

public interface BinaryJava extends Format<Byte[]> {
    Event instantiate(Byte[] payload);
    Byte[] format(Event e);
}