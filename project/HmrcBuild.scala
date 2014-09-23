import sbt._
import Keys._

object HmrcBuild extends Build {

  import uk.gov.hmrc.DefaultBuildSettings
  import DefaultBuildSettings._
  import BuildDependencies._
  import uk.gov.hmrc.{SbtBuildInfo, ShellPrompt}

  val nameApp = "reactivemongo-json"
  val versionApp = "1.1.0-SNAPSHOT"

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
    .settings(version := versionApp)
    .settings(scalaSettings : _*)
    .settings(defaultSettings() : _*)
    .settings(
      targetJvm := "jvm-1.7",
      shellPrompt := ShellPrompt(versionApp),
      libraryDependencies ++= appDependencies,
      resolvers := Seq(
        Opts.resolver.sonatypeReleases,
        Opts.resolver.sonatypeSnapshots,
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/",
        "typesafe-snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
      ),
      crossScalaVersions := Seq("2.11.2", "2.10.4")
    )
    .settings(SbtBuildInfo(): _*)
    .settings(SonatypeBuild(): _*)
}

object Dependencies {

  object Compile {
    val reactiveMongo = "org.reactivemongo" %% "reactivemongo" % "0.10.5.akka23-SNAPSHOT" % "provided"
    val playJson = "com.typesafe.play" %% "play-json" % "[2.1.0,2.3.4]" % "provided"
  }

  sealed abstract class Test(scope: String) {

    val scalaTest = "org.scalatest" %% "scalatest" % "2.2.1" % scope
    val pegdown = "org.pegdown" % "pegdown" % "1.4.2" % scope
  }

  object Test extends Test("test")

  object IntegrationTest extends Test("it")

}


object SonatypeBuild {

  private def sonatypeCredentials = (for {
    username <- Option(System.getenv().get("SONATYPE_USERNAME"))
    password <- Option(System.getenv().get("SONATYPE_PASSWORD"))
  } yield credentials += Credentials(
      "Sonatype Nexus Repository Manager",
      "oss.sonatype.org",
      username,
      password)
    ).getOrElse(credentials ++= Seq())

  def apply() = {
    Seq(
      sonatypeCredentials,
      publishTo := {
        val nexus = "https://oss.sonatype.org/"
        if (isSnapshot.value)
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases" at nexus + "service/local/staging/deploy/maven2")
      },
      pomExtra := (<url>https://www.gov.uk/government/organisations/hm-revenue-customs</url>
        <licenses>
          <license>
            <name>Apache 2</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
          </license>
        </licenses>
        <scm>
          <connection>scm:git@github.com:hmrc/reactivemongo-json.git</connection>
          <developerConnection>scm:git@github.com:hmrc/reactivemongo-json.git</developerConnection>
          <url>git@github.com:hmrc/reactivemongo-json.git</url>
        </scm>
        <developers>
          <developer>
            <id>sgodbillon</id>
            <name>Stephane Godbillon</name>
            <url>http://stephane.godbillon.com</url>
          </developer>
          <developer>
            <id>mandubian</id>
            <name>Pascal Voitot</name>
            <url>http://mandubian.com</url>
          </developer>
          <developer>
            <id>xnejp03</id>
            <name>Petr Nejedly</name>
            <url>http://www.equalexperts.com</url>
          </developer>
          <developer>
            <id>duncancrawford</id>
            <name>Duncan Crawford</name>
            <url>http://www.equalexperts.com</url>
          </developer>
        </developers>)
    )
  }
}

