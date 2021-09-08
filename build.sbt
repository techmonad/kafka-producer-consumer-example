name := "kafka-producer-consumer-example"

version := "1.0"

scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % "2.8.0",
  "ch.qos.logback" % "logback-classic" % "1.2.5"
)
