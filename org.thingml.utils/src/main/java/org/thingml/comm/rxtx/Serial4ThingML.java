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


import jssc.*;

import javax.swing.*;
import java.awt.*;
import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;

public class Serial4ThingML {

    protected String port;
    protected SerialPort serialPort;
    protected SerialObserver thing;

    public Serial4ThingML(String port, SerialObserver thing) {
        this.port = selectSerialPort(port);
        this.thing = thing;
        connect();
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
            serialPort.writeBytes(payload);
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

        byte buffer[] = new byte[1024];
        int id = 0;

        public SerialReader() {
            init();
        }

        private void init() {
            id = 0;
            for (int i = 0; i < 1024; i++) {
                buffer[i] = 0x13;
            }
        }

        @Override
        public synchronized void serialEvent(SerialPortEvent event) {
            try {
                if (event.isRXCHAR() && event.getEventValue() > 0) {
                    byte current[] = serialPort.readBytes(1);
                    if (buffer[0] == 0x13 && current[0] == 0x12 || buffer[0] == 0x12) {//start byte, new message OR message already initialized
                        for (byte b : current) {
                            buffer[id] = b;
                            id++;
                        }
                    }
                    if (id > 2 && buffer[id-1] == 0x13 && buffer[id - 2] != 0x7D) {//stop byte
                        thing.receive(Arrays.copyOf(buffer, id));
                        init();
                    }
                }
            } catch (Exception e) {
                System.err.println("Error while reading from serial port: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

    /*
     * ***********************************************************************
     * Serial port utilities: listing
     ************************************************************************
     */

    public static String selectSerialPort(String defaultPort) {
        ArrayList<String> possibilities = new ArrayList<String>();
        for (String commportidentifier : SerialPortList.getPortNames()) {
            possibilities.add(commportidentifier);
        }

        int startPosition = possibilities.indexOf(defaultPort);
        if (startPosition == -1 && possibilities.size() > 1) {
            startPosition = 1;
        } else {
            startPosition = 0;
        }

        final boolean isHeadless = GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadless();
        String serialPort = possibilities.get(0);
        if (isHeadless) { //CLI-based selection of serial port
            Console c = System.console();
            if (c == null) {
                System.err.println("No console.");
                return serialPort;
            }

            String input = c.readLine("Choose serial port [or press ENTER to select " + serialPort + "]: ");
            if (input == null || input.equals("") || input.equals("\n")) {
                return serialPort;
            } else {
                return input;
            }
        } else { //open a popup to choose serial port
            serialPort = (String) JOptionPane.showInputDialog(
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
        }

        return serialPort;
    }
}
