import sbt.Keys.{libraryDependencies, _}
import sbt._
import sbtassembly.AssemblyKeys._
import sbtassembly.MergeStrategy

object Compilation {

  lazy val apiName = "twitter2kafka"
  lazy val suffix = ".jar"

  lazy val buildSettings = Seq(
    name := apiName,
    organization := "io.sdtd",
    version := "1.0.0"
  )

  val settings = Seq(
    ThisBuild / scalaVersion := "2.12.1",
    libraryDependencies ++= Dependencies.all,
    mainClass in (Compile, run) := Some("io.sdtd.Application")
  )
}
