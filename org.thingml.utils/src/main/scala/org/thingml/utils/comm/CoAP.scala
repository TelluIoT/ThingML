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
import ch.eth.coap.coap.{Request, Message}

trait CoAPThingML {
  
  /************************************************************************
   * Send and receive operations to allow communication between CoAP and ThingML
   *************************************************************************/
  def sendDataViaCoAP(bytes : Array[Byte]) {
    //TODO
    Logger.warning("Communication ThingML->CoAP not implemented yet")
  }
  
  def sendData(bytes : Array[Byte]) {
    Logger.debug("sendData(" + bytes.mkString("[", ", ", "]") + ")")
    sendDataViaCoAP(bytes)
  }

  def receive(byte : Array[Byte]) {
    //This will be refined in the Serial Thing defined in ThingML
  }
}

class CoAP(coapThingML : CoAPThingML) extends LocalEndpoint{
  addResource(new ThingMLCoAPResource(coapThingML = coapThingML))
  Logger.info("Californium ThingML server listening at port " + port())

  override def handleRequest(request: Request) {
    System.out.println("Incoming request:")
    request.log
    super.handleRequest(request)
  }
}