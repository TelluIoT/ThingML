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

import org.thingml.utils.comm.SerializableTypes._

import ch.ethz.inf.vs.californium.endpoint.{Resource, RemoteResource, LocalResource}
import ch.ethz.inf.vs.californium.coap.{Request, PUTRequest, Response, CodeRegistry, POSTRequest, GETRequest, ResponseHandler, LinkAttribute}

import org.thingml.utils.log.Logger
import org.thingml.utils.comm.coaphttp.CoAPHTTPResource

import net.modelbased.sensapp.library.senml.Root
import net.modelbased.sensapp.library.senml.MeasurementOrParameter

import scala.collection.JavaConversions._

class ThingMLCoAPRequest(val code : Byte, val resourceURI : String = "ThingML", val serverURI : String) /*extends ResponseHandler*/ {

  def sendData(bytes : Array[Byte]) {
    val request = new PUTRequest()
    request.setURI(serverURI + "/" + resourceURI)
    request.setPayload(bytes)

    request.enableResponseQueue(true)
    //request.registerResponseHandler(this)    
    request.execute()
    
    val response = request.receiveResponse()
    if (response != null) {
      response.prettyPrint
    }
  }
  
  /*override def handleResponse(response : Response) {
   //Logger.info("Response RTT = " + response.getRTT)
   response.prettyPrint();
   } */ 
}

trait ThingMLResource {self : LocalResource =>
  def code : Byte = 0x00
  //def registryURL : Option[String]
}

class ThingMLTypeResource(val resourceIdentifier : String = "ThingML") extends LocalResource(resourceIdentifier) with ThingMLResource {
  def addSubResource(resource : ThingMLResource) {
    super.add(resource.asInstanceOf[LocalResource])
    registerToHTTP(resource)
    //server.resourceMap += (resource.code -> resource.asInstanceOf[LocalResource].getResourcePath)
  }

  def registerToHTTP(resource : ThingMLResource) {
    val json = "{\"id\": \"myVeryOwnSensor\", \"descr\": \"ThingML device\", \"schema\": { \"backend\": \"raw\", \"template\": \"Numerical\"}}" 
    //TODO: send a post request to HTTP.
  }
}

abstract class ThingMLMessageResource(override val resourceIdentifier : String = "ThingML", override val isPUTallowed : Boolean, override val isPOSTallowed : Boolean, override val isGETallowed : Boolean, httpURLs : Set[String], override val code : Byte = 0x00, val server : CoAP) extends CoAPHTTPResource(resourceIdentifier, isPUTallowed, isPOSTallowed, isGETallowed, httpURLs) with ThingMLResource {

  setTitle("Generic ThingML Resource")
  setResourceType("ThingMLResource")
  
  lazy val senMLpath = List(getPath.split("/")(1), getPath.split("/")(2)).mkString("-")
  
  

  val buffer = new Array[Byte](16)
  
  //TODO: remove the hacks
  def getBytes[T<:AnyVal](m : MeasurementOrParameter, flag : String) : Array[Byte] = {
    m.stringValue match {
      case Some(s) => s.toBytes()
      case None => 
        val v : Double = m.value.getOrElse(m.valueSum.getOrElse(m.booleanValue.getOrElse(null.asInstanceOf[Unit]))).asInstanceOf[AnyVal].toDouble
        flag match {
          case f if (f == "Int")  => v.toInt.toBytes()
          case f if (f == "Long")  => v.toLong.toBytes()
          case f if (f == "Short")  => v.toShort.toBytes()
          case f if (f == "Float")  => v.toFloat.toBytes()
          case f if (f == "Double")  => v.toBytes()
          case f if (f == "Float")  => v.toFloat.toBytes()
          case f if (f == "Char") => v.toChar.toBytes()
          case f if (f == "Boolean") => (v>=1).toBytes()
          case _  => v.toBytes()
        }
    }
    
    
  }
  
  def createMeasurement(name : String, unit : String, value : AnyVal, time : Long) : Option[MeasurementOrParameter] = {
    try {
      value match {
        case b : Boolean => 
          println("     Create measurement from boolean")
          Some(MeasurementOrParameter(Some(name), None, None, None, Some(b), None, Some(time), None))
        case c : Char => createMeasurement(name, unit, c.toString, time)
        case u : Unit => None
        case n => Some(MeasurementOrParameter(Some(name), Some(unit), Some(n.toDouble), None, None, None, Some(time), None))
      }
    } catch {
      case iae : IllegalArgumentException => None
      case e : Exception => throw e
    }
  }
  
  def createMeasurement(name : String, unit : String, value : String, time : Long) : Option[MeasurementOrParameter] = {
    try {
      Some(MeasurementOrParameter(Some(name), Some(unit), None, Some(value), None, None, Some(time), None))
    } catch {
      case iae : IllegalArgumentException => None
      case e : Exception => throw e
    }  
  }
  def isThingML(payload : Array[Byte]) : Boolean = {
    payload.length > 4 && payload(3) == code/* && payload(0) == 0x12 && payload(payload.length-1) == 0x13*/
  }

  def resetBuffer(){
    for(i <- 0 until 16) {
      buffer(i) = 0x13
    }
  }

  override def transformPayload(request : Request) : (Option[Root], String) = {
    super.transformPayload(request) match {
      case (Some(root), status) => (Some(root), status)
      case (None, errors) => 
        if (isThingML(request.getPayload)) {
          parse(request.getPayload)
        }
        else {
          (None, "Payload is neither SenML nor ThingML.\nPayload is: "+request.getPayload.mkString("[", ",", "]"))
        }
    }
  }
  
  def parse(payload : Array[Byte]) : (Option[Root], String) //This should be overridden in message resource generated by ThingML
  def toThingML(root : Root) : Array[Byte]
  def setAttributes(root : Root) {
    root.measurementsOrParameters match {
      case Some(params) =>
        params.foreach{p => 
          clearAttribute(p.name.getOrElse("Unknown"))
          setAttribute(new LinkAttribute(p.name.getOrElse("Unknown"), p.stringValue.getOrElse(p.value.getOrElse(p.valueSum.getOrElse(p.booleanValue.getOrElse("unknown")))).toString));
          //setAttributeValue(p.name.getOrElse("Unknown"), p.stringValue.getOrElse(p.value.getOrElse(p.valueSum.getOrElse(p.booleanValue.getOrElse("unknown")))).toString)
        } 
      case _ =>
    } 
  }
  
  override def performCoAPPut(request: PUTRequest) : String = {
    transformPayload(request) match {
      case (Some(root), status) =>
        setAttributes(root)
        println("toThingML: " + toThingML(root).mkString("[", ", ", "]"))
        server.coapThingML.receive(server.coapThingML.escape(toThingML(root)))//we should implement a root to ThingML def
        return "OK!"
      case (None, errors) => errors
    }
  }

  //POST is currently not supported. It will be supported for top-level resources corresponding to ThingML types
  //so that the gateway could dynamically support new instances of known types
  override def performCoAPPost(request: POSTRequest) : String = {
    Logger.warning("POST not supported")
    
    return "POST not supported (currently) by ThingML resources."
  }

  override def performCoAPGet(request: GETRequest) : String = {
    /*val builder = new java.lang.StringBuilder()
     writeAttributes(builder)
   
     return builder.toString*/
   
    return getAttributes.mkString("[", ";", "]")
  }

  def addSubResource(resource : ThingMLResource) {
    super.add(resource.asInstanceOf[LocalResource])
    server.resourceMap += (resource.code -> resource.asInstanceOf[LocalResource].getPath)
  }
}