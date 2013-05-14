import com.jivesoftware.gamification._
import com.jivesoftware.gamification.dispatcher.{RequestHandler, RequestDispatcher}
import com.jivesoftware.gamification.hbase.{InitializationTaskComplete, InitializationTaskFailed, HBaseInitializer, HBaseConfigurator}
import com.jivesoftware.gamification.request.{GamificationResponse, GamificationRequest}
import com.jivesoftware.gamification.user.handler.{LogActionRequestHandler, LoginRequestHandler}
import com.jivesoftware.gamification.user.request.{LogActionRequest, LoginRequest}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.scalatra._
import javax.servlet.ServletContext
import org.slf4j.LoggerFactory

class ScalatraBootstrap extends LifeCycle {
  private val log = LoggerFactory.getLogger(classOf[ScalatraBootstrap])

  override def init(context: ServletContext) {
    log.info("initializing")
    val initializer = new HBaseInitializer with BasicConfigurator
    initializer.init.foreach {
      case Left(InitializationTaskFailed(task, msg, None)) =>
        log.error(s"Error completing task ${task}: ${msg}")
      case Left(InitializationTaskFailed(task, msg, Some(ex))) =>
        log.error(s"Exception occurred with task ${task}: ${msg}", ex)
      case Right(InitializationTaskComplete(task, true)) =>
        log.info(s"Intialization task ${task} completed")
      case _ => //ignore
    }

    context.mount(gamificationServlet, "/*")
  }

  lazy val gamificationServlet = new GamificationServlet with RequestDispatcher {

    val loginRequestHandler = new LoginRequestHandler
    val logActionRequestHandler: LogActionRequestHandler = new LogActionRequestHandler

    val handlers: Map[Class[_], RequestHandler[_ >: GamificationRequest, _]] = Map (
      classOf[LoginRequest] -> loginRequestHandler.asInstanceOf[RequestHandler[GamificationRequest, GamificationResponse]],
      classOf[LogActionRequest] -> logActionRequestHandler.asInstanceOf[RequestHandler[GamificationRequest, GamificationResponse]]
    )
  }
}

object BasicConfigurator {
  lazy val conf: Configuration = HBaseConfiguration.create()
}

trait BasicConfigurator extends HBaseConfigurator {
  def configuration: Configuration = BasicConfigurator.conf
}
