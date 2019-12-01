package io.sdtd.configuration

import java.util.Properties

import org.apache.kafka.clients.producer._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

class CustomProducer {

  val producerProperties = new Properties()

  val bootstrapHost = sys.env.getOrElse("KAFKA_CLUSTER_ENTRY_POINT", "127.0.0.1")

  producerProperties.put("bootstrap.servers", s"$bootstrapHost:9092")
  producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](producerProperties)

  val topicOutName: String = "Transactions2DBTopicOut"

  def sendToTopics2Flink(key: String, message: String): Throwable Either RecordMetadata = {
    sendToKafka(key, message, topicOutName)
  }

  private def sendToKafka(key: String, message: String, topic: String): Throwable Either RecordMetadata = {
    Try {
      val record = new ProducerRecord(topic, key, message)
      producer.send(record).get()
    }.toEither
  }
}