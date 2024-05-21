ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "ofp_5"
  )
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.5"
libraryDependencies += "org.json4s" %% "json4s-jackson" % "4.0.7"