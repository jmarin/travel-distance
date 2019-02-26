package com.travel.distance.impl

import com.travel.distance.utils.HaversineDistance
import com.travel.grpc.distance.DistanceUnits.{KILOMETERS, MILES}
import com.travel.grpc.distance.{Distance, DistanceService, Location, Locations}

import scala.concurrent.Future

class DistanceServiceImpl extends DistanceService {
  override def calculateDistance(locations: Locations): Future[Distance] = {
    val start = locations.start.getOrElse(Location())
    val end = locations.end.getOrElse(Location())
    val units = locations.units
    val lat1 = start.latitude
    val lon1 = start.longitude
    val lat2 = end.latitude
    val lon2 = end.longitude
    val d = HaversineDistance.calculate(lat1, lon1, lat2, lon2)
    val distance = units match {
      case KILOMETERS => d
      case MILES      => d * 0.621371
    }
    Future.successful(Distance(distance))
  }
}
