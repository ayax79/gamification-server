package com.jivesoftware.gamification.util

import org.joda.time.DateTime

object DateTimeUtil {

  /**
   * If we can parse the string into a valid date time then return it
   *
   * @param ts nitro timestamp... in seconds
   * @return the DateTime object
   */
  def fromNitroTimeStamp(ts: String): Option[DateTime] = {
    try {
      if (ts != null) Option(new DateTime(ts.toLong * 1000L))
      else None
    }
    catch {
      case _ : Throwable => None
    }
  }

  implicit class RichDateTime(val self: DateTime) {

    def toNitroString: String =
      if (self != null) (self.getMillis / 1000L).toString
      else null

  }

}


