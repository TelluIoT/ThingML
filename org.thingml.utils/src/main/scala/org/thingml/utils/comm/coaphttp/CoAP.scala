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
   * Transforms the CoAP payload into a format the HTTP server,
   * and associated services, can understand. For example, it could
   * infer a SenML representation that SensApp can manage
   * 
   * Returns None if no representation can be extracted
   * By default, we return the CoAP payload as-it-is
   */
  def transformPayload(request : Request) : Option[String] = Some(request.getPayloadString)
  
  
  def check(request : Request) : Boolean = true
  
  private def processHTTP(request : Request, lambda : String => String) : String = {
    transformPayload(request) match {
      case Some(readablePayload) =>
        lambda(readablePayload)
      case None =>
        "Cannot transform payload from request [" + request + "]"
    }    
  }
    
  private def process(isMethodAllowed : Boolean, request : Request, httpLambda : String => String) {
    if (isMethodAllowed) {
      val response = new Response(CodeRegistry.RESP_CONTENT)
      if (check(request)) {
        val responses = request match{
          case put : PUTRequest => (performCoAPPut(put), future { processHTTP(request, httpLambda) })
          case post : POSTRequest => (performCoAPPost(post), future { processHTTP(request, httpLambda) })
          case get : GETRequest => (performCoAPGet(get), future { processHTTP(request, httpLambda) })
        }
        response.setPayload(responses._1 + "\n" + responses._2())
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
 
  /**
   * Set of private methods to forward the extracted payload to HTTP
   * These methods should not execute any other business logic
   * Any other logic, if any, is the sole responsibility of the HTTP REST service
   * 
   * For this reason, these def are private.
   */
  private def performHTTPPut(payload : String) : String = {
    val responses = httpURLs.par.map{ url =>
      future{ performSingleHTTPPut(payload, url) }
    }
    responses.map{r => r()}.mkString("\n")
  }
  
  private def performSingleHTTPPut(senML : String, url : String) : String = "Should have been forwarded to " + url
  
  private def performHTTPPost(senML : String) : String = "Should have been forwarded to " + httpURLs.mkString(", ")
  
  private def performHTTPGet(senML : String) : String = "Should have been forwarded to " + httpURLs.mkString(", ")
  
  /**
   * Methods defining the behavior (likely to be locally executed on the CoAP gateway)
   * They could be overriden to define more advanced logic
   * 
   * IMPORTANT: These methods should not directly respond to the request (using request.respond(...))
   * They should return the payload to be added to the response
   */
  def performCoAPPut(request: PUTRequest) : String = "No behavior for CoAP PUT requests"
  def performCoAPPost(request: POSTRequest) : String = "No behavior for CoAP POST requests"
  def performCoAPGet(request: GETRequest) : String = "No behavior for CoAP GET requests"

 
  /**
   * Methods inherited from LocalResource (from CoAP API)
   * They basically execute the local behavior and forward to HTTP
   * They also respond to the CoAP request by concatenating:
   * 1/ the response of the associated performCoAPPut/POST/GET
   * 2/ all the HTTP responses where the extracted payload has been forwarded
   */
  override final def performPUT(request: PUTRequest) {
    process(isPUTallowed, request, performHTTPPut _)
  }

  override final def performPOST(request: POSTRequest) {
    process(isPOSTallowed, request, performHTTPPost _)
  }

  override final def performGET(request: GETRequest) {
    process(isGETallowed, request, performHTTPGet _)
  }
}