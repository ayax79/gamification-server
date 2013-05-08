package com.jivesoftware.gamification

import org.scalatra._
import scalate.ScalateSupport

import org.json4s.JsonAST._
import org.json4s.JsonDSL._

class GamificationServlet extends GamificationserverStack {

  get("/") {
    ("success" -> "true")
  }

}
