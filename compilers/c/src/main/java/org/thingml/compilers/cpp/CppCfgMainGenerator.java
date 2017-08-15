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


import org.thingml.compilers.c.CCfgMainGenerator;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.xtext.helpers.CompositeStateHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Session;

/**
 * Created by rkumar on 29.05.15.
 */
public class CppCfgMainGenerator extends CCfgMainGenerator {

    public String getCppNameScope() {
        return "/*CFG_CPPNAME_SCOPE*/";
    }

    @Override
    protected void generateheaderbuilder(Configuration cfg, StringBuilder headerbuilder, CCompilerContext ctx){
        // GENERATE HEADER FOR MAIN
        String cheadertemplate = ctx.getCfgMainHeaderTemplate();
        //generateCppHeaderExternalMessageEnqueue(cfg, headerbuilder, ctx);            
        //generateCppHeaderForConfiguration(cfg, headerbuilder, ctx);
        cheadertemplate = cheadertemplate.replace("/*HEADER_CONFIGURATION*/", headerbuilder.toString());
        ctx.getBuilder(cfg.getName() + ".h").append(cheadertemplate);
    }
    
    @Override
    protected void generateheaderdeclaration(Configuration cfg, StringBuilder builder, CCompilerContext ctx){
	    
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
