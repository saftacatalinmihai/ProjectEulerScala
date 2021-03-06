import scala.io.Source
import scala.language.postfixOps

/**
  * Created by casafta on 26/5/2016.
  */

object ProjectEuler {

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) + " ns")
    result
  }

  def nextPrimeSieve(primes: List[Int]): Int = {
    if (primes.isEmpty) 2
    else {
      def _nextPrime(candidate: Int): Int = {
        if (primes.forall(candidate % _ != 0)) candidate
        else _nextPrime(candidate + 1)
      }
      _nextPrime(primes.head + 1)
    }
  }

  lazy val primes: Stream[Int] = 2 #:: primes.map(a => {
    Stream.from(a + 1)
      .find(b => primes.takeWhile(c => c * c <= b).forall(b % _ != 0)).get
  })

  def getPrimesUntil(until: Int) = {
    primes.takeWhile(_ < until)
  }

  def factors( n: Int) : List[Int] = {
    n :: ( 1 to n / 2 reverse).filter(n % _ == 0).toList
  }

  def factorsLong( n: Long) : List[Long] = {
    n :: (1L to n / 2 reverse).filter(n % _ == 0).toList
  }

  def numOfFactors(n: Long): Int = 2 * (1L to Math.sqrt(n).toInt).count(n % _ == 0)

  def divideInt(n: Int, p: Int): Int =
    if (n % p != 0) n
    else divideInt(n / p, p)

  def divide(n: Long, p: Int): Long =
    if (n % p != 0) n
    else divide(n / p, p)

  def projectEuler1 = {
    Stream.from(1).filter(i => i % 3 == 0 || i % 5 == 0).takeWhile(_ < 1000).sum
  }

  def projectEuler2 = {

    def fibSeq(maxTerm: Int, _fibSeq: List[Int] = List(1,1)): List[Int] = {
      val nextTerm =  _fibSeq.take(2).sum
      if ( nextTerm >= maxTerm ) _fibSeq
      else fibSeq(maxTerm, nextTerm :: _fibSeq)
    }

    fibSeq(4000000 - 1).filter(_ % 2 == 0).sum
  }

  def projectEuler3 = {

    def largestPrimeFactor(n: Long): Int = {
      def iterate(n: Long, primes: List[Int]): Int = {
         if (n <= primes.head) primes.head
         else if (n % primes.head == 0) iterate(divide(n, primes.head),  nextPrimeSieve(primes) :: primes)
         else iterate(n, nextPrimeSieve(primes) :: primes)
      }

      iterate(n, List(2))
    }
    largestPrimeFactor( 600851475143L )
  }

  def projectEuler4 = {

    def findPal(): Int = {
      val palList = for {
        i <- (100 to 999).reverse
        j <- (100 to 999).reverse
        prod = i * j
        if prod.toString.reverse == prod.toString
      } yield prod

      palList.max
    }

    findPal()
  }

  def projectEuler5 = {
    Stream
      .from(20)
      .filter(n => (1 to 20).forall(n % _ == 0))
      .take(1)
      .head
  }

  def projectEuler6 = {
    val sumOfSq = (1 to 100).map(x => x * x).sum
    val sum = (1 to 100).sum
    val sqOfSum = sum * sum
    sqOfSum - sumOfSq
  }

  def projectEuler7 = {
    (1 to 10000).foldLeft(List(2)){
      (primeList, idx) => nextPrimeSieve(primeList) :: primeList
    }.head
  }

  def projectEuler8 = {
    val longNum = "73167176531330624919225119674426574742355349194934\n96983520312774506326239578318016984801869478851843\n85861560789112949495459501737958331952853208805511\n12540698747158523863050715693290963295227443043557\n66896648950445244523161731856403098711121722383113\n62229893423380308135336276614282806444486645238749\n30358907296290491560440772390713810515859307960866\n70172427121883998797908792274921901699720888093776\n65727333001053367881220235421809751254540594752243\n52584907711670556013604839586446706324415722155397\n53697817977846174064955149290862569321978468622482\n83972241375657056057490261407972968652414535100474\n82166370484403199890008895243450658541227588666881\n16427171479924442928230863465674813919123162824586\n17866458359124566529476545682848912883142607690042\n24219022671055626321111109370544217506941658960408\n07198403850962455444362981230987879927244284909188\n84580156166097919133875499200524063689912560717606\n05886116467109405077541002256983155200055935729725\n71636269561882670428252483600823257530420752963450"
    val chars = longNum.replace("\n","").split("""|\n""").map(_.toLong)
    val slices = chars.sliding(13)
    slices.map(a => a.product ).toList.max
  }

  def projectEuler9 = {
    val m = 1000
    val found = for {
      a <- 0 to m
      b <- a + 1 to m
      c <- b + 1 to m
      if ((a * a) + (b * b)) == c * c && a + b + c == 1000
    } yield (a, b, c)
    found.head._1 * found.head._2 * found.head._3
  }

  def projectEuler10 = {
    getPrimesUntil(2000000).foldLeft(0L)(_+_)
  }

  def projectEuler11 = {

    val grid =
      """08 02 22 97 38 15 00 40 00 75 04 05 07 78 52 12 50 77 91 08
        |49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 04 56 62 00
        |81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 03 49 13 36 65
        |52 70 95 23 04 60 11 42 69 24 68 56 01 32 56 71 37 02 36 91
        |22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80
        |24 47 32 60 99 03 45 02 44 75 33 53 78 36 84 20 35 17 12 50
        |32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70
        |67 26 20 68 02 62 12 20 95 63 94 39 63 08 40 91 66 49 94 21
        |24 55 58 05 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72
        |21 36 23 09 75 00 76 44 20 45 35 14 00 61 33 97 34 31 33 95
        |78 17 53 28 22 75 31 67 15 94 03 80 04 62 16 14 09 53 56 92
        |16 39 05 42 96 35 31 47 55 58 88 24 00 17 54 24 36 29 85 57
        |86 56 00 48 35 71 89 07 05 44 44 37 44 60 21 58 51 54 17 58
        |19 80 81 68 05 94 47 69 28 73 92 13 86 52 17 77 04 89 55 40
        |04 52 08 83 97 35 99 16 07 97 57 32 16 26 26 79 33 27 98 66
        |88 36 68 87 57 62 20 72 03 46 33 67 46 55 12 32 63 93 53 69
        |04 42 16 73 38 25 39 11 24 94 72 18 08 46 29 32 40 62 76 36
        |20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 04 36 16
        |20 73 35 29 78 31 90 01 74 31 49 71 48 86 81 16 23 57 05 54
        |01 70 54 71 83 51 54 69 16 92 33 48 61 43 52 01 89 19 67 48""".stripMargin

    val intGrid = grid.split("\\r?\\n").map( l => l.split(" ").map(_.toInt).toList).toList
    val rowLen = intGrid.head.length
    val rowNr = intGrid.length

    val prodsDiagRight = for {
      row <- 0 to rowNr - 4
      col <- 0 to rowLen - 4
      (n1, n2, n3, n4) = ( intGrid(row)(col), intGrid(row + 1)(col + 1), intGrid(row + 2)(col + 2), intGrid(row + 3)(col + 3))
    } yield List(n1, n2, n3, n4).product

    val prodsDiagLeft = for {
      row <- 0 to rowNr - 4
      col <- 0 to rowLen - 4
      (n1, n2, n3, n4) = ( intGrid(row)(col + 3), intGrid(row + 1)(col + 2), intGrid(row + 2)(col + 1), intGrid(row + 3)(col))
    } yield List(n1, n2, n3, n4).product

    val prodsLine = for {
      row <- 0 until rowNr
      col <- 0 to rowLen - 4
      (n1, n2, n3, n4) = ( intGrid(row)(col + 3), intGrid(row)(col + 2), intGrid(row)(col + 1), intGrid(row)(col))
    } yield List(n1, n2, n3, n4).product

    val prodsCols = for {
      row <- 0 to rowNr - 4
      col <- 0 until rowLen
      (n1, n2, n3, n4) = ( intGrid(row)(col), intGrid(row +1 )(col), intGrid(row + 2)(col), intGrid(row + 3)(col))
    } yield List(n1, n2, n3, n4).product

    List(prodsDiagRight.max, prodsDiagLeft.max, prodsLine.max, prodsCols.max)
      .max
  }

  def projectEuler12 = {

    Stream
      .from(1)                          // ints
      .scan(0)(_ + _)                   // triangles
      .map(t => t -> numOfFactors(t))   // triangles -> numOfFactors
      .filter(_._2 >= 500)
      .head

  }

  def projectEuler13 = {
    val input =
      """37107287533902102798797998220837590246510135740250
        |46376937677490009712648124896970078050417018260538
        |74324986199524741059474233309513058123726617309629
        |91942213363574161572522430563301811072406154908250
        |23067588207539346171171980310421047513778063246676
        |89261670696623633820136378418383684178734361726757
        |28112879812849979408065481931592621691275889832738
        |44274228917432520321923589422876796487670272189318
        |47451445736001306439091167216856844588711603153276
        |70386486105843025439939619828917593665686757934951
        |62176457141856560629502157223196586755079324193331
        |64906352462741904929101432445813822663347944758178
        |92575867718337217661963751590579239728245598838407
        |58203565325359399008402633568948830189458628227828
        |80181199384826282014278194139940567587151170094390
        |35398664372827112653829987240784473053190104293586
        |86515506006295864861532075273371959191420517255829
        |71693888707715466499115593487603532921714970056938
        |54370070576826684624621495650076471787294438377604
        |53282654108756828443191190634694037855217779295145
        |36123272525000296071075082563815656710885258350721
        |45876576172410976447339110607218265236877223636045
        |17423706905851860660448207621209813287860733969412
        |81142660418086830619328460811191061556940512689692
        |51934325451728388641918047049293215058642563049483
        |62467221648435076201727918039944693004732956340691
        |15732444386908125794514089057706229429197107928209
        |55037687525678773091862540744969844508330393682126
        |18336384825330154686196124348767681297534375946515
        |80386287592878490201521685554828717201219257766954
        |78182833757993103614740356856449095527097864797581
        |16726320100436897842553539920931837441497806860984
        |48403098129077791799088218795327364475675590848030
        |87086987551392711854517078544161852424320693150332
        |59959406895756536782107074926966537676326235447210
        |69793950679652694742597709739166693763042633987085
        |41052684708299085211399427365734116182760315001271
        |65378607361501080857009149939512557028198746004375
        |35829035317434717326932123578154982629742552737307
        |94953759765105305946966067683156574377167401875275
        |88902802571733229619176668713819931811048770190271
        |25267680276078003013678680992525463401061632866526
        |36270218540497705585629946580636237993140746255962
        |24074486908231174977792365466257246923322810917141
        |91430288197103288597806669760892938638285025333403
        |34413065578016127815921815005561868836468420090470
        |23053081172816430487623791969842487255036638784583
        |11487696932154902810424020138335124462181441773470
        |63783299490636259666498587618221225225512486764533
        |67720186971698544312419572409913959008952310058822
        |95548255300263520781532296796249481641953868218774
        |76085327132285723110424803456124867697064507995236
        |37774242535411291684276865538926205024910326572967
        |23701913275725675285653248258265463092207058596522
        |29798860272258331913126375147341994889534765745501
        |18495701454879288984856827726077713721403798879715
        |38298203783031473527721580348144513491373226651381
        |34829543829199918180278916522431027392251122869539
        |40957953066405232632538044100059654939159879593635
        |29746152185502371307642255121183693803580388584903
        |41698116222072977186158236678424689157993532961922
        |62467957194401269043877107275048102390895523597457
        |23189706772547915061505504953922979530901129967519
        |86188088225875314529584099251203829009407770775672
        |11306739708304724483816533873502340845647058077308
        |82959174767140363198008187129011875491310547126581
        |97623331044818386269515456334926366572897563400500
        |42846280183517070527831839425882145521227251250327
        |55121603546981200581762165212827652751691296897789
        |32238195734329339946437501907836945765883352399886
        |75506164965184775180738168837861091527357929701337
        |62177842752192623401942399639168044983993173312731
        |32924185707147349566916674687634660915035914677504
        |99518671430235219628894890102423325116913619626622
        |73267460800591547471830798392868535206946944540724
        |76841822524674417161514036427982273348055556214818
        |97142617910342598647204516893989422179826088076852
        |87783646182799346313767754307809363333018982642090
        |10848802521674670883215120185883543223812876952786
        |71329612474782464538636993009049310363619763878039
        |62184073572399794223406235393808339651327408011116
        |66627891981488087797941876876144230030984490851411
        |60661826293682836764744779239180335110989069790714
        |85786944089552990653640447425576083659976645795096
        |66024396409905389607120198219976047599490197230297
        |64913982680032973156037120041377903785566085089252
        |16730939319872750275468906903707539413042652315011
        |94809377245048795150954100921645863754710598436791
        |78639167021187492431995700641917969777599028300699
        |15368713711936614952811305876380278410754449733078
        |40789923115535562561142322423255033685442488917353
        |44889911501440648020369068063960672322193204149535
        |41503128880339536053299340368006977710650566631954
        |81234880673210146739058568557934581403627822703280
        |82616570773948327592232845941706525094512325230608
        |22918802058777319719839450180888072429661980811197
        |77158542502016545090413245809786882778948721859617
        |72107838435069186155435662884062257473692284509516
        |20849603980134001723930671666823555245252804609722
        |53503534226472524250874054075591789781264330331690""".stripMargin

    input.split("\\r?\\n").foldLeft(BigDecimal(0))(_ + BigDecimal(_))
  }

  def projectEuler14 = {
    def nextCollatz(n: Long): Long =
      if ( n % 2 == 0 ) n / 2
      else 3 * n + 1

    def getCount(n: Long, count: Int = 1): Int =
      if (n == 1) count
      else getCount( nextCollatz(n), count + 1 )

    (1 to 1000000)
      .map(n => (n, getCount(n)))
      .reduceLeft((x, y) => if (x._2 > y._2) x else y)
      ._1
  }

  def projectEuler15 = {
    val squareLen = 20

    lazy val pascalTriangleRowStream: Stream[List[Long]] =
      List(1L, 1L) #:: pascalTriangleRowStream.map( pascalLine =>
        1L :: pascalLine.sliding(2).map(_.sum).toList ::: List(1L)
      )

    val pascalRow = pascalTriangleRowStream.take(squareLen * 2).last
    pascalRow(pascalRow.length / 2)

  }

  def projectEuler16 = BigInt(2).pow(1000).toString().map(_.asDigit).sum

  def projectEuler17 = {

    // 1 - 19
    var numToStr: Map[Int, String] = Map(
      1 -> "one",
      2 -> "two",
      3 -> "three",
      4 -> "four",
      5 -> "five",
      6 -> "six",
      7 -> "seven",
      8 -> "eight",
      9 -> "nine",
      10 -> "ten",
      11 -> "eleven",
      12 -> "twelve",
      13 -> "thirteen",
      14 -> "fourteen",
      15 -> "fifteen",
      16 -> "sixteen",
      17 -> "seventeen",
      18 -> "eighteen",
      19 -> "nineteen",
      20 -> "twenty",
      30 -> "thirty",
      40 -> "forty",
      50 -> "fifty",
      60 -> "sixty",
      70 -> "seventy",
      80 -> "eighty",
      90 -> "ninety",
      1000 -> "one thousand"
    )

    // 20 - 99
    (2 to 9).foreach { dec =>
      ( (dec * 10 + 1) to (dec * 10 + 9)).foreach( i => numToStr += (i -> (numToStr(dec * 10) + " " + numToStr(i - dec * 10) )))
    }

    // 100 - 900 (just multiples of 100)
    ( 1 to 9 ).foreach( i => numToStr += ( i * 100 -> (numToStr(i) + " hundred")))

    // 101 - 999
    (101 to 999).filter(_ % 100 != 0).foreach( i => numToStr += ( i -> (numToStr(i / 100 * 100 ) + " and " + numToStr(i % 100))))

    // Calc
    (1 to 1000)
      .map(numToStr)
      .map(_.replace(" ", ""))
      .map(_.length)
      .sum
  }

  def maxPathInTriangle( triangle: List[List[Int]]) =
    triangle.reverse.reduce(
      (below, above) => {
        above.indices.map(
          i =>
            if (below(i) > below(i + 1)) above(i) + below(i)
            else above(i) + below(i + 1)
        ).toList
      }
    ).head

  def projectEuler18 = {
    // TODO: optimise for problem 67
    // solution : from bottom to top, change top row with max sum with bottom row
    val triangle =
    """75
        |95 64
        |17 47 82
        |18 35 87 10
        |20 04 82 47 65
        |19 01 23 75 03 34
        |88 02 77 73 07 63 67
        |99 65 04 28 06 16 70 92
        |41 41 26 56 83 40 80 70 33
        |41 48 72 33 47 32 37 16 94 29
        |53 71 44 65 25 43 91 52 97 51 14
        |70 11 33 28 77 73 17 78 39 68 17 57
        |91 71 52 38 17 14 91 43 58 50 27 29 48
        |63 66 04 68 89 53 67 30 73 16 69 87 40 31
        |04 62 98 27 23 09 70 98 73 93 38 53 60 04 23""".stripMargin.split("\\r?\\n").map(_.split(" ").map(_.toInt).toList)

    maxPathInTriangle(triangle.toList)
  }

  def projectEuler19 = {
    val monthToString: Map[Int, String] = Map(
      1 -> "January",
      2 -> "February",
      3 -> "March",
      4 -> "April",
      5 -> "May",
      6 -> "June",
      7 -> "July",
      8 -> "August",
      9 -> "September",
      10 -> "October",
      11 -> "November",
      12 -> "December"
    )

    object Date{
      def isLeapYear(d: Date): Boolean =
        if ( d.year % 100 == 0) {
          if (d.year % 400 == 0 ) true
          else false
        } else if (d.year % 4 == 0) true
        else false
    }
    class Date(val day: Int, val month: Int, val year: Int) {
      override def toString = s"$day " + monthToString(month) + s" $year"
      def next: Date =
        if (month == 12 && day == 31) new Date(1, 1, year + 1)
        else if ( day == 30 && List("September", "April", "June", "November").contains(monthToString(month)) ) new Date(1, month + 1, year)
        else if ( ! Date.isLeapYear(this) && month == 2 && day == 28 ) new Date(1, month + 1, year)
        else if ( Date.isLeapYear(this) && month == 2 && day == 29 ) new Date(1, month + 1, year)
        else if ( day == 31 ) new Date(1, month + 1, year)
        else new Date(day + 1, month, year)
      def equals(other: Date): Boolean = other.day == day && other.month == month && other.year == year
    }

    class Weekday
    case class Monday() extends Weekday
    case class Tuesday() extends Weekday
    case class Wednesday() extends Weekday
    case class Thursday() extends Weekday
    case class Friday() extends Weekday
    case class Saturday() extends Weekday
    case class Sunday() extends Weekday

    object Weekday {
      def next(w: Weekday): Weekday = w match {
        case w: Monday => new Tuesday()
        case w: Tuesday => new Wednesday()
        case w: Wednesday => new Thursday()
        case w: Thursday => new Friday()
        case w: Friday => new Saturday()
        case w: Saturday => new Sunday()
        case w: Sunday => new Monday()
      }
    }

    lazy val dateStream: Stream[Map[Date, Weekday]] = Map(new Date(1, 1, 1900) -> new Monday()) #:: dateStream.map( m => Map(m.keys.head.next -> Weekday.next(m.values.head)))

    dateStream.takeWhile(m => !m.keys.head.equals(new Date(1, 1, 2001)))
      .filter(m => m.keys.head.year != 1900)
      .count(m => {
        val (date, week) = (m.keys.head, m.values.head)
        week match {
          case w: Sunday => if (date.day == 1) true else false
          case _ => false
        }
      })
  }

  def projectEuler22 = {
    val names = scala.io.Source.fromFile("p022_names.txt").mkString.split(",").map(_.replace("\"", "")).sorted.toList
    val alphabet = 'A' to 'Z'
    val alphabetIndex = alphabet.zip(1 to alphabet.length).toMap
    def alphabeticalValue(n: String) = {
      n.toCharArray.map(alphabetIndex).sum
    }
    names.map{
      name =>
        alphabeticalValue(name) * (names.indexOf(name) + 1)
    }.sum
  }

  def projectEuler24 = {
    val n = List("0", "1", "2", "3", "4", "5", "6", "7", "8", "9").permutations
    n.take(1000000).toList(999999).mkString
  }

  def projectEuler25 = {
    lazy val fibs: Stream[(BigInt, Int)] = (BigInt(1),1) #:: (BigInt(1),2) #:: (fibs zip fibs.tail).map{ t => (t._1._1 + t._2._1, t._2._2 + 1) }
    fibs.find(_._1.toString.length == 1000).get._2
  }

  def projectEuler26 = {
    def div(by: Int, to: Int = 10, seq: List[(Int, Int)] = List()): List[Int] = {
      if (to % by == 0) return List()
      val nextDecimal = to / by
      val toNext  = 10 * (to - ( nextDecimal * by))
      if (seq.map(_._2).contains(toNext)) seq.map(_._1).reverse
      else div(by, toNext, (nextDecimal, toNext) :: seq )
    }

    (2 to 1000).maxBy(div(_).length)
  }

  def projectEuler27 = {
    def isPrime(n: Int): Boolean = (2 until Math.sqrt(n).toInt) forall (n % _ != 0)

    val consecutivePrimes = for {
      a <- -999 to 1000
      b <- primes.takeWhile(_<1000)
    } yield {
      Stream.from(1)
        .map(n => (a, b, n * (a + n) + b))
        .filter(_._3 > 1)
        .takeWhile(t => isPrime(t._3))
    }

    val (a, b, _) = consecutivePrimes
      .maxBy(_.length)
      .head

    a * b
  }

  def projectEuler28 = {
    lazy val seq: Stream[(Int, List[Int])] = (2, List(9,7,5,3)) #:: seq.map(t => {
      val next_step = t._1 + 2
      ( next_step,
        (1 to 4).map(i => next_step * i + t._2.head).reverse.toList )
    })

    1 + seq.take((1001 - 1) / 2).map(_._2.sum).sum
  }

  def projectEuler29 = {
    val l = for {
      a <- 2 to 100
      b <- 2 to 100
    } yield math.pow(a, b)

    l.distinct.length
  }

  def projectEuler30 = {
    def pow_5_sum_eq_num (l: List[Int]): Boolean =
      listToNumber(l) == l.foldLeft(0)((a, b) => a + Math.pow(b, 5).toInt)

    def listToNumber(l: List[Int]): Int =
      l.foldLeft((0 ,l.size - 1 )){
        (acc, n) => {
          val (sum, pow) = acc
          (n * Math.pow(10, pow).toInt + sum, pow - 1)
        }
      }._1

    (2 to 999999)
      .map(_.toString.split("").toList.map(_.toInt))
      .filter(pow_5_sum_eq_num)
      .map(listToNumber)
      .sum
  }

  def projectEuler42 = {
    val words = scala.io.Source.fromFile("p042_words.txt").mkString.split(",").map(w => w.replaceAll("\"", "")).toList

    def triangle(n: Int): Int = (0.5 * n * (n + 1)).toInt
    lazy val triangleNums : Stream[Int] = Stream.from(1).map(triangle)

    val alphabet = 'A' to 'Z'
    val alphabetIndex = alphabet.zip(1 to alphabet.length).toMap
    def alphabeticalValue(n: String) = {
      n.toCharArray.map(alphabetIndex).sum
    }

    words.count {
      w =>
        val wordValue = alphabeticalValue(w)
        triangleNums.takeWhile(_ <= wordValue).contains(wordValue)
    }
  }

  def projectEuler50 = {

    def isPrime(n: Int): Boolean = (2 until Math.sqrt(n).toInt) forall (n % _ != 0)

    Stream.from(21)
      .map(width =>
        primes
          .sliding(width)
          .map(s => (s.sum, s))
          .takeWhile(_._1 < 1000000)
          .toList)
      .takeWhile(_.nonEmpty)
      .flatten
      .filter(ps => isPrime(ps._1))
      .maxBy(_._2.length)
      ._1
  }

  def projectEuler67 = {

    val filename = "p067_triangle.txt"
    val triangle = for (line <- Source.fromFile(filename).getLines()) yield {
      line.split(" ").map(_.toInt).toList
    }
    maxPathInTriangle(triangle.toList)
  }

  def main(args: Array[String]) {

//    assert(projectEuler1 == 233168)
//    assert(projectEuler2 == 4613732)
//    assert(projectEuler3 == 6857)
//    assert(projectEuler4 == 906609)
//    assert(projectEuler5 == 232792560)
//    assert(projectEuler6 == 25164150)
//    assert(projectEuler7 == 104743)
//    assert(projectEuler8 == 23514624000L)
//    assert(projectEuler9 == 31875000)
//    assert(projectEuler10 == 142913828922L)
//    assert(projectEuler11 == 70600674)
//    assert(projectEuler12 == (76576500,576))
//    assert(projectEuler13 == BigDecimal("5537376230390876637302048746832985971773659831892672"))
//    assert(projectEuler14 == 837799)
//    assert(projectEuler15 == 137846528820L)
//    assert(projectEuler16 == 1366)
//    assert(projectEuler17 == 21124)
//    assert(projectEuler18 == 1074)
//    assert(projectEuler67 == 7273)
//    assert(projectEuler19 == 171)
//    assert(projectEuler22 == 871198282)
//    assert(projectEuler24 == "2783915460")
//    assert(projectEuler25 == 4782)
//    assert(projectEuler26 == 983)
//    assert(projectEuler50 == 997651)
//    assert(projectEuler27 == -59231)
//    assert(projectEuler28 == 669171001)
//    assert(projectEuler29 == 9183)
//    assert(projectEuler30 == 443839)
    println(projectEuler42)
  }
}