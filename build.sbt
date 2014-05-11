import play.Project._

name := "social_platfone"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.clever-age"          % "play2-elasticsearch"		% "0.8.2",
  "com.google.code.gson"	% "gson"					% "2.2.4"
)

play.Project.playJavaSettings

lazy val social_platfone = project.in(file("."))
    .aggregate(jobLibs)
    .dependsOn(jobLibs)

lazy val jobLibs = project