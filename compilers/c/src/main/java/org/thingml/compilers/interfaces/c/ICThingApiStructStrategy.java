package org.thingml.compilers.interfaces.c;

import org.sintef.thingml.Thing;
import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.c.CCompilerContext;

/**
 * Created by vassik on 04.11.16.
 */
public interface ICThingApiStructStrategy {

    void generateInstanceStruct(Thing thing, StringBuilder builder, CCompilerContext ctx, DebugProfile debugProfile);
}
