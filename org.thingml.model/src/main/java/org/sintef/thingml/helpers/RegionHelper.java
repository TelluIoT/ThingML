package org.sintef.thingml.helpers;

import org.sintef.thingml.*;

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
        final List<State> result = new ArrayList<State>();
        for(Region r : allContainedRegions(self)) {
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


    public static List<Region> allContainedRegions(Region self) {
        final List<Region> result = new ArrayList<Region>();
        result.add(self);
        if (self instanceof CompositeState) {
            for(Region r : ((CompositeState)self).getRegion()) {
                result.addAll(r.allContainedRegions());
            }
        }
        for (State s : self.getSubstate()) {
            if (s instanceof Region) {
                result.addAll(((Region)s).allContainedRegions());
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
                result.addAll(r.allContainedRegions());
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
