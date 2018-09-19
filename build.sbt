lazy val `policeIncidents` = (project in file(".")).
  settings(
    name := "policeIncidents",
    organization:= "com.stratesys.police.incidents",
    version := "1.0.0-00-SNAPSHOT",
    scalaVersion := "2.11.8",
    mainClass in Compile := Some("myPackage.IncidentsMain")
  )

// disable using the Scala version in output paths and artifacts
crossPaths := false

//option to avoid warnings
updateOptions := updateOptions.value.withLatestSnapshots(false)

parallelExecution in Test := false

val sparkVersion = "2.3.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
  "org.elasticsearch" % "elasticsearch-hadoop" % "6.4.0",
  "com.typesafe" % "config" % "1.3.1",
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "junit" % "junit" % "4.12" % "test",
  "org.scalatest" %% "scalatest" % "2.2.2" % Test
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

//artifact in (Compile, assembly) := {
// val art = (artifact in (Compile, assembly)).value
//  art.copy(`classifier` = Some("assembly"))
//}

addArtifact(artifact in (Compile, assembly), assembly)

sources in (Compile,doc) := Seq.empty
publishArtifact in (Compile, packageDoc) := false