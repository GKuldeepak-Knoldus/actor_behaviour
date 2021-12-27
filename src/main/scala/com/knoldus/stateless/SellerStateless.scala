package com.knoldus.stateless

import akka.actor.{Actor, ActorLogging}
import com.knoldus.models.Models._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class SellerStateless extends Actor with ActorLogging{

  override def receive: Receive = startTransaction

  def startTransaction: Receive = {

    case RequestOrder(order: Order) =>
      log.info(s"[Alert] Order accepted ${order}")
      context.become(inProgress, false)

    case Status(_) => sender() ! Status(OrderAccept)
  }

  def inProgress: Receive = {

    case RequestOrder(_) =>
      log.info(s"[Info] Confirm the Payment for last order!!")

    case PaymentRequest(order, _, amount) =>
      log.info(s"Payment Received ${amount}")
      Future(processOrder(order))
      ReadyForNextOrder

    case ReadyForNextOrder =>
      log.info("Seller is Ready For Next Order")
      context.unbecome()

    case Status(_) => sender() ! Status(InProgress)
  }

  private def processOrder(order: Order) = {
    log.info(s"[Info] Order In Process ${order}")
    log.info(s"[Ack] Order with orderId ${order.OrderId} is ready!")
  }
}

