package spike.actorfail

import akka.actor.{ActorLogging, Actor}

class Worker extends Actor with ActorLogging {
  import Protocol._

  override def preStart() = {
    log.debug(s"Worker.preStart(): $self")
  }

  override def preRestart(reason: Throwable, message: Option[Any]) = {
    log.debug(s"Worker.preRestart($reason, $message): $self")
    super.preRestart(reason, message)
  }


  override def postRestart(reason: Throwable) = {
    log.debug(s"Worker.postRestart($reason): $self")
    super.postRestart(reason)
  }

  override def receive = {
    case Fail ⇒
      sys.error("x.x")
  }
}
