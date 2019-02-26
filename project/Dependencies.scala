import sbt._
import Version._

object Dependencies {
  lazy val scalatest = "org.scalatest" %% "scalatest" % scalatestVersion % "test"
  lazy val scalacheck = "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test"
  lazy val cats = "org.typelevel" %% "cats-core" % catsVersion
  lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  lazy val akkaStreamTestKit = "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test
  lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  lazy val junit = "junit" % "junit" % junitVersion % Test
  lazy val novocode = "com.novocode" % "junit-interface" % novocodeVersion % Test
}
