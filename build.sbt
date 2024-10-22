ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file(".")).settings(
  name := "Simple Bloomberg file to CSV"
)

val ZIO_VERSION = "2.1.6"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % ZIO_VERSION,
  "dev.zio" %% "zio-direct" % "1.0.0-RC7",
  "dev.zio" %% "zio-test" % ZIO_VERSION % Test
)
