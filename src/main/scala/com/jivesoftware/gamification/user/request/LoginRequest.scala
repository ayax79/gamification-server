package com.jivesoftware.gamification.user.request

import com.jivesoftware.gamification.request.{GamificationResponse, ResponseCode, GamificationRequest}
import java.util.UUID
import org.joda.time.DateTime
import org.json4s.JsonAST.JObject
import com.jivesoftware.gamification.util.{DateTimeUtil, UUIDUtil}
import org.json4s.JsonDSL._
import UUIDUtil._
import DateTimeUtil._

case class LoginRequest(asyncToken: UUID, apiKey: String, sig: String, ts: DateTime) extends GamificationRequest {
  def toJson: JObject =
    ("Login" ->
      ("asyncToken" -> asyncToken.toString) ~
      ("apiKey" -> apiKey) ~
      ("sig" -> sig) ~
      ("ts" -> toNitroTimeStamp(ts)))
}
object LoginRequest {

  def apply(map: Map[String, String]): Option[LoginRequest] =
    for {
      asyncToken <- asUUID(map("asyncToken"))
      apiKey <- map.get("apiKey")
      sig <- map.get("sig")
      ts <- DateTimeUtil.fromNitroTimeStamp(map("ts"))
    } yield LoginRequest(asyncToken, apiKey, sig, ts)

}

object LoginResponse {

  def apply(code: ResponseCode, sessionKey: String, req: LoginRequest) =
    new LoginResponse(code, sessionKey, req.asyncToken, req.apiKey, req.sig, req.ts)

}
case class LoginResponse(code: ResponseCode, sessionKey: String, asyncToken: UUID, apiKey: String, sig: String, ts: DateTime) extends GamificationResponse {

  def toJson: JObject =
    ("Login" ->
      ("res" -> code.code) ~
        ("sessionKey" -> sessionKey) ~
        ("asyncToken" -> asyncToken.toString) ~
        ("apiKey" -> apiKey) ~
        ("sig" -> sig) ~
        ("ts" -> toNitroTimeStamp(ts)))

}




