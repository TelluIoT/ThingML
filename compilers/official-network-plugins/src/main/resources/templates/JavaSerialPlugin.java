package org.thingml.generated.network;

import org.thingml.java.*;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.Arrays;

public class SerialJava extends Component {

    private final String port;
    private final long baudrate;

    private final SerialPort serialPort;
    private final byte start = 0x12;
    private final byte stop = 0x13;
    private final byte escape = 0x7D;

    /*$MESSAGE TYPES$*/

    /*$PORTS$*/

    public SerialJava(final String port, final long baudrate) {
        this.port = port;
        this.baudrate = baudrate;
        serialPort = new SerialPort(port);
        try {
            serialPort.openPort();
            serialPort.setParams(baudrate, 8, 1, 0);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
            serialPort.addEventListener(new SerialPortReader());
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    private void send(final byte[] payload) {
        serialPort.writeByte(start);
        for(byte b : payload) {
            if (b == start || b == stop || b == escape) {
                serialPort.writeByte(escape);
            }
            serialPort.writeByte(b);
        }
        serialPort.writeByte(stop);
    }

    private void parse(final byte[] payload) {
        /*$PARSING CODE$*/
    }

    @Override
    public void run() {
        while(active) {
            try {
                final Event e = queue.take();//should block if queue is empty, waiting for a message
                send(e.toBytes());
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }

    class SerialPortReader implements SerialPortEventListener {

        public static final int RCV_WAIT = 0;
        public static final int RCV_MSG = 1;
        public static final int RCV_ESC = 2;
        private byte[] buffer = new byte[256];
        protected int buffer_idx = 0;
        protected int state = RCV_WAIT;

        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0) {
                byte[] received = serialPort.readBytes();
                for(byte data : received) {
                    if (state == RCV_WAIT) { // it should be a start byte or we just ignore it
                        if (data == START_BYTE) {
                            state = RCV_MSG;
                            buffer_idx = 0;
                        }
                    } else if (state == RCV_MSG) {
                        if (data == ESCAPE_BYTE) {
                            state = RCV_ESC;
                        } else if (data == STOP_BYTE) {
                            parse(Arrays.copyOf(buffer), buffer_idx);
                            state = RCV_WAIT;
                        } else if (data == START_BYTE) {
                            // Should not happen but we reset just in case
                            state = RCV_MSG;
                            buffer_idx = 0;
                        } else { // it is just a byte to store
                            buffer[buffer_idx] = (byte) data;
                            buffer_idx++;
                        }
                    } else if (state == RCV_ESC) {
                        // Store the byte without looking at it
                        buffer[buffer_idx] = (byte) data;
                        buffer_idx++;
                        state = RCV_MSG;
                    }
                }
            }
        }
    }
}