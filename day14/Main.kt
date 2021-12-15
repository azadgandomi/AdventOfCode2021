import java.io.File

fun main() {
    val input = File("input.txt").readText().trim()
    val template = input.substringBefore("\n\n").trim()
    val list = input.substringAfter("\n\n").trim().split("\n").map { it.split(" -> ") }
    val breakList = list.map { it.first() }

    var map = emptyMap<String, Long>().toMutableMap().toMutableMap()
    for (x in template.windowed(2)) {
        map.compute(x) { _, v -> if (v == null) 1 else v + 1L }
    }
    println(map)
    for (i in 1..40) {
        val newM = emptyMap<String, Long>().toMutableMap()
        for (part in map) {
            if (part.key in breakList) {
                for (l in list) {
                    if (l.first() == part.key) {
                        newM.compute(part.key.first() + l.last()) { _, v -> if (v == null) part.value else v + part.value }
                        newM.compute(l.last() + part.key.last()) { _, v -> if (v == null) part.value else v + part.value }
                        break
                    }
                }
            } else {
                println("OOPS")
                newM.compute(part.key) { _, v -> if (v == null) part.value else v + part.value }

            }
        }
        map = newM
    }
    println(map)
    val count = map.keys.flatMap { it.toList() }.associateWith { 0L }.toMutableMap()
    for (part in map) {
        count[part.key.first()] = count[part.key.first()]!! + part.value
    }
    count['K'] = count['K']!! + 1
    println(count)
    println(count.maxOf { it.value } - count.minOf { it.value })
}
