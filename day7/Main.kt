import java.io.File
import kotlin.math.abs

fun cost(i: Int, j: Int) = (1..abs(i-j)).sum()

fun main() {
    val list = File("input.txt").readText().trim().split(',').map { it.toInt() }.sorted()
    println((0..list.last()).minOfOrNull { list.sumOf { v -> cost(it, v) } })
}
