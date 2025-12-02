fun main() {
    solve(false)
    solve(true)
}

private fun solve(p2: Boolean) {
    val ranges = Reader.input("d02", ",")
        .map { LongRange(it.substringBefore("-").toLong(), it.substringAfter("-").toLong()) }

    var res = 0L

    for (range in ranges) {
        for (number in range) {
            val numberAsString = number.toString()
            val numberLength = numberAsString.length

            for (i in 2..(if (p2) numberLength else 2)) {
                if ((numberLength / i.toDouble()) % 1 != 0.0) {
                    continue
                }

                val split = numberLength / i
                val ref = numberAsString.take(split)

                if (numberAsString.chunked(split).all { it == ref }) {
                    res += number
                    break
                }
            }
        }
    }

    require((!p2 && res == 12599655151) || (p2 && res == 20942028255))
}