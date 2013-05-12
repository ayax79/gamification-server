package com.jivesoftware.gamification.request

import org.json4s.JsonAST.{JString, JArray}
import java.util.UUID
import org.slf4j.LoggerFactory
import com.jivesoftware.gamification.user.request.{LogActionRequest, LoginRequest}
import com.jivesoftware.gamification.util.Jsonable
import com.jivesoftware.gamification.util.MapUtil.parseQueryString

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
      req = parseQueryString(content) // parse the url encoded content for the batch request
      method <- req.get("method") // grab the method from the map
    } yield  request(method, req)
    println("------- requests " + requests)


    val flat = requests.flatten
    println(s"----- Found ${flat.size} requests")
    flat
  }


  /**
   * Builds the request of the method
   */
  protected[request] def request(method: String, request: Map[String, String]): Option[_ <: GamificationRequest] = method match {
    case LoginRequest.method => LoginRequest(request)
    case LogActionRequest.method => LogActionRequest(request)
    case _ =>
      log.warn("Unmatched method found: " + method)
      None // did not match a known type
  }


}
trait GamificationRequest extends Jsonable {
  val asyncToken:UUID
}



