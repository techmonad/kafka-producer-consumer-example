package com.techmonad.kafka

import java.util.Properties
import java.util.concurrent.Future

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.slf4j.{Logger, LoggerFactory}


case class Producer(servers: List[String]) {

  val logger: Logger = LoggerFactory.getLogger(this.getClass())

  private val props: Properties = {
    val props = new Properties
    props.put("bootstrap.servers", servers.mkString(","))
    props.put("acks", "all")
    props.put("retries", "3")
    props.put("batch.size", "1000")
    props.put("linger.ms", "5")//The producer groups together any records that arrive in between request transmissions into a single batched request. Normally this occurs only under load when records arrive faster than they can be sent out. However in some circumstances the client may want to reduce the number of requests even under moderate load. This setting accomplishes this by adding a small amount of artificial delay—that is, rather than immediately sending out a record the producer will wait for up to the given delay to allow other records to be sent so that the sends can be batched together. This can be thought of as analogous to Nagle's algorithm in TCP. This setting gives the upper bound on the delay for batching: once we get batch.size worth of records for a partition it will be sent immediately regardless of this setting, however if we have fewer than this many bytes accumulated for this partition we will 'linger' for the specified time waiting for more records to show up. This setting defaults to 0 (i.e. no delay). Setting linger.ms=5, for example, would have the effect of reducing the number of requests sent but would add up to 5ms of latency to records sent in the absence of load.
    props.put("buffer.memory", "33554432")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props
  }

  private val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)


  def send(topic: String, record: String): Future[RecordMetadata] = {
    val message: ProducerRecord[String, String] = new ProducerRecord[String, String](topic, record, record)
    logger.info("Sending message to kafka cluster .....")
    producer.send(message)
  }

  def close(): Unit = producer.close()

}

