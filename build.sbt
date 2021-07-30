import BuildProperties._

name := "play-jooq"
ThisBuild / version := "21.07u1"
ThisBuild / organization := "com.jackson42.play.jooq"
ThisBuild / scalaVersion := "2.13.6"

ThisBuild / description := "Play-JOOQ is a PlayFramework modules that integrate JOOQ as a way to manipulate your database."
ThisBuild / licenses := Seq("MIT License" -> url("https://opensource.org/licenses/MIT"))
ThisBuild / homepage := Some(url("https://github.com/pierreadam/play-jooq"))

ThisBuild / crossScalaVersions := supportedScalaVersions
ThisBuild / javacOptions ++= Seq("--release", "8")

lazy val root = project.in(file("."))

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % playVersion % Provided,
  "com.typesafe.play" %% "play-guice" % playVersion % Provided,
  "com.typesafe.play" %% "play-jdbc" % playVersion % Provided,
  "org.jooq" % "jooq" % jooqVersion,
  "org.jooq" % "jooq-meta" % jooqVersion,
  "org.jooq" % "jooq-codegen" % jooqVersion
)
