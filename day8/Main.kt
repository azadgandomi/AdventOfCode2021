import java.io.File

fun main() {
    val input = File("input.txt").readLines()
    val list = input.map { it.substringAfter('|').trim() }
    var sum = 0
    for (line in input) {
        val first = line.substringBefore('|').trim().split(' ')
        val second = line.substringAfter('|').trim().split(' ')
        val mapping = emptyMap<Set<Char>, Char>().toMutableMap()
        val rmapping = emptyMap<Char, Set<Char>>().toMutableMap()
        for (v in first) {
            when (v.length) {
                2 -> {
                    mapping[v.toSet()] = '1'
                    rmapping['1'] = v.toSet()
                }
                4 -> {
                    mapping[v.toSet()] = '4'
                    rmapping['4'] = v.toSet()
                }
                3 -> {
                    mapping[v.toSet()] = '7'
                    rmapping['7'] = v.toSet()
                }
                7 -> {
                    mapping[v.toSet()] = '8'
                    rmapping['8'] = v.toSet()
                }
            }
        }
        for (v in first) {
            when (v.length) {
                6 -> {
                    if (v.toSet().intersect(rmapping['1']!!).size == 2) {
                        if (v.toSet().intersect(rmapping['4']!!).size == 4) {
                            mapping[v.toSet()] = '9'
                            rmapping['9'] = v.toSet()
                        } else {
                            mapping[v.toSet()] = '0'
                            rmapping['0'] = v.toSet()
                        }
                    } else {
                        mapping[v.toSet()] = '6'
                        rmapping['6'] = v.toSet()
                    }
                }
                5 -> {
                    if (v.toSet().intersect(rmapping['1']!!).size == 2) {
                        mapping[v.toSet()] = '3'
                        rmapping['3'] = v.toSet()
                    } else {
                        if (v.toSet().intersect(rmapping['4']!!).size == 2) {
                            mapping[v.toSet()] = '2'
                            rmapping['2'] = v.toSet()
                        } else {
                            mapping[v.toSet()] = '5'
                            rmapping['5'] = v.toSet()
                        }
                    }
                }
            }
        }
        val number = "".toMutableList()
        for (num in second) {
            number.add(mapping[num.toSet()]!!)
        }
        sum += number.joinToString("").toInt(10)
    }
    val values = list.flatMap { it.split(' ') }
    println("Part1: " + values.sumOf { if (it.length == 2 || it.length == 4 || it.length == 3 || it.length == 7) 1L else 0L })
    println("Part2: $sum")

}
