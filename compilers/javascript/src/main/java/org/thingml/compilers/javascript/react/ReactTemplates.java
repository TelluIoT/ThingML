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
import org.thingml.compilers.javascript.JSContext;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.FinalState;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;

public class ReactTemplates {
	private static Section renderReturn(Section parent) {
		parent.append("return (");
		Section renderReturn = parent.section("render-return").lines().indent();
		parent.append(");");
		return renderReturn;
	}
	
	public static void configuartionImports(Configuration cfg, Section imports, JSContext jctx) {
		if (jctx.hasContextAnnotation("react-custom-templates")) {
			for (String imp : AnnotatedElementHelper.annotation(cfg, "react_import"))
				imports.append("import "+imp+";");
			for (String req : AnnotatedElementHelper.annotation(cfg, "react_require"))
				imports.append("const "+req+";");
		} else {
			imports.append("import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';");
			imports.append("import {Card, CardTitle, CardText} from 'material-ui/Card';");
		}
	}
	public static void configurationRender(Configuration cfg, Section render, JSContext jctx) {
		render.append("const configuration = this;");
		
		if (jctx.hasContextAnnotation("react-custom-templates")) {
			if (AnnotatedElementHelper.hasAnnotation(cfg, "templatefun")) {
				StringBuilder builder = render.stringbuilder("template-function");
				builder.append(AnnotatedElementHelper.firstAnnotation(cfg, "templatefun"));
			} else {
				
				Section ret = renderReturn(render);
				if (AnnotatedElementHelper.hasAnnotation(cfg, "template")) {
					StringBuilder builder = ret.stringbuilder("template");
					builder.append(AnnotatedElementHelper.firstAnnotation(cfg, "template"));
				} else {
					// If no custom render is supplied, just render all instances
					ret.append("<React.Fragment>");
					ret.section("instances").lines().indent().append("{configuration.getinstances()}");
					ret.append("</React.Fragment>");
				}
			}
		} else {
			// Default template
			Section ret = renderReturn(render);
			ret.append("<MuiThemeProvider>");
			Section theme = ret.section("mui-theme-provider").lines().indent();
			theme.append("<Card>");
			Section card = theme.section("card").lines().indent();
			card.append("<CardTitle title='"+cfg.getName()+"' subtitle='Configuration'/>");
			card.append("<CardText>");
			Section cardText = card.section("card-text").lines().indent();
			cardText.append("{configuration.getinstances()}");
			card.append("</CardText>");
			theme.append("</Card>");
			ret.append("</MuiThemeProvider>");
		}
	}
	
	
	public static void thingImports(Thing thing, Section imports, JSContext jctx) {
		if (jctx.hasContextAnnotation("react-custom-templates")) {
			for (String imp : AnnotatedElementHelper.annotation(thing, "react_import"))
				imports.append("import "+imp+";");
			for (String req : AnnotatedElementHelper.annotation(thing, "react_require"))
				imports.append("const "+req+";");
		} else {
			imports.append("import {Card, CardTitle, CardText} from 'material-ui/Card';");
			imports.append("import Chip from 'material-ui/Chip';");
		}
	}
	public static void thingRender(Thing thing, Section render, JSContext jctx) {
		render.append("const instance = this;");
		
		if (jctx.hasContextAnnotation("react-custom-templates")) {
			if (AnnotatedElementHelper.hasAnnotation(thing, "templatefun")) {
				StringBuilder builder = render.stringbuilder("template-function");
				builder.append(AnnotatedElementHelper.firstAnnotation(thing, "templatefun"));
			} else {
				Section ret = renderReturn(render);
				if (AnnotatedElementHelper.hasAnnotation(thing, "template")) {
					StringBuilder builder = ret.stringbuilder("template");
					builder.append(AnnotatedElementHelper.firstAnnotation(thing, "template"));
				} else {
					// If not template is provided, just render the statemachine
					ret.append("<React.Fragment>");
					ret.section("instances").lines().indent().append("{instance.getstatemachine()}");
					ret.append("</React.Fragment>");
				}
			}
		} else {
			// Default template
			Section ret = renderReturn(render);
			ret.append("<Card>");
			Section card = ret.section("card").lines().indent();
			card.append("<CardTitle title={instance.name+' : "+thing.getName()+"'} subtitle='Thing instance'/>");
			
			card.append("<CardText style={{display: 'flex', flexWrap: 'wrap'}}>");
			Section chipCardText = card.section("card-text").lines().indent();
			Section chipDiv = chipCardText.section("chips").lines().indent();
			for (Property p : thing.getProperties())
				chipDiv.append("<Chip style={{margin:4}}>"+p.getName()+" : {instance."+ThingMLElementHelper.qname(p, "_")+"_var}</Chip>");
			card.append("</CardText>");
			
			card.append("<CardText>");
			card.section("card-text").lines().indent().append("{instance.getstatemachine()}");
			card.append("</CardText>");
			
			if (ThingHelper.hasSession(thing)) {
				card.append("<CardText>");
				card.section("card-text").lines().indent().append("{instance.getsessions()}");
				card.append("</CardText>");
			}
			
			ret.append("</Card>");
		}
	}
	public static void statecontainerRender(StateContainer sc, String type, Section render, JSContext jctx) {
		render.append("const instance = this;");
		
		if (jctx.hasContextAnnotation("react-custom-templates")) {
			boolean renderAlways = AnnotatedElementHelper.annotationOrElse(sc, "render", "active").toLowerCase().equals("always");
			Section body = render.section("body").lines();
			if (!renderAlways)
				body.indent().before("if (state.isactive) {").after("}");
			
			if (AnnotatedElementHelper.hasAnnotation(sc, "templatefun")) {
				StringBuilder builder = body.stringbuilder("template-function");
				builder.append(AnnotatedElementHelper.firstAnnotation(sc, "templatefun"));
			} else {
				Section ret = renderReturn(body);
				if (AnnotatedElementHelper.hasAnnotation(sc, "template")) {
					StringBuilder builder = ret.stringbuilder("template");
					builder.append(AnnotatedElementHelper.firstAnnotation(sc, "template"));
				} else {
					// If not template is provided, just render all substates (and regions)
					ret.append("<React.Fragment>");
					ret.section("substates").lines().indent().append("{state.getsubstates()}");
					ret.section("regions").lines().indent().append("{state.getregions()}");
					ret.append("</React.Fragment>");
				}
			}
		} else {
			// Default template
			Section ret = renderReturn(render);
			ret.append("<Card style={{backgroundColor: state.isactive ? '#ddd' : '#fff'}}>");
			Section card = ret.section("card").lines().indent();
			card.append("<CardTitle title={state.name} subtitle='"+type+"'/>");
			
			if (sc instanceof CompositeState) {
				card.append("<CardText style={{display: 'flex', flexWrap: 'wrap'}}>");
				Section chipCardText = card.section("card-text").lines().indent();
				Section chipDiv = chipCardText.section("chips").lines().indent();
				for (Property p : ((CompositeState)sc).getProperties())
					chipDiv.append("<Chip style={{margin:4}}>"+p.getName()+" : {instance."+ThingMLElementHelper.qname(p, "_")+"_var}</Chip>");
				card.append("</CardText>");
			}
			
			card.append("<CardText>");
			Section cardText = card.section("card-text").lines().indent();
			cardText.append("{state.getsubstates()}");
			if (sc instanceof CompositeState) cardText.append("{state.getregions()}");
			card.append("</CardText>");
			ret.append("</Card>");
		}
	}
	public static void stateRender(State s, Section render, JSContext jctx) {
		render.append("const instance = this;");
		
		if (jctx.hasContextAnnotation("react-custom-templates")) {
			boolean renderAlways = AnnotatedElementHelper.annotationOrElse(s, "render", "active").toLowerCase().equals("always");
			Section body = render.section("body").lines();
			if (!renderAlways)
				body.indent().before("if (state.isactive) {").after("}");
			
			if (AnnotatedElementHelper.hasAnnotation(s, "templatefun")) {
				StringBuilder builder = body.stringbuilder("template-function");
				builder.append(AnnotatedElementHelper.firstAnnotation(s, "templatefun"));
			} else {
				Section ret = renderReturn(body);
				if (AnnotatedElementHelper.hasAnnotation(s, "template")) {
					StringBuilder builder = ret.stringbuilder("template");
					builder.append(AnnotatedElementHelper.firstAnnotation(s, "template"));
				} else {
					// If not template is provided, nothing is rendered
					ret.append("null");
				}
			}
		} else {
			// Default template
			Section propRefs = render.section("property-references").lines();
			Section ret = renderReturn(render);
			
			String type = s instanceof FinalState ? "Final state" : "state";
			
			ret.append("<Card style={{backgroundColor: state.isactive ? '#ddd' : '#fff'}}>");
			Section card = ret.section("card").lines().indent();
			card.append("<CardTitle title={state.name} subtitle='"+type+"'/>");
			
			card.append("<CardText style={{display: 'flex', flexWrap: 'wrap'}}>");
			Section chipCardText = card.section("card-text").lines().indent();
			Section chipDiv = chipCardText.section("chips").lines().indent();
			for (Property p : s.getProperties()) {
				String propName = ThingMLElementHelper.qname(p, "_") + "_var";
				propRefs.append("const "+propName+" = this."+propName+";");
				
				chipDiv.append("<Chip style={{margin:4}}>"+p.getName()+" : {"+propName+"}</Chip>");
			}
			card.append("</CardText>");
			
			ret.append("</Card>");
		}
	}
}
