import java.io.File

fun decode(bits: List<Char>): Pair<Long, Int> {
    var result = 0L
    var end = 0
    if (bits.subList(3, 6).joinToString("").toInt(2) == 4) {
        var start = 6
        val num = emptyList<Char>().toMutableList()
        do {
            val group = bits.subList(start, start + 5)
            start += 5
            num.addAll(group.subList(1, 5))
        } while (group[0] == '1')
        end = start
        result = num.joinToString("").toLong(2)
    } else {
        val nums = emptyList<Long>().toMutableList()
        if (bits[6] == '0') {
            val length = bits.subList(7, 22).joinToString("").toInt(2)
            var start = 22
            end = start
            do {
                val res = decode(bits.subList(start, bits.size))
                nums.add(res.first)
                end += res.second
                start = end
            } while (end - 22 < length)
        } else {
            val count = bits.subList(7, 18).joinToString("").toInt(2)
            var start = 18
            end = start
            for (i in 1..count) {
                val res = decode(bits.subList(start, bits.size))
                nums.add(res.first)
                end += res.second
                start = end
            }
        }
        result = when (bits.subList(3, 6).joinToString("").toInt(2)) {
            0 -> nums.sum()
            1 -> nums.reduce { acc, i -> acc * i }
            2 -> nums.minOrNull()!!
            3 -> nums.maxOrNull()!!
            5 -> if (nums[0] > nums[1]) 1 else 0
            6 -> if (nums[0] < nums[1]) 1 else 0
            7 -> if (nums[0] == nums[1]) 1 else 0
            else -> throw Exception()
        }
    }
    return result to end
}

fun main() {
    val bits = File("input.txt").readText().trim().flatMap {
        Integer.toBinaryString(it.toString().toInt(16)).padStart(4, '0').toList()
    }
    println(decode(bits).first)
}
