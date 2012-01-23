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
 * This file provides implicits so that we can actually get byte arrays from all
 * Scala primitive types using the java nio.ByteBuffer (and not just ONE byte) and
 * also get primitive types out of byte arrays
 * @author Brice Morin (brice.morin@sintef.no)
 */
package org.thingml.utils.comm

import java.nio.{/*ByteOrder, */ByteBuffer}


object SerializableTypes {
  implicit def serializationAspect(self:String) = SerializableString(self)
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
  def byteSize : Int
}

case class DeserializableArray(bytes : Array[Byte]) {
  val trueByte = Array(1.toByte)
  val falseByte = Array(0.toByte)

  def toByte : Byte = bytes(0)
  def toBoolean : Boolean = (if (bytes == trueByte) true else if (bytes == falseByte) false else false)
  def toShort : Short = ByteBuffer.wrap(bytes.toArray)/*.order(ByteOrder.LITTLE_ENDIAN)*/.getShort
  def toInt : Int = ByteBuffer.wrap(bytes.toArray)/*.order(ByteOrder.LITTLE_ENDIAN)*/.getInt
  def toLong : Long = ByteBuffer.wrap(bytes.toArray)/*.order(ByteOrder.LITTLE_ENDIAN)*/.getLong
  def toFloat : Float = ByteBuffer.wrap(bytes.toArray)/*.order(ByteOrder.LITTLE_ENDIAN)*/.getFloat
  def toDouble : Double = ByteBuffer.wrap(bytes.toArray)/*.order(ByteOrder.LITTLE_ENDIAN)*/.getDouble
  def toChar : Char = bytes(0).toChar
  override def toString : String = bytes.collect{case b => {b}.toChar}.mkString
}

case class SerializableBoolean(myBoolean : Boolean) extends Serializable[Boolean]{
  val trueByte = Array(1.toByte)
  val falseByte = Array(0.toByte)

  override def toBytes : Array[Byte] = {
    return (if (myBoolean) trueByte else falseByte)
  }

  override def byteSize = 2
}

case class SerializableShort(myShort : Short) extends Serializable[Short]{
  override def toBytes : Array[Byte] = {
    return ByteBuffer.allocate(byteSize).putShort(myShort).array()
  }

  override def byteSize = 2
}

case class SerializableInt(myInt : Int) extends Serializable[Int]{
  override def toBytes : Array[Byte] = {
    return ByteBuffer.allocate(byteSize).putInt(myInt).array()
  }

  override def byteSize = 4
}

case class SerializableLong(myLong : Long) extends Serializable[Long]{
  override def toBytes : Array[Byte] = {
    return ByteBuffer.allocate(byteSize).putLong(myLong).array()
  }

  override def byteSize = 4
}

case class SerializableFloat(myFloat : Float) extends Serializable[Float]{
  override def toBytes : Array[Byte] = {
    return ByteBuffer.allocate(byteSize).putFloat(myFloat).array()
  }

  override def byteSize = 4
}

case class SerializableDouble(myDouble : Double) extends Serializable[Double]{
  override def toBytes : Array[Byte] = {
    return ByteBuffer.allocate(byteSize).putDouble(myDouble).array()
  }

  override def byteSize = 8
}

case class SerializableChar(myChar : Char) extends Serializable[Char]{
  override def toBytes : Array[Byte] = {
    return Array(myChar.toByte)
  }

  override def byteSize = 2
}

case class SerializableString(myString : String) extends Serializable[String]{
  override def toBytes : Array[Byte] = {
    return myString.toCharArray.collect{case c => c.toByte}.toArray
  }

  override def byteSize = 8
}