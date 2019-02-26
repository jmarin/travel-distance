package com.travel.distance

import akka.actor.ActorSystem
import akka.http.scaladsl.UseHttp2.Always
import akka.http.scaladsl.{Http, HttpConnectionContext}
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.{ActorMaterializer, Materializer}
import com.travel.distance.impl.DistanceServiceImpl
import com.travel.grpc.distance.DistanceServiceHandler

import scala.concurrent.{ExecutionContext, Future}

object DistanceServer extends App {
  val system = ActorSystem("DistanceService")
  new DistanceServer(system).run()
}

class DistanceServer(system: ActorSystem) {

  def run(): Future[Http.ServerBinding] = {
    implicit val sys: ActorSystem = system
    implicit val mat: Materializer = ActorMaterializer()
    implicit val ec: ExecutionContext = sys.dispatcher

    val service: HttpRequest => Future[HttpResponse] =
      DistanceServiceHandler(new DistanceServiceImpl)

    // Bind service handler server to localhost:8080
    val bound = Http().bindAndHandleAsync(
      service,
      interface = "127.0.0.1",
      port = 8080,
      connectionContext = HttpConnectionContext(http2 = Always))

    // report successful binding
    bound.foreach { binding =>
      println(s"gRPC server bound to: ${binding.localAddress}")
    }

    bound

  }

}
