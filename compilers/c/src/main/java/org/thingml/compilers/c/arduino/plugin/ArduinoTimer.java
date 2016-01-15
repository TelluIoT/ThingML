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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.c.arduino.plugin;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Message;
import org.sintef.thingml.Parameter;
import org.sintef.thingml.Port;
import org.sintef.thingml.Thing;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.c.CMessageSerializer;
import org.thingml.compilers.c.CNetworkLibraryGenerator;
import org.thingml.compilers.c.plugin.CByteArraySerializer;

/**
 *
 * @author sintef
 */
public class ArduinoTimer extends CNetworkLibraryGenerator {


    CMessageSerializer ser;
    HWTimer hwtimer0, hwtimer1, hwtimer2;
    Boolean isInit = false;
    
    public ArduinoTimer(Configuration cfg, CCompilerContext ctx) {
        super(cfg, ctx);
        this.ser = new CByteArraySerializer(ctx, cfg);
    }
    
    public ArduinoTimer(Configuration cfg, CCompilerContext ctx, Set<ExternalConnector> ExternalConnectors) {
        super(cfg, ctx, ExternalConnectors);
        this.ser = new CByteArraySerializer(ctx, cfg);
    }
    
    public void Init() {
        if(!isInit) {
            CCompilerContext ctx = (CCompilerContext) this.ctx;
            
            Set<ExternalConnector> eco0 = new HashSet<>();
            Set<ExternalConnector> eco1 = new HashSet<>();
            Set<ExternalConnector> eco2 = new HashSet<>();
            //How many Hardware timers?
            int nbHWTimer = 0;
            for(ExternalConnector eco : this.getExternalConnectors()) {
                if(eco.hasAnnotation("hardware_timer")) {
                    int thisTimer = Integer.parseInt(eco.annotation("hardware_timer").iterator().next());
                    if(thisTimer == 0) {
                        eco0.add(eco);
                    } else if (thisTimer == 1) {
                        eco1.add(eco);
                    } else if (thisTimer == 2) {
                        eco2.add(eco);
                    }
                } else {
                    eco2.add(eco);
                    //System.out.println("add eco to timer 2");
                }
            }

            hwtimer0 = new HWTimer(0, eco0);
            hwtimer1 = new HWTimer(1, eco1);
            hwtimer2 = new HWTimer(2, eco2);
            isInit = true;
        }
    }

    @Override
    public void generateNetworkLibrary() {
        //System.out.println("generateNetworkLibrary");
        this.Init();
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        if(!hwtimer0.ExternalConnectors.isEmpty()) {
            ctx.addToInitCode("\n" + hwtimer0.timerName + "_instance.listener_id = add_instance(&" + hwtimer0.timerName + "_instance);\n");
            ctx.addToInitCode(hwtimer0.timerName + "_setup();\n");
            ctx.addToPollCode(hwtimer0.timerName + "_read();\n");
            
            ctx.getBuilder(hwtimer0.timerName + ".c").append(hwtimer0.generateTimerLibrary(ctx));
            ctx.getBuilder(hwtimer0.timerName + ".h").append("//" + hwtimer0.timerName + "\n");
        }
        if(!hwtimer1.ExternalConnectors.isEmpty()) {
            ctx.addToInitCode("\n" + hwtimer1.timerName + "_instance.listener_id = add_instance(&" + hwtimer1.timerName + "_instance);\n");
            ctx.addToInitCode(hwtimer1.timerName + "_setup();\n");
            ctx.addToPollCode(hwtimer1.timerName + "_read();\n");
            
            ctx.getBuilder(hwtimer1.timerName + ".c").append(hwtimer1.generateTimerLibrary(ctx));
            ctx.getBuilder(hwtimer1.timerName + ".h").append("//" + hwtimer1.timerName + "\n");
        }
        if(!hwtimer2.ExternalConnectors.isEmpty()) {
            ctx.addToInitCode("\n" + hwtimer2.timerName + "_instance.listener_id = add_instance(&" + hwtimer2.timerName + "_instance);\n");
            ctx.addToInitCode(hwtimer2.timerName + "_setup();\n");
            ctx.addToPollCode(hwtimer2.timerName + "_read();\n");
            
            ctx.getBuilder(hwtimer2.timerName + ".c").append(hwtimer2.generateTimerLibrary(ctx));
            ctx.getBuilder(hwtimer2.timerName + ".h").append("//" + hwtimer2.timerName + "\n");
        }
    }
    
    @Override
    public void generateMessageForwarders(StringBuilder builder, StringBuilder headerbuilder) {
        this.Init();
        CCompilerContext ctx = (CCompilerContext) this.ctx;
        if(!hwtimer0.ExternalConnectors.isEmpty()) {
            hwtimer0.generateInstructions(ctx, builder);
        }
        if(!hwtimer1.ExternalConnectors.isEmpty()) {
            hwtimer1.generateInstructions(ctx, builder);
        }
        if(!hwtimer2.ExternalConnectors.isEmpty()) {
            hwtimer2.generateInstructions(ctx, builder);
        }
    }
    
    private class HWTimer {
        Boolean timerStart = false, timerCancel = false, timeOut = false, xmsTic = false;
        int idHWTimer;
        public Set<ExternalConnector> ExternalConnectors;
        Set<BigInteger> tics;
        BigInteger scm;
        String interruptCounterType;
        public String timerName;
        int nbSoftTimer = 0;
        
        BigInteger SCM(List<BigInteger> l) {
            if(l.isEmpty()) {
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
            for(BigInteger bi : tics) {
                l.add(bi);
                //System.out.println(bi.longValue() + " tics");
            }
            
            scm = SCM(l);
            //System.out.println("scm: " + scm.longValue());
            
            if(scm != null) {
                if(scm.longValue() < 256) {
                    interruptCounterType = "uint8_t";
                } else if(scm.longValue() < 65536) {
                    interruptCounterType = "uint16_t";
                } else {
                    interruptCounterType = "uint32_t";
                }
            } else {
                interruptCounterType = "uint8_t";
            }
        }
        
        public HWTimer(int idHWTimer, Set<ExternalConnector> ExternalConnectors) {
            this.idHWTimer = idHWTimer;
            this.ExternalConnectors = ExternalConnectors;
            this.timerName = "timer" + idHWTimer;
            this.tics = new HashSet<BigInteger>();
            
            for(ExternalConnector eco : ExternalConnectors) {
                eco.setName(timerName);
                //System.out.println("eco now named:" + eco.getName());
                
                for(Message msg : eco.getPort().getSends()) {
                    if(msg.hasAnnotation("timer_start")) {
                        timerStart |= true;
                    }
                    if(msg.hasAnnotation("timer_cancel")) {
                        timerCancel |= true;
                    }
                }
                for(Message msg : eco.getPort().getReceives()) {
                    if(msg.hasAnnotation("timeout")) {
                        timeOut |= true;
                    }
                    if(msg.hasAnnotation("xms_tic")) {
                        xmsTic |= true;
                        BigInteger mytic = BigInteger.valueOf(Integer.parseInt(msg.annotation("xms_tic").iterator().next()));
                        Boolean found = false;
                        for(BigInteger i : tics) {
                            if(mytic.compareTo(i) == 0) {
                                found = true;
                                break;
                            }
                        }
                        if(!found) {
                            tics.add(mytic);
                        }
                    }
                }
                
                if(eco.hasAnnotation("nb_soft_timer")) {
                    nbSoftTimer += Integer.parseInt(eco.annotation("nb_soft_timer").iterator().next());
                }
            }
            
            this.findSCM();
            
        }
        
        public String generateTimerLibrary(CCompilerContext ctx) {
            String ctemplate = "";
            if(!ExternalConnectors.isEmpty()) {
                ctemplate = ctx.getTimerTemplate();
                ctemplate = ctemplate.replace("/*PORT_NAME*/", timerName);
                ctemplate = ctemplate.replace("/*INTERRUPT_COUNTER*/", interruptCounterType + " " + timerName + "_interrupt_counter = 0;");

                StringBuilder interruptVector = new StringBuilder();
                StringBuilder initTimer = new StringBuilder();
                if(idHWTimer == 0) {
                    initTimer.append("// Run timer0 interrupt up counting at 250kHz \n" +
    "            TCCR0A = 0;\n" +
    "            TCCR0B = 0<<CS02 | 1<<CS01 | 1<<CS00;\n" +
    "\n" +
    "            //Timer0 Overflow Interrupt Enable\n" +
    "            TIMSK0 |= 1<<TOIE0;\n");

                    interruptVector.append("SIGNAL(TIMER0_OVF_vect) {\n"
                            + "TCNT0 = 5;\n"
                            + timerName + "_interrupt_counter++;\n");

                    for(BigInteger bi : tics) {
                        interruptVector.append("if((" + timerName + "_interrupt_counter % " + bi.longValue() + ") == 0) {\n");
                        interruptVector.append(timerName + "_" + bi.longValue() + "ms_tic();\n");
                        interruptVector.append("}\n");
                    }
                    interruptVector.append("if(" + timerName + "_interrupt_counter >= " + scm.longValue() + ") {\n");
                    interruptVector.append(timerName + "_interrupt_counter = 0;\n");
                    interruptVector.append("}\n");

                    interruptVector.append("}\n\n");


                } else if (idHWTimer == 1) {
                    initTimer.append("// Run timer1 interrupt up counting at 16MHz \n" +
    "		 TCCR1A = 0;\n" +
    "		 TCCR1B = 0<<CS12 | 0<<CS11 | 1<<CS10;\n" +
    "		\n" +
    "		 //Timer1 Overflow Interrupt Enable\n" +
    "		 TIMSK1 |= 1<<TOIE1;\n");

                    interruptVector.append("SIGNAL(TIMER1_OVF_vect) {\n"
                            + "TCNT1 = 49536;\n"
                            + timerName + "_interrupt_counter++;\n");

                    for(BigInteger bi : tics) {
                        interruptVector.append("if((" + timerName + "_interrupt_counter % " + bi.longValue() + ") == 0) {\n");
                        interruptVector.append(timerName + "_" + bi.longValue() + "ms_tic();\n");
                        interruptVector.append("}\n");
                    }
                    interruptVector.append("if(" + timerName + "_interrupt_counter >= " + scm.longValue() + ") {\n");
                    interruptVector.append(timerName + "_interrupt_counter = 0;\n");
                    interruptVector.append("}\n");

                    interruptVector.append("}\n\n");
                } else if (idHWTimer == 2) {
                    initTimer.append("// Run timer2 interrupt up counting at 250kHz \n" +
    "		 TCCR2A = 0;\n" +
    "		 TCCR2B = 1<<CS22 | 0<<CS21 | 0<<CS20;\n" +
    "		\n" +
    "		 //Timer2 Overflow Interrupt Enable\n" +
    "		 TIMSK2 |= 1<<TOIE2;\n");

                    interruptVector.append("SIGNAL(TIMER2_OVF_vect) {\n"
                            + "TCNT2 = 5;\n"
                            + timerName + "_interrupt_counter++;\n");

                    for(BigInteger bi : tics) {
                        interruptVector.append("if((" + timerName + "_interrupt_counter % " + bi.longValue() + ") == 0) {\n");
                        interruptVector.append(timerName + "_" + bi.longValue() + "ms_tic();\n");
                        interruptVector.append("}\n");
                    }
                    interruptVector.append("if(" + timerName + "_interrupt_counter >= " + scm.longValue() + ") {\n");
                    interruptVector.append(timerName + "_interrupt_counter = 0;\n");
                    interruptVector.append("}\n");

                    interruptVector.append("}\n\n");
                }

                ctemplate = ctemplate.replace("/*INTERRUPT_VECTOR*/", interruptVector);
                ctemplate = ctemplate.replace("/*INITIALIZATION*/", initTimer);

                StringBuilder instructions = new StringBuilder();
                if(timerStart) {
                    instructions.append("void " + timerName + "_timer_start(uint8_t id, uint32_t ms) {\n"
                            + "if(id <" + timerName + "_NB_SOFT_TIMER) {\n"
                            + timerName + "_timer[id] = ms + millis();\n"
                            + "}\n"
                            + "}\n\n");
                }
                if(timerCancel) {
                    instructions.append("void " + timerName + "_timer_cancel(uint8_t id) {\n"
                            + "if(id <" + timerName + "_NB_SOFT_TIMER) {\n"
                            + timerName + "_timer[id] = 0;\n"
                            + "}\n"
                            + "}\n\n");
                }
                if(timeOut) {
                    instructions.append("void " + timerName + "_timeout(uint8_t id) {\n"
                            + "uint8_t enqueue_buf[3];\n");

                    Set<Message> timeoutMessages = new HashSet<Message>();
                    for(ExternalConnector eco : ExternalConnectors) {
                        for(Message msg : eco.getPort().getReceives()) {
                            if(msg.hasAnnotation("timeout")) {
                                Boolean found = false;
                                for(Message m : timeoutMessages) {
                                    if(EcoreUtil.equals(msg, m)) {
                                        found = true;
                                        break;
                                    }
                                }
                                if(!found) {
                                    timeoutMessages.add(msg);
                                }
                            }
                        }
                    }
                    for(Message msg : timeoutMessages) {
                        instructions.append("enqueue_buf[0] = (" + ctx.getHandlerCode(ctx.getCurrentConfiguration(), msg) + " >> 8) & 0xFF;\n");
                        instructions.append("enqueue_buf[1] = " + ctx.getHandlerCode(ctx.getCurrentConfiguration(), msg) + " & 0xFF;\n");
                        instructions.append("enqueue_buf[2] = id;\n");
                        instructions.append("externalMessageEnqueue(enqueue_buf, 3, " + timerName + "_instance.listener_id);\n");
                    }

                    instructions.append("}\n\n");
                }
                if(xmsTic) {
                    for(BigInteger bi : tics) {
                        instructions.append("void " + timerName + "_"+ bi.longValue() +"ms_tic() {\n");
                        
                        
                        //instructions.append("uint8_t enqueue_buf[2];\n");

                        Set<Message> timeoutMessages = new HashSet<Message>();
                        for(ExternalConnector eco : ExternalConnectors) {
                            for(Message msg : eco.getPort().getReceives()) {
                                if(msg.hasAnnotation("xms_tic")) {
                                    BigInteger x = BigInteger.valueOf(Integer.parseInt(msg.annotation("xms_tic").iterator().next()));
                                    if(x.compareTo(bi) == 0) {
                                        Boolean found = false;
                                        for(Message m : timeoutMessages) {
                                            if(EcoreUtil.equals(msg, m)) {
                                                found = true;
                                                break;
                                            }
                                        }
                                        if(!found) {
                                            timeoutMessages.add(msg);
                                        }
                                    }
                                }
                            }
                        }
                        for(Message msg : timeoutMessages) {
                            //instructions.append("enqueue_buf[0] = (" + ctx.getHandlerCode(ctx.getCurrentConfiguration(), msg) + " >> 8) & 0xFF;\n");
                            //instructions.append("enqueue_buf[1] = " + ctx.getHandlerCode(ctx.getCurrentConfiguration(), msg) + " & 0xFF;\n");
                            //instructions.append("externalMessageEnqueue(enqueue_buf, 2, " + timerName + "_instance.listener_id);\n");
                            instructions.append("dispatch_" + msg.getName()+ "(" + timerName + "_instance.listener_id);\n");
                        }
                        instructions.append("}\n\n");
                    }
                }

                 ctemplate = ctemplate.replace("/*INSTRUCTIONS*/", instructions);

                 StringBuilder poll = new StringBuilder();
                //Boolean timerStart = false, timerCancel = false, timeOut = false, xmsTic = false;
                if(nbSoftTimer == 0) {
                    nbSoftTimer = 4;
                }
                ctemplate = ctemplate.replace("/*NB_SOFT_TIMER*/", "" + nbSoftTimer);
                
                 if(timeOut) {

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
        
        public void generateInstructions(CCompilerContext ctx, StringBuilder builder) {
            for(ExternalConnector eco : ExternalConnectors) {
                //if (eco.hasAnnotation("c_external_send")) {
                Thing t = eco.getInst().getInstance().getType();
                Port p = eco.getPort();

                for (Message m : p.getSends()) {

                    builder.append("// Forwarding of messages " + eco.getName() + "::" + t.getName() + "::" + p.getName() + "::" + m.getName() + "\n");
                    builder.append("void " + getCppNameScope() + "forward_" + eco.getName() + "_" + ctx.getSenderName(t, p, m));
                    ctx.appendFormalParameters(t, builder, m);
                    builder.append("{\n");

                    if(m.hasAnnotation("timer_start")) {
                        String paramID = "id", paramTime = "time";
                        for(Parameter pt : m.getParameters()) {
                            if(pt.allAnnotations() != null) {
                                if(pt.hasAnnotation("id")) {
                                    paramID = pt.getName();
                                }
                                if(pt.hasAnnotation("time")) {
                                    paramTime = pt.getName();
                                }
                            }
                        }

                        builder.append(timerName + "_timer_start(" + paramID + ", " + paramTime + ");");
                    }
                    if(m.hasAnnotation("timer_cancel")) {
                        String paramID = "id";
                        for(Parameter pt : m.getParameters()) {
                            if(pt.allAnnotations() != null) {
                                if(pt.hasAnnotation("id")) {
                                    paramID = pt.getName();
                                }
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
