import java.io.File
import java.util.*


private infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}

private operator fun Pair<Int, Int>.plus(pair: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(first + pair.first, second + pair.second)
}

private fun String.to2DMap(separator: String = ""): Map<Pair<Int, Int>, Int> {
    return if (separator == "") {
        this.trim().split('\n').withIndex()
            .flatMap { row ->
                row.value.withIndex().map { col -> (row.index to col.index) to col.value.digitToInt() }
            }
            .toMap()
    } else {
        this.trim().split('\n').withIndex()
            .flatMap { row ->
                row.value.split(separator).withIndex().map { col -> (row.index to col.index) to col.value.toInt() }
            }
            .toMap()
    }
}

private fun String.to2DArray(separator: String = ""): Array<IntArray> {
    return if (separator == "") {
        this.trim().split('\n').map { it.map { it.digitToInt() }.toIntArray() }.toTypedArray()
    } else {
        this.trim().split('\n').map { it.split(separator).map { it.toInt() }.toIntArray() }.toTypedArray()
    }
}

fun main() {
    val input = File("input.txt").readText().to2DArray()
    val lps = emptyMap<Pair<Int, Int>, Int>().toMutableMap()
    for (i in input.indices) {
        for (j in input.first().indices) {
            if ((i == 0 || input[i][j] < input[i - 1][j])
                && (i == input.size - 1 || input[i][j] < input[i + 1][j])
                && (j == 0 || input[i][j] < input[i][j - 1])
                && (j == input[i].size - 1 || input[i][j] < input[i][j + 1])
            ) {
                lps[i to j] = 0
            }
        }
    }
    for (p in lps) {
        val set = emptySet<Pair<Int, Int>>().toMutableSet()
        set.add(p.key)
        do {
            val prevSize = set.size
            for ((i, j) in set.toSet()) {
                if (i > 0 && input[i][j] < input[i - 1][j] && input[i - 1][j] != 9) {
                    set.add(i - 1 to j)
                }
                if (i < input.size - 1 && input[i][j] < input[i + 1][j] && input[i + 1][j] != 9) {
                    set.add(i + 1 to j)
                }
                if (j > 0 && input[i][j] < input[i][j - 1] && input[i][j - 1] != 9) {
                    set.add(i to j - 1)
                }
                if (j < input[i].size - 1 && input[i][j] < input[i][j + 1] && input[i][j + 1] != 9) {
                    set.add(i to j + 1)
                }
            }
        } while (prevSize != set.size)
        lps[p.key.first to p.key.second] = set.size
    }
    val s = lps.values.sortedDescending()
    println(s[0] * s[1] * s[2])
}
