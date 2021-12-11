import java.io.File

val whitespace = " +".toRegex()

class Board(private val data: Array<IntArray>) {
    private var marked: Array<BooleanArray> = data.map { it.map { false }.toBooleanArray() }.toTypedArray()

    constructor(str: String) : this(str.trim().split('\n').map { row ->
        row.trim().split(whitespace).map { it.toInt() }.toIntArray()
    }.toTypedArray())

    fun mark(value: Int) {
        for (r in data.withIndex()) {
            for (c in r.value.withIndex()) {
                if (value == c.value) {
                    marked[r.index][c.index] = true
                }
            }
        }
    }

    fun winner() =
        marked.any { r -> r.all { it } } || marked.first().indices.any { c -> marked.indices.all { r -> marked[r][c] } }

    fun preScore() = data.withIndex()
        .sumOf { r -> r.value.withIndex().sumOf { if (!marked[r.index][it.index]) it.value else 0 } }
}

fun main() {
    val text = File("input.txt").readText().trim()
    val values = text.substringBefore('\n').split(',').map { it.toInt() }
    val boards = text.substringAfter('\n').split("\n\n").map { Board(it) }
    for (v in values) {
        boards.forEach {
            if (!it.winner()) {
                it.mark(v)
                if (it.winner()) {
                    println(v * it.preScore())
                }
            }
        }
        if (boards.all { it.winner() }) {
            break
        }
    }
}