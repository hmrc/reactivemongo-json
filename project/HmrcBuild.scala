import sbt.Keys._
import sbt._
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning

/**
 * NOTE: classes under the package reactivemongo have been extracted from Play-ReactiveMongo, 
 * https://github.com/ReactiveMongo/Play-ReactiveMongo, and must retain the license applied by 
 * ReactiveMongo
 **/
object HmrcBuild extends Build {

  val nameApp = "reactivemongo-json"

  val appDependencies = {
    import Dependencies._

    Seq(
      Compile.reactiveMongo,
      Compile.reactiveMongo1,
      Compile.reactiveMongo2,
      Compile.playJson,

      Test.specs2,
      Test.junit,
      Test.logbackCore,
      Test.logbackClassic
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
    val reactiveMongo = "uk.gov.hmrc" %% "reactivemongo" % "0.12.0" % "provided"
    val reactiveMongo1 = "uk.gov.hmrc" %% "reactivemongo-bson" % "0.11.7" % "provided"
    val reactiveMongo2 = "uk.gov.hmrc" %% "reactivemongo-bson-macros" % "0.11.7" % "provided"
    val playJson = "com.typesafe.play" %% "play-json" % "2.5.8" % "provided"
  }

  sealed abstract class Test(scope: String) {

    val specs2 = "org.specs2" % "specs2" % "2.3.12" % scope cross CrossVersion.binary
    val junit = "junit" % "junit" % "4.8" % scope
    val logbackCore = "ch.qos.logback" % "logback-core" % "1.1.7" % scope
    val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.1.7" % scope

  }

  object Test extends Test("test")

  object IntegrationTest extends Test("it")

}