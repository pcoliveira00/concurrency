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

    val ticker  = context.system.scheduler.schedule(100.millis, 100.millis, self, Tick)

    case object Tick 

    def receive = {
        case RateChange(amount) => 
            rateOfClimb = amount.min(1.0f).max(-1.0f) * maxRateOfClimb
            log info(s"Altimeter changed rate of climb to $rateOfClimb")
        
        case Tick => {
            val tick = System.concurrentTimeMillis
            altitude = altitude + ((tick - lastTick)) / 60000.0) * rateOfClimb
            lastTick = tick
        }
    }

    override def postStop(): Unit = ticker.cancel
}