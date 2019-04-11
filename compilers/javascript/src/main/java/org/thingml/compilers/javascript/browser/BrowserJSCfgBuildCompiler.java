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
package org.thingml.compilers.javascript.browser;

import java.io.File;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.thingml.compilers.Context;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.builder.SourceBuilder;
import org.thingml.compilers.javascript.JSCfgBuildCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.Thing;

public class BrowserJSCfgBuildCompiler extends JSCfgBuildCompiler {
	final protected static Pattern dependencyAnnotationPattern = Pattern.compile("^src=(?<src1>.+),target=(?<target1>.+)$|^target=(?<target2>.+),src=(?<src2>.+)$");
	protected Entry<String, String> parseExternalDependencyAnnotation(String annotation) {
		Matcher m = dependencyAnnotationPattern.matcher(annotation);

        if (m.matches()) {
            if (m.group("src1") != null) {
                // The annotation has a src=<URL>,target=<STRING>
            	return new AbstractMap.SimpleEntry<String,String>(m.group("src1"),m.group("target1"));
            } else if (m.group("src2") != null) {
                // The annotation has a or target=<STRING>,src=<URL>
            	return new AbstractMap.SimpleEntry<String,String>(m.group("src2"),m.group("target2"));
            }
        } else if (!annotation.isEmpty()) {
        	// It's just a simple url
        	return new AbstractMap.SimpleEntry<String,String>(annotation, null);
        }
        
        return null;
	}
	
	protected Map<String, String> getExternalDependencies(Configuration cfg) {
		Map<String,String> dependencies = new HashMap<String,String>();
		
		for (String an : AnnotatedElementHelper.annotation(cfg, "js_include")) {
			Entry<String,String> dep = parseExternalDependencyAnnotation(an);
			if (dep != null) dependencies.put(dep.getKey(), dep.getValue());
		}
		for (Thing t : ConfigurationHelper.allThings(cfg)) {
            for (String an : AnnotatedElementHelper.annotation(t, "js_include")) {
                Entry<String,String> dep = parseExternalDependencyAnnotation(an);
                if (dep != null) dependencies.put(dep.getKey(), dep.getValue());
            }
        }

        for (ObjectType t : ConfigurationHelper.allObjectTypes(cfg)) {
            for (String an : AnnotatedElementHelper.annotation(t, "js_include")) {
                Entry<String,String> dep = parseExternalDependencyAnnotation(an);
                if (dep != null) dependencies.put(dep.getKey(), dep.getValue());
            }
        }
		
		return dependencies;
	}
	
	protected Set<String> getHTMLBody(Configuration cfg) {
		Set<String> result = new HashSet<>();		
		for (String an : AnnotatedElementHelper.annotation(cfg, "html_body")) {			
			result.add(an);
		}
		for (Thing t : ConfigurationHelper.allThings(cfg)) {
            for (String an : AnnotatedElementHelper.annotation(t, "html_body")) {
            	result.add(an);
            }
        }
        for (ObjectType t : ConfigurationHelper.allObjectTypes(cfg)) {
            for (String an : AnnotatedElementHelper.annotation(t, "html_body")) {
            	result.add(an);
            }
        }		
		return result;
	}
	
	protected Set<String> getHTMLHead(Configuration cfg) {
		Set<String> result = new HashSet<>();		
		for (String an : AnnotatedElementHelper.annotation(cfg, "html_head")) {			
			result.add(an);
		}
		for (Thing t : ConfigurationHelper.allThings(cfg)) {
            for (String an : AnnotatedElementHelper.annotation(t, "html_head")) {
            	result.add(an);
            }
        }
        for (ObjectType t : ConfigurationHelper.allObjectTypes(cfg)) {
            for (String an : AnnotatedElementHelper.annotation(t, "html_head")) {
            	result.add(an);
            }
        }		
		return result;
	}	
	
	@Override
	public void generateBuildScript(Configuration cfg, Context ctx) {
		SourceBuilder builder = ctx.getSourceBuilder("index.html");
		
		builder.append("<!DOCTYPE html>");
		Section html = builder.append("<html>").section("html").after("</html>").lines().indent();
		Section head = html.append("<head>").section("head").after("</head>").lines().indent();
		Section body = html.append("<body>").section("body").after("</body>").lines().indent();
		
		/* ------ Create <head> ------ */
		head.append("<title>ThingML in the Browser!</title>");
		
		// Libraries
		copyResourceToFile("lib/state.min.js", "lib/state.min.js", ctx);
		head.append("<script type=\"application/javascript\" src=\"lib/state.min.js\" target=\"StateJS\"></script>");
		copyResourceToFile("lib/EventEmitter.min.js", "lib/EventEmitter.min.js", ctx);
		head.append("<script type=\"application/javascript\" src=\"lib/EventEmitter.min.js\"></script>");
		head.append("<script type=\"application/javascript\" src=\"events.js\"></script>");
		for(String h : getHTMLHead(cfg)) {
			body.append(h);
		}
		
		
		for(String b : getHTMLBody(cfg)) {
			body.append(b);
		}

        // External dependencies
        for (Entry<String,String> dep : getExternalDependencies(cfg).entrySet()) {
        	Section externalDep = head.section("dependency");
        	String src = dep.getKey();
        	try {
        		// Check if the dependency is a local file
        		File included = new File(ctx.getInputDirectory(), src);
        		if (included.isFile() && included.exists()) {
        			// Get relative path in source folder
        			Path relative = ctx.getInputDirectory().toPath().relativize(included.toPath());
        			// Put it in lib/ in the output folder
        			File destination = new File(new File(ctx.getOutputDirectory(), "lib"), relative.toString());
        			FileUtils.copyFile(included, destination);
        			// Set its source
        			src = "lib/"+ctx.getInputDirectory().toURI().relativize(included.toURI());
        		}
        	} catch (Exception e) { }
        	
        	externalDep.append("<script type=\"application/javascript\" src=\"").append(src).append("\"");
        	if (dep.getValue() != null) externalDep.append(" target=\"").append(dep.getValue()).append("\"");
        	externalDep.append("></script>");
        }

        // Things
        for (Thing t : ConfigurationHelper.allThings(cfg))
        	head.section("dependency").append("<script type=\"application/javascript\" src=\"").append(ctx.firstToUpper(t.getName()) + ".js").append("\"></script>");
        
        // Runtime
        head.append("<script type=\"application/javascript\" src=\"runtime.js\"></script>");
	}
}
