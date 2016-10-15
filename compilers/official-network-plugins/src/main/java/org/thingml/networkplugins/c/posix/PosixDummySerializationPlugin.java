/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
