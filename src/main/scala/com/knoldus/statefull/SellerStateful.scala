package com.knoldus.statefull

import akka.actor.{Actor, ActorLogging}
import com.knoldus.models.Models._

class SellerStateful extends Actor with ActorLogging{

  var status = OrderAccept.toString

  override def receive: Receive = {
    case RequestOrder(order) =>
      log.info(s"[Alert] Accepting order with orderId ${order.OrderId}")
      status = OrderReject.toString
      Thread.sleep(5000)
      ReadyForNextOrder

    case ReadyForNextOrder => status = OrderAccept
    case Status =>
      if (status == OrderAccept) sender() ! OrderAccept
      else sender() ! OrderReject
  }
}
