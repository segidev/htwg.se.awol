name          := "Arschloch Extended"
organization  := "de.htwg.se"
version       := "1.0.0"
scalaVersion  := "2.11.11"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

resolvers += Resolver.jcenterRepo

libraryDependencies ++= {
  val scalaTestV       = "3.0.1"
  val scalaMockV       = "3.2.2"
  Seq(
    "org.scalatest" %% "scalatest"                   % scalaTestV       % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockV       % "test"
  )
}

libraryDependencies += "junit" % "junit" % "4.8" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "org.scala-lang" % "scala-swing" % "2.11.0-M7"
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.92-R10"
libraryDependencies += "net.liftweb" % "lift-json_2.11" % "3.2.0-RC1"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2"
libraryDependencies += "com.google.inject" % "guice" % "4.1.0"