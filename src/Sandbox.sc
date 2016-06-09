def isPrime(n: Int): Boolean = (2 until Math.sqrt(n).toInt) forall (n % _ != 0)
val l = for {
  a <- -80 to 1
  b <- 1 to 1603
} yield {
  Stream.from(0).map(n => (a, b, n * n + a * n + b)).filter(_._3 > 1).takeWhile(t => isPrime(t._3))
}
val (a, b, _) = l.view
  .filter(_.nonEmpty)
  .maxBy(_.length)
  .head

a * b