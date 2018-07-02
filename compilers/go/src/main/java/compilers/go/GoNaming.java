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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.thingml.compilers.builder.Element;
import org.thingml.compilers.builder.Section;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.util.ThingMLSwitch;

public class GoNaming {
	
	public Element getNameFor(EObject object) {
		List<Element> nameElements = internalGetNameFor(object);
		
		// If the list consist of only a single element, use that
		if (nameElements.size() == 1)
			return nameElements.get(0);
		
		// If not, make a section that concatenates all of them (sections cannot be shared across the builder tree)
		Section nameSection = Section.Orphan("name");
		for (Element name : nameElements)
			nameSection.append(name);
		return nameSection;
	}
	
	public void printAllUsedNames() {
		for (Entry<EObject, List<Element>> entry : preparedNames.entrySet()) {
			System.out.print(entry.getKey().eClass().getName());
			
			for (Method m : entry.getKey().getClass().getMethods()) {
				if (m.getName().equals("getName") && m.getParameterCount() == 0) {
					try {
						Object name = m.invoke(entry.getKey());
						if (name instanceof String) {
							System.out.print(" ["+name+"]");
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {}
					
				}
			}
			
			System.out.print(" -> ");
			for (Element element : entry.getValue()) {
				System.out.print(element.get());
			}
			System.out.println();
		}
	}

	
	
	
	
	private Map<EObject, List<Element>> preparedNames = new HashMap<EObject, List<Element>>();
	
	private List<Element> internalGetNameFor(EObject object) {
		// If we have already generated the list, return it
		if (preparedNames.containsKey(object))
			return preparedNames.get(object);
		
		// If not, generate the preferred one for this object, and store it for later use
		List<Element> names = namingStrategy.doSwitch(object);
		preparedNames.put(object, names);
		return names;
		
	}
	
	
	private List<Element> singleList(String name) {
		List<Element> list = new LinkedList<Element>();
		list.add(new Element(name));
		return list;
	}
	
	private List<Element> appendToParentList(EObject parent, String name) {
		List<Element> list = new LinkedList<Element>();
		list.addAll(internalGetNameFor(parent));
		list.add(new Element(name));
		return list;
	}
	
	private List<Element> prependToParentList(EObject parent, String name) {
		List<Element> list = new LinkedList<Element>();
		list.add(new Element(name));
		list.addAll(internalGetNameFor(parent));
		return list;
	}
	
	private ThingMLSwitch<List<Element>> namingStrategy = new ThingMLSwitch<List<Element>>() {
		
		public List<Element> caseEnumeration(Enumeration object) {
			return singleList("Enum"+object.getName());
		};
		
		public List<Element> caseEnumerationLiteral(EnumerationLiteral object) {
			return appendToParentList(object.eContainer(), object.getName());
		};
		
		public List<Element> caseThing(Thing object) {
			String prefix = object.isFragment() ? "Fragment" : "Thing";
			return singleList(prefix+object.getName());
		};
		
		public List<Element> caseFunction(Function object) {
			return singleList(object.getName());
		};
		
		public List<Element> caseType(Type object) {
			if (AnnotatedElementHelper.hasAnnotation(object, "go_type"))
				return singleList(AnnotatedElementHelper.firstAnnotation(object, "go_type"));
			else
				return singleList("interface{}");
		};
		
		public List<Element> caseTypeRef(TypeRef object) {
			if (object.isIsArray()) return prependToParentList(object.getType(), "[]");
			else return internalGetNameFor(object.getType());
		};
		
		public List<Element> casePort(Port object) {
			return appendToParentList(object.eContainer(), "Port"+object.getName());
		};
		
		public List<Element> caseMessage(Message object) {
			return appendToParentList(object.eContainer(), "Msg"+object.getName());
		};
		
		public List<Element> caseState(State object) {
			return appendToParentList(object.eContainer(), "State"+object.getName());
		};
		
		public List<Element> caseCompositeState(CompositeState object) {
			String name;
			if (object.eContainer() instanceof Thing) name = "StateChart";
			else name = "Composite";
			if (object.getName() != null) name += object.getName();
			return appendToParentList(object.eContainer(), name);
		};
		
		public List<Element> caseRegion(Region object) {
			String name = object.getName();
			if (name == null) name = ""+((CompositeState)object.eContainer()).getRegion().indexOf(object);
			return appendToParentList(object.eContainer(), name);
		};
		
		public List<Element> caseSession(Session object) {
			return appendToParentList(object.eContainer(), "Session"+object.getName());
		};
		
		
		public List<Element> caseReceiveMessage(ReceiveMessage object) {
			// We have already checked that the name is not null
			return singleList(object.getName());
		};
		
		
		public java.util.List<Element> caseInstance(org.thingml.xtext.thingML.Instance object) {
			return singleList(object.getName());
		};
		
		
		
		
		public List<Element> caseProperty(Property object) {
			return singleList(object.getName());
		};
		
		public List<Element> caseLocalVariable(LocalVariable object) {
			return singleList(object.getName());
		};
		
		public List<Element> caseParameter(Parameter object) {
			return singleList(object.getName());
		};
		
		
		public List<Element> defaultCase(EObject object) {
			throw new UnsupportedOperationException("No naming strategy implemented for "+object.eClass().getName());
		};
	};
	
	
}
