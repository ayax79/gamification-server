package com.jivesoftware.gamification.request

import java.util.UUID
import org.slf4j.LoggerFactory
import com.jivesoftware.gamification.user.request.{LogActionRequest, LoginRequest}
import com.jivesoftware.gamification.util.Jsonable
import com.jivesoftware.gamification.util.MapUtil.parseQueryString

object GamificationRequest {

  private val log = LoggerFactory.getLogger(classOf[GamificationRequest])

  def parseRequests(feed: String): List[GamificationRequest] = parseFeed(feed).map {
    s =>
      val clean = removeMethodString(s)
      val mapped: Map[String, String] = parseQueryString(clean)
      mapped.get("method").flatMap(m => request(m, mapped))
  }.flatten

  /**
   * If parsing the json returns a Json array return an option of that else None
   */
  protected[request] def parseFeed(s: String):List[String] =
    s.replaceAll("\\s", "").split(",\"method=").toList.map(removeMethodString)

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


  protected[request] def removeMethodString(s: String) =
    s.trim().replaceAll("""(^\[method=|^method=|]$)""", "")


}
trait GamificationRequest extends Jsonable {
  val asyncToken:UUID
}



