package spike.actorfail

import akka.actor._
import akka.routing.RoundRobinRouter

class Parent extends Actor with ActorLogging {
  import Protocol._

  var child: Option[ActorRef] = None

  override def preStart() = {
    log.debug(s"Parent.preStart(): $self")
  }

  override def preRestart(reason: Throwable, message: Option[Any]) = {
    log.debug(s"Parent.preRestart($reason, $message): $self")
    super.preRestart(reason, message)
  }

  override def postRestart(reason: Throwable) = {
    log.debug(s"Parent.postRestart($reason): $self")
    super.postRestart(reason)
  }

  def receive = {
    case Start ⇒
      val workers = context.actorOf(Props[Worker].withRouter(RoundRobinRouter(2)), "worker")
      child = Option(context.actorOf(Props(classOf[Child], workers), "child"))
      sender ! Started
  }
}
