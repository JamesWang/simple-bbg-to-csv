ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file(".")).settings(
  name := "Simple Bloomberg file to CSV"
)

val ZIO_VERSION = "2.1.2"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % ZIO_VERSION,
  "org.typelevel" %% "cats-core" % "2.12.0",
  "org.tpolecat" %% "doobie-core" % "1.0.0-RC1",
  "dev.zio" %% "zio-test" % ZIO_VERSION % Test
)

val CIRCE_VERSION = "0.14.3"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % CIRCE_VERSION)