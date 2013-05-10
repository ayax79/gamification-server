package com.jivesoftware.gamification.dispatcher

import com.jivesoftware.gamification.request.{GamificationResponse, GamificationRequest}

trait RequestHandler[-REQUEST, +RESPONSE] {

  def handler(request: REQUEST): RESPONSE

}
