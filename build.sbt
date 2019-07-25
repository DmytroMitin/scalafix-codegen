lazy val auxifyV = "0.4"
lazy val auxifyMeta = "com.github.dmytromitin" %% "auxify-meta" % auxifyV
lazy val auxifyMetaCore = "com.github.dmytromitin" %% "auxify-meta-core" % auxifyV

import com.geirsson.coursiersmall.{Repository => R}
scalafixResolvers in ThisBuild += new R.Maven("https://oss.sonatype.org/content/groups/public/")
scalafixDependencies in ThisBuild += auxifyMeta

lazy val V = _root_.scalafix.sbt.BuildInfo

inThisBuild(
  List(
    scalaVersion := V.scala212,
    addCompilerPlugin(scalafixSemanticdb),
    scalacOptions ++= List(
      "-Yrangepos"
    )
  )
)

lazy val rules = project
  .settings(
    libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % V.scalafixVersion
  )

lazy val in = project
  .settings(
    libraryDependencies += auxifyMetaCore
  )

lazy val out = project
  .settings(
    sourceGenerators.in(Compile) += Def.taskDyn {
      val root = baseDirectory.in(ThisBuild).value.toURI.toString
      val from = sourceDirectory.in(in, Compile).value
      val to = sourceManaged.in(Compile).value
      val outFrom = from.toURI.toString.stripSuffix("/").stripPrefix(root)
      val outTo = to.toURI.toString.stripSuffix("/").stripPrefix(root)
      Def.task {
        scalafix
          .in(in, Compile)
          .toTask(s" AuxRule --out-from=$outFrom --out-to=$outTo")
//          .toTask(s" --rules=file:rules/src/main/scala/Scalafixdemo.scala --out-from=$outFrom --out-to=$outTo")
          .value
        (to ** "*.scala").get
      }
    }.taskValue,
    libraryDependencies += auxifyMetaCore
  )
