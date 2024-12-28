import kotlin.math.absoluteValue

class Day16(private val file: String) {

    data class Position(val x: Int, val y: Int) {
        fun move(facing: Facing): Position {
            return when (facing) {
                Facing.EAST -> Position(x + 1, y)
                Facing.NORTH -> Position(x, y - 1)
                Facing.WEST -> Position(x - 1, y)
                Facing.SOUTH -> Position(x, y + 1)
            }
        }
    }

    enum class Facing {
        EAST, NORTH, WEST, SOUTH;

        fun cost(to: Facing) = when ((this.ordinal - to.ordinal).absoluteValue) {
            0 -> 0
            2 -> 2000
            else -> 1000
        }
    }

    enum class BlockType {
        WALL, SPACE, END
    }

    data class Block(val type: BlockType, val costs: MutableMap<Facing, Int> = Facing.entries.associateWith { Int.MAX_VALUE }.toMutableMap()) {
        fun cost(facing: Facing) = costs[facing] ?: Int.MAX_VALUE
    }

    data class Path(val position: Position, val facing: Facing, val path: List<Position>)

    class Maze(private val blocks: Array<Array<Block>>, val start: Position) {

        var minimalCost = Int.MAX_VALUE
        val minimalPathPositions = mutableSetOf<Position>()

        fun block(position: Position): Block {
            return blocks[position.y][position.x]
        }

        fun floodFill(openPositions: List<Path>): List<Path> {
            return openPositions.flatMap { (position, facing, path) ->
                val block = block(position)
                val previousCost = block.cost(facing)

                Facing.entries
                    .mapNotNull { nextFacing ->
                        val nextPosition = position.move(nextFacing)
                        val nextBlock = block(nextPosition)

                        if (nextBlock.type == BlockType.WALL) {
                            return@mapNotNull null
                        }

                        val nextCost = previousCost + 1 + facing.cost(nextFacing)

                        if (nextCost > minimalCost) {
                            return@mapNotNull null
                        }

                        val nextBlockAlreadyCost = nextBlock.cost(nextFacing)

                        if (nextCost > nextBlockAlreadyCost) {
                            return@mapNotNull null
                        }

                        if (nextBlock.type == BlockType.END) {
                            if (nextCost < minimalCost) {
                                minimalPathPositions.clear()
                            }

                            minimalPathPositions.addAll(path)
                            minimalPathPositions.add(nextPosition)

                            minimalCost = nextCost
                            return@mapNotNull null
                        }

                        nextBlock.costs[nextFacing] = nextCost

                        Path(nextPosition, nextFacing, path + nextPosition)
                    }
            }
        }
    }

    private fun loadInput(): Maze {
        lateinit var start: Position
        val mazeInput = file.fileLines()
            .mapIndexed { y, line ->
                line.mapIndexed { x, char ->
                    when (char) {
                        '#' -> Block(BlockType.WALL)
                        '.' -> Block(BlockType.SPACE)
                        'E' -> Block(BlockType.END)
                        'S' -> {
                            start = Position(x, y)
                            Block(BlockType.SPACE, Facing.entries.associateWith { Facing.EAST.cost(it) }.toMutableMap())
                        }
                        else -> throw IllegalArgumentException("Invalid character: $char")
                    }
                }.toTypedArray()
            }.toTypedArray()

        return Maze(mazeInput, start)
    }

    fun part1(): Int {
        val maze = loadInput()

        var openPositions = listOf(Path(maze.start, Facing.EAST, listOf(maze.start)))

        while (openPositions.isNotEmpty()) {
            openPositions = maze.floodFill(openPositions)
        }

        return maze.minimalCost
    }

    fun part2(): Int {
        val maze = loadInput()

        var openPositions = listOf(Path(maze.start, Facing.EAST, listOf(maze.start)))

        while (openPositions.isNotEmpty()) {
            openPositions = maze.floodFill(openPositions)
        }

        return maze.minimalPathPositions.size
    }

}