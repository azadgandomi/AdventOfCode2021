import java.io.File

fun main() {
    val edges = File("input.txt").readLines().map { it.split('-') }
    val vertices = edges.flatten().toSet()
    val counts = vertices.associateWith { 0 }.toMutableMap()
    println(allPaths("start", "end", edges, counts, emptyList<String>().toMutableList()).size)
}
