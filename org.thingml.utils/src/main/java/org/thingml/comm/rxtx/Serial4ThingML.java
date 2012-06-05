/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingml.comm.rxtx;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

public class Serial4ThingML {
    
    

    static {
        System.out.println("Load RxTx");
        try {
            String osName = System.getProperty("os.name");
            String osProc = System.getProperty("os.arch");
            if (osName.equals("Mac OS X")) {
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Mac_OS_X/librxtxSerial.jnilib"), "librxtxSerial.jnilib");
            }
            if (osName.equals("Win32")) {
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Windows/win32/rxtxSerial.dll"), "rxtxSerial.dll");
            }
            if (osName.equals("Win64") || osName.equals("Windows 7")) {
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Windows/win64/rxtxSerial.dll"), "rxtxSerial.dll");
            }
            if (osName.equals("Linux") && osProc.equals("x86-64")) {
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Linux/x86_64-unknown-linux-gnu/librxtxSerial.so"), "librxtxSerial.so");
            }
            if (osName.equals("Linux") && osProc.equals("ia64")) {
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Linux/ia64-unknown-linux-gnu/librxtxSerial.so"), "librxtxSerial.so");
            }
            if (osName.equals("Linux") && osProc.equals("x86")) {
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Linux/i686-unknown-linux-gnu/librxtxParallel.so"), "librxtxParallel.so");
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Linux/i686-unknown-linux-gnu/librxtxSerial.so"), "librxtxSerial.so");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static final byte START_BYTE = 0x12;
    public static final byte STOP_BYTE = 0x13;
    public static final byte ESCAPE_BYTE = 0x7D;
    protected String port;
    protected SerialPort serialPort;
    protected InputStream in;
    protected OutputStream out;
    
    protected org.thingml.utils.comm.SerialThingML thing;

    public Serial4ThingML(String port, org.thingml.utils.comm.SerialThingML thing) {
        this.port = selectSerialPort(port);
        this.thing = thing;
        connect(port);
    }

    void connect(String portName) {
        registerPort(portName);
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            if (portIdentifier.isCurrentlyOwned()) {
                System.err.println("Error: Port " + portName + " is currently in use");
            } else {
                CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

                if (commPort instanceof SerialPort) {
                    SerialPort serialPort = (SerialPort) commPort;
                    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                    in = serialPort.getInputStream();
                    out = serialPort.getOutputStream();
                    thing.setOutputStream(out);

                    serialPort.addEventListener(new SerialReader());
                    serialPort.notifyOnDataAvailable(true);

                } else {
                    System.err.println("Error: Port " + portName + " is not a valid serial port.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (serialPort != null) {
                serialPort.notifyOnDataAvailable(false);
                serialPort.removeEventListener();
                serialPort.close();
            }
        } catch (Exception e) {
        }
    }  

    /* ***********************************************************************
     * Serial Port data send operation
     *************************************************************************/
    protected void sendData(byte[] payload) {
        try {
            // send the start byte
            //out.write((int) START_BYTE);
            // send data
            for (int i = 0; i < payload.length; i++) {
                // escape special bytes
                if (payload[i] == START_BYTE || payload[i] == STOP_BYTE || payload[i] == ESCAPE_BYTE) {
                    out.write((int) ESCAPE_BYTE);
                }
                out.write((int) payload[i]);
            }
            // send the stop byte
            //out.write((int) STOP_BYTE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /* ***********************************************************************
     * Serial Port Listener - reads packets from the serial line and
     * notifies listeners of incoming packets
     *************************************************************************/
    public class SerialReader implements SerialPortEventListener {

        public static final int RCV_WAIT = 0;
        public static final int RCV_MSG = 1;
        public static final int RCV_ESC = 2;
        private byte[] buffer = new byte[256];
        protected int buffer_idx = 0;
        protected int state = RCV_WAIT;

        @Override
        public void serialEvent(SerialPortEvent arg0) {

            int data;

            try {
                while ((data = in.read()) > -1) {
                    //System.out.println("data: " + data);
                    // we got a byte from the serial port
                    if (state == RCV_WAIT) { // it should be a start byte or we just ignore it
                        /*System.out.println("WAIT");
                        System.out.println("data: " + data + " ?= " + START_BYTE);*/
                        if (data == START_BYTE) {
                            state = RCV_MSG;
                            buffer_idx = 0;
                            buffer[buffer_idx] = (byte) data;
                            buffer_idx++;
                        }
                    } else if (state == RCV_MSG) {
                        //System.out.println("RECEIVE");
                        if (data == ESCAPE_BYTE) {
                            state = RCV_ESC;
                        } else if (data == STOP_BYTE) {
                            buffer[buffer_idx] = (byte) data;
                            buffer_idx++;
                            // We got a complete frame
                            //byte[] packet = new byte[buffer_idx];
                            /*for (int i = 0; i < buffer_idx; i++) {
                                packet[i] = buffer[i];
                            }*/
                            //System.out.println("Well-formed packet forwarded to thing");
                            thing.receive(java.util.Arrays.copyOf(buffer, buffer_idx)/*packet*/);
                            
                            state = RCV_WAIT;
                        } else if (data == START_BYTE) {
                            // Should not happen but we reset just in case
                            state = RCV_MSG;
                            buffer_idx = 0;
                            buffer[buffer_idx] = (byte) data;
                            buffer_idx++;
                        } else { // it is just a byte to store
                            buffer[buffer_idx] = (byte) data;
                            buffer_idx++;
                        }
                    } else if (state == RCV_ESC) {
                        //System.out.println("ESCAPE");
                        // Store the byte without looking at it
                        buffer[buffer_idx] = (byte) data;
                        buffer_idx++;
                        state = RCV_MSG;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* ***********************************************************************
     * Serial port utilities: listing
     *************************************************************************/
    /**
     * @return    A HashSet containing the CommPortIdentifier for all serial ports that are not currently being used.
     */
    public static HashSet<CommPortIdentifier> getAvailableSerialPorts() {
        HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
        while (thePorts.hasMoreElements()) {
            CommPortIdentifier com = (CommPortIdentifier) thePorts.nextElement();
            switch (com.getPortType()) {
                case CommPortIdentifier.PORT_SERIAL:
                    try {
                        CommPort thePort = com.open("CommUtil", 50);
                        thePort.close();
                        h.add(com);
                    } catch (PortInUseException e) {
                        System.out.println("Port, " + com.getName() + ", is in use.");
                    } catch (Exception e) {
                        System.err.println("Failed to open port " + com.getName());
                        e.printStackTrace();
                    }
            }
        }
        return h;
    }

    public static void registerPort(String port) {
        String prop = System.getProperty("gnu.io.rxtx.SerialPorts");
        if (prop == null) {
            prop = "";
        }
        if (!prop.contains(port)) {
            prop += port + File.pathSeparator;
            System.setProperty("gnu.io.rxtx.SerialPorts", prop);
        }
        //System.out.println("gnu.io.rxtx.SerialPorts = " + prop);

        prop = System.getProperty("javax.comm.rxtx.SerialPorts");
        if (prop == null) {
            prop = "";
        }
        if (!prop.contains(port)) {
            prop += port + File.pathSeparator;
            System.setProperty("javax.comm.rxtx.SerialPorts", prop);
        }
        //System.out.println("javax.comm.rxtx.SerialPorts = " + prop);
    }

    public static String selectSerialPort(String defaultPort) {

        ArrayList<String> possibilities = new ArrayList<String>();
        for (CommPortIdentifier commportidentifier : getAvailableSerialPorts()) {
            possibilities.add(commportidentifier.getName());
        }

        int startPosition = possibilities.indexOf(defaultPort);
        if (startPosition == -1 && possibilities.size() > 1) {
            startPosition = 1;
        } else {
            startPosition = 0;
        }
        
       return (String) JOptionPane.showInputDialog(
               null,
               "ThingML Serial",
               "Select serial port",
               JOptionPane.PLAIN_MESSAGE,
               null,
               possibilities.toArray(),
               possibilities.toArray()[startPosition]);
        
    }
}