typealias Button = List<Int>

fun main() {
    require(solveP1() == 494)
}

private fun solveP1(): Int {
    val input = Reader.input("d10")
    val machines = input.map { line -> line.drop(1).substringBefore("]").map { it == '#' } }
    val buttonsList = input.map { line ->
        line.split(" ")
            .filter { it.contains('(') }
            .map { it.replace("(", "").replace(")", "") }
            .map { it.split(",").map(String::toInt) }
    }

    var totalPresses = 0
    var presses: Int

    for ((index, machine) in machines.withIndex()) {
        val buttons = buttonsList[index]
        val tree = buttons.associateWith { buttons }
        presses = 1

        loop@ while (true) {
            for (i in buttons.indices) {
                for (possibility in possibilities(tree, buttons[i], presses)) {
                    if (possibility satisfies machine) {
                        totalPresses += presses
                        break@loop
                    }
                }
            }
            presses++
        }
    }

    return totalPresses
}

private fun solveP2(): Long {
    return 0L
}

private fun possibilities(
    graph: Map<Button, List<Button>>,
    start: Button,
    maxDepth: Int
): List<List<Button>> {
    val queue = ArrayDeque<List<Button>>()
    val results = mutableListOf<List<Button>>()
    queue.add(listOf(start))

    while (queue.isNotEmpty()) {
        val path = queue.removeFirst()
        results.add(path)
        if (path.size == maxDepth) {
            continue
        }
        val last = path.last()
        for (next in graph[last].orEmpty()) {
            val new = path.toMutableList().also { it.add(next) }
            queue.add(new)
        }
    }
    
    return results
}

private infix fun List<Button>.satisfies(machine: List<Boolean>): Boolean {
    val lights = machine.toBooleanArray()
    for (button in this) {
        for (i in button) {
            lights[i] = !lights[i]
        }
    }
    return lights.none { it }
}