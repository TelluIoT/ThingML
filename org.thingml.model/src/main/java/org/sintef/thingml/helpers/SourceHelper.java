package org.sintef.thingml.helpers;

import org.sintef.thingml.Source;
import org.sintef.thingml.Stream;
import org.sintef.thingml.constraints.ThingMLHelpers;

/**
 * Created by ffl on 10.05.2016.
 */
public class SourceHelper {


    public static String qname(Source self, String separator) {
        Stream stream = ThingMLHelpers.findContainingStream(self);
        return stream.getName();
    }

}
