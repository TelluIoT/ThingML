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
package org.thingml.compilers.java;

import org.apache.commons.io.IOUtils;
import org.sintef.thingml.*;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgExternalConnectorCompiler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bmori on 27.01.2015.
 */
public class Java2Swing extends CfgExternalConnectorCompiler {

    @Override
    public void generateExternalConnector(Configuration cfg, Context ctx, String... options) {
        for(Instance i : cfg.allInstances()) {
            compileType(i.getType(), ctx, "org.thingml.generated");
        }
    }

    protected void compileType(Thing t, Context ctx, String pack) {
        if (t.hasAnnotation("mockup")) {
            final Map<Port, List<Message>> messageToSend = new HashMap<>();
            for(Port p : t.allPorts()) {
                if (p.getSends().size() > 0) {
                    messageToSend.put(p, new ArrayList<Message>(p.getSends()));
                }
            }

            final Map<Port, List<Message>> messageToReceive = new HashMap<>();
            for(Port p : t.allPorts()) {
                if (p.getReceives().size() > 0) {
                    messageToSend.put(p, new ArrayList<Message>(p.getReceives()));
                }
            }

            final StringBuilder builder = ctx.getBuilder("src/main/java/" + pack.replace(".", "/") + "/gui/" + t.getName() + "Mock.java");

            builder.append("public class " + ctx.firstToUpper(t.getName()) + "Mock extends Component implements ActionListener {\n\n");

            for(Type ty : t.findContainingModel().allUsedSimpleTypes()) {
                if (ty instanceof Enumeration) {
                    Enumeration e = (Enumeration) ty;
                    builder.append("private static final Map<String, " + ctx.firstToUpper(e.getName()) + "_ENUM> values_" + e.getName() + " = new HashMap<String, " + ctx.firstToUpper(e.getName()) + "_ENUM>();\n");
                    builder.append("static {\n");
                    for(EnumerationLiteral l : e.getLiterals()) {
                        builder.append("values_" + e.getName() + ".put(\"" + l.getName().toUpperCase() + "\", " + e.getName() + "_ENUM" + "." + e.getName().toUpperCase() + "_" + l.getName().toUpperCase() + ");\n");
                    }
                    builder.append("}\n\n");
                }
            }

            builder.append("//Message types\n");
            for(Message m : t.allMessages()) {
                builder.append("private final " + ctx.firstToUpper(m.getName()) + "MessageType " + m.getName() + "Type = new " + ctx.firstToUpper(m.getName()) + "MessageType();\n");
            }

            for(Port p : t.allPorts()) {
                builder.append("final Port " + "port_" + ctx.firstToUpper(t.getName()) + "_" + p.getName() + ";\n");
                builder.append("public Port get" + ctx.firstToUpper(p.getName()) + "_port(){return port_" + ctx.firstToUpper(t.getName()) + "_" + p.getName() + ";}\n");
            }

            for(Port p : messageToSend.keySet()) {
                builder.append("\npublic java.util.List<I" + ctx.firstToUpper(t.getName()) + "_" + p.getName() + "Client> " + p.getName() + "_listeners = new java.util.LinkedList<I" + ctx.firstToUpper(t.getName()) + "_" + p.getName() + "Client>();\n\n");
            }

            String template = "";
            try {
                final InputStream input = new FileInputStream(ctx.getOutputDirectory() + "/POM.xml");
                final List<String> packLines = IOUtils.readLines(input);
                for (String line : packLines) {
                    template += line + "\n";
                }
                input.close();
            } catch (Exception e) {
                System.err.println("Error loading Swing template: " + e.getLocalizedMessage());
                e.printStackTrace();
            }

            template = template.replace("$NAME$", ctx.firstToUpper(t.getName()));

            StringBuilder tempBuilder = new StringBuilder();
            for(Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
                Port port = entry.getKey();
                for(Message send : entry.getValue()) {
                    tempBuilder.append("//Attributes related to " + send.getName() + " via " + port.getName() +"\n");
                    tempBuilder.append("public JButton send" + send.getName() + "_via_" + port.getName() + ";\n");
                    for(Parameter p : send.getParameters()) {
                        if (p.getType() instanceof Enumeration) {
                            tempBuilder.append("private JComboBox field" + send.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName())+ ";\n");
                            tempBuilder.append("public JComboBox getField" + send.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName())+ "() {\n");
                            tempBuilder.append("return field" + send.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName())+ ";\n");
                            tempBuilder.append("}\n");
                        } else {
                            tempBuilder.append("private JTextField field" + send.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName())+ ";\n");
                            tempBuilder.append("public JTextField getField" + send.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName())+ "() {\n");
                            tempBuilder.append("return field" + send.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName())+ ";\n");
                            tempBuilder.append("}\n\n");
                        }
                    }

                    //////////////////////////////////////////////////////////////////

                    tempBuilder.append("public JButton getSend" + send.getName() + "_via_" + port.getName() + "() {\n");
                    tempBuilder.append("return send" + send.getName() + "_via_" + port.getName() + ";\n");
                    tempBuilder.append("}\n\n");
                }
            }

            tempBuilder.append("public void disableAll() {\n");
            for(Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
                Port port = entry.getKey();
                for(Message send : entry.getValue()) {
                    tempBuilder.append("send" + send.getName() + "_via_" + port.getName() + ".setEnabled(false);\n");
                }
            }
            tempBuilder.append("}\n\n");

            tempBuilder.append("public void enableAll() {\n");
            for(Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
                Port port = entry.getKey();
                for(Message send : entry.getValue()) {
                    tempBuilder.append("send" + send.getName() + "_via_" + port.getName() + ".setEnabled(true);\n");
                }
            }
            tempBuilder.append("}\n\n");

            template = template.replace("$MESSAGE_TO_SEND_DECL$", tempBuilder.toString());

            tempBuilder = new StringBuilder();

            tempBuilder.append("public void addListener(ActionListener l){\n");
            for(Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
                Port port = entry.getKey();
                for(Message msg : entry.getValue()) {
                    tempBuilder.append("send" + msg.getName() + "_via_" + port.getName() + ".addActionListener(l);\n");
                }
            }
            tempBuilder.append("}\n\n");

            template = template.replace("$LISTENERS$", tempBuilder.toString());

            tempBuilder = new StringBuilder();

            for(Port port : messageToSend.keySet()) {
                tempBuilder.append("frame_" + port.getName() + " = new JPanel();\n");
                tempBuilder.append("frame_" + port.getName() + ".setLayout(new GridBagLayout());\n");
                //builder append "frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\n"
            }


            for(Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
                Port port = entry.getKey();
                int x = 0;
                for(Message msg : entry.getValue()) {
                    tempBuilder.append("//GUI related to " + port.getName() + "_via_" + port.getName() + " => " + msg.getName() + "\n");
                    tempBuilder.append("c.gridy = 0;\n");
                    tempBuilder.append("c.gridx = " + x + ";\n");
                    tempBuilder.append("frame_" + port.getName() + ".add(createLabel(\"" + msg.getName() + "\"), c);\n");

                    tempBuilder.append("c.gridy = 1;\n");
                    tempBuilder.append("c.gridx = " + x + ";\n");
                    tempBuilder.append("frame_" + port.getName() + ".add(create" + msg.getName() + "_via_" + port.getName() + "Panel(), c);\n");

                    tempBuilder.append("c.gridy = 2;\n");
                    tempBuilder.append("c.gridx = " + x + ";\n");
                    tempBuilder.append("send" + msg.getName() + "_via_" + port.getName() + " = createSendButton(\"" + port.getName() + " => " + msg.getName() + "\");\n");
                    tempBuilder.append("frame_" + port.getName() + ".add(send" + msg.getName() + "_via_" + port.getName() + ", c);\n");

                    tempBuilder.append("tabbedPane.addTab(\"" + port.getName() + "\", frame_" + port.getName() + ");\n");
                    x++;
                }
            }

            for(Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
                Port port = entry.getKey();
                for(Message msg : entry.getValue()) {
                    tempBuilder.append("public JPanel create" + msg.getName() + "_via_" + port.getName() + "Panel(){\n");

                    tempBuilder.append("GridBagConstraints c = new GridBagConstraints();\n");
                    tempBuilder.append("c.fill = GridBagConstraints.HORIZONTAL;\n");
                    tempBuilder.append("c.weightx = 0.5;\n");

                    tempBuilder.append("JPanel panel = new JPanel(new GridBagLayout());\n");

                    int y = 0;
                    for(Parameter p : msg.getParameters()) {
                        tempBuilder.append("JLabel label" + p.getName() + " = new JLabel();\n");
                        tempBuilder.append("label" + p.getName() + ".setText(\"" + p.getName() + "\");\n");
                        tempBuilder.append("c.gridx = 0;\n");
                        tempBuilder.append("c.gridy = " + y + ";\n");
                        tempBuilder.append("panel.add(label" + p.getName() + ", c);\n");
                        if (p.getType() instanceof Enumeration) {
                            //builder append p.getType.scala_type + "[] values" + self.getName + Context.firstToUpper(p.getName) + " = {"
                            //builder append p.getType.asInstanceOf[Enumeration].getLiterals.collect{case l => p.getType.getName + "_ENUM" + "." + p.getType.getName.toUpperCase + "_" + l.getName.toUpperCase() + "()"}.mkString(", ") + "};\n"
                            tempBuilder.append("field" + msg.getName() + "_via_" + port.getName() + "_" +  ctx.firstToUpper(p.getName()) + " = new JComboBox(values_" + p.getType().getName() + ".keySet().toArray());\n");
                        } else {
                            tempBuilder.append("field" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + " = new JTextField();\n");
                            tempBuilder.append("field" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + ".setText(\"" + p.getName() + "\");\n");
                        }

                        tempBuilder.append("c.gridx = 1;\n");
                        tempBuilder.append("c.gridy = " + y + "\n;");
                        tempBuilder.append("panel.add(field" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + ", c);\n");
                        y++;
                    }
                    tempBuilder.append("return panel;\n");
                    tempBuilder.append("}\n\n");
                }
            }

            template = template.replace("$MESSAGE_TO_SEND_INIT$", tempBuilder.toString());

            tempBuilder = new StringBuilder();







            builder.append(template);
            builder.append("}\n\n");
            builder.append("}\n");
        }
    }

}
