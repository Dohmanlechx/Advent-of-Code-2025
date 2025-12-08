import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.pow

typealias Circuit = Set<Box>

fun main() {
    require(solve(false) == 63920L)
    require(solve(true) == 1026594680L)
}

private fun solve(p2: Boolean): Long {
    val boxes = boxes()
    var distances = mutableMapOf<Double, Pair<Box, Box>>()

    for (box in boxes) {
        for (otherBox in boxes) {
            val distance = box.distanceTo(otherBox)
            distances[distance] = Pair(box, otherBox)
        }
    }

    distances = distances.toSortedMap()
    val circuits = mutableListOf<Circuit>()
    lateinit var lastPair: Pair<Box, Box>

    for (boxPair in distances.values.take(if (p2) distances.values.size else 1000)) {
        val b1 = boxPair.first
        val b2 = boxPair.second

        if (circuits.any { c -> b1 in c && b2 in c }) {
            continue
        }

        if (circuits.none { c -> b1 in c || b2 in c }) {
            circuits.add(setOf(b1, b2))
            continue
        }

        val c1 = circuits.find { b1 in it }
        val c2 = circuits.find { b2 in it }

        if (c1 != null && c2 != null) {
            val mergedBoxes = c1 union c2
            circuits.removeAll(listOf(c1, c2))
            circuits.add(mergedBoxes)
        } else {
            val circuit = c1 ?: c2 ?: throw IllegalStateException()
            val mergedBoxes = circuit union setOf(b1, b2)
            circuits.remove(circuit)
            circuits.add(mergedBoxes)
        }

        lastPair = Pair(b1, b2)
    }

    return if (p2) {
        lastPair.first.x.toLong() * lastPair.second.x.toLong()
    } else {
        circuits
            .sortedBy { it.count() }
            .takeLast(3)
            .fold(1L) { acc, circuit -> acc * circuit.count() }
    }
}

private fun boxes(): List<Box> =
    Reader.input("d08").map {
        val v = it.split(",")
        Box(v[0].toDouble(), v[1].toDouble(), v[2].toDouble())
    }

data class Box(val x: Double, val y: Double, val z: Double) {
    fun distanceTo(other: Box): Double {
        val x = abs(x - other.x)
        val y = abs(y - other.y)
        val z = abs(z - other.z)
        return sqrt(x.pow(2) + y.pow(2) + z.pow(2))
    }
}