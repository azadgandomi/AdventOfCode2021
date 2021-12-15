import java.io.File
import java.util.*
import kotlin.collections.HashMap

class Node(val pos: Pair<Int, Int>, val sum: Int)

fun main() {
    val input = HashMap<Pair<Int,Int>, Int>()
    val array = File("input.txt").readText().trim().to2DArray()
    val dim = array.size*5 to array.first().size*5
    for(x in 0..4){
        for (y in 0..4){
            for (r in array.withIndex()){
                for (v in r.value.withIndex()){
                    input[x*array.size + r.index to y*array.first().size + v.index] = (v.value + x + y) % 10 + (v.value + x + y) / 10
                }
            }
        }
    }

    val queue = PriorityQueue<Node> { x, y -> x.sum - y.sum}
    val visited = emptySet<Pair<Int,Int>>().toMutableSet()
    queue.add(Node(0 to 0, 0))
    while (queue.isNotEmpty()) {
        val node = queue.remove()
        if(node.pos in visited){
            continue
        }
        visited.add(node.pos)
        if (node.pos == dim.first - 1 to dim.second - 1) {
            println(node.sum)
            break
        }
        for (a in adjacentNonDiagonal) {
            val adj = a + node.pos
            if (input.containsKey(adj) && adj !in visited) {
                queue.add(Node(adj, node.sum + input[adj]!!))
            }
        }
    }
}
