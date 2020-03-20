lazy val akkaHttpVersion = "10.1.11"
lazy val akkaVersion = "2.6.4"

lazy val root = (project in file(".")).settings(
  inThisBuild(List(organization := "com.example", scalaVersion := "2.13.1")),
  name := "parallel_test",
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.mock-server" % "mockserver-netty" % "5.9.0",
    "org.scalatest" %% "scalatest" % "3.0.8" % Test
  )
)
