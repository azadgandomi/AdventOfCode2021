import java.io.File

fun main() {
    val list = File("input.txt").readText().trim().split(',').map { it.toInt() }.toMutableList()
    val map = list.groupingBy { it }.eachCount().map { it.key to it.value.toLong() }.toMap()
    val counts = (0..8).map { map.getOrDefault(it, 0L) }.toMutableList()
    for (n in 1..256) {
        val spawns = counts[0]
        for (i in 0..7) {
            counts[i] = counts[i + 1]
        }
        counts[6] += spawns
        counts[8] = spawns
    }

    println(counts.sum())
}
