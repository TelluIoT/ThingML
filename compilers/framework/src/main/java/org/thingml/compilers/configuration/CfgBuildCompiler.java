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
package org.thingml.compilers.configuration;

import java.util.Iterator;

import org.thingml.compilers.Context;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;

/**
 * Created by bmori on 17.12.2014.
 */
public class CfgBuildCompiler {


    public void generateBuildScript(Configuration cfg, Context ctx) {
        throw (new UnsupportedOperationException("Project structure and build scripts are platform-specific."));
    }
    
    public String getDockerBaseImage(Configuration cfg, Context ctx) {
        return null;
    }
    
    public String getDockerCMD(Configuration cfg, Context ctx) {
    	throw (new UnsupportedOperationException("Run command is platform-specific."));
    }
    
    public String getDockerCfgRunPath(Configuration cfg, Context ctx) {
        return null;
    }
        
    public void generateDockerFile(Configuration cfg, Context ctx) {
        if(AnnotatedElementHelper.hasFlag(cfg, "docker") || AnnotatedElementHelper.hasAnnotation(cfg, "docker")) {
            StringBuilder dockerfile = ctx.getBuilder("Dockerfile");
            String dockerfileTemplate = null;
            
           	dockerfileTemplate = ctx.getTemplateByID("commontemplates/Dockerfile");
            
            String baseImage;
            if (AnnotatedElementHelper.hasAnnotation(cfg, "docker_base_image")) {
                baseImage = AnnotatedElementHelper.annotation(cfg, "docker_base_image").iterator().next();
            } else {
                if(getDockerBaseImage(cfg, ctx) != null) {
                    baseImage = getDockerBaseImage(cfg, ctx);
                } else {
                    baseImage = "NULL";
                    System.out.println("[WARNING] No docker base image found for compiler " + ctx.getCompiler().getID());
                }
            }
            dockerfileTemplate = dockerfileTemplate.replace("#BASE_IMAGE", baseImage);
            
            String expose;
            if (AnnotatedElementHelper.hasAnnotation(cfg, "docker_expose")) {
                expose = "EXPOSE";
                Iterator<String> exposeIt = AnnotatedElementHelper.annotation(cfg, "docker_expose").iterator();
                while(exposeIt.hasNext()) {
                    expose += " " + exposeIt.next();
                }
                
            } else {
                expose = "";
            }
            dockerfileTemplate = dockerfileTemplate.replace("#EXPOSE", expose);
            
            String run = "";
            if (AnnotatedElementHelper.hasAnnotation(cfg, "docker_instruction")) {
                Iterator<String> runIt = AnnotatedElementHelper.annotation(cfg, "docker_instruction").iterator();
                while(runIt.hasNext()) {
                    run += runIt.next() + "\n";
                }
                
            }
            dockerfileTemplate = dockerfileTemplate.replace("#DOCKER_INSTRUCTION", run);
            
            String cmd;
            if (AnnotatedElementHelper.hasAnnotation(cfg, "docker_cmd")) {
                cmd = AnnotatedElementHelper.annotation(cfg, "docker_cmd").iterator().next();
            } else {
                if(getDockerCMD(cfg, ctx) != null) {
                    cmd = getDockerCMD(cfg, ctx);
                } else {
                    cmd = "NULL";
                    System.out.println("[WARNING] No docker command found for compiler " + ctx.getCompiler().getID());
                }
            }
            dockerfileTemplate = dockerfileTemplate.replace("#CMD", cmd);
            
            String cfgPath;
            if(getDockerCMD(cfg, ctx) != null) {
                cfgPath = getDockerCfgRunPath(cfg, ctx);
            } else {
                cfgPath = "NULL";
                System.out.println("[WARNING] No docker configuration runnable path found for compiler " + ctx.getCompiler().getID());
            }
            dockerfileTemplate = dockerfileTemplate.replace("#COPY", cfgPath);
            
            String param = "";
            if (AnnotatedElementHelper.hasAnnotation(cfg, "docker_parameter")) {
                Iterator<String> paramIt = AnnotatedElementHelper.annotation(cfg, "docker_parameter").iterator();
                while(paramIt.hasNext()) {
                    param += ", ";
                    param += "\"" + paramIt.next() + "\"";
                }
                
            }
            dockerfileTemplate = dockerfileTemplate.replace("#PARAMETERS", param);
            
            dockerfile.append(dockerfileTemplate);
            
        }
    }
}
