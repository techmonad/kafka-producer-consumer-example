package com.techmonad.app

import com.techmonad.kafka.{Consumer, Producer}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random

object DemoApp extends App {


  val server = "localhost:9092"
  val topic = "event_data"

  val producer = new Producer(List(server))

  val consumer = new Consumer(List(topic), List(server))

 // Future {
    /**
     * Start sending messages on other thread
     */
    1 to 1000000 foreach { _ =>
      val time = System.currentTimeMillis()
      val n = Random.nextInt(50)
      val message = s"""{"value":$n ,"time": $time}"""
      producer.send(topic, message)
      Thread.sleep(100)
    }
  //}


  /**
   * Staring consuming messages
   */
/*  Iterator.continually(consumer.read()).foreach { messages =>
    Thread.sleep(1000) //ONLY FOR DEMO
    messages.foreach(println)

  }*/

}
