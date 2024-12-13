import java.util.SortedSet

class Day06(private val file: String) {

    private fun loadInput(): Triple<Pair<Int, Int>, Pair<Int, Int>, Set<Pair<Int, Int>>> {
        val lines = file.fileLines()

        var startPosition: Pair<Int, Int>? = null
        val obstacleMap = mutableSetOf<Pair<Int, Int>>()

        lines.forEachIndexed { y, row ->
            row.forEachIndexed { x, character ->
                when (character) {
                    '^' -> startPosition = x to y
                    '#' -> obstacleMap.add(x to y)
                }
            }
        }

        if (startPosition == null) {
            throw IllegalArgumentException("No start position found")
        }

        return Triple(startPosition!!, lines[0].length to lines.size, obstacleMap.toSet())
    }

    data class GuardPath(
        val from: Pair<Int, Int>,
        val to: Pair<Int, Int>,
        val direction: Direction,
    ) {
        fun exitingMap(boundaries: Pair<Int,Int>): Boolean {
            val (maxX, maxY) = boundaries

            return when (direction) {
                Direction.UP -> to.second == 0
                Direction.DOWN -> to.second == maxY-1
                Direction.LEFT -> to.first == 0
                Direction.RIGHT -> to.first == maxX-1
            }
        }

        val steps: List<Pair<Int,Int>> by lazy {
            when (direction) {
                Direction.UP -> (from.second downTo to.second).map { y -> from.first to y }
                Direction.DOWN -> (from.second..to.second).map { y -> from.first to y }
                Direction.LEFT -> (from.first downTo  to.first).map { x -> x to from.second }
                Direction.RIGHT -> (from.first.. to.first).map { x -> x to from.second }
            }
        }
    }

    enum class Direction() {
        UP, RIGHT, DOWN, LEFT;

        val turnRight: Direction by lazy {
            when (this) {
            UP -> RIGHT
            LEFT -> UP
            DOWN -> LEFT
            RIGHT -> DOWN
        } }
    }

    private fun nextPosition(
        position: Pair<Int, Int>,
        direction: Direction,
        obstaclesByRow: Map<Int, SortedSet<Int>>,
        obstaclesByColumn: Map<Int, SortedSet<Int>>,
        mapDimensions: Pair<Int, Int>
    ): Pair<Int, Int> {
        val (x, y) = position

        return when (direction) {
            Direction.UP -> {
                val nextObstacle = obstaclesByColumn[x]?.lastOrNull { it < y } ?: -1
                x to nextObstacle+1
            }
            Direction.DOWN -> {
                val nextObstacle = obstaclesByColumn[x]?.firstOrNull { it > y } ?: mapDimensions.second
                x to nextObstacle-1
            }
            Direction.LEFT -> {
                val nextObstacle = obstaclesByRow[y]?.lastOrNull { it < x } ?: -1
                nextObstacle+1 to y
            }
            Direction.RIGHT -> {
                val nextObstacle = obstaclesByRow[y]?.firstOrNull { it > x } ?: mapDimensions.first
                nextObstacle-1 to y
            }
        }
    }

    private fun walk(
        direction: Direction,
        pathStart: Pair<Int, Int>,
        previousPaths: List<GuardPath>,
        boundaries: Pair<Int, Int>,
        obstaclesByRow: Map<Int, SortedSet<Int>>,
        obstaclesByColumn: Map<Int, SortedSet<Int>>,
    ): List<GuardPath> {
        val nextPosition = nextPosition(pathStart, direction, obstaclesByRow, obstaclesByColumn, boundaries)
        val path = GuardPath(pathStart, nextPosition, direction)

        if (path.exitingMap(boundaries)) {
            return previousPaths + path
        }

        if (previousPaths.any { nextPosition == it.from }) {
            return previousPaths + path
        }

        return walk(
            direction = direction.turnRight,
            pathStart = nextPosition,
            previousPaths = previousPaths + path,
            boundaries = boundaries,
            obstaclesByRow = obstaclesByRow,
            obstaclesByColumn = obstaclesByColumn
        )
    }

    fun part1(): Int {
        val (startPosition, mapDimensions, obstacleMap) = loadInput()

        val obstaclesByRow = obstacleMap.groupBy { it.second }.mapValues { (_, obstacles) ->
            obstacles.map { it.first }.toSortedSet()
        }

        val obstaclesByColumn = obstacleMap.groupBy { it.first }.mapValues { (_, obstacles) ->
            obstacles.map { it.second }.toSortedSet()
        }

        val paths = walk(
            direction = Direction.UP,
            pathStart = startPosition,
            previousPaths = emptyList(),
            boundaries = mapDimensions,
            obstaclesByRow = obstaclesByRow,
            obstaclesByColumn = obstaclesByColumn
        )

        val visited = paths
            .flatMap { it.steps }
            .toSet()

        return visited.size
    }

    private fun walk2(
        addObstacle: Boolean,
        direction: Direction,
        pathStart: Pair<Int, Int>,
        previousPaths: List<GuardPath>,
        previousPathStarts: List<Pair<Int, Int>>,
        boundaries: Pair<Int, Int>,
        obstaclesByRow: Map<Int, SortedSet<Int>>,
        obstaclesByColumn: Map<Int, SortedSet<Int>>,
    ): Int {
        val nextPosition = nextPosition(pathStart, direction, obstaclesByRow, obstaclesByColumn, boundaries)
        val path = GuardPath(pathStart, nextPosition, direction)

        if (path.exitingMap(boundaries)) {
            if (!addObstacle || previousPaths.size < 4) {
                return 0
            }

            val allPaths = previousPaths + path
            val firstPath = allPaths.first()

            return allPaths
                .flatMap { lastPath ->
                    lastPath.steps.drop(1)
                }
                .toSet()
                .sumOf { obstacle ->
                        val addedObstaclesByRow = obstaclesByRow.toMutableMap()
                        addedObstaclesByRow.merge(obstacle.second, sortedSetOf(obstacle.first)) { a, b -> (a + b).toSortedSet() }

                        val addedObstaclesByColumn = obstaclesByColumn.toMutableMap()
                        addedObstaclesByColumn.merge(obstacle.first, sortedSetOf(obstacle.second)) { a, b -> (a + b).toSortedSet() }

                        walk2(
                            addObstacle = false,
                            direction = firstPath.direction,
                            pathStart = firstPath.from,
                            previousPaths = emptyList(),
                            previousPathStarts = emptyList(),
                            boundaries = boundaries,
                            obstaclesByRow = addedObstaclesByRow.toMap(),
                            obstaclesByColumn = addedObstaclesByColumn.toMap()
                        )
                }
        }

        if (previousPathStarts.contains(nextPosition)) {
            return 1
        }

        return walk2(
            addObstacle = addObstacle,
            direction = direction.turnRight,
            pathStart = nextPosition,
            previousPaths = previousPaths + path,
            previousPathStarts = previousPathStarts + pathStart,
            boundaries = boundaries,
            obstaclesByRow = obstaclesByRow,
            obstaclesByColumn = obstaclesByColumn
        )
    }

    fun part2(): Int {
        val (startPosition, mapDimensions, obstacleMap) = loadInput()

        val obstaclesByRow = obstacleMap.groupBy { it.second }.mapValues { (_, obstacles) ->
            obstacles.map { it.first }.toSortedSet()
        }

        val obstaclesByColumn = obstacleMap.groupBy { it.first }.mapValues { (_, obstacles) ->
            obstacles.map { it.second }.toSortedSet()
        }

        return walk2(
            addObstacle = true,
            direction = Direction.UP,
            pathStart = startPosition,
            previousPaths = emptyList(),
            previousPathStarts = emptyList(),
            boundaries = mapDimensions,
            obstaclesByRow = obstaclesByRow,
            obstaclesByColumn = obstaclesByColumn
        )
    }
}
