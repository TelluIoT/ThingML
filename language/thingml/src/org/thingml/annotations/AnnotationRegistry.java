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
package org.thingml.annotations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.thingML.ThingMLPackage;

public class AnnotationRegistry {

	public static Map<String, Annotation> annotations = new HashMap<>();
	
	static {
		
		final String compiler_name = "compiler";
		final String compiler_desc = "Specifies the compiler to be used by the auto compiler";
		final EClass compiler_scope[] = {ThingMLPackage.eINSTANCE.getConfiguration()};
		final Annotation compiler_annotation = new Annotation(compiler_name, compiler_desc, compiler_scope);
		annotations.put(compiler_name, compiler_annotation);
		
		final String c_compiler_name = "c_compiler";
		final String c_compiler_desc = "Specifies an alternative compiler to be used to compile the generated C code e.g. clang";
		final EClass c_compiler_scope[] = {ThingMLPackage.eINSTANCE.getConfiguration()};
		final Annotation c_compiler_annotation = new Annotation(c_compiler_name, c_compiler_desc, c_compiler_scope);
		annotations.put(c_compiler_name, c_compiler_annotation);
		
		final String go_compiler_name = "go_compiler";
		final String go_compiler_desc = "Specifies an alternative compiler to be used to compile the generated Go code e.g. gccgo";
		final EClass go_compiler_scope[] = {ThingMLPackage.eINSTANCE.getConfiguration()};
		final Annotation go_compiler_annotation = new Annotation(go_compiler_name, go_compiler_desc, go_compiler_scope);
		annotations.put(go_compiler_name, go_compiler_annotation);
		
		/** MONITORING **/
		//@monitor
		final String monitor_name = "monitor";
		final String monitor_desc = "Generates monitoring instrumentation in a PIM way";
		final EClass monitor_scope[] = {ThingMLPackage.eINSTANCE.getAnnotatedElement()};
		final String monitor_values[] = {"not", "events", "functions", "properties"};
		final Annotation monitor_annotation = new EnumAnnotation(monitor_name, monitor_desc, monitor_scope, monitor_values);
		annotations.put(monitor_name, monitor_annotation);
		
		/** DEPLOYMENT **/
		//@docker
		final String docker_name = "docker";
		final String docker_desc = "Generates a Dockerfile for this configuration";
		final EClass docker_scope[] = {ThingMLPackage.eINSTANCE.getConfiguration()};
		final Annotation docker_annotation = new Annotation(docker_name, docker_desc, docker_scope);
		annotations.put(docker_name, docker_annotation);
		
		//@docker_jre
		final String docker_jre_name = "docker_jre";
		final String docker_jre_desc = "Alternative JRE to be used in the docker image";
		final EClass docker_jre_scope[] = {ThingMLPackage.eINSTANCE.getConfiguration()};
		final Annotation docker_jre_annotation = new Annotation(docker_jre_name, docker_jre_desc, docker_jre_scope);
		annotations.put(docker_jre_name, docker_jre_annotation);
		
		//@docker_js
		final String docker_js_name = "docker_js";
		final String docker_js_desc = "Alternative JS engine to be used in the docker image e.g. chakra";
		final EClass docker_js_scope[] = {ThingMLPackage.eINSTANCE.getConfiguration()};
		final Annotation docker_js_annotation = new Annotation(docker_js_name, docker_js_desc, docker_js_scope);
		annotations.put(docker_js_name, docker_js_annotation);
		
		/** DEPS AND EXTERNS **/
		//@maven_dep
		final String maven_name = "maven_dep";
		final String maven_desc = "Adds a Maven dependency to the generated pom.xml file.";
		final EClass maven_scope[] = {ThingMLPackage.eINSTANCE.getType()};
		final Annotation maven_annotation = new Annotation(maven_name, maven_desc, maven_scope);
		annotations.put(maven_name, maven_annotation);
		
		//@java_interface
		final String jitf_name = "java_interface";
		final String jitf_desc = "Makes this Thing to implement that interface.";
		final EClass jitf_scope[] = {ThingMLPackage.eINSTANCE.getThing()};
		final Annotation jitf_annotation = new Annotation(jitf_name, jitf_desc, jitf_scope);
		annotations.put(jitf_name, jitf_annotation);
		
		//@java_import
		final String jimp_name = "java_import";
		final String jimp_desc = "Adds an import to the generated code for this Thing.";
		final EClass jimp_scope[] = {ThingMLPackage.eINSTANCE.getThing()};
		final Annotation jimp_annotation = new Annotation(jimp_name, jimp_desc, jimp_scope);
		annotations.put(jimp_name, jimp_annotation);
		
		//@java_features
		final String jfeat_name = "java_features";
		final String jfeat_desc = "Adds a standalone fragment of code into the generated code for this Thing.";
		final EClass jfeat_scope[] = {ThingMLPackage.eINSTANCE.getThing()};
		final Annotation jfeat_annotation = new Annotation(jfeat_name, jfeat_desc, jfeat_scope);
		annotations.put(jfeat_name, jfeat_annotation);
		
		//@java_visibility
		final String jvis_name = "java_visibility";
		final String jvis_desc = "Changes the visibility of this function.";
		final EClass jvis_scope[] = {ThingMLPackage.eINSTANCE.getFunction()};
		final Annotation jvis_annotation = new Annotation(jvis_name, jvis_desc, jvis_scope);
		annotations.put(jvis_name, jvis_annotation);
		
		//@override
		final String over_name = "override";
		final String over_desc = "Marks this function as @Override.";
		final EClass over_scope[] = {ThingMLPackage.eINSTANCE.getFunction()};
		final Annotation over_annotation = new Annotation(over_name, over_desc, over_scope);
		annotations.put(over_name, over_annotation);
		
		//@go_import
		final String gimp_name = "go_import";
		final String gimp_desc = "Imports a Go package into the generated code for this Thing.";
		final EClass gimp_scope[] = {ThingMLPackage.eINSTANCE.getThing()};
		final Annotation gimp_annotation = new Annotation(gimp_name, gimp_desc, gimp_scope);
		annotations.put(gimp_name, gimp_annotation);
		
		//@js_dep
		final String npm_name = "js_dep";
		final String npm_desc = "Adds an NPM dependecy to the generated package.json file.";
		final EClass npm_scope[] = {ThingMLPackage.eINSTANCE.getType()};
		final Annotation npm_annotation = new Annotation(npm_name, npm_desc, npm_scope);
		annotations.put(npm_name, npm_annotation);
		
		//@js_include
		final String jsinc_name = "js_include";
		final String jsinc_desc = "Includes a JS resource e.g. from a CDN or from a local file.";
		final EClass jsinc_scope[] = {ThingMLPackage.eINSTANCE.getType()};
		final Annotation jsinc_annotation = new Annotation(jsinc_name, jsinc_desc, jsinc_scope);
		annotations.put(jsinc_name, jsinc_annotation);
		
		//@c_header
		final String cheader_name = "c_header";
		final String cheader_desc = "Adds a header (.h) to the generated code for this Thing.";
		final EClass cheader_scope[] = {ThingMLPackage.eINSTANCE.getThing()};
		final Annotation cheader_annotation = new Annotation(cheader_name, cheader_desc, cheader_scope);
		annotations.put(cheader_name, cheader_annotation);
		
		//@c_global
		final String cglobal_name = "c_global";
		final String cglobal_desc = "Adds a standalone fragment of code into the generated code for this Thing.";
		final EClass cglobal_scope[] = {ThingMLPackage.eINSTANCE.getThing()};
		final Annotation cglobal_annotation = new Annotation(cglobal_name, cglobal_desc, cglobal_scope);
		annotations.put(cglobal_name, cglobal_annotation);
		
		//@fork_linux_thread
		final String cfork_name = "fork_linux_thread";
		final String cfork_desc = "Makes this function to run in a separate thread.";
		final EClass cfork_scope[] = {ThingMLPackage.eINSTANCE.getFunction()};
		final Annotation cfork_annotation = new Annotation(cfork_name, cfork_desc, cfork_scope);
		annotations.put(cfork_name, cfork_annotation);
		
		//@fork_thread
		final String fork_name = "fork_thread";
		final String fork_desc = "Makes this function to run in a separate thread.";
		final EClass fork_scope[] = {ThingMLPackage.eINSTANCE.getFunction()};
		final Annotation fork_annotation = new Annotation(fork_name, fork_desc, fork_scope);
		annotations.put(fork_name, fork_annotation);
		
		//@arduino_stdout
		final String astd_name = "arduino_stdout";
		final String astd_desc = "Specifies the stdout device e.g. Serial";
		final EClass astd_scope[] = {ThingMLPackage.eINSTANCE.getFunction()};
		final Annotation astd_annotation = new Annotation(astd_name, astd_desc, astd_scope);
		annotations.put(astd_name, astd_annotation);
		
		//@arduino_stdout_baudrate
		final String astdbaud_name = "arduino_stdout_baudrate";
		final String astdbaud_desc = "Specifies the baudrate for the (Serial) stdout device e.g. 9600";
		final EClass astdbaud_scope[] = {ThingMLPackage.eINSTANCE.getFunction()};
		final Annotation astdbaud_annotation = new Annotation(astdbaud_name, astdbaud_desc, astdbaud_scope);
		annotations.put(astdbaud_name, astdbaud_annotation);

		/** MESSAGES AND PORTS **/
		
		//@code
		final String code_name = "code";
		final String code_desc = "Sets a code to a given message. Used by serializers to identify messages.";
		final EClass code_scope[] = {ThingMLPackage.eINSTANCE.getMessage()};
		final Annotation code_annotation = new IntegerAnnotation(code_name, code_desc, code_scope, true);
		annotations.put(code_name, code_annotation);
		
		//@sync_send
		final String sync_name = "sync_send";
		final String sync_desc = "Makes a port syncrhonous";
		final EClass sync_scope[] = {ThingMLPackage.eINSTANCE.getPort()};
		final Annotation sync_annotation = new Annotation(sync_name, sync_desc, sync_scope);
		annotations.put(sync_name, sync_annotation);
		
		
		/** TYPES **/
		
		//@type_checker
		final String tc_name = "type_checker";
		final String tc_desc = "Specifies the abstract type of a ThingML-defined primitive type. Used by the type checker.";
		final EClass tc_scope[] = {ThingMLPackage.eINSTANCE.getType()};
		final String tc_values[] = {"Byte", "Integer", "Boolean", "Character", "String", "Real", "Void", "Object"};
		final Annotation tc_annotation = new EnumAnnotation(tc_name, tc_desc, tc_scope, tc_values);
		annotations.put(tc_name, tc_annotation);
		
		//@ignore
		final String ig_name = "ignore";
		final String ig_desc = "Ignores specific warnings raised by the checker";
		final EClass ig_scope[] = {ThingMLPackage.eINSTANCE.getAnnotatedElement()};
		final String ig_values[] = {"not-used", "sink", "type-warning"};
		final Annotation ig_annotation = new EnumAnnotation(ig_name, ig_desc, ig_scope, ig_values);
		annotations.put(ig_name, ig_annotation);
		
		//@c_type
		final String c_name = "c_type";
		final String c_desc = "Specifies the concrete C type of a ThingML-defined type.";
		final EClass c_scope[] = {ThingMLPackage.eINSTANCE.getType()};
		final Annotation c_annotation = new Annotation(c_name, c_desc, c_scope);
		annotations.put(c_name, c_annotation);
		
		//@c_type
		final String a_name = "arduino_type";
		final String a_desc = "Specifies the concrete Arduino type (if different from the c_type) of a ThingML-defined type.";
		final EClass a_scope[] = {ThingMLPackage.eINSTANCE.getType()};
		final Annotation a_annotation = new Annotation(a_name, a_desc, a_scope);
		annotations.put(a_name, a_annotation);
		
		//@go_type
		final String go_name = "go_type";
		final String go_desc = "Specifies the concrete Go type of a ThingML-defined type.";
		final EClass go_scope[] = {ThingMLPackage.eINSTANCE.getType()};
		final Annotation go_annotation = new Annotation(go_name, go_desc, go_scope);
		annotations.put(go_name, go_annotation);
		
		//@java_type
		final String java_name = "java_type";
		final String java_desc = "Specifies the concrete Java type of a ThingML-defined type.";
		final EClass java_scope[] = {ThingMLPackage.eINSTANCE.getType()};
		final Annotation java_annotation = new Annotation(java_name, java_desc, java_scope);
		annotations.put(java_name, java_annotation);
		
		//@js_type
		final String js_name = "js_type";
		final String js_desc = "Specifies the concrete JavaScript type of a ThingML-defined type.";
		final EClass js_scope[] = {ThingMLPackage.eINSTANCE.getType()};
		final Annotation js_annotation = new Annotation(js_name, js_desc, js_scope);
		annotations.put(js_name, js_annotation);
		
		/** DEBUG **/
		
		//@mock //FIXME: should actually be a flag...
		final String mock_name = "mock";
		final String mock_desc = "Generates a graphical mock-up for this thing.";
		final EClass mock_scope[] = {ThingMLPackage.eINSTANCE.getThing()};
		final Annotation mock_annotation = new Annotation(mock_name, mock_desc, mock_scope);
		annotations.put(mock_name, mock_annotation);
		
		final String html_body_name = "html_body";
		final String html_body_desc = "Content to be appended to HTML <body>, when using Browser compiler";
		final EClass html_body_scope[] = {ThingMLPackage.eINSTANCE.getConfiguration()};
		final Annotation html_body_annotation = new Annotation(html_body_name, html_body_desc, html_body_scope);
		annotations.put(html_body_name, html_body_annotation);

		final String html_head_name = "html_head";
		final String html_head_desc = "Content to be appended to HTML <head>, when using Browser compiler";
		final EClass html_head_scope[] = {ThingMLPackage.eINSTANCE.getConfiguration()};
		final Annotation html_head_annotation = new Annotation(html_head_name, html_head_desc, html_head_scope);
		annotations.put(html_head_name, html_head_annotation);
		
		final String stdout_sync_name = "stdout_sync";
		final String stdout_sync_desc = "Syncs prints to stdout";
		final EClass stdout_sync_scope[] = {ThingMLPackage.eINSTANCE.getConfiguration(), ThingMLPackage.eINSTANCE.getThing()};
		final Annotation stdout_sync_annotation = new Annotation(stdout_sync_name, stdout_sync_desc, stdout_sync_scope);
		annotations.put(stdout_sync_name, stdout_sync_annotation);
		
		final String checker_fail_name = "checker_should_fail";
		final String checker_fail_desc = "Used in the Checker tests. Specifies whether or not the checker should fail.";
		final EClass checker_fail_scope[] = {ThingMLPackage.eINSTANCE.getThing()};
		final Annotation checker_fail_annotation = new Annotation(checker_fail_name, checker_fail_desc, checker_fail_scope);
		annotations.put(checker_fail_name, checker_fail_annotation);
		
		/** NodeJs Package **/
		annotations.put("nodejs_package", new Annotation(
			"nodejs_package",
			"If present, the configuration is compiled as a NodeJS NPM package",
			new EClass[] {ThingMLPackage.eINSTANCE.getConfiguration()}
		));
		
		annotations.put("nodejs_package_name", new Annotation(
			"nodejs_package_name",
			"NPM Package name override. Defaults to the project name in kebab-case.",
			new EClass[] {ThingMLPackage.eINSTANCE.getConfiguration()}
		));
		
		annotations.put("nodejs_package_description", new Annotation(
			"nodejs_package_description",
			"NPM Package description",
			new EClass[] {ThingMLPackage.eINSTANCE.getConfiguration()}
		));
		
		annotations.put("nodejs_package_author_name", new Annotation(
			"nodejs_package_author_name",
			"NPM Package author's name",
			new EClass[] {ThingMLPackage.eINSTANCE.getConfiguration()}
		));
		
		annotations.put("nodejs_package_author_email", new Annotation(
			"nodejs_package_author_email",
			"NPM Package author's email",
			new EClass[] {ThingMLPackage.eINSTANCE.getConfiguration()}
		));
		
		annotations.put("nodejs_package_license", new Annotation(
			"nodejs_package_license",
			"NPM Package license",
			new EClass[] {ThingMLPackage.eINSTANCE.getConfiguration()}
		));
		
		annotations.put("nodejs_package_version", new Annotation(
			"nodejs_package_version",
			"NPM Package's version. 1.0.0 by default. Should use semantic versioning.",
			new EClass[] {ThingMLPackage.eINSTANCE.getConfiguration()}
		));
		
		annotations.put("nodejs_package_repository", new Annotation(
			"nodejs_package_repository",
			"NPM Package's repository.",
			new EClass[] {ThingMLPackage.eINSTANCE.getConfiguration()}
		));
		
		annotations.put("nodejs_package_private", new Annotation(
			"nodejs_package_private",
			"Is the package private and can't be published. Defaults to true.",
			new EClass[] {ThingMLPackage.eINSTANCE.getConfiguration()}
		));
		
		annotations.put("nodejs_package_publish_config_registry", new Annotation(
			"nodejs_package_publish_config_registry",
			"Optional registry to publish NPM packages on other registries.",
			new EClass[] {ThingMLPackage.eINSTANCE.getConfiguration()}
		));
	}
	
	public static String toMD() {
		final StringBuilder builder = new StringBuilder();
		builder.append("# List of officially-supported annotations\n\n");
		
		annotations.values().stream().sorted(Comparator.comparing(Annotation::getName)).forEach(a -> {
			builder.append("## @" + a.name + "\n\n");
			builder.append("- *description*: " + a.description + "\n");
			builder.append("- *applies to*: " + ((a.scope == null || a.scope.length == 0)? "all" : a.scopeToString()) + "\n\n");
		});

		return builder.toString();
	}
	
	public static List<String> availableAnnotations(EObject o) {
		final List<String> result = new ArrayList<>();
		for(Annotation a : annotations.values()) {
			for(EClass clazz : a.scope) {
				if (clazz.isInstance(o)) {
					result.add(a.name);
					break;
				}
			}
		}
		return result;
	}
	
	public static void main(String args[]) {
		System.out.println(toMD());
	}
}
