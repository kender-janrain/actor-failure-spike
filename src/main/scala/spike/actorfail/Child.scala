package spike.actorfail

import scala.concurrent.duration._
import akka.actor.{ActorRef, ActorLogging, Actor}

class Child(workers: ActorRef) extends Actor with ActorLogging {
  import Protocol._
  import context.dispatcher

  override def preStart() = {
    log.debug(s"Child.preStart(): $self")
    context.system.scheduler.schedule(1.seconds, 2.seconds, self, Fail)
  }

  override def preRestart(reason: Throwable, message: Option[Any]) = {
    log.debug(s"Child.preRestart($reason, $message): $self")
    super.preRestart(reason, message)
  }

  override def postRestart(reason: Throwable) = {
    log.debug(s"Child.postRestart($reason): $self")
    super.postRestart(reason)
  }

  override def receive = {
    case Fail ⇒
      workers ! Fail
  }
}
