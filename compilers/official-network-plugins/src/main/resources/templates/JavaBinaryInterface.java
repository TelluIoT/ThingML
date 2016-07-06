package org.thingml.generated.network;

import org.thingml.java.ext.*;

import java.util.Arrays;

public interface BinaryJava {
    Event instantiate(byte[] payload);
    byte[] toBytes(Event e);
}