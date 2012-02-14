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
package org.thingml.utils.comm

import org.thingml.utils.comm.SerializableTypes._




class CoAPServer4testOneInstance(coapThingML : CoAPThingML, port : Int) extends CoAP(coapThingML, port){
//Types
val TestThingResource = new ThingMLCoAPResource(resourceIdentifier = "TestThing", server = this)
addResource(TestThingResource)

//Instances and Messages
val testOneInstance_testResource = new TestThingCoAPResource(resourceIdentifier = "testOneInstance_test", server = this)
TestThingResource.addSubResource(testOneInstance_testResource)
testOneInstance_testResource.addSubResource(new M4CoAPResource(server = this))
testOneInstance_testResource.addSubResource(new M2CoAPResource(server = this))
testOneInstance_testResource.addSubResource(new M1CoAPResource(server = this))
testOneInstance_testResource.addSubResource(new M3CoAPResource(server = this))

}

class TestThingCoAPResource(override val resourceIdentifier : String = "TestThing", override val code : Byte = 0x00,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
setResourceTitle("TestThing ThingML resource")
setResourceType("ThingMLResource")

}

class M1CoAPResource(override val resourceIdentifier : String = "m1", override val code : Byte = 27.toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
setResourceTitle("M1 ThingML resource")
setResourceType("ThingMLResource")

override def checkParams(params : Map[String, String]) = {
params.size == 0}

override def doParse(params : Map[String, String]) {
resetBuffer
buffer(0) = 0x12
buffer(1) = 1.toByte
buffer(2) = 0.toByte
buffer(3) = 0.toByte
buffer(4) = code
buffer(5) =  (0).toByte
}

}

class M2CoAPResource(override val resourceIdentifier : String = "m2", override val code : Byte = 1.toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
setResourceTitle("M2 ThingML resource")
setResourceType("ThingMLResource")

override def checkParams(params : Map[String, String]) = {
params.size == 1 && (params.get("i") match{
case Some(p) => try {p.toShort
true} catch {case _ => false}
case None => false})}

override def doParse(params : Map[String, String]) {
resetBuffer
buffer(0) = 0x12
buffer(1) = 1.toByte
buffer(2) = 0.toByte
buffer(3) = 0.toByte
buffer(4) = code
buffer(5) =  (0 + params.get("i").get.toShort.byteSize).toByte
var index = 6
params.get("i").get.toShort.toBytes.foreach{b => 
buffer(index) = b
index = index + 1
}

}

}

class M3CoAPResource(override val resourceIdentifier : String = "m3", override val code : Byte = 2.toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
setResourceTitle("M3 ThingML resource")
setResourceType("ThingMLResource")

override def checkParams(params : Map[String, String]) = {
params.size == 1 && (params.get("s") match{
case Some(p) => try {p.toString
true} catch {case _ => false}
case None => false})}

override def doParse(params : Map[String, String]) {
resetBuffer
buffer(0) = 0x12
buffer(1) = 1.toByte
buffer(2) = 0.toByte
buffer(3) = 0.toByte
buffer(4) = code
buffer(5) =  (0 + params.get("s").get.toString.byteSize).toByte
var index = 6
params.get("s").get.toString.toBytes.foreach{b => 
buffer(index) = b
index = index + 1
}

}

}

class M4CoAPResource(override val resourceIdentifier : String = "m4", override val code : Byte = 12.toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
setResourceTitle("M4 ThingML resource")
setResourceType("ThingMLResource")

override def checkParams(params : Map[String, String]) = {
params.size == 2 && (params.get("i") match{
case Some(p) => try {p.toShort
true} catch {case _ => false}
case None => false}) && (params.get("s") match{
case Some(p) => try {p.toString
true} catch {case _ => false}
case None => false})}

override def doParse(params : Map[String, String]) {
resetBuffer
buffer(0) = 0x12
buffer(1) = 1.toByte
buffer(2) = 0.toByte
buffer(3) = 0.toByte
buffer(4) = code
buffer(5) =  (0 + params.get("i").get.toShort.byteSize + params.get("s").get.toString.byteSize).toByte
var index = 6
params.get("i").get.toShort.toBytes.foreach{b => 
buffer(index) = b
index = index + 1
}

params.get("s").get.toString.toBytes.foreach{b => 
buffer(index) = b
index = index + 1
}

}

}







/*
 
 class CoAPServer4test(coapThingML : CoAPThingML, port : Int) extends CoAP(coapThingML, port){
//Types
  val TestThingResource = new ThingMLCoAPResource(resourceIdentifier = "TestThing", server = this)
  addResource(TestThingResource)

//Instances and Messages
  val test_testRemoteResource = new TestThingCoAPResource(resourceIdentifier = "test_testRemote", server = this)
  TestThingResource.addSubResource(test_testRemoteResource)
  test_testRemoteResource.addSubResource(new M4CoAPResource(server = this))
  test_testRemoteResource.addSubResource(new M2CoAPResource(server = this))
  test_testRemoteResource.addSubResource(new M1CoAPResource(server = this))
  test_testRemoteResource.addSubResource(new M3CoAPResource(server = this))

}

class TestThingCoAPResource(override val resourceIdentifier : String = "TestThing", override val code : Byte = 0x00,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
  setResourceTitle("TestThing ThingML resource")
  setResourceType("ThingMLResource")

}

class MessageDeserializerCoAPResource(override val resourceIdentifier : String = "MessageDeserializer", override val code : Byte = 0x00,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
  setResourceTitle("MessageDeserializer ThingML resource")
  setResourceType("ThingMLResource")

}

class CoAPScalaCoAPResource(override val resourceIdentifier : String = "CoAPScala", override val code : Byte = 0x00,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
  setResourceTitle("CoAPScala ThingML resource")
  setResourceType("ThingMLResource")

}

class MessageSerializerCoAPResource(override val resourceIdentifier : String = "MessageSerializer", override val code : Byte = 0x00,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
  setResourceTitle("MessageSerializer ThingML resource")
  setResourceType("ThingMLResource")

}

class M1CoAPResource(override val resourceIdentifier : String = "m1", override val code : Byte = 27.toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
  setResourceTitle("M1 ThingML resource")
  setResourceType("ThingMLResource")

  override def checkParams(params : Map[String, String]) = {
    params.size == 0}

  override def doParse(params : Map[String, String]) {
    resetBuffer
    buffer(0) = 0x12
    buffer(1) = 1.toByte
    buffer(2) = 0.toByte
    buffer(3) = 0.toByte
    buffer(4) = code
    buffer(5) =  (0).toByte
  }

}

class M2CoAPResource(override val resourceIdentifier : String = "m2", override val code : Byte = 1.toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
  setResourceTitle("M2 ThingML resource")
  setResourceType("ThingMLResource")

  override def checkParams(params : Map[String, String]) = {
    params.size == 1 && (params.get("i") match{
        case Some(p) => try {p.toShort
                             true} catch {case _ => false}
        case None => false})}

  override def doParse(params : Map[String, String]) {
    resetBuffer
    buffer(0) = 0x12
    buffer(1) = 1.toByte
    buffer(2) = 0.toByte
    buffer(3) = 0.toByte
    buffer(4) = code
    buffer(5) =  (0 + params.get("i").get.toShort.byteSize).toByte
    var index = 6
    params.get("i").get.toShort.toBytes.foreach{b => 
      buffer(index) = b
      index = index + 1
    }

  }

}

class M3CoAPResource(override val resourceIdentifier : String = "m3", override val code : Byte = 2.toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
  setResourceTitle("M3 ThingML resource")
  setResourceType("ThingMLResource")

  override def checkParams(params : Map[String, String]) = {
    params.size == 1 && (params.get("s") match{
        case Some(p) => try {p.toString
                             true} catch {case _ => false}
        case None => false})}

  override def doParse(params : Map[String, String]) {
    resetBuffer
    buffer(0) = 0x12
    buffer(1) = 1.toByte
    buffer(2) = 0.toByte
    buffer(3) = 0.toByte
    buffer(4) = code
    buffer(5) =  (0 + params.get("s").get.toString.byteSize).toByte
    var index = 6
    params.get("s").get.toString.toBytes.foreach{b => 
      buffer(index) = b
      index = index + 1
    }

  }

}

class M4CoAPResource(override val resourceIdentifier : String = "m4", override val code : Byte = 12.toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
  setResourceTitle("M4 ThingML resource")
  setResourceType("ThingMLResource")

  override def checkParams(params : Map[String, String]) = {
    params.size == 2 && (params.get("i") match{
        case Some(p) => try {p.toShort
                             true} catch {case _ => false}
        case None => false}) && (params.get("s") match{
        case Some(p) => try {p.toString
                             true} catch {case _ => false}
        case None => false})}

  override def doParse(params : Map[String, String]) {
    resetBuffer
    buffer(0) = 0x12
    buffer(1) = 1.toByte
    buffer(2) = 0.toByte
    buffer(3) = 0.toByte
    buffer(4) = code
    buffer(5) =  (0 + params.get("i").get.toShort.byteSize + params.get("s").get.toString.byteSize).toByte
    var index = 6
    params.get("i").get.toShort.toBytes.foreach{b => 
      buffer(index) = b
      index = index + 1
    }

    params.get("s").get.toString.toBytes.foreach{b => 
      buffer(index) = b
      index = index + 1
    }

  }

}
*/