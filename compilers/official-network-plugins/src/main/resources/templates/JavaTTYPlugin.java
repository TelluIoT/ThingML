package org.thingml.generated.network;

import org.thingml.generated.messages.*;
import no.sintef.jasm.*;
import no.sintef.jasm.ext.*;

import java.io.PrintStream;
import java.util.Scanner;

public class StdIOJava extends Component {
	private final /*$SERIALIZER$*/ formatter = new /*$SERIALIZER$*/();

	final Scanner stdin = new Scanner(System.in);
	final PrintStream stdout = System.out;

	/*$PORTS$*/

	private void parse(final String payload) {
        /*$PARSING CODE$*/
	}

	@Override
	public void run() {
		new Thread(){
			public void run() {
				while (stdin.hasNext()) {
					parse(stdin.next());
				}
			}
		}.start();
		while (active.get()) {
			try {
				final Event e = queue.take();//should block if queue is empty, waiting for a message
				final String payload = formatter.format(e);
				if (payload != null)
					stdout.println(payload);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
	}

	@Override
	public Component buildBehavior(String id, Component root) {
        /*$INIT PORTS$*/
		final AtomicState init = new AtomicState("Init");
		behavior = new CompositeState("default");
		behavior.add(init);
		behavior.initial(init);
		return this;
	}
}

