package org.thingml.generated.network;

import no.sintef.jasm.ext.*;

import java.util.Arrays;

public interface StringJava extends Format<String> {
    Event instantiate(String payload);
    String format(Event e);
}