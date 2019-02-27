# Travel Distance

Sample [gRPC](https://grpc.io/) Service that calculates the travel distance between two locations, using the
[Haversine](https://en.wikipedia.org/wiki/Haversine_formula) formula. Implementations for Scala and Java are included

## Running

To run sample service, from an `sbt` prompt issue `reStart`. A server with the functionality is exposed on port 8080.

## Client

### Scala

From an `sbt` prompt issue `project distanceClient` and the `runMain com.travel.distance.client.DistanceClient`. The application should display the Haversine distance
between New York City and San Francisco

### Java

From an `sbt` prompt issue `project distanceClient` and the `runMain com.travel.distance.client.DistanceClientJava`. The application should display the Haversine distance
between New York City and San Francisco

## Deploying

TBD
