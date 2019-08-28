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
package compilers.go;

import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Configuration;

public class GoCfgBuildCompiler extends CfgBuildCompiler {

	@Override
    public String getDockerBaseImage(Configuration cfg, Context ctx) {
        return "golang:buster";
    }

    @Override
    public String getDockerCMD(Configuration cfg, Context ctx) {
    	if(AnnotatedElementHelper.isDefined(cfg, "docker", "perf")) {
    		return "strace\", \"-o\", \"/data/strace.log\", \"-f\", \"-c\", \"-Scalls\", \"./" + cfg.getName(); 	
    	}
        return "/" + cfg.getName();
    }
    
    @Override
    public String getDockerCfgRunPath(Configuration cfg, Context ctx) {    	
    	String command = "RUN apt-get update && apt-get install -y git "; 
    	if (AnnotatedElementHelper.isDefined(cfg, "docker", "perf"))
    		command += "strace ";
    	if (AnnotatedElementHelper.isDefined(cfg, "go_compiler", "gccgo"))
    		command += "gccgo-go ";
    	command += "&& rm -rf /var/lib/apt/lists/* ";    	    	
    	if (AnnotatedElementHelper.isDefined(cfg, "go_compiler", "gccgo"))
    		command += "&& cp /usr/bin/gccgo-8 /usr/bin/gccgo";
    	command += "\n";
    			
        command += "RUN go get github.com/SINTEF-9012/gosm\n" +        			
            	   "COPY . .\n";
            	   
        if (AnnotatedElementHelper.isDefined(cfg, "go_compiler", "gccgo"))
        	command += "RUN CGO_ENABLED=1 GOOS=linux go build -compiler gccgo -gccgoflags \"-static -O2\" -o " + cfg.getName() + " -a *.go && cp " + cfg.getName() + " /" + cfg.getName() + "\n";
        else
        	command += "RUN go build -ldflags \"-linkmode external -extldflags -static\" -o " + cfg.getName() + " -a *.go && cp " + cfg.getName() + " /" + cfg.getName() + "\n";
        
        if(!AnnotatedElementHelper.isDefined(cfg, "docker", "perf")) {
    		command += "FROM scratch\n" +
        			"COPY --from=0 /" + cfg.getName() + " /" + cfg.getName() + "\n";
    	}
    	return command;
    }
	
}
