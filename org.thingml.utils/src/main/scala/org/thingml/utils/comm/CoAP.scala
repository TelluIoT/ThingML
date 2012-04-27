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

import ch.eth.coap.endpoint.{LocalEndpoint, RemoteEndpoint, Endpoint, LocalResource}
import ch.eth.coap.coap.{Request, PUTRequest, Response}
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

  def addResource(resource: ThingMLCoAPLocalResource) {
    super.addResource(resource)
    resourceMap += (resource.code -> resource.getResourcePath)
  }
  
  override def handleRequest(request: Request) {
    Logger.debug("Incoming request: " + request)
    request.log
    super.handleRequest(request)
  }
  
}

class RemoteCoAP(val coapThingML : CoAPThingML, uri : URI) extends RemoteEndpoint(uri) with CoAP {
  self : RemoteEndpoint =>

  coapThingML.setCoapServer(this)
  
  override def uriToString = uri.toString
  
  protected[comm] def getURI = uri

  override def handleRequest(request: Request) {
    Logger.debug("Incoming request: " + request)
    request.log
    super.handleRequest(request)
  }  
}


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
  
  def send(bytes : Array[Byte]) {
    requestMap.get(bytes(4)) match {
      case Some(r) =>
        r.sendData(bytes)
      case None =>
        Logger.warning("Request not found. No request with code = " + bytes(4))
    }
  }
  
}