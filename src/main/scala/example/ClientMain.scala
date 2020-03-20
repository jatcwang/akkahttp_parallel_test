package example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.Materializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

object ClientMain {

  implicit val actorSystem = ActorSystem("outgoing-http")
  implicit val materializer = Materializer(actorSystem)

  val client = Http()

  def main(args: Array[String]): Unit = {

    val numParallelRequests =
      ConfigFactory.load().getInt("num-parallel-requests")

    Await.result(
      Future.sequence((1 to numParallelRequests).toList.map { idx =>
        println(s"STARTING $idx")
        client
          .singleRequest(HttpRequest(uri = "http://localhost:5555/hello"))
          .map { resp =>
            resp.entity.discardBytes()
            println(resp.status)
          }
      }),
      10.seconds
    )

    Await.result(client.shutdownAllConnectionPools(), 10.seconds)
    materializer.shutdown()
    Await.result(actorSystem.terminate(), 10.seconds)
  }
}
