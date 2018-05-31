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
package org.thingml.networkplugins.c.arduino;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.spi.NetworkPlugin;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Protocol;
import org.thingml.xtext.thingML.Thing;

/**
 *
 * @author sintef
 */
public class ArduinoTimerPlugin extends NetworkPlugin {

	CCompilerContext ctx;
    HWTimer hwtimer0, hwtimer1, hwtimer2, hwtimer3;
    Boolean isInit = false;

    public String getPluginID() {
        return "ArduinoTimerPlugin";
    }

    public List<String> getSupportedProtocols() {
        List<String> res = new ArrayList<>();
        res.add("Timer");
        return res;
    }

    public List<String> getTargetedLanguages() {
        List<String> res = new ArrayList<>();
        res.add("arduino");
        res.add("arduinomf");
        return res;
    }

    public void generateNetworkLibrary(Configuration cfg, Context ctx, Set<Protocol> protocols) {
        this.ctx = (CCompilerContext) ctx;
        for (Protocol prot : protocols) {
            generateNetworkLibrary(cfg, prot);
        }
    }

    public void Init(Configuration cfg, Protocol prot) {
        if (!isInit) {

            Set<ExternalConnector> eco0 = new HashSet<>();
            Set<ExternalConnector> eco1 = new HashSet<>();
            Set<ExternalConnector> eco2 = new HashSet<>();
            Set<ExternalConnector> eco3 = new HashSet<>();
            //How many Hardware timers?
            int nbHWTimer = 0;
            for (ExternalConnector eco : this.getExternalConnectors(cfg, prot)) {
                if (AnnotatedElementHelper.hasAnnotation(eco, "hardware_timer")) {
                    int thisTimer = Integer.parseInt(AnnotatedElementHelper.annotation(eco, "hardware_timer").iterator().next());
                    if (thisTimer == 0) {
                        eco0.add(eco);
                    } else if (thisTimer == 1) {
                        eco1.add(eco);
                    } else if (thisTimer == 2) {
                        eco2.add(eco);
                    } else if (thisTimer == 3) {
                        eco3.add(eco);
                    }
                } else {
                    eco2.add(eco);
                    //System.out.println("add eco to timer 2");
                }
            }

            hwtimer0 = new HWTimer(0, eco0);
            hwtimer1 = new HWTimer(1, eco1);
            hwtimer2 = new HWTimer(2, eco2);
            hwtimer3 = new HWTimer(3, eco3);
            isInit = true;
        }
    }

    public void generateNetworkLibrary(Configuration cfg, Protocol prot) {
        //System.out.println("generateNetworkLibrary");
        this.Init(cfg, prot);
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        if (!hwtimer0.ExternalConnectors.isEmpty()) {
            ctx.addToInitCode("\n" + hwtimer0.timerName + "_instance.listener_id = add_instance(&" + hwtimer0.timerName + "_instance);\n");
            ctx.addToInitCode(hwtimer0.timerName + "_setup();\n");
            ctx.addToPollCode(hwtimer0.timerName + "_read();\n");

            StringBuilder lib = new StringBuilder();
            StringBuilder libh =new StringBuilder();
            lib.append(hwtimer0.generateTimerLibrary(ctx));
            libh.append(hwtimer0.generateTimerHeaderLibrary(ctx));
            hwtimer0.generateInstructions(ctx, lib, libh);

            ctx.getBuilder(hwtimer0.timerName + ".c").append(lib);
            ctx.getBuilder(hwtimer0.timerName + ".h").append(libh);
        }
        if (!hwtimer1.ExternalConnectors.isEmpty()) {
            ctx.addToInitCode("\n" + hwtimer1.timerName + "_instance.listener_id = add_instance(&" + hwtimer1.timerName + "_instance);\n");
            ctx.addToInitCode(hwtimer1.timerName + "_setup();\n");
            ctx.addToPollCode(hwtimer1.timerName + "_read();\n");

            StringBuilder lib = new StringBuilder();
            StringBuilder libh = new StringBuilder();
            lib.append(hwtimer1.generateTimerLibrary(ctx));
            libh.append(hwtimer1.generateTimerHeaderLibrary(ctx));
            hwtimer1.generateInstructions(ctx, lib, libh);

            ctx.getBuilder(hwtimer1.timerName + ".c").append(lib);
            ctx.getBuilder(hwtimer1.timerName + ".h").append(libh);
        }
        if (!hwtimer2.ExternalConnectors.isEmpty()) {
            ctx.addToInitCode("\n" + hwtimer2.timerName + "_instance.listener_id = add_instance(&" + hwtimer2.timerName + "_instance);\n");
            ctx.addToInitCode(hwtimer2.timerName + "_setup();\n");
            ctx.addToPollCode(hwtimer2.timerName + "_read();\n");

            StringBuilder lib = new StringBuilder();
            StringBuilder libh = new StringBuilder();
            lib.append(hwtimer2.generateTimerLibrary(ctx));
            libh.append(hwtimer2.generateTimerHeaderLibrary(ctx));
            hwtimer2.generateInstructions(ctx, lib, libh);

            ctx.getBuilder(hwtimer2.timerName + ".c").append(lib);
            ctx.getBuilder(hwtimer2.timerName + ".h").append(libh); 
        }
        if (!hwtimer3.ExternalConnectors.isEmpty()) {
            ctx.addToInitCode("\n" + hwtimer3.timerName + "_instance.listener_id = add_instance(&" + hwtimer3.timerName + "_instance);\n");
            ctx.addToInitCode(hwtimer3.timerName + "_setup();\n");
            ctx.addToPollCode(hwtimer3.timerName + "_read();\n");

            StringBuilder lib = new StringBuilder();
            StringBuilder libh = new StringBuilder();
            lib.append(hwtimer3.generateTimerLibrary(ctx));
            libh.append(hwtimer3.generateTimerHeaderLibrary(ctx));
            hwtimer3.generateInstructions(ctx, lib, libh);

            ctx.getBuilder(hwtimer3.timerName + ".c").append(lib);
            ctx.getBuilder(hwtimer3.timerName + ".h").append(libh); 
        }
    }

    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder) {
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        if (!hwtimer0.ExternalConnectors.isEmpty()) {
            hwtimer0.generateInstructions(ctx, builder, headerbuilder);
        }
        if (!hwtimer1.ExternalConnectors.isEmpty()) {
            hwtimer1.generateInstructions(ctx, builder, headerbuilder);
        }
        if (!hwtimer2.ExternalConnectors.isEmpty()) {
            hwtimer2.generateInstructions(ctx, builder, headerbuilder);
        }
        if (!hwtimer3.ExternalConnectors.isEmpty()) {
            hwtimer3.generateInstructions(ctx, builder, headerbuilder);
        }
    }

    private class HWTimer {
        public Set<ExternalConnector> ExternalConnectors;
        public String timerName;
        Boolean timerStart = false, timerCancel = false, timeOut = false, xmsTic = false;
        int idHWTimer;
        Set<BigInteger> tics;
        BigInteger scm;
        String interruptCounterType;
        int nbSoftTimer = 0;

        public HWTimer(int idHWTimer, Set<ExternalConnector> ExternalConnectors) {
            this.idHWTimer = idHWTimer;
            this.ExternalConnectors = ExternalConnectors;
            this.timerName = "timer" + idHWTimer;
            this.tics = new HashSet<BigInteger>();

            for (ExternalConnector eco : ExternalConnectors) {
                eco.setName(timerName);
                //System.out.println("eco now named:" + eco.getName());

                for (Message msg : eco.getPort().getSends()) {
                    if (AnnotatedElementHelper.hasAnnotation(msg, "timer_start")) {
                        timerStart |= true;
                    }
                    if (AnnotatedElementHelper.hasAnnotation(msg, "timer_cancel")) {
                        timerCancel |= true;
                    }
                }
                for (Message msg : eco.getPort().getReceives()) {
                    if (AnnotatedElementHelper.hasAnnotation(msg, "timeout")) {
                        timeOut |= true;
                    }
                    if (AnnotatedElementHelper.hasAnnotation(msg, "xms_tic")) {
                        xmsTic |= true;
                        BigInteger mytic = BigInteger.valueOf(Integer.parseInt(AnnotatedElementHelper.annotation(msg, "xms_tic").iterator().next()));
                        Boolean found = false;
                        for (BigInteger i : tics) {
                            if (mytic.compareTo(i) == 0) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            tics.add(mytic);
                        }
                    }
                }

                if (AnnotatedElementHelper.hasAnnotation(eco, "nb_soft_timer")) {
                    nbSoftTimer += Integer.parseInt(AnnotatedElementHelper.annotation(eco, "nb_soft_timer").iterator().next());
                }
            }

            this.findSCM();

        }

        

		BigInteger SCM(List<BigInteger> l) {
            if (l.isEmpty()) {
                //System.out.println("tscm (empty): 0");
                return BigInteger.valueOf(0);
            } else if (l.size() == 1) {
                //System.out.println("tscm (" + l.get(0).longValue() + "): " + l.get(0).longValue());
                return l.get(0);
            } else {
                BigInteger a = l.get(0);
                l.remove(a);
                BigInteger b = SCM(l);
                BigInteger res = BigInteger.valueOf((a.longValue() * b.longValue()) / a.gcd(b).longValue());
                //System.out.println("tscm (" + a.longValue() + ", " + b.longValue() + "): " + res.longValue());
                return res;
            }
        }

        void findSCM() {
            List<BigInteger> l = new LinkedList<BigInteger>();
            for (BigInteger bi : tics) {
                l.add(bi);
                //System.out.println(bi.longValue() + " tics");
            }

            scm = SCM(l);
            //System.out.println("scm: " + scm.longValue());

            if (scm != null) {
                if (scm.longValue() < 256) {
                    interruptCounterType = "uint8_t";
                } else if (scm.longValue() < 65536) {
                    interruptCounterType = "uint16_t";
                } else {
                    interruptCounterType = "uint32_t";
                }
            } else {
                interruptCounterType = "uint8_t";
            }
        }

        String timer_init() {
            String res = "";
            switch (idHWTimer) {
                case 0:
                    res = "// Plugging into timer0 \n" +
                            "               OCR0A = 0xAF;\n" +
                            "               TIMSK0 |= _BV(OCIE0A);\n";
                    break;
                case 1:
                    res = "// Run timer1 interrupt up counting at 16MHz \n" +
                            "		 TCCR1A = 0;\n" +
                            "		 TCCR1B = 0<<CS12 | 0<<CS11 | 1<<CS10;\n" +
                            "		\n" +
                            "		 //Timer1 Overflow Interrupt Enable\n" +
                            "		 TIMSK1 |= 1<<TOIE1;\n";
                    break;
                case 2:
                    res = "// Run timer2 interrupt up counting at 250kHz \n" +
                            "		 TCCR2A = 0;\n" +
                            "		 TCCR2B = 1<<CS22 | 0<<CS21 | 0<<CS20;\n" +
                            "		\n" +
                            "		 //Timer2 Overflow Interrupt Enable\n" +
                            "		 TIMSK2 |= 1<<TOIE2;\n";
                    break;
                case 3:
                    res = "// Run timer3 interrupt up counting at 16MHz \n" +
                            "		 TCCR3A = 0;\n" +
                            "		 TCCR3B = 0<<CS32 | 0<<CS31 | 1<<CS30;\n" +
                            "		\n" +
                            "		 //Timer1 Overflow Interrupt Enable\n" +
                            "		 TIMSK3 |= 1<<TOIE3;\n";
                    break;
            }
            return res;
        }

        String timer_interrupt() {

            String res = "";
            switch (idHWTimer) {
                case 0:
                    res = "SIGNAL(TIMER0_COMPA_vect) {\n";
                    break;
                case 1:
                    res = "SIGNAL(TIMER1_OVF_vect) {\n" +
                            "TCNT1 = 49536;\n";
                    break;
                case 2:
                    res = "SIGNAL(TIMER2_OVF_vect) {\n" +
                            "TCNT2 = 5;\n";
                    break;
                case 3:
                    res = "SIGNAL(TIMER3_OVF_vect) {\n" +
                            "TCNT3 = 49536;\n";
                    break;
            }
            return res;
        }
        
        public String getBitMask(int i, int size, boolean one) {
            String res = "0b";
            size--;
            while(size > i) {
                size--;
                if(one) res += "0";
                else res += "1";
            }
            size--;
                if(one) res += "1";
                else res += "0";
            while(size >= 0) {
                size--;
                if(one) res += "0";
                else res += "1";
            }
            return res;
        }

        
        public String generateTimerHeaderLibrary(CCompilerContext ctx) {
        	String cheadertemplate = "";
        	if(!ExternalConnectors.isEmpty()){
        		cheadertemplate = ctx.getTimerHeaderTemplate();
        		cheadertemplate = cheadertemplate.replace("/*PORT_NAME*/", timerName);
                cheadertemplate = cheadertemplate.replace("/*INSTANCE_INFORMATION*/", "/*INSTANCE_INFORMATION*/");
        	}
			return cheadertemplate;
		}
        
        public String generateTimerLibrary(CCompilerContext ctx) {
        	String ctemplate = "";
            if (!ExternalConnectors.isEmpty()) {
                ctemplate = ctx.getTimerTemplate();
                ctemplate = ctemplate.replace("/*PORT_NAME*/", timerName);
                ctemplate = ctemplate.replace("/*INTERRUPT_COUNTER*/", interruptCounterType + " " + timerName + "_interrupt_counter = 0;");

                StringBuilder interruptVector = new StringBuilder();
                StringBuilder initTimer = new StringBuilder();
                StringBuilder ticFlags = new StringBuilder();
                StringBuilder ticFlagsHandling = new StringBuilder();
                int ticFlagSize = 0;
                
                if(tics.size() > 0) {
                    if(tics.size() <= 8) {
                        ticFlagSize = 8;
                        ticFlags.append("uint8_t " + timerName + "_tic_flags = 0;");
                    } else if (tics.size() <= 16) {
                        ticFlagSize = 16;
                        ticFlags.append("uint16_t " + timerName + "_tic_flags = 0;");
                    } else if (tics.size() <= 32) {
                        ticFlagSize = 32;
                        ticFlags.append("uint32_t " + timerName + "_tic_flags = 0;");
                    } else if (tics.size() <= 64) {
                        ticFlagSize = 64;
                        ticFlags.append("uint64_t " + timerName + "_tic_flags = 0;");
                    } else {
                        System.out.println("[ERROR] Too many different tics for timer " + this.timerName);
                    }
                }
                ctemplate = ctemplate.replace("/*FLAGS*/", ticFlags);

                initTimer.append(timer_init());

                interruptVector.append(timer_interrupt());
                interruptVector.append(timerName + "_interrupt_counter++;\n");
                
                int i = 0;
                for (BigInteger bi : tics) {
                    interruptVector.append("if((" + timerName + "_interrupt_counter % " + bi.longValue() + ") == 0) {\n");
                    interruptVector.append(timerName + "_tic_flags |= " + getBitMask(i, ticFlagSize, true) + ";\n");
                    
                    ticFlagsHandling.append("if((" + timerName + "_tic_flags & " + getBitMask(i, ticFlagSize, true) + ") >> " + i + ") {\n");
                    ticFlagsHandling.append(timerName + "_" + bi.longValue() + "ms_tic();\n");
                    ticFlagsHandling.append(timerName + "_tic_flags &= " + getBitMask(i, ticFlagSize, false) + ";\n");
                    ticFlagsHandling.append("}\n");
                    //interruptVector.append(timerName + "_" + bi.longValue() + "ms_tic();\n");
                    interruptVector.append("}\n");
                    i++;
                }
                ctemplate = ctemplate.replace("/*FLAGS_HANDLING*/", ticFlagsHandling);
                
                interruptVector.append("if(" + timerName + "_interrupt_counter >= " + scm.longValue() + ") {\n");
                interruptVector.append(timerName + "_interrupt_counter = 0;\n");
                interruptVector.append("}\n");

                interruptVector.append("}\n\n");

                ctemplate = ctemplate.replace("/*INTERRUPT_VECTOR*/", interruptVector);
                ctemplate = ctemplate.replace("/*INITIALIZATION*/", initTimer);

                StringBuilder instructions = new StringBuilder();
                if (timerStart) {
                    instructions.append("void " + timerName + "_timer_start(uint8_t id, uint32_t ms) {\n"
                            + "if(id <" + timerName + "_NB_SOFT_TIMER) {\n"
                            + timerName + "_timer[id] = ms + millis();\n"
                            + "}\n"
                            + "}\n\n");
                }
                if (timerCancel) {
                    instructions.append("void " + timerName + "_timer_cancel(uint8_t id) {\n"
                            + "if(id <" + timerName + "_NB_SOFT_TIMER) {\n"
                            + timerName + "_timer[id] = 0;\n"
                            + "}\n"
                            + "}\n\n");
                }
                //System.out.println("*******> timeOut = " + timeOut);
                if (timeOut) {

                    instructions.append("void " + timerName + "_timeout(uint8_t id) {\n"
                            + "uint8_t enqueue_buf[3];\n");

                    Set<Message> timeoutMessages = new HashSet<Message>();
                    for (ExternalConnector eco : ExternalConnectors) {
                        for (Message msg : eco.getPort().getReceives()) {
                            if (AnnotatedElementHelper.hasAnnotation(msg, "timeout")) {
                                Boolean found = false;
                                for (Message m : timeoutMessages) {
                                    if (EcoreUtil.equals(msg, m)) {
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    timeoutMessages.add(msg);
                                }
                            }
                        }
                    }
                    for (Message msg : timeoutMessages) {
                        instructions.append("enqueue_buf[0] = (" + ctx.getHandlerCode(ctx.getCurrentConfiguration(), msg) + " >> 8) & 0xFF;\n");
                        instructions.append("enqueue_buf[1] = " + ctx.getHandlerCode(ctx.getCurrentConfiguration(), msg) + " & 0xFF;\n");
                        instructions.append("enqueue_buf[2] = id;\n");
                        instructions.append("externalMessageEnqueue(enqueue_buf, 3, " + timerName + "_instance.listener_id);\n");
                    }

                    instructions.append("}\n\n");
                }
                
                if (xmsTic) {
                    for (BigInteger bi : tics) {
                        instructions.append("void " + timerName + "_" + bi.longValue() + "ms_tic() {\n");


                        Set<Message> timeoutMessages = new HashSet<Message>();
                        for (ExternalConnector eco : ExternalConnectors) {
                            for (Message msg : eco.getPort().getReceives()) {
                                if (AnnotatedElementHelper.hasAnnotation(msg, "xms_tic")) {
                                    BigInteger x = BigInteger.valueOf(Integer.parseInt(AnnotatedElementHelper.annotation(msg, "xms_tic").iterator().next()));
                                    if (x.compareTo(bi) == 0) {
                                        Boolean found = false;
                                        for (Message m : timeoutMessages) {
                                            if (EcoreUtil.equals(msg, m)) {
                                                found = true;
                                                break;
                                            }
                                        }
                                        if (!found) {
                                            timeoutMessages.add(msg);
                                        }
                                    }
                                }
                            }
                        }
                        for (Message msg : timeoutMessages) {
                            instructions.append("{\n");
                            instructions.append("uint8_t enqueue_buf[2];\n");
                            instructions.append("enqueue_buf[0] = (" + ctx.getHandlerCode(ctx.getCurrentConfiguration(), msg) + " >> 8) & 0xFF;\n");
                            instructions.append("enqueue_buf[1] = " + ctx.getHandlerCode(ctx.getCurrentConfiguration(), msg) + " & 0xFF;\n");
                            instructions.append("externalMessageEnqueue(enqueue_buf, 2, " + timerName + "_instance.listener_id);\n");
                            instructions.append("}\n");
                        }
                        instructions.append("}\n\n");
                    }
                }

                ctemplate = ctemplate.replace("/*INSTRUCTIONS*/", instructions);

                StringBuilder poll = new StringBuilder();
                if (nbSoftTimer == 0) {
                    nbSoftTimer = 4;
                }
                ctemplate = ctemplate.replace("/*NB_SOFT_TIMER*/", "" + nbSoftTimer);

                if (timeOut) {

                    poll.append("uint8_t t;\n"
                            + "for(t = 0; t < " + nbSoftTimer + "; t++) {\n"
                            + "if((" + timerName + "_timer[t] > 0) && (" + timerName + "_timer[t] < tms)) {\n"
                            + timerName + "_timer[t] = 0;\n"
                            + timerName + "_timeout(t);\n"
                            + "}\n"
                            + "}\n");
                }
                ctemplate = ctemplate.replace("/*POLL*/", poll);
            }
            return ctemplate;
        }

        public void generateInstructions(CCompilerContext ctx, StringBuilder builder, StringBuilder headerbuilder) {
        	
        	// Keep track of messages which have been processed. The forward function should not be generated several times
        	// for the same thing/port/message combination
        	
        	HashSet<String> processed = new HashSet<String>();
        	
        	for (ExternalConnector eco : ExternalConnectors) {
                Thing t = eco.getInst().getType();
                Port p = eco.getPort();

                for (Message m : p.getSends()) {
                	
                	String id = eco.getName() + "_" + ctx.getSenderName(t, p, m);
                	
                	if (processed.contains(id)) continue;
                	processed.add(id);
                	
                	builder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    builder.append("void forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                    headerbuilder.append("void forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, builder, m);
                    ctx.appendFormalParameters(t, headerbuilder, m);
                    headerbuilder.append(";\n");
                    builder.append("{\n");

                    if (AnnotatedElementHelper.hasAnnotation(m, "timer_start")) {
                        String paramID = "id", paramTime = "time";
                        for (Parameter pt : m.getParameters()) {
                            if (AnnotatedElementHelper.hasAnnotation(pt, "id")) {
                                paramID = pt.getName();
                            }
                            if (AnnotatedElementHelper.hasAnnotation(pt, "time")) {
                                paramTime = pt.getName();
                            }                            
                        }

                        builder.append(timerName + "_timer_start(" + paramID + ", " + paramTime + ");");
                    }
                    if (AnnotatedElementHelper.hasAnnotation(m, "timer_cancel")) {
                        String paramID = "id";
                        for (Parameter pt : m.getParameters()) {                            
                            if (AnnotatedElementHelper.hasAnnotation(pt, "id")) {
                                paramID = pt.getName();
                            }
                        }

                        builder.append(timerName + "_timer_cancel(" + paramID + ");");
                    }

                    builder.append("}\n\n");
                }
            }
        }
    }

}
