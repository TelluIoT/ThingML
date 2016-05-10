package org.sintef.thingml.helpers;

import org.sintef.thingml.*;
import org.sintef.thingml.constraints.ThingMLHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ffl on 10.05.2016.
 */
public class ThingHelper {


    public static boolean isSingleton(Thing self) {
        return AnnotatedElementHelper.isDefined(self, "singleton", "true");
    }

    public static List<Transition> allTransitionsWithAction(Thing self) {
        //var result = new ArrayList[Handler]()
        final List<Transition> result = new ArrayList<Transition>();
        for(StateMachine sm : self.getBehaviour()) {
            for(State s : sm.allStates()) {
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
        for(StateMachine sm : self.getBehaviour()) {
            for(State s : sm.allStates()) {
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
        for(StateMachine sm : ThingMLHelpers.allStateMachines(self)) {
            result.addAll(sm.allContainedProperties());
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
                if (t.allProperties().contains(p)) {
                    imports.add(t);
                }
            }
            //  imports cannot be empty since the property must be defined in a imported thing
            if (imports.size() > 1)
                System.out.println("Warning: Thing " + self.getName() + " gets property " + p.getName() + " from several paths, it should define its initial value");

            return imports.get(0).initExpression(p);
        } else { // It is a property of a state machine
            return p.getInit();
        }
    }


    public static List<PropertyAssign> initExpressionsForArray(Thing self, Property p) {

        List<PropertyAssign> result = new ArrayList<PropertyAssign>();

        if (ThingMLHelpers.allProperties(self).contains(p)) {  // It is a property of the thing

            // collect assignment in the imported things first:
            for (Thing t : self.getIncludes()) {
                if (t.allProperties().contains(p))
                    result.addAll(t.initExpressionsForArray(p));
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



}
