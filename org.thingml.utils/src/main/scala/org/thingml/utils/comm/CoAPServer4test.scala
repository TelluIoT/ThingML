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

class CoAPServer4test(coapThingML : CoAPThingML) extends CoAP(coapThingML){
//Types
val TestThingResource = new ThingMLCoAPResource(resourceIdentifier = "TestThing", server = this)
addResource(TestThingResource)
val MessageDeserializerResource = new ThingMLCoAPResource(resourceIdentifier = "MessageDeserializer", server = this)
addResource(MessageDeserializerResource)
val CoAPScalaResource = new ThingMLCoAPResource(resourceIdentifier = "CoAPScala", server = this)
addResource(CoAPScalaResource)
val MessageSerializerResource = new ThingMLCoAPResource(resourceIdentifier = "MessageSerializer", server = this)
addResource(MessageSerializerResource)

//Instances and Messages
val test_testRemoteResource = new TestThingCoAPResource(resourceIdentifier = "test_testRemote", server = this)
TestThingResource.addSubResource(test_testRemoteResource)
test_testRemoteResource.addSubResource(new M4CoAPResource(server = this))
test_testRemoteResource.addSubResource(new M2CoAPResource(server = this))
test_testRemoteResource.addSubResource(new M1CoAPResource(server = this))
test_testRemoteResource.addSubResource(new M3CoAPResource(server = this))
val test_deserializerResource = new MessageDeserializerCoAPResource(resourceIdentifier = "test_deserializer", server = this)
MessageDeserializerResource.addSubResource(test_deserializerResource)
//test_deserializerResource.addSubResource(new Receive_bytesCoAPResource(server = this))
test_deserializerResource.addSubResource(new M4CoAPResource(server = this))
test_deserializerResource.addSubResource(new M2CoAPResource(server = this))
test_deserializerResource.addSubResource(new M1CoAPResource(server = this))
test_deserializerResource.addSubResource(new M3CoAPResource(server = this))
val test_networkResource = new CoAPScalaCoAPResource(resourceIdentifier = "test_network", server = this)
CoAPScalaResource.addSubResource(test_networkResource)
//test_networkResource.addSubResource(new Receive_bytesCoAPResource(server = this))
//test_networkResource.addSubResource(new Write_bytesCoAPResource(server = this))
val test_serializerResource = new MessageSerializerCoAPResource(resourceIdentifier = "test_serializer", server = this)
MessageSerializerResource.addSubResource(test_serializerResource)
//test_serializerResource.addSubResource(new Write_bytesCoAPResource(server = this))
test_serializerResource.addSubResource(new M4CoAPResource(server = this))
test_serializerResource.addSubResource(new M2CoAPResource(server = this))
test_serializerResource.addSubResource(new M1CoAPResource(server = this))
test_serializerResource.addSubResource(new M3CoAPResource(server = this))

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

}

class M2CoAPResource(override val resourceIdentifier : String = "m2", override val code : Byte = 1.toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
setResourceTitle("M2 ThingML resource")
setResourceType("ThingMLResource")

}

class M3CoAPResource(override val resourceIdentifier : String = "m3", override val code : Byte = 2.toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
setResourceTitle("M3 ThingML resource")
setResourceType("ThingMLResource")

}

class M4CoAPResource(override val resourceIdentifier : String = "m4", override val code : Byte = 12.toByte,  override val server : CoAP) extends ThingMLCoAPResource(resourceIdentifier, code, server) {
setResourceTitle("M4 ThingML resource")
setResourceType("ThingMLResource")

}