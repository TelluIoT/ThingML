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

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ffl on 10.05.2016.
 */
public class RegionHelper {

    //Derived properties

	

    public static List<State> allContainedStates(Region self) {
        return ThingMLHelpers.<State>allContainedElementsOfType(self, State.class);
    }
    
    
   public static List<StateContainer> allContainedRegions(Region self) {
       final List<StateContainer> result = new ArrayList<StateContainer>();
       result.add(self);
       result.addAll(ThingMLHelpers.<StateContainer>allContainedElementsOfType(self, CompositeState.class));
       result.addAll(ThingMLHelpers.<StateContainer>allContainedElementsOfType(self, Region.class));
       return result;
    }
    
    public static List<StateContainer> allContainedRegionsAndSessions(Region self) {
    	List<StateContainer> result = new ArrayList<StateContainer>();
        result.add(self);
        result.addAll(ThingMLHelpers.<StateContainer>allContainedElementsOfType(self, StateContainer.class));
        return result;
    }
    
    public static List<Session> allContainedSessions(Region self) {
    	List<Session> result = new ArrayList<Session>();
        result.addAll(ThingMLHelpers.<Session>allContainedElementsOfType(self, Session.class));
        return result;
    }

    public static List<Property> allContainedProperties(Region self) {
        final List<Property> result = new ArrayList<Property>();
        for(State s : allContainedStates(self)) {
            result.addAll(s.getProperties());
        }
        return result;
    }

    
    public static List<CompositeState> allContainedCompositeStates(Region self) {
        final List<CompositeState> result = new ArrayList<CompositeState>();
        for(State s : allContainedStates(self)) {
            if (s instanceof CompositeState) {
                result.add((CompositeState)s);
            }
        }
        return result;
    }


    public static List<State> allContainedSimpleStates(Region self) {
        final List<State> result = allContainedStates(self);
        result.removeAll(allContainedCompositeStates(self));
        return result;
    }


    public static Set<Type> allUsedTypes(Region self) {
        Set<Type> result = new HashSet<Type>();
        for(Property p : allContainedProperties(self)) {
            result.add(p.getTypeRef().getType());
        }
        return result;
    }


}
