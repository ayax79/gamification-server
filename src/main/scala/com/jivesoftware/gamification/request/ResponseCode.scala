package com.jivesoftware.gamification.request

sealed case class ResponseCode(code: String)
object ResponseCode {
  object OK extends ResponseCode("ok")
  object INVALID_REQUEST extends ResponseCode("113")
  object SERVER_ERROR extends ResponseCode("500")
}
