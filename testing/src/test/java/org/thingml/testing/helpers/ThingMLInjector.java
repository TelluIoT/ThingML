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
package org.thingml.testing.helpers;

import java.io.StringReader;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.impl.ListBasedDiagnosticConsumer;
import org.eclipse.xtext.xtext.XtextLinker;
import org.thingml.xtext.ThingMLStandaloneSetup;
import org.thingml.xtext.parser.antlr.ThingMLParser;
import org.thingml.xtext.services.ThingMLGrammarAccess;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class ThingMLInjector {
	@Inject
	private ThingMLParser parser;
	@Inject
	private XtextLinker linker;
	
	private ThingMLInjector() {
		Injector inj = new ThingMLStandaloneSetup().createInjectorAndDoEMFRegistration();
		inj.injectMembers(this);
	}
	
	private static ThingMLInjector instance = new ThingMLInjector();
	
	public static ThingMLGrammarAccess grammar() { return instance.parser.getGrammarAccess(); }
	
	@SuppressWarnings("unchecked")
	public static <T extends EObject> T parseString(ParserRule rule, String text) {
		IParseResult result = instance.parser.parse(rule, new StringReader(text));
		if (!result.hasSyntaxErrors())
			return (T)result.getRootASTElement();
		else {
			String msg = "";
			for (INode node : result.getSyntaxErrors())
				msg += node.getSyntaxErrorMessage()+"\n";
			throw new RuntimeException(msg);
		}
	}
	
	public static void linkFrom(EObject root) {
		ListBasedDiagnosticConsumer consumer = new ListBasedDiagnosticConsumer();
		instance.linker.linkModel(root, consumer);
		// I don't think we need a resolveAll() here...
		// TODO: Maybe we should throw an error here when it fails to link?
		List<Diagnostic> errors = consumer.getResult(Severity.ERROR);
		if (!errors.isEmpty()) {
			String msg = "";
			for (Diagnostic error : errors)
				msg += error.getMessage()+"\n";
			throw new RuntimeException(msg);
		}
	}
	
	/* --- Some type specific helpers --- */
	public static void addProperties(Thing root, String... properties) {
		ParserRule rule = grammar().getPropertyRule();
		for (String property : properties) {
			Property pProperty = parseString(rule, property);
			if (pProperty != null) {
				root.getProperties().add(pProperty);
				linkFrom(pProperty);
			}
		}
	}
	
	public static void addActions(ActionBlock root, String... actions) {
		ParserRule rule = grammar().getActionRule();
		for (String action : actions) {
			Action pAction = parseString(rule, action);
			if (pAction != null) {
				root.getActions().add(pAction);
				linkFrom(pAction);
			}
		}
	}
}
