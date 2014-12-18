name := """shapely"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "de.fuberlin.wiwiss.r2r" % "r2rApi" % "0.2.3"
)
