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
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.Thing;

/**
 * Created by ffl on 03.05.2016.
 */
public class ActionHelper {


    public static <T extends Action> List<T> getAllActions(EObject self, Class<T> clazz) {
        List<T> result = new ArrayList<T>();

        TreeIterator<EObject> it = self.eAllContents();
        while(it.hasNext()) {
            EObject o = it.next();
            if (clazz.isInstance(o)) result.add((T) o);
        }
        
        if (self instanceof Thing) {//We need this to get all the actions defined in included Things, as they are not part of self.eAllContents. This should be the only case where we need a hack...
        	Thing t = (Thing) self;
        	for(Thing i : ThingHelper.allIncludedThings(t)) {
        		result.addAll(getAllActions(i, clazz));
        	}
        } else {
        	if (clazz.isInstance(self)) result.add((T)self);
        }
        
        return result;
    }


    public static List<Action> getAllActions(EObject self) {
       return getAllActions(self, Action.class);
    }

}
