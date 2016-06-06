val alph = 'A' to 'Z'
val alphMap = alph.zip(1 to alph.length).toMap
def alphabetical_val(n: String) = {
  n.toCharArray.map(alphMap).sum
}
val L = List("ASD", "ABS", "EFG")
alphabetical_val("ASD")

L.map{
  name =>
    alphabetical_val(name) * L.indexOf(name)
}.sum

List("A", "B").indexOf("B")