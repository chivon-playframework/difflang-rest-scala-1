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

doc in Compile <<= target.map(_ / "none")

scalariformSettings

libraryDependencies ++= Seq(
  specs2 % Test,
	"org.specs2" %% "specs2-matcher-extra" % "3.8.5" % Test
)
