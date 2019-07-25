package in

import com.github.dmytromitin.auxify.meta.aux

import scala.annotation.StaticAnnotation

@main
object A {
  println("hi")
}

@aux
class B[T] {
  type U
}

class main extends StaticAnnotation