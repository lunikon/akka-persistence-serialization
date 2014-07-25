package net.lunikon.akka.persistence

import akka.actor.ExtendedActorSystem
import akka.serialization.Serializer

/**
 * Marker traits for all protobuf-serializable events and states.
 */
trait ProtobufSerializable extends java.io.Serializable
trait Event extends ProtobufSerializable
trait State extends ProtobufSerializable

class ProtobufSerializer(val system: ExtendedActorSystem) extends Serializer {

  def identifier: Int = 42
  def includeManifest: Boolean = true

  val BookedClass = classOf[Booked]

  def toBinary(obj: AnyRef): Array[Byte] = ProtobufAdapter.get(obj.getClass) match {
    case Some(adapter) => adapter.toBinary(obj)
    case _ => throw new IllegalArgumentException(s"No adapter defined for class ${obj.getClass}")
  }

  def fromBinary(bytes: Array[Byte], manifest: Option[Class[_]]): AnyRef = manifest match {
    case None => throw new IllegalArgumentException(s"Can't deserialize object, no manifest provided!")
    case Some(c) => ProtobufAdapter.get(c) match {
      case Some(adapter) => adapter.fromBinary(bytes)
      case _ => throw new IllegalArgumentException(s"Can't deserialize object of type ${c}")
    }
  }

}