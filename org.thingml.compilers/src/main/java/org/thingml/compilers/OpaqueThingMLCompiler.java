package org.thingml.compilers;

import org.sintef.thingml.Configuration;

import java.io.OutputStream;

/**
 * Created by ffl on 24.11.14.
 */
public class OpaqueThingMLCompiler extends AbstractThingMLCompiler {


    @Override
    public String getOutputDirectory() {
        return null;
    }

    @Override
    public OutputStream getMessageOutputStream() {
        return null;
    }

    @Override
    public OutputStream getErrorOutputStream() {
        return null;
    }

}
