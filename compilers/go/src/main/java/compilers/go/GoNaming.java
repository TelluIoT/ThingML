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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.thingml.compilers.builder.Element;
import org.thingml.compilers.builder.Section;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.util.ThingMLSwitch;

//FIXME: a heavy machinery just to get a convoluted (and somehow useless) test to pass...
@Deprecated
public class GoNaming {
	private static int MAX_RESOLVE_TRIES = 100;
	
	public Element getNameFor(EObject object) {
		Name name = internalGetNameFor(object);
		return name.toElement();
	}
	
	public void resolveAllConflicts() {
		int counter = 0;
		while (globalScope.resolveConflicts()) {
			counter++;
			if (counter > MAX_RESOLVE_TRIES)
				throw new RuntimeException("It doesn't seem possible to resolve all naming conflicts");
		}
	}

	/* --- Names --- */
	private class Name {
		List<Element> elements;
		Element changeable;
		
		private Name(List<Element> elements, Element changeble) {
			this.elements = elements;
			this.changeable = changeble;
		}
		
		public Element toElement() {
			if (elements.size() == 1)
				return elements.get(0);
			
			Section name = Section.Orphan("name");
			for (Element part : elements)
				name.append(part);
			return name;
		}
		
		@Override
		public String toString() {
			String name = "";
			for (Element part : elements)
				name += part.get();
			return name;
		}
	}
	
	private Map<EObject, Name> preparedNames = new HashMap<EObject, Name>();
	
	private Name internalGetNameFor(EObject object) {
		// If we have already generated the list, return it
		if (preparedNames.containsKey(object))
			return preparedNames.get(object);
		
		// If not, generate the preferred one for this object, and store it for later use
		Name name = namingStrategy.doSwitch(object);
		preparedNames.put(object, name);
		
		// Also create the proper scopes for this name, and add this name to the list
		NameScope scope = nameScopingStrategy.doSwitch(object);
		scope.add(name);
		
		return name;
		
	}
	
	private Name singleName(String name) {
		Element own = new Element(name);
		List<Element> list = new LinkedList<Element>();
		list.add(own);
		return new Name(list, own);
	}
	
	private Name appendToParentName(EObject parent, String name) {
		Element own = new Element(name);
		List<Element> list = new LinkedList<Element>();
		list.addAll(internalGetNameFor(parent).elements);
		list.add(own);
		return new Name(list, own);
	}
	
	private Name prependToParentName(EObject parent, String name) {
		Element own = new Element(name);
		List<Element> list = new LinkedList<Element>();
		list.add(own);
		list.addAll(internalGetNameFor(parent).elements);
		return new Name(list, own);
	}
	
	
	/* -- Naming strategy -- */
	private ThingMLSwitch<Name> namingStrategy = new ThingMLSwitch<Name>() {
		
		public Name caseEnumerationLiteral(EnumerationLiteral object) {
			return appendToParentName(object.eContainer(), object.getName());
		};
		
		public Name caseThing(Thing object) {
			String prefix = object.isFragment() ? "Fragment" : "Thing";
			return singleName(prefix+object.getName());
		};
		
		public Name caseFunction(Function object) {
			return singleName(object.getName());
		};
		
		public Name caseType(Type object) {
			if (AnnotatedElementHelper.hasAnnotation(object, "go_type"))
				return singleName(AnnotatedElementHelper.firstAnnotation(object, "go_type"));
			if (object instanceof Enumeration) {
				final Enumeration e = (Enumeration) object;
				if (e.getTypeRef()!=null && e.getTypeRef().getType()!=null) {
					return caseType(e.getTypeRef().getType());
				}
			}
			return singleName("interface{}");
		};
		
		public Name caseTypeRef(TypeRef object) {
			if (object.isIsArray()) return prependToParentName(object.getType(), "[]");
			else return internalGetNameFor(object.getType());
		};
		
		public Name casePort(Port object) {
			return appendToParentName(object.eContainer(), "Port"+object.getName());
		};
		
		public Name caseMessage(Message object) {
			return appendToParentName(object.eContainer(), "Msg"+object.getName());
		};
		
		public Name caseState(State object) {
			return appendToParentName(object.eContainer(), "State"+object.getName());
		};
		
		public Name caseCompositeState(CompositeState object) {
			String name;
			if (object.eContainer() instanceof Thing) name = "StateChart";
			else name = "Composite";
			if (object.getName() != null) name += object.getName();
			return appendToParentName(object.eContainer(), name);
		};
		
		public Name caseRegion(Region object) {
			String name = object.getName();
			if (name == null) name = ""+((CompositeState)object.eContainer()).getRegion().indexOf(object);
			return appendToParentName(object.eContainer(), name);
		};
		
		public Name caseSession(Session object) {
			return appendToParentName(object.eContainer(), "Session"+object.getName());
		};
		
		
		public Name caseReceiveMessage(ReceiveMessage object) {
			// We have already checked that the name is not null
			return singleName(object.getName());
		};
		
		
		public Name caseInstance(Instance object) {
			return singleName(object.getName());
		};
		
		
		
		
		public Name caseProperty(Property object) {
			return singleName(object.getName());
		};
		
		public Name caseLocalVariable(LocalVariable object) {
			return singleName(object.getName());
		};
		
		public Name caseParameter(Parameter object) {
			if (object.eContainer() instanceof Message) {
				if (object.getName().length()>1)
					return singleName(object.getName().substring(0, 1).toUpperCase() + object.getName().substring(1));
				else
					return singleName(object.getName().substring(0, 1).toUpperCase());
			}
			return singleName(object.getName());
		};
		
		
		public Name defaultCase(EObject object) {
			throw new UnsupportedOperationException("No naming strategy implemented for "+object.eClass().getName());
		};
	};
	
	
	
	/* --- Name scope ---*/
	private class NameScope {
		NameScope parent;
		List<NameScope> related = new LinkedList<NameScope>();
		List<NameScope> children = new LinkedList<NameScope>();
		List<Name> names = new LinkedList<Name>();
		
		private NameScope(NameScope parent) {
			if (parent != null) {
				this.parent = parent;
				this.parent.children.add(this);
			}
		}
		
		NameScope add(Name name) {
			this.names.add(name);
			return this;
		}
		
		NameScope relatesTo(NameScope other) {
			this.related.add(other);
			return this;
		}
		
		NameScope relatesToAll(NameScope other) {
			this.related.add(other);
			this.related.addAll(other.related);
			return this;
		}
		
		boolean hasCollision(Name name) {
			// Check against names in own scope
			for (Name other : this.names) {
				if (name == other)
					continue;
				if (other.toString().equals(name.toString()))
					return true;
			}
			// Then check with the related scopes
			for (NameScope others : this.related)
				for (Name other : others.names)
					if (other.toString().equals(name.toString()))
						return true;
			
			return false;
		}
		
		boolean resolveConflicts() {
			boolean didchange = false;
			// Resolve own name conflicts
			for (Name name : this.names) {
				String originalName = name.changeable.get();
				int suffix = 1;
				while (this.hasCollision(name)) {
					name.changeable.set(originalName+"_"+suffix);
					suffix++;
					didchange = true;
				}
			}
			// Resolve children name conflicts
			for (NameScope child : this.children)
				didchange |= child.resolveConflicts();
			
			return didchange;
		}
	}
	
	private NameScope globalScope = new NameScope(null);
	private NameScope dummyScope = new NameScope(null);
	
	/* --- Scoping strategy --- */
	private ThingMLSwitch<NameScope> nameScopingStrategy = new ThingMLSwitch<NameScope>() {
		
		public NameScope caseEnumeration(Enumeration object) {
			return globalScope;
		};
		
		public NameScope caseEnumerationLiteral(EnumerationLiteral object) {
			return globalScope;
		};
		
		public NameScope caseThing(Thing object) {
			return globalScope;
		};
		
		public NameScope caseMessage(Message object) {
			return globalScope;
		};
		
		public NameScope casePort(Port object) {
			return globalScope;
		};
		
		public NameScope caseProperty(Property object) {
			return internalGetScopeFor(object.eContainer());
		};
		
		public NameScope caseState(State object) {
			return globalScope;
		};
		
		public NameScope caseStateContainer(StateContainer object) {
			return globalScope;
		};
		
		public NameScope caseFunction(Function object) {
			return internalGetScopeFor(object.eContainer());
		};
		
		public NameScope caseParameter(Parameter object) {
			return internalGetScopeFor(object.eContainer());
		};
		
		public NameScope caseType(Type object) {
			return dummyScope;
		};
		
		public NameScope caseTypeRef(TypeRef object) {
			return dummyScope;
		};
		
		public NameScope caseLocalVariable(LocalVariable object) {
			return internalGetScopeFor(getActionContainer(object));
		};
		
		public NameScope caseInstance(Instance object) {
			return internalGetScopeFor(object.eContainer());
		};
		
		public NameScope caseReceiveMessage(ReceiveMessage object) {
			return internalGetScopeFor(object.eContainer());
		};
		
		
		public NameScope defaultCase(EObject object) {
			throw new UnsupportedOperationException("No name-scoping strategy implemented for "+object.eClass().getName());
		};
	};
	
	
	private Map<EObject, NameScope> nestedScopes = new HashMap<EObject, NameScope>();
	private ThingMLSwitch<NameScope> nestedScopingStrategy = new ThingMLSwitch<NameScope>() {
		
		public NameScope caseThing(Thing object) {
			NameScope scope = new NameScope(globalScope);
			for (Thing inc : object.getIncludes())
				scope.relatesToAll(internalGetScopeFor(inc));
			return scope;
		};
		
		public NameScope caseFunction(Function object) {
			NameScope thingScope = internalGetScopeFor(object.eContainer());
			return new NameScope(thingScope).relatesTo(thingScope);
			
		};
		
		public NameScope caseMessage(Message object) {
			NameScope thingScope = internalGetScopeFor(ThingMLHelpers.findContainingThing(object));
			return new NameScope(thingScope);
		};
		
		public NameScope caseState(State object) {
			NameScope parentScope = internalGetScopeFor(object.eContainer());
			return new NameScope(parentScope).relatesToAll(parentScope);
		};
		
		public NameScope caseStateContainer(StateContainer object) {
			NameScope parentScope = internalGetScopeFor(object.eContainer());
			return new NameScope(parentScope).relatesToAll(parentScope);
		};
		
		public NameScope caseConfiguration(Configuration object) {
			return new NameScope(globalScope);
		};
		
		public NameScope caseReceiveMessage(ReceiveMessage object) {
			return internalGetScopeFor(object.eContainer());
		};
		
		public NameScope caseAction(Action object) {
			return internalGetScopeFor(getActionContainer(object));
		};
		
		public NameScope caseHandler(Handler object) {
			NameScope parentScope = internalGetScopeFor(object.eContainer());
			return new NameScope(parentScope).relatesToAll(parentScope);
		};
		
		
		public NameScope defaultCase(EObject object) {
			throw new UnsupportedOperationException("No nested scoping strategy implemented for "+object.eClass().getName());
		};
	};
	
	private NameScope internalGetScopeFor(EObject object) {
		if (nestedScopes.containsKey(object))
			return nestedScopes.get(object);
		
		NameScope created = nestedScopingStrategy.doSwitch(object);
		nestedScopes.put(object, created);
		return created;
	}
	
	private EObject getActionContainer(EObject object) {
		EObject outermostAction = null;
		while (object != null) {
			if (object instanceof Action) outermostAction = object;
			object = object.eContainer();
		}
		return outermostAction.eContainer();
	}
}
