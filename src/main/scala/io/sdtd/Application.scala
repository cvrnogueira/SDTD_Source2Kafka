package io.sdtd

import java.time.LocalDate

import com.typesafe.scalalogging.LazyLogging
import io.circe.syntax._
import io.sdtd.configuration.CustomProducer
import io.sdtd.model.TwitterPayload
import twitter4j._
import twitter4j.conf.ConfigurationBuilder

object Application extends App with LazyLogging {

  val producer = new CustomProducer()

  //TODO: set as environment in AWS
  val cb = new ConfigurationBuilder()
    .setDebugEnabled(true)
    .setOAuthConsumerKey("-")
    .setOAuthConsumerSecret("-")
    .setOAuthAccessToken("328681900--")
    .setOAuthAccessTokenSecret("-")

  val tf = new TwitterStreamFactory(cb.build())
  val twitterStream = tf.getInstance()

  // implement a simple listener that will receive
  // tweets status (akka twitter messages), log to
  // configured logger and publish to kafka topic
  twitterStream.addListener(new StatusListener() {

    override def onStatus(status: Status) {
      val response = TwitterPayload(
        status.getUser.getId,
        status.getCreatedAt.getTime,
        status.getText,
        Option(if (status.getPlace != null) status.getPlace.getCountry else status.getUser.getLocation)
      )

      val send = producer.sendToTopics2Flink(
        key = s"${response.userId}${LocalDate.now().toString}",
        message = response.asJson.noSpaces
      )

      send match {
        case Right(value) => {
          logger.debug(s"published message with offset ${value.offset()}")
        }
        case Left(t) => {
          logger.error(t.getMessage, t)
        }
      }
    }

    // these methods can be ignored and haven't be used thus far
    override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {}
    override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = {}
    override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = {}
    override def onStallWarning(warning: StallWarning): Unit = {}
    override def onException(ex: Exception): Unit = { logger.error(ex.getMessage, ex) }
  })

  // start a thread to sample tweets accordingly
  // to docs twitter api can send at most 1% of total
  // tweets being posted
  twitterStream.sample()

  // block main thread until a sigterm is received
  // to keep tweet sampling thread running since once main
  // thread finishes then its children are also finished
  Thread.currentThread.join()
}
