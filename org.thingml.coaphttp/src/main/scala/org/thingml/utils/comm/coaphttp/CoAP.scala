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

import ch.ethz.inf.vs.californium.endpoint.{LocalResource}
import ch.ethz.inf.vs.californium.coap.{Request, PUTRequest, POSTRequest, GETRequest, Response, CodeRegistry}

//import scala.actors.Actor._

import java.net.URL

import net.modelbased.sensapp.library.system._
import net.modelbased.sensapp.library.senml._
import net.modelbased.sensapp.library.senml.export.JsonParser
import net.modelbased.sensapp.library.senml.export.JsonProtocol._ 

import cc.spray.typeconversion.DefaultUnmarshallers._
import cc.spray.json._
import cc.spray.typeconversion.SprayJsonSupport
import cc.spray.client.{HttpConduit, Get, Put, Post}

import akka.dispatch.Await
import akka.dispatch.Future
import akka.util.duration._

import cc.spray.typeconversion.SprayJsonSupport

class CoAPHTTPResource(val resourceIdentifier : String, val isPUTallowed : Boolean, val isPOSTallowed : Boolean, val isGETallowed : Boolean, httpURLs : Set[String], fireAndForgetHTTP : Boolean) extends LocalResource(resourceIdentifier) with HttpSpraySupport  with SprayJsonSupport {
  
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
  
  private def processHTTP(request : Request, fireAndForgetHTTP : Boolean) : String = {
    transformPayload(request) match {
      case (Some(root), _) =>
        httpURLs.par.map{ url =>
          val realURL = new URL(url)
          val conduit = new HttpConduit(httpClient, realURL.getHost, realURL.getPort) {
            val pipeline = simpleRequest[Root] ~> sendReceive ~> unmarshal[String]
          }
          val response = conduit.pipeline(
            request match {
              case put : PUTRequest => Put(url, root)
              case post : POSTRequest => Post(url, root)
              case get : GETRequest => Get(url, root)
            }
          ).onSuccess { case _ => conduit.close() }
          .onFailure { case _ => conduit.close(); println("Error while notifiying ["+url+"] for ["+root+"]")}
          
          var data : String = "Sent to " + url + "\n No response was requested"
          if (!fireAndForgetHTTP) {
            data = try {Await.result(response, 3 seconds).toString} catch { case e : Exception => "TIMEOUT:" + url }
            //conduit.close()
          } 
          data
        }.mkString("\n")//returns the "\n"-separated list of responses of all the servers
      case (None, errors) =>
        "Cannot transform payload from request [" + request + "]\n" + errors
    }    
  }
    
  private def process(request : Request, fireAndForgetHTTP : Boolean) {
    println("process: " + request + "(" + fireAndForgetHTTP + ")")
    val response = new Response(/*CodeRegistry.RESP_CONTENT*/)
    /*if (check(request)) {*/
    val responses = request match{
      case put : PUTRequest if (isPUTallowed) => (performCoAPPut(put), Future{processHTTP(request, fireAndForgetHTTP)})
      case post : POSTRequest if (isPOSTallowed)  => (performCoAPPost(post), Future{processHTTP(request, fireAndForgetHTTP)})
      case get : GETRequest if (isGETallowed) => (performCoAPGet(get), Future{processHTTP(request, fireAndForgetHTTP)})
      case _ => ("Request [" + request + "]: Method not supported.", Future{"Not forwarded to HTTP"})
    }
    var rep = responses._1 + "\n"
    if(!fireAndForgetHTTP) {
      try {
        val httpResponse = Await.result(responses._2, 4 seconds).toString
        rep = rep + httpResponse
      } catch {
        case e : Exception => rep = rep + "HTTP timeout: not able to contact all servers within 5 seconds"
      }
    }
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
    process(request, fireAndForgetHTTP)
  }

  override final def performPOST(request: POSTRequest) {
    process(request, fireAndForgetHTTP)
  }

  override final def performGET(request: GETRequest) {
    process(request, fireAndForgetHTTP)
  }
}