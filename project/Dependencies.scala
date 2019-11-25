import sbt._

object Dependencies {

  object Version {
    val kafka = "2.1.0"
    val scalaTest = "3.0.8"
    val twitter4j = "4.0.4"
    val circeJSON = "0.12.3"
    val slf4j = "1.7.28"
    val log4j = "1.2.17"
    val scalaLogging = "3.9.2"
  }

  val scalaTest = Seq("org.scalatest" %% "scalatest" % Version.scalaTest % Test)

  val kafka = Seq("org.apache.kafka" % "kafka-clients" % Version.kafka)

  val twitter4j = Seq(
    "org.twitter4j" % "twitter4j-core" % Version.twitter4j,
    "org.twitter4j" % "twitter4j-stream" % Version.twitter4j
  )

  val circeJSON = Seq(
    "io.circe" %% "circe-literal",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % Version.circeJSON)

  val logging = Seq(
    "org.slf4j" % "slf4j-api" % Version.slf4j,
    "org.slf4j" % "slf4j-log4j12" % Version.slf4j,
    "log4j" % "log4j" % Version.log4j,
    "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLogging
  )

  val all = scalaTest ++ twitter4j ++ circeJSON ++ logging ++ kafka
}