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
package org.thingml.compilers.javascript.node;

import org.thingml.compilers.Context;
import org.thingml.compilers.javascript.JSCfgBuildCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.Thing;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.WriterConfig;

public class NodeJSCfgBuildCompiler extends JSCfgBuildCompiler {
	@Override
	public void generateBuildScript(Configuration cfg, Context ctx) {
		
		String json = String.join("", readResource("lib/package.json"));
		JsonObject pkg = Json.parse(json).asObject();
		
		String nameKebabCase = cfg.getName().replaceAll("([a-z0-9])([A-Z])", "$1-$2").toLowerCase();
		pkg.set("name", AnnotatedElementHelper.annotationOrElse(cfg, "nodejs_package_name", nameKebabCase));
		
		pkg.set("description", AnnotatedElementHelper.annotationOrElse(
				cfg, "nodejs_package_description", nameKebabCase +" configuration generated from ThingML"));
		
		pkg.set("version", AnnotatedElementHelper.annotationOrElse(cfg, "nodejs_package_version", "1.0.0"));
		pkg.set("license", AnnotatedElementHelper.annotationOrElse(cfg, "nodejs_package_license", "Apache-2.0"));
		pkg.set("repository", AnnotatedElementHelper.annotationOrElse(cfg, "nodejs_package_repository", ""));
		pkg.set("private", !AnnotatedElementHelper.annotationOrElse(cfg, "nodejs_package_private", "true").equals("false"));
		
		if (AnnotatedElementHelper.hasAnnotation(cfg, "nodejs_package_publish_config_registry")) {
			JsonObject publishConfig = new JsonObject();
			publishConfig.set("registry", AnnotatedElementHelper.firstAnnotation(cfg, "nodejs_package_publish_config_registry"));
			pkg.set("publishConfig", publishConfig);
		}
		
		JsonObject author = pkg.get("author").asObject();
		author.set("name", AnnotatedElementHelper.annotationOrElse(cfg, "nodejs_package_author_name", ""));
		author.set("email", AnnotatedElementHelper.annotationOrElse(cfg, "nodejs_package_author_email", ""));
		
		JsonValue deps = pkg.get("dependencies");
		
		if (AnnotatedElementHelper.hasFlag(cfg, "use_fifo")) {
        	deps.asObject().add("p-fifo", "^1.0.0");
        }
		
		if (AnnotatedElementHelper.hasAnnotation(cfg, "arguments")) {
        	deps.asObject().add("nconf", "^0.8.4");
        }
		
		for (Thing t : ConfigurationHelper.allThings(cfg)) {
            for (String dep : AnnotatedElementHelper.annotation(t, "js_dep")) {
                deps.asObject().add(dep.split(":")[0].trim(), dep.split(":")[1].trim());
            }
        }
        
        for (ObjectType t : ConfigurationHelper.allObjectTypes(cfg)) {
            for (String dep : AnnotatedElementHelper.annotation(t, "js_dep")) {
                deps.asObject().add(dep.split(":")[0].trim(), dep.split(":")[1].trim());
            }
        }
        
        StringBuilder builder = ctx.getBuilder("package.json");
        builder.append(pkg.toString(WriterConfig.PRETTY_PRINT));
	}
	
	@Override
    public String getDockerBaseImage(Configuration cfg, Context ctx) {		
		if (AnnotatedElementHelper.isDefined(cfg, "docker_js", "chakra"))
			return "debian\n"
					+ "RUN apt-get update && apt-get install -y curl build-essential strace && rm -rf /var/lib/apt/lists/*\n"
					+ "RUN curl -O -J -L https://github.com/nodejs/node-chakracore/releases/download/node-chakracore-v10.13.0/node-v10.13.0-linux-x64.tar.gz && tar -zvxf node-v10.13.0-linux-x64.tar.gz --strip-components=1 --directory / && rm node-v10.13.0-linux-x64.tar.gz\n";
		return "node:lts-alpine\n"
				+ "RUN apk add --no-cache strace\n";
    }
	
    @Override
    public String getDockerCMD(Configuration cfg, Context ctx) {
    	if(AnnotatedElementHelper.isDefined(cfg, "docker", "perf")) {
    		return "strace\", \"-o\", \"/data/strace.log\", \"-f\", \"-c\", \"-Scalls\", \"node\", \"--expose-gc\", \"main.js"; //Param main.js
    	}
        return "node\", \"--expose-gc\", \"main.js"; //Param main.js
    }

    @Override
    public String getDockerCfgRunPath(Configuration cfg, Context ctx) {
    	return "COPY . .\n" +
    	"RUN npm install --production\n";    	
    }	
}
