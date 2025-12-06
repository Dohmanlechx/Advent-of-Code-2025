import java.io.File

object Reader {
    fun example(path: String, split: String? = "\n", trim: Boolean = true) =
        lines("resources/$path/example", split, trim)

    fun input(path: String, split: String? = "\n", trim: Boolean = true) =
        lines("resources/$path/input", split, trim)

    private fun lines(path: String, split: String?, trim: Boolean): List<String> =
        File(path)
            .readText()
            .replace("\r", "")
            .run {
                if (split == null) {
                    listOf(this)
                } else {
                    split(split).let { if (trim) it.map(String::trim) else it }
                }
            }.let {
                if (trim) it.superTrim() else it
            }

    private fun List<String>.superTrim(): List<String> {
        val dirt = "  "
        return map { line ->
            var temp = line
            while (temp.contains(dirt)) {
                temp = temp.replace(dirt, " ")
            }
            temp
        }
    }
}
