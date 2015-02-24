// Copyright (C) 2011-2012 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import sbt.Keys._
import sbt._

object BuildSettings {

  import org.scalastyle.sbt.ScalastylePlugin.{Settings => scalastyleSettings}
  import scoverage.ScoverageSbtPlugin.{buildSettings => scoverageSettings}
  import spray.revolver.RevolverPlugin.{buildSettings => revolverSettings}
  import net.virtualvoid.sbt.graph.Plugin.{graphSettings => graphSettings}

  object Versions {
    val kafka = "0.8.2.0"
    val spark = "1.2.1"
    val zookeeper = "3.4.6"
  }

  val buildSettings = Defaults.coreDefaultSettings ++
    Seq(
      organization := "http://mkuthan.github.io/",
      version := "1.0",
      scalaVersion := "2.11.4",
      scalacOptions := Seq(
        "-deprecation",
        "-encoding", "UTF-8",
        "-feature",
        "-unchecked"),
      resolvers ++= Seq(
        Classpaths.sbtPluginReleases,
        Classpaths.typesafeReleases,
        "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
        "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
      ),
      libraryDependencies ++= Seq(
        "org.apache.spark" % "spark-core_2.10" % Versions.spark notTransitive,
        "org.apache.spark" % "spark-streaming_2.10" % Versions.spark notTransitive,
        "org.apache.spark" % "spark-streaming-kafka_2.10" % Versions.spark notTransitive,

        "org.apache.kafka" %% "kafka" % Versions.kafka notTransitive,

        "org.apache.zookeeper" % "zookeeper" % Versions.zookeeper notTransitive,

        "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
        "org.slf4j" % "slf4j-api" % "1.7.10",
        "ch.qos.logback" % "logback-classic" % "1.1.2",

        "org.scalatest" %% "scalatest" % "2.2.4" % "test",

        "org.apache.curator" % "curator-test" % "2.7.1" % "test" notTransitive
      )
    ) ++ scalastyleSettings ++ scoverageSettings ++ revolverSettings ++ graphSettings

}

object ApplicationBuild extends Build {

  import BuildSettings._

  lazy val main = Project(id = "example-spark", base = file("."), settings = buildSettings)

}

