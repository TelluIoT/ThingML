import java.util.Scanner;

public class StdIOJava extends Component {
	final Scanner stdin = new Scanner(System.in);
	final OutputStream stdout = System.out;

	public StdIOJava() {
		new Thread({
			public void run() {
				while (stdin.hasNext()) {
					parse(stdin.next());
				}
			}
		}).start();
	}

	private void parse(final String payload) {
        /*$PARSING CODE$*/
	}

	@Override
	public void run() {
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

