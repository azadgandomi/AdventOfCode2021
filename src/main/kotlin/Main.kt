import java.io.File

data class Args(val p1: Int, val p2: Int, val s1: Int, val s2: Int, val sum: Int, val count: Int, val first: Boolean)

val cache = emptyMap<Args, Long>().toMutableMap()

fun calcScore(p1: Int, p2: Int, s1: Int, s2: Int, sum: Int, count: Int, first: Boolean): Long {
    var result = cache[Args(p1, p2, s1, s2, sum, count, first)] ?: 0L
    if(result != 0L){
        return result
    }
    var newP1 = p1
    var newP2 = p2
    var newS1 = s1
    var newS2 = s2
    var newSum = sum
    if (count > 0 && count % 3 == 0) {
        if (count % 2 != 0) {
            newP1 = (p1 + sum) % 10
            newS1 += newP1 + 1
        } else {
            newP2 = (p2 + sum) % 10
            newS2 += newP2 + 1
        }
        newSum = 0
        if (newS1 >= 21) {
            return if (first) 1 else 0
        }
        if (newS2 >= 21) {
            return if (first) 0 else 1
        }
    }
    for (k in 1..3) {
        result += calcScore(newP1, newP2, newS1, newS2, newSum + k, count + 1, first)
    }
    cache[Args(p1, p2, s1, s2, sum, count, first)] = result
    return result
}

fun main() {
    //val input = File("input.txt").readLines()
    var p1 = 0
    var p2 = 1
    var s1 = 0
    var s2 = 0
    var dice = (1..100).iterator()
    for (k in 1..Int.MAX_VALUE) {
        var sum = 0
        for (i in 1..3) {
            if (!dice.hasNext()) {
                dice = (1..100).iterator()
            }
            sum += dice.next()
        }
        if (k % 2 == 1) {
            p1 = (p1 + sum) % 10
            s1 += p1 + 1
        } else {
            p2 = (p2 + sum) % 10
            s2 += p2 + 1
        }
        if (s1 >= 1000 || s2 >= 1000) {
            println("s1 = $s1 \t s2 = $s2")
            println(k * 3)
            println(minOf(s1, s2) * k * 3)
            break
        }
    }
    println(calcScore(0, 1, 0, 0, 0, 0, true))
    println(calcScore(0, 1, 0, 0, 0, 0, false))
}
