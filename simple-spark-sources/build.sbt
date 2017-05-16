
organization := "generated"

name := "simple-spark"

version := "0.0.1-SNAPSHOT"
// append scala version to artifact name(s)
crossPaths := true

scalaVersion := "2.10.6"

maintainer := "" //Docker

resolvers ++= Seq(
 "Maven2 Local" at "file:/Users/vidma-vinted/.m2/repository/" ,
 "public" at "https://repo1.maven.org/maven2/" ,
 "spark-packages" at "http://dl.bintray.com/spark-packages/maven/" ,
 new sbt.URLRepository("typesafe-ivy-releases", new sbt.Patterns(
              List("https://repo.typesafe.com/typesafe/ivy-releases/[organisation]/[module]/(scala_[scalaVersion]/)(sbt_[sbtVersion]/)[revision]/[type]s/[artifact](-[classifier]).[ext]"),
              List("https://repo.typesafe.com/typesafe/ivy-releases/[organisation]/[module]/(scala_[scalaVersion]/)(sbt_[sbtVersion]/)[revision]/[type]s/[artifact](-[classifier]).[ext]"),
              false
            )
            ) ,
 "jcenter" at "https://jcenter.bintray.com/" ,
 "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases" )

net.virtualvoid.sbt.graph.Plugin.graphSettings

enablePlugins(UniversalPlugin)

enablePlugins(DockerPlugin)

enablePlugins(JavaAppPackaging)

import com.typesafe.sbt.SbtNativePackager.autoImport.NativePackagerHelper._

import com.typesafe.sbt.packager.docker._

dockerBaseImage := "data-fellas-docker-public.bintray.io/base-adst:0.0.1"

dockerExposedPorts := Seq(9000, 9443)

daemonUser in Docker := "root"

packageName in Docker := "generated.simplespark"

mappings in Docker ++= directory("spark-lib")

mappings in Universal ++= directory("spark-lib")

resolvers += Resolver.mavenLocal

resolvers += Resolver.typesafeRepo("releases")

resolvers += "cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos"



credentials += Credentials(Path.userHome / ".bintray" / ".credentials")

resolvers += Resolver.url("bintray-data-fellas-maven", url("http://dl.bintray.com/data-fellas/maven"))(Resolver.ivyStylePatterns)

dockerCommands ++= Seq(Cmd("ENV", "SPARK_HOME \"\""))

dockerRepository := Some("") //Docker

enablePlugins(DebianPlugin)

name in Debian := "simple-spark"

maintainer in Debian := "Data Fellas"

packageSummary in Debian := "Data Fellas Generated Job"

packageDescription := "Generated Job by Spark-notebook"

debianPackageDependencies in Debian += "java8-runtime-headless"

serverLoading in Debian := com.typesafe.sbt.packager.archetypes.ServerLoader.Upstart

daemonUser in Linux := "root"

daemonGroup in Linux := "root"

bashScriptExtraDefines += "export SPARK_HOME=\"\""




val sparkVersion  = sys.env.get("SPARK_VERSION") .orElse(sys.props.get("spark.version")) .getOrElse("2.0.1")

val hadoopVersion = sys.env.get("HADOOP_VERSION").orElse(sys.props.get("hadoop.version")).getOrElse("2.2.0")

// TODO: needed only if you use some of spark-notebook code
// (most likely you don't want to use this, otherwise  you'd need to publishLocal the SN libs)
// libraryDependencies += "io.kensu" %% "common" % (sparkVersion + "_0.8.0-SNAPSHOT") excludeAll(
//    ExclusionRule("org.apache.hadoop"),
//    ExclusionRule("org.apache.spark")
// )

libraryDependencies += "com.typesafe" % "config" % "1.3.1"

// you might not need all of the Spark jars below
libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion  excludeAll(
    ExclusionRule("org.apache.hadoop"),
    ExclusionRule("org.apache.ivy", "ivy")
  )

libraryDependencies += "org.apache.spark" %% "spark-mllib" % sparkVersion  excludeAll(
    ExclusionRule("org.apache.hadoop"),
    ExclusionRule("org.apache.ivy", "ivy")
  )

libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion  excludeAll(
  ExclusionRule("org.apache.hadoop")
)

libraryDependencies += "org.apache.spark" %% "spark-yarn" % sparkVersion  excludeAll(
  ExclusionRule("org.apache.hadoop"),
  ExclusionRule("org.apache.ivy", "ivy")
  )

libraryDependencies += "org.apache.spark" %% "spark-hive" % sparkVersion   excludeAll(
    ExclusionRule("org.apache.hadoop"),
    ExclusionRule("org.apache.ivy", "ivy"),
    ExclusionRule("javax.servlet", "servlet-api"),
    ExclusionRule("org.mortbay.jetty", "servlet-api")
  )

libraryDependencies += "org.apache.hadoop" % "hadoop-client" % hadoopVersion   excludeAll(
    ExclusionRule("org.apache.commons", "commons-exec"),
    ExclusionRule("commons-codec", "commons-codec"),
    ExclusionRule("com.google.guava", "guava"),
    ExclusionRule("javax.servlet")
  )

libraryDependencies += "org.apache.hadoop" % "hadoop-yarn-server-web-proxy" % hadoopVersion   excludeAll(
      ExclusionRule("org.apache.commons", "commons-exec"),
      ExclusionRule("commons-codec", "commons-codec"),
      ExclusionRule("com.google.guava", "guava"),
      ExclusionRule("javax.servlet")
  )

libraryDependencies += "net.java.dev.jets3t" % "jets3t" % "0.9.0" force()

libraryDependencies += "com.google.guava" % "guava" % "16.0.1" force()



//asssembly
// skip test during assembly
test in assembly := {}

//main class
mainClass in assembly := Some("generated.Main")

artifact in (Compile, assembly) ~= { art =>
  art.copy(`classifier` = Some("assembly"))
}



// merging files... specially application.conf!
assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet",          xs @ _*) => MergeStrategy.first
  case PathList("org",   "apache",           xs @ _*) => MergeStrategy.first
  case PathList("org",   "fusesource",       xs @ _*) => MergeStrategy.first
  case PathList("org",   "slf4j",            xs @ _*) => MergeStrategy.first
  case PathList("com",   "google",           xs @ _*) => MergeStrategy.first
  case PathList("play",  "core",             xs @ _*) => MergeStrategy.first
  case PathList("javax", "xml",              xs @ _*) => MergeStrategy.first
  case PathList("com",   "esotericsoftware", xs @ _*) => MergeStrategy.first
  case PathList("xsbt",                      xs @ _*) => MergeStrategy.first
  case PathList("META-INF", "MANIFEST.MF"           ) => MergeStrategy.discard
  case PathList("META-INF",                  xs @ _*) => MergeStrategy.first
  case "application.conf"                             => MergeStrategy.concat
  case "module.properties"                             => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html"  => MergeStrategy.discard
  case PathList(ps @ _*) if ps.last endsWith ".thrift"  =>  MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".xml"  =>  MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

aggregate in update := false

updateOptions := updateOptions.value.withCachedResolution(true)

