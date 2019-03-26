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
package org.thingml.compilers.uml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.thingml.compilers.Context;
import org.thingml.compilers.thing.common.FSMBasedThingImplCompiler;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.FinalState;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.NamedElement;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.Region;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Transition;

/**
 * Created by bmori on 16.04.2015.
 */
public class PlantUMLThingImplCompiler extends FSMBasedThingImplCompiler {

	public boolean FACTORIZE_TRANSITIONS = true;
	public boolean COMPACT = true;

	private static Map<String, List<NamedElement>> names = new HashMap<String, List<NamedElement>>();

	private String getName(NamedElement s) {
		final String name = (s.getName()!=null)?s.getName():"default";
		final int index = names.get(name).indexOf(s);
		if (index == 0)
			return name;
		return name + "_" + index;
	}

	private void doBuildAction(Action a, StringBuilder builder, Context ctx) {
		if (!COMPACT)
			ctx.getCompiler().getThingActionCompiler().generate(a, builder, ctx);
		else
			builder.append("...\\n");
	}

	private void buildActions(State s, StringBuilder builder, Context ctx) {
		final String name = getName(s);
		if (s.getEntry() != null) {//TODO: pretty-print ThingML actions and expressions
			builder.append("\t" + name + " : entry / ");
			doBuildAction(s.getEntry(), builder, ctx);
			builder.append("\n");
		}
		if (s.getExit() != null) {
			builder.append("\t" + name + " : exit / ");
			doBuildAction(s.getExit(), builder, ctx);
			builder.append("\n");
		}
	}

	private void doGenerateHandlers(Handler h, StringBuilder builder, Context ctx) {
		if (h.getEvent() == null) {
			generateHandler(h, null, null, builder, ctx);
		} else {
			ReceiveMessage rm = (ReceiveMessage)h.getEvent();
			generateHandler(h, rm.getMessage(), rm.getPort(), builder, ctx);
		}
	}

	private void generateHandlers(State c, StringBuilder builder, Context ctx) {

		for (Handler h : c.getOutgoing()) {
			doGenerateHandlers(h, builder, ctx);
		}
		for (Handler h : c.getInternal()) {
			doGenerateHandlers(h, builder, ctx);
		}
	}

	protected void generateStateMachine(CompositeState sm, StringBuilder builder, Context ctx) {
		List<NamedElement> l = new ArrayList<>();
		l.add(sm);
		names.put((sm.getName()!=null)?sm.getName():"default", l);
		TreeIterator<EObject> content = sm.eAllContents();
		while(content.hasNext()) {
			final EObject c = content.next();
			if (c instanceof NamedElement) {//FIXME: we might refine this condition to target states, regions, composites, sessions
				final NamedElement e = (NamedElement)c;
				final String name = (e.getName()!=null)?e.getName():"default";
				if (names.get(name) == null) {
					List<NamedElement> elems = new ArrayList<NamedElement>();
					names.put(name, elems);
				}
				List<NamedElement> elems = names.get(name);
				if (!elems.contains(e)) {
					elems.add(e);
				}
			}
		}

		final String name = getName(sm);
		builder.append("@startuml\n");
		builder.append("skinparam defaultTextAlignment left\n");
		builder.append("caption Behavior of thing " + ThingMLHelpers.findContainingThing(sm).getName() + "\n");
		builder.append("[*] --> " + name + "\n");
		builder.append("state " + name + "{\n");
		for (State s : sm.getSubstate()) {
			if (!(s instanceof Session))
				generateState(s, builder, ctx);
		}
		builder.append("[*] --> " + getName(sm.getInitial()) + "\n");
		buildActions(sm, builder, ctx);

		generateHandlers(sm, builder, ctx);

		for (Region r : sm.getRegion()) {
			generateRegion(r, builder, ctx);
		}

		for(Session s : sm.getSession()) {
			generateRegion(s, builder, ctx);
		}

		builder.append("}\n");
		builder.append("@enduml\n");
	}

	protected void generateCompositeState(StateContainer c, StringBuilder builder, Context ctx) {
		final String name = getName(c);
		builder.append("state " + name + "{\n");
		for (State s : c.getSubstate()) {
			if (!(s instanceof Session))
				generateState(s, builder, ctx);
		}
		builder.append("[*] --> " + getName(c.getInitial()) + "\n");

		if (c instanceof CompositeState) {
			generateHandlers((CompositeState)c, builder, ctx);

			for (Region r : ((CompositeState)c).getRegion()) {
				generateRegion(r, builder, ctx);
			}

			for(Session s : ((CompositeState)c).getSession()) {
				generateRegion(s, builder, ctx);
			}

			buildActions((CompositeState)c, builder, ctx);
		}
		builder.append("}\n");
	}

	protected void generateAtomicState(State s, StringBuilder builder, Context ctx) {
		final String name = getName(s);
		builder.append("state " + name + "{\n");
		buildActions(s, builder, ctx);
		generateHandlers(s, builder, ctx);
		builder.append("}\n");
	}

	protected void generateFinalState(FinalState s, StringBuilder builder, Context ctx) {
		generateAtomicState(s, builder, ctx);
		builder.append(getName(s) + " --> [*]\n");
	}

	public void generateRegion(StateContainer r, StringBuilder builder, Context ctx) {
		builder.append("--\n");
		builder.append("[*] --> " + getName(r.getInitial()) + "\n");
		for (State s : r.getSubstate()) {
			generateState(s, builder, ctx);
		}
		if (r instanceof Session) {
			builder.append("Note top of " + getName(r.getInitial()) + " : Session " + getName(r) + "\n");
		} else if (r instanceof Region) {
			builder.append("Note top of " + getName(r.getInitial()) + " : Region " + getName(r) + "\n");
		} else {
	        throw new UnsupportedOperationException(r.getClass() + ".generateRegion(...) invalid");
		}
	}

	private void generateGuardAndActions(Handler t, StringBuilder builder, Context ctx) {
		if (t.getGuard() != null) {
			builder.append("[");
			ctx.getCompiler().getThingActionCompiler().generate(t.getGuard(), builder, ctx);
			builder.append("]");
		}
		if (t.getAction() != null) {
			if (t.getEvent() == null && t.getGuard() == null)
				builder.append("action ");
			else
				builder.append("\\naction ");
			if (!COMPACT)
				ctx.getCompiler().getThingActionCompiler().generate(t.getAction(), builder, ctx);
			else
				builder.append("...\\n");
		}
	}

	protected void generateTransition(Transition t, Message msg, Port p, StringBuilder builder, Context ctx) {
		String content = builder.toString();
		String transition = getName(((State)t.eContainer())) + " --> " + getName(t.getTarget());
		if ((msg != null && p != null) || t.getAction() != null || t.getGuard() != null) {
			transition = transition + " : ";
		}
		if (FACTORIZE_TRANSITIONS && content.contains(transition)) {//FIXME: not the most elegant way to manage this factorization...
			StringBuilder temp = new StringBuilder();
			temp.append(transition);
			if (msg != null && p != null) {
				if(t.getEvent() != null && ((ReceiveMessage)t.getEvent()).getName()!= null)
					temp.append(((ReceiveMessage)t.getEvent()).getName() + ":");
				temp.append(p.getName() + "?" + msg.getName());
			}
			generateGuardAndActions(t, temp, ctx);
			content = content.replace(transition, "\n" + temp.toString() + "\\n||");
			builder.delete(0, builder.length());
			builder.append(content);
		} else {
			builder.append("\n" + getName(((State)t.eContainer())) + " --> " + getName(t.getTarget()));
			if (t.getName()!=null || (msg != null && p != null) || t.getAction() != null || t.getGuard() != null) {
				builder.append(" : ");
			}
			if (t.getName()!=null) {
				builder.append(t.getName());
			}
			if (msg != null && p != null) {
				if (t.getName()!=null)
					builder.append("\\n");
				if(t.getEvent() != null && ((ReceiveMessage)t.getEvent()).getName()!= null)
					builder.append(((ReceiveMessage)t.getEvent()).getName() + ":");
				builder.append(p.getName() + "?" + msg.getName());
			}
			generateGuardAndActions(t, builder, ctx);
			builder.append("\n");
		}
	}

	protected void generateInternalTransition(InternalTransition t, Message msg, Port p, StringBuilder builder, Context ctx) {
		builder.append("\t" + getName(ThingMLHelpers.findContainingState(t)) + " : ");
		if(t.getEvent() != null && ((ReceiveMessage)t.getEvent()).getName()!= null)
			builder.append(((ReceiveMessage)t.getEvent()).getName() + ":");
		if(p != null && msg != null)
			builder.append(p.getName() + "?" + msg.getName() + " / ");
		generateGuardAndActions(t, builder, ctx);
		builder.append("\n");
	}
}
