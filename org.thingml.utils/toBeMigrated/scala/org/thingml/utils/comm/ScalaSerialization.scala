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
 * This file provides implicits so that we can actually get byte arrays from all
 * Scala primitive types using the java nio.ByteBuffer (and not just ONE byte) and
 * also get primitive types out of byte arrays
 * @author Brice Morin (brice.morin@sintef.no)
 */
package org.thingml.utils.comm

iimport java.nio.{ByteBuffer, ByteOrder}

object SerializableTypes {

  implicit def scalaGeneratorAspect(self: AnyVal) = self match {
    case b: Boolean => SerializableBoolean(b)
    case s: Short => SerializableShort(s)
    case i: Int => SerializableInt(i)
    case l: Long => SerializableLong(l)
    case f: Float => SerializableFloat(f)
    case d: Double => SerializableDouble(d)
    case c: Char => SerializableChar(c)
    case b: Byte => SerializableByte(b)
    case u: Unit => SerializableUnit(u)
  }

  implicit def serializationAspect(self: String) = SerializableString(self)

  implicit def serializationAspect(self: Array[Byte]) = DeserializableArray(wrap(self))

  def wrap(bytes: Array[Byte]): Array[Byte] = {
    val array = new Array[Byte](8)
    Array.copy(bytes, 0, array, 0, Math.min(bytes.size, 8))
    return array
  }
}

trait DynamicSerializable[T] extends Serializable[T] {
  def toVarBytes: Array[Byte]

  val dynamicByteSize: Int
}

trait Serializable[T] {
  def toBytes(): Array[Byte] = toBytes(ByteOrder.BIG_ENDIAN)

  def toBytes(order: ByteOrder): Array[Byte]

  def toDouble: Double

  //for SenML serialization
  def toBoolean: Boolean = throw new java.lang.UnsupportedOperationException("Cannot convert to Boolean")

  val byteSize: Int
}

case class DeserializableArray(bytes: Array[Byte]) {
  def toByte: Byte = bytes(0)

  def toBoolean: Boolean = (if (bytes == SerializableBoolean.trueByte) true else if (bytes == SerializableBoolean.falseByte) false else false)

  def toShort(): Short = toShort(ByteOrder.BIG_ENDIAN)

  def toInt(): Int = toInt(ByteOrder.BIG_ENDIAN)

  def toLong(): Long = toLong(ByteOrder.BIG_ENDIAN)

  def toFloat(): Float = toFloat(ByteOrder.BIG_ENDIAN)

  def toDouble(): Double = toDouble(ByteOrder.BIG_ENDIAN)

  def toShort(order: ByteOrder): Short = ByteBuffer.wrap(SerializableShort.adjust(bytes).toArray).order(order).getShort

  def toInt(order: ByteOrder): Int = ByteBuffer.wrap(SerializableInt.adjust(bytes).toArray).order(order).getInt

  def toLong(order: ByteOrder): Long = ByteBuffer.wrap(SerializableByte.adjust(bytes).toArray).order(order).getLong

  def toFloat(order: ByteOrder): Float = ByteBuffer.wrap(SerializableFloat.adjust(bytes).toArray).order(order).getFloat

  def toDouble(order: ByteOrder): Double = ByteBuffer.wrap(SerializableDouble.adjust(bytes).toArray).order(order).getDouble

  def toChar: Char = bytes(0).toChar

  override def toString: String = bytes.collect { case b => {
    b
  }.toChar
  }.mkString
}


case object SerializableByte {
  val byteSize = 1

  def adjust(array: Array[Byte]): Array[Byte] = Array(array(0))
}

case class SerializableByte(myByte: Byte) extends Serializable[Byte] {
  override def toBytes(order: ByteOrder): Array[Byte] = {
    return Array(myByte)
  }

  override def toDouble: Double = myByte.toDouble

  override val byteSize = SerializableByte.byteSize
}


case object SerializableUnit {
  val byteSize = 0

  def adjust(array: Array[Byte]): Array[Byte] = throw new java.lang.UnsupportedOperationException("Cannot serialize Unit (void) type")
}

case class SerializableUnit(void: Unit) extends Serializable[Unit] {
  override def toBytes(order: ByteOrder): Array[Byte] = throw new java.lang.UnsupportedOperationException("Cannot serialize Unit (void) type")

  override def toDouble: Double = throw new java.lang.UnsupportedOperationException("Cannot convert Unit (void) to Double")

  override val byteSize = SerializableUnit.byteSize
}


object SerializableBoolean {
  val byteSize = 1

  def adjust(array: Array[Byte]): Array[Byte] = Array(array(0))

  val trueByte = Array(1.toByte)
  val falseByte = Array(0.toByte)
}

case class SerializableBoolean(myBoolean: Boolean) extends Serializable[Boolean] {
  override def toBytes(order: ByteOrder): Array[Byte] = {
    return (if (myBoolean) SerializableBoolean.trueByte else SerializableBoolean.falseByte)
  }

  override def toDouble: Double = if (myBoolean) 1 else 0

  override def toBoolean: Boolean = myBoolean

  override val byteSize = SerializableBoolean.byteSize
}


object SerializableShort {
  val byteSize = 2

  def adjust(array: Array[Byte]): Array[Byte] = Array(array(0), array(1))
}

case class SerializableShort(myShort: Short) extends Serializable[Short] {
  override def toBytes(order: ByteOrder): Array[Byte] = {
    //println("Short.toBytes = " + ByteBuffer.allocate(byteSize).putShort(myShort).array().mkString("[", ", ", "]"))
    return ByteBuffer.allocate(byteSize).order(order).putShort(myShort).array()
  }

  override def toDouble: Double = myShort.toDouble

  override val byteSize = SerializableShort.byteSize
}


object SerializableInt {
  val byteSize = 4

  def adjust(array: Array[Byte]): Array[Byte] = Array(array(0), array(1), array(2), array(3))
}

case class SerializableInt(myInt: Int) extends Serializable[Int] {
  override def toBytes(order: ByteOrder): Array[Byte] = {
    return ByteBuffer.allocate(byteSize).order(order).putInt(myInt).array()
  }

  override def toDouble: Double = myInt.toDouble

  override val byteSize = SerializableInt.byteSize
}


object SerializableLong {
  val byteSize = 4

  def adjust(array: Array[Byte]): Array[Byte] = Array(array(0), array(1), array(2), array(3))
}

case class SerializableLong(myLong: Long) extends Serializable[Long] {
  override def toBytes(order: ByteOrder): Array[Byte] = {
    return ByteBuffer.allocate(byteSize).order(order).putLong(myLong).array()
  }

  override def toDouble: Double = myLong.toDouble

  override val byteSize = SerializableLong.byteSize
}


object SerializableFloat {
  val byteSize = 4

  def adjust(array: Array[Byte]): Array[Byte] = Array(array(0), array(1), array(2), array(3))
}

case class SerializableFloat(myFloat: Float) extends Serializable[Float] {
  override def toBytes(order: ByteOrder): Array[Byte] = {
    return ByteBuffer.allocate(byteSize).order(order).putFloat(myFloat).array()
  }

  override def toDouble: Double = myFloat.toDouble

  override val byteSize = SerializableFloat.byteSize
}


object SerializableDouble {
  val byteSize = 8

  def adjust(array: Array[Byte]): Array[Byte] = array
}

case class SerializableDouble(myDouble: Double) extends Serializable[Double] {
  override def toBytes(order: ByteOrder): Array[Byte] = {
    return ByteBuffer.allocate(byteSize).order(order).putDouble(myDouble).array()
  }

  override def toDouble: Double = myDouble

  override val byteSize = SerializableDouble.byteSize
}


object SerializableChar {
  val byteSize = 1

  def adjust(array: Array[Byte]): Array[Byte] = Array(array(0))
}

case class SerializableChar(myChar: Char) extends Serializable[Char] {
  override def toBytes(order: ByteOrder): Array[Byte] = {
    return Array(myChar.toByte)
  }

  override def toDouble: Double = myChar.toDouble

  override val byteSize = SerializableChar.byteSize
}


object SerializableString {
  val byteSize = 8

  def adjust(array: Array[Byte]): Array[Byte] = array
}

case class SerializableString(myString: String) extends DynamicSerializable[String] {
  override def toBytes(order: ByteOrder): Array[Byte] = {
    val buffer = new Array[Byte](byteSize)
    Array.copy(toVarBytes, 0, buffer, 0, Math.min(byteSize, dynamicByteSize))
    return buffer
  }

  override def toDouble: Double = toBytes().toDouble() //TODO: not the right way to do...

  def toVarBytes: Array[Byte] = myString.toCharArray.collect { case c => c.toByte}.toArray

  override val byteSize = SerializableString.byteSize
  override val dynamicByteSize = myString.length
}