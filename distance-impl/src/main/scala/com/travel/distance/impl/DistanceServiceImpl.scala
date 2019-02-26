package com.travel.distance.impl

import com.travel.grpc.distance.{Distance, DistanceService, Location, Locations}

import scala.concurrent.Future

class DistanceServiceImpl extends DistanceService {
  override def calculateDistance(locations: Locations): Future[Distance] = {
    val start = locations.start.getOrElse(Location())
    val end = locations.end.getOrElse(Location())

    ???
  }
}
