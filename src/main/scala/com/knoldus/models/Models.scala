package com.knoldus.models

import akka.actor.ActorRef

object Models {

  case class startTransaction(sellerRef: ActorRef)

  case class Order(OrderId: String, ItemName: String, Quantity: Int)

  case class RequestOrder(order: Order)

  case class Status(message: String)

  case class PaymentRequest(order: Order, cardNumber: String, amount: Long)

  val OrderAccept = "Accept Order"
  val OrderReject = "Reject Order"

  case object ReadyForNextOrder

  val InProgress = "In Progress"
  val Accepted = "Accepted"

}
