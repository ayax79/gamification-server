package com.jivesoftware.gamification

import org.scalatra._
import scalate.ScalateSupport

import org.json4s.JsonAST._
import org.json4s.JsonDSL._
import org.slf4j.LoggerFactory
import org.json4s.JsonAST
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonAST.JArray
import com.jivesoftware.gamification.request.{ErrorResponse, ResponseCode, GamificationResponse, GamificationRequest}
import com.jivesoftware.gamification.dispatcher.RequestDispatcher

class GamificationServlet extends GamificationServerStack {
  self: RequestDispatcher =>

  private val log = LoggerFactory.getLogger(classOf[GamificationServlet])

  get("/") {
    try {
      val result = for {
        method <- params.get("method")
        if (method == "batch.run")
        feed <- params.get("methodFeed")
      } yield {
        log.info("feed: " + feed)
        GamificationRequest.parseRequests(feed).map(dispatch)
      }
      GamificationResponse.toJson(result.getOrElse(Nil))
    }
    catch {
      case t: Throwable =>
        log.error("An error occurred: " + t.getMessage, t)
        ErrorResponse(ResponseCode.SERVER_ERROR, t.getMessage)
    }
  }

}







