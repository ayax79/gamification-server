import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object GamificationserverBuild extends Build {
  import com.github.siasia.PluginKeys.port
  import com.github.siasia.WebPlugin.{container, webSettings}

  val Organization = "com.jivesoftware"
  val Name = "gamification-server"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.10.0"
  val ScalatraVersion = "2.2.0"
  def Conf = config("container")
  def jettyPort = 8081

  val excludeSlfRule = ExclusionRule(organization = "org.slf4j")

  lazy val project = Project(
    "gamification-server",
    file("."),
    settings = Defaults.defaultSettings ++ ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ webSettings ++ Seq(
      port in Conf := jettyPort,
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
      resolvers += "Thrift" at "http://people.apache.org/~rawson/repo/",
      libraryDependencies ++= Seq(
        "org.slf4j" % "slf4j-api" % "1.7.5",
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.0.13" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "8.1.8.v20121106" % "container",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar")),
        "org.apache.hadoop" % "hadoop-core" % "1.1.2" excludeAll(excludeSlfRule),
//        "org.apache.hbase" % "hbase" % "0.95.0",
        "org.apache.hbase" % "hbase" % "0.94.7" excludeAll(excludeSlfRule),
        "org.scalatra" %% "scalatra-json" % "2.2.1",
        "org.json4s" %% "json4s-jackson" % "3.2.4",
        "org.scalaz" %% "scalaz-core" % "7.0.0"
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile) {
        base =>
          Seq(
            TemplateConfig(
              base / "webapp" / "WEB-INF" / "templates",
              Seq.empty, /* default imports should be added here */
              Seq(
                Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
              ), /* add extra bindings here */
              Some("templates")
            )
          )
      }
    )
  )
}
