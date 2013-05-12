package com.jivesoftware.gamification.user.request

//import org.specs2.mutable._
import java.util.UUID
import org.joda.time.DateTime
import com.jivesoftware.gamification.request.ResponseCode

import org.json4s.JsonAST._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.specs2.mutable.Specification


class LoginResponseSpec extends Specification {

  "have proper json" in {
    val request = LoginRequest(UUID.randomUUID(), "sadlkfjasdlkfj", "lsdkjfsdlkfj", new DateTime())
    val response = LoginResponse(ResponseCode.OK, "sadfasdfasdf", request)
    val json = response.toJson

    val JString(result) = json \ "apiKey"
    result must be (request.apiKey)
  }

}
