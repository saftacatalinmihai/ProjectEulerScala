//val n:Stream[Int] = 1 #:: n.map( _ + 2)
//
//n.take(10).toList
//
//val ints = Stream.from(1)
//n.map(n => )


val seq: Stream[(Int, List[Int])] = (2, List(9,7,5,3)) #:: seq.map(t => {
  val nt = t._1 + 2
  val lastN = t._2.head
  val nextL = lastN + 4 * nt :: lastN + 3 * nt :: lastN + 2 * nt :: lastN + nt :: Nil
  (nt, nextL)
})


val n = 5
val nToListLen = (n - 1) / 2
val seqToN = seq.take(nToListLen)
val diag1Sum = seqToN.map(_._2).map(l => l(0) + l(2)).sum
val diag2Sum = seqToN.map(_._2).map(l => l(1) + l(3)).sum
val s = 1 + diag1Sum + diag2Sum