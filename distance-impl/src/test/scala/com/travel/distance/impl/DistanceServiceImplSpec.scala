package com.travel.distance.impl

import akka.actor.ActorSystem
import com.travel.grpc.distance.DistanceUnits.KILOMETERS
import com.travel.grpc.distance.{Distance, Location, Locations}
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}

class DistanceServiceImplSpec
    extends AsyncWordSpec
    with Matchers
    with BeforeAndAfterAll {

  val system = ActorSystem()
  val service = new DistanceServiceImpl

  "DistanceServiceImpl" should {
    "return Distance between locations (NYC and SF)" in {
      val nyc = Location(-74.005974, 40.714268)
      val sf = Location(-122.419418, 37.774929)
      val locations = Locations(Some(nyc), Some(sf), KILOMETERS)
      val fDistance = service.calculateDistance(locations)
      fDistance.map { distance =>
        distance shouldBe Distance(4130.22225739269)
      }
    }
  }

}
