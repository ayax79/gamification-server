package com.jivesoftware.gamification.user.request

import com.jivesoftware.gamification.request.{GamificationResponse, ResponseCode, GamificationRequest}
import java.util.UUID
import org.joda.time.DateTime
import org.json4s.JsonAST.JValue
import com.jivesoftware.gamification.util.{DateTimeUtil, UUIDUtil}
import org.json4s.JsonDSL._
import UUIDUtil._
import DateTimeUtil._

case class LoginRequest(asyncToken: UUID, apiKey: String, sig: String, ts: DateTime) extends GamificationRequest {
  def toJson =
    ("method" -> LoginRequest.method) ~
      ("asyncToken" -> asyncToken.toString) ~
      ("apiKey" -> apiKey) ~
      ("sig" -> sig) ~
      ("ts" -> ts.toNitroString)
}

object LoginRequest {

  val method = "user.login"

  def apply(map: Map[String, String]): Option[LoginRequest] =
    for {
      asyncToken <- asUUID(map.get("asyncToken"))
      apiKey <- map.get("apiKey")
      sig <- map.get("sig")
      tsString <- map.get("ts")
      ts <- DateTimeUtil.fromNitroTimeStamp(tsString)
    } yield LoginRequest(asyncToken, apiKey, sig, ts)

}

case class LoginResponse(code: ResponseCode, sessionKey: String, request: LoginRequest) extends GamificationResponse {

  def toJson = {
    val json:JValue = ("Login" -> ("sessionKey" -> sessionKey)) ~
      ("res" -> code.code)
    json ++ request.toJson
  }

}