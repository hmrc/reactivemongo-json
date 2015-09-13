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

      Test.specs2,
      Test.junit
    )
  }

  lazy val reactiveMongoJson = Project(nameApp, file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
    .settings(
      scalaVersion := "2.11.7",
      libraryDependencies ++= appDependencies,
      resolvers := Seq(
        Resolver.bintrayRepo("hmrc", "releases"),
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/",
        "Sonatype" at "http://oss.sonatype.org/content/groups/public/"
      ),
      crossScalaVersions := Seq("2.11.7")
    )
}

object Dependencies {

  object Compile {
    val reactiveMongo = "org.reactivemongo" %% "reactivemongo" % "0.11.5" % "provided"
    val playJson = "com.typesafe.play" %% "play-json" % "2.3.9" % "provided"
  }

  sealed abstract class Test(scope: String) {

    val specs2 = "org.specs2" % "specs2" % "2.3.12" % scope cross CrossVersion.binary
    val junit = "junit" % "junit" % "4.8" % scope
  }

  object Test extends Test("test")

  object IntegrationTest extends Test("it")

}