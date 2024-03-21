// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.0" // your current series x.y

ThisBuild / organization := "tf.bug"
ThisBuild / organizationName := "Aly"
ThisBuild / startYear := Some(2023)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("s5bug", "Aly Cerruti")
)

// publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
ThisBuild / tlSonatypeUseLegacyHost := false

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")

val Scala213 = "2.13.13"
ThisBuild / crossScalaVersions := Seq(Scala213, "3.3.3")
ThisBuild / scalaVersion := Scala213 // the default Scala

ThisBuild / tlJdkRelease := Some(22)
ThisBuild / githubWorkflowJavaVersions := Seq(
  JavaSpec.graalvm("22")
)
ThisBuild / githubWorkflowOSes ++= Seq("macos-latest", "macos-14", "windows-latest")

lazy val root = tlCrossRootProject.aggregate(core)

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(
    name := "dirs",
    libraryDependencies ++= Seq(
      "org.scalameta" %%% "munit" % "1.0.0-M11" % Test,
    )
  )

lazy val docs = project.in(file("site")).enablePlugins(TypelevelSitePlugin)
