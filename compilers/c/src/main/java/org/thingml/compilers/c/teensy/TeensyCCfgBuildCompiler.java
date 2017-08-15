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
package org.thingml.compilers.c.teensy;


import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;

public class TeensyCCfgBuildCompiler extends CfgBuildCompiler{

	@Override
	public void generateBuildScript(Configuration cfg, Context ctx) {
		generateLinuxMakefile(cfg, (CCompilerContextTeensy) ctx);
	}
	
	  protected String getSourceFileName(Thing thing) {
	        return "src/" + thing.getName() + ".cpp ";
	    }

	private void generateLinuxMakefile(Configuration cfg, CCompilerContextTeensy ctx) {
		
        //GENERATE THE MAKEFILE
        String mtemplate = ctx.getTemplateByID("ctemplates/Teensy_Makefile");
        mtemplate = mtemplate.replace("/*NAME*/", "src/" + cfg.getName() + ".cpp src/runtime.cpp");
  
        StringBuilder srcs = new StringBuilder();
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            TeensyCCfgBuildCompiler plugable = (TeensyCCfgBuildCompiler) getPlugableCfgBuildCompiler(t, ctx);
            srcs.append(plugable.getSourceFileName(t));
        }
      
        String libs = "";

        for (String s : AnnotatedElementHelper.annotation(cfg, "add_c_libraries")) {
            String[] strs = s.split(" ");
            for (int i = 0; i < strs.length; i++) {
                libs += "-l " + strs[i].trim() + " ";
            }
        }
        for (String s : AnnotatedElementHelper.annotation(cfg, "add_c_libraries_rep")) {
            String[] strs = s.split(" ");
            for (int i = 0; i < strs.length; i++) {
                libs += "-L " + strs[i].trim() + " ";
            }
        }
        libs = libs.trim();

        
        if(AnnotatedElementHelper.hasAnnotation(cfg, "teensy_platform")){
        	String platform = AnnotatedElementHelper.annotation(cfg, "teensy_platform").get(0);
        	mtemplate = mtemplate.replace("/*PLATFORM*/", platform);
        } else {
        	mtemplate = mtemplate.replace("/*PLATFORM*/", "32");
        }

        if(AnnotatedElementHelper.hasAnnotation(cfg, "teensy_overclock")){
        	mtemplate = mtemplate.replace("/*OVERCLOCK*/", "overclock");
        } else {
        	mtemplate = mtemplate.replace("/*OVERCLOCK*/", "default");
        }
        if(AnnotatedElementHelper.hasAnnotation(cfg, "teensy_corepath")){
        	String corepath = AnnotatedElementHelper.annotation(cfg, "teensy_corepath").get(0);
        	mtemplate = mtemplate.replace("/*COREPATH*/", corepath);
        } else {
        	mtemplate = mtemplate.replace("/*COREPATH*/", "${TEENSY_COREPATH}");
        }
        
        if(AnnotatedElementHelper.hasAnnotation(cfg, "teensy_externpath")){
        	String externpath = AnnotatedElementHelper.annotation(cfg,"teensy_externpath").get(0);
        	mtemplate = mtemplate.replace("/*EXTERNPATH*/", "# path location for your code\nEXTERNPATH = /*EXTERNPATH*/\nTMPEXTERNFOLDER := $(subst /, ,$(EXTERNPATH))\nEXTERNFOLDER := $(word $(shell echo $(words $(TMPEXTERNFOLDER))),$(TMPEXTERNFOLDER))");
        	mtemplate = mtemplate.replace("/*EXTERNPATH*/", externpath);
        	mtemplate = mtemplate.replace("/*EXTERNINCLUDE*/"," -I$(EXTERNPATH)");
        	mtemplate = mtemplate.replace("/*EXTERNSOURCES*/", "C_EXTERN := $(wildcard $(EXTERNPATH)/*.c)\nCPP_EXTERN := $(wildcard $(EXTERNPATH)/*.cpp)\nSOURCES_EXTERN := $(C_EXTERN:.c=.o) $(CPP_EXTERN:.cpp=.o)");
        	mtemplate = mtemplate.replace("/*EXTERNOBJS*/", " $(subst $(EXTERNPATH),$(BUILDDIR)/$(EXTERNFOLDER),$(SOURCES_EXTERN))");
        	mtemplate = mtemplate.replace("/*EXTERNBUILDER*/","$(BUILDDIR)/$(EXTERNFOLDER)/%.o: $(EXTERNPATH)/%.c\n\t@echo -e \"[CC]\t$<\"\n\t@mkdir -p \"$(dir $@)\"\n\t@$(CC) $(CPPFLAGS) $(CFLAGS) -o \"$@\" -c \"$<\"\n\n$(BUILDDIR)/$(EXTERNFOLDER)/%.o: $(EXTERNPATH)/%.cpp\n\t@echo -e \"[CXX]\t$<\"\n\t@mkdir -p \"$(dir $@)\"\n\t@$(CXX) $(CPPFLAGS) $(CXXFLAGS) -o \"$@\" -c \"$<\"");
        }else {
        	mtemplate = mtemplate.replace("/*EXTERNPATH*/", "");
        	mtemplate = mtemplate.replace("/*EXTERNINCLUDE*/", "");
        	mtemplate = mtemplate.replace("/*EXTERNSOURCES*/", "");
        	mtemplate = mtemplate.replace("/*EXTERNOBJS*/", "");
        	mtemplate = mtemplate.replace("/*EXTERNBUILDER*/","");
        }
        
        
        mtemplate = mtemplate.replace("/*SOURCES*/", srcs.toString().trim());
        mtemplate = mtemplate.replace("/*LIBS*/", libs);

        ctx.getBuilder("Makefile").append(mtemplate);
	}
}
