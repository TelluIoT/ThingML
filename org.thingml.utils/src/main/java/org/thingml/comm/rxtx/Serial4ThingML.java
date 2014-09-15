/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
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

/*import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;*/

import jssc.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import javax.swing.JOptionPane;

public class Serial4ThingML {

    /*static {
        System.out.println("Load RxTx");
        String osName = System.getProperty("os.name");
        String osProc = System.getProperty("os.arch");
        try {
            System.out.println("osName=" + osName + ", osProc=" + osProc);
            if (osName.equals("Mac OS X")) {
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Mac_OS_X/librxtxSerial.jnilib"), "librxtxSerial.jnilib");
            } else if (osName.equals("Win32")) {
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Windows/win32/rxtxSerial.dll"), "rxtxSerial.dll");
            } else if (osName.equals("Win64") || osName.equals("Windows 7")) {
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Windows/win64/rxtxSerial.dll"), "rxtxSerial.dll");
            } else if (osName.equals("Linux") && (osProc.equals("x86-64") || osProc.equals("amd64"))) {
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Linux/x86_64-unknown-linux-gnu/librxtxSerial.so"), "librxtxSerial.so");
            } else if (osName.equals("Linux") && osProc.equals("ia64")) {
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Linux/ia64-unknown-linux-gnu/librxtxSerial.so"), "librxtxSerial.so");
            } else if (osName.equals("Linux") && (osProc.equals("x86") || osProc.equals("i386"))) {
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Linux/i686-unknown-linux-gnu/librxtxParallel.so"), "librxtxParallel.so");
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Linux/i686-unknown-linux-gnu/librxtxSerial.so"), "librxtxSerial.so");
            } else if (osName.equals("Linux") && (osProc.equals("arm"))) {//Raspberry Pi
                NativeLibUtil.copyFile(Serial4ThingML.class.getClassLoader().getResourceAsStream("nativelib/Linux/arm/librxtxSerial.so"), "librxtxSerial.so");
            }
        } catch (Exception e) {
            System.err.println("Cannot Load RxTx on " + osName + "(" + osProc + ")");
            e.printStackTrace();
            System.exit(1);
        }
    } */
    /*
     * public static final byte START_BYTE = 0x12; public static final byte
     * STOP_BYTE = 0x13; public static final byte ESCAPE_BYTE = 0x7D;
     */
    protected String port;
    protected SerialPort serialPort;
    //protected InputStream in;
    //protected OutputStream out;
    protected SerialObserver thing;

    public Serial4ThingML(String port, SerialObserver thing) {
        this.port = selectSerialPort(port);
        this.thing = thing;
        connect();
        //thing.setSerial4ThingML(this);
    }

    void connect() {
        serialPort = new SerialPort(port);
        try {
            final boolean isOpened = serialPort.openPort();
            if (isOpened) {
                serialPort.setParams(9600, 8, 1, 0);
                serialPort.addEventListener(new SerialReader());
            }
        } catch (SerialPortException e) {
            System.err.println("Cannot open serial port. Abort!");
            e.printStackTrace();
            close();
        }


        /*registerPort(port);
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(port);
            if (portIdentifier.isCurrentlyOwned()) {
                System.err.println("Error: Port " + port + " is currently in use");
            } else {
                CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

                if (commPort instanceof SerialPort) {
                    SerialPort serialPort = (SerialPort) commPort;
                    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                    in = serialPort.getInputStream();
                    out = serialPort.getOutputStream();


                    serialPort.addEventListener(new SerialReader());
                    serialPort.notifyOnDataAvailable(true);

                } else {
                    System.err.println("Error: Port " + port + " is not a valid serial port.");
                }
            }
        } catch (Exception e) {
            System.err.println("Cannot open serial port. Abort!");
            e.printStackTrace();
            close();
        } */
    }

    public boolean close() {
        try {
            if (serialPort != null) {
                serialPort.removeEventListener();
                serialPort.closePort();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * ***********************************************************************
     * Serial Port data send operation
     ************************************************************************
     */
    public void sendData(byte[] payload) {
        System.out.println("Send data " + payload.length);
        try {
            //for (int i = 0; i < payload.length; i++) {
                serialPort.writeBytes(payload);
            //}
        } catch (SerialPortException e) {
            System.err.println("Error while writing on serial port. Trying to close and reconnect...");
            close();
            connect();
        }
    }

    /*
     * ***********************************************************************
     * Serial Port Listener - reads packets from the serial line and notifies
     * listeners of incoming packets
     ************************************************************************
     */
    public class SerialReader implements SerialPortEventListener {




        /*
         * public static final int RCV_WAIT = 0; public static final int RCV_MSG
         * = 1; public static final int RCV_ESC = 2;
         */
        //private byte[] buffer = new byte[256];
        //protected int buffer_idx = 0;
        //protected int state = RCV_WAIT;
        @Override
        public void serialEvent(SerialPortEvent event) {
            try {
                //while (serialPort.isOpened()) {
                    if (event.isRXCHAR() && event.getEventValue() > 0) {
                        byte buffer[] = serialPort.readBytes(18);
                        thing.receive(buffer);

                        //TODO: we should escape special thingml bytes...
                    /*if (buffer[buffer_idx] == 0x13 && buffer[buffer_idx - 1] != 0x7D) {
                        //System.out.println("  forward");
                        thing.receive(java.util.Arrays.copyOfRange(buffer, 0, buffer_idx + 1));
                        buffer_idx = 0;
                    } else {
                        buffer_idx++;
                    }*/
                    }

                //}


            } catch (Exception e) {
                //e.printStackTrace();
                System.err.println("Error while reading from serial port. Trying to close and reconnect...");
                /*close();
                connect();*/
            }
        }
    }

    /*
     * ***********************************************************************
     * Serial port utilities: listing
     ************************************************************************
     */
    /**
     * @return A HashSet containing the CommPortIdentifier for all serial ports
     * that are not currently being used.
     */
    public static String[] getAvailableSerialPorts() {
        return SerialPortList.getPortNames();


        /*HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
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
        return h;*/
    }

    /*public static void registerPort(String port) {
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
    }*/

    public static String selectSerialPort(String defaultPort) {

        ArrayList<String> possibilities = new ArrayList<String>();
        for (String commportidentifier : getAvailableSerialPorts()) {
            possibilities.add(commportidentifier);
        }

        int startPosition = possibilities.indexOf(defaultPort);
        if (startPosition == -1 && possibilities.size() > 1) {
            startPosition = 1;
        } else {
            startPosition = 0;
        }

        String serialPort = (String) JOptionPane.showInputDialog(
                null,
                "ThingML Serial",
                "Select serial port",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities.toArray(),
                possibilities.toArray()[startPosition]);

        if (serialPort == null) {
            serialPort = (String) JOptionPane.showInputDialog(
                    null,
                    "ThingML Serial",
                    "Enter serial port",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
        }

        return serialPort;

    }
}
