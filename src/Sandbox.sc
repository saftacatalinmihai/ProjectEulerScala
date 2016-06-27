def triangle(n: Int): Int = (0.5 * n * (n + 1)).toInt
val triangleNums : Stream[Int] = Stream.from(1).map(triangle)

triangleNums.take(4).foreach(println)