name := """akka-persistence-serialization"""

version := "1.0"

scalaVersion := "2.11.1"

// Uncomment to use Akka
libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.3.6",
  	"com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.6",
  	"com.typesafe.akka" %% "akka-slf4j" % "2.3.6",
  	"com.github.krasserm" %% "akka-persistence-cassandra" % "0.3.4",
  	"ch.qos.logback" % "logback-classic" % "1.0.13"
)

resolvers += "krasserm at bintray" at "http://dl.bintray.com/krasserm/maven"

