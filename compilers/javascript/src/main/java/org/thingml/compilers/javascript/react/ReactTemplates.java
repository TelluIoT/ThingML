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
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;

public class ReactTemplates {
	private static Section renderReturn(Section parent) {
		parent.append("return (");
		Section renderReturn = parent.section("render-return").lines().indent();
		parent.append(");");
		return renderReturn;
	}
	// TODO: Check for annotations in all of the following:
	
	public static void configuartionImports(Configuration cfg, Section imports) {
		imports.append("import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';");
		imports.append("import {Card, CardTitle, CardText} from 'material-ui/Card';");
	}
	public static void configurationRender(Configuration cfg, Section render) {
		render.append("const instances = this.instances();");
		
		Section ret = renderReturn(render);
		ret.append("<MuiThemeProvider>");
		Section theme = ret.section("mui-theme-provider").lines().indent();
		theme.append("<Card>");
		Section card = theme.section("card").lines().indent();
		card.append("<CardTitle title='"+cfg.getName()+"' subtitle='Configuration'/>");
		card.append("<CardText>");
		Section cardText = card.section("card-text").lines().indent();
		cardText.append("{instances}");
		card.append("</CardText>");
		theme.append("</Card>");
		ret.append("</MuiThemeProvider>");
	}
	
	
	public static void thingImports(Thing thing, Section imports) {
		imports.append("import {Card, CardTitle, CardText} from 'material-ui/Card';");
		imports.append("import Chip from 'material-ui/Chip';");
	}
	public static void thingRender(Thing thing, Section render) {
		Section propRefs = render.section("property-references").lines();
		
		Section ret = renderReturn(render);
		ret.append("<Card>");
		Section card = ret.section("card").lines().indent();
		card.append("<CardTitle title={this.name+' : "+thing.getName()+"'} subtitle='Thing instance'/>");
		
		card.append("<CardText style={{display: 'flex', flexWrap: 'wrap'}}>");
		Section chipCardText = card.section("card-text").lines().indent();
		Section chipDiv = chipCardText.section("chips").lines().indent();
		for (Property p : thing.getProperties()) {
			String propName = ThingMLElementHelper.qname(p, "_") + "_var";
			propRefs.append("const "+propName+" = this."+propName+";");
			
			chipDiv.append("<Chip style={{margin:4}}>"+p.getName()+" : {"+propName+"}</Chip>");
		}
		card.append("</CardText>");
		
		card.append("<CardText>");
		propRefs.append("const statemachine = this.statemachine();");
		card.section("card-text").lines().indent().append("{statemachine}");
		card.append("</CardText>");
		
		ret.append("</Card>");
	}
	public static void statecontainerRender(StateContainer sc, String type, Section render) {
		Section ret = renderReturn(render);
		
		ret.append("<Card>");
		Section card = ret.section("card").lines().indent();
		card.append("<CardTitle title={state.name} subtitle='"+type+"'/>");
		ret.append("</Card>");
	}
}
