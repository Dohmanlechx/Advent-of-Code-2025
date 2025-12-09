fun main() {
    require(solveP1() == 4750092396L)
    require(solveP2() == 1468516555L)
}

private fun solveP1(): Long {
    val input = Reader.input("d09")
    val tiles = input.map { Tile(it.split(",")[0].toLong(), it.split(",")[1].toLong()) }
    var biggestArea = 0L

    for (a in tiles) {
        for (b in tiles) {
            val side1 = maxOf(a.x, b.x) - minOf(a.x, b.x) + 1
            val side2 = maxOf(a.y, b.y) - minOf(a.y, b.y) + 1
            val area = side1 * side2
            if (area > biggestArea) {
                biggestArea = area
            }
        }
    }

    return biggestArea
}

private fun solveP2(): Long {
    val divide = 300L

    //
    // Since my implementation requires the coordinates to be divided,
    // we keep original coordinates here.
    //
    val originals = mutableMapOf<Long, Pair<Long, Long>>()

    //
    // Parse input into tiles.
    //
    val input = Reader.input("d09")
    val tiles = input
        .mapIndexed { index, it ->
            val x = it.split(",")[0].toLong()
            val y = it.split(",")[1].toLong()
            originals[index.toLong()] = Pair(x, y)
            Tile(x / divide, y / divide, id = index.toLong())
        }
        .associateBy { it.toString() }
        .toMutableMap()

    //
    // Connect the red tiles sequentially.
    //
    for ((a, b) in tiles.values.zipWithNext()) {
        if (a.x == b.x) {
            if (a.y > b.y) {
                for (i in a.y downTo b.y) {
                    val tile = Tile(a.x, i, true)
                    tiles.putIfAbsent(tile.toString(), tile)
                }
            } else {
                for (i in b.y downTo a.y) {
                    val tile = Tile(a.x, i, true)
                    tiles.putIfAbsent(tile.toString(), tile)
                }
            }
        } else {
            if (a.x > b.x) {
                for (i in a.x downTo b.x) {
                    val tile = Tile(i, a.y, true)
                    tiles.putIfAbsent(tile.toString(), tile)
                }
            } else {
                for (i in b.x downTo a.x) {
                    val tile = Tile(i, a.y, true)
                    tiles.putIfAbsent(tile.toString(), tile)
                }
            }
        }
    }

    //
    // Connect the green tiles between the red tiles, horizontally.
    //
    for (tile in tiles.values.toList()) {
        val x = tile.x
        val y = tile.y
        val w = tiles.values.maxOf { it.x }

        for (i in (x + 1..w)) {
            val otherTile = tiles["$i,$y"]
            if (otherTile != null) {
                for (j in x + 1..otherTile.x) {
                    val tile = Tile(j, y, green = true)
                    if (!tiles.contains(tile.toString())) {
                        tiles[tile.toString()] = tile
                    }
                }
            }
        }
    }

    var biggestArea = 0L
    val redTiles = tiles.values.filterNot(Tile::green).toList()

    //
    // Go through every two red tiles and check if they make a valid
    // rectangle together, within the boundaries.
    //
    for (a in redTiles) {
        b_loop@ for (b in redTiles.drop(1)) {
            val left = minOf(a.x, b.x)
            val right = maxOf(a.x, b.x)
            val bottom = minOf(a.y, b.y)
            val top = maxOf(a.y, b.y)
            val t1 = tiles["$left,$bottom"]
            val t2 = tiles["$right,$bottom"]
            val t3 = tiles["$left,$top"]
            val t4 = tiles["$right,$top"]

            if (t1 != null && t2 != null && t3 != null && t4 != null) {
                for (i in t1.x..t2.x) {
                    if (tiles["$i,${t1.y}"] == null) break@b_loop
                }
                for (i in t1.y..t3.y) {
                    if (tiles["${t1.x},$i"] == null) break@b_loop
                }
                for (i in t3.x..t4.x) {
                    if (tiles["$i,${t3.y}"] == null) break@b_loop
                }
                for (i in t2.y..t4.y) {
                    if (tiles["${t2.x},$i"] == null) break@b_loop
                }

                val a1 = originals[a.id]!!
                val b1 = originals[b.id]!!
                val left = minOf(a1.first, b1.first)
                val right = maxOf(a1.first, b1.first)
                val bottom = minOf(a1.second, b1.second)
                val top = maxOf(a1.second, b1.second)

                val area = (right - left + 1) * (top - bottom + 1)
                if (area > biggestArea) {
                    biggestArea = area
                }
            }
        }
    }

    return biggestArea
}

data class Tile(
    val x: Long,
    val y: Long,
    val green: Boolean = false,
    val id: Long = -1L
) {
    override fun toString() = "$x,$y"
}