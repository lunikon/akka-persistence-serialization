package net.lunikon.akka.persistence

import akka.actor._

object PersistentActorExample extends App {

  val system = ActorSystem("example")
  val persistentActor = system.actorOf(Props(classOf[AccountActor], 1), "account-1")

  persistentActor ! "print"
  persistentActor ! Book(100)
  persistentActor ! Book(-15)
  persistentActor ! Book(50)
  persistentActor ! "snap"
  persistentActor ! Book(-30)
  persistentActor ! "print"

  Thread.sleep(1000)
  system.shutdown()
}
