package org.thingml.compilers.main;

import org.sintef.thingml.Configuration;
import org.sintef.thingml.ThingMLModel;
import org.thingml.compilers.Context;

/**
 * Created by ffl on 29.05.15.
 */
public class CMainGenerator extends MainGenerator {

    public void generate(Configuration cfg, ThingMLModel model, Context ctx) {

    }


    protected void generateCForConfiguration(Configuration cfg, StringBuilder builder, Context ctx) {

        builder.append("\n");
        builder.append("/*****************************************************************************\n");
        builder.append(" * Definitions for configuration : " + cfg.getName() + "\n");
        builder.append(" *****************************************************************************/\n\n");

        builder.append("//Declaration of instance variables\n");
        /*
        self.allInstances.foreach {
            inst =>
            builder.append(inst.c_var_decl() + "\n"
        }

        builder.append("\n"

        generateMessageEnqueue(builder, context)
        builder.append("\n"
        generateMessageDispatchers(builder, context)
        builder.append("\n"
        generateMessageProcessQueue(builder, context)

        builder.append("\n"

        builder.append("void initialize_configuration_" + self.getName + "() {\n"

        // Generate code to initialize connectors
        builder.append("// Initialize connectors\n"
        self.allThings.foreach {
            t => t.allPorts.foreach {
                port => port.getSends.foreach {
                    msg =>
                    context.set_concrete_thing(t)
                    // check if there is an connector for this message
                    if (self.allConnectors.exists {
                        c =>
                        (c.getRequired == port && c.getProvided.getReceives.contains(msg)) ||
                                (c.getProvided == port && c.getRequired.getReceives.contains(msg))
                    }) {
                        //builder append t.sender_name(port, msg) + "_listener = "


                        builder.append("register_" + t.sender_name(port, msg) + "_listener("


                        if (isSyncSend(port)) {
                            // This is for static call of dispatches
                            builder.append("dispatch_" + t.sender_name(port, msg) + ");\n"
                        }
                        else {
                            // This is to enquqe the message and let the scheduler forward it
                            builder.append("enqueue_" + t.sender_name(port, msg) + ");\n"
                        }

                    }
                }
            }
        }
        context.clear_concrete_thing()

        builder.append("\n"
        //builder append "// Initialize instance variables and states\n"
        // Generate code to initialize variable for instances
        self.allInstances.foreach {
            inst =>
            inst.generateC(builder, context)
        }

        self.allInstances.foreach {
            inst =>
            inst.generateOnEntry(builder, context)
        }

        builder.append("}\n"

*/
    }
}
