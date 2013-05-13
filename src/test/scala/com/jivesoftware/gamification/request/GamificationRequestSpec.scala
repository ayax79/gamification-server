package com.jivesoftware.gamification.request

import org.specs2.mutable.Specification

import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods.parse

class GamificationRequestSpec extends Specification {

  val requests =
    """
      |"[method=user.login&asyncToken=32288f39-d33b-4283-8757-796ca1585502&userId=1&apiKey=3fc2b0c43407479088ceb8891565569f&ts=1368323427&sig=16c050403ec11cb63eec724b12fc0272",
      |"method=user.logAction&asyncToken=c80c020a-f248-4300-9a36-12a428ac0b7f&tags=LikeEvent-LIKED-streamEntry,id:1015,type:streamEntry,requestID:c80c020a-f248-4300-9a36-12a428ac0b7f&userId=2014&metadata=id:1015,type:streamEntry",
      |"method=user.logAction&asyncToken=61a9699c-7a3f-4c06-9496-e790308c702b&tags=LikeEvent-LIKED-streamEntry,id:1013,type:streamEntry,requestID:61a9699c-7a3f-4c06-9496-e790308c702b&userId=2014&metadata=id:1013,type:streamEntry",
      |"method=user.logAction&asyncToken=a576b4b7-9653-4171-b6d0-d1519fcd6b23&tags=LikeEvent-LIKED-streamEntry,id:1014,type:streamEntry,requestID:a576b4b7-9653-4171-b6d0-d1519fcd6b23&userId=2014&metadata=id:1014,type:streamEntry]"
    """.stripMargin


  "Have two requests" in {

    val result = GamificationRequest.parseFeed(requests)


    println("---- " + result)
    result must haveSize(4)
  }


}
