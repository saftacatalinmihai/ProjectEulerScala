val fibs: Stream[Int] = 1 #:: 1 #:: (fibs zip fibs.tail).map{ t => t._1 + t._2 }

fibs.find(_.toString.length == 3).get