package com.techmonad.kafka

import java.util.Properties
import java.util.concurrent.Future

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.slf4j.{Logger, LoggerFactory}

/*
 Reference => https://javadoc.io/doc/org.apache.kafka/kafka-clients/latest/index.html
 */
class Producer(servers: List[String]) {

  val logger: Logger = LoggerFactory.getLogger(this.getClass())

  private val props: Properties = {
    val props = new Properties
    props.put("bootstrap.servers", servers.mkString(","))
    props.put("acks", "all")
    props.put("retries", "3")
    props.put("batch.size", "1000")
    props.put("linger.ms", "5")
    props.put("buffer.memory", "33554432")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props
  }

  private val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)


  def send(topic: String, record: String): Future[RecordMetadata] = {
    val message: ProducerRecord[String, String] = new ProducerRecord[String, String](topic, record)
    logger.info("Sending message to kafka cluster .....")
    producer.send(message)
  }

  def close(): Unit = producer.close()

}

