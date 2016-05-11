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
                result.addAll(ParallelRegionHelper.allContainedSessions((ParallelRegion) r));
            }
        }
        for (State s : self.getSubstate()) {
            if (s instanceof Session) {
                result.addAll(ParallelRegionHelper.allContainedSessions((ParallelRegion)s));
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
                result.addAll(ParallelRegionHelper.allContainedSessions((ParallelRegion)r));
            }
        }
        return result;
    }
}
