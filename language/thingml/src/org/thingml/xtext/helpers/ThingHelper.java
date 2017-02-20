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
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ffl on 10.05.2016.
 */
public class ThingHelper {

    public static boolean hasSession(Thing self) {
        for(CompositeState sm : ThingMLHelpers.allStateMachines(self)) {
            for(State s : StateHelper.allStatesIncludingSessions(sm)) {
                if (s instanceof Session)
                    return true;
            }
        }
        return false;
}

    public static boolean isSingleton(Thing self) {
        return AnnotatedElementHelper.isDefined(self, "singleton", "true");
    }

    public static List<Transition> allTransitionsWithAction(Thing self) {
        //var result = new ArrayList[Handler]()
        final List<Transition> result = new ArrayList<Transition>();
        for(CompositeState sm : self.getBehaviour()) {
            for(State s : StateHelper.allStates(sm)) {
                for(Transition o : s.getOutgoing()) {
                    if (o.getAction() != null) {
                        result.add(o);
                    }
                }
            }
        }
        return result;
    }

    public static List<InternalTransition> allInternalTransitionsWithAction(Thing self) {
        //var result = new ArrayList[Handler]()
        final List<InternalTransition> result = new ArrayList<InternalTransition>();
        for(CompositeState sm : self.getBehaviour()) {
            for(State s : StateHelper.allStates(sm)) {
                for(InternalTransition o : s.getInternal()) {
                    if (o.getAction() != null) {
                        result.add(o);
                    }
                }
            }
        }
        return result;
    }


    public static List<Property> allPropertiesInDepth(Thing self) {
        List<Property> result = ThingMLHelpers.allProperties(self);
        for(CompositeState sm : ThingMLHelpers.allStateMachines(self)) {
            result.addAll(CompositeStateHelper.allContainedProperties(sm));
        }
        return result;
    }


    public static Expression initExpression(Thing self, Property p) {

        if (ThingMLHelpers.allProperties(self).contains(p)) {  // It is a property of the thing

            List<PropertyAssign> assigns = new ArrayList<PropertyAssign>();
            for (PropertyAssign e : self.getAssign()) {
                if (e.getProperty().equals(p))
                    assigns.add(e);
            }

            // If the expression is defined locally return the init expression
            if (self.getProperties().contains(p)) {
                if (assigns.size() > 0)
                    System.out.println("Error: Thing " + self.getName() + " cannot redefine initial value for property " + p.getName());
                return p.getInit();
            }

            if (assigns.size() > 1)
                System.out.println("Error: Thing " + self.getName() + " contains several assignments for property " + p.getName());

            if (assigns.size() == 1) {
                return assigns.get(0).getInit();
            }

            List<Thing> imports = new ArrayList<Thing>();
            for (Thing t : self.getIncludes()) {
                if (ThingMLHelpers.allProperties(t).contains(p)) {
                    imports.add(t);
                }
            }
            //  imports cannot be empty since the property must be defined in a imported thing
            if (imports.size() > 1)
                System.out.println("Warning: Thing " + self.getName() + " gets property " + p.getName() + " from several paths, it should define its initial value");


            return ThingHelper.initExpression(imports.get(0), p);
        } else { // It is a property of a state machine
            return p.getInit();
        }
    }


    public static List<PropertyAssign> initExpressionsForArray(Thing self, Property p) {

        List<PropertyAssign> result = new ArrayList<PropertyAssign>();

        if (ThingMLHelpers.allProperties(self).contains(p)) {  // It is a property of the thing

            // collect assignment in the imported things first:
            for (Thing t : self.getIncludes()) {
                if (ThingMLHelpers.allProperties(t).contains(p))
                    result.addAll(ThingHelper.initExpressionsForArray(t,p));
            }
            // collect assignments in this thing
            List<PropertyAssign> assigns = null;
            for(PropertyAssign pa : self.getAssign()) {
                if (pa.getProperty().equals(p))
                    result.add(pa);
            }
        }
        else { // It is a property of a state machine
            // No way to initialize arrays in state machines (so far)
        }
        return result;
    }

    public static List<Property> allUsedProperties(Thing self) {
        List<Property> result = new ArrayList<>();
        for(Property p : allPropertiesInDepth(self)) {
            for (Action a : ActionHelper.getAllActions(self, VariableAssignment.class)) {
                if (EcoreUtil.equals(p, ((VariableAssignment)a).getProperty())) {
                    boolean isPresent = false;
                    for(Property pr : result) {
                        if (EcoreUtil.equals(p, pr)) {
                            isPresent = true;
                            break;
                        }
                    }
                    if (!isPresent)
                        result.add(p);
                    break;
                }
            }
            for (Expression e : ThingMLHelpers.getAllExpressions(self, PropertyReference.class)) {
                if (EcoreUtil.equals(p, ((PropertyReference)e).getProperty())) {
                    boolean isPresent = false;
                    for(Property pr : result) {
                        if (EcoreUtil.equals(p, pr)) {
                            isPresent = true;
                            break;
                        }
                    }
                    if (!isPresent)
                        result.add(p);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Returns a list of all the types that is used in a thing
     * @param self
     * @return
     */
     public static Set<Type> allUsedTypes(Thing self) { //TODO: Optimise for only Types that are actually used
         List<Type> list = new ArrayList<Type>();
         // Types for all properties (things or state machines)
         for(Property p : ThingHelper.allPropertiesInDepth(self)) {
             list.add(p.getTypeRef().getType());
         }
         // Types for all messages
         for(Message m : ThingMLHelpers.allMessages(self)) {
             for(Parameter p : m.getParameters()) {
                 list.add(p.getTypeRef().getType());
             }
         }
         // Types for all variables
         for (Variable v : ThingMLHelpers.allVariables(self)) {
             list.add(v.getTypeRef().getType());
         }
         // Types for all functions
         for (Function f : ThingMLHelpers.allFunctions(self)) {
             for (Parameter p : f.getParameters()) {
                 list.add(p.getTypeRef().getType());
             }
         }

         // Make sure we only have one of each type in the resulting set
         Set<Type> result = new HashSet<Type>();
         for (Type tl : list) {
             boolean found = false;
             for (Type ts : result) {
                 if (EcoreUtil.equals(tl,ts)) {
                     found = true;
                     break;
                 }
             }
             if (!found) {
                 result.add(tl);
             }
         }
         return result;
 }

}
