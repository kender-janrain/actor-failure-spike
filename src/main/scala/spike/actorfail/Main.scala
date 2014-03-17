package spike.actorfail

import scala.concurrent.duration._
import akka.actor.{Props, ActorSystem}

object Main extends App {
  import Protocol._
  import akka.pattern.ask

  implicit val actorSystem = ActorSystem("FailSpike")
  import actorSystem.dispatcher

  val parentRef = actorSystem.actorOf(Props(classOf[Parent]), "parent")
  parentRef.ask(Start)(1.minute) map { r ⇒
    println("started.")
  }

  actorSystem.awaitTermination()
}
