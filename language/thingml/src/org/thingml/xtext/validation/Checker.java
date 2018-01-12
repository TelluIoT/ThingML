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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.xtext.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.validation.rules.*;

/**
 *
 * @author sintef
 */
public class Checker {
	public Set<CheckerInfo> Errors;
	public Set<CheckerInfo> Warnings;
	public Set<CheckerInfo> Notices;
	public TypeChecker typeChecker = new TypeChecker();
	private Set<Rule> Rules;
	private String compiler;
	private String generic;
	
	AbstractThingMLValidator validator;

	public Checker(String compiler, AbstractThingMLValidator v) {
		this.validator = v;
		Rules = new HashSet<Rule>();
		Errors = new TreeSet<CheckerInfo>();
		Warnings = new TreeSet<CheckerInfo>();
		Notices = new TreeSet<CheckerInfo>();

		this.compiler = compiler;
		generic = "ThingML";

		//Rules.add(new ThingsUsage());
		Rules.add(new PortsUsage());
		Rules.add(new MessagesUsage());
		Rules.add(new ConnectorCycles());
		Rules.add(new InternalTransitions());
		Rules.add(new AutotransitionCycles());
		//Rules.add(new NonDeterministicTransitions());
		//Rules.add(new FunctionImplementation());
		Rules.add(new FunctionUsage());
		//Rules.add(new StatesUsage());
		//Rules.add(new VariableUsage());
		Rules.add(new ControlStructures());
		Rules.add(new PropertyInitialization());
		Rules.add(new LostMessages());
	}

	public void do_generic_check(Configuration cfg, boolean reportInEditor) {
		Errors.clear();
		Warnings.clear();
		Notices.clear();
		List<String> notChecked = AnnotatedElementHelper.annotation(cfg, "SuppressWarnings");
		for (Rule r : Rules) {
			if (!notChecked.contains(r.getName()))
				r.check(cfg, this);
		}
		if (reportInEditor && validator != null)
			addXTextError();
	}

	public void do_generic_check(ThingMLModel model, boolean reportInEditor) {
		Errors.clear();
		Warnings.clear();
		Notices.clear();
		for (Rule r : Rules) {
			r.check(model, this);
		}
		if (reportInEditor && validator!=null)
			addXTextError();
	}
	
	private void addXTextError() {
		for(CheckerInfo error : Errors) {
			try {
				validator.acceptError(error.message, error.element, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
			} catch (IllegalArgumentException e) {}
		}
		for(CheckerInfo error : Warnings) {
			try {
				validator.acceptWarning(error.message, error.element, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
			} catch (IllegalArgumentException e) {}
		}
	}

	// Must be implemented and must contain a call to do_generic_check(cfg)
	public void do_check(Configuration cfg, boolean reportInEditor) {
		do_generic_check(cfg, reportInEditor);
	}


	// ---------------------- Accessors ----------------------

	public void addError(String msg, EObject el) {
		Errors.add(new CheckerInfo(InfoType.ERROR, compiler, msg, el));
	}

	public void addError(String compiler, String msg, EObject el) {
		Errors.add(new CheckerInfo(InfoType.ERROR, compiler, msg, el));
	}

	public void addGenericError(String msg, EObject el) {
		Errors.add(new CheckerInfo(InfoType.ERROR, generic, msg, el));
	}

	public void addWarning(String msg, EObject el) {
		Warnings.add(new CheckerInfo(InfoType.WARNING, compiler, msg, el));
	}

	public void addWarning(String compiler, String msg, EObject el) {
		Warnings.add(new CheckerInfo(InfoType.WARNING, compiler, msg, el));
	}

	public void addGenericWarning(String msg, EObject el) {
		Warnings.add(new CheckerInfo(InfoType.WARNING, generic, msg, el));
	}

	public void addNotice(String msg, EObject el) {
		Notices.add(new CheckerInfo(InfoType.NOTICE, compiler, msg, el));
	}

	public void addNotice(String compiler, String msg, EObject el) {
		Notices.add(new CheckerInfo(InfoType.NOTICE, compiler, msg, el));
	}

	public void addGenericNotice(String msg, EObject el) {
		Notices.add(new CheckerInfo(InfoType.NOTICE, generic, msg, el));
	}

	public boolean containsErrors() {
		return !Errors.isEmpty();
	}

	public boolean containsWarnings() {
		return !Warnings.isEmpty();
	}

	public boolean containsNotices() {
		return !Notices.isEmpty();
	}
	
	public void printReport() {
		printNotices();
		printWarnings();
		printErrors();
	}

	public void printErrors() {
		String file = "";
		for (final CheckerInfo i : Errors) {
			if (i.file != null && !i.file.equals(file)) {
				System.err.println("Errors in file " + i.file);
				file = i.file;
			}
			System.err.println("\t" + i.toString());
		}
	}

	public void printWarnings() {
		String file = "";
		for (final CheckerInfo i : Warnings) {
			if (i.file != null && !i.file.equals(file)) {
				System.out.println("Warnings in file " + i.file);
				file = i.file;
			}
			System.out.println("\t" + i.toString());
		}
	}

	public void printNotices() {
		String file = "";
		for (CheckerInfo i : Notices) {
			if (i.file != null && !i.file.equals(file)) {
				System.out.println("Notices in file " + i.file);
				file = i.file;
			}
			System.out.println("\t" + i.toString());
		}
	}

	// ---------------------- Structures ----------------------

	public enum InfoType {ERROR, WARNING, NOTICE}

	;

	public class CheckerInfo implements Comparable<CheckerInfo> {
		public InfoType type;
		public String source;
		public String message;
		public EObject element;
		public String file;
		public int startLine;
		public int endLine;

		public CheckerInfo(InfoType type, String source, String message, EObject element) {
			this.type = type;
			this.source = source;
			this.message = message;
			this.element = element;

			/*if (element.eResource().getURI().isFile()) {
				this.file = element.eResource().getURI().deresolve(URI.createFileURI(ctx.getInputDirectory().getAbsolutePath())).toFileString();          	
				final INode node = NodeModelUtils.getNode(element);
				this.startLine = node.getStartLine();
				this.endLine = node.getEndLine(); 
			} else {
				this.file = null;
			}*/
		}

		public String print() {              	                         	
			return element.getClass().getSimpleName().replace("Impl", "") + " \"" + ThingMLElementHelper.getName(element) + "\"";
		}

		public String toString() {
			/*String t;
			switch (type) {
			case NOTICE:
				t = "NOTICE";
				break;
			case WARNING:
				t = "WARNING";
				break;
			case ERROR:
				t = "ERROR";
				break;
			default:
				t = "";
			}*/

			if (file != null)
				return "- [" + source + "] at lines " + startLine + "-" + endLine + ": " + message + " (in " + print() + ")\n";
			else
				return "- [" + source + "] : " + message + " (in " + print() + ")\n";
		}

		@Override
		public int compareTo(CheckerInfo o) {
			if (type != o.type) {
				if (type == InfoType.NOTICE)
					return -1;
				if (type == InfoType.ERROR)
					return 1;
				if (type == InfoType.WARNING)
					return ((o.type == InfoType.ERROR)? -1 : 0);
				return 0;
			} else {
				if (file!=null && o.file!=null) {
					if (file.equals(o.file)) {
						return startLine - o.startLine;
					} else {
						return file.compareTo(o.file);
					}
				} else {
					return 0;
				}
			}
		}
	}
}
