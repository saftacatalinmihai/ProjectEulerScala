def div1(n: Int, s: Int = 10, seq: List[(Int, Int)] = List()): List[Int] = {
  if (s % n == 0) List()
  else {
    val nn = s / n
    val ns  = 10 * (s - ( nn * n))
    if (seq.map(_._2).contains(ns)){
      seq.map(_._1).reverse
    } else
    div1(n, ns, (nn, ns) :: seq )
  }
}

div1(101)