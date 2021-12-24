import java.lang.Exception
import java.util.*
import kotlin.math.abs

fun getMultiplier(type: Char) = when (type) {
    'A' -> 1
    'B' -> 10
    'C' -> 100
    'D' -> 1000
    '.' -> 0
    else -> throw Exception()
}

fun getCorrectRoomIndex(type: Char) = when (type) {
    'A' -> 0
    'B' -> 1
    'C' -> 2
    'D' -> 3
    else -> throw Exception()
}

fun printState(stateCost: StateCost) {
    println()
    println(stateCost.cost)
    val s = stateCost.state
    println("#############")
    print('#')
    print(s.hallway.joinToString(""))
    println('#')

    print("###")
    for (r in s.rooms[0]) {
        print("$r#")
    }
    println("##")

    print("  #")
    for (r in s.rooms[1]) {
        print("$r#")
    }
    println()
    println("  #########")
    println()
}


data class State(val hallway: List<Char>, val rooms: List<List<Char>>)
data class StateCost(val state: State, val cost: Int)

fun main() {
    val goals = "ABCD".toList()
    val roomIndex = listOf(2, 4, 6, 8)
    val queue = PriorityQueue<StateCost> { x, y ->
        x.cost - y.cost

    }
    queue.add(
        StateCost(
            State(
                "...........".toList(),
                listOf(
                    listOf('A', 'C', 'B', 'A'),
                    listOf('D', 'C', 'B', 'A'),
                    listOf('D', 'B', 'A', 'C'),
                    listOf('D', 'D', 'B', 'C')
                )
            ),
            0
        )
    )
    val visited = emptySet<State>().toMutableSet()
    while (queue.isNotEmpty()) {
        val stateCost = queue.remove()
        val s = stateCost.state
        if (s in visited) {
            continue
        }
//        printState(stateCost)
        visited.add(s)
        if (s.rooms.all { it == listOf('A', 'B', 'C', 'D') }) {
            printState(stateCost)
            println(stateCost.cost)
            break
        }
        for (room in s.rooms.withIndex()) {
            for (c in room.value.withIndex()) {
                if (c.value != '.') {
                    if ((room.index until s.rooms.size).all { s.rooms[it][c.index] == goals[c.index] }) {
                        continue
                    }
                    if (room.index == 0 || (0 until room.index).all { s.rooms[it][c.index] == '.' }) {
                        val m = getMultiplier(c.value)
                        for (hi in s.hallway.indices) {
                            if (hi !in roomIndex && s.hallway.slice(hi toward roomIndex[c.index]).all { it == '.' }) {
                                val newHallway = s.hallway.toMutableList()
                                newHallway[hi] = c.value
                                val newRooms = s.rooms.toMutableList()
                                val newRoom = room.value.toMutableList()
                                newRoom[c.index] = '.'
                                newRooms[room.index] = newRoom
                                queue.add(
                                    StateCost(
                                        State(
                                            newHallway,
                                            newRooms
                                        ),
                                        stateCost.cost + m * ((room.index + 1) + abs(hi - roomIndex[c.index]))

                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
        for (c in s.hallway.withIndex()) {
            if (c.value != '.') {
                val m = getMultiplier(c.value)
                val index = getCorrectRoomIndex(c.value)
                val newHallway = s.hallway.toMutableList()
                newHallway[c.index] = '.'
                if (newHallway.slice((c.index) toward roomIndex[index]).all { it == '.' }) {
                    for (room in s.rooms.withIndex()) {
                        if (
                            s.rooms[room.index][index] == '.'
                            && (0..room.index).all { s.rooms[it][index] == '.' || s.rooms[it][index] == goals[index] }
                            && (room.index == s.rooms.size - 1 || ((room.index + 1) until s.rooms.size).all { s.rooms[it][index] == goals[index] })
                        ) {
                            val newRooms = s.rooms.toMutableList()
                            val newRoom = room.value.toMutableList()
                            newRoom[index] = c.value
                            newRooms[room.index] = newRoom
                            queue.add(
                                StateCost(
                                    State(
                                        newHallway,
                                        newRooms
                                    ),
                                    stateCost.cost + m * ((room.index + 1) + abs(c.index - roomIndex[index]))

                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
