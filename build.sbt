lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(
    organization := "tf.bug",
    name := "dirs",
    version := "0.1.0",
    scalaVersion := "2.13.10",
  )
  .jvmSettings(
    scalacOptions ++= Seq(
      "-release", "19",
      "-target:19",
    ),
    javacOptions ++= Seq(
      "--release", "19",
    ),
  )

lazy val coreJVM = core.jvm
lazy val coreJS = core.js
lazy val coreNative = core.native

lazy val test = project
  .in(file("test"))
  .settings(
    organization := "tf.bug",
    name := "dirs-test",
    version := "0.1.0",
    scalaVersion := "2.13.10",
  ).dependsOn(coreJVM)
