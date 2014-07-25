package net.lunikon.akka.persistence

object ProtobufAdapter {

  private val adapters: scala.collection.mutable.Map[Class[_], ProtobufAdapter] =
    scala.collection.mutable.Map()

  def register[T](clazz: Class[T], adapter: ProtobufAdapter): Unit = {
    adapters += (clazz -> adapter)
  }

  def get(clazz: Class[_]) = adapters.get(clazz)

}

trait ProtobufAdapter {
  def toBinary(obj: AnyRef): Array[Byte]
  def fromBinary(bytes: Array[Byte]): AnyRef
}

trait TypedProtobufAdapter[T <: AnyRef] extends ProtobufAdapter {
  final def toBinary(obj: AnyRef): Array[Byte] = getBinary(obj.asInstanceOf[T])
  def getBinary(obj: T): Array[Byte]

  def fromBinary(bytes: Array[Byte]): T
}