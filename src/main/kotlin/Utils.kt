infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}

val adjacentInclusive = listOf((-1 to -1), (-1 to 0), (-1 to 1), (0 to -1), (0 to 0), (0 to 1), (1 to -1), (1 to 0), (1 to 1))
val adjacentNonDiagonal = listOf((-1 to 0), (0 to -1), (0 to 1), (1 to 0))

operator fun Pair<Int, Int>.plus(pair: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(first + pair.first, second + pair.second)
}

fun String.to2DMap(separator: String = ""): Map<Pair<Int, Int>, Int> {
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

fun String.to2DBinaryMap(): Map<Pair<Int, Int>, Boolean> {
    return this.trim().split('\n').withIndex()
        .flatMap { row ->
            row.value.withIndex().map { col -> (row.index to col.index) to (col.value == '#') }
        }
        .toMap()

}

fun String.to2DArray(separator: String = ""): Array<IntArray> {
    return if (separator == "") {
        this.trim().split('\n').map { it.map { it.digitToInt() }.toIntArray() }.toTypedArray()
    } else {
        this.trim().split('\n').map { it.split(separator).map { it.toInt() }.toIntArray() }.toTypedArray()
    }
}
