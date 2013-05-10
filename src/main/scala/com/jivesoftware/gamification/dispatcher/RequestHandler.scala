package com.jivesoftware.gamification.dispatcher

import com.jivesoftware.gamification.request.{GamificationResponse, GamificationRequest}

trait RequestHandler {

  def handler(request: GamificationRequest): GamificationResponse

}
