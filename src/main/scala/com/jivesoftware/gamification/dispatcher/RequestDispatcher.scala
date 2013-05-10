package com.jivesoftware.gamification.dispatcher

import com.jivesoftware.gamification.request.{GamificationRequest, GamificationResponse}

// eventually this needs to be a little more robust.... but for now.
trait RequestDispatcher {

  val handlers: Map[Class[_], RequestHandler[_ >: GamificationRequest, _]]

  def dispatch[T <: GamificationRequest](request: T): Either[UnhandledRequest, _ >: GamificationResponse] =
    handlers.get(request.getClass).map( d => d.handler(request)) match {
      case Some(response) => Right(response)
      case None => Left(UnhandledRequest(request))
    }

}

/**
 * If we couldn't handle a request we just return the request back unhandled in this wrapper
 */
case class UnhandledRequest(request: GamificationRequest)
