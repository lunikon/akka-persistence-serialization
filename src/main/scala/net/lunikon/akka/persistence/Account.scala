package net.lunikon.akka.persistence

import akka.actor._
import akka.persistence._

import net.lunikon.akka.persistence.serialization.EventFormats._
import net.lunikon.akka.persistence.serialization.StateFormats._

case class Book(amount: Long)
case class Booked(amount: Long) extends Event

case class Account(balance: Long = 0) extends State {
  def updated(booked: Booked): Account = copy(balance = balance + booked.amount)
}

class AccountActor(number: Int) extends PersistentActor with ActorLogging {
  override def persistenceId = "account-" + number

  var state = Account()

  def updateState(event: Booked): Unit = {
    log.info("[{}] Booking amount: {}", number, event.amount)
    state = state.updated(event)
  }

  val receiveRecover: Receive = {
    case evt: Booked => updateState(evt)
    case SnapshotOffer(_, snapshot: Account) => {
      state = snapshot
      log.info("[{}] Recovered from snapshot.", number)
    }
  }

  val receiveCommand: Receive = {
    case Book(amount) =>
      persist(Booked(amount))(updateState)
    case "snap" => {
      log.info("[{}] Taking snapshot.", number)
      saveSnapshot(state)
    }
    case SaveSnapshotSuccess(metadata) => log.info("[{}] Snapshot saved successfully.", number)
    case SaveSnapshotFailure(metadata, reason) => log.info("[{}] Saving snapshot failed: {}", number, reason)
    case "print" => log.info("[{}] Current balance: {}", number, state.balance)
  }

}

object Account {

  ProtobufAdapter.register(classOf[Booked], new TypedProtobufAdapter[Booked] {
    def getBinary(obj: Booked): Array[Byte] = {
      val builder = BookedMessage.newBuilder
      builder.setAmount(obj.amount)
      builder.build().toByteArray
    }
    def fromBinary(bytes: Array[Byte]): Booked = {
      Booked(BookedMessage.parseFrom(bytes).getAmount())
    }
  })

  ProtobufAdapter.register(classOf[Account], new TypedProtobufAdapter[Account] {
    def getBinary(obj: Account): Array[Byte] = {
      val builder = AccountMessage.newBuilder
      builder.setBalance(obj.balance)
      builder.build().toByteArray
    }
    def fromBinary(bytes: Array[Byte]): Account = {
      Account(AccountMessage.parseFrom(bytes).getBalance())
    }
  })

}

