package com.jivesoftware.gamification.user.handler

import com.jivesoftware.gamification.dispatcher.RequestHandler
import com.jivesoftware.gamification.user.request.{LogActionResponse, LogActionRequest}
import com.jivesoftware.gamification.request.ResponseCode
import org.slf4j.LoggerFactory

class LogActionRequestHandler extends RequestHandler[LogActionRequest, LogActionResponse] {

  private val log = LoggerFactory.getLogger(classOf[LogActionRequestHandler])

  def handler(request: LogActionRequest): LogActionResponse = {
    // eventually this should just send stuff to a queue.
    log.info("Logging a new action request " + request.toString)
    LogActionResponse(ResponseCode.OK, request)
  }
}
