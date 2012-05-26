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
package org.thingml.utils.comm.coaphttp

import ch.eth.coap.endpoint.{LocalResource}
import ch.eth.coap.coap.{Request, PUTRequest, POSTRequest, GETRequest, Response, CodeRegistry}

import scala.actors.Future
import scala.actors.Futures._

class CoAPHTTPResource(val resourceIdentifier : String, val isPUTallowed : Boolean, val isPOSTallowed : Boolean, val isGETallowed : Boolean, httpURLs : Set[String]) extends LocalResource(resourceIdentifier){
  
  setResourceTitle("Generic CoAP/HTTP Resource")
  setResourceType("CoAP-HTTPResource")

  /*
   * Translate the CoAP REST URI into an HTTP REST URI.
   * By default, we assume REST URIs are aligned in CoAP and HTTP servers
   */
  def translateCoAPtoHTTP() : Option[String] = Some(resourceIdentifier)//getPath()
  
  /*
   * Extract/infer the SenML representation of a request
   * managed by this resource.
   * By default, we assume the payload of the request is a valid
   * SenML description for this resource
   * 
   * Returns None if no SenML representation can be extracted
   */
  def transformPayload(payload : String) : Option[String] = Some(payload)
  
  def check(request : Request) : Boolean = true
  
  def process(request : Request, lambda : String => String) : String = {
    transformPayload(request.getPayloadString) match {
      case Some(senML) =>
        lambda(senML)
      case None =>
        "Cannot transform payload from request [" + request + "]"
    }    
  }
 
  def performHTTPPut(senML : String) : String = {
    val responses = httpURLs.par.map{ url =>
      future{ performHTTPPut(senML, url) }
    }
    responses.map{r => r()}.mkString("\n")
  }

  def performHTTPPut(senML : String, url : String) : String = "OK: "+url
  def performHTTPPost(senML : String) : String = ""
  def performHTTPGet(senML : String) : String = ""
  
  def performCoAPPut(request: PUTRequest) : String = ""
  def performCoAPPost(request: POSTRequest) : String = ""
  def performCoAPGet(request: POSTRequest) : String = ""

 
  override def performPUT(request: PUTRequest) {
    if (isPUTallowed) {
      val response = new Response(CodeRegistry.RESP_CONTENT)
      if (check(request)) {
        val httpResponses = future { process(request, performHTTPPut) }
        val coapResponse = performCoAPPut(request)
      }
      else {
        response.setPayload("Request cannot be handled. Reason: Request [" + request + "] is invalid")
      }
      request.respond(response)
    }
    else {
       val response = new Response(CodeRegistry.RESP_CONTENT)
    }
  }

  override def performPOST(request: POSTRequest) {
    process(request, performHTTPPost)
  }

  override def performGET(request: GETRequest) {
    process(request, performHTTPGet)
  }
}