package org.thingml.generated.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

import org.thingml.generated.messages.*;
import no.sintef.jasm.*;
import no.sintef.jasm.ext.*;

public class UDPJava extends Component {

    private final /*$SERIALIZER$*/ formatter = new /*$SERIALIZER$*/();

    private final int clientID;
    private final int port;

    private SocketAddress remote;
    private DatagramChannel channel;
    private Selector selector;

    private Thread receiver;
    private java.util.concurrent.atomic.AtomicBoolean receiving = new java.util.concurrent.atomic.AtomicBoolean(true);

   	/*$PORTS$*/

    public UDPJava(int clientID, int port, String dest) {
        this.clientID = clientID;
        this.port = port;
        try {
            remote = new InetSocketAddress(dest, port);
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.connect(remote);
            selector = SelectorProvider.provider().openSelector();
            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            receiver = new Thread( new Runnable() {
                @Override
                public void run() {
                    while(UDPJava.this.receiving.get()) {
                        receive();
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException e) {}
                    }
                }
            });
            receiver.start();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void send(final byte[] payload) {
        try {
            selector.select(clientID);
            final Set readyKeys = selector.selectedKeys();
            final Iterator iterator = readyKeys.iterator();
            if (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                if (key.isWritable()) {
                    channel.write(ByteBuffer.wrap(payload));
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void receive() {
        try {
            selector.select(clientID);
            final Set readyKeys = selector.selectedKeys();
            final Iterator iterator = readyKeys.iterator();
            if (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                if (key.isReadable()) {
                    final ByteBuffer buffer = ByteBuffer.allocate(128);
                    channel.read(buffer);
                    buffer.flip();
                    /*$PARSING CODE$*/
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void stop() {
        super.stop();
        receiving.set(false);
        try {
            receiver.join(100);
        } catch (InterruptedException e) {}    }

    @Override
    public void run() {
        while (active.get()) {
            try {
                final Event e = queue.take();//should block if queue is empty, waiting for a message
                final byte[] payload = JavaBinaryHelper.toPrimitive(formatter.format(e));
                if (payload != null) {
                    send(payload);
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }

    @Override
    public Component buildBehavior(String id, Component root) {
        /*$INIT PORTS$*/
        final java.util.List < AtomicState > states = new java.util.ArrayList < AtomicState > ();
        final AtomicState init = new AtomicState("Init");
        states.add(init);
        behavior = new CompositeState("default", states, init, java.util.Collections.EMPTY_LIST);
        return this;
    }
}