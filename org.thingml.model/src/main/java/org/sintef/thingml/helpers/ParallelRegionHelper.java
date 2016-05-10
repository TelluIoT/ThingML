package org.sintef.thingml.helpers;

import org.sintef.thingml.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ffl on 10.05.2016.
 */
public class ParallelRegionHelper {


    public static List<Session> allContainedSessions(ParallelRegion self) {
        List<Session> result = new ArrayList<Session>();
        if (self instanceof CompositeState) {
            for(Region r : ((CompositeState)self).getRegion()) {
                if (r instanceof Session) {
                    result.add((Session)r);
                }
                result.addAll(r.allContainedSessions());
            }
        }
        for (State s : self.getSubstate()) {
            if (s instanceof Session) {
                result.addAll(((Session)s).allContainedSessions());
            }
        }
        return result;
    }


    public static List<Session> directSubSessions(ParallelRegion self) {
        List<Session> result = new ArrayList<Session>();
        if (self instanceof CompositeState) {
            for (Region r : ((CompositeState)self).getRegion()) {
                if (r instanceof Session)
                    result.add((Session) r);
                result.addAll(r.allContainedSessions());
            }
        }
        return result;
    }
}
