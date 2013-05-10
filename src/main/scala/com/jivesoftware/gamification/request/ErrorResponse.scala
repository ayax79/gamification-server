package com.jivesoftware.gamification.request

import org.json4s.JsonAST.JObject
import org.json4s.JsonDSL._

case class ErrorResponse(code: ResponseCode, msg: String) extends GamificationResponse {
  def toJson =
    ("res" -> code.code) ~
    ("msg" -> msg)
}
