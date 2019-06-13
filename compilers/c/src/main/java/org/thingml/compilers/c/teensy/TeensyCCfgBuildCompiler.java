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
        	mtemplate = mtemplate.replace("/*SOURCES_EXTERN*/", " $(SOURCES_EXTERN)");
        	mtemplate = mtemplate.replace("/*EXTERNSOURCES*/", "C_EXTERN := $(wildcard $(EXTERNPATH)/*.c)\nCPP_EXTERN := $(wildcard $(EXTERNPATH)/*.cpp)\nSOURCES_EXTERN := $(C_EXTERN:.c=.o) $(CPP_EXTERN:.cpp=.o)");
        	mtemplate = mtemplate.replace("/*EXTERNOBJS*/", " $(subst $(EXTERNPATH),$(BUILDDIR)/$(EXTERNFOLDER),$(SOURCES_EXTERN))");
        	mtemplate = mtemplate.replace("/*EXTERNBUILDER*/","$(BUILDDIR)/$(EXTERNFOLDER)/%.o: $(EXTERNPATH)/%.c\n\t@echo -e \"[CC]\t$<\"\n\t@mkdir -p \"$(dir $@)\"\n\t@$(CC) $(CPPFLAGS) $(CFLAGS) -o \"$@\" -c \"$<\"\n\n$(BUILDDIR)/$(EXTERNFOLDER)/%.o: $(EXTERNPATH)/%.cpp\n\t@echo -e \"[CXX]\t$<\"\n\t@mkdir -p \"$(dir $@)\"\n\t@$(CXX) $(CPPFLAGS) $(CXXFLAGS) -o \"$@\" -c \"$<\"");
        }else {
        	mtemplate = mtemplate.replace("/*EXTERNPATH*/", "");
        	mtemplate = mtemplate.replace("/*EXTERNINCLUDE*/", "");
        	mtemplate = mtemplate.replace("/*EXTERNSOURCES*/", "");
        	mtemplate = mtemplate.replace("/*SOURCES_EXTERN*/", "");
        	mtemplate = mtemplate.replace("/*EXTERNOBJS*/", "");
        	mtemplate = mtemplate.replace("/*EXTERNBUILDER*/","");
        }
        
        if(AnnotatedElementHelper.hasAnnotation(cfg, "teensy_libpath")){
        	int i = 0;
        	for(String libpath : AnnotatedElementHelper.annotation(cfg,"teensy_libpath")){
        		mtemplate = mtemplate.replace("/*LIBPATH*/", "# path location for your LIB"+i+"\nLIB"+i+"PATH = /*LIB_PATH*/\nTMPLIB"+i+"FOLDER := $(subst /, ,$(LIB"+i+"PATH))\nLIB"+i+"FOLDER := $(word $(shell echo $(words $(TMPLIB"+i+"FOLDER))),$(TMPLIB"+i+"FOLDER))\n\n/*LIBPATH*/");
            	mtemplate = mtemplate.replace("/*LIB_PATH*/", libpath);
            	mtemplate = mtemplate.replace("/*LIBINCLUDE*/"," -I$(LIB"+i+"PATH)/*LIBINCLUDE*/");
            	mtemplate = mtemplate.replace("/*SOURCES_LIB*/", " $(SOURCES_LIB"+i+")/*SOURCES_LIB*/");
            	mtemplate = mtemplate.replace("/*LIBSOURCES*/", "C_LIB"+i+" := $(wildcard $(LIB"+i+"PATH)/*.c)\nCPP_LIB"+i+" := $(wildcard $(LIB"+i+"PATH)/*.cpp)\nSOURCES_LIB"+i+" := $(C_LIB"+i+":.c=.o) $(CPP_LIB"+i+":.cpp=.o)\n/*LIBSOURCES*/");
            	mtemplate = mtemplate.replace("/*LIBOBJS*/", " $(subst $(LIB"+i+"PATH),$(BUILDDIR)/$(LIB"+i+"FOLDER),$(SOURCES_LIB"+i+"))\n/*LIBOBJS*/");
            	mtemplate = mtemplate.replace("/*LIBBUILDER*/","$(BUILDDIR)/$(LIB"+i+"FOLDER)/%.o: $(LIB"+i+"PATH)/%.c\n\t@echo -e \"[CC]\t$<\"\n\t@mkdir -p \"$(dir $@)\"\n\t@$(CC) $(CPPFLAGS) $(CFLAGS) -o \"$@\" -c \"$<\"\n\n$(BUILDDIR)/$(LIB"+i+"FOLDER)/%.o: $(LIB"+i+"PATH)/%.cpp\n\t@echo -e \"[CXX]\t$<\"\n\t@mkdir -p \"$(dir $@)\"\n\t@$(CXX) $(CPPFLAGS) $(CXXFLAGS) -o \"$@\" -c \"$<\"\n\n/*LIBBUILDER*/");
            	i++;
        	}

        }
        mtemplate = mtemplate.replace("/*LIBPATH*/", "");
    	mtemplate = mtemplate.replace("/*LIBINCLUDE*/", "");
    	mtemplate = mtemplate.replace("/*LIBSOURCES*/", "");
    	mtemplate = mtemplate.replace("/*SOURCES_LIB*/", "");
    	mtemplate = mtemplate.replace("/*LIBOBJS*/", "");
    	mtemplate = mtemplate.replace("/*LIBBUILDER*/","");
        
    	// Add all generated files
        //mtemplate = mtemplate.replace("/*SOURCES*/", srcs.toString().trim());
    	mtemplate = mtemplate.replace("/*SOURCES*/", String.join(" ",ctx.getGeneratedSources()).replace("\\", "/"));
        mtemplate = mtemplate.replace("/*LIBS*/", /*libs*/"");

        ctx.getBuilder("Makefile").append(mtemplate);
	}
}
