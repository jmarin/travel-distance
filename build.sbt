import BuildSettings._
import Dependencies._

lazy val testDeps = Seq(scalatest, scalacheck, novocode, junit, akkaStreamTestKit)

lazy val commonDeps = testDeps
lazy val catsDeps = Seq(cats)
lazy val akkaHttpDeps = Seq(akkaHttp, akkaStream)

lazy val travelDistance = (project in file("."))
  .settings(commonSettings:_*)
  .aggregate(distanceApi, distanceImpl)

lazy val distanceApi = (project in file("distance-api"))
  .enablePlugins(AkkaGrpcPlugin)
  .settings(commonSettings:_*)
  .settings(
   //Generate both Java and Scala API's
    akkaGrpcGeneratedLanguages := Seq(AkkaGrpc.Scala, AkkaGrpc.Java),
    //Disable 'flat_package' option to avoid conflicts between both language implementations
    akkaGrpcCodeGeneratorSettings := akkaGrpcCodeGeneratorSettings.value.filterNot(_ == "flat_package")
  )
  

lazy val distanceImpl = (project in file("distance-impl"))
  .enablePlugins(JavaAgent)
  .settings(
    javaAgents += "org.mortbay.jetty.alpn" % "jetty-alpn-agent" % "2.0.7" % "runtime"
  )
  .settings(commonSettings:_*)
  .settings(
    libraryDependencies ++= commonDeps ++ akkaHttpDeps
  ).dependsOn(distanceApi)


lazy val distanceClient = (project in file("distance-client"))
  .settings(commonSettings: _*)
  .dependsOn(distanceApi)
