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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.thingml.xtext.thingML.ThingMLPackage;

public class AnnotationRegistry {

	public static Map<String, Annotation> annotations = new HashMap<>();
	
	static {
		
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
		
		//@js_dep
		final String npm_name = "js_dep";
		final String npm_desc = "Adds an NPM dependecy to the generated package.json file.";
		final EClass npm_scope[] = {ThingMLPackage.eINSTANCE.getType()};
		final Annotation npm_annotation = new Annotation(npm_name, npm_desc, npm_scope);
		annotations.put(npm_name, npm_annotation);

		/** MESSAGES AND PORTS **/
		
		//@code
		final String code_name = "code";
		final String code_desc = "Sets a code to a given message. Used by serializers to identify messages.";
		final EClass code_scope[] = {ThingMLPackage.eINSTANCE.getMessage()};
		final Annotation code_annotation = new Annotation(code_name, code_desc, code_scope);
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
		final EClass tc_scope[] = {ThingMLPackage.eINSTANCE.getPrimitiveType()};
		final String tc_values[] = {"Byte", "Integer", "Boolean", "Character", "String", "Real", "Void", "Object"};
		final Annotation tc_annotation = new EnumAnnotation(tc_name, tc_desc, tc_scope, tc_values);
		annotations.put(tc_name, tc_annotation);
		
		//@c_type
		final String c_name = "c_type";
		final String c_desc = "Specifies the concrete C type of a ThingML-defined type.";
		final EClass c_scope[] = {ThingMLPackage.eINSTANCE.getType()};
		final Annotation c_annotation = new Annotation(c_name, c_desc, c_scope);
		annotations.put(c_name, c_annotation);
		
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
		
	}
}
