package org.thingml.networkplugins.c.posix;

import org.sintef.thingml.Message;
import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.SerializationPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by vassik on 06.10.16.
 */
public class PosixDummySerializationPlugin extends SerializationPlugin {
    CCompilerContext cctx;

    public PosixDummySerializationPlugin() {
        super();
    }

    @Override
    public SerializationPlugin clone() {
        return new PosixTextDigitSerializerPlugin();
    }

    @Override
    public void setContext(Context ctx) {
        context = ctx;
        cctx = (CCompilerContext) context;
    }

    @Override
    public String generateSerialization(StringBuilder builder, String bufferName, Message m) {
        return null;
    }

    @Override
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender) {

    }

    @Override
    public String getPluginID() {
        return "PosixDummySerializationPlugin";
    }

    @Override
    public List<String> getTargetedLanguages() {

        List<String> res = new ArrayList<>();
        res.add("posix");
        return res;
    }

    @Override
    public List<String> getSupportedFormat() {

        List<String> res = new ArrayList<>();
        res.add("DummySerializer");
        res.add("dummy");
        res.add("");
        res.add("null");
        return res;
    }
}
