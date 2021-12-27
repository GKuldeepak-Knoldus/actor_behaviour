package com.knoldus.bootstrap

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.knoldus.models.Models._
import com.knoldus.statefull.SellerStateful
import com.knoldus.stateless.SellerStateless

object Application extends App{

  class Buyer extends Actor with ActorLogging {
    override def receive: Receive = {
      case startTransaction(sellerRef) =>
        log.info("[Info] Starting Transaction")
        sellerRef ! RequestOrder(Order("123", "Scala", 2))
        sellerRef ! Status
        sellerRef ! RequestOrder(Order("1234", "Java", 1))
        sellerRef ! PaymentRequest(Order("123", "Scala", 2), "cardNumber", 20L)

      case Status(OrderAccept) =>
        log.info("Order accepted")

      case Status(OrderReject) =>
        log.info("Oder Rejected")

      case _ =>
        log.info("[Oops] System Failure")
    }
  }

  val actorSystem = ActorSystem("ActorBehaviour")
  val statelessActor = actorSystem.actorOf(Props[SellerStateless])
  val statefulActor = actorSystem.actorOf(Props[SellerStateful])
  val buyer = actorSystem.actorOf(Props[Buyer])

  buyer ! startTransaction(statelessActor)

//  actorSystem.terminate()

}