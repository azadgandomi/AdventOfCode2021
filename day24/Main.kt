import java.io.File

inline fun calcZ(w: Int, prevZ: Int, arg: List<Int>): Int {
    var z = prevZ
    val x = z % 26 + arg[1]
    z /= arg[0]
    if (x != w) {
        z *= 26
        z += arg[2] + w
    }
    return z
}


fun main() {
    val input = File("input.txt").readText().trim().substringAfter('\n').split("inp w\n").map { it.trim().split("\n") }
    val args = input.map {
        listOf(
            it[3].split(" ").last().toInt(),
            it[4].split(" ").last().toInt(),
            it[14].split(" ").last().toInt()
        )
    }
    val possibleZs = emptyList<Map<Int, Pair<Int, Int>>>().toMutableList()
    possibleZs.add(mapOf(0 to (-1 to -1)))
    for (k in 0..13) {
        println(k)
        val ppz = possibleZs[k].keys
        val pz = emptyMap<Int, Pair<Int, Int>>().toMutableMap()
        for (w in 9 downTo 1) {
            for (z in ppz) {
                pz[calcZ(w, z, args[k])] = w to z
            }
        }
        possibleZs.add(pz)
    }
    var z = 0
    val output = emptyList<Char>().toMutableList()
    for (p in possibleZs.asReversed()) {
        if (p[z]!!.first > 0) {
            output.add((p[z]!!.first).digitToChar())
            z = p[z]!!.second
        }
    }
    println(output.reversed().joinToString(""))
}
