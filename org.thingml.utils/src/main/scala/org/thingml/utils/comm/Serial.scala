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
package org.thingml.utils.comm

import gnu.io.{CommPort, CommPortIdentifier, PortInUseException, SerialPort, SerialPortEvent, SerialPortEventListener}

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
//import javax.swing.JOptionPane

import org.thingml.utils.log.Logger

class Serial(port : String) {

  Logger.info("Load RxTx")
  try {
    val osName = System.getProperty("os.name")
    val osProc = System.getProperty("os.arch")
    if (osName.equals("Mac OS X")) {
      NativeLibUtil.copyFile(classOf[Serial].getClassLoader().getResourceAsStream("nativelib/Mac_OS_X/librxtxSerial.jnilib"), "librxtxSerial.jnilib")
    }
    if (osName.equals("Win32")) {
      NativeLibUtil.copyFile(classOf[Serial].getClassLoader().getResourceAsStream("nativelib/Windows/win32/rxtxSerial.dll"), "rxtxSerial.dll")
    }
    if (osName.equals("Win64") || osName.equals("Windows 7")) {
      NativeLibUtil.copyFile(classOf[Serial].getClassLoader().getResourceAsStream("nativelib/Windows/win64/rxtxSerial.dll"), "rxtxSerial.dll")
    }
    if (osName.equals("Linux") && osProc.equals("x86-64")) {
      NativeLibUtil.copyFile(classOf[Serial].getClassLoader().getResourceAsStream("nativelib/Linux/x86_64-unknown-linux-gnu/librxtxSerial.so"), "librxtxSerial.so")
    }
    if (osName.equals("Linux") && osProc.equals("ia64")) {
      NativeLibUtil.copyFile(classOf[Serial].getClassLoader().getResourceAsStream("nativelib/Linux/ia64-unknown-linux-gnu/librxtxSerial.so"), "librxtxSerial.so")
    }
    if (osName.equals("Linux") && osProc.equals("x86")) {
      NativeLibUtil.copyFile(classOf[Serial].getClassLoader().getResourceAsStream("nativelib/Linux/i686-unknown-linux-gnu/librxtxParallel.so"), "librxtxParallel.so")
      NativeLibUtil.copyFile(classOf[Serial].getClassLoader().getResourceAsStream("nativelib/Linux/i686-unknown-linux-gnu/librxtxSerial.so"), "librxtxSerial.so")
    }
  } catch {
    case e : Exception => e.printStackTrace()
  }

  val START_BYTE = 0x12;
  val STOP_BYTE = 0x13;
  val ESCAPE_BYTE = 0x7D;
    
  protected var serialPort : SerialPort = _
  protected var in : InputStream = _
  protected var out : OutputStream = _

  connect()
    
  def connect() {
    registerPort()
    try {
      val portIdentifier = CommPortIdentifier.getPortIdentifier(port)
      if (portIdentifier.isCurrentlyOwned()) {
        Logger.error("Port " + port + " is currently in use")
      } else {
        val commPort = portIdentifier.open("SerialThingML", 2000)

        commPort match {
          case s : SerialPort => 
            serialPort = s
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE)

            in = serialPort.getInputStream()
            out = serialPort.getOutputStream()

            serialPort.addEventListener(new SerialReader());
            serialPort.notifyOnDataAvailable(true);

          case _ => Logger.error("Port " + port + " is not a valid serial port.");
        }
      }
    } catch {
      case e : Exception => e.printStackTrace()
    } finally {
      close()
    }
  }
    

  def close() {
    try {
      if (in != null) {
        in.close()
      }
      if (out != null) {
        out.close()
      }
      if (serialPort != null) {
        serialPort.notifyOnDataAvailable(false)
        serialPort.removeEventListener()
        serialPort.close()
      }
    } catch {
      case e : Exception => e.printStackTrace()
    }
  }

  /* ***********************************************************************
   * Serial Port data send operation
   *************************************************************************/
  def sendData(payload : Array[Byte]) {
    try {
      // send the start byte
      out.write(START_BYTE)
      // send data
      payload.foreach{b =>
        // escape special bytes
        if (b == START_BYTE || b == STOP_BYTE || b == ESCAPE_BYTE) {
          out.write(ESCAPE_BYTE)
        }
        out.write(b)
      }
      // send the stop byte
      out.write(STOP_BYTE)
    } catch {
      case e : IOException => e.printStackTrace()
    }
  }

  /* ***********************************************************************
   * Serial Port Listener - reads packets from the serial line and
   * notifies listeners of incoming packets
   *************************************************************************/
  class SerialReader extends SerialPortEventListener {

    val RCV_WAIT = 0
    val RCV_MSG = 1
    val RCV_ESC = 2
      
    private var buffer = new Array[Byte](256)
    var buffer_idx = 0
    var state = RCV_WAIT

    def serialEvent(event : SerialPortEvent) {

      var data = in.read()

      try {
        while (data > -1) {
          // we got a byte from the serial port
          if (state == RCV_WAIT) { // it should be a start byte or we just ignore it
            if (data == START_BYTE) {
              state = RCV_MSG
              buffer_idx = 0
            }
          } else if (state == RCV_MSG) {
            if (data == ESCAPE_BYTE) {
              state = RCV_ESC
            } else if (data == STOP_BYTE) {
              // We got a complete frame
              val packet = new Array[Byte](buffer_idx)
              for(i <- 0 to buffer_idx) {
                packet(i) = buffer(i)
              }
                
              //TODO: do something with packet
                
              state = RCV_WAIT
            } else if (data == START_BYTE) {
              // Should not happen but we reset just in case
              state = RCV_MSG
              buffer_idx = 0
            } else { // it is just a byte to store
              buffer(buffer_idx) = data.toByte
              buffer_idx += 1
            }
          } else if (state == RCV_ESC) {
            // Store the byte without looking at it
            buffer(buffer_idx) = data.toByte
            buffer_idx += 1
            state = RCV_MSG
          }
          data = in.read()
        } 
      } catch {
        case e : IOException => e.printStackTrace()
      }
    }
  }

  /* ***********************************************************************
   * Serial port utilities: listing
   *************************************************************************/
  /**
   * @return    A HashSet containing the CommPortIdentifier for all serial ports that are not currently being used.
   */
  /* public static HashSet<CommPortIdentifier> getAvailableSerialPorts() {
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
   }*/

  def registerPort() {
    var prop = System.getProperty("gnu.io.rxtx.SerialPorts")
    if (prop == null) {
      prop = ""
    }
    if (!prop.contains(port)) {
      prop += port + File.pathSeparator
      System.setProperty("gnu.io.rxtx.SerialPorts", prop)
    }

    prop = System.getProperty("javax.comm.rxtx.SerialPorts")
    if (prop == null) {
      prop = ""
    }
    if (!prop.contains(port)) {
      prop += port + File.pathSeparator
      System.setProperty("javax.comm.rxtx.SerialPorts", prop)
    }
  }

  /*public static String selectSerialPort() {

   ArrayList<String> possibilities = new ArrayList<String>();
   possibilities.add("Emulator");
   for (CommPortIdentifier commportidentifier : getAvailableSerialPorts()) {
   possibilities.add(commportidentifier.getName());
   }

   int startPosition = 0;
   if (possibilities.size() > 1) {
   startPosition = 1;
   }
        
   return (String) JOptionPane.showInputDialog(
   null,
   "JArduino",
   "Select serial port",
   JOptionPane.PLAIN_MESSAGE,
   null,
   possibilities.toArray(),
   possibilities.toArray()[startPosition]);
        
   }*/
}


object NativeLibUtil {
  def copyFile(in : InputStream, to : String) {
    //try {
      val out = new FileOutputStream(to)
      var data = in.read()
      while (data > -1) {
        out.write(data)
        data = in.read()
      } 
      in.close()
      out.close()
    /*} catch {
      case e : IOException => e.printStackTrace()
    } finally {
      if (in != null) {
        try {
          in.close()
        } catch  {
          case e : IOException => e.printStackTrace()
        }
      }
      if (out != null) {
        try {
          out.close()
        } catch {
          case e : IOException => e.printStackTrace()
        }
      }
    }*/
  }
}