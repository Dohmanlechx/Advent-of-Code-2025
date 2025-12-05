fun main() {
    require(solveP1() == 558)
    require(solveP2() == 344813017450467)
}

private fun solveP1(): Int {
    val input = Reader.input("d05", split = "\n\n")
    val pairs = input[0].toPairs()
    val ids = input[1].split("\n").map(String::toLong)
    return ids.count { id -> pairs.any { id >= it.first && id <= it.second } }
}

private fun solveP2(): Long {
    val input = Reader.input("d05", split = "\n\n")
    val pairs = input[0].toPairs().sortedBy { it.first }
    var res = 0L
    var checked = 0L

    for (pair in pairs) {
        if (pair.second > checked) {
            res += pair.second - maxOf(pair.first, checked + 1) + 1
            checked = pair.second
        }
    }

    return res
}

private fun String.toPairs() =
    split("\n").map { Pair(it.substringBefore("-").toLong(), it.substringAfter("-").toLong()) }