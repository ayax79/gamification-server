package com.jivesoftware.gamification.dispatcher

import com.jivesoftware.gamification.request.{UnhandledRequest, GamificationRequest, GamificationResponse}

// eventually this needs to be a little more robust.... but for now.
// I am thinking I am eventually going to replace this with apache camel
trait RequestDispatcher {

  val handlers: Map[Class[_], RequestHandler[_ >: GamificationRequest, _]]

  def dispatch[T <: GamificationRequest](request: T): Either[UnhandledRequest, GamificationResponse] =
    handlers.get(request.getClass).map( d => d.handler(request)) match {
      case Some(response) => Right(response.asInstanceOf[GamificationResponse])
      case None => Left(UnhandledRequest(request))
    }

}


