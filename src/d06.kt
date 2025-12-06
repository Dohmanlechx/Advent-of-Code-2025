fun main() {
    require(solveP1() == 6209956042374)
    require(solveP2() == 12608160008022)
}

private fun solveP1(): Long {
    val input = Reader.input("d06")
    val nums = input.dropLast(1).toNumbers()
    val ops = input.last().split(" ")
    val h = nums.count()

    return nums[0].indices.fold(0) { acc, x ->
        val op = ops[x]
        var res = if (op == "+") 0L else 1L

        for (y in 0..<h) {
            val num = nums[y][x]
            when (op) {
                "+" -> res += num
                "*" -> res *= num
            }
        }

        acc + res
    }
}

private fun solveP2(): Long {
    val input = Reader.input("d06", trim = false)
    val digits = mutableListOf<Long>()
    var w = input.maxOf(String::count) - 1
    var res = 0L

    while (w >= 0) {
        val chars = input.map { it.getOrNull(w) }
        val last = chars.last()
        digits.add(chars.toDigit())

        if (last == '+' || last == '*') {
            when (last) {
                '+' -> res += digits.sum()
                '*' -> res += digits.fold(1L) { acc, n -> acc * n }
            }
            digits.clear()
            w--
        }
        w--
    }

    return res
}

private fun List<String>.toNumbers() =
    map { it.split(" ").map(String::toLong) }

private fun List<Char?>.toDigit() =
    filterNotNull().filter(Char::isDigit).joinToString("").trim().toLong()