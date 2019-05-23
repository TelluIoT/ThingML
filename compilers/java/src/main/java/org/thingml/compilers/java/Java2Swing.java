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
package org.thingml.compilers.java;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgExternalConnectorCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Type;

/**
 * Created by bmori on 27.01.2015.
 */
public class Java2Swing extends CfgExternalConnectorCompiler {

    @Override
    public void generateExternalConnector(Configuration cfg, Context ctx, String... options) {
        String pack = ctx.getContextAnnotation("package");
        if (pack == null) pack = "org.thingml.generated";
        pack = pack + ".gui";

        final StringBuilder b = ctx.getNewBuilder("src/main/java/" + pack.replace(".", "/") + "/StringHelper.java");
        String helper = "";
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("javatemplates/StringHelper.java");
            final List<String> packLines = IOUtils.readLines(input);
            for (String line : packLines) {
                helper += line + "\n";
            }
            input.close();
        } catch (Exception e) {
            System.err.println("Error loading Swing template: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        b.append(helper);


        final StringBuilder b2 = ctx.getNewBuilder("src/main/java/" + pack.replace(".", "/") + "/Command.java");
        String command = "";
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("javatemplates/Command.java");
            final List<String> packLines = IOUtils.readLines(input);
            for (String line : packLines) {
                command += line + "\n";
            }
            input.close();
        } catch (Exception e) {
            System.err.println("Error loading Swing template: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        b2.append(command);


        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            compileType(t, ctx, pack);
        }
        ctx.writeGeneratedCodeToFiles();
    }

    protected void compileType(Thing t, Context ctx, String pack) {
        if (!AnnotatedElementHelper.hasAnnotation(t, "mock"))
            return;
        final Map<Port, List<Message>> messageToSend = new HashMap<>();
        for (Port p : ThingMLHelpers.allPorts(t)) {
            if (p.getSends().size() > 0) {
                messageToSend.put(p, new ArrayList<Message>(p.getSends()));
            }
        }

        final Map<Port, List<Message>> messageToReceive = new HashMap<>();
        for (Port p : ThingMLHelpers.allPorts(t)) {
            if (p.getReceives().size() > 0) {
                messageToReceive.put(p, new ArrayList<Message>(p.getReceives()));
            }
        }

        final StringBuilder builder = ctx.getBuilder("src/main/java/" + pack.replace(".", "/") + "/" + t.getName() + "Mock.java");

        String imports = "";
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("javatemplates/swing-header");
            final List<String> packLines = IOUtils.readLines(input);
            for (String line : packLines) {
                imports += line + "\n";
            }
            input.close();
        } catch (Exception e) {
            System.err.println("Error loading Swing template: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        imports = imports.replace("$PACK$", pack);
        builder.append(imports);

        builder.append("public class " + ctx.firstToUpper(t.getName()) + "Mock extends Component implements ActionListener, ItemListener");
        for (Port p : messageToReceive.keySet()) {
            builder.append(", I" + t.getName() + "_" + p.getName());
        }
        builder.append("{\n\n");

        builder.append("private boolean debug = false;\n");
        builder.append("public boolean isDebug() {return debug;}\n");
        builder.append("public void setDebug(boolean debug) {this.debug = debug;}\n");

        //if (debugProfile.isActive()) {
            builder.append("public String instanceName;");
            builder.append("public void printDebug(");
            builder.append("String trace");//if debugWithString
            builder.append(") {\n");
            builder.append("if(this.isDebug()) {\n");
            builder.append("System.out.println(this.instanceName + trace);\n");
            builder.append("}\n");
            builder.append("}\n\n");
        //}

        for (Type ty : ThingMLHelpers.allUsedSimpleTypes(ThingMLHelpers.findContainingModel(t))) {
            if (ty instanceof Enumeration) {
                Enumeration e = (Enumeration) ty;
                builder.append("private static final Map<String, " + ctx.firstToUpper(e.getName()) + "_ENUM> values_" + e.getName() + " = new HashMap<String, " + ctx.firstToUpper(e.getName()) + "_ENUM>();\n");
                builder.append("static {\n");
                for (EnumerationLiteral l : e.getLiterals()) {
                    builder.append("values_" + e.getName() + ".put(\"" + l.getName().toUpperCase() + "\", " + e.getName() + "_ENUM" + "." + e.getName().toUpperCase() + "_" + l.getName().toUpperCase() + ");\n");
                }
                builder.append("}\n\n");
            }
        }

        builder.append("//Message types\n");
        for (Message m : ThingMLHelpers.allMessages(t)) {
            builder.append("private final " + ctx.firstToUpper(m.getName()) + "MessageType " + m.getName() + "Type = new " + ctx.firstToUpper(m.getName()) + "MessageType();\n");
        }

        for (Port p : ThingMLHelpers.allPorts(t)) {
            builder.append("final Port " + "port_" + ctx.firstToUpper(t.getName()) + "_" + p.getName() + ";\n");
            builder.append("public Port get" + ctx.firstToUpper(p.getName()) + "_port(){return port_" + ctx.firstToUpper(t.getName()) + "_" + p.getName() + ";}\n");
        }

        for (Port p : messageToSend.keySet()) {
            builder.append("public java.util.List<I" + ctx.firstToUpper(t.getName()) + "_" + p.getName() + "Client> " + p.getName() + "_listeners = new java.util.LinkedList<I" + ctx.firstToUpper(t.getName()) + "_" + p.getName() + "Client>();\n");
        }

        String template = "";
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("javatemplates/swing-component.java");
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

        for (Port p : ThingMLHelpers.allPorts(t)) {
            tempBuilder.append("port_" + ctx.firstToUpper(t.getName()) + "_" + p.getName() + " = new Port(");
            tempBuilder.append("\"" + p.getName() + "\", this);\n");
        }
        template = template.replace("$PORT_DECL$", tempBuilder.toString());

        tempBuilder = new StringBuilder();

        for (Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
            Port port = entry.getKey();
            for (Message send : entry.getValue()) {
                tempBuilder.append("//Attributes related to " + send.getName() + " via " + port.getName() + "\n");
                tempBuilder.append("public JButton send" + send.getName() + "_via_" + port.getName() + ";\n");
                for (Parameter p : send.getParameters()) {
                    if (p.getTypeRef().getType() instanceof Enumeration) {
                        tempBuilder.append("private JComboBox field" + send.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + ";\n");
                        tempBuilder.append("public JComboBox getField" + send.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + "() {\n");
                        tempBuilder.append("return field" + send.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + ";\n");
                        tempBuilder.append("}\n");
                    } else {
                        tempBuilder.append("private JTextField field" + send.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + ";\n");
                        tempBuilder.append("public JTextField getField" + send.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + "() {\n");
                        tempBuilder.append("return field" + send.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + ";\n");
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
        for (Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
            Port port = entry.getKey();
            for (Message send : entry.getValue()) {
                tempBuilder.append("send" + send.getName() + "_via_" + port.getName() + ".setEnabled(false);\n");
            }
        }
        tempBuilder.append("}\n\n");

        tempBuilder.append("public void enableAll() {\n");
        for (Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
            Port port = entry.getKey();
            for (Message send : entry.getValue()) {
                tempBuilder.append("send" + send.getName() + "_via_" + port.getName() + ".setEnabled(true);\n");
            }
        }
        tempBuilder.append("}\n\n");

        template = template.replace("$MESSAGE_TO_SEND_DECL$", tempBuilder.toString());

        tempBuilder = new StringBuilder();

        tempBuilder.append("public void addListener(ActionListener l){\n");
        for (Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
            Port port = entry.getKey();
            for (Message msg : entry.getValue()) {
                tempBuilder.append("send" + msg.getName() + "_via_" + port.getName() + ".addActionListener(l);\n");
            }
        }
        tempBuilder.append("}\n\n");

        template = template.replace("$LISTENERS$", tempBuilder.toString());

        tempBuilder = new StringBuilder();

        for (Port port : messageToSend.keySet()) {
            tempBuilder.append("JPanel frame_" + port.getName() + " = new JPanel();\n");
            tempBuilder.append("frame_" + port.getName() + ".setLayout(new GridBagLayout());\n");
            //builder append "frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\n"
        }


        for (Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
            Port port = entry.getKey();
            int x = 0;
            for (Message msg : entry.getValue()) {
                tempBuilder.append("//GUI related to " + port.getName() + "_via_" + port.getName() + " => " + msg.getName() + "\n");
                tempBuilder.append("c.gridy = 0;\n");
                tempBuilder.append("c.gridx = " + x + ";\n");
                tempBuilder.append("frame_" + port.getName() + ".add(createLabel(\"" + msg.getName() + "\"), c);\n");

                tempBuilder.append("c.gridy = 1;\n");
                tempBuilder.append("c.gridx = " + x + ";\n");
                tempBuilder.append("frame_" + port.getName() + ".add(create" + msg.getName() + "_via_" + port.getName() + "Panel(), c);\n");

                tempBuilder.append("c.gridy = 2;\n");
                tempBuilder.append("c.gridx = " + x + ";\n");
                tempBuilder.append("c.weighty = 0;\n");
                tempBuilder.append("send" + msg.getName() + "_via_" + port.getName() + " = createSendButton(\"" + port.getName() + " => " + msg.getName() + "\");\n");
                tempBuilder.append("frame_" + port.getName() + ".add(send" + msg.getName() + "_via_" + port.getName() + ", c);\n");

                tempBuilder.append("tabbedPane.addTab(\"" + port.getName() + "\", frame_" + port.getName() + ");\n");
                x++;
            }
        }

        template = template.replace("$MESSAGE_TO_SEND_INIT$", tempBuilder.toString());

        tempBuilder = new StringBuilder();

        for (Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
            Port port = entry.getKey();
            for (Message msg : entry.getValue()) {
                tempBuilder.append("public JPanel create" + msg.getName() + "_via_" + port.getName() + "Panel(){\n");

                tempBuilder.append("GridBagConstraints c = new GridBagConstraints();\n");
                tempBuilder.append("c.fill = GridBagConstraints.HORIZONTAL;\n");
                tempBuilder.append("c.weightx = 0.5;\n");

                tempBuilder.append("JPanel panel = new JPanel(new GridBagLayout());\n");

                int y = 0;
                for (Parameter p : msg.getParameters()) {
                    tempBuilder.append("JLabel label" + p.getName() + " = new JLabel();\n");
                    tempBuilder.append("label" + p.getName() + ".setText(\"" + p.getName() + "\");\n");
                    tempBuilder.append("c.gridx = 0;\n");
                    tempBuilder.append("c.gridy = " + y + ";\n");
                    tempBuilder.append("panel.add(label" + p.getName() + ", c);\n");
                    if (p.getTypeRef().getType() instanceof Enumeration) {
                        //builder append p.getType.scala_type + "[] values" + self.getName + Context.firstToUpper(p.getName) + " = {"
                        //builder append p.getType.asInstanceOf[Enumeration].getLiterals.collect{case l => p.getType.getName + "_ENUM" + "." + p.getType.getName.toUpperCase + "_" + l.getName.toUpperCase() + "()"}.mkString(", ") + "};\n"
                        tempBuilder.append("field" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + " = new JComboBox(values_" + p.getTypeRef().getType().getName() + ".keySet().toArray());\n");
                    } else {
                        tempBuilder.append("field" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + " = new JTextField();\n");
                        tempBuilder.append("field" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + ".setText(\"" + JavaHelper.getJavaType(p.getTypeRef().getType(), p.getTypeRef().getCardinality() != null, ctx) + "\");\n");
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

        template = template.replace("$MESSAGE_TO_SEND_BEHAVIOR$", tempBuilder.toString());

        tempBuilder = new StringBuilder();

        for (Map.Entry<Port, List<Message>> entry : messageToReceive.entrySet()) {
            Port port = entry.getKey();
            for (Message msg : entry.getValue()) {
                tempBuilder.append("Style receive" + msg.getName() + "_via_" + port.getName() + "Style = doc.addStyle(\"" + msg.getName() + "_via_" + port.getName() + "\", null);\n");
                tempBuilder.append("StyleConstants.setBackground(receive" + msg.getName() + "_via_" + port.getName() + "Style, new Color(" + ((255 + (Math.abs(msg.getName().hashCode()) % 255)) / 2) + ", " + ((255 + (Math.abs(port.getName().hashCode()) % 255)) / 2) + ", " + ((255 + ((Math.abs(port.getName().hashCode() + msg.getName().hashCode())) % 255)) / 2) + "));\n");
            }
        }

        template = template.replace("$MESSAGE_TO_RECEIVE_BEHAVIOR$", tempBuilder.toString());

        tempBuilder = new StringBuilder();
        StringBuilder parseBuilder = new StringBuilder();

        for (Map.Entry<Port, List<Message>> entry : messageToSend.entrySet()) {
            Port port = entry.getKey();

            parseBuilder.append("if(params[0].equals(\"" + port.getName() + "\")) {\n");

            int i = 0;
            for (Message msg : entry.getValue()) {
                if (i > 0)
                    parseBuilder.append("else ");

                parseBuilder.append("if (params[1].startsWith(\"" + msg.getName() + "\")) {\n");
                parseBuilder.append("params[1] = params[1].substring(\"" + msg.getName() + "\".length(), params[1].length());\n");
                parseBuilder.append("if (!(params[1].startsWith(\"(\") && params[1].endsWith(\")\"))) {\n");
                parseBuilder.append("cliButton.setForeground(alertColor);\n");
                parseBuilder.append("cli.setText(\"port!message(param1, param2, param3)\");\n");
                parseBuilder.append("return;\n");
                parseBuilder.append("}\n");
                parseBuilder.append("params = params[1].substring(1,params[1].length()-1).split(\",\");\n");
                if (msg.getParameters().size() == 0) {
                    parseBuilder.append("if (params[0].equals(\"\"))\n");
                    parseBuilder.append("params = new String[0];\n");
                }
                parseBuilder.append("if (!(params.length == " + msg.getParameters().size() + ")) {\n");
                parseBuilder.append("cliButton.setForeground(alertColor);\n");
                parseBuilder.append("cli.setText(\"port!message(param1, param2, param3)\");\n");
                parseBuilder.append("return;\n");
                parseBuilder.append("}\n");
                parseBuilder.append("try {\n");

                tempBuilder.append("else if ( ae.getSource() == getSend" + msg.getName() + "_via_" + port.getName() + "()) {\n");
                tempBuilder.append("try{\n");

                tempBuilder.append("Map<String, Object> param = new HashMap<String, Object>();\n");
                parseBuilder.append("Map<String, Object> param = new HashMap<String, Object>();\n");


                for (Parameter p : msg.getParameters()) {
                    tempBuilder.append("param.put(\"" + p.getName() + "\", ");
                    parseBuilder.append("param.put(\"" + p.getName() + "\", ");
                    if (p.getTypeRef().getCardinality() == null) {
                        if (p.getTypeRef().getType() instanceof Enumeration) {
                            tempBuilder.append("values_" + p.getTypeRef().getType().getName() + ".get(getField" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + "().getSelectedItem().toString())");
                            parseBuilder.append("values_" + p.getTypeRef().getType().getName() + ".get(getField" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + "().getSelectedItem().toString())");
                        } else {
                            /*tempBuilder.append("(");
                            parseBuilder.append("(");
                            if (JavaHelper.getJavaType(p.getType(), false, ctx).equals("int")) {
                                tempBuilder.append("Integer");
                                parseBuilder.append("Integer");
                            }
                            else {
                                tempBuilder.append(ctx.firstToUpper(JavaHelper.getJavaType(p.getType(), false, ctx)));
                                parseBuilder.append(ctx.firstToUpper(JavaHelper.getJavaType(p.getType(), false, ctx)));
                            }*/
                            tempBuilder.append("StringHelper.toObject (" + JavaHelper.getJavaType(p.getTypeRef().getType(), false, ctx) + ".class, getField" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + "().getText())");
                            parseBuilder.append("StringHelper.toObject (" + JavaHelper.getJavaType(p.getTypeRef().getType(), false, ctx) + ".class, params[" + msg.getParameters().indexOf(p) + "].trim())");
                        }
                    } else {
                        builder.append("getField" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + "().getText().getBytes()");
                    }
                    tempBuilder.append(");\n");
                    parseBuilder.append(");\n");
                }

                tempBuilder.append("Command c = new Command(port_" + ctx.firstToUpper(t.getName()) + "_" + port.getName() + ", " + msg.getName() + "Type, param);");
                tempBuilder.append("c.execute();\n");
                tempBuilder.append("((DefaultListModel)commands.getModel()).addElement(c);\n");

                parseBuilder.append("Command c = new Command(port_" + ctx.firstToUpper(t.getName()) + "_" + port.getName() + ", " + msg.getName() + "Type, param);");
                parseBuilder.append("c.execute();\n");
                parseBuilder.append("((DefaultListModel)commands.getModel()).addElement(c);\n");
                parseBuilder.append("cliButton.setForeground(Color.BLACK);\n");

                tempBuilder.append("for(I" + ctx.firstToUpper(t.getName()) + "_" + port.getName() + "Client l : " + port.getName() + "_listeners)\n");
                tempBuilder.append("l." + msg.getName() + "_from_" + port.getName() + "(");
                int j = 0;
                for (Parameter p : msg.getParameters()) {
                    if (j > 0)
                        tempBuilder.append(", ");
                    if (p.getTypeRef().getCardinality() == null) {
                        if (p.getTypeRef().getType() instanceof Enumeration) {
                            tempBuilder.append("values_" + p.getTypeRef().getType().getName() + ".get(getField" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + "().getSelectedItem().toString())");
                        } else {
                            tempBuilder.append("(");
                            if (JavaHelper.getJavaType(p.getTypeRef().getType(), false, ctx).equals("int"))
                                tempBuilder.append("Integer");
                            else
                                tempBuilder.append(ctx.firstToUpper(JavaHelper.getJavaType(p.getTypeRef().getType(), false, ctx)));
                            tempBuilder.append(")StringHelper.toObject (" + JavaHelper.getJavaType(p.getTypeRef().getType(), false, ctx) + ".class, getField" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + "().getText())");
                        }
                    } else {
                        tempBuilder.append("getField" + msg.getName() + "_via_" + port.getName() + "_" + ctx.firstToUpper(p.getName()) + "().getText().getBytes()");
                    }
                    j++;
                }
                tempBuilder.append(");\n");
                tempBuilder.append("getSend" + msg.getName() + "_via_" + port.getName() + "().setForeground(Color.BLACK);\n");
                tempBuilder.append("} catch(IllegalArgumentException iae) {\n");
                tempBuilder.append("System.err.println(\"Cannot parse arguments for message " + msg.getName() + " on port " + port.getName() + ". Please try again with proper parameters\");\n");
                tempBuilder.append("getSend" + msg.getName() + "_via_" + port.getName() + "().setForeground(alertColor);\n");
                tempBuilder.append("}\n");
                tempBuilder.append("}\n");

                parseBuilder.append("} catch(IllegalArgumentException iae) {\n");
                parseBuilder.append("System.err.println(\"Cannot parse arguments for message " + msg.getName() + " on port " + port.getName() + ". Please try again with proper parameters\");\n");
                parseBuilder.append("cliButton.setForeground(alertColor);\n");
                parseBuilder.append("}\n");
                parseBuilder.append("}\n");
                i++;
            }
            parseBuilder.append("}\n");
        }

        template = template.replace("$ON_ACTION$", tempBuilder.toString());
        template = template.replace("$PARSE$", parseBuilder.toString());

        builder.append(template);

        for (Map.Entry<Port, List<Message>> entry : messageToReceive.entrySet()) {
            Port port = entry.getKey();
            for (Message m : entry.getValue()) {
                builder.append("@Override\n");
                builder.append("public synchronized void " + m.getName() + "_via_" + port.getName() + "(");
                JavaHelper.generateParameter(m, builder, ctx);
                builder.append("){\n");
                builder.append("print(\"" + m.getName() + "_via_" + port.getName() + "\", \"TODO\");\n");
                builder.append("}\n");
            }
        }

        builder.append("}\n");
    }

}
