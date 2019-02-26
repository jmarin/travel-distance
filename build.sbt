import BuildSettings._
import Dependencies._

lazy val testDeps = Seq(scalatest, scalacheck)

lazy val commonDeps = testDeps
lazy val catsDeps = Seq(cats)
lazy val akkaHttpDeps = Seq(akkaHttp, akkaStream)

lazy val travelDistance = (project in file("."))
  .settings(commonSettings:_*)
  .aggregate(distanceApi, distanceImpl)

lazy val distanceApi = (project in file("distance-api"))
  .settings(commonSettings:_*)
  

lazy val distanceImpl = (project in file("distance-impl"))
  .enablePlugins(AkkaGrpcPlugin)
  .settings(commonSettings:_*)
  .settings(
    akkaGrpcGeneratedSources := Seq(AkkaGrpc.Server),
    //Generate both Java and Scala API's
    akkaGrpcGeneratedLanguages := Seq(AkkaGrpc.Scala, AkkaGrpc.Java),
    //Disable 'flat_package' option to avoid conflicts between both languages implementations
    akkaGrpcCodeGeneratorSettings := akkaGrpcCodeGeneratorSettings.value.filterNot(_ == "flat_package"),
    libraryDependencies ++= commonDeps ++ akkaHttpDeps,
  ).dependsOn(distanceApi)
