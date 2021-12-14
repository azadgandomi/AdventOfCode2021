import java.io.File

fun main() {
    val regex = "fold along ([xy])=(.*)".toRegex()
    val input = File("input.txt").readText().trim()
    val coords = input.substringBefore("\n\n").trim().split('\n').map { it.split(',').map { it.toInt() } }
    val folds = input.substringAfter("\n\n").trim().split('\n').map {
        val (k, num) = regex.find(it)!!.destructured
        (k == "x") to num.toInt()
    }
    var grid = emptyMap<Pair<Int, Int>, Boolean>().toMutableMap()
    for (c in coords) {
        grid[c.first() to c.last()] = true
    }
    for (f in folds) {
        if (f.first) {
            val newGrid = emptyMap<Pair<Int, Int>, Boolean>().toMutableMap()
            for (g in grid) {
                if (g.key.first < f.second) {
                    newGrid[g.key] = true
                } else {
                    newGrid[(2 * f.second - g.key.first to g.key.second)] = true
                }
            }
            grid = newGrid
        } else {
            val newGrid = emptyMap<Pair<Int, Int>, Boolean>().toMutableMap()
            for (g in grid) {
                if (g.key.second < f.second) {
                    newGrid[g.key] = true
                } else {
                    newGrid[(g.key.first to 2 * f.second - g.key.second)] = true
                }
            }
            grid = newGrid
        }
    }
    val arr = Array(6) { CharArray(40) }
    println(grid.values.sumOf { if (it) 1L else 0L })
    for (i in 0 until 6) {
        for (j in 0 until 40) {
            if (grid.getOrDefault(j to i, false)) {
                arr[i][j] = '#'
            } else {
                arr[i][j] = '.'
            }
        }
    }
    for(i in arr.indices){
        for (j in arr[0].indices){
            print(arr[i][j])
        }
        println()
    }
}
