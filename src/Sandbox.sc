lazy val primes: Stream[Int] = 2 #:: 3 #:: primes.map(a => {
  Stream.from(a + 2)
    .find(b => primes.takeWhile(c => c * c <= b).forall(b % _ != 0)).get
})

//val p1 = primes.head
//p1 #:: primes.tail.toIndexedSeq.filter()
primes.distinct.take(10).toList