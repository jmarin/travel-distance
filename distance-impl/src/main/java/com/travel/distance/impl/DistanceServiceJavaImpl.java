package com.travel.distance.impl;

import com.travel.distance.utils.HaversineDistance;
import com.travel.grpc.DistanceProto.*;
import com.travel.grpc.DistanceService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class DistanceServiceJavaImpl implements DistanceService {
    @Override
    public CompletionStage<Distance> calculateDistance(Locations locations) {
        DistanceUnits units = locations.getUnits();
        Location start = locations.getStart();
        Location end = locations.getEnd();
        double lat1 = start.getLatitude();
        double lon1 = start.getLongitude();
        double lat2 = end.getLatitude();
        double lon2 = end.getLongitude();

        double d = HaversineDistance.calculate(lat1, lon1, lat2, lon2);

        double distance = 0d;
        switch (units) {
            case KILOMETERS:
                distance = d;
                break;
            case MILES:
                distance = d * 0.621371;
                break;
        }
        return CompletableFuture.completedFuture(Distance.newBuilder().setUnits(units).setValue(distance).build());
    }
}
