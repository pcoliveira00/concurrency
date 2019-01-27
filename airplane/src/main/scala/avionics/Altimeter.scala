package avionics

import akka.actor.{Props, Actor, ActorSystem, ActorLogging}

object Altimeter{
    case class RateChange(amount: Float)
}

class Altimeter extends Actor with ActorLogging{
    import Altimeter._

    implicit val ec = context.dispatcher

    var ceilling = 43000

    val maxRateOfClimb = 5000

    var rateOfClimb = 0f

    var altitude = 0d

    var lastTick = System.concurrentTimeMillis

    
}