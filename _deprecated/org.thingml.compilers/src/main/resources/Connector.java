package org.thingml.generated;

import com.FranckBarbier.Java._Composytor.Statechart;
import com.FranckBarbier.Java._Composytor.Statechart_monitor;
import com.FranckBarbier.Java._PauWare.AbstractStatechart;
import com.FranckBarbier.Java._PauWare.AbstractStatechart_monitor;
import com.FranckBarbier.Java._PauWare._Exception.Statechart_exception;
import com.FranckBarbier.Java._PauWare._Exception.Statechart_transition_based_exception;
import com.FranckBarbier.Java._PauWareView.Statechart_context;
import com.FranckBarbier.Java._PauWareView.Statechart_monitor_viewer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bmori
 */
public class Connector {

    private boolean debug = false;
    // ORTHOGONAL_LAYOUT_STRATEGY or FORCE_LAYOUT_STRATEGY
    private Statechart_monitor_viewer viewer = new Statechart_monitor_viewer(Statechart_context.ORTHOGONAL_LAYOUT_STRATEGY);
    private Object forward;
    private String name;
    private AbstractStatechart _Init;
    private AbstractStatechart _Dispatch;
    private AbstractStatechart_monitor _Connector;

    public String getName() {
        return name;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
        if (debug) {
            viewer.show();
        } else {
            viewer.hide();
        }
    }

    public Connector(Object forward, String name) {
        this.forward = forward;
        this.name = name;
        try {
            init_behavior();
            start();
        } catch (Exception e) {
            System.err.println("Problem when initializing connector");
            e.printStackTrace();
        }

    }

    private void init_behavior() throws Statechart_exception {
        _Init = new Statechart("Init");
        _Dispatch = new Statechart("Dispatch");
        _Dispatch.entryAction(this, "done", null, AbstractStatechart.Broadcast);
        _Init.inputState();//intial state
    }

    private void start() throws Statechart_exception {
        _Connector = new Statechart_monitor(_Init.xor(_Dispatch), "Connector_" + getName(), true);

        _Connector.fires("dispatch", _Init, _Dispatch);
        _Connector.fires("done", _Dispatch, _Init, true, this, "done", null, AbstractStatechart.Broadcast);

        _Connector.add_listener(viewer);
        _Connector.initialize_listener();

        if (debug) {
            viewer.show();
        }
    }

    public void done() throws Statechart_exception {
        _Connector.run_to_completion("done");
    }

    public void dispatch(Object[] toDispatch) {
        System.out.print("dispatching to ");
        if (toDispatch.length == 2) {
            if (toDispatch[0] instanceof String && toDispatch[1] instanceof Object[]) {
                Class[] argTypes = new Class[((Object[]) toDispatch[1]).length];
                System.out.print(forward + "." + toDispatch[0] + "(");
                int i = 0;
                for (Object o : (Object[]) toDispatch[1]) {
                    if (i > 0) {
                        System.out.print(", ");
                    }
                    System.out.print(o);
                    argTypes[i] = ((Object[]) toDispatch[1])[i].getClass();
                    i++;
                }
                System.out.println(")");
                try {
                    Method m = forward.getClass().getDeclaredMethod((String) toDispatch[0], argTypes);
                    m.invoke(forward, (Object[]) toDispatch[1]);
                } catch (IllegalAccessException ex) {
                    //Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    //Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    //Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    //Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    //Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
