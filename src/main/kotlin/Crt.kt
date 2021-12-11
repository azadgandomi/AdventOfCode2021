fun computeInverse(a1: Long, b1: Long): Long {
    var a = a1
    var b = b1
    val m = b
    var t: Long
    var q: Long
    var x = 0L
    var y = 1L
    if (b == 1L) return 0
    while (a > 1) {
        q = a / b
        t = b
        b = a % b
        a = t
        t = x
        x = y - q * x
        y = t
    }
    if (y < 0) y += m
    return y
}

fun computeMinX(rem: IntArray, num: IntArray): Long {
    var product = 1L
    for (i in num.indices) {
        product *= num[i]
    }
    val partialProduct = LongArray(num.size)
    val inverse = LongArray(num.size)
    var sum = 0L
    for (i in num.indices) {
        partialProduct[i] = product / num[i]
        inverse[i] = computeInverse(partialProduct[i], num[i].toLong())
        sum += partialProduct[i] * inverse[i] * rem[i]
    }
    return sum % product
}
