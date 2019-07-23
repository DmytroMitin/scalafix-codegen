# scalafix-codegen

```sbtshell
[IJ]sbt:scalafix-codegen> out/compile
[info] Compiling 1 Scala source to /media/data/Projects/scalafix-codegen1/scalafix-codegen/in/target/scala-2.12/classes ...
[info] Done compiling.
[info] Compiling 1 Scala source to /media/data/Projects/scalafix-codegen1/scalafix-codegen/out/target/scala-2.12/classes ...
[info] Done compiling.
[success] Total time: 11 s, completed 23.07.2019 3:38:13
[IJ]sbt:scalafix-codegen> out/run
[info] Packaging /media/data/Projects/scalafix-codegen1/scalafix-codegen/out/target/scala-2.12/out_2.12-0.1.0-SNAPSHOT.jar ...
[info] Done packaging.
[info] Running A 
hi
[success] Total time: 2 s, completed 23.07.2019 3:40:45
```

Example how to use scalafix for code generation.  Run the following command in
sbt:

```
out/compile
```

- triggers a source generator that runs `in/scalafix Scalafixdemo --rules=file:rules/src/main/scala/Scalafixdemo.scala
  --out-from=in/src/main --out-to=out/target/scala-2.12/src_managed/main`
- generates a new file
  `out/target/scala-2.12/src_managed/main/scala/foo/A.scala` which is a copy
  of the same file from the `in` project with the `Scalafixdemo` rule
applied.

To customize the transformation, implement a Scalafix rule following
this tutorial here: https://scalacenter.github.io/scalafix/docs/developers/tutorial.html

Note that generating source code from manually edited code has several issues:

- goto definition and other navigation in the editor jumps to the generated
  source code instead of the original source code
- stack traces at runtime have line numbers from the generated source code
  instead of the original source code.

