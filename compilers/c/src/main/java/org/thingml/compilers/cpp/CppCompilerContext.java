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
package org.thingml.compilers.cpp;


import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.CCompilerContext;

/**
 * Created by rkumar
 */
@Deprecated
public abstract class CppCompilerContext extends CCompilerContext {
    
    public CppCompilerContext(ThingMLCompiler c) {
        super(c);
    }

    public String getCfgMainHeaderTemplate() {
        return getTemplateByID("cpptemplates/" + getCompiler().getID() + "_main_header.h");
    }
    
    public String getNetworkLibSerialRingTemplate() {
        if (getCompiler().getID().compareTo("arduinomf") == 0 || getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("cpptemplates/network_lib/arduino/Ring/ArduinoSerialForward.cpp");
        } else {
            return getTemplateByID("ctemplates/network_lib/posix/PosixSerialForward.c");
        }
    }

    
    public String getTimerTemplate() {
        if (getCompiler().getID().compareTo("arduinomf") == 0 || getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("cpptemplates/network_lib/arduino/Timer/Timer.cpp");
        } else {
            return getTemplateByID("");
        }
    }
    
    public String getTimerHeaderTemplate() {
    	if (getCompiler().getID().compareTo("arduinomf") == 0 || getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("cpptemplates/network_lib/arduino/Timer/Timer.h");
        } else {
            return getTemplateByID("");
        }
    }
    

    public String getNetworkLibSerialTemplate() {
        if (getCompiler().getID().compareTo("arduinomf") == 0 || getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("cpptemplates/network_lib/arduino/Serial/ArduinoSerialForward.cpp");
        } else {
            return getTemplateByID("ctemplates/network_lib/posix/PosixSerialForward.c");
        }
    }

    public String getNetworkLibNoBufSerialTemplate() {
        if (getCompiler().getID().compareTo("arduinomf") == 0 || getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("cpptemplates/network_lib/arduino/NoBufSerial/NoBufSerial.cpp");
        } else {
            return getTemplateByID("");
        }
    }

    public String getNetworkLibSerialHeaderTemplate() {
        if (getCompiler().getID().compareTo("arduinomf") == 0 || getCompiler().getID().compareTo("arduino") == 0) {
            return getTemplateByID("cpptemplates/network_lib/arduino/Serial/ArduinoSerialForward.h");
        } else {
            return getTemplateByID("ctemplates/network_lib/posix/PosixSerialForward.h");
        }
    }

    public String getCfgMainTemplate() {
        return getTemplateByID("cpptemplates/" + getCompiler().getID() + "_main.cpp");
    }

    public String getThingHeaderTemplate() {
        return getTemplateByID("cpptemplates/" + getCompiler().getID() + "_thing_header.h");
    }

    public String getThingImplTemplate() {
        return getTemplateByID("cpptemplates/" + getCompiler().getID() + "_thing_impl.cpp");
    }
    public String getThingImplInitTemplate() {
        return getTemplateByID("cpptemplates/" + getCompiler().getID() + "_thing_impl_init.cpp");
    }

    public String getRuntimeHeaderTemplate() {
        return getTemplateByID("cpptemplates/" + getCompiler().getID() + "_runtime.h");
    }

    public String getRuntimeImplTemplate() {
        return getTemplateByID("cpptemplates/" + getCompiler().getID() + "_runtime.cpp");
    }

    public String getCommonHeaderTemplate() {
        return getTemplateByID("cpptemplates/" + getCompiler().getID() + "_thingml_typedefs.h");
    }

    public String getCEPLibTemplateClass() {
        if (getCompiler().getID().compareTo("arduino") == 0 || getCompiler().getID().compareTo("arduinomf") == 0) {
            return getTemplateByID("cpptemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_class.h");
        }
        return null;
    }

    public String getCEPLibTemplateMethodsSignatures() {
        if (getCompiler().getID().compareTo("arduino") == 0 || getCompiler().getID().compareTo("arduinomf") == 0) {
            return getTemplateByID("cpptemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_methods_signatures.h");
        }
        return null;
    }

    public String getCEPLibTemplateAttributesSignatures() {
        if (getCompiler().getID().compareTo("arduino") == 0 || getCompiler().getID().compareTo("arduinomf") == 0) {
            return getTemplateByID("cpptemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_attributes_signatures.h");
        }
        return null;
    }

    public String getCEPLibTemplateMessageConstants() {
        if (getCompiler().getID().compareTo("arduino") == 0 || getCompiler().getID().compareTo("arduinomf") == 0) {
            return getTemplateByID("cpptemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_message_constants.h");
        }
        return null;
    }

    public String getCEPLibTemplateStreamConstants() {
        if (getCompiler().getID().compareTo("arduino") == 0 || getCompiler().getID().compareTo("arduinomf") == 0) {
            return getTemplateByID("cpptemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_stream_constants.h");
        }
        return null;
    }

    public String getCEPLibTemplateClassImpl() {
        if (getCompiler().getID().compareTo("arduino") == 0 || getCompiler().getID().compareTo("arduinomf") == 0) {
            return getTemplateByID("cpptemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_classImpl.cpp");
        }
        return null;
    }

    public String getCEPLibTemplatesMessageImpl() {
        if (getCompiler().getID().compareTo("arduino") == 0 || getCompiler().getID().compareTo("arduinomf") == 0) {
            return getTemplateByID("cpptemplates/arduino_libCEP/" + getCompiler().getID() + "_libCEP_messageImpl.cpp");
        }
        return null;
    }
}
