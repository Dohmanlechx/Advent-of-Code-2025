fun main() {
    require(solveP1() == 1609)
    println(solveP2())
}

private fun solveP1(): Int {
    val grid = Reader.input("d07").reversed().map { it.toCharArray() }.toTypedArray()
    val w = grid[0].size
    val h = grid.size

    var res = 0

    for (y in 0..<h) {
        for (x in 0..<w) {
            if (grid[y][x] == '^' && isSplit(x, y, grid)) {
                res++
            }
        }
    }

    return res
}

private fun solveP2(): Long {
    return 0L
}

private fun isSplit(
    x: Int,
    y: Int,
    grid: Array<CharArray>
): Boolean {
    val w = grid[0].size
    val h = grid.size
    var i = y + 1

    // Move downward (input is reversed) from the current '^' and inspect each row.
    // We stop when we either:
    // 1. Encounter another '^' in the same column -> not a split
    // 2. Encounter a '^' in any adjacent column -> split
    // 3. Encounter an 'S' in the same column -> split
    while (true) {
        // Another '^' directly below before seeing any on the sides -> not split.
        if (i > h - 1 || grid[i][x] == '^') {
            return false
        }
        // A side '^' or an 'S' below tells us the path splits here.
        if (grid[i][x] == 'S' || x > 0 && grid[i][x - 1] == '^' || x < w - 1 && grid[i][x + 1] == '^') {
            return true
        }
        i++
    }
}

