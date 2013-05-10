package com.jivesoftware.gamification.util

import org.json4s.JsonAST.JValue

trait Jsonable {

  def toJson: JValue

}
