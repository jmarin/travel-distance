package com.travel.distance.impl;

import akka.actor.ActorSystem;
import com.travel.grpc.DistanceProto.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import akka.testkit.javadsl.TestKit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DistanceServiceJavaImplTest {
    private static ActorSystem system;
    private static DistanceServiceJavaImpl service;

    @BeforeClass
    public static void setUp() {
        system = ActorSystem.create();
        service = new DistanceServiceJavaImpl();
    }

    @AfterClass
    public static void tearDown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void distanceServiceJavaImplHaversineDistance() throws InterruptedException, ExecutionException, TimeoutException {
        Location nyc = Location.newBuilder().setLatitude(40.714268).setLongitude(-74.005974).build();
        Location sf =  Location.newBuilder().setLatitude(37.774929).setLongitude(-122.419418).build();
        Locations locations = Locations.newBuilder().setStart(nyc).setEnd(sf).build();
        Distance distance = service.calculateDistance(locations)
                .toCompletableFuture()
                .get(5, TimeUnit.SECONDS);
        Distance expected = Distance.newBuilder().setValue(4130.22225739269).build();
        assertEquals(expected, distance);
    }

}
