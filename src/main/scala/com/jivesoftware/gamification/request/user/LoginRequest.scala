package com.jivesoftware.gamification.request.user

import com.jivesoftware.gamification.request.GamificationRequest
import java.util.UUID
import org.joda.time.DateTime
import org.json4s.JsonAST.{JString, JObject}
import com.jivesoftware.gamification.util.{DateTimeUtil, UUIDUtil}

case class LoginRequest(asyncToken: UUID, requestID: UUID, apiKey: String, sig: String, ts: DateTime) extends GamificationRequest
object LoginRequest {

  def apply(map: Map[String, String]): Option[LoginRequest] =
    for {
      asyncToken <- UUIDUtil.asUUID(map("asyncToken"))
      requestID <- UUIDUtil.asUUID(map("requestID"))
      apiKey <- map.get("apiKey")
      sig <- map.get("sig")
      ts <- DateTimeUtil.asNitroTimeStamp(map("ts"))
    } yield LoginRequest(asyncToken, requestID, apiKey, sig, ts)

}