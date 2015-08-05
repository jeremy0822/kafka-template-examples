# cubeanalyse
kafka examples

可以用来作为编写Kafka producer和consumer的模板。


1.如果是mvn项目pom依赖：

<dependency>
  <groupId>org.apache.kafka</groupId>
  <artifactId>kafka-clients</artifactId>
  <version>0.8.2.1</version>
</dependency>

<dependency>
  <groupId>org.apache.kafka</groupId>
  <artifactId>kafka_2.10</artifactId>
  <version>0.8.2.1</version>
</dependency>


2.如果是sbt项目build.sbt添加

libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % "0.8.2.1")