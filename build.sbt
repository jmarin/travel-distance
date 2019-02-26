import BuildSettings._
import Dependencies._

lazy val testDeps = Seq(scalatest, scalacheck)

lazy val commonDeps = testDeps
lazy val catsDeps = Seq(cats)

lazy val howFar = (project in file("."))
  .settings(commonSettings:_*)
  .aggregate(service)

lazy val service = (project in file("service"))
  .settings(commonSettings:_*)
  .settings(
    libraryDependencies ++= commonDeps	
  )
