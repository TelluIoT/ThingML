package org.thingml.generated.network;

import org.thingml.generated.messages.*;
import org.thingml.java.*;
import org.thingml.java.ext.*;

import java.io.PrintStream;
import java.util.Scanner;

public class StdIOJava extends Component {
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
		while (active) {
			try {
				final Event e = queue.take();//should block if queue is empty, waiting for a message
				final String payload = /*$SERIALIZER$*/.toString(e);
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
		return this;
	}
}

