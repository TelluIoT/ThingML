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
package org.thingml.compilers.c.posix;

import org.sintef.thingml.Configuration;
import org.sintef.thingml.Thing;
import org.sintef.thingml.helpers.AnnotatedElementHelper;
import org.sintef.thingml.helpers.ConfigurationHelper;
import org.thingml.compilers.Context;
import org.thingml.compilers.c.CCompilerContext;
import org.thingml.compilers.configuration.CfgBuildCompiler;

import java.io.File;

/**
 * Created by ffl on 17.06.15.
 */
public class PosixCCfgBuildCompiler extends CfgBuildCompiler {


    @Override
    public void generateBuildScript(Configuration cfg, Context ctx) {
        generateLinuxMakefile(cfg, (CCompilerContext) ctx);
    }

    protected String getSourceFileName(Thing thing) {
        return thing.getName() + ".c ";
    }

    protected String getObjectFileName(Thing thing) {
        return thing.getName() + ".o ";
    }

    protected String getThirdPartyLibraries(Thing thing) { return ""; }

    protected void generateLinuxMakefile(Configuration cfg, CCompilerContext ctx) {

        //GENERATE THE MAKEFILE
        String mtemplate = ctx.getTemplateByID("ctemplates/Makefile");
        mtemplate = mtemplate.replace("/*NAME*/", cfg.getName());

        String compiler = "cc"; // default value
        if (AnnotatedElementHelper.hasAnnotation(cfg, "c_compiler")) compiler = AnnotatedElementHelper.annotation(cfg, "c_compiler").iterator().next();
        mtemplate = mtemplate.replace("/*CC*/", compiler);

        String flags;
        if (ctx.enableDebug()) flags = "CFLAGS = -DDEBUG";
        else flags = "CFLAGS = -O2 -w";
        for (String s : AnnotatedElementHelper.annotation(cfg, "add_c_flags")) {
            flags += " " + s;
        }
        mtemplate = mtemplate.replace("/*CFLAGS*/", flags);

        String srcs = "";
        String objs = "";

        // Add the modules for the Things
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            PosixCCfgBuildCompiler plugable = (PosixCCfgBuildCompiler) getPlugableCfgBuildCompiler(t, ctx);
            srcs += plugable.getSourceFileName(t);
            objs += plugable.getObjectFileName(t);
        }

        // Add the module for the Configuration
        srcs += cfg.getName() + "_cfg.c ";
        objs += cfg.getName() + "_cfg.o ";

        // Add any additional modules from the annotations
        for (String s : AnnotatedElementHelper.annotation(cfg, "add_c_modules")) {
            String[] mods = s.split(" ");
            for (int i = 0; i < mods.length; i++) {
                srcs += mods[i].trim() + ".c ";
                objs += mods[i].trim() + ".o ";
              
                // If .c (and .h) files exist in the current input directory, copy them to the output
                // TODO: Jakob, paths within the output??
                File indir = ctx.getInputDirectory();
                File cfile = new File(indir, mods[i].trim() + ".c");              
                if (cfile.exists()) {
                    ctx.addFileToCopy(mods[i].trim() + ".c", cfile);
                    File hfile = new File(indir, mods[i].trim() + ".h");
                    if (hfile.exists()) {
                        ctx.addFileToCopy(mods[i].trim() + ".h", hfile);
                    }
                }
            }
        }
        srcs = srcs.trim();
        objs = objs.trim();

        String libs = "";
        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            PosixCCfgBuildCompiler plugable = (PosixCCfgBuildCompiler) getPlugableCfgBuildCompiler(t, ctx);
            libs += plugable.getThirdPartyLibraries(t);
        }

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

        String preproc = "";
        for (String s : AnnotatedElementHelper.annotation(cfg, "add_c_directives")) {
            String[] strs = s.split(" ");
            for (int i = 0; i < strs.length; i++) {
                preproc += "-D " + strs[i].trim() + " ";
            }
        }
        preproc = preproc.trim();
        
        if(AnnotatedElementHelper.isDefined(cfg, "c_static_linking", "true") || ctx.staticLinking) {
            mtemplate = mtemplate.replace("/*STATIC*/", "-static ");
        } else {
            mtemplate = mtemplate.replace("/*STATIC*/", "");
        }

        mtemplate = mtemplate.replace("/*SOURCES*/", srcs);
        mtemplate = mtemplate.replace("/*OBJECTS*/", objs);
        mtemplate = mtemplate.replace("/*LIBS*/", libs);
        mtemplate = mtemplate.replace("/*PREPROC_DIRECTIVES*/", preproc);

        ctx.getBuilder("Makefile").append(mtemplate);
    }

}
