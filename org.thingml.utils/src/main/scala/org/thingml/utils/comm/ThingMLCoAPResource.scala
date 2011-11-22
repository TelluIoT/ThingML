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

import ch.eth.coap.endpoint.LocalResource
import org.thingml.utils.log.Logger
import ch.eth.coap.coap._

class ThingMLCoAPResource(val resourceIdentifier : String = "ThingML", val coapThingML : CoAPThingML) extends LocalResource(resourceIdentifier) {

  setResourceTitle("Generic ThingML Resource")
  setResourceType("ThingMLResource")

  override def performPOST(request: POSTRequest) {
    Logger.debug("performPOST: " + request.getPayload.mkString ("[", ", ", "]"))
    //Send the payload to the ThingML side
    coapThingML.receive(request.getPayload)

    //Default response, whatever we do with the request
    val response = new Response(CodeRegistry.RESP_CONTENT)
    response.setPayload("OK!")
    request.respond(response)
  }

  override def performGET(request: GETRequest) {
    Logger.debug("performGET: " + request.getPayload.mkString ("[", ", ", "]"))
    Logger.warning("performGET not supported")

    //Default response, whatever we do with the request
    val response = new Response(CodeRegistry.RESP_METHOD_NOT_ALLOWED)
    response.setPayload("GET not supported by ThingML resources")
    request.respond(response)
  }
}