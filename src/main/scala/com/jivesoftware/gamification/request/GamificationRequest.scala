package com.jivesoftware.gamification.request

import org.json4s.JsonAST.{JObject, JString, JField, JArray}
import java.util.UUID
import com.jivesoftware.gamification.request.user.LoginRequest
import org.slf4j.LoggerFactory

object GamificationRequest {

  private val log = LoggerFactory.getLogger(classOf[GamificationRequest])

  import org.json4s.jackson.JsonMethods._

  def parseRequests(feed: String): List[GamificationRequest] = parseFeed(feed).map(requests).getOrElse(Nil)

  /**
   * If parsing the json returns a Json array return an option of that else None
   */
  protected[request] def parseFeed(s: String):Option[JArray] = parse(s) match {
    case arr: JArray => Option(arr)
    case _ => None
  }

  /**
   * go through each item in the json array and create a GamificationRequest from it
   */
  protected[request] def requests(arr: JArray): List[GamificationRequest] = {
    val requests: List[Option[GamificationRequest]] = for {
      JString(content) <- arr
      req = parseRequest(content) // parse the url encoded content for the batch request
      method <- req.get("method") // grab the method from the map
    } yield  request(method, req)
    requests.flatten
  }

  /**
   * Takes the string from the json array which is the method request in key values pairs separated by
   * ampersands.
   */
  protected[request] def parseRequest(s: String): Map[String, String] =
    s.split("&").foldLeft(Map.empty[String, String]) {
      (acc, cur) =>
        val tokens = cur.split("=")
        acc + (tokens(0) -> tokens(1))
    }


  /**
   * Builds the request of the method
   */
  protected[request] def request(method: String, request: Map[String, String]): Option[GamificationRequest] = method match {
    case "user.login" =>
      LoginRequest(request).asInstanceOf[Option[GamificationRequest]]
    case _ =>
      log.warn("Unmatched method found: " + method)
      None // did not match a known type
  }


}
trait GamificationRequest {
  val asyncToken:UUID
  val apiKey:String
}
trait GamificationResponse {
  val code: ResponseCode

  def toJson: JObject
}



