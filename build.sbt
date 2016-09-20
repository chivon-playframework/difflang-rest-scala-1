name := """difflang-rest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-feature", "-deprecation",
  "-unchecked", "-language:reflectiveCalls",
  "-language:postfixOps",
  "-language:implicitConversions")

// https://github.com/scala/pickling/issues/10
scalacOptions ++= Seq("-Xmax-classfile-name", "254")

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "Humble video" at "https://mvnrepository.com/artifact/io.humble/humble-video-noarch"
resolvers += "gson" at "https://mvnrepository.com/artifact/com.google.code.gson/gson"
resolvers += "pdfbox" at "https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox"
resolvers += "apache poi" at "https://mvnrepository.com/artifact/org.apache.poi/poi"
resolvers += "zxing" at "https://mvnrepository.com/artifact/com.google.zxing/core"
resolvers += "libsms" at "https://mvnrepository.com/artifact/com.wix.sms/libsms-api"

doc in Compile <<= target.map(_ / "none")

scalariformSettings

libraryDependencies ++= Seq(
  specs2 % Test,
	"org.specs2" %% "specs2-matcher-extra" % "3.8.5" % Test,
  "io.swagger" %% "swagger-play2" % "1.5.1",
  "org.webjars" % "swagger-ui" % "2.1.8-M1",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.12.0-SNAPSHOT",
  "org.apache.pdfbox" % "pdfbox" % "2.0.2",
  "org.apache.poi" % "poi" % "3.14",
  "com.google.zxing" % "core" % "3.2.1",
  "com.wix.sms" % "libsms-api" % "1.0.0",
  "org.apache.cassandra" % "cassandra-all" % "3.7",
  "com.google.code.gson" % "gson" % "2.7",
  "com.paypal.sdk" % "rest-api-sdk" % "1.4.1",
  "com.stripe" % "stripe-java" % "2.8.0",
  "io.humble" % "humble-video-noarch" % "0.2.1"
)
