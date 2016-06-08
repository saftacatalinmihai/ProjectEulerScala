List(2,3,5,7).slice(0, 1)
lazy val primes: Stream[Int] = 2 #:: primes.map(a => {
  Stream.from(a + 1)
    .find(b => primes.takeWhile(c => c * c <= b).forall(b % _ != 0)).get
})
def getSum(p: Int, fromIdx: Int, toIdx: Int): List[Int] = {
  if ( fromIdx > toIdx ) return List()

  val primesFromTo = primes.slice(fromIdx, fromIdx + toIdx)
  val sumPrimes = primesFromTo.sum
  if ( sumPrimes == p) primesFromTo.toList
  else if (sumPrimes < p) getSum(p, fromIdx, toIdx + 1)
  else getSum(p, fromIdx + 1, toIdx)
}

def isPrime(n: Int): Boolean = (2 until n) forall (n % _ != 0)
Stream.from(1)
  .map(idx =>
    primes
      .sliding(idx)
        .map(s => (s.sum, s))
      .takeWhile(_._1 < 10000)
      .toList)
  .takeWhile(_.nonEmpty)
  .flatten
  .filter(ps => isPrime(ps._1))
  .maxBy(_._2.length)
  ._1
