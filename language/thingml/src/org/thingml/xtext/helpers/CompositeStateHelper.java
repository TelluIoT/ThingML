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
package org.thingml.xtext.helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Session;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.Type;

/**
 * Created by ffl on 10.05.2016.
 */
public class CompositeStateHelper {
	
	/**
	 * All state contained by self. This includes CompositeStates, States, FinalStates which are contained
	 * by self in depth. State which are in regions are included but states in Sessions are excluded.
	 * the parameter self is included in  the result set.
	 * @param self
	 * @return all contained states as a set
	 */
    public static Set<State> allContainedStatesExludingSessions(CompositeState self) {
    	final Set<State> result = new HashSet<State>();
		result.add(self);
		List<EObject> toexplore = new ArrayList<EObject>();
		toexplore.addAll(self.eContents());
		while (!toexplore.isEmpty()) {
			EObject current = toexplore.get(0);
			toexplore.remove(0);
			
			// Add if the object is a state
			if (current instanceof State) result.add((State)current);
			
			// Explore contained objects for all except sessions
			if (!(current instanceof Session)) {
				toexplore.addAll(current.eContents());
			}
		}
		return result;
    }
    
    /**
     * All composite states contained by self.
     * CompositeStates which are in regions are included but states in Sessions are excluded.
     * the parameter self is included in  the result set.
     * @param self
     * @return all contained CompositeStates as a set
     */
    public static Set<CompositeState> allContainedCompositeStatesExludingSessions(CompositeState self) {
    	final Set<CompositeState> result = new HashSet<CompositeState>();
		result.add(self);
		List<EObject> toexplore = new ArrayList<EObject>();
		toexplore.addAll(self.eContents());
		while (!toexplore.isEmpty()) {
			EObject current = toexplore.get(0);
			toexplore.remove(0);
			
			// Add if the object is a state
			if (current instanceof CompositeState) result.add((CompositeState)current);
			
			// Explore contained objects for all except sessions
			if (!(current instanceof Session)) {
				toexplore.addAll(current.eContents());
			}
		}
		return result;
    }

    /**
     * All simple states contained by self. This include State and FinalState
     * State which are in regions are included but states in Sessions are excluded.
     * 
     * @param self
     * @return all contained simple states as a set
     */
    public static Set<State> allContainedSimpleStatesExludingSessions(CompositeState self) {
    	final Set<State> result = new HashSet<State>();
		result.add(self);
		List<EObject> toexplore = new ArrayList<EObject>();
		toexplore.addAll(self.eContents());
		while (!toexplore.isEmpty()) {
			EObject current = toexplore.get(0);
			toexplore.remove(0);
			
			// Add if the object is a state
			if ((current instanceof State) && !(current instanceof CompositeState)) result.add((State)current);
			
			// Explore contained objects for all except sessions
			if (!(current instanceof Session)) {
				toexplore.addAll(current.eContents());
			}
		}
		return result;
    }
    

    /**
	 * All state contained by self. This includes CompositeStates, States, FinalStates which are contained
	 * by self in depth. State which are in regions and sessions contained by self are included.
	 * the parameter self is included in  the result set.
	 * @param self
	 * @return all contained states as a set
	 */
    public static Set<State> allContainedStatesIncludingSessions(CompositeState self) {
    	final Set<State> result = new HashSet<State>();
    	result.add(self);
    	result.addAll(ThingMLHelpers.<State>allContainedElementsOfType(self, State.class));
    	return result;
    }
    
    /**
     * All composite states contained by self.
     * CompositeStates which are in regions and sessions contained by self are included.
     * the parameter self is included in  the result set.
     * @param self
     * @return all contained CompositeStates as a set
     */
    public static Set<CompositeState> allContainedCompositeStatesIncludingSessions(CompositeState self) {
    	final Set<CompositeState> result = new HashSet<CompositeState>();
        result.add(self);
    	result.addAll(ThingMLHelpers.<CompositeState>allContainedElementsOfType(self, CompositeState.class));
    	return result;
    }

    /**
     * All simple states contained by self. This include State and FinalState
     * States which are in regions and sessions contained by self are included.
     * 
     * @param self
     * @return all contained simple states as a set
     */
    public static Set<State> allContainedSimpleStatesIncludingSessions(CompositeState self) {
    	final Set<State> result = allContainedStatesIncludingSessions(self);
        result.removeAll(allContainedCompositeStatesIncludingSessions(self));
        return result;
    }
    
    /**
     * Return the regions for this composite state. NOT IN DEPTH.
     * It corresponds to { self U self.getRegion() }
     * @param self
     * @return itself plus self.getRegion()
     */
    public static List<StateContainer> allRegionsFor(CompositeState self) {
    	List<StateContainer> result = new ArrayList<StateContainer>();
        result.add(self);
        result.addAll(self.getRegion());
        return result;
    }

    public static List<StateContainer> allContainedStateContainers(CompositeState self) {
        List<StateContainer> result = new ArrayList<StateContainer>();
        result.add(self);
        result.addAll(ThingMLHelpers.<StateContainer>allContainedElementsOfType(self, StateContainer.class));
        return result;
    }

    
    public static List<Session> allContainedSessions(CompositeState self) {
    	List<Session> result = new ArrayList<Session>();
        result.addAll(ThingMLHelpers.<Session>allContainedElementsOfType(self, Session.class));
        return result;
    }
    
    public static List<Property> allContainedSessionsProperties(CompositeState self) {
        List<Property> result = new ArrayList<Property>();
        for(Session s : allContainedSessions(self)) {
        	final TreeIterator<EObject> content = s.eAllContents();
        	while(content.hasNext()) {
        		final EObject o = content.next();
        		if (o instanceof Property)
        			result.add((Property)o);
        	}
        }
        return result;
    }

    public static List<Property> allContainedProperties(CompositeState self) {
        List<Property> result = new ArrayList<Property>();
        for(State s : allContainedStatesExludingSessions(self)) {
            result.addAll(s.getProperties());
        }
        return result;
    }

    public static Set<Type> allUsedTypes(CompositeState self) {
        Set<Type> result = new HashSet<Type>();
        for(Property p : allContainedProperties(self)) {
            result.add(p.getTypeRef().getType());
        }
        return result;
    }

    public static boolean hasSeveralRegions(CompositeState self) {
        return self.getRegion().size() > 0;
    }

}
