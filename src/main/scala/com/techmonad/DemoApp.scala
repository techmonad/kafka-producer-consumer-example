package com.techmonad

import com.techmonad.kafka.{Consumer, Producer}

object DemoApp extends App {


  val server = "localhost:9092"
  val topic = "demo"

  val producer = new Producer(List(server))

  val consumer = new Consumer(List(topic), List(server))

  1 to 10000 foreach { no =>
    producer.send(topic, "Message - " + no)
  }

  Iterator.continually(consumer.read()).foreach { messages =>
    Thread.sleep(1000) //ONLY FOR DEMO
    println(" messages " + messages.mkString(" | "))

  }

}
