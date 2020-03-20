package example

import java.util.concurrent.TimeUnit

import org.mockserver.client.MockServerClient
import org.mockserver.integration.ClientAndServer.startClientAndServer
import org.mockserver.model.HttpRequest._
import org.mockserver.model.HttpResponse._

object ServerMain {

  def main(args: Array[String]): Unit = {
    startClientAndServer(5555)

    new MockServerClient("localhost", 5555)
      .when(
        request()
          .withMethod("GET")
          .withPath("/hello"),
      )
      .respond(
        response("world").withStatusCode(200).withDelay(TimeUnit.SECONDS, 1),
      )
    println("Mock server started")
  }

}
