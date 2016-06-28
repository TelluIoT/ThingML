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
package org.thingml.compilers.checker.genericRules;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;
import org.thingml.compilers.checker.Checker;
import org.thingml.compilers.checker.Rule;

/**
 * Created by Alexandre RIO on 6/13/16.
 */
public class MultipleWindowStreams extends Rule {
    @Override
    public Checker.InfoType getHighestLevel() {
        return Checker.InfoType.ERROR;
    }

    @Override
    public String getName() {
        return "Multiple window on streams";
    }

    @Override
    public String getDescription() {
        return "Check that no streams declares multiple window";
    }

    @Override
    public void check(Configuration cfg, Checker checker) {
        for (Thing t : ThingMLHelpers.allThings(ThingMLHelpers.findContainingModel(cfg))) {
            for (Stream s : t.getStreams()) {
                boolean streamHasLength = false;
                boolean streamHasTime = false;
                for (ViewSource vs : s.getInput().getOperators()) {
                    if (vs instanceof LengthWindow) {
                        if (!streamHasLength && !streamHasTime)
                            streamHasLength = true;
                        else
                            checker.addError("Multiple definition of LenghtWindow (::buffer)", s);
                    } else if (vs instanceof TimeWindow) {
                        if (!streamHasTime && !streamHasLength)
                            streamHasTime = true;
                        else
                            checker.addError("Multiple definition of TimeWindow (::during)", s);

                    }
                }
            }
        }
    }
}
