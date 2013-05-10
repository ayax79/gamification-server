import com.jivesoftware.gamification._
import com.jivesoftware.gamification.dispatcher.{RequestHandler, RequestDispatcher}
import com.jivesoftware.gamification.request.{GamificationResponse, GamificationRequest}
import com.jivesoftware.gamification.user.handler.LoginRequestHandler
import com.jivesoftware.gamification.user.request.LoginRequest
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(gamificationServlet, "/api/json*")
  }

  lazy val gamificationServlet = new GamificationServlet with RequestDispatcher {

    val loginRequestHandler = new LoginRequestHandler

    val handlers: Map[Class[_], RequestHandler[_ >: GamificationRequest, _]] = Map (
      classOf[LoginRequest] -> loginRequestHandler.asInstanceOf[RequestHandler[GamificationRequest, GamificationResponse]]
    )
  }
}
