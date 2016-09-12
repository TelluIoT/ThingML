/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingml.xtext.helpers;

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

	public static List<State> getSubstate(Region self) {
		if (self instanceof CompositeState) {
			return ((CompositeState)self).getSubstate();
		}
		else if (self instanceof Session) {
			return ((Session)self).getSubstate();
		}
		else if (self instanceof ParallelRegion) {
			return ((ParallelRegion)self).getSubstate();
		}
		throw new Error("ERROR: Incomplete implementation of RegionHelper.getSubstate");
	}
	

    public static List<State> allContainedStates(Region self) {
        final List<State> result = new ArrayList<State>();
        for(Region r : allContainedRegions(self)) {
            if (r instanceof State) {
                result.add((State)r);
            }
            for(State s : getSubstate(r)) {
                if (! (s instanceof Region)) {
                    result.add(s);
                }
            }
        }
        return result;
    }


    public static List<Region> allContainedRegions(Region self) {
        final List<Region> result = new ArrayList<Region>();
        result.add(self);
        if (self instanceof CompositeState) {
            for(Region r : ((CompositeState)self).getRegion()) {
                result.addAll(RegionHelper.allContainedRegions(r));
            }
        }
        for (State s : getSubstate(self)) {
            if (s instanceof Region) {
                result.addAll(RegionHelper.allContainedRegions((Region)s));
            }
        }
        return result;
    }
    
    public static List<Region> allContainedRegionsAndSessions(Region self) {
        final List<Region> result = new ArrayList<Region>();
        result.add(self);
        if (self instanceof CompositeState) {
            for(Region r : ((CompositeState)self).getRegion()) {
                result.addAll(RegionHelper.allContainedRegionsAndSessions(r));
            }
        }
        if (self instanceof Session) {
            for(Region r : ((Session)self).getRegion()) {
                result.addAll(RegionHelper.allContainedRegionsAndSessions(r));
            }
        }
        for (State s : getSubstate(self)) {
            if (s instanceof Region) {
                result.addAll(RegionHelper.allContainedRegionsAndSessions((Region)s));
            }
        }
        return result;
    }
    
    public static List<Session> allContainedSessions(Region self) {
        final List<Session> result = new ArrayList<Session>();
        if (self instanceof CompositeState) {
            for(Region r : ((CompositeState)self).getRegion()) {
                result.addAll(RegionHelper.allContainedSessions(r));
            }
        }
        if (self instanceof Session) {
            result.add((Session)self);
            for(Region r : ((Session)self).getRegion()) {
                result.addAll(RegionHelper.allContainedSessions(r));
            }
        }
        for (State s : getSubstate(self)) {
            if (s instanceof Session) {
                result.addAll(RegionHelper.allContainedSessions((Session)s));
            }
        }
        return result;
    }
    
    public static List<Session> allFirstLevelSessions(Region self) {
        final List<Session> result = new ArrayList<Session>();
        if (self instanceof Session) {
            result.add((Session)self);
            return result;
        }
        if (self instanceof CompositeState) {
            for(Region r : ((CompositeState)self).getRegion()) {
                result.addAll(RegionHelper.allFirstLevelSessions(r));
            }
        }
        for (State s : getSubstate(self)) {
            if (s instanceof Session) {
                result.addAll(RegionHelper.allFirstLevelSessions((Session)s));
            }
        }
        return result;
    }

    public static List<Property> allContainedProperties(Region self) {
        final List<Property> result = new ArrayList<Property>();
        for(State s : allContainedStates(self)) {
            result.addAll(s.getProperties());
        }
        return result;
    }



    public static List<Region> directSubRegions(Region self) {
        final List<Region> result = new ArrayList<Region>();
        result.add(self);
        if (self instanceof CompositeState) {
            for (Region r : ((CompositeState)self).getRegion()){
                result.addAll(RegionHelper.allContainedRegions(r));
            }
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
            result.add(p.getType());
        }
        return result;
    }


}
