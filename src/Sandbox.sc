
def pow_5_sum_eq_num (l: List[Int]): Boolean =
  listToNumber(l) == l.foldLeft(0)((a, b) => a + Math.pow(b, 5).toInt)

def listToNumber(l: List[Int]): Int =
  l.foldLeft((0 ,l.size - 1 )){
    (acc, n) => {
      val (sum, pow) = acc
      (n * Math.pow(10, pow).toInt + sum, pow - 1)
    }
  }._1

1 to 5
