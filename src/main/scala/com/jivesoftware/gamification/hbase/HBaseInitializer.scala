package com.jivesoftware.gamification.hbase

import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.{HColumnDescriptor, HTableDescriptor}
import org.slf4j.LoggerFactory

/**
 * Basically just checks to make sure that our tables exist, etc.
 */
class HBaseInitializer {
  self: HBaseConfigurator =>

  private val log = LoggerFactory.getLogger(classOf[HBaseInitializer])

  def init: List[Either[InitializationTaskFailed, InitializationTaskComplete]] = {
    log.info("hbase configuration" + configuration)
    val admin = new HBaseAdmin(configuration)
    initializeUserActivityLog(admin) :: Nil
  }

  protected def initializeUserActivityLog(admin: HBaseAdmin): Either[InitializationTaskFailed, InitializationTaskComplete] = {
    val taskName = "initializeUserActivityLog"
    try {
      val tableName: String = "UserActivityLog"
      if (!admin.isTableAvailable(tableName)) {
        log.info(s"Table ${tableName} has not been created yet... creating.")

        val table: HTableDescriptor = new HTableDescriptor(tableName)
        table.addFamily(new HColumnDescriptor("method"))
        table.addFamily(new HColumnDescriptor("tags"))
        table.addFamily(new HColumnDescriptor("metadata"))
        table.addFamily(new HColumnDescriptor("userId"))
        table.addFamily(new HColumnDescriptor("value"))
        table.addFamily(new HColumnDescriptor("newsfeed"))
        table.addFamily(new HColumnDescriptor("target"))
        admin.createTable(table)
        Right(InitializationTaskComplete(taskName, ran = true))
      } else Right(InitializationTaskComplete(taskName))
    }
    catch {
      case ex: Throwable => Left(InitializationTaskFailed(taskName, ex.getMessage, Option(ex)))
    }
  }

}

case class InitializationTaskComplete(task: String, ran: Boolean = false)
case class InitializationTaskFailed(task: String, msg: String, ex: Option[Throwable] = None)
