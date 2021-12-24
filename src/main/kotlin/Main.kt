import java.io.File
import java.lang.Exception
import java.util.*
import javax.swing.text.Utilities
import kotlin.math.abs


fun main() {
    val input = File("input.txt").readLines().map { it.split(" ") }
    val number = "99999999999999".toList().map { it.digitToInt() }.toMutableList()
    do {
//        println(number)
        for (i in 13 downTo 0) {
            if (number[i] > 1) {
                --number[i]
                for (j in i + 1..13) {
                    number[j] = 9
                }
                break
            }
        }
        val iterator = number.iterator()
        val regs = emptyMap<Char, Int>().toMutableMap()
        loop@ for (instruction in input) {
            val first = instruction.first()
            val a = instruction[1].single()
            val last = instruction.last()
            val b = if (last in "wxyz") {
                regs.getOrDefault(last.single(), 0)
            } else {
                last.toInt()
            }
//            println("$first $a $b")
            when (first) {
                "inp" -> {
                    regs[a] = iterator.next()
                }
                "add" -> {
                    regs[a] = regs.getOrDefault(a, 0) + b
                }
                "mul" -> {
                    regs[a] = regs.getOrDefault(a, 0) * b
                }
                "div" -> {
                    regs[a] = regs.getOrDefault(a, 0) / b
                }
                "mod" -> {
                    val valA = regs.getOrDefault(a, 0)
                    if (valA < 0 || b <= 0) {
                        break@loop
                    }
                    regs[a] = valA % b
                }
                "eql" -> {
                    regs[a] = if (regs.getOrDefault(a, 0) == b) 1 else 0
                }
            }
        }
        val z = regs.getOrDefault('z', 0)
    } while (z != 0)
    println(number)
}
