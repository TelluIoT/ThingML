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
package org.sintef.thingml.helpers;

import org.sintef.thingml.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ffl on 10.05.2016.
 */
public class CompositeStateHelper {

    public static List<State> allContainedStates(CompositeState self) {
        final List<State> result = new ArrayList<State>();
        for(Region r : allContainedRegions(self)) {
            if (r instanceof State && !(r instanceof Session)) {
                result.add((State)r);
            }
            for(State s : r.getSubstate()) {
                if (! (s instanceof Region)) {
                    result.add(s);
                }
            }
        }
        return result;
    }

    public static List<State> allContainedStatesIncludingSessions(CompositeState self) {
        final List<State> result = new ArrayList<State>();
        for(Region r : allContainedRegionsAndSessions(self)) {
            if (r instanceof State) {
                result.add((State)r);
            }
            for(State s : r.getSubstate()) {
                if (! (s instanceof Region)) {
                    result.add(s);
                }
            }
        }
        return result;
    }


    public static List<Region> allContainedRegions(CompositeState self) {
        List<Region> result = new ArrayList<Region>();
        result.add(self);
        if (self instanceof CompositeState) {
            for(Region r : ((CompositeState)self).getRegion()) {
                result.addAll(RegionHelper.allContainedRegions(r));
            }
        }
        for (State s : self.getSubstate()) {
            if (s instanceof Region && !(s instanceof Session)) {
                result.addAll(RegionHelper.allContainedRegions((Region)s));
            }
        }
        return result;
    }


    public static List<Region> allContainedRegionsAndSessions(CompositeState self) {
        List<Region> result = new ArrayList<Region>();
        result.add(self);
        if (self instanceof CompositeState) {
            for(Region r : ((CompositeState)self).getRegion()) {
                result.addAll(RegionHelper.allContainedRegionsAndSessions(r));
            }
        }
        for (State s : self.getSubstate()) {
            if (s instanceof Region) {
                result.addAll(RegionHelper.allContainedRegionsAndSessions((Region)s));
            }
        }
        return result;
    }


    public static List<Session> allContainedSessions(CompositeState self) {
        List<Session> result = new ArrayList<Session>();
        for (State s :self.getSubstate()) {
            if (s instanceof Session) {
                result.add(((Session)s));
            }
            if(s instanceof CompositeState)
                result.addAll(allContainedSessions((CompositeState)s));
        }
        for(Region r: self.getRegion()) {
            result.addAll(RegionHelper.allContainedSessions(r));
        }
        return result;
    }


    public static List<Session> allFirstLevelSessions(CompositeState self) {
        List<Session> result = new ArrayList<Session>();
        for (State s :self.getSubstate()) {
            if (s instanceof Session) {
                result.add(((Session)s));
            } else if(s instanceof CompositeState)
                result.addAll(allFirstLevelSessions((CompositeState)s));
        }
        for(Region r: self.getRegion()) {
            if (r instanceof Session)
                result.add(((Session)r));
            else
                result.addAll(RegionHelper.allFirstLevelSessions(r));
        }
        return result;
    }



    public static List<Property> allContainedProperties(CompositeState self) {
        List<Property> result = new ArrayList<Property>();
        for(State s : allContainedStates(self)) {
            result.addAll(s.getProperties());
        }
        return result;
    }


    public static List<Region> directSubRegions(CompositeState self) {
        List<Region> result = new ArrayList<Region>();
        if (!(self instanceof Session))
            result.add(self);
        for (Region r : self.getRegion()){
            //if (!(r instanceof Session))
            //    result.addAll(RegionHelper.allContainedRegions(r));
            if (!(r instanceof Session))
                result.add(r);
        }
        return result;
    }

    public static List<Session> directSubSessions(CompositeState self) {
        List<Session> result = new ArrayList<Session>();
        for (Region r : self.getRegion()){
            if (r instanceof Session)
                result.add((Session)r);
            //result.addAll(RegionHelper.allContainedSessions((ParallelRegion)r));
        }
        return result;
    }


    public static List<CompositeState> allContainedCompositeStates(CompositeState self) {
        List<CompositeState> result = new ArrayList<CompositeState>();
        for(State s : allContainedStates(self)) {
            if (s instanceof CompositeState && !(s instanceof Session)) {
                result.add((CompositeState)s);
            }
        }
        return result;
    }


    public static List<State> allContainedSimpleStates(CompositeState self) {
        final List<State> result = allContainedStates(self);
        result.removeAll(allContainedCompositeStates(self));
        return result;
    }


    public static List<CompositeState> allContainedCompositeStatesIncludingSessions(CompositeState self) {
        List<CompositeState> result = new ArrayList<CompositeState>();
        for(State s : allContainedStatesIncludingSessions(self)) {
            if (s instanceof CompositeState) {
                result.add((CompositeState)s);
            }
        }
        return result;
    }


    public static List<State> allContainedSimpleStatesIncludingSessions(CompositeState self) {
        final List<State> result = allContainedStatesIncludingSessions(self);
        result.removeAll(allContainedCompositeStatesIncludingSessions(self));
        return result;
    }


    public static Set<Type> allUsedTypes(CompositeState self) {
        Set<Type> result = new HashSet<Type>();
        for(Property p : allContainedProperties(self)) {
            result.add(p.getType());
        }
        return result;
    }

    public static boolean hasSeveralRegions(CompositeState self) {
        return self.getRegion().size() > 0;
    }

}
