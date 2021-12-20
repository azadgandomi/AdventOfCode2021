import java.io.File

class Node {
    var left: Node? = null
    var right: Node? = null
    var parent: Node? = null
    var value = -1
    val depth: Int
        get() {
            var res = 0
            var current = parent
            while (current != null) {
                current = current.parent
                ++res
            }
            return res
        }

    fun deepCopy(parent: Node?): Node {
        val node = Node()
        node.parent = parent
        if (value == -1) {
            node.left = left!!.deepCopy(node)
            node.right = right!!.deepCopy(node)
        } else {
            node.value = value
        }
        return node
    }
}

class Number(private var root: Node) {
    constructor(string: String) : this(createNode(string, null))

    companion object {
        private fun createNode(string: String, parent: Node?): Node {
            val node = Node()
            node.parent = parent
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
                node.left = createNode(string.substring(1, index), node)
                node.right = createNode(string.substring(index + 1, string.length - 1), node)
            }

            return node
        }
    }

    fun sum(other: Number): Number {
        val newRoot = Node()
        newRoot.left = root.deepCopy(newRoot)
        newRoot.right = other.root.deepCopy(newRoot)
        val res = Number(newRoot)
        res.reduce()
        return res
    }

    private fun reduce() {
        do {
            var cont = explode()
            if (cont) {
                continue
            }
            cont = split()
        } while (cont)
    }

    private fun split(): Boolean {
        for (l in getLeafList()) {
            if (l.value > 9) {
                val left = Node()
                val right = Node()
                left.parent = l
                right.parent = l
                left.value = l.value / 2
                right.value = l.value / 2 + l.value % 2
                l.value = -1
                l.left = left
                l.right = right
                return true
            }
        }
        return false
    }

    private fun explode(): Boolean {
        val list = getLeafList()
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

    private fun getLeafList(): List<Node> {
        fun nodeListStep(node: Node, list: MutableList<Node>) {
            if (node.value != -1) {
                list.add(node)
            } else {
                nodeListStep(node.left!!, list)
                nodeListStep(node.right!!, list)
            }
        }

        val list = emptyList<Node>().toMutableList()
        nodeListStep(root, list)
        return list
    }

    fun calcMag(node: Node = root): Int {
        if (node.value != -1) {
            return node.value
        }
        return 3 * calcMag(node.left!!) + 2 * calcMag(node.right!!)

    }

}

fun main() {
    val input = File("input.txt").readLines()
    val numbers = input.map { Number(it) }
    var max = 0
    for (i1 in input.indices) {
        for (i2 in input.indices) {
            val n1 = numbers[i1]
            val n2 = numbers[i2]
            var sum = n1.sum(n2).calcMag()
            if (sum > max) {
                max = sum
            }
            sum = n2.sum(n1).calcMag()
            if (sum > max) {
                max = sum
            }
        }
    }
    println(max)
}
