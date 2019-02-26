package com.travel.distance.utils

import org.scalatest.{Matchers, WordSpec}

class HaversineDistanceSpec extends WordSpec with Matchers {

  "HaversineDistance" should {
    "calculate distance between NYC and SF" in {
      HaversineDistance.calculate(40.714268, -74.005974, 37.774929, -122.419418) shouldBe 4130.22225739269
    }
  }

}
