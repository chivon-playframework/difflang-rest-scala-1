name := """difflang-rest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.mongodb" % "mongodb-driver-async" % "3.0.4",
  "org.mongodb" % "mongodb-driver-core" % "3.0.4",
  "org.mongodb" % "bson" % "3.0.4",
  "org.elasticsearch" % "elasticsearch" % "1.7.2",
  "com.google.code.gson" % "gson" % "2.7",
  "com.paypal.sdk" % "rest-api-sdk" % "1.4.1",
  "org.mockito" % "mockito-all" % "1.10.19",
  "com.stripe" % "stripe-java" % "2.8.0",
  "io.humble" % "humble-video-noarch" % "0.2.1",
  "com.feth" % "play-authenticate_2.11" % "0.7.1",
  "com.wordnik" %% "swagger-play2" % "1.3.12" exclude("org.reflections", "reflections"),
  "org.reflections" % "reflections" % "0.9.10",
  "org.webjars" % "swagger-ui" % "2.1.8-M1",
  "org.apache.pdfbox" % "pdfbox" % "2.0.2",
  "org.apache.poi" % "poi" % "3.14",
  "com.google.zxing" % "core" % "3.2.1",
  "com.wix.sms" % "libsms-api" % "1.0.0",
  "org.apache.cassandra" % "cassandra-all" % "3.7"
)
resolvers += "Humble video" at "https://mvnrepository.com/artifact/io.humble/humble-video-noarch"
resolvers += "gson" at "https://mvnrepository.com/artifact/com.google.code.gson/gson"
resolvers += "play authenticate" at "https://mvnrepository.com/artifact/com.feth/play-authenticate_2.11"
resolvers += "swagger play2" at "https://mvnrepository.com/artifact/com.wordnik/swagger-play2_2.10"
resolvers += "reflections" at "https://mvnrepository.com/artifact/org.reflections/reflections"
resolvers += "pdfbox" at "https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox"
resolvers += "apache poi" at "https://mvnrepository.com/artifact/org.apache.poi/poi"
resolvers += "zxing" at "https://mvnrepository.com/artifact/com.google.zxing/core"
resolvers += "libsms" at "https://mvnrepository.com/artifact/com.wix.sms/libsms-api"
resolvers += "cassandra" at "https://mvnrepository.com/artifact/org.apache.cassandra/cassandra-all"








