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
/*
 * @author Brice MORIN, SINTEF IKT
 */
package org.thingml.utils.comm

import gnu.io.{CommPort, CommPortIdentifier, PortInUseException, SerialPort, SerialPortEvent, SerialPortEventListener}

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

import org.thingml.utils.log.Logger

trait SerialThingML {
  
  protected var out : OutputStream = _
  
  def setOutputStream(out : OutputStream) {this.out = out}
  
  /* ***********************************************************************
   * Serial Port data send and receive operations
   *************************************************************************/
  def sendData(byte : Byte) {
    Logger.debug("SCALA:sendData(" + byte + ")")
    out.write(byte)
  }
  
  def receive(byte : Byte) {
    //This will be refined in the Serial Thing defined in ThingML
  }
}

class Serial(port : String, serialThingML : SerialThingML) {
  Logger.debug("Load RxTx")
  
  protected var serialPort : SerialPort = _
  protected var in : InputStream = _
  protected var out : OutputStream = _
  
  try {
    val osName = System.getProperty("os.name")
    val osProc = System.getProperty("os.arch")
    Logger.debug("OS=" + osName + ", proc=" + osProc)
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
            serialThingML.setOutputStream(out)

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
   * Serial Port Listener - reads bytes from the serial line and
   * notifies the ThingML serial component (via the receive method)
   *************************************************************************/
  class SerialReader extends SerialPortEventListener {
    def serialEvent(event : SerialPortEvent) {
      var data = in.read()
      while (data > -1) {
        serialThingML.receive(data.toByte)
        data = in.read()
      }
    }
  }

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
}

object NativeLibUtil {
  def copyFile(in : InputStream, to : String) {
    val out = new FileOutputStream(to)
    var data = in.read()
    while (data > -1) {
      out.write(data)
      data = in.read()
    } 
    in.close()
    out.close()
  }
}