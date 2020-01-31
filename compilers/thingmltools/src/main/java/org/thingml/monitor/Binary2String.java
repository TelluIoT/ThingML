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
package org.thingml.monitor;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Transition;

public class Binary2String implements MonitoringAspect {
	
	final ThingMLModel model;
	final BinaryMonitorGenerator tool;
	
	public Binary2String(ThingMLModel model, BinaryMonitorGenerator tool) {
		this.model = model;
		this.tool = tool;
	}
	
    public void writeTextFile(String content) {
        try {    
        	final File file = new File(tool.getOutputDirectory(), "monitor/bin2string.thingml");
            FileUtils.write(file, content, "UTF-8");
        } catch (Exception ex) {
            System.err.println("Problem while dumping the code");
            ex.printStackTrace();
        }
    }	
	
	@Override
	public void monitor() {
		final StringBuilder builder = new StringBuilder();
		//builder.append("import \"logbinary.thingml\" from stl\n\n");
		
		builder.append("thing Binary2StringLogger includes Logger {\n\n");
		
		builder.append("  function get_byte(b : Byte) : Byte do\n");
		builder.append("    if (HAS_SIGNED_BYTE)\n");
		builder.append("      return (``& b & ` & 0xFF`) as Byte\n");				
		builder.append("    else\n");
	    builder.append("      return b as Byte\n");
	    builder.append("  end\n\n");
		
		builder.append("  function do_log(payload : Byte[], size : UInt8) do\n\n");
		
		builder.append("    readonly var log_type : Byte = get_byte(payload[0])\n");
		builder.append("    readonly var inst : Byte = get_byte(payload[1])\n");
		builder.append("    if (log_type == LogType:function_called) do\n");
		logFunctionCalled(builder);
		builder.append("    end\n");
		builder.append("    else if (log_type == LogType:property_changed) do\n");
		logPropertyChanged(builder);
		builder.append("    end\n");
		builder.append("    else if (log_type == LogType:message_lost) do\n");
		logMessageLost(builder);
		builder.append("    end\n");
		builder.append("    else if (log_type == LogType:message_sent) do\n");
		logMessageSent(builder);
		builder.append("    end\n");
		builder.append("    else if (log_type == LogType:message_handled)  do\n");
		logMessageHandled(builder);
		builder.append("    end\n");
		builder.append("    else println \"unknown\"\n\n");
		
		builder.append("  end\n\n");
		builder.append("}\n\n");
		
		writeTextFile(builder.toString());
	}
	
	private void parseBytes(StringBuilder builder, PrimitiveType t, int startPayload) {
		long size = t.getByteSize();
		builder.append("((``");
		for(long i = 0; i < size; i++) {
			builder.append(" & get_byte(payload[" + (long)(startPayload + i) + "]) & ` << " + 8*(size-1-i) + ((i<size-1)?" | ":"") + "`");
		}		
		if (t.getName().equals("Boolean")) {
			builder.append(") as Integer != 0\n");
		} else {
			builder.append(") as " + t.getName());
		}
		builder.append(")");
	}

	private void logFunctionCalled(StringBuilder builder) {		
		builder.append("      readonly var func_ : Byte = get_byte(payload[2])\n");
		boolean isFirstThing = true;
		for(Thing t : ThingMLHelpers.allThings(model)) {
    		if (AnnotatedElementHelper.isDefined(t, "monitor", "not")) continue;
    		if (!AnnotatedElementHelper.isDefined(t, "monitor", "functions")) continue;
    		final String t_ID = AnnotatedElementHelper.firstAnnotation(t, "id");
    		builder.append((isFirstThing)?"      ":"      else ");
    		builder.append("if (inst == " + t_ID + ") do\n");    		
    		boolean isFirstFunction = true;
    		for(Function f : ThingMLHelpers.allFunctions(t)) {
    			if (f.isAbstract()) continue;
        		if (AnnotatedElementHelper.isDefined(f, "monitor", "not")) continue;
        		final String f_ID = AnnotatedElementHelper.firstAnnotation(f, "id");
        		builder.append((isFirstFunction)?"          ":"          else ");
        		builder.append("if (func_ == " + f_ID + ") do\n");
        		builder.append("            println \"function_called(" + t.getName() + t_ID + ", " + f.getName() + ", " + (f.getTypeRef()==null ? "void" : f.getTypeRef().getType().getName()) + "\"");
        		if (f.getTypeRef()==null)
        			builder.append(", \", _\"");
        		else {
        			builder.append(", \", \", ");
        			int startPayload = 2;
        			for(Parameter p : f.getParameters()) {
        				startPayload += ((PrimitiveType)p.getTypeRef().getType()).getByteSize();
        			}
        			parseBytes(builder, (PrimitiveType)f.getTypeRef().getType(), startPayload);
        		}
        		int index = 2;
        		for(Parameter p : f.getParameters()) {
        			builder.append(", \", " + p.getName() + "=\", ");
        			parseBytes(builder, (PrimitiveType)p.getTypeRef().getType(), index);
        			index += ((PrimitiveType)p.getTypeRef().getType()).getByteSize();
        		}
        		builder.append(", \")\"\n");
        		builder.append("          end\n");
        		isFirstFunction = false;
    		}
    		builder.append("        end\n");
    		isFirstThing = false;
		}
	}
	
	private void logPropertyChanged(StringBuilder builder) {		
		builder.append("      readonly var prop : Byte = get_byte(payload[2])\n");
		boolean isFirstThing = true;
		for(Thing t : ThingMLHelpers.allThings(model)) {
    		if (AnnotatedElementHelper.isDefined(t, "monitor", "not")) continue;
    		if (!AnnotatedElementHelper.isDefined(t, "monitor", "properties")) continue;
    		final String t_ID = AnnotatedElementHelper.firstAnnotation(t, "id");
    		builder.append((isFirstThing)?"        ":"        else ");
    		builder.append("if (inst == " + t_ID + ") do\n");    		
    		boolean isFirstProperty = true;
    		for(Property p : ThingMLHelpers.allProperties(t)) {
    			if (AnnotatedElementHelper.isDefined(p, "monitor", "not")) continue;
        		final String p_ID = AnnotatedElementHelper.firstAnnotation(p, "id");
        		builder.append((isFirstProperty)?"          ":"          else ");
        		builder.append("if (prop == " + p_ID + ") do\n");
        		final long size = ((PrimitiveType)p.getTypeRef().getType()).getByteSize();
        		int index = 3;
        		final StringBuilder oldBuilder = new StringBuilder();
        		parseBytes(oldBuilder, (PrimitiveType)p.getTypeRef().getType(), index);
        		index += size;
        		final StringBuilder newBuilder = new StringBuilder();
        		parseBytes(newBuilder, (PrimitiveType)p.getTypeRef().getType(), index);
        		builder.append("            println \"property_changed(" + t.getName() + t_ID + ", " + p.getName() + ", " + p.getTypeRef().getType().getName() + "\", \", \", " + oldBuilder.toString() + ", \", \", " + newBuilder.toString() + ", \")\"\n");
        		builder.append("          end\n");        		
        		isFirstProperty = false;
    		}
    		builder.append("        end\n");
    		isFirstThing = false;
		}
	}
	
	private void logMessageLost(StringBuilder builder) {
		builder.append("      readonly var portID : Byte = get_byte(payload[2])\n");
		builder.append("      readonly var messageID : Byte = get_byte(payload[3])\n");
		boolean isFirstThing = true;
		for(Thing t : ThingMLHelpers.allThings(model)) {
			if (AnnotatedElementHelper.isDefined(t, "monitor", "not")) continue;
    		if (!AnnotatedElementHelper.isDefined(t, "monitor", "events")) continue;
    		final String t_ID = AnnotatedElementHelper.firstAnnotation(t, "id");
    		builder.append((isFirstThing)?"        ":"        else ");
    		builder.append("if (inst == " + t_ID + ") do\n");
    		boolean isFirstPortMessage = true;
    		for(Message m : ThingMLHelpers.allMessages(t)) {
    			if (AnnotatedElementHelper.isDefined(m, "monitor", "not")) continue;
    			final String messageID = AnnotatedElementHelper.firstAnnotation(m, "id");
    			for(Port p : ThingMLHelpers.allPorts(t)) {
    				if (AnnotatedElementHelper.isDefined(p, "monitor", "not")) continue;
    				final String portID = AnnotatedElementHelper.firstAnnotation(p, "id");
    				if (p.getReceives().contains(m)) {
    					builder.append((isFirstPortMessage)?"          ":"          else ");
    		    		builder.append("if (portID == " + portID + " and messageID == " + messageID + ") do\n");
    	        		builder.append("            println \"message_lost(" + t.getName() + t_ID + ", " + p.getName() + ", " + m.getName() + "\"");
    	        		int index = 4;
    	        		for(Parameter pa : m.getParameters()) {
    	        			builder.append(", \", " + pa.getName() + "=\", ");
    	        			parseBytes(builder, (PrimitiveType)pa.getTypeRef().getType(), index);
    	        			index += ((PrimitiveType)pa.getTypeRef().getType()).getByteSize();
    	        		}
    	        		builder.append(", \")\"\n");
    		    		builder.append("          end\n");
    		    		isFirstPortMessage = false;
    				}
    			}
    		}
    		builder.append("        end\n");
    		isFirstThing = false;
		}
		
	}
	
	private void logMessageHandled(StringBuilder builder) {
		builder.append("      readonly var handlerID : Byte = get_byte(payload[2])\n");		
		boolean isFirstThing = true;
		for(Thing t : ThingMLHelpers.allThings(model)) {
			if (AnnotatedElementHelper.isDefined(t, "monitor", "not")) continue;
    		if (!AnnotatedElementHelper.isDefined(t, "monitor", "events")) continue;
    		final String t_ID = AnnotatedElementHelper.firstAnnotation(t, "id");
    		builder.append((isFirstThing)?"        ":"        else ");
    		builder.append("if (inst == " + t_ID + ") do\n");    		    		
    		final TreeIterator<EObject> allContent = EcoreUtil.getAllContents(t, true);
    		boolean isFirstHandler = true;
        	while(allContent.hasNext()) {
        		final EObject o = allContent.next();
        		if (o instanceof Handler) {
        			final Handler h = (Handler) o;
        			final String handlerID = AnnotatedElementHelper.firstAnnotation(h, "id");
        			if (handlerID == null) continue; //this is a transition added by the logging instrumentation
        			builder.append((isFirstHandler)?"          ":"          else ");
        			builder.append("if (handlerID == " + handlerID + ") do\n");
        			final String stateName = ((State)h.eContainer()).getName();
        			String portName = "_";
        			String msgName = "_";
        			String targetName = "_";
        			if (h instanceof Transition) {
        				Transition tr = (Transition) h;
        				targetName = tr.getTarget().getName();
        			}
        			if (h.getEvent() != null) {
        				final ReceiveMessage rm = (ReceiveMessage) h.getEvent();
        				portName = rm.getPort().getName();
        				msgName = rm.getMessage().getName();
        			}
	        		builder.append("            println \"message_handled(" + t.getName() + t_ID + ", " + portName + ", " + msgName + ", " + stateName + ", " + targetName + ", \"");
	        		if (h.getEvent() != null) {
        				final ReceiveMessage rm = (ReceiveMessage) h.getEvent();
            			int index = 3;
        				for(Parameter pa : rm.getMessage().getParameters()) {
        					builder.append(", \", " + pa.getName() + "=\", ");
        					parseBytes(builder, (PrimitiveType)pa.getTypeRef().getType(), index);
        					index += ((PrimitiveType)pa.getTypeRef().getType()).getByteSize();
        				}
	        		}
	        		builder.append(", \")\"\n");
		    		builder.append("          end\n");
		    		isFirstHandler = false;        			
        		}
        	}
    		builder.append("        end\n");
    		isFirstThing = false;
		}
	}
	
	private void logMessageSent(StringBuilder builder) {
		builder.append("      readonly var portID : Byte = get_byte(payload[2])\n");
		builder.append("      readonly var messageID : Byte = get_byte(payload[3])\n");
		boolean isFirstThing = true;
		for(Thing t : ThingMLHelpers.allThings(model)) {
    		if (AnnotatedElementHelper.isDefined(t, "monitor", "not")) continue;
    		if (!AnnotatedElementHelper.isDefined(t, "monitor", "events")) continue;
    		final String t_ID = AnnotatedElementHelper.firstAnnotation(t, "id");
    		builder.append((isFirstThing)?"        ":"        else ");
    		builder.append("if (inst == " + t_ID + ") do\n");
    		boolean isFirstPortMessage = true;
    		for(Message m : ThingMLHelpers.allMessages(t)) {
    			if (AnnotatedElementHelper.isDefined(m, "monitor", "not")) continue;
    			final String messageID = AnnotatedElementHelper.firstAnnotation(m, "id");
    			for(Port p : ThingMLHelpers.allPorts(t)) {
    				if (AnnotatedElementHelper.isDefined(p, "monitor", "not")) continue;
    				final String portID = AnnotatedElementHelper.firstAnnotation(p, "id");
    				if (p.getSends().contains(m)) {
    					builder.append((isFirstPortMessage)?"          ":"          else ");
    		    		builder.append("if (portID == " + portID + " and messageID == " + messageID + ") do\n");
    	        		builder.append("            println \"message_sent(" + t.getName() + t_ID + ", " + p.getName() + ", " + m.getName() + "\"");
    		    		int index = 4;
    	        		for(Parameter pa : m.getParameters()) {
    	        			builder.append(", \", " + pa.getName() + "=\", ");
    	        			parseBytes(builder, (PrimitiveType)pa.getTypeRef().getType(), index);
    	        			index += ((PrimitiveType)pa.getTypeRef().getType()).getByteSize();
    	        		}
    	        		builder.append(", \")\"\n");
    		    		builder.append("          end\n");
    					isFirstPortMessage = false;
    				}
    			}
    		}    		
    		builder.append("        end\n");
    		isFirstThing = false;
		}
	}
}