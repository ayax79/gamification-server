package com.jivesoftware.gamification.request

sealed case class ResponseCode(code: String)
object ResponseCode {
  object OK extends ResponseCode("ok")
}
