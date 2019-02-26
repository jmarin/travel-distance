import BuildSettings._
import Dependencies._

lazy val testDeps = Seq(scalatest, scalacheck)

lazy val commonDeps = testDeps
lazy val catsDeps = Seq(cats)
lazy val akkaHttpDeps = Seq(akkaHttp, akkaStream)

lazy val howFar = (project in file("."))
  .settings(commonSettings:_*)
  .aggregate(service)

lazy val protobuf = (project in file("protobuf"))
  .enablePlugins(AkkaGrpcPlugin)
  .settings(commonSettings:_*)
  

lazy val service = (project in file("service"))
  .enablePlugins(AkkaGrpcPlugin)
  .settings(commonSettings:_*)
  .settings(
    akkaGrpcGeneratedSources := Seq(AkkaGrpc.Server),
    //Generate both Java and Scala API's
    akkaGrpcGeneratedLanguages := Seq(AkkaGrpc.Scala, AkkaGrpc.Java),
    //Disable 'flat_package' option to avoid conflicts between both languages implementations
    akkaGrpcCodeGeneratorSettings := akkaGrpcCodeGeneratorSettings.value.filterNot(_ == "flat_package"),
    libraryDependencies ++= commonDeps ++ akkaHttpDeps,
  )
