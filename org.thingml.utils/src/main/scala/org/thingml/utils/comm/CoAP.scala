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

import ch.eth.coap.endpoint.LocalEndpoint
import ch.eth.coap.coap.{Request, PUTRequest}
import java.net.URI

trait CoAPThingML {

  val rootURI = "coap://localhost:5683"//TODO: this should not be hard wired

  var coapServer : CoAP = _
  def setCoapServer(coapServer : CoAP) {this.coapServer = coapServer}

  /************************************************************************
   * Send and receive operations to allow communication between CoAP and ThingML
   *************************************************************************/
  def sendDataViaCoAP(bytes : Array[Byte], resourceURI : String) {
    val request = new PUTRequest()
    val uri = new URI(rootURI + "/" + resourceURI)
    request.setURI(uri)
    request.setPayload(bytes)

    //enable response queue in order to use blocking I/O
    //request.enableResponseQueue(true)//Let's just fire-n-forget for now...

    request.execute()
  }
  
  def sendData(bytes : Array[Byte]) {
    Logger.debug("sendData(" + bytes.mkString("[", ", ", "]") + ")")
    val uri = coapServer.resourceMap.get(bytes(4)).getOrElse("")//it should be bytes(3) in the 16-bytes array, but this 18-bytes array includes the START and STOP bytes, hence the index++
    sendDataViaCoAP(bytes, uri)
  }

  def receive(byte : Array[Byte])//This will be refined in the Serial Thing defined in ThingML
}

class CoAP(val coapThingML : CoAPThingML) extends LocalEndpoint {
  Logger.info("Californium ThingML server listening at port " + port())

  var resourceMap = Map[Byte, String]()
  //val rootThingMLresource = new ThingMLCoAPResource(server = this)
  //addResource(rootThingMLresource)
  coapThingML.setCoapServer(this)

  def addResource(resource: ThingMLCoAPResource) {
    super.addResource(resource)
    resourceMap += (resource.code -> resource.getResourcePath)
  }

  override def handleRequest(request: Request) {
    Logger.debug("Incoming request: " + request)
    request.log
    super.handleRequest(request)
  }
}