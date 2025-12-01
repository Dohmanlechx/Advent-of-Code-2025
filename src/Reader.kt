import java.io.File

object Reader {
    fun example(path: String, split: String? = "\n") =
        lines("resources/$path/example", split)

    fun input(path: String, split: String? = "\n") =
        lines("resources/$path/input", split)

    private fun lines(path: String, split: String?): List<String> =
        File(path)
            .readText()
            .replace("\r", "")
            .run { if (split == null) listOf(this) else split(split).map(String::trim) }
            .superTrim()

    private fun List<String>.superTrim(): List<String> {
        val trimmedList = mutableListOf<String>()
        val dirt = "  "

        for (line in this) {
            var trimmedLine = line
            while (trimmedLine.contains(dirt)) {
                trimmedLine = trimmedLine.replace(dirt, " ")
            }
            trimmedList.add(trimmedLine)
        }

        return trimmedList
    }
}