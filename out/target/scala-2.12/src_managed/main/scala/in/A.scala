package in

import com.github.dmytromitin.auxify.meta.aux

import scala.annotation.StaticAnnotation

@main
object A {
  println("hi")
}


class B[T] {
  type U
}
object B { type Aux[T, U0$meta$2] = B[T] { type U = U0$meta$2 } }

class main extends StaticAnnotation