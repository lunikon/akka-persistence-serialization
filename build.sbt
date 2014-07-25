name := """akka-persistence-serialization"""

version := "1.0"

scalaVersion := "2.11.1"

// Uncomment to use Akka
libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.3.4",
  	"com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.4"
)

