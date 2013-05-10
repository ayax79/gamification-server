package com.jivesoftware.gamification.request

import com.jivesoftware.gamification.util.Jsonable
import org.json4s.JsonDSL._

/**
 * If we couldn't handle a request we just return the request back unhandled in this wrapper
 */
case class UnhandledRequest(request: GamificationRequest) extends Jsonable {

  def toJson =
    ("res" ->  ResponseCode.INVALID_REQUEST.code) ~
    ("request" -> request.toJson)


}
