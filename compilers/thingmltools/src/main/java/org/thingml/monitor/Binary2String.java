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
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;

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
		builder.append("import \"logbinary.thingml\" from stl\n\n");
		
		builder.append("thing Binary2StringLogger includes Logger {\n\n");
		builder.append("function do_log(payload : Byte[], size : UInt8) do\n");
		
		builder.append("readonly var log_type : Byte = payload[0]\n");
		builder.append("readonly var inst : Byte = payload[1]\n");
		builder.append("if (log_type == LogType:function_called) do\n");
		logFunctionCalled(builder);
		builder.append("end\n");
		builder.append("else if (log_type == LogType:property_changed) do\n");
		logPropertyChanged(builder);
		builder.append("end\n");
		builder.append("else if (log_type == LogType:message_lost) do\n");
		logMessageLost(builder);
		builder.append("end\n");
		builder.append("else if (log_type == LogType:message_sent) do\n");
		logMessageSent(builder);
		builder.append("end\n");
		builder.append("else if (log_type == LogType:message_handled)  do\n");
		logMessageHandled(builder);
		builder.append("end\n");
		builder.append("else println \"unknown\"\n\n");
		
		builder.append("end\n\n");
		builder.append("}\n\n");
		
		writeTextFile(builder.toString());
	}
	
	private void parseBytes(StringBuilder builder, PrimitiveType t, String name, String startPayload) {
		long size = t.getByteSize();
		builder.append("readonly var bin_" + name + " : Byte[" + size + "] = {");
		boolean firstByte = true;
		for(long i = 0; i < size; i++) {
			if (!firstByte)
				builder.append(", ");
			builder.append("payload[" + startPayload + " + " + i + "]");
			firstByte = false;
		}
		builder.append("}\n");
		builder.append("readonly var " + name + " : " + t.getName() + " = (");
		for(long i = 0; i < size; i++) {
			if (i>0)
				builder.append(" + ");
			builder.append("((`` & bin_" + name + "[" + i + "] & ` << " + 8*i + "`) as Byte)");
		}
		if (t.getName().equals("Boolean")) {
			builder.append(") != 0\n");
		} else {
			builder.append(") as " + t.getName() + "\n");
		}
	}

	private void logFunctionCalled(StringBuilder builder) {
		boolean isFirstThing = true;
		for(Thing t : ThingMLHelpers.allThings(model)) {
    		if (AnnotatedElementHelper.isDefined(t, "monitor", "not")) continue;
    		if (!AnnotatedElementHelper.isDefined(t, "monitor", "functions")) continue;
    		final String t_ID = AnnotatedElementHelper.firstAnnotation(t, "id");
    		builder.append((isFirstThing)?"":"else ");
    		builder.append("if (inst == " + t_ID + ") do\n");
    		builder.append("readonly var func : Byte = payload[2]\n");
    		boolean isFirstFunction = true;
    		for(Function f : ThingMLHelpers.allFunctions(t)) {
    			if (f.isAbstract()) continue;
        		if (AnnotatedElementHelper.isDefined(f, "monitor", "not")) continue;
        		final String f_ID = AnnotatedElementHelper.firstAnnotation(f, "id");
        		builder.append((isFirstFunction)?"":"else ");
        		builder.append("if (func == " + f_ID + ") do\n");
        		if (f.getTypeRef() != null) {
        			final String startPayload = "size-" + ((PrimitiveType)f.getTypeRef().getType()).getByteSize();
        			parseBytes(builder, (PrimitiveType)f.getTypeRef().getType(), "result", startPayload);
        		}
        		//TODO: parameters, if any
        		int index = 3;
        		for(Parameter p : f.getParameters()) {
        			parseBytes(builder, (PrimitiveType)p.getTypeRef().getType(), p.getName(), String.valueOf(index));
        			index += ((PrimitiveType)p.getTypeRef().getType()).getByteSize();
        		}
        		//println "function_called(", inst, ", ", fn_name, ", ", ty, ", ", returns, ", ", params, ")"
        		builder.append("println \"function_called(\", \"" + t.getName() + t_ID + "\", \", \", \"" + f.getName() + ", \", \", " + (f.getTypeRef()==null ? "void\"" : f.getTypeRef().getType().getName() + "\""));
        		if (f.getTypeRef()==null)
        			builder.append(", \", _\"");
        		else
        			builder.append(", result");
        		for(Parameter p : f.getParameters()) {
        			builder.append(", \", \", " + p.getName());
        		}
        		builder.append("\n");
        		builder.append("end\n");
        		isFirstFunction = false;
    		}
    		builder.append("end\n");
    		isFirstThing = false;
		}
	}
	
	private void logPropertyChanged(StringBuilder builder) {
		
	}
	
	private void logMessageLost(StringBuilder builder) {
		
	}
	
	private void logMessageHandled(StringBuilder builder) {
		
	}
	
	private void logMessageSent(StringBuilder builder) {
		
	}
}
