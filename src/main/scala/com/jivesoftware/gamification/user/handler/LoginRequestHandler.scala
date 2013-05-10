package com.jivesoftware.gamification.user.handler

import com.jivesoftware.gamification.dispatcher.RequestHandler
import com.jivesoftware.gamification.request.{ResponseCode, GamificationRequest}
import com.jivesoftware.gamification.user.request.{LoginRequest, LoginResponse}
import java.util.UUID

class LoginRequestHandler extends RequestHandler[LoginRequest, LoginResponse] {

  def handler(request: LoginRequest): LoginResponse =
    LoginResponse(ResponseCode.OK, buildSessionKey(request), request)


  // for the time being let's just use UUID's as a session key... also we won't really be checking them
  protected def buildSessionKey(request: GamificationRequest):String = UUID.randomUUID().toString

}
