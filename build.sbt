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
          .toTask(s" Scalafixdemo --rules=file:rules/src/main/scala/Scalafixdemo.scala --out-from=$outFrom --out-to=$outTo")
          .value
        (to ** "*.scala").get
      }
    }.taskValue
  )
