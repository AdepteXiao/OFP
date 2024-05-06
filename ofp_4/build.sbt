ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "ofp_4",
    idePackagePrefix := Some("ru.ad.ofp_4")
  )

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.10"
