/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.thingml.tronjavamonitor;

import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

 
public class SimpleRead {
 
    static SerialPort serialPort;
    public static TronMonitor monitor;
 
    public static void main(String[] args) {
        
        
        
        serialPort = new SerialPort("/dev/ttyUSB0"); 
        try {
            monitor = new TronMonitor();
        	
        	serialPort.openPort();
            serialPort.setParams(9600, 8, 1, 0);
            //Preparing a mask. In a mask, we need to specify the types of events that we want to track.
            //Well, for example, we need to know what came some data, thus in the mask must have the
            //following value: MASK_RXCHAR. If we, for example, still need to know about changes in states 
            //of lines CTS and DSR, the mask has to look like this: SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR
            int mask = SerialPort.MASK_RXCHAR;
            //Set the prepared mask
            serialPort.setEventsMask(mask);
            //Add an interface through which we will receive information about events
            serialPort.addEventListener(new SerialPortReader(serialPort, monitor));
            System.out.println("[Listener] Start");
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
 
    static class SerialPortReader implements SerialPortEventListener {
    	public byte msgBuffer[];
    	public SerialMachineState state;
    	public byte START_BYTE = 18;
    	public byte STOP_BYTE = 19;
    	public byte ESCAPE_BYTE = 125;
    	public int msgSize = 0;
    	public int bufferSize = 32;
    	public SerialPort serialPort;
    	public TronMonitor monitor;
    	public boolean[] hasLost;
    	
    	public SerialPortReader(SerialPort sp, TronMonitor m) {
    		super();
    		msgBuffer = new byte[bufferSize];
    		state = SerialMachineState.Idle;
    		serialPort = sp;
    		monitor = m;
    		hasLost = new boolean[3];
    		hasLost[0] = false;
    		hasLost[1] = false;
    		hasLost[2] = false;
    	}
    	
    	public int victory() {
    		if(hasLost[0] && hasLost[1]) {
    			return 2;
    		} else if(hasLost[0] && hasLost[2]) {
    			return 1;
    		} else if(hasLost[1] && hasLost[2]) {
    			return 0;
    		} else {
    			return 256;
    		}
    	}
    	
    	public void parse(byte[] msgBuffer, int msgSize) {
    		byte ttl = msgBuffer[0];
    		System.out.println("[SerialPort] ttl = " + ttl);
			ttl--;
			try {
	    		if(msgBuffer[0] > 0) {
					ArrayList<Byte> sendBuffer = new ArrayList<Byte>();
					sendBuffer.add(START_BYTE);
					if((ttl == ESCAPE_BYTE) || (ttl == STOP_BYTE) || (ttl == START_BYTE)) {
						sendBuffer.add(ESCAPE_BYTE);
					}
					sendBuffer.add(ttl);
					for(int i = 1; i < msgSize; i++) {
						if((msgBuffer[i] == ESCAPE_BYTE) || (msgBuffer[i] == STOP_BYTE) || (msgBuffer[i] == START_BYTE)) {
							sendBuffer.add(ESCAPE_BYTE);
						}
						sendBuffer.add(msgBuffer[i]);
					}
					sendBuffer.add(STOP_BYTE);
					byte toSend[] = new byte[sendBuffer.size()];
					for (int i = 0; i < sendBuffer.size(); i++) {
						toSend[i] = sendBuffer.get(i);
					}
					serialPort.writeBytes(toSend);
	    		}
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		int msgID = msgBuffer[1] * 255 + msgBuffer[2];
			switch(msgID) {
					
				case 5:
					System.out.println("[SerialPort] received 5");//ready
					
					break;
					
				case 6://addHead
					System.out.println("[SerialPort] received 6");
					if((msgBuffer[5] < 42) && (msgBuffer[6] < 52)) {
						monitor.paintSquare(msgBuffer[5], msgBuffer[6], msgBuffer[7]);
					}
					break;
					
				case 7://tronLoose
					System.out.println("[SerialPort] received 7 loose");
					hasLost[msgBuffer[5]] = true;
					monitor.fenetre.paintingPanel.victory = victory();
					monitor.fenetre.paintingPanel.repaint();
					break;
					
				case 8:
					System.out.println("[SerialPort] received 8"); //go
					
					break;
			}
    	}
    	
        public void serialEvent(SerialPortEvent event) {
            //Object type SerialPortEvent carries information about which event occurred and a value.
            //For example, if the data came a method event.getEventValue() returns us the number of bytes in the input buffer.
            if(event.isRXCHAR()){
                while(event.getEventValue() > 0){
                    try {
                        byte incByte[] = serialPort.readBytes(1);
                        System.out.print("Received 1 bytes :");
                        System.out.println(incByte[0]);
                        
                        switch(state) {
	                        
	                        case Idle:
	                        	if(incByte[0] == START_BYTE) {
	                        		state = SerialMachineState.Reading;
	                        		msgSize = 0;
	                        	}
	                        	break;
	                        	
	                        case Reading:
	                        	if(incByte[0] == ESCAPE_BYTE) {
	                        		state = SerialMachineState.Escape;
	                        	} else if(incByte[0] == STOP_BYTE) {
	                        		parse(msgBuffer, msgSize);
	                        		state = SerialMachineState.Idle;
	                        	} else if (msgSize < bufferSize) {
	                        		msgBuffer[msgSize] = incByte[0];
	                        		msgSize++;
	                        	} else {
	                        		state = SerialMachineState.Error;
	                        	}
	                        	
	                        	break;
	                        	
	                        case Escape:
	                        	if (msgSize < bufferSize) {
	                        		msgBuffer[msgSize] = incByte[0];
	                        		msgSize++;
	                        		state = SerialMachineState.Reading;
	                        	} else {
	                        		state = SerialMachineState.Error;
	                        	}
	                        	break;
	                        	
	                        case Error:
	                        	System.out.println("[Error] Buffer overflow");
	                        	state = SerialMachineState.Idle;
	                        	break;
	                        
                        }
                    }
                    catch (SerialPortException ex) {
                        System.out.println(ex);
                    }
                }
            }
            //If the CTS line status has changed, then the method event.getEventValue() returns 1 if the line is ON and 0 if it is OFF.
            else if(event.isCTS()){
                if(event.getEventValue() == 1){
                    System.out.println("CTS - ON");
                }
                else {
                    System.out.println("CTS - OFF");
                }
            }
            else if(event.isDSR()){
                if(event.getEventValue() == 1){
                    System.out.println("DSR - ON");
                }
                else {
                    System.out.println("DSR - OFF");
                }
            }
        }
    }
}