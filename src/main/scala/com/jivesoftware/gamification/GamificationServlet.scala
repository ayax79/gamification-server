package com.jivesoftware.gamification

import org.scalatra._
import scalate.ScalateSupport

import org.json4s.JsonAST._
import org.json4s.JsonDSL._
import org.slf4j.LoggerFactory
import org.json4s.JsonAST
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonAST.JArray
import com.jivesoftware.gamification.request.GamificationRequest

class GamificationServlet extends GamificationserverStack {

  private val log = LoggerFactory.getLogger(classOf[GamificationServlet])

  get("/") {
    val result = for {
      method <- Option(params("method"))
      if (method == "batch.run")
      feed <- Option(params("methodFeed"))
    } yield {
      log.info("feed: " + feed)

      val requests = GamificationRequest.parseRequests(feed)
      log.info("requests size: " + requests.size)
      requests.foreach(r => log.info("Found request: " + r))

      //todo later generate a real result
      feed
    }
    log.info("ending request")
    ("success" -> "true")
  }


}







