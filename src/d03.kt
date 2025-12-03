data class Battery(
    val value: Int,
    val index: Int
)

fun main() {
    require(solve(2) == 17278L)
    require(solve(12) == 171528556468625L)
}

private fun solve(count: Int): Long {
    return Reader.input("d03")
        .fold(0L) { acc, bank ->
            val batteries = bank.toBatteries().withIndex().map { Battery(it.value, it.index) }
            var joltage = ""
            var desiredValue = 9
            var batteriesToDrop = 0

            while (joltage.count() < count) {
                val batteriesToCheck =
                    batteries
                        .drop(batteriesToDrop)
                        .dropLast(count - 1 - joltage.count())

                for (battery in batteriesToCheck) {
                    if (desiredValue == battery.value) {
                        joltage += battery.value.toString()
                        batteriesToDrop = battery.index + 1
                        desiredValue = 10
                        break
                    }
                }

                desiredValue--
            }

            acc + joltage.toLong()
        }
}

private fun String.toBatteries(): List<Int> =
    split("").filter(String::isNotEmpty).map(String::toInt)