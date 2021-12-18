import java.io.File

class Node {
    var left: Node? = null
    var right: Node? = null
    var parent: Node? = null
    var value = -1
    var depth = 0
    var mag = 0
}

class Number(var root: Node) {
    constructor(string: String) : this(createNode(string, null, 0))

    companion object {
        private fun createNode(string: String, parent: Node?, depth: Int): Node {
            val node = Node()
            node.parent = parent
            node.depth = depth
            if (string.length == 1) {
                node.value = string.single().digitToInt()
            } else {
                var index = -1
                for (c in string.withIndex()) {
                    if (c.value == ',') {
                        if (string.subSequence(0, c.index).count { it == '[' } - string.subSequence(0, c.index)
                                .count { it == ']' } == 1) {
                            index = c.index
                            break
                        }
                    }
                }
                assert(index != -1)
                node.left = createNode(string.substring(1, index), node, depth + 1)
                node.right = createNode(string.substring(index + 1, string.length - 1), node, depth + 1)
            }

            return node
        }
    }

    fun increaseDepths(node: Node){
        node.depth++
        if (node.value == -1) {
            increaseDepths(node.left!!)
            increaseDepths(node.right!!)
        }
    }

    fun sum(other: Number): Number {
        val newRoot = Node()
        root.parent = newRoot
        other.root.parent = newRoot
        increaseDepths(root)
        increaseDepths(other.root)
        newRoot.left = root
        newRoot.right = other.root
        root = newRoot
        reduce()
        return this
    }

    fun reduce() {
        do {
            println(getNodeList().map { it.value to it.depth })
            var cont = explode()
            if (cont) {
                continue
            }
            cont = split()
        } while (cont)
    }

    private fun split(): Boolean {
        val list = getNodeList()
        for (l in list.withIndex()) {
            if (l.value.value > 9) {
                val left = Node()
                val right = Node()
                left.depth = l.value.depth + 1
                right.depth = l.value.depth + 1
                left.parent = l.value
                right.parent = l.value
                left.value = l.value.value / 2
                right.value = l.value.value / 2 + l.value.value % 2
                l.value.value = -1
                l.value.left = left
                l.value.right = right
                return true
            }
        }
        return false
    }

    private fun explode(): Boolean {
        val list = getNodeList()
        for (l in list.withIndex()) {
            if (l.value.depth > 4) {
                val parent = l.value.parent!!
                parent.value = 0
                if (l.index > 0) {
                    list[l.index - 1].value += parent.left!!.value
                }
                if (l.index < list.size - 2) {
                    list[l.index + 2].value += parent.right!!.value
                }
                parent.left = null
                parent.right = null
                return true
            }
        }
        return false
    }

    private fun nodeListStep(node: Node?, list: MutableList<Node>) {
        if (node == null) {
            return
        }
        if (node.value != -1) {
            list.add(node)
        }
        nodeListStep(node.left, list)
        nodeListStep(node.right, list)
    }

    fun getNodeList(): List<Node> {
        val list = emptyList<Node>().toMutableList()
        nodeListStep(root, list)
        return list
    }

    fun calcMag(node: Node = root): Int{
        if (node.value != -1) {
            return node.value
        }
        return 3 * calcMag(node.left!!) + 2 * calcMag(node.right!!)

    }

}

fun main() {
    val input = File("input.txt").readLines()
    var max = 0
    for(i1 in input.indices){
        for(i2 in input.indices){
            val numbers = input.map { Number(it) }
            val n1 = numbers[i1]
            val n2 = numbers[i2]
            println(n1.getNodeList().map { it.value to it.depth })
            println(n2.getNodeList().map { it.value to it.depth })
            var sum = n1.sum(n2).calcMag()
            if(sum > max){
                max = sum
            }
            sum = n2.sum(n1).calcMag()
            if(sum > max){
                max = sum
            }
        }
    }
    println(max)
}
