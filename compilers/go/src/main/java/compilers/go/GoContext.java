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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.Context;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.builder.Element;
import org.thingml.compilers.builder.Section;
import org.thingml.compilers.utils.OpaqueThingMLCompiler;
import org.thingml.utilities.logging.Logger;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.TypeRef;

public class GoContext extends Context {

	private Map<String, GoSourceBuilder> generatedFiles = new HashMap<String, GoSourceBuilder>();
	private Logger logger;
	private GoNaming namer = new GoNaming();

	public GoContext(ThingMLCompiler compiler, Logger log) {
		super(compiler, "break","case","chan","const","continue",
                        "default","defer","else","fallthrough","for",
                        "func","go","goto","if","import",
                        "interface","map","package","range","return",
                        "select","struct","switch","type","var");
		this.logger = log;
	}


	public GoSourceBuilder getSourceBuilder(String path) {
		if (generatedFiles.containsKey(path))
			return generatedFiles.get(path);
		else {
			GoSourceBuilder builder = new GoSourceBuilder();
			generatedFiles.put(path, builder);
			return builder;
		}
	}

	@Override
	public void writeGeneratedCodeToFiles() {
		super.writeGeneratedCodeToFiles();
		try {
			for (Entry<String, GoSourceBuilder> generatedFile : generatedFiles.entrySet()) {
				File outFile = new File(this.getOutputDirectory(), generatedFile.getKey());
				File outDir = outFile.getParentFile();
				if (outDir != null) outDir.mkdirs();
				FileWriter writer = new FileWriter(outFile);
				generatedFile.getValue().write(writer);
				writer.close();
			}
		} catch (IOException e) {
			System.err.println("Problem while dumping the code");
            e.printStackTrace();
		}
	}

	public Logger Logger() {
		return this.logger;
	}

	/* --- NEW naming helpers --- */
	public GoNaming getNamer() {
		return namer;
	}

	public Element getNameFor(EObject object) {
		return namer.getNameFor(object);
	}
	public Element getNameFor(String prefix, EObject object) {
		return Section.Orphan("prefixedname").append(prefix).append(getNameFor(object));

	}
	public Element getNameFor(EObject object, String postfix) {
		return Section.Orphan("postfixedname").append(getNameFor(object)).append(postfix);
	}
	public Element getNameFor(String prefix, EObject object, String postfix) {
		return Section.Orphan("prepostfixedname").append(prefix).append(getNameFor(object)).append(postfix);
	}

	public Element getPointerNameFor(EObject object) {
		return Section.Orphan("pointer").append(GoSourceBuilder.STAR_E).append(getNameFor(object));
	}


	/* --- Auto-casting --- */
	public boolean shouldAutocast = false;

	/* --- Naming helpers --- */
	public String getTypesPath() {
		return "Types.go";
	}

	public String getThingPath(Thing t) {
		return "Thing"+t.getName()+".go";
	}

	public String getConfigurationPath(Configuration cfg) {
		return cfg.getName()+".go";
	}

	/*
	public Element getTypeName(Type t) {
		if (t instanceof Enumeration)
			return getNameFor(t);
		if (AnnotatedElementHelper.hasAnnotation(t, "go_type"))
			return new Element(AnnotatedElementHelper.firstAnnotation(t, "go_type"));
		return new Element("interface{}");
	}

	public Element addTypeRef(TypeRef ref, Section parent) {
		if (ref.isIsArray()) parent.append("[]");
		parent.append(getTypeName(ref.getType()));
	}
	*/

	/*
	public String getMessageName(Message msg) {
		return "Thing"+ThingMLHelpers.findContainingThing(msg).getName()+"Msg"+msg.getName();
	}

	public String getPortName(Port prt) {
		return "Thing"+ThingMLHelpers.findContainingThing(prt).getName()+"Port"+prt.getName();
	}

	public String getStateContainerName(StateContainer sc) {
		String name = "";
		// Check the containing element
		if (sc.eContainer() instanceof Thing) {
			// This should be the top-level statechart
			name += "Thing"+((Thing)sc.eContainer()).getName()+"Statechart";
		} else {
			// This is within another state container, add its name
			name += getStateContainerName((StateContainer)sc.eContainer());
			// Also add the type of ourself
			if (sc instanceof CompositeState) name += "Composite";
			else if (sc instanceof Region) name += "Region";
			else if (sc instanceof Session) name += "Session";
		}
		// Check the name of the current element
		if (sc.getName() != null) {
			name += sc.getName();
		} else if (sc instanceof Region) {
			// Find the region number in the parent array
			CompositeState parent = (CompositeState)sc.eContainer();
			name += parent.getRegion().indexOf(sc);
		}
		return name;
	}
	public String getStateName(State s) {
		if (s instanceof StateContainer) return getStateContainerName((StateContainer)s);
		else return getStateContainerName((StateContainer)s.eContainer())+"State"+s.getName();
	}
	*/

	public static Element defaultInstanceStateName = GoSourceBuilder.STATE_E;
	private Element currentInstanceStateName = defaultInstanceStateName;
	public Element getCurrentInstanceStateName() { return this.currentInstanceStateName; }
	public void setCurrentInstanceStatename(Element name) { this.currentInstanceStateName = name; }
	public void resetCurrentInstanceStateName() { this.currentInstanceStateName = defaultInstanceStateName; }

	/* --- Port IDs --- */
	private Map<Port,Integer> portIDs = new HashMap<Port, Integer>();
	public Integer getPortID(Port prt) {
		if (portIDs.containsKey(prt)) return portIDs.get(prt);
		Integer newID = portIDs.size();
		portIDs.put(prt, newID);
		return newID;
	}

	/* --- Thing contexts --- */
	public ThingContext currentThingContext = null;

	private Map<Thing, ThingContext> thingContexts = new HashMap<Thing, ThingContext>();
	public ThingContext setCurrentThingContext(Thing thing) {
		if (!this.thingContexts.containsKey(thing))
			this.thingContexts.put(thing, new ThingContext(thing));

		this.currentThingContext = this.thingContexts.get(thing);
		return this.currentThingContext;
	}
	public ThingContext unsetCurrentThingContext(Thing thing) {
		// TODO: Should we check that it actually is the right context?
		ThingContext current = this.currentThingContext;
		this.currentThingContext = null;
		return current;
	}

	public static class ThingContext {
		private Thing thing;
		private Set<String> addedImports = new HashSet<String>();
		private Section imports = null;

		public boolean messageUsedInTransition = false;
		public boolean instanceUsedInInitialisation = false;

		private ThingContext(Thing thing) {
			this.thing = thing;
		}

		public void setImportsSection(Section imports) {
			if (this.imports == null) this.imports = imports;
		}

		public void addImports(String...imports) {
			for (String i : imports)
				if (this.addedImports.add(i))
					this.imports.append("\""+i+"\"");
		}
	}

	public void currentThingImport(String...imports) {
		if (this.currentThingContext != null)
			this.currentThingContext.addImports(imports);
	}
	public void currentThingImportGosm() { this.currentThingImport("github.com/SINTEF-9012/gosm"); }

	/* --- Variable assignment type (for arrays mostly) --- */
	private TypeRef currentVariableAssignmentType = null;
	public TypeRef getCurrentVariableAssignmentType() {
		return this.currentVariableAssignmentType;
	}
	public void setCurrentVariableAssignmentType(TypeRef type) {
		this.currentVariableAssignmentType = type;
	}
	public void resetCurrentVariableAssignmentType() {
		this.currentVariableAssignmentType = null;
	}

	/* --- Check if variable is used in an action block --- */
	public boolean checkIfLocalVariableIsUsed(LocalVariable variable) {
		ThingMLModel model = ThingMLHelpers.findContainingModel(variable);
		return !EcoreUtil.UsageCrossReferencer.find(variable, model).isEmpty();
	}


	/* --- Some logging helpers --- */
	public void println(String line) {
		if (getCompiler() instanceof OpaqueThingMLCompiler) {
			OpaqueThingMLCompiler compiler = (OpaqueThingMLCompiler)getCompiler();
			compiler.println(line);
		} else {
			new PrintStream(getCompiler().getMessageStream()).println(line);
		}
	}
	public void errorln(String line) {
		if (getCompiler() instanceof OpaqueThingMLCompiler) {
			OpaqueThingMLCompiler compiler = (OpaqueThingMLCompiler)getCompiler();
			compiler.erroln(line);
		} else {
			new PrintStream(getCompiler().getErrorStream()).println(line);
		}
	}
}
