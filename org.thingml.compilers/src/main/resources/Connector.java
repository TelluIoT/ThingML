

import com.FranckBarbier.Java._Composytor.Statechart;
import com.FranckBarbier.Java._Composytor.Statechart_monitor;
import com.FranckBarbier.Java._PauWare.AbstractStatechart;
import com.FranckBarbier.Java._PauWare.AbstractStatechart_monitor;
import com.FranckBarbier.Java._PauWare._Exception.Statechart_exception;
import com.FranckBarbier.Java._PauWare._Exception.Statechart_transition_based_exception;
import com.FranckBarbier.Java._PauWareView.Statechart_context;
import com.FranckBarbier.Java._PauWareView.Statechart_monitor_viewer;

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
        }

    }

    private void init_behavior() throws Statechart_exception {
        _Init = new Statechart("Init");
        _Init.inputState();//intial state
    }

    private void start() throws Statechart_exception {
        _Connector = new Statechart_monitor(_Init, "Connector", true);

        _Connector.fires("dispatch", _Init, _Init);

        _Connector.add_listener(viewer);
        _Connector.initialize_listener();

        if (debug) {
            viewer.show();
        }
    }

    public void dispatch(Object[] toDispatch) {
        if (toDispatch.length == 2) {
            if (toDispatch[0] instanceof String && toDispatch[1] instanceof Object[]) {
                try {
                    _Connector.fires("dispatch", _Init, _Init, true, forward, (String) toDispatch[0], (Object[]) toDispatch[1], AbstractStatechart.Broadcast);
                } catch (Statechart_transition_based_exception ex) {
                    System.err.println("Problem when dispatching");
                }
            }
        }
    }
}
