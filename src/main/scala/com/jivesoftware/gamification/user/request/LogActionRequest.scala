package com.jivesoftware.gamification.user.request

import com.jivesoftware.gamification.request.{GamificationResponse, ResponseCode, GamificationRequest}
import java.util.UUID
import org.json4s.JsonAST.JValue
import org.json4s.JsonDSL._
import com.jivesoftware.gamification.util.UUIDUtil
import com.jivesoftware.gamification.util.MapUtil._


case class LogActionRequest(asyncToken: UUID,
                            sessionKey: String,
                            tags: List[String],
                            metaData: Map[String, String],
                            userId: Option[String],
                            value: Option[Int],
                            newsfeed: Option[String],
                            target: Option[String]) extends GamificationRequest {


  def toJson: JValue =
    ("method" -> LogActionRequest.method) ~
      ("tags" -> tagString) ~
      ("metadata" -> metaDataString) ~
      ("userId" -> userId) ~
      ("value" -> value) ~
      ("newsfeed" -> newsfeed) ~
      ("target" -> target)

  def tagString = tags.reduceLeft(_ + "," + _)

  def metaDataString = metaData.asCommaString
}

object LogActionRequest {

  val method = "user.loginAction"

  def apply(map: Map[String, String]): Option[LogActionRequest] =
    for {
      asyncToken <- UUIDUtil.asUUID(map.get("asyncToken"))
      sessionKey <- map.get("sessionKey")
      tagString <- map.get("tags")
      tags = tagString.split(",").toList.filter(_ != "")
      metaData = parseCommaString(map.get("metadata").getOrElse(""))
      userId = map.get("userId")
      value = map.get("value").map(_.toInt)
      newsfeed = map.get("newsfeed")
      target = map.get("target")
    } yield new LogActionRequest(asyncToken, sessionKey, tags, metaData, userId, value, newsfeed, target)

}

case class LogActionResponse(code: ResponseCode,
                             request: LoginRequest) extends GamificationResponse {
  def toJson = {
    val json: JValue = ("code" -> code.code)
    json ++ request.toJson
  }
}




