import java.io.File
import java.lang.Exception
import java.lang.Math.abs

fun main() {
    val perms =
        listOf(listOf(0, 1, 2), listOf(0, 2, 1), listOf(1, 0, 2), listOf(1, 2, 0), listOf(2, 0, 1), listOf(2, 1, 0))
    val signs = listOf(
        listOf(1, 1, 1),
        listOf(1, 1, -1),
        listOf(1, -1, 1),
        listOf(1, -1, -1),
        listOf(-1, 1, 1),
        listOf(-1, 1, -1),
        listOf(-1, -1, 1),
        listOf(-1, -1, -1)
    )
    val regex = """--- scanner .+ ---""".toRegex()
    val input = File("input.txt").readText().trim().split(regex)
        .map { it.trim().split('\n').map { it.split(',').map { it.toInt() } } }
    val list = emptyList<List<Int>>().toMutableList()
    val first = input.first().toMutableSet()
    val others = input.slice(1 until input.size).toMutableList()
    val scanners = emptyList<List<Int>>().toMutableList()
    outer@ while (others.isNotEmpty()) {
        val otherOthers = others.toList()
        for (next in otherOthers.withIndex()) {
            for (p in perms) {
                for (s in signs) {
                    list.clear()
                    for (l1 in first) {
                        for (l2 in next.value) {
                            list.add((0..2).map { l1[it] - s[it] * l2[p[it]] })
                        }
                    }
                    val sortedCounts =
                        list.groupingBy { it }.eachCount().toList().sortedByDescending { pair -> pair.second }
                    if (sortedCounts.first().second >= 12) {
                        scanners.add(sortedCounts.first().first)
                        first.addAll(next.value.map { line -> line.indices.map { sortedCounts.first().first[it] + line[p[it]] * s[it] } })
                        others.removeAt(next.index)
                        continue@outer
                    }
                }
            }
        }
        return
    }
    println(first.size)

    var max = 0
    for (i in scanners) {
        for (j in scanners) {
            val d = kotlin.math.abs(i[0] - j[0]) + kotlin.math.abs(i[1] - j[1]) + kotlin.math.abs(i[2] - j[2])
            if (d > max) {
                max = d
            }
        }
    }
    println(max)
}
