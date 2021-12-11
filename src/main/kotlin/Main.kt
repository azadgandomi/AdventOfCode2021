import java.io.File

fun main() {
    val input = File("input.txt").readText().to2DMap().toMutableMap()
    var count = 0
    for (i in 1..Int.MAX_VALUE) {
        val flashes = emptySet<Pair<Int, Int>>().toMutableSet()
        for (e in input) {
            input.computeIfPresent(e.key) { _, v -> v + 1 }
        }
        do {
            val flashCount = flashes.size
            for (e in input) {
                if ((e.value > 9) && e.key !in flashes) {
                    count++
                    flashes.add(e.key)
                    for (a in adjacentAll) {
                        input.computeIfPresent(e.key + a) { _, v -> v + 1 }
                    }
                }
            }
        } while (flashCount < flashes.size)
        for (f in flashes) {
            input[f] = 0
        }
        if (input.keys == flashes) {
            println(i)
            return
        }
    }
    println(count)
}
