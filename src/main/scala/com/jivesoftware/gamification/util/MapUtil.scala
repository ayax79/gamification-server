package com.jivesoftware.gamification.util

object MapUtil {

  def parse(delimeter: String)(s: String): Map[String, String] =
    s.split(delimeter).foldLeft(Map.empty[String, String]) {
      (acc, cur) =>
        val tokens = cur.split("=")
        acc + (tokens(0) -> tokens(1))
    }

  /**
   * Parses a http query style string
   */
  val parseQueryString = parse("#") _

  val parseCommaString = parse(",") _

  implicit class RichMap[A, +B](val self: Map[A,B]) {

    def asString(delimeter: String):String =
      self.foldLeft(new StringBuilder) {
        (acc, cur) =>
          acc.append(",").append(cur._1).append("=").append(cur._2)
      }.toString()

    val asCommaString = asString(",")

  }

}



