fun main() {
    require(solve(false) == 1367)
    require(solve(true) == 9144)
}

private fun solve(p2: Boolean): Int {
    val grid = Reader.input("d04").reversed().map { it.toCharArray() }.toTypedArray()
    val max = grid.lastIndex
    var res = 0

    do {
        var shouldContinue = false
        for (x in 0..max) {
            for (y in 0..max) {
                if (grid[x][y] != '@') {
                    continue
                } else {
                    var adj = 0
                    if (y < max && grid[x][y + 1] == '@') adj++
                    if (y < max && x < max && grid[x + 1][y + 1] == '@') adj++
                    if (x < max && grid[x + 1][y] == '@') adj++
                    if (y > 0 && x < max && grid[x + 1][y - 1] == '@') adj++
                    if (y > 0 && grid[x][y - 1] == '@') adj++
                    if (x > 0 && y > 0 && grid[x - 1][y - 1] == '@') adj++
                    if (x > 0 && grid[x - 1][y] == '@') adj++
                    if (x > 0 && y < max && grid[x - 1][y + 1] == '@') adj++
                    if (adj < 4) {
                        res++
                        if (p2) {
                            grid[x][y] = '.'
                            shouldContinue = true
                        }
                    }
                }
            }
        }
    } while (shouldContinue)

    return res
}