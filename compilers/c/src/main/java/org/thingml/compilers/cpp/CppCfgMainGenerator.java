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


import org.thingml.compilers.DebugProfile;
import org.thingml.compilers.c.CCfgMainGenerator;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Session;

/**
 * Created by rkumar on 29.05.15.
 */
public class CppCfgMainGenerator extends CCfgMainGenerator {

    public boolean isGeneratingCpp() {
        return true;
    }

    public String getCppNameScope() {
        return "/*CFG_CPPNAME_SCOPE*/";
    }

    @Override
    protected void generateheaderbuilder(Configuration cfg, CCompilerContext ctx){
    	StringBuilder builder = new StringBuilder();
        StringBuilder headerbuilder = new StringBuilder();
        generateCForConfiguration(cfg, builder, headerbuilder, ctx);
        generateDynamicConnectors(cfg, builder, headerbuilder, ctx);
        // GENERATE HEADER FOR MAIN
        String cheadertemplate = ctx.getCfgMainHeaderTemplate();
        //generateCppHeaderExternalMessageEnqueue(cfg, headerbuilder, ctx);            
        //generateCppHeaderForConfiguration(cfg, headerbuilder, ctx);
        cheadertemplate = cheadertemplate.replace("/*HEADER_CONFIGURATION*/", headerbuilder.toString());
        ctx.getBuilder(cfg.getName() + ".h").append(cheadertemplate);
    }
    
    @Override
    protected void generateheaderdeclaration(Configuration cfg, StringBuilder builder, CCompilerContext ctx){
	    builder.append("//Declaration of instance variables\n");

        for (Instance inst : ConfigurationHelper.allInstances(cfg)) {
            
        builder.append("//Instance " + inst.getName() + "\n");
            
        builder.append("// Variables for the properties of the instance\n");

            builder.append(ctx.getInstanceVarDecl(inst) + "\n");

            if (AnnotatedElementHelper.hasAnnotation(cfg, "c_dyn_connectors")) {
                for (Port p : ThingMLHelpers.allPorts(inst.getType())) {
                    if (!p.getReceives().isEmpty()) {
                builder.append("struct Msg_Handler " + inst.getName()
                        + "_" + p.getName() + "_handlers;\n");
                builder.append("uint16_t " + inst.getName()
                        + "_" + p.getName() + "_msgs[" + p.getReceives().size() + "];\n");
                builder.append("void * " + inst.getName()
                        + "_" + p.getName() + "_handlers_tab[" + p.getReceives().size() + "];\n\n");

            }
        }
        }
        DebugProfile debugProfile = ctx.getCompiler().getDebugProfiles().get(inst.getType());
        //if(!(debugProfile==null) && debugProfile.g) {}
        //if(ctx.containsDebug(cfg, inst.getType())) {
        boolean debugInst = false;
            for (Instance i : debugProfile.getDebugInstances()) {
                if (i.getName().equals(inst.getName())) {
                debugInst = true;
                break;
            }
        }
            if (debugProfile.isActive()) {
            //if(ctx.isToBeDebugged(ctx.getCurrentConfiguration(), inst)) {
                if (debugInst) {
                builder.append("char * " + ctx.getInstanceVarName(inst) + "_name = \"" + inst.getName() + "\";\n");
            }
        }
            

            builder.append("// Variables for the sessions of the instance\n");
            CompositeState sm = ThingMLHelpers.allStateMachines(inst.getType()).get(0);
            generateSessionInstanceDeclaration(cfg, ctx, builder, inst, sm, "1");
    }

        builder.append("\n");
    }
    
    //TODO: Check that it still works after migration
    private void generateSessionInstanceDeclaration(Configuration cfg, CCompilerContext ctx, StringBuilder builder, Instance i, CompositeState cs, String curMaxInstances) {        
        for(Session s : CompositeStateHelper.allContainedSessions(cs)) {
            StringBuilder maxInstances = new StringBuilder();
            maxInstances.append(curMaxInstances + " * (");
            ctx.generateFixedAtInitValue(cfg, i, s.getMaxInstances(), maxInstances);
            maxInstances.append(")");
            
            builder.append("//Instance: " + i.getName() + ", Session: " + s.getName() + "\n");
            builder.append("struct " + ctx.getInstanceStructName(i.getType()) + " sessions_" + i.getName() + "_" + s.getName() + "[" + maxInstances + "];\n");
            for (Property a : ConfigurationHelper.allArrays(cfg, i)) {
                builder.append(ctx.getCType(a.getTypeRef().getType()) + " ");
                builder.append("array_" + i.getName() + "_" + s.getName() + "_" + ctx.getCVarName(a));
                builder.append("[" + maxInstances + "][");
                ctx.generateFixedAtInitValue(cfg, i, a.getTypeRef().getCardinality(), builder);
                builder.append("];\n");
            }            
        }
        
    }
}
