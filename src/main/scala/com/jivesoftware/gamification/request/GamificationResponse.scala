package com.jivesoftware.gamification.request

import org.json4s.JsonAST.{JValue, JObject}
import org.json4s.JsonDSL._
import com.jivesoftware.gamification.util.Jsonable


trait GamificationResponse extends Jsonable {
  val code: ResponseCode
}

object GamificationResponse {

  def toJson(responses: List[Either[UnhandledRequest, GamificationResponse]]): JValue =
    ("Gamification" ->
      ("res" -> ResponseCode.OK.code) ~
        ("method" -> "batch.run") ~
        ("Gamification" -> responses.map {
          case Left(l) => l.toJson
          case Right(r) => r.toJson
        }))

}