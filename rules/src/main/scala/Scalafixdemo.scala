import scalafix.v1._
import scala.meta._

class Scalafixdemo extends SemanticRule("Scalafixdemo") {

  override def description: String = "Expand annotation @main"

  override def isRewrite: Boolean = true

  val isMain: Mod => Boolean = { case mod"@main" => true; case _ => false }

  override def fix(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect {
      case q"..$mods object $ename extends { ..$stats } with ..$inits { $self => ..$stats1 }" if mods.exists(isMain) =>
        val tree1 = q"""
            ..${mods.filterNot(isMain)} object $ename extends { ..$stats } with ..$inits { $self =>
              def main(args: Array[String]): Unit = {
                ..$stats1
              }
            }"""
        Patch.replaceTree(doc.tree, tree1.toString)
    }.asPatch
  }
}
