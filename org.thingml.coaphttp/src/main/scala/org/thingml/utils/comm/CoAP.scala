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

import org.thingml.utils.log.Logger

import ch.ethz.inf.vs.californium.endpoint.{LocalEndpoint, RemoteEndpoint, Endpoint, LocalResource}
import ch.ethz.inf.vs.californium.coap.{Request, PUTRequest, Response}
import java.net.{InetAddress, URI}

trait CoAPThingML {

  private var coapServer : CoAP = _
  def setCoapServer(coapServer : CoAP) {this.coapServer = coapServer}

  /*
   * This will be redefined in concrete ThingML things to instantiate their specific (and generated) COaP servers
   */
  def initialize(port : Int = 61616) {
    new LocalCoAP(this, port)
  }

  def receive(byte : Array[Byte])//This will be refined in the COaP Thing defined in ThingML
  
}


abstract trait CoAP {
  self : Endpoint =>
  
  val addr = InetAddress.getLocalHost()
  
  var resourceMap = Map[Byte, String]()
  Logger.info("           Californium/ThingML server started")
  Logger.info("Use Copper, the Firefox plugin for CoAP to access this address:")
  Logger.info("coap://" + addr.getHostAddress + ":" + uriToString)
  Logger.info("             You can download Copper at:") 
  Logger.info("https://addons.mozilla.org/fr/firefox/addon/copper-270430/")
  
  def uriToString : String
  def coapThingML : CoAPThingML  
}

class LocalCoAP(val coapThingML : CoAPThingML, override val port : Int) extends LocalEndpoint(port) with CoAP {

  coapThingML.setCoapServer(this)

  override def uriToString = port.toString

  def addResource(resource: ThingMLResource) {
    super.addResource(resource.asInstanceOf[LocalResource])
    resourceMap += (resource.code -> resource.asInstanceOf[LocalResource].getPath)
  }
  
  override def handleRequest(request: Request) {
    //request.log
    super.handleRequest(request)
  }
  
}

/*class RemoteCoAP(val coapThingML : CoAPThingML, uri : URI) extends RemoteEndpoint(uri) with CoAP {
 self : RemoteEndpoint =>

 coapThingML.setCoapServer(this)
  
 override def uriToString = uri.toString
  
 protected[comm] def getURI = uri

 override def handleRequest(request: Request) {
 //request.log
 super.handleRequest(request)
 }  
 }*/


trait CoAPThingMLClient {
  private var coapClient : CoAPClient = _
  def setCoapClient(coapClient : CoAPClient) {this.coapClient = coapClient}
  
  def send(bytes : Array[Byte]) {
    coapClient.send(bytes)
  }
}

class CoAPClient(thingmlClient : CoAPThingMLClient, serverURI : String) {
  thingmlClient.setCoapClient(this)
  
  var requestMap = Map[Byte, ThingMLCoAPRequest]()
  
  def addRequest(request : ThingMLCoAPRequest) {
    requestMap += (request.code -> request) 
  }
  
  def isThingML(payload : Array[Byte]) : Boolean = {
    payload.length > 5 && payload(0) == 0x12 && payload(payload.length-1) == 0x13
  }
  
  def unescape(payload : Array[Byte]) : Array[Byte] = {
    val result = new Array[Byte](payload.length-2)
    
    var i = 0
    var index = 0
    
    var current : Byte = payload(i)
    if (current == 0x12) {
      i = i + 1
      var next = payload(i)
      if (next != 0x13) {
        var continue = true
        while(continue && i < payload.length-1) {
          current = next
          i = i + 1
          next = payload(i)
          if (current == 0x7D) {
            current = next
            i = i + 1
            next = payload(i)
          }
          result(index) = current
          index = index + 1
          continue = !(next == 0x13 && !(current == 0x7D))
        }
        //result(index) = current
      }
    }
    return result
  }
  
  def send(bytes : Array[Byte]) {
    
    println("         " + unescape(bytes).mkString("[", ", ", "]"))
    
    if (isThingML(bytes)) {
      Logger.debug("Send ThingML data: " + bytes.mkString("[", ", ", "]") + " ...")
      requestMap.get(bytes(4)) match {
        case Some(r) =>
          r.sendData(bytes)
        case None =>
          Logger.warning("Request not found. No request with code = " + bytes(4))
      }
    } else {
      Logger.warning("Trying to send non-ThingML data: " + bytes.mkString("[", ", ", "]"))
    }
  }
  
}