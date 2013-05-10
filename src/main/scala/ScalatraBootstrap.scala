import com.jivesoftware.gamification._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {





    context.mount(new GamificationServlet, "/api/json*")
  }
}
