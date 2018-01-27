package org.thingml.generated.network;

import no.sintef.jasm.ext.*;

import java.util.Arrays;

public interface BinaryJava extends Format<Byte[]> {
    Event instantiate(Byte[] payload);
    Byte[] format(Event e);
}