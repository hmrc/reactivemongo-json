import sbt._
import Keys._
import uk.gov.hmrc.versioning.SbtGitVersioning
import uk.gov.hmrc.SbtAutoBuildPlugin

/**
 * NOTE: classes under the package reactivemongo have been extracted from Play-ReactiveMongo, 
 * https://github.com/ReactiveMongo/Play-ReactiveMongo, and must retain the license applied by 
 * ReactiveMongo
 **/
object HmrcBuild extends Build {

  import uk.gov.hmrc.DefaultBuildSettings
  import DefaultBuildSettings._

  import uk.gov.hmrc.{SbtBuildInfo, ShellPrompt}

  val nameApp = "reactivemongo-json"

  val appDependencies = {
    import Dependencies._

    Seq(
      Compile.reactiveMongo,
      Compile.playJson,

      Test.scalaTest,
      Test.pegdown
    )
  }

  lazy val reactiveMongoJson = Project(nameApp, file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
    .settings(
      targetJvm := "jvm-1.7",
      libraryDependencies ++= appDependencies,
      resolvers := Seq(
        Resolver.bintrayRepo("hmrc", "releases"),
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      ),
      crossScalaVersions := Seq("2.11.6")
    )
}

object Dependencies {

  object Compile {
    val reactiveMongo = "org.reactivemongo" %% "reactivemongo" % "0.10.5.0.akka23" % "provided"
    val playJson = "com.typesafe.play" %% "play-json" % "2.3.8" % "provided"
  }

  sealed abstract class Test(scope: String) {

    val scalaTest = "org.scalatest" %% "scalatest" % "2.2.4" % scope
    val pegdown = "org.pegdown" % "pegdown" % "1.4.2" % scope
  }

  object Test extends Test("test")

  object IntegrationTest extends Test("it")

}