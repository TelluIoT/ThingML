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
 * Basic serialization/deserialization facilities for primitive types
 * @author Brice MORIN
 */
package org.thingml.scala.types

import java.nio.ByteBuffer

object SerializableTypes {
  implicit def serializationAspect(self:Boolean) = SerializableBoolean(self)
  implicit def serializationAspect(self:Short) = SerializableShort(self)
  implicit def serializationAspect(self:Int) = SerializableInt(self)
  implicit def serializationAspect(self:Long) = SerializableLong(self)
  implicit def serializationAspect(self:Float) = SerializableFloat(self)
  implicit def serializationAspect(self:Double) = SerializableDouble(self)
  implicit def serializationAspect(self:Array[Byte]) = DeserializableArray(self)
}

trait Serializable[T] {
  def toBytes : Array[Byte]
}

case class DeserializableArray(bytes : Array[Byte]) {
  val trueByte = Array(1.toByte)
  val falseByte = Array(0.toByte)

  def toByte : Byte = bytes(0)
  def toBoolean : Boolean = (if (bytes == trueByte) true else if (bytes == falseByte) false else false)
  def toShort : Short = ByteBuffer.wrap(bytes.toArray).getShort
  def toInt : Int = ByteBuffer.wrap(bytes.toArray).getInt
  def toLong : Long = ByteBuffer.wrap(bytes.toArray).getLong
  def toFloat : Float = ByteBuffer.wrap(bytes.toArray).getFloat
  def toDouble : Double = ByteBuffer.wrap(bytes.toArray).getDouble
  def toChar : Char = bytes(0).toChar
  override def toString : String = bytes.collect{case b => {b}.toChar}.mkString
}

case class SerializableBoolean(myBoolean : Boolean) extends Serializable[Boolean]{
  val trueByte = Array(1.toByte)
  val falseByte = Array(0.toByte)

  override def toBytes : Array[Byte] = {
    return (if (myBoolean) trueByte else falseByte)
  }
}

case class SerializableShort(myShort : Short) extends Serializable[Short]{
  override def toBytes : Array[Byte] = {
    return ByteBuffer.allocate(2).putShort(myShort).array()
  }
}

case class SerializableInt(myInt : Int) extends Serializable[Int]{
  override def toBytes : Array[Byte] = {
    return ByteBuffer.allocate(4).putInt(myInt).array()
  }
}

case class SerializableLong(myLong : Long) extends Serializable[Long]{
  override def toBytes : Array[Byte] = {
    return ByteBuffer.allocate(8).putLong(myLong).array()
  }
}

case class SerializableFloat(myFloat : Float) extends Serializable[Float]{
  override def toBytes : Array[Byte] = {
    return ByteBuffer.allocate(4).putFloat(myFloat).array()
  }
}

case class SerializableDouble(myDouble : Double) extends Serializable[Double]{
  override def toBytes : Array[Byte] = {
    return ByteBuffer.allocate(8).putDouble(myDouble).array()
  }
}

case class SerializableChar(myChar : Char) extends Serializable[Char]{
  override def toBytes : Array[Byte] = {
    return Array(myChar.toByte)
  }
}

case class SerializableString(myString : String) extends Serializable[String]{
  override def toBytes : Array[Byte] = {
    return myString.toCharArray.collect{case c => c.toByte}.toArray
  }
}