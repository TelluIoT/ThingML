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
package org.thingml.compilers.javascript;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.thingml.compilers.Context;
import org.thingml.compilers.configuration.CfgBuildCompiler;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ObjectType;

/**
 * Created by jakobho on 28.03.2017.
 */
public class BrowserJSCfgBuildCompiler extends CfgBuildCompiler {
    private static Pattern dependencyAnnotationPattern = Pattern.compile("^src=(?<src1>.+),target=(?<target1>.+)$|^target=(?<target2>.+),src=(?<src2>.+)$");
    private Map.Entry<String,String> parseExternalDependencyAnnotation(String annotation) {
        Matcher m = dependencyAnnotationPattern.matcher(annotation);

        if (m.matches()) {
            if (m.group("src1") != null) {
                // The annotation has a src=<URL>,target=<STRING>
                return new AbstractMap.SimpleEntry<>(m.group("src1"),m.group("target1"));
            } else if (m.group("src2") != null) {
                // The annotation has a or target=<STRING>,src=<URL>
                return new AbstractMap.SimpleEntry<>(m.group("src2"), m.group("target2"));
            }
        } else if (!annotation.isEmpty()) {
            // It's just a simple url
            return  new AbstractMap.SimpleEntry<>(annotation, null);
        }

        return  new AbstractMap.SimpleEntry<>(null, null);
    }

    private Map<String,String> getExternalDependencies(Configuration cfg) {
        Map<String,String> dependencies = new HashMap<>();

        for (String an : AnnotatedElementHelper.annotation(cfg, "js_include")) {
            Map.Entry<String,String> dep = parseExternalDependencyAnnotation(an);
            if (dep.getKey() != null) dependencies.put(dep.getKey(), dep.getValue());
        }

        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            for (String an : AnnotatedElementHelper.annotation(t, "js_include")) {
                Map.Entry<String,String> dep = parseExternalDependencyAnnotation(an);
                if (dep.getKey() != null) dependencies.put(dep.getKey(), dep.getValue());
            }
        }

        for (ObjectType t : ConfigurationHelper.allObjectTypes(cfg)) {
            for (String an : AnnotatedElementHelper.annotation(t, "js_include")) {
                Map.Entry<String,String> dep = parseExternalDependencyAnnotation(an);
                if (dep.getKey() != null) dependencies.put(dep.getKey(), dep.getValue());
            }
        }

        return dependencies;
    }

    public void generateBuildScript(Configuration cfg, Context ctx) {

        final StringBuilder builder = ctx.getBuilder("index.html");

        builder.append("<!DOCTYPE html>\n");
        builder.append("<html>\n");
        builder.append("\t<head>\n");
        builder.append("\t\t<title>ThingML in the Browser!</title>\n");

        ctx.getBuilder("lib/state.min.js").append(ctx.getTemplateByID("javascript/lib/state.min.js"));
        builder.append("\t\t<script type=\"application/javascript\" src=\"lib/state.min.js\" target=\"StateJS\"></script>\n");

        ctx.getBuilder("lib/EventEmitter.min.js").append(ctx.getTemplateByID("javascript/lib/EventEmitter.min.js"));
        builder.append("\t\t<script type=\"application/javascript\" src=\"lib/EventEmitter.min.js\"></script>\n");

        // External dependencies
        for (Map.Entry<String,String> dep : getExternalDependencies(cfg).entrySet()) {
            builder.append("\t\t<script type=\"application/javascript\"");
            builder.append(" src=\"").append(dep.getKey()).append("\"");
            if (dep.getValue() != null)
                builder.append(" target=\"").append(dep.getValue()).append("\"");
            builder.append("></script>\n");
        }

        for (Thing t : ConfigurationHelper.allThings(cfg)) {
            builder.append("\t\t<script type=\"application/javascript\" src=\"");
            builder.append(ctx.firstToUpper(t.getName()) + ".js");
            builder.append("\"></script>\n");
        }

        builder.append("\t\t<script type=\"application/javascript\" src=\"runtime.js\"></script>\n");
        builder.append("\t</head>\n");
        builder.append("\t<body>\n");
        builder.append("\t</body>\n");
        builder.append("</html>\n");
    }
}
