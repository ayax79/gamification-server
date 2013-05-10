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

  implicit def toNitroTimeStamp(dt: DateTime): String =
    if (dt != null) (dt.getMillis / 1000L).asInstanceOf[String]
    else null

}
