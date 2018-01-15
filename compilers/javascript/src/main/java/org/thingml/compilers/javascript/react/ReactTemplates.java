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
package org.thingml.compilers.javascript.react;

import org.thingml.compilers.builder.Section;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Thing;

public class ReactTemplates {
	public static void defaultConfiguration(Configuration cfg, Section render) {
		render.append("<div className=\"thingml-configuration\">");
		Section div = render.section("configuration-div").lines().indent();
		div.append("<p>"+cfg.getName()+"</p>");
		div.append("{this.instances()}");
		render.append("</div>");
	}
	
	public static void defaultThing(Thing thing, Section render) {
		render.append("<div className=\"thingml-thing\">");
		render.section("thing-div").lines().indent()
			  .section("thing").append("<p>").append(thing.getName()).append("</p>");
		render.append("</div>");
	}
}
