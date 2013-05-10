package com.jivesoftware.gamification.util

import java.util.UUID

object UUIDUtil {
  def asUUID(s: String): Option[UUID] = {
    try {
      if (s != null) Option(UUID.fromString(s))
      else None
    }
    catch {
      case _: Throwable => None
    }
  }

  def asUUID(s: Option[String]): Option[UUID] = s.flatMap(asUUID)
}
