package org.thingml.compilers;

import org.sintef.thingml.Configuration;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by ffl on 23.11.14.
 */
public abstract class AbstractThingMLCompiler {

    /**************************************************************
     * META-DATA about this particular compiler
     **************************************************************/
    public abstract String getPlatform();
    public abstract String getName();

    /**************************************************************
     * Parameters common to all compilers
     **************************************************************/
    private File outputDirectory = null;

    public void setOutputDirectory(File outDir) {
        if (!outDir.exists()) throw new Error("ERROR: The output directory does not exist (" + outDir.getAbsolutePath() + ").");
        if (!outDir.isDirectory()) throw new Error("ERROR: The output directory has to be a directory (" + outDir.getAbsolutePath() + ").");
        if (!outDir.canWrite()) throw new Error("ERROR: The output directory is not writable (" + outDir.getAbsolutePath() + ").");
        outputDirectory = outDir;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    private OutputStream messageStream;
    private OutputStream errorStream;

    public OutputStream getErrorStream() {
        return errorStream;
    }

    public void setErrorStream(OutputStream errorStream) {
        this.errorStream = errorStream;
    }

    public OutputStream getMessageStream() {
        return messageStream;
    }

    public void setMessageStream(OutputStream messageStream) {
        this.messageStream = messageStream;
    }

    /**************************************************************
     * Entry point of the compiler
     **************************************************************/
    public abstract boolean compile(Configuration cfg);

}
