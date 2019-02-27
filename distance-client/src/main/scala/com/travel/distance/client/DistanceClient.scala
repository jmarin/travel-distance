package com.travel.distance.client

import akka.actor.ActorSystem
import akka.grpc.GrpcClientSettings
import akka.stream.ActorMaterializer
import com.travel.grpc.distance.DistanceUnits.KILOMETERS
import com.travel.grpc.distance.{DistanceServiceClient, Location, Locations}

import scala.util.{Failure, Success}

object DistanceClient extends App {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val clientSettings = GrpcClientSettings.fromConfig("DistanceService")
  val client = DistanceServiceClient(clientSettings)

  testDistanceClient()

  def testDistanceClient(): Unit = {
    system.log.info("Calculating distance between New York City and San Francisco")
    val nyc = Location(-74.005974, 40.714268)
    val sf = Location(-122.419418, 37.774929)
    val locations = Locations(Some(nyc), Some(sf), KILOMETERS)
    val reply = client.calculateDistance(locations)
    reply.onComplete {
      case Success(d) =>
        system.log.info(s"The distance from NYC to SF is ${d.value} ${d.units}")
      case Failure(e) =>
        system.log.error(e.getLocalizedMessage)

    }
  }
}
