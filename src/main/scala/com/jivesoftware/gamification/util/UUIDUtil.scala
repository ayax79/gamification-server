package com.jivesoftware.gamification.util

import java.util.UUID

object UUIDUtil {
  implicit def uuidAsString(uuid: UUID) =
    if (uuid != null) uuid.toString
    else null

  def asUUID(s: String): Option[UUID] = {
    try {
      if (s != null) Option(UUID.fromString(s))
      else None
    }
    catch {
      case _ : Throwable => None
    }
  }
}
