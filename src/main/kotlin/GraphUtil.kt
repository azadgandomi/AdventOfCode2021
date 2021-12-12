import java.io.File

fun getAdjVertices(v: String, edges: List<List<String>>) =
    edges.filter { v in it }.map { if (it.first() == v) it.last() else it.first() }


fun canSelectVertex(v: String, visited: Map<String, Int>): Boolean {
    return v != "start" && (v.first().isUpperCase() || visited[v]!! < 1)
}

fun allPaths(s: String, e: String, edges: List<List<String>>, visited: MutableMap<String, Int>, path: MutableList<String>): Set<List<String>> {
    if (s == e) {
        return setOf(path.toList())
    }
    visited[s] = visited[s]!! + 1
    val paths = emptySet<List<String>>().toMutableSet()
    for (v in getAdjVertices(s, edges)) {
        if (canSelectVertex(v, visited)) {
            path.add(v)
            paths += allPaths(v, e, edges, visited, path)
            path.removeLast()
        }
    }
    visited[s] = visited[s]!! - 1
    return paths
}

fun main() {
    val edges = File("input.txt").readLines().map { it.split('-') }
    val vertices = edges.flatten().toSet()
    val counts = vertices.associateWith { 0 }.toMutableMap()
    println(allPaths("start", "end", edges, counts, emptyList<String>().toMutableList()).size)
}
