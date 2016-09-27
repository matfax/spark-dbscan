name := "spark-dbscan"

organization := "org.apache"

version := "0.2.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.11.8"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.8"

libraryDependencies += "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.4"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core_2.11
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.0.0"

// https://mvnrepository.com/artifact/org.apache.spark/spark-mllib_2.11
libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "2.0.0"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.6" % "test"

libraryDependencies += "org.apache.commons" % "commons-math3" % "3.4.1"

libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

resolvers += Resolver.sonatypeRepo("public")

//resolvers += "Local Maven Repository" at "file:///Users/zafshar/.m2/repository"

//publishTo := Some(Resolver.file("file",  new File("/Users/zafshar/.m2/repository")))
publishTo := Some("Local Nexus" at "http://localhost:8081/content/repositories/snapshots")

// fun fact the first parameter in Credentials MUST read EXACTLY:
// "Sonatype Nexus Repository Manager"
credentials += Credentials("Sonatype Nexus Repository Manager", "localhost", "deployment", "deployment123")

scalacOptions += "-target:jvm-1.8"
