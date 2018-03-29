/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.spi;

import java.util.List;
import java.util.Set;

import org.thingml.compilers.Context;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Protocol;

/**
 *
 * @author sintef
 */
public abstract class SerializationPlugin {

    public Context context;
    public Configuration configuration;
    public Protocol protocol;
    
    public SerializationPlugin() {
    	super();
    }

    abstract public SerializationPlugin clone();

    public void setConfiguration(Configuration cfg) {
        this.configuration = cfg;
    }

    public void setContext(Context ctx) {
        this.context = ctx;
    }

    public void setProtocol(Protocol prot) {
        this.protocol = prot;
    }

    public String getIncludes() {
        return "";
    }

    public String generateSubFunctions() {
        return "";
    }
    
    /* ------------ Plugin Body (Mandatory) ------------ */

    /* Methods: generateSerialization
     * 
     * Parameters:
     *      - builder : StringBuilder in which the generated 
     *                  code will be put.
     *      - bufferName : name of the buffer that will contain
     *                    the serialization.
     *                    the size of buffer.
     *      - m : Model of the message to be serialized
     * 
     * Results:
     *      builder will contain code declaring a buffer named
     *      bufferName of the require size. The buffer will 
     *      contain a serialization of message m. The size of
     *      the buffer is returned by the method.
     * 
     * Note: All parameters annotated as ignored must be ignored.
    */
    public abstract String generateSerialization(StringBuilder builder, String bufferName, Message m, ExternalConnector eco);
    
    public String generateSerialization(StringBuilder builder, String bufferName, Message m) {
        return generateSerialization(builder, bufferName, m, null);
    }

    /* Methods: generateParserBody
     * 
     * Parameters:
     *      - builder : StringBuilder in which the generated 
     *                  code will be put.
     *      - bufferName : name of the buffer that contains
     *                    the raw information to be parsed
     *      - bufferSizeName : name of the variable that contains
     *                    the size of buffer.
     *      - messages : Model of the messages to be parsed
     *      - sender : name of the variable describing the external port
     * 
     * Results:
     *      builder will contain code parsing buffer, and sending
     *      a ThingML message to connected ports.
     * 
     * Note: All paramters annotated as ignored must be ignored.
    */
    public abstract void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender, ExternalConnector eco);
    public void generateParserBody(StringBuilder builder, String bufferName, String bufferSizeName, Set<Message> messages, String sender) {
        generateParserBody(builder, bufferName, bufferSizeName, messages, sender, null);
    }

    /* ------------ Plugin Info (Mandatory) ------------ */

    /*
     * In case of overlapping protocol support, the
     * choice of plugin will be specified with the
     * annotation @plugin "pluginID"
    */
    public abstract String getPluginID();

    public abstract List<String> getTargetedLanguages();
    
    public abstract List<String> getSupportedFormat();
    
    
    
    
    /* ------------ Rule Checkng (Optional) ------------
     * Should be overridden if the plugin need to perform
     * some specific checking.
    */


    public String getName() {
        return this.getPluginID() + " plugin's rules";
    }


    public String getDescription() {
        return "Check that " + this.getPluginID() + " plugin can be used.";
    }

}
