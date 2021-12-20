import java.io.File

fun main() {
    val input = File("input.txt").readText().trim()
    val code = input.substringBefore('\n').trim()
    var grid = input.substringAfter('\n').trim().to2DBinaryMap()

    val lines = input.substringAfter('\n').trim().split('\n')
    var dim = lines.size to lines.first().length
    var start = 0 to 0
    for (k in 1..50) {
        dim = (dim.first + 1 to dim.second + 1)
        start = (start.first -1 to start.second-1)
        val output = emptyMap<Pair<Int, Int>, Boolean>().toMutableMap()
        for (i in start.first until dim.first) {
            for (j in start.first until dim.second) {
                val str = emptyList<Char>().toMutableList()
                for (adj in adjacentInclusive) {
                    str.add(if (grid.getOrDefault((i to j) + adj, k%2 == 0)) '1' else '0')
                }
                val index = str.joinToString("").toInt(2)
                output[(i to j)] = code[index] == '#'
            }
        }
        grid = output
    }
    println(grid.values.count { it })
}

