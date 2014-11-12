/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
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

import ch.ethz.inf.vs.californium.endpoint.{LocalResource}
import ch.ethz.inf.vs.californium.coap.{Request, PUTRequest, POSTRequest, GETRequest, Response, CodeRegistry}

import java.net.URL

import net.modelbased.sensapp.library.senml.Root
import net.modelbased.sensapp.library.senml.MeasurementOrParameter
import net.modelbased.sensapp.library.senml.export.JsonParser

import org.thingml.utils.http.SensAppHelper

import akka.actor.Actor._

class CoAPHTTPResource(val resourceIdentifier : String, val isPUTallowed : Boolean, val isPOSTallowed : Boolean, val isGETallowed : Boolean, httpURLs : Set[String], httpRegistryURLs : Set[String]) extends LocalResource(resourceIdentifier) {
  
  def httpClientName = "CoAP2HTTP-"+resourceIdentifier
  
  setTitle("Generic CoAP/HTTP Resource")
  setResourceType("CoAP-HTTPResource")
  
  /*
   * Transforms the CoAP payload into a SenML representation 
   * that SensApp can manage
   * 
   * Returns None if no representation can be extracted
   * By default, we return the CoAP payload as-it-is
   */
  def transformPayload(request : Request) : (Option[Root], String) = {
    try {
      val root = JsonParser.fromJson(request.getPayloadString)
      (Some(root), "OK!")
    } catch {
      case e : Exception => (None, e.getMessage)
    }
  }
  
  def check(request : Request) : Boolean = true
  
  private def processHTTP(request : Request) : String = {
    transformPayload(request) match {
      case (Some(root), _) =>
        httpURLs.par.map{ url =>
          val realURL = new URL(url)          
          actor{SensAppHelper.pushData(realURL, JsonParser.toJson(root))}
          var data : String = "Sent " + JsonParser.toJson(root) + "\n to " + url + "\n\t#Measurements: " + root.measurementsOrParameters.get.size
          data
        }.mkString("\n")
      case (None, errors) =>
        "Cannot transform payload from request [" + request + "]\n" + errors
    }    
  }
    
  private def process(request : Request) {
    println("process: " + request)
    val response = new Response(/*CodeRegistry.RESP_CONTENT*/)
    
    val responses = request match{
      case put : PUTRequest if (isPUTallowed) => (performCoAPPut(put), processHTTP(request))
      case post : POSTRequest if (isPOSTallowed)  => (performCoAPPost(post), processHTTP(request))
      case get : GETRequest if (isGETallowed) => (performCoAPGet(get), processHTTP(request))
      case _ => ("Request [" + request + "]: Method not supported.", "Not forwarded to HTTP")
    }
    var rep = responses._1 + "\n" + responses._2
    
    println("RESPONSE: "+rep)
    response.setPayload(rep)
    request.respond(response)
  }
  
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
    process(request)
  }

  override final def performPOST(request: POSTRequest) {
    process(request)
  }

  override final def performGET(request: GETRequest) {
    process(request)
  }
}