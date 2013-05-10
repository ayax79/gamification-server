import com.jivesoftware.gamification._
import com.jivesoftware.gamification.dispatcher.{RequestHandler, RequestDispatcher}
import com.jivesoftware.gamification.request.{GamificationResponse, GamificationRequest}
import com.jivesoftware.gamification.user.handler.{LogActionRequestHandler, LoginRequestHandler}
import com.jivesoftware.gamification.user.request.{LogActionRequest, LoginRequest}
import org.scalatra._
import javax.servlet.ServletContext
import org.slf4j.LoggerFactory

class ScalatraBootstrap extends LifeCycle {
  private val log = LoggerFactory.getLogger(classOf[ScalatraBootstrap])

  override def init(context: ServletContext) {
    log.info("initializing");
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
