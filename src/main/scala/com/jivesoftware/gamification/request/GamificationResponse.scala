package com.jivesoftware.gamification.request

import org.json4s.JsonAST.{JArray, JValue, JObject}
import org.json4s.JsonDSL._
import com.jivesoftware.gamification.util.Jsonable


trait GamificationResponse extends Jsonable {
  val code: ResponseCode
}

object GamificationResponse {

  def toJson(responses: List[Either[UnhandledRequest, GamificationResponse]]): JValue = {

    val arr: JArray =
      responses.map {
        case Left(l) => l.toJson
        case Right(r) => r.toJson
      }

    ("Nitro" ->
      ("res" -> ResponseCode.OK.code) ~
        ("method" -> "batch.run") ~
        ("Nitro" -> arr))
  }

}