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


    public static List<Region> allContainedRegions(CompositeState self) {
        List<Region> result = new ArrayList<Region>();
        result.add(self);
        if (self instanceof CompositeState) {
            for(Region r : ((CompositeState)self).getRegion()) {
                result.addAll(r.allContainedRegions());
            }
        }
        for (State s : self.getSubstate()) {
            if (s instanceof Region && !(s instanceof Session)) {
                result.addAll(((Region)s).allContainedRegions());
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
        result.add(self);
        for (Region r : self.getRegion()){
            if (!(r instanceof Session))
                result.addAll(r.allContainedRegions());
        }
        return result;
    }

    public static List<Session> directSubSessions(CompositeState self) {
        List<Session> result = new ArrayList<Session>();
        for (Region r : self.getRegion()){
            if (r instanceof Session)
                result.add((Session)r);
            result.addAll(r.allContainedSessions());
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
