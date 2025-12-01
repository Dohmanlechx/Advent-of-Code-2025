fun main() {
    require(solve(countZeroes = false) == 1195)
    require(solve(countZeroes = true) == 6770)
}

fun solve(countZeroes: Boolean): Int {
    val moves = Reader.input("d01")
    var dial = 50
    var password = 0

    for (move in moves) {
        val dir = move[0]
        val num = move.substring(1).toInt()

        repeat(num) {
            dial += (if (dir == 'L') -1 else 1)
            dial = dial.wrapped
            if (countZeroes && dial == 0) {
                password += 1
            }
        }
        if (!countZeroes && dial == 0) {
            password += 1
        }
    }

    return password
}


val Int.wrapped: Int
    get() {
        val range = 100
        var offset = this % range
        if (offset < 0) {
            offset += range
        }
        return offset
    }