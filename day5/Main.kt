import java.io.File

private infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}

fun main() {
    val regex = "(.+),(.+) -> (.+),(.+)".toRegex()
    val data = emptyMap<Pair<Int, Int>, Int>().toMutableMap()
    val lines = File("input.txt").readLines()
    for (l in lines) {
        val (x1, y1, x2, y2) = regex.find(l)!!.destructured.toList().map { it.toInt() }
        when {
            x1 == x2 ->
                for (y in y1 toward y2) {
                    data.compute(x1 to y) { _, v -> 1 + (v ?: 0) }
                }

            y1 == y2 ->
                for (x in x1 toward x2) {
                    data.compute(x to y1) { _, v -> 1 + (v ?: 0) }
                }

            else ->
                for ((x, y) in (x1 toward x2).zip(y1 toward y2)) {
                    data.compute(x to y) { _, v -> 1 + (v ?: 0) }

                }
        }
    }

    println(data.values.sumOf { v -> if (v >= 2) 1 else 0L })
}
