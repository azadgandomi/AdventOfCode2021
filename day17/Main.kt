import java.io.File
import kotlin.math.abs

fun main() {
    val regex = "target area: x=(.+)\\.\\.(.+), y=(.+)\\.\\.(.+)".toRegex()
    val input = File("input.txt").readText().trim()
    val (x1, x2, y1, y2) = regex.find(input)!!.destructured.toList().map { it.toInt() }
    val rx = 0..maxOf(abs(x1), abs(x2))
    val ry = minOf(y1, y2)..(maxOf(abs(y1), abs(y2))+1)
    val list = emptySet<Pair<Int,Int>>().toMutableSet()
    for(vx in rx){
        for (vy in ry) {
            var t = 0
            var y = 0
            var x = 0
            var currVx = vx
            while (y >= y1 && x <= x2) {
                if(y <= y2 && x >= x1){
                    println("x=$x, y=$y")
                    list.add(vx to vy)
                    break
                }
                y += vy-t
                x += currVx
                currVx = if (currVx > 0) currVx - 1 else currVx
                t++
            }
        }
    }
    println("max = ${list.size}")
}
