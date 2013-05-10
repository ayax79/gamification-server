package com.jivesoftware.gamification.dispatcher

import com.jivesoftware.gamification.request.{GamificationRequest, GamificationResponse}

// eventually this needs to be a little more robust.... but for now.
trait RequestDispatcher {

  val handlers: Map[Class[ _ <: GamificationRequest], (GamificationRequest => GamificationResponse)]

  def dispatch(request: GamificationRequest): Either[UnhandledRequest, GamificationResponse] =
    handlers.get(request.getClass).map(_(request)) match {
      case Some(response) => Right(response)
      case None => Left(UnhandledRequest(request))
    }

}

/**
 * If we couldn't handle a request we just return the request back unhandled in this wrapper
 */
case class UnhandledRequest(request: GamificationRequest)
