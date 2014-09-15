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
import java.util.ArrayList;

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
                System.err.println("Error while reading from serial port. Trying to close and reconnect...");
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
