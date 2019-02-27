package com.travel.distance.client;

import akka.actor.ActorSystem;
import akka.grpc.GrpcClientSettings;
import akka.stream.ActorMaterializer;
import com.travel.grpc.DistanceProto;
import com.travel.grpc.DistanceServiceClient;
import io.grpc.StatusRuntimeException;
import scala.concurrent.ExecutionContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class DistanceClientJava {


    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorMaterializer materializer = ActorMaterializer.create(system);
        ExecutionContext ec = system.dispatcher();

        GrpcClientSettings settings = GrpcClientSettings.fromConfig("DistanceService", system);
        DistanceServiceClient client = null;

        try {
            client = DistanceServiceClient.create(settings, materializer, ec);
            testDistanceClient(client);
        } catch (StatusRuntimeException se) {
            System.out.println(se.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    private static void testDistanceClient(DistanceServiceClient client) throws InterruptedException, ExecutionException, TimeoutException {
        DistanceProto.Location nyc = DistanceProto.Location.newBuilder().setLatitude(40.714268).setLongitude(-74.005974).build();
        DistanceProto.Location sf = DistanceProto.Location.newBuilder().setLatitude(37.774929).setLongitude(-122.419418).build();
        DistanceProto.Locations locations = DistanceProto.Locations.newBuilder().setStart(nyc).setEnd(sf).build();
        DistanceProto.Distance d = client.calculateDistance(locations).toCompletableFuture().get(5, TimeUnit.SECONDS);
        System.out.println("The distance from NYC to SF is " + d.getValue() + " " + d.getUnits());
    }

}
