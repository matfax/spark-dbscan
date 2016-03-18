name := "spark_dbscan"

organization := "org.alitouka"

version := "0.0.4-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "1.6.0" % "provided"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.1.3" % "test"

libraryDependencies += "org.apache.commons" % "commons-math3" % "3.2"

libraryDependencies += "com.github.scopt" %% "scopt" % "3.2.0"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

resolvers += Resolver.sonatypeRepo("public")

//resolvers += "Local Maven Repository" at "file:///Users/zafshar/.m2/repository"

//publishTo := Some(Resolver.file("file",  new File("/Users/zafshar/.m2/repository")))
publishTo := Some("Local Nexus" at "http://localhost:8081/content/repositories/snapshots")

// fun fact the first parameter in Credentials MUST read EXACTLY:
// "Sonatype Nexus Repository Manager"
credentials += Credentials("Sonatype Nexus Repository Manager", "localhost", "deployment", "deployment123")
